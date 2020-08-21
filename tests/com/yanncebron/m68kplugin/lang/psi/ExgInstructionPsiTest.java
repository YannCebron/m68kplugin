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

public class ExgInstructionPsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kExgInstruction instruction = parse("exg d0,d1");

    assertNull(instruction.getDataSize());

    final M68kAdmRrd source = assertInstanceOf(instruction.getSource(), M68kAdmRrd.class);
    assertNull(source.getAdmArd());
    final M68kAdmDrd sourceDrd = assertInstanceOf(source.getAdmDrd(), M68kAdmDrd.class);
    assertEquals("d0", sourceDrd.getText());

    final M68kAdmRrd destination = assertInstanceOf(instruction.getDestination(), M68kAdmRrd.class);
    assertNull(destination.getAdmArd());
    final M68kAdmDrd destinationDrd = assertInstanceOf(destination.getAdmDrd(), M68kAdmDrd.class);
    assertEquals("d1", destinationDrd.getText());
  }

  public void testWithDataSize() {
    final M68kExgInstruction instruction = parse("exg.l d0,d1");

    assertEquals(M68kDataSize.LONG, instruction.getDataSize());
  }

  private M68kExgInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kExgInstruction.class);
  }

}
