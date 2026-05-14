/*
 * Copyright 2026 The Authors
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

package com.yanncebron.m68kplugin.intentions;

import com.intellij.codeInsight.intention.PriorityAction;
import com.intellij.modcommand.ActionContext;
import com.intellij.modcommand.ModCommand;
import com.intellij.modcommand.ModCommandAction;
import com.intellij.modcommand.Presentation;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.xml.util.XmlStringUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

final class M68kShowUsedRegistersIntention implements ModCommandAction, DumbAware {

  @Override
  public @NotNull @Nls(capitalization = Nls.Capitalization.Sentence) String getFamilyName() {
    return M68kBundle.message("intention.M68kShowUsedRegistersIntention.text");
  }

  @Override
  public @Nullable Presentation getPresentation(@NotNull ActionContext context) {
    if (context.file() instanceof M68kFile && !context.selection().isEmpty()) {
      return Presentation.of(getFamilyName()).withPriority(PriorityAction.Priority.TOP);
    }
    return null;
  }

  @Override
  public @NotNull ModCommand perform(@NotNull ActionContext context) {
    Set<M68kRegister> used = getUsedRegisters(context.file(), context.selection());

    if (used.isEmpty()) {
      return ModCommand.info(M68kBundle.message("intention.M68kShowUsedRegistersIntention.hint.no.registers"));
    }

    StringBuilder sb = new StringBuilder();
    sb.append("<table cellpadding=3>");
    final Iterator<M68kRegister> dataRegIt = EnumSet.range(M68kRegister.D0, M68kRegister.D7).iterator();
    final Iterator<M68kRegister> addrRegIt = EnumSet.range(M68kRegister.A0, M68kRegister.A7).iterator();
    final Iterator<M68kRegister> specialRegIt = EnumSet.range(M68kRegister.SP, M68kRegister.CCR).iterator();
    final Iterator<M68kRegister> controlRegIt = EnumSet.range(M68kRegister.DFC, M68kRegister.VBR).iterator();
    while (dataRegIt.hasNext()) {
      sb.append("<tr>");
      printRegister(sb, used, dataRegIt);
      printRegister(sb, used, addrRegIt);
      printRegister(sb, used, specialRegIt);
      printRegister(sb, used, controlRegIt);
      sb.append("</tr>");
    }
    sb.append("</table>");

    return ModCommand.info(XmlStringUtil.wrapInHtml(sb));
  }

  private void printRegister(StringBuilder message, Set<M68kRegister> used, Iterator<M68kRegister> it) {
    message.append("<td>");
    if (it.hasNext()) {
      final M68kRegister register = it.next();
      if (used.contains(register)) {
        message.append("<b>").append(register.toString()).append("</b>");
      } else {
        message.append(register.toString());
      }
    }
    message.append("</td>");
  }

  @NotNull
  static Set<M68kRegister> getUsedRegisters(@NotNull PsiFile file, @NotNull TextRange textRange) {
    Set<M68kRegister> used = EnumSet.noneOf(M68kRegister.class);
    final M68kVisitor registerVisitor = new M68kVisitor() {

      private void addIfInside(PsiElement o, M68kRegister register) {
        if (textRange.contains(o.getTextRange())) {
          used.add(register);
        }
      }

      @Override
      public void visitPsiElement(@NotNull M68kPsiElement o) {
        o.acceptChildren(this);
      }

      @Override
      public void visitAdmPcd(@NotNull M68kAdmPcd o) {
        visitAdmWithRegister(o);
      }

      @Override
      public void visitAdmPci(@NotNull M68kAdmPci o) {
        visitAdmWithRegister(o);
        M68kAdmRrdIndex admRrdIndex = o.getAdmRrdIndex();
        if (admRrdIndex != null) {
          visitAdmWithRegister(admRrdIndex);
        }
      }

      @Override
      public void visitAdmWithRegister(@NotNull M68kAdmWithRegister o) {
        addIfInside(o, o.getRegister());
      }

      @Override
      public void visitAdmRegisterList(@NotNull M68kAdmRegisterList o) {
        for (M68kRegisterRange registerRange : o.getRegisterRangeList()) {
          for (M68kRegister register : registerRange.getRegisters()) {
            addIfInside(o, register);
          }
        }
      }
    };

    file.getFirstChild().acceptChildren(registerVisitor);
    M68kPsiTreeUtil.processSiblingsForwards(file.getFirstChild(), element -> {
      element.acceptChildren(registerVisitor);
      return true;
    });
    return used;
  }
}
