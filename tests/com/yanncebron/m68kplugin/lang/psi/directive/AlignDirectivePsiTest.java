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

import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class AlignDirectivePsiTest extends M68kPsiTestCase {

  public void testWithValues() {
    final M68kAlignDirective directive = parse("align 0");

    final M68kNumberExpression offset = assertInstanceOf(directive.getOffset(), M68kNumberExpression.class);
    assertEquals("0", offset.getText());
  }

  private M68kAlignDirective parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kAlignDirective.class);
  }

}
