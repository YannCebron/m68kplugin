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

  public void testEquDirectiveMissingExpression() throws Exception {
    doCodeTest("label equ ");
  }

  public void testEqDirective() throws Exception {
    doCodeTest("label = 42");
  }

  public void testEqDirectiveMissingExpression() throws Exception {
    doCodeTest("label = ");
  }

  public void testEqrDirectiveDataRegister() throws Exception {
    doCodeTest("label equr d6");
  }

  public void testEqrDirectiveAddressRegister() throws Exception {
    doCodeTest("label equr a4");
  }

  public void testEqrDirectiveMissingRegister() throws Exception {
    doCodeTest("label equr ");
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

  public void testIncdirDirective() throws Exception {
    doCodeTest(" incdir \"path\"");
  }

  public void testIncdirDirectiveMissingPath() throws Exception {
    doCodeTest(" incdir ");
  }

  public void testIncludeDirective() throws Exception {
    doCodeTest(" include \"includeFile\"");
  }

  public void testIncludeDirectiveMissingPath() throws Exception {
    doCodeTest(" include ");
  }

  public void testDcDirective() throws Exception {
    doCodeTest(" dc.b 42");
  }

  public void testDcDirectiveWithoutDataSize() throws Exception {
    doCodeTest(" dc 42");
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

  public void testRsDirectiveMissingExpression() throws Exception {
    doCodeTest(" rs ");
  }

  public void testBlkDirective() throws Exception {
    doCodeTest(" blk 42,666");
  }

  public void testOptDirective() throws Exception {
    doCodeTest(" opt w+");
  }

  public void testOptDirectiveMultipleParameters() throws Exception {
    doCodeTest(" opt w+,u+");
  }

  public void testOptDirectiveMissingParameter() throws Exception {
    doCodeTest(" opt ");
  }

  public void testMacroDirective() throws Exception {
    doCodeTest("macroName MACRO");
  }

  public void testEndmDirective() throws Exception {
    doCodeTest("endm");
  }
}
