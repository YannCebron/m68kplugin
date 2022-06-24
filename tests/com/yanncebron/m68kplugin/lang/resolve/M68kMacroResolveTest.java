/*
 * Copyright 2022 The Authors
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
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveResult;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.inspections.M68kUnresolvedMacroReferenceInspection;
import com.yanncebron.m68kplugin.lang.M68kLookupElementTestUtil;
import icons.M68kIcons;

import static com.yanncebron.m68kplugin.lang.M68kLookupElementTestUtil.assertLookupIcon;
import static com.yanncebron.m68kplugin.lang.M68kLookupElementTestUtil.findLookupElement;
import static com.yanncebron.m68kplugin.lang.resolve.M68kLabelResolveTest.assertResolveResultPsiElement;

@TestDataPath("$PROJECT_ROOT/testData/resolve/macro")
public class M68kMacroResolveTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/resolve/macro";
  }

  @Override
  protected boolean isIconRequired() {
    return true;
  }

  public void testHighlightResolvingInSingleFile() {
    myFixture.enableInspections(new M68kUnresolvedMacroReferenceInspection());
    myFixture.testHighlighting("macroHighlightResolvingInSingleFile.s");
  }

  public void testCompletionVariantsInSingleFile() {
    myFixture.testCompletionVariants("macroCompletionVariantsInSingleFile.s",
      "macro1", "macro2");

    final LookupElement firstMacro1 = findLookupElement(myFixture, "macro1", 0);
    final LookupElementPresentation firstMacro1Presentation = LookupElementPresentation.renderElement(firstMacro1);
    assertLookupIcon(firstMacro1Presentation, M68kIcons.LABEL_MACRO);
    final LookupElement secondMacro1 = findLookupElement(myFixture, "macro1", 21);
    final LookupElementPresentation secondMacro1Presentation = LookupElementPresentation.renderElement(secondMacro1);
    assertLookupIcon(secondMacro1Presentation, M68kIcons.LABEL_MACRO);
  }

  public void testHighlightResolveInMultipleFiles() {
    final String[] testDataPaths = {"macroHighlightResolvingInMultipleFiles.s", "macroHighlightResolvingInMultipleFiles_other.s"};
    myFixture.enableInspections(new M68kUnresolvedMacroReferenceInspection());
    myFixture.testHighlighting(testDataPaths);

    final PsiReference referenceAtCaretPositionWithAssertion = myFixture.getReferenceAtCaretPositionWithAssertion(testDataPaths);
    final PsiPolyVariantReference polyVariantReference = assertInstanceOf(referenceAtCaretPositionWithAssertion, PsiPolyVariantReference.class);
    final ResolveResult[] resolveResults = polyVariantReference.multiResolve(false);
    assertSize(2, resolveResults);

    assertResolveResultPsiElement(resolveResults[0], "yetAnotherMacro", "macroHighlightResolvingInMultipleFiles_other.s", 846);
    assertResolveResultPsiElement(resolveResults[1], "yetAnotherMacro", "macroHighlightResolvingInMultipleFiles_other.s", 876);
  }

  public void testCompletionVariantsInMultipleFiles() {
    myFixture.copyFileToProject("macroHighlightResolvingInMultipleFiles_other.s");
    myFixture.testCompletionVariants("macroCompletionVariantsInMultipleFiles.s",
      "macro1", "macro2", "otherMacro", "yetAnotherMacro");

    final LookupElement myMacro = findLookupElement(myFixture, "macro1");
    final LookupElementPresentation myMacroPresentation = LookupElementPresentation.renderElement(myMacro);
    assertTrue(myMacroPresentation.isItemTextBold());
    assertLookupIcon(myMacroPresentation, M68kIcons.LABEL_MACRO);
    assertEmpty(myMacroPresentation.getTypeText());
    M68kLookupElementTestUtil.assertPrioritizedLookupElement(myMacro, 30.0);

    final LookupElement otherMacro = findLookupElement(myFixture, "otherMacro");
    final LookupElementPresentation otherMacroPresentation = LookupElementPresentation.renderElement(otherMacro);
    assertFalse(otherMacroPresentation.isItemTextBold());
    assertLookupIcon(otherMacroPresentation, M68kIcons.LABEL_MACRO);
    assertEquals("macroHighlightResolvingInMultipleFiles_other.s", otherMacroPresentation.getTypeText());

    final LookupElement firstYetAnotherMacro = findLookupElement(myFixture, "yetAnotherMacro", 846);
    final LookupElementPresentation firstYetAnotherMacroPresentation = LookupElementPresentation.renderElement(firstYetAnotherMacro);
    assertLookupIcon(firstYetAnotherMacroPresentation, M68kIcons.LABEL_MACRO);
    final LookupElement secondYetAnotherMacro = findLookupElement(myFixture, "yetAnotherMacro", 876);
    final LookupElementPresentation secondYetAnotherMacroPresentation = LookupElementPresentation.renderElement(secondYetAnotherMacro);
    assertLookupIcon(secondYetAnotherMacroPresentation, M68kIcons.LABEL_MACRO);
  }

  public void testRenameMacrocall() {
    myFixture.testRename("macroRename_before.s", "macroRename_after.s", "macroNewName");
  }

}
