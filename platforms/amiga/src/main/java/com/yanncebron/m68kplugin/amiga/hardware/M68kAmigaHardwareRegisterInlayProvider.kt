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
import com.intellij.psi.util.PsiTreeUtil
import com.yanncebron.m68kplugin.lang.psi.M68kDataSize
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression
import com.yanncebron.m68kplugin.settings.ide.M68kProjectEnvironment
import com.yanncebron.m68kplugin.settings.ide.M68kTargetPlatform

private const val minimumAddressValue = 0xBFD000 // CIAB_PRA

internal class M68kAmigaHardwareRegisterInlayProvider : InlayHintsProvider {

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
                val register = findByFullAddress(element) ?: findByCopperList(element)
                if (register == null) return

                sink.addPresentation(
                    InlineInlayPosition(element.textRange.endOffset, true),
                    tooltip = register.description,
                    hasBackground = true
                ) {
                    text(register.name)
                }
            }
        }

        /**
         * `$xxxXXX` expression in code for all registers.
         */
        private fun findByFullAddress(element: M68kNumberExpression): M68kAmigaHardwareRegister? {
            if (element.textLength != 7 || !element.textContains('$')) return null
            val constantValue = element.value as? Int ?: return null
            if (constantValue < minimumAddressValue) return null

            return M68kAmigaHardwareRegister.findByAddress(constantValue)
        }

        /**
         * `$XX`..`$XXXX` (+`$DFF000`) as the first expression in `dc.w expr,expr`.
         */
        private fun findByCopperList(element: M68kNumberExpression): M68kAmigaHardwareRegister? {
            if (element.textLength !in 2..4 && !element.textContains('$')) return null

            val dcDirective = PsiTreeUtil.getNonStrictParentOfType(element, M68kDcDirective::class.java) ?: return null
            if (dcDirective.dataSize != M68kDataSize.WORD) return null
            if (dcDirective.expressionList.size != 2) return null
            if (!dcDirective.expressionList.first().equals(element)) return null

            val constantValue = element.value as? Int ?: return null
            if (constantValue.and(1) == 1) return null // register address = even
            if (constantValue > 0x1FC) return null     // highest register address (FMODE)

            return M68kAmigaHardwareRegister.findByAddress(constantValue + 0xDFF000)
        }
    }
}