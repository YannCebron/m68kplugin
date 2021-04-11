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
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides reference to macro.
 * <ol>
 *   <li>search in current file, must precede current element</li>
 *   <li>search in related files (includes), TODO: currently "all files"</li>
 * </ol>
 */
abstract class M68kMacrocallDirectiveMixIn extends ASTWrapperPsiElement {

  private static final CachedValueProvider<Map<String, PsiElement>> MACRO_NAME_CV_PROVIDER = () ->
    CachedValueProvider.Result.create(new ConcurrentHashMap<>(), PsiModificationTracker.MODIFICATION_COUNT);

  protected M68kMacrocallDirectiveMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiReference getReference() {
    final ASTNode idNode = getNode().findChildByType(M68kTokenTypes.MACRO_CALL_ID);
    assert idNode != null;

    return new MacroCallReference(idNode);
  }

  private class MacroCallReference extends PsiReferenceBase<M68kMacrocallDirectiveMixIn> implements EmptyResolveMessageProvider {

    MacroCallReference(ASTNode idNode) {
      super(M68kMacrocallDirectiveMixIn.this, idNode.getTextRange().shiftLeft(M68kMacrocallDirectiveMixIn.this.getTextOffset()));
    }

    @Nullable
    @Override
    public PsiElement resolve() {
      String macroName = getValue();

      final CommonProcessors.FindProcessor<M68kLabel> localFindProcessor = new CommonProcessors.FindProcessor<M68kLabel>() {
        @Override
        protected boolean accept(M68kLabel m68kLabel) {
          return m68kLabel.getTextOffset() < getElement().getTextOffset();
        }
      };
      processMacrosInScope(localFindProcessor, getCurrentFileSearchScope(), macroName);
      if (localFindProcessor.isFound()) {
        return localFindProcessor.getFoundValue();
      }

      // cache result from other files to speedup highlighting
      final Map<String, PsiElement> cachedValue = CachedValuesManager.getCachedValue(getContainingFile(), MACRO_NAME_CV_PROVIDER);
      return cachedValue.computeIfAbsent(macroName, s -> {
        final CommonProcessors.FindProcessor<M68kLabel> processor = new CommonProcessors.FindFirstProcessor<>();
        processMacrosInScope(processor, getIncludeSearchScope(), macroName);
        return processor.getFoundValue();
      });
    }

    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
      final ASTNode idNode = getNode().findChildByType(M68kTokenTypes.MACRO_CALL_ID);
      assert idNode != null;
      final M68kMacroCallDirective macroCallDirective = M68kElementFactory.createMacroCall(getProject(), newElementName);
      getNode().replaceChild(idNode, macroCallDirective.getFirstChild().getNode());
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
      }, getCurrentFileSearchScope(), null);

      processMacrosInScope(m68kLabel -> {
        final PsiFile containingFile = m68kLabel.getContainingFile();
        final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(m68kLabel)
          .withTypeText(SymbolPresentationUtil.getFilePathPresentation(containingFile), true);
        variants.add(builder);
        return true;
      }, getIncludeSearchScope(), null);
      return variants.toArray();
    }

    @NotNull
    @Override
    public String getUnresolvedMessagePattern() {
      return M68kBundle.message("macrocall.directive.cannot.resolve", getValue());
    }

    private void processMacrosInScope(Processor<M68kLabel> processor, GlobalSearchScope scope, @Nullable String macroName) {
      final Project project = getProject();

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

    private GlobalSearchScope getIncludeSearchScope() {
      final GlobalSearchScope notCurrentFile = GlobalSearchScope.notScope(getCurrentFileSearchScope());
      return GlobalSearchScope.allScope(getProject()).intersectWith(notCurrentFile);
    }

    private GlobalSearchScope getCurrentFileSearchScope() {
      return GlobalSearchScope.fileScope(getContainingFile().getOriginalFile());
    }

    private Collection<M68kLabel> getMacroStubLabels(String key, Project project, GlobalSearchScope scope) {
      return StubIndex.getElements(M68kMacroStubIndex.KEY, key, project, scope, M68kLabel.class);
    }
  }
}
