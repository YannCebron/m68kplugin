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

import org.jetbrains.annotations.NotNull;

public class ResetInstructionPsiTest extends M68kPsiTestCase {

  public void testResetInstruction() {
    final M68kResetInstruction instruction = parse("reset");
    assertTrue(instruction.isPrivileged(M68kCpu.M_68000));
  }

  private @NotNull M68kResetInstruction parse(String text) {
    return assertInstanceOf(doParse(" " + text), M68kResetInstruction.class);
  }

}
