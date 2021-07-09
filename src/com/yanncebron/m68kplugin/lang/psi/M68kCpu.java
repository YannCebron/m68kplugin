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

import org.jetbrains.annotations.NonNls;

import java.util.EnumSet;
import java.util.Set;

/**
 * All known CPUs architectures.
 * <p>
 * <em>NB</em> Not all are (or will be) supported.
 * </p>
 */
public enum M68kCpu {

  M_68000("Motorola 68000"),
  M_68010("Motorola 68010"),
  M_68020("Motorola 68020"),
  M_68030("Motorola 68030"),
  M_68040("Motorola 68040"),
  M_68060("Motorola 68060"),

  M_68881("Motorola 68881"),

  M_68851("Motorola 68851"),

  AC_68080("Apollo Core 68080");

  private final String displayName;

  M68kCpu(String displayName) {
    this.displayName = displayName;
  }

  @NonNls
  public String getDisplayName() {
    return displayName;
  }

  public static final Set<M68kCpu> MOTOROLA = EnumSet.of(M_68000, M_68010, M_68020, M_68030, M_68040, M_68060);
  public static final Set<M68kCpu> APOLLO = EnumSet.of(AC_68080);

  public static final Set<M68kCpu> FLOAT = EnumSet.of(M_68881, M_68040, M_68060);
  public static final Set<M68kCpu> MMU = EnumSet.of(M_68851, M_68030, M_68040, M_68060);

  public static final Set<M68kCpu> GROUP_68000_UP = EnumSet.of(M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080);
  public static final Set<M68kCpu> GROUP_68010_UP = EnumSet.of(M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080);
  public static final Set<M68kCpu> GROUP_68020_UP = EnumSet.of(M_68020, M_68030, M_68040, M_68060, AC_68080);
  public static final Set<M68kCpu> GROUP_68030_UP = EnumSet.of(M_68030, M_68040, M_68060, AC_68080);
  public static final Set<M68kCpu> GROUP_68040_UP = EnumSet.of(M_68040, M_68060, AC_68080);

}
