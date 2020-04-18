/*
 * Copyright 2020 The Authors
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
import com.yanncebron.m68kplugin.lang.M68kFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class M68kColorSettingsPage implements ColorSettingsPage {

  private static final AttributesDescriptor[] ourDescriptors = {
    new AttributesDescriptor("Instruction", M68kTextAttributes.INSTRUCTION),
    new AttributesDescriptor("Labels//Label", M68kTextAttributes.LABEL),
    new AttributesDescriptor("Labels//Local Label", M68kTextAttributes.LOCAL_LABEL),
    new AttributesDescriptor("Comment", M68kTextAttributes.COMMENT),
    new AttributesDescriptor("Directive", M68kTextAttributes.DIRECTIVE),
    new AttributesDescriptor("Data Size", M68kTextAttributes.DATA_SIZES),

    new AttributesDescriptor("Registers//Data register", M68kTextAttributes.DATA_REGISTER),
    new AttributesDescriptor("Registers//Address register", M68kTextAttributes.ADDRESS_REGISTER),
    new AttributesDescriptor("Registers//SP", M68kTextAttributes.SP_REGISTER),
    new AttributesDescriptor("Registers//SSP", M68kTextAttributes.SSP_REGISTER),
    new AttributesDescriptor("Registers//USP", M68kTextAttributes.USP_REGISTER),
    new AttributesDescriptor("Registers//PC", M68kTextAttributes.PC_REGISTER),
    new AttributesDescriptor("Registers//SR", M68kTextAttributes.SR_REGISTER),
    new AttributesDescriptor("Registers//CCR", M68kTextAttributes.CCR_REGISTER),

    new AttributesDescriptor("Addressing Mode//Dn", M68kTextAttributes.ADM_DRD),
    new AttributesDescriptor("Addressing Mode//An", M68kTextAttributes.ADM_ARD),
    new AttributesDescriptor("Addressing Mode//(An)", M68kTextAttributes.ADM_ARI),
    new AttributesDescriptor("Addressing Mode//(An)+", M68kTextAttributes.ADM_API),
    new AttributesDescriptor("Addressing Mode//-(An)", M68kTextAttributes.ADM_APD),
    new AttributesDescriptor("Addressing Mode//i(An)", M68kTextAttributes.ADM_ADI),
    new AttributesDescriptor("Addressing Mode//i(An,Rn)", M68kTextAttributes.ADM_AIX),
    new AttributesDescriptor("Addressing Mode//#n", M68kTextAttributes.ADM_IMM),
    new AttributesDescriptor("Addressing Mode//i(PC)", M68kTextAttributes.ADM_PCD),
    new AttributesDescriptor("Addressing Mode//i(PC,Rn)", M68kTextAttributes.ADM_PCI),
    new AttributesDescriptor("Addressing Mode//Absolute(.w|l)", M68kTextAttributes.ADM_ABS),


    new AttributesDescriptor("Literals//Decimal number", M68kTextAttributes.DEC_NUMBER),
    new AttributesDescriptor("Literals//Hexadecimal number", M68kTextAttributes.HEX_NUMBER),
    new AttributesDescriptor("Literals//Octal number", M68kTextAttributes.OCT_NUMBER),
    new AttributesDescriptor("Literals//Binary number", M68kTextAttributes.BIN_NUMBER),
    new AttributesDescriptor("Literals//String", M68kTextAttributes.STRING),

    new AttributesDescriptor("Braces and Operators//Colon", M68kTextAttributes.COLON),
    new AttributesDescriptor("Braces and Operators//Comma", M68kTextAttributes.COMMA),
    new AttributesDescriptor("Braces and Operators//Dot", M68kTextAttributes.DOT),
    new AttributesDescriptor("Braces and Operators//Hash", M68kTextAttributes.HASH),
    new AttributesDescriptor("Braces and Operators//Operators", M68kTextAttributes.OPERATORS),
    new AttributesDescriptor("Braces and Operators//Parentheses", M68kTextAttributes.PARENTHESES),
  };

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
    return "<label>label</label>:\n" +
      "  lea <admAbs>$dff000</admAbs>,<admArd>a6</admArd> ;comment\n" +
      "* line comment\n" +
      "  moveq <admImm>#1>>2</admImm>,<admDrd>d0</admDrd>\n" +
      "  movea.l <admAri>(a1)</admAri>,<admArd>a0</admArd>\n" +
      "  movea.l <admApi>(a1)+</admApi>,<admArd>a0</admArd>\n" +
      "  movea.l <admApd>-(a1)</admApd>,<admArd>a0</admArd>\n" +
      "  movea.l <admAdi>42(a1)</admAdi>,<admArd>a0</admArd>\n" +
      "  movea.l <admAix>42(a1,d0)</admAix>,<admArd>a0</admArd>\n" +
      "  movea.l <admPcd>42(PC)</admPcd>,<admArd>a0</admArd>\n" +
      "  movea.l <admPci>42(pc,d0)</admPci>,<admArd>a0</admArd>\n" +
      "<localLabel>.local</localLabel>:\n" +
      "  moveq <admImm>#%0101+$FF</admImm>,<admDrd>d1</admDrd>\n" +
      "  rts\n" +
      "\n" +
      "<label>DATA</label> equ @42\n" +
      "  dc.b 'some text',0\n";
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    Map<String, TextAttributesKey> additionalMap = new HashMap<>();
    additionalMap.put("label", M68kTextAttributes.LABEL);
    additionalMap.put("localLabel", M68kTextAttributes.LOCAL_LABEL);


    additionalMap.put("admDrd", M68kTextAttributes.ADM_DRD);
    additionalMap.put("admArd", M68kTextAttributes.ADM_ARD);
    additionalMap.put("admAri", M68kTextAttributes.ADM_ARI);
    additionalMap.put("admApi", M68kTextAttributes.ADM_API);
    additionalMap.put("admApd", M68kTextAttributes.ADM_APD);
    additionalMap.put("admAdi", M68kTextAttributes.ADM_ADI);
    additionalMap.put("admAix", M68kTextAttributes.ADM_AIX);
    additionalMap.put("admPcd", M68kTextAttributes.ADM_PCD);
    additionalMap.put("admPci", M68kTextAttributes.ADM_PCI);
    additionalMap.put("admAbs", M68kTextAttributes.ADM_ABS);
    additionalMap.put("admImm", M68kTextAttributes.ADM_IMM);
    return additionalMap;
  }

  @NotNull
  @Override
  public AttributesDescriptor[] getAttributeDescriptors() {
    return ourDescriptors;
  }

  @NotNull
  @Override
  public ColorDescriptor[] getColorDescriptors() {
    return ColorDescriptor.EMPTY_ARRAY;
  }
}
