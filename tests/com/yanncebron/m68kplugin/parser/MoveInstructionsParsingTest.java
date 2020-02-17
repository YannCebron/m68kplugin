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

public class MoveInstructionsParsingTest extends M68kParsingTestCase {

  public MoveInstructionsParsingTest() {
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

  public void testMoveInstructionDataRegisterDataRegister() throws Exception {
    doCodeTest(" move d0,d1");
  }

  public void testMoveInstructionDataSizeByteDataRegisterDataRegister() throws Exception {
    doCodeTest(" move.b d0,d1");
  }
              
  public void testMoveInstructionDataSizeWordDataRegisterDataRegister() throws Exception {
    doCodeTest(" move.w d0,d1");
  }

  public void testMoveInstructionDataSizeLongDataRegisterDataRegister() throws Exception {
    doCodeTest(" move.w d0,d1");
  }

  public void testMoveInstructionImmediateDataRegister() throws Exception {
    doCodeTest(" move #1,d1");
  }

  public void testMoveInstructionDataRegisterEffectiveAddress() throws Exception {
    doCodeTest(" move d0,dest");
  }

  public void testMoveInstructionAddressRegisterAddressRegister() throws Exception {
    doCodeTest(" move a0,a1");
  }


  // movea -------------------------
  public void testMoveaInstructionMissingSource() throws Exception {
    doCodeTest(" movea ");
  }

  public void testMoveaInstructionMissingDestination() throws Exception {
    doCodeTest(" movea a0,");
  }

  public void testMoveaInstructionAddressRegister() throws Exception {
    doCodeTest(" movea a0,a1");
  }

  public void testMoveaInstructionAddressRegisterDataSizeWord() throws Exception {
    doCodeTest(" movea.w a0,a1");
  }

  public void testMoveaInstructionAddressRegisterDataSizeLong() throws Exception {
    doCodeTest(" movea.l a0,a1");
  }

  public void testMoveaInstructionEffectiveAddress() throws Exception {
    doCodeTest(" movea src,a1");
  }


  // moveq -------------------------
  public void testMoveqInstructionMissingSource() throws Exception {
    doCodeTest(" moveq ");
  }

  public void testMoveqInstructionMissingDestination() throws Exception {
    doCodeTest(" moveq #1,");
  }

  public void testMoveqInstruction() throws Exception {
    doCodeTest(" moveq #1,d1");
  }

  public void testMoveqInstructionDataSizeLong() throws Exception {
    doCodeTest(" moveq.l #1,d1");
  }


  // movem -------------------------
  public void testMovemInstructionMissingSource() throws Exception {
    doCodeTest(" movem ");
  }

  public void testMovemInstructionDestinationMissing() throws Exception {
    doCodeTest(" movem d1,");
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

  public void testMovemInstructionMultipleRegisterRange() throws Exception {
    doCodeTest(" movem d1-d3/a0-a2,(a7)");
  }

  public void testMovemInstructionMultipleRegisters() throws Exception {
    doCodeTest(" movem d1/d2/a0,(a7)");
  }

  public void testMovemInstructionDestinationSingleRegister() throws Exception {
    doCodeTest(" movem (a7),d1");
  }

  public void testMovemInstructionDestinationRegisterRange() throws Exception {
    doCodeTest(" movem (a7),d1-d3");
  }

  public void testMovemInstructionDestinationMultipleRegisters() throws Exception {
    doCodeTest(" movem (a7),d1/d2/a0");
  }

}