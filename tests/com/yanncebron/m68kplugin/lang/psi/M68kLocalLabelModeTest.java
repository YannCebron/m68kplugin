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

package com.yanncebron.m68kplugin.lang.psi;

import junit.framework.TestCase;

public class M68kLocalLabelModeTest extends TestCase {

  public void testMatches() {
    assertTrue(M68kLocalLabelMode.DOT.matches(".label"));
    assertTrue(M68kLocalLabelMode.DOT.matches(".label:"));

    assertTrue(M68kLocalLabelMode.DOLLAR.matches("label$"));
    assertTrue(M68kLocalLabelMode.DOLLAR.matches("label$:"));
  }

  public void testGetPatchedName() {
    assertEquals(".label", M68kLocalLabelMode.DOT.getPatchedName("label"));
    assertEquals("label$", M68kLocalLabelMode.DOLLAR.getPatchedName("label"));
  }

  public void testFind() {
    assertEquals(M68kLocalLabelMode.DOT, M68kLocalLabelMode.find(".label"));
    assertEquals(M68kLocalLabelMode.DOT, M68kLocalLabelMode.find(".label:"));
    assertEquals(M68kLocalLabelMode.DOLLAR, M68kLocalLabelMode.find("label$"));
    assertEquals(M68kLocalLabelMode.DOLLAR, M68kLocalLabelMode.find("label$:"));

    assertNull(M68kLocalLabelMode.find("label"));
    assertNull(M68kLocalLabelMode.find(""));
    assertNull(M68kLocalLabelMode.find(null));
  }
}