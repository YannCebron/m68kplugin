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
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.util.CommonProcessors;
import com.intellij.util.Processor;
import com.intellij.util.Processors;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kEndmDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kLabelStubIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

/**
 * Reference to label.
 * <p>
 * Resolve mechanism (all results cached):
 * <ol>
 *   <li>local label: search backwards, then forwards for local labels only - until encountering global label/macro boundary ("parent scope"); first match</li>
 *   <li>global label: search in current file, if not found in included (currently "all other files"); all matches</li>
 * </ol>
 * <p>
 * This allows for both correct scoping of local labels and optimal performance, as resolving can be optimized for each type.
 * </p>
 * <p>
 * In completion variants, local labels and labels from the current file are prioritized and shown in bold.
 * </p>
 */
class M68kLabelReference extends PsiReferenceBase.Poly<M68kLabelRefExpressionMixIn> implements EmptyResolveMessageProvider {

  private static class Resolver implements ResolveCache.PolyVariantResolver<M68kLabelReference> {

    private static final Resolver INSTANCE = new Resolver();

    @Override
    public ResolveResult @NotNull [] resolve(@NotNull M68kLabelReference m68kLabelReference, boolean incompleteCode) {
      PsiElement psiElement = m68kLabelReference.getElement();
      String labelName = m68kLabelReference.getValue();

      final M68kLocalLabelMode localLabelMode = M68kLocalLabelMode.find(labelName);
      if (localLabelMode != null) {
        final CommonProcessors.FindProcessor<M68kLocalLabel> findLocalProcessor = new CommonProcessors.FindProcessor<M68kLocalLabel>() {
          @Override
          protected boolean accept(M68kLocalLabel localLabel) {
            return labelName.equals(localLabelMode.getPatchedName(localLabel));
          }
        };
        processLocalLabels(psiElement, localLabelMode, findLocalProcessor);
        if (findLocalProcessor.isFound()) {
          return PsiElementResolveResult.createResults(findLocalProcessor.getFoundValue());
        }
        return ResolveResult.EMPTY_ARRAY;
      }

      List<M68kLabel> resolveResults = new SmartList<>();
      final Processor<M68kLabel> processor = Processors.cancelableCollectProcessor(resolveResults);
      processLabelsInScope(processor, getCurrentFileSearchScope(psiElement), labelName);
      if (!resolveResults.isEmpty()) {
        return PsiElementResolveResult.createResults(resolveResults);
      }

      processLabelsInScope(processor, getIncludeSearchScope(psiElement), labelName);
      return PsiElementResolveResult.createResults(resolveResults);
    }
  }

  M68kLabelReference(M68kLabelRefExpressionMixIn element) {
    super(element);
  }

  @Override
  public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
    return ResolveCache.getInstance(myElement.getProject()).resolveWithCaching(this, Resolver.INSTANCE, false, incompleteCode);
  }

  @NotNull
  @Override
  public Object @NotNull [] getVariants() {
    List<LookupElement> variants = new SmartList<>();

    processLocalLabels(getElement(), null, localLabel -> {
      M68kLocalLabelMode localLabelMode = M68kLocalLabelMode.find(localLabel.getText());
      assert localLabelMode != null;
      final LookupElementBuilder builder = LookupElementBuilder.create(localLabel, localLabelMode.getPatchedName(localLabel))
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
    }, getCurrentFileSearchScope(getElement()), null);

    processLabelsInScope(label -> {
      final PsiFile containingFile = label.getContainingFile();
      final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(label)
        .withTailText(getTailText(label), true)
        .withTypeText(SymbolPresentationUtil.getFilePathPresentation(containingFile), true);
      variants.add(builder);
      return true;
    }, getIncludeSearchScope(getElement()), null);

    return variants.toArray();
  }

  @NotNull
  private String getTailText(M68kLabel label) {
    final String value = label.getValue();
    if (value == null) return "";
    return " " + value;
  }

  private static void processLocalLabels(PsiElement element, @Nullable M68kLocalLabelMode localLabelMode, Processor<M68kLocalLabel> processor) {
    Processor<M68kPsiElement> localLabelProcessor = m68kPsiElement -> {
      if (m68kPsiElement instanceof M68kLocalLabel &&
        (localLabelMode == null || localLabelMode.matches(m68kPsiElement.getText()))) {
        return processor.process((M68kLocalLabel) m68kPsiElement);
      }
      return true;
    };

    final M68kPsiElement startElement = M68kPsiTreeUtil.getContainingInstructionOrDirective(element);
    assert startElement != null : element.getText();

    if (!M68kPsiTreeUtil.processSiblingsBackwards(
      startElement, localLabelProcessor,
      M68kLabel.class, M68kMacroDirective.class)) {
      return;
    }
    M68kPsiTreeUtil.processSiblingsForwards(
      startElement, localLabelProcessor,
      M68kLabel.class, M68kEndmDirective.class);
  }

  private static void processLabelsInScope(Processor<M68kLabel> processor, GlobalSearchScope scope, @Nullable String labelName) {
    final Project project = scope.getProject();

    if (labelName != null) {
      ContainerUtil.process(getStubLabels(labelName, project, scope), processor);
      return;
    }

    StubIndex.getInstance().processAllKeys(M68kLabelStubIndex.KEY,
      key -> {
        final Collection<M68kLabel> labels = getStubLabels(key, project, scope);
        return ContainerUtil.process(labels, processor);
      }, scope, null);
  }

  private static final EnumSet<M68kLabelBase.LabelKind> NON_RELEVANT_LABEL_KINDS = EnumSet.of(M68kLabelBase.LabelKind.EQUR, M68kLabelBase.LabelKind.REG, M68kLabelBase.LabelKind.MACRO);
  private static final Condition<M68kLabel> RELEVANT_LABEL_CONDITION = m68kLabel -> !NON_RELEVANT_LABEL_KINDS.contains(m68kLabel.getLabelKind());

  private static Collection<M68kLabel> getStubLabels(String key, Project project, GlobalSearchScope scope) {
    return ContainerUtil.filter(StubIndex.getElements(M68kLabelStubIndex.KEY, key, project, scope, M68kLabel.class), RELEVANT_LABEL_CONDITION);
  }

  private static GlobalSearchScope getIncludeSearchScope(PsiElement element) {
    final GlobalSearchScope notCurrentFile = GlobalSearchScope.notScope(getCurrentFileSearchScope(element));
    return GlobalSearchScope.projectScope(element.getProject()).intersectWith(notCurrentFile);
  }

  private static GlobalSearchScope getCurrentFileSearchScope(PsiElement element) {
    return GlobalSearchScope.fileScope(element.getContainingFile().getOriginalFile());
  }

  @Override
  public @InspectionMessage @NotNull String getUnresolvedMessagePattern() {
    return M68kBundle.message("label.cannot.resolve", getValue());
  }
}
