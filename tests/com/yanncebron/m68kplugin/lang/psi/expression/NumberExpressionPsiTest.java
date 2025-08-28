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

import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;

public class NumberExpressionPsiTest extends M68kPsiTestCase<M68kDcDirective> {

  public NumberExpressionPsiTest() {
    super(M68kDcDirective.class);
  }

  public void testGetValueInteger() {
    doTestGetValue("1234", 1234L);
  }

  public void testGetValueHex() {
    doTestGetValue("$fffffffe", 4294967294L);
  }

  public void testGetValueOctal() {
    doTestGetValue("@123", 83L);
  }

  public void testGetValueBinary() {
    doTestGetValue("%01011", 11L);
  }

  private void doTestGetValue(String numberValue, Long expectedValue) {
    final M68kExpression expression = parseNumber(numberValue);
    final M68kNumberExpression numberExpression = assertInstanceOf(expression, M68kNumberExpression.class);
    assertEquals(numberValue, expectedValue, numberExpression.getValue());
  }

  private M68kExpression parseNumber(String expressionText) {
    final M68kDcDirective m68kDcDirective = parse(" dc " + expressionText);
    return assertInstanceOf(ContainerUtil.getFirstItem(m68kDcDirective.getExpressionList()), M68kExpression.class);
  }

}
