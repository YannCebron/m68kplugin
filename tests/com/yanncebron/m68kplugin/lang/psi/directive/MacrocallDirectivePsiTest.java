/*
 * Copyright 2022 The Authors
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

import com.yanncebron.m68kplugin.lang.psi.M68kDataSize;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MacrocallDirectivePsiTest extends M68kPsiTestCase {

  public void testNoArgs() {
    M68kMacroCallDirective directive = parse(" MACRO_NAME");

    assertEmpty(directive.getMacroCallParameterList());
    assertNull(directive.getDataSize());
  }

  public void testArgs() {
    M68kMacroCallDirective directive = parse(" MACRO_NAME d0,label,SR,VBR");

    final List<M68kMacroCallParameter> parameters = directive.getMacroCallParameterList();
    assertSize(4, parameters);
    assertNotNull(parameters.get(0).getAdmDrd());
    assertNotNull(parameters.get(1).getAdmAbs());
    assertNotNull(parameters.get(2).getAdmSr());
    assertNotNull(parameters.get(3).getAdmVbr());
  }

  public void testDataSized() {
    M68kMacroCallDirective directive = parse(" MACRO_NAME.w");

    assertEmpty(directive.getMacroCallParameterList());
    assertEquals(M68kDataSize.WORD, directive.getDataSize());
  }


  @NotNull
  private M68kMacroCallDirective parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kMacroCallDirective.class);
  }

}
