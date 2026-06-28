/*
 * Copyright 2026 The Authors
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

package com.yanncebron.m68kplugin.browser;

import com.intellij.diagnostic.PluginException;
import com.intellij.ide.SelectInContext;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public interface M68kBrowserPaneFactory<T extends M68kBrowserPaneBase<V>, V> {

  default boolean isAvailable(Project project) {
    return true;
  }

  T createPane(Project project);

  /**
   * @return {@code true} if element from "Select In..." context can be selected in this pane
   */
  default boolean canSelect(SelectInContext context) {
    return false;
  }

  default @Nullable V getSelectedItem(SelectInContext context) {
    throw PluginException.createByClass("getSelectedItem() not implemented: " + this, null, this.getClass());
  }

}
