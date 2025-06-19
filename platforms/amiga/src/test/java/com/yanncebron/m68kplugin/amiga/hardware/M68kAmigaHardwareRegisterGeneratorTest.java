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
import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generates {@link M68kAmigaHardwareRegister} enumeration.
 */
@SuppressWarnings("SpellCheckingInspection")
public class M68kAmigaHardwareRegisterGeneratorTest extends TestCase {

  private static final boolean ENABLED = false;

  private static final String AGA_GUIDE_PATH = "/Users/yann/idea-ultimate/m68kplugin/platforms/amiga/src/test/java/com/yanncebron/m68kplugin/amiga/hardware/aga_guide.txt";

  static final String END_NODE = "@endnode";

  static final Pattern NAME_PATTERN = Pattern.compile("@\\{\"(\\w+)\"\\p{Space}+link\\p{Space}+(\\w+)}");

  private static final Pattern ADDRESS_PATTERN = Pattern.compile("(\\p{XDigit}{3})\\p{Space}{2,}");

  public void testGenerateHardwareRegisterData() throws IOException {
    if (!ENABLED) return;

    Iterator<String> iterator = getLineIterator("@{\"BLTDDAT\"");

    List<RegisterData> registers = new ArrayList<>();
    while (iterator.hasNext()) {
      String string = iterator.next();
      if (string.equals(END_NODE)) break;

      if (!StringUtil.startsWith(string, "@{\"")) continue;

      final Matcher nameMatcher = NAME_PATTERN.matcher(string);
      final boolean findName = nameMatcher.find();
      assertTrue("NO NAME " + string, findName);
      String name = nameMatcher.group(1);
      String descriptionNodeName = nameMatcher.group(2);

      final Matcher addressMatcher = ADDRESS_PATTERN.matcher(string);
      final boolean found = addressMatcher.find();
      assertTrue("NO ADDRESS " + name, found);
      final String addressOriginal = addressMatcher.group(1);
      String address = StringUtil.toUpperCase(addressOriginal);

      String earlyFlags = string.substring(string.indexOf('}') + 1, addressMatcher.start());
      boolean copperDanger = StringUtil.containsChar(earlyFlags, '~');
      M68kAmigaHardwareRegister.Chipset chipset = M68kAmigaHardwareRegister.Chipset.OCS;
      if (StringUtil.containsChar(earlyFlags, 'h')) {
        chipset = M68kAmigaHardwareRegister.Chipset.ECS;
      } else if (StringUtil.containsChar(earlyFlags, 'p')) {
        chipset = M68kAmigaHardwareRegister.Chipset.AGA;
      }

      final int beforeDescription = string.lastIndexOf("   ");
      String description = string.substring(beforeDescription + 3);


      String flags = string.substring(string.indexOf(addressOriginal) + address.length(), beforeDescription);

      M68kAmigaHardwareRegister.Access access = null;

      if (StringUtil.containsChar(flags, 'W')) {
        access = M68kAmigaHardwareRegister.Access.WRITE;
      } else if (StringUtil.contains(flags, "ER")) {
        access = M68kAmigaHardwareRegister.Access.EARLY_READ;
      } else if (StringUtil.containsChar(flags, 'R')) {
        access = M68kAmigaHardwareRegister.Access.READ;
      } else if (StringUtil.containsChar(flags, 'S')) {
        access = M68kAmigaHardwareRegister.Access.STROBE;
      }
      assertNotNull(name + " " + flags, access);

      Set<M68kAmigaHardwareRegister.Chip> chips = EnumSet.noneOf(M68kAmigaHardwareRegister.Chip.class);
      if (StringUtil.containsChar(flags, 'A')) {
        chips.add(M68kAmigaHardwareRegister.Chip.AGNUS_ALICE);
      }
      if (StringUtil.containsChar(flags, 'D')) {
        chips.add(M68kAmigaHardwareRegister.Chip.DENISE_LISA);
      }
      if (StringUtil.containsChar(flags, 'P')) {
        chips.add(M68kAmigaHardwareRegister.Chip.PAULA);
      }
      assertFalse(name + " " + flags, chips.isEmpty());

      RegisterData registerData = new RegisterData(name, descriptionNodeName, address, description, chipset, copperDanger, access, chips);
      registers.add(registerData);
    }

    assertEquals(236, registers.size());

    for (RegisterData register : registers) {
      String chipsData = StringUtil.join(register.chips, chip -> "Chip." + chip.name(), ", ");
      String chipsText = "EnumSet.of(" + chipsData + ")";
      System.out.println(register.name + "(\"" + register.name + "\", \"DFF" + register.address + "\", " +
        "\"" + register.description + "\", \"" + register.descriptionNodeName + "\", " +
        "Chipset." + register.chipset.name() + ", " +
        register.copperDanger + ", " +
        "Access." + register.access.name() + ", " +
        chipsText +
        "),");
    }
  }

  @NotNull
  static Iterator<String> getLineIterator(String startLineMarker) throws IOException {
    final List<String> strings = Files.readAllLines(Path.of(AGA_GUIDE_PATH));

    int listStartIdx = -1;
    for (String string : strings) {
      listStartIdx++;
      if (string.startsWith(startLineMarker)) {
        break;
      }
    }
    assertFalse("marker not found: '" + startLineMarker + "'", listStartIdx == -1);

    return strings.listIterator(listStartIdx);
  }

  private record RegisterData(String name, String descriptionNodeName, String address, String description,
                              M68kAmigaHardwareRegister.Chipset chipset, boolean copperDanger,
                              M68kAmigaHardwareRegister.Access access, Set<M68kAmigaHardwareRegister.Chip> chips) {
  }

}
