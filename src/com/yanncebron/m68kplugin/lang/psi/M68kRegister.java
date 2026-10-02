/*
 * Copyright 2026 The Authors
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * All supported registers.
 * <p>
 * See vasm {@code cpus/m68k/specregs.h}.
 * Reference: Table 1-1, Table 1-2
 */
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

  SP(M68kTokenTypes.SP),
  SSP(M68kTokenTypes.SSP),
  USP(M68kTokenTypes.USP),

  PC(M68kTokenTypes.PC),

  SR(M68kTokenTypes.SR),
  CCR(M68kTokenTypes.CCR),


  DFC(M68kTokenTypes.DFC, null, M68kCpu.GROUP_68010_UP),
  SFC(M68kTokenTypes.SFC, null, M68kCpu.GROUP_68010_UP),
  VBR(M68kTokenTypes.VBR, null, M68kCpu.GROUP_68010_UP);

  private final IElementType elementType;
  private final @Nullable String text;
  private final Set<M68kCpu> cpus;

  M68kRegister(IElementType elementType) {
    this(elementType, null, M68kCpu.GROUP_68000_UP);
  }

  M68kRegister(IElementType elementType, @NotNull String text) {
    this(elementType, text, M68kCpu.GROUP_68000_UP);
  }
  M68kRegister(IElementType elementType, @Nullable String text, Set<M68kCpu> cpus) {
    this.elementType = elementType;
    this.text = text;
    this.cpus = cpus;
  }

  @NotNull
  public static M68kRegister find(IElementType elementType, @Nullable String text) {
    for (M68kRegister value : values()) {
      if (value.elementType != elementType) continue;
      if (value.text == null) {
        return value;
      }
      if (value.text.equalsIgnoreCase(text)) {
        return value;
      }
    }
    throw new IllegalArgumentException("No register for " + elementType + " with text '" + text + "'");
  }

  public Set<M68kCpu> getCpus() {
    return cpus;
  }

  public IElementType getElementType() {
    return elementType;
  }

  public boolean isSameKind(M68kRegister other) {
    return elementType == other.elementType;
  }

  public boolean isSupported(M68kCpu cpu) {
    return cpus.contains(cpu);
  }

  /**
   * @return if register is supported by <em>all</em> given CPUs
   */
  public boolean isSupported(Set<M68kCpu> supportedCpus) {
    return cpus.containsAll(supportedCpus);
  }
}
