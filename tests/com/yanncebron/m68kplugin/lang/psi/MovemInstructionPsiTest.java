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

import java.util.List;

public class MovemInstructionPsiTest extends M68kPsiTestCase {

  public void testWithDataSize() {
    final M68kMovemInstruction instruction = parse("movem.l d1-d2/a0/a2,(a7)");

    assertEquals(M68kDataSize.LONG, instruction.getDataSize());

    final M68kRegisterList registerList = instruction.getRegisterList();
    assertNotNull(registerList);
    final List<M68kRegisterRange> registerRangeList = registerList.getRegisterRangeList();
    assertSize(3, registerRangeList);

    final M68kRegisterRange firstRegisterRange = registerRangeList.get(0);
    final M68kAdmRrd firstFrom = firstRegisterRange.getFrom();
    assertNotNull(firstFrom);
    final M68kAdmDrd firstFromAdmDrd = firstFrom.getAdmDrd();
    assertNotNull(firstFromAdmDrd);
    assertEquals("d1", firstFromAdmDrd.getText());
    final M68kAdmRrd firstTo = firstRegisterRange.getTo();
    assertNotNull(firstTo);
    final M68kAdmDrd firstToAdmDrd = firstTo.getAdmDrd();
    assertNotNull(firstToAdmDrd);
    assertEquals("d2", firstTo.getText());

    final M68kRegisterRange secondRegisterRange = registerRangeList.get(1);
    assertNull(secondRegisterRange.getTo());


    final M68kAdmAri admAri = instruction.getAdmAri();
    assertNotNull(admAri);
    assertEquals("(a7)", admAri.getText());
  }

  private M68kMovemInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kMovemInstruction.class);
  }

}
