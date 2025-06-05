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

package com.yanncebron.m68kplugin.template;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.TemplateContextType;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFile;
import org.jetbrains.annotations.NotNull;

final class M68kTemplateContextType extends TemplateContextType {

  @SuppressWarnings("DialogTitleCapitalization")
  public M68kTemplateContextType() {
    super(M68kBundle.message("general.m68k.assembler"));
  }

  @Override
  public boolean isInContext(@NotNull TemplateActionContext templateActionContext) {
    return templateActionContext.getFile() instanceof M68kFile;
  }

}
