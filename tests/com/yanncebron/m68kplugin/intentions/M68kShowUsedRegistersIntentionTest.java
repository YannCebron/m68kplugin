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

package com.yanncebron.m68kplugin.intentions;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;

import java.util.EnumSet;
import java.util.Set;

public class M68kShowUsedRegistersIntentionTest extends BasePlatformTestCase {

  public void testNoRegisters() {
    doTest("<selection> trap</selection>", EnumSet.noneOf(M68kRegister.class));
  }

  public void testSingleRegister() {
    doTest("<selection> dbf d7,label</selection>", EnumSet.of(M68kRegister.D7));
  }

  public void testAdmPcdRegister() {
    doTest("<selection> chk 66(PC),d0</selection>", EnumSet.of(M68kRegister.D0, M68kRegister.PC));
  }

  public void testAdmPciRegister() {
    doTest("<selection> chk 66(PC,d0),d1</selection>", EnumSet.of(M68kRegister.D0, M68kRegister.D1, M68kRegister.PC));
  }

  public void testAdmAixRegister() {
    doTest("<selection> move.l 3,42(a0,d0)</selection>", EnumSet.of(M68kRegister.D0, M68kRegister.A0));
  }

  public void testAdmRegisterListRegister() {
    doTest("<selection> movem d0-d2/a0/a4,-(sp)</selection>",
      EnumSet.of(M68kRegister.D0, M68kRegister.D1, M68kRegister.D2,
        M68kRegister.A0, M68kRegister.A4, M68kRegister.SP));
  }

  public void testAdmVbrRegister() {
    doTest("<selection> movec d0,vbr</selection>", EnumSet.of(M68kRegister.D0, M68kRegister.VBR));
  }

  public void testOnlySelectionRegister() {
    doTest(" asl (a0)\n" +
      "<selection> dbf d7,label</selection>", EnumSet.of(M68kRegister.D7));
  }

  private void doTest(String code, Set<M68kRegister> expectedRegisters) {
    myFixture.configureByText("a.s", code);
    final Set<M68kRegister> usedRegisters = M68kShowUsedRegistersIntention.getUsedRegisters(myFixture.getEditor(), myFixture.getFile());
    assertSameElements(usedRegisters, expectedRegisters);
  }
}
