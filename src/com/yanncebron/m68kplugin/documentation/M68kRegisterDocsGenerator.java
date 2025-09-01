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

import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;

public final class M68kRegisterDocsGenerator {

  private static final String DOCS_REGISTER_ROOT = "/docs/registers/";

  private final M68kRegister m68kRegister;

  public M68kRegisterDocsGenerator(M68kRegister m68kRegister) {
    if (m68kRegister == M68kRegister.SFC) {
      this.m68kRegister = M68kRegister.DFC;
    } else if (m68kRegister == M68kRegister.SP || m68kRegister == M68kRegister.SSP || m68kRegister == M68kRegister.USP) {
      this.m68kRegister = M68kRegister.A7;
    } else {
      this.m68kRegister = m68kRegister;
    }
  }

  public String getDocumentation() {
    StringBuilder metaData = new StringBuilder("<br><hr/>");
    M68kDocsGeneratorUtil.appendCpus(metaData, m68kRegister.getCpus());

    IElementType elementType = m68kRegister.getElementType();
    String filename = elementType.toString().toLowerCase();
    Couple<String> markdownContents = M68kDocumentationUtil.getMarkdownContents(DOCS_REGISTER_ROOT, filename);
    if (markdownContents.first == null) {
      return M68kDocumentationUtil.CSS +
        "<h1>" + StringUtil.toUpperCase(m68kRegister.name()) + "</h1>" +
        "<p>" + markdownContents.getSecond() + "</p>" +
        metaData;
    }

    String definition = M68kDocumentationUtil.getHtmlForMarkdown(DOCS_REGISTER_ROOT, markdownContents.first);
    return M68kDocumentationUtil.CSS + definition + metaData;
  }

}