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

package com.yanncebron.m68kplugin.lang.psi.expression.impl;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
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
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.CommonProcessors;
import com.intellij.util.Processor;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kEquDirectiveBase;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kLabelStubIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides reference to label.
 * <ol>
 *   <li>if local label, search backwards, then forwards for local labels only - until encountering label ("parent scope")</li>
 *   <li>if label, search backwards, then forwards in the current file</li>
 *   <li>if label, search in related files (includes), TODO: currently "all files"</li>
 * </ol>
 * <p>
 * This allows for both correct scoping of local labels and optimal performance, as resolving can be optimized for each type.
 * </p>
 * <p>
 * In completion, local labels and labels from the current file are prioritized and shown in bold.
 * </p>
 */
abstract class M68kLabelRefExpressionMixIn extends ASTWrapperPsiElement {

  protected M68kLabelRefExpressionMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiReference getReference() {
    return new PsiReferenceBase<M68kLabelRefExpressionMixIn>(this) {
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

        final CommonProcessors.FindProcessor<M68kLabel> findProcessor = new CommonProcessors.FindProcessor<M68kLabel>() {
          @Override
          protected boolean accept(M68kLabel label) {
            return labelName.equals(label.getName());
          }
        };
        processLabels(findProcessor);
        if (findProcessor.isFound()) {
          return findProcessor.getFoundValue();
        }

        processIncludeLabels(findProcessor, labelName);
        return findProcessor.getFoundValue();
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

        processLabels(label -> {
          final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(label)
            .bold();
          variants.add(PrioritizedLookupElement.withPriority(builder, 30));
          return true;
        });

        processIncludeLabels(label -> {
          final PsiFile containingFile = label.getContainingFile();
          final LookupElementBuilder builder = LookupElementBuilder.createWithIcon(label)
            .withTypeText(SymbolPresentationUtil.getFilePathPresentation(containingFile), true);
          variants.add(builder);
          return true;
        }, null);

        return variants.toArray();
      }

      private void processLabels(Processor<M68kLabel> processor) {
        Processor<M68kPsiElement> labelProcessor = m68kPsiElement -> {
          if (m68kPsiElement instanceof M68kLabel) {
            return processor.process((M68kLabel) m68kPsiElement);
          }
          if (m68kPsiElement instanceof M68kEquDirectiveBase) {
            return processor.process(((M68kEquDirectiveBase) m68kPsiElement).getLabel());
          }
          return true;
        };

        processCurrentFileLabels(labelProcessor);
      }

      private void processLocalLabels(Processor<M68kLocalLabel> processor) {
        Processor<M68kPsiElement> localLabelProcessor = m68kPsiElement -> {
          if (m68kPsiElement instanceof M68kLocalLabel) {
            return processor.process((M68kLocalLabel) m68kPsiElement);
          }
          return true;
        };

        processCurrentFileLabels(localLabelProcessor, M68kLabel.class);
      }

      @SafeVarargs
      private final void processCurrentFileLabels(Processor<M68kPsiElement> labelProcessor,
                                                  Class<? extends M68kPsiElement>... stopAtElements) {
        PsiElement startElement =
          PsiTreeUtil.getParentOfType(getElement(),
            M68kInstruction.class,
            M68kDirective.class,
            M68kConditionalAssemblyDirective.class);
        assert startElement != null : getElement().getText();

        if (!M68kPsiTreeUtil.processSiblingsBackwards(startElement, labelProcessor, stopAtElements)) return;
        M68kPsiTreeUtil.processSiblingsForwards(startElement, labelProcessor, stopAtElements);
      }


      private void processIncludeLabels(Processor<M68kLabel> processor, @Nullable String labelName) {
        final Project project = getProject();
        final GlobalSearchScope includeSearchScope = getIncludeSearchScope(project);

        if (labelName != null) {
          ContainerUtil.process(getStubLabels(labelName, project, includeSearchScope), processor);
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

      private Collection<M68kLabel> getStubLabels(String key, Project project, GlobalSearchScope scope) {
        return StubIndex.getElements(M68kLabelStubIndex.KEY, key, project, scope, M68kLabel.class);
      }

    };
  }

}
