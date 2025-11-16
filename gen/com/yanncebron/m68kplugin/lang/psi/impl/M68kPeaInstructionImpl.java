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
import com.yanncebron.m68kplugin.lang.psi.*;

public class M68kPeaInstructionImpl extends M68kDataSizedImpl implements M68kPeaInstruction {

  public M68kPeaInstructionImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull M68kVisitor visitor) {
    visitor.visitPeaInstruction(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof M68kVisitor) accept((M68kVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public M68kAdmAbs getAdmAbs() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmAbs.class);
  }

  @Override
  @Nullable
  public M68kAdmAdi getAdmAdi() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmAdi.class);
  }

  @Override
  @Nullable
  public M68kAdmAix getAdmAix() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmAix.class);
  }

  @Override
  @Nullable
  public M68kAdmAri getAdmAri() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmAri.class);
  }

  @Override
  @Nullable
  public M68kAdmPcd getAdmPcd() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmPcd.class);
  }

  @Override
  @Nullable
  public M68kAdmPci getAdmPci() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmPci.class);
  }

}
