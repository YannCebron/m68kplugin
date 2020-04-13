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

@TestDataPath("$PROJECT_ROOT/testData/parser/addSubInstructions")
public class AddSubInstructionsParsingTest extends M68kParsingTestCase {

  public AddSubInstructionsParsingTest() {
    super("addSubInstructions");
  }

  public void testAddInstructionMissingSource() throws IOException {
    doCodeTest(" add ");
  }

  public void testAddInstructionMissingDestination() throws IOException {
    doCodeTest(" add d0,");
  }

  public void testAddInstructionDrdDrd() throws IOException {
    doCodeTest(" add d0,d1");
  }

  public void testAddInstructionDataSizeDrdDrd() throws IOException {
    doCodeTest(" add.b d0,d1");
  }

  public void testAddInstructionDrdAbs() throws IOException {
    doCodeTest(" add d0,label");
  }

  public void testAddInstructionAbsDrd() throws IOException {
    doCodeTest(" add label,d1");
  }

  public void testAddInstructionAbsArd() throws IOException {
    doCodeTest(" add label,a0");
  }

  public void testAddInstructionImmArd() throws IOException {
    doCodeTest(" add #1,a0");
  }

  public void testAddInstructionArdDrd() throws IOException {
    doCodeTest(" add a0,d1");
  }

  public void testAddInstructionImmDrd() throws IOException {
    doCodeTest(" add #2,d1");
  }

  public void testAddInstructionImmAri() throws IOException {
    doCodeTest(" add.w #$4000,(a3)");
  }

  public void testAddaInstructionDrd() throws IOException {
    doCodeTest(" adda d0,a0");
  }

  public void testAddaInstructionDataSizeDrd() throws IOException {
    doCodeTest(" adda.l d0,a0");
  }

  public void testAddaInstructionArd() throws IOException {
    doCodeTest(" adda a1,a0");
  }

  public void testAddiInstructionDrd() throws IOException {
    doCodeTest(" addi #1,d0");
  }

  public void testAddiInstructionWrongArd() throws IOException {
    doCodeTest(" addi #1,a0");
  }

  public void testAddqInstructionDrd() throws IOException {
    doCodeTest(" addq #1,d0");
  }

  public void testAddqInstructionDataSizeDrd() throws IOException {
    doCodeTest(" addq #1.w,d0");
  }

  public void testAddqInstructionArd() throws IOException {
    doCodeTest(" addq #1,a0");
  }

  public void testAddqInstructionAri() throws IOException {
    doCodeTest(" addq #1,(a0)");
  }

  public void testAddqInstructionApi() throws IOException {
    doCodeTest(" addq #1,(a0)+");
  }

  public void testAddqInstructionApd() throws IOException {
    doCodeTest(" addq #1,-(a0)");
  }

  public void testAddqInstructionAdi() throws IOException {
    doCodeTest(" addq #1,42(a0)");
  }

  public void testAddqInstructionAix() throws IOException {
    doCodeTest(" addq #1,42(a0,d0)");
  }

  public void testAddqInstructionAbs() throws IOException {
    doCodeTest(" addq #1,42");
  }

  public void testAddqInstructionAbsDataSize() throws IOException {
    doCodeTest(" addq #1,42.w");
  }

  public void testAddqInstructionAbsExpression() throws IOException {
    doCodeTest(" addq #1,42+666");
  }

  public void testAddxInstructionDrdDrd() throws IOException {
    doCodeTest(" addx d0,d1");
  }

  public void testAddxInstructionApdApd() throws IOException {
    doCodeTest(" addx -(a0),-(a1)");
  }


  public void testSubInstructionMissingSource() throws IOException {
    doCodeTest(" sub ");
  }

  public void testSubInstructionMissingDestination() throws IOException {
    doCodeTest(" sub d0,");
  }

  public void testSubInstructionDrdDrd() throws IOException {
    doCodeTest(" sub d0,d1");
  }

  public void testSubInstructionDataSizeDrdDrd() throws IOException {
    doCodeTest(" sub.b d0,d1");
  }

  public void testSubInstructionDrdAbs() throws IOException {
    doCodeTest(" sub d0,label");
  }

  public void testSubInstructionAbsDrd() throws IOException {
    doCodeTest(" sub label,d1");
  }

  public void testSubInstructionArdDrd() throws IOException {
    doCodeTest(" sub a0,d1");
  }

  public void testSubInstructionImmDrd() throws IOException {
    doCodeTest(" sub #2,d1");
  }

  public void testSubaInstructionDrd() throws IOException {
    doCodeTest(" suba d0,a0");
  }

  public void testSubaInstructionDataSizeDrd() throws IOException {
    doCodeTest(" suba.l d0,a0");
  }

  public void testSubaInstructionArd() throws IOException {
    doCodeTest(" suba a1,a0");
  }

  public void testSubaInstructionPcd() throws IOException {
    doCodeTest(" suba 42(pc),a0");
  }

  public void testSubaInstructionPci() throws IOException {
    doCodeTest(" suba 42(pc,d0),a0");
  }

  public void testSubaInstructionPciMissingRn() throws IOException {
    doCodeTest(" suba 42(pc,),a0");
  }

  public void testSubiInstructionDrd() throws IOException {
    doCodeTest(" subi #1,d0");
  }

  public void testSubiInstructionWrongArd() throws IOException {
    doCodeTest(" subi #1,a0");
  }

  public void testSubqInstructionDrd() throws IOException {
    doCodeTest(" subq #1,d0");
  }

  public void testSubxInstructionDrdDrd() throws IOException {
    doCodeTest(" subx d0,d1");
  }

  public void testSubxInstructionApdApd() throws IOException {
    doCodeTest(" subx -(a0),-(a1)");
  }

}