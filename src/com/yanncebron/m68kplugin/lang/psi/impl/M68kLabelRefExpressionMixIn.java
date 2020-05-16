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

package com.yanncebron.m68kplugin.lang.psi.impl;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Function;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
        final List<M68kLabelBase> labels = getAllLabels();

        // todo hack for local label
        final String labelName = StringUtil.startsWithChar(getValue(), '.') ? getValue().substring(1) : getValue();
        return ContainerUtil.find(labels, m68kLabelBase -> labelName.equals(m68kLabelBase.getName()));
      }

      @NotNull
      private List<M68kLabelBase> getAllLabels() { // todo
        List<M68kLabelBase> labels = new SmartList<>();
        boolean inExpressionOrLabelDirective = PsiTreeUtil.getParentOfType(getElement(),
          M68kExpression.class, M68kIfdDirective.class, M68kIfndDirective.class) != null;
        getElement().getContainingFile().acceptChildren(new M68kVisitor() {
          @Override
          public void visitLabelBase(@NotNull M68kLabelBase o) {
            labels.add(o);
          }

          @Override
          public void visitEquDirectiveBase(@NotNull M68kEquDirectiveBase o) {
            if (inExpressionOrLabelDirective) labels.add(o.getLabel());
          }
        });
        return labels;
      }

      @NotNull
      @Override
      public Object[] getVariants() {
        return ContainerUtil.map2Array(getAllLabels(), LookupElement.class, new Function<M68kLabelBase, LookupElement>() {

          // todo show equ value in completion
          @Override
          public LookupElement fun(M68kLabelBase m68kLabelBase) {
            final LookupElementBuilder withIcon = LookupElementBuilder.createWithIcon(m68kLabelBase);// todo

            if (m68kLabelBase instanceof M68kLocalLabel) {
              return withIcon.withPresentableText("." + m68kLabelBase.getName());
            }
            return withIcon;
          }
        });
      }
    };
  }

}
