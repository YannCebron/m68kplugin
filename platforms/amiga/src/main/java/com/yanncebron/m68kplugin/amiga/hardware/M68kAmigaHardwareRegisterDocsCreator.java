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

package com.yanncebron.m68kplugin.amiga.hardware;

import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase;
import com.yanncebron.m68kplugin.documentation.M68kDocumentationUtil;

import java.util.List;

import static com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase.M68K_BROWSER_ITEM_LINK_PREFIX;

final class M68kAmigaHardwareRegisterDocsCreator {

  private static final String DOC_ROOT = "/docs/amigaHardwareRegister/";

  private final M68kAmigaHardwareRegister register;
  private final boolean isHardwareRegister;

  private final List<M68kAmigaHardwareRegister> sortedShownRegisters;

  M68kAmigaHardwareRegisterDocsCreator(M68kAmigaHardwareRegister register, List<M68kAmigaHardwareRegister> sortedShownRegisters) {
    this.register = register;
    this.sortedShownRegisters = sortedShownRegisters;
    isHardwareRegister = this.register.getChipset() != M68kAmigaHardwareRegister.Chipset.N_A;
  }

  String generateDoc(boolean includeReferenceDoc) {
    StringBuilder sb = new StringBuilder(M68kDocumentationUtil.CSS);

    sb.append(DocumentationMarkup.DEFINITION_START);
    sb.append("<h1>").append(register.getName()).append("</h1>");
    sb.append(register.getDescription());
    sb.append(DocumentationMarkup.DEFINITION_END);

    sb.append(DocumentationMarkup.SECTIONS_START);

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Address:");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    String address = "$" + register.getAddress();
    String shortAddress = "$0" + address.substring(4);
    sb.append("<code>").append(address).append("</code>")
      .append("<a href='").append(M68kBrowserPaneBase.M68K_BROWSER_COPY_DATA_LINK_PREFIX).append(address).append("'><icon src='AllIcons.Actions.Copy'/></a>");
    if (isHardwareRegister) {
      sb.append("&nbsp;&ndash;&nbsp;")
        .append("<code>").append(shortAddress).append("</code>")
        .append("<a href='").append(M68kBrowserPaneBase.M68K_BROWSER_COPY_DATA_LINK_PREFIX).append(shortAddress).append("'><icon src='AllIcons.Actions.Copy'/></a>");
    }
    sb.append(DocumentationMarkup.SECTION_END);

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    if (isHardwareRegister) {
      sb.append("Chip Set (Chips):");
      sb.append(DocumentationMarkup.SECTION_SEPARATOR);
      sb.append(register.getChipset().getDisplayName());
      sb.append(" (").append(StringUtil.join(register.getChips(), M68kAmigaHardwareRegister.Chip::getDisplayName, ", ")).append(")");
    } else {
      sb.append("CIA:");
      sb.append(DocumentationMarkup.SECTION_SEPARATOR);
      sb.append(ContainerUtil.getFirstItem(register.getChips()).getDisplayName());
    }
    sb.append(DocumentationMarkup.SECTION_END);

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Access:");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    sb.append(register.getAccess().getDisplayName());
    sb.append(DocumentationMarkup.SECTION_END);

    if (isHardwareRegister) {
      sb.append(DocumentationMarkup.SECTION_HEADER_START);
      sb.append("Copper Danger:");
      sb.append(DocumentationMarkup.SECTION_SEPARATOR);
      sb.append(register.isCopperDanger() ? M68kDocumentationUtil.CHECK_MARK : "-");
      sb.append(DocumentationMarkup.SECTION_END);
    }

    appendRelated(sb);

    sb.append(DocumentationMarkup.SECTIONS_END);

    if (includeReferenceDoc) {
      sb.append(DocumentationMarkup.CONTENT_START);
      sb.append(getReferenceDoc());
      sb.append(DocumentationMarkup.CONTENT_END);
    }

    return sb.toString();
  }

  private void appendRelated(StringBuilder sb) {
    List<M68kAmigaHardwareRegister> allRelated = ContainerUtil.filter(sortedShownRegisters,
      relatedRegister -> relatedRegister.getDescriptionFileName().equals(register.getDescriptionFileName()));
    if (allRelated.size() == 1) return;

    StringBuilder relatedSb = new StringBuilder("<table><tr>");
    int itemCount = 1;
    for (M68kAmigaHardwareRegister related : allRelated) {
      relatedSb.append(DocumentationMarkup.SECTION_START);
      if (related == register) relatedSb.append("<b>");
      relatedSb.append("<a href='" + M68K_BROWSER_ITEM_LINK_PREFIX).append(related.getName()).append("'>");
      relatedSb.append(related.getName()).append("</a>");
      if (related == register) relatedSb.append("</b>");
      relatedSb.append("</td>");
      if (itemCount++ % 4 == 0) relatedSb.append("</tr><tr>");
    }
    relatedSb.append("</tr></table>");

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Related:");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    sb.append(relatedSb);
    sb.append(DocumentationMarkup.SECTION_END);
  }

  private String getReferenceDoc() {
    Couple<String> markdownContents = M68kDocumentationUtil.getMarkdownContents(DOC_ROOT, register.getDescriptionFileName());
    if (markdownContents.getFirst() == null) {
      return markdownContents.getSecond();
    }

    return M68kDocumentationUtil.getHtmlForMarkdown(DOC_ROOT, markdownContents.getFirst(), M68kBrowserPaneBase.M68K_BROWSER_LINK_FUNCTION);
  }
}
