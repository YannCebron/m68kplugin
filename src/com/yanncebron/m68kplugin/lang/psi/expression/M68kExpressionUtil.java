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

package com.yanncebron.m68kplugin.lang.psi.expression;

import com.intellij.openapi.util.Comparing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class M68kExpressionUtil {

  public static boolean isNumberValue(M68kExpression expression, int expectedValue) {
    expression = unwrapParentheses(expression);

    if (expression instanceof M68kUnaryMinusExpression unaryMinusExpression) {
      expression = unwrapParentheses(unaryMinusExpression.getOperand());
      expectedValue = -expectedValue;
    } else if (expression instanceof M68kUnaryPlusExpression unaryPlusExpression) {
      expression = unwrapParentheses(unaryPlusExpression.getOperand());
    }

    return expression instanceof M68kNumberExpression m68kNumberExpression &&
      Comparing.equal(m68kNumberExpression.getValue(), expectedValue);
  }

  public static boolean isZeroNumberValue(M68kExpression expression) {
    return isNumberValue(expression, 0);
  }

  @Nullable
  public static M68kExpression unwrapParentheses(@Nullable M68kExpression expression) {
    if (expression instanceof M68kParenExpression m68kParenExpression) {
      return unwrapParentheses(m68kParenExpression.getExpression());
    }
    return expression;
  }

  @Nullable
  public static Object computeConstantValue(@NotNull M68kExpression expression) {
    return M68kConstantExpressionEvaluator.computeConstantExpression(expression, true);
  }

  @Nullable
  public static Object computeConstantValueNoOverflow(@NotNull M68kExpression expression) {
    return M68kConstantExpressionEvaluator.computeConstantExpression(expression, false);
  }
}
