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
    DBHS,
    DBLE,
    DBLO,
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
    SHS,
    SLE,
    SLO,
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
    BKPT,
    BRA,
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
    MOVEC,
    MOVEM,
    MOVEP,
    MOVEQ,
    MOVES,
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
    RTD,
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
    AC68080,
    ADDWATCH,
    ALIGN,
    ASSERT,
    AUTO,
    BASEREG,
    BLK,
    BSS,
    BSS_C,
    BSS_F,
    CLRFO,
    CLRSO,
    CNOP,
    CODE,
    CODE_C,
    CODE_F,
    CPU32,
    CSEG,
    DATA,
    DATA_C,
    DATA_F,
    DC,
    DCB,
    DR,
    DS,
    DSEG,
    DX,
    ECHO,
    EINLINE,
    END,
    ENDB,
    ENDM,
    ENDR,
    EQU,
    EQUR,
    EQ_DIRECTIVE,
    EREM,
    EVEN,
    FAIL,
    FAR,
    FO,
    FPU,
    IDNT,
    INCBIN,
    INCDIR,
    INCLUDE,
    INITNEAR,
    INLINE,
    JUMPERR,
    JUMPPTR,
    LIST,
    LLEN,
    LOAD,
    MACHINE,
    MACRO,
    MASK2,
    MC68000,
    MC68010,
    MC68020,
    MC68030,
    MC68040,
    MC68060,
    MEXIT,
    MSOURCE,
    NEAR,
    NEAR_CODE,
    NOLIST,
    NOPAGE,
    ODD,
    OFFSET,
    OPT,
    ORG,
    OUTPUT,
    PAGE,
    PLEN,
    POPSECTION,
    PRINTT,
    PRINTV,
    PUSHSECTION,
    REG,
    REM,
    REPT,
    RS,
    RSRESET,
    RSSET,
    SECTION,
    SET,
    SETFO,
    SETSO,
    SO,
    SPC,
    TEXT,
    TTL,
    XDEF,
    XREF
  );

  public static final TokenSet CONDITIONAL_ASSEMBLY_START_DIRECTIVES = TokenSet.create(
    IF,
    IF1,
    IF2,
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
    IFMI,
    IFNB,
    IFNC,
    IFND,
    IFNE,
    IFP1,
    IFPL
  );

  public static final TokenSet CONDITIONAL_ASSEMBLY_STRUCTURE_DIRECTIVES = TokenSet.create(
    ELSE,
    ELSEIF
  );

  public static final TokenSet CONDITIONAL_ASSEMBLY_END_DIRECTIVES = TokenSet.create(
    ENDC,
    ENDIF
  );

  public static final TokenSet CONDITIONAL_ASSEMBLY_DIRECTIVES =
    TokenSet.orSet(
      CONDITIONAL_ASSEMBLY_START_DIRECTIVES,
      CONDITIONAL_ASSEMBLY_END_DIRECTIVES,
      CONDITIONAL_ASSEMBLY_STRUCTURE_DIRECTIVES
    );

  public static final TokenSet OPERATION_SIGNS = TokenSet.create(
    AMPERSAND,
    AMPERSAND_AMPERSAND,
    BACKSLASH,
    DIV,
    EQ,
    EQ_EQ,
    EXCLAMATION,
    EXCLAMATION_EQ,
    GT,
    GT_EQ,
    GT_GT,
    LT,
    LT_EQ,
    LT_GT,
    LT_LT,
    MINUS,
    MUL,
    PERCENT,
    PIPE,
    PIPE_PIPE,
    PLUS,
    POW,
    SLASH_SLASH,
    TILDE
  );

  public static final TokenSet NUMBERS = TokenSet.create(
    BIN_NUMBER,
    DEC_NUMBER,
    HEX_NUMBER,
    OCT_NUMBER
  );

  public static final TokenSet COMMENTS = TokenSet.create(COMMENT);

  public static final TokenSet STRING_LITERALS = TokenSet.create(STRING);
}
