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

import com.intellij.ide.IdeBundle;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.lang.documentation.ide.DocumentationUtil;
import com.intellij.lang.documentation.ide.ui.DocumentationComponent;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbAwareToggleAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.Splitter;
import com.intellij.openapi.util.ActionCallback;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.platform.backend.documentation.DocumentationResult;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.ui.*;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanelWithEmptyText;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.navigation.BackAction;
import com.intellij.ui.navigation.ForwardAction;
import com.intellij.ui.navigation.History;
import com.intellij.ui.navigation.Place;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.containers.Convertor;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.components.BorderLayoutPanel;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @param <T> must implement {@link #equals(Object)} to keep current selection upon list model update
 */
@SuppressWarnings("UnstableApiUsage")
public abstract class M68kBrowserPaneBase<T> extends SimpleToolWindowPanel implements Place.Navigator, Disposable {

  /**
   * Link to another item in the current pane.
   */
  @NonNls
  public static final String M68K_BROWSER_ITEM_LINK_PREFIX = "m68kBrowser://";

  public static final Function<String, String> M68K_BROWSER_LINK_FUNCTION = link -> M68K_BROWSER_ITEM_LINK_PREFIX + StringUtil.substringBefore(link, ".md");

  private JBSplitter splitter;

  private final JBList<T> list = new JBList<>();
  private DocumentationComponent documentationComponent;

  @NonNls
  private static final String SELECTED_ITEM = "M68kBrowserPaneBase.selected.item";

  private final History history = new History(this);

  private final Class<T> clazz;
  private Project project;

  protected M68kBrowserPaneBase(Class<T> valueClazz) {
    super(true, true);
    clazz = valueClazz;
  }

  void initUI(@NotNull Project project) {
    this.project = project;

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

    int selectedValueIdx = PropertiesComponent.getInstance().getInt(getSelectedIdxKey(), -1);
    list.setSelectedIndex(selectedValueIdx);
  }

  private @NotNull String getSelectedIdxKey() {
    return getClass().getSimpleName() + ".selected.index";
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

  protected abstract Convertor<? super T, String> getListItemNamer();

  protected String getListItemNameForLink(String link) {
    return StringUtil.toUpperCase(link);
  }

  @NotNull
  protected abstract String getDocFor(@NotNull T selectedValue);

  protected ColoredListCellRenderer<T> getListCellRenderer() {
    return new ColoredListCellRenderer<>() {
      @Override
      protected void customizeCellRenderer(@NotNull JList<? extends T> list, T value, int index, boolean selected, boolean hasFocus) {
        append(getListItemNamer().convert(value));

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

  @SuppressWarnings("OverrideOnly")
  private void updateDoc(@Nullable T selectedValue) {
    if (selectedValue == null) {
      setNoSelection();
      return;
    }

    history.pushQueryPlace();

    DocumentationTarget target = new M68kBrowserDocumentationTarget() {
      @Override
      public @NotNull DocumentationResult computeDocumentation() {
        return DocumentationResult.documentation(M68kBrowserPaneBase.this.getDocFor(selectedValue));
      }

      @Override
      void selectItem(String item) {
        M68kBrowserPaneBase.this.selectItem(item);
      }
    };

    if (documentationComponent == null) {
      documentationComponent = DocumentationUtil.documentationComponent(project, target.createPointer(), target.computePresentation(), this);
    }
    splitter.setSecondComponent(documentationComponent.getComponent());
    documentationComponent.resetBrowser(target.createPointer(), target.computePresentation());
  }

  private void setNoSelection() {
    splitter.setSecondComponent(new JBPanelWithEmptyText().withEmptyText(IdeBundle.message("empty.text.nothing.selected")));
  }

  private JComponent createList() {
    list.setBorder(JBUI.Borders.empty());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.setFixedCellHeight(JBUI.CurrentTheme.List.rowHeight());
    list.setFixedCellWidth(list.getWidth());

    list.setCellRenderer(getListCellRenderer());
    list.addListSelectionListener(e -> {
      if (e.getValueIsAdjusting()) return;

      PropertiesComponent.getInstance().setValue(getSelectedIdxKey(), list.getSelectedIndex(), -1);
      updateDoc();
    });
    ScrollingUtil.installActions(list);
    TreeUIHelper.getInstance().installListSpeedSearch(list, getListItemNamer());

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

  /**
   * Select the given item in the list, triggering documentation update.
   *
   * @param item Item name.
   */
  private void selectItem(String item) {
    ApplicationManager.getApplication().invokeLater(() -> {
      String elementName = getListItemNameForLink(item);
      for (int i = 0; i < list.getItemsCount(); i++) {
        T element = list.getModel().getElementAt(i);
        if (StringUtil.equals(elementName, getListItemNamer().convert(element))) {
          list.setSelectedIndex(i);
          list.ensureIndexIsVisible(i);
          return;
        }
      }
    });
  }
}
