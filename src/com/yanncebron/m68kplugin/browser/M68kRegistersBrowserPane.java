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
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.NaturalComparator;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.IconUtil;
import com.intellij.util.containers.Convertor;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.documentation.M68kRegisterDocsGenerator;
import com.yanncebron.m68kplugin.lang.psi.M68kCpu;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

final class M68kRegistersBrowserPane extends M68kBrowserPaneBase<M68kRegister> {

  private Ref<Boolean> isShowMc68010;

  private M68kRegistersBrowserPane(Project project) {
    super(M68kRegister.class, project);
  }

  @Override
  protected ActionGroup getToolbarActionGroup() {
    DefaultActionGroup actionGroup = new DefaultActionGroup();

    isShowMc68010 = Ref.create(Boolean.TRUE);
    actionGroup.add(
      createToggleAction(
        M68kBundle.message("toolwindow.tab.registers.include.cpu.only.registers", M68kCpu.M_68010.getCpuName()),
        IconUtil.addText(AllIcons.Actions.PreviewDetails, M68kCpu.M_68010.getCpuCode()),
        isShowMc68010,
        "M68kRegistersBrowserPane.show.mc68010",
        (anActionEvent, state) -> initList(),
        null
      ));

    return actionGroup;
  }

  @Override
  protected void initList() {
    List<M68kRegister> items = new ArrayList<>();
    for (M68kRegister register : M68kRegister.values()) {
      if (!isShowMc68010.get() && !register.getCpus().contains(M68kCpu.M_68000)) {
        continue;
      }
      items.add(register);
    }

    items.sort(Comparator.comparing(M68kRegister::name, NaturalComparator.INSTANCE));
    setListItems(items);
  }

  @Override
  protected Convertor<? super M68kRegister, String> getListItemNamer() {
    return Enum::name;
  }

  @Override
  protected @NotNull String getDocFor(@NotNull M68kRegister selectedValue) {
    return new M68kRegisterDocsGenerator(selectedValue).getDocumentation();
  }

  @Override
  protected ColoredListCellRenderer<M68kRegister> getListCellRenderer() {
    return new ColoredListCellRenderer<>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends M68kRegister> list, M68kRegister value, int index, boolean selected, boolean hasFocus) {
        append(getListItemNamer().convert(value));

        if (isShowMc68010.get() && !value.getCpus().contains(M68kCpu.M_68000)) {
          append(" (" + M68kCpu.M_68010.getCpuName() + ")", SimpleTextAttributes.GRAY_ATTRIBUTES);
        }

        SpeedSearchUtil.applySpeedSearchHighlighting(list, this, true, selected);
      }
    };
  }

  static final class Factory implements M68kBrowserPaneFactory<M68kRegistersBrowserPane> {

    @Override
    public M68kRegistersBrowserPane createPane(Project project) {
      return new M68kRegistersBrowserPane(project);
    }
  }
}
