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

// todo dst=effective_address
@TestDataPath("$PROJECT_ROOT/testData/parser/shiftInstructions")
public class ShiftInstructionsParsingTest extends M68kParsingTestCase {

  public ShiftInstructionsParsingTest() {
    super("shiftInstructions");
  }

  public void testAslInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" asl #1,d0");
  }

  public void testAslInstructionDataSizeImmediateDataRegister() throws Exception {
    doCodeTest(" asl.b #1,d0");
  }

  public void testAslInstructionDataRegisterDataRegister() throws Exception {
    doCodeTest(" asl d1,d0");
  }

  public void testAslInstructionMissingSource() throws Exception {
    doCodeTest(" asl ");
  }

  public void testAslInstructionMissingDestination() throws Exception {
    doCodeTest(" asl #1,");
  }

  public void testAsrInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" asr.w #1,d0");
  }

  public void testLslInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" lsl.l #1,d0");
  }

  public void testLsrInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" lsr #1,d0");
  }

  public void testRolInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" rol #1,d0");
  }

  public void testRorInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" ror #1,d0");
  }

  public void testRoxlInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" roxl #1,d0");
  }

  public void testRoxrInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" roxr #1,d0");
  }

}
