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
import org.jetbrains.annotations.TestOnly;

import java.util.Set;
import java.util.function.Function;

/**
 * @see M68kMnemonicRegistry
 */
public record M68kMnemonic(IElementType elementType,
                           Set<M68kDataSize> dataSizes,
                           M68kOperand firstOperand,
                           M68kOperand secondOperand,
                           Set<M68kCpu> cpus,
                           PrivilegedType privilegedType) {

  @TestOnly
  public M68kMnemonic(IElementType elementType, Set<M68kDataSize> dataSizes, M68kOperand firstOperand, M68kOperand secondOperand) {
    this(elementType, dataSizes, firstOperand, secondOperand, M68kCpu.GROUP_68000_UP, PrivilegedType.NONE);
  }

  public boolean hasFirstOperand() {
    return firstOperand() != M68kOperand.NONE;
  }

  public boolean hasSecondOperand() {
    return secondOperand() != M68kOperand.NONE;
  }

  public boolean isDeprecated() {
    if (elementType() != M68kTokenTypes.MOVEA) return false;

    return (firstOperand() == M68kOperand.DATA && secondOperand() == M68kOperand.ALTERABLE_DATA) ||
      (firstOperand() == M68kOperand.ADDRESS_REGISTER && secondOperand() == M68kOperand.ALTERABLE);
  }

  public enum PrivilegedType {
    /**
     * Never privileged.
     */
    NONE(m68kCpu -> Boolean.FALSE),

    /**
     * Always privileged.
     */
    PRIVILEGED(m68kCpu -> Boolean.TRUE),

    /**
     * Privileged for 68010 or above only.
     */
    PRIVILEGED_68010_ABOVE(M68kCpu.GROUP_68010_UP::contains);

    private final Function<M68kCpu, Boolean> privilegedFunction;

    PrivilegedType(Function<M68kCpu, Boolean> privilegedFunction) {
      this.privilegedFunction = privilegedFunction;
    }

    public boolean isPrivileged(M68kCpu m68kCpu) {
      return privilegedFunction.apply(m68kCpu);
    }
  }

  @Override
  public @NotNull String toString() {
    final String cpuText;
    if (cpus.equals(M68kCpu.GROUP_68000_UP)) cpuText = "MC68000 Family";
    else if (cpus.equals(M68kCpu.GROUP_68010_UP)) cpuText = "MC68010+";
    else if (cpus.equals(M68kCpu.GROUP_68020_UP)) cpuText = "MC68020+";
    else cpuText = cpus.toString();

    return "M68kMnemonic{" +
      elementType +
      (isDeprecated() ? ", DEPRECATED" : "") +
      ", firstOp=" + firstOperand +
      ", secondOp=" + secondOperand +
      ", " + dataSizes +
      ", " + cpuText +
      (privilegedType != PrivilegedType.NONE ? ", " + privilegedType.name() : "") +
      '}';
  }
}
