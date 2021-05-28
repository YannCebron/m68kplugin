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

package com.yanncebron.m68kplugin.lang.symbol;

import com.intellij.model.Pointer;
import com.intellij.model.Symbol;
import com.intellij.model.presentation.PresentableSymbol;
import com.intellij.model.presentation.SymbolPresentation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class M68kBuiltinSymbol implements PresentableSymbol {

  private final M68kBuiltinSymbolRegistry symbol;

  M68kBuiltinSymbol(M68kBuiltinSymbolRegistry symbol) {
    this.symbol = symbol;
  }

  @Override
  public @NotNull Pointer<? extends Symbol> createPointer() {
    return Pointer.hardPointer(this);
  }

  @Override
  public @NotNull SymbolPresentation getSymbolPresentation() {
    return SymbolPresentation.create(symbol.getIcon(), symbol.getName(), symbol.getDescription());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof M68kBuiltinSymbol)) return false;

    M68kBuiltinSymbol that = (M68kBuiltinSymbol) o;

    return Objects.equals(symbol, that.symbol);
  }

  @Override
  public int hashCode() {
    return symbol != null ? symbol.hashCode() : 0;
  }
}
