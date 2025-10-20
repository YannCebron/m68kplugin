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

import com.intellij.testFramework.TestDataPath;

@TestDataPath("$PROJECT_ROOT/testData/parser/directives")
public class DirectivesParsingTest extends M68kParsingTestCase {

  public DirectivesParsingTest() {
    super("directives");
  }

  public void testEquDirective() throws Exception {
    doCodeTest("label equ 42");
  }

  public void testEquLabelWithColonDirective() throws Exception {
    doCodeTest("label: equ 42");
  }

  public void testEquDirectiveMissingExpression() throws Exception {
    doCodeTest("label equ ");
  }

  public void testEqDirective() throws Exception {
    doCodeTest("label = 42");
  }

  public void testEqLabelWithColonDirective() throws Exception {
    doCodeTest("label: = 42");
  }

  public void testEqDirectiveWithoutWhitespace() throws Exception {
    doCodeTest("label=42");
  }

  public void testEqDirectiveMissingExpression() throws Exception {
    doCodeTest("label = ");
  }

  public void testSetDirective() throws Exception {
    doCodeTest("label sEt 42");
  }

  public void testEqurDirectiveDataRegister() throws Exception {
    doCodeTest("label equr d6");
  }

  public void testEqurDirectiveLabelColonDataRegister() throws Exception {
    doCodeTest("label: equr d6");
  }

  public void testEqurDirectiveAddressRegister() throws Exception {
    doCodeTest("label equR a4");
  }

  public void testEqurDirectiveMissingRegister() throws Exception {
    doCodeTest("label equr ");
  }

  public void testRegDirective() throws Exception {
    doCodeTest("label reg d0-d7/a0");
  }

  public void testRegDirectiveRegisterMask() throws Exception {
    doCodeTest("label reg #123");
  }

  public void testRegDirectiveLabelColon() throws Exception {
    doCodeTest("label: reg d0-d7/a0");
  }

  public void testRegDirectiveMissingRegisterList() throws Exception {
    doCodeTest("label REG ");
  }

  public void testEvenDirective() throws Exception {
    doCodeTest(" even");
  }

  public void testOddDirective() throws Exception {
    doCodeTest(" odd");
  }

  public void testIncbinDirective() throws Exception {
    doCodeTest(" incbin \"path\"");
  }

  public void testIncbinDirectiveNoQuotes() throws Exception {
    doCodeTest(" incbin \"path\"");
  }

  public void testIncbinDirectiveOffsetLength() throws Exception {
    doCodeTest(" incbin \"path\",offset,42");
  }

  public void testIncbinDirectiveMissingOffsetExpression() throws Exception {
    doCodeTest(" incbin \"path\",");
  }

  public void testIncdirDirective() throws Exception {
    doCodeTest(" incdir \"path\"");
  }

  public void testIncdirDirectiveMissingPath() throws Exception {
    doCodeTest(" incdir ");
  }

  public void testIncludeDirective() throws Exception {
    doCodeTest(" include \"includeFile.i\"");
  }

  public void testIncludeDirectiveNoQuotes() throws Exception {
    doCodeTest(" include path/includeFile.i");
  }

  public void testIncludeDirectiveMissingPath() throws Exception {
    doCodeTest(" include ");
  }

  public void testDcDirective() throws Exception {
    doCodeTest(" dc.b 42");
  }

  public void testDcDirectiveMissingValue() throws Exception {
    doCodeTest(" dc.b ");
  }

  public void testDcDirectiveWithoutDataSize() throws Exception {
    doCodeTest(" DC 42");
  }

  public void testDcDirectiveMultipleValues() throws Exception {
    doCodeTest(" dc.l 1,2,3");
  }

  public void testDcbDirective() throws Exception {
    doCodeTest(" dcb 42");
  }

  public void testDcbDirectiveWithValue() throws Exception {
    doCodeTest(" dcb.l 42,666");
  }

  public void testDsDirective() throws Exception {
    doCodeTest(" ds 42");
  }

  public void testDrDirective() throws Exception {
    doCodeTest(" dr label1,label2");
  }

  public void testDxDirective() throws Exception {
    doCodeTest(" dx.l 42");
  }

  public void testRssetDirective() throws Exception {
    doCodeTest(" rsset 0");
  }

  public void testRssetDirectiveMissingExpression() throws Exception {
    doCodeTest(" rsset ");
  }

  public void testRsresetDirective() throws Exception {
    doCodeTest(" rsreset");
  }

  public void testRsDirective() throws Exception {
    doCodeTest(" rs 42");
  }

  public void testRsDirectiveWithDataSize() throws Exception {
    doCodeTest(" rs.b 42");
  }

  public void testRsDirectiveWithoutExpression() throws Exception {
    doCodeTest(" rs ");
  }

  public void testBlkDirective() throws Exception {
    doCodeTest(" blk 42,666");
  }

  public void testBlkDirectiveWithoutValue() throws Exception {
    doCodeTest(" BLK 42");
  }

  public void testOptDirective() throws Exception {
    doCodeTest(" opt w+");
  }

  public void testOptDirectiveMultipleParameters() throws Exception {
    doCodeTest(" opt w+,u+");
  }

  public void testOptDirectiveWithoutPlusMinus() throws Exception {
    doCodeTest(" opt debug");
  }

  public void testOptDirectivePAndCpuType() throws Exception {
    doCodeTest(" OPT p=68000");
  }

  public void testOptDirectivePAndCpuFpuType() throws Exception {
    doCodeTest(" opt p=68020/68881");
  }

  public void testOptDirectiveMissingParameter() throws Exception {
    doCodeTest(" opt ");
  }

  public void testMacroDirective() throws Exception {
    doCodeTest("macroName MACRO");
  }

  public void testEndmDirective() throws Exception {
    doCodeTest(" endm");
  }

  public void testMexitDirective() throws Exception {
    doCodeTest(" mexit");
  }

  public void testOrgDirective() throws Exception {
    doCodeTest(" org $50000");
  }

  public void testOrgDirectiveMissingParameter() throws Exception {
    doCodeTest(" org ");
  }

  public void testEndDirective() throws Exception {
    doCodeTest(" end");
  }

  public void testSectionDirective() throws Exception {
    doCodeTest(" section tos,code,1");
  }

  public void testSectionDirectiveString() throws Exception {
    doCodeTest(" section \"tos\",code,chip");
  }

  public void testSectionDirectiveMissingParameters() throws Exception {
    doCodeTest(" section ");
  }

  public void testTextDirective() throws Exception {
    doCodeTest(" text");
  }

  public void testCsegDirective() throws Exception {
    doCodeTest(" cseg");
  }

  public void testCodeDirective() throws Exception {
    doCodeTest(" code");
  }

  public void testCodeCDirective() throws Exception {
    doCodeTest(" code_c");
  }

  public void testCodeFDirective() throws Exception {
    doCodeTest(" code_f");
  }

  public void testDsegDirective() throws Exception {
    doCodeTest(" dseg");
  }

  public void testDataDirective() throws Exception {
    doCodeTest(" data");
  }

  public void testDataCDirective() throws Exception {
    doCodeTest(" data_c");
  }

  public void testDataFDirective() throws Exception {
    doCodeTest(" data_f");
  }

  public void testBssDirective() throws Exception {
    doCodeTest(" bss");
  }

  public void testBssCDirective() throws Exception {
    doCodeTest(" bss_c");
  }

  public void testBssFDirective() throws Exception {
    doCodeTest(" bss_f");
  }

  public void testAlignDirective() throws Exception {
    doCodeTest(" align 4");
  }

  public void testCnopDirective() throws Exception {
    doCodeTest(" cnop 0,4");
  }

  public void testCnopDirectiveMissingSecondExpression() throws Exception {
    doCodeTest(" cnop 0,");
  }

  public void testAddwatchDirective() throws Exception {
    doCodeTest(" addwatch label");
  }

  public void testAddwatchDirectiveMissingLabel() throws Exception {
    doCodeTest(" addwatch ");
  }

  public void testJumperrDirective() throws Exception {
    doCodeTest(" jumperr label");
  }

  public void testJumpptrDirective() throws Exception {
    doCodeTest(" jumpptr label");
  }

  public void testListDirective() throws Exception {
    doCodeTest(" list");
  }

  public void testNolistDirective() throws Exception {
    doCodeTest(" nolist");
  }

  public void testPageDirective() throws Exception {
    doCodeTest(" page");
  }

  public void testNopageDirective() throws Exception {
    doCodeTest(" nopage");
  }

  public void testPlenDirective() throws Exception {
    doCodeTest(" plen 22");
  }

  public void testLlenDirective() throws Exception {
    doCodeTest(" llen 22");
  }

  public void testSpcDirective() throws Exception {
    doCodeTest(" spc 22");
  }

  public void testInlineDirective() throws Exception {
    doCodeTest(" inline");
  }

  public void testEinlineDirective() throws Exception {
    doCodeTest(" einline");
  }

  public void testRemDirective() throws Exception {
    doCodeTest(" rem");
  }

  public void testEremDirective() throws Exception {
    doCodeTest(" erem");
  }

  public void testRemWithEremDirective() throws Exception {
    doCodeTest("""
       rem
       everything in between
       is a comment
       erem
      """);
  }

  public void testXDefDirective() throws Exception {
    doCodeTest(" xdef label");
  }

  public void testXDefDirectiveMultipleLabels() throws Exception {
    doCodeTest(" xdef label,label2");
  }

  public void testXDefDirectiveMissingLabel() throws Exception {
    doCodeTest(" xdef ");
  }

  public void testXRefDirective() throws Exception {
    doCodeTest(" xref label");
  }

  public void testXRefDirectiveMultipleLabels() throws Exception {
    doCodeTest(" xref label,label2");
  }

  public void testXRefDirectiveMissingLabel() throws Exception {
    doCodeTest(" xref ");
  }

  public void testReptDirective() throws Exception {
    doCodeTest(" rept 5");
  }

  public void testReptDirectiveMissingExpression() throws Exception {
    doCodeTest(" rept ");
  }

  public void testEndrDirective() throws Exception {
    doCodeTest(" endr");
  }

  public void testFailDirective() throws Exception {
    doCodeTest(" fail 'A MESSAGE TO FAIL'");
  }

  public void testPrinttDirective() throws Exception {
    doCodeTest(" printt 'Some text:'");
  }

  public void testPrintvDirective() throws Exception {
    doCodeTest(" printv 2+4");
  }

  public void testPrintvDirectiveMultipleExpressions() throws Exception {
    doCodeTest(" printv 2+4,label");
  }

  public void testPrintvDirectiveMissingExpression() throws Exception {
    doCodeTest(" printv ");
  }

  public void testLoadDirective() throws Exception {
    doCodeTest(" load label");
  }

  public void testTtlDirective() throws Exception {
    doCodeTest(" ttl \"myName\"");
  }

  public void testIdntDirective() throws Exception {
    doCodeTest(" idnt \"myName\"");
  }

  public void testFarDirective() throws Exception {
    doCodeTest(" far");
  }

  public void testNearDirective() throws Exception {
    doCodeTest(" near a0");
  }

  public void testNearDirectiveWithoutRegister() throws Exception {
    doCodeTest(" near");
  }

  public void testNearCodeDirective() throws Exception {
    doCodeTest(" near code");
  }

  public void testInitNearDirective() throws Exception {
    doCodeTest(" initnear");
  }

  public void testPopsectionDirective() throws Exception {
    doCodeTest(" popsection");
  }

  public void testPushsectionDirective() throws Exception {
    doCodeTest(" pushsection");
  }

  public void testEchoDirective() throws Exception {
    doCodeTest(" echo \"some text\"");
  }

  public void testFpuDirective() throws Exception {
    doCodeTest(" fpu 1");
  }

  public void testFpuDirectiveMissingCpID() throws Exception {
    doCodeTest(" fpu ");
  }

  public void testCpu32Directive() throws Exception {
    doCodeTest(" cpu32");
  }

  public void testMc68000Directive() throws Exception {
    doCodeTest(" mc68000");
  }

  public void testMc68010Directive() throws Exception {
    doCodeTest(" mc68010");
  }

  public void testMc68020Directive() throws Exception {
    doCodeTest(" mc68020");
  }

  public void testMc68030Directive() throws Exception {
    doCodeTest(" mc68030");
  }

  public void testMc68040Directive() throws Exception {
    doCodeTest(" mc68040");
  }

  public void testMc68060Directive() throws Exception {
    doCodeTest(" mc68060");
  }

  public void testAc68080Directive() throws Exception {
    doCodeTest(" ac68080");
  }

  public void testMachineDirective() throws Exception {
    doCodeTest(" machine 68000");
  }

  public void testMachineDirectiveId() throws Exception {
    doCodeTest(" machine mc68000");
  }

  public void testMachineDirectiveMissingCpuType() throws Exception {
    doCodeTest(" machine ");
  }

  public void testClrfoDirective() throws Exception {
    doCodeTest(" clrfo");
  }

  public void testSetfoDirective() throws Exception {
    doCodeTest(" setfo 42");
  }

  public void testFoDirective() throws Exception {
    doCodeTest("label fo.w 42");
  }

  public void testFoDirectiveNoDataSize() throws Exception {
    doCodeTest("label fo 42");
  }

  public void testFoDirectiveNoExpression() throws Exception {
    doCodeTest("label fo");
  }

  public void testClrsoDirective() throws Exception {
    doCodeTest(" clrso");
  }

  public void testSetsoDirective() throws Exception {
    doCodeTest(" setso 42");
  }

  public void testSoDirective() throws Exception {
    doCodeTest("label so.w 42");
  }

  public void testSoDirectiveNoDataSize() throws Exception {
    doCodeTest("label so 42");
  }

  public void testSoDirectiveNoExpression() throws Exception {
    doCodeTest("label so");
  }

  public void testAutoDirective() throws Exception {
    doCodeTest(" auto");
  }

  public void testMsourceDirective() throws Exception {
    doCodeTest(" msource on");
  }

  public void testMsourceDirectiveOffParameter() throws Exception {
    doCodeTest(" msource OFF");
  }

  public void testMsourceDirectiveMissingParameter() throws Exception {
    doCodeTest(" msource");
  }

  public void testOffsetDirective() throws Exception {
    doCodeTest(" offset 42");
  }

  public void testOffsetDirectiveWithoutExpression() throws Exception {
    doCodeTest(" offset");
  }

  public void testMask2Directive() throws Exception {
    doCodeTest(" mask2");
  }

  public void testOutputDirective() throws Exception {
    doCodeTest(" output fileName");
  }

  public void testAssertDirective() throws Exception {
    doCodeTest(" assert 0,'message'");
  }

  public void testAssertDirectiveNoMessage() throws Exception {
    doCodeTest(" assert 0");
  }

  public void testBaseregDirective() throws Exception {
    doCodeTest(" basereg label,a5");
  }

  public void testEndbDirective() throws Exception {
    doCodeTest(" endb a5");
  }

}
