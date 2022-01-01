/*
 * Copyright 2022 The Authors
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

package com.yanncebron.m68kplugin.amiga.hardware;

import com.intellij.icons.AllIcons;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.Function;
import com.yanncebron.m68kplugin.amiga.M68kAmigaBundle;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase;
import com.yanncebron.m68kplugin.documentation.M68kDocumentationUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class M68kAmigaHardwareBrowserPane extends M68kBrowserPaneBase<M68kAmigaHardwareRegister> {

  private Ref<Boolean> isAnnotateChipset;

  @Override
  protected @Nullable ActionGroup getToolbarActionGroup() {
    DefaultActionGroup actionGroup = new DefaultActionGroup();
    actionGroup.add(new ChooseChipsetAction());

    isAnnotateChipset = Ref.create(Boolean.TRUE);
    actionGroup.add(
      createToggleAction(
        M68kAmigaBundle.message("toolwindow.tab.amiga.hardware.annotate.chipset"),
        AllIcons.Actions.ShowImportStatements,
        isAnnotateChipset,
        "M68kAmigaHardwareBrowserPane.AnnotateChipset",
        (anActionEvent, state) -> initList(),
        anActionEvent -> anActionEvent.getPresentation().setEnabled(getSelectedChipset() != M68kAmigaHardwareRegister.Chipset.OCS)
      ));
    return actionGroup;
  }

  @Override
  protected void initList() {
    M68kAmigaHardwareRegister.Chipset chipsetSetting = getSelectedChipset();

    List<M68kAmigaHardwareRegister> items = new ArrayList<>();
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
      M68kAmigaHardwareRegister.Chipset chipset = register.getChipset();
      if (chipsetSetting == M68kAmigaHardwareRegister.Chipset.OCS &&
        chipset != M68kAmigaHardwareRegister.Chipset.OCS) {
        continue;
      }
      if (chipsetSetting == M68kAmigaHardwareRegister.Chipset.ECS &&
        chipset == M68kAmigaHardwareRegister.Chipset.AGA) {
        continue;
      }

      items.add(register);
    }

    items.sort(Comparator.comparing(M68kAmigaHardwareRegister::getName));
    setListItems(items);
  }

  @Override
  protected Function<? super M68kAmigaHardwareRegister, String> getListItemNamer() {
    return M68kAmigaHardwareRegister::getName;
  }

  @Override
  protected @NotNull String getDocFor(@NotNull M68kAmigaHardwareRegister selectedValue) {
    String copperDanger = selectedValue.isCopperDanger() ? M68kDocumentationUtil.CHECK_MARK : "";
    String chips = StringUtil.join(selectedValue.getChips(), M68kAmigaHardwareRegister.Chip::getDisplayName, ",<br>");

    return M68kDocumentationUtil.CSS +
      "<h1>" + selectedValue.getName() + "</h1>" +
      "<h2><code>$" + selectedValue.getAddress() + " - $0" + selectedValue.getAddress().substring(3) + "" + "</code></h2>" +
      "<table style=\"width: 100%;\">" +
      "<tr><th style=\"text-align:center;\">Chip Set</th><th style=\"text-align:center;\">Access</th><th style=\"text-align:center;\">Copper Danger</th><th style=\"text-align:center;\">Chips</th></tr>" +
      "<tr>" +
      "<td style=\"text-align:center;\">" + selectedValue.getChipset().getDisplayName() + "</td>" +
      "<td style=\"text-align:center;\">" + selectedValue.getAccess().getDisplayName() + "</td>" +
      "<td style=\"text-align:center;\">" + copperDanger + "</td>" +
      "<td style=\"text-align:center;\">" + chips + "</td>" +
      "</tr></table>" +
      "<br>" +
      selectedValue.getDescription();
  }

  @Override
  protected ColoredListCellRenderer<M68kAmigaHardwareRegister> getListCellRenderer() {
    return new ColoredListCellRenderer<>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends M68kAmigaHardwareRegister> list, M68kAmigaHardwareRegister value, int index, boolean selected, boolean hasFocus) {
        append(getListItemNamer().fun(value));

        if (isAnnotateChipset.get()) {
          M68kAmigaHardwareRegister.Chipset chipset = value.getChipset();
          if (chipset != M68kAmigaHardwareRegister.Chipset.OCS) {
            append(" (" + chipset.getDisplayName() + ")", SimpleTextAttributes.GRAY_ATTRIBUTES);
          }
        }

        SpeedSearchUtil.applySpeedSearchHighlighting(list, this, true, selected);
      }
    };
  }

  @NonNls
  private static final String CHIPSET_SETTING = "M68kAmigaHardwareBrowserPane.Chipset";

  private static M68kAmigaHardwareRegister.Chipset getSelectedChipset() {
    String chipsetValue = PropertiesComponent.getInstance().getValue(CHIPSET_SETTING, M68kAmigaHardwareRegister.Chipset.AGA.name());
    return M68kAmigaHardwareRegister.Chipset.valueOf(chipsetValue);
  }


  private class ChooseChipsetAction extends ComboBoxAction implements DumbAware {

    @Override
    public void update(@NotNull AnActionEvent e) {
      e.getPresentation().setText(getSelectedChipset().getDisplayName());
      e.getPresentation().setDescription(M68kAmigaBundle.message("toolwindow.tab.amiga.hardware.choose.chipset"));
    }

    @Override
    protected @NotNull DefaultActionGroup createPopupActionGroup(JComponent button) {
      DefaultActionGroup chipsetGroup = new DefaultActionGroup();
      for (M68kAmigaHardwareRegister.Chipset chipset : M68kAmigaHardwareRegister.Chipset.values()) {
        chipsetGroup.add(createChipsetAction(chipset));
      }
      return chipsetGroup;
    }

    private AnAction createChipsetAction(M68kAmigaHardwareRegister.Chipset chipset) {
      return new DumbAwareToggleAction(chipset.getDisplayName() + " (" + chipset.getFullName() + ")") {

        @Override
        public boolean isSelected(@NotNull AnActionEvent e) {
          return getSelectedChipset().equals(chipset);
        }

        @Override
        public void setSelected(@NotNull AnActionEvent e, boolean state) {
          PropertiesComponent.getInstance().setValue(CHIPSET_SETTING, chipset.name());
          M68kAmigaHardwareBrowserPane.this.initList();
        }
      };
    }

  }

}
