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
import com.intellij.lang.PsiBuilder.Marker;
import static com.yanncebron.m68kplugin.lang.psi.M68kTypes.*;
import static com.yanncebron.m68kplugin.lang.M68kParserUtil.*;
import static com.yanncebron.m68kplugin.lang.M68kParser.*;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class M68kExpressionParser {

  /* ********************************************************** */
  // L_BRACKET expression R_BRACKET
  static boolean bracket_paren_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bracket_paren_expression")) return false;
    if (!nextTokenIsFast(b, L_BRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokenFast(b, L_BRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, R_BRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // L_PAREN expression R_PAREN
  static boolean plain_paren_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "plain_paren_expression")) return false;
    if (!nextTokenIsFast(b, L_PAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokenFast(b, L_PAREN);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, R_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: BINARY(logical_or_expression)
  // 1: BINARY(logical_and_expression)
  // 2: BINARY(equals_expression) BINARY(not_equals_expression)
  // 3: BINARY(lt_expression) BINARY(lt_eq_expression) BINARY(gt_expression) BINARY(gt_eq_expression)
  // 4: BINARY(plus_expression) BINARY(minus_expression)
  // 5: BINARY(mul_expression) BINARY(div_expression) BINARY(mod_expression)
  // 6: BINARY(or_expression)
  // 7: BINARY(xor_expression)
  // 8: BINARY(and_expression)
  // 9: BINARY(shift_left_expression) BINARY(shift_right_expression)
  // 10: PREFIX(unary_plus_expression) PREFIX(unary_minus_expression) PREFIX(unary_not_expression) PREFIX(unary_complement_expression)
  // 11: ATOM(number_expression) ATOM(string_expression) ATOM(label_ref_expression) ATOM(paren_expression)
  public static boolean expression(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression")) return false;
    addVariant(b, "<expression>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = unary_plus_expression(b, l + 1);
    if (!r) r = unary_minus_expression(b, l + 1);
    if (!r) r = unary_not_expression(b, l + 1);
    if (!r) r = unary_complement_expression(b, l + 1);
    if (!r) r = number_expression(b, l + 1);
    if (!r) r = string_expression(b, l + 1);
    if (!r) r = label_ref_expression(b, l + 1);
    if (!r) r = paren_expression(b, l + 1);
    p = r;
    r = r && expression_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expression_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && consumeTokenSmart(b, PIPE_PIPE)) {
        r = expression(b, l, 0);
        exit_section_(b, l, m, LOGICAL_OR_EXPRESSION, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, AMPERSAND_AMPERSAND)) {
        r = expression(b, l, 1);
        exit_section_(b, l, m, LOGICAL_AND_EXPRESSION, r, true, null);
      }
      else if (g < 2 && equals_expression_0(b, l + 1)) {
        r = expression(b, l, 2);
        exit_section_(b, l, m, EQUALS_EXPRESSION, r, true, null);
      }
      else if (g < 2 && not_equals_expression_0(b, l + 1)) {
        r = expression(b, l, 2);
        exit_section_(b, l, m, NOT_EQUALS_EXPRESSION, r, true, null);
      }
      else if (g < 3 && consumeTokenSmart(b, LT)) {
        r = expression(b, l, 3);
        exit_section_(b, l, m, LT_EXPRESSION, r, true, null);
      }
      else if (g < 3 && consumeTokenSmart(b, LT_EQ)) {
        r = expression(b, l, 3);
        exit_section_(b, l, m, LT_EQ_EXPRESSION, r, true, null);
      }
      else if (g < 3 && gt_expression_0(b, l + 1)) {
        r = expression(b, l, 3);
        exit_section_(b, l, m, GT_EXPRESSION, r, true, null);
      }
      else if (g < 3 && consumeTokenSmart(b, GT_EQ)) {
        r = expression(b, l, 3);
        exit_section_(b, l, m, GT_EQ_EXPRESSION, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, PLUS)) {
        r = expression(b, l, 4);
        exit_section_(b, l, m, PLUS_EXPRESSION, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, MINUS)) {
        r = expression(b, l, 4);
        exit_section_(b, l, m, MINUS_EXPRESSION, r, true, null);
      }
      else if (g < 5 && consumeTokenSmart(b, MUL)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, MUL_EXPRESSION, r, true, null);
      }
      else if (g < 5 && consumeTokenSmart(b, DIV)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, DIV_EXPRESSION, r, true, null);
      }
      else if (g < 5 && mod_expression_0(b, l + 1)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, MOD_EXPRESSION, r, true, null);
      }
      else if (g < 6 && or_expression_0(b, l + 1)) {
        r = expression(b, l, 6);
        exit_section_(b, l, m, OR_EXPRESSION, r, true, null);
      }
      else if (g < 7 && xor_expression_0(b, l + 1)) {
        r = expression(b, l, 7);
        exit_section_(b, l, m, XOR_EXPRESSION, r, true, null);
      }
      else if (g < 8 && consumeTokenSmart(b, AMPERSAND)) {
        r = expression(b, l, 8);
        exit_section_(b, l, m, AND_EXPRESSION, r, true, null);
      }
      else if (g < 9 && consumeTokenSmart(b, LT_LT)) {
        r = expression(b, l, 9);
        exit_section_(b, l, m, SHIFT_LEFT_EXPRESSION, r, true, null);
      }
      else if (g < 9 && consumeTokenSmart(b, GT_GT)) {
        r = expression(b, l, 9);
        exit_section_(b, l, m, SHIFT_RIGHT_EXPRESSION, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // EQ | EQ_EQ
  private static boolean equals_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equals_expression_0")) return false;
    boolean r;
    r = consumeTokenSmart(b, EQ);
    if (!r) r = consumeTokenSmart(b, EQ_EQ);
    return r;
  }

  // EXCLAMATION_EQ | LT_GT
  private static boolean not_equals_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_equals_expression_0")) return false;
    boolean r;
    r = consumeTokenSmart(b, EXCLAMATION_EQ);
    if (!r) r = consumeTokenSmart(b, LT_GT);
    return r;
  }

  // GT <<notClosingAngledMacroCallParameter>>
  private static boolean gt_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gt_expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, GT);
    r = r && notClosingAngledMacroCallParameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean unary_plus_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_plus_expression")) return false;
    if (!nextTokenIsSmart(b, PLUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, PLUS);
    p = r;
    r = p && expression(b, l, 10);
    exit_section_(b, l, m, UNARY_PLUS_EXPRESSION, r, p, null);
    return r || p;
  }

  public static boolean unary_minus_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_minus_expression")) return false;
    if (!nextTokenIsSmart(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, MINUS);
    p = r;
    r = p && expression(b, l, 10);
    exit_section_(b, l, m, UNARY_MINUS_EXPRESSION, r, p, null);
    return r || p;
  }

  // PERCENT | SLASH_SLASH
  private static boolean mod_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mod_expression_0")) return false;
    boolean r;
    r = consumeTokenSmart(b, PERCENT);
    if (!r) r = consumeTokenSmart(b, SLASH_SLASH);
    return r;
  }

  // PIPE | EXCLAMATION
  private static boolean or_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "or_expression_0")) return false;
    boolean r;
    r = consumeTokenSmart(b, PIPE);
    if (!r) r = consumeTokenSmart(b, EXCLAMATION);
    return r;
  }

  // POW | TILDE
  private static boolean xor_expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xor_expression_0")) return false;
    boolean r;
    r = consumeTokenSmart(b, POW);
    if (!r) r = consumeTokenSmart(b, TILDE);
    return r;
  }

  public static boolean unary_not_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_not_expression")) return false;
    if (!nextTokenIsSmart(b, EXCLAMATION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, EXCLAMATION);
    p = r;
    r = p && expression(b, l, 10);
    exit_section_(b, l, m, UNARY_NOT_EXPRESSION, r, p, null);
    return r || p;
  }

  public static boolean unary_complement_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_complement_expression")) return false;
    if (!nextTokenIsSmart(b, TILDE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, TILDE);
    p = r;
    r = p && expression(b, l, 10);
    exit_section_(b, l, m, UNARY_COMPLEMENT_EXPRESSION, r, p, null);
    return r || p;
  }

  // DEC_NUMBER | BIN_NUMBER | HEX_NUMBER | OCT_NUMBER
  public static boolean number_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "number_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NUMBER_EXPRESSION, "<number expression>");
    r = consumeTokenSmart(b, DEC_NUMBER);
    if (!r) r = consumeTokenSmart(b, BIN_NUMBER);
    if (!r) r = consumeTokenSmart(b, HEX_NUMBER);
    if (!r) r = consumeTokenSmart(b, OCT_NUMBER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // STRING
  public static boolean string_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_expression")) return false;
    if (!nextTokenIsSmart(b, STRING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, STRING);
    exit_section_(b, m, STRING_EXPRESSION, r);
    return r;
  }

  // ID
  public static boolean label_ref_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label_ref_expression")) return false;
    if (!nextTokenIsSmart(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LABEL_REF_EXPRESSION, "<label>");
    r = consumeTokenSmart(b, ID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // plain_paren_expression | bracket_paren_expression
  public static boolean paren_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paren_expression")) return false;
    if (!nextTokenIsFast(b, L_BRACKET, L_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PAREN_EXPRESSION, "<paren expression>");
    r = plain_paren_expression(b, l + 1);
    if (!r) r = bracket_paren_expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
