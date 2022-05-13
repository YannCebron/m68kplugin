/*
 * Copyright 2022 The Authors
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

package com.yanncebron.m68kplugin.lang.psi.directive.impl;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.*;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kMacroStubIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Provides reference to macro.
 * <ol>
 *   <li>search in current file, must precede current element</li>
 *   <li>search in related files (includes), TODO: currently "all files"</li>
 * </ol>
 */
class M68kMacroCallReference extends PsiReferenceBase.Poly<M68kMacroCallDirectiveMixIn> implements EmptyResolveMessageProvider {

  private static final Resolver INSTANCE = new Resolver();

  private static class Resolver implements ResolveCache.PolyVariantResolver<M68kMacroCallReference> {
    @Override
    public ResolveResult @NotNull [] resolve(@NotNull M68kMacroCallReference m68kMacroCallReference, boolean incompleteCode) {
      M68kMacroCallDirectiveMixIn element = m68kMacroCallReference.getElement();
      String macroName = m68kMacroCallReference.getValue();
      int currentTextOffset = element.getTextOffset();

      List<M68kLabel> resolveResults = new SmartList<>();
      final CommonProcessors.CollectProcessor<M68kLabel> findLocalProcessor = new CommonProcessors.CollectProcessor<>(resolveResults) {
        @Override
        protected boolean accept(M68kLabel m68kLabel) {
          return m68kLabel.getTextOffset() < currentTextOffset;
        }
      };
      processMacrosInScope(findLocalProcessor, getCurrentFileSearchScope(element), macroName);
      if (!resolveResults.isEmpty()) {
        return PsiElementResolveResult.createResults(resolveResults);
      }

      final Processor<M68kLabel> findIncludeProcessor = Processors.cancelableCollectProcessor(resolveResults);
      processMacrosInScope(findIncludeProcessor, getIncludeSearchScope(element), macroName);
      return PsiElementResolveResult.createResults(resolveResults);
    }
  }


  M68kMacroCallReference(M68kMacroCallDirectiveMixIn m68kMacroCallDirectiveMixIn) {
    super(m68kMacroCallDirectiveMixIn, m68kMacroCallDirectiveMixIn.getMacroNameElement().getTextRange().shiftLeft(m68kMacroCallDirectiveMixIn.getTextOffset()), false);
  }

  @Override
  public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
    return ResolveCache.getInstance(getElement().getProject()).resolveWithCaching(this, INSTANCE, false, incompleteCode);
  }

  @Override
  public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
    final M68kMacroCallDirective macroCallDirective = M68kElementFactory.createMacroCall(getElement().getProject(), newElementName);
    getElement().getMacroNameElement().replace(macroCallDirective.getMacroNameElement());
    return getElement();
  }

  @NotNull
  @Override
  public Object @NotNull [] getVariants() {
    List<LookupElement> variants = new SmartList<>();

    final PsiFile currentFile = getElement().getContainingFile().getOriginalFile();
    final int currentTextOffset = getElement().getOriginalElement().getTextOffset();
    processMacrosInScope(label -> {
      final PsiFile containingFile = label.getContainingFile();
      boolean inCurrentFile = containingFile == currentFile;

      LookupElementBuilder builder = LookupElementBuilder.createWithIcon(label);
      if (inCurrentFile) {
        if (!(label.getTextOffset() < currentTextOffset)) return true;
        variants.add(PrioritizedLookupElement.withPriority(builder.bold(), 30));
      } else {
        variants.add(builder.withTypeText(SymbolPresentationUtil.getFilePathPresentation(containingFile), true));
      }
      return true;
    }, GlobalSearchScope.projectScope(getElement().getProject()), null);

    return variants.toArray();
  }

  @NotNull
  @Override
  public String getUnresolvedMessagePattern() {
    return M68kBundle.message("macrocall.directive.cannot.resolve", getValue());
  }

  private static void processMacrosInScope(Processor<M68kLabel> processor, GlobalSearchScope scope, @Nullable String macroName) {
    final Project project = scope.getProject();

    if (macroName != null) {
      ContainerUtil.process(getMacroStubLabels(macroName, project, scope), processor);
      return;
    }

    StubIndex.getInstance().processAllKeys(M68kMacroStubIndex.KEY,
      key -> {
        final Collection<M68kLabel> labels = getMacroStubLabels(key, project, scope);
        return ContainerUtil.process(labels, processor);
      }, scope, null);
  }

  private static GlobalSearchScope getIncludeSearchScope(PsiElement element) {
    final GlobalSearchScope notCurrentFile = GlobalSearchScope.notScope(getCurrentFileSearchScope(element));
    return GlobalSearchScope.projectScope(element.getProject()).intersectWith(notCurrentFile);
  }

  private static GlobalSearchScope getCurrentFileSearchScope(PsiElement element) {
    return GlobalSearchScope.fileScope(element.getContainingFile().getOriginalFile());
  }

  private static Collection<M68kLabel> getMacroStubLabels(String key, Project project, GlobalSearchScope scope) {
    return StubIndex.getElements(M68kMacroStubIndex.KEY, key, project, scope, M68kLabel.class);
  }
}
