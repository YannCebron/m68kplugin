/*
 * Copyright 2021 The Authors
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
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.CollectionListModel;
import com.intellij.util.Function;
import com.intellij.util.IconUtil;
import com.intellij.util.containers.ContainerUtil;
import com.twelvemonkeys.lang.StringUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.documentation.M68kInstructionDocumentationProvider;
import com.yanncebron.m68kplugin.lang.psi.M68kCpu;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonic;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistry;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

class M68kMnemonicsPanel extends M68kListWithDocsPanelBase<M68kMnemonicsPanel.MnemonicEntry> {

  private static final String SHOW_REFERENCE_DOCS_SETTINGS_KEY = "M68kMnemonicsPanel.show.ref.docs";
  private static final String SHOW_MC68010_SETTINGS_KEY = "M68kMnemonicsPanel.show.mc68010";

  private boolean isShowMc68010;

  private boolean isShowReferenceDocs;

  protected ActionGroup getToolbarActionGroup() {
    DefaultActionGroup actionGroup = new DefaultActionGroup();

    isShowMc68010 = PropertiesComponent.getInstance().getBoolean(SHOW_MC68010_SETTINGS_KEY, true);
    actionGroup.addAction(new DumbAwareToggleAction(
      M68kBundle.message("toolwindow.tab.mnemonic.include.cpu.only.mnemonics", M68kCpu.M_68010.getCpuName()),
      null,
      IconUtil.addText(AllIcons.Actions.PreviewDetails, M68kCpu.M_68010.getCpuCode())
    ) {

      @Override
      public boolean isSelected(@NotNull AnActionEvent e) {
        return isShowMc68010;
      }

      @Override
      public void setSelected(@NotNull AnActionEvent e, boolean state) {
        isShowMc68010 = state;
        PropertiesComponent.getInstance().setValue(SHOW_MC68010_SETTINGS_KEY, isShowMc68010, true);
        initList();
      }
    });

    actionGroup.addSeparator();

    isShowReferenceDocs = PropertiesComponent.getInstance().getBoolean(SHOW_REFERENCE_DOCS_SETTINGS_KEY, true);
    actionGroup.addAction(new DumbAwareToggleAction(
      M68kBundle.message("toolwindow.tab.mnemonic.show.reference.docs"), null, AllIcons.Actions.Preview) {
      @Override
      public boolean isSelected(@NotNull AnActionEvent e) {
        return isShowReferenceDocs;
      }

      @Override
      public void setSelected(@NotNull AnActionEvent e, boolean state) {
        isShowReferenceDocs = state;
        PropertiesComponent.getInstance().setValue(SHOW_REFERENCE_DOCS_SETTINGS_KEY, isShowReferenceDocs, true);
        updateDoc();
      }
    });

    return actionGroup;
  }

  @Override
  protected void initList() {
    final M68kMnemonicRegistry instance = M68kMnemonicRegistry.getInstance();
    CollectionListModel<M68kMnemonicsPanel.MnemonicEntry> model = new CollectionListModel<>();
    for (IElementType type : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      final Collection<M68kMnemonic> all = instance.findAll(type);
      final M68kMnemonic mnemonic = ContainerUtil.getFirstItem(all);

      if (!isShowMc68010 && !mnemonic.getCpus().contains(M68kCpu.M_68000)) {
        continue;
      }

      model.add(new M68kMnemonicsPanel.MnemonicEntry(mnemonic));
    }
    setListModel(model);
  }

  @Override
  protected Function<? super MnemonicEntry, String> getListItemNamer() {
    return M68kMnemonicsPanel.MnemonicEntry::getListName;
  }

  protected @NotNull String getDocFor(@NotNull MnemonicEntry selected) {
    final String mnemonicDoc = M68kInstructionDocumentationProvider.getMnemonicDoc(selected.mnemonic.getElementType(), null);
    final String referenceDoc = isShowReferenceDocs ?
      "<hr/>" + M68kInstructionDocumentationProvider.getInstructionReferenceDoc(selected.mnemonic.getElementType()) : "";

    return M68kInstructionDocumentationProvider.CSS + mnemonicDoc + referenceDoc;
  }


  protected static class MnemonicEntry {

    private final M68kMnemonic mnemonic;

    MnemonicEntry(M68kMnemonic mnemonic) {
      this.mnemonic = mnemonic;
    }

    String getListName() {
      return StringUtil.toUpperCase(mnemonic.getElementType().toString());
    }
  }

}
