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

package com.yanncebron.m68kplugin.lang.psi.expression;

import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;

public class NumberExpressionPsiTest extends M68kPsiTestCase {

  public void testGetValueInteger() {
    doTestGetValue("1234", 1234);
  }

  public void testGetValueHex() {
    doTestGetValue("$a", 10);
  }

  public void testGetValueOctal() {
    doTestGetValue("@123", 83);
  }

  public void testGetValueBinary() {
    doTestGetValue("%01011", 11);
  }

  private void doTestGetValue(String numberValue, Integer expectedValue) {
    final M68kExpression expression = parse(numberValue);
    final M68kNumberExpression numberExpression = assertInstanceOf(expression, M68kNumberExpression.class);
    assertEquals(numberValue, expectedValue, numberExpression.getValue());
  }

  private M68kExpression parse(String expressionText) {
    final M68kDcDirective m68kDcDirective = assertInstanceOf(doParse(" dc " + expressionText), M68kDcDirective.class);
    return assertInstanceOf(ContainerUtil.getFirstItem(m68kDcDirective.getExpressionList()), M68kExpression.class);
  }

}
