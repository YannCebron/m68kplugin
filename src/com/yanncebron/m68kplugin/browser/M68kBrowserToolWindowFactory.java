/*
 * Copyright 2022 The Authors
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

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

public class M68kBrowserToolWindowFactory implements ToolWindowFactory, DumbAware {

  private static final ExtensionPointName<M68kBrowserPaneEP> BROWSER_PANE_EP = ExtensionPointName.create("com.yanncebron.m68kplugin.browserPane");

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    final ContentManager contentManager = toolWindow.getContentManager();

    for (M68kBrowserPaneEP extension : BROWSER_PANE_EP.getExtensionList()) {
      final M68kBrowserPaneBase<?> pane = extension.getInstance();
      if (!pane.isAvailable(project)) continue;

      final Content content = contentManager.getFactory().createContent(pane, extension.getDisplayName(), false);
      content.setPreferredFocusableComponent(pane.getFocusComponent());
      content.setDisposer(pane);

      contentManager.addContent(content);
    }

  }

}
