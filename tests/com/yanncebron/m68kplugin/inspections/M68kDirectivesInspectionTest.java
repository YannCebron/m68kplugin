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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kDirectivesInspectionTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new M68kDirectivesInspection());
  }

  public void testContentAfterEndDirective() {
    myFixture.configureByText("test.s",
      " end\n" +
        "; comment is allowed\n" +
        " <error descr=\"No content after 'end' directive allowed\">moveq #1,d0</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveMacroMissingEndm() {
    myFixture.configureByText("test.s",
      "<error descr=\"Missing matching 'endm' directive\">macroName macro</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveMacroMissingEndmNextMacro() {
    myFixture.configureByText("test.s",
      "<error descr=\"Missing matching 'endm' directive\">macroName macro</error>\n" +
        "anotherMacro macro\n" +
        " endm");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEndmMissingMacro() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'macro' directive\">endm</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveInlineMissingEinline() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'einline' directive\">inline</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEinlineMissingInline() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'inline' directive\">einline</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveRemMissingErem() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'erem' directive\">rem</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEremMissingRem() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'rem' directive\">erem</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveReptMissingEndr() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'endr' directive\">rept 5</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEndrMissingRept() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'rept' directive\">endr</error>");
    myFixture.testHighlighting();
  }

}
