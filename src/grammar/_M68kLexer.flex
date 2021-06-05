/*
 * Copyright 2021 The Authors
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

package com.yanncebron.m68kplugin.lexer;

import com.intellij.psi.tree.IElementType;

import com.intellij.lexer.FlexLexer;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

%%

%{
  public _M68kLexer() {
    this((java.io.Reader)null);
  }

  private boolean afterSpaceOrComma() {
    char previousChar = charAt(-1);
    return Character.isSpaceChar(previousChar) || previousChar == ',';
  }

  private void beginDataSized() {
    pushbackDataSize();
    yybegin(AFTER_INSTRUCTION);
  }

  /**
   * Pushes back (optional) DATA_SIZE token if present.
   */
  private void pushbackDataSize() {
    if (charAt(yylength() - 2) == '.') {
      char previousChar = charAt(yylength() - 1);
      if (previousChar == 's' || previousChar == 'b' || previousChar == 'w' || previousChar == 'l' ||
          previousChar == 'S' || previousChar == 'B' || previousChar == 'W' || previousChar == 'L') {
        yypushback(2);
      }
    }
  }

  private char charAt(final int offset) {
    final int loc = getTokenStart() + offset;
    return 0 <= loc && loc < zzBuffer.length() ? zzBuffer.charAt(loc) : (char) -1;
  }

  int operandSpaceCount = 0;
%}

%public
%class _M68kLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

CRLF=[\r\n]
WHITE_SPACE=[\ \t\f]

COMMENT=;.*|\*.* // todo leading "*" only on empty line
EOL_COMMENT=;.*

DECNUMBER=[\d]+
HEXNUMBER=\$\p{XDigit}+
OCTNUMBER=@[0-7]+
BINNUMBER=%[0|1]+

SINGLE_QUOTED_STRING='([^\\'\r\n]|\\[^\r\n])*'?
DOUBLE_QUOTED_STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?
UNQUOTED_STRING=([^\\\r\n\ \t\f'\"])+

LABEL=[_\d[\\@]]*[\p{Alpha}] [\p{Alpha}\d[.]_[\\@]]*  // without "." first char
ID=[.]?{LABEL}

DATA_SIZE=[.][[sS]|[bB]|[wW]|[lL]]

A=[aA]
B=[bB]
C=[cC]
D=[dD]
E=[eE]
F=[fF]
G=[gG]
H=[hH]
I=[iI]
J=[jJ]
K=[kK]
L=[lL]
M=[mM]
N=[nN]
O=[oO]
P=[pP]
Q=[qQ]
R=[rR]
S=[sS]
T=[tT]
U=[uU]
V=[vV]
W=[wW]
X=[xX]
Y=[yY]
Z=[zZ]

%state AFTER_LABEL
%state IN_INSTRUCTION
%state AFTER_INSTRUCTION
%state STRING_DIRECTIVE
%state IN_OPERAND
%state AFTER_OPERAND

%%
<YYINITIAL, AFTER_LABEL, IN_INSTRUCTION, AFTER_INSTRUCTION, STRING_DIRECTIVE, IN_OPERAND, AFTER_OPERAND> {
  {CRLF}         { operandSpaceCount = 0; yybegin(YYINITIAL); return LINEFEED; }
}

<YYINITIAL> {
  "."            { operandSpaceCount = 0; return DOT; }
  {LABEL}        { operandSpaceCount = 0; yybegin(AFTER_LABEL); return ID; }

  {WHITE_SPACE}* {COMMENT} { return COMMENT; }
  {WHITE_SPACE}+ { operandSpaceCount = 0; yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}


<AFTER_LABEL> {
  ":"            { yybegin(IN_INSTRUCTION); return COLON; }
  "="            { yybegin(IN_OPERAND); return EQ; } // duplicated in IN_INSTRUCTION

// should be here instead of IN_INSTRUCTION - they must follow label (whitespace switching problem)
//  {M}{A}{C}{R}{O}             { yybegin(AFTER_OPERAND); return MACRO; }
//  {E}{Q}{U}                   { yybegin(IN_OPERAND); return EQU; }
//  {E}{Q}{U}{R}                { yybegin(IN_OPERAND); return EQUR; }

  {WHITE_SPACE}* {COMMENT}      { return COMMENT; }
  {WHITE_SPACE}+ { yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}


// after M68kDataSized instruction: data_size || WHITE_SPACE + operand
<AFTER_INSTRUCTION> {
  {WHITE_SPACE}+ { operandSpaceCount = 1; yybegin(IN_OPERAND); return WHITE_SPACE; }

  "." {S}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_S; }
  "." {B}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_B; }
  "." {W}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_W; }
  "." {L}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_L; }
}


// after all operands (if any): only whitespace/automatic comment
<AFTER_OPERAND> {
  {WHITE_SPACE}+ { return WHITE_SPACE; }
  .+             { return COMMENT; }
}

// optimizations: separate case for without {DATA_SIZE}?
<IN_OPERAND> {
  // after 2nd WHITE_SPACE -> AFTER_OPERAND for automatic comment
  {WHITE_SPACE}+           { if (operandSpaceCount++ == 1) { yybegin(AFTER_OPERAND); } return WHITE_SPACE; }

  {WHITE_SPACE}+ {COMMENT} { return COMMENT; }
  {EOL_COMMENT}            { return COMMENT; }

  {S}{P}                   { return SP; }
  {S}{P} {DATA_SIZE}?      { pushbackDataSize(); return SP; }
  {S}{S}{P}                { return SSP; }
  {U}{S}{P}                { return USP; }
  {P}{C}                   { return PC; }
  {S}{R}                   { return SR; }
  {C}{C}{R}                { return CCR; }
  {D}[0-7]                 { return DATA_REGISTER; }
  {D}[0-7] {DATA_SIZE}?    { pushbackDataSize(); return DATA_REGISTER; }
  {A}[0-7]                 { return ADDRESS_REGISTER; }
  {A}[0-7] {DATA_SIZE}?    { pushbackDataSize(); return ADDRESS_REGISTER; }

  // distinguish 'd6.l'/'$4000.l' vs. 'bra .l'/'dbf d0,.s'
  "." {B}    { if (afterSpaceOrComma()) { return ID; } return DOT_B; }
  "." {S}    { if (afterSpaceOrComma()) { return ID; } return DOT_S; }
  "." {W}    { if (afterSpaceOrComma()) { return ID; } return DOT_W; }
  "." {L}    { if (afterSpaceOrComma()) { return ID; } return DOT_L; }

  // must be after all register names
  {ID}       { return ID; }

  "."  { return DOT; }
  ","  { return COMMA; }
  "+"  { return PLUS; }
  "-"  { return MINUS; }
  "*"  { return MUL; }
  "/"  { return DIV; }
  "^"  { return POW; }
  "#"  { return HASH; }
  "~"  { return TILDE; }
  "!"  { return EXCLAMATION; }
  "%"  { return PERCENT; }
  "&"  { return AMPERSAND; }
  "\\" { return BACKSLASH; }
  "|"  { return PIPE; }
  "<<" { return SHIFT_L; }
  ">>" { return SHIFT_R; }
  "("  { return L_PAREN; }
  ")"  { return R_PAREN; }
  "["  { return L_BRACKET; }
  "]"  { return R_BRACKET; }
  "="  { return EQ; }

  {DECNUMBER}                 { return DEC_NUMBER; }
  {HEXNUMBER}                 { return HEX_NUMBER; }
  {OCTNUMBER}                 { return OCT_NUMBER; }
  {BINNUMBER}                 { return BIN_NUMBER; }

  {SINGLE_QUOTED_STRING}      { return STRING; }
  {DOUBLE_QUOTED_STRING}      { return STRING; }
}

// 1st operand==STRING, optionally followed by IN_OPERAND
<STRING_DIRECTIVE> {
  // after 2nd WHITE_SPACE -> AFTER_OPERAND for automatic comment
  {WHITE_SPACE}+           { operandSpaceCount++; return WHITE_SPACE; }

  {WHITE_SPACE}+ {COMMENT} { return COMMENT; }
  {EOL_COMMENT}            { return COMMENT; }

  {SINGLE_QUOTED_STRING}   { yybegin(IN_OPERAND); return STRING; }
  {DOUBLE_QUOTED_STRING}   { yybegin(IN_OPERAND); return STRING; }
  {UNQUOTED_STRING}        { yybegin(IN_OPERAND); return STRING; }
}

// Instructions must switch to:
// - AFTER_INSTRUCTION (beginDataSized()) - if M68kDataSized, must have '{DATA_SIZE}?' suffix
// - IN_OPERAND - if >=1 operand
// - STRING_DIRECTIVE - if 1st operand==STRING
// - AFTER_OPERAND - if no operands
<IN_INSTRUCTION> {
  {WHITE_SPACE}+               { return WHITE_SPACE; }

  // instruction itself can be macro param inside macro block
  "\\"                         { yybegin(IN_OPERAND); return BACKSLASH; }

  // after label+whitespaces switching problems
  "="                          { yybegin(IN_OPERAND); return EQ; }

  {N}{O}{P}                    { yybegin(AFTER_OPERAND); return NOP; }
  {I}{L}{L}{E}{G}{A}{L}        { yybegin(AFTER_OPERAND); return ILLEGAL; }
  {R}{E}{S}{E}{T}              { yybegin(AFTER_OPERAND); return RESET; }
  {S}{T}{O}{P}                 { yybegin(IN_OPERAND); return STOP; }
  {T}{R}{A}{P}                 { yybegin(IN_OPERAND); return TRAP; }
  {T}{R}{A}{P}{V}              { yybegin(AFTER_OPERAND); return TRAPV; }
  {L}{I}{N}{K}                 { yybegin(IN_OPERAND); return LINK; }
  {U}{N}{L}{K}                 { yybegin(IN_OPERAND); return UNLK; }

  {M}{O}{V}{E}    {DATA_SIZE}? { beginDataSized(); return MOVE; }
  {M}{O}{V}{E}{A} {DATA_SIZE}? { beginDataSized(); return MOVEA; }
  {M}{O}{V}{E}{M} {DATA_SIZE}? { beginDataSized(); return MOVEM; }
  {M}{O}{V}{E}{P} {DATA_SIZE}? { beginDataSized(); return MOVEP; }
  {M}{O}{V}{E}{Q} {DATA_SIZE}? { beginDataSized(); return MOVEQ; }

  {T}{S}{T} {DATA_SIZE}?       { beginDataSized(); return TST; }
  {T}{A}{S} {DATA_SIZE}?       { beginDataSized(); return TAS; }
  {L}{E}{A} {DATA_SIZE}?       { beginDataSized(); return LEA; }
  {P}{E}{A} {DATA_SIZE}?       { beginDataSized(); return PEA; }
  {C}{L}{R} {DATA_SIZE}?       { beginDataSized(); return CLR; }

  {J}{M}{P} {DATA_SIZE}?       { beginDataSized(); return JMP; }
  {J}{S}{R}                    { yybegin(IN_OPERAND); return JSR; }
  {B}{S}{R} {DATA_SIZE}?       { beginDataSized(); return BSR; }

  {R}{T}{S}                    { yybegin(AFTER_OPERAND); return RTS; }
  {R}{T}{E}                    { yybegin(AFTER_OPERAND); return RTE; }
  {R}{T}{R}                    { yybegin(AFTER_OPERAND); return RTR; }


  {A}{D}{D}    {DATA_SIZE}?    { beginDataSized(); return ADD; }
  {A}{D}{D}{A} {DATA_SIZE}?    { beginDataSized(); return ADDA; }
  {A}{D}{D}{I} {DATA_SIZE}?    { beginDataSized(); return ADDI; }
  {A}{D}{D}{Q} {DATA_SIZE}?    { beginDataSized(); return ADDQ; }
  {A}{D}{D}{X} {DATA_SIZE}?    { beginDataSized(); return ADDX; }
  {S}{U}{B}    {DATA_SIZE}?    { beginDataSized(); return SUB; }
  {S}{U}{B}{A} {DATA_SIZE}?    { beginDataSized(); return SUBA; }
  {S}{U}{B}{I} {DATA_SIZE}?    { beginDataSized(); return SUBI; }
  {S}{U}{B}{Q} {DATA_SIZE}?    { beginDataSized(); return SUBQ; }
  {S}{U}{B}{X} {DATA_SIZE}?    { beginDataSized(); return SUBX; }
  {M}{U}{L}{S} {DATA_SIZE}?    { beginDataSized(); return MULS; }
  {M}{U}{L}{U} {DATA_SIZE}?    { beginDataSized(); return MULU; }
  {D}{I}{V}{S} {DATA_SIZE}?    { beginDataSized(); return DIVS; }
  {D}{I}{V}{U} {DATA_SIZE}?    { beginDataSized(); return DIVU; }

  {A}{B}{C}{D} {DATA_SIZE}?    { beginDataSized(); return ABCD; }
  {N}{B}{C}{D} {DATA_SIZE}?    { beginDataSized(); return NBCD; }
  {S}{B}{C}{D} {DATA_SIZE}?    { beginDataSized(); return SBCD; }

  {A}{N}{D}    {DATA_SIZE}?    { beginDataSized(); return AND; }
  {A}{N}{D}{I} {DATA_SIZE}?    { beginDataSized(); return ANDI; }
  {O}{R}       {DATA_SIZE}?    { beginDataSized(); return OR; }
  {O}{R}{I}    {DATA_SIZE}?    { beginDataSized(); return ORI; }
  {E}{O}{R}    {DATA_SIZE}?    { beginDataSized(); return EOR; }
  {E}{O}{R}{I} {DATA_SIZE}?    { beginDataSized(); return EORI; }

  {E}{X}{T}    {DATA_SIZE}?    { beginDataSized(); return EXT; }
  {N}{E}{G}    {DATA_SIZE}?    { beginDataSized(); return NEG; }
  {N}{E}{G}{X} {DATA_SIZE}?    { beginDataSized(); return NEGX; }
  {S}{W}{A}{P} {DATA_SIZE}?    { beginDataSized(); return SWAP; }
  {N}{O}{T}    {DATA_SIZE}?    { beginDataSized(); return NOT; }
  {C}{H}{K}    {DATA_SIZE}?    { beginDataSized(); return CHK; }
  {E}{X}{G}    {DATA_SIZE}?    { beginDataSized(); return EXG; }

  {C}{M}{P}    {DATA_SIZE}?    { beginDataSized(); return CMP; }
  {C}{M}{P}{A} {DATA_SIZE}?    { beginDataSized(); return CMPA; }
  {C}{M}{P}{I} {DATA_SIZE}?    { beginDataSized(); return CMPI; }
  {C}{M}{P}{M} {DATA_SIZE}?    { beginDataSized(); return CMPM; }

  {B}{C}{H}{G} {DATA_SIZE}?    { beginDataSized(); return BCHG; }
  {B}{C}{L}{R} {DATA_SIZE}?    { beginDataSized(); return BCLR; }
  {B}{S}{E}{T} {DATA_SIZE}?    { beginDataSized(); return BSET; }
  {B}{T}{S}{T} {DATA_SIZE}?    { beginDataSized(); return BTST; }

  {B}{R}{A} {DATA_SIZE}?       { beginDataSized(); return BRA; }
  {B}{C}{S} {DATA_SIZE}?       { beginDataSized(); return BCS; }
  {B}{L}{O} {DATA_SIZE}?       { beginDataSized(); return BLO; }
  {B}{L}{S} {DATA_SIZE}?       { beginDataSized(); return BLS; }
  {B}{E}{Q} {DATA_SIZE}?       { beginDataSized(); return BEQ; }
  {B}{N}{E} {DATA_SIZE}?       { beginDataSized(); return BNE; }
  {B}{H}{I} {DATA_SIZE}?       { beginDataSized(); return BHI; }
  {B}{C}{C} {DATA_SIZE}?       { beginDataSized(); return BCC; }
  {B}{H}{S} {DATA_SIZE}?       { beginDataSized(); return BHS; }
  {B}{P}{L} {DATA_SIZE}?       { beginDataSized(); return BPL; }
  {B}{V}{C} {DATA_SIZE}?       { beginDataSized(); return BVC; }
  {B}{L}{T} {DATA_SIZE}?       { beginDataSized(); return BLT; }
  {B}{L}{E} {DATA_SIZE}?       { beginDataSized(); return BLE; }
  {B}{G}{T} {DATA_SIZE}?       { beginDataSized(); return BGT; }
  {B}{G}{E} {DATA_SIZE}?       { beginDataSized(); return BGE; }
  {B}{M}{I} {DATA_SIZE}?       { beginDataSized(); return BMI; }
  {B}{V}{S} {DATA_SIZE}?       { beginDataSized(); return BVS; }

  {D}{B}{R}{A} {DATA_SIZE}?    { beginDataSized(); return DBRA; }
  {D}{B}{C}{S} {DATA_SIZE}?    { beginDataSized(); return DBCS; }
  {D}{B}{L}{O} {DATA_SIZE}?    { beginDataSized(); return DBLO; }
  {D}{B}{L}{S} {DATA_SIZE}?    { beginDataSized(); return DBLS; }
  {D}{B}{E}{Q} {DATA_SIZE}?    { beginDataSized(); return DBEQ; }
  {D}{B}{N}{E} {DATA_SIZE}?    { beginDataSized(); return DBNE; }
  {D}{B}{H}{I} {DATA_SIZE}?    { beginDataSized(); return DBHI; }
  {D}{B}{C}{C} {DATA_SIZE}?    { beginDataSized(); return DBCC; }
  {D}{B}{H}{S} {DATA_SIZE}?    { beginDataSized(); return DBHS; }
  {D}{B}{P}{L} {DATA_SIZE}?    { beginDataSized(); return DBPL; }
  {D}{B}{V}{C} {DATA_SIZE}?    { beginDataSized(); return DBVC; }
  {D}{B}{L}{T} {DATA_SIZE}?    { beginDataSized(); return DBLT; }
  {D}{B}{L}{E} {DATA_SIZE}?    { beginDataSized(); return DBLE; }
  {D}{B}{G}{T} {DATA_SIZE}?    { beginDataSized(); return DBGT; }
  {D}{B}{G}{E} {DATA_SIZE}?    { beginDataSized(); return DBGE; }
  {D}{B}{M}{I} {DATA_SIZE}?    { beginDataSized(); return DBMI; }
  {D}{B}{V}{S} {DATA_SIZE}?    { beginDataSized(); return DBVS; }
  {D}{B}{F}    {DATA_SIZE}?    { beginDataSized(); return DBF; }
  {D}{B}{T}    {DATA_SIZE}?    { beginDataSized(); return DBT; }

  {S}{E}{Q} {DATA_SIZE}?       { beginDataSized(); return SEQ; }
  {S}{N}{E} {DATA_SIZE}?       { beginDataSized(); return SNE; }
  {S}{P}{L} {DATA_SIZE}?       { beginDataSized(); return SPL; }
  {S}{M}{I} {DATA_SIZE}?       { beginDataSized(); return SMI; }
  {S}{V}{C} {DATA_SIZE}?       { beginDataSized(); return SVC; }
  {S}{V}{S} {DATA_SIZE}?       { beginDataSized(); return SVS; }
  {S}{T}    {DATA_SIZE}?       { beginDataSized(); return ST; }
  {S}{F}    {DATA_SIZE}?       { beginDataSized(); return SF; }
  {S}{G}{E} {DATA_SIZE}?       { beginDataSized(); return SGE; }
  {S}{G}{T} {DATA_SIZE}?       { beginDataSized(); return SGT; }
  {S}{L}{E} {DATA_SIZE}?       { beginDataSized(); return SLE; }
  {S}{L}{T} {DATA_SIZE}?       { beginDataSized(); return SLT; }
  {S}{C}{C} {DATA_SIZE}?       { beginDataSized(); return SCC; }
  {S}{H}{I} {DATA_SIZE}?       { beginDataSized(); return SHI; }
  {S}{L}{S} {DATA_SIZE}?       { beginDataSized(); return SLS; }
  {S}{C}{S} {DATA_SIZE}?       { beginDataSized(); return SCS; }
  {S}{H}{S} {DATA_SIZE}?       { beginDataSized(); return SHS; }
  {S}{L}{O} {DATA_SIZE}?       { beginDataSized(); return SLO; }

  {A}{S}{L}    {DATA_SIZE}?    { beginDataSized(); return ASL; }
  {A}{S}{R}    {DATA_SIZE}?    { beginDataSized(); return ASR; }
  {L}{S}{L}    {DATA_SIZE}?    { beginDataSized(); return LSL; }
  {L}{S}{R}    {DATA_SIZE}?    { beginDataSized(); return LSR; }
  {R}{O}{L}    {DATA_SIZE}?    { beginDataSized(); return ROL; }
  {R}{O}{R}    {DATA_SIZE}?    { beginDataSized(); return ROR; }
  {R}{O}{X}{L} {DATA_SIZE}?    { beginDataSized(); return ROXL; }
  {R}{O}{X}{R} {DATA_SIZE}?    { beginDataSized(); return ROXR; }

  {A}{D}{D}{W}{A}{T}{C}{H}     { yybegin(IN_OPERAND); return ADDWATCH; }
  {A}{L}{I}{G}{N}              { yybegin(IN_OPERAND); return ALIGN; }
  {B}{L}{K} {DATA_SIZE}?       { beginDataSized(); return BLK; }
  {B}{S}{S}                    { yybegin(AFTER_OPERAND); return BSS; }
  {B}{S}{S}_{C}                { yybegin(AFTER_OPERAND); return BSS_C; }
  {B}{S}{S}_{F}                { yybegin(AFTER_OPERAND); return BSS_F; }
  {C}{N}{O}{P}                 { yybegin(IN_OPERAND); return CNOP; }
  {C}{O}{D}{E}                 { yybegin(AFTER_OPERAND); return CODE; }
  {C}{O}{D}{E}_{C}             { yybegin(AFTER_OPERAND); return CODE_C; }
  {C}{O}{D}{E}_{F}             { yybegin(AFTER_OPERAND); return CODE_F; }
  {C}{S}{E}{G}                 { yybegin(AFTER_OPERAND); return CSEG; }
  {D}{A}{T}{A}                 { yybegin(AFTER_OPERAND); return DATA; }
  {D}{A}{T}{A}_{C}             { yybegin(AFTER_OPERAND); return DATA_C; }
  {D}{A}{T}{A}_{F}             { yybegin(AFTER_OPERAND); return DATA_F; }
  {D}{C}       {DATA_SIZE}?    { beginDataSized(); return DC; }
  {D}{C}{B}    {DATA_SIZE}?    { beginDataSized(); return DCB; }
  {D}{R}       {DATA_SIZE}?    { beginDataSized(); return DR; }
  {D}{S}       {DATA_SIZE}?    { beginDataSized(); return DS; }
  {D}{S}{E}{G} {DATA_SIZE}?    { beginDataSized(); return DSEG; }
  {E}{C}{H}{O}                 { yybegin(STRING_DIRECTIVE); return ECHO; }
  {E}{I}{N}{L}{I}{N}{E}        { yybegin(AFTER_OPERAND); return EINLINE; }
  {E}{N}{D}                    { yybegin(AFTER_OPERAND); return END; }
  {E}{N}{D}{R}                 { yybegin(AFTER_OPERAND); return ENDR; }
  {E}{Q}{U}                    { yybegin(IN_OPERAND); return EQU; }
  {E}{Q}{U}{R}                 { yybegin(IN_OPERAND); return EQUR; }
  {E}{R}{E}{M}                 { yybegin(AFTER_OPERAND); return EREM; }
  {E}{V}{E}{N}                 { yybegin(AFTER_OPERAND); return EVEN; }
  {F}{A}{I}{L}                 { yybegin(AFTER_OPERAND); return FAIL; }
  {F}{A}{R}                    { yybegin(AFTER_OPERAND); return FAR; }
  {I}{D}{N}{T}                 { yybegin(STRING_DIRECTIVE); return IDNT; }
  {I}{N}{C}{B}{I}{N}           { yybegin(STRING_DIRECTIVE); return INCBIN; }
  {I}{N}{C}{D}{I}{R}           { yybegin(STRING_DIRECTIVE); return INCDIR; }
  {I}{N}{C}{L}{U}{D}{E}        { yybegin(STRING_DIRECTIVE); return INCLUDE; }
  {I}{N}{I}{T}{N}{E}{A}{R}     { yybegin(AFTER_OPERAND); return INITNEAR; }
  {I}{N}{L}{I}{N}{E}           { yybegin(AFTER_OPERAND); return INLINE; }
  {J}{U}{M}{P}{E}{R}{R}        { yybegin(IN_OPERAND); return JUMPERR; }
  {J}{U}{M}{P}{P}{T}{R}        { yybegin(IN_OPERAND); return JUMPPTR; }
  {L}{I}{S}{T}                 { yybegin(AFTER_OPERAND); return LIST; }
  {L}{L}{E}{N}                 { yybegin(IN_OPERAND); return LLEN; }
  {L}{O}{A}{D}                 { yybegin(IN_OPERAND); return LOAD; }
  {N}{E}{A}{R}                 { yybegin(IN_OPERAND); return NEAR; }
  {N}{E}{A}{R}{WHITE_SPACE}{C}{O}{D}{E} { yybegin(AFTER_OPERAND); return NEAR_CODE; }
  {N}{O}{L}{I}{S}{T}           { yybegin(AFTER_OPERAND); return NOLIST; }
  {N}{O}{P}{A}{G}{E}           { yybegin(AFTER_OPERAND); return NOPAGE; }
  {O}{D}{D}                    { yybegin(AFTER_OPERAND); return ODD; }
  {O}{P}{T}                    { yybegin(IN_OPERAND); return OPT; }
  {O}{R}{G}                    { yybegin(IN_OPERAND); return ORG; }
  {P}{A}{G}{E}                 { yybegin(AFTER_OPERAND); return PAGE; }
  {P}{L}{E}{N}                 { yybegin(IN_OPERAND); return PLEN; }
  {P}{O}{P}{S}{E}{C}{T}{I}{O}{N}    { yybegin(AFTER_OPERAND); return POPSECTION; }
  {P}{R}{I}{N}{T}{T}           { yybegin(STRING_DIRECTIVE); return PRINTT; }
  {P}{R}{I}{N}{T}{V}           { yybegin(IN_OPERAND); return PRINTV; }
  {P}{U}{S}{H}{S}{E}{C}{T}{I}{O}{N} { yybegin(AFTER_OPERAND); return PUSHSECTION; }
  {R}{E}{G}                    { yybegin(IN_OPERAND); return REG; }
  {R}{E}{M}                    { yybegin(AFTER_OPERAND); return REM; }
  {R}{E}{P}{T}                 { yybegin(IN_OPERAND); return REPT; }
  {R}{S} {DATA_SIZE}?          { beginDataSized(); return RS; }
  {R}{S}{R}{E}{S}{E}{T}        { yybegin(AFTER_OPERAND); return RSRESET; }
  {R}{S}{S}{E}{T}              { yybegin(IN_OPERAND); return RSSET; }
  {S}{E}{C}{T}{I}{O}{N}        { yybegin(IN_OPERAND); return SECTION; }
  {S}{E}{T}                    { yybegin(IN_OPERAND); return SET; }
  {S}{P}{C}                    { yybegin(IN_OPERAND); return SPC; }
  {T}{E}{X}{T}                 { yybegin(AFTER_OPERAND); return TEXT; }
  {T}{T}{L}                    { yybegin(STRING_DIRECTIVE); return TTL; }
  {X}{D}{E}{F}                 { yybegin(IN_OPERAND); return XDEF; }
  {X}{R}{E}{F}                 { yybegin(IN_OPERAND); return XREF; }

  {M}{A}{C}{R}{O}              { yybegin(AFTER_OPERAND); return MACRO; }
  {E}{N}{D}{M}                 { yybegin(AFTER_OPERAND); return ENDM; }
  {M}{E}{X}{I}{T}              { yybegin(AFTER_OPERAND); return MEXIT; }

  {I}{F}                       { yybegin(IN_OPERAND); return IF; }
  {I}{F}{B}                    { yybegin(IN_OPERAND); return IFB; }
  {I}{F}{N}{B}                 { yybegin(IN_OPERAND); return IFNB; }
  {I}{F}{C}                    { yybegin(IN_OPERAND); return IFC; }
  {I}{F}{N}{C}                 { yybegin(IN_OPERAND); return IFNC; }
  {I}{F}{D}                    { yybegin(IN_OPERAND); return IFD; }
  {I}{F}{E}{Q}                 { yybegin(IN_OPERAND); return IFEQ; }
  {I}{F}{G}{E}                 { yybegin(IN_OPERAND); return IFGE; }
  {I}{F}{P}{L}                 { yybegin(IN_OPERAND); return IFPL; }
  {I}{F}{G}{T}                 { yybegin(IN_OPERAND); return IFGT; }
  {I}{F}{M}{A}{C}{R}{O}{D}     { yybegin(IN_OPERAND); return IFMACROD; }
  {I}{F}{M}{A}{C}{R}{O}{N}{D}  { yybegin(IN_OPERAND); return IFMACROND; }
  {I}{F}{N}{D}                 { yybegin(IN_OPERAND); return IFND; }
  {I}{F}{N}{E}                 { yybegin(IN_OPERAND); return IFNE; }
  {I}{F}{L}{E}                 { yybegin(IN_OPERAND); return IFLE; }
  {I}{F}{L}{T}                 { yybegin(IN_OPERAND); return IFLT; }
  {I}{F}{M}{I}                 { yybegin(IN_OPERAND); return IFMI; }
  {E}{N}{D}{C}                 { yybegin(AFTER_OPERAND); return ENDC; }
  {E}{N}{D}{I}{F}              { yybegin(AFTER_OPERAND); return ENDIF; }
  {E}{L}{S}{E}                 { yybegin(AFTER_OPERAND); return ELSE; }
  {E}{L}{S}{E}{I}{F}           { yybegin(AFTER_OPERAND); return ELSEIF; }

  // anything else is macro name
  {ID}                         { yybegin(IN_OPERAND); return MACRO_CALL_ID; }

  {COMMENT}                    { return COMMENT; }
}

[^] { return BAD_CHARACTER; }
