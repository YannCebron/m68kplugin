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

import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class MovepInstructionPsiTest extends M68kPsiTestCase<M68kMovepInstruction> {

  public MovepInstructionPsiTest() {
    super(M68kMovepInstruction.class);
  }

  public void testWithDataSize() {
    final M68kMovepInstruction instruction = parse("movep.l d0,42(a0)");

    assertEquals(M68kDataSize.LONGWORD, instruction.getDataSize());
    final M68kAdmDrd admDrd = instruction.getAdmDrd();
    assertNotNull(admDrd);
    assertEquals(M68kRegister.D0, admDrd.getRegister());

    final M68kAdmAdi admAdi = instruction.getAdmAdi();
    assertNotNull(admAdi);
    assertEquals(M68kRegister.A0, admAdi.getRegister());
    final M68kNumberExpression displacementExpression = assertInstanceOf(admAdi.getDisplacement(), M68kNumberExpression.class);
    assertEquals(42L, displacementExpression.getValue());
  }

}
