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

import com.intellij.ide.util.ModuleRendererFactory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Goto symbol: show containing file on right instead of "Module".
 */
public class M68kGotoLabelModuleRendererFactory extends ModuleRendererFactory {

  @Override
  protected boolean handles(Object element) {
    return element instanceof M68kGotoLabelNavigationItem;
  }

  @Override
  public boolean rendersLocationString() {
    return true;
  }

  @Override
  public DefaultListCellRenderer getModuleRenderer() {
    return new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        final Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        M68kGotoLabelNavigationItem navigationItem = (M68kGotoLabelNavigationItem) value;

        final PsiElement targetElement = navigationItem.getNavigationElement();
        setText(SymbolPresentationUtil.getFilePathPresentation(targetElement.getContainingFile()));
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, UIUtil.getListCellHPadding()));
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setBackground(isSelected ? UIUtil.getListSelectionBackground(true) : UIUtil.getListBackground());
        setForeground(isSelected ? UIUtil.getListSelectionForeground(true) : UIUtil.getInactiveTextColor());

        return component;
      }
    };
  }
}
