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

package com.yanncebron.m68kplugin.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.model.Pointer;
import com.intellij.model.SingleTargetReference;
import com.intellij.model.Symbol;
import com.intellij.model.psi.PsiSymbolReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.platform.backend.documentation.DocumentationContent;
import com.intellij.platform.backend.documentation.DocumentationResult;
import com.intellij.platform.backend.documentation.DocumentationSymbol;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.presentation.TargetPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.ConcurrentFactoryMap;
import com.yanncebron.m68kplugin.documentation.M68kRegisterDocsGenerator;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmWithRegister;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
abstract class M68kAdmWithRegisterMixIn extends ASTWrapperPsiElement implements M68kAdmWithRegister {

  protected M68kAdmWithRegisterMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public @NotNull Collection<? extends @NotNull PsiSymbolReference> getOwnReferences() {
    return Collections.singleton(new RegisterSymbolReference(this));
  }

  private static class RegisterSymbolReference extends SingleTargetReference implements PsiSymbolReference {

    private static final Map<M68kRegister, RegisterSymbol> REGISTER_SYMBOL_MAP = ConcurrentFactoryMap.createMap(RegisterSymbol::new);

    private final M68kAdmWithRegister m68kAdmWithRegister;

    private RegisterSymbolReference(M68kAdmWithRegister m68kAdmWithRegister) {
      this.m68kAdmWithRegister = m68kAdmWithRegister;
    }

    @Override
    protected @Nullable Symbol resolveSingleTarget() {
      return REGISTER_SYMBOL_MAP.get(m68kAdmWithRegister.getRegister());
    }

    @Override
    public @NotNull PsiElement getElement() {
      return m68kAdmWithRegister;
    }

    @Override
    public @NotNull TextRange getRangeInElement() {
      return m68kAdmWithRegister.getRegisterElement().getTextRangeInParent();
    }

  }

  @SuppressWarnings({"UnstableApiUsage", "ClassCanBeRecord"})
  private static class RegisterSymbol implements DocumentationSymbol {

    private final M68kRegister register;

    private RegisterSymbol(M68kRegister register) {
      this.register = register;
    }

    @Override
    public @NotNull Pointer<? extends DocumentationSymbol> createPointer() {
      return Pointer.hardPointer(this);
    }

    @Override
    public @NotNull DocumentationTarget getDocumentationTarget() {
      return new M68RegisterSymbolDocumentationTarget(register);
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      RegisterSymbol that = (RegisterSymbol) o;
      return register == that.register;
    }

    @Override
    public int hashCode() {
      return register.hashCode();
    }
  }

  @SuppressWarnings("ClassCanBeRecord")
  private static class M68RegisterSymbolDocumentationTarget implements DocumentationTarget {

    private final M68kRegister m68kRegister;

    private M68RegisterSymbolDocumentationTarget(M68kRegister register) {
      this.m68kRegister = register;
    }

    @Override
    public @NotNull Pointer<? extends DocumentationTarget> createPointer() {
      return Pointer.hardPointer(this);
    }

    @Override
    public @Nullable DocumentationResult computeDocumentation() {
      return DocumentationResult.documentation(DocumentationContent.content(new M68kRegisterDocsGenerator(m68kRegister).getDocumentation()));
    }

    @Override
    public @NotNull TargetPresentation computePresentation() {
      return TargetPresentation.builder(m68kRegister.name()).presentation();
    }
  }
}
