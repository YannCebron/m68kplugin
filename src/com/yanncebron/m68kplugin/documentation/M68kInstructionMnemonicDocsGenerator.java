/*
 * Copyright 2021 The Authors
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

import java.util.Collection;
import java.util.Set;

class M68kInstructionMnemonicDocsGenerator {

  private final IElementType elementType;
  private StringBuilder sb;

  M68kInstructionMnemonicDocsGenerator(IElementType elementType) {
    this.elementType = elementType;
  }

  String generateHtmlDoc() {
    final Collection<M68kMnemonic> mnemonics = M68kMnemonicRegistry.getInstance().findAll(elementType);
    assert !mnemonics.isEmpty() : elementType;

    sb = new StringBuilder();

    boolean allCpusSame = true;
    Set<M68kCpu> previousCpus = null;
    for (M68kMnemonic mnemonic : mnemonics) {
      final Set<M68kCpu> cpus = mnemonic.getCpus();
      if (previousCpus != null && !previousCpus.equals(cpus)) {
        allCpusSame = false;
        break;
      }
      previousCpus = cpus;
    }
    if (allCpusSame) {
      appendCpus(ContainerUtil.getFirstItem(mnemonics));
      appendBreak();
    }

    for (M68kMnemonic mnemonic : mnemonics) {
      sb.append("<h2>");
      final String mnemonicText = elementType + StringUtil.join(mnemonic.getDataSizes(), M68kDataSize::getText, "|");
      sb.append("<code>").append(mnemonicText);
      sb.append(StringUtil.repeat("&nbsp;", 15 - mnemonicText.length()));

      appendOperand(mnemonic.getSourceOperand(), "");
      appendOperand(mnemonic.getDestinationOperand(), ",");

      sb.append("</code></h2>");


      if (!allCpusSame) {
        appendCpus(mnemonic);
      }


      final M68kAddressMode[] sourceAddressModes = mnemonic.getSourceOperand().getAddressModes();
      final M68kAddressMode[] destinationAddressModes = mnemonic.getDestinationOperand().getAddressModes();
      if (sourceAddressModes.length <= 1 &&
        destinationAddressModes.length <= 1) {
        appendBreak();
        continue;
      }


      appendBreak();
      sb.append("<table><tr>");
      sb.append("<th></th>");
      for (M68kAddressMode value : M68kAddressMode.values()) {
        sb.append("<th>");
        sb.append(value.getNotation());
        sb.append("</th>");
      }
      sb.append("</tr>");

      appendAddressModes(M68kBundle.message("documentation.hover.source"), sourceAddressModes);
      appendAddressModes(M68kBundle.message("documentation.hover.destination"), destinationAddressModes);
      sb.append("</table>");

      appendBreak();
    }

    return sb.toString();
  }

  private void appendBreak() {
    sb.append("<br>");
  }

  private void appendCpus(M68kMnemonic mnemonic) {
    final Set<M68kCpu> cpus = mnemonic.getCpus();
    if (cpus == M68kCpu.GROUP_68000_UP) {
      sb.append(M68kBundle.message("cpu.group.GROUP_68000_UP"));
      appendBreak();
    } else {
      sb.append("<table><tr>");
      for (M68kCpu value : M68kCpu.GROUP_68000_UP) {
        sb.append("<th>");
        sb.append(value.getCpuName());
        sb.append("</th>");
      }
      sb.append("</tr>");

      sb.append("<tr>");
      for (M68kCpu value : M68kCpu.GROUP_68000_UP) {
        sb.append("<td style=\"text-align:center;\">");
        if (cpus.contains(value)) {
          sb.append("✓");
        }
        sb.append("</td>");
      }
      sb.append("</tr>");
      sb.append("</table>");
    }
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

  private void appendAddressModes(String label, M68kAddressMode[] addressModes) {
    if (addressModes.length <= 1) return;

    sb.append("<tr>");
    sb.append("<td>").append(label).append("</td>");
    for (M68kAddressMode value : M68kAddressMode.values()) {
      sb.append("<td style=\"text-align:center;\">");
      if (ArrayUtil.contains(value, addressModes)) {
        sb.append("✓");
      }
      sb.append("</td>");
    }
    sb.append("</tr>");
  }

}
