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

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kNumberExpressionDocumentationProviderTest extends BasePlatformTestCase {

  public void testGenerateHoverDoc() {
    doTestGenerateDoc(" dc.b 4<caret>2",
      "<code><span style=\"color:#0000ff;\">42</span><br><span style=\"color:#0000ff;\">$2a</span><br><span style=\"color:#0000ff;\">@52</span><br><span style=\"color:#0000ff;\">%101010</span><br></code>");
  }

  public void testGenerateHoverDocNegativeNumber() {
    doTestGenerateDoc(" dc.b -<caret>1",
      "<code><span style=\"\">-</span><span style=\"color:#0000ff;\">1</span><br><span style=\"color:#0000ff;\">$ffffffff</span><br><span style=\"color:#0000ff;\">@37777777777</span><br><span style=\"color:#0000ff;\">%11111111111111111111111111111111</span><br></code>");
  }

  public void testGenerateHoverDocInvalidValue() {
    doTestGenerateDoc(" dc.b -999999999<caret>99999999999999999", "Invalid number value");
  }

  private void doTestGenerateDoc(String source, String docText) {
    myFixture.configureByText("a.s", source);

    final PsiElement docElement =
      DocumentationManager.getInstance(getProject()).findTargetElement(myFixture.getEditor(), myFixture.getFile());
    DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

    final String doc = provider.generateDoc(docElement, getOriginalElement());
    assertEquals(docText, doc);
  }

  private PsiElement getOriginalElement() {
    return myFixture.getFile().findElementAt(myFixture.getEditor().getCaretModel().getOffset());
  }
}
