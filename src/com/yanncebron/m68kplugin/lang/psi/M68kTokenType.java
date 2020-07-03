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

import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

class M68kTokenType extends IElementType {

  M68kTokenType(@NotNull @NonNls String debugName) {
    super(debugName, M68kLanguage.INSTANCE);
  }
}
