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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kSyntaxAnnotatorTest extends BasePlatformTestCase {

  public void testPrivilegedInstructions() {
    myFixture.configureByText("test.s",
      " <info descr=\"Privileged instruction\">rte</info>\n" +
        " <info descr=\"Privileged instruction\">reset</info>\n" +
        " <info descr=\"Privileged instruction\">stop #2</info>");
    myFixture.testHighlighting(false, true, false);
  }

  public void testLabels() {
    myFixture.configureByText("test.s",
      ".<info descr=\"M68K_LOCAL_LABEL\">localLabel</info>\n" +
        "<info descr=\"M68K_LABEL\">globalLabel</info>");
    myFixture.testHighlighting(false, true, false);
  }
}
