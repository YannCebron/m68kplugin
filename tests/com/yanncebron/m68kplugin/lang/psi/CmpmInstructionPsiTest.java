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

public class CmpmInstructionPsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kCmpmInstruction instruction = parse("cmpm (a0)+,(a1)+");

    assertNull(instruction.getDataSize());
  }

  public void testWithDataSize() {
    final M68kCmpmInstruction instruction = parse("cmpm.w (a0)+,(a1)+");

    assertEquals(M68kDataSize.WORD, instruction.getDataSize());
    final M68kAdmApi source = instruction.getSource();
    assertNotNull(source);
    assertEquals("(a0)+", source.getText());
    assertEquals(M68kRegister.A0, source.getRegister());
    
    final M68kAdmApi destination = instruction.getDestination();
    assertNotNull(destination);
    assertEquals("(a1)+", destination.getText());
    assertEquals(M68kRegister.A1, destination.getRegister());
  }

  private M68kCmpmInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kCmpmInstruction.class);
  }

}
