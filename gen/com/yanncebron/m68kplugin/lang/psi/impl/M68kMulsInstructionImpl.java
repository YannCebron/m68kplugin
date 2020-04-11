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

public class M68kMulsInstructionImpl extends ASTWrapperPsiElement implements M68kMulsInstruction {

  public M68kMulsInstructionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull M68kVisitor visitor) {
    visitor.visitMulsInstruction(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof M68kVisitor) accept((M68kVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public M68kAdmAbs getAdmAbs() {
    return findChildByClass(M68kAdmAbs.class);
  }

  @Override
  @Nullable
  public M68kAdmAdi getAdmAdi() {
    return findChildByClass(M68kAdmAdi.class);
  }

  @Override
  @Nullable
  public M68kAdmAix getAdmAix() {
    return findChildByClass(M68kAdmAix.class);
  }

  @Override
  @Nullable
  public M68kAdmApd getAdmApd() {
    return findChildByClass(M68kAdmApd.class);
  }

  @Override
  @Nullable
  public M68kAdmApi getAdmApi() {
    return findChildByClass(M68kAdmApi.class);
  }

  @Override
  @Nullable
  public M68kAdmArd getAdmArd() {
    return findChildByClass(M68kAdmArd.class);
  }

  @Override
  @Nullable
  public M68kAdmAri getAdmAri() {
    return findChildByClass(M68kAdmAri.class);
  }

  @Override
  @NotNull
  public List<M68kAdmDrd> getAdmDrdList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmDrd.class);
  }

  @Override
  @Nullable
  public M68kAdmImm getAdmImm() {
    return findChildByClass(M68kAdmImm.class);
  }

  @Override
  @Nullable
  public M68kAdmPcd getAdmPcd() {
    return findChildByClass(M68kAdmPcd.class);
  }

  @Override
  @Nullable
  public M68kAdmPci getAdmPci() {
    return findChildByClass(M68kAdmPci.class);
  }

}
