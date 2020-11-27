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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.testFramework.LightPlatformTestCase;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.util.IncorrectOperationException;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kLanguage;

public abstract class M68kPsiTestCase extends LightPlatformTestCase {

  protected M68kPsiElement doParse(final String text) {
    return doParse(text, false);
  }

  /**
   * workaround for {@code eq*} directives including `label`
   */
  protected M68kPsiElement doParse(final String text, final boolean withLabel) {
    final M68kFile m68kFile = createFile(text);

    final PsiElement firstChild = m68kFile.getFirstChild();
    if (withLabel) {
      return assertInstanceOf(firstChild, M68kPsiElement.class);
    }

    assertNotNull(firstChild);
    final PsiElement sibling = firstChild.getNextSibling();
    return assertInstanceOf(sibling, M68kPsiElement.class);
  }

  private M68kFile createFile(final String text) throws IncorrectOperationException {
    final PsiFile psiFile = PsiFileFactory.getInstance(getProject())
      .createFileFromText("test.s", M68kLanguage.INSTANCE, text);

    final String testName = getTestName(false);
    if (!testName.contains("Missing") && !testName.contains("Wrong")) {
      PsiTestUtil.checkErrorElements(psiFile);
    }

    return assertInstanceOf(psiFile, M68kFile.class);
  }

}
