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

package com.yanncebron.m68kplugin.lang.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import org.jetbrains.annotations.NotNull;

public final class M68kStubElementTypeFactory {

  public static IStubElementType<? extends StubElement<? extends M68kPsiElement>, ? extends M68kPsiElement> stubFactory(@NotNull String name) {
    if ("LABEL".equals(name)) {
      return M68kStubElementTypesHolder.LABEL;
    }

    throw new RuntimeException("Unknown element type '" + name + "'");
  }

}