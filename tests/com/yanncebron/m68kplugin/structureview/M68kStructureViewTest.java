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

package com.yanncebron.m68kplugin.structureview;

import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElementWrapper;
import com.intellij.navigation.ItemPresentation;
import com.intellij.testFramework.PlatformTestUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.TestLookupElementPresentation;
import com.intellij.util.ui.tree.TreeUtil;
import com.yanncebron.m68kplugin.lang.M68kIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class M68kStructureViewTest extends BasePlatformTestCase {

  public void testStructureView() {
    myFixture.configureByText("a.s",
      "myEqu EQU 2\n" +
        "myEquals = 42\n" +
        "mySet set 1\n" +
        "myEqur equr d0\n" +
        "myMacro macro\n" +
        " endm\n" +
        " include \"include.s\"\n" +
        " incbin \"incbin_file\"\n" +
        "label\n" +
        ".localLabel\n" +
        "anotherLabel\n"
    );
    myFixture.testStructureView(component -> {
      final JTree tree = component.getTree();
      PlatformTestUtil.waitWhileBusy(tree);
      PlatformTestUtil.assertTreeEqual(tree,
        "-a.s\n" +
          " myEqu\n" +
          " myEquals\n" +
          " mySet\n" +
          " myEqur\n" +
          " myMacro\n" +
          " include.s\n" +
          " incbin_file\n" +
          " -label\n" +
          "  localLabel\n" +
          " anotherLabel");


      TreeElementWrapper root = (TreeElementWrapper) TreeUtil.getUserObject(component.getTree().getModel().getRoot());
      assertNotNull(root);
      TreeElement[] topLevelNodes = root.getValue().getChildren();
      assertNodePresentation(topLevelNodes, 0, M68kIcons.LABEL_EQU, "2");
      assertNodePresentation(topLevelNodes, 1, M68kIcons.LABEL_EQU, "42");
      assertNodePresentation(topLevelNodes, 2, M68kIcons.LABEL_SET, "1");
      assertNodePresentation(topLevelNodes, 3, M68kIcons.LABEL_EQUR, "d0");
      assertNodePresentation(topLevelNodes, 4, M68kIcons.LABEL_MACRO, null);
      assertNodePresentation(topLevelNodes, 5, M68kIcons.INCLUDE, null);
      assertNodePresentation(topLevelNodes, 6, M68kIcons.INCBIN, null);
      assertNodePresentation(topLevelNodes, 7, M68kIcons.LABEL_GLOBAL, null);

      final TreeElement[] labelNodeChildren = topLevelNodes[7].getChildren();
      assertSize(1, labelNodeChildren);
      assertNodePresentation(labelNodeChildren, 0, M68kIcons.LABEL_LOCAL, null);
    });
  }

  private static void assertNodePresentation(TreeElement[] nodes, int index,
                                             @NotNull Icon expectedIcon,
                                             @Nullable String expectedLocationString) {
    final ItemPresentation presentation = nodes[index].getPresentation();

    assertEquals(expectedIcon, TestLookupElementPresentation.unwrapIcon(presentation.getIcon(false)));

    if (expectedLocationString != null) {
      assertEquals(expectedLocationString, presentation.getLocationString());
    }
  }
}
