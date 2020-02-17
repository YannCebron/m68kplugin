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

public class SccInstructionsParsingTest extends M68kParsingTestCase {

  public SccInstructionsParsingTest() {
    super("sccInstructions");
  }

  public void testSeqInstructionMissingDestination() throws IOException {
    doCodeTest(" seq ");
  }

  public void testSeqInstructionDataRegister() throws IOException {
    doCodeTest(" seq d0");
  }

  public void testSeqInstructionLabel() throws IOException {
    doCodeTest(" seq label");
  }

  public void testSeqInstructionDataSizeByte() throws IOException {
    doCodeTest(" seq.b d0");
  }

  public void testScsInstruction() throws IOException {
    doCodeTest(" scs label");
  }

  public void testSlsInstruction() throws IOException {
    doCodeTest(" sls label");
  }

  public void testSneInstruction() throws IOException {
    doCodeTest(" sne label");
  }

  public void testShiInstruction() throws IOException {
    doCodeTest(" shi label");
  }

  public void testSccInstruction() throws IOException {
    doCodeTest(" scc label");
  }

  public void testSplInstruction() throws IOException {
    doCodeTest(" spl label");
  }

  public void testSvcInstruction() throws IOException {
    doCodeTest(" svc label");
  }

  public void testSltInstruction() throws IOException {
    doCodeTest(" slt label");
  }

  public void testSleInstruction() throws IOException {
    doCodeTest(" sle label");
  }

  public void testSgtInstruction() throws IOException {
    doCodeTest(" sgt label");
  }

  public void testSgeInstruction() throws IOException {
    doCodeTest(" sge label");
  }

  public void testSmiInstruction() throws IOException {
    doCodeTest(" smi label");
  }

  public void testSvsInstruction() throws IOException {
    doCodeTest(" svs label");
  }

  public void testStInstruction() throws IOException {
    doCodeTest(" st label");
  }

  public void testSfInstruction() throws IOException {
    doCodeTest(" sf label");
  }

}
