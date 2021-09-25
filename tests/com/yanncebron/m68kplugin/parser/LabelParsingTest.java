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

import com.intellij.testFramework.TestDataPath;

import java.io.IOException;

@TestDataPath("$PROJECT_ROOT/testData/parser/label")
public class LabelParsingTest extends M68kParsingTestCase {

  public LabelParsingTest() {
    super("label");
  }

  public void testLabel() throws IOException {
    doCodeTest("label");
  }

  public void testLabelWithColon() throws IOException {
    doCodeTest("label:");
  }

  public void testLabelStartingWithDecimal() throws IOException {
    doCodeTest("4label");
  }

  public void testLabelStartingWithMultipleDecimal() throws IOException {
    doCodeTest("4321label");
  }

  public void testLabelStartingWithDecimalAndUnderScores() throws IOException {
    doCodeTest("4la_bel123");
  }

  public void testLabelOnlyDecimalWrong() throws IOException {
    doCodeTest("4");
  }

  public void testLabelAfterWhitespaceWithColon() throws IOException {
    doCodeTest("  label:");
  }

  public void testLabelInstruction() throws IOException {
    doCodeTest("label nop");
  }

  public void testLabelWithColonInstruction() throws IOException {
    doCodeTest("label: nop");
  }

  public void testLabelWithColonInstructionNoWhitespace() throws IOException {
    doCodeTest("label:nop");
  }

  public void testLabelWithUnderscorePrefix() throws IOException {
    doCodeTest("_label");
  }

  public void testLocalLabel() throws IOException {
    doCodeTest(".localLabel");
  }

  public void testLocalLabelWithColon() throws IOException {
    doCodeTest(".localLabel:");
  }

  public void testLocalLabelAfterWhitespaceWithColon() throws IOException {
    doCodeTest("  .label:");
  }

  public void testLocalLabelDollarAfterWhitespaceWithColon() throws IOException {
    doCodeTest("  label$:");
  }

  public void testLocalLabelStartingWithDecimal() throws IOException {
    doCodeTest(".4label");
  }

  public void testLocalLabelWithColonInstruction() throws IOException {
    doCodeTest(".localLabel: nop");
  }

  public void testLocalLabelWithColonInstructionTwoSpaces() throws IOException {
    doCodeTest(".localLabel:  nop");
  }

  public void testLocalLabelWithColonInstructionNoWhitespace() throws IOException {
    doCodeTest(".localLabel:nop");
  }

  public void testLocalLabelWithDataSizeName() throws IOException {
    doCodeTest(".s bra.s .s");
  }

  public void testLocalLabelWithColonWithDataSizeName() throws IOException {
    doCodeTest(".s:  bra.s .s");
  }

  public void testLocalLabelWithColonWithDataSizeNameMultipleSpaces() throws IOException {
    doCodeTest(".s:  bra.s   .s");
  }

  public void testLocalLabelDollar() throws IOException {
    doCodeTest("localLabel$");
  }

  public void testLocalLabelDollarWithColon() throws IOException {
    doCodeTest("localLabel$:");
  }

  public void testLocalLabelDollarWithColonInstruction() throws IOException {
    doCodeTest("localLabel$: nop");
  }

  public void testLocalLabelDollarWithColonInstructionNoWhitespace() throws IOException {
    doCodeTest("localLabel$:nop");
  }

}
