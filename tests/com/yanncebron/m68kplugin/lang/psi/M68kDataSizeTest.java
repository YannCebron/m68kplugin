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

import com.intellij.psi.tree.IElementType;
import junit.framework.TestCase;

public class M68kDataSizeTest extends TestCase {

  public void testFindByText() {
    assertDataSize(M68kDataSize.BYTE, ".b");
    assertDataSize(M68kDataSize.BYTE, ".B");
    assertDataSize(M68kDataSize.SHORT, ".s");
    assertDataSize(M68kDataSize.WORD, ".w");
    assertDataSize(M68kDataSize.LONGWORD, ".L");

    assertDataSize(null, "INVALID_VALUE");
  }

  private void assertDataSize(M68kDataSize expected, String text) {
    assertEquals(expected, M68kDataSize.findByText(text));
  }

  public void testFindByElementType() {
    assertDataSize2(M68kDataSize.BYTE, M68kTokenTypes.DOT_B);
    assertDataSize2(M68kDataSize.SHORT, M68kTokenTypes.DOT_S);
    assertDataSize2(M68kDataSize.WORD, M68kTokenTypes.DOT_W);
    assertDataSize2(M68kDataSize.LONGWORD, M68kTokenTypes.DOT_L);

    assertDataSize2(null, M68kTokenTypes.ABCD);
  }

  private void assertDataSize2(M68kDataSize expected, IElementType elementType) {
    assertEquals(expected, M68kDataSize.findByElementType(elementType));
  }

}