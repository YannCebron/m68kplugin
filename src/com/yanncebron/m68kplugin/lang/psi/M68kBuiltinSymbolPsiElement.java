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

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.impl.FakePsiElement;

/**
 * Fake resolve element for {@link M68kBuiltinSymbol}.
 */
public final class M68kBuiltinSymbolPsiElement extends FakePsiElement implements SyntheticElement {

  private final PsiElement psiElement;
  private final M68kBuiltinSymbol builtinSymbol;

  public M68kBuiltinSymbolPsiElement(PsiElement psiElement, M68kBuiltinSymbol builtinSymbol) {
    this.psiElement = psiElement;
    this.builtinSymbol = builtinSymbol;
  }

  @Override
  public PsiElement getParent() {
    return psiElement;
  }

  // to get some basic tooltip
  @Override
  public String getName() {
    return StringUtil.defaultIfEmpty(builtinSymbol.getDescription(), builtinSymbol.getName());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof M68kBuiltinSymbolPsiElement)) return false;

    M68kBuiltinSymbolPsiElement that = (M68kBuiltinSymbolPsiElement) o;

    return builtinSymbol == that.builtinSymbol;
  }

  @Override
  public int hashCode() {
    return builtinSymbol != null ? builtinSymbol.hashCode() : 0;
  }
}