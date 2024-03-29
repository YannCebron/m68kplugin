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

package com.yanncebron.m68kplugin.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmWithRegister;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmWithRrd;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

abstract class M68kAdmWithRrdMixIn extends ASTWrapperPsiElement implements M68kAdmWithRrd {

  protected M68kAdmWithRrdMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public @NotNull M68kRegister getRegister() {
    final M68kAdmWithRegister admWithRegister = ObjectUtils.chooseNotNull(getAdmArd(), getAdmDrd());
    return Objects.requireNonNull(admWithRegister).getRegister();
  }
}
