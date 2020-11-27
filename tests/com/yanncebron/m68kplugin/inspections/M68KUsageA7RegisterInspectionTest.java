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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

@TestDataPath("$PROJECT_ROOT/testData/inspections/usageA7Register")
public class M68KUsageA7RegisterInspectionTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/inspections/usageA7Register";
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new M68kUsageA7RegisterInspection());
  }

  public void testHighlighting() {
    myFixture.testHighlighting("usageA7Register.s");
  }

  public void testReplaceWithSPFix() {
    myFixture.configureByText("a.s",
      "  jmp 4(pc,<warning descr=\"Usage of A7 register\">a<caret>7</warning>.l)");
    myFixture.testHighlighting();
    final List<IntentionAction> intentions = myFixture.filterAvailableIntentions("Replace with SP");
    final IntentionAction intention = assertOneElement(intentions);
    myFixture.launchAction(intention);
    myFixture.checkResult("  jmp 4(pc,sp.l)");
  }
}
