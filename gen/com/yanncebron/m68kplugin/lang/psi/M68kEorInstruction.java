/*
 * Copyright 2020 The Authors
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

public interface M68kEorInstruction extends M68kBoolInstructionBase {

  @NotNull
  List<M68kAdmAbs> getAdmAbsList();

  @NotNull
  List<M68kAdmAdi> getAdmAdiList();

  @NotNull
  List<M68kAdmAix> getAdmAixList();

  @NotNull
  List<M68kAdmApd> getAdmApdList();

  @NotNull
  List<M68kAdmApi> getAdmApiList();

  @NotNull
  List<M68kAdmAri> getAdmAriList();

  @Nullable
  M68kAdmCcr getAdmCcr();

  @NotNull
  List<M68kAdmDrd> getAdmDrdList();

  @Nullable
  M68kAdmImm getAdmImm();

  @Nullable
  M68kAdmPcd getAdmPcd();

  @Nullable
  M68kAdmPci getAdmPci();

  @Nullable
  M68kAdmSr getAdmSr();

}
