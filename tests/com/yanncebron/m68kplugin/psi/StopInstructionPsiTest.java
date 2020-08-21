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

package com.yanncebron.m68kplugin.psi;

import com.yanncebron.m68kplugin.lang.psi.M68kAdmImm;
import com.yanncebron.m68kplugin.lang.psi.M68kStopInstruction;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class StopInstructionPsiTest extends M68kPsiTestCase {

  public void testStopInstruction() {
    final M68kStopInstruction instruction = parse("stop #2");
    assertTrue(instruction.isPrivileged());

    final M68kAdmImm admImm = instruction.getAdmImm();
    assertNotNull(admImm);
    final M68kNumberExpression m68kNumberExpression = assertInstanceOf(admImm.getExpression(), M68kNumberExpression.class);
    assertEquals("2", m68kNumberExpression.getText());
  }

  private M68kStopInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kStopInstruction.class);
  }

}
