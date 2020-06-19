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

STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")  // todo no CRLF
ID=[_.]*[a-zA-Z][a-zA-Z_0-9]*
LABEL=[a-zA-Z][a-zA-Z_0-9]*  // todo without "./_" first char

%state IN_LABEL
%state IN_INSTRUCTION
%state IN_COMMENT

%%
<YYINITIAL> {
  {CRLF} { return WHITE_SPACE; }

  "."    { return DOT; }
  "_"    { return UNDERSCORE; }
  
  {LABEL}   { yybegin(IN_LABEL); return ID; }

  {WHITE_SPACE}* {COMMENT} { yybegin(IN_COMMENT); return COMMENT; }
  {WHITE_SPACE}+ { clearBranchIdMode(); yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}


// todo valid? "INTREQ=$09C"
<IN_LABEL> {
  ":" { clearBranchIdMode(); yybegin(IN_INSTRUCTION); return COLON; }

  {WHITE_SPACE}+ {COMMENT} { yybegin(IN_COMMENT); return COMMENT; }
  {WHITE_SPACE}+ { clearBranchIdMode(); yybegin(IN_INSTRUCTION); return WHITE_SPACE; }

  {CRLF} { yybegin(YYINITIAL); return WHITE_SPACE; }
}

<IN_COMMENT> {
  {CRLF} { yybegin(YYINITIAL); return WHITE_SPACE; }
}

<IN_INSTRUCTION> {
//  "\\n"                  { return NL; }
  {CRLF}          { yybegin(YYINITIAL); return WHITE_SPACE; }
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

  [sS][pP]      { return SP; }
  [sS][sS][pP]  { return SSP; }
  [uU][sS][pP]  { return USP; }
  [pP][cC]      { return PC; }
  [sS][rR]      { return SR; }
  [cC][cC][rR]  { return CCR; }
  [dD][0-7]     { return DATA_REGISTER; }
  [aA][0-7]     { return ADDRESS_REGISTER; }

  [nN][oO][pP]                    { return NOP; }
  [iI][lL][lL][eE][gG][aA][lL]    { return ILLEGAL; }
  [rR][eE][sS][eE][tT]            { return RESET; }
  [sS][tT][oO][pP]                { return STOP; }
  [tT][rR][aA][pP]                { return TRAP; }
  [tT][rR][aA][pP][vV]            { return TRAPV; }
  [lL][iI][nN][kK]                { return LINK; }
  [uU][nN][lL][kK]                { return UNLK; }

  [mM][oO][vV][eE]                { return MOVE; }
  [mM][oO][vV][eE][aA]            { return MOVEA; }
  [mM][oO][vV][eE][mM]            { return MOVEM; }
  [mM][oO][vV][eE][pP]            { return MOVEP; }
  [mM][oO][vV][eE][qQ]            { return MOVEQ; }

  [tT][sS][tT]                    { return TST; }
  [tT][aA][sS]                    { return TAS; }
  [lL][eE][aA]                    { return LEA; }
  [pP][eE][aA]                    { return PEA; }
  [cC][lL][rR]                    { return CLR; }

  [jJ][mM][pP]                    { return JMP; }
  [jJ][sS][rR]                    { return JSR; }
  [bB][sS][rR]                    { return BSR; }
  [rR][tT][sS]                    { return RTS; }
  [rR][tT][eE]                    { return RTE; }
  [rR][tT][rR]                    { return RTR; }

  [aA][dD][dD]                    { return ADD; }
  [aA][dD][dD][aA]                { return ADDA; }
  [aA][dD][dD][iI]                { return ADDI; }
  [aA][dD][dD][qQ]                { return ADDQ; }
  [aA][dD][dD][xX]                { return ADDX; }
  [sS][uU][bB]                    { return SUB; }
  [sS][uU][bB][aA]                { return SUBA; }
  [sS][uU][bB][iI]                { return SUBI; }
  [sS][uU][bB][qQ]                { return SUBQ; }
  [sS][uU][bB][xX]                { return SUBX; }
  [mM][uU][lL][sS]                { return MULS; }
  [mM][uU][lL][uU]                { return MULU; }
  [dD][iI][vV][sS]                { return DIVS; }
  [dD][iI][vV][uU]                { return DIVU; }

  [aA][bB][cC][dD]                { return ABCD; }
  [nN][bB][cC][dD]                { return NBCD; }
  [sS][bB][cC][dD]                { return SBCD; }

  [aA][nN][dD]                    { return AND; }
  [aA][nN][dD][iI]                { return ANDI; }
  [oO][rR]                        { return OR; }
  [oO][rR][iI]                    { return ORI; }
  [eE][oO][rR]                    { return EOR; }
  [eE][oO][rR][iI]                { return EORI; }

  [eE][xX][tT]                    { return EXT; }
  [nN][eE][gG]                    { return NEG; }
  [nN][eE][gG][xX]                { return NEGX; }
  [sS][wW][aA][pP]                { return SWAP; }
  [nN][oO][tT]                    { return NOT; }
  [cC][hH][kK]                    { return CHK; }
  [eE][xX][gG]                    { return EXG; }

  [cC][mM][pP]                    { return CMP; }
  [cC][mM][pP][aA]                { return CMPA; }
  [cC][mM][pP][iI]                { return CMPI; }
  [cC][mM][pP][mM]                { return CMPM; }

  [bB][cC][hH][gG]                { return BCHG; }
  [bB][cC][lL][rR]                { return BCLR; }
  [bB][sS][eE][tT]                { return BSET; }
  [bB][tT][sS][tT]                { return BTST; }

  [bB][rR][aA]                    { incBranchIdMode(); return BRA; }
  [bB][cC][sS]                    { incBranchIdMode(); return BCS; }
  [bB][lL][oO]                    { incBranchIdMode(); return BLO; }
  [bB][lL][sS]                    { incBranchIdMode(); return BLS; }
  [bB][eE][qQ]                    { incBranchIdMode(); return BEQ; }
  [bB][nN][eE]                    { incBranchIdMode(); return BNE; }
  [bB][hH][iI]                    { incBranchIdMode(); return BHI; }
  [bB][cC][cC]                    { incBranchIdMode(); return BCC; }
  [bB][hH][sS]                    { incBranchIdMode(); return BHS; }
  [bB][pP][lL]                    { incBranchIdMode(); return BPL; }
  [bB][vV][cC]                    { incBranchIdMode(); return BVC; }
  [bB][lL][tT]                    { incBranchIdMode(); return BLT; }
  [bB][lL][eE]                    { incBranchIdMode(); return BLE; }
  [bB][gG][tT]                    { incBranchIdMode(); return BGT; }
  [bB][gG][eE]                    { incBranchIdMode(); return BGE; }
  [bB][mM][iI]                    { incBranchIdMode(); return BMI; }
  [bB][vV][sS]                    { incBranchIdMode(); return BVS; }

  [dD][bB][rR][aA]                { incBranchIdMode(); return DBRA; }
  [dD][bB][cC][sS]                { incBranchIdMode(); return DBCS; }
  [dD][bB][lL][sS]                { incBranchIdMode(); return DBLS; }
  [dD][bB][eE][qQ]                { incBranchIdMode(); return DBEQ; }
  [dD][bB][nN][eE]                { incBranchIdMode(); return DBNE; }
  [dD][bB][hH][iI]                { incBranchIdMode(); return DBHI; }
  [dD][bB][cC][cC]                { incBranchIdMode(); return DBCC; }
  [dD][bB][pP][lL]                { incBranchIdMode(); return DBPL; }
  [dD][bB][vV][cC]                { incBranchIdMode(); return DBVC; }
  [dD][bB][lL][tT]                { incBranchIdMode(); return DBLT; }
  [dD][bB][lL][eE]                { incBranchIdMode(); return DBLE; }
  [dD][bB][gG][tT]                { incBranchIdMode(); return DBGT; }
  [dD][bB][gG][eE]                { incBranchIdMode(); return DBGE; }
  [dD][bB][mM][iI]                { incBranchIdMode(); return DBMI; }
  [dD][bB][vV][sS]                { incBranchIdMode(); return DBVS; }
  [dD][bB][fF]                    { incBranchIdMode(); return DBF; }
  [dD][bB][tT]                    { incBranchIdMode(); return DBT; }

  [sS][eE][qQ]                    { return SEQ; }
  [sS][nN][eE]                    { return SNE; }
  [sS][pP][lL]                    { return SPL; }
  [sS][mM][iI]                    { return SMI; }
  [sS][vV][cC]                    { return SVC; }
  [sS][vV][sS]                    { return SVS; }
  [sS][tT]                        { return ST; }
  [sS][fF]                        { return SF; }
  [sS][gG][eE]                    { return SGE; }
  [sS][gG][tT]                    { return SGT; }
  [sS][lL][eE]                    { return SLE; }
  [sS][lL][tT]                    { return SLT; }
  [sS][cC][cC]                    { return SCC; }
  [sS][hH][iI]                    { return SHI; }
  [sS][lL][sS]                    { return SLS; }
  [sS][cC][sS]                    { return SCS; }

  [aA][sS][lL]                    { return ASL; }
  [aA][sS][rR]                    { return ASR; }
  [lL][sS][lL]                    { return LSL; }
  [lL][sS][rR]                    { return LSR; }
  [rR][oO][lL]                    { return ROL; }
  [rR][oO][rR]                    { return ROR; }
  [rR][oO][xX][lL]                { return ROXL; }
  [rR][oO][xX][rR]                { return ROXR; }


  "." [bB]                        { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_B; }
  "." [sS]                        { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_S; }
  "." [wW]                        { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_W; }
  "." [lL]                        { if (isBranchIdMode()) return ID; incBranchIdMode(); return DOT_L; }


  [bB][lL][kK]                    { return BLK; }
  [dD][cC]                        { return DC; }
  [dD][cC][bB]                    { return DCB; }
  [dD][sS]                        { return DS; }
  [eE][nN][dD]                    { return END; }
  [eE][qQ][uU]                    { return EQU; }
  [eE][qQ][uU][rR]                { return EQUR; }
  [eE][vV][eE][nN]                { return EVEN; }
  [iI][nN][cC][bB][iI][nN]        { return INCBIN; }
  [iI][nN][cC][dD][iI][rR]        { return INCDIR; }
  [iI][nN][cC][lL][uU][dD][eE]    { return INCLUDE; }
  [oO][dD][dD]                    { return ODD; }
  [oO][pP][tT]                    { return OPT; }
  [oO][rR][gG]                    { return ORG; }
  [rR][sS]                        { return RS; }
  [rR][sS][sS][eE][tT]            { return RSSET; }
  [rR][sS][rR][eE][sS][eE][tT]    { return RSRESET; }
  [sS][eE][cC][tT][iI][oO][nN]    { return SECTION; }
  [sS][eE][tT]                    { return SET; }

  [mM][aA][cC][rR][oO]            { return MACRO; }
  [eE][nN][dD][mM]                { return ENDM; }

  [iI][fF]                        { return IF; }
  [iI][fF][bB]                    { return IFB; }
  [iI][fF][nN][bB]                { return IFNB; }
  [iI][fF][cC]                    { return IFC; }
  [iI][fF][nN][cC]                { return IFNC; }
  [iI][fF][dD]                    { return IFD; }
  [iI][fF][eE][qQ]                { return IFEQ; }
  [iI][fF][gG][eE]                { return IFGE; }
  [iI][fF][gG][tT]                { return IFGT; }
  [iI][fF][nN][dD]                { return IFND; }
  [iI][fF][nN][eE]                { return IFNE; }
  [iI][fF][lL][eE]                { return IFLE; }
  [iI][fF][lL][tT]                { return IFLT; }
  [eE][nN][dD][cC]                { return ENDC; }
  [eE][lL][sS][eE]                { return ELSE; }
  [eE][lL][sS][eE][iI][fF]        { return ELSEIF; }

  {EOL_COMMENT}                   { yybegin(IN_COMMENT); return COMMENT; }

  {DECNUMBER}                     { clearBranchIdMode(); return DEC_NUMBER; }
  {HEXNUMBER}                     { clearBranchIdMode(); return HEX_NUMBER; }
  {OCTNUMBER}                     { clearBranchIdMode(); return OCT_NUMBER; }
  {BINNUMBER}                     { clearBranchIdMode(); return BIN_NUMBER; }

  {ID}                            { return ID; }
  {STRING}                        { return STRING; }
}

[^] { return BAD_CHARACTER; }
