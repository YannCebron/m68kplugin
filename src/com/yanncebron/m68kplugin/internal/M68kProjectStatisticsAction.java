/*
 * Copyright 2025 The Authors
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

import com.intellij.analysis.AnalysisScope;
import com.intellij.analysis.BaseAnalysisAction;
import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.codeInsight.highlighting.HighlightErrorFilter;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.UniqueVFilePathBuilder;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.Conditions;
import com.intellij.openapi.util.text.NaturalComparator;
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
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

final class M68kProjectStatisticsAction extends BaseAnalysisAction {

  private static final Condition<M68kPsiElement> NO_DATA_SIZE_CONDITION = m68kPsiElement -> {
    if (m68kPsiElement instanceof M68kDataSized m68kDataSized) {
      return m68kDataSized.getDataSize() == null;
    }
    return false;
  };

  M68kProjectStatisticsAction() {
    super(M68kBundle.messagePointer("action.M68kProjectStatisticsAction.text"), CodeInsightBundle.messagePointer("action.analysis.noun"));
  }

  @Override
  protected void analyze(@NotNull Project project, @NotNull AnalysisScope scope) {

    Map<Class<? extends M68kPsiElement>, Integer> instructions = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> withoutDataSize = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> labels = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> directives = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> conditional = createMap();

    List<String> fileInfos = new ArrayList<>();
    final int[] totalElements = {0};
    final int[] totalErrors = {0};
    final int[] totalResolve = {0};
    final int[] totalResolveErrors = {0};
    final long[] totalResolveTime = {0};

    long startTime = System.currentTimeMillis();
    boolean completed = ProgressManager.getInstance().runProcessWithProgressSynchronously(() ->
      ApplicationManager.getApplication().runReadAction(() -> {
        GlobalSearchScope searchScope = (GlobalSearchScope) scope.toSearchScope();
        final Collection<VirtualFile> files =
          FileTypeIndex.getFiles(M68kFileType.INSTANCE, searchScope);

        final FileIncludeManager fileIncludeManager = FileIncludeManager.getManager(project);
        List<HighlightErrorFilter> highlightErrorFilters = HighlightErrorFilter.EP_NAME.getExtensions(project);

        final ProgressIndicator pi = ProgressManager.getInstance().getProgressIndicator();
        pi.setIndeterminate(false);
        int processed = 0;
        for (VirtualFile virtualFile : files) {
          pi.setText(virtualFile.getPath());
          pi.setFraction((double) processed++ / files.size());

          final PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
          if (!(psiFile instanceof M68kFile m68kPsiFile)) return;

          pi.setText2("Errors");
          final PsiErrorElement[] errors = m68kPsiFile.findChildrenByClass(PsiErrorElement.class);
          int filteredErrorCount = errors.length;
          // filter M68kMacroParameterHighlightErrorFilter
          for (PsiErrorElement error : errors) {
            for (HighlightErrorFilter filter : highlightErrorFilters) {
              if (!filter.shouldHighlightErrorElement(error)) {
                filteredErrorCount--;
                break;
              }
            }
          }
          totalErrors[0] += filteredErrorCount;

          pi.setText2("Includes");
          final VirtualFile[] directInclude = fileIncludeManager.getIncludedFiles(virtualFile, true);
          final VirtualFile[] recursiveInclude = fileIncludeManager.getIncludedFiles(virtualFile, true, true);
          final VirtualFile[] incbinInclude = fileIncludeManager.getIncludedFiles(virtualFile, false);

          pi.setText2("Label resolving");
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
          totalResolveTime[0] += System.currentTimeMillis() - resolveStart;
          totalResolve[0] += macroCalls + labelRefs;
          totalResolveErrors[0] += macroCallsUnresolved + labelRefsUnresolved;

          pi.setText2("Instruction count");
          M68kInstruction[] computeInstructions = m68kPsiFile.findChildrenByClass(M68kInstruction.class);

          // try to find Mnemonic for instruction
          // - this may fail due (known) to lexer/parser issues, manual inspection is needed
          // - alternatively, the parser allows variants the registry doesn't know about :-/
          for (M68kInstruction instruction : computeInstructions) {
            try {
              M68kMnemonicRegistry.getInstance().find(instruction);
            } catch (Throwable e) {
              System.out.println(virtualFile.getPresentableUrl() + ": no M68kMnemonic, could be syntax/parser issue '" + instruction.getText() + "' @" + instruction.getTextOffset());
            }
          }

          countByClass(instructions, computeInstructions);
          countByClass(withoutDataSize, computeInstructions, NO_DATA_SIZE_CONDITION);

          pi.setText2("Label count");
          M68kLabelBase[] computeLabels = m68kPsiFile.findChildrenByClass(M68kLabelBase.class);
          countByClass(labels, computeLabels);

          pi.setText2("Directive count");
          final M68kDirective[] computeDirectives = m68kPsiFile.findChildrenByClass(M68kDirective.class);
          countByClass(directives, computeDirectives);
          countByClass(withoutDataSize, computeDirectives, NO_DATA_SIZE_CONDITION);

          pi.setText2("Conditional assembly directive count");
          final M68kConditionalAssemblyDirective[] computeConditional = m68kPsiFile.findChildrenByClass(M68kConditionalAssemblyDirective.class);
          countByClass(conditional, computeConditional);

          int elements = computeInstructions.length + computeLabels.length + computeDirectives.length + computeConditional.length;
          totalElements[0] += elements;

          String info =
            StringUtils.rightPad(UniqueVFilePathBuilder.getInstance().getUniqueVirtualFilePath(project, virtualFile, searchScope), 34) +
              " | " + StringUtils.rightPad(getErrorCountText(filteredErrorCount, elements), 7) +
              " | " + StringUtils.rightPad(getErrorCountText(labelRefsUnresolved, labelRefs), 7) +
              " | " + StringUtils.rightPad(getErrorCountText(macroCallsUnresolved, macroCalls), 7) +
              " | " + directInclude.length + " (" + (recursiveInclude.length - 1) + ") [" + incbinInclude.length + "]";
          fileInfos.add(info);
        }
      }), M68kBundle.message("action.M68kProjectStatisticsAction.progress.title"), true, project);

    if (!completed) return;

    StringBuilder sb = new StringBuilder();
    sb.append(scope.getDisplayName())
      .append("\n")
      .append("Files: ").append(fileInfos.size())
      .append(" (").append(StringUtil.formatDuration(System.currentTimeMillis() - startTime)).append(")")
      .append("\n")
      .append("Total Errors: ").append(getErrorCountText(totalErrors[0], totalElements[0]))
      .append("\n")
      .append("Total Resolve Errors: ").append(getErrorCountText(totalResolveErrors[0], totalResolve[0]))
      .append(" (").append(StringUtil.formatDuration(totalResolveTime[0])).append(")")
      .append("\n\n");

    sb.append("File                               | Errors  | Label   | Macro   | include (recursive) [incbin]\n");
    sb.append("===============================================================================================\n");
    fileInfos.sort(NaturalComparator.INSTANCE);
    sb.append(StringUtil.join(fileInfos, "\n"));

    appendClasses(labels, sb, "Labels");
    appendClasses(instructions, sb, "Instructions");
    appendClasses(directives, sb, "Directives");
    appendClasses(withoutDataSize, sb, "Instructions|Directives w/o specified DataSize");
    appendClasses(conditional, sb, "Conditional Assembly Directives");

    VirtualFile file = new LightVirtualFile("M68k Project Statistics.txt", sb.toString());
    FileEditorManager.getInstance(project).openFile(file, true);
  }

  private static String getErrorCountText(int errorCount, int total) {
    return errorCount == 0 ? Integer.toString(total) : errorCount + "/" + total;
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
      Integer count = map.computeIfAbsent(byClass.getClass(), __ -> 0);
      map.replace(byClass.getClass(), count + 1);
    }
  }

}
