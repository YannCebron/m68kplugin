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

import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.util.SmartList;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.List;

public class M68kMnemonicTest extends TestCase {

  public void testDeprecated() {
    List<M68kMnemonic> deprecated = new SmartList<>();
    for (IElementType instructionsType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      final Collection<M68kMnemonic> mnemonics = M68kMnemonicRegistry.getInstance().findAll(instructionsType);
      assertFalse(instructionsType.toString(), mnemonics.isEmpty());
      for (M68kMnemonic mnemonic : mnemonics) {
        if (mnemonic.deprecated()) {
          deprecated.add(mnemonic);
        }
      }
    }

    assertEquals(UsefulTestCase.toString(deprecated), 2, deprecated.size());
  }
}
