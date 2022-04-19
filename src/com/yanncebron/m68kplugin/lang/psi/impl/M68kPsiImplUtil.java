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

package com.yanncebron.m68kplugin.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.SmartList;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncbinDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncdirDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kIncludeDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kOutputDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class M68kPsiImplUtil {

  @Nullable
  public static M68kDataSize getDataSize(M68kDataSized psiElement) {
    final ASTNode childByType = psiElement.getNode().findChildByType(M68kTokenGroups.DATA_SIZES);
    if (childByType == null) return null;

    return M68kDataSize.findByElementType(childByType.getElementType());
  }

  @NotNull
  public static List<M68kLocalLabel> getLocalLabels(M68kLabel label) {
    List<M68kLocalLabel> result = new SmartList<>();
    M68kPsiTreeUtil.processSiblingsForwards(label, m68kPsiElement -> {
      if (m68kPsiElement instanceof M68kLocalLabel) {
        result.add((M68kLocalLabel) m68kPsiElement);
      }
      return true;
    }, M68kLabel.class);
    return result;
  }

  // TODO extract M68kInstructionWithSrcAndDest
  public static boolean isDest(M68kInstruction instruction, @NotNull M68kAdm sourceOrDestElement) {
    final ASTNode commaNode = instruction.getNode().findChildByType(M68kTokenTypes.COMMA);
    return commaNode != null && commaNode.getStartOffset() < sourceOrDestElement.getNode().getStartOffset();
  }

  public static boolean isSrc(M68kInstruction instruction, @NotNull M68kAdm sourceOrDestElement) {
    final ASTNode commaNode = instruction.getNode().findChildByType(M68kTokenTypes.COMMA);
    return commaNode != null && commaNode.getStartOffset() > sourceOrDestElement.getNode().getStartOffset();
  }

  @NotNull
  public static EnumSet<M68kRegister> getRegisters(M68kRegisterRange range) {
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
  private static String _getStringPath(M68kPsiElement stringDirective) {
    final ASTNode node = stringDirective.getNode().findChildByType(M68kTokenTypes.STRING);
    if (node == null) {
      return null;
    }

    return StringUtil.unquoteString(node.getText());
  }
}
