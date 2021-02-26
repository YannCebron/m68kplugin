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

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
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
      "label \"equLabel\" [a.s]<br><span style=\"color:#0000ff;\">42</span>");
  }

  public void testEquLabelUndefinedValueQuickDoc() {
    doTestQuickNavigateInfo("equ<caret>Label equ ",
      "label \"equLabel\" [a.s]<br>undefined value");
  }

  public void testEqualsLabelQuickDoc() {
    doTestQuickNavigateInfo("equ<caret>Label = 42",
      "label \"equLabel\" [a.s]<br><span style=\"color:#0000ff;\">42</span>");
  }

  public void testSetLabelQuickDoc() {
    doTestQuickNavigateInfo("set<caret>Label set 42",
      "label \"setLabel\" [a.s]<br><span style=\"color:#0000ff;\">42</span>");
  }

  public void testEqurLabelQuickDoc() {
    doTestQuickNavigateInfo("equr<caret>Label equr d7",
      "label \"equrLabel\" [a.s]<br>&#32;<span style=\"\">d7</span>");
  }

  public void testMacroLabelQuickDoc() {
    doTestQuickNavigateInfo("macro<caret>Label macro", "macro \"macroLabel\" [a.s]");
  }

  private void doTestQuickNavigateInfo(String source, String quickNavigateInfoText) {
    myFixture.configureByText("a.s", source);

    final PsiElement docElement =
      DocumentationManager.getInstance(getProject()).findTargetElement(myFixture.getEditor(), myFixture.getFile());
    DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

    final String doc = provider.getQuickNavigateInfo(docElement, getOriginalElement());
    assertEquals(quickNavigateInfoText, doc);
  }

  private PsiElement getOriginalElement() {
    return myFixture.getFile().findElementAt(myFixture.getEditor().getCaretModel().getOffset());
  }

}
