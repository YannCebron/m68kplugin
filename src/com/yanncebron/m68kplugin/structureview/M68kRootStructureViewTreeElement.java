/*
 * Copyright 2024 The Authors
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

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.M68kFile;
import icons.M68kIcons;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

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
      M68kLabel.class, M68kMacroDirective.class,
      M68kEquDirectiveBase.class,
      M68kEqurDirective.class, M68kRegDirective.class,
      M68kFoDirective.class, M68kSoDirective.class,
      M68kIncludeDirective.class, M68kIncbinDirective.class);

    Collection<StructureViewTreeElement> nodes = new ArrayList<>();
    for (PsiElement child : children) {
      if (child instanceof M68kLabel m68kLabel) {
        nodes.add(new M68kStructureViewNode(m68kLabel) {
          @Override
          public @NotNull Collection<StructureViewTreeElement> getChildrenBase() {
            return ContainerUtil.map(m68kLabel.getLocalLabels(), M68kStructureViewNode::new);
          }
        });
      } else if (child instanceof M68kMacroDirective macroDirective) {
        nodes.add(new M68kStructureViewNode(macroDirective.getLabel()));
      } else if (child instanceof M68kEquDirectiveBase equDirective) {
        nodes.add(new M68kStructureViewNode(equDirective.getLabel()));
      } else if (child instanceof M68kEqurDirective equrDirective) {
        nodes.add(new M68kStructureViewNode(equrDirective.getLabel()));
      } else if (child instanceof M68kRegDirective regDirective) {
        nodes.add(new M68kStructureViewNode(regDirective.getLabel()));
      } else if (child instanceof M68kFoDirective foDirective) {
        nodes.add(new M68kStructureViewNode(foDirective.getLabel()));
      } else if (child instanceof M68kSoDirective soDirective) {
        nodes.add(new M68kStructureViewNode(soDirective.getLabel()));
      } else if (child instanceof M68kIncludeDirective includeDirective) {
        nodes.add(new PsiTreeElementBase<>(includeDirective) {
          @Override
          @Nullable
          public String getPresentableText() {
            return includeDirective.getIncludePath();
          }

          @Override
          public Icon getIcon(boolean open) {
            return M68kIcons.INCLUDE;
          }

          @Override
          @NotNull
          public Collection<StructureViewTreeElement> getChildrenBase() {
            return Collections.emptyList();
          }
        });
      } else if (child instanceof M68kIncbinDirective incbinDirective) {
        nodes.add(new PsiTreeElementBase<>(incbinDirective) {
          @Override
          @Nullable
          public String getPresentableText() {
            return incbinDirective.getIncludePath();
          }

          @Override
          public Icon getIcon(boolean open) {
            return M68kIcons.INCBIN;
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

    @Override
    public String getLocationString() {
      final PsiElement element = getElement();
      if (element instanceof M68kLabel) {
        return ((M68kLabel) element).getValue();
      }
      return null;
    }

    @Nullable
    @Override
    public String getPresentableText() {
      return getItemPresentation().getPresentableText();
    }

    @Override
    public Icon getIcon(boolean open) {
      return getItemPresentation().getIcon(false);
    }

    @NotNull
    private ItemPresentation getItemPresentation() {
      PsiElement element = getElement();
      assert element instanceof NavigationItem : element;
      return Objects.requireNonNull(((NavigationItem) element).getPresentation());
    }

    @NotNull
    @Override
    public Collection<StructureViewTreeElement> getChildrenBase() {
      return Collections.emptyList();
    }
  }
}
