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

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralValue;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kReptDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class M68kConstantExpressionInspection extends LocalInspectionTool {

  @Override
  public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly, @NotNull LocalInspectionToolSession session) {
    return new ExpressionVisitor(holder);
  }

  private static class ExpressionVisitor extends M68kVisitor {
    @NotNull
    private final ProblemsHolder holder;

    private ExpressionVisitor(@NotNull ProblemsHolder holder) {
      this.holder = holder;
    }

    @Override
    public void visitExpression(@NotNull M68kExpression o) {
      if (o instanceof PsiLiteralValue || o instanceof M68kParenExpression) {
        return;
      }

      try {
        M68kExpressionUtil.computeConstantValue(o);
      } catch (M68kConstantEvaluationOverflowException overflowException) {
        holder.registerProblem(o,
          M68kBundle.message("inspection.constant.expression.error.evaluating.expression",
            overflowException.getOverflowingExpression().getText()));
      }
    }

    @Override
    public void visitNumberExpression(@NotNull M68kNumberExpression o) {
      if (o.getValue() == null) {
        holder.registerProblem(o, M68kBundle.message("inspection.constant.expression.number.value.out.of.range"));
      }
    }

    @Override
    public void visitMoveqInstruction(@NotNull M68kMoveqInstruction o) {
      checkNumberRangeAdmQuick(o.getSource(), -128, 127);
    }

    @Override
    public void visitTrapInstruction(@NotNull M68kTrapInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 0, 15);
    }

    @Override
    public void visitBkptInstruction(@NotNull M68kBkptInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 0, 7);
    }

    @Override
    public void visitStopInstruction(@NotNull M68kStopInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 0, 65535);
    }

    @Override
    public void visitRtdInstruction(@NotNull M68kRtdInstruction o) {
      checkNumberRangeAdmQuick(o.getDisplacement(), -32768, 32767);
    }

    @Override
    public void visitAddqInstruction(@NotNull M68kAddqInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    @Override
    public void visitSubqInstruction(@NotNull M68kSubqInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    @Override
    public void visitBchgInstruction(@NotNull M68kBchgInstruction o) {
      checkNumberRangeAdmQuick(o.getSourceQuick(), 0, 255);
    }

    @Override
    public void visitBtstInstruction(@NotNull M68kBtstInstruction o) {
      checkNumberRangeAdmQuick(o.getSourceQuick(), 0, 255);
    }

    @Override
    public void visitBclrInstruction(@NotNull M68kBclrInstruction o) {
      checkNumberRangeAdmQuick(o.getSourceQuick(), 0, 255);
    }

    @Override
    public void visitAslInstruction(@NotNull M68kAslInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    @Override
    public void visitAsrInstruction(@NotNull M68kAsrInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    @Override
    public void visitLslInstruction(@NotNull M68kLslInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    @Override
    public void visitLsrInstruction(@NotNull M68kLsrInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    @Override
    public void visitRoxlInstruction(@NotNull M68kRoxlInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    @Override
    public void visitRoxrInstruction(@NotNull M68kRoxrInstruction o) {
      checkNumberRangeAdmQuick(o.getAdmQuick(), 1, 8);
    }

    // DIRECTIVES ------------------------------------------------------------------------------------------------------

    @Override
    public void visitReptDirective(@NotNull M68kReptDirective o) {
      checkNumberRange(o.getExpression(), 1, Integer.MAX_VALUE);
    }

    @Override
    public void visitDcDirective(@NotNull M68kDcDirective o) {
      M68kDataSize dataSize = ObjectUtils.notNull(o.getDataSize(), M68kDataSize.UNSIZED);
      int min = 0;
      int max = 0;
      switch (dataSize) {
        case BYTE -> {
          min = -128;
          max = 255;
        }
        case WORD, UNSIZED -> {
          min = -32768;
          max = 65535;
        }
        case LONGWORD -> {
          return;
        }
      }

      for (M68kExpression expression : o.getExpressionList()) {
        checkNumberRange(expression, min, max);
      }
    }

    private void checkNumberRangeAdmQuick(@Nullable M68kAdmQuick m68kAdmQuick, int min, int max) {
      if (m68kAdmQuick == null) return;

      checkNumberRange(m68kAdmQuick.getExpression(), min, max);
    }

    private void checkNumberRange(@Nullable M68kExpression expression, int min, int max) {
      if (expression == null) return;

      Object value = M68kExpressionUtil.computeConstantValueNoOverflow(expression);
      if (value instanceof Integer intValue) {
        if (intValue < min || intValue > max) {
          holder.registerProblem(expression, M68kBundle.message("inspection.constant.expression.operand.value.out.of.range",
            intValue.toString(), Integer.toString(min), Integer.toString(max)));
        }
      }
    }

  }

}
