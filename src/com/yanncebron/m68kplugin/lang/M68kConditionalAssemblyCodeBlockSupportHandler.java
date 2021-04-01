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

package com.yanncebron.m68kplugin.lang;

import com.intellij.codeInsight.highlighting.CodeBlockSupportHandler;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.ObjectUtils;
import com.intellij.util.Processor;
import com.intellij.util.SmartList;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * Support code block support for conditional assembly structure.
 *
 * @see M68kBraceMatcher for simple 1:1 keyword "brace" matching
 */
public class M68kConditionalAssemblyCodeBlockSupportHandler implements CodeBlockSupportHandler {

  @Override
  public @NotNull List<TextRange> getCodeBlockMarkerRanges(@NotNull PsiElement elementAtCursor) {
    final IElementType elementType = PsiUtilCore.getElementType(elementAtCursor);

    if (!M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES.contains(elementType)) return Collections.emptyList();

    List<TextRange> ranges = new SmartList<>();
    ranges.add(elementAtCursor.getTextRange());

    final M68kConditionalAssemblyDirective startElement =
      PsiTreeUtil.getParentOfType(elementAtCursor, M68kConditionalAssemblyDirective.class);
    assert startElement != null;

    // todo "hard" stop: where? section?
    if (M68kTokenGroups.CONDITIONAL_ASSEMBLY_END_DIRECTIVES.contains(elementType)) {
      processBackwards(ranges, startElement);
    } else {
      if (M68kTokenGroups.CONDITIONAL_ASSEMBLY_STRUCTURE_DIRECTIVES.contains(elementType)) {
        processBackwards(ranges, startElement);
      }

      M68kPsiTreeUtil.processSiblingsForwards(startElement,
        createRangeCollectProcessor(ranges, M68kTokenGroups.CONDITIONAL_ASSEMBLY_END_DIRECTIVES));
    }
    return ranges;
  }

  private void processBackwards(List<TextRange> ranges, M68kConditionalAssemblyDirective startElement) {
    M68kPsiTreeUtil.processSiblingsBackwards(startElement,
      createRangeCollectProcessor(ranges, M68kTokenGroups.CONDITIONAL_ASSEMBLY_START_DIRECTIVES));
  }

  private static Processor<M68kPsiElement> createRangeCollectProcessor(List<TextRange> ranges, TokenSet stopTokenSet) {
    return m68kPsiElement -> {
      if (!(m68kPsiElement instanceof M68kConditionalAssemblyDirective)) return true;

      final PsiElement firstChild = m68kPsiElement.getFirstChild();
      final IElementType elementType = PsiUtilCore.getElementType(firstChild);
      if (stopTokenSet.contains(elementType)) {
        ranges.add(firstChild.getTextRange());
        return false;
      }

      if (M68kTokenGroups.CONDITIONAL_ASSEMBLY_STRUCTURE_DIRECTIVES.contains(elementType)) {
        ranges.add(firstChild.getTextRange());
      }
      return true;
    };
  }

  @Override
  public @NotNull TextRange getCodeBlockRange(@NotNull PsiElement elementAtCursor) {
    PsiElement startElement = ObjectUtils.chooseNotNull(
      M68kPsiTreeUtil.getContainingInstructionOrDirective(elementAtCursor), elementAtCursor);

    Ref<Integer> startOffset = Ref.create();
    M68kPsiTreeUtil.processSiblingsBackwards(startElement, m68kPsiElement -> {
      if (m68kPsiElement instanceof M68kConditionalAssemblyDirective) {
        startOffset.set(m68kPsiElement.getTextRange().getStartOffset());
        return false;
      }
      return true;
    });
    if (startOffset.isNull()) return TextRange.EMPTY_RANGE;

    Ref<Integer> endOffset = Ref.create();
    M68kPsiTreeUtil.processSiblingsForwards(startElement, m68kPsiElement -> {
      if (m68kPsiElement instanceof M68kConditionalAssemblyDirective) {
        endOffset.set(m68kPsiElement.getTextRange().getStartOffset());
        return false;
      }
      return true;
    });
    if (endOffset.isNull()) return TextRange.EMPTY_RANGE;

    return new TextRange(startOffset.get(), endOffset.get());
  }

}
