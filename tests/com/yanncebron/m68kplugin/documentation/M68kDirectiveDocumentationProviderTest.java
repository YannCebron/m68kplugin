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

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PairConsumer;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kSoDirective;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("SpellCheckingInspection")
public class M68kDirectiveDocumentationProviderTest extends BasePlatformTestCase {

  public void testDirectivesWithoutReferenceDoc() {
    assertNoReferenceDoc(M68kTokenGroups.DIRECTIVES,
      M68kTokenTypes.ADDWATCH,
      M68kTokenTypes.AUTO,
      M68kTokenTypes.JUMPERR,
      M68kTokenTypes.JUMPPTR,
      M68kTokenTypes.LOAD,
      M68kTokenTypes.MASK2
    );

    assertNoReferenceDoc(M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES /* none missing */);
  }

  private void assertNoReferenceDoc(TokenSet tokenSet, IElementType... missing) {
    Set<IElementType> noReferenceDoc = new HashSet<>();
    for (IElementType directive : tokenSet.getTypes()) {
      String referenceDoc = M68kDirectiveDocumentationProvider.getDirectiveDoc(directive);
      if (referenceDoc.contains(M68kDocumentationUtil.CONTRIBUTION_FOOTER)) {
        noReferenceDoc.add(directive);
      }
    }
    assertSameElements(noReferenceDoc, missing);
  }

  public void testMacroCallDirectiveNoReferenceDocAvailable() {
    doTest(" macro<caret>Name param", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertNull(doc);
    });
  }

  public void testDirectiveNoReferenceDocAvailableFallback() {
    doTest(" add<caret>watch", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px; } strong { font-weight: bold; }blockquote { padding-left: 10px; padding-right:10px; padding-bottom: 5px; }</style><h1>ADDWATCH</h1><p>No reference documentation available for 'addwatch'</p><br><br><a href=\"http://sun.hasenbraten.de/vasm/release/vasm_4.html#Directives-2\">vasm directives docs 1</a><br><br><a href=\"http://sun.hasenbraten.de/vasm/release/vasm_23.html#Extensions-3\">vasm directives docs 2</a><br><br><a href=\"https://github.com/prb28/m68k-instructions-documentation\">Contribute to m68k-instructions-documentation project</a>", doc);
    });
  }

  public void testEremDirectiveReferenceDoc() {
    doTest(" e<caret>rem", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px; } strong { font-weight: bold; }blockquote { padding-left: 10px; padding-right:10px; padding-bottom: 5px; }</style><h1>EREM</h1>\n" +
        "<h2>Syntax</h2>\n" +
        "<pre><code class=\"language-assembly\">erem\n" +
        "</code></pre>\n" +
        "<h2>Description</h2>\n" +
        "<p>Ends an outcommented block from <a rel=\"nofollow\" href=\"psi_element://rem\">rem</a>. Assembly will continue.</p>\n", doc);
    });
  }

  public void testIfmiConditionalAssemblyDirectiveReferenceDoc() {
    doTest(" if<caret>mi", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px; } strong { font-weight: bold; }blockquote { padding-left: 10px; padding-right:10px; padding-bottom: 5px; }</style><h1>IFMI</h1>\n" +
        "<h2>Syntax</h2>\n" +
        "<pre><code class=\"language-assembly\">ifmi &lt;expression&gt;\n" +
        "</code></pre>\n" +
        "<h2>Description</h2>\n" +
        "<p>Conditionally assemble the following lines if <code>&lt;expression&gt;</code> is less than zero. Equivalent to <a rel=\"nofollow\" href=\"psi_element://iflt\">iflt</a>.</p>\n", doc);
    });
  }

  public void testEremDirectiveReferenceDocForBrowser() {
    String directiveDoc = M68kDirectiveDocumentationProvider.getDirectiveDoc(M68kTokenTypes.EREM);
    assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px; } strong { font-weight: bold; }blockquote { padding-left: 10px; padding-right:10px; padding-bottom: 5px; }</style><h1>EREM</h1>\n" +
      "<h2>Syntax</h2>\n" +
      "<pre><code class=\"language-assembly\">erem\n" +
      "</code></pre>\n" +
      "<h2>Description</h2>\n" +
      "<p>Ends an outcommented block from <a rel=\"nofollow\" href=\"rem.md\">rem</a>. Assembly will continue.</p>\n", directiveDoc);
  }

  public void testEquDirectiveReferenceDoc() {
    doTest("label e<caret>qu 42", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px; } strong { font-weight: bold; }blockquote { padding-left: 10px; padding-right:10px; padding-bottom: 5px; }</style><h1>EQU</h1>\n" +
        "<h2>Syntax</h2>\n" +
        "<pre><code class=\"language-assembly\">&lt;symbol&gt; equ &lt;expression&gt;\n" +
        "</code></pre>\n" +
        "<h2>Description</h2>\n" +
        "<p>Define a new program symbol with the name <code>&lt;symbol&gt;</code> and assign to it the value of <code>&lt;expression&gt;</code>.\n" +
        "Defining <code>&lt;symbol&gt;</code> twice will cause an error.</p>\n", doc);
    });
  }

  // '=' -> 'equ'
  public void testEqualSignDirectiveReferenceDoc() {
    doTest("label <caret>= 42", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px; } strong { font-weight: bold; }blockquote { padding-left: 10px; padding-right:10px; padding-bottom: 5px; }</style><h1>EQU</h1>\n" +
        "<h2>Syntax</h2>\n" +
        "<pre><code class=\"language-assembly\">&lt;symbol&gt; equ &lt;expression&gt;\n" +
        "</code></pre>\n" +
        "<h2>Description</h2>\n" +
        "<p>Define a new program symbol with the name <code>&lt;symbol&gt;</code> and assign to it the value of <code>&lt;expression&gt;</code>.\n" +
        "Defining <code>&lt;symbol&gt;</code> twice will cause an error.</p>\n", doc);
    });
  }

  public void testSoDirectiveReferenceDocFromLink() {
    PsiElement linkDocElement = new M68kDirectiveDocumentationProvider().getDocumentationElementForLink(getPsiManager(), "so", null);
    assertInstanceOf(linkDocElement, M68kSoDirective.class);
  }

  private void doTest(String source, PairConsumer<PsiElement, DocumentationProvider> consumer) {
    myFixture.configureByText("a.s", source);

    final PsiElement docElement = DocumentationManager.getInstance(getProject()).findTargetElement(myFixture.getEditor(), myFixture.getFile());
    DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

    consumer.consume(docElement, provider);
  }

  private PsiElement getOriginalElement() {
    return myFixture.getFile().findElementAt(myFixture.getEditor().getCaretModel().getOffset());
  }
}
