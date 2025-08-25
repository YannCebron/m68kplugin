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

package com.yanncebron.m68kplugin.navigation;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.resolve.M68kImplicitMacroLabelResolver;
import icons.M68kIcons;
import org.jetbrains.annotations.Nullable;

class M68kGotoImplicitMacroLabelNavigationItem extends GoToSymbolProvider.BaseNavigationItem {

  private final M68kMacroCallDirective m68kMacroCallDirective;

  M68kGotoImplicitMacroLabelNavigationItem(M68kMacroCallDirective m68kMacroCallDirective) {
    super(m68kMacroCallDirective, M68kImplicitMacroLabelResolver.getFirstParameterText(m68kMacroCallDirective), M68kIcons.LABEL_MACRO);
    this.m68kMacroCallDirective = m68kMacroCallDirective;
  }

  @Override
  public ItemPresentation getPresentation() {
    // do not remove spaces, otherwise parentheses get removed in presentation
    return new PresentationData(getName(), " (" + M68kImplicitMacroLabelResolver.getImplicitMacroLabelLocationString(m68kMacroCallDirective) + ") ", getIcon(0), null);
  }

  @Override
  public @Nullable TextRange getTextRange() {
    return m68kMacroCallDirective.getTextRange();
  }

}
