/*
 * Copyright 2026 The Authors
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

package com.yanncebron.m68kplugin.lang.psi;

import junit.framework.TestCase;

public class M68kMnemonicConditionCodesTest extends TestCase {

  public void testToString() {
    assertEquals("01U*A", M68kMnemonic.ConditionCodes.parseAffected("01U*A").toString());
  }

  public void testEqualsHashCode() {
    M68kMnemonic.ConditionCodes first = M68kMnemonic.ConditionCodes.parseAffected("CAOUU");
    M68kMnemonic.ConditionCodes second = M68kMnemonic.ConditionCodes.parseAffected("CAOUU");
    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  public void testNoneAffected() {
    assertEquals("-----", M68kMnemonic.ConditionCodes.NONE_AFFECTED.toDisplayText());
  }

  public void testToDisplayText() {
    assertEquals("-01U*", M68kMnemonic.ConditionCodes.parseAffected("-01U*").toDisplayText());
    // different displayId: C, A, O
    assertEquals("***UU", M68kMnemonic.ConditionCodes.parseAffected("CAOUU").toDisplayText());

    assertEquals("?-?-?", M68kMnemonic.ConditionCodes.parseTested("?-?-?").toDisplayText());
  }

  public void testParseAffectedInvalidLength() {
    testInvalid("invalid", "Invalid value length for 'invalid'");
  }

  public void testParseAffectedInvalidCodes() {
    testInvalid("99999", "id '9' invalid (@0 in '99999')");

    assertEquals("*----", M68kMnemonic.ConditionCodes.parseAffected("C----").toDisplayText());
    testInvalid("?----", "id '?' forbidden (@0 in '?----')");

    testInvalid("-C---", "id 'C' forbidden (@1 in '-C---')");
    testInvalid("-?---", "id '?' forbidden (@1 in '-?---')");

    testInvalid("--C--", "id 'C' forbidden (@2 in '--C--')");
    testInvalid("--?--", "id '?' forbidden (@2 in '--?--')");

    testInvalid("---C-", "id 'C' forbidden (@3 in '---C-')");
    testInvalid("---?-", "id '?' forbidden (@3 in '---?-')");

    testInvalid("----C", "id 'C' forbidden (@4 in '----C')");
    testInvalid("----?", "id '?' forbidden (@4 in '----?')");
  }

  public void testParseTested() {
    assertEquals("--??-", M68kMnemonic.ConditionCodes.parseTested("--??-").toDisplayText());
  }

  public void testParseTestedInvalidLength() {
    testInvalidTested("invalid", "Invalid value length for 'invalid'");
  }

  public void testParseTestedInvalidCodes() {
    testInvalidTested("----0", "id '0' forbidden (@4 in '----0')");
    testInvalidTested("----1", "id '1' forbidden (@4 in '----1')");
    testInvalidTested("----U", "id 'U' forbidden (@4 in '----U')");
    testInvalidTested("----*", "id '*' forbidden (@4 in '----*')");
    testInvalidTested("----A", "id 'A' forbidden (@4 in '----A')");
    testInvalidTested("----O", "id 'O' forbidden (@4 in '----O')");
  }

  private void testInvalid(String value, String expectedExceptionMessage) {
    doTestInvalid(value, expectedExceptionMessage, false);
  }

  private void testInvalidTested(String value, String expectedExceptionMessage) {
    doTestInvalid(value, expectedExceptionMessage, true);
  }

  private static void doTestInvalid(String value, String expectedExceptionMessage, boolean tested) {
    try {
      if (tested) {
        M68kMnemonic.ConditionCodes.parseTested(value);
      } else {
        M68kMnemonic.ConditionCodes.parseAffected(value);
      }

      fail("did not fail for '" + value + "', tested=" + tested);
    } catch (IllegalArgumentException e) {
      assertEquals(expectedExceptionMessage, e.getMessage());
    }
  }
}
