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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.openapi.util.Comparing;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public enum M68kRegister {

  D0(M68kTokenTypes.DATA_REGISTER, "d0", M68kCpu.GROUP_68000_UP),
  D1(M68kTokenTypes.DATA_REGISTER, "d1", M68kCpu.GROUP_68000_UP),
  D2(M68kTokenTypes.DATA_REGISTER, "d2", M68kCpu.GROUP_68000_UP),
  D3(M68kTokenTypes.DATA_REGISTER, "d3", M68kCpu.GROUP_68000_UP),
  D4(M68kTokenTypes.DATA_REGISTER, "d4", M68kCpu.GROUP_68000_UP),
  D5(M68kTokenTypes.DATA_REGISTER, "d5", M68kCpu.GROUP_68000_UP),
  D6(M68kTokenTypes.DATA_REGISTER, "d6", M68kCpu.GROUP_68000_UP),
  D7(M68kTokenTypes.DATA_REGISTER, "d7", M68kCpu.GROUP_68000_UP),

  A0(M68kTokenTypes.ADDRESS_REGISTER, "a0", M68kCpu.GROUP_68000_UP),
  A1(M68kTokenTypes.ADDRESS_REGISTER, "a1", M68kCpu.GROUP_68000_UP),
  A2(M68kTokenTypes.ADDRESS_REGISTER, "a2", M68kCpu.GROUP_68000_UP),
  A3(M68kTokenTypes.ADDRESS_REGISTER, "a3", M68kCpu.GROUP_68000_UP),
  A4(M68kTokenTypes.ADDRESS_REGISTER, "a4", M68kCpu.GROUP_68000_UP),
  A5(M68kTokenTypes.ADDRESS_REGISTER, "a5", M68kCpu.GROUP_68000_UP),
  A6(M68kTokenTypes.ADDRESS_REGISTER, "a6", M68kCpu.GROUP_68000_UP),
  A7(M68kTokenTypes.ADDRESS_REGISTER, "a7", M68kCpu.GROUP_68000_UP),

  SP(M68kTokenTypes.SP, null, M68kCpu.GROUP_68000_UP),
  SSP(M68kTokenTypes.SSP, null, M68kCpu.GROUP_68000_UP),
  USP(M68kTokenTypes.USP, null, M68kCpu.GROUP_68000_UP),

  PC(M68kTokenTypes.PC, null, M68kCpu.GROUP_68000_UP),

  SR(M68kTokenTypes.SR, null, M68kCpu.GROUP_68000_UP),
  CCR(M68kTokenTypes.CCR, null, M68kCpu.GROUP_68000_UP);

  private final IElementType elementType;
  private final String text;
  private final Set<M68kCpu> cpus;

  M68kRegister(IElementType elementType, String text, Set<M68kCpu> cpus) {
    this.elementType = elementType;
    this.text = text;
    this.cpus = cpus;
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

  public boolean isSupported(M68kCpu cpu) {
    return cpus.contains(cpu);
  }

  public boolean isSupported(Set<M68kCpu> cpus) {
    for (M68kCpu m68kCpu : cpus) {
      if (cpus.contains(m68kCpu)) {
        return true;
      }
    }
    return false;
  }
}
