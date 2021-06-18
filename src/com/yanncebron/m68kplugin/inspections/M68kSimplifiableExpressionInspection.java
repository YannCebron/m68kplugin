/*
 * Copyright 2021 The Authors
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
import com.intellij.codeInspection.LocalQuickFixOnPsiElement;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Comparing;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLiteralValue;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.expression.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kSimplifiableExpressionInspection extends LocalInspectionTool {

  @Override
  public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder,
                                                 boolean isOnTheFly,
                                                 @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {

      @Override
      public void visitParenExpression(@NotNull M68kParenExpression o) {
        M68kExpression expression = unwrapParenExpression(o);
        if (expression instanceof PsiLiteralValue) {
          holder.registerProblem(o,
            M68kBundle.message("inspection.simplifiable.expression.unnecessary.parentheses.message"),
            new RemoveParenthesesQuickFix(o));
        }
      }

      @Override
      public void visitUnaryMinusExpression(@NotNull M68kUnaryMinusExpression o) {
        reportSuperfluousZero(holder, o.getExpression());
      }

      @Override
      public void visitUnaryPlusExpression(@NotNull M68kUnaryPlusExpression o) {
        reportSuperfluousZero(holder, o.getExpression());
      }

      @Override
      public void visitModExpression(@NotNull M68kModExpression o) {
        reportSuperfluousOne(holder, o.getRight());
      }

      @Override
      public void visitDivExpression(@NotNull M68kDivExpression o) {
        reportSuperfluousOne(holder, o.getRight());
        reportSuperfluousMinusOne(holder, o.getRight());
      }

      @Override
      public void visitMulExpression(@NotNull M68kMulExpression o) {
        reportSuperfluousOne(holder, o.getLeft());
        reportSuperfluousOne(holder, o.getRight());

        reportSuperfluousZero(holder, o.getLeft());
        reportSuperfluousZero(holder, o.getRight());

        reportSuperfluousMinusOne(holder, o.getLeft());
        reportSuperfluousMinusOne(holder, o.getRight());
      }

      @Override
      public void visitPlusExpression(@NotNull M68kPlusExpression o) {
        reportSuperfluousZero(holder, o.getLeft());
        reportSuperfluousZero(holder, o.getRight());
      }

      @Override
      public void visitMinusExpression(@NotNull M68kMinusExpression o) {
        reportSuperfluousZero(holder, o.getLeft());
        reportSuperfluousZero(holder, o.getRight());
      }
    };
  }

  private void reportSuperfluousZero(ProblemsHolder holder, M68kExpression expression) {
    if (isZero(expression)) {
      holder.registerProblem(expression, M68kBundle.message("inspection.simplifiable.expression.message"));
    }
  }

  private void reportSuperfluousOne(ProblemsHolder holder, M68kExpression expression) {
    if (isOne(expression)) {
      holder.registerProblem(expression, M68kBundle.message("inspection.simplifiable.expression.message"));
    }
  }

  private void reportSuperfluousMinusOne(ProblemsHolder holder, M68kExpression expression) {
    if (isMinusOne(expression)) {
      holder.registerProblem(expression, M68kBundle.message("inspection.simplifiable.expression.message"));
    }
  }

  private boolean isZero(M68kExpression expression) {
    return isNumberLiteral(expression, 0L);
  }

  private boolean isOne(M68kExpression expression) {
    return isNumberLiteral(expression, 1L);
  }

  private boolean isMinusOne(M68kExpression expression) {
    return expression instanceof M68kUnaryMinusExpression &&
      isNumberLiteral(((M68kUnaryMinusExpression) expression).getExpression(), 1L);
  }

  private boolean isNumberLiteral(M68kExpression expression, long l) {
    return expression instanceof M68kNumberExpression &&
      Comparing.equal(((M68kNumberExpression) expression).getValue(), l);
  }

  @Nullable
  private M68kExpression unwrapParenExpression(@NotNull M68kParenExpression o) {
    M68kExpression expression = o.getExpression();
    if (expression instanceof M68kUnaryMinusExpression) {
      expression = ((M68kUnaryMinusExpression) expression).getExpression();
    } else if (expression instanceof M68kUnaryPlusExpression) {
      expression = ((M68kUnaryPlusExpression) expression).getExpression();
    }
    return expression;
  }


  private static class RemoveParenthesesQuickFix extends LocalQuickFixOnPsiElement {

    private RemoveParenthesesQuickFix(@NotNull M68kParenExpression element) {
      super(element);
    }

    @Override
    public @IntentionFamilyName @NotNull String getFamilyName() {
      return M68kBundle.message("inspection.simplifiable.expression.unnecessary.parentheses.fix.family.name");
    }

    @Override
    public @IntentionName @NotNull String getText() {
      return M68kBundle.message("inspection.simplifiable.expression.unnecessary.parentheses.fix.name");
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiFile file, @NotNull PsiElement startElement, @NotNull PsiElement endElement) {
      assert startElement instanceof M68kParenExpression;
      final M68kExpression replacement = ((M68kParenExpression) startElement).getExpression();
      startElement.replace(replacement);
    }

  }

}
