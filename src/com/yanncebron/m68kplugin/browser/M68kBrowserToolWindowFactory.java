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

package com.yanncebron.m68kplugin.browser;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.ui.content.ContentManagerListener;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

final class M68kBrowserToolWindowFactory implements ToolWindowFactory, DumbAware {

  private static final ExtensionPointName<M68kBrowserPaneFactoryEP> BROWSER_PANE_FACTORY_EP = ExtensionPointName.create("com.yanncebron.m68kplugin.browserPaneFactory");

  private static final Key<String> PANE_FQN_KEY = Key.create("M68kBrowserToolWindowFactory.pane.fqn");

  @NonNls
  private static final String ACTIVE_PANE = "M68kBrowserToolWindowFactory.activePane";

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    final ContentManager contentManager = toolWindow.getContentManager();

    String activePane = PropertiesComponent.getInstance(project).getValue(ACTIVE_PANE);

    for (M68kBrowserPaneFactoryEP extension : BROWSER_PANE_FACTORY_EP.getExtensionList()) {
      M68kBrowserPaneFactory<?> factory = extension.getInstance();
      if (!factory.isAvailable(project)) continue;

      M68kBrowserPaneBase<?> pane = factory.createPane(project);

      final Content content = contentManager.getFactory().createContent(pane, extension.getDisplayName(), false);
      content.setPreferredFocusableComponent(pane.getFocusComponent());
      content.setDisposer(pane);
      contentManager.addContent(content);

      String paneKey = pane.getClass().getSimpleName();
      content.putUserData(PANE_FQN_KEY, paneKey);
      if (paneKey.equals(activePane)) {
        contentManager.setSelectedContent(content);
      }
    }

    toolWindow.addContentManagerListener(new ContentManagerListener() {
      @Override
      public void selectionChanged(@NotNull ContentManagerEvent event) {
        if (event.getOperation() != ContentManagerEvent.ContentOperation.add) return;

        String paneKey = event.getContent().getUserData(PANE_FQN_KEY);
        PropertiesComponent.getInstance(project).setValue(ACTIVE_PANE, paneKey);
      }
    });
  }

}
