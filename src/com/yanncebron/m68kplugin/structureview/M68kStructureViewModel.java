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
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.ide.util.treeView.smartTree.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.M68kEquDirectiveBase;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import org.jetbrains.annotations.NotNull;

public class M68kStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

  public M68kStructureViewModel(M68kFile psiFile, Editor editor) {
    super(psiFile, editor, new M68kRootStructureViewTreeElement(psiFile));
    withSorters(Sorter.ALPHA_SORTER);
    withSuitableClasses(M68kLabelBase.class, M68kEquDirectiveBase.class);
  }

  @Override
  public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
    return false;
  }

  @Override
  public boolean isAlwaysLeaf(StructureViewTreeElement element) {
    return element.getValue() instanceof M68kLabelBase ||
      element.getValue() instanceof M68kEquDirectiveBase;
  }

  @NotNull
  @Override
  public Filter[] getFilters() {
    return new Filter[]{
      new Filter() {
        @Override
        public boolean isVisible(TreeElement treeNode) {
          if (!(treeNode instanceof PsiTreeElementBase)) return false;

          return !(((PsiTreeElementBase) treeNode).getElement() instanceof M68kLabel);
        }

        @Override
        public boolean isReverted() {
          return true;
        }

        @NotNull
        @Override
        public ActionPresentation getPresentation() {
          return new ActionPresentationData("Show labels", null, AllIcons.Nodes.Method);
        }

        @NotNull
        @Override
        public String getName() {
          return "SHOW_LABELS";
        }
      }, new Filter() {
      @Override
      public boolean isVisible(TreeElement treeNode) {
        if (!(treeNode instanceof PsiTreeElementBase)) return false;

        return !(((PsiTreeElementBase) treeNode).getElement() instanceof M68kLocalLabel);
      }

      @Override
      public boolean isReverted() {
        return true;
      }

      @NotNull
      @Override
      public ActionPresentation getPresentation() {
        return new ActionPresentationData("Show local labels", null, AllIcons.Nodes.AbstractMethod);
      }

      @NotNull
      @Override
      public String getName() {
        return "SHOW_LOCAL_LABELS";
      }
    },
      new Filter() {
        @Override
        public boolean isVisible(TreeElement treeNode) {
          if (!(treeNode instanceof PsiTreeElementBase)) return false;

          final PsiElement element = ((PsiTreeElementBase) treeNode).getElement();
          return !(element instanceof M68kEquDirectiveBase);
        }

        @Override
        public boolean isReverted() {
          return true;
        }

        @NotNull
        @Override
        public ActionPresentation getPresentation() {
          return new ActionPresentationData("Show EQUs", null, AllIcons.Nodes.Constant);
        }

        @NotNull
        @Override
        public String getName() {
          return "SHOW_EQUS";
        }
      }
    };
  }
}
