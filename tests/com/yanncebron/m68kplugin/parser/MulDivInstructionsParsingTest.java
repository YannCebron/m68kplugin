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

public class MulDivInstructionsParsingTest extends M68kParsingTestCase {

  public MulDivInstructionsParsingTest() {
    super("mulDivInstructions");
  }

  public void testMulsInstructionMissingSource() throws IOException {
    doCodeTest(" muls ");
  }

  public void testMulsInstructionMissingDestination() throws IOException {
    doCodeTest(" muls d0,");
  }

  public void testMulsInstructionDataRegister() throws IOException {
    doCodeTest(" muls d0,d1");
  }

  public void testMulsInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" muls.w d0,d1");
  }

  public void testMulsInstructionLabel() throws IOException {
    doCodeTest(" muls label,d1");
  }

  public void testMulsInstructionImmediateData() throws IOException {
    doCodeTest(" muls #42,d1");
  }

  public void testMuluInstructionMissingSource() throws IOException {
    doCodeTest(" mulu ");
  }

  public void testMuluInstructionMissingDestination() throws IOException {
    doCodeTest(" mulu d0,");
  }

  public void testMuluInstructionDataRegister() throws IOException {
    doCodeTest(" mulu d0,d1");
  }

  public void testMuluInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" mulu.w d0,d1");
  }

  public void testMuluInstructionLabel() throws IOException {
    doCodeTest(" mulu label,d1");
  }

  public void testMuluInstructionImmediateData() throws IOException {
    doCodeTest(" mulu #42,d1");
  }

  public void testDivsInstructionMissingSource() throws IOException {
    doCodeTest(" divs ");
  }

  public void testDivsInstructionMissingDestination() throws IOException {
    doCodeTest(" divs d0,");
  }

  public void testDivsInstructionDataRegister() throws IOException {
    doCodeTest(" divs d0,d1");
  }

  public void testDivsInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" divs.w d0,d1");
  }

  public void testDivsInstructionLabel() throws IOException {
    doCodeTest(" divs label,d1");
  }

  public void testDivsInstructionImmediateData() throws IOException {
    doCodeTest(" divs #42,d1");
  }

  public void testDivuInstructionMissingSource() throws IOException {
    doCodeTest(" divu ");
  }

  public void testDivuInstructionMissingDestination() throws IOException {
    doCodeTest(" divu d0,");
  }

  public void testDivuInstructionDataRegister() throws IOException {
    doCodeTest(" divu d0,d1");
  }

  public void testDivuInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" divu.w d0,d1");
  }

  public void testDivuInstructionLabel() throws IOException {
    doCodeTest(" divu label,d1");
  }

  public void testDivuInstructionImmediateData() throws IOException {
    doCodeTest(" divu #42,d1");
  }

}