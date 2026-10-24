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

public class CommentLexerTest extends M68kLexerTestCase {

  // —— Semicolon comments ——

  public void testCommentOnFirstLine() {
    doTest("; Comment", "comment ('; Comment')");
  }

  public void testCommentAfterWhitespace() {
    doTest("    ; comment",
      """
        WHITE_SPACE ('    ')
        comment ('; comment')""");
  }

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

  public void testCommentAfterLabelWithColon() {
    doTest("label: ; comment",
      """
        id ('label')
        : (':')
        WHITE_SPACE (' ')
        comment ('; comment')""");
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

  public void testCommentAfterInstructionWithOperands() {
    doTest(" move.l d0,d1; comment",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        Dn ('d0')
        , (',')
        Dn ('d1')
        comment ('; comment')""");
  }

  // —— Star comments ——
  // The `*` prefix is recognized as a comment when preceded by whitespace.

  public void testStarCommentAfterLabel() {
    doTest("label * comment",
      """
        id ('label')
        WHITE_SPACE (' ')
        comment ('* comment')""");
  }

  public void testStarCommentAfterLabelWithColon() {
    doTest("label: *** comment",
      """
        id ('label')
        : (':')
        WHITE_SPACE (' ')
        comment ('*** comment')""");
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
        Dn ('d0')
        , (',')
        Dn ('d1')
        WHITE_SPACE (' ')
        comment ('* comment')""");
  }

  // —— Automatic comments (no prefix) ——
  // After the 2nd whitespace group in IN_OPERAND, the lexer switches to AFTER_OPERAND
  // where all remaining text becomes a comment without any prefix character.

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
        Dn ('d6')
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

  // —— rem/erem block comments ——

  public void testRemBlock() {
    doTest(" rem\nthis is a remark\n erem",
      """
        WHITE_SPACE (' ')
        rem ('rem')
        LINEFEED ('\\n')
        comment_rem ('this is a remark')
        LINEFEED ('\\n')
        erem (' erem')""");
  }

  public void testRemBlockWithContentOnSameLine() {
    doTest(" rem this is a remark\n erem",
      """
        WHITE_SPACE (' ')
        rem ('rem')
        comment_rem (' this is a remark')
        LINEFEED ('\\n')
        erem (' erem')""");
  }

  public void testRemBlockMultiLine() {
    doTest(" rem\nline one\nline two\nline three\n erem",
      """
        WHITE_SPACE (' ')
        rem ('rem')
        LINEFEED ('\\n')
        comment_rem ('line one')
        LINEFEED ('\\n')
        comment_rem ('line two')
        LINEFEED ('\\n')
        comment_rem ('line three')
        LINEFEED ('\\n')
        erem (' erem')""");
  }

}
