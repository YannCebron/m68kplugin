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
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmArd;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

final class M68kUsageA7RegisterInspection extends LocalInspectionTool implements CleanupLocalInspectionTool {

  @Override
  public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder,
                                                 boolean isOnTheFly,
                                                 @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {

      @Override
      public void visitAdmArd(@NotNull M68kAdmArd m68kAdmArd) {
        if (m68kAdmArd.getRegister() != M68kRegister.A7) return;

        holder.registerProblem(m68kAdmArd, M68kBundle.message("inspection.usage.a7.register.message"),
          new ReplaceWithSPRegisterQuickFix());
      }

    };
  }


  private static class ReplaceWithSPRegisterQuickFix implements LocalQuickFix {

    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getFamilyName() {
      return M68kBundle.message("inspection.usage.a7.register.replace.with.sp.fix.name");
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
      final M68kAdmArd sp = M68kElementFactory.createAddressRegister(project, "sp");
      descriptor.getPsiElement().replace(sp);
    }
  }
}
