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
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kLabelDocumentationProviderTest extends BasePlatformTestCase {

  public void testGlobalLabelQuickDoc() {
    doTestQuickNavigateInfo("my<caret>Label", "label \"myLabel\" [a.s]");
  }

  public void testLocalLabelQuickDoc() {
    doTestQuickNavigateInfo(".myLocal<caret>Label", "local label \"myLocalLabel\" [a.s]");
  }

  public void testEquLabelQuickDoc() {
    doTestQuickNavigateInfo("equ<caret>Label equ 42",
      "label \"equLabel\" [a.s]<br><code><span style=\"color:#0000ff;\">42</span></code>");
  }

  public void testEquLabelUndefinedValueQuickDoc() {
    doTestQuickNavigateInfo("equ<caret>Label equ ",
      "label \"equLabel\" [a.s]<br><code>undefined value</code>");
  }

  public void testEqualsLabelQuickDoc() {
    doTestQuickNavigateInfo("equ<caret>Label = 42",
      "label \"equLabel\" [a.s]<br><code><span style=\"color:#0000ff;\">42</span></code>");
  }

  public void testSetLabelQuickDoc() {
    doTestQuickNavigateInfo("set<caret>Label set 42",
      "label \"setLabel\" [a.s]<br><code><span style=\"color:#0000ff;\">42</span></code>");
  }

  public void testEqurLabelQuickDoc() {
    doTestQuickNavigateInfo("equr<caret>Label equr d7",
      "label \"equrLabel\" [a.s]<br><code><span style=\"\">d7</span></code>");
  }

  public void testMacroLabelQuickDoc() {
    doTestQuickNavigateInfo("macro<caret>Label macro", "macro \"macroLabel\" [a.s]");
  }

  public void testLabelDoc() {
    doTestDoc(
      "* not relevant doc" +
        "\n\n" +
        "*************************************************************************\n" +
        "*\t\t\t\t\t\t\t\t\t*\n" +
        "***       Set default function key definitions:\t\t\t\t\t***\n" +
        "*\t\t\t\t\t\t\t\t\t*\n" +
        "*\n" +
        "* newline before\n" +
        "*************************************************************************\n" +
        "\n" +
        "la<caret>bel",
      "<div class='definition'><pre>label</pre></div><div class='content'>Set default function key definitions:<br><br>newline before</div>");
  }

  public void testLabelNoCommentDoc() {
    doTestDoc("la<caret>bel",
      "<div class='definition'><pre>label</pre></div><div class='content'></div>");
  }

  public void testLabelIrrelevantCommentDoc() {
    doTestDoc("""
        * ******
        la<caret>bel""",
      "<div class='definition'><pre>label</pre></div><div class='content'></div>");
  }

  public void testLabelNoEOLCommentDoc() {
    doTestDoc("""
         rte ; EOL comment
        
        la<caret>bel""",
      "<div class='definition'><pre>label</pre></div><div class='content'></div>");
  }

  public void testEquLabelDoc() {
    doTestDoc("""
        * doc
        la<caret>bel equ 42""",
      "<div class='definition'><pre>label<br><span style=\"color:#0000ff;\">42</span></pre></div><div class='content'>doc</div>");
  }

  public void testLabelDocWithPrecedingConditionalAssemblyDirective() {
    doTestDoc("""
        ; comment
         IF condition
        la<caret>bel""",
      "<div class='definition'><pre>label</pre></div><div class='content'>comment</div>");
  }

  public void testLabelDocSemicolonEOLComment() {
    doTestDoc("la<caret>bel ; comment",
      "<div class='definition'><pre>label</pre></div><div class='content'>comment</div>");
  }

  public void testLabelDocStarEOLComment() {
    doTestDoc("la<caret>bel * comment",
      "<div class='definition'><pre>label</pre></div><div class='content'>comment</div>");
  }

  public void testLabelDocSemicolonEOLCommentWithPrecedingEOLComment() {
    doTestDoc("""
        anotherLabel = 42 ; NO
        la<caret>bel ; comment""",
      "<div class='definition'><pre>label</pre></div><div class='content'>comment</div>");
  }

  private void doTestDoc(String source, String docText) {
    final PsiElement docElement = getDocElement(source);
    final String doc = DocumentationManager.getProviderFromElement(docElement).generateDoc(docElement, getOriginalElement());
    assertEquals(docText, doc);
  }

  private void doTestQuickNavigateInfo(String source, String quickNavigateInfoText) {
    final PsiElement docElement = getDocElement(source);
    final String doc = DocumentationManager.getProviderFromElement(docElement).getQuickNavigateInfo(docElement, getOriginalElement());
    assertEquals(quickNavigateInfoText, doc);
  }

  private PsiElement getDocElement(String source) {
    myFixture.configureByText("a.s", source);
    return DocumentationManager.getInstance(getProject()).findTargetElement(myFixture.getEditor(), myFixture.getFile());
  }

  private PsiElement getOriginalElement() {
    return myFixture.getFile().findElementAt(myFixture.getEditor().getCaretModel().getOffset());
  }

}
