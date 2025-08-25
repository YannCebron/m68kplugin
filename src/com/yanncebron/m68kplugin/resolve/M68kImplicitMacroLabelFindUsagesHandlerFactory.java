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

package com.yanncebron.m68kplugin.resolve;

import com.intellij.find.findUsages.FindUsagesHandler;
import com.intellij.find.findUsages.FindUsagesHandlerFactory;
import com.intellij.find.findUsages.FindUsagesOptions;
import com.intellij.model.search.SearchContext;
import com.intellij.model.search.SearchService;
import com.intellij.model.search.TextOccurrence;
import com.intellij.openapi.application.ReadAction;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.Processor;
import com.intellij.util.SmartList;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * "Find Usages" for implicit macro label name parameter.
 */
@SuppressWarnings("UnstableApiUsage")
final class M68kImplicitMacroLabelFindUsagesHandlerFactory extends FindUsagesHandlerFactory {

  @Override
  public boolean canFindUsages(@NotNull PsiElement element) {
    return element instanceof M68kMacroCallDirective;
  }

  @Override
  public FindUsagesHandler createFindUsagesHandler(@NotNull PsiElement element, boolean forHighlightUsages) {
    return new FindUsagesHandler(element) {

      @Override
      public @NotNull Collection<PsiReference> findReferencesToHighlight(@NotNull PsiElement target, @NotNull SearchScope searchScope) {
        String label = M68kImplicitMacroLabelResolver.getFirstParameterText((M68kMacroCallDirective) target);

        List<PsiReference> refs = new SmartList<>();
        processLabels(textOccurrence -> {
          M68kLabelRefExpression labelRefExpression = PsiTreeUtil.getParentOfType(textOccurrence.getElement(), M68kLabelRefExpression.class);
          assert labelRefExpression != null;
          refs.add(labelRefExpression.getReference());
          return true;
        }, searchScope, label);
        return refs;
      }

      @Override
      public boolean processElementUsages(@NotNull PsiElement element, @NotNull Processor<? super UsageInfo> processor, @NotNull FindUsagesOptions options) {
        String label = M68kImplicitMacroLabelResolver.getFirstParameterText((M68kMacroCallDirective) element);

        processLabels(textOccurrence -> {
          M68kMacroCallDirective macroCallDirective = PsiTreeUtil.getParentOfType(textOccurrence.getElement(), M68kMacroCallDirective.class);
          if (macroCallDirective != null) return true; // skip declaration

          M68kLabelRefExpression labelRefExpression = PsiTreeUtil.getParentOfType(textOccurrence.getElement(), M68kLabelRefExpression.class);
          assert labelRefExpression != null;
          return processor.process(new UsageInfo(labelRefExpression));
        }, options.searchScope, label);

        return true;
      }

      private void processLabels(Processor<? super TextOccurrence> processor, SearchScope searchScope, String label) {
        ReadAction.run(() -> SearchService.getInstance().searchWord(getProject(), label)
          .inContexts(SearchContext.IN_CODE)
          .caseSensitive(true)
          .inScope(searchScope)
          .restrictFileTypes(M68kFileType.INSTANCE)
          .buildLeafOccurrenceQuery()
          .forEach(processor));
      }

    };
  }

}
