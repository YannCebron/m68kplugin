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

@TestDataPath("$PROJECT_ROOT/testData/parser/moveInstructions/movea")
public class MoveaInstructionParsingTest extends M68kParsingTestCase {

  public MoveaInstructionParsingTest() {
    super("moveInstructions/movea");
  }

  public void testMoveaInstructionMissingSource() throws Exception {
    doCodeTest(" movea ");
  }

  public void testMoveaInstructionMissingDestination() throws Exception {
    doCodeTest(" movea a0,");
  }

  public void testMoveaInstructionArd() throws Exception {
    doCodeTest(" movea a0,a1");
  }

  public void testMoveaInstructionDataSizeWordArd() throws Exception {
    doCodeTest(" movea.w a0,a1");
  }

  public void testMoveaInstructionDataSizeLongImm() throws Exception {
    doCodeTest(" movea.l #1,a1");
  }

  public void testMoveaInstructionAbs() throws Exception {
    doCodeTest(" movea src,a1");
  }

}