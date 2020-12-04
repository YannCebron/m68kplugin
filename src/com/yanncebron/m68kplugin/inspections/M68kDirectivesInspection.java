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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class M68kDirectivesInspection extends LocalInspectionTool {

  @Override
  public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly, @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {

      @Override
      public void visitEndDirective(@NotNull M68kEndDirective element) {
        final M68kPsiElement nextSibling = PsiTreeUtil.getNextSiblingOfType(element, M68kPsiElement.class);
        if (nextSibling != null) {
          holder.registerProblem(nextSibling, M68kBundle.message("highlight.no.content.after.end.directive"));
        }
      }

      @Override
      public void visitMacroDirective(@NotNull M68kMacroDirective element) {
        checkUnmatchedOpeningDirective(element, holder, M68kEndmDirective.class, "endm", M68kMacroDirective.class);
      }

      @Override
      public void visitEndmDirective(@NotNull M68kEndmDirective element) {
        checkUnmatchedClosingDirective(element, holder, M68kMacroDirective.class, "macro", M68kEndmDirective.class);
      }

      @Override
      public void visitInlineDirective(@NotNull M68kInlineDirective element) {
        checkUnmatchedOpeningDirective(element, holder, M68kEinlineDirective.class, "einline", M68kInlineDirective.class);
      }

      @Override
      public void visitEinlineDirective(@NotNull M68kEinlineDirective element) {
        checkUnmatchedClosingDirective(element, holder, M68kInlineDirective.class, "inline", M68kEinlineDirective.class);
      }

      @Override
      public void visitRemDirective(@NotNull M68kRemDirective element) {
        checkUnmatchedOpeningDirective(element, holder, M68kEremDirective.class, "erem", M68kRemDirective.class);
      }

      @Override
      public void visitEremDirective(@NotNull M68kEremDirective element) {
        checkUnmatchedClosingDirective(element, holder, M68kRemDirective.class, "rem", M68kEremDirective.class);
      }
    };
  }

  @SuppressWarnings("UnusedReturnValue")
  @SafeVarargs
  private final boolean checkUnmatchedOpeningDirective(PsiElement element, ProblemsHolder holder,
                                                       Class<? extends M68kDirective> matchingDirective,
                                                       @NonNls String matchingDirectiveText,
                                                       Class<? extends M68kDirective>... stopAtDirectives) {
    return checkUnmatchedDirective(element, holder, true, matchingDirective, matchingDirectiveText, stopAtDirectives);
  }

  @SuppressWarnings("UnusedReturnValue")
  @SafeVarargs
  private final boolean checkUnmatchedClosingDirective(PsiElement element, ProblemsHolder holder,
                                                       Class<? extends M68kDirective> matchingDirective,
                                                       @NonNls String matchingDirectiveText,
                                                       Class<? extends M68kDirective>... stopAtDirectives) {
    return checkUnmatchedDirective(element, holder, false, matchingDirective, matchingDirectiveText, stopAtDirectives);
  }

  @SafeVarargs
  private final boolean checkUnmatchedDirective(PsiElement element, ProblemsHolder holder,
                                                boolean forwards,
                                                Class<? extends M68kDirective> matchingDirective,
                                                @NonNls String matchingDirectiveText,
                                                Class<? extends M68kDirective>... stopAtDirectives) {
    if (forwards) {
      if (M68kPsiTreeUtil.hasSiblingForwards(element, matchingDirective, stopAtDirectives)) return false;
    } else {
      if (M68kPsiTreeUtil.hasSiblingBackwards(element, matchingDirective, stopAtDirectives)) return false;
    }

    holder.registerProblem(element, M68kBundle.message("highlight.unmatched.directive", matchingDirectiveText));
    return true;
  }

}