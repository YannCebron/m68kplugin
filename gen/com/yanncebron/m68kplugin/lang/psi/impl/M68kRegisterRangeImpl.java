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

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.yanncebron.m68kplugin.lang.psi.M68kTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.yanncebron.m68kplugin.lang.psi.*;
import java.util.EnumSet;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

public class M68kRegisterRangeImpl extends ASTWrapperPsiElement implements M68kRegisterRange {

  public M68kRegisterRangeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull M68kVisitor visitor) {
    visitor.visitRegisterRange(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof M68kVisitor) accept((M68kVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public M68kAdmRrd getFrom() {
    List<M68kAdmRrd> p1 = PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmRrd.class);
    return p1.get(0);
  }

  @Override
  @Nullable
  public M68kAdmRrd getTo() {
    List<M68kAdmRrd> p1 = PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmRrd.class);
    return p1.size() < 2 ? null : p1.get(1);
  }

  @Override
  public @NotNull EnumSet<M68kRegister> getRegisters() {
    return M68kPsiImplUtil.getRegisters(this);
  }

}
