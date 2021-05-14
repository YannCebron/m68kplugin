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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;
import org.jetbrains.annotations.Nullable;

public final class M68kPsiTreeUtil {

  @SafeVarargs
  public static boolean processSiblingsBackwards(PsiElement element,
                                                 Processor<M68kPsiElement> processor,
                                                 Class<? extends M68kPsiElement>... stopAtElements) {
    for (PsiElement child = element.getPrevSibling(); child != null; child = child.getPrevSibling()) {
      if (!(child instanceof M68kPsiElement)) continue;

      if (PsiTreeUtil.instanceOf(child, stopAtElements)) return true;
      if (!processor.process((M68kPsiElement) child)) return false;
    }
    return true;
  }

  @SafeVarargs
  public static boolean processSiblingsForwards(PsiElement element,
                                                Processor<M68kPsiElement> processor,
                                                Class<? extends M68kPsiElement>... stopAtElements) {
    for (PsiElement child = element.getNextSibling(); child != null; child = child.getNextSibling()) {
      if (!(child instanceof M68kPsiElement)) continue;

      if (PsiTreeUtil.instanceOf(child, stopAtElements)) return true;
      if (!processor.process((M68kPsiElement) child)) return false;
    }
    return true;
  }

  @SafeVarargs
  public static boolean hasSiblingBackwards(PsiElement element,
                                            Class<? extends M68kPsiElement> matchingElement,
                                            Class<? extends M68kPsiElement>... stopAtElements) {
    return !processSiblingsBackwards(element, m68kPsiElement -> !matchingElement.isInstance(m68kPsiElement), stopAtElements);
  }

  @SafeVarargs
  public static boolean hasSiblingForwards(PsiElement element,
                                           Class<? extends M68kPsiElement> matchingElement,
                                           Class<? extends M68kPsiElement>... stopAtElements) {
    return !processSiblingsForwards(element, m68kPsiElement -> !matchingElement.isInstance(m68kPsiElement), stopAtElements);
  }

  @Nullable
  public static M68kPsiElement getContainingInstructionOrDirective(PsiElement element) {
    return PsiTreeUtil.getNonStrictParentOfType(element,
      M68kInstruction.class,
      M68kDirective.class,
      M68kConditionalAssemblyDirective.class);
  }

}
