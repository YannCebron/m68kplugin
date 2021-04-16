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

  private char previousChar() {
    int loc = getTokenStart() - 1;
    return 0 <= loc && loc < zzBuffer.length() ? zzBuffer.charAt(loc) : (char) -1;
  }

  private boolean afterSpaceOrComma(){
    char previousChar = previousChar();
    return Character.isSpaceChar(previousChar) || previousChar == ',';
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

DECNUMBER=[0-9]+
HEXNUMBER=\$\p{XDigit}+
OCTNUMBER=@[0-7]+
BINNUMBER=%[0|1]+

SINGLE_QUOTED_STRING='([^\\'\r\n]|\\[^\r\n])*'?
DOUBLE_QUOTED_STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?
UNQUOTED_STRING=([^\\\r\n\ \t\f'\"])+

ID=[.]?[_]*[:digit:]*[a-zA-Z][[a-zA-Z][:digit:]_]*
LABEL=[_]*[:digit:]*[a-zA-Z][[a-zA-Z][:digit:]_]*  // without "." first char

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


<IN_OPERAND> {
  // after 2nd WHITE_SPACE -> AFTER_OPERAND for automatic comment
  {WHITE_SPACE}+           { if (operandSpaceCount++ == 1) { yybegin(AFTER_OPERAND); } return WHITE_SPACE; }

  {WHITE_SPACE}+ {COMMENT} { return COMMENT; }
  {EOL_COMMENT}            { return COMMENT; }

  {S}{P}     { return SP; }
  {S}{S}{P}  { return SSP; }
  {U}{S}{P}  { return USP; }
  {P}{C}     { return PC; }
  {S}{R}     { return SR; }
  {C}{C}{R}  { return CCR; }
  {D}[0-7]   { return DATA_REGISTER; }
  {A}[0-7]   { return ADDRESS_REGISTER; }

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
// - AFTER_INSTRUCTION - if M68kDataSized
// - IN_OPERAND - if >=1 operand
// - STRING_DIRECTIVE - if 1st operand==STRING
// - AFTER_OPERAND - if no operands
<IN_INSTRUCTION> {
  {WHITE_SPACE}+              { return WHITE_SPACE; }

  // instruction itself can be macro param inside macro block
  "\\"                        { yybegin(IN_OPERAND); return BACKSLASH; }

  // after label+whitespaces switching problems
  "="                         { yybegin(IN_OPERAND); return EQ; }

  {N}{O}{P}                   { yybegin(AFTER_OPERAND); return NOP; }
  {I}{L}{L}{E}{G}{A}{L}       { yybegin(AFTER_OPERAND); return ILLEGAL; }
  {R}{E}{S}{E}{T}             { yybegin(AFTER_OPERAND); return RESET; }
  {S}{T}{O}{P}                { yybegin(IN_OPERAND); return STOP; }
  {T}{R}{A}{P}                { yybegin(IN_OPERAND); return TRAP; }
  {T}{R}{A}{P}{V}             { yybegin(AFTER_OPERAND); return TRAPV; }
  {L}{I}{N}{K}                { yybegin(IN_OPERAND); return LINK; }
  {U}{N}{L}{K}                { yybegin(IN_OPERAND); return UNLK; }

  {M}{O}{V}{E}                { yybegin(AFTER_INSTRUCTION); return MOVE; }
  {M}{O}{V}{E}{A}             { yybegin(AFTER_INSTRUCTION); return MOVEA; }
  {M}{O}{V}{E}{M}             { yybegin(AFTER_INSTRUCTION); return MOVEM; }
  {M}{O}{V}{E}{P}             { yybegin(AFTER_INSTRUCTION); return MOVEP; }
  {M}{O}{V}{E}{Q}             { yybegin(AFTER_INSTRUCTION); return MOVEQ; }

  {T}{S}{T}                   { yybegin(AFTER_INSTRUCTION); return TST; }
  {T}{A}{S}                   { yybegin(AFTER_INSTRUCTION); return TAS; }
  {L}{E}{A}                   { yybegin(AFTER_INSTRUCTION); return LEA; }
  {P}{E}{A}                   { yybegin(AFTER_INSTRUCTION); return PEA; }
  {C}{L}{R}                   { yybegin(AFTER_INSTRUCTION); return CLR; }

  {J}{M}{P}                   { yybegin(AFTER_INSTRUCTION); return JMP; }
  {J}{S}{R}                   { yybegin(IN_OPERAND); return JSR; }
  {B}{S}{R}                   { yybegin(AFTER_INSTRUCTION); return BSR; }

  {R}{T}{S}                   { yybegin(AFTER_OPERAND); return RTS; }
  {R}{T}{E}                   { yybegin(AFTER_OPERAND); return RTE; }
  {R}{T}{R}                   { yybegin(AFTER_OPERAND); return RTR; }


  {A}{D}{D}                   { yybegin(AFTER_INSTRUCTION); return ADD; }
  {A}{D}{D}{A}                { yybegin(AFTER_INSTRUCTION); return ADDA; }
  {A}{D}{D}{I}                { yybegin(AFTER_INSTRUCTION); return ADDI; }
  {A}{D}{D}{Q}                { yybegin(AFTER_INSTRUCTION); return ADDQ; }
  {A}{D}{D}{X}                { yybegin(AFTER_INSTRUCTION); return ADDX; }
  {S}{U}{B}                   { yybegin(AFTER_INSTRUCTION); return SUB; }
  {S}{U}{B}{A}                { yybegin(AFTER_INSTRUCTION); return SUBA; }
  {S}{U}{B}{I}                { yybegin(AFTER_INSTRUCTION); return SUBI; }
  {S}{U}{B}{Q}                { yybegin(AFTER_INSTRUCTION); return SUBQ; }
  {S}{U}{B}{X}                { yybegin(AFTER_INSTRUCTION); return SUBX; }
  {M}{U}{L}{S}                { yybegin(AFTER_INSTRUCTION); return MULS; }
  {M}{U}{L}{U}                { yybegin(AFTER_INSTRUCTION); return MULU; }
  {D}{I}{V}{S}                { yybegin(AFTER_INSTRUCTION); return DIVS; }
  {D}{I}{V}{U}                { yybegin(AFTER_INSTRUCTION); return DIVU; }

  {A}{B}{C}{D}                { yybegin(AFTER_INSTRUCTION); return ABCD; }
  {N}{B}{C}{D}                { yybegin(AFTER_INSTRUCTION); return NBCD; }
  {S}{B}{C}{D}                { yybegin(AFTER_INSTRUCTION); return SBCD; }

  {A}{N}{D}                   { yybegin(AFTER_INSTRUCTION); return AND; }
  {A}{N}{D}{I}                { yybegin(AFTER_INSTRUCTION); return ANDI; }
  {O}{R}                      { yybegin(AFTER_INSTRUCTION); return OR; }
  {O}{R}{I}                   { yybegin(AFTER_INSTRUCTION); return ORI; }
  {E}{O}{R}                   { yybegin(AFTER_INSTRUCTION); return EOR; }
  {E}{O}{R}{I}                { yybegin(AFTER_INSTRUCTION); return EORI; }

  {E}{X}{T}                   { yybegin(AFTER_INSTRUCTION); return EXT; }
  {N}{E}{G}                   { yybegin(AFTER_INSTRUCTION); return NEG; }
  {N}{E}{G}{X}                { yybegin(AFTER_INSTRUCTION); return NEGX; }
  {S}{W}{A}{P}                { yybegin(AFTER_INSTRUCTION); return SWAP; }
  {N}{O}{T}                   { yybegin(AFTER_INSTRUCTION); return NOT; }
  {C}{H}{K}                   { yybegin(AFTER_INSTRUCTION); return CHK; }
  {E}{X}{G}                   { yybegin(AFTER_INSTRUCTION); return EXG; }

  {C}{M}{P}                   { yybegin(AFTER_INSTRUCTION); return CMP; }
  {C}{M}{P}{A}                { yybegin(AFTER_INSTRUCTION); return CMPA; }
  {C}{M}{P}{I}                { yybegin(AFTER_INSTRUCTION); return CMPI; }
  {C}{M}{P}{M}                { yybegin(AFTER_INSTRUCTION); return CMPM; }

  {B}{C}{H}{G}                { yybegin(AFTER_INSTRUCTION); return BCHG; }
  {B}{C}{L}{R}                { yybegin(AFTER_INSTRUCTION); return BCLR; }
  {B}{S}{E}{T}                { yybegin(AFTER_INSTRUCTION); return BSET; }
  {B}{T}{S}{T}                { yybegin(AFTER_INSTRUCTION); return BTST; }

  {B}{R}{A}                   { yybegin(AFTER_INSTRUCTION); return BRA; }
  {B}{C}{S}                   { yybegin(AFTER_INSTRUCTION); return BCS; }
  {B}{L}{O}                   { yybegin(AFTER_INSTRUCTION); return BLO; }
  {B}{L}{S}                   { yybegin(AFTER_INSTRUCTION); return BLS; }
  {B}{E}{Q}                   { yybegin(AFTER_INSTRUCTION); return BEQ; }
  {B}{N}{E}                   { yybegin(AFTER_INSTRUCTION); return BNE; }
  {B}{H}{I}                   { yybegin(AFTER_INSTRUCTION); return BHI; }
  {B}{C}{C}                   { yybegin(AFTER_INSTRUCTION); return BCC; }
  {B}{H}{S}                   { yybegin(AFTER_INSTRUCTION); return BHS; }
  {B}{P}{L}                   { yybegin(AFTER_INSTRUCTION); return BPL; }
  {B}{V}{C}                   { yybegin(AFTER_INSTRUCTION); return BVC; }
  {B}{L}{T}                   { yybegin(AFTER_INSTRUCTION); return BLT; }
  {B}{L}{E}                   { yybegin(AFTER_INSTRUCTION); return BLE; }
  {B}{G}{T}                   { yybegin(AFTER_INSTRUCTION); return BGT; }
  {B}{G}{E}                   { yybegin(AFTER_INSTRUCTION); return BGE; }
  {B}{M}{I}                   { yybegin(AFTER_INSTRUCTION); return BMI; }
  {B}{V}{S}                   { yybegin(AFTER_INSTRUCTION); return BVS; }

  {D}{B}{R}{A}                { yybegin(AFTER_INSTRUCTION); return DBRA; }
  {D}{B}{C}{S}                { yybegin(AFTER_INSTRUCTION); return DBCS; }
  {D}{B}{L}{O}                { yybegin(AFTER_INSTRUCTION); return DBLO; }
  {D}{B}{L}{S}                { yybegin(AFTER_INSTRUCTION); return DBLS; }
  {D}{B}{E}{Q}                { yybegin(AFTER_INSTRUCTION); return DBEQ; }
  {D}{B}{N}{E}                { yybegin(AFTER_INSTRUCTION); return DBNE; }
  {D}{B}{H}{I}                { yybegin(AFTER_INSTRUCTION); return DBHI; }
  {D}{B}{C}{C}                { yybegin(AFTER_INSTRUCTION); return DBCC; }
  {D}{B}{H}{S}                { yybegin(AFTER_INSTRUCTION); return DBHS; }
  {D}{B}{P}{L}                { yybegin(AFTER_INSTRUCTION); return DBPL; }
  {D}{B}{V}{C}                { yybegin(AFTER_INSTRUCTION); return DBVC; }
  {D}{B}{L}{T}                { yybegin(AFTER_INSTRUCTION); return DBLT; }
  {D}{B}{L}{E}                { yybegin(AFTER_INSTRUCTION); return DBLE; }
  {D}{B}{G}{T}                { yybegin(AFTER_INSTRUCTION); return DBGT; }
  {D}{B}{G}{E}                { yybegin(AFTER_INSTRUCTION); return DBGE; }
  {D}{B}{M}{I}                { yybegin(AFTER_INSTRUCTION); return DBMI; }
  {D}{B}{V}{S}                { yybegin(AFTER_INSTRUCTION); return DBVS; }
  {D}{B}{F}                   { yybegin(AFTER_INSTRUCTION); return DBF; }
  {D}{B}{T}                   { yybegin(AFTER_INSTRUCTION); return DBT; }

  {S}{E}{Q}                   { yybegin(AFTER_INSTRUCTION); return SEQ; }
  {S}{N}{E}                   { yybegin(AFTER_INSTRUCTION); return SNE; }
  {S}{P}{L}                   { yybegin(AFTER_INSTRUCTION); return SPL; }
  {S}{M}{I}                   { yybegin(AFTER_INSTRUCTION); return SMI; }
  {S}{V}{C}                   { yybegin(AFTER_INSTRUCTION); return SVC; }
  {S}{V}{S}                   { yybegin(AFTER_INSTRUCTION); return SVS; }
  {S}{T}                      { yybegin(AFTER_INSTRUCTION); return ST; }
  {S}{F}                      { yybegin(AFTER_INSTRUCTION); return SF; }
  {S}{G}{E}                   { yybegin(AFTER_INSTRUCTION); return SGE; }
  {S}{G}{T}                   { yybegin(AFTER_INSTRUCTION); return SGT; }
  {S}{L}{E}                   { yybegin(AFTER_INSTRUCTION); return SLE; }
  {S}{L}{T}                   { yybegin(AFTER_INSTRUCTION); return SLT; }
  {S}{C}{C}                   { yybegin(AFTER_INSTRUCTION); return SCC; }
  {S}{H}{I}                   { yybegin(AFTER_INSTRUCTION); return SHI; }
  {S}{L}{S}                   { yybegin(AFTER_INSTRUCTION); return SLS; }
  {S}{C}{S}                   { yybegin(AFTER_INSTRUCTION); return SCS; }
  {S}{H}{S}                   { yybegin(AFTER_INSTRUCTION); return SHS; }
  {S}{L}{O}                   { yybegin(AFTER_INSTRUCTION); return SLO; }

  {A}{S}{L}                   { yybegin(AFTER_INSTRUCTION); return ASL; }
  {A}{S}{R}                   { yybegin(AFTER_INSTRUCTION); return ASR; }
  {L}{S}{L}                   { yybegin(AFTER_INSTRUCTION); return LSL; }
  {L}{S}{R}                   { yybegin(AFTER_INSTRUCTION); return LSR; }
  {R}{O}{L}                   { yybegin(AFTER_INSTRUCTION); return ROL; }
  {R}{O}{R}                   { yybegin(AFTER_INSTRUCTION); return ROR; }
  {R}{O}{X}{L}                { yybegin(AFTER_INSTRUCTION); return ROXL; }
  {R}{O}{X}{R}                { yybegin(AFTER_INSTRUCTION); return ROXR; }

  {A}{D}{D}{W}{A}{T}{C}{H}    { yybegin(IN_OPERAND); return ADDWATCH; }
  {A}{L}{I}{G}{N}             { yybegin(IN_OPERAND); return ALIGN; }
  {B}{L}{K}                   { yybegin(AFTER_INSTRUCTION); return BLK; }
  {B}{S}{S}                   { yybegin(AFTER_OPERAND); return BSS; }
  {B}{S}{S}_{C}               { yybegin(AFTER_OPERAND); return BSS_C; }
  {B}{S}{S}_{F}               { yybegin(AFTER_OPERAND); return BSS_F; }
  {C}{N}{O}{P}                { yybegin(IN_OPERAND); return CNOP; }
  {C}{O}{D}{E}                { yybegin(AFTER_OPERAND); return CODE; }
  {C}{O}{D}{E}_{C}            { yybegin(AFTER_OPERAND); return CODE_C; }
  {C}{O}{D}{E}_{F}            { yybegin(AFTER_OPERAND); return CODE_F; }
  {C}{S}{E}{G}                { yybegin(AFTER_OPERAND); return CSEG; }
  {D}{A}{T}{A}                { yybegin(AFTER_OPERAND); return DATA; }
  {D}{A}{T}{A}_{C}            { yybegin(AFTER_OPERAND); return DATA_C; }
  {D}{A}{T}{A}_{F}            { yybegin(AFTER_OPERAND); return DATA_F; }
  {D}{C}                      { yybegin(AFTER_INSTRUCTION); return DC; }
  {D}{C}{B}                   { yybegin(AFTER_INSTRUCTION); return DCB; }
  {D}{S}                      { yybegin(AFTER_INSTRUCTION); return DS; }
  {D}{S}{E}{G}                { yybegin(AFTER_INSTRUCTION); return DSEG; }
  {E}{I}{N}{L}{I}{N}{E}       { yybegin(AFTER_OPERAND); return EINLINE; }
  {E}{N}{D}                   { yybegin(AFTER_OPERAND); return END; }
  {E}{N}{D}{R}                { yybegin(AFTER_OPERAND); return ENDR; }
  {E}{Q}{U}                   { yybegin(IN_OPERAND); return EQU; }
  {E}{Q}{U}{R}                { yybegin(IN_OPERAND); return EQUR; }
  {E}{R}{E}{M}                { yybegin(AFTER_OPERAND); return EREM; }
  {E}{V}{E}{N}                { yybegin(AFTER_OPERAND); return EVEN; }
  {F}{A}{I}{L}                { yybegin(AFTER_OPERAND); return FAIL; }
  {I}{N}{C}{B}{I}{N}          { yybegin(STRING_DIRECTIVE); return INCBIN; }
  {I}{N}{C}{D}{I}{R}          { yybegin(STRING_DIRECTIVE); return INCDIR; }
  {I}{N}{C}{L}{U}{D}{E}       { yybegin(STRING_DIRECTIVE); return INCLUDE; }
  {I}{N}{L}{I}{N}{E}          { yybegin(AFTER_OPERAND); return INLINE; }
  {J}{U}{M}{P}{E}{R}{R}       { yybegin(IN_OPERAND); return JUMPERR; }
  {J}{U}{M}{P}{P}{T}{R}       { yybegin(IN_OPERAND); return JUMPPTR; }
  {L}{I}{S}{T}                { yybegin(AFTER_OPERAND); return LIST; }
  {L}{L}{E}{N}                { yybegin(IN_OPERAND); return LLEN; }
  {N}{O}{L}{I}{S}{T}          { yybegin(AFTER_OPERAND); return NOLIST; }
  {N}{O}{P}{A}{G}{E}          { yybegin(AFTER_OPERAND); return NOPAGE; }
  {O}{D}{D}                   { yybegin(AFTER_OPERAND); return ODD; }
  {O}{P}{T}                   { yybegin(IN_OPERAND); return OPT; }
  {O}{R}{G}                   { yybegin(IN_OPERAND); return ORG; }
  {P}{A}{G}{E}                { yybegin(AFTER_OPERAND); return PAGE; }
  {P}{L}{E}{N}                { yybegin(IN_OPERAND); return PLEN; }
  {R}{E}{M}                   { yybegin(AFTER_OPERAND); return REM; }
  {R}{E}{P}{T}                { yybegin(IN_OPERAND); return REPT; }
  {R}{S}                      { yybegin(AFTER_INSTRUCTION); return RS; }
  {R}{S}{R}{E}{S}{E}{T}       { yybegin(AFTER_OPERAND); return RSRESET; }
  {R}{S}{S}{E}{T}             { yybegin(IN_OPERAND); return RSSET; }
  {S}{E}{C}{T}{I}{O}{N}       { yybegin(IN_OPERAND); return SECTION; }
  {S}{E}{T}                   { yybegin(IN_OPERAND); return SET; }
  {S}{P}{C}                   { yybegin(IN_OPERAND); return SPC; }
  {T}{E}{X}{T}                { yybegin(AFTER_OPERAND); return TEXT; }
  {X}{D}{E}{F}                { yybegin(IN_OPERAND); return XDEF; }
  {X}{R}{E}{F}                { yybegin(IN_OPERAND); return XREF; }

  {M}{A}{C}{R}{O}             { yybegin(AFTER_OPERAND); return MACRO; }
  {E}{N}{D}{M}                { yybegin(AFTER_OPERAND); return ENDM; }
  {M}{E}{X}{I}{T}             { yybegin(AFTER_OPERAND); return MEXIT; }

  {I}{F}                      { yybegin(IN_OPERAND); return IF; }
  {I}{F}{B}                   { yybegin(IN_OPERAND); return IFB; }
  {I}{F}{N}{B}                { yybegin(IN_OPERAND); return IFNB; }
  {I}{F}{C}                   { yybegin(IN_OPERAND); return IFC; }
  {I}{F}{N}{C}                { yybegin(IN_OPERAND); return IFNC; }
  {I}{F}{D}                   { yybegin(IN_OPERAND); return IFD; }
  {I}{F}{E}{Q}                { yybegin(IN_OPERAND); return IFEQ; }
  {I}{F}{G}{E}                { yybegin(IN_OPERAND); return IFGE; }
  {I}{F}{P}{L}                { yybegin(IN_OPERAND); return IFPL; }
  {I}{F}{G}{T}                { yybegin(IN_OPERAND); return IFGT; }
  {I}{F}{M}{A}{C}{R}{O}{D}    { yybegin(IN_OPERAND); return IFMACROD; }
  {I}{F}{M}{A}{C}{R}{O}{N}{D} { yybegin(IN_OPERAND); return IFMACROND; }
  {I}{F}{N}{D}                { yybegin(IN_OPERAND); return IFND; }
  {I}{F}{N}{E}                { yybegin(IN_OPERAND); return IFNE; }
  {I}{F}{L}{E}                { yybegin(IN_OPERAND); return IFLE; }
  {I}{F}{L}{T}                { yybegin(IN_OPERAND); return IFLT; }
  {I}{F}{M}{I}                { yybegin(IN_OPERAND); return IFMI; }
  {E}{N}{D}{C}                { yybegin(AFTER_OPERAND); return ENDC; }
  {E}{N}{D}{I}{F}             { yybegin(AFTER_OPERAND); return ENDIF; }
  {E}{L}{S}{E}                { yybegin(AFTER_OPERAND); return ELSE; }
  {E}{L}{S}{E}{I}{F}          { yybegin(AFTER_OPERAND); return ELSEIF; }

  // anything else is macro name
  {ID}                        { yybegin(IN_OPERAND); return MACRO_CALL_ID; }

  {COMMENT}                   { return COMMENT; }
}

[^] { return BAD_CHARACTER; }
