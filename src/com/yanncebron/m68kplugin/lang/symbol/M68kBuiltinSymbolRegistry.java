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

package com.yanncebron.m68kplugin.lang.symbol;

import com.yanncebron.m68kplugin.compiler.M68kCompiler;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

enum M68kBuiltinSymbolRegistry {

  STAR(M68kCompiler.ANY, null, "*", ""),
  __LINE__(M68kCompiler.ANY, null, "__LINE__", "Current line number"),

  // todo inside `rept` only
  REPTN(M68kCompiler.ANY, null, "REPTN", "Repeat Count"),

  // todo inside `macro` only
  CARG(M68kCompiler.ANY, null, "CARG", ""),
  NARG(M68kCompiler.ANY, null, "NARG", "Number of macro arguments"),

  __G2(M68kCompiler.DEVPAC, null, "__G2", ""),
  _LK(M68kCompiler.DEVPAC, null, "_LK", ""),
  __RS(M68kCompiler.DEVPAC, null, "__RS", ""),
  __SO(M68kCompiler.DEVPAC, null, "__SO", ""),
  __FO(M68kCompiler.DEVPAC, null, "__FO", ""),

  _MOVEMBYTES(M68kCompiler.BASM, null, "_MOVEMBYTES", ""),
  __MOVEMREGS(M68kCompiler.BASM, null, "__MOVEMREGS", ""),

  _PHXASS_(M68kCompiler.PHX_ASS, null, "_PHXASS_", ""),
  __CPU(M68kCompiler.PHX_ASS, null, "__CPU", ""),
  __FPU(M68kCompiler.PHX_ASS, null, "__FPU", ""),
  __MMU(M68kCompiler.PHX_ASS, null, "__MMU", ""),
  __OPTC(M68kCompiler.PHX_ASS, null, "__OPTC", ""),

  __VASM(M68kCompiler.VASM, null, "__VASM", "");

  private final M68kCompiler compiler;
  private final Icon icon;
  private final String name;
  private final String description;

  M68kBuiltinSymbolRegistry(M68kCompiler compiler, @Nullable Icon icon, @NonNls String name, String description) {
    this.compiler = compiler;
    this.icon = icon;
    this.name = name;
    this.description = description;
  }

  M68kCompiler getCompiler() {
    return compiler;
  }

  String getName() {
    return name;
  }

  String getDescription() {
    return description;
  }

  @Nullable
  Icon getIcon() {
    return icon;
  }
}
