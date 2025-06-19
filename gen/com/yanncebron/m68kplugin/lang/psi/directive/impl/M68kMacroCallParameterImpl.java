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
import com.yanncebron.m68kplugin.lang.psi.M68kAdmDfc;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmDrd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmImm;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmPcd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmPci;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmRegisterList;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmSfc;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmSr;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmUsp;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmVbr;

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
  public List<M68kAdmCcr> getAdmCcrList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmCcr.class);
  }

  @Override
  @NotNull
  public List<M68kAdmDfc> getAdmDfcList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmDfc.class);
  }

  @Override
  @NotNull
  public List<M68kAdmDrd> getAdmDrdList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmDrd.class);
  }

  @Override
  @NotNull
  public List<M68kAdmImm> getAdmImmList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmImm.class);
  }

  @Override
  @NotNull
  public List<M68kAdmPcd> getAdmPcdList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmPcd.class);
  }

  @Override
  @NotNull
  public List<M68kAdmPci> getAdmPciList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmPci.class);
  }

  @Override
  @NotNull
  public List<M68kAdmRegisterList> getAdmRegisterListList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmRegisterList.class);
  }

  @Override
  @NotNull
  public List<M68kAdmSfc> getAdmSfcList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmSfc.class);
  }

  @Override
  @NotNull
  public List<M68kAdmSr> getAdmSrList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmSr.class);
  }

  @Override
  @NotNull
  public List<M68kAdmUsp> getAdmUspList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmUsp.class);
  }

  @Override
  @NotNull
  public List<M68kAdmVbr> getAdmVbrList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, M68kAdmVbr.class);
  }

}
