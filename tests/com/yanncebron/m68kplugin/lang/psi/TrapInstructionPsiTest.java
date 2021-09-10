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

import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class TrapInstructionPsiTest extends M68kPsiTestCase {

  public void testTrapInstruction() {
    final M68kTrapInstruction instruction = parse("trap #2");

    final M68kAdmQuick admQuick = instruction.getAdmQuick();
    assertNotNull(admQuick);
    final M68kNumberExpression m68kNumberExpression = assertInstanceOf(admQuick.getExpression(), M68kNumberExpression.class);
    assertEquals("2", m68kNumberExpression.getText());
  }

  private M68kTrapInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kTrapInstruction.class);
  }

}
