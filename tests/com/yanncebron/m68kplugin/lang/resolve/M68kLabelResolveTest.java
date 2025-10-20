/*
 * Copyright 2025 The Authors
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

import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.codeInsight.daemon.impl.IdentifierHighlighterPassFactory;
import com.intellij.codeInsight.daemon.impl.SeveritiesProvider;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInspection.ex.InspectionProfileImpl;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveResult;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.usages.Usage;
import com.intellij.util.ArrayUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xml.ElementPresentationManager;
import com.yanncebron.m68kplugin.inspections.M68kUnresolvedLabelReferenceInspection;
import com.yanncebron.m68kplugin.lang.psi.M68kBuiltinSymbol;
import com.yanncebron.m68kplugin.lang.psi.M68kBuiltinSymbolPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import icons.M68kIcons;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

import static com.yanncebron.m68kplugin.lang.M68kLookupElementTestUtil.*;

@TestDataPath("$PROJECT_ROOT/testData/resolve/label")
public class M68kLabelResolveTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/resolve/label";
  }

  @Override
  protected boolean isIconRequired() {
    return true;
  }

  private static String[] getCompletionVariants(String... labels) {
    return ArrayUtil.mergeArrays(labels,
      ContainerUtil.map2Array(M68kBuiltinSymbol.values(), String.class, M68kBuiltinSymbol::getName));
  }

  private void setupImplicitMacroConfiguration() {
    M68kUnresolvedLabelReferenceInspection inspection = new M68kUnresolvedLabelReferenceInspection();
    inspection.labelDefiningMacros.add("implicitLabelMacro");
    myFixture.enableInspections(inspection);
  }

  public void testResolveBuiltinSymbol() {
    myFixture.configureByText("a.s",
      "MY_CPU equ __C<caret>PU");
    final ResolveResult result = getSingleResolveResultAtCaret();
    final M68kBuiltinSymbolPsiElement psiElement = assertInstanceOf(result.getElement(), M68kBuiltinSymbolPsiElement.class);
    assertEquals(M68kBuiltinSymbol.__CPU.getDescription() + " (" + M68kBuiltinSymbol.__CPU.getCompiler().getDisplayName() + ")", psiElement.getName());
    assertEquals("Builtin Symbol", ElementPresentationManager.getTypeNameForObject(psiElement));
  }

  public void testHighlightResolvingInSingleFile() {
    setupImplicitMacroConfiguration();
    myFixture.testHighlighting("highlightResolvingInSingleFile.s");
  }

  public void testCompletionVariantsInSingleFile() {
    setupImplicitMacroConfiguration();
    myFixture.testCompletionVariants("completionVariantsInSingleFile.s",
      getCompletionVariants(
        "topLevelLabel", "anotherTopLevel.Label",
        ".localLabel", ".localLabel2", ".localLabelWithColon",
        "localLabelDollar$", "localLabelDollarWithColon$",
        "setLabel", "equLabel", "equalsLabel",
        "implicitLabel")
    );

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

    final LookupElement builtinSymbol = findLookupElement(myFixture, "*");
    final LookupElementPresentation builtinSymbolPresentation = LookupElementPresentation.renderElement(builtinSymbol);
    assertLookupIcon(builtinSymbolPresentation, AllIcons.Debugger.ShowCurrentFrame);
    assertTrue(builtinSymbolPresentation.isItemTextItalic());
    assertEquals(" Current PC", builtinSymbolPresentation.getTailText());
    assertEquals("any", builtinSymbolPresentation.getTypeText());

    final LookupElement implicitLabel1 = findLookupElement(myFixture, "implicitLabel", 46);
    final LookupElementPresentation implicitLabel1Presentation = LookupElementPresentation.renderElement(implicitLabel1);
    assertLookupIcon(implicitLabel1Presentation, M68kIcons.LABEL_MACRO);
    assertTrue(implicitLabel1Presentation.isItemTextBold());
    assertEquals(" (implicitLabelMacro implicitLabel)", implicitLabel1Presentation.getTailText());
    final LookupElement implicitLabel2 = findLookupElement(myFixture, "implicitLabel", 80);
    final LookupElementPresentation implicitLabelPresentation2 = LookupElementPresentation.renderElement(implicitLabel2);
    assertLookupIcon(implicitLabelPresentation2, M68kIcons.LABEL_MACRO);
    assertTrue(implicitLabelPresentation2.isItemTextBold());
    assertEquals(" (implicitLabelMacro implicitLabel)", implicitLabelPresentation2.getTailText());
  }

  public void testCompletionVariantsInParenthesizedExpression() {
    myFixture.testCompletionVariants("completionVariantsInParenthesizedExpression.s",
      getCompletionVariants("label"));
  }

  public void testResolveLocalLabelInCorrectScope() {
    final PsiReference reference = myFixture.getReferenceAtCaretPositionWithAssertion("resolveLocalLabelInCorrectScope.s");
    assertEquals(".localLabel", reference.getCanonicalText());

    final M68kLocalLabel resolvedLocalLabel = assertInstanceOf(reference.resolve(), M68kLocalLabel.class);
    assertEquals(15, resolvedLocalLabel.getTextOffset());
  }

  public void testHighlightResolveInMultipleFiles() {
    final String[] testDataPaths = {"highlightResolvingInMultipleFiles.s", "highlightResolvingInMultipleFiles_other.s"};
    setupImplicitMacroConfiguration();
    myFixture.testHighlighting(testDataPaths);

    // resolve in current file (2nd declaration in _other.s)
    final ResolveResult resolveResult = getSingleResolveResultAtCaret();
    assertResolveResultPsiElement(resolveResult, "myLabel", "highlightResolvingInMultipleFiles.s", 0);
  }

  public void testCompletionVariantsInMultipleFiles() {
    setupImplicitMacroConfiguration();
    myFixture.copyFileToProject("highlightResolvingInMultipleFiles_other.s");
    myFixture.testCompletionVariants("completionVariantsInMultipleFiles.s",
      getCompletionVariants("topLevelLabel", "anotherTopLevelLabel", "otherLabel", "otherLabel2", "myLabel", "implicitLabel"));

    final LookupElement otherLabel = findLookupElement(myFixture, "otherLabel");
    final LookupElementPresentation otherLabelPresentation = LookupElementPresentation.renderElement(otherLabel);
    assertFalse(otherLabelPresentation.isItemTextBold());
    assertEquals("highlightResolvingInMultipleFiles_other.s", otherLabelPresentation.getTypeText());

    final LookupElement implicitLabel = findLookupElement(myFixture, "implicitLabel");
    final LookupElementPresentation implicitLabelPresentation = LookupElementPresentation.renderElement(implicitLabel);
    assertLookupIcon(implicitLabelPresentation, M68kIcons.LABEL_MACRO);
    assertFalse(implicitLabelPresentation.isItemTextBold());
    assertEquals(" (implicitLabelMacro implicitLabel)", implicitLabelPresentation.getTailText());
    assertEquals("highlightResolvingInMultipleFiles_other.s", implicitLabelPresentation.getTypeText());
  }

  public void testImplicitMacroLabelMacroCallQuickFix() {
    runImplicitMacroQuickFixTest(1);
  }

  // todo M68kUnresolvedLabelReferenceInspection.isSuitableAsImplicitMacro
  public void _testImplicitMacroLabelMacroCallUnsuitable() {
    runImplicitMacroQuickFixTest(0);
  }

  public void testImplicitMacroLabelLabelRefQuickFix() {
    runImplicitMacroQuickFixTest(1);
  }

  public void testImplicitMacroLabelLabelRefMultipleQuickFixes() {
    runImplicitMacroQuickFixTest(2);
  }

  public void testImplicitMacroLabelHighlightUsages() {
    setupImplicitMacroConfiguration();
    SeveritiesProvider.EP_NAME.getPoint().registerExtension(new SeveritiesProvider() {
      @Override
      public @NotNull List<HighlightInfoType> getSeveritiesHighlightInfoTypes() {
        return List.of(HighlightInfoType.ELEMENT_UNDER_CARET_READ, HighlightInfoType.ELEMENT_UNDER_CARET_WRITE);
      }
    }, getTestRootDisposable());
    IdentifierHighlighterPassFactory.doWithHighlightingEnabled(getProject(), getTestRootDisposable(), () -> {
      myFixture.setReadEditorMarkupModel(true);
      myFixture.configureByFile(getTestName(true) + ".s");
      myFixture.checkHighlighting();
    });
  }

  public void testImplicitMacroLabelFindUsages() {
    setupImplicitMacroConfiguration();
    Collection<Usage> usages = myFixture.testFindUsagesUsingAction(getTestName(true) + ".s");
    assertSize(2, usages);
    assertSameElements(ContainerUtil.map(usages, Object::toString), "8|MY_EQU |equ| |implicitLabel", "6|lea| |implicitLabel|(|a0|),|a1");
  }

  private void runImplicitMacroQuickFixTest(int quickFixCount) {
    try {
      InspectionProfileImpl.INIT_INSPECTIONS = true;
      myFixture.enableInspections(new M68kUnresolvedLabelReferenceInspection());

      myFixture.testHighlighting(getTestName(true) + ".s");

      assertSize(quickFixCount, myFixture.filterAvailableIntentions("Add macro"));

      if (quickFixCount != 0) {
        IntentionAction intention = myFixture.findSingleIntention("Add macro 'implicitLabelMacro' as defining label");
        myFixture.launchAction(intention);
        myFixture.checkResultByFile(getTestName(true) + "_after.s");
      }
    } finally {
      InspectionProfileImpl.INIT_INSPECTIONS = false;
    }
  }

  private ResolveResult getSingleResolveResultAtCaret(String... testDataPaths) {
    final PsiReference referenceAtCaretPositionWithAssertion = myFixture.getReferenceAtCaretPositionWithAssertion(testDataPaths);
    final PsiPolyVariantReference polyVariantReference = assertInstanceOf(referenceAtCaretPositionWithAssertion, PsiPolyVariantReference.class);
    return assertOneElement(polyVariantReference.multiResolve(false));
  }

  static void assertResolveResultPsiElement(ResolveResult resolveResult, String text, String filename, int offset) {
    final PsiElement element = resolveResult.getElement();
    assertNotNull(text, element);
    assertEquals(text, element.getText());
    assertEquals(filename, element.getContainingFile().getName());
    assertEquals(offset, element.getTextOffset());
  }

}
