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

package com.yanncebron.m68kplugin.amiga.hardware;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.UsefulTestCase;

public class M68kAmigaHardwareRegisterTest extends UsefulTestCase {

  public void testConsistency() {
    assertSize(236, M68kAmigaHardwareRegister.values());

    int lastAddress = 0;
    int countRevisionOcs = 0;
    int countRevisionEcs = 0;
    int countRevisionAga = 0;
    int countCopperDanger = 0;
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
      assertFalse(register.name(), StringUtil.containsWhitespaces(register.getName()));

      String address = register.getAddress();
      assertFalse(register.name(), StringUtil.containsWhitespaces(address));
      assertTrue(register.name(), StringUtil.startsWith(address, "DFF"));
      final int addressValue = Integer.parseInt(address, 16);
      assertTrue(addressValue > lastAddress);
      lastAddress = addressValue;

      assertFalse(register.name(), StringUtil.containsWhitespaces(register.getDescriptionFileName()));
      assertFalse(register.name(), register.getDescription().length() < 11);

      assertNotNull(register.name(), register.getAccess());
      assertFalse(register.name(), register.getChips().isEmpty());

      final M68kAmigaHardwareRegister.Revision revision = register.getRevision();
      if (revision == M68kAmigaHardwareRegister.Revision.ECS) {
        countRevisionEcs++;
      } else if (revision == M68kAmigaHardwareRegister.Revision.AGA) {
        countRevisionAga++;
      } else {
        countRevisionOcs++;
      }


      if (register.isCopperDanger()) countCopperDanger++;
    }

    assertEquals(197, countRevisionOcs);
    assertEquals(30, countRevisionEcs);
    assertEquals(9, countRevisionAga);

    assertEquals(58, countCopperDanger);
  }

}