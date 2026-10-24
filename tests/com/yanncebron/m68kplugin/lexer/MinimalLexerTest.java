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

public class MinimalLexerTest extends M68kLexerTestCase {

  // —— Empty / whitespace-only files ——

  public void testEmptyFile() {
    doTest("", "");
  }

  public void testWhitespaceAndLineFeeds() {
    doTest("  \n ",
      """
        WHITE_SPACE ('  ')
        LINEFEED ('\\n')
        WHITE_SPACE (' ')""");
  }

  // —— Registers with data size suffix ——
  // The `pushbackDataSize()` logic splits register + data size into two separate tokens.

  public void testDataRegisterWithDataSize() {
    doTest(" move.l d0.l,d1",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        Dn ('d0')
        .l ('.l')
        , (',')
        Dn ('d1')""");
  }

  public void testAddressRegisterWithDataSize() {
    doTest(" move.w a0.w,d1",
      """
        WHITE_SPACE (' ')
        move ('move')
        .w ('.w')
        WHITE_SPACE (' ')
        An ('a0')
        .w ('.w')
        , (',')
        Dn ('d1')""");
  }

  public void testSpWithDataSize() {
    doTest(" move.l sp.l,d0",
      """
        WHITE_SPACE (' ')
        move ('move')
        .l ('.l')
        WHITE_SPACE (' ')
        sp ('sp')
        .l ('.l')
        , (',')
        Dn ('d0')""");
  }

}
