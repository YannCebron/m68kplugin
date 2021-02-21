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

package com.yanncebron.m68kplugin.lang.resolve;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.inspections.M68kUnresolvedLabelReferenceInspection;
import com.yanncebron.m68kplugin.lang.M68kIcons;
import org.jetbrains.annotations.NotNull;

@TestDataPath("$PROJECT_ROOT/testData/resolve/macro")
public class M68kMacroResolveTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/resolve/macro";
  }

  public void testHighlightResolvingInSingleFile() {
    myFixture.enableInspections(new M68kUnresolvedLabelReferenceInspection());
    myFixture.testHighlighting("macroHighlightResolvingInSingleFile.s");
  }

  public void testCompletionVariantsInSingleFile() {
    myFixture.testCompletionVariants("macroCompletionVariantsInSingleFile.s",
      "macro1", "macro2");
  }

  public void testHighlightResolveInMultipleFiles() {
    myFixture.enableInspections(new M68kUnresolvedLabelReferenceInspection());
    myFixture.testHighlighting("macroHighlightResolvingInMultipleFiles.s", "macroHighlightResolvingInMultipleFiles_other.s");
  }

  public void testCompletionVariantsInMultipleFiles() {
    myFixture.copyFileToProject("macroHighlightResolvingInMultipleFiles_other.s");
    myFixture.testCompletionVariants("macroCompletionVariantsInMultipleFiles.s",
      "macro1", "macro2", "otherMacro", "yetAnotherMacro");

    final LookupElement myMacro = findLookupElement("macro1");
    final LookupElementPresentation myPresentation = LookupElementPresentation.renderElement(myMacro);
    assertTrue(myPresentation.isItemTextBold());
    M68kLabelResolveTest.assertLookupIcon(myPresentation, M68kIcons.LABEL_MACRO);
    assertEmpty(myPresentation.getTypeText());
    M68kLabelResolveTest.assertPrioritizedLookupElement(myMacro, 30.0);

    final LookupElement otherLabel = findLookupElement("otherMacro");
    final LookupElementPresentation otherLabelPresentation = LookupElementPresentation.renderElement(otherLabel);
    assertFalse(otherLabelPresentation.isItemTextBold());
    M68kLabelResolveTest.assertLookupIcon(myPresentation, M68kIcons.LABEL_MACRO);
    assertEquals("macroHighlightResolvingInMultipleFiles_other.s", otherLabelPresentation.getTypeText());
  }

  public void testRenameMacrocall() {
    myFixture.testRename("macroRename_before.s", "macroRename_after.s", "macroNewName");
  }

  @NotNull
  private LookupElement findLookupElement(String lookupString) {
    final LookupElement[] lookupElements = myFixture.getLookupElements();
    assertNotNull(lookupElements);
    final LookupElement lookupElement = ContainerUtil.find(lookupElements,
      element -> element.getLookupString().equals(lookupString));
    assertNotNull(lookupString, lookupElement);
    return lookupElement;
  }

}
