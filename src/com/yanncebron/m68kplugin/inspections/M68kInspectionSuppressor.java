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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.codeInspection.InspectionSuppressor;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.SuppressQuickFix;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Allows suppressing any inspection via special comment marker text.
 * <p>
 * Suppression variants:
 * <ul>
 *   <li>For element: comment in preceding line</li>
 *   <li>For file: any comment before first instruction/directive in file</li>
 * </ul>
 * </p>
 */
final class M68kInspectionSuppressor implements InspectionSuppressor {

  @NonNls
  private static final String MARKER = " @@@suppress_inspection@@@";

  @Override
  public boolean isSuppressedFor(@NotNull PsiElement element, @NotNull String toolId) {
    if (!(element instanceof M68kPsiElement)) return false;

    return isSuppressedForElement(element, toolId) ||
      isSuppressedForFile(element.getContainingFile(), toolId);
  }

  private static boolean isSuppressedForFile(PsiFile containingFile, String toolId) {
    for (PsiElement child = containingFile.getFirstChild(); child != null; child = child.getNextSibling()) {
      if (child instanceof PsiComment && hasSuppressionComment(child, toolId)) return true;

      if (PsiTreeUtil.instanceOf(child, M68kPsiElement.class)) return false;
    }
    return false;
  }

  @Override
  public SuppressQuickFix @NotNull [] getSuppressActions(@Nullable PsiElement element, @NotNull String toolId) {
    return new SuppressQuickFix[]{
      new ForElementSuppressQuickFix(toolId),
      new ForFileSuppressQuickFix(toolId)
    };
  }

  private static boolean isSuppressedForElement(PsiElement element, String toolId) {
    final M68kPsiElement containing = M68kPsiTreeUtil.getContainingInstructionOrDirective(element);
    if (containing == null) return false;

    final PsiElement firstNonWs = PsiTreeUtil.skipWhitespacesBackward(containing);
    PsiElement prevSibling = firstNonWs != null ? firstNonWs.getPrevSibling() : containing;
    return hasSuppressionComment(prevSibling, toolId);
  }

  private static boolean hasSuppressionComment(PsiElement psiElement, String toolId) {
    if (!(psiElement instanceof PsiComment)) return false;
    String commentText = psiElement.getText();
    return commentText.contains(MARKER) && commentText.contains(toolId);
  }


  private static class ForElementSuppressQuickFix implements SuppressQuickFix {

    private final String toolId;

    private ForElementSuppressQuickFix(String toolId) {
      this.toolId = toolId;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, @NotNull PsiElement context) {
      return context.isValid() && M68kPsiTreeUtil.getContainingInstructionOrDirective(context) != null;
    }

    @Override
    public boolean isSuppressAll() {
      return false;
    }

    @Override
    public @IntentionFamilyName @NotNull String getFamilyName() {
      return M68kBundle.message("inspection.suppress.for.element");
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
      final M68kPsiElement containingElement = M68kPsiTreeUtil.getContainingInstructionOrDirective(descriptor.getStartElement());
      assert containingElement != null;

      @NonNls final Document doc = PsiDocumentManager.getInstance(project).getDocument(containingElement.getContainingFile());
      assert doc != null;
      final int line = doc.getLineNumber(containingElement.getTextOffset());
      final int lineStart = doc.getLineStartOffset(line);

      doc.insertString(lineStart, "*" + MARKER + " " + toolId + "\n");
    }
  }


  private static class ForFileSuppressQuickFix implements SuppressQuickFix {

    private final String toolId;

    private ForFileSuppressQuickFix(String toolId) {
      this.toolId = toolId;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, @NotNull PsiElement context) {
      return true;
    }

    @Override
    public boolean isSuppressAll() {
      return false;
    }

    @Override
    public @IntentionFamilyName @NotNull String getFamilyName() {
      return M68kBundle.message("inspection.suppress.for.file");
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
      @NonNls final Document doc = PsiDocumentManager.getInstance(project).getDocument(descriptor.getStartElement().getContainingFile());
      assert doc != null;

      doc.insertString(0, "*" + MARKER + " " + toolId + "\n");
    }
  }


}
