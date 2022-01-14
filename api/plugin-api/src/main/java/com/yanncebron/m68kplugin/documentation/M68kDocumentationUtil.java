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

package com.yanncebron.m68kplugin.documentation;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.util.io.URLUtil;
import com.yanncebron.m68kplugin.M68kApiBundle;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.DefaultUrlSanitizer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public final class M68kDocumentationUtil {

  private static final Logger LOG = Logger.getInstance(M68kDocumentationUtil.class);

  @NonNls
  public static final String CSS = "<style>" +
    "h1 { font-weight: bold; font-size: 120%; } " +
    "h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } " +
    "h3 { padding-top: 10px; font-weight: bold; } " +
    "table { padding-bottom: 10px; white-space: nowrap; } " +
    "td { margin: 4px 0 0 0; padding: 0 0 0 0; }" +
    "th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } " +
    "em { font-style: italic; }" +
    "code { white-space: nowrap; }" +
    "</style>";

  @NonNls
  public static final String CHECK_MARK = "✓";

  public static Pair<String, String> getMarkdownContents(String docRoot, String markdownFileName) {
    final InputStream resource = M68kDocumentationUtil.class.getResourceAsStream(docRoot + markdownFileName + ".md");
    if (resource == null) {
      return Pair.create(null, M68kApiBundle.message("documentation.no.reference.doc", markdownFileName));
    }

    try {
      return Pair.create(FileUtil.loadTextAndClose(resource), null);
    } catch (IOException e) {
      String message = M68kApiBundle.message("documentation.error.loading.reference.doc", markdownFileName, e.getMessage());
      LOG.error(message, e);
      return Pair.create(null, message);
    }
  }

  public static String getHtmlForMarkdown(String docRoot, String markdownText) {
    List<Extension> extensions = Collections.singletonList(TablesExtension.create());
    Parser parser = Parser.builder().extensions(extensions).build();
    Node document = parser.parse(markdownText);
    HtmlRenderer renderer = HtmlRenderer.builder()
      .extensions(extensions)
      .urlSanitizer(new DefaultUrlSanitizer() {
        @Override
        public String sanitizeImageUrl(String url) {
          final String sanitizedUrl = super.sanitizeImageUrl(url);
          try {
            final URL resourceUrl = M68kDocumentationUtil.class.getResource(docRoot + sanitizedUrl);
            assert resourceUrl != null : sanitizedUrl;
            final InputStream is = URLUtil.openStream(resourceUrl);
            final File tempFile = FileUtil.createTempFile("m68k", ".png", true);
            StreamUtil.copy(is, new FileOutputStream(tempFile));
            return FileUtil.getUrl(tempFile);
          } catch (IOException e) {
            LOG.error("Error sanitizing URL '" + url + "'", e);
            return sanitizedUrl;
          }
        }
      })
      .sanitizeUrls(true)
      .build();
    return renderer.render(document);
  }
}
