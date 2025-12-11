/*
 * Copyright 2025 The Authors
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

import com.intellij.openapi.util.Ref;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.FactoryMap;
import com.yanncebron.m68kplugin.lang.psi.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static java.util.Map.entry;

public class MnemonicGeneratedParserDataTest extends M68kParsingTestCase {

  // testData/sanity/AllInstructionsParsing.s
  private static final boolean DUMP = false;

  // do not generate exact same variants from previously tested mnemonic(s)
  private static final boolean SKIP_DUPLICATE_VARIANTS = true;

  private static final String INDENT = "         ";
  private int labelCounter = 0;
  private int total = 0, deprecated = 0;
  private final List<String> failedVariants = new SmartList<>();

  public MnemonicGeneratedParserDataTest() {
    super("DUMMY_NOT_USED");
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    labelCounter = 0;
    total = 0;
    deprecated = 0;
    failedVariants.clear();
  }

  @Override
  protected void tearDown() throws Exception {
    try {
      super.tearDown();
    } finally {
      dump("\n* " + failedVariants.size() + "/" + total + " deprecated: " + deprecated);
    }
  }

  private static void dump(String text) {
    if (DUMP) System.out.println(text);
  }

  /**
   * Generate all _invalid_ address mode variants and make sure they result in a parser error or not found mnemonic.
   * This avoids "too lenient" parser (BNF).
   */
  public void testParserDoesNotAllowInvalidAddressModes() {
    int matchedByMnemonicCount = 0;
    int skippedValidCount = 0;

    for (IElementType instructionType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
//    for (IElementType instructionType : TokenSet.create(M68kTokenTypes.ABCD).getTypes()) {

      Collection<M68kMnemonic> allMnemonics = M68kMnemonicRegistry.getInstance().findAll(instructionType);
      assertNotEmpty(allMnemonics);

      boolean hasSourceOperand = ContainerUtil.exists(allMnemonics, m68kMnemonic -> m68kMnemonic.sourceOperand() != M68kOperand.NONE);
      boolean hasDestinationOperand = ContainerUtil.exists(allMnemonics, m68kMnemonic -> m68kMnemonic.destinationOperand() != M68kOperand.NONE);
      if (!hasSourceOperand && !hasDestinationOperand) {
        continue;
      }
      boolean hasDataSize = ContainerUtil.exists(allMnemonics, m68kMnemonic -> !m68kMnemonic.dataSizes().contains(M68kDataSize.UNSIZED));

      Set<String> generatedVariants = new HashSet<>();
      for (M68kDataSize dataSize : M68kDataSize.values()) {
        if (!hasDataSize && dataSize != M68kDataSize.UNSIZED) continue; // UNSIZED mnemonic with size becomes macro call

        String dataSizeText = dataSize == M68kDataSize.UNSIZED ? "" : dataSize.getText();

        for (M68kAddressMode sourceAdm : M68kAddressMode.values()) {
          for (M68kAddressMode destinationAdm : M68kAddressMode.values()) {

            boolean foundValid = false;
            for (M68kMnemonic known : allMnemonics) {
              if ((dataSize == M68kDataSize.UNSIZED || known.dataSizes().contains(dataSize)) &&
                containsAddressMode(known.sourceOperand(), sourceAdm) &&
                (known.destinationOperand() == M68kOperand.NONE || containsAddressMode(known.destinationOperand(), destinationAdm))) {
                foundValid = true;
                break;
              }
            }
            if (foundValid) {
              skippedValidCount++;
              continue;
            }

            // enough to test against first notation variant
            String sourceText = ADDRESS_MODE_TEXT.get(sourceAdm).get(0);
            String variant;
            if (hasDestinationOperand) {
              String destinationText = ADDRESS_MODE_TEXT.get(destinationAdm).get(0);
              variant = "  " + instructionType + dataSizeText + " " + sourceText + "," + destinationText;
            } else {
              variant = "  " + instructionType + dataSizeText + " " + sourceText;
            }
            if (!generatedVariants.add(variant)) {
              continue; // skip duplicates from unnecessary loop (hasDestinationOperand=false)
            }

            String variantOutput = variant + StringUtil.repeat(" ", 30 - variant.length()) + " ; " + sourceAdm + (hasDestinationOperand ? "," + destinationAdm : "") + " ";
            total++;
            myFile = createPsiFile("a", variant);
            M68kPsiElement m68kPsiElement = M68kPsiTreeUtil.getContainingInstructionOrDirective(myFile.findElementAt(3));
            ensureNoMacroCallElements();
            M68kInstruction m68kInstruction = assertInstanceOf(m68kPsiElement, M68kInstruction.class);

            Ref<Boolean> foundAnyError = Ref.create(Boolean.FALSE);
            myFile.accept(new PsiRecursiveElementVisitor() {
              @Override
              public void visitErrorElement(@NotNull PsiErrorElement element) {
                foundAnyError.set(true);
                super.visitErrorElement(element);
              }
            });
            if (foundAnyError.get()) {
              dump(variantOutput + "failed expectedly");
            } else {
              // check that adm's match if no parser error (IMMEDIATE vs QUICK_IMMEDIATE, LABEL vs ABSOLUTE etc.)
              try {
                M68kMnemonic foundMnemonic = M68kMnemonicRegistry.getInstance().find(m68kInstruction);
                if (foundMnemonic != null) {
                  matchedByMnemonicCount++;
                  dump(variantOutput + "no parser error, but found matching mnemonic: " + foundMnemonic);
                  continue;
                }
              } catch (AssertionError expected) {
                // no matching mnemonic currently yields assertion
              }

              failedVariants.add(variantOutput);
              dump(variantOutput + "SHOULD HAVE FAILED");
            }

          }
        }
      }
    }

    assertEquals(121291, total);
    assertEquals(17166, skippedValidCount);
    assertEquals(1472, matchedByMnemonicCount);
    assertEmpty(failedVariants);
  }

  private static boolean containsAddressMode(M68kOperand m68kOperand, M68kAddressMode addressMode) {
    for (M68kAddressMode mode : m68kOperand.getAddressModes()) {
      if (mode == addressMode) return true;
    }
    return false;
  }

  /**
   * Generate all possible _valid_ unique variants for all registered {@link M68kMnemonic} and verify:
   * <ul>
   * <li>parsing yields no errors</li>
   * <li>mnemonic is recognized (not a macro call)</li>
   * <li>every variant returns entry via {@link M68kMnemonicRegistry#find}</li>
   * </ul>
   */
  public void testGenerateAndParseAllInstructions() {
    for (IElementType instructionsType : M68kTokenGroups.INSTRUCTIONS.getTypes()) {
      doParserTest(instructionsType);
    }

    dump("* Instructions count: " + M68kTokenGroups.INSTRUCTIONS.getTypes().length);

    assertEquals(5428, total);
    assertEmpty(failedVariants);
    assertEquals(781, deprecated);
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
          ensureHasEntryInRegistry();
          dump(variant);
        } catch (AssertionError e) {
          if (variants.getKey().isDeprecated()) {
            deprecated++;
            dump(";" + variant.substring(1) + " ; DEPRECATED");
            continue;
          }

          final String failedVariantText = variant + " ; FAILED " + StringUtil.splitByLines(e.getMessage())[0];
          failedVariants.add(failedVariantText);
          dump(failedVariantText);
        }
      }
    }

  }

  private void ensureHasEntryInRegistry() {
    final M68kPsiElement instruction = M68kPsiTreeUtil.getContainingInstructionOrDirective(myFile.findElementAt(INDENT.length() + 2));
    final M68kInstruction m68kInstruction = assertInstanceOf(instruction, M68kInstruction.class);

    final M68kMnemonic m68kMnemonic = M68kMnemonicRegistry.getInstance().find(m68kInstruction);
    assertNotNull(myFile.getText(), m68kMnemonic);
  }

  private void ensureNoMacroCallElements() {
    myFile.acceptChildren(new M68kVisitor() {

      @Override
      public void visitMacroCallDirective(@NotNull M68kMacroCallDirective o) {
        fail("unexpected macrocall '" + myFile.getText() + "'");
      }
    });
  }

  private Map<M68kMnemonic, List<String>> createVariants(IElementType type) {
    final Collection<M68kMnemonic> allMnemonics = M68kMnemonicRegistry.getInstance().findAll(type);
    assertFalse(type.toString(), allMnemonics.isEmpty());

    final boolean needsLocalBranchLabel =
      M68kTokenGroups.BCC_INSTRUCTIONS.contains(type) ||
        type == M68kTokenTypes.BRA ||
        type == M68kTokenTypes.BSR;


    Map<M68kMnemonic, List<String>> result = new LinkedHashMap<>();
    for (M68kMnemonic mnemonic : allMnemonics) {
      String labelName = "<NOT_BRANCH>";
      String labelOrIndent = INDENT;
      if (needsLocalBranchLabel) {
        labelName = "label" + String.format("%02d", labelCounter++);
        labelOrIndent = labelName + ": ";
      }

      String mnemonicText = mnemonic.elementType().toString();
      List<String> variants = new SmartList<>();

      for (String sourceText : OPERAND_TEXTS.get(mnemonic.sourceOperand())) {
        for (String destinationText : OPERAND_TEXTS.get(mnemonic.destinationOperand())) {
          if (mnemonic.dataSizes().iterator().next() != M68kDataSize.UNSIZED) {
            addVariant(variants, labelOrIndent, mnemonicText, needsLocalBranchLabel ? labelName : sourceText, destinationText, "  ");
          }
          for (M68kDataSize dataSize : mnemonic.dataSizes()) {
            String dataSizeText = dataSize == M68kDataSize.UNSIZED ? "" : dataSize.getText();
            addVariant(variants, INDENT, mnemonicText, needsLocalBranchLabel ? labelName : sourceText, destinationText, dataSizeText);
          }

        }
      }

      if (SKIP_DUPLICATE_VARIANTS) {
        for (Map.Entry<M68kMnemonic, List<String>> existingEntry : result.entrySet()) {
          if (existingEntry.getKey().elementType() == mnemonic.elementType()) {
            variants.removeAll(existingEntry.getValue());
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

  private static final Map<M68kAddressMode, List<String>> ADDRESS_MODE_TEXT = Map.ofEntries(
    entry(M68kAddressMode.DATA_REGISTER, List.of("d0")),
    entry(M68kAddressMode.ADDRESS_REGISTER, List.of("a0")),
    entry(M68kAddressMode.ADDRESS_REGISTER_INDIRECT, List.of("(a0)")),
    entry(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, List.of("(a0)+")),
    entry(M68kAddressMode.ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, List.of("-(a0)")),
    entry(M68kAddressMode.ADDRESS_REGISTER_DISPLACEMENT, List.of("42(a0)", "(-42,a0)")),
    entry(M68kAddressMode.ADDRESS_REGISTER_INDEX_DISPLACEMENT, List.of("12(a0,d0)", "(12,a0,a0)")),
    entry(M68kAddressMode.ABSOLUTE_SHORT, List.of("$4000", "$4000.W")),
    entry(M68kAddressMode.ABSOLUTE_LONG, List.of("$4000.L")),
    entry(M68kAddressMode.PC_REGISTER_DISPLACEMENT, List.of("(PC)", "66(PC)", "(-66,PC)")),
    entry(M68kAddressMode.PC_REGISTER_INDEX_DISPLACEMENT, List.of("66(PC,d0)", "(66,PC,a0)")),
    entry(M68kAddressMode.LABEL, List.of("label")),
    entry(M68kAddressMode.IMMEDIATE, List.of("#42")),
    entry(M68kAddressMode.QUICK_IMMEDIATE, List.of("#1")),
    entry(M68kAddressMode.REGISTER_LIST, List.of("d0/a0-a2", "#3")),
    entry(M68kAddressMode.SPECIAL_REGISTER_SR, List.of("SR")),
    entry(M68kAddressMode.SPECIAL_REGISTER_USP, List.of("USP")),
    entry(M68kAddressMode.SPECIAL_REGISTER_CCR, List.of("CCR")),
    entry(M68kAddressMode.CONTROL_REGISTER, List.of("DFC", "SFC", "VBR"))
  );

  private static final Map<M68kOperand, List<String>> OPERAND_TEXTS = FactoryMap.create(operand -> {
    if (operand == M68kOperand.NONE) {
      return Collections.singletonList(null);
    }

    List<String> texts = new SmartList<>();
    for (M68kAddressMode addressMode : operand.getAddressModes()) {
      texts.addAll(ADDRESS_MODE_TEXT.get(addressMode));
    }
    return texts;
  });

}
