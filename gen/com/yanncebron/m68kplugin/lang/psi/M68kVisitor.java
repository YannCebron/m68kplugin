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

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class M68kVisitor extends PsiElementVisitor {

  public void visitAbcdInstruction(@NotNull M68kAbcdInstruction o) {
    visitBcdInstructionBase(o);
  }

  public void visitAddInstruction(@NotNull M68kAddInstruction o) {
    visitInstruction(o);
  }

  public void visitAddaInstruction(@NotNull M68kAddaInstruction o) {
    visitInstruction(o);
  }

  public void visitAddiInstruction(@NotNull M68kAddiInstruction o) {
    visitInstruction(o);
  }

  public void visitAddqInstruction(@NotNull M68kAddqInstruction o) {
    visitInstruction(o);
  }

  public void visitAddxInstruction(@NotNull M68kAddxInstruction o) {
    visitInstruction(o);
  }

  public void visitAdmAbs(@NotNull M68kAdmAbs o) {
    visitPsiElement(o);
  }

  public void visitAdmAdi(@NotNull M68kAdmAdi o) {
    visitPsiElement(o);
  }

  public void visitAdmAix(@NotNull M68kAdmAix o) {
    visitPsiElement(o);
  }

  public void visitAdmApd(@NotNull M68kAdmApd o) {
    visitPsiElement(o);
  }

  public void visitAdmApi(@NotNull M68kAdmApi o) {
    visitPsiElement(o);
  }

  public void visitAdmArd(@NotNull M68kAdmArd o) {
    visitPsiElement(o);
  }

  public void visitAdmAri(@NotNull M68kAdmAri o) {
    visitPsiElement(o);
  }

  public void visitAdmCcr(@NotNull M68kAdmCcr o) {
    visitPsiElement(o);
  }

  public void visitAdmDrd(@NotNull M68kAdmDrd o) {
    visitPsiElement(o);
  }

  public void visitAdmImm(@NotNull M68kAdmImm o) {
    visitPsiElement(o);
  }

  public void visitAdmPcd(@NotNull M68kAdmPcd o) {
    visitPsiElement(o);
  }

  public void visitAdmPci(@NotNull M68kAdmPci o) {
    visitPsiElement(o);
  }

  public void visitAdmRrd(@NotNull M68kAdmRrd o) {
    visitPsiElement(o);
  }

  public void visitAdmRrdIndex(@NotNull M68kAdmRrdIndex o) {
    visitDataSized(o);
  }

  public void visitAdmSr(@NotNull M68kAdmSr o) {
    visitPsiElement(o);
  }

  public void visitAdmUsp(@NotNull M68kAdmUsp o) {
    visitPsiElement(o);
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
    visitShiftInstructionBase(o);
  }

  public void visitAsrInstruction(@NotNull M68kAsrInstruction o) {
    visitShiftInstructionBase(o);
  }

  public void visitBccInstruction(@NotNull M68kBccInstruction o) {
    visitInstruction(o);
  }

  public void visitBcdInstructionBase(@NotNull M68kBcdInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBchgInstruction(@NotNull M68kBchgInstruction o) {
    visitBitInstructionBase(o);
  }

  public void visitBclrInstruction(@NotNull M68kBclrInstruction o) {
    visitBitInstructionBase(o);
  }

  public void visitBcsInstruction(@NotNull M68kBcsInstruction o) {
    visitInstruction(o);
  }

  public void visitBeqInstruction(@NotNull M68kBeqInstruction o) {
    visitInstruction(o);
  }

  public void visitBgeInstruction(@NotNull M68kBgeInstruction o) {
    visitInstruction(o);
  }

  public void visitBgtInstruction(@NotNull M68kBgtInstruction o) {
    visitInstruction(o);
  }

  public void visitBhiInstruction(@NotNull M68kBhiInstruction o) {
    visitInstruction(o);
  }

  public void visitBhsInstruction(@NotNull M68kBhsInstruction o) {
    visitInstruction(o);
  }

  public void visitBinaryExpression(@NotNull M68kBinaryExpression o) {
    visitExpression(o);
  }

  public void visitBitInstructionBase(@NotNull M68kBitInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBleInstruction(@NotNull M68kBleInstruction o) {
    visitInstruction(o);
  }

  public void visitBlkDirective(@NotNull M68kBlkDirective o) {
    visitDataSized(o);
  }

  public void visitBloInstruction(@NotNull M68kBloInstruction o) {
    visitInstruction(o);
  }

  public void visitBlsInstruction(@NotNull M68kBlsInstruction o) {
    visitInstruction(o);
  }

  public void visitBltInstruction(@NotNull M68kBltInstruction o) {
    visitInstruction(o);
  }

  public void visitBmiInstruction(@NotNull M68kBmiInstruction o) {
    visitInstruction(o);
  }

  public void visitBneInstruction(@NotNull M68kBneInstruction o) {
    visitInstruction(o);
  }

  public void visitBoolInstructionBase(@NotNull M68kBoolInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBplInstruction(@NotNull M68kBplInstruction o) {
    visitInstruction(o);
  }

  public void visitBraInstruction(@NotNull M68kBraInstruction o) {
    visitInstruction(o);
  }

  public void visitBsetInstruction(@NotNull M68kBsetInstruction o) {
    visitBitInstructionBase(o);
  }

  public void visitBsrInstruction(@NotNull M68kBsrInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitBtstInstruction(@NotNull M68kBtstInstruction o) {
    visitBitInstructionBase(o);
  }

  public void visitBvcInstruction(@NotNull M68kBvcInstruction o) {
    visitInstruction(o);
  }

  public void visitBvsInstruction(@NotNull M68kBvsInstruction o) {
    visitInstruction(o);
  }

  public void visitChkInstruction(@NotNull M68kChkInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitClrInstruction(@NotNull M68kClrInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
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

  public void visitDataSized(@NotNull M68kDataSized o) {
    visitPsiElement(o);
  }

  public void visitDbccInstruction(@NotNull M68kDbccInstruction o) {
    visitInstruction(o);
  }

  public void visitDbcsInstruction(@NotNull M68kDbcsInstruction o) {
    visitInstruction(o);
  }

  public void visitDbeqInstruction(@NotNull M68kDbeqInstruction o) {
    visitInstruction(o);
  }

  public void visitDbfInstruction(@NotNull M68kDbfInstruction o) {
    visitInstruction(o);
  }

  public void visitDbgeInstruction(@NotNull M68kDbgeInstruction o) {
    visitInstruction(o);
  }

  public void visitDbgtInstruction(@NotNull M68kDbgtInstruction o) {
    visitInstruction(o);
  }

  public void visitDbhiInstruction(@NotNull M68kDbhiInstruction o) {
    visitInstruction(o);
  }

  public void visitDbleInstruction(@NotNull M68kDbleInstruction o) {
    visitInstruction(o);
  }

  public void visitDblsInstruction(@NotNull M68kDblsInstruction o) {
    visitInstruction(o);
  }

  public void visitDbltInstruction(@NotNull M68kDbltInstruction o) {
    visitInstruction(o);
  }

  public void visitDbmiInstruction(@NotNull M68kDbmiInstruction o) {
    visitInstruction(o);
  }

  public void visitDbneInstruction(@NotNull M68kDbneInstruction o) {
    visitInstruction(o);
  }

  public void visitDbplInstruction(@NotNull M68kDbplInstruction o) {
    visitInstruction(o);
  }

  public void visitDbraInstruction(@NotNull M68kDbraInstruction o) {
    visitInstruction(o);
  }

  public void visitDbtInstruction(@NotNull M68kDbtInstruction o) {
    visitInstruction(o);
  }

  public void visitDbvcInstruction(@NotNull M68kDbvcInstruction o) {
    visitInstruction(o);
  }

  public void visitDbvsInstruction(@NotNull M68kDbvsInstruction o) {
    visitInstruction(o);
  }

  public void visitDcDirective(@NotNull M68kDcDirective o) {
    visitDataSized(o);
  }

  public void visitDcbDirective(@NotNull M68kDcbDirective o) {
    visitDataSized(o);
  }

  public void visitDivExpression(@NotNull M68kDivExpression o) {
    visitBinaryExpression(o);
  }

  public void visitDivsInstruction(@NotNull M68kDivsInstruction o) {
    visitInstruction(o);
  }

  public void visitDivuInstruction(@NotNull M68kDivuInstruction o) {
    visitInstruction(o);
  }

  public void visitDsDirective(@NotNull M68kDsDirective o) {
    visitDataSized(o);
  }

  public void visitElseDirective(@NotNull M68kElseDirective o) {
    visitPsiElement(o);
  }

  public void visitElseifDirective(@NotNull M68kElseifDirective o) {
    visitPsiElement(o);
  }

  public void visitEndDirective(@NotNull M68kEndDirective o) {
    visitPsiElement(o);
  }

  public void visitEndcDirective(@NotNull M68kEndcDirective o) {
    visitPsiElement(o);
  }

  public void visitEndmDirective(@NotNull M68kEndmDirective o) {
    visitPsiElement(o);
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
    visitPsiElement(o);
  }

  public void visitEqualsDirective(@NotNull M68kEqualsDirective o) {
    visitEquDirectiveBase(o);
  }

  public void visitEqurDirective(@NotNull M68kEqurDirective o) {
    visitPsiElement(o);
  }

  public void visitEvenDirective(@NotNull M68kEvenDirective o) {
    visitPsiElement(o);
  }

  public void visitExgInstruction(@NotNull M68kExgInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitExpExpression(@NotNull M68kExpExpression o) {
    visitBinaryExpression(o);
  }

  public void visitExpression(@NotNull M68kExpression o) {
    visitPsiElement(o);
  }

  public void visitExtInstruction(@NotNull M68kExtInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitIfdDirective(@NotNull M68kIfdDirective o) {
    visitPsiElement(o);
  }

  public void visitIfeqDirective(@NotNull M68kIfeqDirective o) {
    visitPsiElement(o);
  }

  public void visitIfgeDirective(@NotNull M68kIfgeDirective o) {
    visitPsiElement(o);
  }

  public void visitIfgtDirective(@NotNull M68kIfgtDirective o) {
    visitPsiElement(o);
  }

  public void visitIfleDirective(@NotNull M68kIfleDirective o) {
    visitPsiElement(o);
  }

  public void visitIfltDirective(@NotNull M68kIfltDirective o) {
    visitPsiElement(o);
  }

  public void visitIfndDirective(@NotNull M68kIfndDirective o) {
    visitPsiElement(o);
  }

  public void visitIfneDirective(@NotNull M68kIfneDirective o) {
    visitPsiElement(o);
  }

  public void visitIllegalInstruction(@NotNull M68kIllegalInstruction o) {
    visitInstruction(o);
  }

  public void visitIncbinDirective(@NotNull M68kIncbinDirective o) {
    visitPsiElement(o);
  }

  public void visitIncdirDirective(@NotNull M68kIncdirDirective o) {
    visitPsiElement(o);
  }

  public void visitIncludeDirective(@NotNull M68kIncludeDirective o) {
    visitPsiElement(o);
  }

  public void visitJmpInstruction(@NotNull M68kJmpInstruction o) {
    visitInstruction(o);
  }

  public void visitJsrInstruction(@NotNull M68kJsrInstruction o) {
    visitInstruction(o);
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
    visitInstruction(o);
  }

  public void visitLocalLabel(@NotNull M68kLocalLabel o) {
    visitLabelBase(o);
  }

  public void visitLslInstruction(@NotNull M68kLslInstruction o) {
    visitShiftInstructionBase(o);
  }

  public void visitLsrInstruction(@NotNull M68kLsrInstruction o) {
    visitShiftInstructionBase(o);
  }

  public void visitMacroDirective(@NotNull M68kMacroDirective o) {
    visitPsiElement(o);
  }

  public void visitMinusExpression(@NotNull M68kMinusExpression o) {
    visitBinaryExpression(o);
  }

  public void visitModExpression(@NotNull M68kModExpression o) {
    visitBinaryExpression(o);
  }

  public void visitMoveInstruction(@NotNull M68kMoveInstruction o) {
    visitMoveInstructionBase(o);
  }

  public void visitMoveInstructionBase(@NotNull M68kMoveInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitMoveaInstruction(@NotNull M68kMoveaInstruction o) {
    visitMoveInstructionBase(o);
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

  public void visitMulExpression(@NotNull M68kMulExpression o) {
    visitBinaryExpression(o);
  }

  public void visitMulsInstruction(@NotNull M68kMulsInstruction o) {
    visitInstruction(o);
  }

  public void visitMuluInstruction(@NotNull M68kMuluInstruction o) {
    visitInstruction(o);
  }

  public void visitNbcdInstruction(@NotNull M68kNbcdInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitNegInstruction(@NotNull M68kNegInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitNegxInstruction(@NotNull M68kNegxInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitNopInstruction(@NotNull M68kNopInstruction o) {
    visitInstruction(o);
  }

  public void visitNotInstruction(@NotNull M68kNotInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitNumberExpression(@NotNull M68kNumberExpression o) {
    visitExpression(o);
  }

  public void visitOddDirective(@NotNull M68kOddDirective o) {
    visitPsiElement(o);
  }

  public void visitOptDirective(@NotNull M68kOptDirective o) {
    visitPsiElement(o);
  }

  public void visitOrExpression(@NotNull M68kOrExpression o) {
    visitBinaryExpression(o);
  }

  public void visitOrInstruction(@NotNull M68kOrInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitOrgDirective(@NotNull M68kOrgDirective o) {
    visitPsiElement(o);
  }

  public void visitOriInstruction(@NotNull M68kOriInstruction o) {
    visitBoolInstructionBase(o);
  }

  public void visitParenExpression(@NotNull M68kParenExpression o) {
    visitExpression(o);
  }

  public void visitPeaInstruction(@NotNull M68kPeaInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitPlusExpression(@NotNull M68kPlusExpression o) {
    visitBinaryExpression(o);
  }

  public void visitRegisterList(@NotNull M68kRegisterList o) {
    visitPsiElement(o);
  }

  public void visitRegisterRange(@NotNull M68kRegisterRange o) {
    visitPsiElement(o);
  }

  public void visitResetInstruction(@NotNull M68kResetInstruction o) {
    visitInstruction(o);
  }

  public void visitRolInstruction(@NotNull M68kRolInstruction o) {
    visitShiftInstructionBase(o);
  }

  public void visitRorInstruction(@NotNull M68kRorInstruction o) {
    visitShiftInstructionBase(o);
  }

  public void visitRoxlInstruction(@NotNull M68kRoxlInstruction o) {
    visitShiftInstructionBase(o);
  }

  public void visitRoxrInstruction(@NotNull M68kRoxrInstruction o) {
    visitShiftInstructionBase(o);
  }

  public void visitRsDirective(@NotNull M68kRsDirective o) {
    visitDataSized(o);
  }

  public void visitRsresetDirective(@NotNull M68kRsresetDirective o) {
    visitPsiElement(o);
  }

  public void visitRssetDirective(@NotNull M68kRssetDirective o) {
    visitPsiElement(o);
  }

  public void visitRteInstruction(@NotNull M68kRteInstruction o) {
    visitInstruction(o);
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
    visitInstruction(o);
  }

  public void visitScsInstruction(@NotNull M68kScsInstruction o) {
    visitInstruction(o);
  }

  public void visitSeqInstruction(@NotNull M68kSeqInstruction o) {
    visitInstruction(o);
  }

  public void visitSetDirective(@NotNull M68kSetDirective o) {
    visitEquDirectiveBase(o);
  }

  public void visitSfInstruction(@NotNull M68kSfInstruction o) {
    visitInstruction(o);
  }

  public void visitSgeInstruction(@NotNull M68kSgeInstruction o) {
    visitInstruction(o);
  }

  public void visitSgtInstruction(@NotNull M68kSgtInstruction o) {
    visitInstruction(o);
  }

  public void visitShiInstruction(@NotNull M68kShiInstruction o) {
    visitInstruction(o);
  }

  public void visitShiftInstructionBase(@NotNull M68kShiftInstructionBase o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitShiftLeftExpression(@NotNull M68kShiftLeftExpression o) {
    visitBinaryExpression(o);
  }

  public void visitShiftRightExpression(@NotNull M68kShiftRightExpression o) {
    visitBinaryExpression(o);
  }

  public void visitSleInstruction(@NotNull M68kSleInstruction o) {
    visitInstruction(o);
  }

  public void visitSlsInstruction(@NotNull M68kSlsInstruction o) {
    visitInstruction(o);
  }

  public void visitSltInstruction(@NotNull M68kSltInstruction o) {
    visitInstruction(o);
  }

  public void visitSmiInstruction(@NotNull M68kSmiInstruction o) {
    visitInstruction(o);
  }

  public void visitSneInstruction(@NotNull M68kSneInstruction o) {
    visitInstruction(o);
  }

  public void visitSplInstruction(@NotNull M68kSplInstruction o) {
    visitInstruction(o);
  }

  public void visitStInstruction(@NotNull M68kStInstruction o) {
    visitInstruction(o);
  }

  public void visitStopInstruction(@NotNull M68kStopInstruction o) {
    visitInstruction(o);
  }

  public void visitStringExpression(@NotNull M68kStringExpression o) {
    visitExpression(o);
  }

  public void visitSubInstruction(@NotNull M68kSubInstruction o) {
    visitInstruction(o);
  }

  public void visitSubaInstruction(@NotNull M68kSubaInstruction o) {
    visitInstruction(o);
  }

  public void visitSubiInstruction(@NotNull M68kSubiInstruction o) {
    visitInstruction(o);
  }

  public void visitSubqInstruction(@NotNull M68kSubqInstruction o) {
    visitInstruction(o);
  }

  public void visitSubxInstruction(@NotNull M68kSubxInstruction o) {
    visitInstruction(o);
  }

  public void visitSvcInstruction(@NotNull M68kSvcInstruction o) {
    visitInstruction(o);
  }

  public void visitSvsInstruction(@NotNull M68kSvsInstruction o) {
    visitInstruction(o);
  }

  public void visitSwapInstruction(@NotNull M68kSwapInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
  }

  public void visitTasInstruction(@NotNull M68kTasInstruction o) {
    visitDataSized(o);
    // visitInstruction(o);
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

  public void visitUnaryComplementExpression(@NotNull M68kUnaryComplementExpression o) {
    visitExpression(o);
  }

  public void visitUnaryMinusExpression(@NotNull M68kUnaryMinusExpression o) {
    visitExpression(o);
  }

  public void visitUnaryPlusExpression(@NotNull M68kUnaryPlusExpression o) {
    visitExpression(o);
  }

  public void visitUnlkInstruction(@NotNull M68kUnlkInstruction o) {
    visitInstruction(o);
  }

  public void visitInstruction(@NotNull M68kInstruction o) {
    visitPsiElement(o);
  }

  public void visitLabelBase(@NotNull M68kLabelBase o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull M68kPsiElement o) {
    visitElement(o);
  }

}
