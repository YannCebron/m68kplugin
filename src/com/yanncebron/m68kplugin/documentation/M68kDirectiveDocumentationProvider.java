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

package com.yanncebron.m68kplugin.documentation;

import com.intellij.codeInsight.documentation.DocumentationManagerProtocol;
import com.intellij.lang.ASTNode;
import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ExtensionClassShouldBeFinalAndNonPublic")
public final class M68kDirectiveDocumentationProvider extends AbstractDocumentationProvider {

  private static final String DOCS_MNEMONIC_ROOT = "/docs/directives/";

  @Override
  public @Nullable PsiElement getCustomDocumentationElement(@NotNull Editor editor,
                                                            @NotNull PsiFile file,
                                                            @Nullable PsiElement contextElement,
                                                            int targetOffset) {
    if (contextElement != null &&
      M68kTokenGroups.DIRECTIVES.contains(contextElement.getNode().getElementType())) {
      return PsiTreeUtil.getParentOfType(contextElement, M68kDirective.class);
    }
    return null;
  }

  @Override
  public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
    if (!(element instanceof M68kDirective directive)) return null;

    if (directive instanceof M68kMacroCallDirective) return null;

    ASTNode directiveNode = directive.getNode().findChildByType(M68kTokenGroups.DIRECTIVES);
    assert directiveNode != null : directive.getText();

    final IElementType directiveType = directiveNode.getElementType();
    return getDirectiveDoc(directiveType, false);
  }

  @Override
  public @Nullable PsiElement getDocumentationElementForLink(PsiManager psiManager, String link, PsiElement context) {
    // create fake M68kDirective from given "link" = directive text, add "dummy label" as required for some directives
    return M68kElementFactory.createFile(psiManager.getProject(), "dummy " + link).getLastChild();
  }

  @NotNull
  public static String getDirectiveDoc(IElementType directiveType) {
    return getDirectiveDoc(directiveType, true);
  }

  @NotNull
  private static String getDirectiveDoc(IElementType directiveType, boolean forBrowserPane) {
    directiveType = directiveType == M68kTokenTypes.EQ_DIRECTIVE ? M68kTokenTypes.EQU : directiveType;

    String directiveText = directiveType.toString();
    Couple<String> contents = M68kDocumentationUtil.getMarkdownContents(DOCS_MNEMONIC_ROOT, StringUtil.toLowerCase(directiveText));
    if (contents.getFirst() == null) {
      return M68kDocumentationUtil.CSS +
        "<h1>" + StringUtil.toUpperCase(directiveText) + "</h1>" +
        "<p>" + contents.getSecond() +
        M68kDocumentationUtil.CONTRIBUTION_FOOTER;
    }

    if (forBrowserPane) {
      return M68kDocumentationUtil.CSS + M68kDocumentationUtil.getHtmlForMarkdown(DOCS_MNEMONIC_ROOT, contents.getFirst());
    }

    // provide "inline" link to handle in getDocumentationElementForLink()
    return M68kDocumentationUtil.CSS + M68kDocumentationUtil.getHtmlForMarkdown(DOCS_MNEMONIC_ROOT, contents.getFirst(),
      link -> DocumentationManagerProtocol.PSI_ELEMENT_PROTOCOL + StringUtil.substringBefore(link, ".md"));
  }

}
