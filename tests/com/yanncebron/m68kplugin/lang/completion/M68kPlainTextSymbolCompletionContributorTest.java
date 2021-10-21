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

package com.yanncebron.m68kplugin.lang.completion;

import com.intellij.codeInsight.completion.PlainTextSymbolCompletionContributor;
import com.intellij.codeInsight.completion.PlainTextSymbolCompletionContributorEP;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import icons.M68kIcons;
import com.yanncebron.m68kplugin.lang.M68kLanguage;

import java.util.Collection;

import static com.yanncebron.m68kplugin.lang.M68kLookupElementTestUtil.assertLookupIcon;
import static com.yanncebron.m68kplugin.lang.M68kLookupElementTestUtil.findLookupElement;

public class M68kPlainTextSymbolCompletionContributorTest extends BasePlatformTestCase {

  public void testVariants() {
    final PsiFile psiFile = myFixture.configureByText("test.s",
      "label\n" +
        "macroLabel macro\n" +
        " endm\n");

    PlainTextSymbolCompletionContributor contributor = PlainTextSymbolCompletionContributorEP.forLanguage(M68kLanguage.INSTANCE);
    assertNotNull(contributor);

    Collection<LookupElement> options = contributor.getLookupElements(psiFile, 0, "");
    assertNotEmpty(options);
    final LookupElement[] lookupElements = options.toArray(new LookupElement[0]);
    
    final LookupElement label = findLookupElement(lookupElements, "label", -1);
    final LookupElementPresentation labelPresentation = LookupElementPresentation.renderElement(label);
    assertEquals("test.s", labelPresentation.getTypeText());
    assertTrue(labelPresentation.isTypeGrayed());
    assertLookupIcon(labelPresentation, M68kIcons.LABEL_GLOBAL);

    final LookupElement macroLabel = findLookupElement(lookupElements, "macroLabel", -1);
    final LookupElementPresentation macroLabelPresentation = LookupElementPresentation.renderElement(macroLabel);
    assertLookupIcon(macroLabelPresentation, M68kIcons.LABEL_MACRO);
  }
}
