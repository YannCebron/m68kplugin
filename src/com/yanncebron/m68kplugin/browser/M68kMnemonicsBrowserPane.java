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
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.NaturalComparator;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.IconUtil;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.Convertor;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.documentation.M68kInstructionDocumentationProvider;
import com.yanncebron.m68kplugin.lang.psi.M68kCpu;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonic;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistry;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

final class M68kMnemonicsBrowserPane extends M68kBrowserPaneBase<M68kMnemonic> {

  private Ref<Boolean> isShowMc68010;

  private Ref<Boolean> isShowReferenceDocs;

  private M68kMnemonicsBrowserPane(Project project) {
    super(M68kMnemonic.class, project);
  }

  protected ActionGroup getToolbarActionGroup() {
    DefaultActionGroup actionGroup = new DefaultActionGroup();

    isShowMc68010 = Ref.create(Boolean.TRUE);
    actionGroup.add(
      createToggleAction(
        M68kBundle.message("toolwindow.tab.mnemonic.include.cpu.only.mnemonics", M68kCpu.M_68010.getCpuName()),
        IconUtil.addText(AllIcons.Actions.PreviewDetails, M68kCpu.M_68010.getCpuCode()),
        isShowMc68010,
        "M68kMnemonicsPanel.show.mc68010",
        (anActionEvent, state) -> initList(),
        null
      ));

    actionGroup.addSeparator();

    isShowReferenceDocs = Ref.create(Boolean.TRUE);
    actionGroup.add(
      createToggleAction(
        M68kBundle.message("toolwindow.tab.mnemonic.show.reference.docs"),
        AllIcons.General.ReaderMode,
        isShowReferenceDocs,
        "M68kMnemonicsPanel.show.ref.docs",
        (anActionEvent, aBoolean) -> updateDoc(),
        null
      ));

    return actionGroup;
  }

  @Override
  protected void initList() {
    final M68kMnemonicRegistry instance = M68kMnemonicRegistry.getInstance();
    List<M68kMnemonic> items = new ArrayList<>();
    for (IElementType type : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      final Collection<M68kMnemonic> all = instance.findAll(type);
      final M68kMnemonic mnemonic = ContainerUtil.getFirstItem(all);

      if (!isShowMc68010.get() && !mnemonic.cpus().contains(M68kCpu.M_68000)) {
        continue;
      }

      items.add(mnemonic);
    }

    items.sort(Comparator.comparing(m68kMnemonic -> m68kMnemonic.elementType().toString(), NaturalComparator.INSTANCE));
    setListItems(items);
  }

  @Override
  protected Convertor<? super M68kMnemonic, String> getListItemNamer() {
    return (Convertor<M68kMnemonic, String>) mnemonic -> StringUtil.toUpperCase(mnemonic.elementType().toString());
  }

  protected @NotNull String getDocFor(@NotNull M68kMnemonic mnemonic) {
    final String mnemonicDoc = M68kInstructionDocumentationProvider.getMnemonicDoc(mnemonic.elementType(), null);
    final String referenceDoc = isShowReferenceDocs.get() ?
      "<br/>" + M68kInstructionDocumentationProvider.getInstructionReferenceDoc(mnemonic.elementType()) : "";

    return mnemonicDoc + DocumentationMarkup.CONTENT_START + referenceDoc + DocumentationMarkup.CONTENT_END;
  }

  @Override
  protected ColoredListCellRenderer<M68kMnemonic> getListCellRenderer() {
    return new ColoredListCellRenderer<>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends M68kMnemonic> list, M68kMnemonic value, int index, boolean selected, boolean hasFocus) {
        append(getListItemNamer().convert(value));

        if (isShowMc68010.get() && !value.cpus().contains(M68kCpu.M_68000)) {
          append(" (" + M68kCpu.M_68010.getCpuName() + ")", SimpleTextAttributes.GRAY_ATTRIBUTES);
        }

        SpeedSearchUtil.applySpeedSearchHighlighting(list, this, true, selected);
      }
    };
  }

  static final class Factory implements M68kBrowserPaneFactory<M68kMnemonicsBrowserPane> {

    @Override
    public M68kMnemonicsBrowserPane createPane(Project project) {
      return new M68kMnemonicsBrowserPane(project);
    }
  }
}
