/*
 * Copyright 2020 The Authors
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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzerSettings;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.impl.DaemonCodeAnalyzerImpl;
import com.intellij.openapi.editor.markup.SeparatorPlacement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;

import java.util.List;

public class M68kMethodSeparatorLineMarkerProviderTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    DaemonCodeAnalyzerSettings.getInstance().SHOW_METHOD_SEPARATORS = true;
  }

  @Override
  protected void tearDown() throws Exception {
    try {
      super.tearDown();
    } finally {
      DaemonCodeAnalyzerSettings.getInstance().SHOW_METHOD_SEPARATORS = false;
    }
  }

  public void testMacro() {
    final List<LineMarkerInfo<?>> lineMarkerInfos = getLineMarkerInfos("""
      macroName macro
       endm
      """);
    assertSize(2, lineMarkerInfos);

    LineMarkerInfo<?> before = lineMarkerInfos.get(0);
    assertBefore(before, "macroName");

    final LineMarkerInfo<?> after = lineMarkerInfos.get(1);
    assertEquals(SeparatorPlacement.BOTTOM, after.separatorPlacement);
    final LeafPsiElement afterPsiElement = assertInstanceOf(after.getElement(), LeafPsiElement.class);
    assertEquals("endm", afterPsiElement.getText());
  }

  public void testSectionDirective() {
    doTestSingleBefore(" section tos,code,chip", "section");
  }

  public void testMachineDirective() {
    doTestSingleBefore(" machine mc68000", "machine");
  }

  public void testSpecificMachineDirectives() {
    List<IElementType> directives = List.of(M68kTokenTypes.MC68000, M68kTokenTypes.MC68010, M68kTokenTypes.MC68020,
      M68kTokenTypes.MC68030, M68kTokenTypes.MC68040, M68kTokenTypes.MC68060,
      M68kTokenTypes.AC68080, M68kTokenTypes.CPU32);
    for (IElementType directive : directives) {
      String directiveText = directive.toString();
      doTestSingleBefore(" " + directiveText, directiveText);
    }
  }

  private List<LineMarkerInfo<?>> getLineMarkerInfos(String text) {
    myFixture.configureByText("test.s", text);
    myFixture.doHighlighting();
    return DaemonCodeAnalyzerImpl.getLineMarkers(myFixture.getEditor().getDocument(), getProject());
  }

  private void doTestSingleBefore(String text, String leafText) {
    List<LineMarkerInfo<?>> lineMarkerInfos = getLineMarkerInfos(text);
    LineMarkerInfo<?> info = assertOneElement(lineMarkerInfos);
    assertBefore(info, leafText);
  }

  private static void assertBefore(LineMarkerInfo<?> info, String leafText) {
    assertEquals(SeparatorPlacement.TOP, info.separatorPlacement);
    final LeafPsiElement beforePsiElement = assertInstanceOf(info.getElement(), LeafPsiElement.class);
    assertEquals(leafText, beforePsiElement.getText());
  }
}
