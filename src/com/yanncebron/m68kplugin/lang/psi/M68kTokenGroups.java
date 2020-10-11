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

import com.intellij.psi.tree.TokenSet;

import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

public final class M68kTokenGroups {

  public static final TokenSet BCC_INSTRUCTIONS = TokenSet.create(
    BCC,
    BCS,
    BEQ,
    BGE,
    BGT,
    BHI,
    BHS,
    BLE,
    BLO,
    BLS,
    BLT,
    BMI,
    BNE,
    BPL,
    BRA,
    BVC,
    BVS
  );

  public static final TokenSet DBCC_INSTRUCTIONS = TokenSet.create(
    DBCC,
    DBCS,
    DBEQ,
    DBF,
    DBGE,
    DBGT,
    DBHI,
    DBLE,
    DBLS,
    DBLT,
    DBMI,
    DBNE,
    DBPL,
    DBRA,
    DBT,
    DBVC,
    DBVS
  );

  public static final TokenSet SCC_INSTRUCTIONS = TokenSet.create(
    SCC,
    SCS,
    SEQ,
    SF,
    SGE,
    SGT,
    SHI,
    SLE,
    SLS,
    SLT,
    SMI,
    SNE,
    SPL,
    ST,
    SVC,
    SVS
  );

  public static final TokenSet INSTRUCTIONS = TokenSet.orSet(TokenSet.create(
    ABCD,
    ADD,
    ADDA,
    ADDI,
    ADDQ,
    ADDX,
    AND,
    ANDI,
    ASL,
    ASR,
    BCHG,
    BCLR,
    BLK,
    BSET,
    BSR,
    BTST,
    CHK,
    CLR,
    CMP,
    CMPA,
    CMPI,
    CMPM,
    DIVS,
    DIVU,
    EOR,
    EORI,
    EXG,
    EXT,
    ILLEGAL,
    JMP,
    JSR,
    LEA,
    LINK,
    LSL,
    LSR,
    MOVE,
    MOVEA,
    MOVEM,
    MOVEP,
    MOVEQ,
    MULS,
    MULU,
    NBCD,
    NEG,
    NEGX,
    NOP,
    NOT,
    OR,
    ORI,
    PEA,
    RESET,
    ROL,
    ROR,
    ROXL,
    ROXR,
    RTE,
    RTR,
    RTS,
    SBCD,
    STOP,
    SUB,
    SUBA,
    SUBI,
    SUBQ,
    SUBX,
    SWAP,
    TAS,
    TRAP,
    TRAPV,
    TST,
    UNLK
  ), BCC_INSTRUCTIONS, DBCC_INSTRUCTIONS, SCC_INSTRUCTIONS);

  public static final TokenSet DATA_SIZES = TokenSet.create(
    DOT_B,
    DOT_L,
    DOT_S,
    DOT_W
  );

  public static final TokenSet DIRECTIVES = TokenSet.create(
    ADDWATCH,
    ALIGN,
    BLK,
    BSS,
    BSS_C,
    BSS_F,
    CNOP,
    CODE,
    CODE_C,
    CODE_F,
    CSEG,
    DATA,
    DATA_C,
    DATA_F,
    DC,
    DCB,
    DS,
    DSEG,
    EINLINE,
    END,
    ENDM,
    EQ,
    EQU,
    EQUR,
    EVEN,
    INCBIN,
    INCDIR,
    INCLUDE,
    INLINE,
    JUMPERR,
    JUMPPTR,
    LIST,
    LLEN,
    MACRO,
    NOLIST,
    NOPAGE,
    ODD,
    OPT,
    ORG,
    PAGE,
    PLEN,
    RS,
    RSRESET,
    RSSET,
    SECTION,
    SET,
    SPC,
    TEXT
  );

  public static final TokenSet CONDITIONAL_ASSEMBLY_DIRECTIVES = TokenSet.create(
    ELSE,
    ELSEIF,
    ENDC,
    ENDIF,
    IF,
    IFB,
    IFC,
    IFD,
    IFEQ,
    IFGE,
    IFGT,
    IFLE,
    IFLT,
    IFMACROD,
    IFMACROND,
    IFNB,
    IFNC,
    IFND,
    IFNE
  );

  public static final TokenSet OPERATORS = TokenSet.create(
    AMPERSAND,
    BACKSLASH,
    DIV,
    MINUS,
    MUL,
    PERCENT,
    PIPE,
    PLUS,
    SHIFT_L,
    SHIFT_R,
    TILDE
  );

  public static final TokenSet NUMBERS = TokenSet.create(
    BIN_NUMBER,
    DEC_NUMBER,
    HEX_NUMBER,
    OCT_NUMBER
  );
}
