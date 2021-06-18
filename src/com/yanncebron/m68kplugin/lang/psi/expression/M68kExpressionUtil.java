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

package com.yanncebron.m68kplugin.lang.psi.expression;

import com.intellij.openapi.util.Comparing;
import org.jetbrains.annotations.Nullable;

public final class M68kExpressionUtil {

  public static boolean isNumberValue(M68kExpression expression, long expectedValue) {
    expression = unwrapExpression(expression);
    return expression instanceof M68kNumberExpression &&
      Comparing.equal(((M68kNumberExpression) expression).getValue(), expectedValue);
  }

  public static boolean isZeroNumberValue(M68kExpression expression) {
    return isNumberValue(expression, 0L);
  }

  @Nullable
  public static M68kExpression unwrapExpression(@Nullable M68kExpression o) {
    if (o instanceof M68kParenExpression) {
      return unwrapExpression(((M68kParenExpression) o).getExpression());
    }
    if (o instanceof M68kUnaryExpression) {
      return unwrapExpression(((M68kUnaryExpression) o).getOperand());
    }
    return o;
  }
}
