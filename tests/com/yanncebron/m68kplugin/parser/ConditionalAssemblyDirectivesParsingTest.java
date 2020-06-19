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

@TestDataPath("$PROJECT_ROOT/testData/parser/directives/conditionalAssembly")
public class ConditionalAssemblyDirectivesParsingTest extends M68kParsingTestCase {

  public ConditionalAssemblyDirectivesParsingTest() {
    super("directives/conditionalAssembly");
  }

  public void testIfDirective() throws Exception {
    doCodeTest(" if NAME");
  }

  public void testIfbDirective() throws Exception {
    doCodeTest(" ifb NAME");
  }

  public void testIfnbDirective() throws Exception {
    doCodeTest(" ifnb NAME");
  }

  public void testIfcDirective() throws Exception {
    doCodeTest(" ifc arg1,arg2");
  }

  public void testIfncDirective() throws Exception {
    doCodeTest(" ifnc arg1,arg2");
  }

  public void testIfncDirectiveMissingArg2() throws Exception {
    doCodeTest(" ifnc arg1,");
  }

  public void testIfdDirective() throws Exception {
    doCodeTest(" ifd NAME");
  }

  public void testIfdDirectiveMissingLabel() throws Exception {
    doCodeTest(" ifd ");
  }

  public void testIfeqDirective() throws Exception {
    doCodeTest(" ifeq 1+2");
  }

  public void testIfneDirective() throws Exception {
    doCodeTest(" ifne 1+2");
  }

  public void testIfgeDirective() throws Exception {
    doCodeTest(" ifge 1+2");
  }

  public void testIfgtDirective() throws Exception {
    doCodeTest(" ifgt 1+2");
  }

  public void testIfleDirective() throws Exception {
    doCodeTest(" ifle 1+2");
  }

  public void testIfltDirective() throws Exception {
    doCodeTest(" iflt 1+2");
  }

  public void testIfndDirective() throws Exception {
    doCodeTest(" ifnd NAME");
  }

  public void testElseDirective() throws Exception {
    doCodeTest(" else");
  }

  public void testElseifDirective() throws Exception {
    doCodeTest(" elseif");
  }

  public void testEndcDirective() throws Exception {
    doCodeTest(" endc");
  }

}
