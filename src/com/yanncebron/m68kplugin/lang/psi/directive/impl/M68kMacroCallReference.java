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

package com.yanncebron.m68kplugin.lang.psi.directive.impl;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
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
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kMacroStubIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides reference to macro.
 * <ol>
 *   <li>search in current file, must precede current element</li>
 *   <li>search in related files (includes), TODO: currently "all files"</li>
 * </ol>
 */
class M68kMacroCallReference extends PsiReferenceBase.Poly<M68kMacrocallDirectiveMixIn> implements EmptyResolveMessageProvider {

  private static final Resolver INSTANCE = new Resolver();

  private static class Resolver implements ResolveCache.PolyVariantResolver<M68kMacroCallReference> {
    @Override
    public ResolveResult @NotNull [] resolve(@NotNull M68kMacroCallReference m68kMacroCallReference, boolean incompleteCode) {
      final M68kMacrocallDirectiveMixIn element = m68kMacroCallReference.getElement();
      String macroName = m68kMacroCallReference.getValue();

      List<M68kLabel> resolveResults = new SmartList<>();
      final CommonProcessors.CollectProcessor<M68kLabel> findLocalProcessor = new CommonProcessors.CollectProcessor<M68kLabel>(resolveResults) {
        @Override
        protected boolean accept(M68kLabel m68kLabel) {
          return m68kLabel.getTextOffset() < element.getTextOffset();
        }
      };
      processMacrosInScope(findLocalProcessor, getCurrentFileSearchScope(element), macroName);
      if (!resolveResults.isEmpty()) {
        return PsiElementResolveResult.createResults(resolveResults);
      }

      final CommonProcessors.CollectProcessor<M68kLabel> findIncludeProcessor = new CommonProcessors.CollectProcessor<>(resolveResults);
      processMacrosInScope(findIncludeProcessor, getIncludeSearchScope(element), macroName);
      return PsiElementResolveResult.createResults(resolveResults);
    }
  }


  M68kMacroCallReference(M68kMacrocallDirectiveMixIn m68kMacrocallDirectiveMixIn, ASTNode idNode) {
    super(m68kMacrocallDirectiveMixIn, idNode.getTextRange().shiftLeft(m68kMacrocallDirectiveMixIn.getTextOffset()), false);
  }

  @Override
  public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
    return ResolveCache.getInstance(myElement.getProject()).resolveWithCaching(this, INSTANCE, false, incompleteCode);
  }

  @Override
  public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
    final ASTNode idNode = getElement().getNode().findChildByType(M68kTokenTypes.MACRO_CALL_ID);
    assert idNode != null;
    final M68kMacroCallDirective macroCallDirective = M68kElementFactory.createMacroCall(getElement().getProject(), newElementName);
    getElement().getNode().replaceChild(idNode, macroCallDirective.getFirstChild().getNode());
    return getElement();
  }

  @NotNull
  @Override
  public Object @NotNull [] getVariants() {
    List<LookupElement> variants = new SmartList<>();
    processMacrosInScope(m68kLabel -> {
      if (!(m68kLabel.getTextOffset() < getElement().getOriginalElement().getTextOffset())) return true;
      variants.add(PrioritizedLookupElement.withPriority(LookupElementBuilder.createWithIcon(m68kLabel).bold(), 30));
      return true;
    }, getCurrentFileSearchScope(getElement()), null);

    processMacrosInScope(m68kLabel -> {
      final PsiFile containingFile = m68kLabel.getContainingFile();
      final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(m68kLabel)
        .withTypeText(SymbolPresentationUtil.getFilePathPresentation(containingFile), true);
      variants.add(builder);
      return true;
    }, getIncludeSearchScope(getElement()), null);
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

    List<String> allKeys = new ArrayList<>();
    StubIndex.getInstance().processAllKeys(M68kMacroStubIndex.KEY, Processors.cancelableCollectProcessor(allKeys), scope, null);
    for (String key : allKeys) {
      if (!ContainerUtil.process(getMacroStubLabels(key, project, scope), processor)) return;
    }
  }

  private static GlobalSearchScope getIncludeSearchScope(PsiElement element) {
    final GlobalSearchScope notCurrentFile = GlobalSearchScope.notScope(getCurrentFileSearchScope(element));
    return GlobalSearchScope.allScope(element.getProject()).intersectWith(notCurrentFile);
  }

  private static GlobalSearchScope getCurrentFileSearchScope(PsiElement element) {
    return GlobalSearchScope.fileScope(element.getContainingFile().getOriginalFile());
  }

  private static Collection<M68kLabel> getMacroStubLabels(String key, Project project, GlobalSearchScope scope) {
    return StubIndex.getElements(M68kMacroStubIndex.KEY, key, project, scope, M68kLabel.class);
  }
}