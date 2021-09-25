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

public class DirectivesLexerTest extends M68kLexerTestCase {

  public void testEquDirective() {
    doTest("label equ 42",
      "id ('label')\n" +
        "WHITE_SPACE (' ')\n" +
        "equ ('equ')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')");
  }

  public void testEqDirective() {
    doTest("label = 42",
      "id ('label')\n" +
        "WHITE_SPACE (' ')\n" +
        "= (DIRECTIVE) ('=')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')");
  }

  public void testEqDirectiveWithoutWhitespace() {
    doTest("A=42",
      "id ('A')\n" +
        "= (DIRECTIVE) ('=')\n" +
        "dec_number ('42')");
  }

  public void testEqrDirective() {
    doTest("label equr d6",
      "id ('label')\n" +
        "WHITE_SPACE (' ')\n" +
        "equr ('equr')\n" +
        "WHITE_SPACE (' ')\n" +
        "data_register ('d6')");
  }

  public void testEvenDirective() {
    doTest(" even",
      "WHITE_SPACE (' ')\n" +
        "even ('even')");
  }

  public void testOddDirective() {
    doTest(" odd",
      "WHITE_SPACE (' ')\n" +
        "odd ('odd')");
  }

  public void testIncbinDirective() {
    doTest(" incbin 'logo.raw' ; comment",
      "WHITE_SPACE (' ')\n" +
        "incbin ('incbin')\n" +
        "WHITE_SPACE (' ')\n" +
        "string (''logo.raw'')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('; comment')");
  }

  public void testIncdirDirectiveWithDoubleQuotes() {
    doTest(" incdir \"dir\" comment",
      "WHITE_SPACE (' ')\n" +
        "incdir ('incdir')\n" +
        "WHITE_SPACE (' ')\n" +
        "string ('\"dir\"')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('comment')");
  }

  public void testIncdirDirectiveNoQuotes() {
    doTest(" incdir df0: comment",
      "WHITE_SPACE (' ')\n" +
        "incdir ('incdir')\n" +
        "WHITE_SPACE (' ')\n" +
        "string ('df0:')\n" +
        "WHITE_SPACE (' ')\n" +
        "comment ('comment')");
  }

  public void testIncludeDirective() {
    doTest(" include path.i",
      "WHITE_SPACE (' ')\n" +
        "include ('include')\n" +
        "WHITE_SPACE (' ')\n" +
        "string ('path.i')");
  }

  public void testIncludeDirectiveSlashPath() {
    doTest(" include path/file.i",
      "WHITE_SPACE (' ')\n" +
        "include ('include')\n" +
        "WHITE_SPACE (' ')\n" +
        "string ('path/file.i')");
  }

  public void testIncludeDirectiveWithSingleQuotes() {
    doTest(" include 'path'",
      "WHITE_SPACE (' ')\n" +
        "include ('include')\n" +
        "WHITE_SPACE (' ')\n" +
        "string (''path'')");
  }

  public void testIncludeDirectiveWithDoubleQuotes() {
    doTest(" include \"path\"",
      "WHITE_SPACE (' ')\n" +
        "include ('include')\n" +
        "WHITE_SPACE (' ')\n" +
        "string ('\"path\"')");
  }

  public void testDcDirective() {
    doTest(" dc.b 42",
      "WHITE_SPACE (' ')\n" +
        "dc ('dc')\n" +
        ".b ('.b')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')");
  }

  public void testDcbDirective() {
    doTest(" dcb 42",
      "WHITE_SPACE (' ')\n" +
        "dcb ('dcb')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')");
  }

  public void testDsDirective() {
    doTest(" ds 42",
      "WHITE_SPACE (' ')\n" +
        "ds ('ds')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')");
  }

  public void testRssetDirective() {
    doTest(" rsset 0",
      "WHITE_SPACE (' ')\n" +
        "rsset ('rsset')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('0')");
  }

  public void testRsresetDirective() {
    doTest(" rsreset",
      "WHITE_SPACE (' ')\n" +
        "rsreset ('rsreset')");
  }

  public void testRsDirective() {
    doTest("label rs 42",
      "id ('label')\n" +
        "WHITE_SPACE (' ')\n" +
        "rs ('rs')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')");
  }

  public void testBlkDirective() {
    doTest(" blk 42,666",
      "WHITE_SPACE (' ')\n" +
        "blk ('blk')\n" +
        "WHITE_SPACE (' ')\n" +
        "dec_number ('42')\n" +
        ", (',')\n" +
        "dec_number ('666')");
  }

  public void testOptDirective() {
    doTest(" opt w+",
      "WHITE_SPACE (' ')\n" +
        "opt ('opt')\n" +
        "WHITE_SPACE (' ')\n" +
        "id ('w')\n" +
        "+ ('+')");
  }
}
