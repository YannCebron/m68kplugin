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

public class M68kMnemonicRegistryTest extends TestCase {

  private final M68kMnemonicRegistry instance = M68kMnemonicRegistry.getInstance();

  public void testAllInstructionsHaveMnemonics() {
    for (IElementType instructionsType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      final Collection<M68kMnemonic> all = instance.findAll(instructionsType);
      assertFalse("no mnemonics for '" + instructionsType + "'", all.isEmpty());
    }
  }

  public void testFindAllIsEmptyForUnknownElementType() {
    assertTrue(instance.findAll(M68kTokenTypes.ADDWATCH).isEmpty());
  }

}