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

package com.yanncebron.m68kplugin.editor.navigation;

import com.intellij.ide.navigationToolbar.AbstractNavBarModelExtension;
import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.CommonProcessors;
import com.intellij.util.ObjectUtils;
import com.intellij.util.Processor;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirectiveWithLabel;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kEndmDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kNavBarModelExtension extends AbstractNavBarModelExtension {

  @Override
  public PsiElement getLeafElement(@NotNull DataContext dataContext) {
    if (!UISettings.getInstance().getShowMembersInNavigationBar()) {
      return null;
    }

    PsiFile psiFile = dataContext.getData(CommonDataKeys.PSI_FILE);
    Editor editor = dataContext.getData(CommonDataKeys.EDITOR);
    if (psiFile == null || !psiFile.isValid() || editor == null) return null;

    PsiElement psiElement = psiFile.findElementAt(editor.getCaretModel().getOffset());
    if (isNotRelevant(psiElement)) return null;

    // 1. directly on label
    M68kLabelBase labelBase = PsiTreeUtil.getParentOfType(psiElement, M68kLabelBase.class);
    if (labelBase != null) return labelBase;

    // 2. directive with label
    M68kDirectiveWithLabel m68kDirectiveWithLabel = PsiTreeUtil.getParentOfType(psiElement, M68kDirectiveWithLabel.class);
    if (m68kDirectiveWithLabel != null) return m68kDirectiveWithLabel.getLabel();

    // 3. anywhere else: search backwards to next label/macro
    Ref<M68kLabelBase> foundLabel = Ref.create();
    Processor<M68kPsiElement> processor = m68kPsiElement -> {
      if (m68kPsiElement instanceof M68kLabelBase m68kLabelBase) {
        foundLabel.set(m68kLabelBase);
        return false;
      }

      if (m68kPsiElement instanceof M68kMacroDirective m68kMacroDirective) {
        foundLabel.set(m68kMacroDirective.getLabel());
        return false;
      }

      return true;
    };

    M68kPsiElement containing = M68kPsiTreeUtil.getContainingInstructionOrDirective(psiElement);
    M68kPsiTreeUtil.processSiblingsBackwards(ObjectUtils.chooseNotNull(containing, psiElement), processor,
      M68kEndmDirective.class);
    return foundLabel.get();
  }

  private static boolean isNotRelevant(@Nullable PsiElement element) {
    return element == null || element.getLanguage() != M68kLanguage.INSTANCE;
  }

  @Override
  public @Nullable PsiElement getParent(PsiElement psiElement) {
    if (isNotRelevant(psiElement)) return null;

    if (psiElement instanceof M68kLocalLabel m68kLocalLabel) {
      return m68kLocalLabel.getContainingLabel();
    }

    // find containing macro
    if (psiElement instanceof M68kLabel) {
      CommonProcessors.FindFirstProcessor<M68kPsiElement> processor = new CommonProcessors.FindFirstProcessor<>() {
        @Override
        protected boolean accept(M68kPsiElement m68kPsiElement) {
          return m68kPsiElement instanceof M68kMacroDirective;
        }
      };
      M68kPsiTreeUtil.processSiblingsBackwards(psiElement, processor, M68kEndmDirective.class);
      if (processor.getFoundValue() instanceof M68kMacroDirective m68kMacroDirective) {
        return m68kMacroDirective.getLabel();
      }
    }

    return psiElement.getContainingFile();
  }

  @Override
  public @Nullable String getPresentableText(Object object) {
    if (object instanceof M68kLabelBase labelBase) {
      return labelBase.getName();
    }

    return null;
  }

}
