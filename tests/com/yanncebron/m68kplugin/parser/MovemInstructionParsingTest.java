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

@TestDataPath("$PROJECT_ROOT/testData/parser/moveInstructions/movem")
public class MovemInstructionParsingTest extends M68kParsingTestCase {

  public MovemInstructionParsingTest() {
    super("moveInstructions/movem");
  }

  public void testMovemInstructionMissingSource() throws Exception {
    doCodeTest(" movem ");
  }

  public void testMovemInstructionDestinationMissing() throws Exception {
    doCodeTest(" movem d1,");
  }

  public void testMovemInstructionImmediate() throws Exception {
    doCodeTest(" movem #123,(a7)");
  }

  public void testMovemInstructionSingleRegister() throws Exception {
    doCodeTest(" movem d1,(a7)");
  }

  public void testMovemInstructionDataSizeWordSingleRegister() throws Exception {
    doCodeTest(" movem.w d1,(a7)");
  }

  public void testMovemInstructionDataSizeLongSingleRegister() throws Exception {
    doCodeTest(" movem.l d1,(a7)");
  }

  public void testMovemInstructionRegisterRange() throws Exception {
    doCodeTest(" movem d1-d3,(a7)");
  }

  public void testMovemInstructionRegisterRangeMissingTo() throws Exception {
    doCodeTest(" movem d1-,(a7)");
  }

  public void testMovemInstructionRegisterRangeMissingNextRange() throws Exception {
    doCodeTest(" movem d1/,(a7)");
  }

  public void testMovemInstructionMultipleRegisterRange() throws Exception {
    doCodeTest(" movem d1-d3/a0-a2,(a7)");
  }

  public void testMovemInstructionMultipleRegisters() throws Exception {
    doCodeTest(" movem d1/d2/a0,(a7)");
  }

  public void testMovemInstructionDestinationSingleRegister() throws Exception {
    doCodeTest(" movem (a7)+,d1");
  }

  public void testMovemInstructionDestinationRegisterRange() throws Exception {
    doCodeTest(" movem 42(a7),d1-d3");
  }

  public void testMovemInstructionDestinationMultipleRegisters() throws Exception {
    doCodeTest(" movem 42(a7,d0),d1/d2/a0");
  }

}