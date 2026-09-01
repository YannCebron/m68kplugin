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

import com.intellij.testFramework.utils.inlays.declarative.DeclarativeInlayHintsProviderTestCase;
import com.yanncebron.m68kplugin.settings.ide.M68kProjectEnvironment;
import com.yanncebron.m68kplugin.settings.ide.M68kTargetPlatform;

import java.util.Collections;

public class M68kAmigaHardwareRegisterInlayProviderTest extends DeclarativeInlayHintsProviderTestCase {

  public void testHardwareRegisterInlayNumberExpression() {
    M68kProjectEnvironment.getInstance(getProject()).setTargetPlatform(M68kTargetPlatform.AMIGA, getTestRootDisposable());

    doTestProvider("a.s", """
      BG_COLOR EQU $DFF180/*<# COLOR00 #>*/
      BG_COLOR_DECIMAL EQU 14676352 ; only for hex numbers
      
        move.b	#0,$bfe001/*<# CIAA_PRA #>*/  ; clear
        btst 		#14,$dff002/*<# DMACONR #>*/ ; blitter busy?
      """, new M68kAmigaHardwareRegisterInlayProvider(), Collections.emptyMap(), false);
  }

  public void testHardwareRegisterInlayNumberExpressionNotAmigaTargetPlatform() {
    doTestProvider("a.s", """
      BG_COLOR EQU $DFF180
      
        move.b	#0,$bfe001  ; clear
        btst 		#14,$dff002 ; blitter busy?
      """, new M68kAmigaHardwareRegisterInlayProvider(), Collections.emptyMap(), false);
  }

  public void testHardwareRegisterCopperlist() {
    M68kProjectEnvironment.getInstance(getProject()).setTargetPlatform(M68kTargetPlatform.AMIGA, getTestRootDisposable());

    doTestProvider("a.s", """
        dc.w $8E/*<# DIWSTRT #>*/,$2c81
        dc.w $08E/*<# DIWSTRT #>*/,$2c81
        dc.w $008E/*<# DIWSTRT #>*/,$2c81
      
        dc.w $0180/*<# COLOR00 #>*/,$0180 ; only first value
        dc.w $0180,$0180,$0180            ; only if 2 values
      
        dc.l $0180,$0180                  ; only .W
      """, new M68kAmigaHardwareRegisterInlayProvider(), Collections.emptyMap(), false);
  }
}
