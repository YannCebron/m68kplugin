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
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generates hardware documentation in Markdown format from {@code aga_guide.txt}.
 */
public class M68kAmigaHardwareRegisterDocumentationGeneratorTest extends TestCase {

  private static final boolean ENABLED = false;

  // 'NAME = Description' / 'NAME, NAME2 = Description'
  private static final Pattern BIT_DESCRIPTION = Pattern.compile("\\A\\p{Upper}\\d*+[\\s,\\p{Upper}\\d*]*\\s+=\\s+.+");

  private static final String OUTPUT_DIR = "/Users/yann/idea-ultimate/m68kplugin/platforms/amiga/src/main/resources/docs/amigaHardwareRegister/";

  public void testGenerateRegisterMarkdown() throws IOException {
    if (!ENABLED) return;

    Set<String> descriptionNames = ContainerUtil.map2LinkedSet(EnumSet.allOf(M68kAmigaHardwareRegister.class), M68kAmigaHardwareRegister::getDescriptionFileName);
    assertEquals(78, descriptionNames.size());

    for (String descriptionName : descriptionNames) {
      final Iterator<String> iterator = find(descriptionName);
      assertNotNull("no entry for " + descriptionName, iterator);

      List<String> docLines = new ArrayList<>();
      boolean passedHeader = false;
      while (iterator.hasNext()) {
        final String string = iterator.next();
        if (string.equals(M68kAmigaHardwareRegisterGeneratorTest.END_NODE)) break;

        if (passedHeader) {
          docLines.add(processDocLine(string));
        } else if (string.isEmpty()) {
          passedHeader = true;
        } else if (string.startsWith(descriptionName)) {
          while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equals(M68kAmigaHardwareRegisterGeneratorTest.END_NODE)) break;
            if (next.isEmpty()) {
              passedHeader = true;
              break;
            }
          }
        }
      }

      assertFalse(descriptionName, docLines.isEmpty());
      final String finishedDoc = processDoc(docLines);

      Path path = Path.of(OUTPUT_DIR, descriptionName + ".md");
      Files.writeString(path, finishedDoc, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
  }

  private static String processDocLine(String input) {
    assertFalse(input, StringUtil.containsChar(input, '`'));

    boolean hasLink = input.contains("@{\"");
    if (hasLink) {
      Matcher linkMatcher = M68kAmigaHardwareRegisterGeneratorTest.NAME_PATTERN.matcher(input);
      while (linkMatcher.find()) {
        final String group = linkMatcher.group(1);
        input = linkMatcher.replaceFirst(group);
        linkMatcher = M68kAmigaHardwareRegisterGeneratorTest.NAME_PATTERN.matcher(input);
      }
    }

    final String trim = input.trim();
    if (StringUtil.startsWithIgnoreCase(trim, "note:")) {
      return "  > " + trim;
    }
    return trim;
  }

  private static String processDoc(List<String> lines) {
    StringBuilder copy = new StringBuilder();

    boolean bitTableMode = false;
    int tableCounter = 0;
    for (String line : lines) {
      if (line.startsWith("+--") || line.startsWith("+:-")) {
        tableCounter++;
        if (tableCounter == 1) {
          copy.append("\n");
          continue;
        } else if (tableCounter == 2) {
          for (String column : StringUtil.split(line, "+")) {
            copy.append("|").append(StringUtil.replace(column, "+", ""));
          }
          copy.append("|");
          copy.append("\n");
          continue;
        } else {
          tableCounter = 0;
          copy.append("\n");
          continue;
        }
      }

      if (line.startsWith("BIT#")) {
        bitTableMode = true;
        final String header = "| " + StringUtil.replace(StringUtil.replace(line, "BIT# ", "BIT# | "), "  ", " | ") + " |";
        copy.append(header);
        copy.append("\n");
        final int columns = StringUtil.countChars(header, '|');
        copy.append("|:-:|").append(StringUtil.repeat(":-:|", columns - 2));
        copy.append("\n");
        continue;
      }

      if (bitTableMode) {
        if (line.isEmpty()) {
          bitTableMode = false;
        } else {
          line = "| " + StringUtil.replace(line, "  ", " | ") + " |";
        }
      }

      line = line.replace("(unused)", "_(unused)_");

      if (BIT_DESCRIPTION.matcher(line).matches()) {
        int idx = line.indexOf('=');
        copy.append("### ").append(line.substring(0, idx).trim());
        copy.append("\n");
        copy.append(line.substring(idx + 1).trim());
      } else {
        copy.append(line);
      }

      copy.append("\n");
    }

    String copyTrim = StringUtil.trim(copy.toString());

    // format known register names and "REGx"
    Set<String> replacedNames = new HashSet<>();
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
      String fileName = register.getDescriptionFileName();
      if (replacedNames.add(fileName)) {
        copyTrim = copyTrim.replaceAll("\\b" + fileName + "\\b", "[" + fileName + "](" + fileName + ".md)");
      }

      String registerName = register.getName();
      if (replacedNames.add(registerName)) {
        copyTrim = copyTrim.replaceAll("\\b" + registerName + "\\b", "[" + registerName + "](" + register.getDescriptionFileName() + ".md)");
      }
    }
    return copyTrim;
  }

  @Nullable
  private Iterator<String> find(String registerName) throws IOException {
    final Iterator<String> iterator = M68kAmigaHardwareRegisterGeneratorTest.getLineIterator("@node ADKCON ADKCON");
    while (iterator.hasNext()) {
      String string = iterator.next();
      if (string.startsWith("@node " + registerName)) {
        iterator.next();
        iterator.next();
        return iterator;
      }
    }
    return null;
  }
}
