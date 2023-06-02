/*
 * Copyright 2023 The Authors
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
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.Function;
import com.intellij.util.IconUtil;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.documentation.M68kDocumentationUtil;
import com.yanncebron.m68kplugin.documentation.M68kInstructionDocumentationProvider;
import com.yanncebron.m68kplugin.lang.psi.M68kCpu;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonic;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistry;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class M68kMnemonicsBrowserPane extends M68kBrowserPaneBase<M68kMnemonic> {

  private Ref<Boolean> isShowMc68010;

  private Ref<Boolean> isShowReferenceDocs;

  public M68kMnemonicsBrowserPane() {
    super(M68kMnemonic.class);
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

      if (!isShowMc68010.get() && !mnemonic.getCpus().contains(M68kCpu.M_68000)) {
        continue;
      }

      items.add(mnemonic);
    }
    setListItems(items);
  }

  @Override
  protected Function<? super M68kMnemonic, String> getListItemNamer() {
    return (Function<M68kMnemonic, String>) mnemonic -> StringUtil.toUpperCase(mnemonic.getElementType().toString());
  }

  protected @NotNull String getDocFor(@NotNull M68kMnemonic mnemonic) {
    final String mnemonicDoc = M68kInstructionDocumentationProvider.getMnemonicDoc(mnemonic.getElementType(), null);
    final String referenceDoc = isShowReferenceDocs.get() ?
      "<hr/>" + M68kInstructionDocumentationProvider.getInstructionReferenceDoc(mnemonic.getElementType()) : "";

    return M68kDocumentationUtil.CSS + mnemonicDoc + referenceDoc;
  }

}
