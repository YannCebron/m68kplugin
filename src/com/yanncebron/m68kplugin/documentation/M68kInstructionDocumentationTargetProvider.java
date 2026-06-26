/*
 * Copyright 2026 The Authors
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

import com.intellij.model.Pointer;
import com.intellij.platform.backend.documentation.DocumentationResult;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.documentation.DocumentationTargetProvider;
import com.intellij.platform.backend.presentation.TargetPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.suggested.UtilsKt;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.M68kInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonic;
import com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistry;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
final class M68kInstructionDocumentationTargetProvider implements DocumentationTargetProvider {

  @Override
  public @NotNull List<? extends @NotNull DocumentationTarget> documentationTargets(@NotNull PsiFile file, int offset) {
    if (!(file instanceof M68kFile)) return Collections.emptyList();

    M68kInstruction m68kInstruction = findM68kInstruction(file.findElementAt(offset));
    if (m68kInstruction == null) return Collections.emptyList();

    return Collections.singletonList(new M68kInstructionDocumentationTarget(m68kInstruction));
  }

  private static @Nullable M68kInstruction findM68kInstruction(@Nullable PsiElement element) {
    if (element == null) return null;
    if (element instanceof M68kInstruction m68kInstruction) return m68kInstruction;

    IElementType elementType = element.getNode().getElementType();
    if (M68kTokenGroups.INSTRUCTIONS.contains(elementType) ||
      M68kTokenGroups.DATA_SIZES.contains(elementType)) {
      return PsiTreeUtil.getParentOfType(element, M68kInstruction.class);
    }
    return null;
  }


  @SuppressWarnings("ClassCanBeRecord")
  private static class M68kInstructionDocumentationTarget implements DocumentationTarget {

    private final M68kInstruction m68kInstruction;

    private M68kInstructionDocumentationTarget(M68kInstruction m68kInstruction) {
      this.m68kInstruction = m68kInstruction;
    }

    @Override
    public @NotNull Pointer<? extends DocumentationTarget> createPointer() {
      return Pointer.delegatingPointer(UtilsKt.createSmartPointer(m68kInstruction), M68kInstructionDocumentationTarget::new);
    }

    @Override
    public @NotNull TargetPresentation computePresentation() {
      return TargetPresentation.builder(m68kInstruction.getText()).presentation();
    }

    @Override
    public @Nullable DocumentationResult computeDocumentation() {
      return DocumentationResult.documentation(generateDoc());
    }

    private @NotNull String generateDoc() {
      // find specific matching mnemonic (valid instruction)
      M68kMnemonic instructionMnemonic = M68kMnemonicRegistry.getInstance().find(m68kInstruction);
      if (instructionMnemonic != null) {
        return M68kInstructionDocsUtil.getMnemonicDoc(instructionMnemonic, true);
      }

      // invalid instruction (e.g., PSI error, missing operands): show docs for all mnemonics
      return M68kInstructionDocsUtil.getMnemonicDoc(m68kInstruction);
    }
  }

}