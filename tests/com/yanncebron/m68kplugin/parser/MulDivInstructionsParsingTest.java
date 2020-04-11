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

@TestDataPath("$PROJECT_ROOT/testData/parser/mulDivInstructions")
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

  public void testMulsInstructionDrd() throws IOException {
    doCodeTest(" muls d0,d1");
  }

  public void testMulsInstructionDataSizeDrd() throws IOException {
    doCodeTest(" muls.w d0,d1");
  }

  public void testMulsInstructionAbs() throws IOException {
    doCodeTest(" muls label,d1");
  }

  public void testMulsInstructionImm() throws IOException {
    doCodeTest(" muls #42,d1");
  }

  public void testMuluInstructionMissingSource() throws IOException {
    doCodeTest(" mulu ");
  }

  public void testMuluInstructionMissingDestination() throws IOException {
    doCodeTest(" mulu d0,");
  }

  public void testMuluInstructionDrd() throws IOException {
    doCodeTest(" mulu d0,d1");
  }

  public void testMuluInstructionDataSizeDrd() throws IOException {
    doCodeTest(" mulu.w d0,d1");
  }

  public void testMuluInstructionAbs() throws IOException {
    doCodeTest(" mulu label,d1");
  }

  public void testMuluInstructionImm() throws IOException {
    doCodeTest(" mulu #42,d1");
  }

  public void testDivsInstructionMissingSource() throws IOException {
    doCodeTest(" divs ");
  }

  public void testDivsInstructionMissingDestination() throws IOException {
    doCodeTest(" divs d0,");
  }

  public void testDivsInstructionDrd() throws IOException {
    doCodeTest(" divs d0,d1");
  }

  public void testDivsInstructionDataSizeDrd() throws IOException {
    doCodeTest(" divs.w d0,d1");
  }

  public void testDivsInstructionAbs() throws IOException {
    doCodeTest(" divs label,d1");
  }

  public void testDivsInstructionImm() throws IOException {
    doCodeTest(" divs #42,d1");
  }

  public void testDivuInstructionMissingSource() throws IOException {
    doCodeTest(" divu ");
  }

  public void testDivuInstructionMissingDestination() throws IOException {
    doCodeTest(" divu d0,");
  }

  public void testDivuInstructionDrd() throws IOException {
    doCodeTest(" divu d0,d1");
  }

  public void testDivuInstructionDataSizeDrd() throws IOException {
    doCodeTest(" divu.w d0,d1");
  }

  public void testDivuInstructionAbs() throws IOException {
    doCodeTest(" divu label,d1");
  }

  public void testDivuInstructionImm() throws IOException {
    doCodeTest(" divu #42,d1");
  }

  public void testDivuInstructionPcd() throws IOException {
    doCodeTest(" divu 42(pc),d1");
  }

}