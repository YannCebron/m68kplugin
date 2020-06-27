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

@TestDataPath("$PROJECT_ROOT/testData/parser/boolInstructions")
public class BoolInstructionsParsingTest extends M68kParsingTestCase {

  public BoolInstructionsParsingTest() {
    super("boolInstructions");
  }

  public void testAndInstructionDrdDrd() throws IOException {
    doCodeTest(" and d0,d1");
  }

  public void testAndInstructionDataSizeDrdAix() throws IOException {
    doCodeTest(" and.l d0,42(a1,d0)");
  }

  public void testAndInstructionImmAbs() throws Exception {
    doCodeTest(" and.w #$8100,label");
  }

  public void testAndiInstructionMissingSource() throws IOException {
    doCodeTest(" andi");
  }

  public void testAndiInstructionDrd() throws IOException {
    doCodeTest(" andi #0,d1");
  }

  public void testAndiInstructionCcr() throws IOException {
    doCodeTest(" andi #0,ccr");
  }

  public void testAndiInstructionDataSizeCcr() throws IOException {
    doCodeTest(" andi.b #0,ccr");
  }

  public void testAndiInstructionDataSizeWrongCcr() throws IOException {
    doCodeTest(" andi.l #0,ccr");
  }

  public void testAndiInstructionSr() throws IOException {
    doCodeTest(" andi #0,sr");
  }

  public void testAndiInstructionDataSizeSr() throws IOException {
    doCodeTest(" andi.w #0,sr");
  }

  public void testAndiInstructionDataSizeWrongSr() throws IOException {
    doCodeTest(" andi.b #0,sr");
  }

  public void testOrInstructionDrdDrd() throws IOException {
    doCodeTest(" or d0,d1");
  }

  public void testOrInstructionDataSizeDrdAbs() throws IOException {
    doCodeTest(" or.l d0,$50000");
  }

  public void testOriInstructionDrd() throws IOException {
    doCodeTest(" ori #0,d1");
  }

  public void testEorInstructionDrdDrd() throws IOException {
    doCodeTest(" eor d0,d1");
  }

  public void testEorInstructionDataSizeDrdAdi() throws IOException {
    doCodeTest(" eor.l d0,42(a0)");
  }

  public void testEoriInstructionDrd() throws IOException {
    doCodeTest(" eori #0,d1");
  }

  public void testNotInstructionDrd() throws IOException {
    doCodeTest(" not d0");
  }

  public void testNotInstructionDataSizeDrd() throws IOException {
    doCodeTest(" not.b d0");
  }

  public void testNotInstructionAbs() throws IOException {
    doCodeTest(" not label");
  }

}
