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

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralValue;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.util.ConcurrencyUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.CollectionFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kRecursiveElementWalkingVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

final class M68kConstantExpressionEvaluator extends M68kRecursiveElementWalkingVisitor {

  @NotNull
  private final Project project;
  private final Supplier<ConcurrentMap<PsiElement, Object>> myMapFactory;
  private final M68kConstantExpressionVisitor visitor;

  private static final Key<CachedValue<ConcurrentMap<PsiElement, Object>>> CONSTANT_VALUE_WO_OVERFLOW_MAP_KEY = Key.create("CONSTANT_VALUE_WO_OVERFLOW_MAP_KEY");
  private static final Key<CachedValue<ConcurrentMap<PsiElement, Object>>> CONSTANT_VALUE_WITH_OVERFLOW_MAP_KEY = Key.create("CONSTANT_VALUE_WITH_OVERFLOW_MAP_KEY");
  private static final CachedValueProvider<ConcurrentMap<PsiElement, Object>> PROVIDER = () -> {
    ConcurrentMap<PsiElement, Object> value = CollectionFactory.createConcurrentWeakMap();
    return CachedValueProvider.Result.create(value, PsiModificationTracker.MODIFICATION_COUNT);
  };

  private static final Object NO_VALUE = ObjectUtils.NULL;

  private M68kConstantExpressionEvaluator(@NotNull Project project, boolean throwExceptionOnOverflow) {
    this.project = project;
    this.myMapFactory = () -> {
      final Key<CachedValue<ConcurrentMap<PsiElement, Object>>> key =
        throwExceptionOnOverflow ? CONSTANT_VALUE_WITH_OVERFLOW_MAP_KEY : CONSTANT_VALUE_WO_OVERFLOW_MAP_KEY;
      return CachedValuesManager.getManager(this.project).getCachedValue(this.project, key, PROVIDER, false);
    };
    this.visitor = new M68kConstantExpressionVisitor(throwExceptionOnOverflow);
  }

  private Object getCached(@NotNull M68kExpression element) {
    return map().get(element);
  }

  private void cache(@NotNull M68kExpression element, @Nullable Object value) {
    ConcurrencyUtil.cacheOrGet(map(), element, value == null ? NO_VALUE : value);
  }

  private @NotNull ConcurrentMap<PsiElement, Object> map() {
    return myMapFactory.get();
  }

  @Override
  protected void elementFinished(@NotNull PsiElement element) {
    if (!(element instanceof M68kExpression)) return;

    Object value = getCached((M68kExpression) element);
    if (value == null) {
      Object result = visitor.handle(element);
      cache((M68kExpression) element, result);
    } else {
      visitor.store(element, value == NO_VALUE ? null : value);
    }
  }

  @Override
  public void visitElement(@NotNull PsiElement element) {
    if (!(element instanceof M68kExpression)) {
      super.visitElement(element);
      return;
    }

    Object value = getCached((M68kExpression) element);
    if (value == null) {
      super.visitElement(element);
      // will cache back in elementFinished()
    } else {
      visitor.store(element, value == NO_VALUE ? null : value);
    }
  }

  @Nullable
  static Object computeConstantExpression(M68kExpression expression, boolean throwExceptionOnOverflow) {
    if (expression instanceof PsiLiteralValue psiLiteralValue) {
      return psiLiteralValue.getValue();
    }

    M68kConstantExpressionEvaluator evaluator = new M68kConstantExpressionEvaluator(expression.getProject(), throwExceptionOnOverflow);
    expression.accept(evaluator);
    Object result = evaluator.getCached(expression);
    return result == NO_VALUE ? null : result;
  }
}
