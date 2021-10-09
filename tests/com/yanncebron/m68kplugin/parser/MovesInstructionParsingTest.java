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

@TestDataPath("$PROJECT_ROOT/testData/parser/moveInstructions/moves")
public class MovesInstructionParsingTest extends M68kParsingTestCase {

  public MovesInstructionParsingTest() {
    super("moveInstructions/moves");
  }

  public void testMovesInstructionMissingSource() throws Exception {
    doCodeTest(" moves ");
  }

  public void testMovesInstructionAdiMissingDestination() throws Exception {
    doCodeTest(" moves 42(a0),");
  }

  public void testMovesInstruction() throws Exception {
    doCodeTest(" moves (a0),d0");
  }

}