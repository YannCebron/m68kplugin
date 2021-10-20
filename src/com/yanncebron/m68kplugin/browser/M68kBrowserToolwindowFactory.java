/*
 * Copyright 2021 The Authors
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

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowContentUiType;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.yanncebron.m68kplugin.M68kBundle;
import org.jetbrains.annotations.NotNull;

public class M68kBrowserToolwindowFactory implements ToolWindowFactory, DumbAware {

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    toolWindow.setContentUiType(ToolWindowContentUiType.TABBED, null);

    final ContentManager contentManager = toolWindow.getContentManager();

    final M68kMnemonicsPanel mnemonicsPanel = new M68kMnemonicsPanel();
    final Content mnemonics = contentManager.getFactory().createContent(mnemonicsPanel, M68kBundle.message("toolwindow.tab.mnemonic"), false);
    mnemonics.setPreferredFocusableComponent(mnemonicsPanel.getFocusComponent());
    contentManager.addContent(mnemonics);

    if (!ApplicationManager.getApplication().isInternal()) return;

    final M68kDirectivesPanel directivesPanel = new M68kDirectivesPanel();
    final Content directives = contentManager.getFactory().createContent(directivesPanel, M68kBundle.message("toolwindow.tab.directives"), false);
    directives.setPreferredFocusableComponent(directivesPanel.getFocusComponent());
    contentManager.addContent(directives);
  }

}
