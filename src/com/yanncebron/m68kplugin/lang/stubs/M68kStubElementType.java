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

import com.intellij.psi.stubs.ILightStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.yanncebron.m68kplugin.lang.M68kFileElementType;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import org.jetbrains.annotations.NotNull;

abstract class M68kStubElementType<StubT extends StubElement<? extends M68kPsiElement>, PsiT extends M68kPsiElement>
  extends ILightStubElementType<StubT, PsiT> {

  M68kStubElementType(@NotNull String debugName) {
    super(debugName, M68kLanguage.INSTANCE);
  }

  @Override
  public final @NotNull String getExternalId() {
    return M68kFileElementType.STUB_EXTERNAL_ID_PREFIX + toString();
  }

  @NotNull
  @Override
  public final StubT createStub(@NotNull PsiT psi, StubElement parentStub) {
    final String message = "Should not be called. Element=" + psi + "; class" + psi.getClass() + "; file=" + (psi.isValid() ? psi.getContainingFile() : "-");
    throw new UnsupportedOperationException(message);
  }
}
