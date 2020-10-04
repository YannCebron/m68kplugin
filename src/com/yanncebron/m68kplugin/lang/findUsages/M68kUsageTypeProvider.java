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

package com.yanncebron.m68kplugin.lang.findUsages;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.usages.impl.rules.UsageType;
import com.intellij.usages.impl.rules.UsageTypeProvider;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kStringExpression;
import org.jetbrains.annotations.Nullable;

public class M68kUsageTypeProvider implements UsageTypeProvider {

  static final UsageType EXPRESSION = new UsageType(M68kBundle.message("usage.type.expression"));
  static final UsageType DIRECTIVE = new UsageType(M68kBundle.message("usage.type.directive"));
  static final UsageType CONDITIONAL_ASSEMBLY = new UsageType(M68kBundle.message("usage.type.conditional.assembly"));

  @Nullable
  @Override
  public UsageType getUsageType(PsiElement element) {
    if (PsiTreeUtil.getParentOfType(element, M68kDirective.class) != null) {
      return DIRECTIVE;
    }
    if (PsiTreeUtil.getParentOfType(element, M68kConditionalAssemblyDirective.class) != null) {
      return CONDITIONAL_ASSEMBLY;
    }

    // search for text occurrences
    if (PsiTreeUtil.getParentOfType(element, M68kStringExpression.class) != null) {
      return UsageType.LITERAL_USAGE;
    }
    if (PsiTreeUtil.getParentOfType(element, M68kExpression.class) != null) {
      return EXPRESSION;
    }
    return null;
  }
}
