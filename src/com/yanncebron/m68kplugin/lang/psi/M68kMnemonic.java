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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.M68kBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Function;

/**
 * @see M68kMnemonicRegistry
 * @see M68kMnemonicPredicates
 */
public record M68kMnemonic(IElementType elementType,
                           Set<M68kDataSize> dataSizes,
                           M68kOperand firstOperand,
                           M68kOperand secondOperand,
                           Set<M68kCpu> cpus,
                           PrivilegedType privilegedType,
                           boolean deprecated) {

  private static final Set<M68kOperand> SPECIAL_REGISTER_OPERANDS = Set.of(
    M68kOperand.CCR_REGISTER,
    M68kOperand.SR_REGISTER,
    M68kOperand.USP_REGISTER
  );

  public boolean hasFirstOperand() {
    return firstOperand() != M68kOperand.NONE;
  }

  public boolean hasSecondOperand() {
    return secondOperand() != M68kOperand.NONE;
  }

  @Nls
  public String getExternalName() {
    String elementName = StringUtil.toUpperCase(elementType().toString());
    if (SPECIAL_REGISTER_OPERANDS.contains(firstOperand)) {
      return M68kBundle.message("mnemonic.external.name.from.operand", elementName, getOperandRegisterExternalName(firstOperand));
    }
    if (SPECIAL_REGISTER_OPERANDS.contains(secondOperand)) {
      return M68kBundle.message("mnemonic.external.name.to.operand", elementName, getOperandRegisterExternalName(secondOperand));
    }
    return elementName;
  }

  public boolean hasSpecialRegisterOperands() {
    return SPECIAL_REGISTER_OPERANDS.contains(firstOperand) ||
      SPECIAL_REGISTER_OPERANDS.contains(secondOperand);
  }

  private String getOperandRegisterExternalName(M68kOperand operand) {
    M68kAddressMode[] addressModes = operand.getAddressModes();
    assert addressModes.length == 1 : this;
    return addressModes[0].getNotation();
  }

  enum PrivilegedType {
    /**
     * Never privileged.
     */
    NONE(m68kCpu -> Boolean.FALSE),

    /**
     * Always privileged.
     */
    PRIVILEGED(m68kCpu -> Boolean.TRUE),

    /**
     * Privileged for MC68010 or above only.
     */
    PRIVILEGED_68010_ABOVE(M68kCpu.GROUP_68010_UP::contains);

    private final Function<M68kCpu, Boolean> privilegedFunction;

    PrivilegedType(Function<M68kCpu, Boolean> privilegedFunction) {
      this.privilegedFunction = privilegedFunction;
    }

    public boolean isPrivileged(M68kCpu m68kCpu) {
      return privilegedFunction.apply(m68kCpu);
    }
  }

  @Override
  public @NotNull String toString() {
    final String cpuText;
    if (cpus.equals(M68kCpu.GROUP_68000_UP)) cpuText = "MC68000 Family";
    else if (cpus.equals(M68kCpu.GROUP_68010_UP)) cpuText = "MC68010+";
    else if (cpus.equals(M68kCpu.GROUP_68020_UP)) cpuText = "MC68020+";
    else cpuText = cpus.toString();

    return "M68kMnemonic{" +
      elementType +
      ", firstOp=" + firstOperand +
      ", secondOp=" + secondOperand +
      ", " + dataSizes +
      ", " + cpuText +
      (M68kMnemonicPredicates.privilegedAny().test(this) ? ", " + privilegedType.name() : "") +
      (deprecated() ? ", DEPRECATED" : "") +
      '}';
  }
}
