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

package com.yanncebron.m68kplugin.editor.folding;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(name = "M68kFoldingSettings", storages = @Storage("editor.xml"))
@Service
public final class M68kFoldingSettings implements PersistentStateComponent<M68kFoldingSettings> {

  private boolean isCollapseZeroTerminatedStringLiteral = true;

  public boolean isCollapseZeroTerminatedStringLiteral() {
    return isCollapseZeroTerminatedStringLiteral;
  }

  public void setCollapseZeroTerminatedStringLiteral(boolean collapseZeroTerminatedStringLiteral) {
    isCollapseZeroTerminatedStringLiteral = collapseZeroTerminatedStringLiteral;
  }

  public static M68kFoldingSettings getInstance() {
    return ApplicationManager.getApplication().getService(M68kFoldingSettings.class);
  }

  @Override
  public M68kFoldingSettings getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull M68kFoldingSettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }
}
