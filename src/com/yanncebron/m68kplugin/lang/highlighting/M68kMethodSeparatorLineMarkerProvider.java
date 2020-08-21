/*
 * Copyright 2020 The Authors
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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzerSettings;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.codeInsight.daemon.impl.LineMarkersPass;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.markup.SeparatorPlacement;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kEndmDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kSectionDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kMethodSeparatorLineMarkerProvider implements LineMarkerProvider {

  @Nullable
  @Override
  public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
    if (!DaemonCodeAnalyzerSettings.getInstance().SHOW_METHOD_SEPARATORS) return null;

    if (element instanceof M68kMacroDirective) {
      final PsiElement labelFirstChild = ((M68kMacroDirective) element).getLabel().getFirstChild();
      return createSeparator(labelFirstChild);
    }

    if (element instanceof M68kEndmDirective) {
      return createSeparatorBottom(element.getFirstChild());
    }

    if (element instanceof M68kSectionDirective) {
      return createSeparator(element.getFirstChild());
    }

    return null;
  }

  @NotNull
  private LineMarkerInfo<?> createSeparator(PsiElement psiElement) {
    return LineMarkersPass.createMethodSeparatorLineMarker(psiElement, EditorColorsManager.getInstance());
  }

  @NotNull
  private LineMarkerInfo<?> createSeparatorBottom(PsiElement psiElement) {
    final LineMarkerInfo<?> separator = createSeparator(psiElement);
    separator.separatorPlacement = SeparatorPlacement.BOTTOM;
    return separator;
  }
}
