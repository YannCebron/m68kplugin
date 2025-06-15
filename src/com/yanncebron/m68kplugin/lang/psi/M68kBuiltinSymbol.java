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

import com.intellij.icons.AllIcons;
import com.yanncebron.m68kplugin.compiler.M68kCompiler;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Builtin compiler symbols.
 *
 * @see M68kBuiltinSymbolPsiElement
 */
public enum M68kBuiltinSymbol {

  CURRENT_PC(M68kCompiler.ANY, AllIcons.Debugger.ShowCurrentFrame, "*", "Current PC"),
  __LINE__(M68kCompiler.ANY, AllIcons.General.LayoutEditorOnly, "__LINE__", "Current line number"),

  REPTN(M68kCompiler.ANY, null, "REPTN", "Repeat Count"),

  CARG(M68kCompiler.ANY, null, "CARG", "Current macro argument"),
  NARG(M68kCompiler.ANY, null, "NARG", "Number of macro arguments"),

  __RS(M68kCompiler.ANY, null, "__RS", "RS-counter value"),
  __SO(M68kCompiler.ANY, null, "__SO", "SO-counter value"),
  __FO(M68kCompiler.ANY, null, "__FO", "FO-counter value"),

  __G2(M68kCompiler.DEVPAC, null, "__G2", ""),
  _LK(M68kCompiler.DEVPAC, null, "_LK", ""),

  _MOVEMBYTES(M68kCompiler.BASM, null, "_MOVEMBYTES", ""),
  __MOVEMREGS(M68kCompiler.BASM, null, "__MOVEMREGS", ""),

  _PHXASS_(M68kCompiler.PHX_ASS, null, "_PHXASS_", "Set to 1 if PhxAss"),
  __CPU(M68kCompiler.PHX_ASS, null, "__CPU", "Current CPU type"),
  __FPU(M68kCompiler.PHX_ASS, null, "__FPU", "Current FPU type"),
  __MMU(M68kCompiler.PHX_ASS, null, "__MMU", "PMMU Generation allowed"),
  __OPTC(M68kCompiler.PHX_ASS, null, "__OPTC", "Optimization flags"),

  __VASM(M68kCompiler.VASM, null, "__VASM", "CPU type");

  private final M68kCompiler compiler;
  private final Icon icon;
  private final String name;
  private final String description;

  M68kBuiltinSymbol(M68kCompiler compiler, @Nullable Icon icon, @NonNls String name, String description) {
    this.compiler = compiler;
    this.icon = icon;
    this.name = name;
    this.description = description;
  }

  public M68kCompiler getCompiler() {
    return compiler;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @Nullable
  public Icon getIcon() {
    return icon;
  }

  private static final Map<String, M68kBuiltinSymbol> builtinSymbols = new HashMap<>();

  static {
    for (M68kBuiltinSymbol value : M68kBuiltinSymbol.values()) {
      builtinSymbols.put(value.getName(), value);
    }
  }

  @Nullable
  public static M68kBuiltinSymbol findByName(@NonNls String name) {
    return builtinSymbols.get(name);
  }
}
