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

package com.yanncebron.m68kplugin.lang;

import com.intellij.grazie.spellcheck.GrazieSpellCheckingInspection;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;

public class M68kSpellcheckerTest extends BasePlatformTestCase {

  public void testLabel() {
    doTest("""
      correctLabel
      .correctLocalLabel
      
      <TYPO descr="Typo: In word 'abcdegh'">abcdegh</TYPO>
      .<TYPO descr="Typo: In word 'abcdegh'">abcdegh</TYPO>
      """);
  }

  public void testComment() {
    doTest("""
      ; A comment with <TYPO descr="Typo: In word 'abcdegh'">abcdegh</TYPO>
      
      ; but we know about some bundled words: btst bitplane bltbmod BLTBMOD scroller pretracker
      """);
  }

  public void testStringExpression() {
    doTest(" dc.b 'A String with <TYPO descr=\"Typo: In word 'abcdegh'\">abcdegh</TYPO>',0");
  }

  public void testAllMnemonics() {
    doTestElementTypes(M68kTokenGroups.INSTRUCTIONS.getTypes());
  }

  public void testAllDirectives() {
    doTestElementTypes(M68kTokenGroups.DIRECTIVES.getTypes());
  }

  public void testAllConditionalDirectives() {
    doTestElementTypes(M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES.getTypes());
  }

  private void doTestElementTypes(IElementType[] elements) {
    StringBuilder sb = new StringBuilder();
    for (IElementType element : elements) {
      sb.append("; ").append(element);
      sb.append("\n");
    }
    doTest(sb.toString());
  }

  private void doTest(String fileText) {
    myFixture.configureByText("a.s", fileText);
    myFixture.enableInspections(new GrazieSpellCheckingInspection());
    myFixture.testHighlighting(false, false, true);
  }

}
