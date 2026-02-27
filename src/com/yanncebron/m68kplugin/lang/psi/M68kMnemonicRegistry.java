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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.yanncebron.m68kplugin.lang.psi.M68kCpu.GROUP_68010_UP;
import static com.yanncebron.m68kplugin.lang.psi.M68kCpu.GROUP_68020_UP;
import static com.yanncebron.m68kplugin.lang.psi.M68kDataSize.*;
import static com.yanncebron.m68kplugin.lang.psi.M68kOperand.*;

/**
 * Registry of all supported mnemonics.
 * <p>
 * Data is generated via {@code M68kMnemonicRegistryGeneratorTest} using <a href="http://sun.hasenbraten.de/vasm"/>vasm</a> sources.
 * Thanks to vasm project owners Frank Wille and Dr. Volker Barthelmann for granting permission.
 */
public final class M68kMnemonicRegistry {

  // fixed order
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
  public M68kMnemonic find(@NotNull M68kInstruction instruction) {
    if (PsiTreeUtil.hasErrorElements(instruction)) {
      return null;
    }

    final IElementType originalMnemonic = instruction.getNode().getFirstChildNode().getElementType();
    final Collection<M68kMnemonic> all = findAll(originalMnemonic);
    assert !all.isEmpty() : instruction.getText();

    List<M68kMnemonic> filtered = getFilteredM68Mnemonics(all, instruction);
    // this may fail if there are lexer/parser issues in file, but that's OK for now
    // TODO disabled as we need finding mnemonic for invalid (suppressed) highlighting as well
    // assert !filtered.isEmpty() : instruction.getText();
    if (filtered.isEmpty()) {
      return null;
    }

    if (filtered.size() == 1) {
      return filtered.get(0);
    }

    // multiple matches: sort by min(addressMode.count), so IMMEDIATE wins over DATA etc.
    List<M68kMnemonic> multipleMatches = new SmartList<>(filtered);
    multipleMatches.sort((o1, o2) -> {
      final int o1Source = o1.firstOperand().getAddressModes().length;
      final int o2Source = o2.firstOperand().getAddressModes().length;
      if (o1Source != o2Source) {
        return Integer.compare(o1Source, o2Source);
      }

      final int o1Destination = o1.secondOperand().getAddressModes().length;
      final int o2Destination = o2.secondOperand().getAddressModes().length;
      return Integer.compare(o1Destination, o2Destination);
    });

    return multipleMatches.get(0);
  }

  @NotNull
  private static @Unmodifiable List<M68kMnemonic> getFilteredM68Mnemonics(Collection<M68kMnemonic> all, M68kInstruction instruction) {
    List<M68kAdm> admList = PsiTreeUtil.getChildrenOfTypeAsList(instruction, M68kAdm.class);
    int operandsCount = admList.size();

    // operand count
    List<M68kMnemonic> filtered = ContainerUtil.filter(all, mnemonic -> {
      boolean hasFirstOperand = mnemonic.firstOperand() != NONE;
      boolean hasSecondOperand = mnemonic.secondOperand() != NONE;
      if (!hasFirstOperand && !hasSecondOperand && operandsCount == 0) {
        return true;
      }

      if (hasFirstOperand && !hasSecondOperand && operandsCount == 1) {
        return true;
      }

      return hasFirstOperand && hasSecondOperand && operandsCount == 2;
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
          return mnemonic.firstOperand() == NONE &&
            mnemonic.secondOperand() == NONE;
        }

        if (operandsCount == 1) {
          return mnemonic.firstOperand().matches(admList.get(0));
        }

        if (operandsCount == 2) {
          return mnemonic.firstOperand().matches(admList.get(0)) &&
            mnemonic.secondOperand().matches(admList.get(1));
        }

        return false;
      }
    );
    return filtered;
  }

  private MnemonicBuilder create(IElementType elementType) {
    return new MnemonicBuilder(elementType);
  }

  private class MnemonicBuilder {
    private final IElementType elementType;
    private Set<M68kDataSize> dataSizes = GROUP_UNSIZED;
    private M68kOperand firstOperand = NONE;
    private M68kOperand secondOperand = NONE;
    private Set<M68kCpu> cpus = M68kCpu.GROUP_68000_UP;
    private M68kMnemonic.PrivilegedType privilegedType = M68kMnemonic.PrivilegedType.NONE;

    private MnemonicBuilder(IElementType elementType) {
      this.elementType = elementType;
    }

    private MnemonicBuilder dataSizes(Set<M68kDataSize> dataSizes) {
      this.dataSizes = dataSizes;
      return this;
    }

    private MnemonicBuilder first(M68kOperand sourceOperand) {
      this.firstOperand = sourceOperand;
      return this;
    }

    private MnemonicBuilder second(M68kOperand destinationOperand) {
      this.secondOperand = destinationOperand;
      return this;
    }

    private MnemonicBuilder cpus(Set<M68kCpu> cpus) {
      this.cpus = cpus;
      return this;
    }

    private MnemonicBuilder privileged(M68kMnemonic.PrivilegedType privilegedType) {
      this.privilegedType = privilegedType;
      return this;
    }

    private void build() {
      M68kMnemonic m68kMnemonic = new M68kMnemonic(elementType, dataSizes, firstOperand, secondOperand, cpus, privilegedType);
      mnemonics.putValue(m68kMnemonic.elementType(), m68kMnemonic);
    }
  }

  private M68kMnemonicRegistry() {
// Total mnemonics: 283

// ABCD ----------------------------------------------------------------------------

    create(M68kTokenTypes.ABCD).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ABCD).dataSizes(GROUP_B)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

// ADD -----------------------------------------------------------------------------

    create(M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

// ADDA ----------------------------------------------------------------------------

    create(M68kTokenTypes.ADDA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

// ADDI ----------------------------------------------------------------------------

    create(M68kTokenTypes.ADDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ADDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

// ADDQ ----------------------------------------------------------------------------

    create(M68kTokenTypes.ADDQ).dataSizes(GROUP_WL)
      .first(QUICK_IMMEDIATE).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.ADDQ).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_DATA)
      .build();

// ADDX ----------------------------------------------------------------------------

    create(M68kTokenTypes.ADDX).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ADDX).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

// AND -----------------------------------------------------------------------------

    create(M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// ANDI ----------------------------------------------------------------------------

    create(M68kTokenTypes.ANDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ANDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.ANDI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .build();

    create(M68kTokenTypes.ANDI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// ASL -----------------------------------------------------------------------------

    create(M68kTokenTypes.ASL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// ASR -----------------------------------------------------------------------------

    create(M68kTokenTypes.ASR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// BHS -----------------------------------------------------------------------------

    create(M68kTokenTypes.BHS).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BHS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BLO -----------------------------------------------------------------------------

    create(M68kTokenTypes.BLO).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BLO).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BHI -----------------------------------------------------------------------------

    create(M68kTokenTypes.BHI).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BHI).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BLS -----------------------------------------------------------------------------

    create(M68kTokenTypes.BLS).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BLS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BCC -----------------------------------------------------------------------------

    create(M68kTokenTypes.BCC).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BCC).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BCS -----------------------------------------------------------------------------

    create(M68kTokenTypes.BCS).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BCS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BNE -----------------------------------------------------------------------------

    create(M68kTokenTypes.BNE).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BNE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BEQ -----------------------------------------------------------------------------

    create(M68kTokenTypes.BEQ).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BEQ).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BVC -----------------------------------------------------------------------------

    create(M68kTokenTypes.BVC).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BVC).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BVS -----------------------------------------------------------------------------

    create(M68kTokenTypes.BVS).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BVS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BPL -----------------------------------------------------------------------------

    create(M68kTokenTypes.BPL).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BPL).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BMI -----------------------------------------------------------------------------

    create(M68kTokenTypes.BMI).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BMI).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BGE -----------------------------------------------------------------------------

    create(M68kTokenTypes.BGE).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BGE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BLT -----------------------------------------------------------------------------

    create(M68kTokenTypes.BLT).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BLT).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BGT -----------------------------------------------------------------------------

    create(M68kTokenTypes.BGT).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BGT).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BLE -----------------------------------------------------------------------------

    create(M68kTokenTypes.BLE).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BLE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BRA -----------------------------------------------------------------------------

    create(M68kTokenTypes.BRA).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BRA).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BSR -----------------------------------------------------------------------------

    create(M68kTokenTypes.BSR).dataSizes(GROUP_SBW)
      .first(BRANCH_DESTINATION)
      .build();

    create(M68kTokenTypes.BSR).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .cpus(GROUP_68020_UP)
      .build();

// BCHG ----------------------------------------------------------------------------

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY_CF)
      .build();

// BCLR ----------------------------------------------------------------------------

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY_CF)
      .build();

// BSET ----------------------------------------------------------------------------

    create(M68kTokenTypes.BSET).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BSET).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.BSET).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BSET).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY_CF)
      .build();

// BTST ----------------------------------------------------------------------------

    create(M68kTokenTypes.BTST).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BTST).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(MEMORY)
      .build();

    create(M68kTokenTypes.BTST).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.BTST).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY_CF)
      .build();

    create(M68kTokenTypes.BTST).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(MEMORY_WITHOUT_IMMEDIATE)
      .build();

// BKPT ----------------------------------------------------------------------------

    create(M68kTokenTypes.BKPT)
      .first(QUICK_IMMEDIATE)
      .cpus(GROUP_68010_UP)
      .build();

// CHK -----------------------------------------------------------------------------

    create(M68kTokenTypes.CHK).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.CHK).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

// CLR -----------------------------------------------------------------------------

    create(M68kTokenTypes.CLR).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .build();

// CMP -----------------------------------------------------------------------------

    create(M68kTokenTypes.CMP).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT).second(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT)
      .build();

// CMPA ----------------------------------------------------------------------------

    create(M68kTokenTypes.CMPA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

// CMPI ----------------------------------------------------------------------------

    create(M68kTokenTypes.CMPI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.CMPI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

// CMPM ----------------------------------------------------------------------------

    create(M68kTokenTypes.CMPM).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT).second(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT)
      .build();

// DBT -----------------------------------------------------------------------------

    create(M68kTokenTypes.DBT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBF -----------------------------------------------------------------------------

    create(M68kTokenTypes.DBF).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBRA ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBRA).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBHI ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBHI).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBLS ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBLS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBCC ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBCC).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBHS ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBHS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBCS ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBCS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBLO ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBLO).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBNE ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBNE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBEQ ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBEQ).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBVC ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBVC).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBVS ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBVS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBPL ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBPL).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBMI ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBMI).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBGE ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBGE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBLT ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBLT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBGT ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBGT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DBLE ----------------------------------------------------------------------------

    create(M68kTokenTypes.DBLE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .build();

// DIVS ----------------------------------------------------------------------------

    create(M68kTokenTypes.DIVS).dataSizes(GROUP_W)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.DIVS).dataSizes(GROUP_L)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

    create(M68kTokenTypes.DIVS).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.DIVS).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

// DIVU ----------------------------------------------------------------------------

    create(M68kTokenTypes.DIVU).dataSizes(GROUP_W)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.DIVU).dataSizes(GROUP_L)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

    create(M68kTokenTypes.DIVU).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.DIVU).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

// EOR -----------------------------------------------------------------------------

    create(M68kTokenTypes.EOR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.EOR).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.EOR).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.EOR).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .build();

    create(M68kTokenTypes.EOR).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// EORI ----------------------------------------------------------------------------

    create(M68kTokenTypes.EORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.EORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.EORI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .build();

    create(M68kTokenTypes.EORI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// EXG -----------------------------------------------------------------------------

    create(M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .build();

// EXT -----------------------------------------------------------------------------

    create(M68kTokenTypes.EXT).dataSizes(GROUP_WL)
      .first(DATA_REGISTER)
      .build();

// ILLEGAL -------------------------------------------------------------------------

    create(M68kTokenTypes.ILLEGAL)
      .build();

// JMP -----------------------------------------------------------------------------

    create(M68kTokenTypes.JMP)
      .first(CONTROL)
      .build();

// JSR -----------------------------------------------------------------------------

    create(M68kTokenTypes.JSR)
      .first(CONTROL)
      .build();

// LEA -----------------------------------------------------------------------------

    create(M68kTokenTypes.LEA).dataSizes(GROUP_L)
      .first(CONTROL).second(ADDRESS_REGISTER)
      .build();

// LINK ----------------------------------------------------------------------------

    create(M68kTokenTypes.LINK).dataSizes(GROUP_W)
      .first(ADDRESS_REGISTER).second(IMMEDIATE)
      .build();

    create(M68kTokenTypes.LINK).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(IMMEDIATE)
      .cpus(GROUP_68020_UP)
      .build();

// LSL -----------------------------------------------------------------------------

    create(M68kTokenTypes.LSL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// LSR -----------------------------------------------------------------------------

    create(M68kTokenTypes.LSR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// MOVE ----------------------------------------------------------------------------

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(ALTERABLE)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_BWL)
      .first(DATA).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(CCR_REGISTER).second(ALTERABLE_DATA)
      .cpus(GROUP_68010_UP)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(SR_REGISTER).second(ALTERABLE_DATA)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED_68010_ABOVE)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(DATA).second(CCR_REGISTER)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(DATA).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_L)
      .first(USP_REGISTER).second(ADDRESS_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(USP_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// MOVEA ---------------------------------------------------------------------------

    create(M68kTokenTypes.MOVEA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.MOVEA).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(ALTERABLE)
      .build();

    create(M68kTokenTypes.MOVEA).dataSizes(GROUP_BWL)
      .first(DATA).second(ALTERABLE_DATA)
      .build();

// MOVEC ---------------------------------------------------------------------------

    create(M68kTokenTypes.MOVEC).dataSizes(GROUP_L)
      .first(CTRL_REGISTER).second(DATA_OR_ADDRESS_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

    create(M68kTokenTypes.MOVEC).dataSizes(GROUP_L)
      .first(DATA_OR_ADDRESS_REGISTER).second(CTRL_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// MOVEM ---------------------------------------------------------------------------

    create(M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(DATA_OR_ADDRESS_REGISTER_LIST).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

    create(M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(DATA_OR_ADDRESS_REGISTER_LIST).second(ALTERABLE_CONTROL)
      .build();

    create(M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(RESTORE_OPERANDS).second(DATA_OR_ADDRESS_REGISTER_LIST)
      .build();

    create(M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(IMMEDIATE_REGISTER_LIST_VALUE).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

    create(M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(IMMEDIATE_REGISTER_LIST_VALUE).second(ALTERABLE_CONTROL)
      .build();

    create(M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(RESTORE_OPERANDS).second(IMMEDIATE_REGISTER_LIST_VALUE)
      .build();

// MOVEP ---------------------------------------------------------------------------

    create(M68kTokenTypes.MOVEP).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER_DISPLACEMENT).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.MOVEP).dataSizes(GROUP_WL)
      .first(DATA_REGISTER).second(ADDRESS_REGISTER_DISPLACEMENT)
      .build();

// MOVEQ ---------------------------------------------------------------------------

    create(M68kTokenTypes.MOVEQ).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

// MOVES ---------------------------------------------------------------------------

    create(M68kTokenTypes.MOVES).dataSizes(GROUP_BWL)
      .first(ALTERABLE_MEMORY).second(DATA_OR_ADDRESS_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

    create(M68kTokenTypes.MOVES).dataSizes(GROUP_BWL)
      .first(DATA_OR_ADDRESS_REGISTER).second(ALTERABLE_MEMORY)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// MULS ----------------------------------------------------------------------------

    create(M68kTokenTypes.MULS).dataSizes(GROUP_W)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.MULS).dataSizes(GROUP_L)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

    create(M68kTokenTypes.MULS).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.MULS).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

// MULU ----------------------------------------------------------------------------

    create(M68kTokenTypes.MULU).dataSizes(GROUP_W)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.MULU).dataSizes(GROUP_L)
      .first(ALTERABLE_DATA_CF).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

    create(M68kTokenTypes.MULU).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.MULU).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

// NBCD ----------------------------------------------------------------------------

    create(M68kTokenTypes.NBCD).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// NEG -----------------------------------------------------------------------------

    create(M68kTokenTypes.NEG).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.NEG).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .build();

// NEGX ----------------------------------------------------------------------------

    create(M68kTokenTypes.NEGX).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.NEGX).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .build();

// NOP -----------------------------------------------------------------------------

    create(M68kTokenTypes.NOP)
      .build();

// NOT -----------------------------------------------------------------------------

    create(M68kTokenTypes.NOT).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.NOT).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .build();

// OR ------------------------------------------------------------------------------

    create(M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// ORI -----------------------------------------------------------------------------

    create(M68kTokenTypes.ORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.ORI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .build();

    create(M68kTokenTypes.ORI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// PEA -----------------------------------------------------------------------------

    create(M68kTokenTypes.PEA).dataSizes(GROUP_L)
      .first(CONTROL)
      .build();

// RESET ---------------------------------------------------------------------------

    create(M68kTokenTypes.RESET)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// ROL -----------------------------------------------------------------------------

    create(M68kTokenTypes.ROL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// ROR -----------------------------------------------------------------------------

    create(M68kTokenTypes.ROR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// ROXL ----------------------------------------------------------------------------

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// ROXR ----------------------------------------------------------------------------

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .build();

// RTD -----------------------------------------------------------------------------

    create(M68kTokenTypes.RTD)
      .first(QUICK_IMMEDIATE)
      .cpus(GROUP_68010_UP)
      .build();

// RTE -----------------------------------------------------------------------------

    create(M68kTokenTypes.RTE)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// RTR -----------------------------------------------------------------------------

    create(M68kTokenTypes.RTR)
      .build();

// RTS -----------------------------------------------------------------------------

    create(M68kTokenTypes.RTS)
      .build();

// SBCD ----------------------------------------------------------------------------

    create(M68kTokenTypes.SBCD).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SBCD).dataSizes(GROUP_B)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

// ST ------------------------------------------------------------------------------

    create(M68kTokenTypes.ST).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.ST).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SF ------------------------------------------------------------------------------

    create(M68kTokenTypes.SF).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SF).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SHI -----------------------------------------------------------------------------

    create(M68kTokenTypes.SHI).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SHI).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SLS -----------------------------------------------------------------------------

    create(M68kTokenTypes.SLS).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SLS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SCC -----------------------------------------------------------------------------

    create(M68kTokenTypes.SCC).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SCC).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SHS -----------------------------------------------------------------------------

    create(M68kTokenTypes.SHS).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SHS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SCS -----------------------------------------------------------------------------

    create(M68kTokenTypes.SCS).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SCS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SLO -----------------------------------------------------------------------------

    create(M68kTokenTypes.SLO).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SLO).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SNE -----------------------------------------------------------------------------

    create(M68kTokenTypes.SNE).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SNE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SEQ -----------------------------------------------------------------------------

    create(M68kTokenTypes.SEQ).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SEQ).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SVC -----------------------------------------------------------------------------

    create(M68kTokenTypes.SVC).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SVC).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SVS -----------------------------------------------------------------------------

    create(M68kTokenTypes.SVS).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SVS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SPL -----------------------------------------------------------------------------

    create(M68kTokenTypes.SPL).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SPL).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SMI -----------------------------------------------------------------------------

    create(M68kTokenTypes.SMI).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SMI).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SGE -----------------------------------------------------------------------------

    create(M68kTokenTypes.SGE).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SGE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SLT -----------------------------------------------------------------------------

    create(M68kTokenTypes.SLT).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SLT).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SGT -----------------------------------------------------------------------------

    create(M68kTokenTypes.SGT).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SGT).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// SLE -----------------------------------------------------------------------------

    create(M68kTokenTypes.SLE).dataSizes(GROUP_B)
      .first(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SLE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// STOP ----------------------------------------------------------------------------

    create(M68kTokenTypes.STOP)
      .first(QUICK_IMMEDIATE)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .build();

// SUB -----------------------------------------------------------------------------

    create(M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

// SUBA ----------------------------------------------------------------------------

    create(M68kTokenTypes.SUBA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

// SUBI ----------------------------------------------------------------------------

    create(M68kTokenTypes.SUBI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SUBI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .build();

// SUBQ ----------------------------------------------------------------------------

    create(M68kTokenTypes.SUBQ).dataSizes(GROUP_WL)
      .first(QUICK_IMMEDIATE).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.SUBQ).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_DATA)
      .build();

// SUBX ----------------------------------------------------------------------------

    create(M68kTokenTypes.SUBX).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.SUBX).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

// SWAP ----------------------------------------------------------------------------

    create(M68kTokenTypes.SWAP).dataSizes(GROUP_W)
      .first(DATA_REGISTER)
      .build();

// TAS -----------------------------------------------------------------------------

    create(M68kTokenTypes.TAS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .build();

// TRAP ----------------------------------------------------------------------------

    create(M68kTokenTypes.TRAP)
      .first(QUICK_IMMEDIATE)
      .build();

// TRAPV ---------------------------------------------------------------------------

    create(M68kTokenTypes.TRAPV)
      .build();

// TST -----------------------------------------------------------------------------

    create(M68kTokenTypes.TST).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .build();

    create(M68kTokenTypes.TST).dataSizes(GROUP_BWL)
      .first(DATA)
      .cpus(GROUP_68020_UP)
      .build();

    create(M68kTokenTypes.TST).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER)
      .cpus(GROUP_68020_UP)
      .build();

// UNLK ----------------------------------------------------------------------------

    create(M68kTokenTypes.UNLK)
      .first(ADDRESS_REGISTER)
      .build();
  }
}
