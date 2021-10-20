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

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.io.StreamUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.io.URLUtil;
import com.yanncebron.m68kplugin.M68kBundle;
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
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class M68kInstructionDocumentationProvider extends AbstractDocumentationProvider {

  private static final String DOCS_MNEMONIC_ROOT = "/docs/mnemonic/";

  public static final String CSS = "<style>" +
    "h1 { font-weight: bold; font-size: 120%; } " +
    "h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } " +
    "h3 { padding-top: 10px; font-weight: bold; } " +
    "table { padding-bottom: 10px; white-space: nowrap; } " +
    "td { margin: 4px 0 0 0; padding: 0 0 0 0; }" +
    "th { text-align: left; margin: 2px; } " +
    "em { font-style: italic; }" +
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

  @Override
  public @Nullable PsiElement getCustomDocumentationElement(@NotNull Editor editor,
                                                            @NotNull PsiFile file,
                                                            @Nullable PsiElement contextElement,
                                                            int targetOffset) {
    if (contextElement != null &&
      M68kTokenGroups.INSTRUCTIONS.contains(contextElement.getNode().getElementType())) {
      return PsiTreeUtil.getParentOfType(contextElement, M68kInstruction.class);
    }
    return null;
  }

  @Override
  public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
    if (!(element instanceof M68kInstruction)) return null;

    M68kInstruction instruction = (M68kInstruction) element;
    final IElementType originalMnemonic = instruction.getNode().getFirstChildNode().getElementType();

    return CSS + getMnemonicDoc(originalMnemonic, instruction);
  }

  @NotNull
  public static String getMnemonicDoc(IElementType originalMnemonic, @Nullable M68kInstruction instruction) {
    String mnemonicDoc = new M68kInstructionMnemonicDocsGenerator(originalMnemonic, instruction).generateHtmlDoc();

    final Pair<String, String> markdownContents = getMarkdownContents(originalMnemonic);

    String referenceHeading = markdownContents.getFirst() != null ?
      StringUtil.substringAfter(StringUtil.splitByLines(markdownContents.getFirst())[0], "# ") : StringUtil.toUpperCase(originalMnemonic.toString());

    return "<h1>" + referenceHeading + "</h1><br>" + mnemonicDoc;
  }

  public static String getInstructionReferenceDoc(IElementType originalMnemonic) {
    final Pair<String, String> markdownContents = getMarkdownContents(originalMnemonic);
    if (markdownContents.getFirst() == null) {
      return markdownContents.getSecond();
    }

    List<Extension> extensions = Collections.singletonList(TablesExtension.create());
    Parser parser = Parser.builder().extensions(extensions).build();
    Node document = parser.parse(markdownContents.getFirst());
    HtmlRenderer renderer = HtmlRenderer.builder()
      .extensions(extensions)
      .urlSanitizer(new DefaultUrlSanitizer() {
        @Override
        public String sanitizeImageUrl(String url) {
          final String sanitizedUrl = super.sanitizeImageUrl(url);
          try {
            final URL resourceUrl = M68kInstructionDocumentationProvider.class.getResource(DOCS_MNEMONIC_ROOT + sanitizedUrl);
            assert resourceUrl != null : sanitizedUrl;
            final InputStream is = URLUtil.openStream(resourceUrl);
            final File tempFile = FileUtil.createTempFile("m68k", ".png", true);
            StreamUtil.copy(is, new FileOutputStream(tempFile));
            return FileUtil.getUrl(tempFile);
          } catch (IOException e) {
            return sanitizedUrl;
          }
        }
      })
      .sanitizeUrls(true)
      .build();
    return renderer.render(document);
  }

  private static Pair<String, String> getMarkdownContents(IElementType originalMnemonic) {
    String docMnemonic = findDocMnemonic(originalMnemonic);

    final InputStream resource = M68kInstructionDocumentationProvider.class.getResourceAsStream(DOCS_MNEMONIC_ROOT + docMnemonic + ".md");
    if (resource == null) {
      return Pair.create(null, M68kBundle.message("documentation.no.reference.doc", docMnemonic, originalMnemonic));
    }

    try {
      return Pair.create(FileUtil.loadTextAndClose(resource), null);
    } catch (IOException e) {
      return Pair.create(null, M68kBundle.message("documentation.error.loading.reference.doc", docMnemonic, e.getMessage()));
    }
  }

  @NotNull
  private static String findDocMnemonic(IElementType originalMnemonic) {
    for (Map.Entry<String, TokenSet> entry : MNEMONIC_MAP.entrySet()) {
      if (entry.getValue().contains(originalMnemonic)) {
        return entry.getKey();
      }
    }
    return StringUtil.toLowerCase(originalMnemonic.toString());
  }

}
