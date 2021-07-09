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

@TestDataPath("$PROJECT_ROOT/testData/parser/shiftInstructions")
public class ShiftInstructionsParsingTest extends M68kParsingTestCase {

  public ShiftInstructionsParsingTest() {
    super("shiftInstructions");
  }

  public void testAslInstructionAri() throws Exception {
    doCodeTest(" asl (a0)");
  }

  public void testAslInstructionAbs() throws Exception {
    doCodeTest(" asl $40000");
  }

  public void testAslInstructionImmDrd() throws Exception {
    doCodeTest(" asl #1,d0");
  }

  public void testAslInstructionDataSizeImmDrd() throws Exception {
    doCodeTest(" asl.b #1,d0");
  }

  public void testAslInstructionDrdDrd() throws Exception {
    doCodeTest(" asl d1,d0");
  }

  public void testAslInstructionDrdSingle() throws Exception {
    doCodeTest(" asl d1");
  }

  public void testAslInstructionMissingSource() throws Exception {
    doCodeTest(" asl ");
  }

  public void testAslInstructionMissingDestination() throws Exception {
    doCodeTest(" asl #1,");
  }

  public void testAsrInstructionImmDrd() throws Exception {
    doCodeTest(" asr.w #1,d0");
  }

  public void testLslInstructionImmDrd() throws Exception {
    doCodeTest(" lsl.l #1,d0");
  }

  public void testLsrInstructionImmDrd() throws Exception {
    doCodeTest(" lsr #1,d0");
  }

  public void testRolInstructionImmDrd() throws Exception {
    doCodeTest(" rol #1,d0");
  }

  public void testRolInstructionDrdSingle() throws Exception {
    doCodeTest(" rol.b d1");
  }

  public void testRorInstructionImmDrd() throws Exception {
    doCodeTest(" ror #1,d0");
  }

  public void testRoxlInstructionImmDrd() throws Exception {
    doCodeTest(" roxl #1,d0");
  }

  public void testRoxrInstructionImmDrd() throws Exception {
    doCodeTest(" roxr #1,d0");
  }

}
