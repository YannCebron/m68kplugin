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

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("ExtensionClassShouldBeFinalAndNonPublic")
public final class M68kInstructionDocumentationProvider extends AbstractDocumentationProvider {

  private static final String DOCS_MNEMONIC_ROOT = "/docs/mnemonic/";


  private static final Map<String, TokenSet> MNEMONIC_MAP = Map.of(
    "asl_asr", TokenSet.create(M68kTokenTypes.ASL, M68kTokenTypes.ASR),
    "bcc", M68kTokenGroups.BCC_INSTRUCTIONS,
    "dbcc", M68kTokenGroups.DBCC_INSTRUCTIONS,
    "divs_divu", TokenSet.create(M68kTokenTypes.DIVS, M68kTokenTypes.DIVU),
    "muls_mulu", TokenSet.create(M68kTokenTypes.MULS, M68kTokenTypes.MULU),
    "lsl_lsr", TokenSet.create(M68kTokenTypes.LSL, M68kTokenTypes.LSR),
    "rol_ror", TokenSet.create(M68kTokenTypes.ROL, M68kTokenTypes.ROR),
    "roxl_roxr", TokenSet.create(M68kTokenTypes.ROXL, M68kTokenTypes.ROXR),
    "scc", M68kTokenGroups.SCC_INSTRUCTIONS);

  @Override
  public @Nullable PsiElement getCustomDocumentationElement(@NotNull Editor editor,
                                                            @NotNull PsiFile file,
                                                            @Nullable PsiElement contextElement,
                                                            int targetOffset) {
    if (contextElement == null) return null;

    IElementType elementType = contextElement.getNode().getElementType();
    if (M68kTokenGroups.INSTRUCTIONS.contains(elementType) ||
      M68kTokenGroups.DATA_SIZES.contains(elementType)) {
      return PsiTreeUtil.getParentOfType(contextElement, M68kInstruction.class);
    }
    return null;
  }

  @Override
  public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
    if (!(element instanceof M68kInstruction instruction)) return null;

    // find specific matching mnemonic (valid instruction)
    M68kMnemonic instructionMnemonic = M68kMnemonicRegistry.getInstance().find(instruction);
    if (instructionMnemonic != null) {
      return getMnemonicDoc(instructionMnemonic, true);
    }

    // invalid instruction (e.g., PSI error, missing operands): show docs for all mnemonics
    IElementType elementType = instruction.getNode().getFirstChildNode().getElementType();
    String docText = new M68kInstructionMnemonicDocsGenerator(elementType).generateHtmlDoc();
    return buildDoc(getMarkdownContents(elementType), StringUtil.toUpperCase(elementType.toString()), docText);
  }

  @NotNull
  public static String getMnemonicDoc(M68kMnemonic m68kMnemonic, boolean highlightMatching) {
    String docText = new M68kInstructionMnemonicDocsGenerator(m68kMnemonic, highlightMatching).generateHtmlDoc();
    return buildDoc(getMarkdownContents(m68kMnemonic), m68kMnemonic.getExternalName(), docText);
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

  public static String getInstructionReferenceDoc(M68kMnemonic m68kMnemonic) {
    final Couple<String> markdownContents = getMarkdownContents(m68kMnemonic);
    if (markdownContents.getFirst() == null) {
      return markdownContents.getSecond();
    }

    String referenceHtml = M68kDocumentationUtil.getHtmlForMarkdown(DOCS_MNEMONIC_ROOT, markdownContents.getFirst(), M68kBrowserPaneBase.M68K_BROWSER_LINK_FUNCTION);
    return referenceHtml + M68kDocumentationUtil.MOTOROLA_FOOTER;
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

  private static Couple<String> getMarkdownContents(IElementType elementType) {
    String markdownFilename = findDocMnemonic(elementType);
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
