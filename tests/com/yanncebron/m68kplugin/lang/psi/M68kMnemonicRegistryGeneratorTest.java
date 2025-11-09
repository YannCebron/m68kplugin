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

import static java.util.Map.entry;

/**
 * Generates {@link M68kMnemonicRegistry} data from <a href="http://sun.hasenbraten.de/vasm/">vasm</a> input file.
 * <p>
 * To generate new data:
 * <ol>
 *   <li>Specify path to vasm {@code cpus/m68k/opcodes.h} file in {@link #VASM_OPCODES_H_PATH}</li>
 *   <li>Set {@link #ENABLED} to {@code true}</li>
 *   <li>Run {@link #testGenerateMnemonicRegistryData()} and copy generated source output</li>
 * </ol>
 * Last check: vasm 2.0c.
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
      assertNotNull(trim, operandText);
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
      assertNotNull(trim, dataSizeBlockText);
      final String dataSizeText = StringUtil.substringBefore(dataSizeBlockText, "|");
      assertNotNull(trim, dataSizeText);
      Set<M68kDataSize> dataSizes = mapDataSizes(dataSizeText);

      // CPU
      String cpuText = StringUtil.substringBefore(lastSplit.get(3), "}");
      final Set<M68kCpu> m68kCpus = mapCpuSet(cpuText);
      if (m68kCpus == null || m68kCpus.isEmpty()) {
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
    int supportedMnemonics = ContainerUtil.filter(mnemonics, m68kMnemonic -> isSupportedCpu(m68kMnemonic.cpus())).size();
    System.out.println("// Total mnemonics: " + supportedMnemonics);

    for (M68kMnemonic mnemonic : mnemonics) {
      Set<M68kCpu> cpus = mnemonic.cpus();
      if (SKIP_UNSUPPORTED_CPUS && !isSupportedCpu(cpus)) continue;

      String dataSizeText = getDataSizeText(mnemonic);
      String cpuText = getCpuText(cpus);

      String mnemonicText = StringUtil.toUpperCase(mnemonic.elementType().toString());
      String tokenText = "M68kTokenTypes." + mnemonicText;

      System.out.println("mnemonics.putValue(" + tokenText + ",");
      System.out.println("new M68kMnemonic(" +
        tokenText + ",\n" +
        "                 M68kOperand." + mnemonic.sourceOperand().name() + ", " +
        "M68kOperand." + mnemonic.destinationOperand().name() + ",\n" +
        "                 " + dataSizeText + ",\n" +
        "                 " + cpuText +
        "));");
    }
  }

  private static @NotNull String getCpuText(Set<M68kCpu> cpus) {
    if (M68kCpu.GROUP_68000_UP.equals(cpus)) {
      return "M68kCpu.GROUP_68000_UP";
    } else if (M68kCpu.GROUP_68010_UP.equals(cpus)) {
      return "M68kCpu.GROUP_68010_UP";
    } else if (M68kCpu.GROUP_68020_UP.equals(cpus)) {
      return "M68kCpu.GROUP_68020_UP";
    } else if (M68kCpu.APOLLO.equals(cpus)) {
      return "M68kCpu.APOLLO";
    }
    return "EnumSet.of(" + StringUtil.join(cpus, m68kCpu -> "M68kCpu." + m68kCpu.name(), ",") + ")";
  }


  private static @NotNull String getDataSizeText(M68kMnemonic mnemonic) {
    final Set<M68kDataSize> dataSizes = mnemonic.dataSizes();

    if (M68kDataSize.GROUP_SBWL.equals(dataSizes)) {
      return "M68kDataSize.GROUP_SBWL";
    } else if (M68kDataSize.GROUP_SBW.equals(dataSizes)) {
      return "M68kDataSize.GROUP_SBW";
    } else if (M68kDataSize.GROUP_BWL.equals(dataSizes)) {
      return "M68kDataSize.GROUP_BWL";
    } else if (M68kDataSize.GROUP_WL.equals(dataSizes)) {
      return "M68kDataSize.GROUP_WL";
    } else if (M68kDataSize.GROUP_S.equals(dataSizes)) {
      return "M68kDataSize.GROUP_S";
    } else if (M68kDataSize.GROUP_B.equals(dataSizes)) {
      return "M68kDataSize.GROUP_B";
    } else if (M68kDataSize.GROUP_W.equals(dataSizes)) {
      return "M68kDataSize.GROUP_W";
    } else if (M68kDataSize.GROUP_L.equals(dataSizes)) {
      return "M68kDataSize.GROUP_L";
    } else if (M68kDataSize.GROUP_UNSIZED.equals(dataSizes)) {
      return "M68kDataSize.GROUP_UNSIZED";
    }
    return "EnumSet.of(" + StringUtil.join(dataSizes, dataSize -> "M68kDataSize." + dataSize.name(), ",") + ")";
  }

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
    entry("CFAD", M68kOperand.ALTERABLE_DATA_CF),
    entry("MA", M68kOperand.MEMORY),
    entry("AM", M68kOperand.ALTERABLE_MEMORY),
    entry("CFAM", M68kOperand.ALTERABLE_MEMORY_CF),
    entry("CT", M68kOperand.CONTROL),
    entry("AC", M68kOperand.ALTERABLE_CONTROL),
    entry("AL", M68kOperand.ALTERABLE),
    entry("AY", M68kOperand.ALL),
    entry("D_", M68kOperand.DATA_REGISTER),
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

  private static M68kOperand mapOperand(String operandText) {
    return OPERAND_MAP.get(operandText);
  }

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

  private static final Map<String, Set<M68kCpu>> CPU_MAP = Map.ofEntries(
    entry("m68000up", M68kCpu.GROUP_68000_UP),
    entry("m68010up", M68kCpu.GROUP_68010_UP),
    entry("m68020up", M68kCpu.GROUP_68020_UP),
    entry("m68030up", M68kCpu.GROUP_68030_UP),
    entry("m68040up", M68kCpu.GROUP_68040_UP),
    entry("mfloat", M68kCpu.FLOAT),
    entry("apollo", M68kCpu.APOLLO),

    entry("m68851", EnumSet.of(M68kCpu.M_68851)),
    entry("m68020", EnumSet.of(M68kCpu.M_68020)),
    entry("m68030", EnumSet.of(M68kCpu.M_68030)),
    entry("m68040", EnumSet.of(M68kCpu.M_68040)),
    entry("m68060", EnumSet.of(M68kCpu.M_68060))
    );

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
