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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.conditional.*;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

@VisibleForTesting
public final class M68kUnresolvedLabelReferenceInspection extends LocalInspectionTool {

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly,
                                        @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {
      @Override
      public void visitLabelRefExpression(@NotNull M68kLabelRefExpression o) {
        highlightReference(holder, o);
      }
    };
  }

  private void highlightReference(@NotNull ProblemsHolder holder,
                                  @NotNull M68kPsiElement psiElement) {
    PsiPolyVariantReference reference = ObjectUtils.tryCast(psiElement.getReference(), PsiPolyVariantReference.class);
    assert reference != null : psiElement;

    boolean unresolved = reference.multiResolve(false).length == 0;
    if (unresolved) {
      // skip '\1' in labelRef
      if (psiElement.textContains('\\') && !(psiElement.textContains('@'))) {
        return;
      }

      if (isUsageInPotentiallyNonResolvingConditionalAssemblyDirective(psiElement)) {
        holder.registerProblem(reference, ProblemHighlightType.WEAK_WARNING);
      } else {
        holder.registerProblem(reference);
      }
    }
  }

  private boolean isUsageInPotentiallyNonResolvingConditionalAssemblyDirective(M68kPsiElement psiElement) {
    final M68kConditionalAssemblyDirective conditionalAssemblyDirective =
      PsiTreeUtil.getParentOfType(psiElement, M68kConditionalAssemblyDirective.class);
    return conditionalAssemblyDirective instanceof M68kIfdConditionalAssemblyDirective ||
      conditionalAssemblyDirective instanceof M68kIfndConditionalAssemblyDirective ||
      conditionalAssemblyDirective instanceof M68kIfmacrodConditionalAssemblyDirective ||
      conditionalAssemblyDirective instanceof M68kIfmacrondConditionalAssemblyDirective;
  }
}
