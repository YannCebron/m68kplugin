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

package com.yanncebron.m68kplugin.template;

import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.psi.PsiFile;
import com.yanncebron.m68kplugin.lang.M68kFile;
import org.jetbrains.annotations.NotNull;

public class M68kTemplateContextType extends TemplateContextType {

  public M68kTemplateContextType() {
    super("M68K", "M68k Assembler");
  }

  @Override
  public boolean isInContext(@NotNull PsiFile file, int offset) {
    return file instanceof M68kFile;
  }
}
