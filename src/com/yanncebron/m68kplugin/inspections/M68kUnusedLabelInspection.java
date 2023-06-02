/*
 * Copyright 2023 The Authors
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

import com.intellij.codeInspection.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

final class M68kUnusedLabelInspection extends LocalInspectionTool implements CleanupLocalInspectionTool {

  @Override
  public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder,
                                                 boolean isOnTheFly,
                                                 @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {

      @Override
      public void visitLocalLabel(@NotNull M68kLocalLabel m68kLocalLabel) {
        final PsiReference firstReference = ReferencesSearch.search(m68kLocalLabel).findFirst();
        if (firstReference != null) {
          return;
        }

        final String localLabelName = m68kLocalLabel.getName();
        holder.registerProblem(m68kLocalLabel,
          M68kBundle.message("inspection.unused.label.message", localLabelName),
          ProblemHighlightType.LIKE_UNUSED_SYMBOL,

          new LocalQuickFix() {

            @Override
            public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getFamilyName() {
              return M68kBundle.message("inspection.unused.label.fix.family.name");
            }

            @Override
            public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getName() {
              return M68kBundle.message("inspection.unused.label.fix.name", localLabelName);
            }

            @Override
            public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
              descriptor.getPsiElement().getNextSibling().delete();
              descriptor.getPsiElement().delete();
            }
          }
        );
      }
    };
  }

}
