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

package com.yanncebron.m68kplugin.lexer;

public class MinimalLexerTest extends M68kLexerTestCase {

  public void testEmptyFile() {
    doTest("", "");
  }

  public void testLineFeedsOnly() {
    doTest("\n\n",
      """
        LINEFEED ('\\n')
        LINEFEED ('\\n')""");
  }

  public void testLineFeedsAndWhitespaceOnly() {
    doTest("  \n ",
      """
        WHITE_SPACE ('  ')
        LINEFEED ('\\n')
        WHITE_SPACE (' ')""");
  }

  public void testLabelOnFirstLine() {
    doTest("label",
      "id ('label')");
  }

  public void testLabelWithColonOnFirstLine() {
    doTest("label:",
      """
        id ('label')
        : (':')""");
  }

  public void testLabelWithColonOnFirstLineWithLineFeed() {
    doTest("label:\n",
      """
        id ('label')
        : (':')
        LINEFEED ('\\n')""");
  }

  public void testLabelFollowedByWhitespace() {
    doTest("label   ",
      """
        id ('label')
        WHITE_SPACE ('   ')""");
  }

  public void testLabelWithColonFollowedByWhitespace() {
    doTest("label:   ",
      """
        id ('label')
        : (':')
        WHITE_SPACE ('   ')""");
  }

  public void testLabelWithColonFollowedByInstruction() {
    doTest("label: rts",
      """
        id ('label')
        : (':')
        WHITE_SPACE (' ')
        rts ('rts')""");
  }

  public void testLabelWithColonFollowedByInstructionNoWhitespace() {
    doTest("label:rts",
      """
        id ('label')
        : (':')
        rts ('rts')""");
  }

  public void testLineFeedLabelOnSecondLine() {
    doTest("\nlabel",
      """
        LINEFEED ('\\n')
        id ('label')""");
  }

  public void testUnderscoreLabel() {
    doTest("_label",
      "id ('_label')");
  }

  public void testLabelContainingDot() {
    doTest("label.suffix",
      "id ('label.suffix')");
  }

  public void testLabelAfterWhitespaceWithColon() {
    doTest("  label:",
      """
        WHITE_SPACE ('  ')
        id ('label')
        : (':')
        """);
  }

  public void testUnderscoreTwiceLabel() {
    doTest("__label",
      "id ('__label')");
  }

  public void testLocalLabelOnFirstLine() {
    doTest(".localLabel",
      """
        . ('.')
        id ('localLabel')""");
  }

  public void testLocalLabelWithColonFollowedByInstructionNoWhitespace() {
    doTest(".localLabel:rts",
      """
        . ('.')
        id ('localLabel')
        : (':')
        rts ('rts')""");
  }

  public void testLocalLabelDotDataSizeName() {
    doTest(".s bra .s",
      """
        . ('.')
        id ('s')
        WHITE_SPACE (' ')
        bra ('bra')
        WHITE_SPACE (' ')
        id ('.s')""");
  }

  public void testLocalLabelDotDataSizeNameAfterComma() {
    doTest(".s dbf d0,.s",
      """
        . ('.')
        id ('s')
        WHITE_SPACE (' ')
        dbf ('dbf')
        WHITE_SPACE (' ')
        data_register ('d0')
        , (',')
        id ('.s')""");
  }

  public void testLocalLabelWithColonDotDataSizeName() {
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

  public void testLocalLabelDotDataSizeNameInstructionDataSize() {
    doTest(".s bra.s .s",
      """
        . ('.')
        id ('s')
        WHITE_SPACE (' ')
        bra ('bra')
        .s ('.s')
        WHITE_SPACE (' ')
        id ('.s')""");
  }

  public void testLocalLabelMultipleDotDataSizeName() {
    doTest(".l\n" +
        ".s bra .s",
      """
        . ('.')
        id ('l')
        LINEFEED ('\\n')
        . ('.')
        id ('s')
        WHITE_SPACE (' ')
        bra ('bra')
        WHITE_SPACE (' ')
        id ('.s')""");
  }

  public void testLocalLabelDollar() {
    doTest("localLabel$",
      """
        id ('localLabel')
        $ ('$')""");
  }

  public void testLocalLabelDollarColon() {
    doTest("localLabel$:",
      """
        id ('localLabel')
        $ ('$')
        : (':')""");
  }

  public void testLocalLabelAfterWhitespaceWithColon() {
    doTest("  .label:",
      """
        WHITE_SPACE ('  ')
        . ('.')
        id ('label')
        : (':')""");
  }

  public void testInstructionAdmAbsWithDataSize() {
    doTest("  movea.l 4.l,a0",
      """
        WHITE_SPACE ('  ')
        movea ('movea')
        .l ('.l')
        WHITE_SPACE (' ')
        dec_number ('4')
        .l ('.l')
        , (',')
        address_register ('a0')""");
  }

  public void testExpressionUsingLabelsWithMnemonicName() {
    doTest(" dc.b bpl*4/trap",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        id ('bpl')
        * ('*')
        dec_number ('4')
        / ('/')
        id ('trap')""");
  }

  public void testMacroLabelBackslashAt() {
    doTest("label\\@",
      "id ('label\\@')");
  }

  public void testMacroLabelBackslashAtBegin() {
    doTest("\\@label",
      "id ('\\@label')");
  }

  public void testCurrentPcSymbol() {
    doTest(" dc *-4",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        WHITE_SPACE (' ')
        id ('*')
        - ('-')
        dec_number ('4')""");
  }

  public void testCurrentPcSymbolAfterDataSize() {
    doTest(" dc.b *-4",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
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

  public void testCurrentPcSymbolAfterOpeningParentheses() {
    doTest(" dc.b (pc-2)",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        ( ('(')
        pc ('pc')
        - ('-')
        dec_number ('2')
        ) (')')""");
  }
}
