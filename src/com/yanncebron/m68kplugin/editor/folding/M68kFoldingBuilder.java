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

package com.yanncebron.m68kplugin.editor.folding;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.CustomFoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kDataSize;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kStringExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class M68kFoldingBuilder extends CustomFoldingBuilder {

  @Override
  protected void buildLanguageFoldRegions(@NotNull List<FoldingDescriptor> descriptors,
                                          @NotNull PsiElement root,
                                          @NotNull Document document,
                                          boolean quick) {
    final M68kFoldingSettings settings = M68kFoldingSettings.getInstance();

    root.acceptChildren(new M68kVisitor() {

      @Override
      public void visitDcDirective(@NotNull M68kDcDirective o) {
        final String placeholderText = isZeroTerminatedStringLiteral(o);
        if (placeholderText == null) return;

        descriptors.add(new FoldingDescriptor(o.getNode(), o.getTextRange(), null,
          placeholderText, settings.isCollapseZeroTerminatedStringLiteral(), Collections.emptySet()));
      }
    });
  }

  @Nullable
  private String isZeroTerminatedStringLiteral(@NotNull M68kDcDirective o) {
    if (o.getDataSize() != M68kDataSize.BYTE) return null;
    final List<M68kExpression> expressions = o.getExpressionList();
    if (expressions.size() != 2) return null;

    final M68kExpression firstExpression = expressions.get(0);
    if (!(firstExpression instanceof M68kStringExpression)) return null;

    final M68kExpression secondExpression = expressions.get(1);
    if (!(secondExpression instanceof M68kNumberExpression)) return null;
    if (!Comparing.equal(((M68kNumberExpression) secondExpression).getValue(), 0L)) return null;

    return firstExpression.getText();
  }

  @Override
  protected String getLanguagePlaceholderText(@NotNull ASTNode node, @NotNull TextRange range) {
    return getPlaceholderText(node);
  }

  @Override
  protected boolean isRegionCollapsedByDefault(@NotNull ASTNode node) {
    return false;
  }

}