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
  private int branchIdMode;

  public _M68kLexer() {
    this((java.io.Reader)null);
  }

  public boolean isBranchIdMode() {
    return branchIdMode > 1;
  }

  public void clearBranchIdMode(){
    branchIdMode = 0;
  }

  public void incBranchIdMode() {
    branchIdMode++;
  }
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

%state IN_LABEL
%state IN_INSTRUCTION
%state IN_COMMENT

%%
<YYINITIAL> {
  "."    { return DOT; }
  {LABEL}   { yybegin(IN_LABEL); return ID; }

  {WHITE_SPACE}* {COMMENT} { yybegin(IN_COMMENT); return COMMENT; }
  {WHITE_SPACE}+ { clearBranchIdMode(); yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}

<YYINITIAL, IN_LABEL, IN_COMMENT, IN_INSTRUCTION> {
  {CRLF} { yybegin(YYINITIAL); return LINEFEED; }
}

<IN_LABEL> {
  ":" { clearBranchIdMode(); yybegin(IN_INSTRUCTION); return COLON; }
  "=" { clearBranchIdMode(); yybegin(IN_INSTRUCTION); return EQ; }

  {WHITE_SPACE}+ {COMMENT} { yybegin(IN_COMMENT); return COMMENT; }
  {WHITE_SPACE}+ { clearBranchIdMode(); yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}

<IN_INSTRUCTION> {
  {WHITE_SPACE}+  { incBranchIdMode();  return WHITE_SPACE; }

  "."  { return DOT; }
  ":"  { return COLON; }
  ","  { return COMMA; }
  "+"  { return PLUS; }
  "-"  { return MINUS; }
  "*"  { return MUL; }
  "/"  { return DIV; }
  "="  { return EQ; }
  "^"  { return POW; }
  "#"  { clearBranchIdMode(); return HASH; }
  "~"  { return TILDE; }
  "!"  { return EXCLAMATION; }
  "%"  { return PERCENT; }
  "&"  { return AMPERSAND; }
  "\\" { return BACKSLASH; }
  "|"  { return PIPE; }
  "<<" { return SHIFT_L; }
  ">>" { return SHIFT_R; }
  "("  { clearBranchIdMode(); return L_PAREN; }
  ")"  { return R_PAREN; }
  "["  { clearBranchIdMode(); return L_BRACKET; }
  "]"  { return R_BRACKET; }

  {S}{P}     { return SP; }
  {S}{S}{P}  { return SSP; }
  {U}{S}{P}  { return USP; }
  {P}{C}     { return PC; }
  {S}{R}     { return SR; }
  {C}{C}{R}  { return CCR; }
  {D}[0-7]   { return DATA_REGISTER; }
  {A}[0-7]   { return ADDRESS_REGISTER; }

  {N}{O}{P}                   { return NOP; }
  {I}{L}{L}{E}{G}{A}{L}       { return ILLEGAL; }
  {R}{E}{S}{E}{T}             { return RESET; }
  {S}{T}{O}{P}                { return STOP; }
  {T}{R}{A}{P}                { return TRAP; }
  {T}{R}{A}{P}{V}             { return TRAPV; }
  {L}{I}{N}{K}                { return LINK; }
  {U}{N}{L}{K}                { return UNLK; }

  {M}{O}{V}{E}                { return MOVE; }
  {M}{O}{V}{E}{A}             { return MOVEA; }
  {M}{O}{V}{E}{M}             { return MOVEM; }
  {M}{O}{V}{E}{P}             { return MOVEP; }
  {M}{O}{V}{E}{Q}             { return MOVEQ; }

  {T}{S}{T}                   { return TST; }
  {T}{A}{S}                   { return TAS; }
  {L}{E}{A}                   { return LEA; }
  {P}{E}{A}                   { return PEA; }
  {C}{L}{R}                   { return CLR; }

  {J}{M}{P}                   { return JMP; }
  {J}{S}{R}                   { return JSR; }
  {B}{S}{R}                   { return BSR; }
  {R}{T}{S}                   { return RTS; }
  {R}{T}{E}                   { return RTE; }
  {R}{T}{R}                   { return RTR; }

  {A}{D}{D}                   { return ADD; }
  {A}{D}{D}{A}                { return ADDA; }
  {A}{D}{D}{I}                { return ADDI; }
  {A}{D}{D}{Q}                { return ADDQ; }
  {A}{D}{D}{X}                { return ADDX; }
  {S}{U}{B}                   { return SUB; }
  {S}{U}{B}{A}                { return SUBA; }
  {S}{U}{B}{I}                { return SUBI; }
  {S}{U}{B}{Q}                { return SUBQ; }
  {S}{U}{B}{X}                { return SUBX; }
  {M}{U}{L}{S}                { return MULS; }
  {M}{U}{L}{U}                { return MULU; }
  {D}{I}{V}{S}                { return DIVS; }
  {D}{I}{V}{U}                { return DIVU; }

  {A}{B}{C}{D}                { return ABCD; }
  {N}{B}{C}{D}                { return NBCD; }
  {S}{B}{C}{D}                { return SBCD; }

  {A}{N}{D}                   { return AND; }
  {A}{N}{D}{I}                { return ANDI; }
  {O}{R}                      { return OR; }
  {O}{R}{I}                   { return ORI; }
  {E}{O}{R}                   { return EOR; }
  {E}{O}{R}{I}                { return EORI; }

  {E}{X}{T}                   { return EXT; }
  {N}{E}{G}                   { return NEG; }
  {N}{E}{G}{X}                { return NEGX; }
  {S}{W}{A}{P}                { return SWAP; }
  {N}{O}{T}                   { return NOT; }
  {C}{H}{K}                   { return CHK; }
  {E}{X}{G}                   { return EXG; }

  {C}{M}{P}                   { return CMP; }
  {C}{M}{P}{A}                { return CMPA; }
  {C}{M}{P}{I}                { return CMPI; }
  {C}{M}{P}{M}                { return CMPM; }

  {B}{C}{H}{G}                { return BCHG; }
  {B}{C}{L}{R}                { return BCLR; }
  {B}{S}{E}{T}                { return BSET; }
  {B}{T}{S}{T}                { return BTST; }

  {B}{R}{A}                   { incBranchIdMode(); return BRA; }
  {B}{C}{S}                   { incBranchIdMode(); return BCS; }
  {B}{L}{O}                   { incBranchIdMode(); return BLO; }
  {B}{L}{S}                   { incBranchIdMode(); return BLS; }
  {B}{E}{Q}                   { incBranchIdMode(); return BEQ; }
  {B}{N}{E}                   { incBranchIdMode(); return BNE; }
  {B}{H}{I}                   { incBranchIdMode(); return BHI; }
  {B}{C}{C}                   { incBranchIdMode(); return BCC; }
  {B}{H}{S}                   { incBranchIdMode(); return BHS; }
  {B}{P}{L}                   { incBranchIdMode(); return BPL; }
  {B}{V}{C}                   { incBranchIdMode(); return BVC; }
  {B}{L}{T}                   { incBranchIdMode(); return BLT; }
  {B}{L}{E}                   { incBranchIdMode(); return BLE; }
  {B}{G}{T}                   { incBranchIdMode(); return BGT; }
  {B}{G}{E}                   { incBranchIdMode(); return BGE; }
  {B}{M}{I}                   { incBranchIdMode(); return BMI; }
  {B}{V}{S}                   { incBranchIdMode(); return BVS; }

  {D}{B}{R}{A}                { incBranchIdMode(); return DBRA; }
  {D}{B}{C}{S}                { incBranchIdMode(); return DBCS; }
  {D}{B}{L}{O}                { incBranchIdMode(); return DBLO; }
  {D}{B}{L}{S}                { incBranchIdMode(); return DBLS; }
  {D}{B}{E}{Q}                { incBranchIdMode(); return DBEQ; }
  {D}{B}{N}{E}                { incBranchIdMode(); return DBNE; }
  {D}{B}{H}{I}                { incBranchIdMode(); return DBHI; }
  {D}{B}{C}{C}                { incBranchIdMode(); return DBCC; }
  {D}{B}{H}{S}                { incBranchIdMode(); return DBHS; }
  {D}{B}{P}{L}                { incBranchIdMode(); return DBPL; }
  {D}{B}{V}{C}                { incBranchIdMode(); return DBVC; }
  {D}{B}{L}{T}                { incBranchIdMode(); return DBLT; }
  {D}{B}{L}{E}                { incBranchIdMode(); return DBLE; }
  {D}{B}{G}{T}                { incBranchIdMode(); return DBGT; }
  {D}{B}{G}{E}                { incBranchIdMode(); return DBGE; }
  {D}{B}{M}{I}                { incBranchIdMode(); return DBMI; }
  {D}{B}{V}{S}                { incBranchIdMode(); return DBVS; }
  {D}{B}{F}                   { incBranchIdMode(); return DBF; }
  {D}{B}{T}                   { incBranchIdMode(); return DBT; }

  {S}{E}{Q}                   { return SEQ; }
  {S}{N}{E}                   { return SNE; }
  {S}{P}{L}                   { return SPL; }
  {S}{M}{I}                   { return SMI; }
  {S}{V}{C}                   { return SVC; }
  {S}{V}{S}                   { return SVS; }
  {S}{T}                      { return ST; }
  {S}{F}                      { return SF; }
  {S}{G}{E}                   { return SGE; }
  {S}{G}{T}                   { return SGT; }
  {S}{L}{E}                   { return SLE; }
  {S}{L}{T}                   { return SLT; }
  {S}{C}{C}                   { return SCC; }
  {S}{H}{I}                   { return SHI; }
  {S}{L}{S}                   { return SLS; }
  {S}{C}{S}                   { return SCS; }
  {S}{H}{S}                   { return SHS; }
  {S}{L}{O}                   { return SLO; }

  {A}{S}{L}                   { return ASL; }
  {A}{S}{R}                   { return ASR; }
  {L}{S}{L}                   { return LSL; }
  {L}{S}{R}                   { return LSR; }
  {R}{O}{L}                   { return ROL; }
  {R}{O}{R}                   { return ROR; }
  {R}{O}{X}{L}                { return ROXL; }
  {R}{O}{X}{R}                { return ROXR; }


  "." {B}                     { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_B; }
  "." {S}                     { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_S; }
  "." {W}                     { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_W; }
  "." {L}                     { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_L; }


  {A}{D}{D}{W}{A}{T}{C}{H}    { return ADDWATCH; }
  {A}{L}{I}{G}{N}             { return ALIGN; }
  {B}{L}{K}                   { return BLK; }
  {B}{S}{S}                   { return BSS; }
  {B}{S}{S}_{C}               { return BSS_C; }
  {B}{S}{S}_{F}               { return BSS_F; }
  {C}{O}{D}{E}                { return CODE; }
  {C}{O}{D}{E}_{C}            { return CODE_C; }
  {C}{O}{D}{E}_{F}            { return CODE_F; }
  {C}{N}{O}{P}                { return CNOP; }
  {C}{S}{E}{G}                { return CSEG; }
  {D}{A}{T}{A}                { return DATA; }
  {D}{A}{T}{A}_{C}            { return DATA_C; }
  {D}{A}{T}{A}_{F}            { return DATA_F; }
  {D}{C}                      { return DC; }
  {D}{C}{B}                   { return DCB; }
  {D}{S}                      { return DS; }
  {D}{S}{E}{G}                { return DSEG; }
  {E}{N}{D}                   { return END; }
  {E}{N}{D}{R}                { return ENDR; }
  {E}{I}{N}{L}{I}{N}{E}       { return EINLINE; }
  {E}{Q}{U}                   { return EQU; }
  {E}{Q}{U}{R}                { return EQUR; }
  {E}{R}{E}{M}                { return EREM; }
  {E}{V}{E}{N}                { return EVEN; }
  {I}{N}{C}{B}{I}{N}          { return INCBIN; }
  {I}{N}{C}{D}{I}{R}          { return INCDIR; }
  {I}{N}{C}{L}{U}{D}{E}       { return INCLUDE; }
  {I}{N}{L}{I}{N}{E}          { return INLINE; }
  {J}{U}{M}{P}{E}{R}{R}       { return JUMPERR; }
  {J}{U}{M}{P}{P}{T}{R}       { return JUMPPTR; }
  {L}{L}{E}{N}                { return LLEN; }
  {L}{I}{S}{T}                { return LIST; }
  {O}{D}{D}                   { return ODD; }
  {O}{P}{T}                   { return OPT; }
  {O}{R}{G}                   { return ORG; }
  {P}{A}{G}{E}                { return PAGE; }
  {P}{L}{E}{N}                { return PLEN; }
  {N}{O}{L}{I}{S}{T}          { return NOLIST; }
  {N}{O}{P}{A}{G}{E}          { return NOPAGE; }
  {R}{E}{M}                   { return REM; }
  {R}{E}{P}{T}                { return REPT; }
  {R}{S}                      { return RS; }
  {R}{S}{S}{E}{T}             { return RSSET; }
  {R}{S}{R}{E}{S}{E}{T}       { return RSRESET; }
  {S}{E}{C}{T}{I}{O}{N}       { return SECTION; }
  {S}{E}{T}                   { return SET; }
  {S}{P}{C}                   { return SPC; }
  {T}{E}{X}{T}                { return TEXT; }
  {X}{D}{E}{F}                { return XDEF; }
  {X}{R}{E}{F}                { return XREF; }

  {M}{A}{C}{R}{O}             { return MACRO; }
  {E}{N}{D}{M}                { return ENDM; }
  {M}{E}{X}{I}{T}             { return MEXIT; }

  {I}{F}                      { return IF; }
  {I}{F}{B}                   { return IFB; }
  {I}{F}{N}{B}                { return IFNB; }
  {I}{F}{C}                   { return IFC; }
  {I}{F}{N}{C}                { return IFNC; }
  {I}{F}{D}                   { return IFD; }
  {I}{F}{E}{Q}                { return IFEQ; }
  {I}{F}{G}{E}                { return IFGE; }
  {I}{F}{P}{L}                { return IFPL; }
  {I}{F}{G}{T}                { return IFGT; }
  {I}{F}{M}{A}{C}{R}{O}{D}    { return IFMACROD; }
  {I}{F}{M}{A}{C}{R}{O}{N}{D} { return IFMACROND; }
  {I}{F}{N}{D}                { return IFND; }
  {I}{F}{N}{E}                { return IFNE; }
  {I}{F}{L}{E}                { return IFLE; }
  {I}{F}{L}{T}                { return IFLT; }
  {I}{F}{M}{I}                { return IFMI; }
  {E}{N}{D}{C}                { return ENDC; }
  {E}{N}{D}{I}{F}             { return ENDIF; }
  {E}{L}{S}{E}                { return ELSE; }
  {E}{L}{S}{E}{I}{F}          { return ELSEIF; }

  {EOL_COMMENT}               { yybegin(IN_COMMENT); return COMMENT; }

  {DECNUMBER}                 { clearBranchIdMode(); return DEC_NUMBER; }
  {HEXNUMBER}                 { clearBranchIdMode(); return HEX_NUMBER; }
  {OCTNUMBER}                 { clearBranchIdMode(); return OCT_NUMBER; }
  {BINNUMBER}                 { clearBranchIdMode(); return BIN_NUMBER; }

  {ID}                        { return ID; }
  {SINGLE_QUOTED_STRING}      { return STRING; }
  {DOUBLE_QUOTED_STRING}      { return STRING; }
}

[^] { return BAD_CHARACTER; }
