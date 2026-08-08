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
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.yanncebron.m68kplugin.lang.psi.M68kCpu.*;
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
   * Returns all registered mnemonics for the given element type.
   *
   * @return empty list if none registered or elementType is not instruction.
   */
  public Collection<M68kMnemonic> findAll(@NotNull IElementType elementType) {
    return mnemonics.get(elementType);
  }

  /**
   * Returns (most specific) mnemonic for the given instruction.
   * <p>
   * Instructions **MUST** use {@code AdmXXX} as operands.
   * It is **WRONG** to use {@link com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression} or similar directly.
   *
   * @return {@code null} if none matching (e.g., contains a parsing error or input is invalid); if multiple candidates, most specific one.
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

    // multiple matches: sort by
    // 1. not deprecated
    // 2. min(addressMode.count), so IMMEDIATE wins over DATA etc.
    List<M68kMnemonic> multipleMatches = new SmartList<>(filtered);
    multipleMatches.sort((o1, o2) -> {
      if (o1.deprecated()) {
        return 1;
      }

      final int o1FirstOperandAddressModesCount = o1.firstOperand().getAddressModes().length;
      final int o2FirstOperandAddressModesCount = o2.firstOperand().getAddressModes().length;
      if (o1FirstOperandAddressModesCount != o2FirstOperandAddressModesCount) {
        return Integer.compare(o1FirstOperandAddressModesCount, o2FirstOperandAddressModesCount);
      }

      final int o1SecondOperandAddressModesCount = o1.secondOperand().getAddressModes().length;
      final int o2SecondOperandAddressModesCount = o2.secondOperand().getAddressModes().length;
      return Integer.compare(o1SecondOperandAddressModesCount, o2SecondOperandAddressModesCount);
    });

    return multipleMatches.get(0);
  }

  @NotNull
  private static @Unmodifiable List<M68kMnemonic> getFilteredM68Mnemonics(Collection<M68kMnemonic> all, M68kInstruction instruction) {
    final M68kDataSize dataSize = instruction instanceof M68kDataSized dataSized ? dataSized.getDataSize() : null;

    List<M68kAdm> admList = PsiTreeUtil.getChildrenOfTypeAsList(instruction, M68kAdm.class);
    int operandsCount = admList.size();

    return ContainerUtil.filter(all, mnemonic -> {

      // data size (optional)
      if (dataSize != null && !mnemonic.dataSizes().contains(dataSize)) {
        return false;
      }

      // operand count / addressing modes
      boolean hasFirstOperand = mnemonic.hasFirstOperand();
      boolean hasSecondOperand = mnemonic.hasSecondOperand();
      if (operandsCount == 0 && !hasFirstOperand && !hasSecondOperand) {
        return true;
      }

      if (operandsCount == 1 && hasFirstOperand && !hasSecondOperand) {
        return mnemonic.firstOperand().matches(admList.get(0));
      }

      if (operandsCount == 2 && hasFirstOperand && hasSecondOperand) {
        return mnemonic.firstOperand().matches(admList.get(0)) &&
          mnemonic.secondOperand().matches(admList.get(1));
      }

      return false;
    });
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
    private boolean deprecated = false;

    private M68kMnemonic.PrivilegedType privilegedType = M68kMnemonic.PrivilegedType.NONE;
    private M68kMnemonic.ControlFlow controlFlow = M68kMnemonic.ControlFlow.NOTHING;
    private M68kMnemonic.ConditionCodes affected = M68kMnemonic.ConditionCodes.NONE_AFFECTED;
    private M68kMnemonic.ConditionCodes tested = M68kMnemonic.ConditionCodes.NONE_AFFECTED;

    private MnemonicBuilder(IElementType elementType) {
      this.elementType = elementType;
    }

    private MnemonicBuilder dataSizes(Set<M68kDataSize> dataSizes) {
      this.dataSizes = dataSizes;
      return this;
    }

    private MnemonicBuilder first(M68kOperand firstOperand) {
      this.firstOperand = firstOperand;
      return this;
    }

    private MnemonicBuilder second(M68kOperand secondOperand) {
      this.secondOperand = secondOperand;
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

    private MnemonicBuilder deprecated() {
      this.deprecated = true;
      return this;
    }

    private MnemonicBuilder controlFlow(M68kMnemonic.ControlFlow controlFlow) {
      this.controlFlow = controlFlow;
      return this;
    }

    private MnemonicBuilder affected(String value) {
      this.affected = M68kMnemonic.ConditionCodes.parseAffected(value);
      return this;
    }

    private MnemonicBuilder tested(String value) {
      this.tested = M68kMnemonic.ConditionCodes.parseTested(value);
      return this;
    }

    private void build() {
      M68kMnemonic m68kMnemonic = new M68kMnemonic(elementType, dataSizes,
        firstOperand, secondOperand, cpus, deprecated,
        privilegedType, controlFlow,
        affected, tested);
      mnemonics.putValue(m68kMnemonic.elementType(), m68kMnemonic);
    }
  }

  private M68kMnemonicRegistry() {
// Total mnemonics: 244

// ABCD ------------------------------------------------------------------------

    create(M68kTokenTypes.ABCD).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

    create(M68kTokenTypes.ABCD).dataSizes(GROUP_B)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

// ADD -------------------------------------------------------------------------

    create(M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("C****")
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// ADDA ------------------------------------------------------------------------

    create(M68kTokenTypes.ADDA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

// ADDI ------------------------------------------------------------------------

    create(M68kTokenTypes.ADDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// ADDQ ------------------------------------------------------------------------

    create(M68kTokenTypes.ADDQ).dataSizes(GROUP_WL)
      .first(QUICK_IMMEDIATE).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.ADDQ).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// ADDX ------------------------------------------------------------------------

    create(M68kTokenTypes.ADDX).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("C*A**")
      .tested("?-?--")
      .build();

    create(M68kTokenTypes.ADDX).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("C*A**")
      .tested("?-?--")
      .build();

// AND -------------------------------------------------------------------------

    create(M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("AAAAA")
      .tested("?????")
      .build();

    create(M68kTokenTypes.AND).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("AAAAA")
      .tested("?????")
      .build();

// ANDI ------------------------------------------------------------------------

    create(M68kTokenTypes.ANDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.ANDI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("AAAAA")
      .tested("?????")
      .build();

    create(M68kTokenTypes.ANDI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("AAAAA")
      .tested("?????")
      .build();

// ASL -------------------------------------------------------------------------

    create(M68kTokenTypes.ASL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("*****")
      .build();

    create(M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("*****")
      .build();

    create(M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("*****")
      .build();

    create(M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("*****")
      .build();

// ASR -------------------------------------------------------------------------

    create(M68kTokenTypes.ASR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .build();

// BHS -------------------------------------------------------------------------

    create(M68kTokenTypes.BHS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BLO -------------------------------------------------------------------------

    create(M68kTokenTypes.BLO).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BHI -------------------------------------------------------------------------

    create(M68kTokenTypes.BHI).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// BLS -------------------------------------------------------------------------

    create(M68kTokenTypes.BLS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// BCC -------------------------------------------------------------------------

    create(M68kTokenTypes.BCC).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BCS -------------------------------------------------------------------------

    create(M68kTokenTypes.BCS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BNE -------------------------------------------------------------------------

    create(M68kTokenTypes.BNE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// BEQ -------------------------------------------------------------------------

    create(M68kTokenTypes.BEQ).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// BVC -------------------------------------------------------------------------

    create(M68kTokenTypes.BVC).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// BVS -------------------------------------------------------------------------

    create(M68kTokenTypes.BVS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// BPL -------------------------------------------------------------------------

    create(M68kTokenTypes.BPL).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// BMI -------------------------------------------------------------------------

    create(M68kTokenTypes.BMI).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// BGE -------------------------------------------------------------------------

    create(M68kTokenTypes.BGE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// BLT -------------------------------------------------------------------------

    create(M68kTokenTypes.BLT).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// BGT -------------------------------------------------------------------------

    create(M68kTokenTypes.BGT).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// BLE -------------------------------------------------------------------------

    create(M68kTokenTypes.BLE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// BRA -------------------------------------------------------------------------

    create(M68kTokenTypes.BRA).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .build();

// BSR -------------------------------------------------------------------------

    create(M68kTokenTypes.BSR).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .build();

// BCHG ------------------------------------------------------------------------

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BCHG).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

// BCLR ------------------------------------------------------------------------

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BCLR).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

// BSET ------------------------------------------------------------------------

    create(M68kTokenTypes.BSET).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BSET).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BSET).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BSET).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

// BTST ------------------------------------------------------------------------

    create(M68kTokenTypes.BTST).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BTST).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(MEMORY)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BTST).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(M68kTokenTypes.BTST).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(MEMORY_WITHOUT_IMMEDIATE)
      .affected("--*--")
      .build();

// BGND ------------------------------------------------------------------------

    create(M68kTokenTypes.BGND)
      .cpus(GROUP_CPU32)
      .build();

// BKPT ------------------------------------------------------------------------

    create(M68kTokenTypes.BKPT)
      .first(QUICK_IMMEDIATE)
      .cpus(GROUP_68010_UP)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// CHK -------------------------------------------------------------------------

    create(M68kTokenTypes.CHK).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-*UUU")
      .build();

    create(M68kTokenTypes.CHK).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-*UUU")
      .build();

// CLR -------------------------------------------------------------------------

    create(M68kTokenTypes.CLR).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("-0100")
      .build();

// CMP -------------------------------------------------------------------------

    create(M68kTokenTypes.CMP).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .affected("-****")
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("-****")
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("-****")
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-****")
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_WITHOUT_IMMEDIATE)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-****")
      .build();

    create(M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT).second(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT)
      .affected("-****")
      .build();

// CMPA ------------------------------------------------------------------------

    create(M68kTokenTypes.CMPA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("-****")
      .build();

// CMPI ------------------------------------------------------------------------

    create(M68kTokenTypes.CMPI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-****")
      .build();

    create(M68kTokenTypes.CMPI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_WITHOUT_IMMEDIATE)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-****")
      .build();

// CMPM ------------------------------------------------------------------------

    create(M68kTokenTypes.CMPM).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT).second(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT)
      .affected("-****")
      .build();

// DBT -------------------------------------------------------------------------

    create(M68kTokenTypes.DBT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-----")
      .build();

// DBF -------------------------------------------------------------------------

    create(M68kTokenTypes.DBF).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-----")
      .build();

// DBRA ------------------------------------------------------------------------

    create(M68kTokenTypes.DBRA).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-----")
      .build();

// DBHI ------------------------------------------------------------------------

    create(M68kTokenTypes.DBHI).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// DBLS ------------------------------------------------------------------------

    create(M68kTokenTypes.DBLS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// DBCC ------------------------------------------------------------------------

    create(M68kTokenTypes.DBCC).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBHS ------------------------------------------------------------------------

    create(M68kTokenTypes.DBHS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBCS ------------------------------------------------------------------------

    create(M68kTokenTypes.DBCS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBLO ------------------------------------------------------------------------

    create(M68kTokenTypes.DBLO).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBNE ------------------------------------------------------------------------

    create(M68kTokenTypes.DBNE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// DBEQ ------------------------------------------------------------------------

    create(M68kTokenTypes.DBEQ).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// DBVC ------------------------------------------------------------------------

    create(M68kTokenTypes.DBVC).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// DBVS ------------------------------------------------------------------------

    create(M68kTokenTypes.DBVS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// DBPL ------------------------------------------------------------------------

    create(M68kTokenTypes.DBPL).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// DBMI ------------------------------------------------------------------------

    create(M68kTokenTypes.DBMI).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// DBGE ------------------------------------------------------------------------

    create(M68kTokenTypes.DBGE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// DBLT ------------------------------------------------------------------------

    create(M68kTokenTypes.DBLT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// DBGT ------------------------------------------------------------------------

    create(M68kTokenTypes.DBGT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// DBLE ------------------------------------------------------------------------

    create(M68kTokenTypes.DBLE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// DIVS ------------------------------------------------------------------------

    create(M68kTokenTypes.DIVS).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.DIVS).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.DIVS).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

// DIVU ------------------------------------------------------------------------

    create(M68kTokenTypes.DIVU).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.DIVU).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.DIVU).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

// EOR -------------------------------------------------------------------------

    create(M68kTokenTypes.EOR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.EOR).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.EOR).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(M68kTokenTypes.EOR).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .tested("?????")
      .build();

// EORI ------------------------------------------------------------------------

    create(M68kTokenTypes.EORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.EORI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(M68kTokenTypes.EORI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .tested("?????")
      .build();

// EXG -------------------------------------------------------------------------

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

// EXT -------------------------------------------------------------------------

    create(M68kTokenTypes.EXT).dataSizes(GROUP_WL)
      .first(DATA_REGISTER)
      .affected("-**00")
      .build();

// ILLEGAL ---------------------------------------------------------------------

    create(M68kTokenTypes.ILLEGAL)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// JMP -------------------------------------------------------------------------

    create(M68kTokenTypes.JMP)
      .first(CONTROL)
      .controlFlow(M68kMnemonic.ControlFlow.JUMP)
      .build();

// JSR -------------------------------------------------------------------------

    create(M68kTokenTypes.JSR)
      .first(CONTROL)
      .controlFlow(M68kMnemonic.ControlFlow.JUMP)
      .build();

// LEA -------------------------------------------------------------------------

    create(M68kTokenTypes.LEA).dataSizes(GROUP_L)
      .first(CONTROL).second(ADDRESS_REGISTER)
      .build();

// LINK ------------------------------------------------------------------------

    create(M68kTokenTypes.LINK).dataSizes(GROUP_W)
      .first(ADDRESS_REGISTER).second(IMMEDIATE)
      .build();

    create(M68kTokenTypes.LINK).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(IMMEDIATE)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .build();

// LPSTOP ----------------------------------------------------------------------

    create(M68kTokenTypes.LPSTOP).dataSizes(GROUP_W)
      .first(IMMEDIATE)
      .cpus(EnumSet.of(M68kCpu.CPU32))
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .build();

// LSL -------------------------------------------------------------------------

    create(M68kTokenTypes.LSL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .build();

// LSR -------------------------------------------------------------------------

    create(M68kTokenTypes.LSR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .build();

// MOVE ------------------------------------------------------------------------

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(ALTERABLE)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_BWL)
      .first(DATA).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(CCR_REGISTER).second(ALTERABLE_DATA)
      .cpus(GROUP_68010_UP)
      .tested("?????")
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(SR_REGISTER).second(ALTERABLE_DATA)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED_68010_ABOVE)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .tested("?????")
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(DATA).second(CCR_REGISTER)
      .affected("*****")
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(DATA).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_L)
      .first(USP_REGISTER).second(ADDRESS_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

    create(M68kTokenTypes.MOVE).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(USP_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// MOVEA -----------------------------------------------------------------------

    create(M68kTokenTypes.MOVEA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

    create(M68kTokenTypes.MOVEA).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(ALTERABLE)
      .deprecated()
      .build();

    create(M68kTokenTypes.MOVEA).dataSizes(GROUP_BWL)
      .first(DATA).second(ALTERABLE_DATA)
      .deprecated()
      .build();

// MOVEC -----------------------------------------------------------------------

    create(M68kTokenTypes.MOVEC).dataSizes(GROUP_L)
      .first(CTRL_REGISTER).second(DATA_OR_ADDRESS_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

    create(M68kTokenTypes.MOVEC).dataSizes(GROUP_L)
      .first(DATA_OR_ADDRESS_REGISTER).second(CTRL_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// MOVEM -----------------------------------------------------------------------

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

// MOVEP -----------------------------------------------------------------------

    create(M68kTokenTypes.MOVEP).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER_DISPLACEMENT).second(DATA_REGISTER)
      .build();

    create(M68kTokenTypes.MOVEP).dataSizes(GROUP_WL)
      .first(DATA_REGISTER).second(ADDRESS_REGISTER_DISPLACEMENT)
      .build();

// MOVEQ -----------------------------------------------------------------------

    create(M68kTokenTypes.MOVEQ).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("-**00")
      .build();

// MOVES -----------------------------------------------------------------------

    create(M68kTokenTypes.MOVES).dataSizes(GROUP_BWL)
      .first(ALTERABLE_MEMORY).second(DATA_OR_ADDRESS_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

    create(M68kTokenTypes.MOVES).dataSizes(GROUP_BWL)
      .first(DATA_OR_ADDRESS_REGISTER).second(ALTERABLE_MEMORY)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// MULS ------------------------------------------------------------------------

    create(M68kTokenTypes.MULS).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.MULS).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.MULS).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

// MULU ------------------------------------------------------------------------

    create(M68kTokenTypes.MULU).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.MULU).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.MULU).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

// NBCD ------------------------------------------------------------------------

    create(M68kTokenTypes.NBCD).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

// NEG -------------------------------------------------------------------------

    create(M68kTokenTypes.NEG).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("C****")
      .build();

// NEGX ------------------------------------------------------------------------

    create(M68kTokenTypes.NEGX).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("C****")
      .tested("?----")
      .build();

// NOP -------------------------------------------------------------------------

    create(M68kTokenTypes.NOP)
      .build();

// NOT -------------------------------------------------------------------------

    create(M68kTokenTypes.NOT).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("-**00")
      .build();

// OR --------------------------------------------------------------------------

    create(M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(M68kTokenTypes.OR).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("OOOOO")
      .tested("?????")
      .build();

// ORI -------------------------------------------------------------------------

    create(M68kTokenTypes.ORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.ORI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(M68kTokenTypes.ORI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("OOOOO")
      .tested("?????")
      .build();

// PEA -------------------------------------------------------------------------

    create(M68kTokenTypes.PEA).dataSizes(GROUP_L)
      .first(CONTROL)
      .build();

// RESET -----------------------------------------------------------------------

    create(M68kTokenTypes.RESET)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// ROL -------------------------------------------------------------------------

    create(M68kTokenTypes.ROL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("-**0*")
      .build();

    create(M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("-**0*")
      .build();

// ROR -------------------------------------------------------------------------

    create(M68kTokenTypes.ROR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("-**0*")
      .build();

    create(M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("-**0*")
      .build();

// ROXL ------------------------------------------------------------------------

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .tested("?----")
      .build();

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

// ROXR ------------------------------------------------------------------------

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .tested("?----")
      .build();

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

// RTD -------------------------------------------------------------------------

    create(M68kTokenTypes.RTD)
      .first(QUICK_IMMEDIATE)
      .cpus(GROUP_68010_UP)
      .controlFlow(M68kMnemonic.ControlFlow.RETURN)
      .build();

// RTE -------------------------------------------------------------------------

    create(M68kTokenTypes.RTE)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP_RETURN)
      .affected("*****")
      .build();

// RTR -------------------------------------------------------------------------

    create(M68kTokenTypes.RTR)
      .controlFlow(M68kMnemonic.ControlFlow.RETURN)
      .affected("*****")
      .build();

// RTS -------------------------------------------------------------------------

    create(M68kTokenTypes.RTS)
      .controlFlow(M68kMnemonic.ControlFlow.RETURN)
      .build();

// SBCD ------------------------------------------------------------------------

    create(M68kTokenTypes.SBCD).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

    create(M68kTokenTypes.SBCD).dataSizes(GROUP_B)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

// ST --------------------------------------------------------------------------

    create(M68kTokenTypes.ST).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-----")
      .build();

// SF --------------------------------------------------------------------------

    create(M68kTokenTypes.SF).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-----")
      .build();

// SHI -------------------------------------------------------------------------

    create(M68kTokenTypes.SHI).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?-?")
      .build();

// SLS -------------------------------------------------------------------------

    create(M68kTokenTypes.SLS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?-?")
      .build();

// SCC -------------------------------------------------------------------------

    create(M68kTokenTypes.SCC).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SHS -------------------------------------------------------------------------

    create(M68kTokenTypes.SHS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SCS -------------------------------------------------------------------------

    create(M68kTokenTypes.SCS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SLO -------------------------------------------------------------------------

    create(M68kTokenTypes.SLO).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SNE -------------------------------------------------------------------------

    create(M68kTokenTypes.SNE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?--")
      .build();

// SEQ -------------------------------------------------------------------------

    create(M68kTokenTypes.SEQ).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?--")
      .build();

// SVC -------------------------------------------------------------------------

    create(M68kTokenTypes.SVC).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("---?-")
      .build();

// SVS -------------------------------------------------------------------------

    create(M68kTokenTypes.SVS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("---?-")
      .build();

// SPL -------------------------------------------------------------------------

    create(M68kTokenTypes.SPL).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?---")
      .build();

// SMI -------------------------------------------------------------------------

    create(M68kTokenTypes.SMI).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?---")
      .build();

// SGE -------------------------------------------------------------------------

    create(M68kTokenTypes.SGE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?-?-")
      .build();

// SLT -------------------------------------------------------------------------

    create(M68kTokenTypes.SLT).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?-?-")
      .build();

// SGT -------------------------------------------------------------------------

    create(M68kTokenTypes.SGT).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-???-")
      .build();

// SLE -------------------------------------------------------------------------

    create(M68kTokenTypes.SLE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-???-")
      .build();

// STOP ------------------------------------------------------------------------

    create(M68kTokenTypes.STOP)
      .first(QUICK_IMMEDIATE)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .build();

// SUB -------------------------------------------------------------------------

    create(M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("C****")
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// SUBA ------------------------------------------------------------------------

    create(M68kTokenTypes.SUBA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

// SUBI ------------------------------------------------------------------------

    create(M68kTokenTypes.SUBI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// SUBQ ------------------------------------------------------------------------

    create(M68kTokenTypes.SUBQ).dataSizes(GROUP_WL)
      .first(QUICK_IMMEDIATE).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(M68kTokenTypes.SUBQ).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// SUBX ------------------------------------------------------------------------

    create(M68kTokenTypes.SUBX).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("C*A**")
      .tested("?-?--")
      .build();

    create(M68kTokenTypes.SUBX).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("C*A**")
      .tested("?-?--")
      .build();

// SWAP ------------------------------------------------------------------------

    create(M68kTokenTypes.SWAP).dataSizes(GROUP_W)
      .first(DATA_REGISTER)
      .affected("-**00")
      .build();

// TAS -------------------------------------------------------------------------

    create(M68kTokenTypes.TAS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .affected("-**00")
      .build();

// TBLS ------------------------------------------------------------------------

    create(M68kTokenTypes.TBLS).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.TBLS).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TBLSN -----------------------------------------------------------------------

    create(M68kTokenTypes.TBLSN).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.TBLSN).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TBLU ------------------------------------------------------------------------

    create(M68kTokenTypes.TBLU).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.TBLU).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TBLUN -----------------------------------------------------------------------

    create(M68kTokenTypes.TBLUN).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(M68kTokenTypes.TBLUN).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TRAP ------------------------------------------------------------------------

    create(M68kTokenTypes.TRAP)
      .first(QUICK_IMMEDIATE)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// TRAPV -----------------------------------------------------------------------

    create(M68kTokenTypes.TRAPV)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .tested("---?-")
      .build();

// TST -------------------------------------------------------------------------

    create(M68kTokenTypes.TST).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.TST).dataSizes(GROUP_BWL)
      .first(DATA)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

    create(M68kTokenTypes.TST).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

// UNLK ------------------------------------------------------------------------

    create(M68kTokenTypes.UNLK)
      .first(ADDRESS_REGISTER)
      .build();
  }
}
