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

public class RoxlInstructionPsiTest extends M68kPsiTestCase<M68kRoxlInstruction> {

  public RoxlInstructionPsiTest() {
    super(M68kRoxlInstruction.class);
  }

  public void testWithoutDataSize() {
    final M68kRoxlInstruction instruction = parse("roxl #1,d0");

    assertNull(instruction.getDataSize());

    assertNotNull(instruction.getAdmQuick());
  }

  public void testWithDataSize() {
    final M68kRoxlInstruction instruction = parse("roxl.b #1,d0");

    assertEquals(M68kDataSize.BYTE, instruction.getDataSize());

    assertNotNull(instruction.getAdmQuick());
  }

}
