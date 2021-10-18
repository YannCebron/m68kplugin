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
  DATA_REGISTER("Dn", M68kAdmDrd.class),
  /**
   * Ref 2.2.2, {@link M68kAdmArd}
   */
  ADDRESS_REGISTER("An", M68kAdmArd.class),

  /**
   * Ref 2.2.3, {@link M68kAdmAri}
   */
  ADDRESS_REGISTER_INDIRECT("(An)", M68kAdmAri.class),
  /**
   * Ref 2.2.4, {@link M68kAdmApi}
   */
  ADDRESS_REGISTER_INDIRECT_POST_INCREMENT("(An)+", M68kAdmApi.class),
  /**
   * Ref 2.2.5, {@link M68kAdmApd}
   */
  ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT("-(An)", M68kAdmApd.class),
  /**
   * Ref 2.2.6, {@link M68kAdmAdi}
   */
  ADDRESS_REGISTER_DISPLACEMENT("(d,An)", M68kAdmAdi.class),
  /**
   * Ref 2.2.8, {@link M68kAdmAix}
   */
  ADDRESS_REGISTER_INDEX_DISPLACEMENT("(d,An,Xi)", M68kAdmAix.class),

  /**
   * Ref 2.2.16, {@link M68kAdmAbs}
   */
  ABSOLUTE_SHORT("ABS.W", M68kAdmAbs.class),
  /**
   * Ref 2.2.17, {@link M68kAdmAbs}
   */
  ABSOLUTE_LONG("ABS.L", M68kAdmAbs.class),

  /**
   * {@link M68kAdmAbs} -> {@link M68kLabelRefExpression}
   */
  LABEL("label", M68kAdmAbs.class),

  /**
   * Ref 2.2.11, {@link M68kAdmPcd}
   */
  PC_REGISTER_DISPLACEMENT("(d,PC)", M68kAdmPcd.class),
  /**
   * Ref 2.2.13, {@link M68kAdmPci}
   */
  PC_REGISTER_INDEX_DISPLACEMENT("(d,PC,Xn)", M68kAdmPci.class),

  /**
   * Ref 2.2.18, {@link M68kAdmImm}
   */
  IMMEDIATE("imm", M68kAdmImm.class),
  /**
   * Ref 2.2.18, {@link M68kAdmQuick}
   */
  QUICK_IMMEDIATE("quick", M68kAdmQuick.class),

  /**
   * {@link M68kAdmRegisterList}
   */
  REGISTER_LIST("Rn list", M68kAdmRegisterList.class),

  /**
   * {@link M68kAdmSr}
   */
  SPECIAL_REGISTER_SR("SR", M68kAdmSr.class),
  /**
   * {@link M68kAdmUsp}
   */
  SPECIAL_REGISTER_USP("USP", M68kAdmUsp.class),
  /**
   * {@link M68kAdmCcr}
   */
  SPECIAL_REGISTER_CCR("CCR", M68kAdmCcr.class),

  /**
   * {@link M68kAdmDfc}
   * {@link M68kAdmSfc}
   * {@link M68kAdmVbr}
   */
  CONTROL_REGISTER("CTRL", M68kAdmDfc.class, M68kAdmSfc.class, M68kAdmVbr.class);

  private final String notation;
  private final Class<? extends M68kAdm>[] admClasses;

  @SafeVarargs
  M68kAddressMode(String notation, Class<? extends M68kAdm>... admClasses) {
    this.notation = notation;
    this.admClasses = admClasses;
  }

  public String getNotation() {
    return notation;
  }

  public Class<? extends M68kAdm>[] getAdmClasses() {
    return admClasses;
  }
}