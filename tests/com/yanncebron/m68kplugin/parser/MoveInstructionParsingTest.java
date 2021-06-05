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
    doCodeTest(" mOvE #1,D1");
  }

  public void testMoveInstructionDrdAbs() throws Exception {
    doCodeTest(" move d0,dest");
  }

  public void testMoveInstructionArdDrd() throws Exception {
    doCodeTest(" move a0,d0");
  }

  public void testMoveInstructionArdArd() throws Exception {
    doCodeTest(" move a0,A1");
  }

  public void testMoveInstructionAbsArd() throws Exception {
    doCodeTest(" move.l 4,a6");
  }

  public void testMoveInstructionPciDataSizeDrd() throws Exception {
    doCodeTest(" move.b 42(PC,d6.W),d6");
  }

  public void testMoveInstructionPciLabelDataSizeDrd() throws Exception {
    doCodeTest(" move.w label(pc,d0.w),d0");
  }


  // USP ----------------------------------------------------------------
  public void testMoveInstructionUspArd() throws Exception {
    doCodeTest(" move usp,a0");
  }

  public void testMoveInstructionDataSizeUspArd() throws Exception {
    doCodeTest(" move.L usp,a0");
  }

  public void testMoveInstructionDataSizeWrongUspArd() throws Exception {
    doCodeTest(" move.b usp,a0");
  }

  public void testMoveInstructionArdUsp() throws Exception {
    doCodeTest(" move a0,usp");
  }

  public void testMoveInstructionDataSizeArdUsp() throws Exception {
    doCodeTest(" move.l a0,USP");
  }

  public void testMoveInstructionDataSizeWrongArdUsp() throws Exception {
    doCodeTest(" move.b a0,usp");
  }


  // CCR/SR -------------------------------------------------------------
  public void testMoveInstructionDrdCcr() throws Exception {
    doCodeTest(" move d0,ccr");
  }

  public void testMoveInstructionDataSizeDrdCcr() throws Exception {
    doCodeTest(" move.w d0,ccr");
  }

  public void testMoveInstructionDataSizeWrongDrdCcr() throws Exception {
    doCodeTest(" move.b d0,cCr");
  }

  public void testMoveInstructionAdiSr() throws Exception {
    doCodeTest(" move 2(a0),sr");
  }

  public void testMoveInstructionDataSizeAbsSr() throws Exception {
    doCodeTest(" move.w 42,SR");
  }

  public void testMoveInstructionSrAdi() throws Exception {
    doCodeTest(" move sr,2(a0)");
  }

  public void testMoveInstructionDataSizeSrAdi() throws Exception {
    doCodeTest(" move.w sr,2(a0)");
  }

  public void testMoveInstructionDataSizeWrongSrDrd() throws Exception {
    doCodeTest(" move.b sr,d0");
  }

}