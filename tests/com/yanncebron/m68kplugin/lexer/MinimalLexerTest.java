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

package com.yanncebron.m68kplugin.lexer;

public class MinimalLexerTest extends M68kLexerTestCase {

  public void testEmptyFile() {
    doTest("", "");
  }

  public void testLineFeedsOnly() {
    doTest("\n\n",
      "LINEFEED ('\\n')\n" +
        "LINEFEED ('\\n')");
  }

  public void testLineFeedsAndWhitespaceOnly() {
    doTest("  \n ",
      "WHITE_SPACE ('  ')\n" +
        "LINEFEED ('\\n')\n" +
        "WHITE_SPACE (' ')");
  }

  public void testLabelOnFirstLine() {
    doTest("label",
      "id ('label')");
  }

  public void testLabelWithColonOnFirstLine() {
    doTest("label:",
      "id ('label')\n" +
        ": (':')");
  }

  public void testLabelWithColonOnFirstLineWithLineFeed() {
    doTest("label:\n",
      "id ('label')\n" +
        ": (':')\n" +
        "LINEFEED ('\\n')");
  }

  public void testLabelFollowedByWhitespace() {
    doTest("label   ",
      "id ('label')\n" +
        "WHITE_SPACE ('   ')");
  }

  public void testLabelWithColonFollowedByWhitespace() {
    doTest("label:   ",
      "id ('label')\n" +
        ": (':')\n" +
        "WHITE_SPACE ('   ')");
  }

  public void testLabelWithColonFollowedByInstruction() {
    doTest("label: rts",
      "id ('label')\n" +
        ": (':')\n" +
        "WHITE_SPACE (' ')\n" +
        "rts ('rts')");
  }

  public void testLabelWithColonFollowedByInstructionNoWhitespace() {
    doTest("label:rts",
      "id ('label')\n" +
        ": (':')\n" +
        "rts ('rts')");
  }

  public void testLineFeedLabelOnSecondLine() {
    doTest("\nlabel",
      "LINEFEED ('\\n')\n" +
        "id ('label')");
  }

  public void testUnderscoreLabel() {
    doTest("_label",
      "id ('_label')");
  }

  public void testLabelContainingDot() {
    doTest("label.suffix",
      "id ('label.suffix')");
  }

  public void testUnderscoreTwiceLabel() {
    doTest("__label",
      "id ('__label')");
  }

  public void testLocalLabelOnFirstLine() {
    doTest(".localLabel",
      ". ('.')\n" +
        "id ('localLabel')");
  }

  public void testLocalLabelWithColonFollowedByInstructionNoWhitespace() {
    doTest(".localLabel:rts",
      ". ('.')\n" +
        "id ('localLabel')\n" +
        ": (':')\n" +
        "rts ('rts')");
  }

  public void testLocalLabelDotDataSizeName() {
    doTest(".s bra .s",
      ". ('.')\n" +
        "id ('s')\n" +
        "WHITE_SPACE (' ')\n" +
        "bra ('bra')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('.s')");
  }

  public void testLocalLabelDotDataSizeNameAfterComma() {
    doTest(".s dbf d0,.s",
      ". ('.')\n" +
        "id ('s')\n" +
        "WHITE_SPACE (' ')\n" +
        "dbf ('dbf')\n" +
        "WHITE_SPACE (' ')\n" +
        "data_register ('d0')\n" +
        ", (',')\n" +
        "id ('.s')");
  }

  public void testLocalLabelWithColonDotDataSizeName() {
    doTest(".s: bra .s",
      ". ('.')\n" +
        "id ('s')\n" +
        ": (':')\n" +
        "WHITE_SPACE (' ')\n" +
        "bra ('bra')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('.s')");
  }

  public void testLocalLabelDotDataSizeNameInstructionDataSize() {
    doTest(".s bra.s .s",
      ". ('.')\n" +
        "id ('s')\n" +
        "WHITE_SPACE (' ')\n" +
        "bra ('bra')\n" +
        ".s ('.s')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('.s')");
  }

  public void testLocalLabelMultipleDotDataSizeName() {
    doTest(".l\n" +
        ".s bra .s",
      ". ('.')\n" +
        "id ('l')\n" +
        "LINEFEED ('\\n')\n" +
        ". ('.')\n" +
        "id ('s')\n" +
        "WHITE_SPACE (' ')\n" +
        "bra ('bra')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('.s')");
  }

  public void testLocalLabelDollar() {
    doTest("localLabel$",
      "id ('localLabel')\n" +
        "$ ('$')");
  }

  public void testLocalLabelDollarColon() {
    doTest("localLabel$:",
      "id ('localLabel')\n" +
        "$ ('$')\n" +
        ": (':')");
  }

  public void testInstructionAdmAbsWithDataSize() {
    doTest("  movea.l 4.l,a0",
      "WHITE_SPACE ('  ')\n" +
        "movea ('movea')\n" +
        ".l ('.l')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('4')\n" +
        ".l ('.l')\n" +
        ", (',')\n" +
        "address_register ('a0')");
  }

  public void testExpressionUsingLabelsWithMnemonicName() {
    doTest(" dc.b bpl*4/trap",
      "WHITE_SPACE (' ')\n" +
        "dc ('dc')\n" +
        ".b ('.b')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('bpl')\n" +
        "* ('*')\n" +
        "dec_number ('4')\n" +
        "/ ('/')\n" +
        "id ('trap')");
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
      "WHITE_SPACE (' ')\n" +
        "dc ('dc')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('*')\n" +
        "- ('-')\n" +
        "dec_number ('4')");
  }

  public void testCurrentPcSymbolAfterDataSize() {
    doTest(" dc.b *-4",
      "WHITE_SPACE (' ')\n" +
        "dc ('dc')\n" +
        ".b ('.b')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('*')\n" +
        "- ('-')\n" +
        "dec_number ('4')");
  }

  public void testCurrentPcSymbolAfterMinusOperator() {
    doTest(" dc.b 42-*",
      "WHITE_SPACE (' ')\n" +
        "dc ('dc')\n" +
        ".b ('.b')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')\n" +
        "- ('-')\n" +
        "id ('*')");
  }

  public void testCurrentPcSymbolAfterComma() {
    doTest(" dc.b 1,*-42",
      "WHITE_SPACE (' ')\n" +
        "dc ('dc')\n" +
        ".b ('.b')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('1')\n" +
        ", (',')\n" +
        "id ('*')\n" +
        "- ('-')\n" +
        "dec_number ('42')");
  }
}
