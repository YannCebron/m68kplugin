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

import com.intellij.codeInsight.hint.ImplementationTextSelectioner;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kEndmDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;
import org.jetbrains.annotations.NotNull;

/**
 * Expand <em>View | Quick Definition</em> range:
 * <ul>
 *   <li>full {@code macro ... endm} block</li>
 *   <li>(local) label: up to 5 following statements; stop at the next (local) label</li>
 * </ul>
 * .
 */
final class M68kImplementationTextSelectioner implements ImplementationTextSelectioner {

  @Override
  public int getTextStartOffset(@NotNull PsiElement element) {
    return element.getTextRange().getStartOffset();
  }

  @Override
  public int getTextEndOffset(@NotNull PsiElement element) {
    if (!(element instanceof M68kLabel) &&
      !(element instanceof M68kLocalLabel)) {
      return element.getTextRange().getEndOffset();
    }

    Ref<Integer> textEndOffset = Ref.create(element.getTextRange().getEndOffset());

    // macro: show full macro
    final M68kMacroDirective macroDirective = PsiTreeUtil.getParentOfType(element, M68kMacroDirective.class);
    if (macroDirective != null) {
      M68kPsiTreeUtil.processSiblingsForwards(macroDirective, sibling -> {
        if (sibling instanceof M68kEndmDirective) {
          textEndOffset.set(sibling.getTextRange().getEndOffset());
          return false;
        }
        return true;
      });
      return textEndOffset.get();
    }

    Ref<Integer> maxEntries = Ref.create(0);
    M68kPsiTreeUtil.processSiblingsForwards(element, sibling -> {
      if (sibling instanceof M68kLocalLabel || sibling instanceof M68kLabel) {
        return false;
      }

      if (sibling instanceof M68kInstruction ||
        sibling instanceof M68kDirective ||
        sibling instanceof M68kConditionalAssemblyDirective) {
        textEndOffset.set(sibling.getTextRange().getEndOffset());
        maxEntries.set(maxEntries.get() + 1);
      }
      return maxEntries.get() < 5;
    });

    return textEndOffset.get();

  }
}
