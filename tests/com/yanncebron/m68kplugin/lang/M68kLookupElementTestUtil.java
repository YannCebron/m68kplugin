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

package com.yanncebron.m68kplugin.lang;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.testFramework.fixtures.CodeInsightTestFixture;
import com.intellij.testFramework.fixtures.TestLookupElementPresentation;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class M68kLookupElementTestUtil {

  @NotNull
  public static LookupElement findLookupElement(CodeInsightTestFixture fixture, String lookupString) {
    return findLookupElement(fixture, lookupString, -1);
  }

  @NotNull
  public static LookupElement findLookupElement(CodeInsightTestFixture fixture, String lookupString, int textOffset) {
    final LookupElement[] lookupElements = fixture.getLookupElements();
    assertNotNull(lookupString, lookupElements);
    return findLookupElement(lookupElements, lookupString, textOffset);
  }

  @NotNull
  public static LookupElement findLookupElement(LookupElement[] lookupElements, String lookupString, int textOffset) {
    final LookupElement lookupElement = ContainerUtil.find(lookupElements, element ->
      element.getLookupString().equals(lookupString) &&
        (textOffset == -1 || textOffset == Objects.requireNonNull(element.getPsiElement()).getTextOffset()));

    assertNotNull(StringUtil.join(lookupElements, element ->
          element.getLookupString() + ":" + (element.getPsiElement() == null ? "" : element.getPsiElement().getTextOffset()),
        "\n"),
      lookupElement);
    return lookupElement;
  }

  public static void assertPrioritizedLookupElement(LookupElement element, double expectedPriority) {
    final PrioritizedLookupElement<?> prioritizedLookupElement = element.as(PrioritizedLookupElement.CLASS_CONDITION_KEY);
    assertNotNull(prioritizedLookupElement);
    assertEquals(expectedPriority, prioritizedLookupElement.getPriority());
  }

  public static void assertLookupIcon(LookupElementPresentation presentation, Icon expectedIcon) {
    assertEquals(expectedIcon, TestLookupElementPresentation.unwrapIcon(presentation.getIcon()));
  }
}
