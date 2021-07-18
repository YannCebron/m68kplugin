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

package com.yanncebron.m68kplugin.parser;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import junit.framework.AssertionFailedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Generate all possible variants for all registered {@link M68kMnemonic} and verify parsing yields no errors.
 */
public class MnemonicGeneratedParserDataTest extends M68kParsingTestCase {

  private static final boolean DUMP = false;

  private static final String INDENT = "         ";
  private int labelCount = 0;
  private int total = 0, failed = 0, deprecated = 0;

  public MnemonicGeneratedParserDataTest() {
    super("DUMMY_NOT_USED");
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    labelCount = 0;
    total = 0;
    failed = 0;
    deprecated = 0;
  }

  @Override
  protected void tearDown() throws Exception {
    try {
      super.tearDown();
    } finally {
      dump("\n* " + failed + "/" + total + " deprecated: " + deprecated);
    }
  }

  private static void dump(String text) {
    if (DUMP) System.out.println(text);
  }

  public void testGenerateAndParseAllInstructions() {
    for (IElementType instructionsType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      doParserTest(instructionsType);
    }

    dump("* Instructions count: " + M68kTokenGroups.INSTRUCTIONS.getTypes().length);

    assertEquals(3404, total);
    assertEquals(0, failed);
    assertEquals(376, deprecated);
  }

  private void doParserTest(IElementType instructionsType) {
    dump("\n");
    dump(StringUtil.repeat("*", 80));

    final Map<M68kMnemonic, List<String>> variantsMap = createVariants(instructionsType);
    for (Map.Entry<M68kMnemonic, List<String>> variants : variantsMap.entrySet()) {
      dump("\n* " + variants.getKey());

      for (String variant : variants.getValue()) {
        total++;

        myFile = createPsiFile("a", variant);
        try {
          ensureNoErrorElements();
          ensureNoMacroCallElements();
          dump(variant);
        } catch (AssertionFailedError e) {
          if (variants.getKey().isDeprecated()) {
            deprecated++;
            dump(";" + variant.substring(1) + " ; DEPRECATED");
            continue;
          }

          failed++;
          dump(variant + " ; FAILED " + StringUtil.splitByLines(e.getMessage())[0]);
        }
      }
    }

  }

  private void ensureNoMacroCallElements() {
    myFile.acceptChildren(new M68kVisitor() {

      @Override
      public void visitMacroCallDirective(@NotNull M68kMacroCallDirective o) {
        fail("unexpected macrocall '" + o.getText() + "'");
      }
    });
  }

  private Map<M68kMnemonic, List<String>> createVariants(IElementType type) {
    final Collection<M68kMnemonic> allMnemonics = M68kMnemonicRegistry.getInstance().findAll(type);
    assertNotEmpty(allMnemonics);

    final boolean needsLocalBranchLabel = M68kTokenGroups.BCC_INSTRUCTIONS.contains(type) || type == M68kTokenTypes.BSR;
    String labelName = "<NOT_BRANCH>";
    String labelOrIndent = INDENT;
    if (needsLocalBranchLabel) {
      labelName = "label" + String.format("%02d", labelCount++);
      labelOrIndent = labelName + ": ";
    }

    Map<M68kMnemonic, List<String>> result = new LinkedHashMap<>();
    for (M68kMnemonic mnemonic : allMnemonics) {
      String mnemonicText = mnemonic.getElementType().toString();
      List<String> variants = new SmartList<>();

      for (String sourceText : getOperandTexts(mnemonic.getSourceOperand())) {
        for (String destinationText : getOperandTexts(mnemonic.getDestinationOperand())) {
          if (mnemonic.getDataSizes().iterator().next() != M68kDataSize.UNSIZED) {
            addVariant(variants, labelOrIndent, mnemonicText, needsLocalBranchLabel ? labelName : sourceText, destinationText, "  ");
          }
          for (M68kDataSize dataSize : mnemonic.getDataSizes()) {
            String dataSizeText = dataSize == M68kDataSize.UNSIZED ? "" : dataSize.getText();
            addVariant(variants, INDENT, mnemonicText, needsLocalBranchLabel ? labelName : sourceText, destinationText, dataSizeText);
          }

        }
      }
      result.put(mnemonic, variants);
    }

    return result;
  }

  private static void addVariant(List<String> generated, String labelOrIndent, String mnemonicText,
                                 @Nullable String sourceText, @Nullable String destinationText, String dataSizeText) {
    String fullText = labelOrIndent + mnemonicText + dataSizeText;
    if (sourceText != null) {
      fullText += StringUtil.repeatSymbol(' ', 10 - mnemonicText.length()) + sourceText;
    }
    if (destinationText != null) {
      fullText += "," + destinationText;
    }

    generated.add(fullText);
  }

  private static final Map<M68kAddressMode, String> ADDRESS_MODE_TEXT = ContainerUtil.<M68kAddressMode, String>immutableMapBuilder()
    .put(M68kAddressMode.DATA_REGISTER, "d0")
    .put(M68kAddressMode.ADDRESS_REGISTER, "a0")
    .put(M68kAddressMode.ADDRESS_REGISTER_INDIRECT, "(a0)")
    .put(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, "(a0)+")
    .put(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, "-(a0)")
    .put(M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT, "42(a0)")
    .put(M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT, "12(a0,d0)")
    .put(M68kAddressMode.ABSOLUTE_SHORT, "$4000")
    .put(M68kAddressMode.ABSOLUTE_LONG, "$4000.L")
    .put(M68kAddressMode.PC_REGISTER_DISPLACEMENT, "66(PC)")
    .put(M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT, "66(PC,d0)")
    .put(M68kAddressMode.LABEL, "label")
    .put(M68kAddressMode.IMMEDIATE, "#42")
    .put(M68kAddressMode.QUICK_IMMEDIATE, "#1")
    .put(M68kAddressMode.REGISTER_LIST, "d0/a0-a2")
    .put(M68kAddressMode.SPECIAL_REGISTER_SR, "SR")
    .put(M68kAddressMode.SPECIAL_REGISTER_USP, "USP")
    .put(M68kAddressMode.SPECIAL_REGISTER_CCR, "CCR")
    .build();

  @NotNull
  private static Collection<String> getOperandTexts(M68kOperand operand) {
    if (operand == M68kOperand.NONE) return Collections.singleton(null);

    List<String> texts = new SmartList<>();
    for (M68kAddressMode addressMode : operand.getAddressModes()) {
      final String operandText = ADDRESS_MODE_TEXT.get(addressMode);
      assertNotNull(addressMode.toString(), operandText);
      texts.add(operandText);
    }
    return texts;
  }
}
