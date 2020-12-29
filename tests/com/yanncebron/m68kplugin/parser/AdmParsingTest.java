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

import java.io.IOException;

/**
 * Test corner cases and pins/errors.
 */
@TestDataPath("$PROJECT_ROOT/testData/parser/adm")
public class AdmParsingTest extends M68kParsingTestCase {

  public AdmParsingTest() {
    super("adm");
  }

  public void testAdmAbsNegativeParenExpr() throws IOException {
    doCodeTest(" move.l 42,-(42)");
  }

  public void testAdmAbsNegativeParenMissingExpr() throws IOException {
    doCodeTest(" move.l 42,-(");
  }

  public void testAdmApdMissingClosingParen() throws IOException {
    doCodeTest(" move.l 3,-(a0");
  }

  public void testAdmAixMissingRrdIndex() throws IOException {
    doCodeTest(" move.l 3,42(a0,");
  }

  public void testAdmPciMissingRrdIndex() throws IOException {
    doCodeTest(" pea 42(pc,");
  }
}
