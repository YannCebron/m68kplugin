/*
 * Copyright 2022 The Authors
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

import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface M68kLabelBase extends PsiNameIdentifierOwner, NavigatablePsiElement, M68kPsiElement {

  enum LabelKind {
    GLOBAL(false),
    LOCAL(false),
    EQU(true),
    EQUALS(true),
    SET(true),
    EQUR(true),
    REG(true),
    MACRO(false),
    FO(true),
    SO(true);

    private final boolean hasValue;

    LabelKind(boolean hasValue) {
      this.hasValue = hasValue;
    }

    public boolean hasValue() {
      return hasValue;
    }
  }

  @NotNull
  PsiElement getNameIdentifier();

  LabelKind getLabelKind();

  /**
   * Associated value for assignments.
   *
   * @return {@code null} if not {@link LabelKind#hasValue} is {@code false} or value is undefined.
   */
  @Nullable
  String getValue();

}
