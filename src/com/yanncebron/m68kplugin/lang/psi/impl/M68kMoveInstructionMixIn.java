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

import com.intellij.lang.ASTNode;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmSr;
import com.yanncebron.m68kplugin.lang.psi.M68kMoveInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;

abstract class M68kMoveInstructionMixIn extends M68kMoveInstructionBaseImpl implements M68kMoveInstruction {

  protected M68kMoveInstructionMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public boolean isPrivileged() {
    if (getAdmUsp() != null) return true;

    final M68kAdmSr admSr = getAdmSr();
    if (admSr != null) {
      final ASTNode commaNode = getNode().findChildByType(M68kTokenTypes.COMMA);
      return commaNode != null && commaNode.getStartOffset() < admSr.getNode().getStartOffset();
    }
    return false;
  }
}
