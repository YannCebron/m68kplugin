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

import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.CommonProcessors;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class M68kSyntaxAnnotator implements Annotator {

  private static final boolean DEBUG_MODE = ApplicationManager.getApplication().isUnitTestMode();

  @Override
  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (!(element instanceof M68kPsiElement)) return;

    if (element instanceof M68kInstruction) {
      M68kInstruction instruction = (M68kInstruction) element;
      if (instruction.isPrivileged()) {
        holder.newAnnotation(HighlightSeverity.INFORMATION, M68kBundle.message("highlight.privileged.instruction"))
          .textAttributes(M68kTextAttributes.PRIVILEGED_INSTRUCTION).create();
      }
    } else if (element instanceof M68kLabel) {
      doAnnotate(holder, element.getNode().findChildByType(M68kTokenTypes.ID), M68kTextAttributes.LABEL);
    } else if (element instanceof M68kLocalLabel) {
      doAnnotate(holder, element.getNode().findChildByType(M68kTokenTypes.ID), M68kTextAttributes.LOCAL_LABEL);
    } else if (element instanceof M68kMacroCallDirective) {
      doAnnotate(holder, element.getNode().findChildByType(M68kTokenTypes.ID), M68kTextAttributes.MACRO_CALL);
    } else if (element instanceof M68kMacroParameterDirective) {
      doAnnotate(holder, element.getNode(), M68kTextAttributes.MACRO_PARAMETER);
    } else if (element instanceof M68kEndDirective) {
      final M68kPsiElement nextSibling = PsiTreeUtil.getNextSiblingOfType(element, M68kPsiElement.class);
      if (nextSibling != null) {
        holder.createErrorAnnotation(nextSibling, M68kBundle.message("highlight.no.content.after.end.directive")); // todo range
      }
    } else if (element instanceof M68kMacroDirective) {
      checkUnmatchedOpeningDirective(element, holder, M68kEndmDirective.class, "endm", M68kMacroDirective.class);
    } else if (element instanceof M68kEndmDirective) {
      checkUnmatchedClosingDirective(element, holder, M68kMacroDirective.class, "macro", M68kEndmDirective.class);
    } else if (element instanceof M68kInlineDirective) {
      checkUnmatchedOpeningDirective(element, holder, M68kEinlineDirective.class, "einline", M68kInlineDirective.class);
    } else if (element instanceof M68kEinlineDirective) {
      checkUnmatchedClosingDirective(element, holder, M68kInlineDirective.class, "inline", M68kEinlineDirective.class);
    } else if (element instanceof M68kRemDirective) {
      boolean unmatched = checkUnmatchedOpeningDirective(element, holder, M68kEremDirective.class, "erem", M68kRemDirective.class);
      if (!unmatched) {
        final CommonProcessors.FindProcessor<M68kPsiElement> findClosingErem = new CommonProcessors.FindProcessor<M68kPsiElement>() {
          @Override
          protected boolean accept(M68kPsiElement o) {
            return o instanceof M68kEremDirective;
          }
        };
        M68kPsiTreeUtil.processSiblingsForwards(element, findClosingErem, M68kRemDirective.class);
        final M68kPsiElement closingDirective = findClosingErem.getFoundValue();
        assert closingDirective != null;

        TextRange range = new TextRange(element.getTextRange().getEndOffset(), closingDirective.getTextOffset());
        doAnnotate(holder, range, DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR);
      }
    } else if (element instanceof M68kEremDirective) {
      checkUnmatchedClosingDirective(element, holder, M68kRemDirective.class, "rem", M68kEremDirective.class);
    }
  }

  private static void doAnnotate(AnnotationHolder holder, ASTNode node, TextAttributesKey key) {
    final AnnotationBuilder builder;
    if (DEBUG_MODE) {
      builder = holder.newAnnotation(HighlightSeverity.INFORMATION, key.getExternalName());
    } else {
      builder = holder.newSilentAnnotation(HighlightSeverity.INFORMATION);
    }
    builder.range(node).textAttributes(key).create();
  }

  private static void doAnnotate(AnnotationHolder holder, TextRange range, TextAttributesKey key) {
    (DEBUG_MODE ? holder.createInfoAnnotation(range, key.getExternalName()) : holder.createInfoAnnotation(range, null))
      .setTextAttributes(key); // todo range
  }

  @SafeVarargs
  private final boolean checkUnmatchedOpeningDirective(PsiElement element, AnnotationHolder holder,
                                                       Class<? extends M68kDirective> matchingDirective,
                                                       @NonNls String matchingDirectiveText,
                                                       Class<? extends M68kDirective>... stopAtDirectives) {
    return checkUnmatchedDirective(element, holder, true, matchingDirective, matchingDirectiveText, stopAtDirectives);
  }

  @SuppressWarnings("UnusedReturnValue")
  @SafeVarargs
  private final boolean checkUnmatchedClosingDirective(PsiElement element, AnnotationHolder holder,
                                                       Class<? extends M68kDirective> matchingDirective,
                                                       @NonNls String matchingDirectiveText,
                                                       Class<? extends M68kDirective>... stopAtDirectives) {
    return checkUnmatchedDirective(element, holder, false, matchingDirective, matchingDirectiveText, stopAtDirectives);
  }

  @SafeVarargs
  private final boolean checkUnmatchedDirective(PsiElement element, AnnotationHolder holder,
                                                boolean forwards,
                                                Class<? extends M68kDirective> matchingDirective,
                                                @NonNls String matchingDirectiveText,
                                                Class<? extends M68kDirective>... stopAtDirectives) {
    if (forwards) {
      if (M68kPsiTreeUtil.hasSiblingForwards(element, matchingDirective, stopAtDirectives)) return false;
    } else {
      if (M68kPsiTreeUtil.hasSiblingBackwards(element, matchingDirective, stopAtDirectives)) return false;
    }

    holder.newAnnotation(HighlightSeverity.ERROR, M68kBundle.message("highlight.unmatched.directive", matchingDirectiveText)).create();
    return true;
  }

}
