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
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElementVisitor;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIf1ConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIf2ConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kIfp1ConditionalAssemblyDirective;
import org.jetbrains.annotations.NotNull;

final class M68kConditionalAssemblyDirectivesInspection extends LocalInspectionTool implements DumbAware {

  @Override
  public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly, @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {

      @Override
      public void visitIf1ConditionalAssemblyDirective(@NotNull M68kIf1ConditionalAssemblyDirective o) {
        highlightUnsupported(holder, o);
      }

      @Override
      public void visitIf2ConditionalAssemblyDirective(@NotNull M68kIf2ConditionalAssemblyDirective o) {
        highlightUnsupported(holder, o);
      }

      @Override
      public void visitIfp1ConditionalAssemblyDirective(@NotNull M68kIfp1ConditionalAssemblyDirective o) {
        highlightUnsupported(holder, o);
      }
    };
  }

  private static void highlightUnsupported(ProblemsHolder holder, M68kConditionalAssemblyDirective directive) {
    holder.registerProblem(directive, M68kBundle.message("inspection.conditional.assembly.directives.unsupported"));
  }
}
