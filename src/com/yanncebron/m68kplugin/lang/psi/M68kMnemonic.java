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
                           M68kOperand sourceOperand,
                           M68kOperand destinationOperand,
                           Set<M68kDataSize> dataSizes,
                           Set<M68kCpu> cpus) {

  public boolean isDeprecated() {
    if (elementType() != M68kTokenTypes.MOVEA) return false;

    return (sourceOperand() == M68kOperand.DATA && destinationOperand() == M68kOperand.ALTERABLE_DATA) ||
      (sourceOperand() == M68kOperand.ADDRESS_REGISTER && destinationOperand() == M68kOperand.ALTERABLE);
  }

  @Override
  public @NotNull String toString() {
    return "M68kMnemonic{" +
      elementType +
      ", deprecated=" + isDeprecated() +
      ", src=" + sourceOperand +
      ", dst=" + destinationOperand +
      ", " + dataSizes +
      ", " + cpus +
      '}';
  }
}
