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

import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class MoveqInstructionPsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kMoveqInstruction instruction = parse("moveq #1,d0");

    assertNull(instruction.getDataSize());

    final M68kAdmImm admImm = assertInstanceOf(instruction.getSource(), M68kAdmImm.class);
    assertEquals("#1", admImm.getText());
    assertInstanceOf(admImm.getExpression(), M68kNumberExpression.class);

    final M68kAdmDrd destination = instruction.getDestination();
    assertNotNull(destination);
    assertEquals("d0", destination.getText());
    assertEquals(M68kRegister.D0, destination.getRegister());
  }

  public void testWithDataSize() {
    final M68kMoveqInstruction instruction = parse("moveq.l #1,d1");

    assertEquals(M68kDataSize.LONGWORD, instruction.getDataSize());
  }

  private M68kMoveqInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kMoveqInstruction.class);
  }

}
