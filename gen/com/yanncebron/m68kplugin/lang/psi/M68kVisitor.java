/*
 * Copyright 2022 The Authors
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

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.yanncebron.m68kplugin.lang.psi.conditional.*;
import com.yanncebron.m68kplugin.lang.psi.directive.*;
import com.yanncebron.m68kplugin.lang.psi.expression.*;
import com.intellij.psi.PsiLiteralValue;

public class M68kVisitor extends PsiElementVisitor {

  public void visitAbcdInstruction(@NotNull M68kAbcdInstruction o) {
    visitBcdInstructionBase(o);
  }

  public void visitAc68080Directive(@NotNull M68kAc68080Directive o) {
    visitDirective(o);
  }

  public void visitAddInstruction(@NotNull M68kAddInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitAddaInstruction(@NotNull M68kAddaInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitAddiInstruction(@NotNull M68kAddiInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitAddqInstruction(@NotNull M68kAddqInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitAddwatchDirective(@NotNull M68kAddwatchDirective o) {
    visitDirective(o);
  }

  public void visitAddxInstruction(@NotNull M68kAddxInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitAdmAbs(@NotNull M68kAdmAbs o) {
    visitDataSized(o);
    // visitAdm(o);
  }

  public void visitAdmAdi(@NotNull M68kAdmAdi o) {
    visitAdmWithDisplacement(o);
    // visitAdmWithIndirectAddressRegister(o);
  }

  public void visitAdmAix(@NotNull M68kAdmAix o) {
    visitAdmWithDisplacement(o);
    // visitAdmWithIndirectAddressRegister(o);
  }

  public void visitAdmApd(@NotNull M68kAdmApd o) {
    visitAdmWithIndirectAddressRegister(o);
  }

  public void visitAdmApi(@NotNull M68kAdmApi o) {
    visitAdmWithIndirectAddressRegister(o);
  }

  public void visitAdmArd(@NotNull M68kAdmArd o) {
    visitAdmWithRegister(o);
  }

  public void visitAdmAri(@NotNull M68kAdmAri o) {
    visitAdmWithIndirectAddressRegister(o);
  }

  public void visitAdmCcr(@NotNull M68kAdmCcr o) {
    visitAdmWithRegister(o);
  }

  public void visitAdmDfc(@NotNull M68kAdmDfc o) {
    visitAdmWithRegister(o);
  }

  public void visitAdmDrd(@NotNull M68kAdmDrd o) {
    visitAdmWithRegister(o);
  }

  public void visitAdmImm(@NotNull M68kAdmImm o) {
    visitAdm(o);
    // visitDataSized(o);
  }

  public void visitAdmPcd(@NotNull M68kAdmPcd o) {
    visitAdmWithDisplacement(o);
  }

  public void visitAdmPci(@NotNull M68kAdmPci o) {
    visitAdmWithDisplacement(o);
  }

  public void visitAdmQuick(@NotNull M68kAdmQuick o) {
    visitAdm(o);
  }

  public void visitAdmRegisterList(@NotNull M68kAdmRegisterList o) {
    visitAdm(o);
  }

  public void visitAdmRrd(@NotNull M68kAdmRrd o) {
    visitAdmWithRrd(o);
  }

  public void visitAdmRrdIndex(@NotNull M68kAdmRrdIndex o) {
    visitDataSized(o);
    // visitAdmWithRrd(o);
  }

  public void visitAdmSfc(@NotNull M68kAdmSfc o) {
    visitAdmWithRegister(o);
  }

  public void visitAdmSr(@NotNull M68kAdmSr o) {
    visitAdmWithRegister(o);
  }

  public void visitAdmUsp(@NotNull M68kAdmUsp o) {
    visitAdmWithRegister(o);
  }

  public void visitAdmVbr(@NotNull M68kAdmVbr o) {
    visitAdmWithRegister(o);
  }

  public void visitAlignDirective(@NotNull M68kAlignDirective o) {
    visitDirective(o);
  }

  public void visitAndExpression(@NotNull M68kAndExpression o) {
    visitBinaryExpression(o);
  }

  public void visitAndInstruction(@NotNull M68kAndInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitAndiInstruction(@NotNull M68kAndiInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitAslInstruction(@NotNull M68kAslInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitAsrInstruction(@NotNull M68kAsrInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitAutoDirective(@NotNull M68kAutoDirective o) {
    visitDirective(o);
  }

  public void visitBccInstruction(@NotNull M68kBccInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBccInstructionBase(@NotNull M68kBccInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBcdInstructionBase(@NotNull M68kBcdInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBchgInstruction(@NotNull M68kBchgInstruction o) {
    visitBitManipulationInstructionBase(o);
  }

  public void visitBclrInstruction(@NotNull M68kBclrInstruction o) {
    visitBitManipulationInstructionBase(o);
  }

  public void visitBcsInstruction(@NotNull M68kBcsInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBeqInstruction(@NotNull M68kBeqInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBgeInstruction(@NotNull M68kBgeInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBgtInstruction(@NotNull M68kBgtInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBhiInstruction(@NotNull M68kBhiInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBhsInstruction(@NotNull M68kBhsInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBinaryExpression(@NotNull M68kBinaryExpression o) {
    visitExpression(o);
  }

  public void visitBitManipulationInstructionBase(@NotNull M68kBitManipulationInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBkptInstruction(@NotNull M68kBkptInstruction o) {
    visitInstruction(o);
  }

  public void visitBleInstruction(@NotNull M68kBleInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBlkDirective(@NotNull M68kBlkDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitBloInstruction(@NotNull M68kBloInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBlsInstruction(@NotNull M68kBlsInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBltInstruction(@NotNull M68kBltInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBmiInstruction(@NotNull M68kBmiInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBneInstruction(@NotNull M68kBneInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBoolInstructionBase(@NotNull M68kBoolInstructionBase o) {
    visitDataSized(o);
    // visitPrivilegedInstruction(o);
    // visitInstruction(o);
  }

  public void visitBplInstruction(@NotNull M68kBplInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBraInstruction(@NotNull M68kBraInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBsetInstruction(@NotNull M68kBsetInstruction o) {
    visitBitManipulationInstructionBase(o);
  }

  public void visitBsrInstruction(@NotNull M68kBsrInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBssCDirective(@NotNull M68kBssCDirective o) {
    visitDirective(o);
  }

  public void visitBssDirective(@NotNull M68kBssDirective o) {
    visitDirective(o);
  }

  public void visitBssFDirective(@NotNull M68kBssFDirective o) {
    visitDirective(o);
  }

  public void visitBtstInstruction(@NotNull M68kBtstInstruction o) {
    visitBitManipulationInstructionBase(o);
  }

  public void visitBvcInstruction(@NotNull M68kBvcInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitBvsInstruction(@NotNull M68kBvsInstruction o) {
    visitBccInstructionBase(o);
  }

  public void visitChkInstruction(@NotNull M68kChkInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitClrInstruction(@NotNull M68kClrInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitClrfoDirective(@NotNull M68kClrfoDirective o) {
    visitDirective(o);
  }

  public void visitClrsoDirective(@NotNull M68kClrsoDirective o) {
    visitDirective(o);
  }

  public void visitCmpInstruction(@NotNull M68kCmpInstruction o) {
    visitCmpInstructionBase(o);
  }

  public void visitCmpInstructionBase(@NotNull M68kCmpInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitCmpaInstruction(@NotNull M68kCmpaInstruction o) {
    visitCmpInstructionBase(o);
  }

  public void visitCmpiInstruction(@NotNull M68kCmpiInstruction o) {
    visitCmpInstructionBase(o);
  }

  public void visitCmpmInstruction(@NotNull M68kCmpmInstruction o) {
    visitCmpInstructionBase(o);
  }

  public void visitCnopDirective(@NotNull M68kCnopDirective o) {
    visitDirective(o);
  }

  public void visitCodeCDirective(@NotNull M68kCodeCDirective o) {
    visitDirective(o);
  }

  public void visitCodeDirective(@NotNull M68kCodeDirective o) {
    visitDirective(o);
  }

  public void visitCodeFDirective(@NotNull M68kCodeFDirective o) {
    visitDirective(o);
  }

  public void visitCpu32Directive(@NotNull M68kCpu32Directive o) {
    visitDirective(o);
  }

  public void visitCsegDirective(@NotNull M68kCsegDirective o) {
    visitDirective(o);
  }

  public void visitDataCDirective(@NotNull M68kDataCDirective o) {
    visitDirective(o);
  }

  public void visitDataDirective(@NotNull M68kDataDirective o) {
    visitDirective(o);
  }

  public void visitDataFDirective(@NotNull M68kDataFDirective o) {
    visitDirective(o);
  }

  public void visitDataSized(@NotNull M68kDataSized o) {
    visitPsiElement(o);
  }

  public void visitDbccInstruction(@NotNull M68kDbccInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbccInstructionBase(@NotNull M68kDbccInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitDbcsInstruction(@NotNull M68kDbcsInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbeqInstruction(@NotNull M68kDbeqInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbfInstruction(@NotNull M68kDbfInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbgeInstruction(@NotNull M68kDbgeInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbgtInstruction(@NotNull M68kDbgtInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbhiInstruction(@NotNull M68kDbhiInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbhsInstruction(@NotNull M68kDbhsInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbleInstruction(@NotNull M68kDbleInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbloInstruction(@NotNull M68kDbloInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDblsInstruction(@NotNull M68kDblsInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbltInstruction(@NotNull M68kDbltInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbmiInstruction(@NotNull M68kDbmiInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbneInstruction(@NotNull M68kDbneInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbplInstruction(@NotNull M68kDbplInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbraInstruction(@NotNull M68kDbraInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbtInstruction(@NotNull M68kDbtInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbvcInstruction(@NotNull M68kDbvcInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDbvsInstruction(@NotNull M68kDbvsInstruction o) {
    visitDbccInstructionBase(o);
  }

  public void visitDcDirective(@NotNull M68kDcDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitDcbDirective(@NotNull M68kDcbDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitDivExpression(@NotNull M68kDivExpression o) {
    visitBinaryExpression(o);
  }

  public void visitDivsInstruction(@NotNull M68kDivsInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitDivuInstruction(@NotNull M68kDivuInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitDrDirective(@NotNull M68kDrDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitDsDirective(@NotNull M68kDsDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitDsegDirective(@NotNull M68kDsegDirective o) {
    visitDirective(o);
  }

  public void visitDxDirective(@NotNull M68kDxDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitEchoDirective(@NotNull M68kEchoDirective o) {
    visitDirective(o);
  }

  public void visitEinlineDirective(@NotNull M68kEinlineDirective o) {
    visitDirective(o);
  }

  public void visitElseConditionalAssemblyDirective(@NotNull M68kElseConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitElseifConditionalAssemblyDirective(@NotNull M68kElseifConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitEndDirective(@NotNull M68kEndDirective o) {
    visitDirective(o);
  }

  public void visitEndcConditionalAssemblyDirective(@NotNull M68kEndcConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitEndifConditionalAssemblyDirective(@NotNull M68kEndifConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitEndmDirective(@NotNull M68kEndmDirective o) {
    visitDirective(o);
  }

  public void visitEndrDirective(@NotNull M68kEndrDirective o) {
    visitDirective(o);
  }

  public void visitEorInstruction(@NotNull M68kEorInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitEoriInstruction(@NotNull M68kEoriInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitEquDirective(@NotNull M68kEquDirective o) {
    visitEquDirectiveBase(o);
  }

  public void visitEquDirectiveBase(@NotNull M68kEquDirectiveBase o) {
    visitDirective(o);
  }

  public void visitEqualsDirective(@NotNull M68kEqualsDirective o) {
    visitEquDirectiveBase(o);
  }

  public void visitEqualsExpression(@NotNull M68kEqualsExpression o) {
    visitBinaryExpression(o);
  }

  public void visitEqurDirective(@NotNull M68kEqurDirective o) {
    visitDirective(o);
  }

  public void visitEremDirective(@NotNull M68kEremDirective o) {
    visitDirective(o);
  }

  public void visitEvenDirective(@NotNull M68kEvenDirective o) {
    visitDirective(o);
  }

  public void visitExgInstruction(@NotNull M68kExgInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitExpression(@NotNull M68kExpression o) {
    visitPsiElement(o);
  }

  public void visitExtInstruction(@NotNull M68kExtInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitFailDirective(@NotNull M68kFailDirective o) {
    visitDirective(o);
  }

  public void visitFarDirective(@NotNull M68kFarDirective o) {
    visitDirective(o);
  }

  public void visitFoDirective(@NotNull M68kFoDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitFpuDirective(@NotNull M68kFpuDirective o) {
    visitDirective(o);
  }

  public void visitGtEqExpression(@NotNull M68kGtEqExpression o) {
    visitBinaryExpression(o);
  }

  public void visitGtExpression(@NotNull M68kGtExpression o) {
    visitBinaryExpression(o);
  }

  public void visitIdntDirective(@NotNull M68kIdntDirective o) {
    visitDirective(o);
  }

  public void visitIf1ConditionalAssemblyDirective(@NotNull M68kIf1ConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIf2ConditionalAssemblyDirective(@NotNull M68kIf2ConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfConditionalAssemblyDirective(@NotNull M68kIfConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfbConditionalAssemblyDirective(@NotNull M68kIfbConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfcConditionalAssemblyDirective(@NotNull M68kIfcConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfdConditionalAssemblyDirective(@NotNull M68kIfdConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfeqConditionalAssemblyDirective(@NotNull M68kIfeqConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfgeConditionalAssemblyDirective(@NotNull M68kIfgeConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfgtConditionalAssemblyDirective(@NotNull M68kIfgtConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfleConditionalAssemblyDirective(@NotNull M68kIfleConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfltConditionalAssemblyDirective(@NotNull M68kIfltConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfmacrodConditionalAssemblyDirective(@NotNull M68kIfmacrodConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfmacrondConditionalAssemblyDirective(@NotNull M68kIfmacrondConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfmiConditionalAssemblyDirective(@NotNull M68kIfmiConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfnbConditionalAssemblyDirective(@NotNull M68kIfnbConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfncConditionalAssemblyDirective(@NotNull M68kIfncConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfndConditionalAssemblyDirective(@NotNull M68kIfndConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfneConditionalAssemblyDirective(@NotNull M68kIfneConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfp1ConditionalAssemblyDirective(@NotNull M68kIfp1ConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIfplConditionalAssemblyDirective(@NotNull M68kIfplConditionalAssemblyDirective o) {
    visitConditionalAssemblyDirective(o);
  }

  public void visitIllegalInstruction(@NotNull M68kIllegalInstruction o) {
    visitInstruction(o);
  }

  public void visitIncbinDirective(@NotNull M68kIncbinDirective o) {
    visitDirective(o);
  }

  public void visitIncdirDirective(@NotNull M68kIncdirDirective o) {
    visitDirective(o);
  }

  public void visitIncludeDirective(@NotNull M68kIncludeDirective o) {
    visitDirective(o);
  }

  public void visitInitNearDirective(@NotNull M68kInitNearDirective o) {
    visitDirective(o);
  }

  public void visitInlineDirective(@NotNull M68kInlineDirective o) {
    visitDirective(o);
  }

  public void visitJmpInstruction(@NotNull M68kJmpInstruction o) {
    visitInstruction(o);
  }

  public void visitJsrInstruction(@NotNull M68kJsrInstruction o) {
    visitInstruction(o);
  }

  public void visitJumperrDirective(@NotNull M68kJumperrDirective o) {
    visitDirective(o);
  }

  public void visitJumpptrDirective(@NotNull M68kJumpptrDirective o) {
    visitDirective(o);
  }

  public void visitLabel(@NotNull M68kLabel o) {
    visitLabelBase(o);
  }

  public void visitLabelRefExpression(@NotNull M68kLabelRefExpression o) {
    visitExpression(o);
  }

  public void visitLeaInstruction(@NotNull M68kLeaInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitLinkInstruction(@NotNull M68kLinkInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitListDirective(@NotNull M68kListDirective o) {
    visitDirective(o);
  }

  public void visitLlenDirective(@NotNull M68kLlenDirective o) {
    visitDirective(o);
  }

  public void visitLoadDirective(@NotNull M68kLoadDirective o) {
    visitDirective(o);
  }

  public void visitLocalLabel(@NotNull M68kLocalLabel o) {
    visitLabelBase(o);
  }

  public void visitLogicalAndExpression(@NotNull M68kLogicalAndExpression o) {
    visitBinaryExpression(o);
  }

  public void visitLogicalOrExpression(@NotNull M68kLogicalOrExpression o) {
    visitBinaryExpression(o);
  }

  public void visitLslInstruction(@NotNull M68kLslInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitLsrInstruction(@NotNull M68kLsrInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitLtEqExpression(@NotNull M68kLtEqExpression o) {
    visitBinaryExpression(o);
  }

  public void visitLtExpression(@NotNull M68kLtExpression o) {
    visitBinaryExpression(o);
  }

  public void visitMachineDirective(@NotNull M68kMachineDirective o) {
    visitDirective(o);
  }

  public void visitMacroCallDirective(@NotNull M68kMacroCallDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitMacroCallParameter(@NotNull M68kMacroCallParameter o) {
    visitPsiElement(o);
  }

  public void visitMacroDirective(@NotNull M68kMacroDirective o) {
    visitDirective(o);
  }

  public void visitMacroParameterDirective(@NotNull M68kMacroParameterDirective o) {
    visitDirective(o);
  }

  public void visitMask2Directive(@NotNull M68kMask2Directive o) {
    visitDirective(o);
  }

  public void visitMc68000Directive(@NotNull M68kMc68000Directive o) {
    visitDirective(o);
  }

  public void visitMc68010Directive(@NotNull M68kMc68010Directive o) {
    visitDirective(o);
  }

  public void visitMc68020Directive(@NotNull M68kMc68020Directive o) {
    visitDirective(o);
  }

  public void visitMc68030Directive(@NotNull M68kMc68030Directive o) {
    visitDirective(o);
  }

  public void visitMc68040Directive(@NotNull M68kMc68040Directive o) {
    visitDirective(o);
  }

  public void visitMc68060Directive(@NotNull M68kMc68060Directive o) {
    visitDirective(o);
  }

  public void visitMexitDirective(@NotNull M68kMexitDirective o) {
    visitDirective(o);
  }

  public void visitMinusExpression(@NotNull M68kMinusExpression o) {
    visitBinaryExpression(o);
  }

  public void visitModExpression(@NotNull M68kModExpression o) {
    visitBinaryExpression(o);
  }

  public void visitMoveInstruction(@NotNull M68kMoveInstruction o) {
    visitMoveInstructionBase(o);
    // visitPrivilegedInstruction(o);
  }

  public void visitMoveInstructionBase(@NotNull M68kMoveInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitMoveaInstruction(@NotNull M68kMoveaInstruction o) {
    visitMoveInstructionBase(o);
  }

  public void visitMovecInstruction(@NotNull M68kMovecInstruction o) {
    visitMoveInstructionBase(o);
    // visitPrivilegedInstruction(o);
  }

  public void visitMovemInstruction(@NotNull M68kMovemInstruction o) {
    visitMoveInstructionBase(o);
  }

  public void visitMovepInstruction(@NotNull M68kMovepInstruction o) {
    visitMoveInstructionBase(o);
  }

  public void visitMoveqInstruction(@NotNull M68kMoveqInstruction o) {
    visitMoveInstructionBase(o);
  }

  public void visitMovesInstruction(@NotNull M68kMovesInstruction o) {
    visitMoveInstructionBase(o);
    // visitPrivilegedInstruction(o);
  }

  public void visitMsourceDirective(@NotNull M68kMsourceDirective o) {
    visitDirective(o);
  }

  public void visitMulExpression(@NotNull M68kMulExpression o) {
    visitBinaryExpression(o);
  }

  public void visitMulsInstruction(@NotNull M68kMulsInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitMuluInstruction(@NotNull M68kMuluInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitNbcdInstruction(@NotNull M68kNbcdInstruction o) {
    visitBcdInstructionBase(o);
  }

  public void visitNearCodeDirective(@NotNull M68kNearCodeDirective o) {
    visitDirective(o);
  }

  public void visitNearDirective(@NotNull M68kNearDirective o) {
    visitDirective(o);
  }

  public void visitNegInstruction(@NotNull M68kNegInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitNegxInstruction(@NotNull M68kNegxInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitNolistDirective(@NotNull M68kNolistDirective o) {
    visitDirective(o);
  }

  public void visitNopInstruction(@NotNull M68kNopInstruction o) {
    visitInstruction(o);
  }

  public void visitNopageDirective(@NotNull M68kNopageDirective o) {
    visitDirective(o);
  }

  public void visitNotEqualsExpression(@NotNull M68kNotEqualsExpression o) {
    visitBinaryExpression(o);
  }

  public void visitNotInstruction(@NotNull M68kNotInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitNumberExpression(@NotNull M68kNumberExpression o) {
    visitExpression(o);
    // visitPsiLiteralValue(o);
  }

  public void visitOddDirective(@NotNull M68kOddDirective o) {
    visitDirective(o);
  }

  public void visitOffsetDirective(@NotNull M68kOffsetDirective o) {
    visitDirective(o);
  }

  public void visitOptDirective(@NotNull M68kOptDirective o) {
    visitDirective(o);
  }

  public void visitOrExpression(@NotNull M68kOrExpression o) {
    visitBinaryExpression(o);
  }

  public void visitOrInstruction(@NotNull M68kOrInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitOrgDirective(@NotNull M68kOrgDirective o) {
    visitDirective(o);
  }

  public void visitOriInstruction(@NotNull M68kOriInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitOutputDirective(@NotNull M68kOutputDirective o) {
    visitDirective(o);
  }

  public void visitPageDirective(@NotNull M68kPageDirective o) {
    visitDirective(o);
  }

  public void visitParenExpression(@NotNull M68kParenExpression o) {
    visitExpression(o);
  }

  public void visitPeaInstruction(@NotNull M68kPeaInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitPlenDirective(@NotNull M68kPlenDirective o) {
    visitDirective(o);
  }

  public void visitPlusExpression(@NotNull M68kPlusExpression o) {
    visitBinaryExpression(o);
  }

  public void visitPopsectionDirective(@NotNull M68kPopsectionDirective o) {
    visitDirective(o);
  }

  public void visitPrinttDirective(@NotNull M68kPrinttDirective o) {
    visitDirective(o);
  }

  public void visitPrintvDirective(@NotNull M68kPrintvDirective o) {
    visitDirective(o);
  }

  public void visitPushsectionDirective(@NotNull M68kPushsectionDirective o) {
    visitDirective(o);
  }

  public void visitRegDirective(@NotNull M68kRegDirective o) {
    visitDirective(o);
  }

  public void visitRegisterRange(@NotNull M68kRegisterRange o) {
    visitPsiElement(o);
  }

  public void visitRemDirective(@NotNull M68kRemDirective o) {
    visitDirective(o);
  }

  public void visitReptDirective(@NotNull M68kReptDirective o) {
    visitDirective(o);
  }

  public void visitResetInstruction(@NotNull M68kResetInstruction o) {
    visitPrivilegedInstruction(o);
    // visitInstruction(o);
  }

  public void visitRolInstruction(@NotNull M68kRolInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitRorInstruction(@NotNull M68kRorInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitRoxlInstruction(@NotNull M68kRoxlInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitRoxrInstruction(@NotNull M68kRoxrInstruction o) {
    visitShiftRotateInstructionBase(o);
  }

  public void visitRsDirective(@NotNull M68kRsDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitRsresetDirective(@NotNull M68kRsresetDirective o) {
    visitDirective(o);
  }

  public void visitRssetDirective(@NotNull M68kRssetDirective o) {
    visitDirective(o);
  }

  public void visitRteInstruction(@NotNull M68kRteInstruction o) {
    visitPrivilegedInstruction(o);
    // visitInstruction(o);
  }

  public void visitRtrInstruction(@NotNull M68kRtrInstruction o) {
    visitInstruction(o);
  }

  public void visitRtsInstruction(@NotNull M68kRtsInstruction o) {
    visitInstruction(o);
  }

  public void visitSbcdInstruction(@NotNull M68kSbcdInstruction o) {
    visitBcdInstructionBase(o);
  }

  public void visitSccInstruction(@NotNull M68kSccInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSccInstructionBase(@NotNull M68kSccInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitScsInstruction(@NotNull M68kScsInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSectionDirective(@NotNull M68kSectionDirective o) {
    visitDirective(o);
  }

  public void visitSeqInstruction(@NotNull M68kSeqInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSetDirective(@NotNull M68kSetDirective o) {
    visitEquDirectiveBase(o);
  }

  public void visitSetfoDirective(@NotNull M68kSetfoDirective o) {
    visitDirective(o);
  }

  public void visitSetsoDirective(@NotNull M68kSetsoDirective o) {
    visitDirective(o);
  }

  public void visitSfInstruction(@NotNull M68kSfInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSgeInstruction(@NotNull M68kSgeInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSgtInstruction(@NotNull M68kSgtInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitShiInstruction(@NotNull M68kShiInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitShiftLeftExpression(@NotNull M68kShiftLeftExpression o) {
    visitBinaryExpression(o);
  }

  public void visitShiftRightExpression(@NotNull M68kShiftRightExpression o) {
    visitBinaryExpression(o);
  }

  public void visitShiftRotateInstructionBase(@NotNull M68kShiftRotateInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitShsInstruction(@NotNull M68kShsInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSleInstruction(@NotNull M68kSleInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSloInstruction(@NotNull M68kSloInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSlsInstruction(@NotNull M68kSlsInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSltInstruction(@NotNull M68kSltInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSmiInstruction(@NotNull M68kSmiInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSneInstruction(@NotNull M68kSneInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSoDirective(@NotNull M68kSoDirective o) {
    visitDataSized(o);
    // visitDirective(o);
  }

  public void visitSpcDirective(@NotNull M68kSpcDirective o) {
    visitDirective(o);
  }

  public void visitSplInstruction(@NotNull M68kSplInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitStInstruction(@NotNull M68kStInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitStopInstruction(@NotNull M68kStopInstruction o) {
    visitPrivilegedInstruction(o);
    // visitInstruction(o);
  }

  public void visitStringExpression(@NotNull M68kStringExpression o) {
    visitExpression(o);
    // visitPsiLiteralValue(o);
  }

  public void visitSubInstruction(@NotNull M68kSubInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitSubaInstruction(@NotNull M68kSubaInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitSubiInstruction(@NotNull M68kSubiInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitSubqInstruction(@NotNull M68kSubqInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitSubxInstruction(@NotNull M68kSubxInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitSvcInstruction(@NotNull M68kSvcInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSvsInstruction(@NotNull M68kSvsInstruction o) {
    visitSccInstructionBase(o);
  }

  public void visitSwapInstruction(@NotNull M68kSwapInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitTasInstruction(@NotNull M68kTasInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitTextDirective(@NotNull M68kTextDirective o) {
    visitDirective(o);
  }

  public void visitTrapInstruction(@NotNull M68kTrapInstruction o) {
    visitInstruction(o);
  }

  public void visitTrapvInstruction(@NotNull M68kTrapvInstruction o) {
    visitInstruction(o);
  }

  public void visitTstInstruction(@NotNull M68kTstInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitTtlDirective(@NotNull M68kTtlDirective o) {
    visitDirective(o);
  }

  public void visitUnaryComplementExpression(@NotNull M68kUnaryComplementExpression o) {
    visitUnaryExpression(o);
  }

  public void visitUnaryExpression(@NotNull M68kUnaryExpression o) {
    visitExpression(o);
  }

  public void visitUnaryMinusExpression(@NotNull M68kUnaryMinusExpression o) {
    visitUnaryExpression(o);
  }

  public void visitUnaryNotExpression(@NotNull M68kUnaryNotExpression o) {
    visitUnaryExpression(o);
  }

  public void visitUnaryPlusExpression(@NotNull M68kUnaryPlusExpression o) {
    visitUnaryExpression(o);
  }

  public void visitUnlkInstruction(@NotNull M68kUnlkInstruction o) {
    visitInstruction(o);
  }

  public void visitXdefDirective(@NotNull M68kXdefDirective o) {
    visitDirective(o);
  }

  public void visitXorExpression(@NotNull M68kXorExpression o) {
    visitBinaryExpression(o);
  }

  public void visitXrefDirective(@NotNull M68kXrefDirective o) {
    visitDirective(o);
  }

  public void visitAdm(@NotNull M68kAdm o) {
    visitPsiElement(o);
  }

  public void visitAdmWithDisplacement(@NotNull M68kAdmWithDisplacement o) {
    visitPsiElement(o);
  }

  public void visitAdmWithIndirectAddressRegister(@NotNull M68kAdmWithIndirectAddressRegister o) {
    visitPsiElement(o);
  }

  public void visitAdmWithRegister(@NotNull M68kAdmWithRegister o) {
    visitPsiElement(o);
  }

  public void visitAdmWithRrd(@NotNull M68kAdmWithRrd o) {
    visitPsiElement(o);
  }

  public void visitInstruction(@NotNull M68kInstruction o) {
    visitPsiElement(o);
  }

  public void visitLabelBase(@NotNull M68kLabelBase o) {
    visitPsiElement(o);
  }

  public void visitPrivilegedInstruction(@NotNull M68kPrivilegedInstruction o) {
    visitPsiElement(o);
  }

  public void visitConditionalAssemblyDirective(@NotNull M68kConditionalAssemblyDirective o) {
    visitPsiElement(o);
  }

  public void visitDirective(@NotNull M68kDirective o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull M68kPsiElement o) {
    visitElement(o);
  }

}
