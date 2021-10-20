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

package com.yanncebron.m68kplugin.lang.stubs;

import com.intellij.lang.FileASTNode;
import com.intellij.psi.impl.DebugUtil;
import com.intellij.psi.stubs.LightStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.testFramework.LightPlatformTestCase;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kFileElementType;

public class M68kStubBuilderTest extends LightPlatformTestCase {

  public void testLabel() {
    doTest("label",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', GLOBAL, 'null']\n");
  }

  public void testLabelWithColon() {
    doTest("label:",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', GLOBAL, 'null']\n");
  }

  public void testLabelAfterWhitespaceWithColon() {
    doTest("  label:",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', GLOBAL, 'null']\n");
  }

  public void testLabelWithBackslashAt() {
    doTest("label\\@",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label\\@', GLOBAL, 'null']\n");
  }

  public void testLabelWithBackslashAtBegin() {
    doTest("\\@label",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['\\@label', GLOBAL, 'null']\n");
  }

  public void testLabelWithDot() {
    doTest("label.suffix",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label.suffix', GLOBAL, 'null']\n");
  }

  public void testLabelWithMacro() {
    doTest("label macro",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', MACRO, 'null']\n");
  }

  public void testLabelWithEqu() {
    doTest("label equ 42 comment",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', EQU, '42']\n");
  }

  public void testLabelWithEquNoValue() {
    doTest("labelEquNoValue equ ",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['labelEquNoValue', EQU, '']\n");
  }

  public void testLabelWithEquals() {
    doTest("label = 42*something",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', EQUALS, '42*something']\n");
  }

  public void testLabelWithSet() {
    doTest("label set 42",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', SET, '42']\n");
  }

  public void testLabelWithEqur() {
    doTest("label equr d7",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', EQUR, 'd7']\n");
  }

  public void testLabelWithReg() {
    doTest("label reg d0-d7/a0",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', REG, 'd0-d7/a0']\n");
  }

  public void testLabelWithFoDataSize() {
    doTest("label fo.w 42",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', FO, '42']\n");
  }

  public void testLabelWithFo() {
    doTest("label fo 42",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', FO, '42']\n");
  }

  public void testLabelWithSoDataSize() {
    doTest("label so.w 42",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', SO, '42']\n");
  }

  public void testLabelWithSo() {
    doTest("label so 42",
      "PsiFileStubImpl\n" +
        "  LABEL:M68kLabelStubImpl['label', SO, '42']\n");
  }

  public void testLocalLabelNotStubbed() {
    doTest(".localLabel",
      "PsiFileStubImpl\n");
  }

  private void doTest(String source, String expected) {
    M68kFile file = (M68kFile) createLightFile("test.s", source);
    FileASTNode fileNode = file.getNode();
    assertNotNull(fileNode);
    assertFalse(fileNode.isParsed());

    LightStubBuilder stubBuilder = M68kFileElementType.INSTANCE.getBuilder();
    StubElement lightTree = stubBuilder.buildStubTree(file);
    assertFalse(fileNode.isParsed());

    file.getNode().getChildren(null); // force switch to AST
    StubElement astBasedTree = stubBuilder.buildStubTree(file);
    assertTrue(fileNode.isParsed());

    assertEquals("light tree differs", expected, DebugUtil.stubTreeToString(lightTree));
    assertEquals("AST-based tree differs", expected, DebugUtil.stubTreeToString(astBasedTree));
  }
}
