/*
 * Copyright 2023 The Authors
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

package com.yanncebron.m68kplugin.lang.psi.expression.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.util.IncorrectOperationException;
import com.yanncebron.m68kplugin.lang.psi.M68kElementFactory;
import com.yanncebron.m68kplugin.lang.psi.M68kLocalLabelMode;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class M68kLabelRefExpressionElementManipulator extends AbstractElementManipulator<M68kLabelRefExpression> {

  @Nullable
  @Override
  public M68kLabelRefExpression handleContentChange(@NotNull M68kLabelRefExpression element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
    String labelName = newContent;

    // todo hack for local labels, extract to M68kLabelRefExpression#isLocal?
    final M68kLocalLabelMode localLabelMode = M68kLocalLabelMode.find(element.getText());
    if (localLabelMode != null) {
      labelName = localLabelMode.getPatchedName(newContent);
    }

    final M68kLabelRefExpression labelReference = M68kElementFactory.createLabelRefExpression(element.getProject(), labelName);
    element.replace(labelReference);
    return element;
  }
}
