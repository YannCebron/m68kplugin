/*
 * Copyright 2021 The Authors
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
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kUnusedLabelInspectionTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new M68kUnusedLabelInspection());
  }

  public void testHighlightingLocalLabel() {
    myFixture.configureByText("a.s",
      ".used\n" +
        " bra .used\n" +
        "<warning descr=\"Unused local label 'unused'\">.unused</warning>\n" +
        "<warning descr=\"Unused local label 'unusedWithColon'\">.unusedWithColon:</warning>\n");
    myFixture.testHighlighting();
  }

  public void testFixRemoveUnusedLocalLabel() {
    myFixture.configureByText("a.s",
      "  nop\n" +
        "<warning descr=\"Unused local label 'unused'\">.un<caret>used</warning>\n" +
        "  nop\n");
    myFixture.testHighlighting();
    final IntentionAction intention = myFixture.findSingleIntention("Remove unused label 'unused'");
    myFixture.launchAction(intention);
    myFixture.checkResult(
      "  nop\n" +
        "  nop\n");
  }

}
