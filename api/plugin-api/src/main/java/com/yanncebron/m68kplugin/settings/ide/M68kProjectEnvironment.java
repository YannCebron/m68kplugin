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

import com.intellij.openapi.project.Project;

/**
 * Exposes project level environment settings.
 *
 * @see M68kProjectEnvironmentListener
 */
public abstract class M68kProjectEnvironment {

  public static M68kProjectEnvironment getInstance(Project project) {
    return project.getService(M68kProjectEnvironment.class);
  }

  public abstract M68kTargetPlatform getTargetPlatform();
}
