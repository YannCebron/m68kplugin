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
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIfcConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIfncConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kBinaryExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import org.jetbrains.annotations.NotNull;

final class M68kMoveLeftRightHandler extends MoveElementLeftRightHandler {

  @NotNull
  @Override
  public PsiElement @NotNull [] getMovableSubElements(@NotNull PsiElement element) {
    if (element instanceof M68kDcDirective) {
      return ((M68kDcDirective) element).getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kDrDirective) {
      return ((M68kDrDirective) element).getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kPrintvDirective) {
      return ((M68kPrintvDirective) element).getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kXdefDirective) {
      return ((M68kXdefDirective) element).getLabelRefExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kXrefDirective) {
      return ((M68kXrefDirective) element).getLabelRefExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }

    if (element instanceof M68kIfcConditionalAssemblyDirective directive) {
      M68kExpression arg1 = directive.getArg1();
      M68kExpression arg2 = directive.getArg2();
      if (arg1 != null && arg2 != null) {
        return new PsiElement[]{arg1, arg2};
      }
    }
    if (element instanceof M68kIfncConditionalAssemblyDirective directive) {
      M68kExpression arg1 = directive.getArg1();
      M68kExpression arg2 = directive.getArg2();
      if (arg1 != null && arg2 != null) {
        return new PsiElement[]{arg1, arg2};
      }
    }

    if (element instanceof M68kBinaryExpression binaryExpression) {
      final M68kExpression right = binaryExpression.getRight();
      if (right != null) return new PsiElement[]{binaryExpression.getLeft(), right};
    }

    if (element instanceof M68kAdmRegisterList registerList) {
      return registerList.getRegisterRangeList().toArray(PsiElement.EMPTY_ARRAY);
    }

    if (element instanceof M68kExgInstruction instruction) {
      final M68kAdmRrd source = instruction.getSource();
      final M68kAdmRrd destination = instruction.getDestination();
      if (source != null && destination != null) {
        return new PsiElement[]{source, destination};
      }
    }

    if (element instanceof M68kCmpmInstruction instruction) {
      M68kAdmApi source = instruction.getSource();
      M68kAdmApi destination = instruction.getDestination();
      if (source != null && destination != null) {
        return new PsiElement[]{source, destination};
      }
    }

    if (element instanceof M68kMacroCallDirective) {
      return ((M68kMacroCallDirective) element).getMacroCallParameterList().toArray(PsiElement.EMPTY_ARRAY);
    }

    return PsiElement.EMPTY_ARRAY;
  }
}
