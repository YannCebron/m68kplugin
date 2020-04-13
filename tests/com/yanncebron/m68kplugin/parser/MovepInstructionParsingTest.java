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

@TestDataPath("$PROJECT_ROOT/testData/parser/moveInstructions/movep")
public class MovepInstructionParsingTest extends M68kParsingTestCase {

  public MovepInstructionParsingTest() {
    super("moveInstructions/movep");
  }

  public void testMovepInstructionMissingSource() throws Exception {
    doCodeTest(" movep ");
  }

  public void testMovepInstructionDrdAdi() throws Exception {
    doCodeTest(" movep d0,42(a0)");
  }

  public void testMovepInstructionDrdMissingDestination() throws Exception {
    doCodeTest(" movep d0,");
  }

  public void testMovepInstructionAdiDrd() throws Exception {
    doCodeTest(" movep 42(a0),d0");
  }

  public void testMovepInstructionAdiMissingDestination() throws Exception {
    doCodeTest(" movep 42(a0),");
  }

}