/*
 * Copyright 2021 The Authors
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

package com.yanncebron.m68kplugin.lang.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class M68kColorSettingsPage implements ColorSettingsPage {

  private static final AttributesDescriptor[] ourDescriptors = {
    createDescriptor("color.settings.group.braces.operators", "attribute.descriptor.colon", M68kTextAttributes.COLON),
    createDescriptor("color.settings.group.braces.operators", "attribute.descriptor.comma", M68kTextAttributes.COMMA),
    createDescriptor("color.settings.group.braces.operators", "attribute.descriptor.dot", M68kTextAttributes.DOT),
    createDescriptor("color.settings.group.braces.operators", "attribute.descriptor.hash", M68kTextAttributes.HASH),
    createDescriptor("color.settings.group.braces.operators", "attribute.descriptor.operation.sign", M68kTextAttributes.OPERATION_SIGN),
    createDescriptor("color.settings.group.braces.operators", "attribute.descriptor.parentheses", M68kTextAttributes.PARENTHESES),
    createDescriptor("color.settings.group.root", "attribute.descriptor.comment", M68kTextAttributes.COMMENT),
    createDescriptor("color.settings.group.root", "attribute.descriptor.data.size", M68kTextAttributes.DATA_SIZES),
    createDescriptor("color.settings.group.directives", "attribute.descriptor.directive", M68kTextAttributes.DIRECTIVE),
    createDescriptor("color.settings.group.directives", "attribute.descriptor.conditional.assembly.directive", M68kTextAttributes.CONDITIONAL_ASSEMBLY_DIRECTIVE),
    createDescriptor("color.settings.group.directives", "attribute.descriptor.macrocall", M68kTextAttributes.MACRO_CALL),
    createDescriptor("color.settings.group.directives", "attribute.descriptor.macro.parameter", M68kTextAttributes.MACRO_PARAMETER),
    createDescriptor("color.settings.group.instructions", "attribute.descriptor.instruction", M68kTextAttributes.INSTRUCTION),
    createDescriptor("color.settings.group.instructions", "attribute.descriptor.privileged.instruction", M68kTextAttributes.PRIVILEGED_INSTRUCTION),
    createDescriptor("color.settings.group.labels", "attribute.descriptor.label", M68kTextAttributes.LABEL),
    createDescriptor("color.settings.group.labels", "attribute.descriptor.local.label", M68kTextAttributes.LOCAL_LABEL),
    createDescriptor("color.settings.group.literals", "attribute.descriptor.binary.number", M68kTextAttributes.BIN_NUMBER),
    createDescriptor("color.settings.group.literals", "attribute.descriptor.decimal.number", M68kTextAttributes.DEC_NUMBER),
    createDescriptor("color.settings.group.literals", "attribute.descriptor.hex.number", M68kTextAttributes.HEX_NUMBER),
    createDescriptor("color.settings.group.literals", "attribute.descriptor.oct.number", M68kTextAttributes.OCT_NUMBER),
    createDescriptor("color.settings.group.literals", "attribute.descriptor.string", M68kTextAttributes.STRING),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.address.register", M68kTextAttributes.ADDRESS_REGISTER),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.ccr.register", M68kTextAttributes.CCR_REGISTER),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.data.register", M68kTextAttributes.DATA_REGISTER),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.pc.register", M68kTextAttributes.PC_REGISTER),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.sp.register", M68kTextAttributes.SP_REGISTER),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.sr.register", M68kTextAttributes.SR_REGISTER),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.ssp.register", M68kTextAttributes.SSP_REGISTER),
    createDescriptor("color.settings.group.registers", "attribute.descriptor.usp.register", M68kTextAttributes.USP_REGISTER),
  };

  private static AttributesDescriptor createDescriptor(String groupKey, String typeKey, TextAttributesKey textAttributesKey) {
    final String displayName = M68kBundle.message(groupKey) + M68kBundle.message(typeKey);
    return new AttributesDescriptor(displayName, textAttributesKey);
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return M68kFileType.INSTANCE.getName();
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return M68kFileType.INSTANCE.getIcon();
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new M68kSyntaxHighlighter();
  }

  @NotNull
  @Override
  public String getDemoText() {
    return "CALL_MACRO macro\n" +
      "        movea.l <macroParameter>\\1</macroParameter>,a6\n" +
      "        jsr _LVO<macroParameter>\\2</macroParameter>(a6)\n" +
      ".localMacroLabel<macroParameter>\\@</macroParameter>\n" +
      "        endm\n" +
      "\n" +
      "<label>label</label>\n" +
      "        lea $dff000,a6\n" +
      "* line comment\n" +
      "        <privilegedInstruction>stop #2</privilegedInstruction>\n" +
      "        moveq #1>>2,d0\n" +
      "<localLabel>.local</localLabel>:\n" +
      "        CALL_MACRO _LibPtr,-42\n" +
      "        moveq #%0101+$FF,d1\n" +
      "        move.l d0,(a1) ;comment\n" +
      "        rts\n" +
      "\n" +
      "        IFND LABEL\n" +
      "<label>FLAG</label> SET 1\n" +
      "        ENDC\n" +
      "\n" +
      "<label>DATA</label> equ @42\n" +
      "<label>_LibPtr</label> dc.l 0\n" +
      "<label>text</label>    dc.b 'some text',0\n";
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    Map<String, TextAttributesKey> additionalMap = new HashMap<>();
    additionalMap.put("label", M68kTextAttributes.LABEL);
    additionalMap.put("localLabel", M68kTextAttributes.LOCAL_LABEL);
    additionalMap.put("macroParameter", M68kTextAttributes.MACRO_PARAMETER);
    additionalMap.put("privilegedInstruction", M68kTextAttributes.PRIVILEGED_INSTRUCTION);
    return additionalMap;
  }

  @NotNull
  @Override
  public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
    return ourDescriptors;
  }

  @NotNull
  @Override
  public ColorDescriptor @NotNull [] getColorDescriptors() {
    return ColorDescriptor.EMPTY_ARRAY;
  }
}
