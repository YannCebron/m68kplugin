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

package com.yanncebron.m68kplugin.lang.findUsages;

import com.intellij.lang.HelpID;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.ElementDescriptionUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.usageView.UsageViewLongNameLocation;
import com.intellij.usageView.UsageViewShortNameLocation;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lexer.M68kLexer;
import org.jetbrains.annotations.NotNull;

final class M68kFindUsagesProvider implements FindUsagesProvider {

  @Override
  public WordsScanner getWordsScanner() {
    return new DefaultWordsScanner(new M68kLexer(),
      TokenSet.create(M68kTokenTypes.ID),
      TokenSet.create(M68kTokenTypes.COMMENT),
      TokenSet.create(M68kTokenTypes.STRING)
    );
  }

  @Override
  public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
    return psiElement instanceof M68kLabelBase;
  }

  @Override
  public String getHelpId(@NotNull PsiElement psiElement) {
    return HelpID.FIND_OTHER_USAGES;
  }

  @NotNull
  @Override
  public String getType(@NotNull PsiElement element) {
    if (element instanceof M68kLabel label) {
      if (label.getLabelKind() == M68kLabelBase.LabelKind.MACRO) {
        return M68kBundle.message("term.macro");
      }
      return M68kBundle.message("term.label");
    }

    if (element instanceof M68kLocalLabel) {
      return M68kBundle.message("term.local.label");
    }

    return "";
  }

  @NotNull
  @Override
  public String getDescriptiveName(@NotNull PsiElement element) {
    return ElementDescriptionUtil.getElementDescription(element, UsageViewLongNameLocation.INSTANCE);
  }

  @NotNull
  @Override
  public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
    return ElementDescriptionUtil.getElementDescription(element, UsageViewShortNameLocation.INSTANCE);
  }
}
