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

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiReference;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.impl.M68kDataSizedImpl;
import org.jetbrains.annotations.NotNull;

abstract class M68kMacroCallDirectiveMixIn extends M68kDataSizedImpl {

  protected M68kMacroCallDirectiveMixIn(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public PsiReference getReference() {
    final ASTNode idNode = getNode().findChildByType(M68kTokenTypes.MACRO_CALL_ID);
    assert idNode != null;

    return new M68kMacroCallReference(this, idNode);
  }

}
