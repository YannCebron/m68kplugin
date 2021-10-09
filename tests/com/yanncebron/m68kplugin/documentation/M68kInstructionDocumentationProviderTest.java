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
import com.yanncebron.m68kplugin.lang.psi.M68kCpu;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonic;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistry;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;

import java.util.Collection;

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

  public void testAllInstructionsHaveReferenceDocs() {
    for (IElementType elementType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      final Collection<M68kMnemonic> mnemonics = M68kMnemonicRegistry.getInstance().findAll(elementType);
      boolean found68000 = false;
      for (M68kMnemonic mnemonic : mnemonics) {
        if (mnemonic.getCpus().contains(M68kCpu.M_68000)) {
          found68000 = true;
          break;
        }
      }
      if (!found68000) continue;

      final String mnemonic = elementType.toString();
      doTestGenerateDoc(" " + mnemonic.charAt(0) + "<caret>" + mnemonic.substring(1), " - "); // [mnemonic] - [description]
    }
  }

  public void testMovemInstructionHoverDoc() {
    doTest(" move<caret>m", (psiElement, documentationProvider) -> {
      String hoverDoc = documentationProvider.generateHoverDoc(psiElement, getOriginalElement());
      assertEquals("<style>h1 { font-weight: bold; font-size: 120%; } h2 { padding-top: 13px; font-weight: bold; font-size: 110%; } h3 { padding-top: 10px; font-weight: bold; } table { padding-bottom: 10px; white-space: nowrap; } th { margin: 2px; } em { font-style: italic; }</style><h1>MOVEM - Move multiple registers</h1><a href=\"psi_element://m68k_instruction_reference_docs\">Reference Documentation</a><br><br>M68000 Family<br><br><h2><code>movem.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,-(An)</code></h2><br><h2><code>movem.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,&lt;ALTERABLE_CONTROL></code></h2><br><table><tr><th></th><th>Dn</th><th>An</th><th>(An)</th><th>(An)+</th><th>-(An)</th><th>(d,An)</th><th>(d,An,Xi)</th><th>ABS.W</th><th>ABS.L</th><th>label</th><th>(d,PC)</th><th>(d,PC,Xn)</th><th>imm</th><th>quick</th><th>Rn list</th><th>SR</th><th>USP</th><th>CCR</th><th>CTRL</th></tr><tr><td>Destination</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>movem.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS>,Rn list</code></h2><br><table><tr><th></th><th>Dn</th><th>An</th><th>(An)</th><th>(An)+</th><th>-(An)</th><th>(d,An)</th><th>(d,An,Xi)</th><th>ABS.W</th><th>ABS.L</th><th>label</th><th>(d,PC)</th><th>(d,PC,Xn)</th><th>imm</th><th>quick</th><th>Rn list</th><th>SR</th><th>USP</th><th>CCR</th><th>CTRL</th></tr><tr><td>Source</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>movem.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imm,-(An)</code></h2><br><h2><code>movem.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imm,&lt;ALTERABLE_CONTROL></code></h2><br><table><tr><th></th><th>Dn</th><th>An</th><th>(An)</th><th>(An)+</th><th>-(An)</th><th>(d,An)</th><th>(d,An,Xi)</th><th>ABS.W</th><th>ABS.L</th><th>label</th><th>(d,PC)</th><th>(d,PC,Xn)</th><th>imm</th><th>quick</th><th>Rn list</th><th>SR</th><th>USP</th><th>CCR</th><th>CTRL</th></tr><tr><td>Destination</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h2><code>movem.w|.l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS>,imm</code></h2><br><table><tr><th></th><th>Dn</th><th>An</th><th>(An)</th><th>(An)+</th><th>-(An)</th><th>(d,An)</th><th>(d,An,Xi)</th><th>ABS.W</th><th>ABS.L</th><th>label</th><th>(d,PC)</th><th>(d,PC,Xn)</th><th>imm</th><th>quick</th><th>Rn list</th><th>SR</th><th>USP</th><th>CCR</th><th>CTRL</th></tr><tr><td>Source</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br>", hoverDoc);
    });
  }

  private void doTestGenerateDoc(String source, String docTextContains) {
    doTest(source, (psiElement, documentationProvider) -> {
      final String doc = documentationProvider.generateDoc(psiElement, getOriginalElement());
      assertTrue(doc, StringUtil.contains(doc, docTextContains));
    });
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
