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

package com.yanncebron.m68kplugin.editor;

import com.intellij.codeInsight.CodeInsightBundle;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.M68kFileType;

import java.util.List;

public class M68kCommaFlipperTest extends BasePlatformTestCase {

  public void testFlipExpressions() {
   doFlipTest(" dc.b 1,<caret>2",
     " dc.b 2,1");
  }

  public void testFlipInstructionOperands() {
    doFlipTest(" cmp d0,<caret>d1",
      " cmp d1,d0");
  }

  private void doFlipTest(String before, String after) {
    myFixture.configureByText(M68kFileType.INSTANCE, before);
    List<IntentionAction> intentions = myFixture.filterAvailableIntentions(CodeInsightBundle.message("intention.name.flip"));
    IntentionAction flipIntention = assertOneElement(intentions);
    myFixture.launchAction(flipIntention);
    myFixture.checkResult(after);
  }
}
