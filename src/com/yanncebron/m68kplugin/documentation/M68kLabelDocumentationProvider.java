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

package com.yanncebron.m68kplugin.documentation;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.findUsages.LanguageFindUsages;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class M68kLabelDocumentationProvider extends AbstractDocumentationProvider {

  @Override
  public @Nullable String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
    if (!(element instanceof M68kLabelBase)) return null;

    M68kLabelBase labelBase = (M68kLabelBase) element;

    final String typeName = LanguageFindUsages.getType(labelBase);
    final String labelName = labelBase.getName();
    assert labelName != null;

    final String name = StringUtil.wrapWithDoubleQuote(labelName);
    final String location = " [" + SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile()) + "]";

    if (labelBase.getLabelKind().hasValue()) {
      String valueText = getValueText(labelBase);
      return typeName + " " + name + location + "<br>" + valueText;
    }

    return typeName + " " + name + location;
  }

  @NotNull
  private String getValueText(M68kLabelBase labelBase) {
    final String value = labelBase.getValue();
    if (value == null) return M68kBundle.message("term.undefined.value");

    if (labelBase.getLabelKind() == M68kLabelBase.LabelKind.EQUR) {
      return doGetValueText(labelBase, " clr ", value);
    }
    return doGetValueText(labelBase, " dc ", value);
  }

  @NotNull
  private String doGetValueText(M68kLabelBase labelBase, String prefix, String value) {
    final String text = prefix + value;
    final M68kFile dummyFile = M68kElementFactory.createFile(labelBase.getProject(), text);
    EditorColorsScheme scheme = EditorColorsManager.getInstance().getGlobalScheme();
    CharSequence htmlValue = HtmlSyntaxInfoUtil.getHtmlContent(dummyFile, text, null, scheme, 4, text.length());
    return htmlValue != null ? htmlValue.toString() : value;
  }

}
