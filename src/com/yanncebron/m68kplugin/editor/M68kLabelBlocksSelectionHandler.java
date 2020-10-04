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

package com.yanncebron.m68kplugin.editor;

import com.intellij.codeInsight.editorActions.ExtendWordSelectionHandlerBase;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Extend selection to preceding label (or keep current one) (inclusive) up to subsequent label (exclusive).
 */
public class M68kLabelBlocksSelectionHandler extends ExtendWordSelectionHandlerBase {

  @Override
  public boolean canSelect(@NotNull PsiElement e) {
    return e instanceof M68kPsiElement;
  }

  @Override
  public List<TextRange> select(@NotNull PsiElement e, @NotNull CharSequence editorText, int cursorOffset, @NotNull Editor editor) {
    final PsiElement startElement = e instanceof M68kLabelBase ? e : PsiTreeUtil.getPrevSiblingOfType(e, M68kLabelBase.class);
    if (startElement == null) {
      return Collections.emptyList();
    }

    final M68kLabelBase endElement = PsiTreeUtil.getNextSiblingOfType(e, M68kLabelBase.class);
    if (endElement == null) {
      return Collections.emptyList();
    }

    TextRange myRange = new TextRange(
      startElement.getTextRange().getStartOffset(),
      endElement.getTextRange().getStartOffset());
    return Collections.singletonList(myRange);
  }
}
