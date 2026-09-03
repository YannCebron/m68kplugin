/*
 * Copyright 2026 The Authors
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
import com.yanncebron.m68kplugin.assembler.M68kAssembler;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Builtin assembler symbols.
 *
 * @see M68kBuiltinSymbolPsiElement
 */
public enum M68kBuiltinSymbol {

  CURRENT_PC(M68kAssembler.ANY, AllIcons.Debugger.ShowCurrentFrame, "*", "Current PC"),
  __LINE__(M68kAssembler.ANY, AllIcons.General.LayoutEditorOnly, "__LINE__", "Current line number"),

  REPTN(M68kAssembler.ANY, null, "REPTN", "Repeat Count"),

  CARG(M68kAssembler.ANY, null, "CARG", "Current macro argument"),
  NARG(M68kAssembler.ANY, null, "NARG", "Number of macro arguments"),

  __RS(M68kAssembler.ANY, null, "__RS", "RS-counter value"),
  __SO(M68kAssembler.ANY, null, "__SO", "SO-counter value"),
  __FO(M68kAssembler.ANY, null, "__FO", "FO-counter value"),

  __G2(M68kAssembler.DEVPAC, null, "__G2", ""),
  _LK(M68kAssembler.DEVPAC, null, "_LK", ""),

  _MOVEMBYTES(M68kAssembler.BASM, null, "_MOVEMBYTES", ""),
  __MOVEMREGS(M68kAssembler.BASM, null, "__MOVEMREGS", ""),

  _PHXASS_(M68kAssembler.PHX_ASS, null, "_PHXASS_", "Set to 1 if PhxAss"),
  __CPU(M68kAssembler.PHX_ASS, null, "__CPU", "Current CPU type"),
  __FPU(M68kAssembler.PHX_ASS, null, "__FPU", "Current FPU type"),
  __MMU(M68kAssembler.PHX_ASS, null, "__MMU", "PMMU Generation allowed"),
  __OPTC(M68kAssembler.PHX_ASS, null, "__OPTC", "Optimization flags"),

  __VASM(M68kAssembler.VASM, null, "__VASM", "CPU type");

  private final M68kAssembler assembler;
  private final Icon icon;
  private final String name;
  private final String description;

  M68kBuiltinSymbol(M68kAssembler assembler, @Nullable Icon icon, @NonNls String name, String description) {
    this.assembler = assembler;
    this.icon = icon;
    this.name = name;
    this.description = description;
  }

  public M68kAssembler getAssembler() {
    return assembler;
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
