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
import static com.yanncebron.m68kplugin.lang.M68kParser.*;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class M68kConditionalAssemblyParser {

  /* ********************************************************** */
  // ifnd_conditional_assembly_directive |
  //                                             endc_conditional_assembly_directive |
  //                                             if_conditional_assembly_directive |
  //                                             ifd_conditional_assembly_directive |
  //                                             ifeq_conditional_assembly_directive |
  //                                             ifge_conditional_assembly_directive |
  //                                             ifgt_conditional_assembly_directive |
  //                                             ifle_conditional_assembly_directive |
  //                                             iflt_conditional_assembly_directive |
  //                                             ifne_conditional_assembly_directive |
  //                                             ifmi_conditional_assembly_directive |
  //                                             ifpl_conditional_assembly_directive |
  //                                             ifb_conditional_assembly_directive |
  //                                             ifnb_conditional_assembly_directive |
  //                                             ifc_conditional_assembly_directive |
  //                                             ifnc_conditional_assembly_directive |
  //                                             else_conditional_assembly_directive |
  //                                             elseif_conditional_assembly_directive |
  //                                             endif_conditional_assembly_directive |
  //                                             ifmacrod_conditional_assembly_directive |
  //                                             ifmacrond_conditional_assembly_directive
  static boolean conditional_assembly_directives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditional_assembly_directives")) return false;
    boolean r;
    r = ifnd_conditional_assembly_directive(b, l + 1);
    if (!r) r = endc_conditional_assembly_directive(b, l + 1);
    if (!r) r = if_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifd_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifeq_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifge_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifgt_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifle_conditional_assembly_directive(b, l + 1);
    if (!r) r = iflt_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifne_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifmi_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifpl_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifb_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifnb_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifc_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifnc_conditional_assembly_directive(b, l + 1);
    if (!r) r = else_conditional_assembly_directive(b, l + 1);
    if (!r) r = elseif_conditional_assembly_directive(b, l + 1);
    if (!r) r = endif_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifmacrod_conditional_assembly_directive(b, l + 1);
    if (!r) r = ifmacrond_conditional_assembly_directive(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ELSE
  public static boolean else_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ELSE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ELSE_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, ELSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ELSEIF
  public static boolean elseif_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseif_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ELSEIF)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ELSEIF_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, ELSEIF);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ENDC
  public static boolean endc_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endc_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ENDC)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENDC_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, ENDC);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ENDIF
  public static boolean endif_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endif_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", ENDIF)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENDIF_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, ENDIF);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // IF expression
  public static boolean if_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IF_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFB expression
  public static boolean ifb_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifb_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFB_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFB);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFC expression COMMA expression
  public static boolean ifc_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifc_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFC_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFC);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFD expression
  public static boolean ifd_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifd_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFD_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFD);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFEQ expression
  public static boolean ifeq_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifeq_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFEQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFEQ_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFEQ);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFGE expression
  public static boolean ifge_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifge_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFGE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFGE_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFGE);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFGT expression
  public static boolean ifgt_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifgt_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFGT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFGT_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFGT);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFLE expression
  public static boolean ifle_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifle_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFLE_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFLE);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFLT expression
  public static boolean iflt_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "iflt_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFLT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFLT_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFLT);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFMACROD expression
  public static boolean ifmacrod_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifmacrod_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFMACROD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFMACROD_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFMACROD);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFMACROND expression
  public static boolean ifmacrond_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifmacrond_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFMACROND)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFMACROND_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFMACROND);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFMI expression
  public static boolean ifmi_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifmi_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFMI)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFMI_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFMI);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFNB expression
  public static boolean ifnb_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifnb_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFNB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFNB_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFNB);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFNC expression COMMA expression
  public static boolean ifnc_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifnc_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFNC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFNC_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFNC);
    p = r; // pin = 1
    r = r && report_error_(b, M68kExpressionParser.expression(b, l + 1, -1));
    r = p && report_error_(b, consumeToken(b, COMMA)) && r;
    r = p && M68kExpressionParser.expression(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFND expression
  public static boolean ifnd_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifnd_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFND)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFND_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFND);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFNE expression
  public static boolean ifne_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifne_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFNE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFNE_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFNE);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFPL expression
  public static boolean ifpl_conditional_assembly_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifpl_conditional_assembly_directive")) return false;
    if (!nextTokenIs(b, "<directive>", IFPL)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFPL_CONDITIONAL_ASSEMBLY_DIRECTIVE, "<directive>");
    r = consumeToken(b, IFPL);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
