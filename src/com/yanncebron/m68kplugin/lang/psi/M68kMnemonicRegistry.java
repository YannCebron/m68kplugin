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
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

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

  private final Map<IElementType, List<M68kMnemonic>> registry;

  private static final M68kMnemonicRegistry INSTANCE = new M68kMnemonicRegistry();


  public static M68kMnemonicRegistry getInstance() {
    return INSTANCE;
  }

  /**
   * Returns all registered mnemonics for the given element type.
   *
   * @return empty list if none registered or elementType is not instruction.
   */
  public @Unmodifiable Collection<M68kMnemonic> findAll(@NotNull IElementType elementType) {
    List<M68kMnemonic> list = registry.get(elementType);
    if (list == null) {
      return Collections.emptyList();
    }
    return list;
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

    return filtered.getFirst();
  }

  @NotNull
  private static List<M68kMnemonic> getFilteredM68Mnemonics(Collection<M68kMnemonic> all, M68kInstruction instruction) {
    final M68kDataSize dataSize = instruction instanceof M68kDataSized dataSized ? dataSized.getDataSize() : null;

    List<M68kAdm> admList = PsiTreeUtil.getChildrenOfTypeAsList(instruction, M68kAdm.class);
    int operandsCount = admList.size();

    List<M68kMnemonic> result = new ArrayList<>(all.size());
    for (M68kMnemonic mnemonic : all) {
      // data size (optional)
      if (dataSize != null && !mnemonic.dataSizes().contains(dataSize)) {
        continue;
      }

      // operand count / addressing modes
      switch (operandsCount) {
        case 0 -> {
          if (mnemonic.hasFirstOperand()) {
            continue;
          }
        }
        case 1 -> {
          if (mnemonic.hasSecondOperand() || !mnemonic.firstOperand().matches(admList.getFirst())) {
            continue;
          }
        }
        case 2 -> {
          if (!mnemonic.hasSecondOperand() ||
            !mnemonic.firstOperand().matches(admList.get(0)) ||
            !mnemonic.secondOperand().matches(admList.get(1))) {
            continue;
          }
        }
        default -> {
          continue;
        }
      }
      result.add(mnemonic);
    }
    return result;
  }

  private MnemonicBuilder create(MultiMap<IElementType, M68kMnemonic> mnemonics, IElementType elementType) {
    return new MnemonicBuilder(mnemonics, elementType);
  }

  private static class MnemonicBuilder {
    private final MultiMap<IElementType, M68kMnemonic> mnemonics;
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

    private MnemonicBuilder(MultiMap<IElementType, M68kMnemonic> mnemonics, IElementType elementType) {
      this.mnemonics = mnemonics;
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
    MultiMap<IElementType, M68kMnemonic> mnemonics = MultiMap.createLinked();

// Total mnemonics: 244

// ABCD ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ABCD).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

    create(mnemonics, M68kTokenTypes.ABCD).dataSizes(GROUP_B)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

// ADD -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.ADD).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.ADD).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.ADD).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// ADDA ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ADDA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

// ADDI ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ADDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// ADDQ ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ADDQ).dataSizes(GROUP_WL)
      .first(QUICK_IMMEDIATE).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.ADDQ).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// ADDX ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ADDX).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("C*A**")
      .tested("?-?--")
      .build();

    create(mnemonics, M68kTokenTypes.ADDX).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("C*A**")
      .tested("?-?--")
      .build();

// AND -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.AND).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.AND).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("AAAAA")
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.AND).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("AAAAA")
      .tested("?????")
      .build();

// ANDI ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ANDI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.ANDI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("AAAAA")
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.ANDI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("AAAAA")
      .tested("?????")
      .build();

// ASL -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ASL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("*****")
      .build();

    create(mnemonics, M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("*****")
      .build();

    create(mnemonics, M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("*****")
      .build();

    create(mnemonics, M68kTokenTypes.ASL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("*****")
      .build();

// ASR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ASR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.ASR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .build();

// BHS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BHS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BLO -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BLO).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BHI -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BHI).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// BLS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BLS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// BCC -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BCC).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BCS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BCS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// BNE -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BNE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// BEQ -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BEQ).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// BVC -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BVC).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// BVS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BVS).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// BPL -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BPL).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// BMI -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BMI).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// BGE -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BGE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// BLT -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BLT).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// BGT -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BGT).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// BLE -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BLE).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// BRA -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BRA).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .build();

// BSR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BSR).dataSizes(GROUP_SBWL)
      .first(BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .build();

// BCHG ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BCHG).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BCHG).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BCHG).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BCHG).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

// BCLR ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BCLR).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BCLR).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BCLR).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BCLR).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

// BSET ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BSET).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BSET).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BSET).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BSET).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_MEMORY)
      .affected("--*--")
      .build();

// BTST ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BTST).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BTST).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(MEMORY)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BTST).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("--*--")
      .build();

    create(mnemonics, M68kTokenTypes.BTST).dataSizes(GROUP_B)
      .first(QUICK_IMMEDIATE).second(MEMORY_WITHOUT_IMMEDIATE)
      .affected("--*--")
      .build();

// BGND ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BGND)
      .cpus(GROUP_CPU32)
      .build();

// BKPT ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.BKPT)
      .first(QUICK_IMMEDIATE)
      .cpus(GROUP_68010_UP)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// CHK -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.CHK).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-*UUU")
      .build();

    create(mnemonics, M68kTokenTypes.CHK).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-*UUU")
      .build();

// CLR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.CLR).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("-0100")
      .build();

// CMP -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.CMP).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .affected("-****")
      .build();

    create(mnemonics, M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("-****")
      .build();

    create(mnemonics, M68kTokenTypes.CMP).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("-****")
      .build();

    create(mnemonics, M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-****")
      .build();

    create(mnemonics, M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_WITHOUT_IMMEDIATE)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-****")
      .build();

    create(mnemonics, M68kTokenTypes.CMP).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT).second(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT)
      .affected("-****")
      .build();

// CMPA ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.CMPA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("-****")
      .build();

// CMPI ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.CMPI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-****")
      .build();

    create(mnemonics, M68kTokenTypes.CMPI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(DATA_WITHOUT_IMMEDIATE)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-****")
      .build();

// CMPM ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.CMPM).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT).second(ADDRESS_REGISTER_INDIRECT_POST_INCREMENT)
      .affected("-****")
      .build();

// DBT -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-----")
      .build();

// DBF -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBF).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-----")
      .build();

// DBRA ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBRA).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-----")
      .build();

// DBHI ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBHI).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// DBLS ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBLS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?-?")
      .build();

// DBCC ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBCC).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBHS ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBHS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBCS ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBCS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBLO ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBLO).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("----?")
      .build();

// DBNE ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBNE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// DBEQ ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBEQ).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("--?--")
      .build();

// DBVC ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBVC).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// DBVS ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBVS).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("---?-")
      .build();

// DBPL ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBPL).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// DBMI ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBMI).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?---")
      .build();

// DBGE ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBGE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// DBLT ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBLT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-?-?-")
      .build();

// DBGT ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBGT).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// DBLE ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DBLE).dataSizes(GROUP_W)
      .first(DATA_REGISTER).second(DBCC_BRANCH_DESTINATION)
      .controlFlow(M68kMnemonic.ControlFlow.BRANCH)
      .tested("-???-")
      .build();

// DIVS ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DIVS).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.DIVS).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.DIVS).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

// DIVU ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.DIVU).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.DIVU).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.DIVU).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("-***0")
      .build();

// EOR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.EOR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.EOR).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.EOR).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.EOR).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .tested("?????")
      .build();

// EORI ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.EORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.EORI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.EORI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .tested("?????")
      .build();

// EXG -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .build();

    create(mnemonics, M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(ADDRESS_REGISTER)
      .build();

    create(mnemonics, M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(DATA_REGISTER).second(ADDRESS_REGISTER)
      .build();

    create(mnemonics, M68kTokenTypes.EXG).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .build();

// EXT -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.EXT).dataSizes(GROUP_WL)
      .first(DATA_REGISTER)
      .affected("-**00")
      .build();

// ILLEGAL ---------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ILLEGAL)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// JMP -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.JMP)
      .first(CONTROL)
      .controlFlow(M68kMnemonic.ControlFlow.JUMP)
      .build();

// JSR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.JSR)
      .first(CONTROL)
      .controlFlow(M68kMnemonic.ControlFlow.JUMP)
      .build();

// LEA -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.LEA).dataSizes(GROUP_L)
      .first(CONTROL).second(ADDRESS_REGISTER)
      .build();

// LINK ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.LINK).dataSizes(GROUP_W)
      .first(ADDRESS_REGISTER).second(IMMEDIATE)
      .build();

    create(mnemonics, M68kTokenTypes.LINK).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(IMMEDIATE)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .build();

// LPSTOP ----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.LPSTOP).dataSizes(GROUP_W)
      .first(IMMEDIATE)
      .cpus(EnumSet.of(M68kCpu.CPU32))
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("IIIII")
      .build();

// LSL -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.LSL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.LSL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .build();

// LSR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.LSR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .build();

    create(mnemonics, M68kTokenTypes.LSR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .build();

// MOVE ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(ALTERABLE)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_BWL)
      .first(DATA).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(CCR_REGISTER).second(ALTERABLE_DATA)
      .cpus(GROUP_68010_UP)
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(SR_REGISTER).second(ALTERABLE_DATA)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED_68010_ABOVE)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(DATA).second(CCR_REGISTER)
      .affected("*****")
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_W)
      .first(DATA).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("*****")
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_L)
      .first(USP_REGISTER).second(ADDRESS_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

    create(mnemonics, M68kTokenTypes.MOVE).dataSizes(GROUP_L)
      .first(ADDRESS_REGISTER).second(USP_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// MOVEA -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MOVEA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEA).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(ALTERABLE)
      .deprecated()
      .build();

    create(mnemonics, M68kTokenTypes.MOVEA).dataSizes(GROUP_BWL)
      .first(DATA).second(ALTERABLE_DATA)
      .deprecated()
      .build();

// MOVEC -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MOVEC).dataSizes(GROUP_L)
      .first(CTRL_REGISTER).second(DATA_OR_ADDRESS_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEC).dataSizes(GROUP_L)
      .first(DATA_OR_ADDRESS_REGISTER).second(CTRL_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// MOVEM -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(DATA_OR_ADDRESS_REGISTER_LIST).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(DATA_OR_ADDRESS_REGISTER_LIST).second(ALTERABLE_CONTROL)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(RESTORE_OPERANDS).second(DATA_OR_ADDRESS_REGISTER_LIST)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(IMMEDIATE_REGISTER_LIST_VALUE).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(IMMEDIATE_REGISTER_LIST_VALUE).second(ALTERABLE_CONTROL)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEM).dataSizes(GROUP_WL)
      .first(RESTORE_OPERANDS).second(IMMEDIATE_REGISTER_LIST_VALUE)
      .build();

// MOVEP -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MOVEP).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER_DISPLACEMENT).second(DATA_REGISTER)
      .build();

    create(mnemonics, M68kTokenTypes.MOVEP).dataSizes(GROUP_WL)
      .first(DATA_REGISTER).second(ADDRESS_REGISTER_DISPLACEMENT)
      .build();

// MOVEQ -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MOVEQ).dataSizes(GROUP_L)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("-**00")
      .build();

// MOVES -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MOVES).dataSizes(GROUP_BWL)
      .first(ALTERABLE_MEMORY).second(DATA_OR_ADDRESS_REGISTER)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

    create(mnemonics, M68kTokenTypes.MOVES).dataSizes(GROUP_BWL)
      .first(DATA_OR_ADDRESS_REGISTER).second(ALTERABLE_MEMORY)
      .cpus(GROUP_68010_UP)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// MULS ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MULS).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.MULS).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.MULS).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

// MULU ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.MULU).dataSizes(GROUP_W)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.MULU).dataSizes(GROUP_L)
      .first(DATA).second(DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.MULU).dataSizes(GROUP_L)
      .first(DATA).second(DOUBLE_DATA_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

// NBCD ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.NBCD).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

// NEG -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.NEG).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("C****")
      .build();

// NEGX ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.NEGX).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("C*A**")
      .tested("?-?--")
      .build();

// NOP -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.NOP)
      .build();

// NOT -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.NOT).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("-**00")
      .build();

// OR --------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.OR).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.OR).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.OR).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("OOOOO")
      .tested("?????")
      .build();

// ORI -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ORI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.ORI).dataSizes(GROUP_B)
      .first(IMMEDIATE).second(CCR_REGISTER)
      .affected("*****")
      .tested("?????")
      .build();

    create(mnemonics, M68kTokenTypes.ORI).dataSizes(GROUP_W)
      .first(IMMEDIATE).second(SR_REGISTER)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("OOOOO")
      .tested("?????")
      .build();

// PEA -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.PEA).dataSizes(GROUP_L)
      .first(CONTROL)
      .build();

// RESET -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.RESET)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// ROL -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ROL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("-**0*")
      .build();

    create(mnemonics, M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(mnemonics, M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(mnemonics, M68kTokenTypes.ROL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("-**0*")
      .build();

// ROR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ROR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("-**0*")
      .build();

    create(mnemonics, M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(mnemonics, M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("-**0*")
      .build();

    create(mnemonics, M68kTokenTypes.ROR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("-**0*")
      .build();

// ROXL ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ROXL).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .tested("?----")
      .build();

    create(mnemonics, M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(mnemonics, M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(mnemonics, M68kTokenTypes.ROXL).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

// ROXR ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ROXR).dataSizes(GROUP_W)
      .first(ALTERABLE_MEMORY)
      .affected("***0*")
      .tested("?----")
      .build();

    create(mnemonics, M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(mnemonics, M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

    create(mnemonics, M68kTokenTypes.ROXR).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER)
      .affected("***0*")
      .tested("?----")
      .build();

// RTD -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.RTD)
      .first(QUICK_IMMEDIATE)
      .cpus(GROUP_68010_UP)
      .controlFlow(M68kMnemonic.ControlFlow.RETURN)
      .build();

// RTE -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.RTE)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP_RETURN)
      .affected("EEEEE")
      .build();

// RTR -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.RTR)
      .controlFlow(M68kMnemonic.ControlFlow.RETURN)
      .affected("EEEEE")
      .build();

// RTS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.RTS)
      .controlFlow(M68kMnemonic.ControlFlow.RETURN)
      .build();

// SBCD ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SBCD).dataSizes(GROUP_B)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

    create(mnemonics, M68kTokenTypes.SBCD).dataSizes(GROUP_B)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("CUAU*")
      .tested("?-?--")
      .build();

// ST --------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.ST).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-----")
      .build();

// SF --------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SF).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-----")
      .build();

// SHI -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SHI).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?-?")
      .build();

// SLS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SLS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?-?")
      .build();

// SCC -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SCC).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SHS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SHS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SCS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SCS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SLO -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SLO).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("----?")
      .build();

// SNE -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SNE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?--")
      .build();

// SEQ -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SEQ).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("--?--")
      .build();

// SVC -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SVC).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("---?-")
      .build();

// SVS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SVS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("---?-")
      .build();

// SPL -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SPL).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?---")
      .build();

// SMI -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SMI).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?---")
      .build();

// SGE -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SGE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?-?-")
      .build();

// SLT -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SLT).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-?-?-")
      .build();

// SGT -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SGT).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-???-")
      .build();

// SLE -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SLE).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .tested("-???-")
      .build();

// STOP ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.STOP)
      .first(QUICK_IMMEDIATE)
      .privileged(M68kMnemonic.PrivilegedType.PRIVILEGED)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .affected("IIIII")
      .build();

// SUB -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(DATA).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.SUB).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER).second(DATA_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(ALTERABLE_MEMORY)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.SUB).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.SUB).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// SUBA ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SUBA).dataSizes(GROUP_WL)
      .first(ALL).second(ADDRESS_REGISTER)
      .build();

// SUBI ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SUBI).dataSizes(GROUP_BWL)
      .first(IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// SUBQ ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SUBQ).dataSizes(GROUP_WL)
      .first(QUICK_IMMEDIATE).second(ADDRESS_REGISTER)
      .affected("C****")
      .build();

    create(mnemonics, M68kTokenTypes.SUBQ).dataSizes(GROUP_BWL)
      .first(QUICK_IMMEDIATE).second(ALTERABLE_DATA)
      .affected("C****")
      .build();

// SUBX ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SUBX).dataSizes(GROUP_BWL)
      .first(DATA_REGISTER).second(DATA_REGISTER)
      .affected("C*A**")
      .tested("?-?--")
      .build();

    create(mnemonics, M68kTokenTypes.SUBX).dataSizes(GROUP_BWL)
      .first(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT).second(ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
      .affected("C*A**")
      .tested("?-?--")
      .build();

// SWAP ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.SWAP).dataSizes(GROUP_W)
      .first(DATA_REGISTER)
      .affected("-**00")
      .build();

// TAS -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TAS).dataSizes(GROUP_B)
      .first(ALTERABLE_DATA)
      .affected("-**00")
      .build();

// TBLS ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TBLS).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.TBLS).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TBLSN -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TBLSN).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.TBLSN).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TBLU ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TBLU).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.TBLU).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TBLUN -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TBLUN).dataSizes(GROUP_BWL)
      .first(CONTROL).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

    create(mnemonics, M68kTokenTypes.TBLUN).dataSizes(GROUP_BWL)
      .first(DOUBLE_DATA_REGISTER).second(DATA_REGISTER)
      .cpus(GROUP_CPU32)
      .affected("-***0")
      .build();

// TRAP ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TRAP)
      .first(QUICK_IMMEDIATE)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .build();

// TRAPV -----------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TRAPV)
      .controlFlow(M68kMnemonic.ControlFlow.TRAP)
      .tested("---?-")
      .build();

// TST -------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.TST).dataSizes(GROUP_BWL)
      .first(ALTERABLE_DATA)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.TST).dataSizes(GROUP_BWL)
      .first(DATA)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

    create(mnemonics, M68kTokenTypes.TST).dataSizes(GROUP_WL)
      .first(ADDRESS_REGISTER)
      .cpus(GROUP_68020_UP_WITH_CPU32)
      .affected("-**00")
      .build();

// UNLK ------------------------------------------------------------------------

    create(mnemonics, M68kTokenTypes.UNLK)
      .first(ADDRESS_REGISTER)
      .build();

    registry = freezeMnemonics(mnemonics);
  }

  /**
   * Converts the mutable construction-time MultiMap into an immutable registry map.
   * Each mnemonic list is sorted by specificity (non-deprecated first, then fewest address modes)
   * and wrapped in an unmodifiable copy, so {@link #findAll(IElementType)} returns results with zero allocation.
   */
  private Map<IElementType, List<M68kMnemonic>> freezeMnemonics(MultiMap<IElementType, M68kMnemonic> mnemonics) {
    Map<IElementType, List<M68kMnemonic>> result = new LinkedHashMap<>(mnemonics.keySet().size());
    for (Map.Entry<IElementType, Collection<M68kMnemonic>> entry : mnemonics.entrySet()) {
      ArrayList<M68kMnemonic> list = new ArrayList<>(entry.getValue());
      Collections.sort(list);
      result.put(entry.getKey(), List.copyOf(list));
    }
    return result;
  }
}
