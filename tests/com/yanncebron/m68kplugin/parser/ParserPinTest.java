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

package com.yanncebron.m68kplugin.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.yanncebron.m68kplugin.lang.psi.M68kInstruction;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTreeUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.conditional.M68kConditionalAssemblyDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDirective;

/**
 * Validate all instructions/directives have a working {@code pin = 1} in BNF.
 */
public class ParserPinTest extends M68kParsingTestCase {

  public ParserPinTest() {
    super("DUMMY_NOT_USED");
  }

  public void testInstructions() {
    testAllHaveWorkingPin(M68kTokenGroups.INSTRUCTIONS, M68kInstruction.class);
  }

  public void testDirectives() {
    testAllHaveWorkingPin(M68kTokenGroups.DIRECTIVES, M68kDirective.class);
  }

  public void testConditionalAssemblyDirectives() {
    testAllHaveWorkingPin(M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES, M68kConditionalAssemblyDirective.class);
  }

  private void testAllHaveWorkingPin(TokenSet tokenSet, Class<? extends M68kPsiElement> expectedPsiClass) {
    for (IElementType type : tokenSet.getTypes()) {
      myFile = createPsiFile("a", "label " + type.toString());
      final M68kPsiElement directive = M68kPsiTreeUtil.getContainingInstructionOrDirective(myFile.findElementAt(7));
      assertInstanceOf(directive, expectedPsiClass);
    }
  }
}
