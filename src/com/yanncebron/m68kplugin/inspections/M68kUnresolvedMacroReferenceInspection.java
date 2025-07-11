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
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

@VisibleForTesting
public final class M68kUnresolvedMacroReferenceInspection extends LocalInspectionTool {

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly,
                                        @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {
      @Override
      public void visitMacroCallDirective(@NotNull M68kMacroCallDirective o) {
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
      holder.registerProblem(reference);
    }
  }

}
