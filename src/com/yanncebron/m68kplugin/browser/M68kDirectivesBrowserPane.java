/*
 * Copyright 2024 The Authors
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

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.Convertor;
import com.yanncebron.m68kplugin.documentation.M68kDirectiveDocumentationProvider;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

final class M68kDirectivesBrowserPane extends M68kBrowserPaneBase<IElementType> {

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
    setListItems(items);
  }

  @Override
  protected ActionGroup getToolbarActionGroup() {
    return null;
  }

  @Override
  protected Convertor<? super IElementType, String> getListItemNamer() {
    return (Convertor<IElementType, String>) elementType -> StringUtil.toUpperCase(elementType.toString());
  }

  @Override
  protected @NotNull String getDocFor(@NotNull IElementType selectedValue) {
    return M68kDirectiveDocumentationProvider.getDirectiveDoc(selectedValue);
  }

}
