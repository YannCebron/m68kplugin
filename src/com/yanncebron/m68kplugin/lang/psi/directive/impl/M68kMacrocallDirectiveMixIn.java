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

package com.yanncebron.m68kplugin.lang.psi.directive.impl;

import com.intellij.codeInsight.daemon.EmptyResolveMessageProvider;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.CommonProcessors;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.Processor;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

abstract class M68kMacrocallDirectiveMixIn extends ASTWrapperPsiElement {

  protected M68kMacrocallDirectiveMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiReference getReference() {
    final ASTNode idNode = getNode().findChildByType(M68kTokenTypes.ID);
    assert idNode != null;

    return new MacroCallReference(idNode);
  }

  private class MacroCallReference extends PsiReferenceBase<M68kMacrocallDirectiveMixIn> implements EmptyResolveMessageProvider {

    public MacroCallReference(ASTNode idNode) {
      super(M68kMacrocallDirectiveMixIn.this, idNode.getTextRange().shiftLeft(M68kMacrocallDirectiveMixIn.this.getTextOffset()));
    }

    @Nullable
    @Override
    public PsiElement resolve() {
      String name = getValue();

      final CommonProcessors.FindProcessor<M68kLabel> findProcessor = new CommonProcessors.FindProcessor<M68kLabel>() {
        @Override
        protected boolean accept(M68kLabel m68kLabel) {
          return name.equals(m68kLabel.getName());
        }
      };
      processMacros(findProcessor);
      return findProcessor.getFoundValue();
    }

    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
      final ASTNode idNode = getNode().findChildByType(M68kTokenTypes.ID);
      assert idNode != null;
      final M68kLabel label = M68kElementFactory.createLabel(getProject(), newElementName);
      getNode().replaceChild(idNode, label.getFirstChild().getNode());
      return getElement();
    }

    @NotNull
    @Override
    public Object[] getVariants() {
      List<M68kLabel> result = new SmartList<>();
      processMacros(new CommonProcessors.CollectProcessor<>(result));

      return ContainerUtil.map2Array(result, LookupElement.class, LookupElementBuilder::createWithIcon);
    }

    private void processMacros(Processor<M68kLabel> processor) {
      M68kPsiTreeUtil.processSiblingsBackwards(getElement(), m68kPsiElement -> {
        if (m68kPsiElement instanceof M68kMacroDirective) {
          return processor.process(((M68kMacroDirective) m68kPsiElement).getLabel());
        }
        return true;
      });
    }

    @NotNull
    @Override
    public String getUnresolvedMessagePattern() {
      return M68kBundle.message("macrocall.directive.cannot.resolve", getValue());
    }
  }
}
