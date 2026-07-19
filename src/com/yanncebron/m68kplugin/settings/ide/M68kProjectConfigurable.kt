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

import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.SimpleTextAttributes
import com.intellij.ui.dsl.builder.HyperlinkEventAction
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.toNullableProperty
import com.intellij.ui.dsl.listCellRenderer.listCellRenderer
import com.yanncebron.m68kplugin.M68kBundle
import org.jetbrains.annotations.NonNls

internal class M68kProjectConfigurable(private val project: Project) :
    BoundConfigurable(M68kBundle.message("general.m68k.assembler")), SearchableConfigurable {

    private val settings: M68kProjectSettings
        get() = M68kProjectSettings.getInstance(project)

    @Suppress("UnstableApiUsage")
    override fun createPanel(): DialogPanel {
        return panel {

            group(M68kBundle.message("ide.settings.project")) {
                row(M68kBundle.message("ide.settings.project.target.platform.label")) {
                    comboBox(
                        M68kTargetPlatform.entries,
                        listCellRenderer {
                            text(value!!.displayName)

                            if (value == M68kTargetPlatform.GENERIC) {
                                text(M68kBundle.message("ide.settings.project.target.platform.default.suffix")) {
                                    attributes = SimpleTextAttributes.GRAY_ATTRIBUTES
                                }
                            }
                        })
                        .bindItem(settings::targetPlatform.toNullableProperty())
                        .comment(
                            M68kBundle.message("ide.settings.project.target.platform.comment"),
                            action = HyperlinkEventAction.HTML_HYPERLINK_INSTANCE
                        )
                }
            }
        }
    }

    override fun getId(): @NonNls String = "M68kProjectConfigurable"

    override fun apply() {
        val oldTargetPlatform = settings.targetPlatform

        super.apply()

        if (oldTargetPlatform != settings.targetPlatform) {
            val publisher = project.messageBus.syncPublisher(M68kProjectEnvironmentListener.TOPIC)
            publisher.targetPlatformChanged(settings.targetPlatform);
        }
    }
}