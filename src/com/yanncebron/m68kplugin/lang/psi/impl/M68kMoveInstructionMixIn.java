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

import com.intellij.lang.ASTNode;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmSr;
import com.yanncebron.m68kplugin.lang.psi.M68kCpu;
import com.yanncebron.m68kplugin.lang.psi.M68kMoveInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kPrivilegedInstruction;
import org.jetbrains.annotations.NotNull;

abstract class M68kMoveInstructionMixIn extends M68kMoveInstructionBaseImpl implements M68kPrivilegedInstruction, M68kMoveInstruction {

  protected M68kMoveInstructionMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public boolean isPrivileged(@NotNull M68kCpu m68kCpu) {
    if (getAdmUsp() != null) return true;

    final M68kAdmSr admSr = getAdmSr();
    if (admSr == null) {
      return false;
    }

    if (m68kCpu == M68kCpu.M_68000 &&
      M68kPsiImplUtil.isSrc(this, admSr)) {
      return false;
    }

    return true;
  }
}
