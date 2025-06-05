/*
 * Copyright 2025 The Authors
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

package com.yanncebron.m68kplugin.lang.findUsages;

import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.containers.ContainerUtil;

import java.util.Comparator;
import java.util.List;

@TestDataPath("$PROJECT_ROOT/testData/editor/highlightUsages")
public class M68kHighlightUsagesTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/editor/highlightUsages";
  }

  public void testHighlightLabelUsages() {
    doHighlightUsagesTest("highlightLabelUsages.s",
      "6:11 label",
      "21:26 label",
      "38:43 label",
      "53:58 label",
      "59:64 label"
    );
  }

  public void testHighlightLocalLabelUsages() {
    doHighlightUsagesTest("highlightLocalLabelUsages.s",
      "9:14 local",
      "24:30 .local",
      "37:43 .local");
  }

  public void testHighlightMacroUsages() {
    doHighlightUsagesTest("highlightMacroUsages.s",
      "15:22 myMacro",
      "39:46 myMacro");
  }

  private void doHighlightUsagesTest(String filePath, String... expectedHighlights) {
    final RangeHighlighter[] allHighlighters = myFixture.testHighlightUsages(filePath);
    ContainerUtil.sort(allHighlighters, Comparator.comparingInt(RangeMarker::getStartOffset));

    final List<String> highlightData = ContainerUtil.map(allHighlighters, rangeHighlighter -> {
      final int startOffset = rangeHighlighter.getStartOffset();
      final int endOffset = rangeHighlighter.getEndOffset();
      return startOffset + ":" + endOffset +
        " " + myFixture.getFile().getText().substring(startOffset, endOffset);
    });

    assertOrderedEquals(highlightData, expectedHighlights);
  }
}
