/*
 * Copyright 2022 The Authors
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

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.CodeInsightFixtureTestCase;
import com.intellij.testFramework.propertyBased.*;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jetCheck.Generator;
import org.jetbrains.jetCheck.PropertyChecker;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("rawtypes")
@TestDataPath("$PROJECT_ROOT/testData/sanity")
public class M68kSanityTest extends CodeInsightFixtureTestCase {

  private static final int ITERATION_COUNT = 1000;

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

  public void testRandomActivity() {
    MadTestingUtil.enableAllInspections(getProject());

    Function<PsiFile, Generator<? extends MadTestingAction>> actions =
      file -> Generator.sampledFrom(
        new InvokeIntention(file, new IntentionPolicy() {
          @Override
          protected boolean shouldSkipByFamilyName(@NotNull String familyName) {
            return "Remove unused label".equals(familyName); // fails for '.local <instruction>'
          }
        }),
        new InvokeCompletion(file, new MyCompletionPolicy()),
        new DeleteRange(file),
        new ResolveAllReferences(file));
    PropertyChecker.customized()
      .withIterationCount(ITERATION_COUNT)
      .checkScenarios(actionsOnAsmFiles(actions));
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


  private static class MyCompletionPolicy extends CompletionPolicy {

    @Override
    public String getPossibleSelectionCharacters() {
      return "\n\t\r ";
    }

    @Override
    public boolean areDuplicatesOk(@NotNull LookupElement item1, @NotNull LookupElement item2) {
      return item1.getPsiElement() instanceof M68kLabelBase && item2.getPsiElement() instanceof M68kLabelBase;
    }

    @Override
    protected boolean shouldSuggestNonReferenceLeafText(@NotNull PsiElement leaf) {
      return false;
    }
  }
}
