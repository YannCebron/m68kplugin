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

package com.yanncebron.m68kplugin.parser;

import com.intellij.testFramework.TestDataPath;

@TestDataPath("$PROJECT_ROOT/testData/parser/expression")
public class ExpressionParsingTest extends M68kParsingTestCase {

  public ExpressionParsingTest() {
    super("expression");
  }

  public void testPlusExpression() throws Exception {
    doCodeTest(" dc 1+2");
  }

  public void testPlusExpressionMissingSecondExpression() throws Exception {
    doCodeTest(" dc 1+");
  }

  public void testMinusExpression() throws Exception {
    doCodeTest(" dc 1-2");
  }

  public void testMulExpression() throws Exception {
    doCodeTest(" dc 1*2");
  }

  public void testDivExpression() throws Exception {
    doCodeTest(" dc 1/2");
  }

  public void testModExpression() throws Exception {
    doCodeTest(" dc 10%3");
  }

  public void testUnaryPlusExpression() throws Exception {
    doCodeTest(" dc +1");
  }

  public void testUnaryPlusExpressionMissingExpression() throws Exception {
    doCodeTest(" dc +");
  }

  public void testUnaryMinusExpression() throws Exception {
    doCodeTest(" dc -1");
  }

  public void testUnaryMinusExpressionMissingExpression() throws Exception {
    doCodeTest(" dc -");
  }

  public void testUnaryComplementExpression() throws Exception {
    doCodeTest(" dc ~1");
  }

  public void testUnaryComplementExpressionMissingExpression() throws Exception {
    doCodeTest(" dc ~");
  }

  public void testExpExpression() throws Exception {
    doCodeTest(" dc 1^2");
  }

  public void testDecNumberExpression() throws Exception {
    doCodeTest(" dc 1234");
  }

  public void testHexNumberExpression() throws Exception {
    doCodeTest(" dc $1234abc");
  }

  public void testOctNumberExpression() throws Exception {
    doCodeTest(" dc @0123");
  }

  public void testBinNumberExpression() throws Exception {
    doCodeTest(" dc %01010");
  }

  public void testStringExpressionDoubleQuote() throws Exception {
    doCodeTest(" dc \"string\"");
  }

  public void testStringExpressionSingleQuote() throws Exception {
    doCodeTest(" dc 'string'");
  }

  public void testParenExpression() throws Exception {
    doCodeTest(" dc (1)");
  }

  public void testParenExpressionMissingExpression() throws Exception {
    doCodeTest(" dc (");
  }

  public void testParenExpressionMissingRightParen() throws Exception {
    doCodeTest(" dc (1");
  }

  public void testParenExpressionWithNestedExpression() throws Exception {
    doCodeTest(" dc (1+2)");
  }

  public void testLabelRefExpression() throws Exception {
    doCodeTest(" dc label");
  }

  public void testShiftLeftExpression() throws Exception {
    doCodeTest(" dc 1<<2");
  }

  public void testShiftLeftExpressionMissingRightExpression() throws Exception {
    doCodeTest(" dc 1<<");
  }

  public void testShiftRightExpression() throws Exception {
    doCodeTest(" dc 1>>2");
  }

  public void testShiftRightExpressionMissingRightExpression() throws Exception {
    doCodeTest(" dc 1>>");
  }

  public void testAndExpression() throws Exception {
    doCodeTest(" dc 1&2");
  }

  public void testAndExpressionMissingRightExpression() throws Exception {
    doCodeTest(" dc 1&");
  }

  public void testOrExpression() throws Exception {
    doCodeTest(" dc 1|2");
  }

  public void testOrExpressionMissingRightExpression() throws Exception {
    doCodeTest(" dc 1|");
  }

  public void testComplexExpression() throws Exception {
    doCodeTest(" dc 1+(2+3)/-5");
  }
}
