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

package com.yanncebron.m68kplugin.parser;

import com.intellij.testFramework.TestDataPath;

import java.io.IOException;

@TestDataPath("$PROJECT_ROOT/testData/parser/macro")
public class MacroParsingTest extends M68kParsingTestCase {

  public MacroParsingTest() {
    super("macro");
  }

  public void testEmptyMacro() throws IOException {
    doCodeTest("macroName MACRO\n" +
      " ENDM");
  }

  public void testMacroParameter() throws Exception {
    doCodeTest("macroName MACRO\n" +
      " \\1\n" +
      " ENDM");
  }

  public void testMacroParameterAlpha() throws Exception {
    doCodeTest("macroName MACRO\n" +
      " \\a\n" +
      " ENDM");
  }

  public void testMacroParameterMissingIndex() throws Exception {
    doCodeTest("macroName MACRO\n" +
      " \\\n" +
      " ENDM");
  }

  // inserts fake '.w' token
  public void testMacroParameterAsDataSize() throws Exception {
    doCodeTest("macroName MACRO\n" +
      " move.\\0 d0,d1\n" +
      " ENDM");
  }

}
