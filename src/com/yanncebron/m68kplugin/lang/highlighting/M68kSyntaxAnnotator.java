/*
 * Copyright 2021 The Authors
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
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroParameterDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kSyntaxAnnotator implements Annotator {

  private static final boolean DEBUG_MODE = ApplicationManager.getApplication().isUnitTestMode();

  @Override
  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (!(element instanceof M68kPsiElement)) return;

    if (element instanceof M68kInstruction) {
      M68kInstruction instruction = (M68kInstruction) element;
      if (instruction.isPrivileged(M68kCpu.M_68000)) {
        holder.newAnnotation(HighlightSeverity.INFORMATION, M68kBundle.message("highlight.privileged.instruction"))
          .textAttributes(M68kTextAttributes.PRIVILEGED_INSTRUCTION).create();
      }
    } else if (element instanceof M68kLabel) {
      doAnnotate(holder, element.getNode().findChildByType(M68kTokenTypes.ID), M68kTextAttributes.LABEL, true);
    } else if (element instanceof M68kLocalLabel) {
      doAnnotate(holder, element.getNode().findChildByType(M68kTokenTypes.ID), M68kTextAttributes.LOCAL_LABEL, true);
    } else if (element instanceof M68kLabelRefExpression) {
      annotateMacroParameters(holder, element);
    } else if (element instanceof M68kMacroParameterDirective) {
      doAnnotate(holder, element.getNode(), M68kTextAttributes.MACRO_PARAMETER, false);
    }
  }

  private void annotateMacroParameters(AnnotationHolder holder, PsiElement element) {
    if (!element.textContains('\\')) return;
    final String text = element.getText();

    int idx = 0;
    while (true) {
      idx = text.indexOf('\\', idx);
      if (idx == -1 || idx > text.length() - 2) break;

      createMacroParameterAnnotation(holder, element.getTextOffset(), idx);
      idx += 2;
    }
  }

  private static void doAnnotate(AnnotationHolder holder,
                                 @Nullable ASTNode node,
                                 TextAttributesKey key,
                                 boolean highlightMacroParameters) {
    if (node == null) {
      return; // todo workaround for non-supported label ID
    }

    createBuilder(holder, key).range(node).create();

    if (highlightMacroParameters && node.textContains('@')) {
      int idx = node.getText().indexOf("\\@");
      if (idx == -1) return;

      createMacroParameterAnnotation(holder, node.getStartOffset(), idx);
    }
  }

  private static void createMacroParameterAnnotation(AnnotationHolder holder, int startOffset, int idx) {
    createBuilder(holder, M68kTextAttributes.MACRO_PARAMETER)
      .range(TextRange.from(startOffset + idx, 2))
      .create();
  }

  private static AnnotationBuilder createBuilder(AnnotationHolder holder, TextAttributesKey key) {
    if (DEBUG_MODE) {
      return holder.newAnnotation(HighlightSeverity.INFORMATION, key.getExternalName()).textAttributes(key);
    }
    return holder.newSilentAnnotation(HighlightSeverity.INFORMATION).textAttributes(key);
  }

}
