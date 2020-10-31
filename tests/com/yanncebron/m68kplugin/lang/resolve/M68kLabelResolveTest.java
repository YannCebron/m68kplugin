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

package com.yanncebron.m68kplugin.lang.resolve;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.inspections.M68kUnresolvedLabelReferenceInspection;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import org.jetbrains.annotations.NotNull;

@TestDataPath("$PROJECT_ROOT/testData/resolve/label")
public class M68kLabelResolveTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/resolve/label";
  }

  public void testHighlightResolvingInSingleFile() {
    myFixture.enableInspections(new M68kUnresolvedLabelReferenceInspection());
    myFixture.testHighlighting("highlightResolvingInSingleFile.s");
  }

  public void testCompletionVariantsInSingleFile() {
    myFixture.testCompletionVariants("completionVariantsInSingleFile.s",
      "anotherTopLevelLabel", ".localLabel2", "topLevelLabel", ".localLabel");

    final LookupElement anotherTopLevelLabel = findLookupElement("anotherTopLevelLabel");
    final LookupElementPresentation anotherTopLevelPresentation = LookupElementPresentation.renderElement(anotherTopLevelLabel);
    assertEquals(AllIcons.Nodes.Method, anotherTopLevelPresentation.getIcon());

    final LookupElement localLabelLookupElement = findLookupElement(".localLabel");
    final PrioritizedLookupElement<?> as = localLabelLookupElement.as(PrioritizedLookupElement.CLASS_CONDITION_KEY);
    assertNotNull(as);
    assertEquals(50.0, as.getPriority());
  }

  public void testResolveLocalLabelInCorrectScope() {
    final PsiReference reference = myFixture.getReferenceAtCaretPositionWithAssertion("resolveLocalLabelInCorrectScope.s");
    assertEquals(".localLabel", reference.getCanonicalText());

    final M68kLocalLabel resolvedLocalLabel = assertInstanceOf(reference.resolve(), M68kLocalLabel.class);
    assertEquals(15, resolvedLocalLabel.getTextOffset());
  }

  @NotNull
  private LookupElement findLookupElement(String lookupString) {
    final LookupElement[] lookupElements = myFixture.getLookupElements();
    final LookupElement lookupElement = ContainerUtil.find(lookupElements,
      element -> element.getLookupString().equals(lookupString));
    assertNotNull(lookupString, lookupElement);
    return lookupElement;
  }
}
