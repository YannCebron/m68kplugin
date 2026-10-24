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

public class LabelLexerTest extends M68kLexerTestCase {

  // —— Global labels ——

  public void testLabelWithColon() {
    doTest("label:",
      """
        id ('label')
        : (':')""");
  }

  public void testLabelWithColonFollowedByInstruction() {
    doTest("label: rts",
      """
        id ('label')
        : (':')
        WHITE_SPACE (' ')
        rts ('rts')""");
  }

  // —— Local labels (dot-prefixed) ——

  public void testLocalLabelWithColon() {
    doTest(".localLabel:rts",
      """
        . ('.')
        id ('localLabel')
        : (':')
        rts ('rts')""");
  }

  public void testLocalLabelDotSAsLabelAndTarget() {
    doTest(".s: bra .s",
      """
        . ('.')
        id ('s')
        : (':')
        WHITE_SPACE (' ')
        bra ('bra')
        WHITE_SPACE (' ')
        id ('.s')""");
  }

  public void testLocalLabelDollarColon() {
    doTest("localLabel$:",
      """
        id ('localLabel')
        $ ('$')
        : (':')""");
  }

  // —— Backslash-escaped macro labels ——

  public void testMacroLabelBackslashAtBegin() {
    doTest("\\@label",
      "id ('\\@label')");
  }

  // —— Current PC symbol (`*`) disambiguation ——
  // The `isCurrentPcSymbol()` check returns true when prevChar is space, tab, comma, minus, plus, or `(`.

  public void testCurrentPcSymbolAfterSpace() {
    doTest(" dc *-4",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        WHITE_SPACE (' ')
        id ('*')
        - ('-')
        dec_number ('4')""");
  }

  public void testCurrentPcSymbolAfterMinusOperator() {
    doTest(" dc.b 42-*",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        dec_number ('42')
        - ('-')
        id ('*')""");
  }

  public void testCurrentPcSymbolAfterComma() {
    doTest(" dc.b 1,*-42",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        dec_number ('1')
        , (',')
        id ('*')
        - ('-')
        dec_number ('42')""");
  }

  // —— MUL operator vs current PC symbol ——
  // When prevChar is NOT space, tab, comma, minus, plus, or `(`, `*` is tokenized as MUL.

  public void testMulAfterDigit() {
    doTest(" dc.b 42*2",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        dec_number ('42')
        * ('*')
        dec_number ('2')""");
  }

  public void testMulAfterClosingParenthesis() {
    doTest(" dc.b (d0*4)",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        ( ('(')
        Dn ('d0')
        * ('*')
        dec_number ('4')
        ) (')')""");
  }

  // —— Data size disambiguation ——
  // The `afterSpaceOrComma()` check returns ID (local label) rather than data size
  // when preceded by space/tab/comma. Tested for all four data sizes.

  public void testLocalLabelDotBAfterSpace() {
    doTest(" bra .b",
      """
        WHITE_SPACE (' ')
        bra ('bra')
        WHITE_SPACE (' ')
        id ('.b')""");
  }

  public void testLocalLabelDotBAfterComma() {
    doTest(" dbf d0,.b",
      """
        WHITE_SPACE (' ')
        dbf ('dbf')
        WHITE_SPACE (' ')
        Dn ('d0')
        , (',')
        id ('.b')""");
  }

  public void testLocalLabelDotWAfterSpace() {
    doTest(" bra .w",
      """
        WHITE_SPACE (' ')
        bra ('bra')
        WHITE_SPACE (' ')
        id ('.w')""");
  }

  public void testLocalLabelDotLAfterSpace() {
    doTest(" bra .l",
      """
        WHITE_SPACE (' ')
        bra ('bra')
        WHITE_SPACE (' ')
        id ('.l')""");
  }

}
