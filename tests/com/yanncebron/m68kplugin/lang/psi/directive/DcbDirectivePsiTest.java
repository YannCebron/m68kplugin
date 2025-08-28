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

package com.yanncebron.m68kplugin.lang.psi.directive;

import com.yanncebron.m68kplugin.lang.psi.M68kDataSize;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression;

public class DcbDirectivePsiTest extends M68kPsiTestCase<M68kDcbDirective> {

  public DcbDirectivePsiTest() {
    super(M68kDcbDirective.class);
  }

  public void testWithoutValue() {
    final M68kDcbDirective directive = parse("dcb.b 0");

    assertEquals(M68kDataSize.BYTE, directive.getDataSize());

    assertInstanceOf(directive.getNumber(), M68kNumberExpression.class);
    assertNull(directive.getValue());
  }

  public void testWithValue() {
    final M68kDcbDirective directive = parse("dcb 0,42");

    assertNull(directive.getDataSize());

    assertInstanceOf(directive.getNumber(), M68kNumberExpression.class);
    assertInstanceOf(directive.getValue(), M68kNumberExpression.class);
  }

}
