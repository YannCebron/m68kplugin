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

package com.yanncebron.m68kplugin.lang.psi.expression;

import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("SpellCheckingInspection")
public class NumberExpressionPsiTest extends M68kPsiTestCase<M68kDcDirective> {

  public NumberExpressionPsiTest() {
    super(M68kDcDirective.class);
  }

  public void testGetValueInteger() {
    doTestGetValue("1234", 1234);
    doTestGetValue("-1234", -1234);
  }

  public void testGetValueHex() {
    doTestGetValue("$FFFF", 0xffff);
    doTestGetValue("$FFFFFFFF", -1);
    doTestGetValue("$FFFFFFFFFFFF", -1);
    doTestGetValue("$DFDFDF00", -538976512);
    doTestGetValue("$FFFFFFFE", -2);
    doTestGetValue("-$FFFF", -0xffff);
  }

  public void testGetValueOctal() {
    doTestGetValue("@123", 83);
    doTestGetValue("-@123", -83);
  }

  public void testGetValueBinary() {
    doTestGetValue("%01011", 11);
    doTestGetValue("-%01011", -11);
  }

  public void testGetValueFailsForOverflow() {
    doTestGetValue("" + Integer.MAX_VALUE, Integer.MAX_VALUE);
    doTestGetValue("" + (Integer.MAX_VALUE + 1L), -2147483648);
    doTestGetValue("" + Long.MAX_VALUE, -1);

    doTestGetValue("-2147483648", -2147483648);
    doTestGetValue("-2147483649", 2147483647);
  }

  public void testGetValueOptimizedZero() {
    doTestGetValue("0", 0);
    doTestGetValue("$0", 0);
    doTestGetValue("@0", 0);
    doTestGetValue("%0", 0);
  }

  public void testGetValueOptimizedOneMinusOne() {
    doTestGetValue("1", 1);
    doTestGetValue("$1", 1);
    doTestGetValue("@1", 1);
    doTestGetValue("%1", 1);

    doTestGetValue("-1", -1);
    doTestGetValue("-$1", -1);
    doTestGetValue("-@1", -1);
    doTestGetValue("-%1", -1);
  }

  public void testGetValueOptimizedTwoMinusTwo() {
    doTestGetValue("2", 2);
    doTestGetValue("$2", 2);
    doTestGetValue("@2", 2);

    doTestGetValue("-2", -2);
    doTestGetValue("-$2", -2);
    doTestGetValue("-@2", -2);
  }

  private void doTestGetValue(String numberValue, @Nullable Integer expectedValue) {
    final M68kExpression expression = parseNumber(numberValue);
    final M68kNumberExpression numberExpression = PsiTreeUtil.findChildOfType(expression, M68kNumberExpression.class, false);
    assertNotNull(numberExpression);
    assertEquals(numberValue, expectedValue, numberExpression.getValue());
  }

  private M68kExpression parseNumber(String expressionText) {
    final M68kDcDirective m68kDcDirective = parse(" dc " + expressionText);
    return assertInstanceOf(ContainerUtil.getFirstItem(m68kDcDirective.getExpressionList()), M68kExpression.class);
  }

}
