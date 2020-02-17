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

package com.yanncebron.m68kplugin.editor;

import com.intellij.codeInsight.editorActions.moveLeftRight.MoveElementLeftRightHandler;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kBinaryExpression;
import com.yanncebron.m68kplugin.lang.psi.M68kDcDirective;
import org.jetbrains.annotations.NotNull;

public class M68kMoveLeftRightHandler extends MoveElementLeftRightHandler {

  @NotNull
  @Override
  public PsiElement[] getMovableSubElements(@NotNull PsiElement element) {
    if (element instanceof M68kDcDirective) {
      return ((M68kDcDirective) element).getExpressionList().toArray(PsiElement.EMPTY_ARRAY);
    }
    if (element instanceof M68kBinaryExpression) {
      M68kBinaryExpression binaryExpression = ((M68kBinaryExpression) element);
      return new PsiElement[]{binaryExpression.getLeft(), binaryExpression.getRight()};
    }
    return PsiElement.EMPTY_ARRAY;
  }
}
