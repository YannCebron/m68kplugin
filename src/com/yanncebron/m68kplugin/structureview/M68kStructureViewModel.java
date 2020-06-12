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
import com.intellij.util.BooleanFunction;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class M68kStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

  public M68kStructureViewModel(M68kFile psiFile, Editor editor) {
    super(psiFile, editor, new M68kRootStructureViewTreeElement(psiFile));
    withSorters(Sorter.ALPHA_SORTER);
    withSuitableClasses(M68kLabelBase.class, M68kEquDirectiveBase.class,
      M68kIncludeDirective.class, M68kIncbinDirective.class);
  }

  @Override
  public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
    return false;
  }

  @Override
  public boolean isAlwaysLeaf(StructureViewTreeElement element) {
    final Object value = element.getValue();
    return value instanceof M68kLabelBase || value instanceof M68kEquDirectiveBase ||
      value instanceof M68kIncludeDirective || value instanceof M68kIncbinDirective;
  }

  @NotNull
  @Override
  public Filter[] getFilters() {
    return new Filter[]{
      createFilter(psiElement -> psiElement instanceof M68kIncludeDirective || psiElement instanceof M68kIncbinDirective,
        "Show includes", AllIcons.Graph.Layout, "SHOW_INCLUDES"),
      createFilter(psiElement -> psiElement instanceof M68kLabel,
        "Show labels", AllIcons.Nodes.Method, "SHOW_LABELS"),
      createFilter(psiElement -> psiElement instanceof M68kLocalLabel,
        "Show local labels", AllIcons.Nodes.AbstractMethod, "SHOW_LOCAL_LABELS"),
      createFilter(psiElement -> psiElement instanceof M68kEquDirectiveBase,
        "Show EQUs", AllIcons.Nodes.Constant, "SHOW_EQU_DIRECTIVES")
    };
  }

  private static Filter createFilter(BooleanFunction<PsiElement> filter,
                                     @NotNull String text, Icon icon, @NotNull String name) {
    return new Filter() {
      @Override
      public boolean isVisible(TreeElement treeNode) {
        if (!(treeNode instanceof PsiTreeElementBase)) return false;

        return !filter.fun(((PsiTreeElementBase) treeNode).getElement());
      }

      @Override
      public boolean isReverted() {
        return true;
      }

      @Override
      public @NotNull ActionPresentation getPresentation() {
        return new ActionPresentationData(text, null, icon);
      }

      @Override
      @NotNull
      public String getName() {
        return name;
      }
    };
  }
}
