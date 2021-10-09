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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Registry of all supported mnemonics.
 * <p>
 * Data is generated via {@code M68kMnemonicRegistryGeneratorTest} using <a href="http://sun.hasenbraten.de/vasm"/>vasm</a> sources.
 * Thanks to vasm project owners Frank Wille and Dr. Volker Barthelmann for granting permission.
 */
public final class M68kMnemonicRegistry {

  private final MultiMap<IElementType, M68kMnemonic> mnemonics = MultiMap.createLinked();

  private static final M68kMnemonicRegistry INSTANCE = new M68kMnemonicRegistry();

  public static M68kMnemonicRegistry getInstance() {
    return INSTANCE;
  }

  public Collection<M68kMnemonic> findAll(@NotNull IElementType elementType) {
    return mnemonics.get(elementType);
  }

  private M68kMnemonicRegistry() {
    // Total mnemonics: 311
    mnemonics.putValue(M68kTokenTypes.ABCD,
      new M68kMnemonic(M68kTokenTypes.ABCD,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ABCD,
      new M68kMnemonic(M68kTokenTypes.ABCD,
        M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADD,
      new M68kMnemonic(M68kTokenTypes.ADD,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADD,
      new M68kMnemonic(M68kTokenTypes.ADD,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADD,
      new M68kMnemonic(M68kTokenTypes.ADD,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADD,
      new M68kMnemonic(M68kTokenTypes.ADD,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADD,
      new M68kMnemonic(M68kTokenTypes.ADD,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADDA,
      new M68kMnemonic(M68kTokenTypes.ADDA,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADDI,
      new M68kMnemonic(M68kTokenTypes.ADDI,
        M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADDI,
      new M68kMnemonic(M68kTokenTypes.ADDI,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADDQ,
      new M68kMnemonic(M68kTokenTypes.ADDQ,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADDQ,
      new M68kMnemonic(M68kTokenTypes.ADDQ,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADDX,
      new M68kMnemonic(M68kTokenTypes.ADDX,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ADDX,
      new M68kMnemonic(M68kTokenTypes.ADDX,
        M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.AND,
      new M68kMnemonic(M68kTokenTypes.AND,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.AND,
      new M68kMnemonic(M68kTokenTypes.AND,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.AND,
      new M68kMnemonic(M68kTokenTypes.AND,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.AND,
      new M68kMnemonic(M68kTokenTypes.AND,
        M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.AND,
      new M68kMnemonic(M68kTokenTypes.AND,
        M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ANDI,
      new M68kMnemonic(M68kTokenTypes.ANDI,
        M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ANDI,
      new M68kMnemonic(M68kTokenTypes.ANDI,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ANDI,
      new M68kMnemonic(M68kTokenTypes.ANDI,
        M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ANDI,
      new M68kMnemonic(M68kTokenTypes.ANDI,
        M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASL,
      new M68kMnemonic(M68kTokenTypes.ASL,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASL,
      new M68kMnemonic(M68kTokenTypes.ASL,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASL,
      new M68kMnemonic(M68kTokenTypes.ASL,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASL,
      new M68kMnemonic(M68kTokenTypes.ASL,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASR,
      new M68kMnemonic(M68kTokenTypes.ASR,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASR,
      new M68kMnemonic(M68kTokenTypes.ASR,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASR,
      new M68kMnemonic(M68kTokenTypes.ASR,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ASR,
      new M68kMnemonic(M68kTokenTypes.ASR,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BHS,
      new M68kMnemonic(M68kTokenTypes.BHS,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BLO,
      new M68kMnemonic(M68kTokenTypes.BLO,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BHI,
      new M68kMnemonic(M68kTokenTypes.BHI,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BLS,
      new M68kMnemonic(M68kTokenTypes.BLS,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCC,
      new M68kMnemonic(M68kTokenTypes.BCC,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCS,
      new M68kMnemonic(M68kTokenTypes.BCS,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BNE,
      new M68kMnemonic(M68kTokenTypes.BNE,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BEQ,
      new M68kMnemonic(M68kTokenTypes.BEQ,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BVC,
      new M68kMnemonic(M68kTokenTypes.BVC,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BVS,
      new M68kMnemonic(M68kTokenTypes.BVS,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BPL,
      new M68kMnemonic(M68kTokenTypes.BPL,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BMI,
      new M68kMnemonic(M68kTokenTypes.BMI,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BGE,
      new M68kMnemonic(M68kTokenTypes.BGE,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BLT,
      new M68kMnemonic(M68kTokenTypes.BLT,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BGT,
      new M68kMnemonic(M68kTokenTypes.BGT,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BLE,
      new M68kMnemonic(M68kTokenTypes.BLE,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BRA,
      new M68kMnemonic(M68kTokenTypes.BRA,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BSR,
      new M68kMnemonic(M68kTokenTypes.BSR,
        M68kOperand.BRANCH_DESTINATION, M68kOperand.NONE,
        M68kDataSize.GROUP_SBW,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCHG,
      new M68kMnemonic(M68kTokenTypes.BCHG,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCHG,
      new M68kMnemonic(M68kTokenTypes.BCHG,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCHG,
      new M68kMnemonic(M68kTokenTypes.BCHG,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCHG,
      new M68kMnemonic(M68kTokenTypes.BCHG,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCHG,
      new M68kMnemonic(M68kTokenTypes.BCHG,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCLR,
      new M68kMnemonic(M68kTokenTypes.BCLR,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCLR,
      new M68kMnemonic(M68kTokenTypes.BCLR,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCLR,
      new M68kMnemonic(M68kTokenTypes.BCLR,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCLR,
      new M68kMnemonic(M68kTokenTypes.BCLR,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BCLR,
      new M68kMnemonic(M68kTokenTypes.BCLR,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BSET,
      new M68kMnemonic(M68kTokenTypes.BSET,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BSET,
      new M68kMnemonic(M68kTokenTypes.BSET,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BSET,
      new M68kMnemonic(M68kTokenTypes.BSET,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BSET,
      new M68kMnemonic(M68kTokenTypes.BSET,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BSET,
      new M68kMnemonic(M68kTokenTypes.BSET,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BTST,
      new M68kMnemonic(M68kTokenTypes.BTST,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BTST,
      new M68kMnemonic(M68kTokenTypes.BTST,
        M68kOperand.DATA_REGISTER, M68kOperand.MEMORY,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BTST,
      new M68kMnemonic(M68kTokenTypes.BTST,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BTST,
      new M68kMnemonic(M68kTokenTypes.BTST,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BTST,
      new M68kMnemonic(M68kTokenTypes.BTST,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.MEMORY_WITHOUT_IMMEDIATE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.BKPT,
      new M68kMnemonic(M68kTokenTypes.BKPT,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68010_UP));
    mnemonics.putValue(M68kTokenTypes.CHK,
      new M68kMnemonic(M68kTokenTypes.CHK,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CLR,
      new M68kMnemonic(M68kTokenTypes.CLR,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMP,
      new M68kMnemonic(M68kTokenTypes.CMP,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMP,
      new M68kMnemonic(M68kTokenTypes.CMP,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMP,
      new M68kMnemonic(M68kTokenTypes.CMP,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMP,
      new M68kMnemonic(M68kTokenTypes.CMP,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMP,
      new M68kMnemonic(M68kTokenTypes.CMP,
        M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMPA,
      new M68kMnemonic(M68kTokenTypes.CMPA,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMPI,
      new M68kMnemonic(M68kTokenTypes.CMPI,
        M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMPI,
      new M68kMnemonic(M68kTokenTypes.CMPI,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.CMPM,
      new M68kMnemonic(M68kTokenTypes.CMPM,
        M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBT,
      new M68kMnemonic(M68kTokenTypes.DBT,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBF,
      new M68kMnemonic(M68kTokenTypes.DBF,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBRA,
      new M68kMnemonic(M68kTokenTypes.DBRA,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBHI,
      new M68kMnemonic(M68kTokenTypes.DBHI,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBLS,
      new M68kMnemonic(M68kTokenTypes.DBLS,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBCC,
      new M68kMnemonic(M68kTokenTypes.DBCC,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBHS,
      new M68kMnemonic(M68kTokenTypes.DBHS,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBCS,
      new M68kMnemonic(M68kTokenTypes.DBCS,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBLO,
      new M68kMnemonic(M68kTokenTypes.DBLO,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBNE,
      new M68kMnemonic(M68kTokenTypes.DBNE,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBEQ,
      new M68kMnemonic(M68kTokenTypes.DBEQ,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBVC,
      new M68kMnemonic(M68kTokenTypes.DBVC,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBVS,
      new M68kMnemonic(M68kTokenTypes.DBVS,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBPL,
      new M68kMnemonic(M68kTokenTypes.DBPL,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBMI,
      new M68kMnemonic(M68kTokenTypes.DBMI,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBGE,
      new M68kMnemonic(M68kTokenTypes.DBGE,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBLT,
      new M68kMnemonic(M68kTokenTypes.DBLT,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBGT,
      new M68kMnemonic(M68kTokenTypes.DBGT,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DBLE,
      new M68kMnemonic(M68kTokenTypes.DBLE,
        M68kOperand.DATA_REGISTER, M68kOperand.BRANCH_DESTINATION,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DIVS,
      new M68kMnemonic(M68kTokenTypes.DIVS,
        M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DIVS,
      new M68kMnemonic(M68kTokenTypes.DIVS,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DIVU,
      new M68kMnemonic(M68kTokenTypes.DIVU,
        M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.DIVU,
      new M68kMnemonic(M68kTokenTypes.DIVU,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EOR,
      new M68kMnemonic(M68kTokenTypes.EOR,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EOR,
      new M68kMnemonic(M68kTokenTypes.EOR,
        M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EOR,
      new M68kMnemonic(M68kTokenTypes.EOR,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EOR,
      new M68kMnemonic(M68kTokenTypes.EOR,
        M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EOR,
      new M68kMnemonic(M68kTokenTypes.EOR,
        M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EORI,
      new M68kMnemonic(M68kTokenTypes.EORI,
        M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EORI,
      new M68kMnemonic(M68kTokenTypes.EORI,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EORI,
      new M68kMnemonic(M68kTokenTypes.EORI,
        M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EORI,
      new M68kMnemonic(M68kTokenTypes.EORI,
        M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EXG,
      new M68kMnemonic(M68kTokenTypes.EXG,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EXG,
      new M68kMnemonic(M68kTokenTypes.EXG,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EXG,
      new M68kMnemonic(M68kTokenTypes.EXG,
        M68kOperand.DATA_REGISTER, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EXG,
      new M68kMnemonic(M68kTokenTypes.EXG,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.EXT,
      new M68kMnemonic(M68kTokenTypes.EXT,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ILLEGAL,
      new M68kMnemonic(M68kTokenTypes.ILLEGAL,
        M68kOperand.NONE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.JMP,
      new M68kMnemonic(M68kTokenTypes.JMP,
        M68kOperand.CONTROL, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.JSR,
      new M68kMnemonic(M68kTokenTypes.JSR,
        M68kOperand.CONTROL, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LEA,
      new M68kMnemonic(M68kTokenTypes.LEA,
        M68kOperand.CONTROL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LINK,
      new M68kMnemonic(M68kTokenTypes.LINK,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.IMMEDIATE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSL,
      new M68kMnemonic(M68kTokenTypes.LSL,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSL,
      new M68kMnemonic(M68kTokenTypes.LSL,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSL,
      new M68kMnemonic(M68kTokenTypes.LSL,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSL,
      new M68kMnemonic(M68kTokenTypes.LSL,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSR,
      new M68kMnemonic(M68kTokenTypes.LSR,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSR,
      new M68kMnemonic(M68kTokenTypes.LSR,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSR,
      new M68kMnemonic(M68kTokenTypes.LSR,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.LSR,
      new M68kMnemonic(M68kTokenTypes.LSR,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.ALTERABLE,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.DATA, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.CCR_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68010_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.CCR_REGISTER, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68010_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.SR_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.SR_REGISTER, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.DATA_REGISTER, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.DATA, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.DATA_REGISTER, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.DATA, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.USP_REGISTER, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVE,
      new M68kMnemonic(M68kTokenTypes.MOVE,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.USP_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEA,
      new M68kMnemonic(M68kTokenTypes.MOVEA,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEA,
      new M68kMnemonic(M68kTokenTypes.MOVEA,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.ALTERABLE,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEA,
      new M68kMnemonic(M68kTokenTypes.MOVEA,
        M68kOperand.DATA, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEC,
      new M68kMnemonic(M68kTokenTypes.MOVEC,
        M68kOperand.CTRL_REGISTER, M68kOperand.DATA_OR_ADDRESS_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68010_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEC,
      new M68kMnemonic(M68kTokenTypes.MOVEC,
        M68kOperand.DATA_OR_ADDRESS_REGISTER, M68kOperand.CTRL_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68010_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEM,
      new M68kMnemonic(M68kTokenTypes.MOVEM,
        M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEM,
      new M68kMnemonic(M68kTokenTypes.MOVEM,
        M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST, M68kOperand.ALTERABLE_CONTROL,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEM,
      new M68kMnemonic(M68kTokenTypes.MOVEM,
        M68kOperand.RESTORE_OPERANDS, M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEM,
      new M68kMnemonic(M68kTokenTypes.MOVEM,
        M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEM,
      new M68kMnemonic(M68kTokenTypes.MOVEM,
        M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE, M68kOperand.ALTERABLE_CONTROL,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEM,
      new M68kMnemonic(M68kTokenTypes.MOVEM,
        M68kOperand.RESTORE_OPERANDS, M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEP,
      new M68kMnemonic(M68kTokenTypes.MOVEP,
        M68kOperand.ADDRESS_REGISTER_DISPLACEMENT, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEP,
      new M68kMnemonic(M68kTokenTypes.MOVEP,
        M68kOperand.DATA_REGISTER, M68kOperand.ADDRESS_REGISTER_DISPLACEMENT,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVEQ,
      new M68kMnemonic(M68kTokenTypes.MOVEQ,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MOVES,
      new M68kMnemonic(M68kTokenTypes.MOVES,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.DATA_OR_ADDRESS_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68010_UP));
    mnemonics.putValue(M68kTokenTypes.MOVES,
      new M68kMnemonic(M68kTokenTypes.MOVES,
        M68kOperand.DATA_OR_ADDRESS_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68010_UP));
    mnemonics.putValue(M68kTokenTypes.MULS,
      new M68kMnemonic(M68kTokenTypes.MULS,
        M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MULS,
      new M68kMnemonic(M68kTokenTypes.MULS,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MULU,
      new M68kMnemonic(M68kTokenTypes.MULU,
        M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.MULU,
      new M68kMnemonic(M68kTokenTypes.MULU,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NBCD,
      new M68kMnemonic(M68kTokenTypes.NBCD,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NEG,
      new M68kMnemonic(M68kTokenTypes.NEG,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NEG,
      new M68kMnemonic(M68kTokenTypes.NEG,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NEGX,
      new M68kMnemonic(M68kTokenTypes.NEGX,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NEGX,
      new M68kMnemonic(M68kTokenTypes.NEGX,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NOP,
      new M68kMnemonic(M68kTokenTypes.NOP,
        M68kOperand.NONE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NOT,
      new M68kMnemonic(M68kTokenTypes.NOT,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.NOT,
      new M68kMnemonic(M68kTokenTypes.NOT,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.OR,
      new M68kMnemonic(M68kTokenTypes.OR,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.OR,
      new M68kMnemonic(M68kTokenTypes.OR,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.OR,
      new M68kMnemonic(M68kTokenTypes.OR,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.OR,
      new M68kMnemonic(M68kTokenTypes.OR,
        M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.OR,
      new M68kMnemonic(M68kTokenTypes.OR,
        M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ORI,
      new M68kMnemonic(M68kTokenTypes.ORI,
        M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ORI,
      new M68kMnemonic(M68kTokenTypes.ORI,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ORI,
      new M68kMnemonic(M68kTokenTypes.ORI,
        M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ORI,
      new M68kMnemonic(M68kTokenTypes.ORI,
        M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.PEA,
      new M68kMnemonic(M68kTokenTypes.PEA,
        M68kOperand.CONTROL, M68kOperand.NONE,
        M68kDataSize.GROUP_L,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.RESET,
      new M68kMnemonic(M68kTokenTypes.RESET,
        M68kOperand.NONE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROL,
      new M68kMnemonic(M68kTokenTypes.ROL,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROL,
      new M68kMnemonic(M68kTokenTypes.ROL,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROL,
      new M68kMnemonic(M68kTokenTypes.ROL,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROL,
      new M68kMnemonic(M68kTokenTypes.ROL,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROR,
      new M68kMnemonic(M68kTokenTypes.ROR,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROR,
      new M68kMnemonic(M68kTokenTypes.ROR,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROR,
      new M68kMnemonic(M68kTokenTypes.ROR,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROR,
      new M68kMnemonic(M68kTokenTypes.ROR,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXL,
      new M68kMnemonic(M68kTokenTypes.ROXL,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXL,
      new M68kMnemonic(M68kTokenTypes.ROXL,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXL,
      new M68kMnemonic(M68kTokenTypes.ROXL,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXL,
      new M68kMnemonic(M68kTokenTypes.ROXL,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXR,
      new M68kMnemonic(M68kTokenTypes.ROXR,
        M68kOperand.ALTERABLE_MEMORY, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXR,
      new M68kMnemonic(M68kTokenTypes.ROXR,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXR,
      new M68kMnemonic(M68kTokenTypes.ROXR,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ROXR,
      new M68kMnemonic(M68kTokenTypes.ROXR,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.RTE,
      new M68kMnemonic(M68kTokenTypes.RTE,
        M68kOperand.NONE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.RTR,
      new M68kMnemonic(M68kTokenTypes.RTR,
        M68kOperand.NONE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.RTS,
      new M68kMnemonic(M68kTokenTypes.RTS,
        M68kOperand.NONE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SBCD,
      new M68kMnemonic(M68kTokenTypes.SBCD,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SBCD,
      new M68kMnemonic(M68kTokenTypes.SBCD,
        M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ST,
      new M68kMnemonic(M68kTokenTypes.ST,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.ST,
      new M68kMnemonic(M68kTokenTypes.ST,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SF,
      new M68kMnemonic(M68kTokenTypes.SF,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SF,
      new M68kMnemonic(M68kTokenTypes.SF,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SHI,
      new M68kMnemonic(M68kTokenTypes.SHI,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SHI,
      new M68kMnemonic(M68kTokenTypes.SHI,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLS,
      new M68kMnemonic(M68kTokenTypes.SLS,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLS,
      new M68kMnemonic(M68kTokenTypes.SLS,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SCC,
      new M68kMnemonic(M68kTokenTypes.SCC,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SCC,
      new M68kMnemonic(M68kTokenTypes.SCC,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SHS,
      new M68kMnemonic(M68kTokenTypes.SHS,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SHS,
      new M68kMnemonic(M68kTokenTypes.SHS,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SCS,
      new M68kMnemonic(M68kTokenTypes.SCS,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SCS,
      new M68kMnemonic(M68kTokenTypes.SCS,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLO,
      new M68kMnemonic(M68kTokenTypes.SLO,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLO,
      new M68kMnemonic(M68kTokenTypes.SLO,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SNE,
      new M68kMnemonic(M68kTokenTypes.SNE,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SNE,
      new M68kMnemonic(M68kTokenTypes.SNE,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SEQ,
      new M68kMnemonic(M68kTokenTypes.SEQ,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SEQ,
      new M68kMnemonic(M68kTokenTypes.SEQ,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SVC,
      new M68kMnemonic(M68kTokenTypes.SVC,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SVC,
      new M68kMnemonic(M68kTokenTypes.SVC,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SVS,
      new M68kMnemonic(M68kTokenTypes.SVS,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SVS,
      new M68kMnemonic(M68kTokenTypes.SVS,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SPL,
      new M68kMnemonic(M68kTokenTypes.SPL,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SPL,
      new M68kMnemonic(M68kTokenTypes.SPL,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SMI,
      new M68kMnemonic(M68kTokenTypes.SMI,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SMI,
      new M68kMnemonic(M68kTokenTypes.SMI,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SGE,
      new M68kMnemonic(M68kTokenTypes.SGE,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SGE,
      new M68kMnemonic(M68kTokenTypes.SGE,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLT,
      new M68kMnemonic(M68kTokenTypes.SLT,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLT,
      new M68kMnemonic(M68kTokenTypes.SLT,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SGT,
      new M68kMnemonic(M68kTokenTypes.SGT,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SGT,
      new M68kMnemonic(M68kTokenTypes.SGT,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLE,
      new M68kMnemonic(M68kTokenTypes.SLE,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SLE,
      new M68kMnemonic(M68kTokenTypes.SLE,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.STOP,
      new M68kMnemonic(M68kTokenTypes.STOP,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUB,
      new M68kMnemonic(M68kTokenTypes.SUB,
        M68kOperand.DATA, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUB,
      new M68kMnemonic(M68kTokenTypes.SUB,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUB,
      new M68kMnemonic(M68kTokenTypes.SUB,
        M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUB,
      new M68kMnemonic(M68kTokenTypes.SUB,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUB,
      new M68kMnemonic(M68kTokenTypes.SUB,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUBA,
      new M68kMnemonic(M68kTokenTypes.SUBA,
        M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUBI,
      new M68kMnemonic(M68kTokenTypes.SUBI,
        M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUBI,
      new M68kMnemonic(M68kTokenTypes.SUBI,
        M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUBQ,
      new M68kMnemonic(M68kTokenTypes.SUBQ,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ADDRESS_REGISTER,
        M68kDataSize.GROUP_WL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUBQ,
      new M68kMnemonic(M68kTokenTypes.SUBQ,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_DATA,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUBX,
      new M68kMnemonic(M68kTokenTypes.SUBX,
        M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SUBX,
      new M68kMnemonic(M68kTokenTypes.SUBX,
        M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.SWAP,
      new M68kMnemonic(M68kTokenTypes.SWAP,
        M68kOperand.DATA_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_W,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.TAS,
      new M68kMnemonic(M68kTokenTypes.TAS,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_B,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.TRAP,
      new M68kMnemonic(M68kTokenTypes.TRAP,
        M68kOperand.QUICK_IMMEDIATE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.TRAPV,
      new M68kMnemonic(M68kTokenTypes.TRAPV,
        M68kOperand.NONE, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.TST,
      new M68kMnemonic(M68kTokenTypes.TST,
        M68kOperand.ALTERABLE_DATA, M68kOperand.NONE,
        M68kDataSize.GROUP_BWL,
        M68kCpu.GROUP_68000_UP));
    mnemonics.putValue(M68kTokenTypes.UNLK,
      new M68kMnemonic(M68kTokenTypes.UNLK,
        M68kOperand.ADDRESS_REGISTER, M68kOperand.NONE,
        M68kDataSize.GROUP_UNSIZED,
        M68kCpu.GROUP_68000_UP));
  }
}
