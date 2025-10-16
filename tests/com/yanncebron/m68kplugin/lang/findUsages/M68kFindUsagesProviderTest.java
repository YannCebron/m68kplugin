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

package com.yanncebron.m68kplugin.lang.findUsages;

import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kFindUsagesProviderTest extends BasePlatformTestCase {

  private static final FindUsagesProvider INSTANCE = new M68kFindUsagesProvider();

  public void testLabel() {
    doTest("myLabel",
      "label", "myLabel", "myLabel");
  }

  public void testMacro() {
    doTest("""
        myMacro macro
         endm""",
      "macro", "myMacro", "myMacro");
  }

  public void testLocalLabel() {
    doTest(".local<caret>Label",
      "local label", "localLabel", "localLabel");
  }

  private void doTest(String code, String type, String descriptiveName, String nodeText) {
    myFixture.configureByText("a.s", code);
    final PsiElement element = myFixture.getElementAtCaret();

    assertTrue(DebugUtil.psiToString(element, true), INSTANCE.canFindUsagesFor(element));
    assertEquals(type, INSTANCE.getType(element));
    assertEquals(descriptiveName, INSTANCE.getDescriptiveName(element));
    assertEquals(nodeText, INSTANCE.getNodeText(element, false));
  }

}
