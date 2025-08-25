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

import com.intellij.model.search.SearchContext;
import com.intellij.model.search.SearchService;
import com.intellij.openapi.progress.EmptyProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.inspections.M68kUnresolvedLabelReferenceInspection;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallParameter;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kStubIndexKeys;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Resolves to labels defined implicitly in macros defined by user.
 * <p>
 * **NOTE** The same label name can be defined by more than one macro or multiple times by the same one.
 *
 * @see M68kUnresolvedLabelReferenceInspection
 */
public final class M68kImplicitMacroLabelResolver {

  private M68kImplicitMacroLabelResolver() {
  }

  /**
   * Use only if no current context PSI is known (e.g., global navigation).
   */
  public static void processGlobalMacrosDefiningLabels(Processor<M68kMacroCallDirective> processor, GlobalSearchScope scope, Project project, @Nullable String labelName) {
    _processMacrosDefiningLabels(processor, scope, project, M68kUnresolvedLabelReferenceInspection.getLabelDefiningMacros(project), labelName);
  }

  public static void processMacrosDefiningLabels(Processor<M68kMacroCallDirective> processor, GlobalSearchScope scope, PsiElement psiElement, @Nullable String labelName) {
    _processMacrosDefiningLabels(processor, scope, psiElement.getProject(), M68kUnresolvedLabelReferenceInspection.getLabelDefiningMacros(psiElement), labelName);
  }

  public static void processMacrosPossiblyDefiningLabel(Processor<M68kMacroCallDirective> processor, Project project, @NotNull String labelName) {
    _processMacrosDefiningLabels(processor, ProjectScope.getProjectScope(project), project, StubIndex.getInstance().getAllKeys(M68kStubIndexKeys.MACRO, project), labelName);
  }

  private static void _processMacrosDefiningLabels(Processor<M68kMacroCallDirective> processor, GlobalSearchScope scope, Project project, Collection<String> macroNames, @Nullable String labelName) {
    if (macroNames.isEmpty()) {
      return;
    }

    if (labelName != null) {
      processByLabelName(processor, scope, project, macroNames, labelName);
      return;
    }

    for (String macroName : macroNames) {
      for (M68kLabel macro : StubIndex.getElements(M68kStubIndexKeys.MACRO, macroName, project, scope, M68kLabel.class)) {
        ReferencesSearch.search(macro, scope).forEach(psiReference -> {
          ProgressManager.checkCanceled();

          PsiElement element = psiReference.getElement();
          assert element instanceof M68kMacroCallDirective;
          M68kMacroCallDirective macroCallDirective = (M68kMacroCallDirective) element;
          if (macroCallDirective.getMacroCallParameterList().isEmpty()) {
            return true;
          }

          return processor.process(macroCallDirective);
        });
      }
    }
  }

  /**
   * Resolve to macro call via given labelName (faster than inspecting all macro call references with varying names).
   */
  @SuppressWarnings("UnstableApiUsage")
  private static void processByLabelName(Processor<M68kMacroCallDirective> processor, GlobalSearchScope scope, Project project, Collection<String> macroNames, @NotNull String labelName) {
    ProgressManager.getInstance().runProcess(() -> {
      SearchService.getInstance().searchWord(project, labelName)
        .inContexts(SearchContext.IN_CODE)
        .caseSensitive(true)
        .inScope(scope)
        .restrictFileTypes(M68kFileType.INSTANCE)
        .buildLeafOccurrenceQuery()
        .forEach(textOccurrence -> {
            M68kMacroCallDirective macroCallDirective = PsiTreeUtil.getParentOfType(textOccurrence.getElement(), M68kMacroCallDirective.class);
            if (macroCallDirective == null) return true;

            if (!macroNames.contains(macroCallDirective.getMacroNameElement().getText())) return true;
            if (labelName.equals(getFirstParameterText(macroCallDirective))) {
              return processor.process(macroCallDirective);
            }

            return true;
          }
        );
    }, new EmptyProgressIndicator());
  }

  public static @NonNls @NotNull String getFirstParameterText(M68kMacroCallDirective macroCallDirective) {
    M68kMacroCallParameter firstParameter = ContainerUtil.getFirstItem(macroCallDirective.getMacroCallParameterList());
    assert firstParameter != null;
    return firstParameter.getText();
  }

  public static @NotNull @NonNls String getImplicitMacroLabelLocationString(M68kMacroCallDirective macroCallDirective) {
    return macroCallDirective.getText().replaceAll("\\s{2,}", " ").trim();
  }
}
