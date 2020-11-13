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

public class M68kAddInstructionImpl extends ASTWrapperPsiElement implements M68kAddInstruction {

  public M68kAddInstructionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull M68kVisitor visitor) {
    visitor.visitAddInstruction(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof M68kVisitor) accept((M68kVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<M68kAdmAbs> getAdmAbsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmAbs.class);
  }

  @Override
  @NotNull
  public List<M68kAdmAdi> getAdmAdiList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmAdi.class);
  }

  @Override
  @NotNull
  public List<M68kAdmAix> getAdmAixList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmAix.class);
  }

  @Override
  @NotNull
  public List<M68kAdmApd> getAdmApdList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmApd.class);
  }

  @Override
  @NotNull
  public List<M68kAdmApi> getAdmApiList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmApi.class);
  }

  @Override
  @NotNull
  public List<M68kAdmArd> getAdmArdList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmArd.class);
  }

  @Override
  @NotNull
  public List<M68kAdmAri> getAdmAriList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmAri.class);
  }

  @Override
  @NotNull
  public List<M68kAdmDrd> getAdmDrdList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmDrd.class);
  }

  @Override
  @Nullable
  public M68kAdmImm getAdmImm() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmImm.class);
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
