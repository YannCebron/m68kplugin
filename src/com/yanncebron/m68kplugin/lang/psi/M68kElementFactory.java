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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;

public class M68kElementFactory {

  public static M68kFile createFile(Project project, String text) {
    return (M68kFile) PsiFileFactory.getInstance(project).
      createFileFromText("dummy.s", M68kFileType.INSTANCE, text);
  }

  public static M68kLabel createLabel(Project project, String name) {
    final M68kFile file = createFile(project, name);
    return (M68kLabel) file.getFirstChild();
  }

  public static M68kLabelRefExpression createLabelRefExpression(Project project, String name) {
    final M68kBraInstruction firstChild = (M68kBraInstruction) createFile(project, " bra " + name).getChildren()[1];
    final M68kExpression expression = firstChild.getExpression();
    assert expression instanceof M68kLabelRefExpression;
    return (M68kLabelRefExpression) expression;
  }
}
