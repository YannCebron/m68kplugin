/*
 * Copyright 2023 The Authors
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

package com.yanncebron.m68kplugin.editor;

import com.yanncebron.m68kplugin.lang.psi.M68kDbccInstructionBase;
import com.yanncebron.m68kplugin.lang.psi.M68kLabelBase;

/**
 * Extend selection to previous label (or keep current one) (inclusive) up to subsequent {@code dbCC} instruction (inclusive).
 */
final class M68kDbccBlocksSelectionHandler extends M68kSelectionHandlerBase {

  M68kDbccBlocksSelectionHandler() {
    super(M68kLabelBase.class, true, M68kDbccInstructionBase.class, true);
  }

}
