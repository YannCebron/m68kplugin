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
package com.yanncebron.m68kplugin.lang;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.yanncebron.m68kplugin.lang.psi.M68kTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import static com.yanncebron.m68kplugin.lang.M68kParser.*;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class M68kDirectivesParser {

  /* ********************************************************** */
  // BLK data_size_all? expression COMMA expression
  public static boolean blk_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive")) return false;
    if (!nextTokenIs(b, BLK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLK_DIRECTIVE, null);
    r = consumeToken(b, BLK);
    p = r; // pin = 1
    r = r && report_error_(b, blk_directive_1(b, l + 1));
    r = p && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean blk_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // DC data_size_all? expression (COMMA expression)*
  public static boolean dc_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive")) return false;
    if (!nextTokenIs(b, DC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DC_DIRECTIVE, null);
    r = consumeToken(b, DC);
    p = r; // pin = 1
    r = r && report_error_(b, dc_directive_1(b, l + 1));
    r = p && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1)) && r;
    r = p && dc_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean dc_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // (COMMA expression)*
  private static boolean dc_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!dc_directive_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "dc_directive_3", c)) break;
    }
    return true;
  }

  // COMMA expression
  private static boolean dc_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DCB data_size_all? expression (COMMA expression)?
  public static boolean dcb_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dcb_directive")) return false;
    if (!nextTokenIs(b, DCB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DCB_DIRECTIVE, null);
    r = consumeToken(b, DCB);
    p = r; // pin = 1
    r = r && report_error_(b, dcb_directive_1(b, l + 1));
    r = p && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1)) && r;
    r = p && dcb_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean dcb_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dcb_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // (COMMA expression)?
  private static boolean dcb_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dcb_directive_3")) return false;
    dcb_directive_3_0(b, l + 1);
    return true;
  }

  // COMMA expression
  private static boolean dcb_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dcb_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // equ_directive |
  //                        blk_directive |
  //                        equals_directive |
  //                        set_directive |
  //                        equr_directive |
  //                        even_directive |
  //                        odd_directive |
  //                        dc_directive |
  //                        dcb_directive |
  //                        ds_directive |
  //                        incbin_directive |
  //                        incdir_directive |
  //                        include_directive |
  //                        rsset_directive |
  //                        rsreset_directive |
  //                        rs_directive |
  //                        opt_directive |
  //                        org_directive |
  //                        macro_directive |
  //                        endm_directive |
  //                        end_directive |
  //                        section_directive
  static boolean directives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "directives")) return false;
    boolean r;
    r = equ_directive(b, l + 1);
    if (!r) r = blk_directive(b, l + 1);
    if (!r) r = equals_directive(b, l + 1);
    if (!r) r = set_directive(b, l + 1);
    if (!r) r = equr_directive(b, l + 1);
    if (!r) r = even_directive(b, l + 1);
    if (!r) r = odd_directive(b, l + 1);
    if (!r) r = dc_directive(b, l + 1);
    if (!r) r = dcb_directive(b, l + 1);
    if (!r) r = ds_directive(b, l + 1);
    if (!r) r = incbin_directive(b, l + 1);
    if (!r) r = incdir_directive(b, l + 1);
    if (!r) r = include_directive(b, l + 1);
    if (!r) r = rsset_directive(b, l + 1);
    if (!r) r = rsreset_directive(b, l + 1);
    if (!r) r = rs_directive(b, l + 1);
    if (!r) r = opt_directive(b, l + 1);
    if (!r) r = org_directive(b, l + 1);
    if (!r) r = macro_directive(b, l + 1);
    if (!r) r = endm_directive(b, l + 1);
    if (!r) r = end_directive(b, l + 1);
    if (!r) r = section_directive(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // DS data_size_all? expression
  public static boolean ds_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ds_directive")) return false;
    if (!nextTokenIs(b, DS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DS_DIRECTIVE, null);
    r = consumeToken(b, DS);
    p = r; // pin = 1
    r = r && report_error_(b, ds_directive_1(b, l + 1));
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean ds_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ds_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // END
  public static boolean end_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "end_directive")) return false;
    if (!nextTokenIs(b, END)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, END);
    exit_section_(b, m, END_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // ENDM
  public static boolean endm_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endm_directive")) return false;
    if (!nextTokenIs(b, ENDM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENDM);
    exit_section_(b, m, ENDM_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // label EQU expression
  public static boolean equ_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equ_directive")) return false;
    if (!nextTokenIs(b, "<equ directive>", ID, UNDERSCORE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQU_DIRECTIVE, "<equ directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, EQU);
    p = r; // pin = 2
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // label EQ expression
  public static boolean equals_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equals_directive")) return false;
    if (!nextTokenIs(b, "<equals directive>", ID, UNDERSCORE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQUALS_DIRECTIVE, "<equals directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, EQ);
    p = r; // pin = 2
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // label EQUR adm_rrd
  public static boolean equr_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equr_directive")) return false;
    if (!nextTokenIs(b, "<equr directive>", ID, UNDERSCORE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQUR_DIRECTIVE, "<equr directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, EQUR);
    p = r; // pin = 2
    r = r && adm_rrd(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // EVEN
  public static boolean even_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "even_directive")) return false;
    if (!nextTokenIs(b, EVEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EVEN);
    exit_section_(b, m, EVEN_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // INCBIN include_path (COMMA expression)? (COMMA expression)?
  public static boolean incbin_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incbin_directive")) return false;
    if (!nextTokenIs(b, INCBIN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCBIN_DIRECTIVE, null);
    r = consumeToken(b, INCBIN);
    p = r; // pin = 1
    r = r && report_error_(b, include_path(b, l + 1));
    r = p && report_error_(b, incbin_directive_2(b, l + 1)) && r;
    r = p && incbin_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA expression)?
  private static boolean incbin_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incbin_directive_2")) return false;
    incbin_directive_2_0(b, l + 1);
    return true;
  }

  // COMMA expression
  private static boolean incbin_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incbin_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA expression)?
  private static boolean incbin_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incbin_directive_3")) return false;
    incbin_directive_3_0(b, l + 1);
    return true;
  }

  // COMMA expression
  private static boolean incbin_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incbin_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // INCDIR include_path
  public static boolean incdir_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incdir_directive")) return false;
    if (!nextTokenIs(b, INCDIR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCDIR_DIRECTIVE, null);
    r = consumeToken(b, INCDIR);
    p = r; // pin = 1
    r = r && include_path(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // INCLUDE include_path
  public static boolean include_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_directive")) return false;
    if (!nextTokenIs(b, INCLUDE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCLUDE_DIRECTIVE, null);
    r = consumeToken(b, INCLUDE);
    p = r; // pin = 1
    r = r && include_path(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // STRING
  static boolean include_path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_path")) return false;
    if (!nextTokenIs(b, "<include path>", STRING)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<include path>");
    r = consumeToken(b, STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // label MACRO
  public static boolean macro_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_directive")) return false;
    if (!nextTokenIs(b, "<macro directive>", ID, UNDERSCORE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MACRO_DIRECTIVE, "<macro directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, MACRO);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ODD
  public static boolean odd_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "odd_directive")) return false;
    if (!nextTokenIs(b, ODD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ODD);
    exit_section_(b, m, ODD_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // OPT opt_element (COMMA opt_element)*
  public static boolean opt_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_directive")) return false;
    if (!nextTokenIs(b, OPT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, OPT_DIRECTIVE, null);
    r = consumeToken(b, OPT);
    p = r; // pin = 1
    r = r && report_error_(b, opt_element(b, l + 1));
    r = p && opt_directive_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA opt_element)*
  private static boolean opt_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_directive_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!opt_directive_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "opt_directive_2", c)) break;
    }
    return true;
  }

  // COMMA opt_element
  private static boolean opt_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && opt_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID (PLUS | MINUS)
  static boolean opt_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element")) return false;
    if (!nextTokenIs(b, "<option(+|-)>", ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null, "<option(+|-)>");
    r = consumeToken(b, ID);
    p = r; // pin = 1
    r = r && opt_element_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // PLUS | MINUS
  private static boolean opt_element_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element_1")) return false;
    boolean r;
    r = consumeToken(b, PLUS);
    if (!r) r = consumeToken(b, MINUS);
    return r;
  }

  /* ********************************************************** */
  // ORG expression
  public static boolean org_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "org_directive")) return false;
    if (!nextTokenIs(b, ORG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ORG_DIRECTIVE, null);
    r = consumeToken(b, ORG);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // RS data_size_all? expression
  public static boolean rs_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rs_directive")) return false;
    if (!nextTokenIs(b, RS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RS_DIRECTIVE, null);
    r = consumeToken(b, RS);
    p = r; // pin = 1
    r = r && report_error_(b, rs_directive_1(b, l + 1));
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean rs_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rs_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // RSRESET
  public static boolean rsreset_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rsreset_directive")) return false;
    if (!nextTokenIs(b, RSRESET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RSRESET);
    exit_section_(b, m, RSRESET_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // RSSET expression
  public static boolean rsset_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rsset_directive")) return false;
    if (!nextTokenIs(b, RSSET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RSSET_DIRECTIVE, null);
    r = consumeToken(b, RSSET);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SECTION expression (COMMA expression)? (COMMA expression)?
  public static boolean section_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive")) return false;
    if (!nextTokenIs(b, SECTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SECTION_DIRECTIVE, null);
    r = consumeToken(b, SECTION);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && report_error_(b, section_directive_2(b, l + 1)) && r;
    r = p && section_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA expression)?
  private static boolean section_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_2")) return false;
    section_directive_2_0(b, l + 1);
    return true;
  }

  // COMMA expression
  private static boolean section_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA expression)?
  private static boolean section_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_3")) return false;
    section_directive_3_0(b, l + 1);
    return true;
  }

  // COMMA expression
  private static boolean section_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // label SET expression
  public static boolean set_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_directive")) return false;
    if (!nextTokenIs(b, "<set directive>", ID, UNDERSCORE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SET_DIRECTIVE, "<set directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, SET);
    p = r; // pin = 2
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
