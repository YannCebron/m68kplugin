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
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ObjectUtils;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncbinDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncdirDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncludeDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class M68kPsiImplUtil {

  @Nullable
  public static M68kDataSize getDataSize(M68kDataSized psiElement) {
    final ASTNode childByType = psiElement.getNode().findChildByType(M68kTokenGroups.DATA_SIZES);
    if (childByType == null) return null;

    return M68kDataSize.findByElementType(childByType.getElementType());
  }

  // TODO extract M68kInstructionWithSrcAndDest
  public static boolean isDest(M68kInstruction instruction, @NotNull M68kPsiElement sourceOrDestElement) {
    final ASTNode commaNode = instruction.getNode().findChildByType(M68kTokenTypes.COMMA);
    return commaNode != null && commaNode.getStartOffset() < sourceOrDestElement.getNode().getStartOffset();
  }

  public static boolean isSrc(M68kInstruction instruction, @NotNull M68kPsiElement sourceOrDestElement) {
    final ASTNode commaNode = instruction.getNode().findChildByType(M68kTokenTypes.COMMA);
    return commaNode != null && commaNode.getStartOffset() > sourceOrDestElement.getNode().getStartOffset();
  }

  @NotNull
  public static M68kRegister getRegister(M68kAdmDrd admElement) {
    return _getRegister(admElement);
  }

  @NotNull
  public static M68kRegister getRegister(M68kAdmArd admElement) {
    return _getRegister(admElement);
  }

  @NotNull
  public static M68kRegister getRegister(M68kAdmRrd admElement) {
    final M68kPsiElement registerElement = ObjectUtils.chooseNotNull(admElement.getAdmArd(), admElement.getAdmDrd());
    return _getRegister(Objects.requireNonNull(registerElement));
  }

  @NotNull
  private static M68kRegister _getRegister(M68kPsiElement admElement) {
    return M68kRegister.find(admElement.getFirstChild().getNode().getElementType(), admElement.getText());
  }

  @Nullable
  public static String getIncludePath(M68kIncbinDirective includeDirective) {
    return _getIncludePath(includeDirective);
  }

  @Nullable
  public static String getIncludePath(M68kIncludeDirective includeDirective) {
    return _getIncludePath(includeDirective);
  }

  @Nullable
  public static String getIncludePath(M68kIncdirDirective includeDirective) {
    return _getIncludePath(includeDirective);
  }

  @Nullable
  private static String _getIncludePath(M68kPsiElement includeDirective) {
    final ASTNode node = includeDirective.getNode().findChildByType(M68kTokenTypes.STRING);
    if (node == null) {
      return null;
    }

    return StringUtil.unquoteString(node.getText());
  }
}
