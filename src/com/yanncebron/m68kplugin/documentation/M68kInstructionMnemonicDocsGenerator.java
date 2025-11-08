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

package com.yanncebron.m68kplugin.documentation;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class M68kInstructionMnemonicDocsGenerator {

  private final IElementType elementType;
  private final @Nullable M68kInstruction instruction;

  private StringBuilder sb;

  M68kInstructionMnemonicDocsGenerator(IElementType elementType, @Nullable M68kInstruction instruction) {
    this.elementType = elementType;
    this.instruction = instruction;
  }

  String generateHtmlDoc() {
    final Collection<M68kMnemonic> allMnemonics = M68kMnemonicRegistry.getInstance().findAll(elementType);
    assert !allMnemonics.isEmpty() : elementType;

    M68kMnemonic specific = instruction != null && allMnemonics.size() > 1 ? M68kMnemonicRegistry.getInstance().find(instruction) : null;

    sb = new StringBuilder();

    boolean allCpusSame = true;
    Set<M68kCpu> previousCpus = null;
    for (M68kMnemonic mnemonic : allMnemonics) {
      final Set<M68kCpu> cpus = mnemonic.cpus();
      if (previousCpus != null && !previousCpus.equals(cpus)) {
        allCpusSame = false;
        break;
      }
      previousCpus = cpus;
    }
    if (allCpusSame) {
      appendCpus(ContainerUtil.getFirstItem(allMnemonics));
      appendBreak();
    }

    Set<M68kAddressMode> allUsedAddressModes = new HashSet<>();
    for (M68kMnemonic mnemonic : allMnemonics) {
      ContainerUtil.addAll(allUsedAddressModes, mnemonic.sourceOperand().getAddressModes());
      ContainerUtil.addAll(allUsedAddressModes, mnemonic.destinationOperand().getAddressModes());
    }

    for (M68kMnemonic mnemonic : allMnemonics) {
      if (specific == mnemonic) {
        sb.append("<h4 style=\"text-decoration: underline;\"> ");
      } else {
        sb.append("<h4>");
      }
      final String mnemonicText = StringUtil.toUpperCase(StringUtil.toUpperCase(elementType.toString())) + StringUtil.join(mnemonic.dataSizes(), M68kDataSize::getText, "|");
      sb.append("<code>").append(StringUtil.escapeXmlEntities(mnemonicText));
      sb.append(StringUtil.repeat("&nbsp;", Math.max(1, 18 - mnemonicText.length())));

      appendOperand(mnemonic.sourceOperand(), "");
      appendOperand(mnemonic.destinationOperand(), ",");

      sb.append("</code></h4>");


      if (!allCpusSame) {
        appendCpus(mnemonic);
      }


      final M68kAddressMode[] sourceAddressModes = mnemonic.sourceOperand().getAddressModes();
      final M68kAddressMode[] destinationAddressModes = mnemonic.destinationOperand().getAddressModes();
      if (sourceAddressModes.length <= 1 &&
        destinationAddressModes.length <= 1) {
        appendBreak();
        continue;
      }


      appendBreak();
      sb.append("<table style=\"width: 100%;\"><tr>");
      sb.append("<th></th>");
      for (M68kAddressMode value : M68kAddressMode.values()) {
        if (!allUsedAddressModes.contains(value)) continue;

        sb.append("<th style=\"text-align:center;\">");
        // prevent 'abs.w' to break after 'period' on resize. yes, CSS simply won't work here
        sb.append(value.getNotation().replace(".", "&#8228;"));
        sb.append("</th>");
      }
      sb.append("</tr>");

      appendAddressModes(M68kBundle.message("documentation.hover.source"), allUsedAddressModes, sourceAddressModes);
      appendAddressModes(M68kBundle.message("documentation.hover.destination"), allUsedAddressModes, destinationAddressModes);
      sb.append("</table>");

      appendBreak();
    }

    return sb.toString();
  }

  private void appendBreak() {
    sb.append("<br>");
  }

  private void appendCpus(M68kMnemonic mnemonic) {
    M68kDocsGeneratorUtil.appendCpus(sb, mnemonic.cpus());
  }

  private void appendOperand(M68kOperand m68kOperand, String prefix) {
    if (m68kOperand == M68kOperand.NONE) {
      return;
    }

    sb.append(prefix);
    final M68kAddressMode[] addressModes = m68kOperand.getAddressModes();
    if (addressModes.length != 1) {
      sb.append("&lt;").append(m68kOperand).append(">");
    } else {
      sb.append(addressModes[0].getNotation());
    }
  }

  private void appendAddressModes(String label,
                                  Set<M68kAddressMode> allPossibleAddressModes,
                                  M68kAddressMode[] addressModes) {
    if (addressModes.length <= 1) return;

    sb.append("<tr>");
    sb.append("<td style=\"width: 15%;\">").append(label).append("</td>");
    for (M68kAddressMode value : M68kAddressMode.values()) {
      if (!allPossibleAddressModes.contains(value)) continue;

      sb.append("<td style=\"text-align:center;\">");
      if (ArrayUtil.contains(value, addressModes)) {
        sb.append(M68kDocumentationUtil.CHECK_MARK);
      }
      sb.append("</td>");
    }
    sb.append("</tr>");
  }

}
