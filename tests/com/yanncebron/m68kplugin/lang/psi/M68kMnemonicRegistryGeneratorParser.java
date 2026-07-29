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

import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.text.StringUtil;
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

final class M68kMnemonicRegistryGeneratorParser {

  // cpus/m68k/operands.h
  private static final Map<String, M68kOperand> OPERAND_MAP = Map.ofEntries(
    entry("0", M68kOperand.NONE),
    entry("IM", M68kOperand.IMMEDIATE),
    entry("QI", M68kOperand.QUICK_IMMEDIATE),
    entry("MI", M68kOperand.MEMORY_WITHOUT_IMMEDIATE),
    entry("BR", M68kOperand.BRANCH_DESTINATION),
    entry("DB", M68kOperand.DBCC_BRANCH_DESTINATION),
    entry("MR", M68kOperand.RESTORE_OPERANDS),
    entry("IR", M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE),
    entry("DA", M68kOperand.DATA),
    entry("AD", M68kOperand.ALTERABLE_DATA),
    entry("DN", M68kOperand.DATA_WITHOUT_IMMEDIATE),
    entry("CFAD", M68kOperand.ALTERABLE_DATA_CF),
    entry("MA", M68kOperand.MEMORY),
    entry("AM", M68kOperand.ALTERABLE_MEMORY),
    entry("CFAM", M68kOperand.ALTERABLE_MEMORY_CF),
    entry("CT", M68kOperand.CONTROL),
    entry("AC", M68kOperand.ALTERABLE_CONTROL),
    entry("AL", M68kOperand.ALTERABLE),
    entry("AY", M68kOperand.ALL),
    entry("D_", M68kOperand.DATA_REGISTER),
    entry("DD", M68kOperand.DOUBLE_DATA_REGISTER),
    entry("A_", M68kOperand.ADDRESS_REGISTER),
    entry("R_", M68kOperand.DATA_OR_ADDRESS_REGISTER),
    entry("RL", M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST),
    entry("PA", M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT),
    entry("AP", M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT),
    entry("DP", M68kOperand.ADDRESS_REGISTER_DISPLACEMENT),
    entry("_SR", M68kOperand.SR_REGISTER),
    entry("_USP", M68kOperand.USP_REGISTER),
    entry("_CCR", M68kOperand.CCR_REGISTER),
    entry("_CTRL", M68kOperand.CTRL_REGISTER)
    // todo ADDRESS_REGISTER_INDEX_DISPLACEMENT = M6??
  );

  private static final Map<String, Set<M68kDataSize>> DATA_SIZE_MAP = Map.ofEntries(
    entry("B", M68kDataSize.GROUP_B),
    entry("W", M68kDataSize.GROUP_W),
    entry("L", M68kDataSize.GROUP_L),
    entry("WL", M68kDataSize.GROUP_WL),
    entry("CFWL", M68kDataSize.GROUP_WL),
    entry("BWL", M68kDataSize.GROUP_BWL),
    entry("CFBWL", M68kDataSize.GROUP_BWL),
    entry("SBW", M68kDataSize.GROUP_SBW),
    entry("SBWL", M68kDataSize.GROUP_SBWL),
    entry("UNS", M68kDataSize.GROUP_UNSIZED)
  );

  private static final Map<String, Set<M68kCpu>> CPU_MAP = Map.ofEntries(
    entry("m68000up", M68kCpu.GROUP_68000_UP),
    entry("m68010up", M68kCpu.GROUP_68010_UP),
    entry("m68020up", M68kCpu.GROUP_68020_UP),
    entry("m68030up", M68kCpu.GROUP_68030_UP),
    entry("m68040up", M68kCpu.GROUP_68040_UP),

    entry("mfloat", M68kCpu.GROUP_FLOAT),
    entry("apollo", M68kCpu.GROUP_APOLLO),

    entry("m68851", EnumSet.of(M68kCpu.M_68851)),
    entry("m68020", EnumSet.of(M68kCpu.M_68020)),
    entry("m68030", EnumSet.of(M68kCpu.M_68030)),
    entry("m68040", EnumSet.of(M68kCpu.M_68040)),
    entry("m68060", EnumSet.of(M68kCpu.M_68060)),

    entry("cpu32", EnumSet.of(M68kCpu.CPU32))
  );

  static Couple<M68kOperand> mapOperands(String operandText) {
    operandText = operandText.contains("{") ? StringUtil.substringAfter(operandText, "{") : operandText;
    TestCase.assertNotNull(operandText);
    operandText = StringUtil.substringBefore(operandText, "}");
    TestCase.assertNotNull(operandText);
    if (operandText.contains(",")) {
      List<String> operandTexts = StringUtil.split(operandText, ",");
      return Couple.of(OPERAND_MAP.get(operandTexts.get(0)), OPERAND_MAP.get(operandTexts.get(1)));
    }
    return Couple.of(OPERAND_MAP.get(operandText), M68kOperand.NONE);
  }

  @NotNull
  static Set<M68kDataSize> mapDataSizes(String dataSizeText) {
    return DATA_SIZE_MAP.getOrDefault(dataSizeText, EnumSet.noneOf(M68kDataSize.class));
  }

  static Set<M68kCpu> mapCpuSet(String cpuText) {
    if (!StringUtil.contains(cpuText, "|")) {
      return mapCpuPart(cpuText);
    }

    Set<M68kCpu> allCpus = EnumSet.noneOf(M68kCpu.class);
    for (String parseCpuText : StringUtil.split(cpuText, "|")) {
      Set<M68kCpu> parse = mapCpuPart(parseCpuText);
      if (parse == null) continue;

      allCpus.addAll(parse);
    }
    return allCpus;
  }

  @Nullable
  private static Set<M68kCpu> mapCpuPart(String parseCpuText) {
    final Set<M68kCpu> m68kCpus = CPU_MAP.get(parseCpuText);
    if (m68kCpus != null) {
      return m68kCpus;
    }

    //noinspection SpellCheckingInspection
    if (!"mgas".equals(parseCpuText) &&
      !"malias".equals(parseCpuText) &&
      !"mbanked".equals(parseCpuText) &&
      !StringUtil.startsWith(parseCpuText, "mcf")) {
      TestCase.fail("cannot parse CPU '" + parseCpuText + "'");
    }
    return null;
  }
}
