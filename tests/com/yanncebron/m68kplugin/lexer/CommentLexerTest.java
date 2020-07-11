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

  // todo ";" not required after ":"-label?
  public void testCommentAfterLabelWithColon() {
    doTest("label: ; comment",
      "id ('label')\n" +
        ": (':')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('; comment')");
  }

  public void testCommentAfterInstruction() {
    doTest(" nop ; comment",
      "WHITE_SPACE (' ')\n" +
        "nop ('nop')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('; comment')");
  }
}
