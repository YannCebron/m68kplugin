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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.codeInsight.highlighting.HighlightErrorFilter;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroParameterDirective;
import org.jetbrains.annotations.NotNull;

/**
 * Suppress parser error highlighting for macro parameter elements,
 * as these can occur at any position inside macro.
 */
public class M68kMacroParameterHighlightErrorFilter extends HighlightErrorFilter {

  @Override
  public boolean shouldHighlightErrorElement(@NotNull PsiErrorElement element) {
    if (element.getLanguage() != M68kLanguage.INSTANCE) return true;

    if (hasMacroParameterDirectiveSibling(element) ||
    hasMacroParameterDirectiveSibling(element.getParent())) {
      return false;
    }

    return !element.getErrorDescription().endsWith(" got '\\'");
  }

  private boolean hasMacroParameterDirectiveSibling(PsiElement element) {
    return
      element.getNextSibling() instanceof M68kMacroParameterDirective ||
      element.getPrevSibling() instanceof M68kMacroParameterDirective;
  }
}
