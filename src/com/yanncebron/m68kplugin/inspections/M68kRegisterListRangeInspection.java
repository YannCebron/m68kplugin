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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.ui.SingleCheckboxOptionsPanel;
import com.intellij.psi.PsiElementVisitor;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmRrd;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;
import com.yanncebron.m68kplugin.lang.psi.M68kRegisterRange;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class M68kRegisterListRangeInspection extends LocalInspectionTool {

  @SuppressWarnings("PublicField")
  public boolean allowMixedTypes = true;

  @Nullable
  @Override
  public JComponent createOptionsPanel() {
    return new SingleCheckboxOptionsPanel(M68kBundle.message("inspection.register.list.range.mixed.types.option"), this, "allowMixedTypes");
  }

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly,
                                        @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {
      @Override
      public void visitRegisterRange(@NotNull M68kRegisterRange range) {
        final M68kRegister fromRegister = range.getFrom().getRegister();

        final M68kAdmRrd to = range.getTo();
        if (to == null) return;
        final M68kRegister toRegister = to.getRegister();

        if (!allowMixedTypes && !fromRegister.isSameKind(toRegister)) {
          holder.registerProblem(range, M68kBundle.message("inspection.register.list.range.mixed.types"));
        } else if (fromRegister == toRegister) {
          holder.registerProblem(range, M68kBundle.message("inspection.register.list.range.not.a.range"));
        } else if (fromRegister.isSameKind(toRegister) && fromRegister.ordinal() > toRegister.ordinal()) {
          holder.registerProblem(range, M68kBundle.message("inspection.register.list.range.reversed.range"));
        }
      }
    };
  }
}
