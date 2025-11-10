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

import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kCpu;

import java.util.Set;

final class M68kDocsGeneratorUtil {
  private M68kDocsGeneratorUtil() {
  }

  static void appendCpus(StringBuilder sb, Set<M68kCpu> cpus) {
    if (cpus == M68kCpu.GROUP_68000_UP) {
      sb.append(M68kBundle.message("cpu.group.GROUP_68000_UP"));
      sb.append("<br>");
      return;
    }
    if (cpus == M68kCpu.GROUP_68010_UP) {
      sb.append(M68kBundle.message("cpu.group.GROUP_68010_UP"));
      sb.append("<br>");
      return;
    }
    if (cpus == M68kCpu.GROUP_68020_UP) {
      sb.append(M68kBundle.message("cpu.group.GROUP_68020_UP"));
      sb.append("<br>");
      return;
    }


    sb.append("<table><tr>");
    for (M68kCpu value : M68kCpu.GROUP_68000_UP) {
      sb.append("<th>");
      sb.append(value.getCpuName());
      sb.append("</th>");
    }
    sb.append("</tr>");

    sb.append("<tr>");
    for (M68kCpu value : M68kCpu.GROUP_68000_UP) {
      sb.append("<td style=\"text-align:center;\">");
      if (cpus.contains(value)) {
        sb.append(M68kDocumentationUtil.CHECK_MARK);
      }
      sb.append("</td>");
    }
    sb.append("</tr>");
    sb.append("</table>");
  }
}
