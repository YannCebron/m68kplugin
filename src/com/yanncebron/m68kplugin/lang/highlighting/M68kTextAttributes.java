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

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class M68kTextAttributes {

  public static final TextAttributesKey INSTRUCTION =
    createTextAttributesKey("M68K_INSTRUCTION", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey LABEL =
    createTextAttributesKey("M68K_LABEL", DefaultLanguageHighlighterColors.STATIC_METHOD);
  public static final TextAttributesKey LOCAL_LABEL =
    createTextAttributesKey("M68K_LOCAL_LABEL", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
  public static final TextAttributesKey DATA_SIZES =
    createTextAttributesKey("M68K_DATA_SIZES", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey DIRECTIVE =
    createTextAttributesKey("M68K_DIRECTIVE", DefaultLanguageHighlighterColors.CONSTANT);

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


  public static final TextAttributesKey ADM_IMM =
    createTextAttributesKey("M68K_ADM_IMM", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_DRD =
    createTextAttributesKey("M68K_ADM_DRD", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_ARD =
    createTextAttributesKey("M68K_ADM_ARD", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_ARI =
    createTextAttributesKey("M68K_ADM_ARI", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_API =
    createTextAttributesKey("M68K_ADM_API", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_APD =
    createTextAttributesKey("M68K_ADM_APD", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_ADI =
    createTextAttributesKey("M68K_ADM_ADI", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_AIX =
    createTextAttributesKey("M68K_ADM_AIX", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_ABS =
    createTextAttributesKey("M68K_ADM_ABS", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_PCD =
    createTextAttributesKey("M68K_ADM_PCD", HighlighterColors.TEXT);
  public static final TextAttributesKey ADM_PCI =
    createTextAttributesKey("M68K_ADM_PCI", HighlighterColors.TEXT);


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
  public static final TextAttributesKey OPERATORS =
    createTextAttributesKey("M68K_OPERATORS", DefaultLanguageHighlighterColors.OPERATION_SIGN);

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

  public static final TextAttributesKey BAD_CHARACTER =
    createTextAttributesKey("M68K_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
}
