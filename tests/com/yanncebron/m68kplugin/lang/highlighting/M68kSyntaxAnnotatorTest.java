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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kSyntaxAnnotatorTest extends BasePlatformTestCase {

  public void testPrivilegedInstructions() {
    myFixture.configureByText("test.s",
      " <info descr=\"Privileged instruction\">rte</info>\n" +
        " <info descr=\"Privileged instruction\">reset</info>\n" +
        " <info descr=\"Privileged instruction\">stop #2</info>\n" +
        " <info descr=\"Privileged instruction\">andi #2,SR</info>\n" +
        " <info descr=\"Privileged instruction\">or #2,SR</info>\n" +
        " <info descr=\"Privileged instruction\">ori #2,SR</info>\n" +
        " <info descr=\"Privileged instruction\">eori #2,SR</info>\n" +
        " <info descr=\"Privileged instruction\">move.l usp,a0</info>\n" +
        " <info descr=\"Privileged instruction\">move.w d0,sr</info>\n"
    );
    myFixture.testHighlighting(false, true, false);
  }

  public void testLabels() {
    myFixture.configureByText("test.s",
      ".<info descr=\"M68K_LOCAL_LABEL\">localLabel</info>\n" +
        "<info descr=\"M68K_LABEL\">globalLabel</info>");
    myFixture.testHighlighting(false, true, false);
  }

  public void testMacroParameter() {
    myFixture.configureByText("test.s",
      "<info descr=\"M68K_LABEL\">macroName</info> macro\n" +
        "  <info descr=\"M68K_MACRO_PARAMETER\">\\1</info>\n" +
        " endm");
    myFixture.testHighlighting(false, true, false);
  }

  public void testMacroCall() {
    myFixture.configureByText("test.s",
      " <info descr=\"M68K_MACRO_CALL\">MACRO_NAME</info> d7,param");
    myFixture.testHighlighting(false, true, false);
  }

  public void testRemBlock() {
    myFixture.configureByText("test.s",
      " rem<info descr=\"DEFAULT_TEMPLATE_LANGUAGE_COLOR\">\n" +
        " bra somewhere\n" +
        " </info>erem");
    myFixture.testHighlighting(false, true, false);
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
}
