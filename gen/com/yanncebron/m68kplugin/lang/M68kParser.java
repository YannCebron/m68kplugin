/*
 * Copyright 2026 The Authors
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
    create_token_set_(EQUALS_DIRECTIVE, EQUR_DIRECTIVE, EQU_DIRECTIVE, FO_DIRECTIVE,
      MACRO_DIRECTIVE, REG_DIRECTIVE, SET_DIRECTIVE, SO_DIRECTIVE),
    create_token_set_(AND_EXPRESSION, DIV_EXPRESSION, EQUALS_EXPRESSION, EXPRESSION,
      GT_EQ_EXPRESSION, GT_EXPRESSION, LABEL_REF_EXPRESSION, LOGICAL_AND_EXPRESSION,
      LOGICAL_OR_EXPRESSION, LT_EQ_EXPRESSION, LT_EXPRESSION, MINUS_EXPRESSION,
      MOD_EXPRESSION, MUL_EXPRESSION, NOT_EQUALS_EXPRESSION, NUMBER_EXPRESSION,
      OR_EXPRESSION, PAREN_EXPRESSION, PLUS_EXPRESSION, SHIFT_LEFT_EXPRESSION,
      SHIFT_RIGHT_EXPRESSION, STRING_EXPRESSION, UNARY_COMPLEMENT_EXPRESSION, UNARY_MINUS_EXPRESSION,
      UNARY_NOT_EXPRESSION, UNARY_PLUS_EXPRESSION, XOR_EXPRESSION),
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
      DR_DIRECTIVE, DS_DIRECTIVE, DX_DIRECTIVE, EORI_INSTRUCTION,
      EOR_INSTRUCTION, EXG_INSTRUCTION, EXT_INSTRUCTION, LEA_INSTRUCTION,
      LINK_INSTRUCTION, LSL_INSTRUCTION, LSR_INSTRUCTION, MACRO_CALL_DIRECTIVE,
      MOVEA_INSTRUCTION, MOVEC_INSTRUCTION, MOVEM_INSTRUCTION, MOVEP_INSTRUCTION,
      MOVEQ_INSTRUCTION, MOVES_INSTRUCTION, MOVE_INSTRUCTION, MULS_INSTRUCTION,
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
  // (expression L_PAREN adm_ard R_PAREN) |
  //             (L_PAREN expression COMMA adm_ard R_PAREN)
  public static boolean adm_adi(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_adi")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_ADI, "<(d,An)>");
    r = adm_adi_0(b, l + 1);
    if (!r) r = adm_adi_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // expression L_PAREN adm_ard R_PAREN
  private static boolean adm_adi_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_adi_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = M68kExpressionParser.expression(b, l + 1, -1);
    r = r && consumeToken(b, L_PAREN);
    r = r && adm_ard(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // L_PAREN expression COMMA adm_ard R_PAREN
  private static boolean adm_adi_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_adi_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_PAREN);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    r = r && consumeToken(b, COMMA);
    r = r && adm_ard(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // adm_aix_old |
  //             adm_aix_new
  public static boolean adm_aix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_AIX, "<adm aix>");
    r = adm_aix_old(b, l + 1);
    if (!r) r = adm_aix_new(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // L_PAREN (expression COMMA)? adm_ard COMMA adm_rrd_index R_PAREN
  static boolean adm_aix_new(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix_new")) return false;
    if (!nextTokenIs(b, L_PAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, L_PAREN);
    r = r && adm_aix_new_1(b, l + 1);
    r = r && adm_ard(b, l + 1);
    r = r && consumeToken(b, COMMA);
    p = r; // pin = COMMA
    r = r && report_error_(b, adm_rrd_index(b, l + 1));
    r = p && consumeToken(b, R_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (expression COMMA)?
  private static boolean adm_aix_new_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix_new_1")) return false;
    adm_aix_new_1_0(b, l + 1);
    return true;
  }

  // expression COMMA
  private static boolean adm_aix_new_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix_new_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = M68kExpressionParser.expression(b, l + 1, -1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression? L_PAREN adm_ard COMMA adm_rrd_index R_PAREN
  static boolean adm_aix_old(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix_old")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_aix_old_0(b, l + 1);
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
  private static boolean adm_aix_old_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_aix_old_0")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // MINUS L_PAREN adm_ard R_PAREN
  public static boolean adm_apd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_apd")) return false;
    if (!nextTokenIs(b, "<-(An)>", MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_APD, "<-(An)>");
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
    if (!nextTokenIs(b, "<(An)+>", L_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_API, "<(An)+>");
    r = consumeToken(b, L_PAREN);
    r = r && adm_ard(b, l + 1);
    r = r && consumeTokens(b, 0, R_PAREN, PLUS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ADDRESS_REGISTER | SP
  public static boolean adm_ard(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_ard")) return false;
    if (!nextTokenIs(b, "<An>", ADDRESS_REGISTER, SP)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_ARD, "<An>");
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
    if (!nextTokenIs(b, "<CCR>", CCR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_CCR, "<CCR>");
    r = consumeToken(b, CCR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DFC
  public static boolean adm_dfc(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_dfc")) return false;
    if (!nextTokenIs(b, DFC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DFC);
    exit_section_(b, m, ADM_DFC, r);
    return r;
  }

  /* ********************************************************** */
  // adm_drd COLON adm_drd
  public static boolean adm_double_drd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_double_drd")) return false;
    if (!nextTokenIs(b, "<Dx:Dy>", DATA_REGISTER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_DOUBLE_DRD, "<Dx:Dy>");
    r = adm_drd(b, l + 1);
    r = r && consumeToken(b, COLON);
    p = r; // pin = 2
    r = r && adm_drd(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DATA_REGISTER
  public static boolean adm_drd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_drd")) return false;
    if (!nextTokenIs(b, "<Dn>", DATA_REGISTER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_DRD, "<Dn>");
    r = consumeToken(b, DATA_REGISTER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // HASH expression data_size_word_long?
  public static boolean adm_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_imm")) return false;
    if (!nextTokenIs(b, "<#Imm>", HASH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_IMM, "<#Imm>");
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
  // (L_PAREN PC R_PAREN) |
  //             (expression? L_PAREN PC R_PAREN) |
  //             (L_PAREN expression COMMA PC R_PAREN)
  public static boolean adm_pcd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pcd")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_PCD, "<adm pcd>");
    r = adm_pcd_0(b, l + 1);
    if (!r) r = adm_pcd_1(b, l + 1);
    if (!r) r = adm_pcd_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // L_PAREN PC R_PAREN
  private static boolean adm_pcd_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pcd_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, L_PAREN, PC, R_PAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // expression? L_PAREN PC R_PAREN
  private static boolean adm_pcd_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pcd_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = adm_pcd_1_0(b, l + 1);
    r = r && consumeTokens(b, 0, L_PAREN, PC, R_PAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // expression?
  private static boolean adm_pcd_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pcd_1_0")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
  }

  // L_PAREN expression COMMA PC R_PAREN
  private static boolean adm_pcd_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pcd_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_PAREN);
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    r = r && consumeTokens(b, 0, COMMA, PC, R_PAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // adm_pci_old |
  //             adm_pci_new
  public static boolean adm_pci(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_PCI, "<adm pci>");
    r = adm_pci_old(b, l + 1);
    if (!r) r = adm_pci_new(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // L_PAREN (expression COMMA)? PC COMMA adm_rrd_index R_PAREN
  static boolean adm_pci_new(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci_new")) return false;
    if (!nextTokenIs(b, L_PAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, L_PAREN);
    r = r && adm_pci_new_1(b, l + 1);
    r = r && consumeTokens(b, 2, PC, COMMA);
    p = r; // pin = COMMA
    r = r && report_error_(b, adm_rrd_index(b, l + 1));
    r = p && consumeToken(b, R_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (expression COMMA)?
  private static boolean adm_pci_new_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci_new_1")) return false;
    adm_pci_new_1_0(b, l + 1);
    return true;
  }

  // expression COMMA
  private static boolean adm_pci_new_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci_new_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = M68kExpressionParser.expression(b, l + 1, -1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression? L_PAREN PC COMMA adm_rrd_index R_PAREN
  static boolean adm_pci_old(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci_old")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_pci_old_0(b, l + 1);
    r = r && consumeTokens(b, 3, L_PAREN, PC, COMMA);
    p = r; // pin = COMMA
    r = r && report_error_(b, adm_rrd_index(b, l + 1));
    r = p && consumeToken(b, R_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // expression?
  private static boolean adm_pci_old_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_pci_old_0")) return false;
    M68kExpressionParser.expression(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // HASH expression
  public static boolean adm_quick(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_quick")) return false;
    if (!nextTokenIs(b, "<Quick #Imm>", HASH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_QUICK, "<Quick #Imm>");
    r = consumeToken(b, HASH);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // register_range (DIV register_range)*
  public static boolean adm_register_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_register_list")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADM_REGISTER_LIST, "<adm register list>");
    r = register_range(b, l + 1);
    p = r; // pin = 1
    r = r && adm_register_list_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (DIV register_range)*
  private static boolean adm_register_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_register_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!adm_register_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "adm_register_list_1", c)) break;
    }
    return true;
  }

  // DIV register_range
  private static boolean adm_register_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_register_list_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, DIV);
    p = r; // pin = 1
    r = r && register_range(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // adm_drd | adm_ard
  public static boolean adm_rrd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_rrd")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_RRD, "<Rn>");
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
    Marker m = enter_section_(b, l, _NONE_, ADM_RRD_INDEX, "<Rn>");
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
  // SFC
  public static boolean adm_sfc(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_sfc")) return false;
    if (!nextTokenIs(b, SFC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SFC);
    exit_section_(b, m, ADM_SFC, r);
    return r;
  }

  /* ********************************************************** */
  // SR
  public static boolean adm_sr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_sr")) return false;
    if (!nextTokenIs(b, "<SR>", SR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_SR, "<SR>");
    r = consumeToken(b, SR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // USP
  public static boolean adm_usp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_usp")) return false;
    if (!nextTokenIs(b, "<USP>", USP)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADM_USP, "<USP>");
    r = consumeToken(b, USP);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // VBR
  public static boolean adm_vbr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adm_vbr")) return false;
    if (!nextTokenIs(b, VBR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, VBR);
    exit_section_(b, m, ADM_VBR, r);
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
  // !<<afterWhitespace>> (DOT_B | DOT_W | DOT_L | DOT_S)
  static boolean data_size_all_single(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_all_single")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.s|b|w|l>");
    r = data_size_all_single_0(b, l + 1);
    r = r && data_size_all_single_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !<<afterWhitespace>>
  private static boolean data_size_all_single_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_all_single_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !afterWhitespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // DOT_B | DOT_W | DOT_L | DOT_S
  private static boolean data_size_all_single_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_all_single_1")) return false;
    boolean r;
    r = consumeToken(b, DOT_B);
    if (!r) r = consumeToken(b, DOT_W);
    if (!r) r = consumeToken(b, DOT_L);
    if (!r) r = consumeToken(b, DOT_S);
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
  // labelIdentifier DOLLAR
  static boolean dollar_local_label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dollar_local_label")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = labelIdentifier(b, l + 1);
    r = r && consumeToken(b, DOLLAR);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DOT labelIdentifier
  static boolean dot_local_label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dot_local_label")) return false;
    if (!nextTokenIs(b, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && labelIdentifier(b, l + 1);
    exit_section_(b, m, null, r);
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
    r = M68kMnemonicsParser.m68000_instructions(b, l + 1);
    if (!r) r = M68kDirectivesParser.directives(b, l + 1);
    if (!r) r = M68kConditionalAssemblyParser.conditional_assembly_directives(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // labelIdentifier COLON?
  public static boolean label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = labelIdentifier(b, l + 1);
    r = r && label_1(b, l + 1);
    exit_section_(b, m, LABEL, r);
    return r;
  }

  // COLON?
  private static boolean label_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label_1")) return false;
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
    if (!nextTokenIs(b, "", DOT, ID)) return false;
    boolean r;
    r = localLabel(b, l + 1);
    if (!r) r = label(b, l + 1);
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
  // (dot_local_label | dollar_local_label) COLON?
  public static boolean localLabel(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel")) return false;
    if (!nextTokenIs(b, "<local label>", DOT, ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LOCAL_LABEL, "<local label>");
    r = localLabel_0(b, l + 1);
    r = r && localLabel_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // dot_local_label | dollar_local_label
  private static boolean localLabel_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_0")) return false;
    boolean r;
    r = dot_local_label(b, l + 1);
    if (!r) r = dollar_local_label(b, l + 1);
    return r;
  }

  // COLON?
  private static boolean localLabel_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_1")) return false;
    consumeToken(b, COLON);
    return true;
  }

  /* ********************************************************** */
  // adm_drd | adm_ard | adm_imm | adm_api | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean operand_all(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_all")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<ALL>");
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
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_drd | adm_ard |           adm_api | adm_ari | adm_apd |                     adm_adi | adm_aix | adm_abs
  static boolean operand_alterable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_alterable")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<ALTERABLE>");
    r = adm_drd(b, l + 1);
    if (!r) r = adm_ard(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_ari |                               adm_adi | adm_aix | adm_abs
  static boolean operand_alterable_control(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_alterable_control")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<ALTERABLE_CONTROL>");
    r = adm_ari(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_drd |                     adm_api | adm_ari | adm_apd |                     adm_adi | adm_aix | adm_abs
  static boolean operand_alterable_data(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_alterable_data")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<ALTERABLE_DATA>");
    r = adm_drd(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_api | adm_ari | adm_apd |                     adm_adi | adm_aix | adm_abs
  static boolean operand_alterable_memory(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_alterable_memory")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<ALTERABLE_MEMORY>");
    r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_ari |           adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean operand_control(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_control")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<CONTROL>");
    r = adm_ari(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_dfc | adm_sfc | adm_vbr
  static boolean operand_ctrl_register(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_ctrl_register")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<CTRL_REGISTER>");
    r = adm_dfc(b, l + 1);
    if (!r) r = adm_sfc(b, l + 1);
    if (!r) r = adm_vbr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_drd |           adm_imm | adm_api | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean operand_data(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_data")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<DATA>");
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
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_drd |                     adm_api | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean operand_data_without_immediate(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_data_without_immediate")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<DATA_WITHOUT_IMMEDIATE>");
    r = adm_drd(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_imm | adm_api | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean operand_memory(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_memory")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<MEMORY>");
    r = adm_imm(b, l + 1);
    if (!r) r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_api | adm_ari | adm_apd | adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean operand_memory_without_imm(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_memory_without_imm")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<MEMORY_WITHOUT_IMMEDIATE>");
    r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_apd(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // adm_api | adm_ari |           adm_pcd | adm_pci | adm_adi | adm_aix | adm_abs
  static boolean operand_restore_operands(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operand_restore_operands")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<RESTORE_OPERANDS>");
    r = adm_api(b, l + 1);
    if (!r) r = adm_ari(b, l + 1);
    if (!r) r = adm_pcd(b, l + 1);
    if (!r) r = adm_pci(b, l + 1);
    if (!r) r = adm_adi(b, l + 1);
    if (!r) r = adm_aix(b, l + 1);
    if (!r) r = adm_abs(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // register_range_real |
  //                    <<registerRangeStandaloneRegisterValid>> adm_rrd
  public static boolean register_range(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, REGISTER_RANGE, "<Rn list>");
    r = register_range_real(b, l + 1);
    if (!r) r = register_range_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // <<registerRangeStandaloneRegisterValid>> adm_rrd
  private static boolean register_range_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = registerRangeStandaloneRegisterValid(b, l + 1);
    r = r && adm_rrd(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // adm_rrd MINUS adm_rrd
  static boolean register_range_real(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range_real")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = adm_rrd(b, l + 1);
    r = r && consumeToken(b, MINUS);
    p = r; // pin = 2
    r = r && adm_rrd(b, l + 1);
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

}
