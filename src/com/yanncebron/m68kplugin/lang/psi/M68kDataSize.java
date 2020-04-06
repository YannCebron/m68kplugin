/*
 * Copyright 2020 The Authors
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
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum M68kDataSize {

  BYTE(".b"), SHORT(".s"), WORD(".w"), LONG(".l");

  @NonNls
  private final String text;

  M68kDataSize(String text) {
    this.text = text;
  }

  @NonNls
  public String getText() {
    return text;
  }

  @Nullable
  public static M68kDataSize findByText(@NotNull @NonNls String text) {
    for (M68kDataSize value : values()) {
      if (Comparing.strEqual(text, value.getText(), false)) return value;
    }
    return null;
  }
}
