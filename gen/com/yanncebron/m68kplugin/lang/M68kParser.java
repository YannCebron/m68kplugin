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
package com.yanncebron.m68kplugin.lang;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.yanncebron.m68kplugin.lang.psi.M68kTypes.*;
import static com.yanncebron.m68kplugin.lang.M68kParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class M68kParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(EQUALS_DIRECTIVE, EQU_DIRECTIVE, SET_DIRECTIVE),
    create_token_set_(AND_EXPRESSION, DIV_EXPRESSION, EXPRESSION, EXP_EXPRESSION,
      LABEL_REF_EXPRESSION, MINUS_EXPRESSION, MOD_EXPRESSION, MUL_EXPRESSION,
      NOT_EXPRESSION, NUMBER_EXPRESSION, OR_EXPRESSION, PAREN_EXPRESSION,
      PLUS_EXPRESSION, SHIFT_LEFT_EXPRESSION, SHIFT_RIGHT_EXPRESSION, STRING_EXPRESSION,
      UNARY_COMPLEMENT_EXPRESSION, UNARY_MINUS_EXPRESSION, UNARY_PLUS_EXPRESSION),
    create_token_set_(ABCD_INSTRUCTION, ADDA_INSTRUCTION, ADDI_INSTRUCTION, ADDQ_INSTRUCTION,
      ADDX_INSTRUCTION, ADD_INSTRUCTION, ADM_ABS, ADM_RRD_INDEX,
      ANDI_INSTRUCTION, AND_INSTRUCTION, ASL_INSTRUCTION, ASR_INSTRUCTION,
      BCC_INSTRUCTION, BCHG_INSTRUCTION, BCLR_INSTRUCTION, BCS_INSTRUCTION,
      BEQ_INSTRUCTION, BGE_INSTRUCTION, BGT_INSTRUCTION, BHI_INSTRUCTION,
      BHS_INSTRUCTION, BLE_INSTRUCTION, BLK_DIRECTIVE, BLO_INSTRUCTION,
      BLS_INSTRUCTION, BLT_INSTRUCTION, BMI_INSTRUCTION, BNE_INSTRUCTION,
      BPL_INSTRUCTION, BRA_INSTRUCTION, BSET_INSTRUCTION, BSR_INSTRUCTION,
      BTST_INSTRUCTION, BVC_INSTRUCTION, BVS_INSTRUCTION, CHK_INSTRUCTION,
      CLR_INSTRUCTION, CMPA_INSTRUCTION, CMPI_INSTRUCTION, CMPM_INSTRUCTION,
      CMP_INSTRUCTION, DBCC_INSTRUCTION, DBCS_INSTRUCTION, DBEQ_INSTRUCTION,
      DBF_INSTRUCTION, DBGE_INSTRUCTION, DBGT_INSTRUCTION, DBHI_INSTRUCTION,
      DBHS_INSTRUCTION, DBLE_INSTRUCTION, DBLO_INSTRUCTION, DBLS_INSTRUCTION,
      DBLT_INSTRUCTION, DBMI_INSTRUCTION, DBNE_INSTRUCTION, DBPL_INSTRUCTION,
      DBRA_INSTRUCTION, DBT_INSTRUCTION, DBVC_INSTRUCTION, DBVS_INSTRUCTION,
      DCB_DIRECTIVE, DC_DIRECTIVE, DIVS_INSTRUCTION, DIVU_INSTRUCTION,
      DR_DIRECTIVE, DS_DIRECTIVE, EORI_INSTRUCTION, EOR_INSTRUCTION,
      EXG_INSTRUCTION, EXT_INSTRUCTION, LEA_INSTRUCTION, LINK_INSTRUCTION,
      LSL_INSTRUCTION, LSR_INSTRUCTION, MOVEA_INSTRUCTION, MOVEM_INSTRUCTION,
      MOVEP_INSTRUCTION, MOVEQ_INSTRUCTION, MOVE_INSTRUCTION, MULS_INSTRUCTION,
      MULU_INSTRUCTION, NBCD_INSTRUCTION, NEGX_INSTRUCTION, NEG_INSTRUCTION,
      NOT_INSTRUCTION, ORI_INSTRUCTION, OR_INSTRUCTION, PEA_INSTRUCTION,
      ROL_INSTRUCTION, ROR_INSTRUCTION, ROXL_INSTRUCTION, ROXR_INSTRUCTION,
      RS_DIRECTIVE, SBCD_INSTRUCTION, SCC_INSTRUCTION, SCS_INSTRUCTION,
      SEQ_INSTRUCTION, SF_INSTRUCTION, SGE_INSTRUCTION, SGT_INSTRUCTION,
      SHI_INSTRUCTION, SHS_INSTRUCTION, SLE_INSTRUCTION, SLO_INSTRUCTION,
      SLS_INSTRUCTION, SLT_INSTRUCTION, SMI_INSTRUCTION, SNE_INSTRUCTION,
      SPL_INSTRUCTION, ST_INSTRUCTION, SUBA_INSTRUCTION, SUBI_INSTRUCTION,
      SUBQ_INSTRUCTION, SUBX_INSTRUCTION, SUB_INSTRUCTION, SVC_INSTRUCTION,
      SVS_INSTRUCTION, SWAP_INSTRUCTION, TAS_INSTRUCTION, TST_INSTRUCTION),
  };

  /* ********************************************************** */
  // data_size_all | data_size_short
  static boolean CC_data_size(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CC_data_size")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.s|b|w|l>");
    r = data_size_all(b, l + 1);
    if (!r) r = data_size_short(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ABCD bcd_tail
  public static boolean abcd_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "abcd_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ABCD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ABCD_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ABCD);
    p = r; // pin = 1
    r = r && bcd_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ADD tail_data_size_all___all__all_except_pc_imm
  public static boolean add_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADD_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADD);
    p = r; // pin = 1
    r = r && tail_data_size_all___all__all_except_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // add_instruction |
  //                                  adda_instruction |
  //                                  addi_instruction |
  //                                  addq_instruction |
  //                                  addx_instruction |
  //                                  sub_instruction |
  //                                  suba_instruction |
  //                                  subi_instruction |
  //                                  subq_instruction |
  //                                  subx_instruction
  static boolean add_sub_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_instructions")) return false;
    boolean r;
    r = add_instruction(b, l + 1);
    if (!r) r = adda_instruction(b, l + 1);
    if (!r) r = addi_instruction(b, l + 1);
    if (!r) r = addq_instruction(b, l + 1);
    if (!r) r = addx_instruction(b, l + 1);
    if (!r) r = sub_instruction(b, l + 1);
    if (!r) r = suba_instruction(b, l + 1);
    if (!r) r = subi_instruction(b, l + 1);
    if (!r) r = subq_instruction(b, l + 1);
    if (!r) r = subx_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_all? adm_imm COMMA adm_group_all_except_pc_imm
  static boolean add_sub_q_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_q_tail")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = add_sub_q_tail_0(b, l + 1);
    r = r && adm_imm(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_group_all_except_pc_imm(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean add_sub_q_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_q_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_all?
  //                            (
  //                              add_sub_x_tail_drd_drd |
  //                              add_sub_x_tail_apd_apd
  //                            )
  static boolean add_sub_x_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_x_tail_0(b, l + 1);
    r = r && add_sub_x_tail_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean add_sub_x_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // add_sub_x_tail_drd_drd |
  //                              add_sub_x_tail_apd_apd
  private static boolean add_sub_x_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail_1")) return false;
    boolean r;
    r = add_sub_x_tail_drd_drd(b, l + 1);
    if (!r) r = add_sub_x_tail_apd_apd(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // adm_apd COMMA adm_apd
  static boolean add_sub_x_tail_apd_apd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail_apd_apd")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_apd(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_apd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // adm_drd COMMA adm_drd
  static boolean add_sub_x_tail_drd_drd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail_drd_drd")) return false;
    if (!nextTokenIs(b, DATA_REGISTER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_drd(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ADDA tail_data_size_word_long___all__ard
  public static boolean adda_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adda_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADDA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADDA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADDA);
    p = r; // pin = 1
    r = r && tail_data_size_word_long___all__ard(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ADDI tail_data_size_all___imm__all_except_ard_pc_imm
  public static boolean addi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADDI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADDI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADDI);
    p = r; // pin = 1
    r = r && tail_data_size_all___imm__all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ADDQ add_sub_q_tail
  public static boolean addq_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addq_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADDQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADDQ_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADDQ);
    p = r; // pin = 1
    r = r && add_sub_q_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ADDX add_sub_x_tail
  public static boolean addx_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addx_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADDX)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADDX_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADDX);
    p = r; // pin = 1
    r = r && add_sub_x_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // expression data_size_word_long?
  public static boolean adm_abs(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_abs")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_ABS, "<adm abs>");
    r = M68kExpressionParser.expression(b, l + 1, -1);
    r = r && adm_abs_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // data_size_word_long?
  private static boolean adm_abs_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_abs_1")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // expression L_PAREN adm_ard R_PAREN
  public static boolean adm_adi(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_adi")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_ADI, "<adm adi>");
    r = M68kExpressionParser.expression(b, l + 1, -1);
    r = r && consumeToken(b, L_PAREN);
    r = r && adm_ard(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // expression? L_PAREN adm_ard COMMA adm_rrd_index R_PAREN
  public static boolean adm_aix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_AIX, "<expression>");
    r = adm_aix_0(b, l + 1);
    r = r && consumeToken(b, L_PAREN);
    r = r && adm_ard(b, l + 1);
    r = r && consumeToken(b, COMMA);
    p = r; // pin = COMMA
    r = r && report_error_(b, adm_rrd_index(b, l + 1));
    r = p && consumeToken(b, R_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // expression?
  private static boolean adm_aix_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix_0")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // MINUS L_PAREN adm_ard R_PAREN
  public static boolean adm_apd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_apd")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_APD, null);
    r = consumeTokens(b, 0, MINUS, L_PAREN);
    r = r && adm_ard(b, l + 1);
    p = r; // pin = 3
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // L_PAREN adm_ard R_PAREN PLUS
  public static boolean adm_api(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_api")) return false;
    if (!nextTokenIs(b, L_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_PAREN);
    r = r && adm_ard(b, l + 1);
    r = r && consumeTokens(b, 0, R_PAREN, PLUS);
    exit_section_(b, m, ADM_API, r);
    return r;
  }

  /* ********************************************************** */
  // ADDRESS_REGISTER | SP
  public static boolean adm_ard(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_ard")) return false;
    if (!nextTokenIs(b, "<address register>", ADDRESS_REGISTER, SP)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_ARD, "<address register>");
    r = consumeToken(b, ADDRESS_REGISTER);
    if (!r) r = consumeToken(b, SP);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // L_PAREN adm_ard R_PAREN
  public static boolean adm_ari(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_ari")) return false;
    if (!nextTokenIs(b, L_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_PAREN);
    r = r && adm_ard(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, m, ADM_ARI, r);
    return r;
  }

  /* ********************************************************** */
  // CCR
  public static boolean adm_ccr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_ccr")) return false;
    if (!nextTokenIs(b, CCR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CCR);
    exit_section_(b, m, ADM_CCR, r);
    return r;
  }

  /* ********************************************************** */
  // DATA_REGISTER
  public static boolean adm_drd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_drd")) return false;
    if (!nextTokenIs(b, "<data register>", DATA_REGISTER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_DRD, "<data register>");
    r = consumeToken(b, DATA_REGISTER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_drd | adm_ard | adm_imm | adm_api | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean adm_group_all(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_group_all")) return false;
    boolean r;
    r = adm_drd(b, l + 1);
    if (!r) r = adm_ard(b, l + 1);
    if (!r) r = adm_imm(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // adm_drd |           adm_imm | adm_api | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean adm_group_all_except_ard(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_group_all_except_ard")) return false;
    boolean r;
    r = adm_drd(b, l + 1);
    if (!r) r = adm_imm(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // adm_drd |                     adm_api | adm_ari | adm_apd | adm_adi | adm_aix | adm_abs
  static boolean adm_group_all_except_ard_pc_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_group_all_except_ard_pc_imm")) return false;
    boolean r;
    r = adm_drd(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // adm_drd | adm_ard |           adm_api | adm_ari | adm_apd | adm_adi | adm_aix | adm_abs
  static boolean adm_group_all_except_pc_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_group_all_except_pc_imm")) return false;
    boolean r;
    r = adm_drd(b, l + 1);
    if (!r) r = adm_ard(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // HASH expression data_size_word_long?
  public static boolean adm_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_imm")) return false;
    if (!nextTokenIs(b, "<immediate data>", HASH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_IMM, "<immediate data>");
    r = consumeToken(b, HASH);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && adm_imm_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word_long?
  private static boolean adm_imm_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_imm_2")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // expression L_PAREN PC R_PAREN
  public static boolean adm_pcd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pcd")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_PCD, "<adm pcd>");
    r = M68kExpressionParser.expression(b, l + 1, -1);
    r = r && consumeTokens(b, 0, L_PAREN, PC, R_PAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // expression? L_PAREN PC COMMA adm_rrd_index R_PAREN
  public static boolean adm_pci(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_PCI, "<expression>");
    r = adm_pci_0(b, l + 1);
    r = r && consumeTokens(b, 3, L_PAREN, PC, COMMA);
    p = r; // pin = COMMA
    r = r && report_error_(b, adm_rrd_index(b, l + 1));
    r = p && consumeToken(b, R_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // expression?
  private static boolean adm_pci_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci_0")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // adm_drd | adm_ard
  public static boolean adm_rrd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_rrd")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_RRD, "<data|address register>");
    r = adm_drd(b, l + 1);
    if (!r) r = adm_ard(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (adm_drd data_size_all?) | (adm_ard data_size_word_long?)
  public static boolean adm_rrd_index(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_rrd_index")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_RRD_INDEX, "<data|address register>");
    r = adm_rrd_index_0(b, l + 1);
    if (!r) r = adm_rrd_index_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // adm_drd data_size_all?
  private static boolean adm_rrd_index_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_rrd_index_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = adm_drd(b, l + 1);
    r = r && adm_rrd_index_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean adm_rrd_index_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_rrd_index_0_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // adm_ard data_size_word_long?
  private static boolean adm_rrd_index_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_rrd_index_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = adm_ard(b, l + 1);
    r = r && adm_rrd_index_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_word_long?
  private static boolean adm_rrd_index_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_rrd_index_1_1")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // SR
  public static boolean adm_sr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_sr")) return false;
    if (!nextTokenIs(b, SR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SR);
    exit_section_(b, m, ADM_SR, r);
    return r;
  }

  /* ********************************************************** */
  // USP
  public static boolean adm_usp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_usp")) return false;
    if (!nextTokenIs(b, USP)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, USP);
    exit_section_(b, m, ADM_USP, r);
    return r;
  }

  /* ********************************************************** */
  // AND bool_tail
  public static boolean and_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", AND)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, AND_INSTRUCTION, "<instruction>");
    r = consumeToken(b, AND);
    p = r; // pin = 1
    r = r && bool_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ANDI bool_i_tail
  public static boolean andi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "andi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ANDI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ANDI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ANDI);
    p = r; // pin = 1
    r = r && bool_i_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ASL shift_tail
  public static boolean asl_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asl_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ASL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ASL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ASL);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ASR shift_tail
  public static boolean asr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "asr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ASR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ASR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ASR);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // bra_instruction |
  //                              bcs_instruction |
  //                              blo_instruction |
  //                              bls_instruction |
  //                              beq_instruction |
  //                              bne_instruction |
  //                              bhi_instruction |
  //                              bcc_instruction |
  //                              bhs_instruction |
  //                              bpl_instruction |
  //                              bvc_instruction |
  //                              blt_instruction |
  //                              ble_instruction |
  //                              bgt_instruction |
  //                              bge_instruction |
  //                              bmi_instruction |
  //                              bvs_instruction
  static boolean bCC_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bCC_instructions")) return false;
    boolean r;
    r = bra_instruction(b, l + 1);
    if (!r) r = bcs_instruction(b, l + 1);
    if (!r) r = blo_instruction(b, l + 1);
    if (!r) r = bls_instruction(b, l + 1);
    if (!r) r = beq_instruction(b, l + 1);
    if (!r) r = bne_instruction(b, l + 1);
    if (!r) r = bhi_instruction(b, l + 1);
    if (!r) r = bcc_instruction(b, l + 1);
    if (!r) r = bhs_instruction(b, l + 1);
    if (!r) r = bpl_instruction(b, l + 1);
    if (!r) r = bvc_instruction(b, l + 1);
    if (!r) r = blt_instruction(b, l + 1);
    if (!r) r = ble_instruction(b, l + 1);
    if (!r) r = bgt_instruction(b, l + 1);
    if (!r) r = bge_instruction(b, l + 1);
    if (!r) r = bmi_instruction(b, l + 1);
    if (!r) r = bvs_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // CC_data_size? expression
  static boolean bCC_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bCC_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bCC_tail_0(b, l + 1);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CC_data_size?
  private static boolean bCC_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bCC_tail_0")) return false;
    CC_data_size(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // BCC bCC_tail
  public static boolean bcc_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcc_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BCC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BCC_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BCC);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // abcd_instruction |
  //                              nbcd_instruction |
  //                              sbcd_instruction
  static boolean bcd_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_instructions")) return false;
    boolean r;
    r = abcd_instruction(b, l + 1);
    if (!r) r = nbcd_instruction(b, l + 1);
    if (!r) r = sbcd_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_byte?
  //                      (
  //                        bcd_tail_drd | bcd_tail_apd
  //                      )
  static boolean bcd_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bcd_tail_0(b, l + 1);
    r = r && bcd_tail_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_byte?
  private static boolean bcd_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail_0")) return false;
    data_size_byte(b, l + 1);
    return true;
  }

  // bcd_tail_drd | bcd_tail_apd
  private static boolean bcd_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail_1")) return false;
    boolean r;
    r = bcd_tail_drd(b, l + 1);
    if (!r) r = bcd_tail_apd(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // adm_apd COMMA adm_apd
  static boolean bcd_tail_apd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail_apd")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_apd(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_apd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // adm_drd COMMA adm_drd
  static boolean bcd_tail_drd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail_drd")) return false;
    if (!nextTokenIs(b, DATA_REGISTER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_drd(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BCHG bit_tail
  public static boolean bchg_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bchg_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BCHG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BCHG_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BCHG);
    p = r; // pin = 1
    r = r && bit_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BCLR bit_tail
  public static boolean bclr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bclr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BCLR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BCLR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BCLR);
    p = r; // pin = 1
    r = r && bit_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BCS bCC_tail
  public static boolean bcs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BCS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BCS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BCS);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BEQ bCC_tail
  public static boolean beq_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "beq_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BEQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BEQ_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BEQ);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BGE bCC_tail
  public static boolean bge_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bge_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BGE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BGE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BGE);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BGT bCC_tail
  public static boolean bgt_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bgt_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BGT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BGT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BGT);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BHI bCC_tail
  public static boolean bhi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bhi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BHI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BHI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BHI);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BHS bCC_tail
  public static boolean bhs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bhs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BHS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BHS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BHS);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // data_size_byte | data_size_long
  static boolean bit_data_size(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_data_size")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.b|l>");
    r = data_size_byte(b, l + 1);
    if (!r) r = data_size_long(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // bchg_instruction |
  //                                           bclr_instruction |
  //                                           bset_instruction |
  //                                           btst_instruction
  static boolean bit_manipulation_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_manipulation_instructions")) return false;
    boolean r;
    r = bchg_instruction(b, l + 1);
    if (!r) r = bclr_instruction(b, l + 1);
    if (!r) r = bset_instruction(b, l + 1);
    if (!r) r = btst_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // bit_data_size?
  //                      (adm_drd | adm_imm) COMMA adm_group_all_except_ard_pc_imm
  static boolean bit_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bit_tail_0(b, l + 1);
    r = r && bit_tail_1(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && adm_group_all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // bit_data_size?
  private static boolean bit_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_tail_0")) return false;
    bit_data_size(b, l + 1);
    return true;
  }

  // adm_drd | adm_imm
  private static boolean bit_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_tail_1")) return false;
    boolean r;
    r = adm_drd(b, l + 1);
    if (!r) r = adm_imm(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // BLE bCC_tail
  public static boolean ble_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ble_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BLE);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BLO bCC_tail
  public static boolean blo_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blo_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BLO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLO_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BLO);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BLS bCC_tail
  public static boolean bls_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bls_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BLS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BLS);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BLT bCC_tail
  public static boolean blt_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blt_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BLT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BLT);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BMI bCC_tail
  public static boolean bmi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bmi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BMI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BMI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BMI);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BNE bCC_tail
  public static boolean bne_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bne_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BNE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BNE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BNE);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // bool_i_tail_imm_ccr |
  //                           bool_i_tail_imm_sr |
  //                           tail_data_size_all___imm__all_except_ard_pc_imm
  static boolean bool_i_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail")) return false;
    boolean r;
    r = bool_i_tail_imm_ccr(b, l + 1);
    if (!r) r = bool_i_tail_imm_sr(b, l + 1);
    if (!r) r = tail_data_size_all___imm__all_except_ard_pc_imm(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_byte? adm_imm COMMA adm_ccr
  static boolean bool_i_tail_imm_ccr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail_imm_ccr")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bool_i_tail_imm_ccr_0(b, l + 1);
    r = r && adm_imm(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && adm_ccr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_byte?
  private static boolean bool_i_tail_imm_ccr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail_imm_ccr_0")) return false;
    data_size_byte(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_word? adm_imm COMMA adm_sr
  static boolean bool_i_tail_imm_sr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail_imm_sr")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bool_i_tail_imm_sr_0(b, l + 1);
    r = r && adm_imm(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && adm_sr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_word?
  private static boolean bool_i_tail_imm_sr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail_imm_sr_0")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_all?
  //                       adm_group_all_except_ard COMMA (adm_group_all_except_ard_pc_imm | adm_ccr | adm_sr)
  static boolean bool_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bool_tail_0(b, l + 1);
    r = r && adm_group_all_except_ard(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && bool_tail_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean bool_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // adm_group_all_except_ard_pc_imm | adm_ccr | adm_sr
  private static boolean bool_tail_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_tail_3")) return false;
    boolean r;
    r = adm_group_all_except_ard_pc_imm(b, l + 1);
    if (!r) r = adm_ccr(b, l + 1);
    if (!r) r = adm_sr(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // BPL bCC_tail
  public static boolean bpl_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bpl_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BPL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BPL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BPL);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BRA bCC_tail
  public static boolean bra_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bra_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BRA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BRA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BRA);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BSET bit_tail
  public static boolean bset_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bset_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BSET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BSET_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BSET);
    p = r; // pin = 1
    r = r && bit_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // data_size_short | data_size_byte | data_size_word
  static boolean bsr_data_size(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bsr_data_size")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.s|b|w>");
    r = data_size_short(b, l + 1);
    if (!r) r = data_size_byte(b, l + 1);
    if (!r) r = data_size_word(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BSR bsr_data_size? expression
  public static boolean bsr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bsr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BSR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BSR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BSR);
    p = r; // pin = 1
    r = r && report_error_(b, bsr_instruction_1(b, l + 1));
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // bsr_data_size?
  private static boolean bsr_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bsr_instruction_1")) return false;
    bsr_data_size(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // BTST bit_data_size?
  //                      (adm_drd | adm_imm) COMMA adm_group_all_except_ard
  public static boolean btst_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "btst_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BTST)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BTST_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BTST);
    p = r; // pin = 1
    r = r && report_error_(b, btst_instruction_1(b, l + 1));
    r = p && report_error_(b, btst_instruction_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_group_all_except_ard(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // bit_data_size?
  private static boolean btst_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "btst_instruction_1")) return false;
    bit_data_size(b, l + 1);
    return true;
  }

  // adm_drd | adm_imm
  private static boolean btst_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "btst_instruction_2")) return false;
    boolean r;
    r = adm_drd(b, l + 1);
    if (!r) r = adm_imm(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // BVC bCC_tail
  public static boolean bvc_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bvc_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BVC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BVC_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BVC);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // BVS bCC_tail
  public static boolean bvs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bvs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BVS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BVS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BVS);
    p = r; // pin = 1
    r = r && bCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // CHK data_size_word? adm_group_all_except_ard COMMA adm_drd
  public static boolean chk_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chk_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CHK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CHK_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CHK);
    p = r; // pin = 1
    r = r && report_error_(b, chk_instruction_1(b, l + 1));
    r = p && report_error_(b, adm_group_all_except_ard(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word?
  private static boolean chk_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chk_instruction_1")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // CLR tail_data_size_all___all_except_ard_pc_imm
  public static boolean clr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CLR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CLR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CLR);
    p = r; // pin = 1
    r = r && tail_data_size_all___all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // CMP tail_data_size_all___all__all_except_pc_imm
  public static boolean cmp_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmp_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMP);
    p = r; // pin = 1
    r = r && tail_data_size_all___all__all_except_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // cmp_instruction |
  //                              cmpa_instruction |
  //                              cmpi_instruction |
  //                              cmpm_instruction
  static boolean cmp_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmp_instructions")) return false;
    boolean r;
    r = cmp_instruction(b, l + 1);
    if (!r) r = cmpa_instruction(b, l + 1);
    if (!r) r = cmpi_instruction(b, l + 1);
    if (!r) r = cmpm_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // CMPA tail_data_size_word_long___all__ard
  public static boolean cmpa_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpa_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMPA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMPA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMPA);
    p = r; // pin = 1
    r = r && tail_data_size_word_long___all__ard(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // CMPI tail_data_size_all___imm__all_except_ard_pc_imm
  public static boolean cmpi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMPI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMPI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMPI);
    p = r; // pin = 1
    r = r && tail_data_size_all___imm__all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // CMPM data_size_all?
  //                      adm_api COMMA adm_api
  public static boolean cmpm_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpm_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMPM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMPM_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMPM);
    p = r; // pin = 1
    r = r && report_error_(b, cmpm_instruction_1(b, l + 1));
    r = p && report_error_(b, adm_api(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_api(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean cmpm_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpm_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // exg_instruction |
  //                                        lea_instruction |
  //                                        pea_instruction |
  //                                        link_instruction |
  //                                        unlk_instruction
  static boolean data_movement_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_movement_instructions")) return false;
    boolean r;
    r = exg_instruction(b, l + 1);
    if (!r) r = lea_instruction(b, l + 1);
    if (!r) r = pea_instruction(b, l + 1);
    if (!r) r = link_instruction(b, l + 1);
    if (!r) r = unlk_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> (DOT_B | DOT_W | DOT_L)
  static boolean data_size_all(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_all")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.b|w|l>");
    r = data_size_all_0(b, l + 1);
    r = r && data_size_all_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean data_size_all_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_all_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // DOT_B | DOT_W | DOT_L
  private static boolean data_size_all_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_all_1")) return false;
    boolean r;
    r = consumeToken(b, DOT_B);
    if (!r) r = consumeToken(b, DOT_W);
    if (!r) r = consumeToken(b, DOT_L);
    return r;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> DOT_B
  static boolean data_size_byte(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_byte")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_size_byte_0(b, l + 1);
    r = r && consumeToken(b, DOT_B);
    exit_section_(b, m, null, r);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean data_size_byte_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_byte_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> DOT_L
  static boolean data_size_long(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_long")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_size_long_0(b, l + 1);
    r = r && consumeToken(b, DOT_L);
    exit_section_(b, m, null, r);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean data_size_long_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_long_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> DOT_S
  static boolean data_size_short(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_short")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_size_short_0(b, l + 1);
    r = r && consumeToken(b, DOT_S);
    exit_section_(b, m, null, r);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean data_size_short_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_short_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> DOT_W
  static boolean data_size_word(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_word")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_size_word_0(b, l + 1);
    r = r && consumeToken(b, DOT_W);
    exit_section_(b, m, null, r);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean data_size_word_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_word_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> (DOT_W | DOT_L)
  static boolean data_size_word_long(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_word_long")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.w|l>");
    r = data_size_word_long_0(b, l + 1);
    r = r && data_size_word_long_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean data_size_word_long_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_word_long_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // DOT_W | DOT_L
  private static boolean data_size_word_long_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_word_long_1")) return false;
    boolean r;
    r = consumeToken(b, DOT_W);
    if (!r) r = consumeToken(b, DOT_L);
    return r;
  }

  /* ********************************************************** */
  // dbra_instruction |
  //                               dbcs_instruction |
  //                               dblo_instruction |
  //                               dbls_instruction |
  //                               dbeq_instruction |
  //                               dbne_instruction |
  //                               dbhi_instruction |
  //                               dbcc_instruction |
  //                               dbhs_instruction |
  //                               dbpl_instruction |
  //                               dbvc_instruction |
  //                               dblt_instruction |
  //                               dble_instruction |
  //                               dbgt_instruction |
  //                               dbge_instruction |
  //                               dbmi_instruction |
  //                               dbvs_instruction |
  //                               dbf_instruction |
  //                               dbt_instruction
  static boolean dbCC_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbCC_instructions")) return false;
    boolean r;
    r = dbra_instruction(b, l + 1);
    if (!r) r = dbcs_instruction(b, l + 1);
    if (!r) r = dblo_instruction(b, l + 1);
    if (!r) r = dbls_instruction(b, l + 1);
    if (!r) r = dbeq_instruction(b, l + 1);
    if (!r) r = dbne_instruction(b, l + 1);
    if (!r) r = dbhi_instruction(b, l + 1);
    if (!r) r = dbcc_instruction(b, l + 1);
    if (!r) r = dbhs_instruction(b, l + 1);
    if (!r) r = dbpl_instruction(b, l + 1);
    if (!r) r = dbvc_instruction(b, l + 1);
    if (!r) r = dblt_instruction(b, l + 1);
    if (!r) r = dble_instruction(b, l + 1);
    if (!r) r = dbgt_instruction(b, l + 1);
    if (!r) r = dbge_instruction(b, l + 1);
    if (!r) r = dbmi_instruction(b, l + 1);
    if (!r) r = dbvs_instruction(b, l + 1);
    if (!r) r = dbf_instruction(b, l + 1);
    if (!r) r = dbt_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // CC_data_size? adm_drd COMMA expression
  static boolean dbCC_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbCC_tail")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = dbCC_tail_0(b, l + 1);
    r = r && adm_drd(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // CC_data_size?
  private static boolean dbCC_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbCC_tail_0")) return false;
    CC_data_size(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // DBCC dbCC_tail
  public static boolean dbcc_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbcc_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBCC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBCC_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBCC);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBCS dbCC_tail
  public static boolean dbcs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbcs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBCS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBCS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBCS);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBEQ dbCC_tail
  public static boolean dbeq_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbeq_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBEQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBEQ_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBEQ);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBF dbCC_tail
  public static boolean dbf_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbf_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBF_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBF);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBGE dbCC_tail
  public static boolean dbge_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbge_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBGE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBGE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBGE);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBGT dbCC_tail
  public static boolean dbgt_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbgt_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBGT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBGT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBGT);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBHI dbCC_tail
  public static boolean dbhi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbhi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBHI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBHI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBHI);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBHS dbCC_tail
  public static boolean dbhs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbhs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBHS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBHS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBHS);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBLE dbCC_tail
  public static boolean dble_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dble_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBLE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBLE);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBLO dbCC_tail
  public static boolean dblo_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dblo_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBLO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBLO_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBLO);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBLS dbCC_tail
  public static boolean dbls_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbls_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBLS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBLS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBLS);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBLT dbCC_tail
  public static boolean dblt_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dblt_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBLT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBLT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBLT);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBMI dbCC_tail
  public static boolean dbmi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbmi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBMI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBMI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBMI);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBNE dbCC_tail
  public static boolean dbne_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbne_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBNE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBNE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBNE);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBPL dbCC_tail
  public static boolean dbpl_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbpl_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBPL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBPL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBPL);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBRA dbCC_tail
  public static boolean dbra_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbra_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBRA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBRA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBRA);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBT dbCC_tail
  public static boolean dbt_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbt_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBT);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBVC dbCC_tail
  public static boolean dbvc_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbvc_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBVC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBVC_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBVC);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DBVS dbCC_tail
  public static boolean dbvs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbvs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DBVS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DBVS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DBVS);
    p = r; // pin = 1
    r = r && dbCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DIVS mul_div_tail
  public static boolean divs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "divs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DIVS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DIVS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DIVS);
    p = r; // pin = 1
    r = r && mul_div_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DIVU mul_div_tail
  public static boolean divu_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "divu_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", DIVU)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DIVU_INSTRUCTION, "<instruction>");
    r = consumeToken(b, DIVU);
    p = r; // pin = 1
    r = r && mul_div_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // EOR bool_tail
  public static boolean eor_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eor_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", EOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EOR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, EOR);
    p = r; // pin = 1
    r = r && bool_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // EORI bool_i_tail
  public static boolean eori_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eori_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", EORI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EORI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, EORI);
    p = r; // pin = 1
    r = r && bool_i_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // EXG data_size_long? adm_rrd COMMA adm_rrd
  public static boolean exg_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exg_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", EXG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EXG_INSTRUCTION, "<instruction>");
    r = consumeToken(b, EXG);
    p = r; // pin = 1
    r = r && report_error_(b, exg_instruction_1(b, l + 1));
    r = p && report_error_(b, adm_rrd(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_rrd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_long?
  private static boolean exg_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exg_instruction_1")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // EXT data_size_word_long? adm_drd
  public static boolean ext_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ext_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", EXT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EXT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, EXT);
    p = r; // pin = 1
    r = r && report_error_(b, ext_instruction_1(b, l + 1));
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word_long?
  private static boolean ext_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ext_instruction_1")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ILLEGAL
  public static boolean illegal_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "illegal_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ILLEGAL)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ILLEGAL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ILLEGAL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // instructions | labels
  static boolean instruction_or_label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "instruction_or_label")) return false;
    boolean r;
    r = instructions(b, l + 1);
    if (!r) r = labels(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // m68000_instructions |
  //                          directives |
  //                          conditional_assembly_directives
  static boolean instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "instructions")) return false;
    boolean r;
    r = m68000_instructions(b, l + 1);
    if (!r) r = M68kDirectivesParser.directives(b, l + 1);
    if (!r) r = M68kConditionalAssemblyParser.conditional_assembly_directives(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // JMP jmp_jsr_tail
  public static boolean jmp_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jmp_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", JMP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, JMP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, JMP);
    p = r; // pin = 1
    r = r && jmp_jsr_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // adm_ari | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean jmp_jsr_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jmp_jsr_tail")) return false;
    boolean r;
    r = adm_ari(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // JSR jmp_jsr_tail
  public static boolean jsr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jsr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", JSR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, JSR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, JSR);
    p = r; // pin = 1
    r = r && jmp_jsr_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // jsr_instruction|
  //                               bsr_instruction |
  //                               jmp_instruction |
  //                               rts_instruction |
  //                               rte_instruction |
  //                               rtr_instruction
  static boolean jump_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jump_instructions")) return false;
    boolean r;
    r = jsr_instruction(b, l + 1);
    if (!r) r = bsr_instruction(b, l + 1);
    if (!r) r = jmp_instruction(b, l + 1);
    if (!r) r = rts_instruction(b, l + 1);
    if (!r) r = rte_instruction(b, l + 1);
    if (!r) r = rtr_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> labelIdentifier COLON?
  public static boolean label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LABEL, "<label>");
    r = label_0(b, l + 1);
    r = r && labelIdentifier(b, l + 1);
    r = r && label_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean label_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // COLON?
  private static boolean label_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label_2")) return false;
    consumeToken(b, COLON);
    return true;
  }

  /* ********************************************************** */
  // ID
  static boolean labelIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labelIdentifier")) return false;
    if (!nextTokenIs(b, "<label identifier>", ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<label identifier>");
    r = consumeToken(b, ID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // localLabel | label
  static boolean labels(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labels")) return false;
    boolean r;
    r = localLabel(b, l + 1);
    if (!r) r = label(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // LEA data_size_long?
  //                     (adm_ard | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs) COMMA adm_ard
  public static boolean lea_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lea_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", LEA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LEA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, LEA);
    p = r; // pin = 1
    r = r && report_error_(b, lea_instruction_1(b, l + 1));
    r = p && report_error_(b, lea_instruction_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_ard(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_long?
  private static boolean lea_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lea_instruction_1")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  // adm_ard | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  private static boolean lea_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lea_instruction_2")) return false;
    boolean r;
    r = adm_ard(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // LINEFEED+
  static boolean line_feed(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "line_feed")) return false;
    if (!nextTokenIsFast(b, LINEFEED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenFast(b, LINEFEED);
    while (r) {
      int c = current_position_(b);
      if (!consumeTokenFast(b, LINEFEED)) break;
      if (!empty_element_parsed_guard_(b, "line_feed", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LINK data_size_word? adm_ard COMMA adm_imm
  public static boolean link_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "link_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", LINK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LINK_INSTRUCTION, "<instruction>");
    r = consumeToken(b, LINK);
    p = r; // pin = 1
    r = r && report_error_(b, link_instruction_1(b, l + 1));
    r = p && report_error_(b, adm_ard(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_imm(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word?
  private static boolean link_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "link_instruction_1")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // !<<afterWhitespace>> ((DOT labelIdentifier) | (labelIdentifier DOLLAR)) COLON?
  public static boolean localLabel(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LOCAL_LABEL, "<local label>");
    r = localLabel_0(b, l + 1);
    r = r && localLabel_1(b, l + 1);
    r = r && localLabel_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean localLabel_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (DOT labelIdentifier) | (labelIdentifier DOLLAR)
  private static boolean localLabel_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = localLabel_1_0(b, l + 1);
    if (!r) r = localLabel_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // DOT labelIdentifier
  private static boolean localLabel_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && labelIdentifier(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // labelIdentifier DOLLAR
  private static boolean localLabel_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = labelIdentifier(b, l + 1);
    r = r && consumeToken(b, DOLLAR);
    exit_section_(b, m, null, r);
    return r;
  }

  // COLON?
  private static boolean localLabel_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_2")) return false;
    consumeToken(b, COLON);
    return true;
  }

  /* ********************************************************** */
  // and_instruction |
  //                                            andi_instruction |
  //                                            or_instruction |
  //                                            ori_instruction |
  //                                            eor_instruction |
  //                                            eori_instruction |
  //                                            not_instruction
  static boolean logical_operation_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_operation_instructions")) return false;
    boolean r;
    r = and_instruction(b, l + 1);
    if (!r) r = andi_instruction(b, l + 1);
    if (!r) r = or_instruction(b, l + 1);
    if (!r) r = ori_instruction(b, l + 1);
    if (!r) r = eor_instruction(b, l + 1);
    if (!r) r = eori_instruction(b, l + 1);
    if (!r) r = not_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // LSL shift_tail
  public static boolean lsl_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lsl_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", LSL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LSL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, LSL);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // LSR shift_tail
  public static boolean lsr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lsr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", LSR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LSR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, LSR);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // move_instructions |
  //                          data_movement_instructions |
  //                          jump_instructions |
  //                          add_sub_instructions |
  //                          mul_div_instructions |
  //                          bcd_instructions|
  //                          misc_instructions |
  //                          cmp_instructions |
  //                          logical_operation_instructions |
  //                          bit_manipulation_instructions |
  //                          shift_rotate_instructions |
  //                          bCC_instructions |
  //                          dbCC_instructions |
  //                          sCC_instructions
  static boolean m68000_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "m68000_instructions")) return false;
    boolean r;
    r = move_instructions(b, l + 1);
    if (!r) r = data_movement_instructions(b, l + 1);
    if (!r) r = jump_instructions(b, l + 1);
    if (!r) r = add_sub_instructions(b, l + 1);
    if (!r) r = mul_div_instructions(b, l + 1);
    if (!r) r = bcd_instructions(b, l + 1);
    if (!r) r = misc_instructions(b, l + 1);
    if (!r) r = cmp_instructions(b, l + 1);
    if (!r) r = logical_operation_instructions(b, l + 1);
    if (!r) r = bit_manipulation_instructions(b, l + 1);
    if (!r) r = shift_rotate_instructions(b, l + 1);
    if (!r) r = bCC_instructions(b, l + 1);
    if (!r) r = dbCC_instructions(b, l + 1);
    if (!r) r = sCC_instructions(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // tst_instruction |
  //                               tas_instruction |
  //                               clr_instruction |
  //                               nop_instruction |
  //                               illegal_instruction |
  //                               reset_instruction |
  //                               stop_instruction |
  //                               trap_instruction |
  //                               trapv_instruction |
  //                               ext_instruction |
  //                               neg_instruction |
  //                               negx_instruction |
  //                               swap_instruction |
  //                               chk_instruction
  static boolean misc_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "misc_instructions")) return false;
    boolean r;
    r = tst_instruction(b, l + 1);
    if (!r) r = tas_instruction(b, l + 1);
    if (!r) r = clr_instruction(b, l + 1);
    if (!r) r = nop_instruction(b, l + 1);
    if (!r) r = illegal_instruction(b, l + 1);
    if (!r) r = reset_instruction(b, l + 1);
    if (!r) r = stop_instruction(b, l + 1);
    if (!r) r = trap_instruction(b, l + 1);
    if (!r) r = trapv_instruction(b, l + 1);
    if (!r) r = ext_instruction(b, l + 1);
    if (!r) r = neg_instruction(b, l + 1);
    if (!r) r = negx_instruction(b, l + 1);
    if (!r) r = swap_instruction(b, l + 1);
    if (!r) r = chk_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MOVE
  //                      (
  //                        move_tail_usp_ard |
  //                        move_tail_sr |
  //                        move_tail_to_ccr_sr |
  //                        move_tail_ard_usp |
  //                        tail_data_size_all___all__all_except_pc_imm
  //                      )
  public static boolean move_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVE);
    p = r; // pin = 1
    r = r && move_instruction_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // move_tail_usp_ard |
  //                        move_tail_sr |
  //                        move_tail_to_ccr_sr |
  //                        move_tail_ard_usp |
  //                        tail_data_size_all___all__all_except_pc_imm
  private static boolean move_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instruction_1")) return false;
    boolean r;
    r = move_tail_usp_ard(b, l + 1);
    if (!r) r = move_tail_sr(b, l + 1);
    if (!r) r = move_tail_to_ccr_sr(b, l + 1);
    if (!r) r = move_tail_ard_usp(b, l + 1);
    if (!r) r = tail_data_size_all___all__all_except_pc_imm(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // move_instruction |
  //                               movea_instruction |
  //                               moveq_instruction |
  //                               movem_instruction |
  //                               movep_instruction
  static boolean move_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instructions")) return false;
    boolean r;
    r = move_instruction(b, l + 1);
    if (!r) r = movea_instruction(b, l + 1);
    if (!r) r = moveq_instruction(b, l + 1);
    if (!r) r = movem_instruction(b, l + 1);
    if (!r) r = movep_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_long? adm_ard COMMA adm_usp
  static boolean move_tail_ard_usp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_ard_usp")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = move_tail_ard_usp_0(b, l + 1);
    r = r && adm_ard(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && adm_usp(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_long?
  private static boolean move_tail_ard_usp_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_ard_usp_0")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_word? adm_sr COMMA adm_group_all_except_ard_pc_imm
  static boolean move_tail_sr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_sr")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = move_tail_sr_0(b, l + 1);
    r = r && adm_sr(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_group_all_except_ard_pc_imm(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word?
  private static boolean move_tail_sr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_sr_0")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_word? adm_group_all_except_ard COMMA (adm_ccr | adm_sr)
  static boolean move_tail_to_ccr_sr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_to_ccr_sr")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = move_tail_to_ccr_sr_0(b, l + 1);
    r = r && adm_group_all_except_ard(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && move_tail_to_ccr_sr_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_word?
  private static boolean move_tail_to_ccr_sr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_to_ccr_sr_0")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  // adm_ccr | adm_sr
  private static boolean move_tail_to_ccr_sr_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_to_ccr_sr_3")) return false;
    boolean r;
    r = adm_ccr(b, l + 1);
    if (!r) r = adm_sr(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_long? adm_usp COMMA adm_ard
  static boolean move_tail_usp_ard(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_usp_ard")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = move_tail_usp_ard_0(b, l + 1);
    r = r && adm_usp(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_ard(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_long?
  private static boolean move_tail_usp_ard_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_tail_usp_ard_0")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // MOVEA tail_data_size_word_long___all__ard
  public static boolean movea_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movea_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVEA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVEA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVEA);
    p = r; // pin = 1
    r = r && tail_data_size_word_long___all__ard(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // register_list COMMA (adm_ari | adm_apd | adm_adi | adm_aix | adm_abs)
  static boolean movem_from_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_from_tail")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = register_list(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && movem_from_tail_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // adm_ari | adm_apd | adm_adi | adm_aix | adm_abs
  private static boolean movem_from_tail_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_from_tail_2")) return false;
    boolean r;
    r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MOVEM data_size_word_long?
  //                       (
  //                          movem_from_tail  |
  //                          movem_to_tail
  //                       )
  public static boolean movem_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVEM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVEM_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVEM);
    p = r; // pin = 1
    r = r && report_error_(b, movem_instruction_1(b, l + 1));
    r = p && movem_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word_long?
  private static boolean movem_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_instruction_1")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  // movem_from_tail  |
  //                          movem_to_tail
  private static boolean movem_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_instruction_2")) return false;
    boolean r;
    r = movem_from_tail(b, l + 1);
    if (!r) r = movem_to_tail(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (adm_api | adm_ari | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs) COMMA register_list
  static boolean movem_to_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_to_tail")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = movem_to_tail_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && register_list(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // adm_api | adm_ari | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  private static boolean movem_to_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_to_tail_0")) return false;
    boolean r;
    r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MOVEP data_size_word_long?
  //                       (
  //                         movep_tail_drd_adi |
  //                         movep_tail_adi_drd
  //                       )
  public static boolean movep_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movep_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVEP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVEP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVEP);
    p = r; // pin = 1
    r = r && report_error_(b, movep_instruction_1(b, l + 1));
    r = p && movep_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word_long?
  private static boolean movep_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movep_instruction_1")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  // movep_tail_drd_adi |
  //                         movep_tail_adi_drd
  private static boolean movep_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movep_instruction_2")) return false;
    boolean r;
    r = movep_tail_drd_adi(b, l + 1);
    if (!r) r = movep_tail_adi_drd(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // adm_adi COMMA adm_drd
  static boolean movep_tail_adi_drd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movep_tail_adi_drd")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_adi(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // adm_drd COMMA adm_adi
  static boolean movep_tail_drd_adi(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movep_tail_drd_adi")) return false;
    if (!nextTokenIs(b, DATA_REGISTER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_drd(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_adi(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MOVEQ data_size_long?
  //                       adm_imm COMMA adm_drd
  public static boolean moveq_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "moveq_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVEQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVEQ_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVEQ);
    p = r; // pin = 1
    r = r && report_error_(b, moveq_instruction_1(b, l + 1));
    r = p && report_error_(b, adm_imm(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_long?
  private static boolean moveq_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "moveq_instruction_1")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // muls_instruction |
  //                                  mulu_instruction |
  //                                  divs_instruction |
  //                                  divu_instruction
  static boolean mul_div_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_div_instructions")) return false;
    boolean r;
    r = muls_instruction(b, l + 1);
    if (!r) r = mulu_instruction(b, l + 1);
    if (!r) r = divs_instruction(b, l + 1);
    if (!r) r = divu_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_word?
  //                          adm_group_all_except_ard COMMA adm_drd
  static boolean mul_div_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_div_tail")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = mul_div_tail_0(b, l + 1);
    r = r && adm_group_all_except_ard(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word?
  private static boolean mul_div_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_div_tail_0")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // MULS mul_div_tail
  public static boolean muls_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "muls_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MULS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MULS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MULS);
    p = r; // pin = 1
    r = r && mul_div_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // MULU mul_div_tail
  public static boolean mulu_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mulu_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MULU)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MULU_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MULU);
    p = r; // pin = 1
    r = r && mul_div_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // NBCD data_size_byte? adm_group_all_except_pc_imm
  public static boolean nbcd_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nbcd_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NBCD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NBCD_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NBCD);
    p = r; // pin = 1
    r = r && report_error_(b, nbcd_instruction_1(b, l + 1));
    r = p && adm_group_all_except_pc_imm(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_byte?
  private static boolean nbcd_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nbcd_instruction_1")) return false;
    data_size_byte(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // NEG tail_data_size_all___all_except_ard_pc_imm
  public static boolean neg_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "neg_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NEG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NEG_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NEG);
    p = r; // pin = 1
    r = r && tail_data_size_all___all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // NEGX tail_data_size_all___all_except_ard_pc_imm
  public static boolean negx_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "negx_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NEGX)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NEGX_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NEGX);
    p = r; // pin = 1
    r = r && tail_data_size_all___all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // NOP
  public static boolean nop_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nop_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NOP)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NOP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NOP);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NOT tail_data_size_all___all_except_ard_pc_imm
  public static boolean not_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NOT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NOT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NOT);
    p = r; // pin = 1
    r = r && tail_data_size_all___all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // OR bool_tail
  public static boolean or_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "or_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", OR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, OR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, OR);
    p = r; // pin = 1
    r = r && bool_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ORI bool_i_tail
  public static boolean ori_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ori_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ORI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ORI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ORI);
    p = r; // pin = 1
    r = r && bool_i_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // PEA data_size_long?
  //                     (adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs)
  public static boolean pea_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pea_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", PEA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PEA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, PEA);
    p = r; // pin = 1
    r = r && report_error_(b, pea_instruction_1(b, l + 1));
    r = p && pea_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_long?
  private static boolean pea_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pea_instruction_1")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  // adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  private static boolean pea_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pea_instruction_2")) return false;
    boolean r;
    r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // register_range (DIV register_range)*
  public static boolean register_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_list")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REGISTER_LIST, "<register list>");
    r = register_range(b, l + 1);
    p = r; // pin = 1
    r = r && register_list_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (DIV register_range)*
  private static boolean register_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!register_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "register_list_1", c)) break;
    }
    return true;
  }

  // DIV register_range
  private static boolean register_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_list_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, DIV);
    p = r; // pin = 1
    r = r && register_range(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // adm_rrd (MINUS adm_rrd)?
  public static boolean register_range(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, REGISTER_RANGE, "<register range>");
    r = adm_rrd(b, l + 1);
    p = r; // pin = 1
    r = r && register_range_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (MINUS adm_rrd)?
  private static boolean register_range_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range_1")) return false;
    register_range_1_0(b, l + 1);
    return true;
  }

  // MINUS adm_rrd
  private static boolean register_range_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, MINUS);
    p = r; // pin = 1
    r = r && adm_rrd(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // RESET
  public static boolean reset_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reset_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", RESET)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RESET_INSTRUCTION, "<instruction>");
    r = consumeToken(b, RESET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ROL shift_tail
  public static boolean rol_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rol_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ROL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ROL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ROL);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // root_item*
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    while (true) {
      int c = current_position_(b);
      if (!root_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "root", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // line_feed | instruction_or_label line_feed?
  static boolean root_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = line_feed(b, l + 1);
    if (!r) r = root_item_1(b, l + 1);
    exit_section_(b, l, m, r, false, M68kParser::root_item_recover);
    return r;
  }

  // instruction_or_label line_feed?
  private static boolean root_item_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = instruction_or_label(b, l + 1);
    r = r && root_item_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // line_feed?
  private static boolean root_item_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item_1_1")) return false;
    line_feed(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // !instruction_or_label
  static boolean root_item_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !instruction_or_label(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ROR shift_tail
  public static boolean ror_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ror_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ROR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ROR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ROR);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ROXL shift_tail
  public static boolean roxl_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "roxl_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ROXL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ROXL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ROXL);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ROXR shift_tail
  public static boolean roxr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "roxr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ROXR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ROXR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ROXR);
    p = r; // pin = 1
    r = r && shift_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // RTE
  public static boolean rte_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rte_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", RTE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RTE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, RTE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // RTR
  public static boolean rtr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rtr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", RTR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RTR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, RTR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // RTS
  public static boolean rts_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rts_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", RTS)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RTS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, RTS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // seq_instruction |
  //                              sne_instruction |
  //                              spl_instruction |
  //                              smi_instruction |
  //                              svc_instruction |
  //                              svs_instruction |
  //                              st_instruction  |
  //                              sf_instruction  |
  //                              sge_instruction |
  //                              sgt_instruction |
  //                              sle_instruction |
  //                              slt_instruction |
  //                              scc_instruction |
  //                              shi_instruction |
  //                              sls_instruction |
  //                              scs_instruction |
  //                              shs_instruction |
  //                              slo_instruction
  static boolean sCC_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sCC_instructions")) return false;
    boolean r;
    r = seq_instruction(b, l + 1);
    if (!r) r = sne_instruction(b, l + 1);
    if (!r) r = spl_instruction(b, l + 1);
    if (!r) r = smi_instruction(b, l + 1);
    if (!r) r = svc_instruction(b, l + 1);
    if (!r) r = svs_instruction(b, l + 1);
    if (!r) r = st_instruction(b, l + 1);
    if (!r) r = sf_instruction(b, l + 1);
    if (!r) r = sge_instruction(b, l + 1);
    if (!r) r = sgt_instruction(b, l + 1);
    if (!r) r = sle_instruction(b, l + 1);
    if (!r) r = slt_instruction(b, l + 1);
    if (!r) r = scc_instruction(b, l + 1);
    if (!r) r = shi_instruction(b, l + 1);
    if (!r) r = sls_instruction(b, l + 1);
    if (!r) r = scs_instruction(b, l + 1);
    if (!r) r = shs_instruction(b, l + 1);
    if (!r) r = slo_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_byte? adm_group_all_except_ard_pc_imm
  static boolean sCC_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sCC_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sCC_tail_0(b, l + 1);
    r = r && adm_group_all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_byte?
  private static boolean sCC_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sCC_tail_0")) return false;
    data_size_byte(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // SBCD bcd_tail
  public static boolean sbcd_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sbcd_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SBCD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SBCD_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SBCD);
    p = r; // pin = 1
    r = r && bcd_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SCC sCC_tail
  public static boolean scc_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scc_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SCC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SCC_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SCC);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SCS sCC_tail
  public static boolean scs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "scs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SCS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SCS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SCS);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SEQ sCC_tail
  public static boolean seq_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "seq_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SEQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SEQ_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SEQ);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SF  sCC_tail
  public static boolean sf_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sf_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SF_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SF);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SGE sCC_tail
  public static boolean sge_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sge_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SGE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SGE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SGE);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SGT sCC_tail
  public static boolean sgt_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sgt_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SGT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SGT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SGT);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SHI sCC_tail
  public static boolean shi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SHI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SHI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SHI);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // asl_instruction |
  //                                       asr_instruction |
  //                                       lsl_instruction |
  //                                       lsr_instruction |
  //                                       rol_instruction |
  //                                       ror_instruction |
  //                                       roxl_instruction |
  //                                       roxr_instruction
  static boolean shift_rotate_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_rotate_instructions")) return false;
    boolean r;
    r = asl_instruction(b, l + 1);
    if (!r) r = asr_instruction(b, l + 1);
    if (!r) r = lsl_instruction(b, l + 1);
    if (!r) r = lsr_instruction(b, l + 1);
    if (!r) r = rol_instruction(b, l + 1);
    if (!r) r = ror_instruction(b, l + 1);
    if (!r) r = roxl_instruction(b, l + 1);
    if (!r) r = roxr_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // shift_tail_drd |
  //      shift_tail_other
  static boolean shift_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail")) return false;
    boolean r;
    r = shift_tail_drd(b, l + 1);
    if (!r) r = shift_tail_other(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_all? (adm_drd | adm_imm) COMMA adm_drd
  static boolean shift_tail_drd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_drd")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = shift_tail_drd_0(b, l + 1);
    r = r && shift_tail_drd_1(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean shift_tail_drd_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_drd_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // adm_drd | adm_imm
  private static boolean shift_tail_drd_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_drd_1")) return false;
    boolean r;
    r = adm_drd(b, l + 1);
    if (!r) r = adm_imm(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_word? (adm_api | adm_ari | adm_apd | adm_adi | adm_aix | adm_abs)
  static boolean shift_tail_other(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_other")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = shift_tail_other_0(b, l + 1);
    r = r && shift_tail_other_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_word?
  private static boolean shift_tail_other_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_other_0")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  // adm_api | adm_ari | adm_apd | adm_adi | adm_aix | adm_abs
  private static boolean shift_tail_other_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_other_1")) return false;
    boolean r;
    r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // SHS sCC_tail
  public static boolean shs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SHS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SHS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SHS);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SLE sCC_tail
  public static boolean sle_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sle_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SLE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SLE);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SLO sCC_tail
  public static boolean slo_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "slo_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SLO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SLO_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SLO);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SLS sCC_tail
  public static boolean sls_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sls_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SLS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SLS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SLS);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SLT sCC_tail
  public static boolean slt_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "slt_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SLT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SLT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SLT);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SMI sCC_tail
  public static boolean smi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "smi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SMI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SMI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SMI);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SNE sCC_tail
  public static boolean sne_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sne_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SNE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SNE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SNE);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SPL sCC_tail
  public static boolean spl_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "spl_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SPL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SPL_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SPL);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ST  sCC_tail
  public static boolean st_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "st_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ST)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ST_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ST);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // STOP adm_imm
  public static boolean stop_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stop_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", STOP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, STOP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, STOP);
    p = r; // pin = 1
    r = r && adm_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SUB tail_data_size_all___all__all_except_pc_imm
  public static boolean sub_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUB_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUB);
    p = r; // pin = 1
    r = r && tail_data_size_all___all__all_except_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SUBA tail_data_size_word_long___all__ard
  public static boolean suba_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suba_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUBA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUBA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUBA);
    p = r; // pin = 1
    r = r && tail_data_size_word_long___all__ard(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SUBI tail_data_size_all___imm__all_except_ard_pc_imm
  public static boolean subi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUBI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUBI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUBI);
    p = r; // pin = 1
    r = r && tail_data_size_all___imm__all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SUBQ add_sub_q_tail
  public static boolean subq_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subq_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUBQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUBQ_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUBQ);
    p = r; // pin = 1
    r = r && add_sub_q_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SUBX add_sub_x_tail
  public static boolean subx_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subx_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUBX)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUBX_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUBX);
    p = r; // pin = 1
    r = r && add_sub_x_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SVC sCC_tail
  public static boolean svc_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "svc_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SVC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SVC_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SVC);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SVS sCC_tail
  public static boolean svs_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "svs_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SVS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SVS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SVS);
    p = r; // pin = 1
    r = r && sCC_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SWAP data_size_word? adm_drd
  public static boolean swap_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "swap_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SWAP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SWAP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SWAP);
    p = r; // pin = 1
    r = r && report_error_(b, swap_instruction_1(b, l + 1));
    r = p && adm_drd(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word?
  private static boolean swap_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "swap_instruction_1")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_all?       adm_group_all COMMA adm_group_all_except_pc_imm
  static boolean tail_data_size_all___all__all_except_pc_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_all___all__all_except_pc_imm")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = tail_data_size_all___all__all_except_pc_imm_0(b, l + 1);
    r = r && adm_group_all(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_group_all_except_pc_imm(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean tail_data_size_all___all__all_except_pc_imm_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_all___all__all_except_pc_imm_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_all?       adm_group_all_except_ard_pc_imm
  static boolean tail_data_size_all___all_except_ard_pc_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_all___all_except_ard_pc_imm")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tail_data_size_all___all_except_ard_pc_imm_0(b, l + 1);
    r = r && adm_group_all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean tail_data_size_all___all_except_ard_pc_imm_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_all___all_except_ard_pc_imm_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_all?       adm_imm       COMMA adm_group_all_except_ard_pc_imm
  static boolean tail_data_size_all___imm__all_except_ard_pc_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_all___imm__all_except_ard_pc_imm")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = tail_data_size_all___imm__all_except_ard_pc_imm_0(b, l + 1);
    r = r && adm_imm(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_group_all_except_ard_pc_imm(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean tail_data_size_all___imm__all_except_ard_pc_imm_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_all___imm__all_except_ard_pc_imm_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_word_long? adm_group_all COMMA adm_ard
  static boolean tail_data_size_word_long___all__ard(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_word_long___all__ard")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = tail_data_size_word_long___all__ard_0(b, l + 1);
    r = r && adm_group_all(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, consumeToken(b, COMMA));
    r = p && adm_ard(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word_long?
  private static boolean tail_data_size_word_long___all__ard_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tail_data_size_word_long___all__ard_0")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAS data_size_byte? adm_group_all_except_ard_pc_imm
  public static boolean tas_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tas_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", TAS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, TAS);
    p = r; // pin = 1
    r = r && report_error_(b, tas_instruction_1(b, l + 1));
    r = p && adm_group_all_except_ard_pc_imm(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_byte?
  private static boolean tas_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tas_instruction_1")) return false;
    data_size_byte(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TRAP adm_imm
  public static boolean trap_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "trap_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", TRAP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TRAP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, TRAP);
    p = r; // pin = 1
    r = r && adm_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TRAPV
  public static boolean trapv_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "trapv_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", TRAPV)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TRAPV_INSTRUCTION, "<instruction>");
    r = consumeToken(b, TRAPV);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TST tail_data_size_all___all_except_ard_pc_imm
  public static boolean tst_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tst_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", TST)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TST_INSTRUCTION, "<instruction>");
    r = consumeToken(b, TST);
    p = r; // pin = 1
    r = r && tail_data_size_all___all_except_ard_pc_imm(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // UNLK adm_ard
  public static boolean unlk_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unlk_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", UNLK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, UNLK_INSTRUCTION, "<instruction>");
    r = consumeToken(b, UNLK);
    p = r; // pin = 1
    r = r && adm_ard(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
