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
import static com.yanncebron.m68kplugin.lang.M68kParserUtil.*;
import static com.yanncebron.m68kplugin.lang.M68kParser.*;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class M68kDirectivesParser {

  /* ********************************************************** */
  // ADDWATCH expression
  public static boolean addwatch_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addwatch_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ADDWATCH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADDWATCH_DIRECTIVE, "<directive>");
    r = consumeToken(b, ADDWATCH);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ALIGN expression COMMA expression
  public static boolean align_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "align_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ALIGN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ALIGN_DIRECTIVE, "<directive>");
    r = consumeToken(b, ALIGN);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BLK data_size_all? expression COMMA expression
  public static boolean blk_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive")) return false;
    if (!nextTokenIs(b, "<directive>", BLK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLK_DIRECTIVE, "<directive>");
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
  // BSS_C
  public static boolean bss_c_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bss_c_directive")) return false;
    if (!nextTokenIs(b, "<directive>", BSS_C)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BSS_C_DIRECTIVE, "<directive>");
    r = consumeToken(b, BSS_C);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BSS
  public static boolean bss_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bss_directive")) return false;
    if (!nextTokenIs(b, "<directive>", BSS)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BSS_DIRECTIVE, "<directive>");
    r = consumeToken(b, BSS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BSS_F
  public static boolean bss_f_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bss_f_directive")) return false;
    if (!nextTokenIs(b, "<directive>", BSS_F)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BSS_F_DIRECTIVE, "<directive>");
    r = consumeToken(b, BSS_F);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // CNOP expression COMMA expression
  public static boolean cnop_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cnop_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CNOP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CNOP_DIRECTIVE, "<directive>");
    r = consumeToken(b, CNOP);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // CODE_C
  public static boolean code_c_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_c_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CODE_C)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CODE_C_DIRECTIVE, "<directive>");
    r = consumeToken(b, CODE_C);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // CODE
  public static boolean code_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CODE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CODE_DIRECTIVE, "<directive>");
    r = consumeToken(b, CODE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // CODE_F
  public static boolean code_f_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_f_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CODE_F)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CODE_F_DIRECTIVE, "<directive>");
    r = consumeToken(b, CODE_F);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // CSEG
  public static boolean cseg_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cseg_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CSEG)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CSEG_DIRECTIVE, "<directive>");
    r = consumeToken(b, CSEG);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DATA_C
  public static boolean data_c_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_c_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DATA_C)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DATA_C_DIRECTIVE, "<directive>");
    r = consumeToken(b, DATA_C);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DATA
  public static boolean data_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DATA)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DATA_DIRECTIVE, "<directive>");
    r = consumeToken(b, DATA);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DATA_F
  public static boolean data_f_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_f_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DATA_F)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DATA_F_DIRECTIVE, "<directive>");
    r = consumeToken(b, DATA_F);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DC data_size_all? expression (COMMA expression)*
  public static boolean dc_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DC_DIRECTIVE, "<directive>");
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
    if (!nextTokenIs(b, "<directive>", DCB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DCB_DIRECTIVE, "<directive>");
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
  //                        align_directive |
  //                        cnop_directive |
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
  //                        mexit_directive |
  //                        macro_parameter_directive |
  //                        macro_call_directive |
  //                        end_directive |
  //                        section_directive |
  //                        text_directive |
  //                        cseg_directive |
  //                        code_directive |
  //                        code_c_directive |
  //                        code_f_directive |
  //                        dseg_directive |
  //                        data_directive |
  //                        data_c_directive |
  //                        data_f_directive |
  //                        bss_directive |
  //                        bss_c_directive |
  //                        bss_f_directive |
  //                        addwatch_directive |
  //                        jumperr_directive |
  //                        jumpptr_directive |
  //                        list_directive |
  //                        nolist_directive |
  //                        page_directive |
  //                        nopage_directive |
  //                        plen_directive |
  //                        llen_directive |
  //                        spc_directive |
  //                        inline_directive |
  //                        einline_directive |
  //                        rem_directive |
  //                        erem_directive
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
    if (!r) r = align_directive(b, l + 1);
    if (!r) r = cnop_directive(b, l + 1);
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
    if (!r) r = mexit_directive(b, l + 1);
    if (!r) r = macro_parameter_directive(b, l + 1);
    if (!r) r = macro_call_directive(b, l + 1);
    if (!r) r = end_directive(b, l + 1);
    if (!r) r = section_directive(b, l + 1);
    if (!r) r = text_directive(b, l + 1);
    if (!r) r = cseg_directive(b, l + 1);
    if (!r) r = code_directive(b, l + 1);
    if (!r) r = code_c_directive(b, l + 1);
    if (!r) r = code_f_directive(b, l + 1);
    if (!r) r = dseg_directive(b, l + 1);
    if (!r) r = data_directive(b, l + 1);
    if (!r) r = data_c_directive(b, l + 1);
    if (!r) r = data_f_directive(b, l + 1);
    if (!r) r = bss_directive(b, l + 1);
    if (!r) r = bss_c_directive(b, l + 1);
    if (!r) r = bss_f_directive(b, l + 1);
    if (!r) r = addwatch_directive(b, l + 1);
    if (!r) r = jumperr_directive(b, l + 1);
    if (!r) r = jumpptr_directive(b, l + 1);
    if (!r) r = list_directive(b, l + 1);
    if (!r) r = nolist_directive(b, l + 1);
    if (!r) r = page_directive(b, l + 1);
    if (!r) r = nopage_directive(b, l + 1);
    if (!r) r = plen_directive(b, l + 1);
    if (!r) r = llen_directive(b, l + 1);
    if (!r) r = spc_directive(b, l + 1);
    if (!r) r = inline_directive(b, l + 1);
    if (!r) r = einline_directive(b, l + 1);
    if (!r) r = rem_directive(b, l + 1);
    if (!r) r = erem_directive(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // DS data_size_all? expression
  public static boolean ds_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ds_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DS_DIRECTIVE, "<directive>");
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
  // DSEG
  public static boolean dseg_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dseg_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DSEG)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DSEG_DIRECTIVE, "<directive>");
    r = consumeToken(b, DSEG);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // EINLINE
  public static boolean einline_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "einline_directive")) return false;
    if (!nextTokenIs(b, "<directive>", EINLINE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EINLINE_DIRECTIVE, "<directive>");
    r = consumeToken(b, EINLINE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // END
  public static boolean end_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "end_directive")) return false;
    if (!nextTokenIs(b, "<directive>", END)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, END_DIRECTIVE, "<directive>");
    r = consumeToken(b, END);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ENDM
  public static boolean endm_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endm_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ENDM)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENDM_DIRECTIVE, "<directive>");
    r = consumeToken(b, ENDM);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // label EQU expression
  public static boolean equ_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equ_directive")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQU_DIRECTIVE, "<directive>");
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
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQUALS_DIRECTIVE, "<directive>");
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
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQUR_DIRECTIVE, "<directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, EQUR);
    p = r; // pin = 2
    r = r && adm_rrd(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // EREM
  public static boolean erem_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "erem_directive")) return false;
    if (!nextTokenIs(b, "<directive>", EREM)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EREM_DIRECTIVE, "<directive>");
    r = consumeToken(b, EREM);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // EVEN
  public static boolean even_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "even_directive")) return false;
    if (!nextTokenIs(b, "<directive>", EVEN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EVEN_DIRECTIVE, "<directive>");
    r = consumeToken(b, EVEN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // INCBIN include_path (COMMA expression)? (COMMA expression)?
  public static boolean incbin_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incbin_directive")) return false;
    if (!nextTokenIs(b, "<directive>", INCBIN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCBIN_DIRECTIVE, "<directive>");
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
    if (!nextTokenIs(b, "<directive>", INCDIR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCDIR_DIRECTIVE, "<directive>");
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
    if (!nextTokenIs(b, "<directive>", INCLUDE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCLUDE_DIRECTIVE, "<directive>");
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
  // INLINE
  public static boolean inline_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "inline_directive")) return false;
    if (!nextTokenIs(b, "<directive>", INLINE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INLINE_DIRECTIVE, "<directive>");
    r = consumeToken(b, INLINE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // JUMPERR expression
  public static boolean jumperr_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jumperr_directive")) return false;
    if (!nextTokenIs(b, "<directive>", JUMPERR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, JUMPERR_DIRECTIVE, "<directive>");
    r = consumeToken(b, JUMPERR);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // JUMPPTR expression
  public static boolean jumpptr_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jumpptr_directive")) return false;
    if (!nextTokenIs(b, "<directive>", JUMPPTR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, JUMPPTR_DIRECTIVE, "<directive>");
    r = consumeToken(b, JUMPPTR);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // LIST
  public static boolean list_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_directive")) return false;
    if (!nextTokenIs(b, "<directive>", LIST)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LIST_DIRECTIVE, "<directive>");
    r = consumeToken(b, LIST);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LLEN expression
  public static boolean llen_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "llen_directive")) return false;
    if (!nextTokenIs(b, "<directive>", LLEN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LLEN_DIRECTIVE, "<directive>");
    r = consumeToken(b, LLEN);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // <<afterWhitespace>> ID macro_call_parameter? (COMMA macro_call_parameter)*
  public static boolean macro_call_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MACRO_CALL_DIRECTIVE, "<directive>");
    r = afterWhitespace(b, l + 1);
    r = r && consumeToken(b, ID);
    r = r && macro_call_directive_2(b, l + 1);
    r = r && macro_call_directive_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // macro_call_parameter?
  private static boolean macro_call_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive_2")) return false;
    macro_call_parameter(b, l + 1);
    return true;
  }

  // (COMMA macro_call_parameter)*
  private static boolean macro_call_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!macro_call_directive_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "macro_call_directive_3", c)) break;
    }
    return true;
  }

  // COMMA macro_call_parameter
  private static boolean macro_call_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && macro_call_parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // adm_group_all | adm_sr | adm_ccr | adm_usp
  public static boolean macro_call_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_parameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MACRO_CALL_PARAMETER, "<macro parameter>");
    r = adm_group_all(b, l + 1);
    if (!r) r = adm_sr(b, l + 1);
    if (!r) r = adm_ccr(b, l + 1);
    if (!r) r = adm_usp(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // label MACRO
  public static boolean macro_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_directive")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MACRO_DIRECTIVE, "<directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, MACRO);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BACKSLASH macro_parameter_index_parameter
  public static boolean macro_parameter_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_parameter_directive")) return false;
    if (!nextTokenIs(b, "<directive>", BACKSLASH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MACRO_PARAMETER_DIRECTIVE, "<directive>");
    r = consumeToken(b, BACKSLASH);
    p = r; // pin = 1
    r = r && macro_parameter_index_parameter(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DEC_NUMBER
  static boolean macro_parameter_index_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_parameter_index_parameter")) return false;
    if (!nextTokenIs(b, "<index>", DEC_NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<index>");
    r = consumeToken(b, DEC_NUMBER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // MEXIT
  public static boolean mexit_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mexit_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MEXIT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MEXIT_DIRECTIVE, "<directive>");
    r = consumeToken(b, MEXIT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NOLIST
  public static boolean nolist_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nolist_directive")) return false;
    if (!nextTokenIs(b, "<directive>", NOLIST)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NOLIST_DIRECTIVE, "<directive>");
    r = consumeToken(b, NOLIST);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NOPAGE
  public static boolean nopage_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nopage_directive")) return false;
    if (!nextTokenIs(b, "<directive>", NOPAGE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NOPAGE_DIRECTIVE, "<directive>");
    r = consumeToken(b, NOPAGE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ODD
  public static boolean odd_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "odd_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ODD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ODD_DIRECTIVE, "<directive>");
    r = consumeToken(b, ODD);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // OPT opt_element (COMMA opt_element)*
  public static boolean opt_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_directive")) return false;
    if (!nextTokenIs(b, "<directive>", OPT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, OPT_DIRECTIVE, "<directive>");
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
    if (!nextTokenIs(b, "<directive>", ORG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ORG_DIRECTIVE, "<directive>");
    r = consumeToken(b, ORG);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // PAGE
  public static boolean page_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "page_directive")) return false;
    if (!nextTokenIs(b, "<directive>", PAGE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PAGE_DIRECTIVE, "<directive>");
    r = consumeToken(b, PAGE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PLEN expression
  public static boolean plen_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "plen_directive")) return false;
    if (!nextTokenIs(b, "<directive>", PLEN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PLEN_DIRECTIVE, "<directive>");
    r = consumeToken(b, PLEN);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // REM
  public static boolean rem_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rem_directive")) return false;
    if (!nextTokenIs(b, "<directive>", REM)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, REM_DIRECTIVE, "<directive>");
    r = consumeToken(b, REM);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // RS data_size_all? expression
  public static boolean rs_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rs_directive")) return false;
    if (!nextTokenIs(b, "<directive>", RS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RS_DIRECTIVE, "<directive>");
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
    if (!nextTokenIs(b, "<directive>", RSRESET)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RSRESET_DIRECTIVE, "<directive>");
    r = consumeToken(b, RSRESET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // RSSET expression
  public static boolean rsset_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rsset_directive")) return false;
    if (!nextTokenIs(b, "<directive>", RSSET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RSSET_DIRECTIVE, "<directive>");
    r = consumeToken(b, RSSET);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SECTION ID (COMMA section_type)? (COMMA ID)?
  public static boolean section_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive")) return false;
    if (!nextTokenIs(b, "<directive>", SECTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SECTION_DIRECTIVE, "<directive>");
    r = consumeTokens(b, 1, SECTION, ID);
    p = r; // pin = 1
    r = r && report_error_(b, section_directive_2(b, l + 1));
    r = p && section_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA section_type)?
  private static boolean section_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_2")) return false;
    section_directive_2_0(b, l + 1);
    return true;
  }

  // COMMA section_type
  private static boolean section_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && section_type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA ID)?
  private static boolean section_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_3")) return false;
    section_directive_3_0(b, l + 1);
    return true;
  }

  // COMMA ID
  private static boolean section_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // CODE | CODE_C | CODE_F | TEXT | DATA | DATA_C | DATA_F | BSS | BSS_C | BSS_F
  static boolean section_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_type")) return false;
    boolean r;
    r = consumeToken(b, CODE);
    if (!r) r = consumeToken(b, CODE_C);
    if (!r) r = consumeToken(b, CODE_F);
    if (!r) r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, DATA);
    if (!r) r = consumeToken(b, DATA_C);
    if (!r) r = consumeToken(b, DATA_F);
    if (!r) r = consumeToken(b, BSS);
    if (!r) r = consumeToken(b, BSS_C);
    if (!r) r = consumeToken(b, BSS_F);
    return r;
  }

  /* ********************************************************** */
  // label SET expression
  public static boolean set_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_directive")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SET_DIRECTIVE, "<directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, SET);
    p = r; // pin = 2
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SPC expression
  public static boolean spc_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "spc_directive")) return false;
    if (!nextTokenIs(b, "<directive>", SPC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SPC_DIRECTIVE, "<directive>");
    r = consumeToken(b, SPC);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TEXT
  public static boolean text_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text_directive")) return false;
    if (!nextTokenIs(b, "<directive>", TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TEXT_DIRECTIVE, "<directive>");
    r = consumeToken(b, TEXT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
