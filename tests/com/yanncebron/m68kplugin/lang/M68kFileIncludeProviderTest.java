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
    final PsiFile testIncludedFile = myFixture.addFileToProject("test.i", " include 'recursive.i'");
    final PsiFile testFile = myFixture.configureByText("test.s", " include 'test.i'");

    final VirtualFile[] includedFiles = getIncludedFiles(true, true);
    assertSameElements(includedFiles,
      testIncludedFile.getVirtualFile(),
      testRecursiveFile.getVirtualFile(),
      testFile.getVirtualFile());
  }

  public void testIncbinSingleResolvedFile() {
    myFixture.addFileToProject("test.i", "");
    final PsiFile testIncludedFile = myFixture.addFileToProject("test.dat", "");
    myFixture.configureByText("test.s",
      " incbin 'test.dat'\n" +
        " include 'test.i'");

    final VirtualFile[] includedFiles = getIncludedFiles(false, false);
    assertSameElements(includedFiles, testIncludedFile.getVirtualFile());
  }

  private VirtualFile[] getIncludedFiles(boolean compileTimeOnly, boolean recursively) {
    return FileIncludeManager.getManager(getProject())
      .getIncludedFiles(myFixture.getFile().getVirtualFile(), compileTimeOnly, recursively);
  }
}
