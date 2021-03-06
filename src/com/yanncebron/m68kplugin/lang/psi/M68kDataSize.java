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
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;

public enum M68kDataSize {

  UNSIZED("<unsized>", null),

  SHORT(".s", M68kTokenTypes.DOT_S),
  BYTE(".b", M68kTokenTypes.DOT_B),
  WORD(".w", M68kTokenTypes.DOT_W),
  LONGWORD(".l", M68kTokenTypes.DOT_L);

  @NonNls
  private final String text;
  private final IElementType elementType;

  M68kDataSize(@NonNls String text, @Nullable IElementType elementType) {
    this.text = text;
    this.elementType = elementType;
  }

  @NonNls
  public String getText() {
    return text;
  }

  @Nullable
  public IElementType getElementType() {
    return elementType;
  }

  @Nullable
  public static M68kDataSize findByElementType(@NotNull IElementType elementType) {
    for (M68kDataSize value : values()) {
      if (elementType == value.getElementType()) return value;
    }
    return null;
  }

  @Nullable
  public static M68kDataSize findByText(@NotNull @NonNls String text) {
    for (M68kDataSize value : values()) {
      if (Comparing.strEqual(text, value.getText(), false)) return value;
    }
    return null;
  }

  public static final Set<M68kDataSize> GROUP_SBWL = EnumSet.of(SHORT, BYTE, WORD, LONGWORD);
  public static final Set<M68kDataSize> GROUP_SBW = EnumSet.of(SHORT, BYTE, WORD);
  public static final Set<M68kDataSize> GROUP_BWL = EnumSet.of(BYTE, WORD, LONGWORD);
  public static final Set<M68kDataSize> GROUP_WL = EnumSet.of(WORD, LONGWORD);

  public static final Set<M68kDataSize> GROUP_UNSIZED = EnumSet.of(UNSIZED);
  public static final Set<M68kDataSize> GROUP_S = EnumSet.of(SHORT);
  public static final Set<M68kDataSize> GROUP_B = EnumSet.of(BYTE);
  public static final Set<M68kDataSize> GROUP_W = EnumSet.of(WORD);
  public static final Set<M68kDataSize> GROUP_L = EnumSet.of(LONGWORD);

}
