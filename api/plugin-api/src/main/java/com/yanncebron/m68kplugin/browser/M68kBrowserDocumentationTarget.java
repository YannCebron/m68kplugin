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

import com.intellij.model.Pointer;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.presentation.TargetPresentation;
import org.jetbrains.annotations.NotNull;

/**
 * @see M68kBrowserItemDocumentationLinkHandler
 */
@SuppressWarnings("UnstableApiUsage")
abstract class M68kBrowserDocumentationTarget implements DocumentationTarget {

  @Override
  public final @NotNull Pointer<? extends DocumentationTarget> createPointer() {
    return Pointer.hardPointer(this);
  }

  @Override
  public final @NotNull TargetPresentation computePresentation() {
    return TargetPresentation.builder("").presentation();
  }

  abstract void selectItem(String item);
}
