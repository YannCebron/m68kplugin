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

package com.yanncebron.m68kplugin.documentation;

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
 * Handle "Copy Data" links with prefix {@link M68kDocumentationUtil#M68K_COPY_DATA_LINK_PREFIX}.
 */
final class M68kDocumentationCopyDataLinkHandler implements DocumentationLinkHandler {

  @Override
  public @Nullable LinkResolveResult resolveLink(@NotNull DocumentationTarget target, @NotNull String url) {
    if (url.startsWith(M68kDocumentationUtil.M68K_COPY_DATA_LINK_PREFIX)) {
      String copyData = StringUtil.substringAfter(url, M68kDocumentationUtil.M68K_COPY_DATA_LINK_PREFIX);
      ApplicationManager.getApplication().invokeLater(() -> CopyPasteManager.getInstance().setContents(new StringSelection(copyData)));
      return LinkResolveResult.resolvedTarget(target);
    }
    return null;
  }
}
