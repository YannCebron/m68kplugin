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

import com.intellij.lang.documentation.QuickDocHighlightingHelper;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.SmartList;
import com.intellij.util.io.URLUtil;
import com.yanncebron.m68kplugin.M68kApiBundle;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class M68kDocumentationUtil {

  private static final Logger LOG = Logger.getInstance(M68kDocumentationUtil.class);

  /**
   * Make tables non-wrappable.
   */
  @NonNls
  public static final String CSS = "<style>" +
    "table { white-space: nowrap; } " +
    "blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }" +
    "</style>";

  @NonNls
  public static final String CHECK_MARK = "✓";

  @NonNls
  public static final String NON_BREAK_PERIOD = "&#8228;";

  @NonNls
  public static final String CONTRIBUTION_FOOTER =
    "<br><br><a href=\"http://sun.hasenbraten.de/vasm/release/vasm_4.html#Directives-2\">vasm directives docs 1</a>" +
      "<br><br><a href=\"http://sun.hasenbraten.de/vasm/release/vasm_23.html#Extensions-3\">vasm directives docs 2</a>" +
      "<br><br><a href=\"https://github.com/prb28/m68k-instructions-documentation\">Contribute to m68k-instructions-documentation project</a>";

  @NonNls
  public static final String MOTOROLA_FOOTER =
    "<br><br><small><em>From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with permission.</em></small>";

  public static Couple<String> getMarkdownContents(String docRoot, String markdownFileName) {
    final InputStream resource = M68kDocumentationUtil.class.getResourceAsStream(docRoot + markdownFileName + ".md");
    if (resource == null) {
      return Couple.of(null, M68kApiBundle.message("documentation.no.reference.doc", markdownFileName));
    }

    try {
      String text = FileUtil.loadTextAndClose(resource);
      text = text.replace("# ", "## ");
      return Couple.of(text, null);
    } catch (IOException e) {
      String message = M68kApiBundle.message("documentation.error.loading.reference.doc", markdownFileName, e.getMessage());
      LOG.error(message, e);
      return Couple.of(null, message);
    }
  }

  /**
   * @param docRoot      Markdown files root.
   * @param markdownText Markdown text to render.
   * @param urlFunction  Used to modify existing links.
   * @param project      Project to enable syntax highlighting for 'assembly' code blocks. {@code null} if not used.
   * @return HTML documentation text.
   */
  public static String getHtmlForMarkdown(String docRoot, String markdownText, Function<String, String> urlFunction, @Nullable Project project) {
    List<Extension> extensions = new SmartList<>(TablesExtension.create());
    if (project != null) {
      extensions.add(new FencedCodeBlockExtension(project));
    }

    Parser parser = Parser.builder().extensions(extensions).build();
    Node document = parser.parse(markdownText);
    HtmlRenderer renderer = HtmlRenderer.builder()
      .extensions(extensions)
      .urlSanitizer(new DefaultUrlSanitizer() {

        @Override
        public String sanitizeLinkUrl(String url) {
          if (StringUtil.startsWith(url, "http")) {
            return super.sanitizeLinkUrl(url);
          }
          if (StringUtil.endsWith(url, "png")) {
            return super.sanitizeLinkUrl(url);
          }

          return urlFunction.apply(super.sanitizeLinkUrl(url));
        }

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

  /**
   * Render code blocks with 'assembly' language using IDE's syntax highlighting.
   */
  @SuppressWarnings("ClassCanBeRecord")
  private static class FencedCodeBlockExtension implements HtmlRenderer.HtmlRendererExtension {

    private final Project project;

    private FencedCodeBlockExtension(@NotNull Project project) {
      this.project = project;
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
      rendererBuilder.nodeRendererFactory(context -> new FencedCodeBlockNodeRenderer(context, project));
    }

    private static class FencedCodeBlockNodeRenderer implements NodeRenderer {

      private final HtmlNodeRendererContext context;
      private final Project project;

      private FencedCodeBlockNodeRenderer(HtmlNodeRendererContext context, Project project) {
        this.context = context;
        this.project = project;
      }

      @Override
      public Set<Class<? extends Node>> getNodeTypes() {
        return Set.of(FencedCodeBlock.class);
      }

      @Override
      public void render(Node node) {
        FencedCodeBlock fencedCodeBlock = ObjectUtils.tryCast(node, FencedCodeBlock.class);
        assert fencedCodeBlock != null : node;

        var info = fencedCodeBlock.getInfo();
        if (!"assembly".equals(info)) {
          new CoreHtmlNodeRenderer(context).visit(fencedCodeBlock);
          return;
        }

        HtmlWriter htmlWriter = context.getWriter();
        htmlWriter.line();
        String m68k = QuickDocHighlightingHelper.getStyledCodeBlock(project, M68kLanguage.INSTANCE, fencedCodeBlock.getLiteral());
        htmlWriter.raw(m68k);
        htmlWriter.line();
      }
    }
  }
}
