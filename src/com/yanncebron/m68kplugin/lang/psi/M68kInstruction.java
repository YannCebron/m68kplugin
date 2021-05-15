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

import org.jetbrains.annotations.NotNull;

/**
 * Base interface for all M68k Instructions.
 */
public interface M68kInstruction extends M68kPsiElement {

  /**
   * @param m68kCpu target machine
   * @return {@code true} if the instruction requires supervisor privilege for the given target machine, {@code false} otherwise.
   */
  default boolean isPrivileged(@NotNull M68kCpu m68kCpu) {
    return false;
  }
}
