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

package com.yanncebron.m68kplugin.lang.refactoring;

import junit.framework.TestCase;

public class M68kNamesValidatorTest extends TestCase {

  private static final M68kNamesValidator VALIDATOR = new M68kNamesValidator();

  public void testIsKeyword() {
    String[] keywords = {"abcd", "BRA", "brA", "eQu", "ifNE"};
    for (String keyword : keywords) {
      assertTrue(keyword, VALIDATOR.isKeyword(keyword, null));
    }
  }

  public void testIsIdentifier() {
    String[] identifiers = {"a", "2a", "_A", ".local"};
    for (String identifier : identifiers) {
      assertTrue(identifier, VALIDATOR.isIdentifier(identifier, null));
    }

    String[] invalidIdentifiers = {"", "_", "2", "__a"};
    for (String identifier : invalidIdentifiers) {
      assertFalse(identifier, VALIDATOR.isIdentifier(identifier, null));
    }
  }

}