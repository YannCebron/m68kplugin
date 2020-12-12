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

package com.yanncebron.m68kplugin.lang;

import com.intellij.codeInsight.highlighting.BraceMatchingUtil;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;

public class M68kBraceMatcherTest extends BasePlatformTestCase {

  public void testParentheses() {
    doTest(" dc.l <caret>(42)", false);
  }

  public void testBrackets() {
    doTest(" dc.l <caret>[42]", false);
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

  public void testConditionalAssembly() {
    doConditionalTest(M68kTokenTypes.IF);
    doConditionalTest(M68kTokenTypes.IFB);
    doConditionalTest(M68kTokenTypes.IFNB);
    doConditionalTest(M68kTokenTypes.IFC);
    doConditionalTest(M68kTokenTypes.IFNC);
    doConditionalTest(M68kTokenTypes.IFD);
    doConditionalTest(M68kTokenTypes.IFND);
    doConditionalTest(M68kTokenTypes.IFEQ);
    doConditionalTest(M68kTokenTypes.IFNE);
    doConditionalTest(M68kTokenTypes.IFGE);
    doConditionalTest(M68kTokenTypes.IFGT);
    doConditionalTest(M68kTokenTypes.IFLE);
    doConditionalTest(M68kTokenTypes.IFLT);
    doConditionalTest(M68kTokenTypes.ELSE);
    doConditionalTest(M68kTokenTypes.ELSEIF);
    doConditionalTest(M68kTokenTypes.IFMACROD);
    doConditionalTest(M68kTokenTypes.IFMACROND);
  }

  private void doConditionalTest(IElementType startElement) {
    doTest(" <caret>" + startElement.toString() + "\n" +
      " endc");

    doTest(" <caret>" + startElement + "\n" +
      " endif");
  }

  private void doTest(String source) {
    doTest(source, true);
  }

  private void doTest(String source, boolean structural) {
    myFixture.configureByText("a.s", source);
    final Editor editor = myFixture.getEditor();
    final EditorHighlighter editorHighlighter = ((EditorEx) editor).getHighlighter();
    final HighlighterIterator iterator = editorHighlighter.createIterator(editor.getCaretModel().getOffset());

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
