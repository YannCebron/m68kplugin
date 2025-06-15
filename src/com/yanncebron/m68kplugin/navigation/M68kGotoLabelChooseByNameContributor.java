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

package com.yanncebron.m68kplugin.navigation;

import com.intellij.navigation.ChooseByNameContributorEx;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.Processor;
import com.intellij.util.indexing.DumbModeAccessType;
import com.intellij.util.indexing.FindSymbolParameters;
import com.intellij.util.indexing.IdFilter;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kStubIndexKeys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kGotoLabelChooseByNameContributor implements ChooseByNameContributorEx, DumbAware {

  @Override
  public void processNames(@NotNull Processor<? super String> processor, @NotNull GlobalSearchScope scope, @Nullable IdFilter filter) {
    DumbModeAccessType.RELIABLE_DATA_ONLY.ignoreDumbMode(() ->
      StubIndex.getInstance().processAllKeys(M68kStubIndexKeys.LABEL, processor, scope, filter)
    );
  }

  @Override
  public void processElementsWithName(@NotNull String name, @NotNull Processor<? super NavigationItem> processor, @NotNull FindSymbolParameters parameters) {
    DumbModeAccessType.RELIABLE_DATA_ONLY.ignoreDumbMode(() ->
      StubIndex.getInstance().processElements(M68kStubIndexKeys.LABEL, name,
        parameters.getProject(), parameters.getSearchScope(), parameters.getIdFilter(), M68kLabel.class,
        label -> processor.process(new M68kGotoLabelNavigationItem(label)))
    );
  }

}
