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

import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;

import java.util.function.Function;

public final class M68kRegisterDocsGenerator {

  private static final String DOCS_REGISTER_ROOT = "/docs/registers/";

  private final M68kRegister m68kRegister;

  public M68kRegisterDocsGenerator(M68kRegister m68kRegister) {
    this.m68kRegister = m68kRegister;
  }

  public String getDocumentation(boolean forBrowserPane) {
    StringBuilder sb = new StringBuilder();
    sb.append(DocumentationMarkup.DEFINITION_START);
    sb.append("<h1><code>").append(StringUtil.toUpperCase(m68kRegister.name())).append("</code></h1>");
    sb.append(DocumentationMarkup.DEFINITION_END);

    sb.append(DocumentationMarkup.SECTIONS_START);
    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append(M68kBundle.message("documentation.section.cpu"));
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    M68kDocsGeneratorUtil.appendCpus(sb, m68kRegister.getCpus());
    sb.append(DocumentationMarkup.SECTION_END);
    sb.append(DocumentationMarkup.SECTIONS_END);

    sb.append(DocumentationMarkup.CONTENT_START);
    sb.append(getReferenceDoc(forBrowserPane));
    sb.append(DocumentationMarkup.CONTENT_END);

    return M68kDocumentationUtil.CSS + sb;
  }

  private String getReferenceDoc(boolean forBrowserPane) {
    Couple<String> markdownContents = M68kDocumentationUtil.getMarkdownContents(DOCS_REGISTER_ROOT, getReferenceDocFileName());
    if (markdownContents.first == null) {
      return
        "<h1>" + StringUtil.toUpperCase(m68kRegister.name()) + "</h1>" +
          "<p>" + markdownContents.getSecond() + "</p>";
    }

    return M68kDocumentationUtil.getHtmlForMarkdown(DOCS_REGISTER_ROOT, markdownContents.first,
      forBrowserPane ? M68kBrowserPaneBase.M68K_BROWSER_LINK_FUNCTION : Function.identity());
  }

  private String getReferenceDocFileName() {
    M68kRegister documentationRegister;
    if (m68kRegister == M68kRegister.SFC) {
      documentationRegister = M68kRegister.DFC;
    } else if (m68kRegister == M68kRegister.SP || m68kRegister == M68kRegister.SSP || m68kRegister == M68kRegister.USP) {
      documentationRegister = M68kRegister.A7;
    } else {
      documentationRegister = m68kRegister;
    }
    IElementType elementType = documentationRegister.getElementType();
    return elementType.toString().toLowerCase();
  }

}