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

package com.yanncebron.m68kplugin.lang.psi;

import junit.framework.TestCase;

public class M68kDataSizeTest extends TestCase {

  public void testFindByText() {
    assertDataSize(M68kDataSize.BYTE, ".b");
    assertDataSize(M68kDataSize.BYTE, ".B");
    assertDataSize(M68kDataSize.SHORT, ".s");
    assertDataSize(M68kDataSize.WORD, ".w");
    assertDataSize(M68kDataSize.LONG, ".L");

    assertDataSize(null, "INVALID_VALUE");
  }

  private void assertDataSize(M68kDataSize expected, String text) {
    assertEquals(expected, M68kDataSize.findByText(text));
  }

}