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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lexer.M68kLexer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class M68kSyntaxHighlighter extends SyntaxHighlighterBase {

  private static final Map<IElementType, TextAttributesKey> keys = new HashMap<>();

  static {
    keys.put(TokenType.BAD_CHARACTER, M68kTextAttributes.BAD_CHARACTER);

    keys.put(M68kTokenTypes.DOT, M68kTextAttributes.DOT);
    keys.put(M68kTokenTypes.COLON, M68kTextAttributes.COLON);
    keys.put(M68kTokenTypes.COMMA, M68kTextAttributes.COMMA);
    keys.put(M68kTokenTypes.HASH, M68kTextAttributes.HASH);
    fillMap(keys, M68kTextAttributes.PARENTHESES,
      M68kTokenTypes.L_PAREN, M68kTokenTypes.R_PAREN,
      M68kTokenTypes.L_BRACKET, M68kTokenTypes.R_BRACKET);
    fillMap(keys, M68kTokenGroups.OPERATORS, M68kTextAttributes.OPERATORS);

    keys.put(M68kTokenTypes.COMMENT, M68kTextAttributes.COMMENT);
    keys.put(M68kTokenTypes.STRING, M68kTextAttributes.STRING);

    keys.put(M68kTokenTypes.DEC_NUMBER, M68kTextAttributes.DEC_NUMBER);
    keys.put(M68kTokenTypes.HEX_NUMBER, M68kTextAttributes.HEX_NUMBER);
    keys.put(M68kTokenTypes.OCT_NUMBER, M68kTextAttributes.OCT_NUMBER);
    keys.put(M68kTokenTypes.BIN_NUMBER, M68kTextAttributes.BIN_NUMBER);

    fillMap(keys, M68kTokenGroups.INSTRUCTIONS, M68kTextAttributes.INSTRUCTION);
    fillMap(keys, M68kTokenGroups.DATA_SIZES, M68kTextAttributes.DATA_SIZES);
    fillMap(keys, M68kTokenGroups.DIRECTIVES, M68kTextAttributes.DIRECTIVE);

    keys.put(M68kTokenTypes.DATA_REGISTER, M68kTextAttributes.DATA_REGISTER);
    keys.put(M68kTokenTypes.ADDRESS_REGISTER, M68kTextAttributes.ADDRESS_REGISTER);
    keys.put(M68kTokenTypes.SP, M68kTextAttributes.SP_REGISTER);
    keys.put(M68kTokenTypes.SSP, M68kTextAttributes.SSP_REGISTER);
    keys.put(M68kTokenTypes.USP, M68kTextAttributes.USP_REGISTER);
    keys.put(M68kTokenTypes.PC, M68kTextAttributes.PC_REGISTER);
    keys.put(M68kTokenTypes.SR, M68kTextAttributes.SR_REGISTER);
    keys.put(M68kTokenTypes.CCR, M68kTextAttributes.CCR_REGISTER);
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new M68kLexer();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return pack(keys.get(tokenType));
  }

}