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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kSyntaxAnnotatorTest extends BasePlatformTestCase {

  public void testPrivilegedInstructions() {
    myFixture.configureByText("test.s",
      """
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">rte</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">reset</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">stop #2</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">andi #2,SR</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">or #2,SR</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">ori #2,SR</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">eori #2,SR</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">move.l usp,a0</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">move.w d0,sr</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">movec.l d0,VBR</info>
         <info descr="Privileged instruction" textAttributesKey="M68K_PRIVILEGED_INSTRUCTION">moves (a0),d0</info>
        """
    );
    myFixture.testHighlighting(false, true, false);
  }

  public void testLabels() {
    myFixture.configureByText("test.s",
      """
        .<info textAttributesKey="M68K_LOCAL_LABEL">localLabel</info>
        .<info textAttributesKey="M68K_LOCAL_LABEL">localMacroLabel<info textAttributesKey="M68K_MACRO_PARAMETER">\\@</info></info>
        <info textAttributesKey="M68K_LABEL">globalLabel</info>
        <info textAttributesKey="M68K_LABEL">globalMacroLabel<info textAttributesKey="M68K_MACRO_PARAMETER">\\@</info></info>
        """
    );
    myFixture.testHighlighting(false, true, false);
  }

  public void testBuiltinSymbol() {
    myFixture.configureByText("test.s",
      """
         dc.l <info textAttributesKey="M68K_BUILTIN_SYMBOL">*</info>-42
         IFGE <info textAttributesKey="M68K_BUILTIN_SYMBOL">__CPU</info>-68010
        """);
    myFixture.testHighlighting(false, true, false);
  }

  public void testMacroParameter() {
    myFixture.configureByText("test.s",
      """
        <info textAttributesKey="M68K_LABEL">macroName</info> macro
          <info textAttributesKey="M68K_MACRO_PARAMETER">\\1</info>
         move.<info textAttributesKey="M68K_MACRO_PARAMETER">\\0</info> d0,d1
         jsr _LVO<info textAttributesKey="M68K_MACRO_PARAMETER">\\a</info>(a6)
         jsr _<info textAttributesKey="M68K_MACRO_PARAMETER">\\1</info>_TwoMacroParams_<info textAttributesKey="M68K_MACRO_PARAMETER">\\2</info>(a6)
         endm"""
    );
    myFixture.testHighlighting(false, true, false);
  }

  public void testMacroCallRainbowHighlighting() {
    myFixture.testRainbow("rainbow.s",
      """
        macro1 macro
          endm
        macro2 macro
          endm
          <rainbow color='ff000004'>macro1</rainbow>
          <rainbow color='ff000001'>macro2</rainbow> 123,label""",
      true, true);
  }

}
