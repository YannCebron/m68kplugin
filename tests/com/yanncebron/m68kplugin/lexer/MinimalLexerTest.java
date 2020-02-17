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

public class MinimalLexerTest extends M68kLexerTestCase {

  public void testEmptyFile() {
    doTest("", "");
  }

  public void testLineFeedsOnly() {
    doTest("\n\n",
      "WHITE_SPACE ('\\n')\n" +
        "WHITE_SPACE ('\\n')");
  }

  public void testLineFeedsAndWhitespaceOnly() {
    doTest("  \n ",
      "WHITE_SPACE ('  ')\n" +
        "WHITE_SPACE ('\\n')\n" +
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
        "WHITE_SPACE ('\\n')");
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
      "WHITE_SPACE ('\\n')\n" +
        "id ('label')");
  }

  public void testLocalLabelOnFirstLine() {
    doTest(".localLabel",
      ". ('.')\n" +
        "id ('localLabel')");
  }

}
