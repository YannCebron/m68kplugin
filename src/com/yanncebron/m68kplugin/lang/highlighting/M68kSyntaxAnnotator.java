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
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import org.jetbrains.annotations.NotNull;

public class M68kSyntaxAnnotator implements Annotator {

  private static final boolean DEBUG_MODE = ApplicationManager.getApplication().isUnitTestMode();

  @Override
  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (!(element instanceof M68kPsiElement)) return;

    if (element instanceof M68kInstruction) {
      M68kInstruction instruction = (M68kInstruction) element;
      if (instruction.isPrivileged()) {
        holder.createInfoAnnotation(element, M68kBundle.message("highlight.privileged.instruction"))
          .setTextAttributes(M68kTextAttributes.PRIVILEGED_INSTRUCTION);
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
        holder.createErrorAnnotation(nextSibling, M68kBundle.message("highlight.no.content.after.end.directive"));
      }
    } else if (element instanceof M68kMacroDirective) {
      checkUnmatchedDirective(element, holder, M68kEndmDirective.class, "endm", M68kMacroDirective.class);
    } else if (element instanceof M68kInlineDirective) {
      checkUnmatchedDirective(element, holder, M68kEinlineDirective.class, "einline", M68kInlineDirective.class);
    }
  }

  private static void doAnnotate(AnnotationHolder holder, ASTNode node, TextAttributesKey key) {
    (DEBUG_MODE ? holder.createInfoAnnotation(node, key.getExternalName()) : holder.createInfoAnnotation(node, null))
      .setTextAttributes(key);
  }

  @SafeVarargs
  private final void checkUnmatchedDirective(PsiElement element, AnnotationHolder holder,
                                             Class<? extends M68kDirective> matchingDirective,
                                             String matchingDirectiveText,
                                             Class<? extends M68kDirective>... stopAtDirectives) {
    final boolean hasMatchingClosingDirective = hasMatchingClosingDirective(element, matchingDirective, stopAtDirectives);
    if (!hasMatchingClosingDirective) {
      holder.createErrorAnnotation(element, M68kBundle.message("highlight.unmatched.directive", matchingDirectiveText));
    }
  }

  @SafeVarargs
  private static boolean hasMatchingClosingDirective(PsiElement element,
                                                     Class<? extends M68kDirective> matchingDirective,
                                                     Class<? extends M68kDirective>... stopAtDirectives) {
    for (PsiElement child = element.getNextSibling(); child != null; child = child.getNextSibling()) {
      if (PsiTreeUtil.instanceOf(child, stopAtDirectives)) return false;
      if (matchingDirective.isInstance(child)) return true;
    }
    return false;
  }

}
