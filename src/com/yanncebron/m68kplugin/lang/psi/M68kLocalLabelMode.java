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
import com.intellij.util.Function;
import org.jetbrains.annotations.Nullable;

public enum M68kLocalLabelMode {

  DOT(string -> '.' + string, text -> StringUtil.startsWithChar(text, '.')),
  DOLLAR(string -> string + '$', text -> StringUtil.endsWithChar(text, '$'));

  private final Function<String, String> nameFunction;
  private final Function<String, Boolean> matchFunction;

  M68kLocalLabelMode(Function<String, String> nameFunction,
                     Function<String, Boolean> matchFunction) {
    this.nameFunction = nameFunction;
    this.matchFunction = matchFunction;
  }

  public String getPatchedName(M68kLocalLabel localLabel) {
    return getPatchedName(localLabel.getName());
  }

  public String getPatchedName(String labelText) {
    return nameFunction.fun(labelText);
  }

  public boolean matches(String labelText) {
    return matchFunction.fun(StringUtil.trimEnd(labelText, ':'));
  }

  @Nullable
  public static M68kLocalLabelMode find(@Nullable String labelText) {
    if (labelText == null) return null;
    for (M68kLocalLabelMode localLabelMode : values()) {
      if (localLabelMode.matches(labelText)) return localLabelMode;
    }
    return null;
  }
}
