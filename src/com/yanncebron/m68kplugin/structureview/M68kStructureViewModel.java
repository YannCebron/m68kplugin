/*
 * Copyright 2026 The Authors
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

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.ide.util.treeView.smartTree.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabel;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncbinDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncludeDirective;
import icons.M68kIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.EnumSet;
import java.util.function.Predicate;

class M68kStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {

  private static final Filter[] FILTERS = createFilters();

  M68kStructureViewModel(M68kFile psiFile, Editor editor) {
    super(psiFile, editor, new M68kRootStructureViewTreeElement(psiFile));
    withSorters(Sorter.ALPHA_SORTER);
    withSuitableClasses(M68kLabelBase.class, M68kIncludeDirective.class, M68kIncbinDirective.class);
  }

  @Override
  public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
    return false;
  }

  @Override
  public boolean isAlwaysLeaf(StructureViewTreeElement element) {
    final Object value = element.getValue();
    if (value instanceof M68kLabel label) {
      return label.getLabelKind() != M68kLabelBase.LabelKind.GLOBAL;
    }
    return value instanceof M68kIncludeDirective || value instanceof M68kIncbinDirective;
  }

  @NotNull
  @Override
  public Filter @NotNull [] getFilters() {
    return FILTERS;
  }

  private static Filter[] createFilters() {
    final EnumSet<M68kLabelBase.LabelKind> assignmentKinds = EnumSet.of(
      M68kLabelBase.LabelKind.EQU, M68kLabelBase.LabelKind.EQUALS,
      M68kLabelBase.LabelKind.SET, M68kLabelBase.LabelKind.EQUR,
      M68kLabelBase.LabelKind.FO, M68kLabelBase.LabelKind.SO
    );

    return new Filter[]{
      createFilter(psiElement -> psiElement instanceof M68kIncludeDirective || psiElement instanceof M68kIncbinDirective,
        M68kBundle.message("structure.view.filter.includes"), M68kIcons.INCLUDE, "SHOW_INCLUDES"),
      createFilter(psiElement -> psiElement instanceof M68kLabel label && label.getLabelKind() == M68kLabelBase.LabelKind.GLOBAL,
        M68kBundle.message("structure.view.filter.labels"), M68kIcons.LABEL_GLOBAL, "SHOW_LABELS"),
      createFilter(psiElement -> psiElement instanceof M68kLocalLabel,
        M68kBundle.message("structure.view.filter.local.labels"), M68kIcons.LABEL_LOCAL, "SHOW_LOCAL_LABELS"),
      createFilter(psiElement -> psiElement instanceof M68kLabel label && label.getLabelKind() == M68kLabelBase.LabelKind.MACRO,
        M68kBundle.message("structure.view.filter.macros"), M68kIcons.LABEL_MACRO, "SHOW_MACROS"),
      createFilter(psiElement -> psiElement instanceof M68kLabel label && assignmentKinds.contains(label.getLabelKind()),
        M68kBundle.message("structure.view.filter.assignments"), M68kIcons.LABEL_EQU, "SHOW_ASSIGNMENTS")
    };
  }

  private static Filter createFilter(Predicate<PsiElement> filter, @NotNull String text, Icon icon, @NotNull String name) {
    final ActionPresentation presentation = new ActionPresentationData(text, null, icon);
    return new Filter() {
      @Override
      public boolean isVisible(TreeElement treeNode) {
        if (!(treeNode instanceof PsiTreeElementBase<?> baseNode)) return false;

        return !filter.test(baseNode.getElement());
      }

      @Override
      public boolean isReverted() {
        return true;
      }

      @Override
      public @NotNull ActionPresentation getPresentation() {
        return presentation;
      }

      @Override
      public @NotNull String getName() {
        return name;
      }
    };
  }
}
