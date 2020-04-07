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

import com.yanncebron.m68kplugin.lang.psi.M68kClrInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kDataSize;

public class ClrInstructionPsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kClrInstruction instruction = parse("clr d0");

    assertNull(instruction.getDataSize());
  }

  public void testWithDataSize() {
    final M68kClrInstruction instruction = parse("clr.l d0");

    assertEquals(M68kDataSize.LONG, instruction.getDataSize());
  }

  private M68kClrInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kClrInstruction.class);
  }

}
