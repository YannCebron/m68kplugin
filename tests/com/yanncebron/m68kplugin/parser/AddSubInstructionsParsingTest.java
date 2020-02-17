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

import java.io.IOException;

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

  public void testAddInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" add d0,d1");
  }

  public void testAddInstructionDataSizeDataRegisterDataRegister() throws IOException {
    doCodeTest(" add.b d0,d1");
  }

  public void testAddInstructionDataRegisterLabel() throws IOException {
    doCodeTest(" add d0,label");
  }

  public void testAddInstructionLabelDataRegister() throws IOException {
    doCodeTest(" add label,d1");
  }

  public void testAddInstructionAddressRegisterDataRegister() throws IOException {
    doCodeTest(" add a0,d1");
  }

  public void testAddInstructionImmediateDataDataRegister() throws IOException {
    doCodeTest(" add #2,d1");
  }

  public void testAddaInstructionDataRegister() throws IOException {
    doCodeTest(" adda d0,a0");
  }

  public void testAddaInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" adda.l d0,a0");
  }

  public void testAddaInstructionAddressRegister() throws IOException {
    doCodeTest(" adda a1,a0");
  }

  public void testAddiInstructionDataRegister() throws IOException {
    doCodeTest(" addi #1,d0");
  }

  public void testAddiInstructionAddressRegister() throws IOException {
    doCodeTest(" addi #1,a0");
  }

  public void testAddqInstructionDataRegister() throws IOException {
    doCodeTest(" addq #1,d0");
  }

  public void testAddxInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" addx d0,d1");
  }

  public void testAddxInstructionPreDecrementAddressRegister() throws IOException {
    doCodeTest(" addx -(a0),-(a1)");
  }


  public void testSubInstructionMissingSource() throws IOException {
    doCodeTest(" sub ");
  }

  public void testSubInstructionMissingDestination() throws IOException {
    doCodeTest(" sub d0,");
  }

  public void testSubInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" sub d0,d1");
  }

  public void testSubInstructionDataSizeDataRegisterDataRegister() throws IOException {
    doCodeTest(" sub.b d0,d1");
  }

  public void testSubInstructionDataRegisterLabel() throws IOException {
    doCodeTest(" sub d0,label");
  }

  public void testSubInstructionLabelDataRegister() throws IOException {
    doCodeTest(" sub label,d1");
  }

  public void testSubInstructionAddressRegisterDataRegister() throws IOException {
    doCodeTest(" sub a0,d1");
  }

  public void testSubInstructionImmediateDataDataRegister() throws IOException {
    doCodeTest(" sub #2,d1");
  }

  public void testSubaInstructionDataRegister() throws IOException {
    doCodeTest(" suba d0,a0");
  }

  public void testSubaInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" suba.l d0,a0");
  }

  public void testSubaInstructionAddressRegister() throws IOException {
    doCodeTest(" suba a1,a0");
  }

  public void testSubiInstructionDataRegister() throws IOException {
    doCodeTest(" subi #1,d0");
  }

  public void testSubiInstructionAddressRegister() throws IOException {
    doCodeTest(" subi #1,a0");
  }

  public void testSubqInstructionDataRegister() throws IOException {
    doCodeTest(" subq #1,d0");
  }

  public void testSubxInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" subx d0,d1");
  }

  public void testSubxInstructionPreDecrementAddressRegister() throws IOException {
    doCodeTest(" subx -(a0),-(a1)");
  }

}