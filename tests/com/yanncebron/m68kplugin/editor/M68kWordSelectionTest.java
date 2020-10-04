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

import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

@TestDataPath("$PROJECT_ROOT/testData/editor/wordSelection")
public class M68kWordSelectionTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/editor/wordSelection";
  }

  public void testLabelBlocksInstruction() {
    doExpandTest(3);
  }

  public void testLabelBlocksAtStartLabel() {
    doExpandTest(4);
  }

  public void testLocalLabelBlocksInstruction() {
    doExpandTest(3);
  }

  private void doExpandTest(int times) {
    myFixture.configureByFile(getTestName(true) + ".s");
    for (int i = 0; i < times; i++) {
      myFixture.performEditorAction(IdeActions.ACTION_EDITOR_SELECT_WORD_AT_CARET);
    }
    myFixture.checkResultByFile(getTestName(true) + "_after.s");
  }
}
