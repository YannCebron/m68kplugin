/*
 * Copyright 2021 The Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yanncebron.m68kplugin.internal;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Conditions;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.impl.include.FileIncludeManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.M68kDataSized;
import com.yanncebron.m68kplugin.lang.psi.M68kInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class M68kProjectStatisticsAction extends AnAction {

  private static final Condition<M68kPsiElement> NO_DATA_SIZE_CONDITION = m68kPsiElement -> {
    if (m68kPsiElement instanceof M68kDataSized) {
      return ((M68kDataSized) m68kPsiElement).getDataSize() == null;
    }
    return false;
  };

  @Override
  public void update(@NotNull AnActionEvent e) {
    e.getPresentation().setEnabledAndVisible(e.getProject() != null);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    final Project project = e.getRequiredData(CommonDataKeys.PROJECT);

    Map<Class<? extends M68kPsiElement>, Integer> instructions = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> withoutDataSize = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> labels = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> directives = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> conditional = createMap();

    List<String> fileInfos = new ArrayList<>();
    final int[] totalErrors = {0};
    final int[] totalResolve = {0};
    final int[] totalResolveErrors = {0};
    final long[] totalResolveTime = {0};

    long startTime = System.currentTimeMillis();
    ProgressManager.getInstance().runProcessWithProgressSynchronously(() ->
      ApplicationManager.getApplication().runReadAction(() -> {
        final Collection<VirtualFile> files =
          FileTypeIndex.getFiles(M68kFileType.INSTANCE, GlobalSearchScope.allScope(project));

        final FileIncludeManager fileIncludeManager = FileIncludeManager.getManager(project);

        final ProgressIndicator pi = ProgressManager.getInstance().getProgressIndicator();
        pi.setIndeterminate(false);
        int processed = 0;
        for (VirtualFile virtualFile : files) {
          pi.setText(virtualFile.getPath());
          pi.setFraction((double) processed++ / files.size());

          final PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
          if (!(psiFile instanceof M68kFile)) return;

          final M68kFile m68kPsiFile = (M68kFile) psiFile;

          pi.setText2("Errors");
          final PsiErrorElement[] errors = m68kPsiFile.findChildrenByClass(PsiErrorElement.class);
          totalErrors[0] = totalErrors[0] + errors.length;

          pi.setText2("Includes");
          final VirtualFile[] directInclude = fileIncludeManager.getIncludedFiles(virtualFile, true);
          final VirtualFile[] recursiveInclude = fileIncludeManager.getIncludedFiles(virtualFile, true, true);
          final VirtualFile[] incbinInclude = fileIncludeManager.getIncludedFiles(virtualFile, false);

          pi.setText2("Resolves");
          int labelRefs = 0;
          int labelRefsUnresolved = 0;
          int macroCalls = 0;
          int macroCallsUnresolved = 0;
          long resolveStart = System.currentTimeMillis();
          for (M68kPsiElement element : m68kPsiFile.findChildrenByClass(M68kPsiElement.class)) {
            if (element instanceof M68kMacroCallDirective) {
              final PsiPolyVariantReference reference = ObjectUtils.tryCast(element.getReference(), PsiPolyVariantReference.class);
              assert reference != null;
              if (reference.multiResolve(false).length == 0) {
                macroCallsUnresolved++;
              }
              macroCalls++;
            }

            for (M68kPsiElement m68kPsiElement : PsiTreeUtil.getChildrenOfTypeAsList(element, M68kPsiElement.class)) {
              if (!(m68kPsiElement instanceof M68kLabelRefExpression)) continue;

              try {
                final PsiPolyVariantReference reference = ObjectUtils.tryCast(m68kPsiElement.getReference(), PsiPolyVariantReference.class);
                assert reference != null;
                if (reference.multiResolve(false).length == 0) {
                  labelRefsUnresolved++;
                }
                labelRefs++;
              } catch (AssertionError ignored) {
              }
            }
          }
          totalResolveTime[0] = totalResolveTime[0] + (System.currentTimeMillis() - resolveStart);
          totalResolve[0] = totalResolve[0] + macroCalls + labelRefs;
          totalResolveErrors[0] = totalResolveErrors[0] + macroCallsUnresolved + labelRefsUnresolved;

          String info = StringUtils.rightPad(virtualFile.getName(), 30) +
            StringUtils.leftPad(String.valueOf(errors.length), 8) +
            " | " + StringUtils.rightPad(labelRefsUnresolved + "/" + labelRefs, 7) +
            " | " + StringUtils.rightPad(macroCallsUnresolved + "/" + macroCalls, 7) +
            " | " + directInclude.length + " (" + (recursiveInclude.length - 1) + ") [" + incbinInclude.length + "]";
          fileInfos.add(info);

          pi.setText2("Instruction count");
          M68kInstruction[] computeInstructions = m68kPsiFile.findChildrenByClass(M68kInstruction.class);
          countByClass(instructions, computeInstructions);
          countByClass(withoutDataSize, computeInstructions, NO_DATA_SIZE_CONDITION);

          M68kLabelBase[] computeLabels = m68kPsiFile.findChildrenByClass(M68kLabelBase.class);
          countByClass(labels, computeLabels);

          final M68kDirective[] computeDirectives = m68kPsiFile.findChildrenByClass(M68kDirective.class);
          countByClass(directives, computeDirectives);
          countByClass(withoutDataSize, computeDirectives, NO_DATA_SIZE_CONDITION);

          countByClass(conditional, m68kPsiFile.findChildrenByClass(M68kConditionalAssemblyDirective.class));
        }
      }), "Scanning files...", true, project);

    StringBuilder sb = new StringBuilder();
    sb.append("Files: ").append(fileInfos.size())
      .append(" (").append(StringUtil.formatDuration(System.currentTimeMillis() - startTime)).append(")")
      .append("\n")
      .append("Total Errors: ").append(totalErrors[0])
      .append("\n")
      .append("Total Resolve Errors: ").append(totalResolveErrors[0]).append("/").append(totalResolve[0])
      .append(" (").append(StringUtil.formatDuration(totalResolveTime[0])).append(")")
      .append("\n\n");
    Collections.sort(fileInfos);
    sb.append("File                            Errors | Label   | Macro   | include (recursive) [incbin]\n");
    sb.append("=========================================================================================\n");
    sb.append(StringUtil.join(fileInfos, "\n"));

    appendClasses(labels, sb, "Labels");
    appendClasses(instructions, sb, "Instructions");
    appendClasses(directives, sb, "Directives");
    appendClasses(withoutDataSize, sb, "Instructions/Directives w/o specified DataSize");
    appendClasses(conditional, sb, "Conditional Assembly Directives");

    VirtualFile file = new LightVirtualFile("M68k Project Statistics.txt", sb.toString());
    OpenFileDescriptor descriptor = new OpenFileDescriptor(project, file);
    FileEditorManager.getInstance(project).openEditor(descriptor, true);
  }

  @NotNull
  private static Map<Class<? extends M68kPsiElement>, Integer> createMap() {
    return new TreeMap<>((o1, o2) -> Comparing.compare(o1.getSimpleName(), o2.getSimpleName()));
  }

  private void appendClasses(Map<Class<? extends M68kPsiElement>, Integer> instructions, StringBuilder sb, String title) {
    int total = instructions.values().stream().mapToInt(value -> value).sum();
    sb.append("\n");
    sb.append(StringUtil.repeatSymbol('-', 80));
    sb.append("\n#").append(total).append(" --- ").append(title).append("\n\n");

    for (Map.Entry<Class<? extends M68kPsiElement>, Integer> entry : instructions.entrySet()) {
      final String fqn = entry.getKey().getSimpleName();
      final String className = fqn.substring(4, fqn.length() - 4);
      sb.append(StringUtils.rightPad(className, 40))
        .append(StringUtils.leftPad(String.valueOf(entry.getValue()), 8)).append("\n");
    }
  }

  private void countByClass(Map<Class<? extends M68kPsiElement>, Integer> map, M68kPsiElement[] compute) {
    countByClass(map, compute, Conditions.alwaysTrue());
  }

  private void countByClass(Map<Class<? extends M68kPsiElement>, Integer> map, M68kPsiElement[] compute, Condition<M68kPsiElement> filterCondition) {
    for (M68kPsiElement byClass : compute) {
      if (!filterCondition.value(byClass)) continue;
      Integer count = ContainerUtil.getOrCreate(map, byClass.getClass(), 0);
      map.replace(byClass.getClass(), count + 1);
    }
  }

}
