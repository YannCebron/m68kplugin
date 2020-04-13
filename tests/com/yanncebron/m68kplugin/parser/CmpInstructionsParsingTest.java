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

@TestDataPath("$PROJECT_ROOT/testData/parser/cmpInstructions")
public class CmpInstructionsParsingTest extends M68kParsingTestCase {

  public CmpInstructionsParsingTest() {
    super("cmpInstructions");
  }

  public void testCmpInstructionMissingSource() throws IOException {
    doCodeTest(" cmp ");
  }

  public void testCmpInstructionMissingDestination() throws IOException {
    doCodeTest(" cmp d0,");
  }

  public void testCmpInstructionDrd() throws IOException {
    doCodeTest(" cmp d0,d1");
  }

  public void testCmpInstructionAbs() throws IOException {
    doCodeTest(" cmp label,d1");
  }

  public void testCmpInstructionDataSizeDrd() throws IOException {
    doCodeTest(" cmp.b d0,d1");
  }


  // cmpa ==================================================================
  public void testCmpaInstructionMissingDestination() throws IOException {
    doCodeTest(" cmpa d0,");
  }

  public void testCmpaInstructionDrd() throws IOException {
    doCodeTest(" cmpa d0,a0");
  }

  public void testCmpaInstructionDataSizeDrd() throws IOException {
    doCodeTest(" cmpa.l d0,a0");
  }


  // cmpi ==================================================================
  public void testCmpiInstructionMissingSource() throws IOException {
    doCodeTest(" cmpi ");
  }

  public void testCmpiInstructionDrd() throws IOException {
    doCodeTest(" cmpi #1,d0");
  }

  public void testCmpiInstructionApd() throws IOException {
    doCodeTest(" cmpi #1,-(a0)");
  }


  // cmpm ==================================================================
  public void testCmpmInstructionMissingSource() throws IOException {
    doCodeTest(" cmpm ");
  }

  public void testCmpmInstructionMissingDestination() throws IOException {
    doCodeTest(" cmpm (a0)+,");
  }

  public void testCmpmInstruction() throws IOException {
    doCodeTest(" cmpm (a0)+,(a1)+");
  }

  public void testCmpmInstructionDataSize() throws IOException {
    doCodeTest(" cmpm.b (a0)+,(a1)+");
  }
}
