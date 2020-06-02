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

import com.intellij.spellchecker.inspections.SpellCheckingInspection;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

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

  private void doTest() {
    myFixture.testHighlighting(false, false, true, getTestName(true) + ".s");
  }

}
