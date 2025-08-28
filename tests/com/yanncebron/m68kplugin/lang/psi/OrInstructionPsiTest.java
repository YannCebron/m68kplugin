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

public class OrInstructionPsiTest extends M68kPsiTestCase<M68kOrInstruction> {

  public OrInstructionPsiTest() {
    super(M68kOrInstruction.class);
  }

  public void testWithoutDataSize() {
    final M68kOrInstruction instruction = parse("or d0,d1");

    assertNull(instruction.getDataSize());
  }

  public void testWithDataSize() {
    final M68kOrInstruction instruction = parse("or.b d0,d1");

    assertEquals(M68kDataSize.BYTE, instruction.getDataSize());
  }

}
