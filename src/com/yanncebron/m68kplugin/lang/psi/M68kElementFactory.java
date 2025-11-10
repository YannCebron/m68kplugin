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

package com.yanncebron.m68kplugin.lang.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.M68kFileType;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kExpression;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class M68kElementFactory {

  @NotNull
  public static M68kFile createFile(Project project, String text) {
    return (M68kFile) PsiFileFactory.getInstance(project).
      createFileFromText("dummy.s", M68kFileType.INSTANCE, text);
  }

  @NotNull
  public static M68kLabel createLabel(Project project, String name) {
    final M68kFile file = createFile(project, name);
    final PsiElement firstChild = file.getFirstChild();
    assert firstChild instanceof M68kLabel : name;
    return (M68kLabel) firstChild;
  }

  @NotNull
  public static M68kMacroCallDirective createMacroCall(Project project, String name) {
    final M68kFile file = createFile(project, " " + name);
    final PsiElement firstChild = file.getChildren()[1];
    assert firstChild instanceof M68kMacroCallDirective : name;
    return (M68kMacroCallDirective) firstChild;
  }

  @NotNull
  public static M68kAdmArd createAddressRegister(Project project, String text) {
    final M68kExgInstruction child = (M68kExgInstruction) createFile(project, " exg " + text + ",d0").getChildren()[1];
    final M68kAdmRrd source = child.getSource();
    assert source != null : text;
    final M68kAdmArd admArd = source.getAdmArd();
    assert admArd != null : text;
    return admArd;
  }

  @NotNull
  public static M68kLabelRefExpression createLabelRefExpression(Project project, String name) {
    final M68kBraInstruction firstChild = (M68kBraInstruction) createFile(project, " bra " + name).getChildren()[1];
    final M68kExpression expression = Objects.requireNonNull(firstChild.getAdmAbs()).getExpression();
    assert expression instanceof M68kLabelRefExpression : name;
    return (M68kLabelRefExpression) expression;
  }
}
