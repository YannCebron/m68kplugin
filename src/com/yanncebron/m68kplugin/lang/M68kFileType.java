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

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.yanncebron.m68kplugin.M68kBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class M68kFileType extends LanguageFileType {

  public static final M68kFileType INSTANCE = new M68kFileType();

  private M68kFileType() {
    super(M68kLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return M68kBundle.message("general.m68k.assembler");
  }

  @NotNull
  @Override
  public String getDescription() {
    return M68kBundle.message("general.m68000.assembler");
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return "s";
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return M68kIcons.FILE;
  }
}
