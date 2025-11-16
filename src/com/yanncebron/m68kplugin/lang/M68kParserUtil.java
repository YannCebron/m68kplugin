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

package com.yanncebron.m68kplugin.lang;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.util.Key;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kGtExpression;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
class M68kParserUtil extends GeneratedParserUtilBase {

  private static final Key<Object> INSIDE_MACRO_CALL = Key.create("inside macro call");

  static boolean afterWhitespace(PsiBuilder b, @SuppressWarnings("unused") int level) {
    final IElementType left = b.rawLookup(-1);
    return left == TokenType.WHITE_SPACE;
  }

  static boolean enterMacroCall(PsiBuilder b, @SuppressWarnings("unused") int level) {
    b.putUserData(INSIDE_MACRO_CALL, Boolean.TRUE);
    return true;
  }

  static boolean exitMacroCall(PsiBuilder b, @SuppressWarnings("unused") int level) {
    b.putUserData(INSIDE_MACRO_CALL, null);
    return true;
  }

  static boolean insideMacroCall(PsiBuilder b, @SuppressWarnings("unused") int level) {
    return b.getUserData(INSIDE_MACRO_CALL) != null;
  }

  /**
   * Inside macro call parameters, do not parse standalone {@code Rn} (not first after macro name, {@code ',' or '/'})
   * as {@link com.yanncebron.m68kplugin.lang.psi.M68kRegisterRange}
   * to avoid precedence conflict with {@link com.yanncebron.m68kplugin.lang.psi.M68kAdmRrd}.
   */
  static boolean registerRangeStandaloneRegisterValid(PsiBuilder b, @SuppressWarnings("unused") int level) {
    if (!insideMacroCall(b, level)) return true;

    IElementType left = b.rawLookup(-1);
    return left == M68kTokenTypes.DIV || left == M68kTokenTypes.COMMA ||
      (left == TokenType.WHITE_SPACE && b.lookAhead(1) == M68kTokenTypes.DIV);
  }

  /**
   * Inside macro call parameter {@code MACRO_NAME <1,d0,"param">} or {@code MACRO_NAME 1,<"text",10>,3}.
   * Do not parse a '>' closing angle as {@link M68kGtExpression} if followed by
   * comma (',') or whitespace/linefeed.
   */
  static boolean notClosingAngledMacroCallParameter(PsiBuilder b, int level) {
    if (!insideMacroCall(b, level)) return true;

    if (b.getTokenType() == M68kTokenTypes.COMMA) {
      return false;
    }

    IElementType next = b.rawLookup(1);
    return next != null &&
      next != TokenType.WHITE_SPACE &&
      next != M68kTokenTypes.LINEFEED;
  }

}