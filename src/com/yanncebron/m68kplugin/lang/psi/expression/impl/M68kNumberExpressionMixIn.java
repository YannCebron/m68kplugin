/*
 * Copyright 2021 The Authors
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
import org.jetbrains.annotations.NotNull;

abstract class M68kNumberExpressionMixIn extends ASTWrapperPsiElement implements M68kNumberExpression {

  protected M68kNumberExpressionMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public Object getValue() {
    final String text = getText();

    IElementType elementType = getFirstChild().getNode().getElementType();
    if (elementType == M68kTokenTypes.DEC_NUMBER) {
      return Long.parseLong(text);
    }
    if (elementType == M68kTokenTypes.HEX_NUMBER) {
      return Long.parseLong(text.substring(1), 16);
    }
    if (elementType == M68kTokenTypes.OCT_NUMBER) {
      return Long.parseLong(text.substring(1), 8);
    }
    if (elementType == M68kTokenTypes.BIN_NUMBER) {
      return Long.parseLong(text.substring(1), 2);
    }

    throw new IllegalArgumentException("could not determine getValue() for " + elementType + ", '" + text + "'");
  }
}
