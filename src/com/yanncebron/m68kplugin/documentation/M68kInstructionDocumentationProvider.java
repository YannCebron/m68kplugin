/*
 * Copyright 2020 The Authors
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

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.io.URLUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.DefaultUrlSanitizer;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class M68kInstructionDocumentationProvider extends AbstractDocumentationProvider {

  private static final String DOCS_MNEMONIC_ROOT = "/docs/mnemonic/";

  private static final String CSS = "<style>" +
    "h1 { font-weight: bold; font-size: 120%; } " +
    "h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } " +
    "h3 { padding-top: 10px; font-weight: bold; } " +
    "table { padding-bottom: 10px; } " +
    "td { margin: 5px;} " +
    "em { font-style: italic;}" +
    "</style>";


  private static final Map<String, TokenSet> MNEMONIC_MAP = ContainerUtil.<String, TokenSet>immutableMapBuilder()
    .put("asl_asr", TokenSet.create(M68kTokenTypes.ASL, M68kTokenTypes.ASR))
    .put("bcc", M68kTokenGroups.BCC_INSTRUCTIONS)
    .put("dbcc", M68kTokenGroups.DBCC_INSTRUCTIONS)
    .put("divs_divu", TokenSet.create(M68kTokenTypes.DIVS, M68kTokenTypes.DIVU))
    .put("muls_mulu", TokenSet.create(M68kTokenTypes.MULS, M68kTokenTypes.MULU))
    .put("lsl_lsr", TokenSet.create(M68kTokenTypes.LSL, M68kTokenTypes.LSR))
    .put("rol_ror", TokenSet.create(M68kTokenTypes.ROL, M68kTokenTypes.ROR))
    .put("roxl_roxr", TokenSet.create(M68kTokenTypes.ROXL, M68kTokenTypes.ROXR))
    .put("scc", M68kTokenGroups.SCC_INSTRUCTIONS)
    .build();

  @NotNull
  private static String findDocMnemonic(IElementType originalMnemonic) {
    for (Map.Entry<String, TokenSet> entry : MNEMONIC_MAP.entrySet()) {
      if (entry.getValue().contains(originalMnemonic)) {
        return entry.getKey();
      }
    }
    return StringUtil.toLowerCase(originalMnemonic.toString());
  }

  @Override
  public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
    if (!(element instanceof M68kInstruction)) return null;

    M68kInstruction instruction = (M68kInstruction) element;

    final IElementType originalMnemonic = instruction.getNode().getFirstChildNode().getElementType();
    String docMnemonic = findDocMnemonic(originalMnemonic);

    final InputStream resource = getClass().getResourceAsStream(DOCS_MNEMONIC_ROOT + docMnemonic + ".md");
    if (resource == null) {
      return "No documentation for '" + docMnemonic + "' ('" + originalMnemonic + "')";
    }

    final String text;
    try {
      text = FileUtil.loadTextAndClose(resource);
    } catch (IOException e) {
      return "Error loading documentation for '" + docMnemonic + "': " + e.getMessage();
    }

    List<Extension> extensions = Collections.singletonList(TablesExtension.create());
    Parser parser = Parser.builder().extensions(extensions).build();
    Node document = parser.parse(text);
    HtmlRenderer renderer = HtmlRenderer.builder()
      .extensions(extensions)
      .urlSanitizer(new DefaultUrlSanitizer() {
        @Override
        public String sanitizeImageUrl(String url) {
          final String sanitizedUrl = super.sanitizeImageUrl(url);
          try {
            final InputStream is = URLUtil.openStream(getClass().getResource(DOCS_MNEMONIC_ROOT + sanitizedUrl));
            final File tempFile = FileUtil.createTempFile("m68k", ".png", true);
            StreamUtil.copyStreamContent(is, new FileOutputStream(tempFile));
            return FileUtil.getUrl(tempFile);
          } catch (IOException e) {
            return sanitizedUrl;
          }
        }
      })
      .sanitizeUrls(true)
      .build();
    final String html = renderer.render(document);
    return CSS + html;
  }

}
