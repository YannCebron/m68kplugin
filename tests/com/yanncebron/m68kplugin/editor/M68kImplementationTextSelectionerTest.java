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

package com.yanncebron.m68kplugin.editor;

import com.intellij.codeInsight.TargetElementUtil;
import com.intellij.codeInsight.hint.ImplementationViewComponent;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kImplementationTextSelectionerTest extends BasePlatformTestCase {

  public void testMacro() {
    myFixture.configureByText("a.s",
      "macroName macro\n" +
        " move.l d0,d1\n" +
        " endm\n" +
        " macro<caret>Name");
    PsiElement element =
      TargetElementUtil.findTargetElement(myFixture.getEditor(), TargetElementUtil.getInstance().getAllAccepted());
    assertNotNull(element);
    final String implementationText = ImplementationViewComponent.getNewText(element.getNavigationElement());
    assertEquals("macroName macro\n" +
      " move.l d0,d1\n" +
      " endm", implementationText);
  }
}
