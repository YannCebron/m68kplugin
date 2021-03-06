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

/**
 * Translates to {@code Adm*} PSI.
 *
 * @see M68kOperand
 */
public enum M68kAddressMode {

  DATA_REGISTER,
  ADDRESS_REGISTER,

  ADDRESS_REGISTER_INDIRECT,
  ADDRESS_REGISTER_INDIRECT_POST_INCREMENT,
  ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT,
  ADDRESS_REGISTER_DISPLACEMENT,
  ADDRESS_REGISTER_INDEX_DISPLACEMENT,

  ABSOLUTE_SHORT,
  ABSOLUTE_LONG,

  LABEL,

  PC_REGISTER_DISPLACEMENT,
  PC_REGISTER_INDEX_DISPLACEMENT,

  IMMEDIATE,
  QUICK_IMMEDIATE,
  REGISTER_LIST,

  SPECIAL_REGISTER_SR,
  SPECIAL_REGISTER_USP,
  SPECIAL_REGISTER_CCR

}