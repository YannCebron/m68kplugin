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

package com.yanncebron.m68kplugin.editor;

import com.intellij.codeInsight.editorActions.moveLeftRight.MoveElementLeftRightHandler;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmRegisterList;
import com.yanncebron.m68kplugin.lang.psi.M68kCmpmInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kExgInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIfcConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIfncConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kBinaryExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kMoveLeftRightHandler extends MoveElementLeftRightHandler {

  @NotNull
  @Override
  public PsiElement @NotNull [] getMovableSubElements(@NotNull PsiElement element) {
    if (element instanceof M68kDcDirective directive) {
      return directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kDrDirective directive) {
      return directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kPrintvDirective directive) {
      return directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kXdefDirective directive) {
      return directive.getLabelRefExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kXrefDirective directive) {
      return directive.getLabelRefExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }

    if (element instanceof M68kIfcConditionalAssemblyDirective directive) {
      return getElements(directive.getArg1(), directive.getArg2());
    }
    if (element instanceof M68kIfncConditionalAssemblyDirective directive) {
      return getElements(directive.getArg1(), directive.getArg2());
    }

    if (element instanceof M68kBinaryExpression instruction) {
      return getElements(instruction.getLeft(), instruction.getRight());
    }

    if (element instanceof M68kAdmRegisterList registerList) {
      return registerList.getRegisterRangeList().toArray(PsiElement.EMPTY_ARRAY);
    }

    if (element instanceof M68kExgInstruction instruction) {
      return getElements(instruction.getSource(), instruction.getDestination());
    }

    if (element instanceof M68kCmpmInstruction instruction) {
      return getElements(instruction.getSource(), instruction.getDestination());
    }

    if (element instanceof M68kMacroCallDirective directive) {
      return directive.getMacroCallParameterList().toArray(PsiElement.EMPTY_ARRAY);
    }

    return PsiElement.EMPTY_ARRAY;
  }

  private static PsiElement @NotNull [] getElements(@Nullable M68kPsiElement first, @Nullable M68kPsiElement second) {
    if (first != null && second != null) {
      return new PsiElement[]{first, second};
    }

    return PsiElement.EMPTY_ARRAY;
  }
}
