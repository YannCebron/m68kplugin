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

package com.yanncebron.m68kplugin.amiga.hardware

import com.intellij.codeInsight.hints.declarative.*
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression
import com.yanncebron.m68kplugin.settings.ide.M68kProjectEnvironment
import com.yanncebron.m68kplugin.settings.ide.M68kTargetPlatform

private const val minimumAddressValue = 12570624 // M68kAmigaHardwareRegister.CIAB_PRA

internal class M68kAmigaHardwareInlayProvider : InlayHintsProvider {

    override fun createCollector(
        file: PsiFile,
        editor: Editor
    ): InlayHintsCollector? {
        val project = file.project
        if (project.isDefault) return null;

        if (M68kProjectEnvironment.getInstance(project).targetPlatform == M68kTargetPlatform.AMIGA) {
            return AmigaHardwareRegisterCollector()
        }
        return null
    }


    private class AmigaHardwareRegisterCollector : SharedBypassCollector {

        override fun collectFromElement(
            element: PsiElement,
            sink: InlayTreeSink
        ) {
            if (element is M68kNumberExpression) {
                if (!looksLikeRegisterExpression(element)) return

                val constantValue = element.value as? Int ?: return
                if (constantValue < minimumAddressValue) return

                val register = M68kAmigaHardwareRegister.findByAddress(constantValue) ?: return

                sink.addPresentation(
                    InlineInlayPosition(element.textRange.endOffset, true),
                    tooltip = register.description,
                    hasBackground = true
                ) {
                    text(register.name)
                }
            }
        }

        private fun looksLikeRegisterExpression(element: M68kNumberExpression): Boolean {
            return element.textLength == 7 && element.textContains('$')
        }
    }
}