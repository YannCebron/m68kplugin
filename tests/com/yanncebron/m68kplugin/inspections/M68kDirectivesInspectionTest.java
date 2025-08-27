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
      """
         end
        ; comment is allowed
         <error descr="No content after 'end' directive allowed">moveq #1,d0
         tst.l d0</error>""");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveMacroMissingEndm() {
    myFixture.configureByText("test.s",
      "<error descr=\"Missing matching 'endm' closing directive\">macroName macro</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveMacroMissingEndmNextMacro() {
    myFixture.configureByText("test.s",
      "<error descr=\"Missing matching 'endm' closing directive\">macroName macro</error>\n" +
        "anotherMacro macro\n" +
        " endm");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEndmMissingMacro() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'macro' opening directive\">endm</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveInlineMissingEinline() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'einline' closing directive\">inline</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEinlineMissingInline() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'inline' opening directive\">einline</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveRemMissingErem() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'erem' closing directive\">rem</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEremMissingRem() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'rem' opening directive\">erem</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveReptMissingEndr() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'endr' closing directive\">rept 5</error>");
    myFixture.testHighlighting();
  }

  public void testUnmatchedDirectiveEndrMissingRept() {
    myFixture.configureByText("test.s",
      " <error descr=\"Missing matching 'rept' opening directive\">endr</error>");
    myFixture.testHighlighting();
  }

  public void testNestedRept() {
    myFixture.configureByText("test.s",
      "  rept 1\n" +
        "  rept 2\n" +
        "  rept 3\n" +
        "  endr\n" +
        "  endr\n" +
        "  endr\n" +
        "\n" +
        "  rept 1\n" +
        "  rept 2\n" +
        "  endr\n" +
        "  rept 3\n" +
        "  endr\n" +
        "  endr");
    myFixture.testHighlighting();
  }

  public void testNestedReptMissingEndr() {
    myFixture.configureByText("test.s",
      "  <error descr=\"Missing matching 'endr' closing directive\">rept 1</error>\n" +
        "  rept 2\n" +
        "  endr");
    myFixture.testHighlighting();
  }

  public void testNestedReptMissingRept() {
    myFixture.configureByText("test.s",
      "  rept 1\n" +
        "  endr\n" +
        "  <error descr=\"Missing matching 'rept' opening directive\">endr</error>");
    myFixture.testHighlighting();
  }

  public void testAutoDirective() {
    myFixture.configureByText("a.s",
      " <error descr=\"Unsupported directive\">auto</error>");
    myFixture.testHighlighting();
  }

  public void testMask2Directive() {
    myFixture.configureByText("a.s",
      " <error descr=\"Unsupported directive\">mask2</error>");
    myFixture.testHighlighting();
  }

}
