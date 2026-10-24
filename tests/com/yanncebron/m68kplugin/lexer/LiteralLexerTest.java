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

public class LiteralLexerTest extends M68kLexerTestCase {

  // —— Number formats ——
  // Hex (`$`), octal (`@`), and binary (`%`) number prefixes.

  public void testHexNumber() {
    doTest(" dc.b $FF",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        hex_number ('$FF')""");
  }

  public void testOctalNumber() {
    doTest(" dc.b @177",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        oct_number ('@177')""");
  }

  public void testBinaryNumber() {
    doTest(" dc.b %1010",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        bin_number ('%1010')""");
  }

  // —— String literals ——
  // Single-quoted, double-quoted, escaped characters, and empty strings.

  public void testSingleQuotedString() {
    doTest(" dc.b 'hello'",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        string (''hello'')""");
  }

  public void testStringWithEscapedQuote() {
    doTest(" dc.b 'hello\\'world'",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        string (''hello\\'world'')""");
  }

  public void testStringWithEscapedBackslash() {
    doTest(" dc.b 'hello\\\\world'",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        string (''hello\\\\world'')""");
  }

  public void testEmptyString() {
    doTest(" dc.b ''",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        string ('''')""");
  }

  public void testStringWithSpaces() {
    doTest(" dc.b 'hello world'",
      """
        WHITE_SPACE (' ')
        dc ('dc')
        .b ('.b')
        WHITE_SPACE (' ')
        string (''hello world'')""");
  }

  // —— Unquoted strings ——
  // Used by directives like `include` for file paths.

  public void testUnquotedString() {
    doTest(" include df0:mydir/myfile",
      """
        WHITE_SPACE (' ')
        include ('include')
        WHITE_SPACE (' ')
        string ('df0:mydir/myfile')""");
  }

  // —— STRING_DIRECTIVE state ——
  // Directives like `echo`, `fail`, `include`, etc. enter STRING_DIRECTIVE state
  // after the keyword, then transition to IN_OPERAND after the string literal.

  public void testStringDirective() {
    doTest(" echo \"hello\"",
      """
        WHITE_SPACE (' ')
        echo ('echo')
        WHITE_SPACE (' ')
        string ('"hello"')""");
  }

}
