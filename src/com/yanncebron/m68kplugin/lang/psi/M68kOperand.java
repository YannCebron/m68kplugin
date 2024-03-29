/*
 * Copyright 2022 The Authors
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

  NONE,

  IMMEDIATE(M68kAddressMode.IMMEDIATE),
  QUICK_IMMEDIATE(M68kAddressMode.QUICK_IMMEDIATE),

  BRANCH_DESTINATION(M68kAddressMode.LABEL),
  DBCC_BRANCH_DESTINATION(M68kAddressMode.LABEL),

  CONTROL(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT),
  ALTERABLE_CONTROL(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),

  RESTORE_OPERANDS(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT),
  IMMEDIATE_REGISTER_LIST_VALUE(M68kAddressMode.IMMEDIATE),

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
  MEMORY_WITHOUT_IMMEDIATE(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG,
    M68kAddressMode.PC_REGISTER_DISPLACEMENT,
    M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT),

  ALTERABLE(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  ALTERABLE_DATA(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  ALTERABLE_DATA_CF(M68kAddressMode.DATA_REGISTER,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  ALTERABLE_MEMORY(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),
  ALTERABLE_MEMORY_CF(M68kAddressMode.ADDRESS_REGISTER_INDIRECT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
    M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT,
    M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT,
    M68kAddressMode.ABSOLUTE_SHORT,
    M68kAddressMode.ABSOLUTE_LONG),

  DATA_REGISTER(M68kAddressMode.DATA_REGISTER),
  ADDRESS_REGISTER(M68kAddressMode.ADDRESS_REGISTER),

  DATA_OR_ADDRESS_REGISTER(M68kAddressMode.DATA_REGISTER, M68kAddressMode.ADDRESS_REGISTER),
  DATA_OR_ADDRESS_REGISTER_LIST(M68kAddressMode.REGISTER_LIST),

  ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT),
  ADDRESS_REGISTER_INDIRECT_POST_INCREMENT(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT),
  ADDRESS_REGISTER_DISPLACEMENT(M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT),
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
