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

package com.yanncebron.m68kplugin.amiga.hardware;

import com.intellij.model.Pointer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.platform.backend.documentation.*;
import com.intellij.platform.backend.presentation.TargetPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;
import com.yanncebron.m68kplugin.settings.ide.M68kProjectEnvironment;
import com.yanncebron.m68kplugin.settings.ide.M68kTargetPlatform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
final class M68kAmigaHardwareRegisterDocumentationTargetProvider implements DocumentationTargetProvider {

  @Override
  public @NotNull List<? extends @NotNull DocumentationTarget> documentationTargets(@NotNull PsiFile file, int offset) {
    if (!(file instanceof M68kFile)) return Collections.emptyList();

    Project project = file.getProject();
    if (M68kProjectEnvironment.getInstance(project).getTargetPlatform() != M68kTargetPlatform.AMIGA)
      return Collections.emptyList();

    PsiElement psiElement = file.findElementAt(offset);
    M68kNumberExpression numberExpression = PsiTreeUtil.getParentOfType(psiElement, M68kNumberExpression.class);
    if (numberExpression == null) return Collections.emptyList();

    M68kAmigaHardwareRegister hardwareRegister = M68kAmigaHardRegisterPsiLocator.findByFullAddress(numberExpression);
    if (hardwareRegister == null) {
      hardwareRegister = M68kAmigaHardRegisterPsiLocator.findByCopperList(numberExpression);
    }
    if (hardwareRegister == null) return Collections.emptyList();

    return List.of(new M68kAmigaHardwareRegisterDocumentationTarget(hardwareRegister));
  }

  private static class M68kAmigaHardwareRegisterDocumentationTarget implements DocumentationTarget {

    private final M68kAmigaHardwareRegister hardwareRegister;

    public M68kAmigaHardwareRegisterDocumentationTarget(M68kAmigaHardwareRegister hardwareRegister) {
      this.hardwareRegister = hardwareRegister;
    }

    @Override
    public @NotNull Pointer<? extends DocumentationTarget> createPointer() {
      return Pointer.hardPointer(this);
    }

    @Override
    public @NotNull TargetPresentation computePresentation() {
      return TargetPresentation.builder(hardwareRegister.getName()).icon(hardwareRegister.getIcon()).presentation();
    }

    @Override
    public DocumentationResult computeDocumentation() {
      String html = new M68kAmigaHardwareRegisterDocsCreator(hardwareRegister, List.of(M68kAmigaHardwareRegister.values())).generateDoc(true, false);
      return DocumentationResult.documentation(html);
    }
  }

  /**
   * Resolve {@code registerName.md} cross-links in MD reference docs.
   */
  static final class M68kAmigaHardwareRegisterSymbolLinkHandler implements DocumentationLinkHandler {

    @Override
    public @Nullable LinkResolveResult resolveLink(@NotNull DocumentationTarget target, @NotNull String url) {
      if (target instanceof M68kAmigaHardwareRegisterDocumentationTarget) {
        String registerName = StringUtil.substringBefore(url, ".md");
        if (registerName == null) {
          registerName = url;
        }
        M68kAmigaHardwareRegister targetRegister = M68kAmigaHardwareRegister.valueOf(StringUtil.toUpperCase(registerName));
        return LinkResolveResult.resolvedTarget((new M68kAmigaHardwareRegisterDocumentationTarget(targetRegister)));
      }
      return null;
    }
  }

}
