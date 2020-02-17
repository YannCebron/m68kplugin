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

@TestDataPath("$PROJECT_ROOT/testData/parser/dbccInstructions")
public class DbccInstructionsParsingTest extends M68kParsingTestCase {

  public DbccInstructionsParsingTest() {
    super("dbccInstructions");
  }

  public void testDbraInstructionMissingSource() throws IOException {
    doCodeTest(" dbra ");
  }

  public void testDbraInstructionMissingLabel() throws IOException {
    doCodeTest(" dbra d0,");
  }

  public void testDbraInstruction() throws IOException {
    doCodeTest(" dbra d0,label");
  }

  public void testDbraDataSizeShortInstruction() throws IOException {
    doCodeTest(" dbra.s d0,label");
  }

  public void testDbraDataSizeLongInstruction() throws IOException {
    doCodeTest(" dbra.l d0,label");
  }

  public void testDbcsInstruction() throws IOException {
    doCodeTest(" dbcs d0,label");
  }

  public void testDBlsInstruction() throws IOException {
    doCodeTest(" dbls d0,label");
  }

  public void testDbeqInstruction() throws IOException {
    doCodeTest(" dbeq d0,label");
  }

  public void testDbneInstruction() throws IOException {
    doCodeTest(" dbne d0,label");
  }

  public void testDbhiInstruction() throws IOException {
    doCodeTest(" dbhi d0,label");
  }

  public void testDbccInstruction() throws IOException {
    doCodeTest(" dbcc d0,label");
  }

  public void testDbplInstruction() throws IOException {
    doCodeTest(" dbpl d0,label");
  }

  public void testDbvcInstruction() throws IOException {
    doCodeTest(" dbvc d0,label");
  }

  public void testDbltInstruction() throws IOException {
    doCodeTest(" dblt d0,label");
  }

  public void testDbleInstruction() throws IOException {
    doCodeTest(" dble d0,label");
  }

  public void testDbgtInstruction() throws IOException {
    doCodeTest(" dbgt d0,label");
  }

  public void testDbgeInstruction() throws IOException {
    doCodeTest(" dbge d0,label");
  }

  public void testDbmiInstruction() throws IOException {
    doCodeTest(" dbmi d0,label");
  }

  public void testDbvsInstruction() throws IOException {
    doCodeTest(" dbvs d0,label");
  }

  public void testDbfInstruction() throws IOException {
    doCodeTest(" dbf d0,label");
  }

  public void testDbtInstruction() throws IOException {
    doCodeTest(" dbt d0,label");
  }

}
