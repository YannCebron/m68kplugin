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

import com.intellij.lang.documentation.ide.IdeDocumentationTargetProvider;
import com.intellij.openapi.util.Predicates;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.platform.backend.documentation.DocumentationData;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.documentation.impl.ImplKt;
import com.intellij.platform.backend.presentation.TargetPresentation;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class M68KInstructionDocumentationTargetProviderTest extends BasePlatformTestCase {

  public void testAslAsrInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.ASL, M68kTokenTypes.ASR), "<h1>ASL, ASR - Arithmetic shift left/right</h1>");
  }

  public void testLslLsrInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.LSL, M68kTokenTypes.LSR), "<h1>LSL, LSR - Logical shift left/right</h1>");
  }

  public void testRolRorInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.ROL, M68kTokenTypes.ROR), "<h1>ROL, ROR - Rotate left/right (without extend)</h1>");
  }

  public void testRoxlRoxrInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.ROXL, M68kTokenTypes.ROXR), "<h1>ROXL, ROXR - Rotate left/right with extend</h1>");
  }

  public void testDivsDivuInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.DIVS, M68kTokenTypes.DIVU), "<h1>DIVS, DIVU - Signed divide, unsigned divide</h1>");
  }

  public void testMulsMuluInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.MULS, M68kTokenTypes.MULU), "<h1>MULS, MULU - Signed multiply, unsigned multiply</h1>");
  }

  public void testBccInstructionsReferenceDoc() {
    doTestMappedReferenceDoc(M68kTokenGroups.BCC_INSTRUCTIONS, "<h1>Bcc - Branch on condition cc</h1>");
  }

  public void testDbccInstructionsReferenceDoc() {
    doTestMappedReferenceDoc(M68kTokenGroups.DBCC_INSTRUCTIONS, "<h1>DBcc - Test condition, decrement, and branch</h1>");
  }

  public void testSccInstructionsReferenceDoc() {
    doTestMappedReferenceDoc(M68kTokenGroups.SCC_INSTRUCTIONS, "<h1>Scc - Set according to condition cc</h1>");
  }

  public void testTblsXInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.TBLS, M68kTokenTypes.TBLSN), "<h1>TBLS, TBLSN - Table Lookup and Interpolate (Signed)</h1>");
  }

  public void testTbluXInstructionReferenceDoc() {
    doTestMappedReferenceDoc(TokenSet.create(M68kTokenTypes.TBLU, M68kTokenTypes.TBLUN), "<h1>TBLU, TBLUN - Table Lookup and Interpolate (Unsigned)</h1>");
  }

  public void testAllMnemonicsHaveReferenceDocs() {
    for (IElementType elementType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      Set<String> externalNames = new HashSet<>();
      for (M68kMnemonic m68kMnemonic : M68kMnemonicRegistry.getInstance().findAll(elementType)) {
        if (externalNames.add(m68kMnemonic.getExternalName())) {
          doTestReferenceDoc(m68kMnemonic,
            " - ", // [mnemonic(s)] - [description]
            "Description",
            "From MOTOROLA M68000 FAMILY Programmer's reference manual."
          );
        }
      }
    }
  }

  public void testMovemNoOperandsInstructionDoc() {
    doTest(" move<caret>m", "<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>MOVEM</code></h1>Move multiple registers</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>MOVEM&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,-(An)</code></h4><br><h4><code>MOVEM&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rn list,&lt;ALTERABLE_CONTROL&gt;</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>MOVEM&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS&gt;,Rn list</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4><code>MOVEM&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#Imm,-(An)</code></h4><br><h4><code>MOVEM&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#Imm,&lt;ALTERABLE_CONTROL&gt;</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>MOVEM&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;RESTORE_OPERANDS&gt;,#Imm</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br>");
  }

  public void testAndiInstructionDoc() { // skip to CCR|SR variants
    doTest(" and<caret>i #3,d0", "<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>ANDI</code></h1>AND immediate</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>ANDI&#8228;[bwl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#Imm,&lt;ALTERABLE_DATA&gt;</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br>");
  }

  public void testUnlkInstructionDoc() { // "<unsized>"
    doTest(" unl<caret>k a0", "<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>UNLK</code></h1>Unlink</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>UNLK&lt;unsized&gt;&nbsp;&nbsp;&nbsp;&nbsp;An</code></h4>");
  }

  public void testMovecInstructionDoc() { // privileged
    doTest(" move<caret>c", "<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>MOVEC</code></h1>Move Control Register</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68010+</td></table><table class='sections'><tr><td valign='top' class='section'><p>Privileged:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>MOVEC&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;CTRL_REGISTER&gt;,&lt;Rn&gt;</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">DFC</th><th style=\"text-align:center;\">SFC</th><th style=\"text-align:center;\">VBR</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>MOVEC&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Rn&gt;,&lt;CTRL_REGISTER&gt;</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">DFC</th><th style=\"text-align:center;\">SFC</th><th style=\"text-align:center;\">VBR</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br>");
  }

  public void testCmpInstructionDocUnderlineSpecific() {
    doTest(" cmp.<caret>b #42,d0", "<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>CMP</code></h1>Compare</pre></div><h4><code>CMP&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;An,Dn</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>CMP&#8228;[bwl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA&gt;,Dn</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4><code>CMP&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ALL&gt;,An</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4 style=\"text-decoration: underline;\"><code>CMP&#8228;[bwl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#Imm,&lt;ALTERABLE_DATA&gt;</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>CMP&#8228;[bwl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#Imm,&lt;DATA_WITHOUT_IMMEDIATE&gt;</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68020+, CPU32</td></table><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td></tr></table><br><h4><code>CMP&#8228;[bwl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(An)+,(An)+</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table>");
  }

  public void testMoveaInstructionDocUnderlineSpecificAndDeprecated() {
    doTest(" movea.<caret>l a0,d0", "<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>MOVEA</code></h1>Move address</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>MOVEA&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;ALL&gt;,An</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4 style=\"text-decoration: underline line-through;\"><code>MOVEA&#8228;[wl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;An,&lt;ALTERABLE&gt;</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br><h4 style=\"text-decoration:  line-through;\"><code>MOVEA&#8228;[bwl]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA&gt;,&lt;ALTERABLE_DATA&gt;</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">An</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>Second:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td><td style=\"text-align:center;\"></td></tr></table><br>");
  }

  public void testBeqInstructionMnemonicDocsFiltered() {
    M68kMnemonic m68kMnemonic = ContainerUtil.getFirstItem(M68kMnemonicRegistry.getInstance().findAll(M68kTokenTypes.CHK));

    String mnemonicDoc = M68kInstructionDocsUtil.getMnemonicDoc(m68kMnemonic, false, Predicates.alwaysTrue());
    assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>CHK</code></h1>Check register against bounds</pre></div><h4><code>CHK&#8228;w&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA&gt;,Dn</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br><h4><code>CHK&#8228;l&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA&gt;,Dn</code></h4><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68020+</td></table><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br>", mnemonicDoc);

    String mnemonicDocWithout68020 = M68kInstructionDocsUtil.getMnemonicDoc(m68kMnemonic, false, M68kMnemonicPredicates.forCpuGroup(M68kCpu.GROUP_68020_UP).negate());
    assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1><code>CHK</code></h1>Check register against bounds</pre></div><table class='sections'><tr><td valign='top' class='section'><p>CPU:</td><td valign='top'>MC68000 Family</td></table><br><h4><code>CHK&#8228;w&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;DATA&gt;,Dn</code></h4><table style=\"width: 100%;\"><tr><th></th><th style=\"text-align:center;\">Dn</th><th style=\"text-align:center;\">(An)</th><th style=\"text-align:center;\">(An)+</th><th style=\"text-align:center;\">-(An)</th><th style=\"text-align:center;\">(d,An)</th><th style=\"text-align:center;\">(d,An,Xi)</th><th style=\"text-align:center;\">ABS&#8228;W</th><th style=\"text-align:center;\">ABS&#8228;L</th><th style=\"text-align:center;\">(d,PC)</th><th style=\"text-align:center;\">(d,PC,Xn)</th><th style=\"text-align:center;\">#Imm</th></tr><tr><td class=\"section\" valign=\"top\" width=\"15%\"/>First:</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td><td style=\"text-align:center;\">✓</td></tr></table><br>", mnemonicDocWithout68020);
  }

  private void doTestMappedReferenceDoc(TokenSet instructionTokens, String... docTextContains) {
    for (IElementType instructionType : instructionTokens.getTypes()) {
      Collection<M68kMnemonic> all = M68kMnemonicRegistry.getInstance().findAll(instructionType);
      M68kMnemonic m68kMnemonic = ContainerUtil.getFirstItem(all);
      assertNotNull(m68kMnemonic);
      doTestReferenceDoc(m68kMnemonic, docTextContains);
    }
  }

  private void doTestReferenceDoc(M68kMnemonic m68kMnemonic, String... docTextContains) {
    final String doc = M68kInstructionDocsUtil.getMnemonicReferenceDoc(m68kMnemonic);
    for (String contain : docTextContains) {
      assertTrue(m68kMnemonic + ": missing '" + contain + "' in " + doc, StringUtil.contains(doc, contain));
    }
  }

  @SuppressWarnings("OverrideOnly")
  private void doTest(String source, String expectedDoc) {
    myFixture.configureByText("a.s", source);

    List<? extends @NotNull DocumentationTarget> documentationTargets =
      IdeDocumentationTargetProvider.getInstance(getProject()).documentationTargets(
        myFixture.getEditor(), myFixture.getFile(), myFixture.getCaretOffset());

    DocumentationTarget documentationTarget = assertOneElement(documentationTargets);

    TargetPresentation targetPresentation = documentationTarget.computePresentation();
    assertEquals(myFixture.getFile().getText().trim(), targetPresentation.getPresentableText());

    DocumentationData documentationData = ImplKt.computeDocumentationBlocking(documentationTarget.createPointer());
    assertNotNull(documentationData);
    assertEquals(expectedDoc, documentationData.getHtml());
  }

}
