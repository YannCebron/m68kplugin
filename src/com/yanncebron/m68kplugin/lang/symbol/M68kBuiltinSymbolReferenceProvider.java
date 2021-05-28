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

package com.yanncebron.m68kplugin.lang.symbol;

import com.intellij.codeInsight.highlighting.PsiHighlightedReference;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.model.SingleTargetReference;
import com.intellij.model.Symbol;
import com.intellij.model.psi.*;
import com.intellij.model.search.SearchRequest;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// TODO disabled, as GTD fails in com.intellij.model.psi.impl.TargetsKt#declarationsOrReferences when mixing Symbol/PsiReference
@SuppressWarnings("UnstableApiUsage")
public class M68kBuiltinSymbolReferenceProvider implements PsiSymbolReferenceProvider {

  @Override
  public @NotNull Collection<? extends @NotNull PsiSymbolReference> getReferences(@NotNull PsiExternalReferenceHost element, @NotNull PsiSymbolReferenceHints hints) {
    if (!Registry.is("m68k.builtin.symbols")) return Collections.emptyList();

    return Collections.singleton(new M68kBuiltinSymbolReference(element));
  }

  @Override
  public @NotNull Collection<? extends @NotNull SearchRequest> getSearchRequests(@NotNull Project project, @NotNull Symbol target) {
    return Collections.emptyList();
  }


  public static class M68kBuiltinSymbolReference extends SingleTargetReference implements PsiCompletableReference, PsiHighlightedReference {

    @Override
    public @NotNull AnnotationBuilder highlightReference(@NotNull AnnotationBuilder annotationBuilder) {
      if (resolveSingleTarget() == null) return annotationBuilder;

      return annotationBuilder.textAttributes(DefaultLanguageHighlighterColors.METADATA); // todo own color
    }

    private final PsiExternalReferenceHost element;

    private M68kBuiltinSymbolReference(PsiExternalReferenceHost element) {
      this.element = element;
    }

    @Override
    protected @Nullable Symbol resolveSingleTarget() {
      final String name = element.getText();
      for (M68kBuiltinSymbolRegistry value : M68kBuiltinSymbolRegistry.values()) {
        if (value.getName().equals(name)) {
          return new M68kBuiltinSymbol(value);
        }
      }
      return null;
    }

    @Override
    public @NotNull PsiElement getElement() {
      return element;
    }

    @Override
    public @NotNull TextRange getRangeInElement() {
      return TextRange.allOf(element.getText());
    }

    @Override
    public @NotNull Collection<LookupElement> getCompletionVariants() {
      List<LookupElement> variants = new ArrayList<>();
      for (M68kBuiltinSymbolRegistry value : M68kBuiltinSymbolRegistry.values()) {
        variants.add(LookupElementBuilder.create(value.getName())
          .withItemTextItalic(true)
          .withIcon(value.getIcon())
          .withTailText(" " + value.getDescription())
          .withTypeText(value.getCompiler().getDisplayName()));
      }
      return variants;
    }
  }

}
