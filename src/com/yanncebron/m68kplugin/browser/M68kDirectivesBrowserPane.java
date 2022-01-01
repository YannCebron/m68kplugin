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

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.Function;
import com.twelvemonkeys.lang.StringUtil;
import com.yanncebron.m68kplugin.documentation.M68kDocumentationUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class M68kDirectivesBrowserPane extends M68kBrowserPaneBase<M68kDirectivesBrowserPane.DirectiveEntry> {

  @Override
  public boolean isAvailable(Project project) {
    return ApplicationManager.getApplication().isInternal();
  }

  @Override
  protected void initList() {
    List<DirectiveEntry> items = new ArrayList<>();
    for (IElementType type : M68kTokenGroups.DIRECTIVES.getTypes()) {
      items.add(new DirectiveEntry(type));
    }
    setListItems(items);
  }

  @Override
  protected ActionGroup getToolbarActionGroup() {
    return null;
  }

  @Override
  protected Function<? super DirectiveEntry, String> getListItemNamer() {
    return DirectiveEntry::getListName;
  }

  @Override
  protected @NotNull String getDocFor(@NotNull M68kDirectivesBrowserPane.DirectiveEntry selectedValue) {
    return M68kDocumentationUtil.CSS +
      "<h1>" + selectedValue.getListName() + "</h1>" +
      "<em>TODO</em> documentation for this directive ";
  }

  protected static class DirectiveEntry {
    private final IElementType directiveType;

    DirectiveEntry(IElementType directiveType) {
      this.directiveType = directiveType;
    }

    String getListName() {
      return StringUtil.toUpperCase(directiveType.toString());
    }
  }
}
