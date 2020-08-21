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

public class MoveaInstructionPsiTest extends M68kPsiTestCase {

  public void testWithDataSize() {
    final M68kMoveaInstruction instruction = parse("movea.l a0,a1");

    assertEquals(M68kDataSize.LONG, instruction.getDataSize());

    assertSize(2, instruction.getAdmArdList());
  }

  private M68kMoveaInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kMoveaInstruction.class);
  }

}
