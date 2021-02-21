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

import com.intellij.testFramework.PlatformTestUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

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
    });
  }
}
