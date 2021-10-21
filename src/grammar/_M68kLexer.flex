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

  /**
   * Whether given {@code '*'} is "current PC" symbol instead of {@link MUL}.
   */
  private boolean isCurrentPcSymbol(){
    if (afterSpaceOrComma()) return true;
    
    char previousChar = charAt(-1);
    return previousChar == '-' || previousChar == '+';
  }

  /**
   * Push back DATA_SIZE token.
   */
  private void pushbackDataSize() {
    yypushback(2);
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

COMMENT=;.*|\*.*
EOL_COMMENT=;.*

DECNUMBER=[\d]+
HEXNUMBER=\$\p{XDigit}+
OCTNUMBER=@[0-7]+
BINNUMBER=%[0|1]+

SINGLE_QUOTED_STRING='([^\\'\r\n]|\\[^\r\n])*'?
DOUBLE_QUOTED_STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?
UNQUOTED_STRING=([^\\\r\n\ \t\f'\"])+

LABEL=[_\d[\\@]]*[\p{Alpha}] [\p{Alpha}\d[.]_[\\@]]*  // without "." first char
ID=[.]?{LABEL}[\$]?

MACRO_NAME=[_\d]*[\p{Alpha}] [\p{Alpha}\d_]*

DATA_SIZE=[.][[sS]|[bB]|[wW]|[lL]|[\\0]]

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

%state MACRO_DECLARATION
%state AFTER_LABEL
%state IN_INSTRUCTION
%state AFTER_INSTRUCTION
%state STRING_DIRECTIVE
%state IN_OPERAND
%state MACRO_PARAMETER
%state AFTER_OPERAND

%%
<YYINITIAL, MACRO_DECLARATION, AFTER_LABEL, IN_INSTRUCTION, AFTER_INSTRUCTION, STRING_DIRECTIVE, IN_OPERAND, MACRO_PARAMETER, AFTER_OPERAND> {
  {CRLF}         { operandSpaceCount = 0; yybegin(YYINITIAL); return LINEFEED; }
}

<YYINITIAL> {
  "."            { operandSpaceCount = 0; return DOT; }

  {LABEL}        { operandSpaceCount = 0; yybegin(AFTER_LABEL); return ID; }
  {COMMENT}      { return COMMENT; }

  // whitespace followed NOT by instruction variants
  {WHITE_SPACE}+ / {ID} ":"          { return WHITE_SPACE; }
  {WHITE_SPACE}+ / {M}{A}{C}{R}{O} {WHITE_SPACE}+  { yybegin(MACRO_DECLARATION); return WHITE_SPACE; }

  {WHITE_SPACE}+ { operandSpaceCount = 0; yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}

<MACRO_DECLARATION> {
  {WHITE_SPACE}+  { return WHITE_SPACE; }
  {M}{A}{C}{R}{O} { return MACRO; }
  {MACRO_NAME}    { operandSpaceCount = 0; yybegin(AFTER_LABEL); return ID; }
}

<AFTER_LABEL> {
  {COMMENT}       { return COMMENT; }

  "$"             { return DOLLAR; }
  ":"             { yybegin(IN_INSTRUCTION); return COLON; }
  "="             { yybegin(IN_OPERAND); return EQ_DIRECTIVE; } // EQ in <IN_INSTRUCTION>

  {M}{A}{C}{R}{O} { yybegin(AFTER_OPERAND); return MACRO; }
  {E}{Q}{U}       { yybegin(IN_OPERAND); return EQU; }
  {E}{Q}{U}{R}    { yybegin(IN_OPERAND); return EQUR; }

  // whitespace followed NOT by instruction variants
  {WHITE_SPACE}+ / ({COMMENT} | {E}{Q}{U} | {E}{Q}{U}{R} | {M}{A}{C}{R}{O})  { return WHITE_SPACE; }

  {WHITE_SPACE}+  { yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}


// after M68kDataSized instruction: data_size || WHITE_SPACE + operand
<AFTER_INSTRUCTION> {
  {WHITE_SPACE}+ { operandSpaceCount = 1; yybegin(IN_OPERAND); return WHITE_SPACE; }

  "." {S}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_S; }
  "." {B}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_B; }
  "." {W}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_W; }
  "." {L}        { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_L; }
  ".\\0"         { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_W; } // fake for macro parameter
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

  {EOL_COMMENT}            { return COMMENT; }

  {S}{P}                   { return SP; }
  {S}{P} {DATA_SIZE}       { pushbackDataSize(); return SP; }
  {S}{S}{P}                { return SSP; }
  {U}{S}{P}                { return USP; }
  {P}{C}                   { return PC; }
  {S}{R}                   { return SR; }
  {C}{C}{R}                { return CCR; }
  {D}{F}{C}                { return DFC; }
  {S}{F}{C}                { return SFC; }
  {V}{B}{R}                { return VBR; }

  {D}[0-7]                 { return DATA_REGISTER; }
  {D}[0-7] {DATA_SIZE}     { pushbackDataSize(); return DATA_REGISTER; }
  {A}[0-7]                 { return ADDRESS_REGISTER; }
  {A}[0-7] {DATA_SIZE}     { pushbackDataSize(); return ADDRESS_REGISTER; }

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
  "*"  { if (isCurrentPcSymbol()) { return ID; } return MUL; }
  "//" { return SLASH_SLASH; }
  "/"  { return DIV; }
  "^"  { return POW; }
  "#"  { return HASH; }
  "~"  { return TILDE; }
  "("  { return L_PAREN; }
  ")"  { return R_PAREN; }
  "["  { return L_BRACKET; }
  "]"  { return R_BRACKET; }
  "!=" { return EXCLAMATION_EQ; }
  "!"  { return EXCLAMATION; }
  "%"  { return PERCENT; }
  "&&" { return AMPERSAND_AMPERSAND; }
  "&"  { return AMPERSAND; }
  "\\" { yybegin(MACRO_PARAMETER); return BACKSLASH; }
  "||" { return PIPE_PIPE; }
  "|"  { return PIPE; }
  "<>" { return LT_GT; }
  "<<" { return LT_LT; }
  "<=" { return LT_EQ; }
  "<"  { return LT; }
  ">>" { return GT_GT; }
  ">=" { return GT_EQ; }
  ">"  { return GT; }
  "==" { return EQ_EQ; }
  "="  { return EQ; }

  {DECNUMBER}                 { return DEC_NUMBER; }
  {HEXNUMBER}                 { return HEX_NUMBER; }
  {OCTNUMBER}                 { return OCT_NUMBER; }
  {BINNUMBER}                 { return BIN_NUMBER; }

  {SINGLE_QUOTED_STRING}      { return STRING; }
  {DOUBLE_QUOTED_STRING}      { return STRING; }
}

// '\0' or '\a'
<MACRO_PARAMETER> {
  \d    { yybegin(IN_OPERAND); return DEC_NUMBER; }
  [a-z] { yybegin(IN_OPERAND); return ID; }
}

// 1st operand==STRING, optionally followed by IN_OPERAND
<STRING_DIRECTIVE> {
  // after 2nd WHITE_SPACE -> AFTER_OPERAND for automatic comment
  {WHITE_SPACE}+           { operandSpaceCount++; return WHITE_SPACE; }

  {COMMENT}                { return COMMENT; }
  {EOL_COMMENT}            { return COMMENT; }

  {SINGLE_QUOTED_STRING}   { yybegin(IN_OPERAND); return STRING; }
  {DOUBLE_QUOTED_STRING}   { yybegin(IN_OPERAND); return STRING; }
  {UNQUOTED_STRING}        { yybegin(IN_OPERAND); return STRING; }
}

// Instructions must switch to:
// - AFTER_INSTRUCTION - if M68kDataSized, must have '/ {DATA_SIZE}?' lookahead suffix
// - IN_OPERAND - if >=1 operand
// - STRING_DIRECTIVE - if 1st operand==STRING
// - AFTER_OPERAND - if no operands
<IN_INSTRUCTION> {
  {WHITE_SPACE}+               { return WHITE_SPACE; }

  // instruction itself can be macro param inside macro block
  "\\"                         { yybegin(IN_OPERAND); return BACKSLASH; }

  {N}{O}{P}                    { yybegin(AFTER_OPERAND); return NOP; }
  {I}{L}{L}{E}{G}{A}{L}        { yybegin(AFTER_OPERAND); return ILLEGAL; }
  {R}{E}{S}{E}{T}              { yybegin(AFTER_OPERAND); return RESET; }
  {S}{T}{O}{P}                 { yybegin(IN_OPERAND); return STOP; }
  {T}{R}{A}{P}                 { yybegin(IN_OPERAND); return TRAP; }
  {B}{K}{P}{T}                 { yybegin(IN_OPERAND); return BKPT; }
  {T}{R}{A}{P}{V}              { yybegin(AFTER_OPERAND); return TRAPV; }
  {L}{I}{N}{K}  / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return LINK; }
  {U}{N}{L}{K}                 { yybegin(IN_OPERAND); return UNLK; }

  {M}{O}{V}{E}    / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return MOVE; }
  {M}{O}{V}{E}{A} / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return MOVEA; }
  {M}{O}{V}{E}{C} / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return MOVEC; }
  {M}{O}{V}{E}{M} / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return MOVEM; }
  {M}{O}{V}{E}{P} / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return MOVEP; }
  {M}{O}{V}{E}{Q} / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return MOVEQ; }
  {M}{O}{V}{E}{S} / {DATA_SIZE}? { yybegin(AFTER_INSTRUCTION); return MOVES; }

  {T}{S}{T} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return TST; }
  {T}{A}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return TAS; }
  {L}{E}{A} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return LEA; }
  {P}{E}{A} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return PEA; }
  {C}{L}{R} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return CLR; }

  {J}{M}{P} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return JMP; }
  {J}{S}{R}                    { yybegin(IN_OPERAND); return JSR; }
  {B}{S}{R} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BSR; }

  {R}{T}{S}                    { yybegin(AFTER_OPERAND); return RTS; }
  {R}{T}{E}                    { yybegin(AFTER_OPERAND); return RTE; }
  {R}{T}{R}                    { yybegin(AFTER_OPERAND); return RTR; }


  {A}{D}{D}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ADD; }
  {A}{D}{D}{A} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ADDA; }
  {A}{D}{D}{I} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ADDI; }
  {A}{D}{D}{Q} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ADDQ; }
  {A}{D}{D}{X} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ADDX; }
  {S}{U}{B}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SUB; }
  {S}{U}{B}{A} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SUBA; }
  {S}{U}{B}{I} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SUBI; }
  {S}{U}{B}{Q} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SUBQ; }
  {S}{U}{B}{X} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SUBX; }
  {M}{U}{L}{S} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return MULS; }
  {M}{U}{L}{U} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return MULU; }
  {D}{I}{V}{S} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DIVS; }
  {D}{I}{V}{U} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DIVU; }

  {A}{B}{C}{D} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ABCD; }
  {N}{B}{C}{D} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return NBCD; }
  {S}{B}{C}{D} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SBCD; }

  {A}{N}{D}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return AND; }
  {A}{N}{D}{I} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ANDI; }
  {O}{R}       / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return OR; }
  {O}{R}{I}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ORI; }
  {E}{O}{R}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return EOR; }
  {E}{O}{R}{I} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return EORI; }

  {E}{X}{T}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return EXT; }
  {N}{E}{G}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return NEG; }
  {N}{E}{G}{X} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return NEGX; }
  {S}{W}{A}{P} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SWAP; }
  {N}{O}{T}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return NOT; }
  {C}{H}{K}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return CHK; }
  {E}{X}{G}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return EXG; }

  {C}{M}{P}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return CMP; }
  {C}{M}{P}{A} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return CMPA; }
  {C}{M}{P}{I} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return CMPI; }
  {C}{M}{P}{M} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return CMPM; }

  {B}{C}{H}{G} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return BCHG; }
  {B}{C}{L}{R} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return BCLR; }
  {B}{S}{E}{T} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return BSET; }
  {B}{T}{S}{T} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return BTST; }

  {B}{R}{A} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BRA; }
  {B}{C}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BCS; }
  {B}{L}{O} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BLO; }
  {B}{L}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BLS; }
  {B}{E}{Q} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BEQ; }
  {B}{N}{E} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BNE; }
  {B}{H}{I} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BHI; }
  {B}{C}{C} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BCC; }
  {B}{H}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BHS; }
  {B}{P}{L} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BPL; }
  {B}{V}{C} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BVC; }
  {B}{L}{T} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BLT; }
  {B}{L}{E} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BLE; }
  {B}{G}{T} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BGT; }
  {B}{G}{E} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BGE; }
  {B}{M}{I} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BMI; }
  {B}{V}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BVS; }

  {D}{B}{R}{A} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBRA; }
  {D}{B}{C}{S} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBCS; }
  {D}{B}{L}{O} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBLO; }
  {D}{B}{L}{S} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBLS; }
  {D}{B}{E}{Q} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBEQ; }
  {D}{B}{N}{E} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBNE; }
  {D}{B}{H}{I} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBHI; }
  {D}{B}{C}{C} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBCC; }
  {D}{B}{H}{S} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBHS; }
  {D}{B}{P}{L} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBPL; }
  {D}{B}{V}{C} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBVC; }
  {D}{B}{L}{T} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBLT; }
  {D}{B}{L}{E} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBLE; }
  {D}{B}{G}{T} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBGT; }
  {D}{B}{G}{E} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBGE; }
  {D}{B}{M}{I} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBMI; }
  {D}{B}{V}{S} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBVS; }
  {D}{B}{F}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBF; }
  {D}{B}{T}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DBT; }

  {S}{E}{Q} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SEQ; }
  {S}{N}{E} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SNE; }
  {S}{P}{L} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SPL; }
  {S}{M}{I} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SMI; }
  {S}{V}{C} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SVC; }
  {S}{V}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SVS; }
  {S}{T}    / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return ST; }
  {S}{F}    / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SF; }
  {S}{G}{E} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SGE; }
  {S}{G}{T} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SGT; }
  {S}{L}{E} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SLE; }
  {S}{L}{T} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SLT; }
  {S}{C}{C} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SCC; }
  {S}{H}{I} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SHI; }
  {S}{L}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SLS; }
  {S}{C}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SCS; }
  {S}{H}{S} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SHS; }
  {S}{L}{O} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return SLO; }

  {A}{S}{L}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ASL; }
  {A}{S}{R}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ASR; }
  {L}{S}{L}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return LSL; }
  {L}{S}{R}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return LSR; }
  {R}{O}{L}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ROL; }
  {R}{O}{R}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ROR; }
  {R}{O}{X}{L} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ROXL; }
  {R}{O}{X}{R} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return ROXR; }

  {A}{D}{D}{W}{A}{T}{C}{H}     { yybegin(IN_OPERAND); return ADDWATCH; }
  {A}{L}{I}{G}{N}              { yybegin(IN_OPERAND); return ALIGN; }
  {A}{U}{T}{O}                 { yybegin(AFTER_OPERAND); return AUTO; }
  {B}{L}{K} / {DATA_SIZE}?     { yybegin(AFTER_INSTRUCTION); return BLK; }
  {B}{S}{S}                    { yybegin(AFTER_OPERAND); return BSS; }
  {B}{S}{S}_{C}                { yybegin(AFTER_OPERAND); return BSS_C; }
  {B}{S}{S}_{F}                { yybegin(AFTER_OPERAND); return BSS_F; }
  {C}{L}{R}{F}{O}              { yybegin(IN_OPERAND); return CLRFO; }
  {C}{L}{R}{S}{O}              { yybegin(IN_OPERAND); return CLRSO; }
  {C}{N}{O}{P}                 { yybegin(IN_OPERAND); return CNOP; }
  {C}{O}{D}{E}                 { yybegin(AFTER_OPERAND); return CODE; }
  {C}{O}{D}{E}_{C}             { yybegin(AFTER_OPERAND); return CODE_C; }
  {C}{O}{D}{E}_{F}             { yybegin(AFTER_OPERAND); return CODE_F; }
  {C}{S}{E}{G}                 { yybegin(AFTER_OPERAND); return CSEG; }
  {D}{A}{T}{A}                 { yybegin(AFTER_OPERAND); return DATA; }
  {D}{A}{T}{A}_{C}             { yybegin(AFTER_OPERAND); return DATA_C; }
  {D}{A}{T}{A}_{F}             { yybegin(AFTER_OPERAND); return DATA_F; }
  {D}{C}       / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DC; }
  {D}{C}{B}    / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DCB; }
  {D}{R}       / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DR; }
  {D}{S}       / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DS; }
  {D}{S}{E}{G} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DSEG; }
  {D}{X}       / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return DX; }
  {E}{C}{H}{O}                 { yybegin(STRING_DIRECTIVE); return ECHO; }
  {E}{I}{N}{L}{I}{N}{E}        { yybegin(AFTER_OPERAND); return EINLINE; }
  {E}{N}{D}                    { yybegin(AFTER_OPERAND); return END; }
  {E}{N}{D}{R}                 { yybegin(AFTER_OPERAND); return ENDR; }
  {E}{R}{E}{M}                 { yybegin(AFTER_OPERAND); return EREM; }
  {E}{V}{E}{N}                 { yybegin(AFTER_OPERAND); return EVEN; }
  {F}{A}{I}{L}                 { yybegin(AFTER_OPERAND); return FAIL; }
  {F}{A}{R}                    { yybegin(AFTER_OPERAND); return FAR; }
  {F}{O}       / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return FO; }
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
  {M}{S}{O}{U}{R}{C}{E}        { yybegin(IN_OPERAND); return MSOURCE; }
  {M}{A}{S}{K}2                { yybegin(AFTER_OPERAND); return MASK2; }
  {N}{E}{A}{R}                 { yybegin(IN_OPERAND); return NEAR; }
  {N}{E}{A}{R}{WHITE_SPACE}{C}{O}{D}{E} { yybegin(AFTER_OPERAND); return NEAR_CODE; }
  {N}{O}{L}{I}{S}{T}           { yybegin(AFTER_OPERAND); return NOLIST; }
  {N}{O}{P}{A}{G}{E}           { yybegin(AFTER_OPERAND); return NOPAGE; }
  {O}{D}{D}                    { yybegin(AFTER_OPERAND); return ODD; }
  {O}{F}{F}{S}{E}{T}           { yybegin(IN_OPERAND); return OFFSET; }
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
  {R}{S} / {DATA_SIZE}?        { yybegin(AFTER_INSTRUCTION); return RS; }
  {R}{S}{R}{E}{S}{E}{T}        { yybegin(AFTER_OPERAND); return RSRESET; }
  {R}{S}{S}{E}{T}              { yybegin(IN_OPERAND); return RSSET; }
  {S}{E}{C}{T}{I}{O}{N}        { yybegin(IN_OPERAND); return SECTION; }
  {S}{E}{T}                    { yybegin(IN_OPERAND); return SET; }
  {S}{E}{T}{F}{O}              { yybegin(IN_OPERAND); return SETFO; }
  {S}{E}{T}{S}{O}              { yybegin(IN_OPERAND); return SETSO; }
  {S}{P}{C}                    { yybegin(IN_OPERAND); return SPC; }
  {S}{O}       / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return SO; }
  {T}{E}{X}{T}                 { yybegin(AFTER_OPERAND); return TEXT; }
  {T}{T}{L}                    { yybegin(STRING_DIRECTIVE); return TTL; }
  {X}{D}{E}{F}                 { yybegin(IN_OPERAND); return XDEF; }
  {X}{R}{E}{F}                 { yybegin(IN_OPERAND); return XREF; }

  {E}{N}{D}{M}                 { yybegin(AFTER_OPERAND); return ENDM; }
  {M}{E}{X}{I}{T}              { yybegin(AFTER_OPERAND); return MEXIT; }

  {I}{F}                       { yybegin(IN_OPERAND); return IF; }
  {I}{F}1                      { yybegin(IN_OPERAND); return IF1; }
  {I}{F}2                      { yybegin(IN_OPERAND); return IF2; }
  {I}{F}{B}                    { yybegin(IN_OPERAND); return IFB; }
  {I}{F}{N}{B}                 { yybegin(IN_OPERAND); return IFNB; }
  {I}{F}{C}                    { yybegin(IN_OPERAND); return IFC; }
  {I}{F}{N}{C}                 { yybegin(IN_OPERAND); return IFNC; }
  {I}{F}{D}                    { yybegin(IN_OPERAND); return IFD; }
  {I}{F}{E}{Q}                 { yybegin(IN_OPERAND); return IFEQ; }
  {I}{F}{G}{E}                 { yybegin(IN_OPERAND); return IFGE; }
  {I}{F}{P}1                   { yybegin(IN_OPERAND); return IFP1; }
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

  {M}{C}68000                  { yybegin(AFTER_OPERAND); return MC68000; }
  {M}{C}68010                  { yybegin(AFTER_OPERAND); return MC68010; }
  {M}{C}68020                  { yybegin(AFTER_OPERAND); return MC68020; }
  {M}{C}68030                  { yybegin(AFTER_OPERAND); return MC68030; }
  {M}{C}68040                  { yybegin(AFTER_OPERAND); return MC68040; }
  {M}{C}68060                  { yybegin(AFTER_OPERAND); return MC68060; }
  {A}{C}68080                  { yybegin(AFTER_OPERAND); return AC68080; }
  {M}{A}{C}{H}{I}{N}{E}        { yybegin(IN_OPERAND); return MACHINE; }

  // after 'label:', duplicated from <AFTER_LABEL>
  {M}{A}{C}{R}{O}              { yybegin(AFTER_OPERAND); return MACRO; }
  {E}{Q}{U}                    { yybegin(IN_OPERAND); return EQU; }
  {E}{Q}{U}{R}                 { yybegin(IN_OPERAND); return EQUR; }
  "="                          { yybegin(IN_OPERAND); return EQ_DIRECTIVE; }

  // anything else is macro name
  {MACRO_NAME} / {DATA_SIZE}?  { yybegin(AFTER_INSTRUCTION); return MACRO_CALL_ID; }

  {COMMENT}                    { return COMMENT; }
}

[^] { return BAD_CHARACTER; }
