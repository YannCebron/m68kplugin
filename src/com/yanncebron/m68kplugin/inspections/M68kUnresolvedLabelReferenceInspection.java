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

package com.yanncebron.m68kplugin.inspections;

import com.intellij.codeInspection.*;
import com.intellij.codeInspection.ex.InspectionToolWrapper;
import com.intellij.codeInspection.options.OptPane;
import com.intellij.codeInspection.ui.StringValidatorWithSwingSelector;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.profile.codeInspection.InspectionProjectProfileManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ObjectUtils;
import com.intellij.util.Processor;
import com.intellij.util.SmartList;
import com.yanncebron.m68kplugin.M68kBundle;
import com.yanncebron.m68kplugin.lang.psi.M68kPsiElement;
import com.yanncebron.m68kplugin.lang.psi.M68kVisitor;
import com.yanncebron.m68kplugin.lang.psi.conditional.*;
import com.yanncebron.m68kplugin.lang.psi.directive.M68kMacroCallDirective;
import com.yanncebron.m68kplugin.lang.psi.expression.M68kLabelRefExpression;
import com.yanncebron.m68kplugin.lang.stubs.index.M68kStubIndexKeys;
import com.yanncebron.m68kplugin.resolve.M68kImplicitMacroLabelResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@VisibleForTesting
public final class M68kUnresolvedLabelReferenceInspection extends LocalInspectionTool {

  @SuppressWarnings("UnstableApiUsage")
  public final List<@NlsSafe String> labelDefiningMacros = new ArrayList<>();

  private static final String INSPECTION_SHORT_NAME = InspectionProfileEntry.getShortName(M68kUnresolvedLabelReferenceInspection.class.getSimpleName());

  public static List<String> getLabelDefiningMacros(PsiElement psiElement) {
    InspectionProfileEntry entry = InspectionProjectProfileManager.getInstance(psiElement.getProject()).getCurrentProfile().getUnwrappedTool(INSPECTION_SHORT_NAME, psiElement);
    if (entry == null) return Collections.emptyList();

    assert entry instanceof M68kUnresolvedLabelReferenceInspection;
    return ((M68kUnresolvedLabelReferenceInspection) entry).labelDefiningMacros;
  }

  public static List<String> getLabelDefiningMacros(Project project) {
    InspectionToolWrapper<?, ?> tool = InspectionProjectProfileManager.getInstance(project).getCurrentProfile().getInspectionTool(INSPECTION_SHORT_NAME, project);
    assert tool != null;
    InspectionProfileEntry entry = tool.getTool();
    assert entry instanceof M68kUnresolvedLabelReferenceInspection;
    return ((M68kUnresolvedLabelReferenceInspection) entry).labelDefiningMacros;
  }

  @Override
  public @NotNull OptPane getOptionsPane() {
    return OptPane.pane(OptPane.stringList("labelDefiningMacros", M68kBundle.message("inspection.unresolved.label.reference.settings.macros.defining.labels"), new StringValidatorWithSwingSelector() {

      @Override
      public @NotNull String validatorId() {
        return "labelDefiningMacrosValidator";
      }

      @Override
      public @NlsContexts.HintText String getErrorMessage(@Nullable Project project, @NotNull String string) {
        if (project == null || project.isDefault() || DumbService.isDumb(project)) return null;
        if (StubIndex.getInstance().getAllKeys(M68kStubIndexKeys.MACRO, project).contains(string)) return null;

        return M68kBundle.message("inspection.unresolved.label.reference.settings.macros.cannot.resolve.macro", string);
      }

      @Override
      public @Nullable String select(@NotNull Project project) {
        if (project.isDefault() || DumbService.isDumb(project)) return null;

        // todo collect all _suitable_ macros
        Collection<String> macroNames = StubIndex.getInstance().getAllKeys(M68kStubIndexKeys.MACRO, project);
        macroNames.removeAll(labelDefiningMacros);
        List<String> sortedMacroNames = new ArrayList<>(macroNames);
        Collections.sort(sortedMacroNames);

        //noinspection deprecation
        int idx = Messages.showChooseDialog(project, M68kBundle.message("inspection.unresolved.label.reference.settings.macros.defining.labels.dialog.message"), M68kBundle.message("inspection.unresolved.label.reference.settings.macros.defining.labels.dialog.title"), null, sortedMacroNames.toArray(new String[0]), null);
        return idx != -1 ? sortedMacroNames.get(idx) : null;
      }
    }));
  }

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly,
                                        @NotNull LocalInspectionToolSession session) {
    return new M68kVisitor() {
      @Override
      public void visitLabelRefExpression(@NotNull M68kLabelRefExpression o) {
        highlightReference(holder, o);
      }
    };
  }

  private void highlightReference(@NotNull ProblemsHolder holder,
                                  @NotNull M68kPsiElement psiElement) {
    PsiPolyVariantReference reference = ObjectUtils.tryCast(psiElement.getReference(), PsiPolyVariantReference.class);
    assert reference != null : psiElement;

    // skip resolve if known defining macro call declaration
    if (!labelDefiningMacros.isEmpty()) {
      M68kMacroCallDirective macroCallDirective = PsiTreeUtil.getParentOfType(psiElement, M68kMacroCallDirective.class);
      if (macroCallDirective != null) {
        String macroName = macroCallDirective.getMacroNameElement().getText();
        if (labelDefiningMacros.contains(macroName)) {
          return;
        }
      }
    }

    boolean unresolved = reference.multiResolve(false).length == 0;
    if (unresolved) {
      // skip '\1' in labelRef
      if (psiElement.textContains('\\') && !(psiElement.textContains('@'))) {
        return;
      }

      if (isUsageInPotentiallyNonResolvingConditionalAssemblyDirective(psiElement)) {
        holder.registerProblem(reference, ProblemHighlightType.WEAK_WARNING);
      } else if (isUsageInMacroCall(psiElement)) {
        M68kMacroCallDirective macroCallDirective = PsiTreeUtil.getParentOfType(psiElement, M68kMacroCallDirective.class);
        assert macroCallDirective != null;
        if (isSuitableAsImplicitMacro(macroCallDirective)) {
          holder.registerProblemForReference(reference, ProblemHighlightType.WEAK_WARNING,
            ProblemsHolder.unresolvedReferenceMessage(reference),
            createAddMacroFix(macroCallDirective));
        } else {
          holder.registerProblem(reference, ProblemHighlightType.WEAK_WARNING); // possibly a dynamic parameter
        }

      } else {
        List<LocalQuickFix> macroCandidateFixes = new SmartList<>();
        Processor<M68kMacroCallDirective> macroCallDirectiveProcessor = macroCallDirective -> {
          if (isSuitableAsImplicitMacro(macroCallDirective)) {
            macroCandidateFixes.add(createAddMacroFix(macroCallDirective));
          }
          return true;
        };
        M68kImplicitMacroLabelResolver.processMacrosPossiblyDefiningLabel(macroCallDirectiveProcessor, psiElement.getProject(), reference.getCanonicalText());
        String descriptionTemplate = macroCandidateFixes.isEmpty() ? ProblemsHolder.unresolvedReferenceMessage(reference) : M68kBundle.message("inspection.unresolved.label.reference.cannot.resolve.macro.candidate", psiElement.getText());
        holder.registerProblemForReference(reference, ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
          descriptionTemplate,
          macroCandidateFixes.toArray(LocalQuickFix.EMPTY_ARRAY));
      }
    }
  }

  private @NotNull AddToInspectionOptionListFix<M68kUnresolvedLabelReferenceInspection> createAddMacroFix(M68kMacroCallDirective macroCallDirective) {
    String macroName = macroCallDirective.getMacroNameElement().getText();
    return new AddToInspectionOptionListFix<>(this, M68kBundle.message("inspection.unresolved.label.reference.cannot.resolve.macro.candidate.quick.fix.name", macroName),
      macroName, inspection -> inspection.labelDefiningMacros);
  }

  // todo no-op, check macro actually defines a label (`\1 rs.l 1`), change level to ERROR
  // M68kLabelResolveTest.testImplicitMacroLabelMacroCallUnsuitable
  private boolean isSuitableAsImplicitMacro(@SuppressWarnings("unused") M68kMacroCallDirective macroCallDirective) {
    return true;
  }

  private static boolean isUsageInPotentiallyNonResolvingConditionalAssemblyDirective(M68kPsiElement psiElement) {
    final M68kConditionalAssemblyDirective conditionalAssemblyDirective =
      PsiTreeUtil.getParentOfType(psiElement, M68kConditionalAssemblyDirective.class);
    return conditionalAssemblyDirective instanceof M68kIfdConditionalAssemblyDirective ||
      conditionalAssemblyDirective instanceof M68kIfndConditionalAssemblyDirective ||
      conditionalAssemblyDirective instanceof M68kIfmacrodConditionalAssemblyDirective ||
      conditionalAssemblyDirective instanceof M68kIfmacrondConditionalAssemblyDirective;
  }

  private static boolean isUsageInMacroCall(M68kPsiElement psiElement) {
    return PsiTreeUtil.getParentOfType(psiElement, M68kMacroCallDirective.class) != null;
  }
}
