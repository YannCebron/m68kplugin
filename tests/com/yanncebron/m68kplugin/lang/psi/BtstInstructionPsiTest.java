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

package com.yanncebron.m68kplugin.lang.psi;

public class BtstInstructionPsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kBtstInstruction instruction = parse("btst #1,d0");

    assertNull(instruction.getDataSize());
    assertNotNull(instruction.getSourceQuick());
  }

  public void testWithDataSize() {
    final M68kBtstInstruction instruction = parse("btst.b d1,d0");

    assertEquals(M68kDataSize.BYTE, instruction.getDataSize());

    assertNull(instruction.getSourceQuick());
    final M68kAdmDrd sourceDrd = instruction.getSourceDrd();
    assertNotNull(sourceDrd);
    assertEquals(M68kRegister.D1, sourceDrd.getRegister());
  }

  private M68kBtstInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kBtstInstruction.class);
  }

}
