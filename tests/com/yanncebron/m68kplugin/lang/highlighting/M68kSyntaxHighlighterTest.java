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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import org.jetbrains.annotations.NonNls;

public class M68kSyntaxHighlighterTest extends BasePlatformTestCase {

  public void testStringWithEscapeSequences() {
    doTest(" dc.b \"string \\n \\\\ \\r \\n \\t \\0 \\1 \\9 \\X\"",
      """
        WHITE_SPACE (' ') -\s
        dc ('dc') - M68K_DIRECTIVE
        .b ('.b') - M68K_DATA_SIZES
        WHITE_SPACE (' ') -\s
        string ('"string ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\n') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\\\') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\r') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\n') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\t') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\0') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\1') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        VALID_STRING_ESCAPE_TOKEN ('\\9') - M68K_VALID_STRING_ESCAPE
        string (' ') - M68K_STRING
        INVALID_CHARACTER_ESCAPE_TOKEN ('\\X') - M68K_INVALID_STRING_ESCAPE
        string ('"') - M68K_STRING
        """);
  }

  protected void doTest(@NonNls String text, @NonNls String expected) {
    myFixture.configureByText(M68kFileType.INSTANCE, text);

    EditorHighlighter highlighter = myFixture.getEditor().getHighlighter();
    final HighlighterIterator iterator = highlighter.createIterator(0);

    final StringBuilder result = new StringBuilder();
    do {
      final IElementType tokenType = iterator.getTokenType();

      final String tokenText = getTokenText(iterator);
      final String tokenTypeName = tokenType.toString();
      final String textAttributeKeyNames = StringUtil.join(iterator.getTextAttributesKeys(), TextAttributesKey::getExternalName, "/");
      final String line = tokenTypeName + " ('" + tokenText + "') - " + textAttributeKeyNames + "\n";
      result.append(line);

      if (iterator.getEnd() >= myFixture.getFile().getTextLength()) {
        break;
      }
      iterator.advance();
    } while (true);

    assertSameLines(expected, result.toString().trim());
  }

  private String getTokenText(HighlighterIterator iterator) {
    String text = myFixture.getFile().getText().subSequence(iterator.getStart(), iterator.getEnd()).toString();
    return StringUtil.replace(text, "\n", "\\n");
  }
}
