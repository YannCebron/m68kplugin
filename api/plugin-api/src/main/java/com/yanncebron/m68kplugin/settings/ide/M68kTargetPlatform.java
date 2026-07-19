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

package com.yanncebron.m68kplugin.settings.ide;

import com.yanncebron.m68kplugin.M68kApiBundle;
import org.jetbrains.annotations.Nls;

/**
 * @see M68kProjectEnvironment#getTargetPlatform()
 */
public enum M68kTargetPlatform {

  GENERIC(M68kApiBundle.message("ide.settings.target.platform.value.generic")),
  AMIGA(M68kApiBundle.message("ide.settings.target.platform.value.amiga"));

  private final String displayName;

  M68kTargetPlatform(String displayName) {
    this.displayName = displayName;
  }

  @Nls
  public String getDisplayName() {
    return displayName;
  }
}
