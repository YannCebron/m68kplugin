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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.psi.tree.IElementType;
import junit.framework.TestCase;

import java.util.Collection;

public class M68kMnemonicTest extends TestCase {

  public void testDeprecated() {
    int totalDeprecated = 0;
    for (IElementType instructionsType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      final Collection<M68kMnemonic> mnemonics = M68kMnemonicRegistry.getInstance().findAll(instructionsType);
      assertFalse(instructionsType.toString(), mnemonics.isEmpty());
      for (M68kMnemonic mnemonic : mnemonics) {
        if (mnemonic.getElementType() != M68kTokenTypes.MOVEA) {
          assertFalse(mnemonic.toString(), mnemonic.isDeprecated());
        } else {
          if (mnemonic.getSourceOperand() != M68kOperand.ALL &&
            mnemonic.getDestinationOperand() != M68kOperand.ADDRESS_REGISTER) {
            assertTrue(mnemonic.toString(), mnemonic.isDeprecated());
            totalDeprecated++;
          } else {
            assertFalse(mnemonic.toString(), mnemonic.isDeprecated());
          }
        }
      }
    }

    assertEquals(2, totalDeprecated);
  }
}
