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

package com.yanncebron.m68kplugin.settings.ide

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.annotations.Property

/**
 * Local workspace settings (not shared).
 */
@Service(Service.Level.PROJECT)
@State(name = "M68kWorkspaceSettings", storages = [Storage(StoragePathMacros.WORKSPACE_FILE)])
class M68kWorkspaceSettings : SerializablePersistentStateComponent<M68kWorkspaceSettings.State>(State()) {

    var labelQuickDefinitionStatementsAfter: Int
        get() = state.labelQuickDefinitionStatementsAfter
        set(value) {
            updateState {
                it.copy(labelQuickDefinitionStatementsAfter = value)
            }
        }

    data class State(
        @field:Property
        val labelQuickDefinitionStatementsAfter: Int = 5
    )

    companion object {
        @JvmStatic
        fun getInstance(project: Project): M68kWorkspaceSettings = project.service()
    }
}