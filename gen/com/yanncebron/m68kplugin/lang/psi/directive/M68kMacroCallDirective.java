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
package com.yanncebron.m68kplugin.lang.psi.directive;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAbs;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAdi;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAix;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmApd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmApi;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmArd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmAri;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmDrd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmImm;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmPcd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmPci;

public interface M68kMacroCallDirective extends M68kDirective {

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
  List<M68kAdmArd> getAdmArdList();

  @NotNull
  List<M68kAdmAri> getAdmAriList();

  @NotNull
  List<M68kAdmDrd> getAdmDrdList();

  @NotNull
  List<M68kAdmImm> getAdmImmList();

  @NotNull
  List<M68kAdmPcd> getAdmPcdList();

  @NotNull
  List<M68kAdmPci> getAdmPciList();

}
