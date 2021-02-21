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

package com.yanncebron.m68kplugin.navigation;

import com.intellij.ide.util.gotoByName.GotoSymbolModel2;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.vfs.VirtualFileFilter;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.ui.DeferredIcon;
import com.yanncebron.m68kplugin.lang.M68kIcons;

import javax.swing.*;
import java.util.Objects;

import static java.util.Arrays.asList;

@TestDataPath("$PROJECT_ROOT/testData/navigation/gotoLabel")
public class M68kGotoLabelTest extends BasePlatformTestCase {

  @Override
  protected String getTestDataPath() {
    return "testData/navigation/gotoLabel";
  }

  public void testGotoLabel() {
    myFixture.configureByFiles("gotoLabelA.s", "gotoLabelB.s");

    ((PsiManagerEx) myFixture.getPsiManager()).setAssertOnFileLoadingFilter(VirtualFileFilter.ALL, myFixture.getTestRootDisposable());

    GotoSymbolModel2 model = new GotoSymbolModel2(myFixture.getProject());

    assertContainsElements(asList(model.getNames(false)),
      "aLabel",
      "aLabel2",
      "aMacro",
      "aEqu",
      "aEquals",
      "aSet",
      "aEqur",
      "bLabel");

    assertPresentation(model, "aLabel", M68kIcons.LABEL_GLOBAL, "gotoLabelA.s");
    assertPresentation(model, "aMacro", M68kIcons.LABEL_MACRO, "gotoLabelA.s");
    assertPresentation(model, "aEqu", M68kIcons.LABEL_EQU, "gotoLabelA.s");
    assertPresentation(model, "aEquals", M68kIcons.LABEL_EQU, "gotoLabelA.s");
    assertPresentation(model, "aSet", M68kIcons.LABEL_SET, "gotoLabelA.s");
    assertPresentation(model, "aEqur", M68kIcons.LABEL_EQUR, "gotoLabelA.s");

    assertPresentation(model, "bLabel", M68kIcons.LABEL_GLOBAL, "gotoLabelB.s");
  }

  private void assertPresentation(GotoSymbolModel2 model, String elementName, Icon expectedIcon, String expectedLocation) {
    final Object[] elements = model.getElementsByName(elementName, false, "");
    for (Object element : elements) {
      final NavigationItem navigationItem = assertInstanceOf(element, NavigationItem.class);
      if (Objects.equals(navigationItem.getName(), elementName)) {
        final ItemPresentation presentation = navigationItem.getPresentation();
        assertNotNull(presentation);
        Icon actualIcon = presentation.getIcon(false);
        if (actualIcon instanceof DeferredIcon) {
          actualIcon = ((DeferredIcon) actualIcon).evaluate();
        }
        assertEquals(expectedIcon, actualIcon);
        assertEquals(elementName, presentation.getPresentableText());
        assertEquals(expectedLocation, presentation.getLocationString());
        return;
      }
    }

    fail("no element with name '" + elementName + "'");
  }
}
