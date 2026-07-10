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

import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.Predicates;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase;
import com.yanncebron.m68kplugin.lang.psi.M68kInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonic;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Predicate;

import static java.util.Map.entry;

public final class M68kInstructionDocsUtil {

  private static final String DOCS_MNEMONIC_ROOT = "/docs/mnemonic/";

  private static final Map<String, TokenSet> MNEMONIC_MAP = Map.ofEntries(
    entry("asl_asr", TokenSet.create(M68kTokenTypes.ASL, M68kTokenTypes.ASR)),
    entry("bcc", M68kTokenGroups.BCC_INSTRUCTIONS),
    entry("dbcc", M68kTokenGroups.DBCC_INSTRUCTIONS),
    entry("divs_divu", TokenSet.create(M68kTokenTypes.DIVS, M68kTokenTypes.DIVU)),
    entry("muls_mulu", TokenSet.create(M68kTokenTypes.MULS, M68kTokenTypes.MULU)),
    entry("lsl_lsr", TokenSet.create(M68kTokenTypes.LSL, M68kTokenTypes.LSR)),
    entry("rol_ror", TokenSet.create(M68kTokenTypes.ROL, M68kTokenTypes.ROR)),
    entry("roxl_roxr", TokenSet.create(M68kTokenTypes.ROXL, M68kTokenTypes.ROXR)),
    entry("scc", M68kTokenGroups.SCC_INSTRUCTIONS),
    entry("tbls_tblsn", TokenSet.create(M68kTokenTypes.TBLS, M68kTokenTypes.TBLSN)),
    entry("tblu_tblun", TokenSet.create(M68kTokenTypes.TBLU, M68kTokenTypes.TBLUN))
  );

  @NotNull
  public static String getMnemonicDoc(M68kMnemonic m68kMnemonic, boolean highlightMatching, Predicate<M68kMnemonic> predicate) {
    String docText = new M68kInstructionMnemonicDocsGenerator(m68kMnemonic, highlightMatching).generateHtmlDoc(predicate);
    return buildDoc(getMarkdownContents(m68kMnemonic), m68kMnemonic.getExternalName(), docText);
  }

  @NotNull
  static String getMnemonicDoc(M68kInstruction instruction) {
    IElementType elementType = instruction.getNode().getFirstChildNode().getElementType();
    String docText = new M68kInstructionMnemonicDocsGenerator(elementType).generateHtmlDoc(Predicates.alwaysTrue());
    String markdownFilename = findDocMnemonic(elementType);
    return buildDoc(M68kDocumentationUtil.getMarkdownContents(DOCS_MNEMONIC_ROOT, StringUtil.toLowerCase(markdownFilename)), StringUtil.toUpperCase(elementType.toString()), docText);
  }

  @NotNull
  public static String getMnemonicReferenceDoc(M68kMnemonic m68kMnemonic) {
    final Couple<String> markdownContents = getMarkdownContents(m68kMnemonic);
    if (markdownContents.getFirst() == null) {
      return markdownContents.getSecond();
    }

    String referenceHtml = M68kDocumentationUtil.getHtmlForMarkdown(DOCS_MNEMONIC_ROOT, markdownContents.getFirst(), M68kBrowserPaneBase.M68K_BROWSER_LINK_FUNCTION);
    return referenceHtml + M68kDocumentationUtil.MOTOROLA_FOOTER;
  }

  private static @NotNull String buildDoc(Couple<String> markdownContents, String mnemonicTitle, String docText) {
    String referenceHeading = markdownContents.getFirst() != null ?
      StringUtil.substringAfter(StringUtil.splitByLines(markdownContents.getFirst())[0], " - ") : "";

    return M68kDocumentationUtil.CSS +
      DocumentationMarkup.DEFINITION_START +
      "<h1><code>" + mnemonicTitle + "</code></h1>" +
      referenceHeading +
      DocumentationMarkup.DEFINITION_END +
      docText;
  }

  private static Couple<String> getMarkdownContents(M68kMnemonic originalMnemonic) {
    String markdownFilename;
    if (originalMnemonic.hasSpecialRegisterOperands()) {
      markdownFilename = originalMnemonic.getExternalName().replace(' ', '_');
    } else {
      markdownFilename = findDocMnemonic(originalMnemonic.elementType());
    }

    return M68kDocumentationUtil.getMarkdownContents(DOCS_MNEMONIC_ROOT, StringUtil.toLowerCase(markdownFilename));
  }

  @NotNull
  private static String findDocMnemonic(IElementType originalMnemonic) {
    for (Map.Entry<String, TokenSet> entry : MNEMONIC_MAP.entrySet()) {
      if (entry.getValue().contains(originalMnemonic)) {
        return entry.getKey();
      }
    }
    return originalMnemonic.toString();
  }

}
