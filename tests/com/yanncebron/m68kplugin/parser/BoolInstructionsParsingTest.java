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

public class BoolInstructionsParsingTest extends M68kParsingTestCase {

  public BoolInstructionsParsingTest() {
    super("boolInstructions");
  }

  public void testAndInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" and d0,d1");
  }

  public void testAndInstructionDataSizeDataRegisterAddressRegister() throws IOException {
    doCodeTest(" and.l d0,a1");
  }

  public void testAndiInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" andi d0,d1");
  }

  public void testAndiInstructionImmediateDataRegister() throws IOException {
    doCodeTest(" andi #0,d1");
  }

  public void testOrInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" or d0,d1");
  }

  public void testOrInstructionDataSizeDataRegisterAddressRegister() throws IOException {
    doCodeTest(" or.l d0,a1");
  }

  public void testOriInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" ori d0,d1");
  }

  public void testOriInstructionImmediateDataRegister() throws IOException {
    doCodeTest(" ori #0,d1");
  }

  public void testEorInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" eor d0,d1");
  }

  public void testEorInstructionDataSizeDataRegisterAddressRegister() throws IOException {
    doCodeTest(" eor.l d0,a1");
  }

  public void testEoriInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" eori d0,d1");
  }

  public void testEoriInstructionImmediateDataRegister() throws IOException {
    doCodeTest(" eori #0,d1");
  }

  public void testNotInstructionDataRegister() throws IOException {
    doCodeTest(" not d0");
  }

  public void testNotInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" not.b d0");
  }

  public void testNotInstructionLabel() throws IOException {
    doCodeTest(" not label");
  }

}
