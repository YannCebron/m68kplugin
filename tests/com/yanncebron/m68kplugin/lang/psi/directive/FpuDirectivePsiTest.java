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

import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;
import org.jetbrains.annotations.NotNull;

public class FpuDirectivePsiTest extends M68kPsiTestCase {

  public void testCpID() {
    M68kFpuDirective directive = parse("fpu 1");
    M68kNumberExpression numberExpression = assertInstanceOf(directive.getCpID(), M68kNumberExpression.class);
    assertEquals("1", numberExpression.getText());
  }

  public void testMissingCpID() {
    M68kFpuDirective directive = parse("fpu");
    assertNull(directive.getCpID());
  }

  @NotNull
  private M68kFpuDirective parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kFpuDirective.class);
  }

}
