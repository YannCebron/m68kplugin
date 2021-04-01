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

package com.yanncebron.m68kplugin.lang;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see M68kConditionalAssemblyCodeBlockSupportHandler for conditional assembly structure
 */
public class M68kBraceMatcher implements PairedBraceMatcher {

  private final BracePair[] pairs =
    new BracePair[]{
      new BracePair(M68kTokenTypes.L_PAREN, M68kTokenTypes.R_PAREN, false),
      new BracePair(M68kTokenTypes.L_BRACKET, M68kTokenTypes.R_BRACKET, false),

      new BracePair(M68kTokenTypes.MACRO, M68kTokenTypes.ENDM, true),
      new BracePair(M68kTokenTypes.INLINE, M68kTokenTypes.EINLINE, true),
      new BracePair(M68kTokenTypes.REM, M68kTokenTypes.EREM, true),
      new BracePair(M68kTokenTypes.REPT, M68kTokenTypes.ENDR, true),
    };

  @NotNull
  @Override
  public BracePair @NotNull [] getPairs() {
    return pairs;
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
    return true;
  }

  @Override
  public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
    return openingBraceOffset;
  }
}
