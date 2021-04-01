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

package com.yanncebron.m68kplugin.lang;

import com.intellij.codeInsight.daemon.impl.IdentifierHighlighterPassFactory;
import com.intellij.codeInsight.editorActions.CodeBlockEndAction;
import com.intellij.codeInsight.editorActions.CodeBlockStartAction;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.testFramework.EditorTestUtil;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.Collections;

@TestDataPath("$PROJECT_ROOT/testData/editor/codeBlock/conditionalAssembly")
public class M68kConditionalAssemblyCodeBlockSupportHandlerTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/editor/codeBlock/conditionalAssembly";
  }

  public void testIfEndc() {
    doMatchingRangesTest();
  }

  public void testIfEndif() {
    doMatchingRangesTest();
  }

  public void testIfElseEndc() {
    doMatchingRangesTest();
  }

  public void testIfElseifEndc() {
    doMatchingRangesTest();
  }

  public void testMultipleConditions() {
    doMatchingRangesTest();
  }

  private void doMatchingRangesTest() {
    myFixture.setReadEditorMarkupModel(true);
    IdentifierHighlighterPassFactory.doWithHighlightingEnabled(/*getProject(), getTestRootDisposable(),*/ () -> {
      myFixture.configureByFile(getTestName(false) + "." + M68kFileType.INSTANCE.getDefaultExtension());
      EditorTestUtil.checkEditorHighlighting(myFixture,
        FileUtil.join(getTestDataPath(), getTestName(false) + ".txt"),
        Collections.singleton("MATCHED_BRACE_ATTRIBUTES"));
    });
  }

  public void testNavigationIfEndc() {
    doNavigationTest(false);
  }

  public void testNavigationOutsideIfEndc() {
    doNavigationTest(false);
  }

  public void testNavigationWhitespaceIfEndc() {
    doNavigationTest(false);
  }

  public void testNavigationIfElseifEndc() {
    doNavigationTest(false);
  }

  public void testNavigationIfElseEndc() {
    doNavigationTest(true);
  }

  public void testNavigationMultipleConditionsMissingIf() {
    doNavigationTest(true);
  }

  private void doNavigationTest(boolean gotoStart) {
    String name = getTestName(false);
    String startFilename = name + "." + M68kFileType.INSTANCE.getDefaultExtension();
    String endFilename = name + "_after." + M68kFileType.INSTANCE.getDefaultExtension();
    myFixture.configureByFile(startFilename);
    if (gotoStart) {
      performStartBlockAction();
    } else {
      performEndBlockAction();
    }
    myFixture.checkResultByFile(endFilename);
  }

  private void performEndBlockAction() {
    EditorTestUtil.executeAction(myFixture.getEditor(), true, new CodeBlockEndAction());
  }

  private void performStartBlockAction() {
    EditorTestUtil.executeAction(myFixture.getEditor(), true, new CodeBlockStartAction());
  }
}
