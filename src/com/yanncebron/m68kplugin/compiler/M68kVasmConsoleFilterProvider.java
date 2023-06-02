/*
 * Copyright 2023 The Authors
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

import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import org.jetbrains.annotations.NotNull;

final class M68kVasmConsoleFilterProvider implements ConsoleFilterProvider {

  @NotNull
  @Override
  public Filter @NotNull [] getDefaultFilters(@NotNull Project project) {
    if (!FileTypeIndex.containsFileOfType(M68kFileType.INSTANCE, GlobalSearchScope.allScope(project))) {
      return Filter.EMPTY_ARRAY;
    }

    return new Filter[]{new M68kVasmLineInLocationFilter(project)};
  }

}
