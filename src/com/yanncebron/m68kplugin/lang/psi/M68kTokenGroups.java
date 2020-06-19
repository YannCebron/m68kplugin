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

  public static final TokenSet INSTRUCTIONS = TokenSet.create(
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
    BCC,
    BCHG,
    BCLR,
    BCS,
    BEQ,
    BGE,
    BGT,
    BHI,
    BHS,
    BLE,
    BLK,
    BLO,
    BLS,
    BLT,
    BMI,
    BNE,
    BPL,
    BRA,
    BSET,
    BSR,
    BTST,
    BVC,
    BVS,
    CHK,
    CLR,
    CMP,
    CMPA,
    CMPI,
    CMPM,
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
    DBVS,
    DIV,
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
    MUL,
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
    STOP,
    SUB,
    SUBA,
    SUBI,
    SUBQ,
    SUBX,
    SVC,
    SVS,
    SWAP,
    TAS,
    TRAP,
    TRAPV,
    TST,
    UNLK
  );

  public static final TokenSet DATA_SIZES = TokenSet.create(
    DOT_B,
    DOT_W,
    DOT_L,
    DOT_S
  );

  public static final TokenSet DIRECTIVES = TokenSet.create(
    BLK,
    DC,
    DCB,
    DS,
    END,
    ENDM,
    EQ,
    EQU,
    EQUR,
    EVEN,
    INCBIN,
    INCDIR,
    INCLUDE,
    MACRO,
    ODD,
    OPT,
    ORG,
    RS,
    RSRESET,
    RSSET,
    SET
  );

  public static final TokenSet CONDITIONAL_ASSEMBLY_DIRECTIVES = TokenSet.create(
    ELSE,
    ELSEIF,
    ENDC,
    IF,
    IFB,
    IFD,
    IFEQ,
    IFGE,
    IFGT,
    IFLE,
    IFLT,
    IFNB,
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
    DEC_NUMBER,
    HEX_NUMBER,
    OCT_NUMBER,
    BIN_NUMBER
  );
}
