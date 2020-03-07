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

@TestDataPath("$PROJECT_ROOT/testData/parser/bccInstructions")
public class BccInstructionsParsingTest extends M68kParsingTestCase {

  public BccInstructionsParsingTest() {
    super("bccInstructions");
  }

  public void testBraInstructionMissingLabel() throws IOException {
    doCodeTest(" bra ");
  }

  public void testBraInstruction() throws IOException {
    doCodeTest(" bra label");
  }

  public void testBraDataSizeShortInstruction() throws IOException {
    doCodeTest(" bra.s label");
  }

  public void testBraDataSizeLongInstruction() throws IOException {
    doCodeTest(" bra.l label");
  }

  public void testBcsInstruction() throws IOException {
    doCodeTest(" bcs label");
  }

  public void testBloInstruction() throws IOException {
    doCodeTest(" blo label");
  }

  public void testBlsInstruction() throws IOException {
    doCodeTest(" bls label");
  }

  public void testBeqInstruction() throws IOException {
    doCodeTest(" beq label");
  }

  public void testBneInstruction() throws IOException {
    doCodeTest(" bne label");
  }

  public void testBhiInstruction() throws IOException {
    doCodeTest(" bhi label");
  }

  public void testBccInstruction() throws IOException {
    doCodeTest(" bcc label");
  }

  public void testBhsInstruction() throws IOException {
    doCodeTest(" bhs label");
  }

  public void testBplInstruction() throws IOException {
    doCodeTest(" bpl label");
  }

  public void testBvcInstruction() throws IOException {
    doCodeTest(" bvc label");
  }

  public void testBltInstruction() throws IOException {
    doCodeTest(" blt label");
  }

  public void testBleInstruction() throws IOException {
    doCodeTest(" ble label");
  }

  public void testBgtInstruction() throws IOException {
    doCodeTest(" bgt label");
  }

  public void testBgeInstruction() throws IOException {
    doCodeTest(" bge label");
  }

  public void testBmiInstruction() throws IOException {
    doCodeTest(" bmi label");
  }

  public void testBvsInstruction() throws IOException {
    doCodeTest(" bvs label");
  }

}
