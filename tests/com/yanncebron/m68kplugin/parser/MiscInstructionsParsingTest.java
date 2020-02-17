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

public class MiscInstructionsParsingTest extends M68kParsingTestCase {

  public MiscInstructionsParsingTest() {
    super("miscInstructions");
  }

  public void testNopInstruction() throws Exception {
    doCodeTest(" nop");
  }

  public void testIllegalInstruction() throws Exception {
    doCodeTest(" illegal");
  }

  public void testResetInstruction() throws Exception {
    doCodeTest(" reset");
  }

  public void testTrapInstruction() throws Exception {
    doCodeTest(" trap #1");
  }

  public void testTrapInstructionMissingVector() throws Exception {
    doCodeTest(" trap ");
  }

  public void testTrapvInstruction() throws Exception {
    doCodeTest(" trapv");
  }

  public void testLinkInstruction() throws Exception {
    doCodeTest(" link a0,#1");
  }

  public void testLinkInstructionMissingSource() throws Exception {
    doCodeTest(" link ");
  }

  public void testLinkInstructionMissingDestination() throws Exception {
    doCodeTest(" link a0,");
  }

  public void testUnlkInstruction() throws Exception {
    doCodeTest(" unlk a0");
  }

  public void testUnlkInstructionMissingSource() throws Exception {
    doCodeTest(" unlk ");
  }

  public void testLeaInstructionPcIndirectAddressRegister() throws Exception {
    doCodeTest(" lea id(pc),a0");
  }

  public void testLeaInstructionLabelLabel() throws Exception {
    doCodeTest(" lea src,dest");
  }

  public void testLeaInstructionExpressionLabel() throws Exception {
    doCodeTest(" lea $4000+42,dest");
  }

  public void testLeaInstructionDataSizeLabelLabel() throws Exception {
    doCodeTest(" lea.l src,dest");
  }

  public void testLeaInstructionMissingSource() throws Exception {
    doCodeTest(" lea ");
  }

  public void testLeaInstructionMissingDestination() throws Exception {
    doCodeTest(" lea src,");
  }

  public void testPeaInstruction() throws Exception {
    doCodeTest(" pea a0");
  }

  public void testPeaInstructionDataSize() throws Exception {
    doCodeTest(" pea.l a0");
  }

  public void testPeaInstructionMissingSource() throws Exception {
    doCodeTest(" pea ");
  }

  public void testClrInstruction() throws Exception {
    doCodeTest(" clr label");
  }

  public void testClrInstructionDataSize() throws Exception {
    doCodeTest(" clr.l label");
  }

  public void testClrInstructionMissingDestination() throws Exception {
    doCodeTest(" clr ");
  }

  public void testTstInstruction() throws Exception {
    doCodeTest(" tst label");
  }

  public void testTstInstructionDataRegister() throws Exception {
    doCodeTest(" tst d0");
  }

  public void testTstInstructionDataSize() throws Exception {
    doCodeTest(" tst.l label");
  }

  public void testTasInstruction() throws Exception {
    doCodeTest(" tas d0");
  }

  public void testTasInstructionDataSize() throws Exception {
    doCodeTest(" tas (a0)");
  }

  public void testExtInstruction() throws Exception {
    doCodeTest(" ext d0");
  }

  public void testExtInstructionDataSize() throws Exception {
    doCodeTest(" ext.w d0");
  }

  public void testExgInstruction() throws Exception {
    doCodeTest(" exg d0,a0");
  }

  public void testExgInstructionDataSize() throws Exception {
    doCodeTest(" exg.l d0,a0");
  }

  public void testNegInstruction() throws Exception {
    doCodeTest(" neg d0");
  }

  public void testNegInstructionDataSize() throws Exception {
    doCodeTest(" neg.b d0");
  }

  public void testNegxInstruction() throws Exception {
    doCodeTest(" negx d0");
  }

  public void testNegxInstructionDataSize() throws Exception {
    doCodeTest(" negx.b d0");
  }

  public void testSwapInstruction() throws Exception {
    doCodeTest(" swap d0");
  }

  public void testSwapInstructionDataSize() throws Exception {
    doCodeTest(" swap.w d0");
  }

  public void testChkInstruction() throws Exception {
    doCodeTest(" chk a0,d0");
  }

  public void testChkInstructionDataSize() throws Exception {
    doCodeTest(" chk.w (a0),d0");
  }

}
