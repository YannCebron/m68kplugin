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

package com.yanncebron.m68kplugin.psi;

import com.yanncebron.m68kplugin.lang.psi.M68kCnopDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class CnopDirectivePsiTest extends M68kPsiTestCase {

  public void testWithValues() {
    final M68kCnopDirective directive = parse("cnop 0,4");

    final M68kNumberExpression divisor = assertInstanceOf(directive.getDivisor(), M68kNumberExpression.class);
    assertEquals("4", divisor.getText());

    final M68kNumberExpression offset = assertInstanceOf(directive.getOffset(), M68kNumberExpression.class);
    assertEquals("0", offset.getText());
  }

  private M68kCnopDirective parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kCnopDirective.class);
  }

}
