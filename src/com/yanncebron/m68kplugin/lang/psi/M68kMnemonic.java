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

import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.yanncebron.m68kplugin.M68kBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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
                           boolean deprecated,
                           PrivilegedType privilegedType,
                           ControlFlow controlFlow,
                           ConditionCodes affected,
                           ConditionCodes tested) {

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

  @Override
  public @NotNull String toString() {
    final String cpuText;
    if (cpus.equals(M68kCpu.GROUP_68000_UP)) cpuText = "MC68000 Family";
    else if (cpus.equals(M68kCpu.GROUP_68010_UP)) cpuText = "MC68010+";
    else if (cpus.equals(M68kCpu.GROUP_68020_UP)) cpuText = "MC68020+";
    else if (cpus.equals(M68kCpu.GROUP_68020_UP_WITH_CPU32)) cpuText = "MC68020+/CPU32";
    else cpuText = cpus.toString();

    return "M68kMnemonic{" +
      elementType +
      ", " + firstOperand +
      ", " + secondOperand +
      ", " + dataSizes +
      ", " + cpuText +
      (deprecated() ? ", DEPRECATED" : "") +
      (M68kMnemonicPredicates.privilegedAny().test(this) ? ", " + privilegedType.name() : "") +
      (controlFlow() != ControlFlow.NOTHING ? ", " + controlFlow : "") +
      (affected != ConditionCodes.NONE_AFFECTED ? ", " + affected : "") +
      (tested != ConditionCodes.NONE_AFFECTED ? ", " + tested : "") +
      '}';
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

  public enum ControlFlow {
    NOTHING,
    TRAP,
    TRAP_RETURN,
    BRANCH,
    JUMP,
    RETURN
  }


  @SuppressWarnings("UnstableApiUsage")
  public static final class ConditionCodes {

    private final Code x;
    private final Code n;
    private final Code z;
    private final Code v;
    private final Code c;

    /**
     * Constant for "not affected".
     */
    public static final ConditionCodes NONE_AFFECTED = parseAffected("-----");

    private ConditionCodes(Code x, Code n, Code z, Code v, Code c) {
      this.x = x;
      this.n = n;
      this.z = z;
      this.v = v;
      this.c = c;
    }

    static ConditionCodes parseAffected(String value) {
      assertLength(value);

      EnumSet<Code> INVALID_CODES_N_C = EnumSet.of(Code.CARRY, Code.TEST);

      Code x = Code.fromId(value, 0, EnumSet.of(Code.TEST));
      Code n = Code.fromId(value, 1, INVALID_CODES_N_C);
      Code z = Code.fromId(value, 2, INVALID_CODES_N_C);
      Code v = Code.fromId(value, 3, INVALID_CODES_N_C);
      Code c = Code.fromId(value, 4, INVALID_CODES_N_C);

      return new ConditionCodes(x, n, z, v, c);
    }

    static ConditionCodes parseTested(String value) {
      assertLength(value);

      EnumSet<Code> INVALID_CODES_TESTED = EnumSet.complementOf(EnumSet.of(Code.NOT_AFFECTED, Code.TEST));

      Code x = Code.fromId(value, 0, INVALID_CODES_TESTED);
      Code n = Code.fromId(value, 1, INVALID_CODES_TESTED);
      Code z = Code.fromId(value, 2, INVALID_CODES_TESTED);
      Code v = Code.fromId(value, 3, INVALID_CODES_TESTED);
      Code c = Code.fromId(value, 4, INVALID_CODES_TESTED);

      return new ConditionCodes(x, n, z, v, c);
    }

    private static void assertLength(String value) {
      if (value.length() != 5) {
        throw new IllegalArgumentException("Invalid value length for '" + value + "'");
      }
    }

    /**
     * @return Short string for UI use.
     */
    @NlsSafe
    public String toDisplayText() {
      return "" + x.displayId + n.displayId + z.displayId + v.displayId + c.displayId;
    }

    /**
     * @return List of display IDs for use in the mnemonic docs.
     */
    @NlsSafe
    public List<Character> getDisplayIds() {
      return List.of(x.displayId, n.displayId, z.displayId, v.displayId, c.displayId);
    }

    /**
     * @return List of explanation texts for use in the mnemonic docs.
     */
    @Nls
    public List<String> getDisplayTexts() {
      List<String> texts = new ArrayList<>(5);
      texts.add(getDisplayText(x, 0));
      texts.add(getDisplayText(n, 1));
      texts.add(getDisplayText(z, 2));
      texts.add(getDisplayText(v, 3));
      texts.add(getDisplayText(c, 4));
      return texts;
    }

    @Nls
    private static String getDisplayText(Code code, int idx) {
      if (code == Code.RESULT) {
        return M68kBundle.message("documentation.condition.codes.RESULT." + idx);
      }
      return M68kBundle.message("documentation.conditions.codes." + code);
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof ConditionCodes that)) return false;

      return x == that.x && n == that.n && z == that.z && v == that.v && c == that.c;
    }

    @Override
    public int hashCode() {
      int result = x.hashCode();
      result = 31 * result + n.hashCode();
      result = 31 * result + z.hashCode();
      result = 31 * result + v.hashCode();
      result = 31 * result + c.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return "" + x.parseId + n.parseId + z.parseId + v.parseId + c.parseId;
    }

    private enum Code {
      NOT_AFFECTED('-', '–'), // displayId: n-dash for better visibility

      CLEAR('0', '0'),
      SET('1', '1'),

      UNDEFINED('U', 'U'),
      RESULT('*', '*'),

      // additional "custom" codes
      CARRY('C', '*'),

      AND('A', '*'),
      OR('O', '*'),

      IMMEDIATE('I', '*'),

      // Tested only
      TEST('?', '?');

      private final char parseId;
      private final char displayId;

      Code(char parseId, char displayId) {
        this.parseId = parseId;
        this.displayId = displayId;
      }

      private static Code fromId(String value, int offset, EnumSet<Code> invalidCodes) {
        char id = value.charAt(offset);
        for (Code code : Code.values()) {
          if (id == code.parseId) {
            if (invalidCodes.contains(code)) {
              throw new IllegalArgumentException("id '" + id + "' forbidden (@" + offset + " in '" + value + "')");
            }

            return code;
          }
        }

        throw new IllegalArgumentException("id '" + id + "' invalid (@" + offset + " in '" + value + "')");
      }
    }

  }
}
