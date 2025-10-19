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

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.yanncebron.m68kplugin.lang.M68kFileElementType;
import com.yanncebron.m68kplugin.lang.M68kLanguage;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class M68kTokenTest extends TestCase {

  private static final TokenSet IGNORE_TYPES = TokenSet.create(
    M68kFileElementType.INSTANCE,
    M68kTokenTypes.COLON,
    M68kTokenTypes.COMMA,
    M68kTokenTypes.COMMENT,
    M68kTokenTypes.DOLLAR,
    M68kTokenTypes.DOT,
    M68kTokenTypes.HASH,
    M68kTokenTypes.ID,
    M68kTokenTypes.LINEFEED,
    M68kTokenTypes.L_BRACKET,
    M68kTokenTypes.L_PAREN,
    M68kTokenTypes.MACRO_CALL_ID,
    M68kTokenTypes.R_BRACKET,
    M68kTokenTypes.R_PAREN,
    M68kTokenTypes.STRING
  );

  private static final TokenSet IGNORE_REGISTERS = TokenSet.create(
    M68kTokenTypes.ADDRESS_REGISTER,
    M68kTokenTypes.CCR,
    M68kTokenTypes.DATA_REGISTER,
    M68kTokenTypes.DFC,
    M68kTokenTypes.PC,
    M68kTokenTypes.SFC,
    M68kTokenTypes.SP,
    M68kTokenTypes.SR,
    M68kTokenTypes.SSP,
    M68kTokenTypes.USP,
    M68kTokenTypes.VBR
  );

  private static final TokenSet ALL_IGNORES = TokenSet.orSet(IGNORE_TYPES, IGNORE_REGISTERS);

  private static final TokenSet[] GROUPS = new TokenSet[]{
    M68kTokenGroups.INSTRUCTIONS,
    M68kTokenGroups.DIRECTIVES,
    M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES,
    M68kTokenGroups.OPERATION_SIGNS,
    M68kTokenGroups.NUMBERS,
    M68kTokenGroups.DATA_SIZES
  };

  public void testAllTokenTypesRegisteredInOneGroup() {
    for (IElementType type : IElementType.enumerate(type ->
      type.getLanguage() == M68kLanguage.INSTANCE &&
        !(type instanceof M68kCompositeElementType) &&
        !(type instanceof IStubElementType))) {
      if (ALL_IGNORES.contains(type)) continue;

      int groupMatch = 0;
      for (TokenSet group : GROUPS) {
        if (group.contains(type)) groupMatch++;
      }

      assertEquals(type.toString(), 1, groupMatch);
    }
  }

  public void testUniqueDebugNames() {
    Map<String, IElementType> names = new HashMap<>();
    for (IElementType type : IElementType.enumerate(type ->
      type.getLanguage() == M68kLanguage.INSTANCE &&
        !(type instanceof M68kCompositeElementType) &&
        !(type instanceof IStubElementType))) {

      String debugName = type.toString();
      assertFalse("Duplicate debugName " + debugName, names.containsKey(debugName));
      names.put(debugName, type);
    }
  }
}
