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
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68KSimplifiableExpressionInspectionTest extends BasePlatformTestCase {

  public void testSuperfluousZero() {
    doTest("<warning descr=\"Expression can be simplified\">0</warning>+1");
    doTest("1+<warning descr=\"Expression can be simplified\">0</warning>");

    doTest("<warning descr=\"Expression can be simplified\">0</warning>-1");
    doTest("1-<warning descr=\"Expression can be simplified\">0</warning>");

    doTest("+<warning descr=\"Expression can be simplified\">0</warning>");
    doTest("-<warning descr=\"Expression can be simplified\">0</warning>");
  }

  public void testSuperfluousOne() {
    doTest("<warning descr=\"Expression can be simplified\">1</warning>*2");
    doTest("$2*<warning descr=\"Expression can be simplified\">1</warning>");

    doTest("2/<warning descr=\"Expression can be simplified\">1</warning>");

    doTest("2%<warning descr=\"Expression can be simplified\">$1</warning>");
  }

  public void testSuperfluousMinusOne() {
    doTest("2/<warning descr=\"Expression can be simplified\">-1</warning>");

    doTest("2*<warning descr=\"Expression can be simplified\">-1</warning>");
    doTest("<warning descr=\"Expression can be simplified\">-1</warning>*2");
  }

  public void testUnnecessaryParentheses() {
    doTest("<warning descr=\"Unnecessary parentheses\">(22)</warning>");
    doTest("-<warning descr=\"Unnecessary parentheses\">(22)</warning>");
    doTest("18+<warning descr=\"Unnecessary parentheses\">(22)</warning>");

    doTest("<warning descr=\"Unnecessary parentheses\">(+22)</warning>");
    doTest("<warning descr=\"Unnecessary parentheses\">(-22)</warning>");
  }

  public void testRemoveUnnecessaryParenthesesLiteral() {
    doTest("<warning descr=\"Unnecessary parentheses\">(<caret>22)</warning>");
    final IntentionAction intention = myFixture.findSingleIntention("Remove parentheses");
    myFixture.launchAction(intention);
    myFixture.checkResult(" dc 22");
  }

  private void doTest(String text) {
    myFixture.configureByText("a.s", " dc " + text);
    myFixture.enableInspections(new M68kSimplifiableExpressionInspection());
    myFixture.testHighlighting();
  }

}
