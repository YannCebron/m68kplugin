/*
 * Copyright 2026 The Authors
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

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveVisitor;
import com.intellij.psi.PsiWalkingState;
import org.jetbrains.annotations.NotNull;

public abstract class M68kRecursiveElementWalkingVisitor extends M68kVisitor implements PsiRecursiveVisitor {

  private final PsiWalkingState myWalkingState = new PsiWalkingState(this) {
    @Override
    public void elementFinished(@NotNull PsiElement element) {
      M68kRecursiveElementWalkingVisitor.this.elementFinished(element);
    }
  };

  @Override
  public void visitElement(@NotNull PsiElement element) {
    myWalkingState.elementStarted(element);
  }

  protected void elementFinished(@NotNull PsiElement element) {
  }

  public void stopWalking() {
    myWalkingState.stopWalking();
  }
}
