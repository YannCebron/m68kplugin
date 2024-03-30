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

import com.intellij.codeInsight.documentation.DocumentationComponent;
import com.intellij.ide.BrowserUtil;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsUtil;
import com.intellij.openapi.options.FontSize;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.*;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanelWithEmptyText;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.navigation.BackAction;
import com.intellij.ui.navigation.ForwardAction;
import com.intellij.ui.navigation.History;
import com.intellij.ui.navigation.Place;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.Function;
import com.intellij.util.ui.HTMLEditorKitBuilder;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import com.intellij.util.ui.components.BorderLayoutPanel;
import com.yanncebron.m68kplugin.M68kApiBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @param <T> must implement {@link #equals(Object)} to keep current selection upon list model update
 */
public abstract class M68kBrowserPaneBase<T> extends SimpleToolWindowPanel implements Place.Navigator, Disposable {

  private final JBSplitter splitter;

  private final JBList<T> list = new JBList<>();
  private JEditorPane docEditorPane;

  @NonNls
  private static final String SELECTED_ITEM = "M68kBrowserPaneBase.selected.item";

  private final History history = new History(this);

  private final Class<T> clazz;

  protected M68kBrowserPaneBase(Class<T> valueClazz) {
    super(true, true);

    clazz = valueClazz;

    splitter = new OnePixelSplitter(false, getClass().getSimpleName() + ".splitter.proportion", getInitialSplitProportion());
    splitter.setDividerPositionStrategy(Splitter.DividerPositionStrategy.KEEP_FIRST_SIZE);
    splitter.setHonorComponentsPreferredSize(true);
    splitter.setLackOfSpaceStrategy(Splitter.LackOfSpaceStrategy.HONOR_THE_FIRST_MIN_SIZE);

    splitter.setFirstComponent(createList());
    setNoSelection();

    setContent(splitter);

    BorderLayoutPanel toolbarPanel = JBUI.Panels.simplePanel();
    Component toolbarComponent = getToolbarComponent();
    if (toolbarComponent != null) {
      toolbarPanel.addToLeft(toolbarComponent);
    }
    toolbarPanel.addToRight(getHistoryToolbarComponent());
    setToolbar(toolbarPanel);

    initList();
  }

  @Nullable
  private Component getToolbarComponent() {
    final ActionGroup toolbarActionGroup = getToolbarActionGroup();
    if (toolbarActionGroup == null) return null;

    ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(getClass().getSimpleName(), toolbarActionGroup, true);
    toolbar.setTargetComponent(this);
    return toolbar.getComponent();
  }

  private Component getHistoryToolbarComponent() {
    JComponent component = Objects.requireNonNull(getComponent());
    DefaultActionGroup historyActionGroup = new DefaultActionGroup(
      new BackAction(component, this),
      new ForwardAction(component, this)
    );
    ActionToolbar historyToolbar = ActionManager.getInstance().createActionToolbar(getClass().getSimpleName() + ".History", historyActionGroup, true);
    historyToolbar.setTargetComponent(this);
    return historyToolbar.getComponent();
  }

  protected float getInitialSplitProportion() {
    return 0.2f;
  }

  protected JComponent getFocusComponent() {
    return list;
  }

  public boolean isAvailable(@SuppressWarnings("unused") Project project) {
    return true;
  }

  /**
   * @see #createToggleAction
   */
  @Nullable
  protected abstract ActionGroup getToolbarActionGroup();

  /**
   * Populate list items and call {@link #setListItems(List)}.
   */
  protected abstract void initList();

  protected abstract Function<? super T, String> getListItemNamer();

  protected String getListItemNameForLink(String link) {
    return StringUtil.toUpperCase(StringUtil.substringBefore(link, ".md"));
  }

  @NotNull
  protected abstract String getDocFor(@NotNull T selectedValue);

  protected ColoredListCellRenderer<T> getListCellRenderer() {
    return new ColoredListCellRenderer<>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends T> list, T value, int index, boolean selected, boolean hasFocus) {
        append(getListItemNamer().fun(value));

        SpeedSearchUtil.applySpeedSearchHighlighting(list, this, true, selected);
      }
    };
  }

  protected void setListItems(List<T> items) {
    T selectedValue = list.getSelectedValue();

    list.setModel(new CollectionListModel<>(items));

    list.setSelectedValue(selectedValue, true);
  }

  protected void updateDoc() {
    updateDoc(list.getSelectedValue());
  }

  private void updateDoc(@Nullable T selectedValue) {
    if (selectedValue == null) {
      setNoSelection();
      return;
    }

    history.pushQueryPlace();

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
      docEditorPane.setBorder(JBUI.Borders.empty(UIUtil.getListCellHPadding(), UIUtil.getListCellHPadding(), UIUtil.getScrollBarWidth(), UIUtil.getScrollBarWidth()));
      docEditorPane.setEditorKit(new HTMLEditorKitBuilder().build());
      docEditorPane.addHyperlinkListener(new HyperlinkAdapter() {
        @Override
        protected void hyperlinkActivated(@NotNull HyperlinkEvent e) {
          URL url = e.getURL();
          if (url != null && BrowserUtil.isAbsoluteURL(url.toExternalForm())) {
            BrowserUtil.browse(url);
            return;
          }

          String elementName = getListItemNameForLink(e.getDescription());
          for (int i = 0; i < list.getItemsCount(); i++) {
            T element = list.getModel().getElementAt(i);
            if (StringUtil.equals(elementName, getListItemNamer().fun(element))) {
              list.setSelectedIndex(i);
              list.ensureIndexIsVisible(i);
              return;
            }
          }

          RelativePoint target = new RelativePoint((MouseEvent) e.getInputEvent());
          JBPopupFactory.getInstance().
            createHtmlTextBalloonBuilder(
              M68kApiBundle.message("documentation.error.cannot.show.item", elementName),
              MessageType.WARNING, null)
            .createBalloon()
            .show(target, Balloon.Position.above);
        }
      });
    }

    docEditorPane.setBackground(EditorColorsUtil.getGlobalOrDefaultColor(EditorColors.DOCUMENTATION_COLOR));
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

  protected final AnAction createToggleAction(String text, Icon icon,
                                              Ref<Boolean> settingField, String settingKey,
                                              @NotNull BiConsumer<AnActionEvent, Boolean> setSelectedConsumer,
                                              @Nullable Consumer<AnActionEvent> updateConsumer) {
    assert settingField != null;
    settingField.set(PropertiesComponent.getInstance().getBoolean(settingKey, true));

    return new DumbAwareToggleAction(text, null, icon) {

      @Override
      public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
      }

      @Override
      public void update(@NotNull AnActionEvent e) {
        super.update(e);

        if (updateConsumer != null) {
          updateConsumer.accept(e);
        }
      }

      @Override
      public boolean isSelected(@NotNull AnActionEvent e) {
        return settingField.get();
      }

      @Override
      public void setSelected(@NotNull AnActionEvent e, boolean state) {
        settingField.set(state);
        PropertiesComponent.getInstance().setValue(settingKey, settingField.get(), true);

        setSelectedConsumer.accept(e, state);
      }
    };
  }

  @Override
  public @Nullable Object getData(@NotNull @NonNls String dataId) {
    if (History.KEY.is(dataId)) {
      return history;
    }

    return super.getData(dataId);
  }

  @Override
  public final @Nullable ActionCallback navigateTo(@Nullable Place place, boolean requestFocus) {
    if (place == null) return ActionCallback.DONE;

    Object value = place.getPath(SELECTED_ITEM);
    if (value == null) return ActionCallback.REJECTED;

    list.setSelectedValue(value, true);

    // list might not contain value anymore due to filtering, force update doc manually
    if (!list.getSelectedValue().equals(value)) {
      updateDoc((clazz.cast(value)));
    }

    return ActionCallback.DONE;
  }

  @Override
  public final void queryPlace(@NotNull Place place) {
    place.putPath(SELECTED_ITEM, list.getSelectedValue());
  }

  @Override
  public void dispose() {
  }
}
