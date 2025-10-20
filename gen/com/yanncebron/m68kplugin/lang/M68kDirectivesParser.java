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
public class M68kDirectivesParser {

  /* ********************************************************** */
  // ac68080
  public static boolean ac68080_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ac68080_directive")) return false;
    if (!nextTokenIs(b, "<directive>", AC68080)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, AC_68080_DIRECTIVE, "<directive>");
    r = consumeToken(b, AC68080);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

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
  // ALIGN expression
  public static boolean align_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "align_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ALIGN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ALIGN_DIRECTIVE, "<directive>");
    r = consumeToken(b, ALIGN);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // LT macro_call_parameter_element (COMMA macro_call_parameter_element)* GT
  static boolean angled_macro_call_parameter_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "angled_macro_call_parameter_element")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LT);
    p = r; // pin = 1
    r = r && report_error_(b, macro_call_parameter_element(b, l + 1));
    r = p && report_error_(b, angled_macro_call_parameter_element_2(b, l + 1)) && r;
    r = p && consumeToken(b, GT) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA macro_call_parameter_element)*
  private static boolean angled_macro_call_parameter_element_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "angled_macro_call_parameter_element_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!angled_macro_call_parameter_element_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "angled_macro_call_parameter_element_2", c)) break;
    }
    return true;
  }

  // COMMA macro_call_parameter_element
  private static boolean angled_macro_call_parameter_element_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "angled_macro_call_parameter_element_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && macro_call_parameter_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ASSERT expression (COMMA STRING)?
  public static boolean assert_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assert_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ASSERT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ASSERT_DIRECTIVE, "<directive>");
    r = consumeToken(b, ASSERT);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && assert_directive_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA STRING)?
  private static boolean assert_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assert_directive_2")) return false;
    assert_directive_2_0(b, l + 1);
    return true;
  }

  // COMMA STRING
  private static boolean assert_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assert_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, STRING);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // AUTO
  public static boolean auto_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "auto_directive")) return false;
    if (!nextTokenIs(b, "<directive>", AUTO)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, AUTO_DIRECTIVE, "<directive>");
    r = consumeToken(b, AUTO);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BASEREG expression COMMA adm_ard
  public static boolean basereg_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "basereg_directive")) return false;
    if (!nextTokenIs(b, "<directive>", BASEREG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BASEREG_DIRECTIVE, "<directive>");
    r = consumeToken(b, BASEREG);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_ard(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BLK data_size_all? expression (COMMA expression)?
  public static boolean blk_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive")) return false;
    if (!nextTokenIs(b, "<directive>", BLK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLK_DIRECTIVE, "<directive>");
    r = consumeToken(b, BLK);
    p = r; // pin = 1
    r = r && report_error_(b, blk_directive_1(b, l + 1));
    r = p && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1)) && r;
    r = p && blk_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean blk_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // (COMMA expression)?
  private static boolean blk_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive_3")) return false;
    blk_directive_3_0(b, l + 1);
    return true;
  }

  // COMMA expression
  private static boolean blk_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
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
  // CLRFO
  public static boolean clrfo_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clrfo_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CLRFO)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CLRFO_DIRECTIVE, "<directive>");
    r = consumeToken(b, CLRFO);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // CLRSO
  public static boolean clrso_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clrso_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CLRSO)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CLRSO_DIRECTIVE, "<directive>");
    r = consumeToken(b, CLRSO);
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
  // expression
  static boolean cpID(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cpID")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<cpID>");
    r = M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // cpu32
  public static boolean cpu32_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cpu32_directive")) return false;
    if (!nextTokenIs(b, "<directive>", CPU32)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CPU_32_DIRECTIVE, "<directive>");
    r = consumeToken(b, CPU32);
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
  // dc_directive |
  //                        equ_directive |
  //                        equals_directive |
  //                        macro_call_directive |
  //                        macro_directive |
  //                        rs_directive |
  //                        include_directive |
  //                        blk_directive |
  //                        set_directive |
  //                        equr_directive |
  //                        reg_directive |
  //                        even_directive |
  //                        dcb_directive |
  //                        dr_directive |
  //                        ds_directive |
  //                        dx_directive |
  //                        align_directive |
  //                        cnop_directive |
  //                        incbin_directive |
  //                        incdir_directive |
  //                        rsset_directive |
  //                        rsreset_directive |
  //                        opt_directive |
  //                        org_directive |
  //                        endm_directive |
  //                        mexit_directive |
  //                        macro_parameter_directive |
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
  //                        erem_directive |
  //                        xdef_directive |
  //                        xref_directive |
  //                        rept_directive |
  //                        endr_directive |
  //                        printt_directive |
  //                        printv_directive |
  //                        load_directive |
  //                        fail_directive |
  //                        ttl_directive |
  //                        idnt_directive |
  //                        far_directive |
  //                        near_directive |
  //                        near_code_directive |
  //                        init_near_directive |
  //                        popsection_directive |
  //                        pushsection_directive |
  //                        odd_directive |
  //                        echo_directive |
  //                        fpu_directive |
  //                        cpu32_directive |
  //                        mc68000_directive |
  //                        mc68010_directive |
  //                        mc68020_directive |
  //                        mc68030_directive |
  //                        mc68040_directive |
  //                        mc68060_directive |
  //                        ac68080_directive |
  //                        machine_directive |
  //                        fo_directive |
  //                        clrfo_directive |
  //                        setfo_directive |
  //                        so_directive |
  //                        clrso_directive |
  //                        setso_directive |
  //                        auto_directive |
  //                        msource_directive |
  //                        offset_directive |
  //                        mask2_directive |
  //                        output_directive |
  //                        assert_directive |
  //                        basereg_directive |
  //                        endb_directive
  static boolean directives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "directives")) return false;
    boolean r;
    r = dc_directive(b, l + 1);
    if (!r) r = equ_directive(b, l + 1);
    if (!r) r = equals_directive(b, l + 1);
    if (!r) r = macro_call_directive(b, l + 1);
    if (!r) r = macro_directive(b, l + 1);
    if (!r) r = rs_directive(b, l + 1);
    if (!r) r = include_directive(b, l + 1);
    if (!r) r = blk_directive(b, l + 1);
    if (!r) r = set_directive(b, l + 1);
    if (!r) r = equr_directive(b, l + 1);
    if (!r) r = reg_directive(b, l + 1);
    if (!r) r = even_directive(b, l + 1);
    if (!r) r = dcb_directive(b, l + 1);
    if (!r) r = dr_directive(b, l + 1);
    if (!r) r = ds_directive(b, l + 1);
    if (!r) r = dx_directive(b, l + 1);
    if (!r) r = align_directive(b, l + 1);
    if (!r) r = cnop_directive(b, l + 1);
    if (!r) r = incbin_directive(b, l + 1);
    if (!r) r = incdir_directive(b, l + 1);
    if (!r) r = rsset_directive(b, l + 1);
    if (!r) r = rsreset_directive(b, l + 1);
    if (!r) r = opt_directive(b, l + 1);
    if (!r) r = org_directive(b, l + 1);
    if (!r) r = endm_directive(b, l + 1);
    if (!r) r = mexit_directive(b, l + 1);
    if (!r) r = macro_parameter_directive(b, l + 1);
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
    if (!r) r = xdef_directive(b, l + 1);
    if (!r) r = xref_directive(b, l + 1);
    if (!r) r = rept_directive(b, l + 1);
    if (!r) r = endr_directive(b, l + 1);
    if (!r) r = printt_directive(b, l + 1);
    if (!r) r = printv_directive(b, l + 1);
    if (!r) r = load_directive(b, l + 1);
    if (!r) r = fail_directive(b, l + 1);
    if (!r) r = ttl_directive(b, l + 1);
    if (!r) r = idnt_directive(b, l + 1);
    if (!r) r = far_directive(b, l + 1);
    if (!r) r = near_directive(b, l + 1);
    if (!r) r = near_code_directive(b, l + 1);
    if (!r) r = init_near_directive(b, l + 1);
    if (!r) r = popsection_directive(b, l + 1);
    if (!r) r = pushsection_directive(b, l + 1);
    if (!r) r = odd_directive(b, l + 1);
    if (!r) r = echo_directive(b, l + 1);
    if (!r) r = fpu_directive(b, l + 1);
    if (!r) r = cpu32_directive(b, l + 1);
    if (!r) r = mc68000_directive(b, l + 1);
    if (!r) r = mc68010_directive(b, l + 1);
    if (!r) r = mc68020_directive(b, l + 1);
    if (!r) r = mc68030_directive(b, l + 1);
    if (!r) r = mc68040_directive(b, l + 1);
    if (!r) r = mc68060_directive(b, l + 1);
    if (!r) r = ac68080_directive(b, l + 1);
    if (!r) r = machine_directive(b, l + 1);
    if (!r) r = fo_directive(b, l + 1);
    if (!r) r = clrfo_directive(b, l + 1);
    if (!r) r = setfo_directive(b, l + 1);
    if (!r) r = so_directive(b, l + 1);
    if (!r) r = clrso_directive(b, l + 1);
    if (!r) r = setso_directive(b, l + 1);
    if (!r) r = auto_directive(b, l + 1);
    if (!r) r = msource_directive(b, l + 1);
    if (!r) r = offset_directive(b, l + 1);
    if (!r) r = mask2_directive(b, l + 1);
    if (!r) r = output_directive(b, l + 1);
    if (!r) r = assert_directive(b, l + 1);
    if (!r) r = basereg_directive(b, l + 1);
    if (!r) r = endb_directive(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // DR data_size_all? expression (COMMA expression)*
  public static boolean dr_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dr_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DR_DIRECTIVE, "<directive>");
    r = consumeToken(b, DR);
    p = r; // pin = 1
    r = r && report_error_(b, dr_directive_1(b, l + 1));
    r = p && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1)) && r;
    r = p && dr_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean dr_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dr_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // (COMMA expression)*
  private static boolean dr_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dr_directive_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!dr_directive_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "dr_directive_3", c)) break;
    }
    return true;
  }

  // COMMA expression
  private static boolean dr_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dr_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
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
  // DX data_size_all? expression
  public static boolean dx_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dx_directive")) return false;
    if (!nextTokenIs(b, "<directive>", DX)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DX_DIRECTIVE, "<directive>");
    r = consumeToken(b, DX);
    p = r; // pin = 1
    r = r && report_error_(b, dx_directive_1(b, l + 1));
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean dx_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dx_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ECHO STRING
  public static boolean echo_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "echo_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ECHO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ECHO_DIRECTIVE, "<directive>");
    r = consumeTokens(b, 1, ECHO, STRING);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // ENDB adm_ard
  public static boolean endb_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endb_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ENDB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ENDB_DIRECTIVE, "<directive>");
    r = consumeToken(b, ENDB);
    p = r; // pin = 1
    r = r && adm_ard(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // ENDR
  public static boolean endr_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endr_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ENDR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENDR_DIRECTIVE, "<directive>");
    r = consumeToken(b, ENDR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // label EQU expression
  public static boolean equ_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equ_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID)) return false;
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
  // label EQ_DIRECTIVE expression
  public static boolean equals_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equals_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQUALS_DIRECTIVE, "<directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, EQ_DIRECTIVE);
    p = r; // pin = 2
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // label EQUR adm_rrd
  public static boolean equr_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equr_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID)) return false;
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
  // FAIL STRING?
  public static boolean fail_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fail_directive")) return false;
    if (!nextTokenIs(b, "<directive>", FAIL)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FAIL_DIRECTIVE, "<directive>");
    r = consumeToken(b, FAIL);
    r = r && fail_directive_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // STRING?
  private static boolean fail_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fail_directive_1")) return false;
    consumeToken(b, STRING);
    return true;
  }

  /* ********************************************************** */
  // FAR
  public static boolean far_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "far_directive")) return false;
    if (!nextTokenIs(b, "<directive>", FAR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FAR_DIRECTIVE, "<directive>");
    r = consumeToken(b, FAR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // label FO data_size_all? expression?
  public static boolean fo_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fo_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FO_DIRECTIVE, "<directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, FO);
    p = r; // pin = 2
    r = r && report_error_(b, fo_directive_2(b, l + 1));
    r = p && fo_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean fo_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fo_directive_2")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // expression?
  private static boolean fo_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fo_directive_3")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // fpu cpID
  public static boolean fpu_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fpu_directive")) return false;
    if (!nextTokenIs(b, "<directive>", FPU)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FPU_DIRECTIVE, "<directive>");
    r = consumeToken(b, FPU);
    p = r; // pin = 1
    r = r && cpID(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IDNT STRING
  public static boolean idnt_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "idnt_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IDNT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IDNT_DIRECTIVE, "<directive>");
    r = consumeTokens(b, 1, IDNT, STRING);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // INITNEAR
  public static boolean init_near_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "init_near_directive")) return false;
    if (!nextTokenIs(b, "<directive>", INITNEAR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, INIT_NEAR_DIRECTIVE, "<directive>");
    r = consumeToken(b, INITNEAR);
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
  // LOAD expression
  public static boolean load_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "load_directive")) return false;
    if (!nextTokenIs(b, "<directive>", LOAD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LOAD_DIRECTIVE, "<directive>");
    r = consumeToken(b, LOAD);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DEC_NUMBER | ID
  static boolean machine_cpu_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "machine_cpu_type")) return false;
    if (!nextTokenIs(b, "<cpu type>", DEC_NUMBER, ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<cpu type>");
    r = consumeToken(b, DEC_NUMBER);
    if (!r) r = consumeToken(b, ID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // machine machine_cpu_type
  public static boolean machine_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "machine_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MACHINE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MACHINE_DIRECTIVE, "<directive>");
    r = consumeToken(b, MACHINE);
    p = r; // pin = 1
    r = r && machine_cpu_type(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MACRO_CALL_ID
  //                          <<enterMacroCall>>
  //                          data_size_all?
  //                          macro_call_parameter? (COMMA macro_call_parameter)*
  //                          <<exitMacroCall>>
  public static boolean macro_call_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MACRO_CALL_ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MACRO_CALL_DIRECTIVE, "<directive>");
    r = consumeToken(b, MACRO_CALL_ID);
    r = r && enterMacroCall(b, l + 1);
    r = r && macro_call_directive_2(b, l + 1);
    r = r && macro_call_directive_3(b, l + 1);
    r = r && macro_call_directive_4(b, l + 1);
    r = r && exitMacroCall(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // data_size_all?
  private static boolean macro_call_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive_2")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // macro_call_parameter?
  private static boolean macro_call_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive_3")) return false;
    macro_call_parameter(b, l + 1);
    return true;
  }

  // (COMMA macro_call_parameter)*
  private static boolean macro_call_directive_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!macro_call_directive_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "macro_call_directive_4", c)) break;
    }
    return true;
  }

  // COMMA macro_call_parameter
  private static boolean macro_call_directive_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_directive_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && macro_call_parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // macro_call_parameter_element |
  //                          angled_macro_call_parameter_element
  public static boolean macro_call_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_parameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MACRO_CALL_PARAMETER, "<macro parameter>");
    r = macro_call_parameter_element(b, l + 1);
    if (!r) r = angled_macro_call_parameter_element(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_register_list | adm_group_all | adm_sr | adm_ccr | adm_usp | adm_group_ctrl_registers
  static boolean macro_call_parameter_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_call_parameter_element")) return false;
    boolean r;
    r = adm_register_list(b, l + 1);
    if (!r) r = adm_group_all(b, l + 1);
    if (!r) r = adm_sr(b, l + 1);
    if (!r) r = adm_ccr(b, l + 1);
    if (!r) r = adm_usp(b, l + 1);
    if (!r) r = adm_group_ctrl_registers(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (label MACRO) | (MACRO label)
  public static boolean macro_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID, MACRO)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MACRO_DIRECTIVE, "<directive>");
    r = macro_directive_0(b, l + 1);
    if (!r) r = macro_directive_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // label MACRO
  private static boolean macro_directive_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_directive_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = label(b, l + 1);
    r = r && consumeToken(b, MACRO);
    exit_section_(b, m, null, r);
    return r;
  }

  // MACRO label
  private static boolean macro_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_directive_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MACRO);
    r = r && label(b, l + 1);
    exit_section_(b, m, null, r);
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
  // DEC_NUMBER | ID
  static boolean macro_parameter_index_parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_parameter_index_parameter")) return false;
    if (!nextTokenIs(b, "<index>", DEC_NUMBER, ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<index>");
    r = consumeToken(b, DEC_NUMBER);
    if (!r) r = consumeToken(b, ID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // MASK2
  public static boolean mask2_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mask2_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MASK2)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MASK_2_DIRECTIVE, "<directive>");
    r = consumeToken(b, MASK2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mc68000
  public static boolean mc68000_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mc68000_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MC68000)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MC_68000_DIRECTIVE, "<directive>");
    r = consumeToken(b, MC68000);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mc68010
  public static boolean mc68010_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mc68010_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MC68010)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MC_68010_DIRECTIVE, "<directive>");
    r = consumeToken(b, MC68010);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mc68020
  public static boolean mc68020_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mc68020_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MC68020)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MC_68020_DIRECTIVE, "<directive>");
    r = consumeToken(b, MC68020);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mc68030
  public static boolean mc68030_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mc68030_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MC68030)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MC_68030_DIRECTIVE, "<directive>");
    r = consumeToken(b, MC68030);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mc68040
  public static boolean mc68040_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mc68040_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MC68040)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MC_68040_DIRECTIVE, "<directive>");
    r = consumeToken(b, MC68040);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // mc68060
  public static boolean mc68060_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mc68060_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MC68060)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MC_68060_DIRECTIVE, "<directive>");
    r = consumeToken(b, MC68060);
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
  // MSOURCE msource_param
  public static boolean msource_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "msource_directive")) return false;
    if (!nextTokenIs(b, "<directive>", MSOURCE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MSOURCE_DIRECTIVE, "<directive>");
    r = consumeToken(b, MSOURCE);
    p = r; // pin = 1
    r = r && msource_param(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // "on" | "off" | "ON" | "OFF"
  static boolean msource_param(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "msource_param")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<on/off>");
    r = consumeToken(b, "on");
    if (!r) r = consumeToken(b, "off");
    if (!r) r = consumeToken(b, "ON");
    if (!r) r = consumeToken(b, "OFF");
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NEAR_CODE
  public static boolean near_code_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "near_code_directive")) return false;
    if (!nextTokenIs(b, "<directive>", NEAR_CODE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NEAR_CODE_DIRECTIVE, "<directive>");
    r = consumeToken(b, NEAR_CODE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NEAR adm_ard?
  public static boolean near_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "near_directive")) return false;
    if (!nextTokenIs(b, "<directive>", NEAR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NEAR_DIRECTIVE, "<directive>");
    r = consumeToken(b, NEAR);
    r = r && near_directive_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // adm_ard?
  private static boolean near_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "near_directive_1")) return false;
    adm_ard(b, l + 1);
    return true;
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
  // OFFSET expression?
  public static boolean offset_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "offset_directive")) return false;
    if (!nextTokenIs(b, "<directive>", OFFSET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, OFFSET_DIRECTIVE, "<directive>");
    r = consumeToken(b, OFFSET);
    p = r; // pin = 1
    r = r && offset_directive_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // expression?
  private static boolean offset_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "offset_directive_1")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
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
  // (ID EQ DEC_NUMBER (DIV DEC_NUMBER)?) |
  //                         (ID (PLUS | MINUS)?)
  static boolean opt_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element")) return false;
    if (!nextTokenIs(b, "<option>", ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<option>");
    r = opt_element_0(b, l + 1);
    if (!r) r = opt_element_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ID EQ DEC_NUMBER (DIV DEC_NUMBER)?
  private static boolean opt_element_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, EQ, DEC_NUMBER);
    r = r && opt_element_0_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (DIV DEC_NUMBER)?
  private static boolean opt_element_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element_0_3")) return false;
    opt_element_0_3_0(b, l + 1);
    return true;
  }

  // DIV DEC_NUMBER
  private static boolean opt_element_0_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element_0_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DIV, DEC_NUMBER);
    exit_section_(b, m, null, r);
    return r;
  }

  // ID (PLUS | MINUS)?
  private static boolean opt_element_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    r = r && opt_element_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (PLUS | MINUS)?
  private static boolean opt_element_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element_1_1")) return false;
    opt_element_1_1_0(b, l + 1);
    return true;
  }

  // PLUS | MINUS
  private static boolean opt_element_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "opt_element_1_1_0")) return false;
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
  // OUTPUT STRING
  public static boolean output_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "output_directive")) return false;
    if (!nextTokenIs(b, "<directive>", OUTPUT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, OUTPUT_DIRECTIVE, "<directive>");
    r = consumeTokens(b, 1, OUTPUT, STRING);
    p = r; // pin = 1
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
  // POPSECTION
  public static boolean popsection_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "popsection_directive")) return false;
    if (!nextTokenIs(b, "<directive>", POPSECTION)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, POPSECTION_DIRECTIVE, "<directive>");
    r = consumeToken(b, POPSECTION);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // PRINTT STRING?
  public static boolean printt_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "printt_directive")) return false;
    if (!nextTokenIs(b, "<directive>", PRINTT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PRINTT_DIRECTIVE, "<directive>");
    r = consumeToken(b, PRINTT);
    p = r; // pin = 1
    r = r && printt_directive_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // STRING?
  private static boolean printt_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "printt_directive_1")) return false;
    consumeToken(b, STRING);
    return true;
  }

  /* ********************************************************** */
  // PRINTV expression (COMMA expression)*
  public static boolean printv_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "printv_directive")) return false;
    if (!nextTokenIs(b, "<directive>", PRINTV)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PRINTV_DIRECTIVE, "<directive>");
    r = consumeToken(b, PRINTV);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && printv_directive_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA expression)*
  private static boolean printv_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "printv_directive_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!printv_directive_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "printv_directive_2", c)) break;
    }
    return true;
  }

  // COMMA expression
  private static boolean printv_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "printv_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PUSHSECTION
  public static boolean pushsection_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pushsection_directive")) return false;
    if (!nextTokenIs(b, "<directive>", PUSHSECTION)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PUSHSECTION_DIRECTIVE, "<directive>");
    r = consumeToken(b, PUSHSECTION);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // label REG (adm_register_list | adm_imm)
  public static boolean reg_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reg_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REG_DIRECTIVE, "<directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, REG);
    p = r; // pin = 2
    r = r && reg_directive_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // adm_register_list | adm_imm
  private static boolean reg_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reg_directive_2")) return false;
    boolean r;
    r = adm_register_list(b, l + 1);
    if (!r) r = adm_imm(b, l + 1);
    return r;
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
  // REPT expression
  public static boolean rept_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rept_directive")) return false;
    if (!nextTokenIs(b, "<directive>", REPT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REPT_DIRECTIVE, "<directive>");
    r = consumeToken(b, REPT);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // RS data_size_all? expression?
  public static boolean rs_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rs_directive")) return false;
    if (!nextTokenIs(b, "<directive>", RS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RS_DIRECTIVE, "<directive>");
    r = consumeToken(b, RS);
    p = r; // pin = 1
    r = r && report_error_(b, rs_directive_1(b, l + 1));
    r = p && rs_directive_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean rs_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rs_directive_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // expression?
  private static boolean rs_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rs_directive_2")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
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
  // SECTION (ID | STRING) (COMMA ID)? (COMMA (ID | number_expression) )?
  public static boolean section_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive")) return false;
    if (!nextTokenIs(b, "<directive>", SECTION)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SECTION_DIRECTIVE, "<directive>");
    r = consumeToken(b, SECTION);
    p = r; // pin = 1
    r = r && report_error_(b, section_directive_1(b, l + 1));
    r = p && report_error_(b, section_directive_2(b, l + 1)) && r;
    r = p && section_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ID | STRING
  private static boolean section_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_1")) return false;
    boolean r;
    r = consumeToken(b, ID);
    if (!r) r = consumeToken(b, STRING);
    return r;
  }

  // (COMMA ID)?
  private static boolean section_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_2")) return false;
    section_directive_2_0(b, l + 1);
    return true;
  }

  // COMMA ID
  private static boolean section_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA (ID | number_expression) )?
  private static boolean section_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_3")) return false;
    section_directive_3_0(b, l + 1);
    return true;
  }

  // COMMA (ID | number_expression)
  private static boolean section_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && section_directive_3_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ID | number_expression
  private static boolean section_directive_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_directive_3_0_1")) return false;
    boolean r;
    r = consumeToken(b, ID);
    if (!r) r = M68kExpressionParser.number_expression(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // label SET expression
  public static boolean set_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID)) return false;
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
  // SETFO expression
  public static boolean setfo_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "setfo_directive")) return false;
    if (!nextTokenIs(b, "<directive>", SETFO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SETFO_DIRECTIVE, "<directive>");
    r = consumeToken(b, SETFO);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SETSO expression
  public static boolean setso_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "setso_directive")) return false;
    if (!nextTokenIs(b, "<directive>", SETSO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SETSO_DIRECTIVE, "<directive>");
    r = consumeToken(b, SETSO);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // label SO data_size_all? expression?
  public static boolean so_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "so_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SO_DIRECTIVE, "<directive>");
    r = label(b, l + 1);
    r = r && consumeToken(b, SO);
    p = r; // pin = 2
    r = r && report_error_(b, so_directive_2(b, l + 1));
    r = p && so_directive_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean so_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "so_directive_2")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // expression?
  private static boolean so_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "so_directive_3")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
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

  /* ********************************************************** */
  // TTL STRING
  public static boolean ttl_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ttl_directive")) return false;
    if (!nextTokenIs(b, "<directive>", TTL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TTL_DIRECTIVE, "<directive>");
    r = consumeTokens(b, 1, TTL, STRING);
    p = r; // pin = 1
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // XDEF label_ref_expression (COMMA label_ref_expression)*
  public static boolean xdef_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xdef_directive")) return false;
    if (!nextTokenIs(b, "<directive>", XDEF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, XDEF_DIRECTIVE, "<directive>");
    r = consumeToken(b, XDEF);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.label_ref_expression(b, l + 1));
    r = p && xdef_directive_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA label_ref_expression)*
  private static boolean xdef_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xdef_directive_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!xdef_directive_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "xdef_directive_2", c)) break;
    }
    return true;
  }

  // COMMA label_ref_expression
  private static boolean xdef_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xdef_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.label_ref_expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // XREF label_ref_expression (COMMA label_ref_expression)*
  public static boolean xref_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xref_directive")) return false;
    if (!nextTokenIs(b, "<directive>", XREF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, XREF_DIRECTIVE, "<directive>");
    r = consumeToken(b, XREF);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.label_ref_expression(b, l + 1));
    r = p && xref_directive_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA label_ref_expression)*
  private static boolean xref_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xref_directive_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!xref_directive_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "xref_directive_2", c)) break;
    }
    return true;
  }

  // COMMA label_ref_expression
  private static boolean xref_directive_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xref_directive_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && M68kExpressionParser.label_ref_expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
