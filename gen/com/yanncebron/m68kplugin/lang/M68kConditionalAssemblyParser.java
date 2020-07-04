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
public class M68kConditionalAssemblyParser {

  /* ********************************************************** */
  // if_directive |
  //                                             ifd_directive |
  //                                             ifeq_directive |
  //                                             ifge_directive |
  //                                             ifgt_directive |
  //                                             ifnd_directive |
  //                                             ifle_directive |
  //                                             iflt_directive |
  //                                             ifne_directive |
  //                                             ifb_directive |
  //                                             ifnb_directive |
  //                                             ifc_directive |
  //                                             ifnc_directive |
  //                                             else_directive |
  //                                             elseif_directive |
  //                                             endc_directive
  static boolean conditional_assembly_directives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditional_assembly_directives")) return false;
    boolean r;
    r = if_directive(b, l + 1);
    if (!r) r = ifd_directive(b, l + 1);
    if (!r) r = ifeq_directive(b, l + 1);
    if (!r) r = ifge_directive(b, l + 1);
    if (!r) r = ifgt_directive(b, l + 1);
    if (!r) r = ifnd_directive(b, l + 1);
    if (!r) r = ifle_directive(b, l + 1);
    if (!r) r = iflt_directive(b, l + 1);
    if (!r) r = ifne_directive(b, l + 1);
    if (!r) r = ifb_directive(b, l + 1);
    if (!r) r = ifnb_directive(b, l + 1);
    if (!r) r = ifc_directive(b, l + 1);
    if (!r) r = ifnc_directive(b, l + 1);
    if (!r) r = else_directive(b, l + 1);
    if (!r) r = elseif_directive(b, l + 1);
    if (!r) r = endc_directive(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ELSE
  public static boolean else_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_directive")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    exit_section_(b, m, ELSE_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // ELSEIF
  public static boolean elseif_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elseif_directive")) return false;
    if (!nextTokenIs(b, ELSEIF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSEIF);
    exit_section_(b, m, ELSEIF_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // ENDC
  public static boolean endc_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endc_directive")) return false;
    if (!nextTokenIs(b, ENDC)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENDC);
    exit_section_(b, m, ENDC_DIRECTIVE, r);
    return r;
  }

  /* ********************************************************** */
  // IF expression
  public static boolean if_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "if_directive")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IF_DIRECTIVE, null);
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFB expression
  public static boolean ifb_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifb_directive")) return false;
    if (!nextTokenIs(b, IFB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFB_DIRECTIVE, null);
    r = consumeToken(b, IFB);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFC expression COMMA expression
  public static boolean ifc_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifc_directive")) return false;
    if (!nextTokenIs(b, IFC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFC_DIRECTIVE, null);
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
  public static boolean ifd_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifd_directive")) return false;
    if (!nextTokenIs(b, IFD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFD_DIRECTIVE, null);
    r = consumeToken(b, IFD);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFEQ expression
  public static boolean ifeq_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifeq_directive")) return false;
    if (!nextTokenIs(b, IFEQ)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFEQ_DIRECTIVE, null);
    r = consumeToken(b, IFEQ);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFGE expression
  public static boolean ifge_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifge_directive")) return false;
    if (!nextTokenIs(b, IFGE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFGE_DIRECTIVE, null);
    r = consumeToken(b, IFGE);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFGT expression
  public static boolean ifgt_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifgt_directive")) return false;
    if (!nextTokenIs(b, IFGT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFGT_DIRECTIVE, null);
    r = consumeToken(b, IFGT);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFLE expression
  public static boolean ifle_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifle_directive")) return false;
    if (!nextTokenIs(b, IFLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFLE_DIRECTIVE, null);
    r = consumeToken(b, IFLE);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFLT expression
  public static boolean iflt_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "iflt_directive")) return false;
    if (!nextTokenIs(b, IFLT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFLT_DIRECTIVE, null);
    r = consumeToken(b, IFLT);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFNB expression
  public static boolean ifnb_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifnb_directive")) return false;
    if (!nextTokenIs(b, IFNB)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFNB_DIRECTIVE, null);
    r = consumeToken(b, IFNB);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFNC expression COMMA expression
  public static boolean ifnc_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifnc_directive")) return false;
    if (!nextTokenIs(b, IFNC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFNC_DIRECTIVE, null);
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
  public static boolean ifnd_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifnd_directive")) return false;
    if (!nextTokenIs(b, IFND)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFND_DIRECTIVE, null);
    r = consumeToken(b, IFND);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // IFNE expression
  public static boolean ifne_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ifne_directive")) return false;
    if (!nextTokenIs(b, IFNE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IFNE_DIRECTIVE, null);
    r = consumeToken(b, IFNE);
    p = r; // pin = 1
    r = r && M68kExpressionParser.expression(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

}
