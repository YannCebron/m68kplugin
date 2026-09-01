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

package com.yanncebron.m68kplugin.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncbinDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncdirDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncludeDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kOutputDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Map;

public final class M68kPsiImplUtil {

  private static final Map<IElementType, M68kDataSize> dataSizeMap = Map.of(
    M68kTokenTypes.DOT_S, M68kDataSize.SINGLE,
    M68kTokenTypes.DOT_B, M68kDataSize.BYTE,
    M68kTokenTypes.DOT_W, M68kDataSize.WORD,
    M68kTokenTypes.DOT_L, M68kDataSize.LONGWORD
  );

  @Nullable
  public static M68kDataSize getDataSize(@NotNull M68kDataSized psiElement) {
    final ASTNode childByType = psiElement.getNode().findChildByType(M68kTokenGroups.DATA_SIZES);
    if (childByType == null) return null;

    return dataSizeMap.get(childByType.getElementType());
  }

  public static boolean isFirstOperand(@NotNull M68kInstruction instruction, @NotNull M68kAdm operand) {
    final ASTNode commaNode = instruction.getNode().findChildByType(M68kTokenTypes.COMMA);
    return commaNode != null && commaNode.getStartOffset() < operand.getNode().getStartOffset();
  }

  public static boolean isSecondOperand(@NotNull M68kInstruction instruction, @NotNull M68kAdm operand) {
    final ASTNode commaNode = instruction.getNode().findChildByType(M68kTokenTypes.COMMA);
    return commaNode != null && commaNode.getStartOffset() > operand.getNode().getStartOffset();
  }

  @NotNull
  public static EnumSet<M68kRegister> getRegisters(@NotNull M68kRegisterRange range) {
    final M68kRegister fromRegister = range.getFrom().getRegister();

    final M68kAdmRrd to = range.getTo();
    if (to == null) {
      return EnumSet.of(fromRegister);
    }

    final M68kRegister toRegister = to.getRegister();
    if (fromRegister.ordinal() >= toRegister.ordinal()) {
      return EnumSet.noneOf(M68kRegister.class);
    }

    return EnumSet.range(fromRegister, toRegister);
  }

  @Nullable
  public static String getIncludePath(M68kIncbinDirective includeDirective) {
    return _getStringPath(includeDirective);
  }

  @Nullable
  public static String getIncludePath(M68kIncludeDirective includeDirective) {
    return _getStringPath(includeDirective);
  }

  @Nullable
  public static String getIncludePath(M68kIncdirDirective includeDirective) {
    return _getStringPath(includeDirective);
  }

  @Nullable
  public static String getOutputPath(M68kOutputDirective outputDirective) {
    return _getStringPath(outputDirective);
  }

  @Nullable
  private static String _getStringPath(@NotNull M68kPsiElement stringDirective) {
    final ASTNode node = stringDirective.getNode().findChildByType(M68kTokenTypes.STRING);
    if (node == null) {
      return null;
    }

    return StringUtil.unquoteString(node.getText());
  }
}
