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
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Provides additional runtime information from {@code M68kMnemonicRegistryRuntimeData.txt}.
 */
final class M68kMnemonicRegistryRuntimeParser {

  private static final String RUNTIME_DATA_PATH = "/Users/yann/idea-ultimate/m68kplugin/tests/com/yanncebron/m68kplugin/lang/psi/M68kMnemonicRegistryRuntimeData.txt";

  private static List<M68kMnemonicRuntimeData> allRuntimeData = null;

  record M68MnemonicRuntimeInfo(M68kMnemonic.PrivilegedType privilegedType,
                                M68kMnemonic.ControlFlow controlFlow) {
  }

  /**
   * No runtime info available.
   */
  static M68MnemonicRuntimeInfo NO_ENTRY = new M68MnemonicRuntimeInfo(M68kMnemonic.PrivilegedType.NONE, M68kMnemonic.ControlFlow.NOTHING);

  static M68MnemonicRuntimeInfo find(IElementType elementType, M68kOperand firstOperand, M68kOperand secondOperand, Set<M68kDataSize> dataSizes, Set<M68kCpu> m68kCpus) {
    M68kMnemonicRuntimeData m68kMnemonicRuntimeData = ContainerUtil.find(getAllRuntimeData(), it ->
      it.elementType == elementType &&
        it.firstOperand == firstOperand &&
        it.secondOperand == secondOperand &&
        it.dataSizes.equals(dataSizes) &&
        it.cpus.equals(m68kCpus));

    if (m68kMnemonicRuntimeData == null) {
      return NO_ENTRY;
    }

    allRuntimeData.remove(m68kMnemonicRuntimeData);

    return new M68MnemonicRuntimeInfo(m68kMnemonicRuntimeData.privilegedType, m68kMnemonicRuntimeData.controlFlow);
  }

  static void assertAllUsed() {
    UsefulTestCase.assertEmpty(allRuntimeData);
  }

  private static List<M68kMnemonicRuntimeData> getAllRuntimeData() {
    if (allRuntimeData == null) {
      allRuntimeData = readRuntimeData();
    }
    return allRuntimeData;
  }

  private static List<M68kMnemonicRuntimeData> readRuntimeData() {
    List<M68kMnemonicRuntimeData> data = new ArrayList<>();

    List<String> lines;
    try {
      lines = Files.readAllLines(Paths.get(RUNTIME_DATA_PATH));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Iterator<String> it = lines.iterator();
    while (it.hasNext()) {
      String line = it.next();
      if (!line.startsWith("\"")) continue;

      List<String> split = StringUtil.split(line, ", ");

      String mnemonic = StringUtil.unquoteString(split.get(0).strip());
      IElementType elementType = findElementType(mnemonic);
      Couple<M68kOperand> operands = M68kMnemonicRegistryGeneratorParser.mapOperands(split.get(1).strip());
      Set<M68kDataSize> dataSizes = M68kMnemonicRegistryGeneratorParser.mapDataSizes(split.get(2).strip());
      Set<M68kCpu> m68kCpus = M68kMnemonicRegistryGeneratorParser.mapCpuSet(split.get(3).strip());

      line = it.next();
      split = StringUtil.split(line, ", ");

      String privilegedText = split.get(0).strip();
      M68kMnemonic.PrivilegedType privilegedType = "-".equals(privilegedText) ? M68kMnemonic.PrivilegedType.NONE : M68kMnemonic.PrivilegedType.valueOf(privilegedText);
      String controlFlowText = split.get(1).strip();
      M68kMnemonic.ControlFlow controlFlow = "-".equals(controlFlowText) ? M68kMnemonic.ControlFlow.NOTHING : M68kMnemonic.ControlFlow.valueOf(controlFlowText);

      data.add(
        new M68kMnemonicRuntimeData(elementType, operands.getFirst(), operands.getSecond(), dataSizes,
          m68kCpus, privilegedType, controlFlow));
    }

    assert data.size() == 244 : data.size();
    return data;
  }

  private static @Nullable IElementType findElementType(String mnemonic) {
    return ContainerUtil.find(M68kTokenGroups.INSTRUCTIONS.getTypes(),
      iElementType -> iElementType.toString().equals(mnemonic));
  }


  private record M68kMnemonicRuntimeData(IElementType elementType,
                                         M68kOperand firstOperand, M68kOperand secondOperand,
                                         Set<M68kDataSize> dataSizes, Set<M68kCpu> cpus,
                                         M68kMnemonic.PrivilegedType privilegedType,
                                         M68kMnemonic.ControlFlow controlFlow) {
  }
}
