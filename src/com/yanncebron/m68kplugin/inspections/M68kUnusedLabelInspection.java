/*
 * Copyright 2026 The Authors
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
import com.intellij.modcommand.ActionContext;
import com.intellij.modcommand.ModCommand;
import com.intellij.modcommand.Presentation;
import com.intellij.modcommand.PsiBasedModCommandAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kUnusedLabelInspection extends LocalInspectionTool implements CleanupLocalInspectionTool, DumbAware {

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
          LocalQuickFix.from(new RemoveLocalLabelQuickFix(m68kLocalLabel, localLabelName))
        );
      }
    };
  }

  private static class RemoveLocalLabelQuickFix extends PsiBasedModCommandAction<M68kLocalLabel> implements DumbAware {

    private final String localLabelName;

    private RemoveLocalLabelQuickFix(M68kLocalLabel label, String localLabelName) {
      super(label);
      this.localLabelName = localLabelName;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getFamilyName() {
      return M68kBundle.message("inspection.unused.label.fix.family.name");
    }

    @Override
    protected @NotNull ModCommand perform(@NotNull ActionContext context, @NotNull M68kLocalLabel element) {
      return ModCommand.psiUpdate(element, (m68kLocalLabel, modPsiUpdater) -> {
        m68kLocalLabel.getNextSibling().delete();
        m68kLocalLabel.delete();
      });
    }

    @Override
    protected @Nullable Presentation getPresentation(@NotNull ActionContext context, @NotNull M68kLocalLabel element) {
      return Presentation.of(M68kBundle.message("inspection.unused.label.fix.name", localLabelName));
    }
  }
}
