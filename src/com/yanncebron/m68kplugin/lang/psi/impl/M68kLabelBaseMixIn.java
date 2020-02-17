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

package com.yanncebron.m68kplugin.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.icons.AllIcons;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.util.IncorrectOperationException;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

// todo eigenes interface?
public abstract class M68kLabelBaseMixIn extends ASTWrapperPsiElement implements PsiNamedElement {

  protected M68kLabelBaseMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Nullable
  @Override
  public Icon getIcon(int flags) {
    return AllIcons.Nodes.Method;
  }

  @Override
  public ItemPresentation getPresentation() {
    return new ItemPresentation() {
      @Nullable
      @Override
      public String getPresentableText() {
        return getName();
      }

      @Nullable
      @Override
      public String getLocationString() {
        return SymbolPresentationUtil.getFilePathPresentation(M68kLabelBaseMixIn.this.getContainingFile());
      }

      @Nullable
      @Override
      public Icon getIcon(boolean unused) {
        return M68kLabelBaseMixIn.this.getIcon(0);
      }
    };
  }

  @Nullable
  @Override
  public String getName() {
    final ASTNode idNode = findIdNode(this);
    return idNode.getText();
  }

  @Override
  public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
    final M68kLabel newLabel = M68kElementFactory.createLabel(getProject(), name);
    getNode().replaceChild(findIdNode(this), findIdNode(newLabel));
    return this;
  }

  @NotNull
  private static ASTNode findIdNode(PsiElement labelBase) {
    final ASTNode idNode = labelBase.getNode().findChildByType(M68kTokenTypes.ID);
    assert idNode != null;
    return idNode;
  }

}