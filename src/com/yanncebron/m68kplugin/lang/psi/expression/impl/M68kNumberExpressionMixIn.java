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

package com.yanncebron.m68kplugin.lang.psi.expression.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kUnaryMinusExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class M68kNumberExpressionMixIn extends ASTWrapperPsiElement implements M68kNumberExpression {

  protected M68kNumberExpressionMixIn(@NotNull ASTNode node) {
    super(node);
  }

  /**
   * @return {@code null} for invalid number (out of range)
   */
  @Override
  public Object getValue() {
    String text = getText();
    boolean isNegative = getParent() instanceof M68kUnaryMinusExpression;

    IElementType elementType = getFirstChild().getNode().getElementType();
    if (elementType == M68kTokenTypes.DEC_NUMBER) {
      return parseNumber(text, isNegative, 10);
    }

    text = text.substring(1);
    if (elementType == M68kTokenTypes.HEX_NUMBER) {
      return parseNumber(text, isNegative, 16);
    }
    if (elementType == M68kTokenTypes.OCT_NUMBER) {
      return parseNumber(text, isNegative, 8);
    }
    if (elementType == M68kTokenTypes.BIN_NUMBER) {
      return parseNumber(text, isNegative, 2);
    }

    throw new IllegalArgumentException("could not determine getValue() for " + elementType + ", '" + text + "'");
  }

  @Nullable
  private static Integer parseNumber(String text, boolean isNegative, int radix) {
    try {
      return Integer.parseInt(isNegative ? "-" + text : text, radix);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
