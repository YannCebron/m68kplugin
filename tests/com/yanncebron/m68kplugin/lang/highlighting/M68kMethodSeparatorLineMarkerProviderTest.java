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
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class M68kMethodSeparatorLineMarkerProviderTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    DaemonCodeAnalyzerSettings.getInstance().SHOW_METHOD_SEPARATORS = true;

  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    DaemonCodeAnalyzerSettings.getInstance().SHOW_METHOD_SEPARATORS = false;
  }

  public void testMacro() {
    myFixture.configureByText("test.s",
      "macroName macro\n" +
        " endm\n");
    myFixture.doHighlighting();
    final @NotNull List<LineMarkerInfo> lineMarkerInfos =
      DaemonCodeAnalyzerImpl.getLineMarkers(myFixture.getEditor().getDocument(), getProject());

    assertSize(2, lineMarkerInfos);

    final LineMarkerInfo before = lineMarkerInfos.get(0);
    assertEquals(SeparatorPlacement.TOP, before.separatorPlacement);
    final LeafPsiElement beforePsiElement = assertInstanceOf(before.getElement(), LeafPsiElement.class);
    assertEquals("macroName", beforePsiElement.getText());

    final LineMarkerInfo after = lineMarkerInfos.get(1);
    assertEquals(SeparatorPlacement.BOTTOM, after.separatorPlacement);
    final LeafPsiElement afterPsiElement = assertInstanceOf(after.getElement(), LeafPsiElement.class);
    assertEquals("endm", afterPsiElement.getText());
  }
}
