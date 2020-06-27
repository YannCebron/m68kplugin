/*
 * Copyright 2020 The Authors
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
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.include.FileIncludeManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.TokenSet;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.M68kInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class M68kProjectStatisticsAction extends AnAction {

  @Override
  public void update(@NotNull AnActionEvent e) {
    e.getPresentation().setEnabledAndVisible(e.getProject() != null);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    final Project project = e.getRequiredData(CommonDataKeys.PROJECT);

    Map<Class<? extends M68kPsiElement>, Integer> instructions = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> labels = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> directives = createMap();
    Map<Class<? extends M68kPsiElement>, Integer> conditional = createMap();

    List<String> fileInfos = new ArrayList<>();

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

          final PsiErrorElement[] errors = m68kPsiFile.findChildrenByClass(PsiErrorElement.class);

          final VirtualFile[] directInclude = fileIncludeManager.getIncludedFiles(virtualFile, true);
          final VirtualFile[] recursiveInclude = fileIncludeManager.getIncludedFiles(virtualFile, true, true);
          final VirtualFile[] incbinInclude = fileIncludeManager.getIncludedFiles(virtualFile, false);

          String info = StringUtils.rightPad(virtualFile.getName(), 20) +
            StringUtils.leftPad(String.valueOf(errors.length), 8) + " | "
            + directInclude.length + " (" + (recursiveInclude.length - 1) + ") [" + incbinInclude.length + "]";
          fileInfos.add(info);


          M68kInstruction[] computeInstructions = m68kPsiFile.findChildrenByClass(M68kInstruction.class);
          countByClass(instructions, computeInstructions);

          M68kLabelBase[] computeLabels = m68kPsiFile.findChildrenByClass(M68kLabelBase.class);
          countByClass(labels, computeLabels);

          @NotNull M68kPsiElement[] all = m68kPsiFile.findChildrenByClass(M68kPsiElement.class);
          countByClass(directives, getByTokenGroup(all, M68kTokenGroups.DIRECTIVES));
          countByClass(conditional, getByTokenGroup(all, M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES));
        }
      }), "Scanning Files...", true, project);

    StringBuilder sb = new StringBuilder();
    sb.append("Total files: ").append(fileInfos.size()).append("\n\n");
    Collections.sort(fileInfos);
    sb.append("File                  Errors | include (recursive) [incbin]\n");
    sb.append(StringUtil.join(fileInfos, "\n"));

    appendClasses(labels, sb);
    appendClasses(instructions, sb);
    appendClasses(directives, sb);
    appendClasses(conditional, sb);

    VirtualFile file = new LightVirtualFile("M68k Project Statistics.txt", sb.toString());
    OpenFileDescriptor descriptor = new OpenFileDescriptor(project, file);
    FileEditorManager.getInstance(project).openEditor(descriptor, true);
  }

  @NotNull
  private static Map<Class<? extends M68kPsiElement>, Integer> createMap() {
    return new TreeMap<>((o1, o2) -> Comparing.compare(o1.getSimpleName(), o2.getSimpleName()));
  }

  private static M68kPsiElement[] getByTokenGroup(@NotNull M68kPsiElement[] all, TokenSet tokenSet) {
    return ContainerUtil.filter(all, psiElement -> psiElement.getNode().findChildByType(tokenSet) != null)
      .toArray(new M68kPsiElement[0]);
  }

  private void appendClasses(Map<Class<? extends M68kPsiElement>, Integer> instructions, StringBuilder sb) {
    int total = instructions.values().stream().mapToInt(value -> value).sum();
    sb.append("\n");
    sb.append(StringUtil.repeatSymbol('-', 80));
    sb.append("\n#").append(total).append("\n");

    for (Map.Entry<Class<? extends M68kPsiElement>, Integer> entry : instructions.entrySet()) {
      final String fqn = entry.getKey().getSimpleName();
      final String className = fqn.substring(4, fqn.length() - 4);
      sb.append(StringUtils.rightPad(className, 20))
        .append(StringUtils.leftPad(String.valueOf(entry.getValue()), 8)).append("\n");
    }
  }

  private void countByClass(Map<Class<? extends M68kPsiElement>, Integer> instructions, M68kPsiElement[] compute) {
    for (M68kPsiElement byClass : compute) {
      Integer count = ContainerUtil.getOrCreate(instructions, byClass.getClass(), 0);
      instructions.replace(byClass.getClass(), count + 1);
    }
  }
}