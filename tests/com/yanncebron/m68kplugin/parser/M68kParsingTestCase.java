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

import com.intellij.testFramework.ParsingTestCase;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.M68kParserDefinition;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

abstract class M68kParsingTestCase extends ParsingTestCase {

  protected M68kParsingTestCase(@NonNls final String dataPath) {
    super(dataPath, M68kFileType.INSTANCE.getDefaultExtension(), new M68kParserDefinition());
  }

  @Override
  protected void doCodeTest(@NotNull String code) throws IOException {
    super.doCodeTest(code);

    ensureCorrectReparse(myFile);

    final String testName = getTestName();
    if (!testName.contains("Missing") && !testName.contains("Wrong")) {
      ensureNoErrorElements();
    }
  }

  @Override
  protected String getTestDataPath() {
    return "testData/parser";
  }
}
