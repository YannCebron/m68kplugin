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

import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;

public class M68kExpressionUtilTest extends M68kPsiTestCase<M68kDcDirective> {

  public M68kExpressionUtilTest() {
    super(M68kDcDirective.class);
  }

  public void testIsNumberValue() {
    assertTrue(M68kExpressionUtil.isNumberValue(getExpression("42"), 42));
    assertTrue(M68kExpressionUtil.isNumberValue(getExpression("+42"), 42));
    assertTrue(M68kExpressionUtil.isNumberValue(getExpression("-42"), -42));
    assertTrue(M68kExpressionUtil.isNumberValue(getExpression("-$F"), -15));
  }

  public void testUnwrapParentheses() {
    doTestUnwrapParentheses("1");
    doTestUnwrapParentheses("(1)");
    doTestUnwrapParentheses("((1))");
  }

  private void doTestUnwrapParentheses(String expressionText) {
    M68kExpression unwrappedExpression = M68kExpressionUtil.unwrapParentheses(getExpression(expressionText));
    assertNotNull(unwrappedExpression);
    assertEquals("1", unwrappedExpression.getText());
  }

  public void testComputeConstantValueLiterals() {
    assertEquals(0, computeConstantValue("$0"));
    assertEquals(1, computeConstantValue("%1"));
    assertEquals(-1, computeConstantValue("-1"));

    assertEquals("string", computeConstantValue("\"string\""));
  }

  public void testComputeConstantValueParenExpression() {
    assertEquals(1, computeConstantValue("(1)"));
    assertEquals(1, computeConstantValue("(0+1)"));
    assertEquals(1, computeConstantValue("((1))"));
    assertEquals(1, computeConstantValue("((0+1))"));
  }

  public void testComputeConstantValuePlusExpression() {
    assertEquals(2, computeConstantValue("1+1"));

    assertNull(computeConstantValue("1+X")); // NaN
  }

  public void testComputeConstantValuePlusExpressionOverflow() {
    try {
      computeConstantValue(Integer.MAX_VALUE + "+1");
      fail("must overflow");
    } catch (M68kConstantEvaluationOverflowException e) {
      assertEquals("2147483647+1", e.getOverflowingExpression().getText());
    }
  }

  public void testComputeConstantValueMinusExpression() {
    assertEquals(2, computeConstantValue("3-1"));
  }

  public void testComputeConstantValueMinusExpressionOverflow() {
    try {
      computeConstantValue(Integer.MIN_VALUE + "-1");
      fail("must overflow");
    } catch (M68kConstantEvaluationOverflowException e) {
      assertEquals("-2147483648-1", e.getOverflowingExpression().getText());
    }
  }

  public void testComputeConstantValueMulExpression() {
    assertEquals(16, computeConstantValue("2*8"));
  }

  public void testComputeConstantValueMulExpressionOverflow() {
    assertEquals(-2, computeConstantValueNoOverflow(Integer.MAX_VALUE + "*2"));

    try {
      computeConstantValue(Integer.MAX_VALUE + "*2");
      fail("must overflow");
    } catch (M68kConstantEvaluationOverflowException e) {
      assertEquals("2147483647*2", e.getOverflowingExpression().getText());
    }
  }

  public void testComputeConstantValueDivExpression() {
    assertEquals(2, computeConstantValue("8/4"));
  }

  public void testComputeConstantValueDivExpressionOverflowDivisionByZero() {
    assertNull(computeConstantValueNoOverflow("1/0"));

    try {
      computeConstantValue("42+" + Integer.MAX_VALUE + "/0");
      fail("must overflow");
    } catch (M68kConstantEvaluationOverflowException e) {
      assertEquals("2147483647/0", e.getOverflowingExpression().getText());
    }
  }

  public void testComputeConstantValueDivExpressionOverflow() {
    try {
      computeConstantValue(Integer.MIN_VALUE + "/-1");
      fail("must overflow");
    } catch (M68kConstantEvaluationOverflowException e) {
      assertEquals("-2147483648/-1", e.getOverflowingExpression().getText());
    }
  }

  public void testComputeConstantValueModExpression() {
    assertEquals(4, computeConstantValue("9%5"));
  }

  public void testComputeConstantValueModExpressionOverflowByZero() {
    assertNull(computeConstantValueNoOverflow("5//0"));

    try {
      computeConstantValue("5//0");
      fail("must overflow");
    } catch (M68kConstantEvaluationOverflowException e) {
      assertEquals("5//0", e.getOverflowingExpression().getText());
    }
  }

  public void testComputeConstantValueUnaryMinusExpression() {
    assertEquals(-1, computeConstantValue("-(2-1)"));
  }

  public void testComputeConstantValueUnaryPlusExpression() {
    assertEquals(1, computeConstantValue("+1"));
    assertEquals(1, computeConstantValue("+(1)"));
  }

  public void testComputeConstantValueUnaryComplementExpression() {
    assertEquals(-1, computeConstantValue("~0"));
    assertEquals(-2, computeConstantValue("~1"));
  }

  public void testComputeConstantValueUnaryNotExpression() {
    assertEquals(0, computeConstantValue("!42"));
    assertEquals(-1, computeConstantValue("!0"));
  }

  public void testComputeConstantShiftLeftExpression() {
    assertEquals(4, computeConstantValue("1<<2"));
  }

  public void testComputeConstantShiftRightExpression() {
    assertEquals(4, computeConstantValue("16>>2"));
  }

  public void testComputeConstantAndExpression() {
    assertEquals(2, computeConstantValue("7&2"));
  }

  public void testComputeConstantOrExpression() {
    assertEquals(5, computeConstantValue("1|4"));
  }

  public void testComputeConstantXorExpression() {
    assertEquals(6, computeConstantValue("2^4"));
  }

  private Object computeConstantValue(String expressionText) {
    return M68kExpressionUtil.computeConstantValue(getExpression(expressionText));
  }

  private Object computeConstantValueNoOverflow(String expressionText) {
    return M68kExpressionUtil.computeConstantValueNoOverflow(getExpression(expressionText));
  }

  private M68kExpression getExpression(String expressionText) {
    final M68kDcDirective m68kDcDirective = parse(" dc " + expressionText);
    return assertInstanceOf(ContainerUtil.getFirstItem(m68kDcDirective.getExpressionList()), M68kExpression.class);
  }
}
