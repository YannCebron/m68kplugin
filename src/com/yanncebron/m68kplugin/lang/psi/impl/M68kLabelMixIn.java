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

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.util.IncorrectOperationException;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kIcons;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.stubs.M68kLabelStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

abstract class M68kLabelMixIn extends StubBasedPsiElementBase<M68kLabelStub> implements M68kLabelBase {

  protected M68kLabelMixIn(@NotNull ASTNode node) {
    super(node);
  }

  protected M68kLabelMixIn(@NotNull M68kLabelStub stub, @NotNull IStubElementType nodeType) {
    super(stub, nodeType);
  }

  @Override
  public LabelKind getLabelKind() {
    final M68kLabelStub stub = getGreenStub();
    if (stub != null) {
      return stub.getLabelKind();
    }

    PsiElement parent = getParent();
    if (parent instanceof M68kFile) {
      return LabelKind.GLOBAL;
    }
    if (parent instanceof M68kMacroDirective) {
      return LabelKind.MACRO;
    }
    if (parent instanceof M68kEquDirective) {
      return LabelKind.EQU;
    }
    if (parent instanceof M68kEqualsDirective) {
      return LabelKind.EQUALS;
    }
    if (parent instanceof M68kSetDirective) {
      return LabelKind.SET;
    }
    if (parent instanceof M68kEqurDirective) {
      return LabelKind.EQUR;
    }

    throw new IllegalArgumentException("cannot determine labelKind for '" + getName() + "' " + this);
  }

  @Override
  protected @Nullable Icon getElementIcon(int flags) {
    final LabelKind labelKind = getLabelKind();
    switch (labelKind) {
      case GLOBAL:
        return M68kIcons.LABEL_GLOBAL;
      case MACRO:
        return M68kIcons.LABEL_MACRO;
      case EQU:
      case EQUALS:
        return M68kIcons.LABEL_EQU;
      case SET:
        return M68kIcons.LABEL_SET;
      case EQUR:
        return M68kIcons.LABEL_EQUR;
      default:
        throw new IllegalArgumentException("unknown labelKind " + labelKind);
    }
  }

  @Override
  public ItemPresentation getPresentation() {
    return new PresentationData(getName(), SymbolPresentationUtil.getFilePathPresentation(getContainingFile()),
      getIcon(0), null);
  }

  @Nullable
  @Override
  public String getName() {
    final M68kLabelStub stub = getGreenStub();
    if (stub != null) {
      return stub.getName();
    }

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

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(LABEL)";
  }
}
