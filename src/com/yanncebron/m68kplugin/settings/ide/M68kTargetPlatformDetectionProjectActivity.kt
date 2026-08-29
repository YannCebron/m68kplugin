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

import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.readActionBlocking
import com.intellij.openapi.extensions.ExtensionNotApplicableException
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.yanncebron.m68kplugin.M68kBundle
import com.yanncebron.m68kplugin.lang.M68kFileType
import icons.M68kIcons

internal class M68kTargetPlatformDetectionProjectActivity : ProjectActivity {

    private val EP_NAME: ExtensionPointName<M68kTargetPlatformDetector> =
        ExtensionPointName.create("com.yanncebron.m68kplugin.targetPlatformDetector")

    init {
        val app = ApplicationManager.getApplication()
        if (app.isUnitTestMode || app.isHeadlessEnvironment) {
            throw ExtensionNotApplicableException.create()
        }
    }

    override suspend fun execute(project: Project) {
        if (M68kProjectEnvironment.getInstance(project).targetPlatform != M68kTargetPlatform.GENERIC) {
            return
        }

        if (readActionBlocking {
                !FileTypeIndex.containsFileOfType(M68kFileType.INSTANCE, GlobalSearchScope.allScope(project))
            }) {
            return
        }

        for (detector in EP_NAME.extensionList) {
            val targetPlatform = detector.detect(project)
            if (targetPlatform != null) {
                showNotification(project, targetPlatform)
                return
            }
        }

    }

    private fun showNotification(project: Project, targetPlatform: M68kTargetPlatform) {
        val applyPlatformAction =
            NotificationAction.create(M68kBundle.message("notification.target.platform.apply.action")) { _, notification ->
                M68kProjectSettings.getInstance(project).targetPlatform = targetPlatform

                val publisher = project.messageBus.syncPublisher(M68kProjectEnvironmentListener.TOPIC)
                publisher.targetPlatformChanged(targetPlatform)

                notification.expire()
            }

        val openSettingsAction =
            NotificationAction.createSimple(M68kBundle.message("notification.target.platform.open.settings.action")) {
                ShowSettingsUtil.getInstance()
                    .showSettingsDialog(project, M68kProjectConfigurable::class.java)
            }

        Notification(
            "m68kplugin.target.platform",
            M68kBundle.message("notification.target.platform.title", targetPlatform.displayName),
            M68kBundle.message("notification.target.platform.text"),
            NotificationType.INFORMATION
        )
            .setIcon(M68kIcons.PLUGIN)
            .setSuggestionType(true)
            .setImportant(true)
            .addAction(applyPlatformAction)
            .addAction(openSettingsAction)
            .notify(project)
    }
}