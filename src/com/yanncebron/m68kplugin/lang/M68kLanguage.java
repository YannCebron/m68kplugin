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

package com.yanncebron.m68kplugin.lang;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.yanncebron.m68kplugin.M68kBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kLanguage extends Language {

  @NonNls
  public static final String ID = "M68k";

  public static final M68kLanguage INSTANCE = new M68kLanguage();

  private M68kLanguage() {
    super(ID);
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return M68kBundle.message("general.m68k.assembler");
  }

  @Override
  public boolean isCaseSensitive() {
    return true;
  }

  @Nullable
  @Override
  public LanguageFileType getAssociatedFileType() {
    return M68kFileType.INSTANCE;
  }
}
