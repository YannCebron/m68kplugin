/*
 * Copyright 2023 The Authors
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
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.lang.findUsages.LanguageFindUsages;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

final class M68kLabelDocumentationProvider extends AbstractDocumentationProvider {

  private static final String BR = "<br>";

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
      return typeName + " " + name + location + BR + "<code>" + valueText + "</code>";
    }

    return typeName + " " + name + location;
  }

  @Override
  public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
    if (!(element instanceof M68kLabelBase)) return null;
    M68kLabelBase labelBase = (M68kLabelBase) element;

    String commentText = getCommentText(element);
    String valueText = labelBase.getLabelKind().hasValue() ? BR + getValueText(labelBase) : "";

    return DocumentationMarkup.DEFINITION_START + labelBase.getName() + valueText + DocumentationMarkup.DEFINITION_END +
      DocumentationMarkup.CONTENT_START + commentText + DocumentationMarkup.CONTENT_END;
  }

  @NotNull
  private static String getCommentText(PsiElement element) {
    PsiElement startElement = ObjectUtils.chooseNotNull(M68kPsiTreeUtil.getContainingInstructionOrDirective(element), element);
    List<String> comments = new SmartList<>();

    boolean lastElementLineFeed = false;
    boolean lastDocIrrelevant = false;
    for (PsiElement child = startElement.getPrevSibling(); child != null; child = child.getPrevSibling()) {
      if (child.getNode().getElementType() == M68kTokenTypes.LINEFEED) {
        if (lastElementLineFeed && !comments.isEmpty()) break;
        lastElementLineFeed = true;
        continue;
      }
      lastElementLineFeed = false;

      if (!(child instanceof PsiComment)) break;
      if (child.getPrevSibling() instanceof M68kPsiElement) break; // do not include EOL comment

      final String commentText = child.getText().substring(1);
      if (isRelevantDocText(commentText)) {
        lastDocIrrelevant = false;
        comments.add(StringUtil.trim(StringUtil.trimLeading(StringUtil.trimTrailing(commentText, '*'), '*')));
        continue;
      }

      // keep single "delimiter" lines
      if (!comments.isEmpty() && !lastDocIrrelevant) {
        comments.add("");
      }
      lastDocIrrelevant = true;
    }

    // EOL comment
    if (comments.isEmpty()) {
      final PsiElement nextSibling = PsiTreeUtil.skipWhitespacesForward(startElement);
      if (nextSibling instanceof PsiComment) {
        final String commentText = nextSibling.getText();
        final int commentTextIdx = StringUtil.indexOfAny(commentText, ";*");
        if (commentTextIdx != -1) return commentText.substring(commentTextIdx + 1).trim();
      }
      return "";
    }

    if (ContainerUtil.getLastItem(comments).isEmpty()) {
      comments.remove(comments.size() - 1);
    }
    final List<String> orderedComments = ContainerUtil.reverse(comments);
    return StringUtil.join(orderedComments, BR);
  }

  /**
   * Checks whether given text has "relevant" doc and is not simple delimiter, e.g., {@code '**********'}.
   */
  private static boolean isRelevantDocText(String comment) {
    final String strip = StringUtil.strip(comment, ch -> ch != '*' && ch != '-' && !Character.isWhitespace(ch));
    return !strip.isEmpty();
  }

  @NotNull
  private static String getValueText(M68kLabelBase labelBase) {
    final String value = labelBase.getValue();
    if (value == null) return M68kBundle.message("term.undefined.value");

    if (labelBase.getLabelKind() == M68kLabelBase.LabelKind.EQUR) {
      return doGetValueText(labelBase, " clr ", value);
    }
    return doGetValueText(labelBase, " dc ", value);
  }

  @NotNull
  static String doGetValueText(PsiElement element, String prefix, String value) {
    final String text = prefix + value;
    final M68kFile dummyFile = M68kElementFactory.createFile(element.getProject(), text);
    EditorColorsScheme scheme = EditorColorsManager.getInstance().getGlobalScheme();
    CharSequence htmlValue = HtmlSyntaxInfoUtil.getHtmlContent(dummyFile, text, null, scheme, prefix.length(), text.length());
    return htmlValue != null ? htmlValue.toString() : value;
  }

}
