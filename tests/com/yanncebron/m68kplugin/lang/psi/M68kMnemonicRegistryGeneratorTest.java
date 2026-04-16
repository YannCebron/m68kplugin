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
 * Generates {@link M68kMnemonicRegistry} data from <a href="http://sun.hasenbraten.de/vasm/">vasm</a> input file
 * plus additional runtime information from {@code M68kMnemonicRegistryRuntimeData.txt}.
 * <p>
 * To generate new data:
 * <ol>
 *   <li>Specify path to vasm {@code cpus/m68k/opcodes.h} file in {@link #VASM_OPCODES_H_PATH}</li>
 *   <li>Adjust {@link #RUNTIME_DATA_PATH}</li>
 *   <li>Set {@link #ENABLED} to {@code true}</li>
 *   <li>Run {@link #testGenerateMnemonicRegistryData()} and copy generated source output</li>
 *   <li>Verify MnemonicGeneratedParserDataTest passes, dump</li>
 * </ol>
 * Last check: vasm 2.0e.
 */
@SuppressWarnings("SpellCheckingInspection")
public class M68kMnemonicRegistryGeneratorTest extends TestCase {

  private static final boolean ENABLED = false;

  private static final boolean LOG_UNKNOWN_MNEMONICS = false;

  private static final boolean SKIP_UNSUPPORTED_CPUS = true;

  /**
   * Generate 680x0-only variants for _known_ operands.
   */
  private static final boolean IGNORE_UNKNOWN_OPERANDS = true;

  private static final Set<M68kCpu> SUPPORTED_CPUS = EnumSet.of(M68kCpu.M_68000, M68kCpu.M_68010, M68kCpu.M_68020);

  private static final String VASM_OPCODES_H_PATH = "/Users/yann/idea-ultimate/vasm/cpus/m68k/opcodes.h";
  private static final String RUNTIME_DATA_PATH = "/Users/yann/idea-ultimate/m68kplugin/tests/com/yanncebron/m68kplugin/lang/psi/M68kMnemonicRegistryRuntimeData.txt";

  public void testGenerateMnemonicRegistryData() throws IOException {
    if (!ENABLED) return;

    List<M68kMnemonicRuntimeData> allRuntimeData = readRuntimeData();
    assertEquals("parsed runtime data count", 17, allRuntimeData.size());

    final List<String> lines = Files.readAllLines(Paths.get(VASM_OPCODES_H_PATH));
    assertEquals("line count opcodes.h", 2914, lines.size());

    printDivider();

    Set<String> unknownMnemonics = new HashSet<>();
    List<M68kMnemonic> parsedMnemonics = new ArrayList<>();

    for (String line : lines) {
      final String trim = line.trim();
      final String[] mnemonicParts = trim.split("\"");
      if (mnemonicParts.length != 3) continue;

      String mnemonic = mnemonicParts[1];
      if (mnemonic.isEmpty() || " no-op".equals(mnemonic)) continue;

      IElementType elementType = findElementType(mnemonic);
      if (elementType == null) {
        if (LOG_UNKNOWN_MNEMONICS && unknownMnemonics.add(mnemonic)) {
          System.out.println("unknown mnemonic '" + mnemonic + "': " + trim);
        }
        continue;
      }

      final List<String> split = StringUtil.split(trim, "{");
      assertEquals(split.size() + " parts: cannot parse '" + trim + "'", 4, split.size());

      // Operands ----------------------------
      Couple<M68kOperand> operands = mapOperands(split.get(1));
      M68kOperand firstOperand = operands.getFirst();
      M68kOperand secondOperand = operands.getSecond();

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

      if (isSupportedCpu(m68kCpus)) {
        if (firstOperand == null) {
          if (IGNORE_UNKNOWN_OPERANDS) {
            System.out.println("skipping unknown first operand: " + trim);
            continue;
          }
          fail("unknown first operand: " + trim);
        }
        if (secondOperand == null) {
          if (IGNORE_UNKNOWN_OPERANDS) {
            System.out.println("skipping unknown second operand: " + trim);
            continue;
          }
          fail("unknown second operand: " + trim);
        }
      }

      if (firstOperand == null || secondOperand == null) {
        continue;
      }

      M68kMnemonicRuntimeData m68kMnemonicRuntimeData = ContainerUtil.find(allRuntimeData, it ->
        it.elementType == elementType &&
          it.firstOperand == firstOperand &&
          it.secondOperand == secondOperand &&
          it.dataSizes.equals(dataSizes) &&
          it.cpus.equals(m68kCpus));
      M68kMnemonic.PrivilegedType privilegedType = m68kMnemonicRuntimeData != null ? m68kMnemonicRuntimeData.privilegedType : M68kMnemonic.PrivilegedType.NONE;

      M68kMnemonic m68kMnemonic = new M68kMnemonic(elementType,
        dataSizes,
        firstOperand,
        secondOperand,
        m68kCpus,
        privilegedType);

      parsedMnemonics.add(m68kMnemonic);
    }

    assertEquals("total parsed mnemonic count", 333, parsedMnemonics.size());

    List<M68kMnemonic> cleanupMnemonics = cleanupMnemonics(parsedMnemonics);
    assertEquals("total cleanup mnemonic count", 293, cleanupMnemonics.size());

    dumpCode(cleanupMnemonics);
  }

  private static List<M68kMnemonic> cleanupMnemonics(List<M68kMnemonic> parsedMnemonics) {
    printDivider();

    List<M68kMnemonic> cleanupMnemonics = new ArrayList<>();

    // skip duplicate entries for bset,bclr,bchg with ALTERABLE_MEMORY_CF vs. ALTERABLE_MEMORY
    // both have the exact same of address modes ATM, so it's a full duplicate
    for (M68kMnemonic m68kMnemonic : parsedMnemonics) {
      boolean skip = false;
      for (M68kMnemonic existing : parsedMnemonics) {
        if (matchingMnemonic(existing, m68kMnemonic) &&
          existing.firstOperand() == m68kMnemonic.firstOperand() &&
          m68kMnemonic.secondOperand() == M68kOperand.ALTERABLE_MEMORY_CF && existing.secondOperand() == M68kOperand.ALTERABLE_MEMORY) {
          System.out.println("skip ALTERABLE_MEMORY(CF) duplication:");
          System.out.println("  entry:    " + m68kMnemonic);
          System.out.println("  existing: " + existing);
          System.out.println();
          skip = true;
          break;
        }
      }
      if (!skip) cleanupMnemonics.add(m68kMnemonic);
    }
    assertEquals("skip ALTERABLE_MEMORY(CF) duplication difference count", 3, parsedMnemonics.size() - cleanupMnemonics.size());


    // cleanup duplicates for addressing modes (due to separate entries for CF), e.g., "IM,_D" vs. "IM,AD"
    List<M68kMnemonic> toRemove = new ArrayList<>();
    for (M68kMnemonic m68kMnemonic : cleanupMnemonics) {
      boolean skip = false;
      for (M68kMnemonic existing : cleanupMnemonics) {
        if (matchingMnemonic(existing, m68kMnemonic) &&
          operandAddressModesOverlap(m68kMnemonic, existing)) {
          System.out.println("skip operand address modes overlap");
          System.out.println("  entry:    " + m68kMnemonic);
          System.out.println("  existing: " + existing);
          System.out.println();
          skip = true;
          break;
        }
      }
      if (skip) toRemove.add(m68kMnemonic);
    }
    assertEquals("operand address modes overlap count", 37, toRemove.size());
    cleanupMnemonics.removeAll(toRemove);


    // SANITY CHECK: there must be no _CF operands leftover
    Set<M68kOperand> CF_OPERANDS = EnumSet.of(M68kOperand.ALTERABLE_DATA_CF, M68kOperand.ALTERABLE_MEMORY_CF);
    for (M68kMnemonic cleanupMnemonic : cleanupMnemonics) {
      assertFalse("must not use _CF in first operand: " + cleanupMnemonic, CF_OPERANDS.contains(cleanupMnemonic.firstOperand()));
      assertFalse("must not use _CF in second operand: " + cleanupMnemonic, CF_OPERANDS.contains(cleanupMnemonic.secondOperand()));
    }

    // SANITY CHECK: no leftover overlap in address modes
    for (M68kMnemonic first : cleanupMnemonics) {
      for (M68kMnemonic second : cleanupMnemonics) {
        assertFalse("must not have overlap address modes: \n" + first + "\n" + second,
          matchingMnemonic(first, second) && operandAddressModesOverlap(first, second));
      }
    }

    return cleanupMnemonics;
  }

  private static boolean matchingMnemonic(M68kMnemonic existing, M68kMnemonic m68kMnemonic) {
    return existing.elementType() == m68kMnemonic.elementType() &&
      existing.dataSizes().equals(m68kMnemonic.dataSizes()) &&
      existing.cpus().equals(m68kMnemonic.cpus());
  }

  /**
   * Either first or second operand's address modes have a full overlap.
   */
  private static boolean operandAddressModesOverlap(M68kMnemonic firstMnemonic, M68kMnemonic secondMnemonic) {
    if (firstMnemonic == secondMnemonic) return false;

    if (firstMnemonic.firstOperand() == secondMnemonic.firstOperand()) {
      return addressModesOverlap(firstMnemonic.secondOperand(), secondMnemonic.secondOperand());
    }

    if (firstMnemonic.secondOperand() == secondMnemonic.secondOperand() &&
      firstMnemonic.hasSecondOperand()) {
      return addressModesOverlap(firstMnemonic.firstOperand(), secondMnemonic.firstOperand());
    }

    return !firstMnemonic.hasSecondOperand() && addressModesOverlap(firstMnemonic.firstOperand(), secondMnemonic.firstOperand());
  }

  private static boolean addressModesOverlap(M68kOperand first, M68kOperand second) {
    if (first == M68kOperand.NONE || second == M68kOperand.NONE) return false;

    Set<M68kAddressMode> firstAddressModes = Set.of(first.getAddressModes());
    Set<M68kAddressMode> secondAddressModes = Set.of(second.getAddressModes());
    return secondAddressModes.containsAll(firstAddressModes);
  }

  private static Couple<M68kOperand> mapOperands(String operandText) {
    operandText = operandText.contains("{") ? StringUtil.substringAfter(operandText, "{") : operandText;
    assertNotNull(operandText);
    operandText = StringUtil.substringBefore(operandText, "}");
    assertNotNull(operandText);
    if (operandText.contains(",")) {
      List<String> operandTexts = StringUtil.split(operandText, ",");
      return Couple.of(mapOperand(operandTexts.get(0)), mapOperand(operandTexts.get(1)));
    }
    return Couple.of(mapOperand(operandText), M68kOperand.NONE);
  }

  private static @Nullable IElementType findElementType(String mnemonic) {
    return ContainerUtil.find(M68kTokenGroups.INSTRUCTIONS.getTypes(),
      iElementType -> iElementType.toString().equals(mnemonic));
  }

  private boolean isSupportedCpu(Set<M68kCpu> cpus) {
    return ContainerUtil.intersects(cpus, SUPPORTED_CPUS);
  }

  private static void printDivider() {
    System.out.println();
    System.out.println(StringUtil.repeat("-", 120));
  }

  private void dumpCode(List<M68kMnemonic> mnemonics) {
    int supportedMnemonics = ContainerUtil.filter(mnemonics, m68kMnemonic -> isSupportedCpu(m68kMnemonic.cpus())).size();
    assertEquals("supported mnemonic count", 246, supportedMnemonics);

    printDivider();

    System.out.println("// Total mnemonics: " + supportedMnemonics);

    IElementType lastElementType = null;
    for (M68kMnemonic mnemonic : mnemonics) {
      Set<M68kCpu> cpus = mnemonic.cpus();
      if (SKIP_UNSUPPORTED_CPUS && !isSupportedCpu(cpus)) continue;

      String mnemonicText = StringUtil.toUpperCase(mnemonic.elementType().toString());
      if (lastElementType != mnemonic.elementType()) {
        System.out.println("\n// " + mnemonicText + " " + StringUtil.repeatSymbol('-', 80 - 4 - mnemonicText.length()));
      }

      String tokenText = "M68kTokenTypes." + mnemonicText;
      String dataSizeText = getDataSizeText(mnemonic);
      String cpuText = getCpuText(cpus);

      System.out.println();
      System.out.print("create(" + tokenText + ")");
      if (dataSizeText != null) {
        System.out.println(".dataSizes(" + dataSizeText + ")");
      } else {
        System.out.println();
      }
      if (mnemonic.hasFirstOperand()) {
        System.out.print(".first(" + mnemonic.firstOperand().name() + ")");
        if (mnemonic.hasSecondOperand()) {
          System.out.print(".second(" + mnemonic.secondOperand().name() + ")");
        }
        System.out.println();
      }
      if (cpuText != null) {
        System.out.println(".cpus(" + cpuText + ")");
      }
      if (mnemonic.privilegedType() != M68kMnemonic.PrivilegedType.NONE) {
        System.out.println(".privileged(M68kMnemonic.PrivilegedType." + mnemonic.privilegedType().name() + ")");
      }
      System.out.println(".build();");

      lastElementType = mnemonic.elementType();
    }
    System.out.println();
    System.out.println();
  }

  private static @Nullable String getCpuText(Set<M68kCpu> cpus) {
    if (M68kCpu.GROUP_68000_UP.equals(cpus)) {
      return null;
    }

    if (M68kCpu.GROUP_68010_UP.equals(cpus)) {
      return "GROUP_68010_UP";
    }
    if (M68kCpu.GROUP_68020_UP.equals(cpus)) {
      return "GROUP_68020_UP";
    }

    return "EnumSet.of(" + StringUtil.join(cpus, m68kCpu -> "M68kCpu." + m68kCpu.name(), ",") + ")";
  }


  private static @Nullable String getDataSizeText(M68kMnemonic mnemonic) {
    final Set<M68kDataSize> dataSizes = mnemonic.dataSizes();

    if (M68kDataSize.GROUP_UNSIZED.equals(dataSizes)) {
      return null;
    } else if (M68kDataSize.GROUP_SBWL.equals(dataSizes)) {
      return "GROUP_SBWL";
    } else if (M68kDataSize.GROUP_SBW.equals(dataSizes)) {
      return "GROUP_SBW";
    } else if (M68kDataSize.GROUP_BWL.equals(dataSizes)) {
      return "GROUP_BWL";
    } else if (M68kDataSize.GROUP_WL.equals(dataSizes)) {
      return "GROUP_WL";
    } else if (M68kDataSize.GROUP_S.equals(dataSizes)) {
      return "GROUP_S";
    } else if (M68kDataSize.GROUP_B.equals(dataSizes)) {
      return "GROUP_B";
    } else if (M68kDataSize.GROUP_W.equals(dataSizes)) {
      return "GROUP_W";
    } else if (M68kDataSize.GROUP_L.equals(dataSizes)) {
      return "GROUP_L";
    }
    return "EnumSet.of(" + StringUtil.join(dataSizes, dataSize -> "M68kDataSize." + dataSize.name(), ",") + ")";
  }

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

  private List<M68kMnemonicRuntimeData> readRuntimeData() throws IOException {
    List<M68kMnemonicRuntimeData> data = new ArrayList<>();

    for (String line : Files.readAllLines(Paths.get(RUNTIME_DATA_PATH))) {
      if (!line.startsWith("\"")) continue;

      List<String> split = StringUtil.split(line, ", ");

      String mnemonic = StringUtil.unquoteString(split.get(0).strip());
      IElementType elementType = findElementType(mnemonic);
      Couple<M68kOperand> operands = mapOperands(split.get(1).strip());
      Set<M68kDataSize> dataSizes = mapDataSizes(split.get(2).strip());
      Set<M68kCpu> m68kCpus = mapCpuSet(split.get(3).strip());
      M68kMnemonic.PrivilegedType privilegedType = M68kMnemonic.PrivilegedType.valueOf(split.get(4).strip());

      data.add(
        new M68kMnemonicRuntimeData(elementType, dataSizes,
          operands.getFirst(), operands.getSecond(),
          m68kCpus, privilegedType));
    }
    return data;
  }

  private record M68kMnemonicRuntimeData(IElementType elementType, Set<M68kDataSize> dataSizes,
                                         M68kOperand firstOperand, M68kOperand secondOperand,
                                         Set<M68kCpu> cpus, M68kMnemonic.PrivilegedType privilegedType) {
  }
}
