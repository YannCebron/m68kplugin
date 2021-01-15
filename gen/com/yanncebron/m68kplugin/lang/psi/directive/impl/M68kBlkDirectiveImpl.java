/*
 * Copyright 2021 The Authors
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
import com.yanncebron.m68kplugin.lang.psi.impl.M68kDataSizedImpl;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.impl.M68kPsiImplUtil;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

public class M68kBlkDirectiveImpl extends M68kDataSizedImpl implements M68kBlkDirective {

  public M68kBlkDirectiveImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull M68kVisitor visitor) {
    visitor.visitBlkDirective(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof M68kVisitor) accept((M68kVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public M68kExpression getNumber() {
    List<M68kExpression> p1 = PsiTreeUtil.getChildrenOfTypeAsList(this, M68kExpression.class);
    return p1.size() < 1 ? null : p1.get(0);
  }

  @Override
  @Nullable
  public M68kExpression getValue() {
    List<M68kExpression> p1 = PsiTreeUtil.getChildrenOfTypeAsList(this, M68kExpression.class);
    return p1.size() < 2 ? null : p1.get(1);
  }

}
