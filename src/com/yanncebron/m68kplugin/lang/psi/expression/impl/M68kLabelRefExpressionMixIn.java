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

package com.yanncebron.m68kplugin.lang.psi.expression.impl;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInspection.util.InspectionMessage;
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
import com.intellij.util.CommonProcessors;
import com.intellij.util.Processor;
import com.intellij.util.Processors;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kLabelStubIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides reference to label.
 * <ol>
 *   <li>if local label, search backwards, then forwards for local labels only - until encountering global label ("parent scope")</li>
 *   <li>if label, search in the current file, then in included (TODO: currently "all other files")</li>
 * </ol>
 * <p>
 * This allows for both correct scoping of local labels and optimal performance, as resolving can be optimized for each type.
 * </p>
 * <p>
 * In completion variants, local labels and labels from the current file are prioritized and shown in bold.
 * </p>
 */
abstract class M68kLabelRefExpressionMixIn extends ASTWrapperPsiElement {

  private static final CachedValueProvider<Map<String, PsiElement>> LABEL_NAME_CV_PROVIDER = () ->
    CachedValueProvider.Result.create(new ConcurrentHashMap<>(), PsiModificationTracker.MODIFICATION_COUNT);

  protected M68kLabelRefExpressionMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiReference getReference() {
    return new LabelReference(this);
  }

  private static class LabelReference extends PsiReferenceBase<M68kLabelRefExpressionMixIn> implements EmptyResolveMessageProvider {

    LabelReference(M68kLabelRefExpressionMixIn element) {
      super(element);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
      final String labelName = getValue();
      if (labelName.startsWith(".")) {
        final CommonProcessors.FindProcessor<M68kLocalLabel> findLocalProcessor = new CommonProcessors.FindProcessor<M68kLocalLabel>() {
          @Override
          protected boolean accept(M68kLocalLabel localLabel) {
            return labelName.equals("." + localLabel.getName());
          }
        };
        processLocalLabels(findLocalProcessor);
        return findLocalProcessor.getFoundValue();
      }

      final Map<String, PsiElement> cachedValue = CachedValuesManager.getCachedValue(getElement().getContainingFile(), LABEL_NAME_CV_PROVIDER);
      return cachedValue.computeIfAbsent(labelName, s -> {
        final CommonProcessors.FindProcessor<M68kLabel> processor = new CommonProcessors.FindFirstProcessor<>();
        processLabelsInScope(processor, getCurrentFileSearchScope(), labelName);
        if (processor.isFound()) {
          return processor.getFoundValue();
        }

        processLabelsInScope(processor, getIncludeSearchScope(), labelName);
        return processor.getFoundValue();
      });
    }

    @NotNull
    @Override
    public Object @NotNull [] getVariants() {
      List<LookupElement> variants = new SmartList<>();

      processLocalLabels(localLabel -> {
        final LookupElementBuilder builder = LookupElementBuilder.create(localLabel, "." + localLabel.getName())
          .withIcon(localLabel.getIcon(0))
          .bold();
        variants.add(PrioritizedLookupElement.withPriority(builder, 50));
        return true;
      });

      processLabelsInScope(label -> {
        final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(label)
          .withTailText(getTailText(label), true)
          .bold();
        variants.add(PrioritizedLookupElement.withPriority(builder, 30));
        return true;
      }, getCurrentFileSearchScope(), null);

      processLabelsInScope(label -> {
        final PsiFile containingFile = label.getContainingFile();
        final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(label)
          .withTailText(getTailText(label), true)
          .withTypeText(SymbolPresentationUtil.getFilePathPresentation(containingFile), true);
        variants.add(builder);
        return true;
      }, getIncludeSearchScope(), null);

      return variants.toArray();
    }

    @NotNull
    private String getTailText(M68kLabel label) {
      final String value = label.getValue();
      if (value == null) return "";
      return " " + value;
    }

    private void processLocalLabels(Processor<M68kLocalLabel> processor) {
      Processor<M68kPsiElement> localLabelProcessor = m68kPsiElement -> {
        if (m68kPsiElement instanceof M68kLocalLabel) {
          return processor.process((M68kLocalLabel) m68kPsiElement);
        }
        return true;
      };

      @SuppressWarnings("unchecked") Class<? extends M68kPsiElement>[] stopAtElements = new Class[]{M68kLabel.class};
      final M68kPsiElement startElement = M68kPsiTreeUtil.getContainingInstructionOrDirective(getElement());
      assert startElement != null : getElement().getText();

      if (!M68kPsiTreeUtil.processSiblingsBackwards(startElement, localLabelProcessor, stopAtElements)) return;
      M68kPsiTreeUtil.processSiblingsForwards(startElement, localLabelProcessor, stopAtElements);
    }

    private void processLabelsInScope(Processor<M68kLabel> processor, GlobalSearchScope scope, @Nullable String labelName) {
      final Project project = getElement().getProject();

      if (labelName != null) {
        ContainerUtil.process(getStubLabels(labelName, project, scope), processor);
        return;
      }

      List<String> allKeys = new ArrayList<>(500);
      StubIndex.getInstance().processAllKeys(M68kLabelStubIndex.KEY, Processors.cancelableCollectProcessor(allKeys), scope, null);
      for (String key : allKeys) {
        if (!ContainerUtil.process(getStubLabels(key, project, scope), processor)) return;
      }
    }

    private GlobalSearchScope getIncludeSearchScope() {
      final GlobalSearchScope notCurrentFile = GlobalSearchScope.notScope(getCurrentFileSearchScope());
      return GlobalSearchScope.allScope(getElement().getProject()).intersectWith(notCurrentFile);
    }

    private GlobalSearchScope getCurrentFileSearchScope() {
      return GlobalSearchScope.fileScope(getElement().getContainingFile().getOriginalFile());
    }

    private Collection<M68kLabel> getStubLabels(String key, Project project, GlobalSearchScope scope) {
      return StubIndex.getElements(M68kLabelStubIndex.KEY, key, project, scope, M68kLabel.class);
    }

    @Override
    public @InspectionMessage @NotNull String getUnresolvedMessagePattern() {
      return M68kBundle.message("label.cannot.resolve", getValue());
    }
  }
}
