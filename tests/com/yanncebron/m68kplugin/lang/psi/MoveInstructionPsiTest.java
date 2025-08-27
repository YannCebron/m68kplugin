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
import com.yanncebron.m68kplugin.lang.psi.impl.M68kPsiImplUtil;

public class MoveInstructionPsiTest extends M68kPsiTestCase {

  public void testWithDataSize() {
    final M68kMoveInstruction instruction = parse("move.l d0,42(a0)");

    assertEquals(M68kDataSize.LONGWORD, instruction.getDataSize());
  }

  public void testMoveInstructionPciDataSizeDrd() {
    final M68kMoveInstruction instruction = parse("move.b 42(PC,d6.w),d6");

    final M68kAdmPci admPci = instruction.getAdmPci();
    assertNotNull(admPci);
    final M68kNumberExpression displacement = assertInstanceOf(admPci.getDisplacement(), M68kNumberExpression.class);
    assertEquals(42L, displacement.getValue());
    assertEquals(M68kRegister.PC, admPci.getRegister());

    final M68kAdmRrdIndex admRrdIndex = admPci.getAdmRrdIndex();
    assertNotNull(admRrdIndex);
    assertEquals(M68kRegister.D6, admRrdIndex.getRegister());
    assertEquals(M68kDataSize.WORD, admRrdIndex.getDataSize());
  }

  public void testAdmSrSource() {
    final M68kMoveInstruction instruction = parse("move.w SR,d6");

    assertFalse(instruction.isPrivileged(M68kCpu.M_68000));
    for (M68kCpu m68kCpu : M68kCpu.GROUP_68010_UP) {
      assertTrue(instruction.isPrivileged(m68kCpu));
    }

    final M68kAdmSr admSr = instruction.getAdmSr();
    assertNotNull(admSr);
    assertEquals(M68kRegister.SR, admSr.getRegister());
    assertTrue(M68kPsiImplUtil.isSrc(instruction, admSr));
    assertFalse(M68kPsiImplUtil.isDest(instruction, admSr));

    final M68kAdmDrd admDrd = instruction.getAdmDrd();
    assertNotNull(admDrd);
    assertTrue(M68kPsiImplUtil.isDest(instruction, admDrd));
    assertFalse(M68kPsiImplUtil.isSrc(instruction, admDrd));
  }

  public void testAdmSrDest() {
    final M68kMoveInstruction instruction = parse("move.w d6,SR");

    for (M68kCpu m68kCpu : M68kCpu.GROUP_68000_UP) {
      assertTrue(instruction.isPrivileged(m68kCpu));
    }
  }

  private M68kMoveInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kMoveInstruction.class);
  }

}
