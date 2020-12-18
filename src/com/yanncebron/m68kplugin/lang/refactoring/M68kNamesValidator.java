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

package com.yanncebron.m68kplugin.lang.refactoring;

import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.ContainerUtil;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenGroups;
import com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes;
import com.yanncebron.m68kplugin.lexer.M68kLexer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class M68kNamesValidator implements NamesValidator {

  private static final Collection<String> KEYWORDS =
    ContainerUtil.map(
      ContainerUtil.concat(
        M68kTokenGroups.INSTRUCTIONS.getTypes(),
        M68kTokenGroups.DIRECTIVES.getTypes(),
        M68kTokenGroups.CONDITIONAL_ASSEMBLY_DIRECTIVES.getTypes()),
      StringUtil.createToStringFunction(IElementType.class));

  @Override
  public boolean isKeyword(@NotNull String name, Project project) {
    return KEYWORDS.contains(StringUtil.toLowerCase(name));
  }

  @Override
  public boolean isIdentifier(@NotNull String input, Project project) {
    String name = StringUtil.startsWithChar(input, '.') ? input.substring(1) : input;
    final M68kLexer lexer = new M68kLexer();
    lexer.start(name);
    return lexer.getTokenEnd() == name.length() && lexer.getTokenType() == M68kTokenTypes.ID;
  }
}
