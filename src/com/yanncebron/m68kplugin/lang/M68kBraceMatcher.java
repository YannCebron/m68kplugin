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

package com.yanncebron.m68kplugin.lang;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ArrayUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class M68kBraceMatcher implements PairedBraceMatcher {

  private final BracePair[] pairs = ArrayUtil.mergeArrays(
    new BracePair[]{
      new BracePair(M68kTokenTypes.L_PAREN, M68kTokenTypes.R_PAREN, false),
      new BracePair(M68kTokenTypes.L_BRACKET, M68kTokenTypes.R_BRACKET, false),

      new BracePair(M68kTokenTypes.MACRO, M68kTokenTypes.ENDM, true),

    },
    createConditionalAssemblyBracePairs(
      M68kTokenTypes.IF, M68kTokenTypes.IFB, M68kTokenTypes.IFNB,
      M68kTokenTypes.IFD, M68kTokenTypes.IFND, M68kTokenTypes.IFEQ,
      M68kTokenTypes.IFGE, M68kTokenTypes.IFGT, M68kTokenTypes.IFLE, M68kTokenTypes.IFLT,
      M68kTokenTypes.IFNE, M68kTokenTypes.ELSE, M68kTokenTypes.ELSEIF)
  );

  private BracePair[] createConditionalAssemblyBracePairs(IElementType... tokenTypes) {
    List<BracePair> pairs = new ArrayList<>();
    for (IElementType tokenType : tokenTypes) {
      pairs.add(new BracePair(tokenType, M68kTokenTypes.ENDC, true));
      pairs.add(new BracePair(tokenType, M68kTokenTypes.ENDIF, true));
    }
    return pairs.toArray(new BracePair[0]);
  }

  @NotNull
  @Override
  public BracePair[] getPairs() {
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
