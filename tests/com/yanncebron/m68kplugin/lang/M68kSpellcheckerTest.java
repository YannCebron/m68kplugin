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

import com.intellij.psi.tree.IElementType;
import com.intellij.spellchecker.inspections.SpellCheckingInspection;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;

@TestDataPath("$PROJECT_ROOT/testData/spellchecker")
public class M68kSpellcheckerTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new SpellCheckingInspection());
  }

  @Override
  protected String getTestDataPath() {
    return "testData/spellchecker";
  }

  public void testLabel() {
    doTest();
  }

  public void testComment() {
    doTest();
  }

  public void testStringExpression() {
    doTest();
  }

  public void testAllMnemonics() {
    for (IElementType elementType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      doTestElementType(elementType);
    }
  }

  public void testAllDirectives() {
    for (IElementType elementType : M68kTokenGroups.DIRECTIVES.getTypes()) {
      doTestElementType(elementType);
    }
  }

  public void testAllConditionalDirectives() {
    for (IElementType elementType : M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES.getTypes()) {
      doTestElementType(elementType);
    }
  }

  private void doTestElementType(IElementType element) {
    myFixture.configureByText("a.s", "; " + element.toString());
    myFixture.testHighlighting(false, false, true);
  }

  private void doTest() {
    myFixture.testHighlighting(false, false, true, getTestName(true) + ".s");
  }

}
