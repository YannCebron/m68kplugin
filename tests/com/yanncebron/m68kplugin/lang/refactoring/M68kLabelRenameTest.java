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

package com.yanncebron.m68kplugin.lang.refactoring;

import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

@TestDataPath("$PROJECT_ROOT/testData/refactoring/label")
public class M68kLabelRenameTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/refactoring/label";
  }

  public void testSingleFile() {
    myFixture.testRenameUsingHandler("singleFile.s", "singleFile_after.s", "newName");
  }

  public void testSingleFileColon() {
    myFixture.testRenameUsingHandler("singleFileColon.s", "singleFileColon_after.s", "newName");
  }

  public void testMultipleFiles() {
    myFixture.testRenameUsingHandler("singleFile.s", "singleFile_after.s", "newName", "multipleFiles.s");
    myFixture.checkResultByFile("multipleFiles.s", "multipleFiles_after.s", true);
  }

  public void testLocal() {
    myFixture.testRenameUsingHandler("local.s", "local_after.s", "newName");
  }

  public void testLocalColon() {
    myFixture.testRenameUsingHandler("localColon.s", "localColon_after.s", "newName");
  }

  public void testLocalDollar() {
    myFixture.testRenameUsingHandler("localDollar.s", "localDollar_after.s", "newName");
  }

  public void testLocalDollarColon() {
    myFixture.testRenameUsingHandler("localDollarColon.s", "localDollarColon_after.s", "newName");
  }

}
