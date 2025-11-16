/*
 * Copyright 2025 The Authors
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

import java.util.Set;

/**
 * @see M68kMnemonicRegistry
 */
public record M68kMnemonic(IElementType elementType,
                           Set<M68kDataSize> dataSizes,
                           M68kOperand sourceOperand,
                           M68kOperand destinationOperand,
                           Set<M68kCpu> cpus) {

  public M68kMnemonic(IElementType elementType, Set<M68kDataSize> dataSizes, M68kOperand sourceOperand, M68kOperand destinationOperand) {
    this(elementType, dataSizes, sourceOperand, destinationOperand, M68kCpu.GROUP_68000_UP);
  }

  public M68kMnemonic(IElementType elementType, Set<M68kDataSize> dataSizes, M68kOperand sourceOperand) {
    this(elementType, dataSizes, sourceOperand, M68kCpu.GROUP_68000_UP);
  }

  public M68kMnemonic(IElementType elementType, Set<M68kDataSize> dataSizes, M68kOperand sourceOperand, Set<M68kCpu> cpus) {
    this(elementType, dataSizes, sourceOperand, M68kOperand.NONE, cpus);
  }

  public boolean isDeprecated() {
    if (elementType() != M68kTokenTypes.MOVEA) return false;

    return (sourceOperand() == M68kOperand.DATA && destinationOperand() == M68kOperand.ALTERABLE_DATA) ||
      (sourceOperand() == M68kOperand.ADDRESS_REGISTER && destinationOperand() == M68kOperand.ALTERABLE);
  }

  @Override
  public @NotNull String toString() {
    final String cpuText;
    if (cpus == M68kCpu.GROUP_68000_UP) cpuText = "M68000 Family";
    else if (cpus == M68kCpu.GROUP_68010_UP) cpuText = "M68010+";
    else if (cpus == M68kCpu.GROUP_68020_UP) cpuText = "M68020+";
    else cpuText = cpus.toString();

    return "M68kMnemonic{" +
      elementType +
      (isDeprecated() ? ", DEPRECATED" : "") +
      ", src=" + sourceOperand +
      ", dst=" + destinationOperand +
      ", " + dataSizes +
      ", " + cpuText +
      '}';
  }
}
