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

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kRegisterListRangeInspectionTest extends BasePlatformTestCase {

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myFixture.enableInspections(new M68kRegisterListRangeInspection());
  }

  public void testRegisterRangeMixedRegisterTypes() {
    final M68kRegisterListRangeInspection inspection = new M68kRegisterListRangeInspection();
    myFixture.enableInspections(inspection);

    doTest(" movem.l d0-a1,(a7)");
    inspection.allowMixedTypes = false;
    doTest(" movem.l <error descr=\"Mixed register types\">d0-a1</error>,(a7)");
  }

  public void testRegisterRangeNotARange() {
    doTest(" movem.l <error descr=\"Not a range\">a0-a0</error>,(a7)");
  }

  public void testRegisterRangeReversedRange() {
    doTest(
      " movem.l d7-a0,(a7)",
      " movem.l <error descr=\"Reversed range\">d7-d6</error>,(a7)");
  }

  public void testRegisterListDuplicatedRegisterSingle() {
    doTest(
      // d0/d0
      " movem.l <error descr=\"Duplicated register D0\">d0</error>/<error descr=\"Duplicated register D0\">d0</error>,(a7)",

      // d0/d0-d1
      " movem.l <error descr=\"Duplicated register D0\">d0</error>/<error descr=\"Duplicated register D0\">d0-d1</error>,(a7)",

      // d0-d1/d1-d2
      " movem.l <error descr=\"Duplicated register D1\">d0-d1</error>/<error descr=\"Duplicated register D1\">d1-d2</error>,(a7)",

      // a0/d0-a1
      "  movem.l <error descr=\"Duplicated register A0\">a0</error>/<error descr=\"Duplicated register A0\">d0-a1</error>,(a7)");
  }

  public void testRegisterListDuplicatedRegisterMultiple() {
    // d0-d1/d0-d3
    doTest(" movem.l <error descr=\"Duplicated register D0\"><error descr=\"Duplicated register D1\">d0-d1</error></error>/<error descr=\"Duplicated register D0\"><error descr=\"Duplicated register D1\">d0-d3</error></error>,(a7)");
  }

  public void testRegisterListDuplicatedRegisterReverseNotHighlighted() {
    doTest(" movem.l <error descr=\"Reversed range\">d1-d0</error>/d0,(a7)");
  }

  private void doTest(String... text) {
    myFixture.configureByText("a.s", StringUtil.join(text, "\n"));
    myFixture.testHighlighting();
  }
}