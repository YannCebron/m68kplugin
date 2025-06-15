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

package com.yanncebron.m68kplugin.compiler;

import com.intellij.codeInsight.daemon.impl.PsiElementListNavigator;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.filters.HyperlinkInfoFactory;
import com.intellij.ide.DataManager;
import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.PathUtilRt;
import com.intellij.util.concurrency.NonUrgentExecutor;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kStubIndexKeys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Navigate to vasm messages containing filename/line number information.
 * <p>
 * Location is resolved in following order:
 *
 * <ol>
 *   <li>to file: {@code [...] line 1 of "$PATH$"}: [...]</li>
 *   <li>to global label: {@code  [...] line 2 of "macroName": [...]}</li>
 * </ol>
 */
class M68kVasmLineInLocationFilter implements Filter, DumbAware {

  private static final Pattern PATTERN = Pattern.compile(".*line (\\d+) of \"([0-9 a-z_A-Z\\-\\\\./]+)\".*");

  private final Project project;

  M68kVasmLineInLocationFilter(Project project) {
    this.project = project;
  }

  @Nullable
  @Override
  public Result applyFilter(@NotNull String line, int entireLength) {
    final int lineIdx = StringUtil.indexOf(line, "line");
    if (lineIdx == -1) return null;

    if (StringUtil.indexOf(line, "of \"") == -1) {
      return null;
    }

    final Matcher matcher = PATTERN.matcher(line.substring(0, line.length() - 1)); // strip NL
    if (!matcher.matches()) {
      return null;
    }

    final String lineText = matcher.group(1);
    int lineNumber = 0;
    try {
      lineNumber = Integer.parseInt(lineText) - 1;
    } catch (NumberFormatException ignored) {
    }

    final String location = matcher.group(2);

    int initialOffset = entireLength - line.length();

    final Collection<VirtualFile> matchingFiles =
      FilenameIndex.getVirtualFilesByName(PathUtilRt.getFileName(location), GlobalSearchScope.allScope(project));
    if (!matchingFiles.isEmpty()) {
      final HyperlinkInfo filesHyperlinkInfo = HyperlinkInfoFactory.getInstance()
        .createMultipleFilesHyperlinkInfo(new ArrayList<>(matchingFiles), lineNumber, project);
      return new Result(initialOffset + lineIdx, initialOffset + matcher.end(2) + 1, filesHyperlinkInfo);
    }

    return new Result(initialOffset + matcher.start(2), initialOffset + matcher.end(2), createLabelHyperlinkInfo(location));
  }

  @NotNull
  private HyperlinkInfo createLabelHyperlinkInfo(String location) {
    return hyperlinkInfo -> DataManager.getInstance()
      .getDataContextFromFocusAsync()
      .onSuccess(context ->
        ReadAction.nonBlocking(()
            -> StubIndex.getElements(M68kStubIndexKeys.LABEL, location, project, GlobalSearchScope.allScope(project), M68kLabel.class))
          .inSmartMode(project)
          .finishOnUiThread(ModalityState.current(), elements -> {
            Editor editor = CommonDataKeys.EDITOR.getData(context);
            if (editor == null) return;

            if (elements.isEmpty()) {
              HintManager.getInstance().showErrorHint(editor, M68kBundle.message("vasm.line.in.location.filter.cannot.resolve.symbol", location));
              return;
            }

            PsiElementListNavigator.openTargets(
              editor,
              elements.toArray(NavigatablePsiElement.EMPTY_NAVIGATABLE_ELEMENT_ARRAY),
              M68kBundle.message("vasm.line.in.location.filter.matching.elements"),
              M68kBundle.message("vasm.line.in.location.filter.matching.elements.for", location),
              new DefaultPsiElementCellRenderer(),
              null);
          })
          .submit(NonUrgentExecutor.getInstance())
      );
  }
}
