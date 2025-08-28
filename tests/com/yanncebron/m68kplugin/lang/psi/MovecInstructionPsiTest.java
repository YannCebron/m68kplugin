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

package com.yanncebron.m68kplugin.lang.psi;

public class MovecInstructionPsiTest extends M68kPsiTestCase {

  public void testWithDataSize() {
    final M68kMovecInstruction instruction = parse("movec.l a0,vbr");

    assertTrue(instruction.isPrivileged(M68kCpu.M_68000));
    assertEquals(M68kDataSize.LONGWORD, instruction.getDataSize());

    assertNotNull(instruction.getAdmRrd());
    M68kAdmVbr admVbr = instruction.getAdmVbr();
    assertNotNull(admVbr);
    assertEquals(M68kRegister.VBR, admVbr.getRegister());
  }

  private M68kMovecInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kMovecInstruction.class);
  }

}
