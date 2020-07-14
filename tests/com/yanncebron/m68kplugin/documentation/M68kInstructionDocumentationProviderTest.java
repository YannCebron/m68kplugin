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

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.twelvemonkeys.lang.StringUtil;

public class M68kInstructionDocumentationProviderTest extends BasePlatformTestCase {

  public void testMoveInstruction() {
    doTestGenerateDoc(" MOV<caret>e", "<h1>MOVE - Copy data from source to destination</h1>");
  }

  public void testAslInstruction() {
    doTestGenerateDoc(" as<caret>l", "<h1>ASL, ASR - Arithmetic shift left/right</h1>");
  }

  public void testLslInstruction() {
    doTestGenerateDoc(" ls<caret>l", "<h1>LSL, LSR - Logical shift left/right</h1>");
  }

  public void testRoxlInstruction() {
    doTestGenerateDoc(" rox<caret>l", "<h1>ROXL, ROXR - Rotate left/right with extend</h1>");
  }

  public void testBccInstruction() {
    doTestGenerateDoc(" b<caret>hs", "<h1>Bcc - Branch on condition cc</h1>");
  }

  private void doTestGenerateDoc(String source, String docTextContains) {
    myFixture.configureByText("a.s", source);

    final PsiElement docElement =
      DocumentationManager.getInstance(getProject()).findTargetElement(myFixture.getEditor(), myFixture.getFile());
    DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

    final String doc = provider.generateDoc(docElement, getOriginalElement());
    assertTrue(doc, StringUtil.contains(doc, docTextContains));
  }

  private PsiElement getOriginalElement() {
    return myFixture.getFile().findElementAt(myFixture.getEditor().getCaretModel().getOffset());
  }

}
