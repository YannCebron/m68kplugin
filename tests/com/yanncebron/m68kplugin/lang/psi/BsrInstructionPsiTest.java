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

import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class BsrInstructionPsiTest extends M68kPsiTestCase<M68kBsrInstruction> {

  public BsrInstructionPsiTest() {
    super(M68kBsrInstruction.class);
  }

  public void testWithoutDataSize() {
    final M68kBsrInstruction instruction = parse("bsr 40000");

    assertNull(instruction.getDataSize());

    final M68kNumberExpression address = assertInstanceOf(instruction.getExpression(), M68kNumberExpression.class);
    assertEquals("40000", address.getText());
  }

  public void testWithDataSize() {
    final M68kBsrInstruction instruction = parse("bsr.s label");

    assertEquals(M68kDataSize.SHORT, instruction.getDataSize());

    assertInstanceOf(instruction.getExpression(), M68kLabelRefExpression.class);
  }

}
