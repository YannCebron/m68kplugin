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
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.NaturalComparator;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.Convertor;
import com.yanncebron.m68kplugin.amiga.M68kAmigaBundle;
import com.yanncebron.m68kplugin.browser.M68kBrowserPaneBase;
import com.yanncebron.m68kplugin.documentation.M68kDocumentationUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class M68kAmigaHardwareBrowserPane extends M68kBrowserPaneBase<M68kAmigaHardwareRegister> {

  private static final String DOC_ROOT = "/docs/amigaHardwareRegister/";

  private Ref<Boolean> isAnnotateChipset;
  private Ref<Boolean> isShowReferenceDocs;

  public M68kAmigaHardwareBrowserPane() {
    super(M68kAmigaHardwareRegister.class);
  }

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

  private static @NotNull List<M68kAmigaHardwareRegister> getSortedShownRegisters() {
    M68kAmigaHardwareRegister.Chipset chipsetSetting = getSelectedChipset();

    List<M68kAmigaHardwareRegister> items = new ArrayList<>();
    for (M68kAmigaHardwareRegister register : M68kAmigaHardwareRegister.values()) {
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
    StringBuilder sb = new StringBuilder(M68kDocumentationUtil.CSS);

    sb.append(DocumentationMarkup.DEFINITION_START);
    sb.append("<h1>").append(selectedValue.getName()).append("</h1>");
    sb.append(selectedValue.getDescription());
    sb.append(DocumentationMarkup.DEFINITION_END);

    sb.append(DocumentationMarkup.SECTIONS_START);

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Address:");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    String address = "$" + selectedValue.getAddress();
    String shortAddress = "$0" + address.substring(4);
    sb.append("<code>").append(address).append("</code>")
      .append("<a href='").append(M68kBrowserPaneBase.M68K_BROWSER_COPY_DATA_LINK_PREFIX).append(address).append("'><icon src='AllIcons.Actions.Copy'/></a>")
      .append("&nbsp;&ndash;&nbsp;")
      .append("<code>").append(shortAddress).append("</code>")
      .append("<a href='").append(M68kBrowserPaneBase.M68K_BROWSER_COPY_DATA_LINK_PREFIX).append(shortAddress).append("'><icon src='AllIcons.Actions.Copy'/></a>");
    sb.append(DocumentationMarkup.SECTION_END);

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Chip Set (Chips):");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    sb.append(selectedValue.getChipset().getDisplayName());
    sb.append(" (").append(StringUtil.join(selectedValue.getChips(), M68kAmigaHardwareRegister.Chip::getDisplayName, ", ")).append(")");
    sb.append(DocumentationMarkup.SECTION_END);

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Access:");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    sb.append(selectedValue.getAccess().getDisplayName());
    sb.append(DocumentationMarkup.SECTION_END);

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Copper Danger:");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    sb.append(selectedValue.isCopperDanger() ? M68kDocumentationUtil.CHECK_MARK : "-");
    sb.append(DocumentationMarkup.SECTION_END);

    appendRelated(sb, selectedValue);

    sb.append(DocumentationMarkup.SECTIONS_END);

    if (isShowReferenceDocs.get()) {
      sb.append(DocumentationMarkup.CONTENT_START);
      sb.append(getReferenceDoc(selectedValue.getDescriptionFileName()));
      sb.append(DocumentationMarkup.CONTENT_END);
    }

    return sb.toString();
  }

  private static void appendRelated(StringBuilder sb, M68kAmigaHardwareRegister selectedValue) {
    if (EnumSet.range(M68kAmigaHardwareRegister.COLOR00, M68kAmigaHardwareRegister.COLOR31).contains(selectedValue)) {
//      return;
    }

    List<M68kAmigaHardwareRegister> related = ContainerUtil.filter(getSortedShownRegisters(),
      register -> selectedValue.getDescriptionFileName().equals(register.getDescriptionFileName()));
    if (related.size() == 1) return;

    StringBuilder relatedSb = new StringBuilder("<table><tr>");
    int itemCount = 1;
    for (M68kAmigaHardwareRegister register : related) {
      relatedSb.append(DocumentationMarkup.SECTION_START);
      if (register == selectedValue) relatedSb.append("<b>");
      relatedSb.append("<a href='" + M68K_BROWSER_ITEM_LINK_PREFIX).append(register.name()).append("'>");
      relatedSb.append(register.getName()).append("</a>");
      if (register == selectedValue) relatedSb.append("</b>");
      relatedSb.append("</td>");
      if (itemCount++ % 4 == 0) relatedSb.append("</tr><tr>");
    }
    relatedSb.append("</tr></table>");

    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append("Related:");
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    sb.append(relatedSb);
    sb.append(DocumentationMarkup.SECTION_END);
  }

  private String getReferenceDoc(String markdownFileName) {
    Couple<String> markdownContents = M68kDocumentationUtil.getMarkdownContents(DOC_ROOT, markdownFileName);
    if (markdownContents.getFirst() == null) {
      return markdownContents.getSecond();
    }

    return M68kDocumentationUtil.getHtmlForMarkdown(DOC_ROOT, markdownContents.getFirst(), M68K_BROWSER_LINK_FUNCTION);
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

}
