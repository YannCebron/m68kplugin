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

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.platform.backend.documentation.DocumentationLinkHandler;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.documentation.LinkResolveResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.StringSelection;

/**
 * Handle link to another item by triggering selection in the current pane.
 * This is a bit hacky, but it's the only way to call into UI from DLH.
 * Also handle "Copy to clipboard" icon.
 */
final class M68kBrowserDocumentationLinkHandler implements DocumentationLinkHandler {

  @Override
  public @Nullable LinkResolveResult resolveLink(@NotNull DocumentationTarget target, @NotNull String url) {
    if (target instanceof M68kBrowserDocumentationTarget m68kBrowserDocumentationTarget) {
      if (url.startsWith(M68kBrowserPaneBase.M68K_BROWSER_ITEM_LINK_PREFIX)) {
        String item = StringUtil.substringAfter(url, M68kBrowserPaneBase.M68K_BROWSER_ITEM_LINK_PREFIX);
        m68kBrowserDocumentationTarget.selectItem(item);
      } else if (url.startsWith(M68kBrowserPaneBase.M68K_BROWSER_COPY_DATA_LINK_PREFIX)) {
        String copyData = StringUtil.substringAfter(url, M68kBrowserPaneBase.M68K_BROWSER_COPY_DATA_LINK_PREFIX);
        ApplicationManager.getApplication().invokeLater(() -> CopyPasteManager.getInstance().setContents(new StringSelection(copyData)));
        return LinkResolveResult.resolvedTarget(target);
      }
    }
    return null;
  }
}
