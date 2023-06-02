/*
 * Copyright 2023 The Authors
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

package com.yanncebron.m68kplugin.editor.folding;

import com.intellij.application.options.editor.CodeFoldingOptionsProvider;
import com.intellij.openapi.options.BeanConfigurable;
import com.yanncebron.m68kplugin.M68kBundle;

final class M68kFoldingOptionsProvider extends BeanConfigurable<M68kFoldingSettings> implements CodeFoldingOptionsProvider {

  public M68kFoldingOptionsProvider() {
    super(M68kFoldingSettings.getInstance(), M68kBundle.message("general.m68k.assembler"));
    final M68kFoldingSettings settings = M68kFoldingSettings.getInstance();

    checkBox(M68kBundle.message("folding.option.zero.terminated.string.literal"),
      settings::isCollapseZeroTerminatedStringLiteral,
      settings::setCollapseZeroTerminatedStringLiteral);
  }
}
