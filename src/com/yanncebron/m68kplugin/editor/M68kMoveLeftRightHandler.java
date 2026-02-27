/*
 * Copyright 2026 The Authors
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
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIfcConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIfncConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kBinaryExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    if (element instanceof M68kMacroCallDirective directive) {
      return directive.getMacroCallParameterList().toArray(PsiElement.EMPTY_ARRAY);
    }

    if (element instanceof M68kInstruction instruction) {
      return getMovableSubElementsForInstruction(instruction);
    }

    return PsiElement.EMPTY_ARRAY;
  }

  private static @NotNull PsiElement @NotNull [] getMovableSubElementsForInstruction(M68kInstruction instruction) {
    List<M68kAdm> admList = PsiTreeUtil.getChildrenOfTypeAsList(instruction, M68kAdm.class);
    if (admList.size() != 2) return PsiElement.EMPTY_ARRAY;

    M68kMnemonic matchingMnemonic = M68kMnemonicRegistry.getInstance().find(instruction);
    if (matchingMnemonic == null) return PsiElement.EMPTY_ARRAY;

    M68kDataSize dataSize = null;
    if (instruction instanceof M68kDataSized m68kDataSized) {
      dataSize = m68kDataSized.getDataSize();
    }

    for (M68kMnemonic mnemonic : M68kMnemonicRegistry.getInstance().findAll(matchingMnemonic.elementType())) {
      // must have 2 operands (e.g. ASL has firstOperand variants)
      if (mnemonic.secondOperand() == M68kOperand.NONE) {
        continue;
      }

      // existing explicit dataSize must be allowed
      if (dataSize != null && !mnemonic.dataSizes().contains(dataSize)) {
        continue;
      }

      // optimization: another mnemonic with 1:1 reversed operands
      if (mnemonic.firstOperand() == matchingMnemonic.secondOperand() && mnemonic.secondOperand() == matchingMnemonic.firstOperand()) {
        return admList.toArray(PsiElement.EMPTY_ARRAY);
      }

      if (mnemonic.firstOperand().matches(admList.get(1)) && mnemonic.secondOperand().matches(admList.get(0))) {
        return admList.toArray(PsiElement.EMPTY_ARRAY);
      }
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
