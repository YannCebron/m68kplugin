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

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelReference;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;

public class M68kSyntaxAnnotator implements Annotator {

  @Override
  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (element instanceof M68kLabel) {
      holder.createInfoAnnotation(element.getNode().findChildByType(M68kTokenTypes.ID), null).setTextAttributes(M68kTextAttributes.LABEL);
    } else if (element instanceof M68kLocalLabel) {
      holder.createInfoAnnotation(element.getNode().findChildByType(M68kTokenTypes.ID), null).setTextAttributes(M68kTextAttributes.LOCAL_LABEL);
    }

    // todo temp --> inspection?
    if (element instanceof M68kLabelReference) {
      final PsiReference reference = element.getReference();
      if (reference.resolve() == null) {
        holder.createErrorAnnotation(element, ProblemsHolder.unresolvedReferenceMessage(reference));
      }
    }
  }
}
