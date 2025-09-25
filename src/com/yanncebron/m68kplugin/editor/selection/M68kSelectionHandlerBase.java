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

package com.yanncebron.m68kplugin.editor.selection;

import com.intellij.codeInsight.editorActions.ExtendWordSelectionHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

abstract class M68kSelectionHandlerBase implements ExtendWordSelectionHandler {

  private final Class<? extends M68kPsiElement> startClazz;
  private final boolean includeStartElement;

  private final Class<? extends M68kPsiElement> endClazz;
  private final boolean includeEndElement;

  protected M68kSelectionHandlerBase(Class<? extends M68kPsiElement> startClazz,
                                     boolean includeStartElement,
                                     Class<? extends M68kPsiElement> endClazz,
                                     boolean includeEndElement) {
    this.startClazz = startClazz;
    this.endClazz = endClazz;
    this.includeStartElement = includeStartElement;
    this.includeEndElement = includeEndElement;
  }

  @Override
  public final boolean canSelect(@NotNull PsiElement e) {
    return e instanceof M68kPsiElement;
  }

  @Override
  public final @NotNull List<TextRange> select(@NotNull PsiElement e, @NotNull CharSequence editorText, int cursorOffset, @NotNull Editor editor) {
    final PsiElement startElement = startClazz.isInstance(e) ? e : PsiTreeUtil.getPrevSiblingOfType(e, startClazz);
    if (startElement == null) {
      return Collections.emptyList();
    }

    final M68kPsiElement endElement = PsiTreeUtil.getNextSiblingOfType(e, endClazz);
    if (endElement == null) {
      return Collections.emptyList();
    }

    TextRange myRange = new TextRange(
      includeStartElement ? startElement.getTextRange().getStartOffset() : startElement.getTextRange().getEndOffset(),
      includeEndElement ? endElement.getTextRange().getEndOffset() : endElement.getTextRange().getStartOffset());
    return Collections.singletonList(myRange);
  }
}
