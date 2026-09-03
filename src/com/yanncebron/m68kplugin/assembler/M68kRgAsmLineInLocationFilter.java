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

package com.yanncebron.m68kplugin.assembler;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.HyperlinkInfoFactory;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.PathUtilRt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class M68kRgAsmLineInLocationFilter implements Filter, DumbAware {

  private static final Pattern PATTERN = Pattern.compile("(.+):(\\d+):\\d+:.+");

  @NotNull
  private final Project project;

  M68kRgAsmLineInLocationFilter(@NotNull Project project) {
    this.project = project;
  }

  @Override
  public @Nullable Result applyFilter(@NotNull String line, int entireLength) {
    if (StringUtil.indexOf(line, " warning:") == -1 &&
      StringUtil.indexOf(line, " error:") == -1) {
      return null;
    }

    Matcher matcher = PATTERN.matcher(line.substring(0, line.length() - 1));
    if (!matcher.matches()) {
      return null;
    }

    String filePath = matcher.group(1);
    Collection<VirtualFile> matchingFiles =
      FilenameIndex.getVirtualFilesByName(PathUtilRt.getFileName(filePath), GlobalSearchScope.allScope(project));
    if (matchingFiles.isEmpty()) {
      return null;
    }

    int lineNumber = 0;
    try {
      lineNumber = Integer.parseInt(matcher.group(2)) - 1;
    } catch (NumberFormatException ignored) {
    }

    int initialOffset = entireLength - line.length();

    final HyperlinkInfo filesHyperlinkInfo = HyperlinkInfoFactory.getInstance()
      .createMultipleFilesHyperlinkInfo(new ArrayList<>(matchingFiles), lineNumber, project);
    return new Result(initialOffset, initialOffset + matcher.end(2), filesHyperlinkInfo);
  }
}
