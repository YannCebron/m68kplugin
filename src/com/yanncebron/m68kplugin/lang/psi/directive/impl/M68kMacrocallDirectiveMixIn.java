/*
 * Copyright 2020 The Authors
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
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.CommonProcessors;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.Processor;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kLabelStubIndex;
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
 *   <li>search backwards in current file</li>
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
    final ASTNode idNode = getNode().findChildByType(M68kTokenTypes.ID);
    assert idNode != null;

    return new MacroCallReference(idNode);
  }

  private class MacroCallReference extends PsiReferenceBase<M68kMacrocallDirectiveMixIn> implements EmptyResolveMessageProvider {

    public MacroCallReference(ASTNode idNode) {
      super(M68kMacrocallDirectiveMixIn.this, idNode.getTextRange().shiftLeft(M68kMacrocallDirectiveMixIn.this.getTextOffset()));
    }

    @Nullable
    @Override
    public PsiElement resolve() {
      String name = getValue();

      final CommonProcessors.FindProcessor<M68kLabel> findProcessor = new CommonProcessors.FindProcessor<M68kLabel>() {
        @Override
        protected boolean accept(M68kLabel m68kLabel) {
          return name.equals(m68kLabel.getName());
        }
      };
      processLocalMacros(findProcessor);
      if (findProcessor.isFound()) {
        return findProcessor.getFoundValue();
      }

      // cache result from other files to speedup highlighting
      final Map<String, PsiElement> cachedValue = CachedValuesManager.getCachedValue(getContainingFile(), MACRO_NAME_CV_PROVIDER);
      return cachedValue.computeIfAbsent(name, s -> {
        processIncludeMacros(findProcessor, name);
        return findProcessor.getFoundValue();
      });
    }

    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
      final ASTNode idNode = getNode().findChildByType(M68kTokenTypes.ID);
      assert idNode != null;
      final M68kLabel label = M68kElementFactory.createLabel(getProject(), newElementName);
      getNode().replaceChild(idNode, label.getFirstChild().getNode());
      return getElement();
    }

    @NotNull
    @Override
    public Object @NotNull [] getVariants() {
      List<LookupElement> variants = new SmartList<>();
      processLocalMacros(m68kLabel -> {
        variants.add(PrioritizedLookupElement.withPriority(LookupElementBuilder.createWithIcon(m68kLabel), 30));
        return true;
      });

      processIncludeMacros(m68kLabel -> {
        final PsiFile containingFile = m68kLabel.getContainingFile();
        final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(m68kLabel)
          .withTypeText(SymbolPresentationUtil.getFilePathPresentation(containingFile), true);
        variants.add(builder);
        return true;
      }, null);
      return variants.toArray();
    }

    @NotNull
    @Override
    public String getUnresolvedMessagePattern() {
      return M68kBundle.message("macrocall.directive.cannot.resolve", getValue());
    }

    private void processLocalMacros(Processor<M68kLabel> processor) {
      M68kPsiTreeUtil.processSiblingsBackwards(getElement(), m68kPsiElement -> {
        if (m68kPsiElement instanceof M68kMacroDirective) {
          return processor.process(((M68kMacroDirective) m68kPsiElement).getLabel());
        }
        return true;
      });
    }

    private void processIncludeMacros(Processor<M68kLabel> processor, @Nullable String macroName) {
      final Project project = getProject();
      final GlobalSearchScope includeSearchScope = getIncludeSearchScope(project);

      if (macroName != null) {
        ContainerUtil.process(getStubLabels(macroName, project, includeSearchScope), processor);
        return;
      }

      List<String> allKeys = new ArrayList<>(500);
      StubIndex.getInstance().processAllKeys(M68kLabelStubIndex.KEY, new CommonProcessors.CollectProcessor<>(allKeys), includeSearchScope, null);
      for (String key : allKeys) {
        if (!ContainerUtil.process(getStubLabels(key, project, includeSearchScope), processor)) return;
      }
    }

    private GlobalSearchScope getIncludeSearchScope(Project project) {
      final PsiFile containingFile = getContainingFile().getOriginalFile();
      final GlobalSearchScope notCurrentFile = GlobalSearchScope.notScope(GlobalSearchScope.fileScope(containingFile));
      return GlobalSearchScope.allScope(project).intersectWith(notCurrentFile);
    }

    // todo dedicated macro stub index
    private Collection<M68kLabel> getStubLabels(String key, Project project, GlobalSearchScope scope) {
      final Collection<M68kLabel> labels = StubIndex.getElements(M68kLabelStubIndex.KEY, key, project, scope, M68kLabel.class);
      return ContainerUtil.filter(labels, m68kLabel -> PsiTreeUtil.getParentOfType(m68kLabel, M68kMacroDirective.class) != null);
    }
  }
}
