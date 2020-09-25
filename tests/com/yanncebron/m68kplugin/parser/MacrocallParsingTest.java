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

package com.yanncebron.m68kplugin.parser;

import com.intellij.testFramework.TestDataPath;

import java.io.IOException;

@TestDataPath("$PROJECT_ROOT/testData/parser/macrocall")
public class MacrocallParsingTest extends M68kParsingTestCase {

  public MacrocallParsingTest() {
    super("macrocall");
  }

  public void testMacroCallNoArgs() throws IOException {
    doCodeTest(" MACRO_NAME");
  }

  public void testMacroCallSingleArg() throws IOException {
    doCodeTest(" MACRO_NAME arg");
  }

  public void testMacroCallMultipleArgs() throws IOException {
    doCodeTest(" MACRO_NAME d7,arg");
  }

  public void testMacroCallMultipleArgsMissingArg() throws IOException {
    doCodeTest(" MACRO_NAME d7,");
  }

}
