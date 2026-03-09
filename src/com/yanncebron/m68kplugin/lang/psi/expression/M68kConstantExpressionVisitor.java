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

import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

final class M68kConstantExpressionVisitor extends M68kVisitor {
  private final boolean throwExceptionOnOverflow;

  private final Map<PsiElement, Object> cachedValues = new HashMap<>();

  private Object result;

  M68kConstantExpressionVisitor(boolean throwExceptionOnOverflow) {
    this.throwExceptionOnOverflow = throwExceptionOnOverflow;
  }

  Object handle(PsiElement element) {
    result = null;
    element.accept(this);
    store(element, result);
    return result;
  }

  private Object getStoredValue(PsiElement element) {
    return cachedValues.remove(element);
  }

  void store(PsiElement element, Object value) {
    cachedValues.put(element, value);
  }

  @Override
  public void visitStringExpression(@NotNull M68kStringExpression o) {
    result = o.getValue();
  }

  @Override
  public void visitNumberExpression(@NotNull M68kNumberExpression o) {
    result = o.getValue();
  }

  @Override
  public void visitUnaryPlusExpression(@NotNull M68kUnaryPlusExpression o) {
    Object value = getStoredValue(o.getOperand());
    if (value instanceof Number) {
      result = ((Number) value).intValue();
    }
  }

  @Override
  public void visitUnaryMinusExpression(@NotNull M68kUnaryMinusExpression o) {
    // todo BS ??
    result = null;
    Object value = getStoredValue(o.getOperand());
    if (value instanceof Number) {
      result = -((Number) value).intValue();
    }
  }

  @Override
  public void visitUnaryComplementExpression(@NotNull M68kUnaryComplementExpression o) {
    Object value = getStoredValue(o.getOperand());
    if (value instanceof Number) {
      result = ~((Number) value).intValue();
    }
  }

  @Override
  public void visitUnaryNotExpression(@NotNull M68kUnaryNotExpression o) {
    Object value = getStoredValue(o.getOperand());
    if (value instanceof Number) {
      result = ((Number) value).intValue() == 0 ? -1 : 0;
    }
  }

  @Override
  public void visitPlusExpression(@NotNull M68kPlusExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      result = left + right;
      checkAdditionOverflow((Integer) result >= 0, left >= 0, right >= 0, expression);
      return null;
    });
  }

  @Override
  public void visitMinusExpression(@NotNull M68kMinusExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      result = left - right;
      checkAdditionOverflow((Integer) result>= 0, left >= 0, right < 0, expression);
      return null;
    });
  }

  @Override
  public void visitMulExpression(@NotNull M68kMulExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      result = left * right;
      checkMultiplicationOverflow((Integer) result, left, right, expression);
      return null;
    });
  }

  @Override
  public void visitDivExpression(@NotNull M68kDivExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      checkDivisionOverflow(left, right, expression);
      result = right == 0 ? null : left / right;
      return null;
    });
  }

  @Override
  public void visitModExpression(@NotNull M68kModExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      checkDivisionOverflow(left, right, expression);
      result = right == 0 ? null : left % right;
      return null;
    });
  }

  @Override
  public void visitAndExpression(@NotNull M68kAndExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      result = left & right;
      return null;
    });
  }

  @Override
  public void visitOrExpression(@NotNull M68kOrExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      result = left | right;
      return null;
    });
  }

  @Override
  public void visitXorExpression(@NotNull M68kXorExpression expression) {
    handleBinaryNumberExpression(expression, (left, right) -> {
      result = left ^ right;
      return null;
    });
  }

  @Override
  public void visitParenExpression(@NotNull M68kParenExpression o) {
    result = getStoredValue(o.getExpression());
  }

  @Override
  public void visitShiftLeftExpression(@NotNull M68kShiftLeftExpression o) {
    handleBinaryNumberExpression(o, (left, right) -> {
      result = left << right;
      return null;
    });
  }

  @Override
  public void visitShiftRightExpression(@NotNull M68kShiftRightExpression o) {
    handleBinaryNumberExpression(o, (left, right) -> {
      result = left >> right;
      return null;
    });

  }

  private void handleBinaryNumberExpression(M68kBinaryExpression expression, BiFunction<Integer, Integer, Void> func) {
    Object left = getStoredValue(expression.getLeft());
    Object right = getStoredValue(expression.getRight());

    if (!(left instanceof Number) ||
      !(right instanceof Number)) {
      result = null;
      return;
    }

    func.apply(((Number) left).intValue(), ((Number) right).intValue());
  }

  private void checkAdditionOverflow(boolean resultPositive, boolean lPositive, boolean rPositive, M68kExpression expression) {
    if (!throwExceptionOnOverflow) return;
    boolean overflow = lPositive == rPositive && lPositive != resultPositive;
    if (overflow) throw new M68kConstantEvaluationOverflowException(expression);
  }

  private void checkMultiplicationOverflow(int result, int l, int r, M68kExpression expression) {
    if (!throwExceptionOnOverflow) return;
    if (r == 0 || l == 0) return;
    if (result / r != l || ((l < 0) ^ (r < 0) != (result < 0)))
      throw new M68kConstantEvaluationOverflowException(expression);
  }

  private void checkDivisionOverflow(int l, int r, M68kExpression expression) {
    if (!throwExceptionOnOverflow) return;
    if (r == 0) throw new M68kConstantEvaluationOverflowException(expression);
    if (r == -1 && l == Integer.MIN_VALUE) throw new M68kConstantEvaluationOverflowException(expression);
  }

}
