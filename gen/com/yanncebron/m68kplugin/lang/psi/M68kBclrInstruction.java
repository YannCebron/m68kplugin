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

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface M68kBclrInstruction extends M68kBitManipulationInstructionBase {

  @Nullable
  M68kAdmAbs getAdmAbs();

  @Nullable
  M68kAdmAdi getAdmAdi();

  @Nullable
  M68kAdmAix getAdmAix();

  @Nullable
  M68kAdmApd getAdmApd();

  @Nullable
  M68kAdmApi getAdmApi();

  @Nullable
  M68kAdmAri getAdmAri();

  @NotNull
  List<M68kAdmDrd> getAdmDrdList();

}
