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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface M68kAdmWithRegister extends M68kAdm {

  @NotNull
  default M68kRegister getRegister() {
    ASTNode node = getRegisterElement().getNode();
    return M68kRegister.find(node.getElementType(), node.getText());
  }

  /**
   * @return the associated register element
   */
  @NotNull
  default PsiElement getRegisterElement() {
    return getFirstChild();
  }

}
