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

package com.yanncebron.m68kplugin.lang.completion;

import com.intellij.codeInsight.completion.PlainTextSymbolCompletionContributor;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.Processors;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kLabelStubIndex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class M68kPlainTextSymbolCompletionContributor implements PlainTextSymbolCompletionContributor {

  @Override
  public @NotNull Collection<LookupElement> getLookupElements(@NotNull PsiFile file, int invocationCount, @NotNull String prefix) {
    if (!(file instanceof M68kFile)) return Collections.emptyList();

    final GlobalSearchScope scope = GlobalSearchScope.fileScope(file);
    Project project = file.getProject();

    List<String> names = new ArrayList<>();
    StubIndex.getInstance().processAllKeys(M68kLabelStubIndex.KEY, Processors.cancelableCollectProcessor(names), scope, null);

    final String filePath = SymbolPresentationUtil.getFilePathPresentation(file);
    List<LookupElement> variants = new ArrayList<>();
    for (String name : names) {
      final Collection<M68kLabel> labels = StubIndex.getElements(M68kLabelStubIndex.KEY, name, project, scope, M68kLabel.class);
      for (M68kLabel label : labels) {
        variants.add(LookupElementBuilder.createWithIcon(label).withTypeText(filePath, true));
      }
    }
    return variants;
  }
}
