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

package com.yanncebron.m68kplugin.lang;

import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.ElementDescriptionProvider;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageViewLongNameLocation;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kElementDescriptionProvider implements ElementDescriptionProvider {

  @Nullable
  @Override
  public String getElementDescription(@NotNull PsiElement element, @NotNull ElementDescriptionLocation location) {
    if (element instanceof M68kLabelBase) {
      if (location == UsageViewLongNameLocation.INSTANCE) {
        return ((M68kLabelBase) element).getName();
      }
    }
    return null;
  }
}
