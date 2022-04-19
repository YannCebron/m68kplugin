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

package com.yanncebron.m68kplugin.editor;

import com.intellij.codeInsight.editorActions.moveLeftRight.MoveElementLeftRightHandler;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmRegisterList;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmRrd;
import com.yanncebron.m68kplugin.lang.psi.M68kExgInstruction;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDrDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kPrintvDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kBinaryExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import org.jetbrains.annotations.NotNull;

public class M68kMoveLeftRightHandler extends MoveElementLeftRightHandler {

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

    if (element instanceof M68kBinaryExpression) {
      M68kBinaryExpression binaryExpression = ((M68kBinaryExpression) element);
      final M68kExpression right = binaryExpression.getRight();
      if (right != null) return new PsiElement[]{binaryExpression.getLeft(), right};
    }

    if (element instanceof M68kAdmRegisterList) {
      M68kAdmRegisterList registerList = (M68kAdmRegisterList) element;
      return registerList.getRegisterRangeList().toArray(PsiElement.EMPTY_ARRAY);
    }

    if (element instanceof M68kExgInstruction) {
      final M68kExgInstruction exgInstruction = (M68kExgInstruction) element;

      final M68kAdmRrd source = exgInstruction.getSource();
      final M68kAdmRrd destination = exgInstruction.getDestination();
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
