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

public class DirectivesLexerTest extends M68kLexerTestCase {

  // —— Symbol assignment directives ——

  public void testEquDirective() {
    doTest("label equ 42",
      """
        id ('label')
        WHITE_SPACE (' ')
        equ ('equ')
        WHITE_SPACE (' ')
        dec_number ('42')""");
  }

  public void testEqDirective() {
    doTest("label = 42",
      """
        id ('label')
        WHITE_SPACE (' ')
        = (DIRECTIVE) ('=')
        WHITE_SPACE (' ')
        dec_number ('42')""");
  }

  public void testEqDirectiveWithoutWhitespace() {
    doTest("A=42",
      """
        id ('A')
        = (DIRECTIVE) ('=')
        dec_number ('42')""");
  }

  public void testEqrDirective() {
    doTest("label equr d6",
      """
        id ('label')
        WHITE_SPACE (' ')
        equr ('equr')
        WHITE_SPACE (' ')
        Dn ('d6')""");
  }

  // —— Alignment directives ——

  public void testEvenDirective() {
    doTest(" even",
      """
        WHITE_SPACE (' ')
        even ('even')""");
  }

  public void testOddDirective() {
    doTest(" odd",
      """
        WHITE_SPACE (' ')
        odd ('odd')""");
  }

  // —— File inclusion directives ——
  // The STRING_DIRECTIVE state handles the string literal after the keyword.

  public void testIncbinDirective() {
    doTest(" incbin 'logo.raw' ; comment",
      """
        WHITE_SPACE (' ')
        incbin ('incbin')
        WHITE_SPACE (' ')
        string (''logo.raw'')
        WHITE_SPACE (' ')
        comment ('; comment')""");
  }

  public void testIncdirDirective() {
    doTest(" incdir df0: comment",
      """
        WHITE_SPACE (' ')
        incdir ('incdir')
        WHITE_SPACE (' ')
        string ('df0:')
        WHITE_SPACE (' ')
        comment ('comment')""");
  }

  // —— Data definition directives ——

  public void testDcDirective() {
    doTest(" dc.b 42",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        dec_number ('42')""");
  }

  public void testDcbDirective() {
    doTest(" dcb 42",
      """
        WHITE_SPACE (' ')
        dcb ('dcb')
        WHITE_SPACE (' ')
        dec_number ('42')""");
  }

  public void testDsDirective() {
    doTest(" ds 42",
      """
        WHITE_SPACE (' ')
        ds ('ds')
        WHITE_SPACE (' ')
        dec_number ('42')""");
  }

  // —— Register save area directives ——

  public void testRssetDirective() {
    doTest(" rsset 0",
      """
        WHITE_SPACE (' ')
        rsset ('rsset')
        WHITE_SPACE (' ')
        dec_number ('0')""");
  }

  public void testRsresetDirective() {
    doTest(" rsreset",
      """
        WHITE_SPACE (' ')
        rsreset ('rsreset')""");
  }

  public void testRsDirective() {
    doTest("label rs 42",
      """
        id ('label')
        WHITE_SPACE (' ')
        rs ('rs')
        WHITE_SPACE (' ')
        dec_number ('42')""");
  }

  // —— Other directives ——

  public void testBlkDirective() {
    doTest(" blk 42,666",
      """
        WHITE_SPACE (' ')
        blk ('blk')
        WHITE_SPACE (' ')
        dec_number ('42')
        , (',')
        dec_number ('666')""");
  }

  public void testOptDirective() {
    doTest(" opt w+",
      """
        WHITE_SPACE (' ')
        opt ('opt')
        WHITE_SPACE (' ')
        id ('w')
        + ('+')""");
  }
}
