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

package com.yanncebron.m68kplugin.documentation;

import com.intellij.lang.documentation.ide.IdeDocumentationTargetProvider;
import com.intellij.platform.backend.documentation.DocumentationData;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.presentation.TargetPresentation;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;

import java.util.List;

import static com.intellij.platform.backend.documentation.impl.ImplKt.computeDocumentationBlocking;

@SuppressWarnings("UnstableApiUsage")
public class M68kRegisterSymbolDocumentationTest extends BasePlatformTestCase {

  public void testDataRegister() {
    doTest(" move.l d<caret>0,d1", "D0", """
      <style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='content'><h1>Data Registers (<code>D0</code>-<code>D7</code>)</h1>
      <p>These registers are for bit and bit field (1 – 32 bits), byte (8 bits), word (16 bits), long-word
      (32 bits), and quad-word (64 bits) operations. They also can be used as index registers.</p>
      <p><em>From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with
      permission.</em></p>
      <br><hr/>M68000 Family<br></div>""");
  }

  public void testSfcRegister() {
    doTest(" movec S<caret>FC,d0", "SFC", """
      <style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='content'><h1>Alternate Function Code Registers (<code>SFC</code> and <code>DFC</code>)</h1>
      <p>The alternate function code registers contain 3-bit function codes. Function codes can be
      considered extensions of the 32-bit logical address that optionally provides as many as eight
      4-Gbyte address spaces. The processor automatically generates function codes to select
      address spaces for data and programs at the user and supervisor modes. Certain
      instructions use SFC and DFC to specify the function codes for operations.</p>
      <p><em>From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with permission.</em></p>
      <br><hr/>M68010+<br></div>""");
  }

  public void testRemapToAddressRegister() {
    M68kRegister[] remapRegisters = new M68kRegister[]{M68kRegister.SP, M68kRegister.SSP, M68kRegister.USP};
    for (M68kRegister register : remapRegisters) {
      String documentation = new M68kRegisterDocsGenerator(register).getDocumentation();
      assertTrue("failed for " + register + ": " + documentation, documentation.contains("<h1>Address registers (<code>A0</code>-<code>A7</code>)</h1>"));
    }
  }

  private void doTest(String source, String expectedPresentableText, String expectedHtml) {
    myFixture.configureByText("a.s", source);

    List<? extends DocumentationTarget> documentationTargets = IdeDocumentationTargetProvider.getInstance(getProject()).documentationTargets(myFixture.getEditor(), myFixture.getFile(), myFixture.getCaretOffset());
    DocumentationTarget documentationTarget = assertOneElement(documentationTargets);

    TargetPresentation targetPresentation = documentationTarget.computePresentation();
    assertEquals(expectedPresentableText, targetPresentation.getPresentableText());

    DocumentationData documentationData = computeDocumentationBlocking(documentationTarget.createPointer());
    assertNotNull(documentationData);
    assertEquals(expectedHtml, documentationData.getHtml());
  }
}
