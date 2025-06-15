/*
 * Copyright 2023 The Authors
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
public final class M68kMnemonic {

  private final IElementType elementType;

  private final M68kOperand sourceOperand;
  private final M68kOperand destinationOperand;
  private final Set<M68kDataSize> dataSizes;
  private final Set<M68kCpu> cpus;

  M68kMnemonic(IElementType elementType,
               M68kOperand sourceOperand,
               M68kOperand destinationOperand,
               Set<M68kDataSize> dataSizes,
               Set<M68kCpu> cpus) {
    this.elementType = elementType;
    this.sourceOperand = sourceOperand;
    this.destinationOperand = destinationOperand;
    this.dataSizes = dataSizes;
    this.cpus = cpus;
  }

  @NotNull
  public IElementType getElementType() {
    return elementType;
  }

  @NotNull
  public M68kOperand getSourceOperand() {
    return sourceOperand;
  }

  @NotNull
  public M68kOperand getDestinationOperand() {
    return destinationOperand;
  }

  @NotNull
  public Set<M68kDataSize> getDataSizes() {
    return dataSizes;
  }

  @NotNull
  public Set<M68kCpu> getCpus() {
    return cpus;
  }

  public boolean isDeprecated() {
    if (getElementType() != M68kTokenTypes.MOVEA) return false;

    return (getSourceOperand() == M68kOperand.DATA && getDestinationOperand() == M68kOperand.ALTERABLE_DATA) ||
      (getSourceOperand() == M68kOperand.ADDRESS_REGISTER && getDestinationOperand() == M68kOperand.ALTERABLE);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof M68kMnemonic that)) return false;

    if (!elementType.equals(that.elementType)) return false;
    if (sourceOperand != that.sourceOperand) return false;
    if (destinationOperand != that.destinationOperand) return false;
    if (!dataSizes.equals(that.dataSizes)) return false;
    return cpus.equals(that.cpus);
  }

  @Override
  public int hashCode() {
    int result = elementType.hashCode();
    result = 31 * result + sourceOperand.hashCode();
    result = 31 * result + destinationOperand.hashCode();
    result = 31 * result + dataSizes.hashCode();
    result = 31 * result + cpus.hashCode();
    return result;
  }

  @Override
  public String toString() {
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
