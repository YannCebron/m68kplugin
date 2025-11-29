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
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PairConsumer;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;

public class M68kInstructionDocumentationProviderTest extends BasePlatformTestCase {

  public void testMoveInstructionReferenceDoc() {
    doTestReferenceDoc(M68kTokenTypes.MOVE, "<h1>MOVE - Copy data from source to destination</h1>");
  }

  public void testAslInstructionReferenceDoc() {
    doTestReferenceDoc(M68kTokenTypes.ASL, "<h1>ASL, ASR - Arithmetic shift left/right</h1>");
  }

  public void testLslInstructionReferenceDoc() {
    doTestReferenceDoc(M68kTokenTypes.LSL, "<h1>LSL, LSR - Logical shift left/right</h1>");
  }

  public void testRoxlInstructionReferenceDoc() {
    doTestReferenceDoc(M68kTokenTypes.ROXL, "<h1>ROXL, ROXR - Rotate left/right with extend</h1>");
  }

  public void testBccInstructionReferenceDoc() {
    doTestReferenceDoc(M68kTokenTypes.BHS, "<h1>Bcc - Branch on condition cc</h1>");
  }

  public void testAllInstructionsHaveReferenceDocs() {
    for (IElementType elementType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      doTestReferenceDoc(elementType,
        " - ", // [mnemonic] - [description]
        "Description",
        "From MOTOROLA M68000 FAMILY Programmer's reference manual."
      );
    }
  }

  public void testUnlkDoc() { // "<unsized>"
    doTest(" unl<caret>k",(psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>UNLK</code></h1>Unlink</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>UNLK&lt;unsized&gt;&nbsp;&nbsp;&nbsp;&nbsp;An</code></h4>", doc);
    });
  }

  public void testMovemInstructionDoc() {
    doTest(" move<caret>m", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>MOVEM</code></h1>Move multiple registers</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>MOVEM&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,-(An)</code></h4><br><h4><code>MOVEM&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,&lt;ALTERABLE_CONTROL></code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Destination:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>MOVEM&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS>,Rn list</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Source:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4><code>MOVEM&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Imm,-(An)</code></h4><br><h4><code>MOVEM&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Imm,&lt;ALTERABLE_CONTROL></code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Destination:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>MOVEM&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS>,Imm</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Source:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br>", doc);
    });
  }

  public void testMovecInstructionDoc() {
    doTest(" move<caret>c", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>MOVEC</code></h1>Move Control Register</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68010+</td></table><br><h4><code>MOVEC&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CTRL,&lt;DATA_OR_ADDRESS_REGISTER></code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Destination:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4><code>MOVEC&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA_OR_ADDRESS_REGISTER>,CTRL</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Source:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br>", doc);
    });
  }

  public void testCmpInstructionDocUnderlineSpecific() {
    doTest(" cmp.<caret>b #42,d0", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>CMP</code></h1>Compare</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>CMP&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;An,Dn</code></h4><br><h4><code>CMP&#8228;b|&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA>,Dn</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Source:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4><code>CMP&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ALL>,An</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Source:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4 style=\"text-decoration: underline;\"> <code>CMP&#8228;b|&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Imm,&lt;ALTERABLE_DATA></code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Destination:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>CMP&#8228;b|&#8228;w|&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(An)+,(An)+</code></h4>", doc);
    });
  }

  private void doTestReferenceDoc(IElementType instructionType, String... docTextContains) {
    final String doc = M68kInstructionDocumentationProvider.getInstructionReferenceDoc(instructionType);
    for (String contain : docTextContains) {
      assertTrue(doc, StringUtil.contains(doc, contain));
    }
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
