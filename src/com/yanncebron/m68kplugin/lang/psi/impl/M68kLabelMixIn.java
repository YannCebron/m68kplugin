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
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import com.yanncebron.m68kplugin.lang.stubs.M68kLabelStub;
import icons.M68kIcons;
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
  public @NotNull PsiElement getNameIdentifier() {
    return notNullChild(findChildByType(M68kTokenTypes.ID));
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
    if (parent instanceof M68kRegDirective) {
      return LabelKind.REG;
    }
    if (parent instanceof M68kFoDirective) {
      return LabelKind.FO;
    }
    if (parent instanceof M68kSoDirective) {
      return LabelKind.SO;
    }

    throw new IllegalArgumentException("cannot determine labelKind for '" + getName() + "' " + this);
  }

  @Override
  public @Nullable String getValue() {
    final M68kLabelStub stub = getGreenStub();
    if (stub != null) {
      return stub.getValue();
    }

    final LabelKind labelKind = getLabelKind();
    if (!labelKind.hasValue()) return null;

    switch (labelKind) {
      case EQUALS:
      case EQU:
      case SET:
        final PsiElement parent = getParent();
        assert parent instanceof M68kEquDirectiveBase;
        final M68kExpression expression = ((M68kEquDirectiveBase) parent).getExpression();
        return expression != null ? expression.getText() : null;
      case EQUR:
        final PsiElement equrParent = getParent();
        assert equrParent instanceof M68kEqurDirective;
        final M68kAdmRrd register = ((M68kEqurDirective) equrParent).getAdmRrd();
        return register != null ? register.getText() : null;
      case REG:
        final PsiElement regParent = getParent();
        assert regParent instanceof M68kRegDirective;
        final M68kAdmRegisterList registerList = ((M68kRegDirective) regParent).getAdmRegisterList();
        return registerList != null ? registerList.getText() : null;
      case FO:
        final PsiElement foParent = getParent();
        assert foParent instanceof M68kFoDirective;
        final M68kExpression foExpression = ((M68kFoDirective) foParent).getExpression();
        return foExpression != null ? foExpression.getText() : null;
      case SO:
        final PsiElement soParent = getParent();
        assert soParent instanceof M68kSoDirective;
        final M68kExpression soExpression = ((M68kSoDirective) soParent).getExpression();
        return soExpression != null ? soExpression.getText() : null;
      default:
        throw new IllegalArgumentException("cannot determine value for '" + labelKind + "' " + this);
    }
  }

  @Override
  protected @Nullable Icon getElementIcon(int flags) {
    final LabelKind labelKind = getLabelKind();
    return switch (labelKind) {
      case GLOBAL -> M68kIcons.LABEL_GLOBAL;
      case MACRO -> M68kIcons.LABEL_MACRO;
      case EQU, EQUALS -> M68kIcons.LABEL_EQU;
      case SET -> M68kIcons.LABEL_SET;
      case EQUR -> M68kIcons.LABEL_EQUR;
      case REG -> M68kIcons.LABEL_REG;
      case FO -> M68kIcons.LABEL_FO;
      case SO -> M68kIcons.LABEL_SO;
      default ->
        throw new IllegalArgumentException("unknown labelKind " + labelKind + "for '" + getName() + "' " + this);
    };
  }

  @Override
  public ItemPresentation getPresentation() {
    return new PresentationData(getName(), SymbolPresentationUtil.getFilePathPresentation(getContainingFile()), getIcon(0), null);
  }

  @Nullable
  @Override
  public String getName() {
    final M68kLabelStub stub = getGreenStub();
    if (stub != null) {
      return stub.getName();
    }

    return getNameIdentifier().getText();
  }

  @Override
  public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
    final M68kLabel newLabel = M68kElementFactory.createLabel(getProject(), name);
    getNameIdentifier().replace(newLabel.getNameIdentifier());
    return this;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(LABEL)";
  }
}
