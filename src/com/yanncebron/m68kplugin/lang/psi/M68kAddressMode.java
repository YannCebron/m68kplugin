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

package com.yanncebron.m68kplugin.lang.psi;

import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;

/**
 * Translates to {@code Adm*} PSI.
 *
 * @see M68kAdm
 * @see M68kOperand
 */
public enum M68kAddressMode {

  /**
   * Ref 2.2.1, {@link M68kAdmDrd}
   */
  DATA_REGISTER("Dn"),
  /**
   * Ref 2.2.2, {@link M68kAdmArd}
   */
  ADDRESS_REGISTER("An"),

  /**
   * Ref 2.2.3, {@link M68kAdmAri}
   */
  ADDRESS_REGISTER_INDIRECT("(An)"),
  /**
   * Ref 2.2.4, {@link M68kAdmApi}
   */
  ADDRESS_REGISTER_INDIRECT_POST_INCREMENT("(An)+"),
  /**
   * Ref 2.2.5, {@link M68kAdmApd}
   */
  ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT("-(An)"),
  /**
   * Ref 2.2.6, {@link M68kAdmAdi}
   */
  ADDRESS_REGISTER_DISPLACEMENT("(d,An)"),
  /**
   * Ref 2.2.8, {@link M68kAdmAix}
   */
  ADDRESS_REGISTER_INDEX_DISPLACEMENT("(d,An,Xi)"),

  /**
   * Ref 2.2.16, {@link M68kAdmAbs}
   */
  ABSOLUTE_SHORT("ABS.W"),
  /**
   * Ref 2.2.17, {@link M68kAdmAbs}
   */
  ABSOLUTE_LONG("ABS.L"),

  /**
   * {@link M68kAdmAbs} -> {@link M68kLabelRefExpression}
   */
  LABEL("label"),

  /**
   * Ref 2.2.11, {@link M68kAdmPcd}
   */
  PC_REGISTER_DISPLACEMENT("(d,PC)"),
  /**
   * Ref 2.2.13, {@link M68kAdmPci}
   */
  PC_REGISTER_INDEX_DISPLACEMENT("(d,PC,Xn)"),

  /**
   * Ref 2.2.18, {@link M68kAdmImm}
   */
  IMMEDIATE("imm"),
  /**
   * Ref 2.2.18, {@link M68kAdmQuick}
   */
  QUICK_IMMEDIATE("quick"),

  /**
   * {@link M68kRegisterList}
   */
  REGISTER_LIST("Rn list"),

  /**
   * {@link M68kAdmSr}
   */
  SPECIAL_REGISTER_SR("SR"),
  /**
   * {@link M68kAdmUsp}
   */
  SPECIAL_REGISTER_USP("USP"),
  /**
   * {@link M68kAdmCcr}
   */
  SPECIAL_REGISTER_CCR("CCR"),

  /**
   * {@link M68kAdmDfc}
   * {@link M68kAdmSfc}
   * {@link M68kAdmVbr}
   */
  CONTROL_REGISTER("CTRL");

  private final String notation;

  M68kAddressMode(String notation) {
    this.notation = notation;
  }

  public String getNotation() {
    return notation;
  }
}