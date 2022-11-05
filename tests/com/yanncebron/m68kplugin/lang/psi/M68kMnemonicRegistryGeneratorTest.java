/*
 * Copyright 2022 The Authors
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

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.ContainerUtil;
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Generates {@link M68kMnemonicRegistry} data from <a href="http://sun.hasenbraten.de/vasm/">vasm</a> input file.
 * <p>
 * To generate new data:
 * <ol>
 *   <li>Specify path to vasm {@code cpus/m68k/opcodes.h} file in {@link #VASM_OPCODES_H_PATH}</li>
 *   <li>Set {@link #ENABLED} to {@code true}</li>
 *   <li>Run {@link #testGenerateMnemonicRegistryData()} and copy generated source output</li>
 * </ol>
 */
@SuppressWarnings("SpellCheckingInspection")
public class M68kMnemonicRegistryGeneratorTest extends TestCase {

  private static final boolean ENABLED = false;

  private static final boolean LOG_UNKNOWN_MNEMONICS = false;

  private static final boolean SKIP_UNSUPPORTED_CPUS = true;

  private static final Set<M68kCpu> SUPPORTED_CPUS = EnumSet.of(M68kCpu.M_68000, M68kCpu.M_68010);

  private static final String VASM_OPCODES_H_PATH = "/Users/yann/idea-ultimate/vasm/cpus/m68k/opcodes.h";

  public void testGenerateMnemonicRegistryData() throws IOException {
    if (!ENABLED) return;

    Set<String> unknownMnemonics = new HashSet<>();
    List<M68kMnemonic> mnemonics = new ArrayList<>();

    final List<String> strings = Files.readAllLines(Paths.get(VASM_OPCODES_H_PATH));
    for (String string : strings) {
      final String trim = string.trim();
      final String[] mnemonicParts = trim.split("\"");
      if (mnemonicParts.length != 3) continue;

      String mnemonic = mnemonicParts[1];
      if (mnemonic.isEmpty() || " no-op".equals(mnemonic)) continue;

      IElementType elementType = ContainerUtil.find(M68kTokenGroups.INSTRUCTIONS.getTypes(),
        iElementType -> iElementType.toString().equals(mnemonic));
      if (elementType == null && LOG_UNKNOWN_MNEMONICS && unknownMnemonics.add(mnemonic)) {
        System.out.println("unknown mnemonic '" + mnemonic + "': " + trim);
        continue;
      }

      final List<String> split = StringUtil.split(trim, "{");
      assertEquals(split.size() + " parts: cannot parse '" + trim + "'", 4, split.size());

      // Operands ----------------------------
      M68kOperand sourceOperand;
      M68kOperand destinationOperand;
      String operandText = StringUtil.substringBefore(split.get(1), "}");
      assert operandText != null : trim;
      if (operandText.contains(",")) {
        sourceOperand = mapOperand(StringUtil.substringBefore(operandText, ","));
        destinationOperand = mapOperand(StringUtil.substringAfter(operandText, ","));
      } else {
        sourceOperand = mapOperand(operandText);
        destinationOperand = M68kOperand.NONE;
      }

      List<String> lastSplit = StringUtil.split(split.get(3), ",");

      // DataSize
      String dataSizeBlockText = StringUtil.substringAfter(lastSplit.get(2), "|");
      assert dataSizeBlockText != null : trim;
      final String dataSizeText = StringUtil.substringBefore(dataSizeBlockText, "|");
      assert dataSizeText != null : trim;
      Set<M68kDataSize> dataSizes = mapDataSizes(dataSizeText);

      // CPU
      String cpuText = StringUtil.substringBefore(lastSplit.get(3), "}");
      final Set<M68kCpu> m68kCpus = mapCpuSet(cpuText);
      if (m68kCpus == null || m68kCpus.size() == 0) {
        if (!SKIP_UNSUPPORTED_CPUS && elementType != null)
          System.out.println("skip entry for unknown CPU '" + cpuText + "': " + trim);
        continue;
      }

      if (elementType != null && isSupportedCpu(m68kCpus)) {
        if (sourceOperand == null) {
          fail("unknown source operand: " + trim);
        }
        if (destinationOperand == null) {
          fail("unknown destination operand: " + trim);
        }
      }

      if (elementType != null && sourceOperand != null && destinationOperand != null) {
        mnemonics.add(new M68kMnemonic(elementType,
          sourceOperand,
          destinationOperand,
          dataSizes,
          m68kCpus));
      }
    }

    dumpCode(mnemonics);
  }

  private boolean isSupportedCpu(Set<M68kCpu> cpus) {
    return ContainerUtil.intersects(cpus, SUPPORTED_CPUS);
  }

  private void dumpCode(List<M68kMnemonic> mnemonics) {
    System.out.println(StringUtil.repeat("-", 80));
    int supportedMnemonics = ContainerUtil.filter(mnemonics, m68kMnemonic -> isSupportedCpu(m68kMnemonic.getCpus())).size();
    System.out.println("// Total mnemonics: " + supportedMnemonics);

    for (M68kMnemonic mnemonic : mnemonics) {
      Set<M68kCpu> cpus = mnemonic.getCpus();
      if (SKIP_UNSUPPORTED_CPUS && !isSupportedCpu(cpus)) continue;

      String cpuText;
      if (M68kCpu.GROUP_68000_UP.equals(cpus)) {
        cpuText = "M68kCpu.GROUP_68000_UP";
      } else if (M68kCpu.GROUP_68010_UP.equals(cpus)) {
        cpuText = "M68kCpu.GROUP_68010_UP";
      } else if (M68kCpu.GROUP_68020_UP.equals(cpus)) {
        cpuText = "M68kCpu.GROUP_68020_UP";
      } else if (M68kCpu.APOLLO.equals(cpus)) {
        cpuText = "M68kCpu.APOLLO";
      } else {
        cpuText = "EnumSet.of(" + StringUtil.join(cpus, m68kCpu -> "M68kCpu." + m68kCpu.name(), ",") + ")";
      }

      final Set<M68kDataSize> dataSizes = mnemonic.getDataSizes();
      String dataSizeText;
      if (M68kDataSize.GROUP_SBWL.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_SBWL";
      } else if (M68kDataSize.GROUP_SBW.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_SBW";
      } else if (M68kDataSize.GROUP_BWL.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_BWL";
      } else if (M68kDataSize.GROUP_WL.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_WL";
      } else if (M68kDataSize.GROUP_S.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_S";
      } else if (M68kDataSize.GROUP_B.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_B";
      } else if (M68kDataSize.GROUP_W.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_W";
      } else if (M68kDataSize.GROUP_L.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_L";
      } else if (M68kDataSize.GROUP_UNSIZED.equals(dataSizes)) {
        dataSizeText = "M68kDataSize.GROUP_UNSIZED";
      } else {
        dataSizeText = "EnumSet.of(" + StringUtil.join(dataSizes, dataSize -> "M68kDataSize." + dataSize.name(), ",") + ")";
      }

      String mnemonicText = StringUtil.toUpperCase(mnemonic.getElementType().toString());
      final String tokenText = "M68kTokenTypes." + mnemonicText + "";

      System.out.println("mnemonics.putValue(" + tokenText + ",");
      System.out.println("new M68kMnemonic(" +
        tokenText + ",\n" +
        "                 M68kOperand." + mnemonic.getSourceOperand().name() + ", " +
        "M68kOperand." + mnemonic.getDestinationOperand().name() + ",\n" +
        "                 " + dataSizeText + ",\n" +
        "                 " + cpuText +
        "));");
    }
  }

  private static final Map<String, M68kOperand> OPERAND_MAP = ContainerUtil.<String, M68kOperand>immutableMapBuilder()
    .put("0", M68kOperand.NONE)
    .put("IM", M68kOperand.IMMEDIATE)
    .put("QI", M68kOperand.QUICK_IMMEDIATE)
    .put("MI", M68kOperand.MEMORY_WITHOUT_IMMEDIATE)
    .put("BR", M68kOperand.BRANCH_DESTINATION)
    .put("DB", M68kOperand.DBCC_BRANCH_DESTINATION)
    .put("MR", M68kOperand.RESTORE_OPERANDS)
    .put("IR", M68kOperand.IMMEDIATE_REGISTER_LIST_VALUE)
    .put("DA", M68kOperand.DATA)
    .put("AD", M68kOperand.ALTERABLE_DATA)
    .put("CFAD", M68kOperand.ALTERABLE_DATA_CF)
    .put("MA", M68kOperand.MEMORY)
    .put("AM", M68kOperand.ALTERABLE_MEMORY)
    .put("CFAM", M68kOperand.ALTERABLE_MEMORY_CF)
    .put("CT", M68kOperand.CONTROL)
    .put("AC", M68kOperand.ALTERABLE_CONTROL)
    .put("AL", M68kOperand.ALTERABLE)
    .put("AY", M68kOperand.ALL)
    .put("D_", M68kOperand.DATA_REGISTER)
    .put("A_", M68kOperand.ADDRESS_REGISTER)
    .put("R_", M68kOperand.DATA_OR_ADDRESS_REGISTER)
    .put("RL", M68kOperand.DATA_OR_ADDRESS_REGISTER_LIST)
    .put("PA", M68kOperand.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT)
    .put("AP", M68kOperand.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT)
    .put("DP", M68kOperand.ADDRESS_REGISTER_DISPLACEMENT)
    .put("_SR", M68kOperand.SR_REGISTER)
    .put("_USP", M68kOperand.USP_REGISTER)
    .put("_CCR", M68kOperand.CCR_REGISTER)
    .put("_CTRL", M68kOperand.CTRL_REGISTER)
    .build();

  private static M68kOperand mapOperand(String operandText) {
    return OPERAND_MAP.get(operandText);
  }

  private static final Map<String, Set<M68kDataSize>> DATA_SIZE_MAP = ContainerUtil.<String, Set<M68kDataSize>>immutableMapBuilder()
    .put("B", M68kDataSize.GROUP_B)
    .put("W", M68kDataSize.GROUP_W)
    .put("L", M68kDataSize.GROUP_L)
    .put("WL", M68kDataSize.GROUP_WL)
    .put("CFWL", M68kDataSize.GROUP_WL)
    .put("BWL", M68kDataSize.GROUP_BWL)
    .put("CFBWL", M68kDataSize.GROUP_BWL)
    .put("SBW", M68kDataSize.GROUP_SBW)
    .put("SBWL", M68kDataSize.GROUP_SBWL)
    .put("UNS", M68kDataSize.GROUP_UNSIZED)
    .build();

  @NotNull
  private static Set<M68kDataSize> mapDataSizes(String dataSizeText) {
    return DATA_SIZE_MAP.getOrDefault(dataSizeText, EnumSet.noneOf(M68kDataSize.class));
  }

  private static Set<M68kCpu> mapCpuSet(String cpuText) {
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

  private static final Map<String, Set<M68kCpu>> CPU_MAP = ContainerUtil.<String, Set<M68kCpu>>immutableMapBuilder()
    .put("m68000up", M68kCpu.GROUP_68000_UP)
    .put("m68010up", M68kCpu.GROUP_68010_UP)
    .put("m68020up", M68kCpu.GROUP_68020_UP)
    .put("m68030up", M68kCpu.GROUP_68030_UP)
    .put("m68040up", M68kCpu.GROUP_68040_UP)
    .put("mfloat", M68kCpu.FLOAT)
    .put("apollo", M68kCpu.APOLLO)

    .put("m68851", EnumSet.of(M68kCpu.M_68851))
    .put("m68020", EnumSet.of(M68kCpu.M_68020))
    .put("m68030", EnumSet.of(M68kCpu.M_68030))
    .put("m68040", EnumSet.of(M68kCpu.M_68040))
    .put("m68060", EnumSet.of(M68kCpu.M_68060))
    .build();

  @Nullable
  private static Set<M68kCpu> mapCpuPart(String parseCpuText) {
    final Set<M68kCpu> m68kCpus = CPU_MAP.get(parseCpuText);
    if (m68kCpus != null) {
      return m68kCpus;
    }

    if (!"cpu32".equals(parseCpuText) &&
      !"mgas".equals(parseCpuText) &&
      !"malias".equals(parseCpuText) &&
      !"mbanked".equals(parseCpuText) &&
      !StringUtil.startsWith(parseCpuText, "mcf")) {
      fail("cannot parse CPU '" + parseCpuText + "'");
    }
    return null;
  }

}
