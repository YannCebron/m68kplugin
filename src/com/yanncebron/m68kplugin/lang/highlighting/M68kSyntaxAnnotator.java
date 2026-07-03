/*
 * Copyright 2026 The Authors
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

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirectiveWithLabel;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroParameterDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kSyntaxAnnotator implements Annotator, DumbAware {

  @Override
  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (holder.isBatchMode()) return;
    if (!(element instanceof M68kPsiElement)) return;

    if (element instanceof M68kInstruction m68kInstruction) {
      if (element instanceof M68kDataSized) {
        annotateMacroParameters(holder, element);
      }

      M68kMnemonic m68kMnemonic = M68kMnemonicRegistry.getInstance().find(m68kInstruction);
      if (m68kMnemonic == null) return;

      if (m68kMnemonic.deprecated()) {
        holder.newAnnotation(HighlightSeverity.WARNING, M68kBundle.message("highlight.deprecated.mnemonic"))
          .highlightType(ProblemHighlightType.LIKE_DEPRECATED)
          .create();
      }

      if (m68kInstruction instanceof M68kPrivilegedInstruction && M68kMnemonicPredicates.privilegedAny().test(m68kMnemonic)) {
        String message;
        if (M68kMnemonicPredicates.privileged68010Above().test(m68kMnemonic)) {
          message = M68kBundle.message("highlight.privileged.instruction.68010.or.above");
        } else {
          message = M68kBundle.message("highlight.privileged.instruction");
        }
        holder.newAnnotation(HighlightSeverity.INFORMATION, message)
          .textAttributes(M68kTextAttributes.PRIVILEGED_INSTRUCTION).create();
      }
    } else if (element instanceof M68kLabel) {
      TextAttributesKey key = M68kTextAttributes.LABEL;
      M68kDirectiveWithLabel directiveWithLabel = PsiTreeUtil.getParentOfType(element, M68kDirectiveWithLabel.class);
      if (directiveWithLabel != null) {
        key = directiveWithLabel instanceof M68kMacroDirective ? M68kTextAttributes.MACRO_LABEL : M68kTextAttributes.SYMBOL_LABEL;
      }
      doAnnotate(holder, element.getNode().findChildByType(M68kTokenTypes.ID), key, true);
    } else if (element instanceof M68kLocalLabel) {
      doAnnotate(holder, element.getNode().findChildByType(M68kTokenTypes.ID), M68kTextAttributes.LOCAL_LABEL, true);
    } else if (element instanceof M68kLabelRefExpression) {
      annotateMacroParameters(holder, element);
      annotateBuiltinSymbol(holder, element);
    } else if (element instanceof M68kMacroParameterDirective) {
      doAnnotate(holder, element.getNode(), M68kTextAttributes.MACRO_PARAMETER, false);
    }
  }

  private void annotateBuiltinSymbol(@NotNull AnnotationHolder holder, @NotNull PsiElement element) {
    PsiReferenceBase<?> reference = ObjectUtils.tryCast(element.getReference(), PsiReferenceBase.class);
    assert reference != null : element;
    String value = reference.getValue();
    if (M68kBuiltinSymbol.findByName(value) != null) {
      doAnnotate(holder, element.getNode(), M68kTextAttributes.BUILTIN_SYMBOL, false);
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
    return holder.newSilentAnnotation(HighlightSeverity.INFORMATION).textAttributes(key);
  }

}
