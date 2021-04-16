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

public class CommentLexerTest extends M68kLexerTestCase {

  public void testCommentWithoutText() {
    doTest(";", "comment (';')");
  }

  public void testCommentOnFirstLine() {
    doTest("; Comment", "comment ('; Comment')");
  }

  public void testEmptyCommentOnFirstLine() {
    doTest(";",
      "comment (';')");
  }

  // todo "*"-prefix label

  public void testCommentLineFeedComment() {
    doTest("; Comment\n\n; second comment",
      "comment ('; Comment')\n" +
        "LINEFEED ('\\n')\n" +
        "LINEFEED ('\\n')\n" +
        "comment ('; second comment')");
  }

  public void testCommentAfterLabel() {
    doTest("label ; comment",
      "id ('label')\n" +
        "comment (' ; comment')");
  }

  public void testCommentAfterLabelWithoutWhitespace() {
    doTest("label; comment",
      "id ('label')\n" +
        "comment ('; comment')");
  }

  public void testStarCommentAfterLabel() {
    doTest("label * comment",
      "id ('label')\n" +
        "comment (' * comment')");
  }

  public void testCommentAfterLabelWithColon() {
    doTest("label: ; comment",
      "id ('label')\n" +
        ": (':')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('; comment')");
  }

  public void testStarCommentAfterLabelWithColon() {
    doTest("label: *** comment",
      "id ('label')\n" +
        ": (':')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('*** comment')");
  }

  public void testCommentAfterInstruction() {
    doTest(" nop ; comment",
      "WHITE_SPACE (' ')\n" +
        "nop ('nop')\n" +
        "comment (' ; comment')");
  }

  public void testCommentAfterInstructionWithoutWhitespace() {
    doTest(" nop; comment",
      "WHITE_SPACE (' ')\n" +
        "nop ('nop')\n" +
        "comment ('; comment')");
  }

  public void testStarCommentAfterInstruction() {
    doTest(" nop * comment",
      "WHITE_SPACE (' ')\n" +
        "nop ('nop')\n" +
        "comment (' * comment')");
  }

  public void testStarCommentAfterInstructionWithOperands() {
    doTest(" move.l d0,d1 * comment",
      "WHITE_SPACE (' ')\n" +
        "move ('move')\n" +
        ".l ('.l')\n" +
        "WHITE_SPACE (' ')\n" +
        "data_register ('d0')\n" +
        ", (',')\n" +
        "data_register ('d1')\n" +
        "comment (' * comment')");
  }

  public void testCommentAfterInstructionWithOperandsWithoutWhitespace() {
    doTest(" move.l d0,d1; comment",
      "WHITE_SPACE (' ')\n" +
        "move ('move')\n" +
        ".l ('.l')\n" +
        "WHITE_SPACE (' ')\n" +
        "data_register ('d0')\n" +
        ", (',')\n" +
        "data_register ('d1')\n" +
        "comment ('; comment')");
  }

  public void testCommentWithoutPrefixAfterNopInstruction() {
    doTest(" nop comment",
      "WHITE_SPACE (' ')\n" +
        "nop ('nop')\n" +
        "comment (' comment')");
  }

  public void testCommentWithoutPrefixAfterMoveInstruction() {
    doTest(" move #1,d6 comment",
      "WHITE_SPACE (' ')\n" +
        "move ('move')\n" +
        "WHITE_SPACE (' ')\n" +
        "# ('#')\n" +
        "dec_number ('1')\n" +
        ", (',')\n" +
        "data_register ('d6')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('comment')");
  }

  public void testCommentWithoutPrefixAfterMoveInstructionWithDataSize() {
    doTest(" move.l #1,d6 comment",
      "WHITE_SPACE (' ')\n" +
        "move ('move')\n" +
        ".l ('.l')\n" +
        "WHITE_SPACE (' ')\n" +
        "# ('#')\n" +
        "dec_number ('1')\n" +
        ", (',')\n" +
        "data_register ('d6')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('comment')");
  }

  public void testCommentWithoutPrefixAfterIncludeDirective() {
    doTest(" include path.i comment",
      "WHITE_SPACE (' ')\n" +
        "include ('include')\n" +
        "WHITE_SPACE (' ')\n" +
        "string ('path.i')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('comment')");
  }

  public void testCommentWithoutPrefixAfterIncbinDirectiveWithOffsetLength() {
    doTest(" incbin 'x.bin',42,666 comment",
      "WHITE_SPACE (' ')\n" +
        "incbin ('incbin')\n" +
        "WHITE_SPACE (' ')\n" +
        "string (''x.bin'')\n" +
        ", (',')\n" +
        "dec_number ('42')\n" +
        ", (',')\n" +
        "dec_number ('666')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('comment')");
  }

}
