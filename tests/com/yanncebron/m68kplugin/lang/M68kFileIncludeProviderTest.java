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

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.include.FileIncludeManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kFileIncludeProviderTest extends BasePlatformTestCase {

  public void testIncludeSingleUnresolvedFile() {
    myFixture.configureByText("test.s", " include 'test.i'");

    final VirtualFile[] includedFiles = getIncludedFiles(true, false);
    assertEmpty(includedFiles);
  }

  public void testIncludeSingleResolvedFile() {
    final PsiFile testIncludedFile = myFixture.addFileToProject("test.i", "");
    myFixture.configureByText("test.s", " include 'test.i'");

    final VirtualFile[] includedFiles = getIncludedFiles(true, false);
    assertSameElements(includedFiles, testIncludedFile.getVirtualFile());
  }

  public void testIncludeSingleResolvedFileRecursively() {
    final PsiFile testRecursiveFile = myFixture.addFileToProject("recursive.i", "");
    final PsiFile testIncludedFile = myFixture.addFileToProject("test.i", " inCLUde 'recursive.i'");
    final PsiFile testFile = myFixture.configureByText("test.s", " include 'test.i'");

    final VirtualFile[] includedFiles = getIncludedFiles(true, true);
    assertSameElements(includedFiles,
      testIncludedFile.getVirtualFile(),
      testRecursiveFile.getVirtualFile(),
      testFile.getVirtualFile());
  }

  public void testIncbinSingleResolvedFile() {
    final PsiFile testIncludeFile = myFixture.addFileToProject("test.i", "");
    final PsiFile testIncbinFile = myFixture.addFileToProject("dir/test.dat", "");
    myFixture.configureByText("test.s",
      " INCBIN 'dir/test.dat'\n" +
        " include 'test.i'");

    final VirtualFile[] includedFiles = getIncludedFiles(false, false);
    assertSameElements(includedFiles, testIncbinFile.getVirtualFile());
    assertDirectiveOffset(testIncbinFile, 1);

    assertDirectiveOffset(testIncludeFile, 24);
  }

  private void assertDirectiveOffset(PsiFile testIncludedFile, int expectedOffset) {
    FileIncludeManager.getManager(getProject()).processIncludingFiles(testIncludedFile, pair -> {
      final int offset = pair.second.offset;
      assertEquals(pair.second.toString(), expectedOffset, offset);
      return false;
    });
  }

  private VirtualFile[] getIncludedFiles(boolean compileTimeOnly, boolean recursively) {
    return FileIncludeManager.getManager(getProject())
      .getIncludedFiles(myFixture.getFile().getVirtualFile(), compileTimeOnly, recursively);
  }
}
