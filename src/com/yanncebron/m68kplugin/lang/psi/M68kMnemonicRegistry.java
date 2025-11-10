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

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

  /**
   * Returns all registered mnemonics for given element type.
   *
   * @return empty list if none registered or elementType is not instruction.
   */
  public Collection<M68kMnemonic> findAll(@NotNull IElementType elementType) {
    return mnemonics.get(elementType);
  }

  /**
   * Returns (most specific) mnemonic for given instruction.
   * <p>
   * Instructions **MUST** use {@code AdmXXX} as operands.
   * It is **WRONG** to use {@link com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression} or similar directly.
   *
   * @return {@code null} if none matching (e.g., contains parsing error or input is invalid); if multiple candidates, most specific one.
   */
  @Nullable
  public M68kMnemonic find(M68kInstruction instruction) {
    if (PsiTreeUtil.hasErrorElements(instruction)) {
      return null;
    }

    final IElementType originalMnemonic = instruction.getNode().getFirstChildNode().getElementType();
    final Collection<M68kMnemonic> all = findAll(originalMnemonic);
    assert !all.isEmpty() : instruction.getText();

    final List<M68kAdm> operands = PsiTreeUtil.getChildrenOfTypeAsList(instruction, M68kAdm.class);
    List<M68kMnemonic> filtered = getFilteredM68Mnemonics(instruction, operands, all);
    // this may fail if there are lexer/parser issues in file, but that's OK for now
    assert !filtered.isEmpty() : instruction.getText();

    if (filtered.size() == 1) {
      return filtered.get(0);
    }

    // multiple matches: rank by min(addressMode.count)
    List<M68kMnemonic> multipleMatches = new ArrayList<>(filtered);
    multipleMatches.sort((o1, o2) -> {
      final int o1Source = o1.sourceOperand().getAddressModes().length;
      final int o2Source = o2.sourceOperand().getAddressModes().length;
      if (o1Source != o2Source) {
        return Integer.compare(o1Source, o2Source);
      }

      final int o1Dest = o1.destinationOperand().getAddressModes().length;
      final int o2Dest = o2.destinationOperand().getAddressModes().length;
      return Integer.compare(o1Dest, o2Dest);
    });

    return ContainerUtil.getFirstItem(multipleMatches);
  }

  @NotNull
  private static @Unmodifiable List<M68kMnemonic> getFilteredM68Mnemonics(M68kInstruction instruction, List<M68kAdm> operands, Collection<M68kMnemonic> all) {
    int operandsCount = operands.size();

    // operand count
    List<M68kMnemonic> filtered = ContainerUtil.filter(all, mnemonic -> {
      boolean hasSource = mnemonic.sourceOperand() != M68kOperand.NONE;
      boolean hasDestination = mnemonic.destinationOperand() != M68kOperand.NONE;
      if (!hasSource && !hasDestination && operandsCount == 0) {
        return true;
      }

      if (hasSource && !hasDestination && operandsCount == 1) {
        return true;
      }

      return hasSource && hasDestination && operandsCount == 2;
    });

    // data size (optional)
    if (instruction instanceof M68kDataSized dataSized) {
      final M68kDataSize dataSize = dataSized.getDataSize();
      if (dataSize != null) {
        filtered = ContainerUtil.filter(filtered, mnemonic -> mnemonic.dataSizes().contains(dataSize));
      }
    }

    // addressing modes
    filtered = ContainerUtil.filter(filtered, mnemonic -> {
        if (operandsCount == 0) {
          return mnemonic.sourceOperand() == M68kOperand.NONE &&
            mnemonic.destinationOperand() == M68kOperand.NONE;
        }

        if (operandsCount == 1) {
          return operandAddressModeMatches(mnemonic.sourceOperand(), operands.get(0));
        }

        if (operandsCount == 2) {
          return operandAddressModeMatches(mnemonic.sourceOperand(), operands.get(0)) &&
            operandAddressModeMatches(mnemonic.destinationOperand(), operands.get(1));
        }

        return false;
      }
    );
    return filtered;
  }

  private static boolean operandAddressModeMatches(M68kOperand operand, M68kAdm givenAdm) {
    for (M68kAddressMode addressMode : operand.getAddressModes()) {
      for (Class<? extends M68kAdm> adm : addressMode.getAdmClasses()) {
        // match by:
        // 1. instance class
        // 2. found ARD/DRD
        if (adm.isInstance(givenAdm)) {
          return true;
        }

        if (givenAdm instanceof M68kAdmWithRrd admWithRrd) {
          if (addressMode == M68kAddressMode.ADDRESS_REGISTER && admWithRrd.getAdmArd() != null) {
            return true;
          }
          if (addressMode == M68kAddressMode.DATA_REGISTER && admWithRrd.getAdmDrd() != null) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private void add(M68kMnemonic m68kMnemonic) {
    mnemonics.putValue(m68kMnemonic.elementType(), m68kMnemonic);
  }

  private M68kMnemonicRegistry() {
// Total mnemonics: 283

// ABCD ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ABCD, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ABCD, M68kDataSize.GROUP_B,
      M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT));

// ADD ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ADD, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ADD, M68kDataSize.GROUP_WL,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ADD, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.ADD, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ADD, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));

// ADDA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ADDA, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));

// ADDI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ADDI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ADDI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));

// ADDQ ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ADDQ, M68kDataSize.GROUP_WL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ADDQ, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_DATA));

// ADDX ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ADDX, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ADDX, M68kDataSize.GROUP_BWL,
      M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT));

// AND ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.AND, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.AND, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.AND, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.AND, M68kDataSize.GROUP_B,
      M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.AND, M68kDataSize.GROUP_W,
      M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER));

// ANDI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ANDI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ANDI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.ANDI, M68kDataSize.GROUP_B,
      M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ANDI, M68kDataSize.GROUP_W,
      M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER));

// ASL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ASL, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.ASL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ASL, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ASL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// ASR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ASR, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.ASR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ASR, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ASR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// BHS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BHS, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BHS, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BLO ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BLO, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BLO, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BHI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BHI, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BHI, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BLS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BLS, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BLS, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BCC ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BCC, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BCC, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BCS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BCS, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BCS, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BNE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BNE, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BNE, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BEQ ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BEQ, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BEQ, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BVC ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BVC, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BVC, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BVS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BVS, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BVS, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BPL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BPL, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BPL, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BMI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BMI, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BMI, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BGE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BGE, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BGE, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BLT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BLT, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BLT, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BGT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BGT, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BGT, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BLE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BLE, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BLE, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BRA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BRA, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BRA, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BSR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BSR, M68kDataSize.GROUP_SBW,
      M68kOperand.BRANCH_DESTINATION));
    add(new M68kMnemonic(M68kTokenTypes.BSR, M68kDataSize.GROUP_SBWL,
      M68kOperand.BRANCH_DESTINATION,
      M68kCpu.GROUP_68020_UP));

// BCHG ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BCHG, M68kDataSize.GROUP_L,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BCHG, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.BCHG, M68kDataSize.GROUP_L,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BCHG, M68kDataSize.GROUP_B,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF));

// BCLR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BCLR, M68kDataSize.GROUP_L,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BCLR, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.BCLR, M68kDataSize.GROUP_L,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BCLR, M68kDataSize.GROUP_B,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF));

// BSET ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BSET, M68kDataSize.GROUP_L,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BSET, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.BSET, M68kDataSize.GROUP_L,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BSET, M68kDataSize.GROUP_B,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF));

// BTST ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BTST, M68kDataSize.GROUP_L,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BTST, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER, M68kOperand.MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.BTST, M68kDataSize.GROUP_L,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.BTST, M68kDataSize.GROUP_B,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_MEMORY_CF));
    add(new M68kMnemonic(M68kTokenTypes.BTST, M68kDataSize.GROUP_B,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.MEMORY_WITHOUT_IMMEDIATE));

// BKPT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.BKPT, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.QUICK_IMMEDIATE,
      M68kCpu.GROUP_68010_UP));

// CHK ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.CHK, M68kDataSize.GROUP_W,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.CHK, M68kDataSize.GROUP_L,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));

// CLR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.CLR, M68kDataSize.GROUP_BWL,
      M68kOperand.ALTERABLE_DATA));

// CMP ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.CMP, M68kDataSize.GROUP_WL,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.CMP, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.CMP, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.CMP, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.CMP, M68kDataSize.GROUP_BWL,
      M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT));

// CMPA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.CMPA, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));

// CMPI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.CMPI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.CMPI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));

// CMPM ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.CMPM, M68kDataSize.GROUP_BWL,
      M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT));

// DBT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBT, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBF ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBF, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBRA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBRA, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBHI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBHI, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBLS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBLS, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBCC ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBCC, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBHS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBHS, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBCS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBCS, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBLO ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBLO, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBNE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBNE, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBEQ ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBEQ, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBVC ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBVC, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBVS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBVS, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBPL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBPL, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBMI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBMI, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBGE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBGE, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBLT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBLT, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBGT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBGT, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DBLE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DBLE, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER, M68kOperand.DBCC_BRANCH_DESTINATION));

// DIVS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DIVS, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.DIVS, M68kDataSize.GROUP_L,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));
    add(new M68kMnemonic(M68kTokenTypes.DIVS, M68kDataSize.GROUP_W,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.DIVS, M68kDataSize.GROUP_L,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));

// DIVU ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.DIVU, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.DIVU, M68kDataSize.GROUP_L,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));
    add(new M68kMnemonic(M68kTokenTypes.DIVU, M68kDataSize.GROUP_W,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.DIVU, M68kDataSize.GROUP_L,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));

// EOR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.EOR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.EOR, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.EOR, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.EOR, M68kDataSize.GROUP_B,
      M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.EOR, M68kDataSize.GROUP_W,
      M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER));

// EORI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.EORI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.EORI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.EORI, M68kDataSize.GROUP_B,
      M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.EORI, M68kDataSize.GROUP_W,
      M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER));

// EXG ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.EXG, M68kDataSize.GROUP_L,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.EXG, M68kDataSize.GROUP_L,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.EXG, M68kDataSize.GROUP_L,
      M68kOperand.DATA_REGISTER, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.EXG, M68kDataSize.GROUP_L,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER));

// EXT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.EXT, M68kDataSize.GROUP_WL,
      M68kOperand.DATA_REGISTER));

// ILLEGAL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ILLEGAL, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.NONE));

// JMP ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.JMP, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.CONTROL));

// JSR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.JSR, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.CONTROL));

// LEA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.LEA, M68kDataSize.GROUP_L,
      M68kOperand.CONTROL, M68kOperand.ADDRESS_REGISTER));

// LINK ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.LINK, M68kDataSize.GROUP_W,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.IMMEDIATE));
    add(new M68kMnemonic(M68kTokenTypes.LINK, M68kDataSize.GROUP_L,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.IMMEDIATE,
      M68kCpu.GROUP_68020_UP));

// LSL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.LSL, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.LSL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.LSL, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.LSL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// LSR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.LSR, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.LSR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.LSR, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.LSR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// MOVE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_WL,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.ALTERABLE));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_W,
      M68kOperand.CCR_REGISTER, M68kOperand.ALTERABLE_DATA,
      M68kCpu.GROUP_68010_UP));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_W,
      M68kOperand.SR_REGISTER, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_W,
      M68kOperand.DATA, M68kOperand.CCR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_W,
      M68kOperand.DATA, M68kOperand.SR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_L,
      M68kOperand.USP_REGISTER, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MOVE, M68kDataSize.GROUP_L,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.USP_REGISTER));

// MOVEA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MOVEA, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MOVEA, M68kDataSize.GROUP_WL,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.ALTERABLE));
    add(new M68kMnemonic(M68kTokenTypes.MOVEA, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA, M68kOperand.ALTERABLE_DATA));

// MOVEC ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MOVEC, M68kDataSize.GROUP_L,
      M68kOperand.CTRL_REGISTER, M68kOperand.DATA_OR_ADDRESS_REGISTER,
      M68kCpu.GROUP_68010_UP));
    add(new M68kMnemonic(M68kTokenTypes.MOVEC, M68kDataSize.GROUP_L,
      M68kOperand.DATA_OR_ADDRESS_REGISTER, M68kOperand.CTRL_REGISTER,
      M68kCpu.GROUP_68010_UP));

// MOVEM ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MOVEM, M68kDataSize.GROUP_WL,
      M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT));
    add(new M68kMnemonic(M68kTokenTypes.MOVEM, M68kDataSize.GROUP_WL,
      M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST, M68kOperand.ALTERABLE_CONTROL));
    add(new M68kMnemonic(M68kTokenTypes.MOVEM, M68kDataSize.GROUP_WL,
      M68kOperand.RESTORE_OPERANDS, M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST));
    add(new M68kMnemonic(M68kTokenTypes.MOVEM, M68kDataSize.GROUP_WL,
      M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT));
    add(new M68kMnemonic(M68kTokenTypes.MOVEM, M68kDataSize.GROUP_WL,
      M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE, M68kOperand.ALTERABLE_CONTROL));
    add(new M68kMnemonic(M68kTokenTypes.MOVEM, M68kDataSize.GROUP_WL,
      M68kOperand.RESTORE_OPERANDS, M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE));

// MOVEP ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MOVEP, M68kDataSize.GROUP_WL,
      M68kOperand.ADDRESS_REGISTER_DISPLACEMENT, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MOVEP, M68kDataSize.GROUP_WL,
      M68kOperand.DATA_REGISTER, M68kOperand.ADDRESS_REGISTER_DISPLACEMENT));

// MOVEQ ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MOVEQ, M68kDataSize.GROUP_L,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));

// MOVES ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MOVES, M68kDataSize.GROUP_BWL,
      M68kOperand.ALTERABLE_MEMORY, M68kOperand.DATA_OR_ADDRESS_REGISTER,
      M68kCpu.GROUP_68010_UP));
    add(new M68kMnemonic(M68kTokenTypes.MOVES, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_OR_ADDRESS_REGISTER, M68kOperand.ALTERABLE_MEMORY,
      M68kCpu.GROUP_68010_UP));

// MULS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MULS, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MULS, M68kDataSize.GROUP_L,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));
    add(new M68kMnemonic(M68kTokenTypes.MULS, M68kDataSize.GROUP_W,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MULS, M68kDataSize.GROUP_L,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));

// MULU ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.MULU, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MULU, M68kDataSize.GROUP_L,
      M68kOperand.ALTERABLE_DATA_CF, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));
    add(new M68kMnemonic(M68kTokenTypes.MULU, M68kDataSize.GROUP_W,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.MULU, M68kDataSize.GROUP_L,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER,
      M68kCpu.GROUP_68020_UP));

// NBCD ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.NBCD, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// NEG ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.NEG, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.NEG, M68kDataSize.GROUP_BWL,
      M68kOperand.ALTERABLE_DATA));

// NEGX ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.NEGX, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.NEGX, M68kDataSize.GROUP_BWL,
      M68kOperand.ALTERABLE_DATA));

// NOP ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.NOP, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.NONE));

// NOT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.NOT, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.NOT, M68kDataSize.GROUP_BWL,
      M68kOperand.ALTERABLE_DATA));

// OR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.OR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.OR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.OR, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.OR, M68kDataSize.GROUP_B,
      M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.OR, M68kDataSize.GROUP_W,
      M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER));

// ORI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ORI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ORI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.ORI, M68kDataSize.GROUP_B,
      M68kOperand.IMMEDIATE, M68kOperand.CCR_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ORI, M68kDataSize.GROUP_W,
      M68kOperand.IMMEDIATE, M68kOperand.SR_REGISTER));

// PEA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.PEA, M68kDataSize.GROUP_L,
      M68kOperand.CONTROL));

// RESET ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.RESET, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.NONE));

// ROL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ROL, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.ROL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROL, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// ROR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ROR, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.ROR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROR, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// ROXL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ROXL, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.ROXL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROXL, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROXL, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// ROXR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ROXR, M68kDataSize.GROUP_W,
      M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.ROXR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROXR, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ROXR, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER));

// RTD ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.RTD, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.QUICK_IMMEDIATE,
      M68kCpu.GROUP_68010_UP));

// RTE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.RTE, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.NONE));

// RTR ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.RTR, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.NONE));

// RTS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.RTS, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.NONE));

// SBCD ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SBCD, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SBCD, M68kDataSize.GROUP_B,
      M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT));

// ST ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.ST, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.ST, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SF ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SF, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SF, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SHI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SHI, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SHI, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SLS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SLS, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SLS, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SCC ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SCC, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SCC, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SHS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SHS, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SHS, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SCS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SCS, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SCS, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SLO ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SLO, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SLO, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SNE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SNE, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SNE, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SEQ ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SEQ, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SEQ, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SVC ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SVC, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SVC, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SVS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SVS, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SVS, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SPL ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SPL, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SPL, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SMI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SMI, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SMI, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SGE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SGE, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SGE, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SLT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SLT, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SLT, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SGT ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SGT, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SGT, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// SLE ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SLE, M68kDataSize.GROUP_B,
      M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SLE, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// STOP ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.STOP, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.QUICK_IMMEDIATE));

// SUB ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SUB, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SUB, M68kDataSize.GROUP_WL,
      M68kOperand.ADDRESS_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SUB, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.ALTERABLE_MEMORY));
    add(new M68kMnemonic(M68kTokenTypes.SUB, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SUB, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));

// SUBA ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SUBA, M68kDataSize.GROUP_WL,
      M68kOperand.ALL, M68kOperand.ADDRESS_REGISTER));

// SUBI ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SUBI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SUBI, M68kDataSize.GROUP_BWL,
      M68kOperand.IMMEDIATE, M68kOperand.ALTERABLE_DATA));

// SUBQ ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SUBQ, M68kDataSize.GROUP_WL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ADDRESS_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SUBQ, M68kDataSize.GROUP_BWL,
      M68kOperand.QUICK_IMMEDIATE, M68kOperand.ALTERABLE_DATA));

// SUBX ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SUBX, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA_REGISTER, M68kOperand.DATA_REGISTER));
    add(new M68kMnemonic(M68kTokenTypes.SUBX, M68kDataSize.GROUP_BWL,
      M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT));

// SWAP ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.SWAP, M68kDataSize.GROUP_W,
      M68kOperand.DATA_REGISTER));

// TAS ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.TAS, M68kDataSize.GROUP_B,
      M68kOperand.ALTERABLE_DATA));

// TRAP ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.TRAP, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.QUICK_IMMEDIATE));

// TRAPV ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.TRAPV, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.NONE));

// TST ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.TST, M68kDataSize.GROUP_BWL,
      M68kOperand.ALTERABLE_DATA));
    add(new M68kMnemonic(M68kTokenTypes.TST, M68kDataSize.GROUP_BWL,
      M68kOperand.DATA,
      M68kCpu.GROUP_68020_UP));
    add(new M68kMnemonic(M68kTokenTypes.TST, M68kDataSize.GROUP_WL,
      M68kOperand.ADDRESS_REGISTER,
      M68kCpu.GROUP_68020_UP));

// UNLK ----------------------------------------------------------------------
    add(new M68kMnemonic(M68kTokenTypes.UNLK, M68kDataSize.GROUP_UNSIZED,
      M68kOperand.ADDRESS_REGISTER));
  }
}
