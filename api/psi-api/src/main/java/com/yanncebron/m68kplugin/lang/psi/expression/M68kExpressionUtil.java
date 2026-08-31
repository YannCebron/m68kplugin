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

import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class M68kExpressionUtil {

  public static M68kExpressionUtil getInstance() {
    return ApplicationManager.getApplication().getService(M68kExpressionUtil.class);
  }

  public abstract boolean isNumberValue(@Nullable M68kExpression expression, int expectedValue);

  public abstract @Nullable M68kExpression unwrapParentheses(@Nullable M68kExpression expression);

  public abstract @Nullable Object computeConstantValue(@NotNull M68kExpression expression);

  public abstract @Nullable Object computeConstantValueNoOverflow(@NotNull M68kExpression expression);

}
