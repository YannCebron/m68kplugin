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

/**
 * @see M68kMacroParameterHighlightErrorFilter
 */
public class M68kMacroHighlightingTest extends BasePlatformTestCase {

  public void testMacroParameterSuppressErrorHighlight() {
    myFixture.configureByText("test.s",
      "macroName MACRO\n" +
        " move.w #\\1,d0\n" +
        " ENDM"
    );
    myFixture.testHighlighting();
  }
}
