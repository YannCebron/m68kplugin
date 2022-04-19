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

package com.yanncebron.m68kplugin.editor;

import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.testFramework.EditorTestUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class M68kMoveLeftRightHandlerTest extends BasePlatformTestCase {

  public void testDcDirective() {
    doTestMoveLeft(" dc.b 0,1,<caret>2", " dc.b 0,<caret>2,1");
  }

  public void testDrDirective() {
    doTestMoveLeft(" dr.b 0,1,<caret>2", " dr.b 0,<caret>2,1");
  }

  public void testPrintvDirective() {
    doTestMoveLeft(" printv 0,1,<caret>2", " printv 0,<caret>2,1");
  }

  public void testXdefDirective() {
    doTestMoveLeft(" xdef label0,label1,<caret>label2", " xdef label0,<caret>label2,label1");
  }

  public void testXrefDirective() {
    doTestMoveLeft(" xref label0,label1,<caret>label2", " xref label0,<caret>label2,label1");
  }

  public void testIfcConditionalAssemblyDirective() {
    doTestMoveLeft(" ifc arg1,<caret>bpl", " ifc <caret>bpl,arg1");
  }

  public void testIfncConditionalAssemblyDirective() {
    doTestMoveLeft(" ifnc arg1,<caret>bpl", " ifnc <caret>bpl,arg1");
  }


  public void testBinaryExpression() {
    doTestMoveLeft(" dc 1+<caret>2", " dc <caret>2+1");
  }

  public void testAdmRegisterList() {
    doTestMoveLeft(" movem d0/<caret>d1/a0", " movem <caret>d1/d0/a0");
  }

  public void testExgInstruction() {
    doTestMoveLeft(" exg d0,<caret>a0", " exg <caret>a0,d0");
  }

  public void testCmpmInstruction() {
    doTestMoveLeft(" cmpm (a0)+,(<caret>a1)+", " cmpm (<caret>a1)+,(a0)+");
  }

  public void testMacroCallDirective() {
    doTestMoveLeft(" MY_MACRO d0,<caret>d1", " MY_MACRO <caret>d1,d0");
  }

  private void doTestMoveLeft(String text, String expectedText) {
    myFixture.configureByText("a.s", text);
    EditorTestUtil.executeAction(myFixture.getEditor(), IdeActions.MOVE_ELEMENT_LEFT);
    myFixture.checkResult(expectedText);
  }

}
