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
    create_token_set_(LABEL, LOCAL_LABEL),
    create_token_set_(EQUALS_DIRECTIVE, EQUR_DIRECTIVE, EQU_DIRECTIVE),
    create_token_set_(AND_EXPRESSION, DIV_EXPRESSION, EXPRESSION, EXP_EXPRESSION,
      LABEL_REF_EXPRESSION, MINUS_EXPRESSION, MOD_EXPRESSION, MUL_EXPRESSION,
      NUMBER_EXPRESSION, OR_EXPRESSION, PAREN_EXPRESSION, PLUS_EXPRESSION,
      SHIFT_LEFT_EXPRESSION, SHIFT_RIGHT_EXPRESSION, STRING_EXPRESSION, UNARY_COMPLEMENT_EXPRESSION,
      UNARY_MINUS_EXPRESSION, UNARY_PLUS_EXPRESSION),
    create_token_set_(ANDI_INSTRUCTION, AND_INSTRUCTION, ASL_INSTRUCTION, ASR_INSTRUCTION,
      BCHG_INSTRUCTION, BCLR_INSTRUCTION, BLK_DIRECTIVE, BSET_INSTRUCTION,
      BSR_INSTRUCTION, BTST_INSTRUCTION, CHK_INSTRUCTION, CLR_INSTRUCTION,
      CMPA_INSTRUCTION, CMPI_INSTRUCTION, CMPM_INSTRUCTION, CMP_INSTRUCTION,
      DCB_DIRECTIVE, DC_DIRECTIVE, DS_DIRECTIVE, EORI_INSTRUCTION,
      EOR_INSTRUCTION, EXG_INSTRUCTION, EXT_INSTRUCTION, LEA_INSTRUCTION,
      LSL_INSTRUCTION, LSR_INSTRUCTION, NEGX_INSTRUCTION, NEG_INSTRUCTION,
      NOT_INSTRUCTION, ORI_INSTRUCTION, OR_INSTRUCTION, PEA_INSTRUCTION,
      ROL_INSTRUCTION, ROR_INSTRUCTION, ROXL_INSTRUCTION, ROXR_INSTRUCTION,
      RS_DIRECTIVE, SWAP_INSTRUCTION, TAS_INSTRUCTION, TST_INSTRUCTION),
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
  // ADD add_sub_tail
  public static boolean add_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADD_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADD);
    p = r; // pin = 1
    r = r && add_sub_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // data_size_word_long?
  //                            any_register COMMA address_register
  static boolean add_sub_a_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_a_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_a_tail_0(b, l + 1);
    r = r && any_register(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && address_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_word_long?
  private static boolean add_sub_a_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_a_tail_0")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // data_size_all?
  //                            immediate_data COMMA any_register
  static boolean add_sub_i_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_i_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_i_tail_0(b, l + 1);
    r = r && immediate_data(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && any_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean add_sub_i_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_i_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
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
  // data_size_all?
  //                            immediate_data COMMA (data_register | effective_address | address_register)
  static boolean add_sub_q_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_q_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_q_tail_0(b, l + 1);
    r = r && immediate_data(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && add_sub_q_tail_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean add_sub_q_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_q_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // data_register | effective_address | address_register
  private static boolean add_sub_q_tail_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_q_tail_3")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    if (!r) r = address_register(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_all?
  //                            (
  //                              (data_register                        COMMA (data_register | effective_address)) |
  //                              ((effective_address | address_register | immediate_data) COMMA data_register )
  //                            )
  static boolean add_sub_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_tail_0(b, l + 1);
    r = r && add_sub_tail_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean add_sub_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // (data_register                        COMMA (data_register | effective_address)) |
  //                              ((effective_address | address_register | immediate_data) COMMA data_register )
  private static boolean add_sub_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_tail_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_tail_1_0(b, l + 1);
    if (!r) r = add_sub_tail_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_register                        COMMA (data_register | effective_address)
  private static boolean add_sub_tail_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_tail_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_register(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && add_sub_tail_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_register | effective_address
  private static boolean add_sub_tail_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_tail_1_0_2")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    return r;
  }

  // (effective_address | address_register | immediate_data) COMMA data_register
  private static boolean add_sub_tail_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_tail_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_tail_1_1_0(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && data_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // effective_address | address_register | immediate_data
  private static boolean add_sub_tail_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_tail_1_1_0")) return false;
    boolean r;
    r = effective_address(b, l + 1);
    if (!r) r = address_register(b, l + 1);
    if (!r) r = immediate_data(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // data_size_all?
  //                            (
  //                              (data_register COMMA data_register) |
  //                              (address_register_pre_decrement COMMA address_register_pre_decrement)
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

  // (data_register COMMA data_register) |
  //                              (address_register_pre_decrement COMMA address_register_pre_decrement)
  private static boolean add_sub_x_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = add_sub_x_tail_1_0(b, l + 1);
    if (!r) r = add_sub_x_tail_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_register COMMA data_register
  private static boolean add_sub_x_tail_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_register(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && data_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // address_register_pre_decrement COMMA address_register_pre_decrement
  private static boolean add_sub_x_tail_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_sub_x_tail_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = address_register_pre_decrement(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && address_register_pre_decrement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ADDA add_sub_a_tail
  public static boolean adda_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "adda_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADDA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADDA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADDA);
    p = r; // pin = 1
    r = r && add_sub_a_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ADDI add_sub_i_tail
  public static boolean addi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "addi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", ADDI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ADDI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, ADDI);
    p = r; // pin = 1
    r = r && add_sub_i_tail(b, l + 1);
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
  // ADDRESS_REGISTER | SP
  static boolean address_register(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_register")) return false;
    if (!nextTokenIs(b, "<address register>", ADDRESS_REGISTER, SP)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<address register>");
    r = consumeToken(b, ADDRESS_REGISTER);
    if (!r) r = consumeToken(b, SP);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (label_ref_expression | MINUS | number_expression)? L_PAREN address_register R_PAREN PLUS?
  static boolean address_register_indirect(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_register_indirect")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = address_register_indirect_0(b, l + 1);
    r = r && consumeToken(b, L_PAREN);
    r = r && address_register(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    r = r && address_register_indirect_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (label_ref_expression | MINUS | number_expression)?
  private static boolean address_register_indirect_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_register_indirect_0")) return false;
    address_register_indirect_0_0(b, l + 1);
    return true;
  }

  // label_ref_expression | MINUS | number_expression
  private static boolean address_register_indirect_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_register_indirect_0_0")) return false;
    boolean r;
    r = label_ref_expression(b, l + 1);
    if (!r) r = consumeToken(b, MINUS);
    if (!r) r = number_expression(b, l + 1);
    return r;
  }

  // PLUS?
  private static boolean address_register_indirect_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_register_indirect_4")) return false;
    consumeToken(b, PLUS);
    return true;
  }

  /* ********************************************************** */
  // L_PAREN ADDRESS_REGISTER R_PAREN PLUS
  static boolean address_register_post_increment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_register_post_increment")) return false;
    if (!nextTokenIs(b, L_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, L_PAREN, ADDRESS_REGISTER, R_PAREN, PLUS);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MINUS L_PAREN address_register R_PAREN
  static boolean address_register_pre_decrement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_register_pre_decrement")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, MINUS, L_PAREN);
    r = r && address_register(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, m, null, r);
    return r;
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
  // data_register |
  //                          address_register |
  //                          program_counter |
  //                          status_register | condition_code_register |
  //                          supervisor_stack_pointer | user_stack_pointer |
  //                          address_register_indirect |
  //                          program_counter_indirect
  static boolean any_register(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "any_register")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = address_register(b, l + 1);
    if (!r) r = program_counter(b, l + 1);
    if (!r) r = status_register(b, l + 1);
    if (!r) r = condition_code_register(b, l + 1);
    if (!r) r = supervisor_stack_pointer(b, l + 1);
    if (!r) r = user_stack_pointer(b, l + 1);
    if (!r) r = address_register_indirect(b, l + 1);
    if (!r) r = program_counter_indirect(b, l + 1);
    return r;
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
  // CC_data_size? label_reference
  static boolean bCC_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bCC_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bCC_tail_0(b, l + 1);
    r = r && label_reference(b, l + 1);
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
  //                       (data_register COMMA data_register) |
  //                       (address_register_pre_decrement COMMA address_register_pre_decrement)
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

  // (data_register COMMA data_register) |
  //                       (address_register_pre_decrement COMMA address_register_pre_decrement)
  private static boolean bcd_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bcd_tail_1_0(b, l + 1);
    if (!r) r = bcd_tail_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_register COMMA data_register
  private static boolean bcd_tail_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_register(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && data_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // address_register_pre_decrement COMMA address_register_pre_decrement
  private static boolean bcd_tail_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bcd_tail_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = address_register_pre_decrement(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && address_register_pre_decrement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
    if (!nextTokenIs(b, "<.b|l>", DOT_B, DOT_L)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.b|l>");
    r = data_size_byte(b, l + 1);
    if (!r) r = data_size_long(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // bchg_instruction |
  //                              bclr_instruction |
  //                              bset_instruction |
  //                              btst_instruction
  static boolean bit_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_instructions")) return false;
    boolean r;
    r = bchg_instruction(b, l + 1);
    if (!r) r = bclr_instruction(b, l + 1);
    if (!r) r = bset_instruction(b, l + 1);
    if (!r) r = btst_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // bit_data_size?
  //                      (immediate_data | data_register) COMMA (data_register | effective_address)
  static boolean bit_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bit_tail_0(b, l + 1);
    r = r && bit_tail_1(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && bit_tail_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // bit_data_size?
  private static boolean bit_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_tail_0")) return false;
    bit_data_size(b, l + 1);
    return true;
  }

  // immediate_data | data_register
  private static boolean bit_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_tail_1")) return false;
    boolean r;
    r = immediate_data(b, l + 1);
    if (!r) r = data_register(b, l + 1);
    return r;
  }

  // data_register | effective_address
  private static boolean bit_tail_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bit_tail_3")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
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
  // BLK data_size_all? expression COMMA expression
  public static boolean blk_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blk_directive")) return false;
    if (!nextTokenIs(b, BLK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BLK_DIRECTIVE, null);
    r = consumeToken(b, BLK);
    p = r; // pin = 1
    r = r && report_error_(b, blk_directive_1(b, l + 1));
    r = p && report_error_(b, expression(b, l + 1, -1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && expression(b, l + 1, -1) && r;
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
  // data_size_all? (any_register | immediate_data) COMMA any_register
  static boolean bool_i_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bool_i_tail_0(b, l + 1);
    r = r && bool_i_tail_1(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && any_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean bool_i_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // any_register | immediate_data
  private static boolean bool_i_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_i_tail_1")) return false;
    boolean r;
    r = any_register(b, l + 1);
    if (!r) r = immediate_data(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // and_instruction |
  //                               andi_instruction |
  //                               or_instruction |
  //                               ori_instruction |
  //                               eor_instruction |
  //                               eori_instruction |
  //                               not_instruction
  static boolean bool_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_instructions")) return false;
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
  // data_size_all? any_register COMMA any_register
  static boolean bool_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = bool_tail_0(b, l + 1);
    r = r && any_register(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && any_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean bool_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bool_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
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
  // data_size_short | data_size_word
  static boolean bsr_data_size(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bsr_data_size")) return false;
    if (!nextTokenIs(b, "<.s|w>", DOT_S, DOT_W)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.s|w>");
    r = data_size_short(b, l + 1);
    if (!r) r = data_size_word(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BSR bsr_data_size? effective_address
  public static boolean bsr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bsr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BSR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BSR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BSR);
    p = r; // pin = 1
    r = r && report_error_(b, bsr_instruction_1(b, l + 1));
    r = p && effective_address(b, l + 1) && r;
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
  // BTST bit_tail
  public static boolean btst_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "btst_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", BTST)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, BTST_INSTRUCTION, "<instruction>");
    r = consumeToken(b, BTST);
    p = r; // pin = 1
    r = r && bit_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // CHK data_size_word? any_register COMMA data_register
  public static boolean chk_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chk_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CHK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CHK_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CHK);
    p = r; // pin = 1
    r = r && report_error_(b, chk_instruction_1(b, l + 1));
    r = p && report_error_(b, any_register(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && data_register(b, l + 1) && r;
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
  // CLR data_size_all? (effective_address | any_register | label_reference)
  public static boolean clr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CLR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CLR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CLR);
    p = r; // pin = 1
    r = r && report_error_(b, clr_instruction_1(b, l + 1));
    r = p && clr_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean clr_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clr_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // effective_address | any_register | label_reference
  private static boolean clr_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clr_instruction_2")) return false;
    boolean r;
    r = effective_address(b, l + 1);
    if (!r) r = any_register(b, l + 1);
    if (!r) r = label_reference(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // CMP data_size_all?
  //                     (data_register | effective_address | address_register_indirect) COMMA data_register
  public static boolean cmp_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmp_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMP);
    p = r; // pin = 1
    r = r && report_error_(b, cmp_instruction_1(b, l + 1));
    r = p && report_error_(b, cmp_instruction_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && data_register(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean cmp_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmp_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // data_register | effective_address | address_register_indirect
  private static boolean cmp_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmp_instruction_2")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    if (!r) r = address_register_indirect(b, l + 1);
    return r;
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
  // CMPA data_size_word_long?
  //                      any_register COMMA address_register
  public static boolean cmpa_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpa_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMPA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMPA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMPA);
    p = r; // pin = 1
    r = r && report_error_(b, cmpa_instruction_1(b, l + 1));
    r = p && report_error_(b, any_register(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && address_register(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word_long?
  private static boolean cmpa_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpa_instruction_1")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // CMPI data_size_all?
  //                      immediate_data COMMA any_register
  public static boolean cmpi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMPI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMPI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMPI);
    p = r; // pin = 1
    r = r && report_error_(b, cmpi_instruction_1(b, l + 1));
    r = p && report_error_(b, immediate_data(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && any_register(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean cmpi_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpi_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // CMPM data_size_all?
  //                      address_register_post_increment COMMA address_register_post_increment
  public static boolean cmpm_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmpm_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", CMPM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CMPM_INSTRUCTION, "<instruction>");
    r = consumeToken(b, CMPM);
    p = r; // pin = 1
    r = r && report_error_(b, cmpm_instruction_1(b, l + 1));
    r = p && report_error_(b, address_register_post_increment(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && address_register_post_increment(b, l + 1) && r;
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
  // CCR
  static boolean condition_code_register(PsiBuilder b, int l) {
    return consumeToken(b, CCR);
  }

  /* ********************************************************** */
  // DATA_REGISTER
  static boolean data_register(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_register")) return false;
    if (!nextTokenIs(b, "<data register>", DATA_REGISTER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<data register>");
    r = consumeToken(b, DATA_REGISTER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // data_size_byte | data_size_word_long
  static boolean data_size_all(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_all")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.b|w|l>");
    r = data_size_byte(b, l + 1);
    if (!r) r = data_size_word_long(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // DOT_B
  static boolean data_size_byte(PsiBuilder b, int l) {
    return consumeToken(b, DOT_B);
  }

  /* ********************************************************** */
  // DOT_L
  static boolean data_size_long(PsiBuilder b, int l) {
    return consumeToken(b, DOT_L);
  }

  /* ********************************************************** */
  // DOT_S
  static boolean data_size_short(PsiBuilder b, int l) {
    return consumeToken(b, DOT_S);
  }

  /* ********************************************************** */
  // DOT_W
  static boolean data_size_word(PsiBuilder b, int l) {
    return consumeToken(b, DOT_W);
  }

  /* ********************************************************** */
  // data_size_word | data_size_long
  static boolean data_size_word_long(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_size_word_long")) return false;
    if (!nextTokenIs(b, "<.w|l>", DOT_L, DOT_W)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<.w|l>");
    r = data_size_word(b, l + 1);
    if (!r) r = data_size_long(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // dbra_instruction |
  //                               dbcs_instruction |
  //                               dbls_instruction |
  //                               dbeq_instruction |
  //                               dbne_instruction |
  //                               dbhi_instruction |
  //                               dbcc_instruction |
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
    if (!r) r = dbls_instruction(b, l + 1);
    if (!r) r = dbeq_instruction(b, l + 1);
    if (!r) r = dbne_instruction(b, l + 1);
    if (!r) r = dbhi_instruction(b, l + 1);
    if (!r) r = dbcc_instruction(b, l + 1);
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
  // CC_data_size? data_register COMMA label_reference
  static boolean dbCC_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbCC_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = dbCC_tail_0(b, l + 1);
    r = r && data_register(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && label_reference(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
  // DC data_size_all? dc_element (COMMA dc_element)*
  public static boolean dc_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive")) return false;
    if (!nextTokenIs(b, DC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DC_DIRECTIVE, null);
    r = consumeToken(b, DC);
    p = r; // pin = 1
    r = r && report_error_(b, dc_directive_1(b, l + 1));
    r = p && report_error_(b, dc_element(b, l + 1)) && r;
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

  // (COMMA dc_element)*
  private static boolean dc_directive_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!dc_directive_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "dc_directive_3", c)) break;
    }
    return true;
  }

  // COMMA dc_element
  private static boolean dc_directive_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dc_directive_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && dc_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression
  static boolean dc_element(PsiBuilder b, int l) {
    return expression(b, l + 1, -1);
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
    r = p && report_error_(b, expression(b, l + 1, -1)) && r;
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
    r = r && expression(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // equ_directive |
  //                        blk_directive |
  //                        equals_directive |
  //                        equr_directive |
  //                        even_directive |
  //                        odd_directive |
  //                        incbin_directive |
  //                        incdir_directive |
  //                        include_directive |
  //                        dc_directive |
  //                        dcb_directive |
  //                        ds_directive |
  //                        rsset_directive |
  //                        rsreset_directive |
  //                        rs_directive |
  //                        opt_directive |
  //                        macro_directive |
  //                        endm_directive
  static boolean directives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "directives")) return false;
    boolean r;
    r = equ_directive(b, l + 1);
    if (!r) r = blk_directive(b, l + 1);
    if (!r) r = equals_directive(b, l + 1);
    if (!r) r = equr_directive(b, l + 1);
    if (!r) r = even_directive(b, l + 1);
    if (!r) r = odd_directive(b, l + 1);
    if (!r) r = incbin_directive(b, l + 1);
    if (!r) r = incdir_directive(b, l + 1);
    if (!r) r = include_directive(b, l + 1);
    if (!r) r = dc_directive(b, l + 1);
    if (!r) r = dcb_directive(b, l + 1);
    if (!r) r = ds_directive(b, l + 1);
    if (!r) r = rsset_directive(b, l + 1);
    if (!r) r = rsreset_directive(b, l + 1);
    if (!r) r = rs_directive(b, l + 1);
    if (!r) r = opt_directive(b, l + 1);
    if (!r) r = macro_directive(b, l + 1);
    if (!r) r = endm_directive(b, l + 1);
    return r;
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
  // DS data_size_all? expression
  public static boolean ds_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ds_directive")) return false;
    if (!nextTokenIs(b, DS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, DS_DIRECTIVE, null);
    r = consumeToken(b, DS);
    p = r; // pin = 1
    r = r && report_error_(b, ds_directive_1(b, l + 1));
    r = p && expression(b, l + 1, -1) && r;
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
  // program_counter_indirect |
  //                               (ID L_PAREN address_register R_PAREN) |   // todo label_reference "jsr     _LVODoIO(a6)"
  //                               expression |
  //                               label_reference
  static boolean effective_address(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "effective_address")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, null, "<effective address>");
    r = program_counter_indirect(b, l + 1);
    if (!r) r = effective_address_1(b, l + 1);
    if (!r) r = expression(b, l + 1, -1);
    if (!r) r = label_reference(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ID L_PAREN address_register R_PAREN
  private static boolean effective_address_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "effective_address_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, L_PAREN);
    r = r && address_register(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, m, null, r);
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
  // label EQU expression
  public static boolean equ_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equ_directive")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQU_DIRECTIVE, null);
    r = label(b, l + 1);
    r = r && consumeToken(b, EQU);
    p = r; // pin = 2
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // label EQ expression
  public static boolean equals_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equals_directive")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQUALS_DIRECTIVE, null);
    r = label(b, l + 1);
    r = r && consumeToken(b, EQ);
    p = r; // pin = 2
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // label EQUR (data_register | address_register)
  public static boolean equr_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equr_directive")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EQUR_DIRECTIVE, null);
    r = label(b, l + 1);
    r = r && consumeToken(b, EQUR);
    p = r; // pin = 2
    r = r && equr_directive_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_register | address_register
  private static boolean equr_directive_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equr_directive_2")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = address_register(b, l + 1);
    return r;
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
  // EXG data_size_long? any_register COMMA any_register
  public static boolean exg_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "exg_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", EXG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EXG_INSTRUCTION, "<instruction>");
    r = consumeToken(b, EXG);
    p = r; // pin = 1
    r = r && report_error_(b, exg_instruction_1(b, l + 1));
    r = p && report_error_(b, any_register(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && any_register(b, l + 1) && r;
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
  // EXT data_size_word_long? data_register
  public static boolean ext_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ext_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", EXT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EXT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, EXT);
    p = r; // pin = 1
    r = r && report_error_(b, ext_instruction_1(b, l + 1));
    r = p && data_register(b, l + 1) && r;
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
  // HASH expression
  public static boolean immediate_data(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "immediate_data")) return false;
    if (!nextTokenIs(b, "<immediate data>", HASH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IMMEDIATE_DATA, "<immediate data>");
    r = consumeToken(b, HASH);
    p = r; // pin = 1
    r = r && expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // INCBIN include_path
  public static boolean incbin_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "incbin_directive")) return false;
    if (!nextTokenIs(b, INCBIN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCBIN_DIRECTIVE, null);
    r = consumeToken(b, INCBIN);
    p = r; // pin = 1
    r = r && include_path(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
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
  // directives |
  //                          move_instructions |
  //                          misc_instructions |
  //                          jump_instructions |
  //                          add_sub_instructions |
  //                          mul_div_instructions |
  //                          bcd_instructions|
  //                          cmp_instructions |
  //                          bool_instructions |
  //                          bit_instructions |
  //                          shift_instructions |
  //                          bCC_instructions |
  //                          dbCC_instructions |
  //                          sCC_instructions
  static boolean instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "instructions")) return false;
    boolean r;
    r = directives(b, l + 1);
    if (!r) r = move_instructions(b, l + 1);
    if (!r) r = misc_instructions(b, l + 1);
    if (!r) r = jump_instructions(b, l + 1);
    if (!r) r = add_sub_instructions(b, l + 1);
    if (!r) r = mul_div_instructions(b, l + 1);
    if (!r) r = bcd_instructions(b, l + 1);
    if (!r) r = cmp_instructions(b, l + 1);
    if (!r) r = bool_instructions(b, l + 1);
    if (!r) r = bit_instructions(b, l + 1);
    if (!r) r = shift_instructions(b, l + 1);
    if (!r) r = bCC_instructions(b, l + 1);
    if (!r) r = dbCC_instructions(b, l + 1);
    if (!r) r = sCC_instructions(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // JMP effective_address
  public static boolean jmp_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jmp_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", JMP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, JMP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, JMP);
    p = r; // pin = 1
    r = r && effective_address(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // JSR effective_address
  public static boolean jsr_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "jsr_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", JSR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, JSR_INSTRUCTION, "<instruction>");
    r = consumeToken(b, JSR);
    p = r; // pin = 1
    r = r && effective_address(b, l + 1);
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
  // ID COLON?
  public static boolean label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
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
  public static boolean label_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label_reference")) return false;
    if (!nextTokenIs(b, "<label>", ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LABEL_REFERENCE, "<label>");
    r = consumeToken(b, ID);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // label | localLabel
  static boolean labels(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "labels")) return false;
    if (!nextTokenIs(b, "", DOT, ID)) return false;
    boolean r;
    r = label(b, l + 1);
    if (!r) r = localLabel(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // LEA data_size_long? effective_address COMMA (address_register | label_reference)
  public static boolean lea_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lea_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", LEA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LEA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, LEA);
    p = r; // pin = 1
    r = r && report_error_(b, lea_instruction_1(b, l + 1));
    r = p && report_error_(b, effective_address(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && lea_instruction_4(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_long?
  private static boolean lea_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lea_instruction_1")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  // address_register | label_reference
  private static boolean lea_instruction_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lea_instruction_4")) return false;
    boolean r;
    r = address_register(b, l + 1);
    if (!r) r = label_reference(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // LINK address_register COMMA immediate_data
  public static boolean link_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "link_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", LINK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LINK_INSTRUCTION, "<instruction>");
    r = consumeToken(b, LINK);
    p = r; // pin = 1
    r = r && report_error_(b, address_register(b, l + 1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && immediate_data(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DOT ID COLON?
  public static boolean localLabel(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel")) return false;
    if (!nextTokenIs(b, "<local label>", DOT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LOCAL_LABEL, "<local label>");
    r = consumeTokens(b, 1, DOT, ID);
    p = r; // pin = 1
    r = r && localLabel_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // COLON?
  private static boolean localLabel_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "localLabel_2")) return false;
    consumeToken(b, COLON);
    return true;
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
  // label MACRO
  public static boolean macro_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "macro_directive")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = label(b, l + 1);
    r = r && consumeToken(b, MACRO);
    exit_section_(b, m, MACRO_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // tst_instruction |
  //                               tas_instruction |
  //                               lea_instruction |
  //                               pea_instruction |
  //                               clr_instruction |
  //                               nop_instruction |
  //                               illegal_instruction |
  //                               reset_instruction |
  //                               trap_instruction |
  //                               trapv_instruction |
  //                               link_instruction |
  //                               unlk_instruction |
  //                               ext_instruction |
  //                               neg_instruction |
  //                               negx_instruction |
  //                               swap_instruction |
  //                               chk_instruction |
  //                               exg_instruction
  static boolean misc_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "misc_instructions")) return false;
    boolean r;
    r = tst_instruction(b, l + 1);
    if (!r) r = tas_instruction(b, l + 1);
    if (!r) r = lea_instruction(b, l + 1);
    if (!r) r = pea_instruction(b, l + 1);
    if (!r) r = clr_instruction(b, l + 1);
    if (!r) r = nop_instruction(b, l + 1);
    if (!r) r = illegal_instruction(b, l + 1);
    if (!r) r = reset_instruction(b, l + 1);
    if (!r) r = trap_instruction(b, l + 1);
    if (!r) r = trapv_instruction(b, l + 1);
    if (!r) r = link_instruction(b, l + 1);
    if (!r) r = unlk_instruction(b, l + 1);
    if (!r) r = ext_instruction(b, l + 1);
    if (!r) r = neg_instruction(b, l + 1);
    if (!r) r = negx_instruction(b, l + 1);
    if (!r) r = swap_instruction(b, l + 1);
    if (!r) r = chk_instruction(b, l + 1);
    if (!r) r = exg_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MOVE data_size_all?
  //                      (any_register | immediate_data) COMMA (any_register | effective_address)
  public static boolean move_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVE_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVE);
    p = r; // pin = 1
    r = r && report_error_(b, move_instruction_1(b, l + 1));
    r = p && report_error_(b, move_instruction_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && move_instruction_4(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean move_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // any_register | immediate_data
  private static boolean move_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instruction_2")) return false;
    boolean r;
    r = any_register(b, l + 1);
    if (!r) r = immediate_data(b, l + 1);
    return r;
  }

  // any_register | effective_address
  private static boolean move_instruction_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instruction_4")) return false;
    boolean r;
    r = any_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // move_instruction |
  //                               movea_instruction |
  //                               moveq_instruction |
  //                               movem_instruction
  static boolean move_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "move_instructions")) return false;
    boolean r;
    r = move_instruction(b, l + 1);
    if (!r) r = movea_instruction(b, l + 1);
    if (!r) r = moveq_instruction(b, l + 1);
    if (!r) r = movem_instruction(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MOVEA data_size_word_long?
  //                       (any_register | effective_address | immediate_data) COMMA address_register
  public static boolean movea_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movea_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVEA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVEA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVEA);
    p = r; // pin = 1
    r = r && report_error_(b, movea_instruction_1(b, l + 1));
    r = p && report_error_(b, movea_instruction_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && address_register(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_word_long?
  private static boolean movea_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movea_instruction_1")) return false;
    data_size_word_long(b, l + 1);
    return true;
  }

  // any_register | effective_address | immediate_data
  private static boolean movea_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movea_instruction_2")) return false;
    boolean r;
    r = any_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    if (!r) r = immediate_data(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MOVEM data_size_word_long?
  //                       (
  //                         (register_list COMMA address_register_indirect) |
  //                         (address_register_indirect COMMA register_list)
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

  // (register_list COMMA address_register_indirect) |
  //                         (address_register_indirect COMMA register_list)
  private static boolean movem_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_instruction_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = movem_instruction_2_0(b, l + 1);
    if (!r) r = movem_instruction_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // register_list COMMA address_register_indirect
  private static boolean movem_instruction_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_instruction_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = register_list(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && address_register_indirect(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // address_register_indirect COMMA register_list
  private static boolean movem_instruction_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "movem_instruction_2_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = address_register_indirect(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && register_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MOVEQ data_size_long?
  //                       immediate_data COMMA data_register
  public static boolean moveq_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "moveq_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", MOVEQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MOVEQ_INSTRUCTION, "<instruction>");
    r = consumeToken(b, MOVEQ);
    p = r; // pin = 1
    r = r && report_error_(b, moveq_instruction_1(b, l + 1));
    r = p && report_error_(b, immediate_data(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && data_register(b, l + 1) && r;
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
  //                          (data_register | effective_address | immediate_data) COMMA data_register
  static boolean mul_div_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_div_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = mul_div_tail_0(b, l + 1);
    r = r && mul_div_tail_1(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && data_register(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_word?
  private static boolean mul_div_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_div_tail_0")) return false;
    data_size_word(b, l + 1);
    return true;
  }

  // data_register | effective_address | immediate_data
  private static boolean mul_div_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_div_tail_1")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    if (!r) r = immediate_data(b, l + 1);
    return r;
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
  // NBCD data_size_byte? any_register
  public static boolean nbcd_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "nbcd_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NBCD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NBCD_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NBCD);
    p = r; // pin = 1
    r = r && report_error_(b, nbcd_instruction_1(b, l + 1));
    r = p && any_register(b, l + 1) && r;
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
  // NEG data_size_all? (data_register | effective_address)
  public static boolean neg_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "neg_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NEG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NEG_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NEG);
    p = r; // pin = 1
    r = r && report_error_(b, neg_instruction_1(b, l + 1));
    r = p && neg_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean neg_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "neg_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // data_register | effective_address
  private static boolean neg_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "neg_instruction_2")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // NEGX data_size_all? (data_register | effective_address)
  public static boolean negx_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "negx_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NEGX)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NEGX_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NEGX);
    p = r; // pin = 1
    r = r && report_error_(b, negx_instruction_1(b, l + 1));
    r = p && negx_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean negx_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "negx_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // data_register | effective_address
  private static boolean negx_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "negx_instruction_2")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    return r;
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
  // NOT data_size_all? (data_register | effective_address)
  public static boolean not_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", NOT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, NOT_INSTRUCTION, "<instruction>");
    r = consumeToken(b, NOT);
    p = r; // pin = 1
    r = r && report_error_(b, not_instruction_1(b, l + 1));
    r = p && not_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean not_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // data_register | effective_address
  private static boolean not_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_instruction_2")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
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
  // PEA data_size_long? any_register
  public static boolean pea_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pea_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", PEA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PEA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, PEA);
    p = r; // pin = 1
    r = r && report_error_(b, pea_instruction_1(b, l + 1));
    r = p && any_register(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_long?
  private static boolean pea_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pea_instruction_1")) return false;
    data_size_long(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // PC
  static boolean program_counter(PsiBuilder b, int l) {
    return consumeToken(b, PC);
  }

  /* ********************************************************** */
  // ID L_PAREN program_counter R_PAREN
  static boolean program_counter_indirect(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "program_counter_indirect")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, ID, L_PAREN);
    r = r && program_counter(b, l + 1);
    r = r && consumeToken(b, R_PAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // register_range (DIV register_range)*
  public static boolean register_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, REGISTER_LIST, "<register list>");
    r = register_range(b, l + 1);
    r = r && register_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
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
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DIV);
    r = r && register_range(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // register_range_element (MINUS register_range_element)?
  public static boolean register_range(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, REGISTER_RANGE, "<register range>");
    r = register_range_element(b, l + 1);
    r = r && register_range_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (MINUS register_range_element)?
  private static boolean register_range_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range_1")) return false;
    register_range_1_0(b, l + 1);
    return true;
  }

  // MINUS register_range_element
  private static boolean register_range_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MINUS);
    r = r && register_range_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // data_register | address_register
  static boolean register_range_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_range_element")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = address_register(b, l + 1);
    return r;
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
  // instructions | labels
  static boolean root_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = instructions(b, l + 1);
    if (!r) r = labels(b, l + 1);
    exit_section_(b, l, m, r, false, M68kParser::root_item_recover);
    return r;
  }

  /* ********************************************************** */
  // !(instructions | labels)
  static boolean root_item_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !root_item_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // instructions | labels
  private static boolean root_item_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item_recover_0")) return false;
    boolean r;
    r = instructions(b, l + 1);
    if (!r) r = labels(b, l + 1);
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
  // RS data_size_all? expression
  public static boolean rs_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rs_directive")) return false;
    if (!nextTokenIs(b, RS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RS_DIRECTIVE, null);
    r = consumeToken(b, RS);
    p = r; // pin = 1
    r = r && report_error_(b, rs_directive_1(b, l + 1));
    r = p && expression(b, l + 1, -1) && r;
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
    r = r && expression(b, l + 1, -1);
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
  //                              scs_instruction
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
    return r;
  }

  /* ********************************************************** */
  // data_size_byte? (any_register | effective_address)
  static boolean sCC_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sCC_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sCC_tail_0(b, l + 1);
    r = r && sCC_tail_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_byte?
  private static boolean sCC_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sCC_tail_0")) return false;
    data_size_byte(b, l + 1);
    return true;
  }

  // any_register | effective_address
  private static boolean sCC_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sCC_tail_1")) return false;
    boolean r;
    r = any_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    return r;
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
  //                                asr_instruction |
  //                                lsl_instruction |
  //                                lsr_instruction |
  //                                rol_instruction |
  //                                ror_instruction |
  //                                roxl_instruction |
  //                                roxr_instruction
  static boolean shift_instructions(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_instructions")) return false;
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
  // data_size_all?
  //                        (immediate_data | data_register) COMMA (data_register | effective_address)
  static boolean shift_tail(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = shift_tail_0(b, l + 1);
    r = r && shift_tail_1(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && shift_tail_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // data_size_all?
  private static boolean shift_tail_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_0")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // immediate_data | data_register
  private static boolean shift_tail_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_1")) return false;
    boolean r;
    r = immediate_data(b, l + 1);
    if (!r) r = data_register(b, l + 1);
    return r;
  }

  // data_register | effective_address
  private static boolean shift_tail_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "shift_tail_3")) return false;
    boolean r;
    r = data_register(b, l + 1);
    if (!r) r = effective_address(b, l + 1);
    return r;
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
  // SR
  static boolean status_register(PsiBuilder b, int l) {
    return consumeToken(b, SR);
  }

  /* ********************************************************** */
  // SUB add_sub_tail
  public static boolean sub_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUB_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUB);
    p = r; // pin = 1
    r = r && add_sub_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SUBA add_sub_a_tail
  public static boolean suba_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "suba_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUBA)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUBA_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUBA);
    p = r; // pin = 1
    r = r && add_sub_a_tail(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // SUBI add_sub_i_tail
  public static boolean subi_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "subi_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SUBI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SUBI_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SUBI);
    p = r; // pin = 1
    r = r && add_sub_i_tail(b, l + 1);
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
  // SSP
  static boolean supervisor_stack_pointer(PsiBuilder b, int l) {
    return consumeToken(b, SSP);
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
  // SWAP data_size_word? data_register
  public static boolean swap_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "swap_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", SWAP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SWAP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, SWAP);
    p = r; // pin = 1
    r = r && report_error_(b, swap_instruction_1(b, l + 1));
    r = p && data_register(b, l + 1) && r;
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
  // TAS data_size_byte? any_register
  public static boolean tas_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tas_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", TAS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAS_INSTRUCTION, "<instruction>");
    r = consumeToken(b, TAS);
    p = r; // pin = 1
    r = r && report_error_(b, tas_instruction_1(b, l + 1));
    r = p && any_register(b, l + 1) && r;
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
  // TRAP immediate_data
  public static boolean trap_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "trap_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", TRAP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TRAP_INSTRUCTION, "<instruction>");
    r = consumeToken(b, TRAP);
    p = r; // pin = 1
    r = r && immediate_data(b, l + 1);
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
  // TST data_size_all? (effective_address | any_register)
  public static boolean tst_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tst_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", TST)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TST_INSTRUCTION, "<instruction>");
    r = consumeToken(b, TST);
    p = r; // pin = 1
    r = r && report_error_(b, tst_instruction_1(b, l + 1));
    r = p && tst_instruction_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // data_size_all?
  private static boolean tst_instruction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tst_instruction_1")) return false;
    data_size_all(b, l + 1);
    return true;
  }

  // effective_address | any_register
  private static boolean tst_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tst_instruction_2")) return false;
    boolean r;
    r = effective_address(b, l + 1);
    if (!r) r = any_register(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // UNLK address_register
  public static boolean unlk_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unlk_instruction")) return false;
    if (!nextTokenIs(b, "<instruction>", UNLK)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, UNLK_INSTRUCTION, "<instruction>");
    r = consumeToken(b, UNLK);
    p = r; // pin = 1
    r = r && address_register(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // USP
  static boolean user_stack_pointer(PsiBuilder b, int l) {
    return consumeToken(b, USP);
  }

  /* ********************************************************** */
  // Expression root: expression
  // Operator priority table:
  // 0: BINARY(plus_expression) BINARY(minus_expression)
  // 1: BINARY(mul_expression) BINARY(div_expression) BINARY(mod_expression)
  // 2: PREFIX(unary_plus_expression) PREFIX(unary_minus_expression) PREFIX(unary_complement_expression)
  // 3: BINARY(exp_expression)
  // 4: BINARY(shift_left_expression) BINARY(shift_right_expression)
  // 5: BINARY(or_expression) BINARY(and_expression)
  // 6: ATOM(number_expression) ATOM(string_expression) ATOM(paren_expression) ATOM(label_ref_expression)
  public static boolean expression(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expression")) return false;
    addVariant(b, "<expression>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expression>");
    r = unary_plus_expression(b, l + 1);
    if (!r) r = unary_minus_expression(b, l + 1);
    if (!r) r = unary_complement_expression(b, l + 1);
    if (!r) r = number_expression(b, l + 1);
    if (!r) r = string_expression(b, l + 1);
    if (!r) r = paren_expression(b, l + 1);
    if (!r) r = label_ref_expression(b, l + 1);
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
      if (g < 0 && consumeTokenSmart(b, PLUS)) {
        r = expression(b, l, 0);
        exit_section_(b, l, m, PLUS_EXPRESSION, r, true, null);
      }
      else if (g < 0 && consumeTokenSmart(b, MINUS)) {
        r = expression(b, l, 0);
        exit_section_(b, l, m, MINUS_EXPRESSION, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, MUL)) {
        r = expression(b, l, 1);
        exit_section_(b, l, m, MUL_EXPRESSION, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, DIV)) {
        r = expression(b, l, 1);
        exit_section_(b, l, m, DIV_EXPRESSION, r, true, null);
      }
      else if (g < 1 && consumeTokenSmart(b, PERCENT)) {
        r = expression(b, l, 1);
        exit_section_(b, l, m, MOD_EXPRESSION, r, true, null);
      }
      else if (g < 3 && consumeTokenSmart(b, POW)) {
        r = expression(b, l, 3);
        exit_section_(b, l, m, EXP_EXPRESSION, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, SHIFT_L)) {
        r = expression(b, l, 4);
        exit_section_(b, l, m, SHIFT_LEFT_EXPRESSION, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, SHIFT_R)) {
        r = expression(b, l, 4);
        exit_section_(b, l, m, SHIFT_RIGHT_EXPRESSION, r, true, null);
      }
      else if (g < 5 && consumeTokenSmart(b, PIPE)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, OR_EXPRESSION, r, true, null);
      }
      else if (g < 5 && consumeTokenSmart(b, AMPERS_AND)) {
        r = expression(b, l, 5);
        exit_section_(b, l, m, AND_EXPRESSION, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  public static boolean unary_plus_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_plus_expression")) return false;
    if (!nextTokenIsSmart(b, PLUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, PLUS);
    p = r;
    r = p && expression(b, l, 2);
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
    r = p && expression(b, l, 2);
    exit_section_(b, l, m, UNARY_MINUS_EXPRESSION, r, p, null);
    return r || p;
  }

  public static boolean unary_complement_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_complement_expression")) return false;
    if (!nextTokenIsSmart(b, TILDE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokenSmart(b, TILDE);
    p = r;
    r = p && expression(b, l, 2);
    exit_section_(b, l, m, UNARY_COMPLEMENT_EXPRESSION, r, p, null);
    return r || p;
  }

  // DEC_NUMBER | HEX_NUMBER | OCT_NUMBER | BIN_NUMBER
  public static boolean number_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "number_expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NUMBER_EXPRESSION, "<number expression>");
    r = consumeTokenSmart(b, DEC_NUMBER);
    if (!r) r = consumeTokenSmart(b, HEX_NUMBER);
    if (!r) r = consumeTokenSmart(b, OCT_NUMBER);
    if (!r) r = consumeTokenSmart(b, BIN_NUMBER);
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

  // L_PAREN expression R_PAREN
  public static boolean paren_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paren_expression")) return false;
    if (!nextTokenIsSmart(b, L_PAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PAREN_EXPRESSION, null);
    r = consumeTokenSmart(b, L_PAREN);
    p = r; // pin = 1
    r = r && report_error_(b, expression(b, l + 1, -1));
    r = p && consumeToken(b, R_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // label_reference
  public static boolean label_ref_expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label_ref_expression")) return false;
    if (!nextTokenIsSmart(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = label_reference(b, l + 1);
    exit_section_(b, m, LABEL_REF_EXPRESSION, r);
    return r;
  }

}
