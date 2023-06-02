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

package com.yanncebron.m68kplugin.editor;

import com.intellij.openapi.editor.actions.FlipCommaIntention;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import org.jetbrains.annotations.NotNull;

final class M68kCommaFlipper implements FlipCommaIntention.Flipper {

  @Override
  public boolean flip(@NotNull PsiElement left, @NotNull PsiElement right) {
    return false;
  }

  @Override
  public boolean canFlip(@NotNull PsiElement left, @NotNull PsiElement right) {
    return left instanceof M68kExpression &&
      right instanceof M68kExpression;
  }
}
