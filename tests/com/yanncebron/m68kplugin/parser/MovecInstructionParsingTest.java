/*
 * Copyright 2021 The Authors
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

@TestDataPath("$PROJECT_ROOT/testData/parser/moveInstructions/movec")
public class MovecInstructionParsingTest extends M68kParsingTestCase {

  public MovecInstructionParsingTest() {
    super("moveInstructions/movec");
  }

  public void testMovecInstructionMissingSource() throws Exception {
    doCodeTest(" movec ");
  }

  public void testMovecInstructionMissingDestination() throws Exception {
    doCodeTest(" movec d0,");
  }

  public void testMovecInstructionMissingDestinationFromCtrl() throws Exception {
    doCodeTest(" movec dfc,");
  }

  public void testMovecInstructionDataSizeLong() throws Exception {
    doCodeTest(" movec.l vbr,a1");
  }

}