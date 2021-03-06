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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68KSimplifiableExpressionInspectionTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new M68kSimplifiableExpressionInspection());
  }

  public void testSuperfluousZero() {
    doTest(
      // 0+1
      "<warning descr=\"Expression can be simplified\">0</warning>+1",
      // 0000+1
      "<warning descr=\"Expression can be simplified\">0000</warning>+1",
      // +0+1
      "<warning descr=\"Expression can be simplified\">+0</warning>+1",
      // 1+0
      "1+<warning descr=\"Expression can be simplified\">0</warning>",

      // 0-1
      "<warning descr=\"Expression can be simplified\">0</warning>-1",
      // 1-0
      "1-<warning descr=\"Expression can be simplified\">0</warning>",

      // +0
      "<warning descr=\"Expression can be simplified\">+0</warning>",
      // -0
      "<warning descr=\"Expression can be simplified\">-0</warning>",
      // +(0)
      "<warning descr=\"Expression can be simplified\">+<warning descr=\"Unnecessary parentheses\">(0)</warning></warning>",
      // -(0)
      "<warning descr=\"Expression can be simplified\">-<warning descr=\"Unnecessary parentheses\">(0)</warning></warning>");
  }

  public void testSuperfluousOne() {
    doTest(
      // 1*2
      "<warning descr=\"Expression can be simplified\">1</warning>*2",
      // +1*2
      "<warning descr=\"Expression can be simplified\">+1</warning>*2",
      // $2*1
      "$2*<warning descr=\"Expression can be simplified\">1</warning>",

      // 2/1
      "2/<warning descr=\"Expression can be simplified\">1</warning>",
      // 2/+1
      "2/<warning descr=\"Expression can be simplified\">+1</warning>",

      // 2%$1
      "2%<warning descr=\"Expression can be simplified\">$1</warning>");
  }

  public void testSuperfluousMinusOne() {
    doTest(
      // 2/-1
      "2/<warning descr=\"Expression can be simplified\">-1</warning>",
      // 2/(-1)
      "2/<warning descr=\"Expression can be simplified\"><warning descr=\"Unnecessary parentheses\">(-1)</warning></warning>",

      // 2*-1
      "2*<warning descr=\"Expression can be simplified\">-1</warning>",
      // -1*2
      "<warning descr=\"Expression can be simplified\">-1</warning>*2");
  }

  public void testUnnecessaryParentheses() {
    doTest(
      // (22)
      "<warning descr=\"Unnecessary parentheses\">(22)</warning>",
      // -(22)
      "-<warning descr=\"Unnecessary parentheses\">(22)</warning>",
      // 18+(22)
      "18+<warning descr=\"Unnecessary parentheses\">(22)</warning>",

      // (+22)
      "<warning descr=\"Unnecessary parentheses\">(+22)</warning>",
      // (-22)
      "<warning descr=\"Unnecessary parentheses\">(-22)</warning>",

      // ((22))
      "<warning descr=\"Unnecessary parentheses\">(<warning descr=\"Unnecessary parentheses\">(22)</warning>)</warning>");
  }

  public void testRemoveUnnecessaryParenthesesLiteral() {
    doTest("<warning descr=\"Unnecessary parentheses\">(<caret>22)</warning>");
    final IntentionAction intention = myFixture.findSingleIntention("Remove parentheses");
    myFixture.launchAction(intention);
    myFixture.checkResult(" dc 22");
  }

  private void doTest(String... expressions) {
    myFixture.configureByText("a.s", StringUtil.join(expressions, s -> " dc " + s, "\n"));
    myFixture.testHighlighting();
  }

}
