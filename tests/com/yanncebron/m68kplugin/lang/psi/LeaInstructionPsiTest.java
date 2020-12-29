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

package com.yanncebron.m68kplugin.lang.psi;

public class LeaInstructionPsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kLeaInstruction instruction = parse("lea $50000,a0");

    assertNull(instruction.getDataSize());
  }

  public void testWithDataSize() {
    final M68kLeaInstruction instruction = parse("lea.l $50000.l,a0");

    assertEquals(M68kDataSize.LONGWORD, instruction.getDataSize());

    final M68kAdmAbs source = instruction.getAdmAbs();
    assertNotNull(source);
    assertEquals(M68kDataSize.LONGWORD, source.getDataSize());
  }

  private M68kLeaInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kLeaInstruction.class);
  }

}
