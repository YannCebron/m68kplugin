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

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.UsefulTestCase;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class M68kAmigaHardwareRegisterTest extends UsefulTestCase {

  public void testConsistency() {
    assertSize(236, M68kAmigaHardwareRegister.values());

    Pattern validName = Pattern.compile("[A-Z0-9]{5,8}");

    int lastAddress = 0;
    int countRevisionOcs = 0;
    int countRevisionEcs = 0;
    int countRevisionAga = 0;
    int countCopperDanger = 0;

    Set<M68kAmigaHardwareRegister.Access> foundAccess = new HashSet<>();
    Set<M68kAmigaHardwareRegister.Chip> foundChip = new HashSet<>();

    Set<String> descriptionFileNames = new HashSet<>();
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
      assertFalse(register.name(), StringUtil.containsWhitespaces(register.getName()));
      assertTrue(register.name(), validName.matcher(register.getName()).matches());

      String address = register.getAddress();
      assertFalse(register.name(), StringUtil.containsWhitespaces(address));
      assertTrue(register.name(), StringUtil.startsWith(address, "DFF"));
      final int addressValue = Integer.parseInt(address, 16);
      assertTrue(addressValue > lastAddress);
      lastAddress = addressValue;

      assertFalse(register.name(), StringUtil.containsWhitespaces(register.getDescriptionFileName()));
      assertFalse(register.name(), register.getDescription().length() < 11);
      descriptionFileNames.add(register.getDescriptionFileName());

      assertNotNull(register.name(), register.getAccess());
      foundAccess.add(register.getAccess());

      assertFalse(register.name(), register.getChips().isEmpty());
      foundChip.addAll(register.getChips());

      final M68kAmigaHardwareRegister.Chipset chipset = register.getChipset();
      if (chipset == M68kAmigaHardwareRegister.Chipset.ECS) {
        countRevisionEcs++;
      } else if (chipset == M68kAmigaHardwareRegister.Chipset.AGA) {
        countRevisionAga++;
      } else {
        countRevisionOcs++;
      }

      if (register.isCopperDanger()) countCopperDanger++;
    }

    assertEquals(197, countRevisionOcs);
    assertEquals(30, countRevisionEcs);
    assertEquals(9, countRevisionAga);

    assertSize(4, foundAccess);
    assertSize(3, foundChip);
    assertEquals(58, countCopperDanger);

    assertEquals(78, descriptionFileNames.size());
  }

}