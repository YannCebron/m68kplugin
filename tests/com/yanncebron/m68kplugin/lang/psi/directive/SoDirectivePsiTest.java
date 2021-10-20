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

package com.yanncebron.m68kplugin.lang.psi.directive;

import com.yanncebron.m68kplugin.lang.psi.M68kDataSize;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class SoDirectivePsiTest extends M68kPsiTestCase {

  public void testWithoutDataSize() {
    final M68kSoDirective directive = parse("label so 42");


    assertNull(directive.getDataSize());

    assertInstanceOf(directive.getExpression(), M68kNumberExpression.class);
  }

  public void testWithDataSize() {
    final M68kSoDirective directive = parse("label so.b 42");

    assertEquals(M68kDataSize.BYTE, directive.getDataSize());

    assertInstanceOf(directive.getExpression(), M68kNumberExpression.class);
  }

  private M68kSoDirective parse(String text) {
    return assertInstanceOf(doParse(text, true), M68kSoDirective.class);
  }
}
