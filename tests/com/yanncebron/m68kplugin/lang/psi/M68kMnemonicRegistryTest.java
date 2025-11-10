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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.LightPlatformTestCase;
import com.yanncebron.m68kplugin.parser.MnemonicGeneratedParserDataTest;

import java.util.Collection;

/**
 * See also {@link MnemonicGeneratedParserDataTest} checking that each variant returns entry via {@link M68kMnemonicRegistry#find}.
 */
public class M68kMnemonicRegistryTest extends LightPlatformTestCase {

  private final M68kMnemonicRegistry instance = M68kMnemonicRegistry.getInstance();

  public void testAllInstructionsHaveMnemonics() {
    for (IElementType instructionsType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      final Collection<M68kMnemonic> all = instance.findAll(instructionsType);
      assertFalse("no mnemonics for '" + instructionsType + "'", all.isEmpty());
    }
  }

  public void testFindAllIsEmptyForUnknownElementType() {
    assertTrue(instance.findAll(M68kTokenTypes.ADDWATCH).isEmpty());
  }

  public void testFindBkptError() {
    doTestFind("bkpt ^", null);
  }

  public void testFindBkptNoMatchNoOperands() {
    doTestFind("bkpt", null);
  }

  public void testFindBkptNoMatchWrongOperand() {
    doTestFind("bkpt something", null);
  }

  public void testFindBkpt() {
    doTestFind("bkpt #2", new M68kMnemonic(M68kTokenTypes.BKPT,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.NONE,
      M68kDataSize.GROUP_UNSIZED,
      M68kCpu.GROUP_68010_UP));
  }

  public void testFindAslAlterableMemory() {
    doTestFind("asl $42", new M68kMnemonic(M68kTokenTypes.ASL,
      M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
      M68kDataSize.GROUP_W,
      M68kCpu.GROUP_68000_UP));
  }

  public void testBeq() {
    doTestFind("beq label", new M68kMnemonic(M68kTokenTypes.BEQ,
      M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
      M68kDataSize.GROUP_SBW,
      M68kCpu.GROUP_68000_UP));
  }

  public void testBeqDataSizeLong() {
    doTestFind("beq.l label", new M68kMnemonic(M68kTokenTypes.BEQ,
      M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
      M68kDataSize.GROUP_SBWL,
      M68kCpu.GROUP_68020_UP));
  }

  public void testRtd() {
    doTestFind("rtd #1", new M68kMnemonic(M68kTokenTypes.RTD,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.NONE,
      M68kDataSize.GROUP_UNSIZED,
      M68kCpu.GROUP_68010_UP));
  }

  public void testFindAslDnDn() {
    doTestFind("asl d0,d1", new M68kMnemonic(M68kTokenTypes.ASL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
      M68kDataSize.GROUP_BWL,
      M68kCpu.GROUP_68000_UP));
  }

  public void testFindAslQuickDn() {
    doTestFind("asl #1,d1", new M68kMnemonic(M68kTokenTypes.ASL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
      M68kDataSize.GROUP_BWL,
      M68kCpu.GROUP_68000_UP));
  }

  public void testFindAslDn() {
    doTestFind("asl d1", new M68kMnemonic(M68kTokenTypes.ASL,
      M68kOperand.DATA_REGISTER, M68kOperand.NONE,
      M68kDataSize.GROUP_BWL,
      M68kCpu.GROUP_68000_UP));
  }

  public void testFindImmediateAlterableData() {
    // second match: `<DATA>,Dn`
    doTestFind("cmp.b #42,d0", new M68kMnemonic(M68kTokenTypes.CMP,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
      M68kDataSize.GROUP_BWL,
      M68kCpu.GROUP_68000_UP));
  }

  // matching via M68kAdmWithRrd
  public void testFindExg() {
    doTestFind("exg a0,a1", new M68kMnemonic(M68kTokenTypes.EXG,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.ADDRESS_REGISTER,
      M68kDataSize.GROUP_L,
      M68kCpu.GROUP_68000_UP));

    doTestFind("exg d0,a1", new M68kMnemonic(M68kTokenTypes.EXG,
      M68kOperand.DATA_REGISTER, M68kOperand.ADDRESS_REGISTER,
      M68kDataSize.GROUP_L,
      M68kCpu.GROUP_68000_UP));

    doTestFind("exg a1,d0", new M68kMnemonic(M68kTokenTypes.EXG,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER,
      M68kDataSize.GROUP_L,
      M68kCpu.GROUP_68000_UP));

    doTestFind("exg d0,d1", new M68kMnemonic(M68kTokenTypes.EXG,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
      M68kDataSize.GROUP_L,
      M68kCpu.GROUP_68000_UP));
  }

  private void doTestFind(String instructionText, M68kMnemonic expectedMnemonic) {
    PsiFile psiFile = createLightFile("a.s", " " + instructionText); //
    final M68kPsiElement instruction = M68kPsiTreeUtil.getContainingInstructionOrDirective(psiFile.findElementAt(2));
    assertNotNull(instructionText, instruction);
    final M68kInstruction m68kInstruction = assertInstanceOf(instruction, M68kInstruction.class);

    final M68kMnemonic m68kMnemonic = instance.find(m68kInstruction);
    assertEquals(expectedMnemonic, m68kMnemonic);
  }

}