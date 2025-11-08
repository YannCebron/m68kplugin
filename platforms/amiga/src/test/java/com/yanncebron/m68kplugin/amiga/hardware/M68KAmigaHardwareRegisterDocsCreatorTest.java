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

package com.yanncebron.m68kplugin.amiga.hardware;

import com.intellij.testFramework.UsefulTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class M68KAmigaHardwareRegisterDocsCreatorTest extends UsefulTestCase {

  public void testRegistersWithoutReferenceDocs() {
    Set<M68kAmigaHardwareRegister> withoutDoc = new HashSet<>();
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
      String registerDoc = getRegisterDoc(register);
      if (registerDoc.contains("No reference documentation available")) {
        withoutDoc.add(register);
      }
    }

    assertSameElements(withoutDoc,
      M68kAmigaHardwareRegister.CIAA_PRA,
      M68kAmigaHardwareRegister.CIAA_PRB,
      M68kAmigaHardwareRegister.CIAA_DDRA,
      M68kAmigaHardwareRegister.CIAA_DDRB,
      M68kAmigaHardwareRegister.CIAA_TALO,
      M68kAmigaHardwareRegister.CIAA_TAHI,
      M68kAmigaHardwareRegister.CIAA_TBLO,
      M68kAmigaHardwareRegister.CIAA_TBHI,
      M68kAmigaHardwareRegister.CIAA_TODLO,
      M68kAmigaHardwareRegister.CIAA_TODMID,
      M68kAmigaHardwareRegister.CIAA_TODHI,
      M68kAmigaHardwareRegister.CIAA_SDR,
      M68kAmigaHardwareRegister.CIAA_ICR,
      M68kAmigaHardwareRegister.CIAA_CRA,
      M68kAmigaHardwareRegister.CIAA_CRB,
      
      M68kAmigaHardwareRegister.CIAB_PRA,
      M68kAmigaHardwareRegister.CIAB_PRB,
      M68kAmigaHardwareRegister.CIAB_DDRA,
      M68kAmigaHardwareRegister.CIAB_DDRB,
      M68kAmigaHardwareRegister.CIAB_TALO,
      M68kAmigaHardwareRegister.CIAB_TAHI,
      M68kAmigaHardwareRegister.CIAB_TBLO,
      M68kAmigaHardwareRegister.CIAB_TBHI,
      M68kAmigaHardwareRegister.CIAB_TODLO,
      M68kAmigaHardwareRegister.CIAB_TODMID,
      M68kAmigaHardwareRegister.CIAB_TODHI,
      M68kAmigaHardwareRegister.CIAB_DR,
      M68kAmigaHardwareRegister.CIAB_ICR,
      M68kAmigaHardwareRegister.CIAB_CRA,
      M68kAmigaHardwareRegister.CIAB_CRB
    );
  }

  public void testCIAA_CRA() {
    assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1>CIAACRA</h1>CIAA Control Register A</pre></div><table class='sections'><tr><td valign='top' class='section'><p>Address:</td><td valign='top'><code>$BFEE01</code><a href='m68kBrowserCopy://$BFEE01'><icon src='AllIcons.Actions.Copy'/></a></td><tr><td valign='top' class='section'><p>CIA:</td><td valign='top'>CIAA</td><tr><td valign='top' class='section'><p>Access:</td><td valign='top'>Read/Write</td><tr><td valign='top' class='section'><p>Related:</td><td valign='top'><table><tr><td valign='top'><a href='m68kBrowser://CIABCRA'>CIABCRA</a></td><td valign='top'><b><a href='m68kBrowser://CIAACRA'>CIAACRA</a></b></td></tr></table></td></table><div class='content'>No reference documentation available for 'CIAx_CRA'</div>",
      getRegisterDoc(M68kAmigaHardwareRegister.CIAA_CRA));
  }

  public void testBPL1DAT() {
    assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1>BPL1DAT</h1>Bit plane 1 data (parallel to serial convert)</pre></div><table class='sections'><tr><td valign='top' class='section'><p>Address:</td><td valign='top'><code>$DFF110</code><a href='m68kBrowserCopy://$DFF110'><icon src='AllIcons.Actions.Copy'/></a>&nbsp;&ndash;&nbsp;<code>$0110</code><a href='m68kBrowserCopy://$0110'><icon src='AllIcons.Actions.Copy'/></a></td><tr><td valign='top' class='section'><p>Chip Set (Chips):</td><td valign='top'>OCS (Denise/Lisa)</td><tr><td valign='top' class='section'><p>Access:</td><td valign='top'>Write</td><tr><td valign='top' class='section'><p>Copper Danger:</td><td valign='top'>-</td><tr><td valign='top' class='section'><p>Related:</td><td valign='top'><table><tr><td valign='top'><b><a href='m68kBrowser://BPL1DAT'>BPL1DAT</a></b></td><td valign='top'><a href='m68kBrowser://BPL2DAT'>BPL2DAT</a></td><td valign='top'><a href='m68kBrowser://BPL3DAT'>BPL3DAT</a></td><td valign='top'><a href='m68kBrowser://BPL4DAT'>BPL4DAT</a></td></tr><tr><td valign='top'><a href='m68kBrowser://BPL5DAT'>BPL5DAT</a></td><td valign='top'><a href='m68kBrowser://BPL6DAT'>BPL6DAT</a></td><td valign='top'><a href='m68kBrowser://BPL7DAT'>BPL7DAT</a></td><td valign='top'><a href='m68kBrowser://BPL8DAT'>BPL8DAT</a></td></tr><tr></tr></table></td></table><div class='content'><p>These registers receive the DMA data fetched from RAM by the\nbit plane address pointers described above.\nThey may also be rewritten by either micro.\nThey act as a 8 word parallel to serial buffer for up\nto 8 memory 'bit planes'. x=1-8 the parallel to serial\nconversion id triggered whenever bit plane #1 is\nwritten, inducing the completion of all bit planes for\nthat word (16/32/64 pixels). The MSB is output first,\nand is therefore always on the left.</p>\n</div>",
      getRegisterDoc(M68kAmigaHardwareRegister.BPL1DAT));
  }

  public void testBPLHDAT() {
    assertEquals("<style>table { white-space: nowrap; } blockquote { padding-left: 10px; padding-right: 10px; padding-bottom: 5px; }</style><div class='definition'><pre><h1>BPLHDAT</h1>Ext logic UHRES bit plane identifier</pre></div><table class='sections'><tr><td valign='top' class='section'><p>Address:</td><td valign='top'><code>$DFF07A</code><a href='m68kBrowserCopy://$DFF07A'><icon src='AllIcons.Actions.Copy'/></a>&nbsp;&ndash;&nbsp;<code>$007A</code><a href='m68kBrowserCopy://$007A'><icon src='AllIcons.Actions.Copy'/></a></td><tr><td valign='top' class='section'><p>Chip Set (Chips):</td><td valign='top'>ECS (Agnus/Alice)</td><tr><td valign='top' class='section'><p>Access:</td><td valign='top'>Write</td><tr><td valign='top' class='section'><p>Copper Danger:</td><td valign='top'>✓</td></table><div class='content'><p>This is the number (sign extended) that is added to the UHRES bitplane\npointer (<a rel=\"nofollow\" href=\"m68kBrowser://BPLHPTH\">BPLHPTL</a>,<a rel=\"nofollow\" href=\"m68kBrowser://BPLHPTH\">BPLHPTH</a>) every line, and then another 2 is added, just like\nthe other modulos.</p>\n</div>",
      getRegisterDoc(M68kAmigaHardwareRegister.BPLHDAT));
  }

  private static @NotNull String getRegisterDoc(M68kAmigaHardwareRegister register) {
    return new M68kAmigaHardwareRegisterDocsCreator(register, List.of(M68kAmigaHardwareRegister.values())).generateDoc(true);
  }
}
