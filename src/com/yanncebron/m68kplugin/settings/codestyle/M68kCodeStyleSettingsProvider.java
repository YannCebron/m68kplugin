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

import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.psi.codeStyle.CodeStyleConfigurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

  @Nullable
  @Override
  public CustomCodeStyleSettings createCustomSettings(CodeStyleSettings settings) {
    return new M68kCodeStyleSettings(settings);
  }

  @Nullable
  @Override
  public String getConfigurableDisplayName() {
    return M68kLanguage.INSTANCE.getDisplayName();
  }

  @NotNull
  @Override
  public CodeStyleConfigurable createConfigurable(@NotNull CodeStyleSettings settings, @NotNull CodeStyleSettings modelSettings) {
    return new CodeStyleAbstractConfigurable(settings, modelSettings, getConfigurableDisplayName()) {

      @Override
      protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
        return new TabbedLanguageCodeStylePanel(M68kLanguage.INSTANCE, getCurrentSettings(), settings) {
          @Override
          protected void initTabs(CodeStyleSettings settings) {
            addIndentOptionsTab(settings);
            addWrappingAndBracesTab(settings);
          }

          @Override
          protected void addWrappingAndBracesTab(CodeStyleSettings settings) {
            addTab(new MyWrappingAndBracesPanel(settings) {
              @Override
              protected @NotNull String getTabTitle() {
                return M68kBundle.message("codestyle.settings.tab.other");
              }
            });
          }
        };
      }
    };
  }
}
