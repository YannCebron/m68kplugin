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

package com.yanncebron.m68kplugin.codeInsight;

import com.intellij.codeInsight.actions.SimpleCodeInsightAction;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.xml.util.XmlStringUtil;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.M68kFile;
import com.yanncebron.m68kplugin.lang.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public class M68kShowUsedRegistersAction extends SimpleCodeInsightAction {

  @Override
  public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
    Set<M68kRegister> used = getUsedRegisters(editor, file);

    if (used.isEmpty()) {
      HintManager.getInstance().showInformationHint(editor, M68kBundle.message("action.M68kShowUsedRegistersAction.hint.no.registers"));
      return;
    }

    StringBuilder sb = new StringBuilder();
    sb.append("<table cellpadding=3>");
    final Iterator<M68kRegister> dataRegIt = EnumSet.range(M68kRegister.D0, M68kRegister.D7).iterator();
    final Iterator<M68kRegister> addrRegIt = EnumSet.range(M68kRegister.A0, M68kRegister.A7).iterator();
    final Iterator<M68kRegister> specialRegIt = EnumSet.complementOf(EnumSet.range(M68kRegister.D0, M68kRegister.A7)).iterator();
    while (dataRegIt.hasNext()) {
      sb.append("<tr>");
      printRegister(sb, used, dataRegIt);
      printRegister(sb, used, addrRegIt);
      printRegister(sb, used, specialRegIt);
      sb.append("</tr>");
    }
    sb.append("</table>");

    HintManager.getInstance().showInformationHint(editor, XmlStringUtil.wrapInHtml(sb));
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
  private Set<M68kRegister> getUsedRegisters(@NotNull Editor editor, @NotNull PsiFile file) {
    final SelectionModel selectionModel = editor.getSelectionModel();
    TextRange selectionRange = TextRange.create(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd());

    Set<M68kRegister> used = EnumSet.noneOf(M68kRegister.class);
    final M68kVisitor registerVisitor = new M68kVisitor() {

      private void addIfInside(PsiElement o, M68kRegister register) {
        if (selectionRange.contains(o.getTextRange())) {
          used.add(register);
        }
      }

      @Override
      public void visitPsiElement(@NotNull M68kPsiElement o) {
        o.acceptChildren(this);
      }

      @Override
      public void visitAdmSr(@NotNull M68kAdmSr o) {
        addIfInside(o, M68kRegister.SR);
      }

      @Override
      public void visitAdmCcr(@NotNull M68kAdmCcr o) {
        addIfInside(o, M68kRegister.CCR);
      }

      @Override
      public void visitAdmUsp(@NotNull M68kAdmUsp o) {
        addIfInside(o, M68kRegister.USP);
      }

      @Override
      public void visitAdmPcd(@NotNull M68kAdmPcd o) {
        addIfInside(o, M68kRegister.PC);
      }

      @Override
      public void visitAdmPci(@NotNull M68kAdmPci o) {
        addIfInside(o, M68kRegister.PC);
      }

      @Override
      public void visitAdmDrd(@NotNull M68kAdmDrd o) {
        addIfInside(o, o.getRegister());
      }

      @Override
      public void visitAdmArd(@NotNull M68kAdmArd o) {
        addIfInside(o, o.getRegister());
      }
    };

    file.getFirstChild().acceptChildren(registerVisitor);
    M68kPsiTreeUtil.processSiblingsForwards(file.getFirstChild(), element -> {
      element.acceptChildren(registerVisitor);
      return true;
    });
    return used;
  }

  @Override
  protected boolean isValidForFile(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
    return file instanceof M68kFile && editor.getSelectionModel().hasSelection();
  }

  @Override
  public boolean startInWriteAction() {
    return false;
  }
}
