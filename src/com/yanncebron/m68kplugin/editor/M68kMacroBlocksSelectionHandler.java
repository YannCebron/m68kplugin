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

import com.yanncebron.m68kplugin.lang.psi.directive.M68kEndmDirective;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroDirective;

/**
 * Extend selection to preceding macro declaration start (or keep current one) (inclusive) up to closing endm (inclusive).
 */
final class M68kMacroBlocksSelectionHandler extends M68kSelectionHandlerBase {

  M68kMacroBlocksSelectionHandler() {
    super(M68kMacroDirective.class, true, M68kEndmDirective.class, true);
  }

}