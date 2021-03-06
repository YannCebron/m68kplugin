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

public class UnlkInstructionPsiTest extends M68kPsiTestCase {

  public void testUnlk() {
    final M68kUnlkInstruction instruction = parse("unlk a0");

    final M68kAdmArd admArd = instruction.getAdmArd();
    assertNotNull(admArd);
    assertEquals(M68kRegister.A0, admArd.getRegister());
  }

  private M68kUnlkInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kUnlkInstruction.class);
  }

}
