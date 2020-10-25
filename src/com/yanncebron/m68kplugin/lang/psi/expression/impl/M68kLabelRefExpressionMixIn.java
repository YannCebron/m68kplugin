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
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.CommonProcessors;
import com.intellij.util.Processor;
import com.intellij.util.SmartList;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kEquDirectiveBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Provides reference to label.
 * <ol>
 *   <li>if local label, search backwards, then forwards for local label only - until encountering global label ("parent scope")</li>
 *   <li>global label in current file</li>
 *   <li>TODO in resolve scope (includes)</li>
 * </ol>
 * <p>
 * This allows for both correct scoping of local labels and optimal performance, as resolving can be optimized for each type.
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
          protected boolean accept(M68kLabel m68kLabel) {
            return Comparing.strEqual(labelName, m68kLabel.getName());
          }
        };
        processLabels(findProcessor);
        return findProcessor.getFoundValue();
      }

      @NotNull
      @Override
      public Object[] getVariants() {
        List<LookupElement> variants = new SmartList<>();

        processLocalLabels(localLabel -> {
          final LookupElementBuilder builder = LookupElementBuilder.create(localLabel, "." + localLabel.getName())
            .withIcon(localLabel.getIcon(0));
          variants.add(PrioritizedLookupElement.withPriority(builder, 50));
          return true;
        });

        processLabels(m68kLabel -> {
          variants.add(LookupElementBuilder.createWithIcon(m68kLabel));
          return true;
        });

        return variants.toArray();
      }

      private void processLabels(Processor<M68kLabel> processor) {
        getElement().getContainingFile().acceptChildren(new M68kVisitor() {
          @Override
          public void visitLabel(@NotNull M68kLabel o) {
            processor.process(o);
          }

          @Override
          public void visitEquDirectiveBase(@NotNull M68kEquDirectiveBase o) {
            processor.process(o.getLabel());
          }
        });

      }

      private void processLocalLabels(Processor<M68kLocalLabel> processor) {
        Processor<M68kPsiElement> localLabelProcessor = m68kPsiElement -> {
          if (m68kPsiElement instanceof M68kLocalLabel) {
            return processor.process((M68kLocalLabel) m68kPsiElement);
          }
          return true;
        };

        final PsiElement startElement = getElement().getParent();
        if (!M68kPsiTreeUtil.processSiblingsBackwards(startElement, localLabelProcessor, M68kLabel.class)) return;
        M68kPsiTreeUtil.processSiblingsForwards(startElement, localLabelProcessor, M68kLabel.class);
      }

    };
  }

}
