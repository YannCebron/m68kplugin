/*
 * Copyright 2022 The Authors
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

package com.yanncebron.m68kplugin.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.search.LocalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import com.yanncebron.m68kplugin.lang.psi.*;
import icons.M68kIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

abstract class M68kLocalLabelMixIn extends ASTWrapperPsiElement implements M68kLabelBase {

  protected M68kLocalLabelMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public LabelKind getLabelKind() {
    return LabelKind.LOCAL;
  }

  @Override
  public @NotNull PsiElement getNameIdentifier() {
    return notNullChild(findChildByType(M68kTokenTypes.ID));
  }

  @Override
  public @Nullable String getValue() {
    return null;
  }

  @Override
  public int getTextOffset() {
    if (M68kLocalLabelMode.DOT.matches(getText())) {
      return getNode().getStartOffset() + 1;
    }
    return super.getTextOffset();
  }

  @Override
  protected @Nullable Icon getElementIcon(int flags) {
    return M68kIcons.LABEL_LOCAL;
  }

  @NotNull
  @Override
  public SearchScope getUseScope() {
    return new LocalSearchScope(getContainingFile());   // todo optimize more?
  }

  @Override
  public ItemPresentation getPresentation() {
    return new PresentationData(getName(), SymbolPresentationUtil.getFilePathPresentation(getContainingFile()), getIcon(0), null);
  }

  @Nullable
  @Override
  public String getName() {
    return getNameIdentifier().getText();
  }

  @Override
  public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
    final M68kLabel newLabel = M68kElementFactory.createLabel(getProject(), name);
    getNameIdentifier().replace(newLabel.getNameIdentifier());
    return this;
  }

}
