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

package com.yanncebron.m68kplugin;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.CodeInsightFixtureTestCase;
import com.intellij.testFramework.propertyBased.CheckHighlighterConsistency;
import com.intellij.testFramework.propertyBased.MadTestingAction;
import com.intellij.testFramework.propertyBased.MadTestingUtil;
import org.jetbrains.jetCheck.Generator;
import org.jetbrains.jetCheck.PropertyChecker;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("rawtypes")
public class M68kSanityTest extends CodeInsightFixtureTestCase {

  private static final int ITERATION_COUNT = 100;

  private static final boolean ENABLED = false;

  @Override
  protected boolean shouldRunTest() {
    return ENABLED && super.shouldRunTest();
  }

  public void testIncrementalHighlighterUpdate() {
    PropertyChecker.customized()
      .withIterationCount(ITERATION_COUNT)
      .checkScenarios(actionsOnAsmFiles(CheckHighlighterConsistency.randomEditsWithHighlighterChecks));
  }

  public void testRandomEditsWithParseChecks() {
    PropertyChecker.customized()
      .withIterationCount(ITERATION_COUNT)
      .checkScenarios(actionsOnAsmFiles(MadTestingUtil::randomEditsWithReparseChecks));
  }

  private Supplier<MadTestingAction> actionsOnAsmFiles(Function<PsiFile, Generator<? extends MadTestingAction>> fileActions) {
    return MadTestingUtil.actionsOnFileContents(myFixture,
      "testData/sanity",
      f -> {
        final boolean matches = f.getName().endsWith(".s");
        if (matches) {
          System.out.println(f.getName());
        }
        return matches;
      }, fileActions);
  }

}
