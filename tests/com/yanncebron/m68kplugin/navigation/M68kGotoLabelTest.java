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

package com.yanncebron.m68kplugin.navigation;

import com.intellij.ide.util.ModuleRendererFactory;
import com.intellij.ide.util.gotoByName.GotoSymbolModel2;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.vfs.VirtualFileFilter;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.TestLookupElementPresentation;
import com.intellij.util.TextWithIcon;
import com.yanncebron.m68kplugin.inspections.M68kUnresolvedLabelReferenceInspection;
import icons.M68kIcons;

import javax.swing.*;
import java.util.Objects;

import static java.util.Arrays.asList;

@TestDataPath("$PROJECT_ROOT/testData/navigation/gotoLabel")
public class M68kGotoLabelTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/navigation/gotoLabel";
  }

  @Override
  protected boolean isIconRequired() {
    return true;
  }

  public void testGotoLabel() {
    myFixture.configureByFiles("gotoLabelA.s", "gotoLabelB.s");

    ((PsiManagerEx) myFixture.getPsiManager()).setAssertOnFileLoadingFilter(VirtualFileFilter.ALL, myFixture.getTestRootDisposable());

    M68kUnresolvedLabelReferenceInspection unresolvedLabelReferenceInspection = new M68kUnresolvedLabelReferenceInspection();
    unresolvedLabelReferenceInspection.labelDefiningMacros.add("implicitLabelMacro");
    myFixture.enableInspections(unresolvedLabelReferenceInspection);

    GotoSymbolModel2 model = new GotoSymbolModel2(myFixture.getProject(), getTestRootDisposable());

    assertContainsElements(asList(model.getNames(false)),
      "aLabel",
      "aLabel2",
      "aMacro",
      "aEqu",
      "aEquals",
      "aSet",
      "aEqur",
      "aEquWithoutValue",
      "bLabel",
      "implicitLabel");

    assertPresentation(model, "aLabel", M68kIcons.LABEL_GLOBAL, null, "gotoLabelA.s");
    assertPresentation(model, "aMacro", M68kIcons.LABEL_MACRO, null, "gotoLabelA.s");
    assertPresentation(model, "aEqu", M68kIcons.LABEL_EQU, "42", "gotoLabelA.s");
    assertPresentation(model, "aEquWithoutValue", M68kIcons.LABEL_EQU, null, "gotoLabelA.s");
    assertPresentation(model, "aEquals", M68kIcons.LABEL_EQU, "1", "gotoLabelA.s");
    assertPresentation(model, "aSet", M68kIcons.LABEL_SET, "2", "gotoLabelA.s");
    assertPresentation(model, "aEqur", M68kIcons.LABEL_EQUR, "d0", "gotoLabelA.s");

    assertPresentation(model, "bLabel", M68kIcons.LABEL_GLOBAL, null, "gotoLabelB.s");

    assertPresentation(model, "implicitLabel", M68kIcons.LABEL_MACRO, " (implicitLabelMacro implicitLabel) ", "gotoLabelA.s");
  }

  private void assertPresentation(GotoSymbolModel2 model, String elementName,
                                  Icon expectedIcon, String expectedLocation,
                                  String expectedFileLocation) {
    final Object[] elements = model.getElementsByName(elementName, false, "");
    for (Object element : elements) {
      final NavigationItem navigationItem = assertInstanceOf(element, NavigationItem.class);
      if (Objects.equals(navigationItem.getName(), elementName)) {
        final ItemPresentation presentation = navigationItem.getPresentation();
        assertNotNull(presentation);
        assertEquals(expectedIcon, TestLookupElementPresentation.unwrapIcon(presentation.getIcon(false)));
        assertEquals(elementName, presentation.getPresentableText());
        assertEquals(expectedLocation, presentation.getLocationString());

        final ModuleRendererFactory moduleRendererFactory = ModuleRendererFactory.findInstance(navigationItem);
        assertInstanceOf(moduleRendererFactory, M68kGotoLabelModuleRendererFactory.class);
        final TextWithIcon textWithIcon = moduleRendererFactory.getModuleTextWithIcon(navigationItem);
        assertNotNull(navigationItem.getName(), textWithIcon);
        assertEquals(expectedFileLocation, textWithIcon.getText());
        return;
      }
    }

    fail("no element with name '" + elementName + "'");
  }
}
