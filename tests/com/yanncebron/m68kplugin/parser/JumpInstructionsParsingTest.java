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

@TestDataPath("$PROJECT_ROOT/testData/parser/jumpInstructions")
public class JumpInstructionsParsingTest extends M68kParsingTestCase {

  public JumpInstructionsParsingTest() {
    super("jumpInstructions");
  }

  public void testBsrInstruction() throws Exception {
    doCodeTest(" bsr label");
  }

  public void testBsrInstructionExpression() throws Exception {
    doCodeTest(" bsr label+label2");
  }

  public void testBsrInstructionMissingDestination() throws Exception {
    doCodeTest(" bsr ");
  }

  public void testBsrInstructionDataSizeShort() throws Exception {
    doCodeTest(" bsr.s offset");
  }

  public void testBsrInstructionDataSizeWord() throws Exception {
    doCodeTest(" bsr.w offset");
  }

  public void testBsrInstructionWrongDataSizeLong() throws Exception {
    doCodeTest(" bsr.l ");
  }

  public void testJmpInstructionAbs() throws Exception {
    doCodeTest(" jmp label");
  }

  public void testJmpInstructionMissingDestination() throws Exception {
    doCodeTest(" jmp ");
  }

  public void testJmpInstructionAixWithoutIndexExpression() throws Exception {
    doCodeTest(" jmp (pc,d0)");
  }

  public void testJsrInstructionAbs() throws Exception {
    doCodeTest(" jsr label");
  }

  public void testJsrInstructionAdi() throws Exception {
    doCodeTest(" jsr label(a0)");
  }

  public void testJsrInstructionAix() throws Exception {
    doCodeTest(" jsr 42(a0,d0)");
  }

  public void testJsrInstructionAixMissingRn() throws Exception {
    doCodeTest(" jsr 42(a0,)");
  }

  public void testJsrInstructionMissingDestination() throws Exception {
    doCodeTest(" jsr ");
  }

  public void testRtsInstruction() throws Exception {
    doCodeTest(" rts");
  }

  public void testRteInstruction() throws Exception {
    doCodeTest(" rte");
  }

  public void testRtrInstruction() throws Exception {
    doCodeTest(" rtr");
  }

}
