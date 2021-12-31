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

import com.intellij.codeInsight.documentation.DocumentationComponent;
import com.intellij.ide.IdeBundle;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.editor.colors.EditorColorsUtil;
import com.intellij.openapi.options.FontSize;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.ui.*;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanelWithEmptyText;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.Function;
import com.intellij.util.ui.JBHtmlEditorKit;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @param <T> must implement {@link #equals(Object)} to keep current selection upon list model update
 */
public abstract class M68kBrowserPaneBase<T> extends SimpleToolWindowPanel {

  private final JBSplitter splitter;

  private final JBList<T> list = new JBList<>();
  private JEditorPane docEditorPane;

  protected M68kBrowserPaneBase() {
    super(true, true);

    splitter = new OnePixelSplitter(false, getClass().getSimpleName() + ".splitter.proportion", getInitialSplitProportion());
    splitter.setDividerPositionStrategy(Splitter.DividerPositionStrategy.KEEP_FIRST_SIZE);
    splitter.setHonorComponentsPreferredSize(true);
    splitter.setLackOfSpaceStrategy(Splitter.LackOfSpaceStrategy.HONOR_THE_FIRST_MIN_SIZE);

    splitter.setFirstComponent(createList());
    setNoSelection();

    setContent(splitter);

    final ActionGroup toolbarActionGroup = getToolbarActionGroup();
    if (toolbarActionGroup != null) {
      final ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(getClass().getSimpleName(), toolbarActionGroup, true);
      toolbar.setTargetComponent(this);

      setToolbar(toolbar.getComponent());
    }

    initList();
  }

  protected float getInitialSplitProportion() {
    return 0.2f;
  }

  protected JComponent getFocusComponent() {
    return list;
  }

  public boolean isAvailable(Project project) {
    return true;
  }

  @Nullable
  protected abstract ActionGroup getToolbarActionGroup();

  /**
   * Populate list items and call {@link #setListModel(ListModel)}.
   */
  protected abstract void initList();

  protected abstract Function<? super T, String> getListItemNamer();

  @NotNull
  protected abstract String getDocFor(@NotNull T selectedValue);

  protected ColoredListCellRenderer<T> getListCellRenderer() {
    return new ColoredListCellRenderer<T>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends T> list, T value, int index, boolean selected, boolean hasFocus) {
        append(getListItemNamer().fun(value));

        SpeedSearchUtil.applySpeedSearchHighlighting(list, this, true, selected);
      }
    };
  }

  protected void setListModel(ListModel<T> model) {
    T selectedValue = list.getSelectedValue();

    list.setModel(model);

    list.setSelectedValue(selectedValue, true);
  }

  protected void updateDoc() {
    final T selectedValue = list.getSelectedValue();
    if (selectedValue == null) {
      setNoSelection();
      return;
    }

    final String docText = getDocFor(selectedValue);
    updateDocText(docText);
  }

  private void setNoSelection() {
    setDocComponent(new JBPanelWithEmptyText().withEmptyText(IdeBundle.message("empty.text.nothing.selected")));
  }

  private void updateDocText(String content) {
    if (docEditorPane == null) {
      docEditorPane = new JEditorPane();
      docEditorPane.setEditable(false);
      docEditorPane.setBorder(JBUI.Borders.empty(0, UIUtil.getScrollBarWidth(), UIUtil.getScrollBarWidth(), UIUtil.getScrollBarWidth()));
      docEditorPane.setEditorKit(new JBHtmlEditorKit(false));
    }

    docEditorPane.setBackground(EditorColorsUtil.getGlobalOrDefaultColor(DocumentationComponent.COLOR_KEY));
    final FontSize fontSize = DocumentationComponent.getQuickDocFontSize();
    docEditorPane.setFont(UIUtil.getFontWithFallback(docEditorPane.getFont().getFontName(), Font.PLAIN, JBUIScale.scale(fontSize.getSize())));

    docEditorPane.setText(content);
    docEditorPane.setCaretPosition(0);
    setDocComponent(docEditorPane);
  }

  private void setDocComponent(JComponent component) {
    splitter.setSecondComponent(new JBScrollPane(component));
  }

  private JComponent createList() {
    list.setBorder(JBUI.Borders.empty());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setFixedCellHeight(JBUIScale.scale(UIUtil.LIST_FIXED_CELL_HEIGHT));
    list.setFixedCellWidth(list.getWidth());

    list.setCellRenderer(getListCellRenderer());
    list.addListSelectionListener(e -> {
      if (e.getValueIsAdjusting()) return;

      updateDoc();
    });
    ScrollingUtil.installActions(list);
    new ListSpeedSearch<>(list, getListItemNamer());

    JBScrollPane scrollPane = new JBScrollPane(list);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    return scrollPane;
  }

}
