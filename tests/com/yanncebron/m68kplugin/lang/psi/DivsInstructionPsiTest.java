/*
 * Copyright 2026 The Authors
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

public class DivsInstructionPsiTest extends M68kPsiTestCase<M68kDivsInstruction> {

  public DivsInstructionPsiTest() {
    super(M68kDivsInstruction.class);
  }

  public void testDoubleDataRegister() {
    M68kDivsInstruction instruction = parse("divs d0,d1:d2");

    M68kAdmDoubleDrd admDoubleDrd = instruction.getAdmDoubleDrd();
    assertNotNull(admDoubleDrd);

    M68kAdmDrd remainder = admDoubleDrd.getFirst();
    assertEquals(M68kRegister.D1, remainder.getRegister());

    M68kAdmDrd quotient = admDoubleDrd.getSecond();
    assertNotNull(quotient);
    assertEquals(M68kRegister.D2, quotient.getRegister());
  }
}
