/*
 * Copyright 2025 The Authors
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

@TestDataPath("$PROJECT_ROOT/testData/parser/bitInstructions")
public class BitInstructionsParsingTest extends M68kParsingTestCase {

  public BitInstructionsParsingTest() {
    super("bitInstructions");
  }

  public void testBchgInstructionImmDrd() throws Exception {
    doCodeTest(" bchg #1,d0");
  }

  public void testBchgInstructionDataSizeImmDrd() throws Exception {
    doCodeTest(" bchg.l #1,d0");
  }

  public void testBchgInstructionDrdDrd() throws Exception {
    doCodeTest(" bchg d0,d1");
  }

  public void testBchgInstructionMissingSource() throws Exception {
    doCodeTest(" bchg ");
  }

  public void testBchgInstructionMissingDestination() throws Exception {
    doCodeTest(" bchg d0,");
  }

  public void testBclrInstructionDrdDrd() throws Exception {
    doCodeTest(" bclr.l d1,d2");
  }

  public void testBsetInstructionDrdDrd() throws Exception {
    doCodeTest(" bset.l d1,d2");
  }

  public void testBtstInstructionDrdDrd() throws Exception {
    doCodeTest(" btst d1,d2");
  }

  public void testBtstInstructionDrdPcd() throws Exception {
    doCodeTest(" btst d1,42(pc)");
  }

  public void testBtstInstructionImmPci() throws Exception {
    doCodeTest(" btst #1,42(pc,d0)");
  }
}
