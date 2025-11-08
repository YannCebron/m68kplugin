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

package com.yanncebron.m68kplugin.amiga.hardware;

import com.intellij.icons.AllIcons;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.NaturalComparator;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.containers.Convertor;
import com.yanncebron.m68kplugin.amiga.M68kAmigaBundle;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class M68kAmigaHardwareBrowserPane extends M68kBrowserPaneBase<M68kAmigaHardwareRegister> {

  private Ref<Boolean> isShowCIA;
  private Ref<Boolean> isAnnotateChipset;
  private Ref<Boolean> isShowReferenceDocs;

  public M68kAmigaHardwareBrowserPane(Project project) {
    super(M68kAmigaHardwareRegister.class, project);
  }

  @Override
  protected @Nullable ActionGroup getToolbarActionGroup() {
    DefaultActionGroup actionGroup = new DefaultActionGroup();

    isShowCIA = Ref.create(Boolean.TRUE);
    actionGroup.add(
      createToggleAction(
        M68kAmigaBundle.message("toolwindow.tab.amiga.hardware.show.cia.registers"),
        AllIcons.Nodes.Plugin,
        isShowCIA,
        "M68kAmigaHardwareBrowserPane.ShowCIARegisters",
        (anActionEvent, state) -> initList(),
        null)
    );

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

    actionGroup.addSeparator();

    isShowReferenceDocs = Ref.create(Boolean.TRUE);
    actionGroup.add(
      createToggleAction(
        M68kAmigaBundle.message("toolwindow.tab.amiga.hardware.show.reference.docs"),
        AllIcons.General.ReaderMode,
        isShowReferenceDocs,
        "M68kAmigaHardwareBrowserPane.show.ref.docs",
        (anActionEvent, aBoolean) -> updateDoc(),
        null
      ));

    return actionGroup;
  }

  @Override
  protected void initList() {
    List<M68kAmigaHardwareRegister> items = getSortedShownRegisters();
    setListItems(items);
  }

  private @NotNull List<M68kAmigaHardwareRegister> getSortedShownRegisters() {
    M68kAmigaHardwareRegister.Chipset chipsetSetting = getSelectedChipset();

    List<M68kAmigaHardwareRegister> items = new ArrayList<>();
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
      if (register.getChipset() == M68kAmigaHardwareRegister.Chipset.N_A) {
        if (isShowCIA.get() &&
          (register.getChips().contains(M68kAmigaHardwareRegister.Chip.CIA_A) ||
            register.getChips().contains(M68kAmigaHardwareRegister.Chip.CIA_B))) {
          items.add(register);
        }
        continue;
      }

      M68kAmigaHardwareRegister.Chipset chipset = register.getChipset();
      if (chipset.ordinal() <= chipsetSetting.ordinal()) {
        items.add(register);
      }
    }

    items.sort(Comparator.comparing(M68kAmigaHardwareRegister::getName, NaturalComparator.INSTANCE));
    return items;
  }

  @Override
  protected Convertor<? super M68kAmigaHardwareRegister, String> getListItemNamer() {
    return M68kAmigaHardwareRegister::getName;
  }

  @Override
  protected String getListItemNameForLink(String link) {
    if (StringUtil.containsChar(link, 'x')) {
      for (M68kAmigaHardwareRegister value : getSortedShownRegisters()) {
        if (value.getDescriptionFileName().equals(link)) {
          return value.getName();
        }
      }
    }
    return super.getListItemNameForLink(link);
  }

  @Override
  protected @NotNull String getDocFor(@NotNull M68kAmigaHardwareRegister selectedValue) {
    return new M68kAmigaHardwareRegisterDocsCreator(selectedValue, getSortedShownRegisters())
      .generateDoc(isShowReferenceDocs.get());
  }

  @Override
  protected ColoredListCellRenderer<M68kAmigaHardwareRegister> getListCellRenderer() {
    return new ColoredListCellRenderer<>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends M68kAmigaHardwareRegister> list, M68kAmigaHardwareRegister value, int index, boolean selected, boolean hasFocus) {
        setIcon(value.getIcon());

        append(getListItemNamer().convert(value));

        if (isAnnotateChipset.get()) {
          M68kAmigaHardwareRegister.Chipset chipset = value.getChipset();
          if (chipset != M68kAmigaHardwareRegister.Chipset.N_A &&
            chipset != M68kAmigaHardwareRegister.Chipset.OCS) {
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
    public @NotNull ActionUpdateThread getActionUpdateThread() {
      return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
      e.getPresentation().setText(getSelectedChipset().getDisplayName());
      e.getPresentation().setDescription(M68kAmigaBundle.message("toolwindow.tab.amiga.hardware.choose.chipset"));
    }

    @Override
    protected @NotNull DefaultActionGroup createPopupActionGroup(@NotNull JComponent button, @NotNull DataContext dataContext) {
      DefaultActionGroup chipsetGroup = new DefaultActionGroup();
      for (M68kAmigaHardwareRegister.Chipset chipset : M68kAmigaHardwareRegister.Chipset.values()) {
        if (chipset == M68kAmigaHardwareRegister.Chipset.N_A) continue;
        chipsetGroup.add(createChipsetAction(chipset));
      }
      return chipsetGroup;
    }

    private AnAction createChipsetAction(M68kAmigaHardwareRegister.Chipset chipset) {
      return new DumbAwareToggleAction(chipset.getDisplayName() + " (" + chipset.getFullName() + ")") {

        @Override
        public @NotNull ActionUpdateThread getActionUpdateThread() {
          return ActionUpdateThread.BGT;
        }

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

  static final class Factory implements M68kBrowserPaneFactory<M68kAmigaHardwareBrowserPane> {

    @Override
    public M68kAmigaHardwareBrowserPane createPane(Project project) {
      return new M68kAmigaHardwareBrowserPane(project);
    }
  }
}
