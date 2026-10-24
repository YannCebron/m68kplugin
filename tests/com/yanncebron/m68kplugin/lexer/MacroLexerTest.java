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

package com.yanncebron.m68kplugin.lexer;

public class MacroLexerTest extends M68kLexerTestCase {

  // —— Macro call in IN_INSTRUCTION ——
  // Any identifier that's not a known instruction/directive is tokenized as macro_call_id.
  // The lookahead 'macro' triggers MACRO_DECLARATION only when whitespace is directly
  // followed by the 'macro' keyword. 'myMacro macro' goes to IN_INSTRUCTION instead,
  // where myMacro is a macro_call_id and 'macro' is the id token.

  public void testMacroCall() {
    doTest(" myMacro arg1,arg2",
      """
        WHITE_SPACE (' ')
        macro_call_id ('myMacro')
        WHITE_SPACE (' ')
        id ('arg1')
        , (',')
        id ('arg2')""");
  }

  public void testMacroCallWithDataSize() {
    doTest(" myMacro.w arg1",
      """
        WHITE_SPACE (' ')
        macro_call_id ('myMacro')
        .w ('.w')
        WHITE_SPACE (' ')
        id ('arg1')""");
  }

  // —— MACRO_PARAMETER state ——

  public void testMacroParameterDigit() {
    doTest(" move.l \\0,d1",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        \\ ('\\')
        dec_number ('0')
        , (',')
        Dn ('d1')""");
  }

  public void testMacroParameterLetter() {
    doTest(" move.l \\a,d1",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        id ('\\a')
        , (',')
        Dn ('d1')""");
  }

  public void testMacroParameterInExpression() {
    doTest(" adda.l \\0*4,a0",
      """
        WHITE_SPACE (' ')
        adda ('adda')
        .l ('.l')
        WHITE_SPACE (' ')
        \\ ('\\')
        dec_number ('0')
        * ('*')
        dec_number ('4')
        , (',')
        An ('a0')""");
  }

  public void testMacroParameterMultiple() {
    doTest(" move.l \\0,\\1",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        \\ ('\\')
        dec_number ('0')
        , (',')
        \\ ('\\')
        dec_number ('1')""");
  }

  // —— `.\\0` in AFTER_INSTRUCTION (macro parameter data size) ——

  public void testMacroParameterDataSize() {
    doTest(" move.\\0 d0,d1",
      """
        WHITE_SPACE (' ')
        move ('move')
        .w ('.\\0')
        WHITE_SPACE (' ')
        Dn ('d0')
        , (',')
        Dn ('d1')""");
  }

  // —— `macro` keyword in AFTER_LABEL state ——

  public void testLabelMacroDeclaration() {
    doTest("myMacro macro",
      """
        id ('myMacro')
        WHITE_SPACE (' ')
        macro ('macro')""");
  }

  // —— endm and mexit directives ——

  public void testEndmDirective() {
    doTest(" endm",
      """
        WHITE_SPACE (' ')
        endm ('endm')""");
  }

  public void testMexitDirective() {
    doTest(" mexit",
      """
        WHITE_SPACE (' ')
        mexit ('mexit')""");
  }

  public void testCaseInsensitiveEndm() {
    doTest(" ENDM",
      """
        WHITE_SPACE (' ')
        endm ('ENDM')""");
  }

  // —— ifmacrod / ifmacrond directives ——

  public void testIfmacrodDirective() {
    doTest(" ifmacrod myMacro",
      """
        WHITE_SPACE (' ')
        ifmacrod ('ifmacrod')
        WHITE_SPACE (' ')
        id ('myMacro')""");
  }
}
