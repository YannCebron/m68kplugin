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

package com.yanncebron.m68kplugin.lang.stubs.index;

import com.intellij.psi.stubs.StubIndexKey;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;

public final class M68kStubIndexKeys {

  public static final StubIndexKey<String, M68kLabel> LABEL = StubIndexKey.createIndexKey("m68k.label.index");

  public static final StubIndexKey<String, M68kLabel> MACRO = StubIndexKey.createIndexKey("m68k.macro.index");

  private M68kStubIndexKeys() {
  }
}
