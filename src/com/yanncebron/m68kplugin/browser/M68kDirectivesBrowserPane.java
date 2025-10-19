/*
 * Copyright 2025 The Authors
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

package com.yanncebron.m68kplugin.browser;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.NaturalComparator;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.containers.Convertor;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.documentation.M68kDirectiveDocumentationProvider;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

final class M68kDirectivesBrowserPane extends M68kBrowserPaneBase<IElementType> {

  private Ref<Boolean> isShowConditionalAssemblyDirectives;

  public M68kDirectivesBrowserPane() {
    super(IElementType.class);
  }

  @Override
  protected void initList() {
    List<IElementType> items = new ArrayList<>();
    for (IElementType type : M68kTokenGroups.DIRECTIVES.getTypes()) {
      if (type == M68kTokenTypes.EQ_DIRECTIVE) continue;

      items.add(type);
    }
    if (isShowConditionalAssemblyDirectives.get()) {
      items.addAll(Arrays.asList(M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES.getTypes()));
    }

    items.sort(Comparator.comparing(IElementType::toString, NaturalComparator.INSTANCE));
    setListItems(items);
  }

  @Override
  protected ActionGroup getToolbarActionGroup() {
    DefaultActionGroup actionGroup = new DefaultActionGroup();

    isShowConditionalAssemblyDirectives = Ref.create(Boolean.TRUE);
    actionGroup.add(
      createToggleAction(
        M68kBundle.message("toolwindow.tab.directives.show.conditional.assembly.directives"),
        AllIcons.Debugger.EvaluateExpression,
        isShowConditionalAssemblyDirectives,
        "M68DirectivesBrowserPane.show.conditional.assembly.directives",
        (anActionEvent, aBoolean) -> initList(),
        null
      )
    );
    return actionGroup;
  }

  @Override
  protected Convertor<? super IElementType, String> getListItemNamer() {
    return (Convertor<IElementType, String>) elementType -> StringUtil.toUpperCase(elementType.toString());
  }

  @Override
  protected @NotNull String getDocFor(@NotNull IElementType selectedValue) {
    return M68kDirectiveDocumentationProvider.getDirectiveDoc(selectedValue);
  }

  @Override
  protected ColoredListCellRenderer<IElementType> getListCellRenderer() {
    return new ColoredListCellRenderer<>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends IElementType> list, IElementType value, int index, boolean selected, boolean hasFocus) {
        if (M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES.contains(value)) {
          setIcon(AllIcons.Debugger.EvaluateExpression);
        }

        append(getListItemNamer().convert(value));
        SpeedSearchUtil.applySpeedSearchHighlighting(list, this, true, selected);
      }
    };
  }
}
