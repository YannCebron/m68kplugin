/*
 * Copyright 2025 The Authors
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

import com.intellij.codeInsight.daemon.RainbowVisitor;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightVisitor;
import com.intellij.openapi.project.DumbAware;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import org.jetbrains.annotations.NotNull;

/**
 * Semantic highlighting ("rainbow") for macro call name.
 */
final class M68kRainbowVisitor extends RainbowVisitor implements DumbAware {

  @Override
  public boolean suitableForFile(@NotNull PsiFile file) {
    return file instanceof M68kFile;
  }

  @Override
  public void visit(@NotNull PsiElement element) {
    if (element instanceof M68kMacroCallDirective) {
      PsiElement macroNameElement = ((M68kMacroCallDirective) element).getMacroNameElement();
      HighlightInfo info = getInfo(element.getContainingFile(), macroNameElement, macroNameElement.getText(), M68kTextAttributes.MACRO_CALL);
      addInfo(info);
    }
  }

  @Override
  public @NotNull HighlightVisitor clone() {
    return new M68kRainbowVisitor();
  }
}
