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

import com.yanncebron.m68kplugin.lang.psi.M68kDataSize;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class DcDirectivePsiTest extends M68kPsiTestCase {

  public void testSingleElement() {
    final M68kDcDirective directive = parse("dc.b 0");

    assertEquals(M68kDataSize.BYTE, directive.getDataSize());

    final M68kExpression expression = assertOneElement(directive.getExpressionList());
    assertInstanceOf(expression, M68kNumberExpression.class);
  }

  public void testMultipleElements() {
    final M68kDcDirective directive = parse("dc.b 0,1,2,3");

    assertEquals(M68kDataSize.BYTE, directive.getDataSize());

    assertSize(4, directive.getExpressionList());
  }

  private M68kDcDirective parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kDcDirective.class);
  }
}
