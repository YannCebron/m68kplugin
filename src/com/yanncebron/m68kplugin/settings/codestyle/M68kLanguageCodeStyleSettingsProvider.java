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

package com.yanncebron.m68kplugin.settings.codestyle;

import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {

  @NotNull
  @Override
  public Language getLanguage() {
    return M68kLanguage.INSTANCE;
  }

  @Nullable
  @Override
  public String getCodeSample(@NotNull LanguageCodeStyleSettingsProvider.SettingsType settingsType) {
    switch (settingsType) {
      case INDENT_SETTINGS:
        return CodeStyleAbstractPanel.readFromFile(getClass(), "IndentSettings.s");

      case WRAPPING_AND_BRACES_SETTINGS:
        return CodeStyleAbstractPanel.readFromFile(getClass(), "WrappingSettings.s");

      default:
        throw new IllegalStateException("Unexpected value: " + settingsType);
    }
  }

  @Nullable
  @Override
  public IndentOptionsEditor getIndentOptionsEditor() {
    return new IndentOptionsEditor(this);
  }

  @Override
  public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull LanguageCodeStyleSettingsProvider.SettingsType settingsType) {
    if (settingsType == SettingsType.INDENT_SETTINGS) {
      consumer.showStandardOptions(
        CodeStyleSettingsCustomizable.IndentOption.TAB_SIZE.toString(),
        CodeStyleSettingsCustomizable.IndentOption.USE_TAB_CHARACTER.toString()
      );
    }
  }
}
