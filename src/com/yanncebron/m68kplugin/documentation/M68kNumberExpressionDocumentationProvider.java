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

package com.yanncebron.m68kplugin.documentation;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kNumberExpressionDocumentationProvider extends AbstractDocumentationProvider {

  @Override
  public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
    if (!(element instanceof M68kNumberExpression numberExpression)) return null;

    Long originalValue = ObjectUtils.tryCast(numberExpression.getValue(), Long.class);
    assert originalValue != null : numberExpression.getText();

    return "<code>" +
      doGetValueText(element, Long.toString(originalValue)) + "<br>" +
      doGetValueText(element, "$" + Long.toHexString(originalValue)) + "<br>" +
      doGetValueText(element, "@" + Long.toOctalString(originalValue)) + "<br>" +
      doGetValueText(element, "%" + Long.toBinaryString(originalValue)) + "<br>" + "</code>";
  }

  @Override
  public @Nullable PsiElement getCustomDocumentationElement(@NotNull Editor editor, @NotNull PsiFile file, @Nullable PsiElement contextElement, int targetOffset) {
    if (contextElement != null &&
      M68kTokenGroups.NUMBERS.contains(contextElement.getNode().getElementType())) {
      return PsiTreeUtil.getParentOfType(contextElement, M68kNumberExpression.class, false);
    }
    return null;
  }

  @NotNull
  private String doGetValueText(PsiElement context, String numberValueText) {
    return M68kLabelDocumentationProvider.doGetValueText(context, " dc ", numberValueText);
  }

}
