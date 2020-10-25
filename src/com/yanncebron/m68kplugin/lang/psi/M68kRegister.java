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

import com.intellij.openapi.util.Comparing;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public enum M68kRegister {

  D0(M68kTokenTypes.DATA_REGISTER, "d0"),
  D1(M68kTokenTypes.DATA_REGISTER, "d1"),
  D2(M68kTokenTypes.DATA_REGISTER, "d2"),
  D3(M68kTokenTypes.DATA_REGISTER, "d3"),
  D4(M68kTokenTypes.DATA_REGISTER, "d4"),
  D5(M68kTokenTypes.DATA_REGISTER, "d5"),
  D6(M68kTokenTypes.DATA_REGISTER, "d6"),
  D7(M68kTokenTypes.DATA_REGISTER, "d7"),

  A0(M68kTokenTypes.ADDRESS_REGISTER, "a0"),
  A1(M68kTokenTypes.ADDRESS_REGISTER, "a1"),
  A2(M68kTokenTypes.ADDRESS_REGISTER, "a2"),
  A3(M68kTokenTypes.ADDRESS_REGISTER, "a3"),
  A4(M68kTokenTypes.ADDRESS_REGISTER, "a4"),
  A5(M68kTokenTypes.ADDRESS_REGISTER, "a5"),
  A6(M68kTokenTypes.ADDRESS_REGISTER, "a6"),
  A7(M68kTokenTypes.ADDRESS_REGISTER, "a7"),

  SP(M68kTokenTypes.SP, null),
  SSP(M68kTokenTypes.SSP, null),
  USP(M68kTokenTypes.USP, null),

  PC(M68kTokenTypes.PC, null),

  SR(M68kTokenTypes.SR, null),
  CCR(M68kTokenTypes.CCR, null);

  private final IElementType elementType;
  private final String text;

  M68kRegister(IElementType elementType, String text) {
    this.elementType = elementType;
    this.text = text;
  }

  @NotNull
  public static M68kRegister find(IElementType elementType, String text) {
    for (M68kRegister value : values()) {
      if (value.elementType != elementType) continue;
      if (value.text == null) {
        return value;
      }
      if (Comparing.strEqual(value.text, text, false)) {
        return value;
      }
    }
    throw new IllegalArgumentException("No register for " + elementType + " with text '" + text + "'");
  }

  public boolean isSameKind(M68kRegister other) {
    return elementType == other.elementType;
  }
}
