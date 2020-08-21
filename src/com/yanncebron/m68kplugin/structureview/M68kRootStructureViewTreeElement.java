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

package com.yanncebron.m68kplugin.structureview;

import com.intellij.icons.AllIcons;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kEquDirectiveBase;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncbinDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncludeDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class M68kRootStructureViewTreeElement extends PsiTreeElementBase<M68kFile> {
  M68kRootStructureViewTreeElement(M68kFile psiFile) {
    super(psiFile);
  }

  @Override
  public String getPresentableText() {
    final M68kFile element = getElement();
    assert element != null;
    return element.getName();
  }

  @NotNull
  @Override
  public Collection<StructureViewTreeElement> getChildrenBase() {
    final List<PsiElement> children = PsiTreeUtil.getChildrenOfAnyType(getValue(),
      M68kLabelBase.class, M68kEquDirectiveBase.class,
      M68kIncludeDirective.class, M68kIncbinDirective.class);

    Collection<StructureViewTreeElement> nodes = new ArrayList<>();
    for (PsiElement child : children) {
      if (child instanceof M68kLabel) {
        M68kLabel m68kLabel = (M68kLabel) child;
        nodes.add(new M68kStructureViewNode(m68kLabel));
      } else if (child instanceof M68kLocalLabel) {
        M68kLocalLabel m68kLocalLabel = (M68kLocalLabel) child;
        nodes.add(new M68kStructureViewNode(m68kLocalLabel));
      } else if (child instanceof M68kEquDirectiveBase) {  // todo refactor to mixin + itemPresentation
        M68kEquDirectiveBase equDirective = (M68kEquDirectiveBase) child;
        nodes.add(new PsiTreeElementBase<M68kEquDirectiveBase>(equDirective) {
          @Nullable
          @Override
          public String getPresentableText() {
            return equDirective.getLabel().getText();
          }

          @Override
          public String getLocationString() {
            final M68kExpression expression = equDirective.getExpression();
            return expression != null ? expression.getText() : null;
          }

          @Override
          public Icon getIcon(boolean open) {
            return AllIcons.Nodes.Constant;
          }

          @NotNull
          @Override
          public Collection<StructureViewTreeElement> getChildrenBase() {
            return Collections.emptyList();
          }
        });
      } else if (child instanceof M68kIncludeDirective) {
        M68kIncludeDirective includeDirective = (M68kIncludeDirective) child;
        nodes.add(new PsiTreeElementBase<M68kIncludeDirective>(includeDirective) {
          @Override
          @Nullable
          public String getPresentableText() {
            return includeDirective.getIncludePath();
          }

          @Override
          public Icon getIcon(boolean open) {
            return AllIcons.Graph.Layout;
          }

          @Override
          @NotNull
          public Collection<StructureViewTreeElement> getChildrenBase() {
            return Collections.emptyList();
          }
        });
      } else if (child instanceof M68kIncbinDirective) {
        M68kIncbinDirective incbinDirective = (M68kIncbinDirective) child;
        nodes.add(new PsiTreeElementBase<M68kIncbinDirective>(incbinDirective) {
          @Override
          @Nullable
          public String getPresentableText() {
            return incbinDirective.getIncludePath();
          }

          @Override
          public Icon getIcon(boolean open) {
            return AllIcons.FileTypes.Archive;
          }

          @Override
          @NotNull
          public Collection<StructureViewTreeElement> getChildrenBase() {
            return Collections.emptyList();
          }
        });
      }
    }

    return nodes;
  }

  private static class M68kStructureViewNode extends PsiTreeElementBase<PsiElement> {

    M68kStructureViewNode(PsiElement element) {
      super(element);
    }

    @Nullable
    @Override
    public String getPresentableText() {
      final ItemPresentation itemPresentation = getItemPresentation();
      return itemPresentation != null ? itemPresentation.getPresentableText() : null;
    }

    @Override
    public Icon getIcon(boolean open) {
      final ItemPresentation itemPresentation = getItemPresentation();
      return itemPresentation != null ? itemPresentation.getIcon(false) : null;
    }

    @Nullable
    private ItemPresentation getItemPresentation() {
      PsiElement element = getElement();
      if (element instanceof NavigationItem) {
        return ((NavigationItem) element).getPresentation();
      }
      return null;
    }

    @NotNull
    @Override
    public Collection<StructureViewTreeElement> getChildrenBase() {
      return Collections.emptyList();
    }
  }
}
