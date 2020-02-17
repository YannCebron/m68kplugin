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

  public void testCmpInstructionDataRegister() throws IOException {
    doCodeTest(" cmp d0,d1");
  }

  public void testCmpInstructionLabel() throws IOException {
    doCodeTest(" cmp label,d1");
  }

  public void testCmpInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" cmp.b d0,d1");
  }

  public void testCmpaInstructionMissingDestination() throws IOException {
    doCodeTest(" cmpa d0,");
  }

  public void testCmpaInstructionDataRegister() throws IOException {
    doCodeTest(" cmpa d0,a0");
  }

  public void testCmpaInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" cmpa.l d0,a0");
  }

  public void testCmpiInstructionMissingSource() throws IOException {
    doCodeTest(" cmpi ");
  }

  public void testCmpiInstructionDataRegister() throws IOException {
    doCodeTest(" cmpi #1,d0");
  }

  public void testCmpiInstructionAddressRegister() throws IOException {
    doCodeTest(" cmpi #1,a0");
  }

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
