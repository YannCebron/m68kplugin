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

  public void testCommentAfterWhitespace() {
    doTest("    ; comment",
      """
        WHITE_SPACE ('    ')
        comment ('; comment')""");
  }

  // todo "*"-prefix label

  public void testCommentLineFeedComment() {
    doTest("; Comment\n\n; second comment",
      """
        comment ('; Comment')
        LINEFEED ('\\n')
        LINEFEED ('\\n')
        comment ('; second comment')""");
  }

  public void testCommentAfterLabel() {
    doTest("label ; comment",
      """
        id ('label')
        WHITE_SPACE (' ')
        comment ('; comment')""");
  }

  public void testCommentAfterLabelWithoutWhitespace() {
    doTest("label; comment",
      """
        id ('label')
        comment ('; comment')""");
  }

  public void testStarCommentAfterLabel() {
    doTest("label * comment",
      """
        id ('label')
        WHITE_SPACE (' ')
        comment ('* comment')""");
  }

  public void testCommentAfterLabelWithColon() {
    doTest("label: ; comment",
      """
        id ('label')
        : (':')
        WHITE_SPACE (' ')
        comment ('; comment')""");
  }

  public void testStarCommentAfterLabelWithColon() {
    doTest("label: *** comment",
      """
        id ('label')
        : (':')
        WHITE_SPACE (' ')
        comment ('*** comment')""");
  }

  public void testCommentAfterInstruction() {
    doTest(" nop ; comment",
      """
        WHITE_SPACE (' ')
        nop ('nop')
        comment (' ; comment')""");
  }

  public void testCommentAfterInstructionWithoutWhitespace() {
    doTest(" nop; comment",
      """
        WHITE_SPACE (' ')
        nop ('nop')
        comment ('; comment')""");
  }

  public void testStarCommentAfterInstruction() {
    doTest(" nop * comment",
      """
        WHITE_SPACE (' ')
        nop ('nop')
        comment (' * comment')""");
  }

  public void testStarCommentAfterInstructionWithOperands() {
    doTest(" move.l d0,d1 * comment",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        data_register ('d0')
        , (',')
        data_register ('d1')
        WHITE_SPACE (' ')
        comment ('* comment')""");
  }

  public void testCommentAfterInstructionWithOperandsWithoutWhitespace() {
    doTest(" move.l d0,d1; comment",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        data_register ('d0')
        , (',')
        data_register ('d1')
        comment ('; comment')""");
  }

  public void testCommentWithoutPrefixAfterNopInstruction() {
    doTest(" nop comment",
      """
        WHITE_SPACE (' ')
        nop ('nop')
        comment (' comment')""");
  }

  public void testCommentWithoutPrefixAfterMoveInstruction() {
    doTest(" move #1,d6 comment",
      """
        WHITE_SPACE (' ')
        move ('move')
        WHITE_SPACE (' ')
        # ('#')
        dec_number ('1')
        , (',')
        data_register ('d6')
        WHITE_SPACE (' ')
        comment ('comment')""");
  }

  public void testCommentWithoutPrefixAfterMoveInstructionWithDataSize() {
    doTest(" move.l #1,d6 comment",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        # ('#')
        dec_number ('1')
        , (',')
        data_register ('d6')
        WHITE_SPACE (' ')
        comment ('comment')""");
  }

  public void testCommentWithoutPrefixAfterIncludeDirective() {
    doTest(" include path.i comment",
      """
        WHITE_SPACE (' ')
        include ('include')
        WHITE_SPACE (' ')
        string ('path.i')
        WHITE_SPACE (' ')
        comment ('comment')""");
  }

  public void testCommentWithoutPrefixAfterIncbinDirectiveWithOffsetLength() {
    doTest(" incbin 'x.bin',42,666 comment",
      """
        WHITE_SPACE (' ')
        incbin ('incbin')
        WHITE_SPACE (' ')
        string (''x.bin'')
        , (',')
        dec_number ('42')
        , (',')
        dec_number ('666')
        WHITE_SPACE (' ')
        comment ('comment')""");
  }

}
