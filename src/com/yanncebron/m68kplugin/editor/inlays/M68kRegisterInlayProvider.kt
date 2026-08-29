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

package com.yanncebron.m68kplugin.editor.inlays

import com.intellij.codeInsight.hints.declarative.*
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.yanncebron.m68kplugin.M68kBundle
import com.yanncebron.m68kplugin.lang.M68kFile
import com.yanncebron.m68kplugin.lang.psi.M68kAdmWithRegister
import com.yanncebron.m68kplugin.lang.psi.M68kCpu

internal class M68kRegisterInlayProvider : InlayHintsProvider {

    override fun createCollector(
        file: PsiFile,
        editor: Editor
    ): InlayHintsCollector? {
        if (file is M68kFile) {
            return RegisterInlayCollector()
        }
        return null
    }

    private class RegisterInlayCollector : SharedBypassCollector {
        override fun collectFromElement(
            element: PsiElement,
            sink: InlayTreeSink
        ) {
            if (element is M68kAdmWithRegister) {
                val register = element.register
                if (register.cpus.equals(M68kCpu.GROUP_68010_UP)) {
                    val offset = element.textRange.endOffset
                    sink.addPresentation(
                        InlineInlayPosition(offset, false),
                        tooltip = M68kBundle.message("cpu.group.GROUP_68010_UP"),
                        hasBackground = true
                    ) {
                        text(M68kBundle.message("inlay.hint.register.GROUP_68010_UP"))
                    }
                }
            }
        }
    }
}