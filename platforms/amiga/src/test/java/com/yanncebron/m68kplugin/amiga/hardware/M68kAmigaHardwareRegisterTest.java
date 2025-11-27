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
import com.intellij.util.containers.ContainerUtil;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static com.intellij.testFramework.UsefulTestCase.assertSize;

public class M68kAmigaHardwareRegisterTest extends TestCase {

  private static final int CIA_COUNT = 15;

  public void testConsistency() {
    assertSize(236 + CIA_COUNT * 2, M68kAmigaHardwareRegister.values());

    Pattern validName = Pattern.compile("[A-Z0-9]{5,8}");
    Pattern ciaEnumValidName = Pattern.compile("CIA[A|B]_[A-Z]{2,6}");
    Pattern ciaValidName = Pattern.compile("CIA[A|B][A-Z]{2,6}");

    int lastAddress = 0;
    int countCiaA = 0;
    int countCiaB = 0;
    int countNA = 0;
    int countRevisionOcs = 0;
    int countRevisionEcs = 0;
    int countRevisionAga = 0;
    int countCopperDanger = 0;

    Set<String> foundAddress = new HashSet<>();
    Set<M68kAmigaHardwareRegister.Access> foundAccess = new HashSet<>();
    Set<M68kAmigaHardwareRegister.Chip> foundChip = new HashSet<>();

    Set<String> ciaDescriptionFileNames = new HashSet<>();
    Set<String> hardwareDescriptionFileNames = new HashSet<>();
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
      String name = register.name();

      Set<M68kAmigaHardwareRegister.Chip> chips = register.getChips();
      boolean isCIA = register.getChipset() == M68kAmigaHardwareRegister.Chipset.N_A;

      String registerDisplayName = register.getName();
      assertFalse(name, StringUtil.containsWhitespaces(registerDisplayName));
      if (isCIA) {
        assertTrue(name, ciaEnumValidName.matcher(name).matches());
        assertTrue(name, ciaValidName.matcher(registerDisplayName).matches());
      } else {
        assertTrue(name, validName.matcher(registerDisplayName).matches());
      }

      String address = register.getAddress();
      assertFalse(name, StringUtil.containsWhitespaces(address));
      assertTrue(name, foundAddress.add(address));
      if (isCIA) {
        assertEquals(name, 1, chips.size());
        assertFalse(name, register.isCopperDanger());
        assertEquals(name, M68kAmigaHardwareRegister.Access.READ_WRITE, register.getAccess());

        M68kAmigaHardwareRegister.Chip ciaChip = ContainerUtil.getFirstItem(chips);
        if (ciaChip == M68kAmigaHardwareRegister.Chip.CIA_A) {
          countCiaA++;
          assertTrue(name, StringUtil.startsWith(name, "CIAA_"));
          assertTrue(name, StringUtil.startsWith(address, "BFE"));
          assertTrue(name, StringUtil.startsWith(register.getDescription(), "CIAA "));
        } else if (ciaChip == M68kAmigaHardwareRegister.Chip.CIA_B) {
          countCiaB++;
          assertTrue(name, StringUtil.startsWith(name, "CIAB_"));
          assertTrue(name, StringUtil.startsWith(address, "BFD"));
          assertTrue(name, StringUtil.startsWith(register.getDescription(), "CIAB "));
        } else {
          fail(name);
        }
      } else {
        assertTrue(name, StringUtil.startsWith(address, "DFF"));
      }
      final int addressValue = Integer.parseInt(address, 16);
      assertTrue(addressValue > lastAddress);
      lastAddress = addressValue;

      assertFalse(name, StringUtil.containsWhitespaces(register.getDescriptionFileName()));
      assertFalse(name, register.getDescription().length() < 11);

      if (isCIA) {
        ciaDescriptionFileNames.add(register.getDescriptionFileName());
      } else {
        hardwareDescriptionFileNames.add(register.getDescriptionFileName());
      }

      assertNotNull(name, register.getAccess());
      foundAccess.add(register.getAccess());

      assertFalse(name, chips.isEmpty());
      foundChip.addAll(chips);

      final M68kAmigaHardwareRegister.Chipset chipset = register.getChipset();
      if (chipset == M68kAmigaHardwareRegister.Chipset.ECS) {
        countRevisionEcs++;
      } else if (chipset == M68kAmigaHardwareRegister.Chipset.AGA) {
        countRevisionAga++;
      } else if (chipset == M68kAmigaHardwareRegister.Chipset.OCS) {
        countRevisionOcs++;
      } else if (isCIA) {
        countNA++;
      }

      if (register.isCopperDanger()) countCopperDanger++;
    }

    assertEquals(CIA_COUNT * 2, countNA);
    assertEquals(CIA_COUNT, countCiaA);
    assertEquals(CIA_COUNT, countCiaB);

    assertEquals(197, countRevisionOcs);
    assertEquals(30, countRevisionEcs);
    assertEquals(9, countRevisionAga);

    assertSize(5, foundAccess);
    assertSize(5, foundChip);
    assertEquals(58, countCopperDanger);

    assertEquals(21, ciaDescriptionFileNames.size());
    assertEquals(78, hardwareDescriptionFileNames.size());
  }

}