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

import com.intellij.psi.util.PsiTreeUtil
import com.yanncebron.m68kplugin.lang.psi.M68kDataSize
import com.yanncebron.m68kplugin.lang.psi.directive.M68kDcDirective
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpression
import com.yanncebron.m68kplugin.lang.psi.expression.M68kNumberExpressionLiteralType

internal object M68kAmigaHardwareRegisterPsiLocator {

    private const val MINIMUM_ADDRESS_VALUE = 0xBFD000 // CIAB_PRA
    private const val BASE_ADDRESS = 0xDFF000
    private const val MAX_ADDRESS_OFFSET = 0x1FC // FMODE

    /**
     * `$xxxXXX` expression in code for all registers.
     */
    @JvmStatic
    fun findByFullAddress(element: M68kNumberExpression): M68kAmigaHardwareRegister? {
        if (element.numberExpressionLiteralType != M68kNumberExpressionLiteralType.HEXADECIMAL) return null
        if (element.textLength != 7) return null

        val constantValue = element.value as? Int ?: return null
        if (constantValue < MINIMUM_ADDRESS_VALUE || constantValue > BASE_ADDRESS + MAX_ADDRESS_OFFSET) return null

        return M68kAmigaHardwareRegister.findByAddress(constantValue)
    }

    /**
     * `$XX`..`$XXXX` (+`$DFF000`) as the first expression in `dc.w expr,expr`.
     */
    @JvmStatic
    fun findByCopperList(element: M68kNumberExpression): M68kAmigaHardwareRegister? {
        if (element.numberExpressionLiteralType != M68kNumberExpressionLiteralType.HEXADECIMAL) return null
        if (element.textLength !in 3..5) return null

        val dcDirective = PsiTreeUtil.getParentOfType(element, M68kDcDirective::class.java) ?: return null
        if (dcDirective.dataSize != M68kDataSize.WORD) return null

        val expressionList = dcDirective.expressionList
        if (expressionList.size != 2 || expressionList[0] != element) return null

        val constantValue = element.value as? Int ?: return null
        if (constantValue.and(1) == 1) return null // register address = even
        if (constantValue > MAX_ADDRESS_OFFSET) return null

        return M68kAmigaHardwareRegister.findByAddress(BASE_ADDRESS + constantValue)
    }
}