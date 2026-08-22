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

package com.yanncebron.m68kplugin.compiler;

import com.intellij.execution.filters.FileHyperlinkInfo;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kRgAsmLineInLocationFilterTest extends BasePlatformTestCase {

  public void testNavigateToFile() {
    final PsiFile testFile = myFixture.addFileToProject("test.s", """
      line 1
      line2""");

    //                                      0123456789012345678901234567
    final Filter.Result result = getResult("/path/test.s:2:19: warning: some text");
    final Filter.ResultItem resultItem = assertOneElement(result.getResultItems());
    assertEquals(0, resultItem.getHighlightStartOffset());
    assertEquals(14, resultItem.getHighlightEndOffset());

    final FileHyperlinkInfo fileHyperlinkInfo = assertInstanceOf(resultItem.getHyperlinkInfo(), FileHyperlinkInfo.class);
    final OpenFileDescriptor openFileDescriptor = fileHyperlinkInfo.getDescriptor();
    assertNotNull(openFileDescriptor);
    assertEquals(testFile.getVirtualFile(), openFileDescriptor.getFile());
    assertEquals(1, openFileDescriptor.getLine());
  }

  private Filter.Result getResult(String text) {
    Filter filter = new M68kRgAsmLineInLocationFilter(getProject());
    Filter.Result result = filter.applyFilter(text, text.length());
    assertNotNull(result);
    return result;
  }
}
