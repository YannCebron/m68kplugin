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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class M68kTextAttributes {

  public static final TextAttributesKey INSTRUCTION =
    createTextAttributesKey("M68K_INSTRUCTION", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey PRIVILEGED_INSTRUCTION =
    createTextAttributesKey("M68K_PRIVILEGED_INSTRUCTION", DefaultLanguageHighlighterColors.HIGHLIGHTED_REFERENCE);

  public static final TextAttributesKey MACRO_CALL =
    createTextAttributesKey("M68K_MACRO_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL);
  public static final TextAttributesKey MACRO_PARAMETER =
    createTextAttributesKey("M68K_MACRO_PARAMETER", DefaultLanguageHighlighterColors.INLINE_PARAMETER_HINT_CURRENT);

  public static final TextAttributesKey BUILTIN_SYMBOL =
    createTextAttributesKey("M68K_BUILTIN_SYMBOL", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
  public static final TextAttributesKey LABEL =
    createTextAttributesKey("M68K_LABEL", DefaultLanguageHighlighterColors.STATIC_METHOD);
  public static final TextAttributesKey LOCAL_LABEL =
    createTextAttributesKey("M68K_LOCAL_LABEL", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
  public static final TextAttributesKey DATA_SIZES =
    createTextAttributesKey("M68K_DATA_SIZES", DefaultLanguageHighlighterColors.KEYWORD);

  public static final TextAttributesKey DIRECTIVE =
    createTextAttributesKey("M68K_DIRECTIVE", DefaultLanguageHighlighterColors.CONSTANT);
  public static final TextAttributesKey CONDITIONAL_ASSEMBLY_DIRECTIVE =
    createTextAttributesKey("M68K_CONDITIONAL_ASSEMBLY_DIRECTIVE", DefaultLanguageHighlighterColors.METADATA);

  public static final TextAttributesKey DATA_REGISTER =
    createTextAttributesKey("M68K_DATA_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey ADDRESS_REGISTER =
    createTextAttributesKey("M68K_ADDRESS_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey SP_REGISTER =
    createTextAttributesKey("M68K_SP_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey SSP_REGISTER =
    createTextAttributesKey("M68K_SSP_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey USP_REGISTER =
    createTextAttributesKey("M68K_USP_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey PC_REGISTER =
    createTextAttributesKey("M68K_PC_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey SR_REGISTER =
    createTextAttributesKey("M68K_SR_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey CCR_REGISTER =
    createTextAttributesKey("M68K_CCR_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
  public static final TextAttributesKey DFC_REGISTER =
    createTextAttributesKey("M68K_DFC_REGISTER", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);

  public static final TextAttributesKey DOT =
    createTextAttributesKey("M68K_DOT", DefaultLanguageHighlighterColors.DOT);
  public static final TextAttributesKey COLON =
    createTextAttributesKey("M68K_COLON", DefaultLanguageHighlighterColors.DOT);
  public static final TextAttributesKey COMMA =
    createTextAttributesKey("M68K_COMMA", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey HASH =
    createTextAttributesKey("M68K_HASH", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey PARENTHESES =
    createTextAttributesKey("M68K_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
  public static final TextAttributesKey OPERATION_SIGN =
    createTextAttributesKey("M68K_OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN);

  public static final TextAttributesKey COMMENT =
    createTextAttributesKey("M68K_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

  public static final TextAttributesKey STRING =
    createTextAttributesKey("M68K_STRING", DefaultLanguageHighlighterColors.STRING);

  public static final TextAttributesKey DEC_NUMBER =
    createTextAttributesKey("M68K_DEC_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey HEX_NUMBER =
    createTextAttributesKey("M68K_HEX_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey OCT_NUMBER =
    createTextAttributesKey("M68K_OCT_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey BIN_NUMBER =
    createTextAttributesKey("M68K_BIN_NUMBER", DefaultLanguageHighlighterColors.NUMBER);

}
