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

import com.yanncebron.m68kplugin.lang.psi.M68kPsiTestCase;

public class OutputDirectivePsiTest extends M68kPsiTestCase<M68kOutputDirective> {

  public OutputDirectivePsiTest() {
    super(M68kOutputDirective.class);
  }

  public void testWithNoQuotes() {
    M68kOutputDirective directive = parse("output fileName");
    assertEquals("fileName", directive.getOutputPath());
  }

  public void testWithSingleQuotes() {
    M68kOutputDirective directive = parse("output 'fileName'");
    assertEquals("fileName", directive.getOutputPath());
  }

  public void testWithDoubleQuotes() {
    M68kOutputDirective directive = parse("output \"fileName\"");
    assertEquals("fileName", directive.getOutputPath());
  }

}
