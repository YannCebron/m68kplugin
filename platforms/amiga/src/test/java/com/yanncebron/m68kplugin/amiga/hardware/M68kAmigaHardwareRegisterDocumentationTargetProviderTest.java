/*
 * Copyright 2026 The Authors
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

package com.yanncebron.m68kplugin.amiga.hardware;

import com.intellij.lang.documentation.ide.IdeDocumentationTargetProvider;
import com.intellij.platform.backend.documentation.DocumentationData;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.presentation.TargetPresentation;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.yanncebron.m68kplugin.settings.ide.M68kProjectEnvironment;
import com.yanncebron.m68kplugin.settings.ide.M68kTargetPlatform;

import java.util.List;

import static com.intellij.platform.backend.documentation.impl.ImplKt.computeDocumentationBlocking;

@SuppressWarnings("UnstableApiUsage")
public class M68kAmigaHardwareRegisterDocumentationTargetProviderTest extends BasePlatformTestCase {

  private static final String EXPECTED_PRESENTABLE_TEXT = "AUD0DAT";
  private static final String EXPECTED_HTML = "<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><b>AUD0DAT</b><br/>Audio channel 0 data</pre></div><table class='sections'><tr><td valign='top' class='section'><p>Address:</td><td valign='top'><code>$DFF0AA</code><a href='m68kCopyData://$DFF0AA'><icon src='AllIcons.Actions.Copy'/></a>&nbsp;&ndash;&nbsp;<code>$00AA</code><a href='m68kCopyData://$00AA'><icon src='AllIcons.Actions.Copy'/></a></td><tr><td valign='top' class='section'><p>Chip Set (Chips):</td><td valign='top'>OCS (Paula)</td><tr><td valign='top' class='section'><p>Access:</td><td valign='top'>Write</td><tr><td valign='top' class='section'><p>Copper Danger:</td><td valign='top'>-</td><tr><td valign='top' class='section'><p>Related:</td><td valign='top'><table><tr><td valign='top'><b><a href='AUD0DAT'>AUD0DAT</a></b></td><td valign='top'><a href='AUD1DAT'>AUD1DAT</a></td><td valign='top'><a href='AUD2DAT'>AUD2DAT</a></td><td valign='top'><a href='AUD3DAT'>AUD3DAT</a></td></tr><tr></tr></table></td></table><div class='content'><p>This reg is the audio channel x (x=0,1,2,3) DMA\ndata buffer. It contains 2 bytes of data (each\nbyte is a two's complement signed integer) that\nare outputted sequentially (with digital to analog\nconversion) to the audio output pins. With maximum\nvolume, each byte can drive the audio outputs\nwith 0.8 volts (peak to peak,typ). The audio DMA\nchannel controller automatically transfers data\nto this reg from RAM. The processor can also\nwrite directly to this reg. When the DMA data is\nfinished (words outputted=length) and the data in\nthis reg has been used, an audio channel interrupt\nrequest is set.</p>\n</div>";

  public void testHardwareRegisterFullAddress() {
    M68kProjectEnvironment.getInstance(getProject()).setTargetPlatform(M68kTargetPlatform.AMIGA, getTestRootDisposable());
    doTest(" move.l #0,$DFF<caret>0AA", EXPECTED_PRESENTABLE_TEXT, EXPECTED_HTML);
    doTest("AUD0DAT equ $DFF<caret>0AA", EXPECTED_PRESENTABLE_TEXT, EXPECTED_HTML);
  }

  public void testHardwareRegisterFullAddressNotAmigaTargetPlatform() {
    doTest(" move.l #0,$DFF<caret>0AA", "$DFF0AA", "<code><span style=\"color:#0000ff;\">14676138</span><br><span style=\"color:#0000ff;\">$dff0aa</span><br><span style=\"color:#0000ff;\">@67770252</span><br><span style=\"color:#0000ff;\">%110111111111000010101010</span><br></code>");
  }

  public void testCopperList() {
    M68kProjectEnvironment.getInstance(getProject()).setTargetPlatform(M68kTargetPlatform.AMIGA, getTestRootDisposable());
    doTest(" dc.w $A<caret>A,$0", EXPECTED_PRESENTABLE_TEXT, EXPECTED_HTML);
    doTest(" dc.w $00A<caret>A,$0", EXPECTED_PRESENTABLE_TEXT, EXPECTED_HTML);
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
