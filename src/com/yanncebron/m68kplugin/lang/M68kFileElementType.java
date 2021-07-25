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

package com.yanncebron.m68kplugin.lang;

import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.tree.ILightStubFileElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class M68kFileElementType extends ILightStubFileElementType<PsiFileStub<M68kFile>> {

  public static final M68kFileElementType INSTANCE = new M68kFileElementType();

  @NonNls
  public static final String STUB_EXTERNAL_ID_PREFIX = "M68k.";

  public static final int STUB_VERSION = 15;

  @NonNls
  private static final String EXTERNAL_ID = STUB_EXTERNAL_ID_PREFIX + "FILE";

  private M68kFileElementType() {
    super(EXTERNAL_ID, M68kLanguage.INSTANCE);
  }

  @Override
  public int getStubVersion() {
    return STUB_VERSION;
  }

  @Override
  public @NotNull String getExternalId() {
    return EXTERNAL_ID;
  }
}
