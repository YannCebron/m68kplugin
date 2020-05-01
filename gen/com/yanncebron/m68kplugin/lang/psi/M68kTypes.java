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

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.yanncebron.m68kplugin.lang.psi.impl.*;

public interface M68kTypes {

  IElementType ABCD_INSTRUCTION = new M68kCompositeElementType("ABCD_INSTRUCTION");
  IElementType ADDA_INSTRUCTION = new M68kCompositeElementType("ADDA_INSTRUCTION");
  IElementType ADDI_INSTRUCTION = new M68kCompositeElementType("ADDI_INSTRUCTION");
  IElementType ADDQ_INSTRUCTION = new M68kCompositeElementType("ADDQ_INSTRUCTION");
  IElementType ADDX_INSTRUCTION = new M68kCompositeElementType("ADDX_INSTRUCTION");
  IElementType ADD_INSTRUCTION = new M68kCompositeElementType("ADD_INSTRUCTION");
  IElementType ADM_ABS = new M68kCompositeElementType("ADM_ABS");
  IElementType ADM_ADI = new M68kCompositeElementType("ADM_ADI");
  IElementType ADM_AIX = new M68kCompositeElementType("ADM_AIX");
  IElementType ADM_APD = new M68kCompositeElementType("ADM_APD");
  IElementType ADM_API = new M68kCompositeElementType("ADM_API");
  IElementType ADM_ARD = new M68kCompositeElementType("ADM_ARD");
  IElementType ADM_ARI = new M68kCompositeElementType("ADM_ARI");
  IElementType ADM_CCR = new M68kCompositeElementType("ADM_CCR");
  IElementType ADM_DRD = new M68kCompositeElementType("ADM_DRD");
  IElementType ADM_IMM = new M68kCompositeElementType("ADM_IMM");
  IElementType ADM_PCD = new M68kCompositeElementType("ADM_PCD");
  IElementType ADM_PCI = new M68kCompositeElementType("ADM_PCI");
  IElementType ADM_RRD = new M68kCompositeElementType("ADM_RRD");
  IElementType ADM_SR = new M68kCompositeElementType("ADM_SR");
  IElementType ADM_USP = new M68kCompositeElementType("ADM_USP");
  IElementType ANDI_INSTRUCTION = new M68kCompositeElementType("ANDI_INSTRUCTION");
  IElementType AND_EXPRESSION = new M68kCompositeElementType("AND_EXPRESSION");
  IElementType AND_INSTRUCTION = new M68kCompositeElementType("AND_INSTRUCTION");
  IElementType ASL_INSTRUCTION = new M68kCompositeElementType("ASL_INSTRUCTION");
  IElementType ASR_INSTRUCTION = new M68kCompositeElementType("ASR_INSTRUCTION");
  IElementType BCC_INSTRUCTION = new M68kCompositeElementType("BCC_INSTRUCTION");
  IElementType BCHG_INSTRUCTION = new M68kCompositeElementType("BCHG_INSTRUCTION");
  IElementType BCLR_INSTRUCTION = new M68kCompositeElementType("BCLR_INSTRUCTION");
  IElementType BCS_INSTRUCTION = new M68kCompositeElementType("BCS_INSTRUCTION");
  IElementType BEQ_INSTRUCTION = new M68kCompositeElementType("BEQ_INSTRUCTION");
  IElementType BGE_INSTRUCTION = new M68kCompositeElementType("BGE_INSTRUCTION");
  IElementType BGT_INSTRUCTION = new M68kCompositeElementType("BGT_INSTRUCTION");
  IElementType BHI_INSTRUCTION = new M68kCompositeElementType("BHI_INSTRUCTION");
  IElementType BHS_INSTRUCTION = new M68kCompositeElementType("BHS_INSTRUCTION");
  IElementType BLE_INSTRUCTION = new M68kCompositeElementType("BLE_INSTRUCTION");
  IElementType BLK_DIRECTIVE = new M68kCompositeElementType("BLK_DIRECTIVE");
  IElementType BLO_INSTRUCTION = new M68kCompositeElementType("BLO_INSTRUCTION");
  IElementType BLS_INSTRUCTION = new M68kCompositeElementType("BLS_INSTRUCTION");
  IElementType BLT_INSTRUCTION = new M68kCompositeElementType("BLT_INSTRUCTION");
  IElementType BMI_INSTRUCTION = new M68kCompositeElementType("BMI_INSTRUCTION");
  IElementType BNE_INSTRUCTION = new M68kCompositeElementType("BNE_INSTRUCTION");
  IElementType BPL_INSTRUCTION = new M68kCompositeElementType("BPL_INSTRUCTION");
  IElementType BRA_INSTRUCTION = new M68kCompositeElementType("BRA_INSTRUCTION");
  IElementType BSET_INSTRUCTION = new M68kCompositeElementType("BSET_INSTRUCTION");
  IElementType BSR_INSTRUCTION = new M68kCompositeElementType("BSR_INSTRUCTION");
  IElementType BTST_INSTRUCTION = new M68kCompositeElementType("BTST_INSTRUCTION");
  IElementType BVC_INSTRUCTION = new M68kCompositeElementType("BVC_INSTRUCTION");
  IElementType BVS_INSTRUCTION = new M68kCompositeElementType("BVS_INSTRUCTION");
  IElementType CHK_INSTRUCTION = new M68kCompositeElementType("CHK_INSTRUCTION");
  IElementType CLR_INSTRUCTION = new M68kCompositeElementType("CLR_INSTRUCTION");
  IElementType CMPA_INSTRUCTION = new M68kCompositeElementType("CMPA_INSTRUCTION");
  IElementType CMPI_INSTRUCTION = new M68kCompositeElementType("CMPI_INSTRUCTION");
  IElementType CMPM_INSTRUCTION = new M68kCompositeElementType("CMPM_INSTRUCTION");
  IElementType CMP_INSTRUCTION = new M68kCompositeElementType("CMP_INSTRUCTION");
  IElementType DBCC_INSTRUCTION = new M68kCompositeElementType("DBCC_INSTRUCTION");
  IElementType DBCS_INSTRUCTION = new M68kCompositeElementType("DBCS_INSTRUCTION");
  IElementType DBEQ_INSTRUCTION = new M68kCompositeElementType("DBEQ_INSTRUCTION");
  IElementType DBF_INSTRUCTION = new M68kCompositeElementType("DBF_INSTRUCTION");
  IElementType DBGE_INSTRUCTION = new M68kCompositeElementType("DBGE_INSTRUCTION");
  IElementType DBGT_INSTRUCTION = new M68kCompositeElementType("DBGT_INSTRUCTION");
  IElementType DBHI_INSTRUCTION = new M68kCompositeElementType("DBHI_INSTRUCTION");
  IElementType DBLE_INSTRUCTION = new M68kCompositeElementType("DBLE_INSTRUCTION");
  IElementType DBLS_INSTRUCTION = new M68kCompositeElementType("DBLS_INSTRUCTION");
  IElementType DBLT_INSTRUCTION = new M68kCompositeElementType("DBLT_INSTRUCTION");
  IElementType DBMI_INSTRUCTION = new M68kCompositeElementType("DBMI_INSTRUCTION");
  IElementType DBNE_INSTRUCTION = new M68kCompositeElementType("DBNE_INSTRUCTION");
  IElementType DBPL_INSTRUCTION = new M68kCompositeElementType("DBPL_INSTRUCTION");
  IElementType DBRA_INSTRUCTION = new M68kCompositeElementType("DBRA_INSTRUCTION");
  IElementType DBT_INSTRUCTION = new M68kCompositeElementType("DBT_INSTRUCTION");
  IElementType DBVC_INSTRUCTION = new M68kCompositeElementType("DBVC_INSTRUCTION");
  IElementType DBVS_INSTRUCTION = new M68kCompositeElementType("DBVS_INSTRUCTION");
  IElementType DCB_DIRECTIVE = new M68kCompositeElementType("DCB_DIRECTIVE");
  IElementType DC_DIRECTIVE = new M68kCompositeElementType("DC_DIRECTIVE");
  IElementType DIVS_INSTRUCTION = new M68kCompositeElementType("DIVS_INSTRUCTION");
  IElementType DIVU_INSTRUCTION = new M68kCompositeElementType("DIVU_INSTRUCTION");
  IElementType DIV_EXPRESSION = new M68kCompositeElementType("DIV_EXPRESSION");
  IElementType DS_DIRECTIVE = new M68kCompositeElementType("DS_DIRECTIVE");
  IElementType ENDC_DIRECTIVE = new M68kCompositeElementType("ENDC_DIRECTIVE");
  IElementType ENDM_DIRECTIVE = new M68kCompositeElementType("ENDM_DIRECTIVE");
  IElementType END_DIRECTIVE = new M68kCompositeElementType("END_DIRECTIVE");
  IElementType EORI_INSTRUCTION = new M68kCompositeElementType("EORI_INSTRUCTION");
  IElementType EOR_INSTRUCTION = new M68kCompositeElementType("EOR_INSTRUCTION");
  IElementType EQUALS_DIRECTIVE = new M68kCompositeElementType("EQUALS_DIRECTIVE");
  IElementType EQUR_DIRECTIVE = new M68kCompositeElementType("EQUR_DIRECTIVE");
  IElementType EQU_DIRECTIVE = new M68kCompositeElementType("EQU_DIRECTIVE");
  IElementType EVEN_DIRECTIVE = new M68kCompositeElementType("EVEN_DIRECTIVE");
  IElementType EXG_INSTRUCTION = new M68kCompositeElementType("EXG_INSTRUCTION");
  IElementType EXPRESSION = new M68kCompositeElementType("EXPRESSION");
  IElementType EXP_EXPRESSION = new M68kCompositeElementType("EXP_EXPRESSION");
  IElementType EXT_INSTRUCTION = new M68kCompositeElementType("EXT_INSTRUCTION");
  IElementType IFND_DIRECTIVE = new M68kCompositeElementType("IFND_DIRECTIVE");
  IElementType ILLEGAL_INSTRUCTION = new M68kCompositeElementType("ILLEGAL_INSTRUCTION");
  IElementType INCBIN_DIRECTIVE = new M68kCompositeElementType("INCBIN_DIRECTIVE");
  IElementType INCDIR_DIRECTIVE = new M68kCompositeElementType("INCDIR_DIRECTIVE");
  IElementType INCLUDE_DIRECTIVE = new M68kCompositeElementType("INCLUDE_DIRECTIVE");
  IElementType JMP_INSTRUCTION = new M68kCompositeElementType("JMP_INSTRUCTION");
  IElementType JSR_INSTRUCTION = new M68kCompositeElementType("JSR_INSTRUCTION");
  IElementType LABEL = new M68kCompositeElementType("LABEL");
  IElementType LABEL_REF_EXPRESSION = new M68kCompositeElementType("LABEL_REF_EXPRESSION");
  IElementType LEA_INSTRUCTION = new M68kCompositeElementType("LEA_INSTRUCTION");
  IElementType LINK_INSTRUCTION = new M68kCompositeElementType("LINK_INSTRUCTION");
  IElementType LOCAL_LABEL = new M68kCompositeElementType("LOCAL_LABEL");
  IElementType LSL_INSTRUCTION = new M68kCompositeElementType("LSL_INSTRUCTION");
  IElementType LSR_INSTRUCTION = new M68kCompositeElementType("LSR_INSTRUCTION");
  IElementType MACRO_DIRECTIVE = new M68kCompositeElementType("MACRO_DIRECTIVE");
  IElementType MINUS_EXPRESSION = new M68kCompositeElementType("MINUS_EXPRESSION");
  IElementType MOD_EXPRESSION = new M68kCompositeElementType("MOD_EXPRESSION");
  IElementType MOVEA_INSTRUCTION = new M68kCompositeElementType("MOVEA_INSTRUCTION");
  IElementType MOVEM_INSTRUCTION = new M68kCompositeElementType("MOVEM_INSTRUCTION");
  IElementType MOVEP_INSTRUCTION = new M68kCompositeElementType("MOVEP_INSTRUCTION");
  IElementType MOVEQ_INSTRUCTION = new M68kCompositeElementType("MOVEQ_INSTRUCTION");
  IElementType MOVE_INSTRUCTION = new M68kCompositeElementType("MOVE_INSTRUCTION");
  IElementType MULS_INSTRUCTION = new M68kCompositeElementType("MULS_INSTRUCTION");
  IElementType MULU_INSTRUCTION = new M68kCompositeElementType("MULU_INSTRUCTION");
  IElementType MUL_EXPRESSION = new M68kCompositeElementType("MUL_EXPRESSION");
  IElementType NBCD_INSTRUCTION = new M68kCompositeElementType("NBCD_INSTRUCTION");
  IElementType NEGX_INSTRUCTION = new M68kCompositeElementType("NEGX_INSTRUCTION");
  IElementType NEG_INSTRUCTION = new M68kCompositeElementType("NEG_INSTRUCTION");
  IElementType NOP_INSTRUCTION = new M68kCompositeElementType("NOP_INSTRUCTION");
  IElementType NOT_INSTRUCTION = new M68kCompositeElementType("NOT_INSTRUCTION");
  IElementType NUMBER_EXPRESSION = new M68kCompositeElementType("NUMBER_EXPRESSION");
  IElementType ODD_DIRECTIVE = new M68kCompositeElementType("ODD_DIRECTIVE");
  IElementType OPT_DIRECTIVE = new M68kCompositeElementType("OPT_DIRECTIVE");
  IElementType ORG_DIRECTIVE = new M68kCompositeElementType("ORG_DIRECTIVE");
  IElementType ORI_INSTRUCTION = new M68kCompositeElementType("ORI_INSTRUCTION");
  IElementType OR_EXPRESSION = new M68kCompositeElementType("OR_EXPRESSION");
  IElementType OR_INSTRUCTION = new M68kCompositeElementType("OR_INSTRUCTION");
  IElementType PAREN_EXPRESSION = new M68kCompositeElementType("PAREN_EXPRESSION");
  IElementType PEA_INSTRUCTION = new M68kCompositeElementType("PEA_INSTRUCTION");
  IElementType PLUS_EXPRESSION = new M68kCompositeElementType("PLUS_EXPRESSION");
  IElementType REGISTER_LIST = new M68kCompositeElementType("REGISTER_LIST");
  IElementType REGISTER_RANGE = new M68kCompositeElementType("REGISTER_RANGE");
  IElementType RESET_INSTRUCTION = new M68kCompositeElementType("RESET_INSTRUCTION");
  IElementType ROL_INSTRUCTION = new M68kCompositeElementType("ROL_INSTRUCTION");
  IElementType ROR_INSTRUCTION = new M68kCompositeElementType("ROR_INSTRUCTION");
  IElementType ROXL_INSTRUCTION = new M68kCompositeElementType("ROXL_INSTRUCTION");
  IElementType ROXR_INSTRUCTION = new M68kCompositeElementType("ROXR_INSTRUCTION");
  IElementType RSRESET_DIRECTIVE = new M68kCompositeElementType("RSRESET_DIRECTIVE");
  IElementType RSSET_DIRECTIVE = new M68kCompositeElementType("RSSET_DIRECTIVE");
  IElementType RS_DIRECTIVE = new M68kCompositeElementType("RS_DIRECTIVE");
  IElementType RTE_INSTRUCTION = new M68kCompositeElementType("RTE_INSTRUCTION");
  IElementType RTR_INSTRUCTION = new M68kCompositeElementType("RTR_INSTRUCTION");
  IElementType RTS_INSTRUCTION = new M68kCompositeElementType("RTS_INSTRUCTION");
  IElementType SBCD_INSTRUCTION = new M68kCompositeElementType("SBCD_INSTRUCTION");
  IElementType SCC_INSTRUCTION = new M68kCompositeElementType("SCC_INSTRUCTION");
  IElementType SCS_INSTRUCTION = new M68kCompositeElementType("SCS_INSTRUCTION");
  IElementType SEQ_INSTRUCTION = new M68kCompositeElementType("SEQ_INSTRUCTION");
  IElementType SF_INSTRUCTION = new M68kCompositeElementType("SF_INSTRUCTION");
  IElementType SGE_INSTRUCTION = new M68kCompositeElementType("SGE_INSTRUCTION");
  IElementType SGT_INSTRUCTION = new M68kCompositeElementType("SGT_INSTRUCTION");
  IElementType SHIFT_LEFT_EXPRESSION = new M68kCompositeElementType("SHIFT_LEFT_EXPRESSION");
  IElementType SHIFT_RIGHT_EXPRESSION = new M68kCompositeElementType("SHIFT_RIGHT_EXPRESSION");
  IElementType SHI_INSTRUCTION = new M68kCompositeElementType("SHI_INSTRUCTION");
  IElementType SLE_INSTRUCTION = new M68kCompositeElementType("SLE_INSTRUCTION");
  IElementType SLS_INSTRUCTION = new M68kCompositeElementType("SLS_INSTRUCTION");
  IElementType SLT_INSTRUCTION = new M68kCompositeElementType("SLT_INSTRUCTION");
  IElementType SMI_INSTRUCTION = new M68kCompositeElementType("SMI_INSTRUCTION");
  IElementType SNE_INSTRUCTION = new M68kCompositeElementType("SNE_INSTRUCTION");
  IElementType SPL_INSTRUCTION = new M68kCompositeElementType("SPL_INSTRUCTION");
  IElementType STOP_INSTRUCTION = new M68kCompositeElementType("STOP_INSTRUCTION");
  IElementType STRING_EXPRESSION = new M68kCompositeElementType("STRING_EXPRESSION");
  IElementType ST_INSTRUCTION = new M68kCompositeElementType("ST_INSTRUCTION");
  IElementType SUBA_INSTRUCTION = new M68kCompositeElementType("SUBA_INSTRUCTION");
  IElementType SUBI_INSTRUCTION = new M68kCompositeElementType("SUBI_INSTRUCTION");
  IElementType SUBQ_INSTRUCTION = new M68kCompositeElementType("SUBQ_INSTRUCTION");
  IElementType SUBX_INSTRUCTION = new M68kCompositeElementType("SUBX_INSTRUCTION");
  IElementType SUB_INSTRUCTION = new M68kCompositeElementType("SUB_INSTRUCTION");
  IElementType SVC_INSTRUCTION = new M68kCompositeElementType("SVC_INSTRUCTION");
  IElementType SVS_INSTRUCTION = new M68kCompositeElementType("SVS_INSTRUCTION");
  IElementType SWAP_INSTRUCTION = new M68kCompositeElementType("SWAP_INSTRUCTION");
  IElementType TAS_INSTRUCTION = new M68kCompositeElementType("TAS_INSTRUCTION");
  IElementType TRAPV_INSTRUCTION = new M68kCompositeElementType("TRAPV_INSTRUCTION");
  IElementType TRAP_INSTRUCTION = new M68kCompositeElementType("TRAP_INSTRUCTION");
  IElementType TST_INSTRUCTION = new M68kCompositeElementType("TST_INSTRUCTION");
  IElementType UNARY_COMPLEMENT_EXPRESSION = new M68kCompositeElementType("UNARY_COMPLEMENT_EXPRESSION");
  IElementType UNARY_MINUS_EXPRESSION = new M68kCompositeElementType("UNARY_MINUS_EXPRESSION");
  IElementType UNARY_PLUS_EXPRESSION = new M68kCompositeElementType("UNARY_PLUS_EXPRESSION");
  IElementType UNLK_INSTRUCTION = new M68kCompositeElementType("UNLK_INSTRUCTION");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ABCD_INSTRUCTION) {
        return new M68kAbcdInstructionImpl(node);
      }
      else if (type == ADDA_INSTRUCTION) {
        return new M68kAddaInstructionImpl(node);
      }
      else if (type == ADDI_INSTRUCTION) {
        return new M68kAddiInstructionImpl(node);
      }
      else if (type == ADDQ_INSTRUCTION) {
        return new M68kAddqInstructionImpl(node);
      }
      else if (type == ADDX_INSTRUCTION) {
        return new M68kAddxInstructionImpl(node);
      }
      else if (type == ADD_INSTRUCTION) {
        return new M68kAddInstructionImpl(node);
      }
      else if (type == ADM_ABS) {
        return new M68kAdmAbsImpl(node);
      }
      else if (type == ADM_ADI) {
        return new M68kAdmAdiImpl(node);
      }
      else if (type == ADM_AIX) {
        return new M68kAdmAixImpl(node);
      }
      else if (type == ADM_APD) {
        return new M68kAdmApdImpl(node);
      }
      else if (type == ADM_API) {
        return new M68kAdmApiImpl(node);
      }
      else if (type == ADM_ARD) {
        return new M68kAdmArdImpl(node);
      }
      else if (type == ADM_ARI) {
        return new M68kAdmAriImpl(node);
      }
      else if (type == ADM_CCR) {
        return new M68kAdmCcrImpl(node);
      }
      else if (type == ADM_DRD) {
        return new M68kAdmDrdImpl(node);
      }
      else if (type == ADM_IMM) {
        return new M68kAdmImmImpl(node);
      }
      else if (type == ADM_PCD) {
        return new M68kAdmPcdImpl(node);
      }
      else if (type == ADM_PCI) {
        return new M68kAdmPciImpl(node);
      }
      else if (type == ADM_RRD) {
        return new M68kAdmRrdImpl(node);
      }
      else if (type == ADM_SR) {
        return new M68kAdmSrImpl(node);
      }
      else if (type == ADM_USP) {
        return new M68kAdmUspImpl(node);
      }
      else if (type == ANDI_INSTRUCTION) {
        return new M68kAndiInstructionImpl(node);
      }
      else if (type == AND_EXPRESSION) {
        return new M68kAndExpressionImpl(node);
      }
      else if (type == AND_INSTRUCTION) {
        return new M68kAndInstructionImpl(node);
      }
      else if (type == ASL_INSTRUCTION) {
        return new M68kAslInstructionImpl(node);
      }
      else if (type == ASR_INSTRUCTION) {
        return new M68kAsrInstructionImpl(node);
      }
      else if (type == BCC_INSTRUCTION) {
        return new M68kBccInstructionImpl(node);
      }
      else if (type == BCHG_INSTRUCTION) {
        return new M68kBchgInstructionImpl(node);
      }
      else if (type == BCLR_INSTRUCTION) {
        return new M68kBclrInstructionImpl(node);
      }
      else if (type == BCS_INSTRUCTION) {
        return new M68kBcsInstructionImpl(node);
      }
      else if (type == BEQ_INSTRUCTION) {
        return new M68kBeqInstructionImpl(node);
      }
      else if (type == BGE_INSTRUCTION) {
        return new M68kBgeInstructionImpl(node);
      }
      else if (type == BGT_INSTRUCTION) {
        return new M68kBgtInstructionImpl(node);
      }
      else if (type == BHI_INSTRUCTION) {
        return new M68kBhiInstructionImpl(node);
      }
      else if (type == BHS_INSTRUCTION) {
        return new M68kBhsInstructionImpl(node);
      }
      else if (type == BLE_INSTRUCTION) {
        return new M68kBleInstructionImpl(node);
      }
      else if (type == BLK_DIRECTIVE) {
        return new M68kBlkDirectiveImpl(node);
      }
      else if (type == BLO_INSTRUCTION) {
        return new M68kBloInstructionImpl(node);
      }
      else if (type == BLS_INSTRUCTION) {
        return new M68kBlsInstructionImpl(node);
      }
      else if (type == BLT_INSTRUCTION) {
        return new M68kBltInstructionImpl(node);
      }
      else if (type == BMI_INSTRUCTION) {
        return new M68kBmiInstructionImpl(node);
      }
      else if (type == BNE_INSTRUCTION) {
        return new M68kBneInstructionImpl(node);
      }
      else if (type == BPL_INSTRUCTION) {
        return new M68kBplInstructionImpl(node);
      }
      else if (type == BRA_INSTRUCTION) {
        return new M68kBraInstructionImpl(node);
      }
      else if (type == BSET_INSTRUCTION) {
        return new M68kBsetInstructionImpl(node);
      }
      else if (type == BSR_INSTRUCTION) {
        return new M68kBsrInstructionImpl(node);
      }
      else if (type == BTST_INSTRUCTION) {
        return new M68kBtstInstructionImpl(node);
      }
      else if (type == BVC_INSTRUCTION) {
        return new M68kBvcInstructionImpl(node);
      }
      else if (type == BVS_INSTRUCTION) {
        return new M68kBvsInstructionImpl(node);
      }
      else if (type == CHK_INSTRUCTION) {
        return new M68kChkInstructionImpl(node);
      }
      else if (type == CLR_INSTRUCTION) {
        return new M68kClrInstructionImpl(node);
      }
      else if (type == CMPA_INSTRUCTION) {
        return new M68kCmpaInstructionImpl(node);
      }
      else if (type == CMPI_INSTRUCTION) {
        return new M68kCmpiInstructionImpl(node);
      }
      else if (type == CMPM_INSTRUCTION) {
        return new M68kCmpmInstructionImpl(node);
      }
      else if (type == CMP_INSTRUCTION) {
        return new M68kCmpInstructionImpl(node);
      }
      else if (type == DBCC_INSTRUCTION) {
        return new M68kDbccInstructionImpl(node);
      }
      else if (type == DBCS_INSTRUCTION) {
        return new M68kDbcsInstructionImpl(node);
      }
      else if (type == DBEQ_INSTRUCTION) {
        return new M68kDbeqInstructionImpl(node);
      }
      else if (type == DBF_INSTRUCTION) {
        return new M68kDbfInstructionImpl(node);
      }
      else if (type == DBGE_INSTRUCTION) {
        return new M68kDbgeInstructionImpl(node);
      }
      else if (type == DBGT_INSTRUCTION) {
        return new M68kDbgtInstructionImpl(node);
      }
      else if (type == DBHI_INSTRUCTION) {
        return new M68kDbhiInstructionImpl(node);
      }
      else if (type == DBLE_INSTRUCTION) {
        return new M68kDbleInstructionImpl(node);
      }
      else if (type == DBLS_INSTRUCTION) {
        return new M68kDblsInstructionImpl(node);
      }
      else if (type == DBLT_INSTRUCTION) {
        return new M68kDbltInstructionImpl(node);
      }
      else if (type == DBMI_INSTRUCTION) {
        return new M68kDbmiInstructionImpl(node);
      }
      else if (type == DBNE_INSTRUCTION) {
        return new M68kDbneInstructionImpl(node);
      }
      else if (type == DBPL_INSTRUCTION) {
        return new M68kDbplInstructionImpl(node);
      }
      else if (type == DBRA_INSTRUCTION) {
        return new M68kDbraInstructionImpl(node);
      }
      else if (type == DBT_INSTRUCTION) {
        return new M68kDbtInstructionImpl(node);
      }
      else if (type == DBVC_INSTRUCTION) {
        return new M68kDbvcInstructionImpl(node);
      }
      else if (type == DBVS_INSTRUCTION) {
        return new M68kDbvsInstructionImpl(node);
      }
      else if (type == DCB_DIRECTIVE) {
        return new M68kDcbDirectiveImpl(node);
      }
      else if (type == DC_DIRECTIVE) {
        return new M68kDcDirectiveImpl(node);
      }
      else if (type == DIVS_INSTRUCTION) {
        return new M68kDivsInstructionImpl(node);
      }
      else if (type == DIVU_INSTRUCTION) {
        return new M68kDivuInstructionImpl(node);
      }
      else if (type == DIV_EXPRESSION) {
        return new M68kDivExpressionImpl(node);
      }
      else if (type == DS_DIRECTIVE) {
        return new M68kDsDirectiveImpl(node);
      }
      else if (type == ENDC_DIRECTIVE) {
        return new M68kEndcDirectiveImpl(node);
      }
      else if (type == ENDM_DIRECTIVE) {
        return new M68kEndmDirectiveImpl(node);
      }
      else if (type == END_DIRECTIVE) {
        return new M68kEndDirectiveImpl(node);
      }
      else if (type == EORI_INSTRUCTION) {
        return new M68kEoriInstructionImpl(node);
      }
      else if (type == EOR_INSTRUCTION) {
        return new M68kEorInstructionImpl(node);
      }
      else if (type == EQUALS_DIRECTIVE) {
        return new M68kEqualsDirectiveImpl(node);
      }
      else if (type == EQUR_DIRECTIVE) {
        return new M68kEqurDirectiveImpl(node);
      }
      else if (type == EQU_DIRECTIVE) {
        return new M68kEquDirectiveImpl(node);
      }
      else if (type == EVEN_DIRECTIVE) {
        return new M68kEvenDirectiveImpl(node);
      }
      else if (type == EXG_INSTRUCTION) {
        return new M68kExgInstructionImpl(node);
      }
      else if (type == EXP_EXPRESSION) {
        return new M68kExpExpressionImpl(node);
      }
      else if (type == EXT_INSTRUCTION) {
        return new M68kExtInstructionImpl(node);
      }
      else if (type == IFND_DIRECTIVE) {
        return new M68kIfndDirectiveImpl(node);
      }
      else if (type == ILLEGAL_INSTRUCTION) {
        return new M68kIllegalInstructionImpl(node);
      }
      else if (type == INCBIN_DIRECTIVE) {
        return new M68kIncbinDirectiveImpl(node);
      }
      else if (type == INCDIR_DIRECTIVE) {
        return new M68kIncdirDirectiveImpl(node);
      }
      else if (type == INCLUDE_DIRECTIVE) {
        return new M68kIncludeDirectiveImpl(node);
      }
      else if (type == JMP_INSTRUCTION) {
        return new M68kJmpInstructionImpl(node);
      }
      else if (type == JSR_INSTRUCTION) {
        return new M68kJsrInstructionImpl(node);
      }
      else if (type == LABEL) {
        return new M68kLabelImpl(node);
      }
      else if (type == LABEL_REF_EXPRESSION) {
        return new M68kLabelRefExpressionImpl(node);
      }
      else if (type == LEA_INSTRUCTION) {
        return new M68kLeaInstructionImpl(node);
      }
      else if (type == LINK_INSTRUCTION) {
        return new M68kLinkInstructionImpl(node);
      }
      else if (type == LOCAL_LABEL) {
        return new M68kLocalLabelImpl(node);
      }
      else if (type == LSL_INSTRUCTION) {
        return new M68kLslInstructionImpl(node);
      }
      else if (type == LSR_INSTRUCTION) {
        return new M68kLsrInstructionImpl(node);
      }
      else if (type == MACRO_DIRECTIVE) {
        return new M68kMacroDirectiveImpl(node);
      }
      else if (type == MINUS_EXPRESSION) {
        return new M68kMinusExpressionImpl(node);
      }
      else if (type == MOD_EXPRESSION) {
        return new M68kModExpressionImpl(node);
      }
      else if (type == MOVEA_INSTRUCTION) {
        return new M68kMoveaInstructionImpl(node);
      }
      else if (type == MOVEM_INSTRUCTION) {
        return new M68kMovemInstructionImpl(node);
      }
      else if (type == MOVEP_INSTRUCTION) {
        return new M68kMovepInstructionImpl(node);
      }
      else if (type == MOVEQ_INSTRUCTION) {
        return new M68kMoveqInstructionImpl(node);
      }
      else if (type == MOVE_INSTRUCTION) {
        return new M68kMoveInstructionImpl(node);
      }
      else if (type == MULS_INSTRUCTION) {
        return new M68kMulsInstructionImpl(node);
      }
      else if (type == MULU_INSTRUCTION) {
        return new M68kMuluInstructionImpl(node);
      }
      else if (type == MUL_EXPRESSION) {
        return new M68kMulExpressionImpl(node);
      }
      else if (type == NBCD_INSTRUCTION) {
        return new M68kNbcdInstructionImpl(node);
      }
      else if (type == NEGX_INSTRUCTION) {
        return new M68kNegxInstructionImpl(node);
      }
      else if (type == NEG_INSTRUCTION) {
        return new M68kNegInstructionImpl(node);
      }
      else if (type == NOP_INSTRUCTION) {
        return new M68kNopInstructionImpl(node);
      }
      else if (type == NOT_INSTRUCTION) {
        return new M68kNotInstructionImpl(node);
      }
      else if (type == NUMBER_EXPRESSION) {
        return new M68kNumberExpressionImpl(node);
      }
      else if (type == ODD_DIRECTIVE) {
        return new M68kOddDirectiveImpl(node);
      }
      else if (type == OPT_DIRECTIVE) {
        return new M68kOptDirectiveImpl(node);
      }
      else if (type == ORG_DIRECTIVE) {
        return new M68kOrgDirectiveImpl(node);
      }
      else if (type == ORI_INSTRUCTION) {
        return new M68kOriInstructionImpl(node);
      }
      else if (type == OR_EXPRESSION) {
        return new M68kOrExpressionImpl(node);
      }
      else if (type == OR_INSTRUCTION) {
        return new M68kOrInstructionImpl(node);
      }
      else if (type == PAREN_EXPRESSION) {
        return new M68kParenExpressionImpl(node);
      }
      else if (type == PEA_INSTRUCTION) {
        return new M68kPeaInstructionImpl(node);
      }
      else if (type == PLUS_EXPRESSION) {
        return new M68kPlusExpressionImpl(node);
      }
      else if (type == REGISTER_LIST) {
        return new M68kRegisterListImpl(node);
      }
      else if (type == REGISTER_RANGE) {
        return new M68kRegisterRangeImpl(node);
      }
      else if (type == RESET_INSTRUCTION) {
        return new M68kResetInstructionImpl(node);
      }
      else if (type == ROL_INSTRUCTION) {
        return new M68kRolInstructionImpl(node);
      }
      else if (type == ROR_INSTRUCTION) {
        return new M68kRorInstructionImpl(node);
      }
      else if (type == ROXL_INSTRUCTION) {
        return new M68kRoxlInstructionImpl(node);
      }
      else if (type == ROXR_INSTRUCTION) {
        return new M68kRoxrInstructionImpl(node);
      }
      else if (type == RSRESET_DIRECTIVE) {
        return new M68kRsresetDirectiveImpl(node);
      }
      else if (type == RSSET_DIRECTIVE) {
        return new M68kRssetDirectiveImpl(node);
      }
      else if (type == RS_DIRECTIVE) {
        return new M68kRsDirectiveImpl(node);
      }
      else if (type == RTE_INSTRUCTION) {
        return new M68kRteInstructionImpl(node);
      }
      else if (type == RTR_INSTRUCTION) {
        return new M68kRtrInstructionImpl(node);
      }
      else if (type == RTS_INSTRUCTION) {
        return new M68kRtsInstructionImpl(node);
      }
      else if (type == SBCD_INSTRUCTION) {
        return new M68kSbcdInstructionImpl(node);
      }
      else if (type == SCC_INSTRUCTION) {
        return new M68kSccInstructionImpl(node);
      }
      else if (type == SCS_INSTRUCTION) {
        return new M68kScsInstructionImpl(node);
      }
      else if (type == SEQ_INSTRUCTION) {
        return new M68kSeqInstructionImpl(node);
      }
      else if (type == SF_INSTRUCTION) {
        return new M68kSfInstructionImpl(node);
      }
      else if (type == SGE_INSTRUCTION) {
        return new M68kSgeInstructionImpl(node);
      }
      else if (type == SGT_INSTRUCTION) {
        return new M68kSgtInstructionImpl(node);
      }
      else if (type == SHIFT_LEFT_EXPRESSION) {
        return new M68kShiftLeftExpressionImpl(node);
      }
      else if (type == SHIFT_RIGHT_EXPRESSION) {
        return new M68kShiftRightExpressionImpl(node);
      }
      else if (type == SHI_INSTRUCTION) {
        return new M68kShiInstructionImpl(node);
      }
      else if (type == SLE_INSTRUCTION) {
        return new M68kSleInstructionImpl(node);
      }
      else if (type == SLS_INSTRUCTION) {
        return new M68kSlsInstructionImpl(node);
      }
      else if (type == SLT_INSTRUCTION) {
        return new M68kSltInstructionImpl(node);
      }
      else if (type == SMI_INSTRUCTION) {
        return new M68kSmiInstructionImpl(node);
      }
      else if (type == SNE_INSTRUCTION) {
        return new M68kSneInstructionImpl(node);
      }
      else if (type == SPL_INSTRUCTION) {
        return new M68kSplInstructionImpl(node);
      }
      else if (type == STOP_INSTRUCTION) {
        return new M68kStopInstructionImpl(node);
      }
      else if (type == STRING_EXPRESSION) {
        return new M68kStringExpressionImpl(node);
      }
      else if (type == ST_INSTRUCTION) {
        return new M68kStInstructionImpl(node);
      }
      else if (type == SUBA_INSTRUCTION) {
        return new M68kSubaInstructionImpl(node);
      }
      else if (type == SUBI_INSTRUCTION) {
        return new M68kSubiInstructionImpl(node);
      }
      else if (type == SUBQ_INSTRUCTION) {
        return new M68kSubqInstructionImpl(node);
      }
      else if (type == SUBX_INSTRUCTION) {
        return new M68kSubxInstructionImpl(node);
      }
      else if (type == SUB_INSTRUCTION) {
        return new M68kSubInstructionImpl(node);
      }
      else if (type == SVC_INSTRUCTION) {
        return new M68kSvcInstructionImpl(node);
      }
      else if (type == SVS_INSTRUCTION) {
        return new M68kSvsInstructionImpl(node);
      }
      else if (type == SWAP_INSTRUCTION) {
        return new M68kSwapInstructionImpl(node);
      }
      else if (type == TAS_INSTRUCTION) {
        return new M68kTasInstructionImpl(node);
      }
      else if (type == TRAPV_INSTRUCTION) {
        return new M68kTrapvInstructionImpl(node);
      }
      else if (type == TRAP_INSTRUCTION) {
        return new M68kTrapInstructionImpl(node);
      }
      else if (type == TST_INSTRUCTION) {
        return new M68kTstInstructionImpl(node);
      }
      else if (type == UNARY_COMPLEMENT_EXPRESSION) {
        return new M68kUnaryComplementExpressionImpl(node);
      }
      else if (type == UNARY_MINUS_EXPRESSION) {
        return new M68kUnaryMinusExpressionImpl(node);
      }
      else if (type == UNARY_PLUS_EXPRESSION) {
        return new M68kUnaryPlusExpressionImpl(node);
      }
      else if (type == UNLK_INSTRUCTION) {
        return new M68kUnlkInstructionImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
