/*
 * Copyright 2022 The Authors
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
package com.yanncebron.m68kplugin.lang.psi.directive.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.yanncebron.m68kplugin.lang.psi.M68kTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.impl.M68kPsiImplUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAbs;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAdi;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAix;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmApd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmApi;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmArd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAri;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmCcr;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmDrd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmImm;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmPcd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmPci;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmSr;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmUsp;

public class M68kMacroCallParameterImpl extends ASTWrapperPsiElement implements M68kMacroCallParameter {

  public M68kMacroCallParameterImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull M68kVisitor visitor) {
    visitor.visitMacroCallParameter(this);
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
  public M68kAdmApd getAdmApd() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmApd.class);
  }

  @Override
  @Nullable
  public M68kAdmApi getAdmApi() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmApi.class);
  }

  @Override
  @Nullable
  public M68kAdmArd getAdmArd() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmArd.class);
  }

  @Override
  @Nullable
  public M68kAdmAri getAdmAri() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmAri.class);
  }

  @Override
  @Nullable
  public M68kAdmCcr getAdmCcr() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmCcr.class);
  }

  @Override
  @Nullable
  public M68kAdmDrd getAdmDrd() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmDrd.class);
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

  @Override
  @Nullable
  public M68kAdmSr getAdmSr() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmSr.class);
  }

  @Override
  @Nullable
  public M68kAdmUsp getAdmUsp() {
    return PsiTreeUtil.getChildOfType(this, M68kAdmUsp.class);
  }

}
