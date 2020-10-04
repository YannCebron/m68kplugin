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

package com.yanncebron.m68kplugin.lang.findUsages;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.usages.impl.rules.UsageType;

public class M68kUsageTypeProviderTest extends BasePlatformTestCase {

  public void testCodeUsage() {
    myFixture.configureByText("a.s",
      "bra lab<el>");
    assertUsageType(null);
  }

  public void testDirectiveUsage() {
    myFixture.configureByText("a.s",
      " JUMPERR lab<caret>el");
    assertUsageType(M68kUsageTypeProvider.DIRECTIVE);
  }

  public void testConditionalAssemblyUsage() {
    myFixture.configureByText("a.s",
      " IFND lab<caret>el");
    assertUsageType(M68kUsageTypeProvider.CONDITIONAL_ASSEMBLY);
  }


  private void assertUsageType(UsageType expected) {
    PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());
    assertNotNull(element);

    UsageType usageType = new M68kUsageTypeProvider().getUsageType(element);
    assertEquals(DebugUtil.psiToString(element, false), expected, usageType);
  }

}
