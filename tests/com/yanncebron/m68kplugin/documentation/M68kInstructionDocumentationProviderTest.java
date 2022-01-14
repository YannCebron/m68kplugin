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
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PairConsumer;
import com.twelvemonkeys.lang.StringUtil;
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

  public void testMovemInstructionDoc() {
    doTest(" move<caret>m", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px;} </style><h1>MOVEM - Move multiple registers</h1><br>M68000 Family<br><br><h2><code>MOVEM.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,-(An)</code></h2><br><h2><code>MOVEM.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,&lt;ALTERABLE_CONTROL></code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS.W</th><th style=\"text-align:center;\">ABS.L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">imm</th><th style=\"text-align:center;\">Rn list</th></tr><tr><td style=\"width: 15%;\">Destination</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>MOVEM.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS>,Rn list</code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS.W</th><th style=\"text-align:center;\">ABS.L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">imm</th><th style=\"text-align:center;\">Rn list</th></tr><tr><td style=\"width: 15%;\">Source</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>MOVEM.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imm,-(An)</code></h2><br><h2><code>MOVEM.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imm,&lt;ALTERABLE_CONTROL></code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS.W</th><th style=\"text-align:center;\">ABS.L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">imm</th><th style=\"text-align:center;\">Rn list</th></tr><tr><td style=\"width: 15%;\">Destination</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>MOVEM.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS>,imm</code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS.W</th><th style=\"text-align:center;\">ABS.L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">imm</th><th style=\"text-align:center;\">Rn list</th></tr><tr><td style=\"width: 15%;\">Source</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br>", doc);
    });
  }

  public void testMovecInstructionDoc() {
    doTest(" move<caret>c", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px;} </style><h1>MOVEC - Move Control Register</h1><br><table><tr><th>MC68000</th><th>MC68010</th><th>MC68020</th><th>MC68030</th><th>MC68040</th><th>MC68060</th><th>AC68080</th></tr><tr><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h2><code>MOVEC.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CTRL,&lt;DATA_OR_ADDRESS_REGISTER></code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">CTRL</th></tr><tr><td style=\"width: 15%;\">Destination</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>MOVEC.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA_OR_ADDRESS_REGISTER>,CTRL</code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">CTRL</th></tr><tr><td style=\"width: 15%;\">Source</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td></tr></table><br>", doc);
    });
  }

  public void testCmpInstructionDocUnderlineSpecific() {
    doTest(" c<caret>mp.b #42,d0", (psiElement, documentationProvider) -> {
      String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } td { margin: 4px 0 0 0; padding: 0 0 0 0; }th { font-weight: bold; text-align: left; white-space: nowrap; margin: 2px; } em { font-style: italic; }code { white-space: nowrap; }p { padding-top: 5px;} </style><h1>CMP - Compare</h1><br>M68000 Family<br><br><h2><code>CMP.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;An,Dn</code></h2><br><h2><code>CMP.b|.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA>,Dn</code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS.W</th><th style=\"text-align:center;\">ABS.L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">imm</th></tr><tr><td style=\"width: 15%;\">Source</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h2><code>CMP.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ALL>,An</code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS.W</th><th style=\"text-align:center;\">ABS.L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">imm</th></tr><tr><td style=\"width: 15%;\">Source</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h2 style=\"text-decoration: underline;\"> <code>CMP.b|.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imm,&lt;ALTERABLE_DATA></code></h2><br><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS.W</th><th style=\"text-align:center;\">ABS.L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">imm</th></tr><tr><td style=\"width: 15%;\">Destination</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>CMP.b|.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(An)+,(An)+</code></h2><br>", doc);
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
