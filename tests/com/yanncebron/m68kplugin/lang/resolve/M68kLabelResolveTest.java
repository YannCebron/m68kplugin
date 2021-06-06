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
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveResult;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.inspections.M68kUnresolvedLabelReferenceInspection;
import com.yanncebron.m68kplugin.lang.M68kIcons;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;

import static com.yanncebron.m68kplugin.lang.M68kLookupElementTestUtil.*;

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
      "topLevelLabel", "anotherTopLevel.Label",
      ".localLabel", ".localLabel2", ".localLabelWithColon",
      "localLabelDollar$", "localLabelDollarWithColon$",
      "setLabel", "equLabel", "equalsLabel");

    final LookupElement topLevelLabel1 = findLookupElement(myFixture, "topLevelLabel", 0);
    final LookupElementPresentation topLevelPresentation1 = LookupElementPresentation.renderElement(topLevelLabel1);
    assertLookupIcon(topLevelPresentation1, M68kIcons.LABEL_GLOBAL);
    final LookupElement topLevelLabel2 = findLookupElement(myFixture, "topLevelLabel", 14);
    final LookupElementPresentation topLevelPresentation2 = LookupElementPresentation.renderElement(topLevelLabel2);
    assertLookupIcon(topLevelPresentation2, M68kIcons.LABEL_GLOBAL);

    final LookupElement anotherTopLevelLabel = findLookupElement(myFixture, "anotherTopLevel.Label");
    final LookupElementPresentation anotherTopLevelPresentation = LookupElementPresentation.renderElement(anotherTopLevelLabel);
    assertLookupIcon(anotherTopLevelPresentation, M68kIcons.LABEL_GLOBAL);
    assertTrue(anotherTopLevelPresentation.isItemTextBold());
    assertEmpty(anotherTopLevelPresentation.getTailText());
    assertEmpty(anotherTopLevelPresentation.getTypeText());
    assertPrioritizedLookupElement(anotherTopLevelLabel, 30.0);

    final LookupElement localLabelLookupElement = findLookupElement(myFixture, ".localLabel");
    final LookupElementPresentation localLabelPresentation = LookupElementPresentation.renderElement(localLabelLookupElement);
    assertLookupIcon(localLabelPresentation, M68kIcons.LABEL_LOCAL);
    assertTrue(localLabelPresentation.isItemTextBold());
    assertEmpty(localLabelPresentation.getTypeText());
    assertPrioritizedLookupElement(localLabelLookupElement, 50.0);

    final LookupElement localLabelDollarLookupElement = findLookupElement(myFixture, "localLabelDollar$");
    final LookupElementPresentation localLabelDollarPresentation = LookupElementPresentation.renderElement(localLabelDollarLookupElement);
    assertLookupIcon(localLabelDollarPresentation, M68kIcons.LABEL_LOCAL);
    assertTrue(localLabelDollarPresentation.isItemTextBold());
    assertEmpty(localLabelDollarPresentation.getTypeText());
    assertPrioritizedLookupElement(localLabelDollarLookupElement, 50.0);

    final LookupElement setLabel = findLookupElement(myFixture, "setLabel");
    final LookupElementPresentation setLabelPresentation = LookupElementPresentation.renderElement(setLabel);
    assertLookupIcon(setLabelPresentation, M68kIcons.LABEL_SET);
    assertTrue(setLabelPresentation.isItemTextBold());
    assertEquals(" 2", setLabelPresentation.getTailText());
    assertEmpty(setLabelPresentation.getTypeText());
    assertPrioritizedLookupElement(setLabel, 30.0);

    final LookupElement equLabel = findLookupElement(myFixture, "equLabel");
    final LookupElementPresentation equLabelPresentation = LookupElementPresentation.renderElement(equLabel);
    assertLookupIcon(equLabelPresentation, M68kIcons.LABEL_EQU);
    assertTrue(equLabelPresentation.isItemTextBold());
    assertEquals(" 42", equLabelPresentation.getTailText());
    assertEmpty(equLabelPresentation.getTypeText());
    assertPrioritizedLookupElement(equLabel, 30.0);

    final LookupElement equalsLabel = findLookupElement(myFixture, "equalsLabel");
    final LookupElementPresentation equalsLabelPresentation = LookupElementPresentation.renderElement(equalsLabel);
    assertLookupIcon(equalsLabelPresentation, M68kIcons.LABEL_EQU);
    assertTrue(equalsLabelPresentation.isItemTextBold());
    assertEquals(" 33", equalsLabelPresentation.getTailText());
    assertEmpty(equalsLabelPresentation.getTypeText());
    assertPrioritizedLookupElement(equalsLabel, 30.0);
  }

  public void testResolveLocalLabelInCorrectScope() {
    final PsiReference reference = myFixture.getReferenceAtCaretPositionWithAssertion("resolveLocalLabelInCorrectScope.s");
    assertEquals(".localLabel", reference.getCanonicalText());

    final M68kLocalLabel resolvedLocalLabel = assertInstanceOf(reference.resolve(), M68kLocalLabel.class);
    assertEquals(15, resolvedLocalLabel.getTextOffset());
  }

  public void testHighlightResolveInMultipleFiles() {
    final String[] testDataPaths = {"highlightResolvingInMultipleFiles.s", "highlightResolvingInMultipleFiles_other.s"};
    myFixture.enableInspections(new M68kUnresolvedLabelReferenceInspection());
    myFixture.testHighlighting(testDataPaths);

    // resolve in current file (2nd declaration in _other.s)
    final PsiReference referenceAtCaretPositionWithAssertion = myFixture.getReferenceAtCaretPositionWithAssertion(testDataPaths);
    final PsiPolyVariantReference polyVariantReference = assertInstanceOf(referenceAtCaretPositionWithAssertion, PsiPolyVariantReference.class);
    final ResolveResult resolveResult = assertOneElement(polyVariantReference.multiResolve(false));
    assertResolveResultPsiElement(resolveResult, "myLabel", "highlightResolvingInMultipleFiles.s", 0);
  }

  public void testCompletionVariantsInMultipleFiles() {
    myFixture.copyFileToProject("highlightResolvingInMultipleFiles_other.s");
    myFixture.testCompletionVariants("completionVariantsInMultipleFiles.s",
      "topLevelLabel", "anotherTopLevelLabel", "otherLabel", "otherLabel2", "myLabel");

    final LookupElement otherLabel = findLookupElement(myFixture, "otherLabel");
    final LookupElementPresentation otherLabelPresentation = LookupElementPresentation.renderElement(otherLabel);
    assertFalse(otherLabelPresentation.isItemTextBold());
    assertEquals("highlightResolvingInMultipleFiles_other.s", otherLabelPresentation.getTypeText());
  }

  static void assertResolveResultPsiElement(ResolveResult resolveResult, String text, String filename, int offset) {
    final PsiElement element = resolveResult.getElement();
    assertNotNull(text, element);
    assertEquals(text, element.getText());
    assertEquals(filename, element.getContainingFile().getName());
    assertEquals(offset, element.getTextOffset());
  }

}
