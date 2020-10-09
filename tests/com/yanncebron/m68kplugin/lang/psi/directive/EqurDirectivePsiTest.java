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

import com.yanncebron.m68kplugin.lang.psi.M68kAdmDrd;
import com.yanncebron.m68kplugin.lang.psi.M68kAdmRrd;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.M68kRegister;

public class EqurDirectivePsiTest extends M68kPsiTestCase {

  public void testDataRegister() {
    final M68kEqurDirective directive = parse("label equr d0");

    final M68kAdmRrd admRrd = directive.getAdmRrd();
    assertNotNull(admRrd);
    final M68kAdmDrd admDrd = admRrd.getAdmDrd();
    assertNotNull(admDrd);
    assertEquals(M68kRegister.D0, admDrd.getRegister());
  }

  private M68kEqurDirective parse(String text) {
    return assertInstanceOf(doParse(text, true), M68kEqurDirective.class);
  }

}
