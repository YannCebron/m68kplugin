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

import com.yanncebron.m68kplugin.lang.psi.M68kChkInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kDataSize;

public class ChkInstructionPsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kChkInstruction instruction = parse("chk d0,d1");

    assertNull(instruction.getDataSize());
  }

  public void testWithDataSize() {
    final M68kChkInstruction instruction = parse("chk.w (a0),d1");

    assertEquals(M68kDataSize.WORD, instruction.getDataSize());
    assertNotNull(instruction.getAdmAri());
  }

  private M68kChkInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kChkInstruction.class);
  }

}
