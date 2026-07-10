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

package com.yanncebron.m68kplugin.documentation;

import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.util.text.Strings;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;

import java.util.*;
import java.util.function.Predicate;

class M68kInstructionMnemonicDocsGenerator {

  private final Collection<M68kMnemonic> allMnemonics;
  private final M68kMnemonic instructionMnemonic;
  private final IElementType mnemonicElementType;

  private StringBuilder sb;

  M68kInstructionMnemonicDocsGenerator(M68kMnemonic mnemonic, boolean highlightMatching) {
    allMnemonics = mnemonic.hasSpecialRegisterOperands() ? Set.of(mnemonic) :
      ContainerUtil.filter(M68kMnemonicRegistry.getInstance().findAll(mnemonic.elementType()), m68kMnemonic -> !m68kMnemonic.hasSpecialRegisterOperands());
    assert !allMnemonics.isEmpty() : mnemonic;
    mnemonicElementType = mnemonic.elementType();
    instructionMnemonic = highlightMatching && allMnemonics.size() > 1 ? mnemonic : null;
  }

  M68kInstructionMnemonicDocsGenerator(IElementType elementType) {
    allMnemonics = M68kMnemonicRegistry.getInstance().findAll(elementType);
    assert !allMnemonics.isEmpty() : elementType;

    mnemonicElementType = elementType;
    instructionMnemonic = null;
  }

  /**
   * @param predicate to filter specific mnemonic variants
   * @see M68kMnemonicPredicates
   */
  String generateHtmlDoc(Predicate<M68kMnemonic> predicate) {
    sb = new StringBuilder();

    List<M68kMnemonic> filteredMnemonics = allMnemonics.stream().filter(predicate).toList();
    assert !filteredMnemonics.isEmpty() : predicate + " for " + allMnemonics;

    boolean insertBreak = false;
    boolean allCpusSame = true;
    Set<M68kCpu> previousCpus = null;
    for (M68kMnemonic mnemonic : filteredMnemonics) {
      final Set<M68kCpu> cpus = mnemonic.cpus();
      if (previousCpus != null && !previousCpus.equals(cpus)) {
        allCpusSame = false;
        break;
      }
      previousCpus = cpus;
    }
    if (allCpusSame) {
      appendCpuSection(ContainerUtil.getFirstItem(filteredMnemonics));
      insertBreak = true;
    }

    M68kMnemonic m68kMnemonic = ContainerUtil.getFirstItem(filteredMnemonics);
    if (M68kMnemonicPredicates.privilegedAny().test(m68kMnemonic)) {
      appendPrivilegedSection(m68kMnemonic);
      insertBreak = true;
    }

    if (insertBreak) {
      appendBreak();
    }

    // only operands with >1 address mode, otherwise they cannot appear in the table(s)
    Set<M68kAddressMode> allUsedAddressModesFromMultiOperands = new HashSet<>();
    for (M68kMnemonic mnemonic : filteredMnemonics) {
      M68kAddressMode[] sourceModes = mnemonic.firstOperand().getAddressModes();
      if (sourceModes.length > 1) ContainerUtil.addAll(allUsedAddressModesFromMultiOperands, sourceModes);
      M68kAddressMode[] destinationModes = mnemonic.secondOperand().getAddressModes();
      if (destinationModes.length > 1) ContainerUtil.addAll(allUsedAddressModesFromMultiOperands, destinationModes);
    }

    for (Iterator<M68kMnemonic> iterator = filteredMnemonics.iterator(); iterator.hasNext(); ) {
      M68kMnemonic mnemonic = iterator.next();
      String style = instructionMnemonic == mnemonic ? "underline" : "";
      if (mnemonic.deprecated()) {
        style += " line-through";
      }
      if (!style.isEmpty()) {
        sb.append("<h4 style=\"text-decoration: ").append(style).append(";\">");
      } else {
        sb.append("<h4>");
      }


      String dataSizeText = StringUtil.join(mnemonic.dataSizes(), M68kDataSize::getText, "|");
      int dataSizeTextLength = dataSizeText.length();
      dataSizeText = StringUtil.escapeXmlEntities(dataSizeText); // "<unsized>"
      dataSizeText = dataSizeText.replace(".", M68kDocumentationUtil.NON_BREAK_PERIOD);

      sb.append("<code>");
      String mnemonicText = mnemonicElementType.toString();
      sb.append(StringUtil.toUpperCase(mnemonicText)).append(dataSizeText);

      if (mnemonic.hasFirstOperand()) {
        sb.append(StringUtil.repeat("&nbsp;", Math.max(1, 17 - mnemonicText.length() - dataSizeTextLength)));
        appendOperand(mnemonic.firstOperand(), "");
        appendOperand(mnemonic.secondOperand(), ",");
      }

      sb.append("</code></h4>");


      if (!allCpusSame) {
        appendCpuSection(mnemonic);
      }

      final M68kAddressMode[] firstAddressModes = mnemonic.firstOperand().getAddressModes();
      final M68kAddressMode[] secondAddressModes = mnemonic.secondOperand().getAddressModes();
      if (firstAddressModes.length <= 1 &&
        secondAddressModes.length <= 1) {
        if (iterator.hasNext()) {
          appendBreak();
        }
        continue;
      }


      sb.append("<table style=\"width: 100%;\"><tr>");
      sb.append("<th></th>");
      for (M68kAddressMode value : M68kAddressMode.values()) {
        if (!allUsedAddressModesFromMultiOperands.contains(value)) continue;

        sb.append("<th style=\"text-align:center;\">");
        // prevent 'abs.w' from breaking after 'period' on resize. yes, CSS simply won't work here
        sb.append(value.getNotation().replace(".", M68kDocumentationUtil.NON_BREAK_PERIOD));
        sb.append("</th>");
      }
      sb.append("</tr>");

      appendAddressModes(M68kBundle.message("documentation.operand.first"), allUsedAddressModesFromMultiOperands, firstAddressModes);
      appendAddressModes(M68kBundle.message("documentation.operand.second"), allUsedAddressModesFromMultiOperands, secondAddressModes);
      sb.append("</table>");

      appendBreak();
    }

    return sb.toString();
  }

  private void appendCpuSection(M68kMnemonic mnemonic) {
    sb.append(DocumentationMarkup.SECTIONS_START);
    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append(M68kBundle.message("documentation.section.cpu"));
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    M68kDocsGeneratorUtil.appendCpus(sb, mnemonic.cpus());
    sb.append(DocumentationMarkup.SECTION_END);
    sb.append(DocumentationMarkup.SECTIONS_END);
  }

  private void appendPrivilegedSection(M68kMnemonic mnemonic) {
    sb.append(DocumentationMarkup.SECTIONS_START);
    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append(M68kBundle.message("documentation.section.privileged"));
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    String message;
    if (M68kMnemonicPredicates.privileged68010Above().test(mnemonic)) {
      message = M68kBundle.message("cpu.group.GROUP_68010_UP");
    } else {
      message = M68kBundle.message("cpu.group.GROUP_68000_UP");
    }
    sb.append(message);
    sb.append(DocumentationMarkup.SECTION_END);
    sb.append(DocumentationMarkup.SECTIONS_END);

  }

  private void appendBreak() {
    sb.append("<br>");
  }

  private void appendOperand(M68kOperand m68kOperand, String prefix) {
    if (m68kOperand == M68kOperand.NONE) {
      return;
    }

    sb.append(prefix);
    sb.append(Strings.escapeXmlEntities(m68kOperand.getNotation()));
  }

  private void appendAddressModes(String label,
                                  Set<M68kAddressMode> allPossibleAddressModes,
                                  M68kAddressMode[] addressModes) {
    if (addressModes.length <= 1) return;

    sb.append("<tr>");
    DocumentationMarkup.SECTION_HEADER_CELL.attr("width", "15%").appendTo(sb);
    sb.append(label);
    sb.append("</td>");

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
