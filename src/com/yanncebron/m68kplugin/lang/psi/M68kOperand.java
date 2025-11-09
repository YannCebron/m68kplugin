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

package com.yanncebron.m68kplugin.lang.psi;

/**
 * @see M68kAddressMode
 */
public enum M68kOperand {

  /**
   * vasm: {@code 0}
   */
  NONE,

  /**
   * vasm: {@code IM}
   */
  IMMEDIATE(M68kAddressMode.IMMEDIATE),
  /**
   * vasm: {@code QI}
   */
  QUICK_IMMEDIATE(M68kAddressMode.QUICK_IMMEDIATE),

  /**
   * vasm: {@code BR}
   */
  BRANCH_DESTINATION(M68kAddressMode.LABEL),
  /**
   * vasm: {@code DB}
   */
  DBCC_BRANCH_DESTINATION(M68kAddressMode.LABEL),

  /**
   * vasm: {@code CT}
   */
  CONTROL(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT),
  /**
   * vasm: {@code AC}
   */
  ALTERABLE_CONTROL(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),

  /**
   * vasm: {@code MR}
   */
  RESTORE_OPERANDS(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT),
  /**
   * vasm: {@code IR}
   */
  IMMEDIATE_REGISTER_LIST_VALUE(M68kAddressMode.IMMEDIATE),

  /**
   * vasm: {@code AY}
   */
  ALL(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.IMMEDIATE),
  /**
   * vasm: {@code DA}
   */
  DATA(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.IMMEDIATE,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT),
  /**
   * vasm: {@code MA}
   */
  MEMORY(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.IMMEDIATE),
  /**
   * vasm: {@code MI}
   */
  MEMORY_WITHOUT_IMMEDIATE(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT),

  /**
   * vasm: {@code AL}
   */
  ALTERABLE(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  /**
   * vasm: {@code AD}
   */
  ALTERABLE_DATA(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  /**
   * vasm: {@code CFAD}
   */
  ALTERABLE_DATA_CF(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  /**
   * vasm: {@code AM}
   */
  ALTERABLE_MEMORY(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  /**
   * vasm: {@code CFAM}
   */
  ALTERABLE_MEMORY_CF(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),

  /**
   * vasm: {@code D_}
   */
  DATA_REGISTER(M68kAddressMode.DATA_REGISTER),
  /**
   * vasm: {@code A_}
   */
  ADDRESS_REGISTER(M68kAddressMode.ADDRESS_REGISTER),

  /**
   * vasm: {@code R_}
   */
  DATA_OR_ADDRESS_REGISTER(M68kAddressMode.DATA_REGISTER, M68kAddressMode.ADDRESS_REGISTER),
  /**
   * vasm: {@code RL}
   */
  DATA_OR_ADDRESS_REGISTER_LIST(M68kAddressMode.REGISTER_LIST),

  /**
   * vasm: {@code PA}
   */
  ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT),
  /**
   * vasm: {@code AP}
   */
  ADDRESS_REGISTER_INDIRECT_POST_INCREMENT(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT),
  /**
   * vasm: {@code DP}
   */
  ADDRESS_REGISTER_DISPLACEMENT(M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT),
  /**
   * vasm: {@code ???}
   */
  ADDRESS_REGISTER_INDEX_DISPLACEMENT(M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT),

  SR_REGISTER(M68kAddressMode.SPECIAL_REGISTER_SR),
  USP_REGISTER(M68kAddressMode.SPECIAL_REGISTER_USP),
  CCR_REGISTER(M68kAddressMode.SPECIAL_REGISTER_CCR),

  CTRL_REGISTER(M68kAddressMode.CONTROL_REGISTER);

  private final M68kAddressMode[] addressModes;

  M68kOperand(M68kAddressMode... addressModes) {
    this.addressModes = addressModes;
  }

  public M68kAddressMode[] getAddressModes() {
    return addressModes;
  }
}
