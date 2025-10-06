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

package com.yanncebron.m68kplugin.editor.navigation;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.ArrayUtil;
import com.yanncebron.m68kplugin.lang.M68kFileType;

import java.util.List;

import static com.intellij.ide.navbar.tests.TestFrameworkKt.contextNavBarPathStrings;

public class M68kNavBarTest extends BasePlatformTestCase {

  public void testEmptyFile() {
    doTestNavBarModel("""
      <caret>
      """);
  }

  public void testOutsideAnything() {
    doTestNavBarModel("""
      label
      macroName macro
        endm
        <caret>
      """);
  }

  public void testLabelOnLabel() {
    doTestNavBarModel("""
      la<caret>bel
      """, "label");
  }

  public void testLabelInsideStatement() {
    doTestNavBarModel("""
      label
        tst.l <caret>d0
      """, "label");
  }

  public void testLabelInsideWhitespace() {
    doTestNavBarModel("""
      label
        <caret>
      """, "label");
  }

  public void testLocalLabelOnLabel() {
    doTestNavBarModel("""
      label
      .local<caret>Label
      """, "label", "localLabel");
  }

  public void testLocalLabelInsideStatement() {
    doTestNavBarModel("""
      label
      .localLabel
        tst.l <caret>d0
      """, "label", "localLabel");
  }

  public void testEquDirectiveOnLabel() {
    doTestNavBarModel("""
      LA<caret>BEL equ 42
      """, "LABEL");
  }

  public void testEquDirectiveOnExpression() {
    doTestNavBarModel("""
      LABEL equ 4<caret>2
      """, "LABEL");
  }

  public void testEquDirectiveAfterDirective() {
    doTestNavBarModel("""
      LABEL equ 42
        <caret>
      """);
  }

  public void testEqualsDirectiveOnLabel() {
    doTestNavBarModel("""
      LA<caret>BEL = 42
      """, "LABEL");
  }

  public void testEqualsDirectiveOnExpression() {
    doTestNavBarModel("""
      LABEL = 4<caret>2
      """, "LABEL");
  }

  public void testSoDirectiveOnDirective() {
    doTestNavBarModel("""
      LABEL so<caret> 42
      """, "LABEL");
  }

  public void testFoDirectiveOnDirective() {
    doTestNavBarModel("""
      LABEL fo<caret> 42
      """, "LABEL");
  }

  public void testMacroDirectiveOnDirective() {
    doTestNavBarModel("""
      macroName macro<caret>
        endm
      """, "macroName");
  }

  public void testMacroDirectiveOnLabel() {
    doTestNavBarModel("""
      macro<caret>Name macro
        endm
      """, "macroName");
  }

  public void testMacroDirectiveOnLabelAfterMacroKeyword() {
    doTestNavBarModel("""
        macro macro<caret>Name
        endm
      """, "macroName");
  }

  public void testMacroDirectiveInsideStatement() {
    doTestNavBarModel("""
      macroName macro
        tst.l <caret>d0
        endm
      """, "macroName");
  }

  public void testMacroDirectiveNestedLocalLabel() {
    doTestNavBarModel("""
      macroName macro
      label
      .localLabel
        tst.l <caret>d0
        endm
      """, "macroName", "label", "localLabel");
  }

  public void testEqurOnDirective() {
    doTestNavBarModel("""
      label equ<caret>r d5
      """, "label");
  }

  public void testRegOnDirective() {
    doTestNavBarModel("""
      label reg<caret> d0-d7
      """, "label");
  }

  public void testSetOnDirective() {
    doTestNavBarModel("""
      label set<caret> 42
      """, "label");
  }

  private void doTestNavBarModel(String text, String... expectedItems) {
    myFixture.configureByText(M68kFileType.INSTANCE, text);
    DataContext dataContext = ((EditorEx) myFixture.getEditor()).getDataContext();
    List<String> items = contextNavBarPathStrings(dataContext);
    assertOrderedEquals(items, ArrayUtil.mergeArrays(new String[]{"src", "aaa.s"}, expectedItems));
  }
}
