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

package com.yanncebron.m68kplugin.navigation;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.xml.model.gotosymbol.GoToSymbolProvider;
import com.yanncebron.m68kplugin.lang.psi.M68kLabel;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Show {@link M68kLabel#getValue()} in "Goto Symbol".
 *
 * @see M68kGotoLabelModuleRendererFactory
 */
class M68kGotoLabelNavigationItem extends GoToSymbolProvider.BaseNavigationItem {
  private final M68kLabel label;

  M68kGotoLabelNavigationItem(M68kLabel label) {
    super(label, Objects.requireNonNull(label.getName()), label.getIcon(0));
    this.label = label;
  }

  @Override
  public ItemPresentation getPresentation() {
    return new PresentationData(getName(), label.getValue(), getIcon(0), null);
  }

  @Override
  public @Nullable TextRange getTextRange() {
    return label.getTextRange();
  }

}
