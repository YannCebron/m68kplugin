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
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroParameterDirective;
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

}
