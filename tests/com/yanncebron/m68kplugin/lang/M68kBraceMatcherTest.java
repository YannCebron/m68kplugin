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

package com.yanncebron.m68kplugin.lang;

import com.intellij.codeInsight.highlighting.BraceMatchingUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kBraceMatcherTest extends BasePlatformTestCase {

  public void testParentheses() {
    doTest(" dc.l <caret>(42)", false);
  }

  public void testMacroEndm() {
    doTest("macroName <caret>macro\n" +
      " endm");
  }

  public void testInlineEInline() {
    doTest(" <caret>inline\n" +
      " einline");
  }

  public void testRemErem() {
    doTest(" <caret>rem\n" +
      " erem");
  }

  public void testReptEndr() {
    doTest(" <caret>rept 5\n" +
      " endr");
  }

  private void doTest(String source) {
    doTest(source, true);
  }

  private void doTest(String source, boolean structural) {
    myFixture.configureByText(M68kFileType.INSTANCE, source);
    final Editor editor = myFixture.getEditor();
    final HighlighterIterator iterator = editor.getHighlighter().createIterator(editor.getCaretModel().getOffset());

    boolean isMatched = BraceMatchingUtil.matchBrace(
      editor.getDocument().getCharsSequence(),
      myFixture.getFile().getFileType(), iterator,
      true);
    assertTrue(source, isMatched);

    final boolean isStructural = BraceMatchingUtil.isStructuralBraceToken(
      myFixture.getFile().getFileType(), iterator, myFixture.getEditor().getDocument().getText());
    assertEquals(source, structural, isStructural);
  }
}
