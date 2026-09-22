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
    return switch (element) {
      case M68kDcDirective directive -> directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kDrDirective directive -> directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kOptDirective directive -> directive.getOptDirectiveArgList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kPrintvDirective directive -> directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kXdefDirective directive -> directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kXrefDirective directive -> directive.getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kIfcConditionalAssemblyDirective directive -> getElements(directive.getArg1(), directive.getArg2());
      case M68kIfncConditionalAssemblyDirective directive -> getElements(directive.getArg1(), directive.getArg2());
      case M68kBinaryExpression instruction -> getElements(instruction.getLeft(), instruction.getRight());
      case M68kAdmRegisterList registerList -> registerList.getRegisterRangeList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kAdmDoubleDrd doubleDrd -> getElements(doubleDrd.getFirst(), doubleDrd.getSecond());
      case M68kMacroCallDirective directive -> directive.getMacroCallParameterList().toArray(PsiElement.EMPTY_ARRAY);
      case M68kInstruction instruction -> getMovableSubElementsForInstruction(instruction);
      default -> PsiElement.EMPTY_ARRAY;
    };
  }

  static @NotNull PsiElement @NotNull [] getMovableSubElementsForInstruction(M68kInstruction instruction) {
    List<M68kAdm> admList = PsiTreeUtil.getChildrenOfTypeAsList(instruction, M68kAdm.class);
    if (admList.size() != 2) return PsiElement.EMPTY_ARRAY;

    M68kMnemonic matchingMnemonic = M68kMnemonicRegistry.getInstance().find(instruction);
    if (matchingMnemonic == null) return PsiElement.EMPTY_ARRAY;

    M68kDataSize dataSize = null;
    if (instruction instanceof M68kDataSized m68kDataSized) {
      dataSize = m68kDataSized.getDataSize();
    }

    for (M68kMnemonic mnemonic : M68kMnemonicRegistry.getInstance().findAll(matchingMnemonic.elementType())) {
      // must have 2 operands (e.g., ASL has firstOperand variants)
      if (!matchingMnemonic.hasSecondOperand()) {
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
