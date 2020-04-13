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

@TestDataPath("$PROJECT_ROOT/testData/parser/moveInstructions")
public class MoveInstructionParsingTest extends M68kParsingTestCase {

  public MoveInstructionParsingTest() {
    super("moveInstructions");
  }

  public void testMoveInstructionMissingSource() throws Exception {
    doCodeTest(" move ");
  }

  public void testMoveInstructionMissingCommaDestination() throws Exception {
    doCodeTest(" move #1");
  }

  public void testMoveInstructionMissingDestination() throws Exception {
    doCodeTest(" move #1,");
  }

  public void testMoveInstructionDrdDrd() throws Exception {
    doCodeTest(" move d0,d1");
  }

  public void testMoveInstructionDataSizeByteDrdDrd() throws Exception {
    doCodeTest(" move.b d0,d1");
  }

  public void testMoveInstructionDataSizeWordDrdDrd() throws Exception {
    doCodeTest(" move.w d0,d1");
  }

  public void testMoveInstructionDataSizeLongDrdDrd() throws Exception {
    doCodeTest(" move.l d0,d1");
  }

  public void testMoveInstructionImmDrd() throws Exception {
    doCodeTest(" move #1,d1");
  }

  public void testMoveInstructionDrdAbs() throws Exception {
    doCodeTest(" move d0,dest");
  }

  public void testMoveInstructionArdDrd() throws Exception {
    doCodeTest(" move a0,d0");
  }

  public void testMoveInstructionArdArd() throws Exception {
    doCodeTest(" move a0,a1");
  }

  public void testMoveInstructionAbsArd() throws Exception {
    doCodeTest(" move.l 4,a6");
  }

}