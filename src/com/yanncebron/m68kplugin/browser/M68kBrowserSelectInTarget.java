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

import com.intellij.ide.SelectInContext;
import com.intellij.ide.SelectInTarget;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.EmptyRunnable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import org.jetbrains.annotations.NonNls;

import javax.swing.*;

final class M68kBrowserSelectInTarget implements SelectInTarget, DumbAware {

  @Override
  public boolean canSelect(SelectInContext context) {
    VirtualFile virtualFile = context.getVirtualFile();
    if (virtualFile.getFileType() != M68kFileType.INSTANCE) return false;

    for (M68kBrowserPaneFactoryEP factory : M68kBrowserToolWindowFactory.BROWSER_PANE_FACTORY_EP.getExtensionList()) {
      M68kBrowserPaneFactory<?, ?> instance = factory.getInstance();
      if (!instance.isAvailable(context.getProject())) continue;

      if (instance.canSelect(context)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void selectIn(SelectInContext context, boolean requestFocus) {
    for (M68kBrowserPaneFactoryEP factory : M68kBrowserToolWindowFactory.BROWSER_PANE_FACTORY_EP.getExtensionList()) {
      M68kBrowserPaneFactory<?, ?> instance = factory.getInstance();
      if (!instance.isAvailable(context.getProject())) continue;

      if (!instance.canSelect(context)) continue;

      Object selectedItem = instance.getSelectedItem(context);
      if (selectedItem == null) return;

      ToolWindow toolWindow = ToolWindowManager.getInstance(context.getProject()).getToolWindow(M68kBrowserToolWindowFactory.TOOLWINDOW_ID);
      assert toolWindow != null;

      ContentManager contentManager = toolWindow.getContentManager();
      Content content = contentManager.findContent(factory.getDisplayName());
      assert content != null : factory;
      contentManager.setSelectedContent(content);

      JComponent component = content.getComponent();
      M68kBrowserPaneBase<?> browserPaneBase = ObjectUtils.tryCast(component, M68kBrowserPaneBase.class);
      assert browserPaneBase != null : component;
      browserPaneBase.selectItem(selectedItem);

      if (requestFocus) {
        toolWindow.activate(EmptyRunnable.getInstance());
      }
      return;
    }
  }

  @Override
  public String toString() {
    return M68kBundle.message("toolwindow.stripe.M68kBrowser");
  }

  @Override
  public float getWeight() {
    return 200;
  }

  @Override
  public @NonNls String getToolWindowId() {
    return M68kBrowserToolWindowFactory.TOOLWINDOW_ID;
  }

}
