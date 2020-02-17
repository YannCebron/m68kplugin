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

@TestDataPath("$PROJECT_ROOT/testData/parser/bcdInstructions")
public class BcdInstructionsParsingTest extends M68kParsingTestCase {

  public BcdInstructionsParsingTest() {
    super("bcdInstructions");
  }

  public void testAbcdInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" abcd d0,d1");
  }

  public void testAbcdInstructionDataRegisterMissingDestination() throws IOException {
    doCodeTest(" abcd d0,");
  }

  public void testAbcdInstructionAddressRegisterAddressRegister() throws IOException {
    doCodeTest(" abcd -(a0),-(a1)");
  }

  public void testAbcdInstructionAddressRegisterMissingDestination() throws IOException {
    doCodeTest(" abcd -(a0),");
  }

  public void testNbcdInstructionDataRegister() throws IOException {
    doCodeTest(" nbcd d0");
  }

  public void testNbcdInstructionDataSizeDataRegister() throws IOException {
    doCodeTest(" nbcd.b d0");
  }

  public void testNbcdInstructionAddressRegister() throws IOException {
    doCodeTest(" nbcd a0");
  }

  public void testSbcdInstructionDataRegisterDataRegister() throws IOException {
    doCodeTest(" sbcd d0,d1");
  }

  public void testSbcdInstructionAddressRegisterAddressRegister() throws IOException {
    doCodeTest(" sbcd -(a0),-(a1)");
  }

}
