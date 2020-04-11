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

  public void testAbcdInstructionDrdDrd() throws IOException {
    doCodeTest(" abcd d0,d1");
  }

  public void testAbcdInstructionDrdMissingDestination() throws IOException {
    doCodeTest(" abcd d0,");
  }

  public void testAbcdInstructionApdApd() throws IOException {
    doCodeTest(" abcd -(a0),-(a1)");
  }

  public void testAbcdInstructionApdMissingDestination() throws IOException {
    doCodeTest(" abcd -(a0),");
  }

  public void testNbcdInstructionDrd() throws IOException {
    doCodeTest(" nbcd d0");
  }

  public void testNbcdInstructionAbs() throws IOException {
    doCodeTest(" nbcd $50000");
  }

  public void testNbcdInstructionDataSizeDrd() throws IOException {
    doCodeTest(" nbcd.b d0");
  }

  public void testNbcdInstructionArd() throws IOException {
    doCodeTest(" nbcd a0");
  }

  public void testSbcdInstructionDrdDrd() throws IOException {
    doCodeTest(" sbcd d0,d1");
  }

  public void testSbcdInstructionApdApd() throws IOException {
    doCodeTest(" sbcd -(a0),-(a1)");
  }

}
