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

package com.yanncebron.m68kplugin.lang.psi.expression;

import com.intellij.openapi.paths.WebReference;
import com.intellij.psi.PsiReference;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;

public class StringExpressionPsiTest extends M68kPsiTestCase<M68kDcDirective> {

  public StringExpressionPsiTest() {
    super(M68kDcDirective.class);
  }

  public void testGetValueDoubleQuotes() {
    final M68kExpression expression = parseString("\"text\"");
    final M68kStringExpression m68kStringExpression = assertInstanceOf(expression, M68kStringExpression.class);
    assertEquals("text", m68kStringExpression.getValue());
  }

  public void testGetValueSingleQuotes() {
    final M68kExpression expression = parseString("'text'");
    final M68kStringExpression m68kStringExpression = assertInstanceOf(expression, M68kStringExpression.class);
    assertEquals("text", m68kStringExpression.getValue());
  }

  public void testGetReferences() {
    final M68kExpression expression = parseString("'http://www.amiga.com'");
    final M68kStringExpression m68kStringExpression = assertInstanceOf(expression, M68kStringExpression.class);
    final PsiReference psiReference = assertOneElement(m68kStringExpression.getReferences());
    final WebReference webReference = assertInstanceOf(psiReference, WebReference.class);
    assertEquals("http://www.amiga.com", webReference.getUrl());
  }

  private M68kExpression parseString(String expressionText) {
    final M68kDcDirective m68kDcDirective = parse(" dc " + expressionText);
    return assertInstanceOf(ContainerUtil.getFirstItem(m68kDcDirective.getExpressionList()), M68kExpression.class);
  }

}
