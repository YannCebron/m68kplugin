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

import com.intellij.psi.tree.IElementType;
import junit.framework.TestCase;

public class M68kRegisterTest extends TestCase {

  public void testFindValidDataRegister() {
    doTest(M68kTokenTypes.DATA_REGISTER, "d0", M68kRegister.D0);
    doTest(M68kTokenTypes.DATA_REGISTER, "D0", M68kRegister.D0);
    doTest(M68kTokenTypes.DATA_REGISTER, "D1", M68kRegister.D1);
    doTest(M68kTokenTypes.DATA_REGISTER, "D2", M68kRegister.D2);
    doTest(M68kTokenTypes.DATA_REGISTER, "D3", M68kRegister.D3);
    doTest(M68kTokenTypes.DATA_REGISTER, "D4", M68kRegister.D4);
    doTest(M68kTokenTypes.DATA_REGISTER, "D5", M68kRegister.D5);
    doTest(M68kTokenTypes.DATA_REGISTER, "D6", M68kRegister.D6);
    doTest(M68kTokenTypes.DATA_REGISTER, "D7", M68kRegister.D7);
  }

  public void testFindInvalidDataRegister() {
    doTestFail(M68kTokenTypes.DATA_REGISTER, "Da");
    doTestFail(M68kTokenTypes.DATA_REGISTER, null);
    doTestFail(M68kTokenTypes.ADDRESS_REGISTER, "d0");
    doTestFail(M68kTokenTypes.MOVEM, "NOT_EXISTING");
  }

  public void testFindValidAddressRegister() {
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a0", M68kRegister.A0);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "A0", M68kRegister.A0);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a1", M68kRegister.A1);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a2", M68kRegister.A2);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a3", M68kRegister.A3);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a4", M68kRegister.A4);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a5", M68kRegister.A5);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a6", M68kRegister.A6);
    doTest(M68kTokenTypes.ADDRESS_REGISTER, "a7", M68kRegister.A7);
  }

  public void testSpecialRegisters() {
    doTest(M68kTokenTypes.SP, null, M68kRegister.SP);
    doTest(M68kTokenTypes.SP, "DOESNT_MATTER", M68kRegister.SP);
    doTest(M68kTokenTypes.USP, "DOESNT_MATTER", M68kRegister.USP);
    doTest(M68kTokenTypes.SR, "DOESNT_MATTER", M68kRegister.SR);
    doTest(M68kTokenTypes.CCR, "DOESNT_MATTER", M68kRegister.CCR);
  }

  private void doTest(IElementType elementType, String text, M68kRegister expected) {
    assertEquals(expected, M68kRegister.find(elementType, text));
  }

  private void doTestFail(IElementType elementType, String text) {
    try {
      final M68kRegister wrong = M68kRegister.find(elementType, text);
      fail("Returned " + wrong + ", should have failed for " + elementType + " with text '" + text + "'");
    } catch (IllegalArgumentException ignore) {
    }
  }
}