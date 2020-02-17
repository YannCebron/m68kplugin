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

import java.nio.channels.Pipe;import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.yanncebron.m68kplugin.lang.psi.M68kTokenTypes.*;

%%

%{
  public _M68kLexer() {
    this((java.io.Reader)null);
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
ID=[_|.]*[:letter:][a-zA-Z_0-9]*
LABEL=[:letter:][a-zA-Z_0-9]*  // todo without "./_" first char

%state IN_LABEL
%state IN_INSTRUCTION
%state IN_COMMENT

// todo instructions: <WHITE_SPACE> <ins><dataSize?> <WHITE_SPACE> <PARAMS?>
// todo comment tail: <WHITE SPACE> <COMMENT_TEXT_WITHOUT_PREFIX> // "; commentwithPrefix"

%%
<YYINITIAL> {
  {CRLF} { return WHITE_SPACE; }

  "."    { return DOT; }  // TODO ??
  {LABEL}   { yybegin(IN_LABEL); return ID; }

  {WHITE_SPACE}* {COMMENT} { yybegin(IN_COMMENT); return COMMENT; }
  {WHITE_SPACE}+ { yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}


// todo valid? "INTREQ=$09C"
<IN_LABEL> {
  ":" { yybegin(IN_INSTRUCTION); return COLON; }

  {WHITE_SPACE}+ {COMMENT} { yybegin(IN_COMMENT); return COMMENT; }
  {WHITE_SPACE}+ { yybegin(IN_INSTRUCTION); return WHITE_SPACE; }

  {CRLF} { yybegin(YYINITIAL); return WHITE_SPACE; }
}

<IN_COMMENT> {
  {CRLF} { yybegin(YYINITIAL); return WHITE_SPACE; }
}

<IN_INSTRUCTION> {
//  "\\n"                  { return NL; }
  {CRLF}         { yybegin(YYINITIAL); return WHITE_SPACE; }
  {WHITE_SPACE}  { return WHITE_SPACE; }

  "."  { return DOT; }
  ":"  { return COLON; }
  ";"  { return SEMICOLON; }
  ","  { return COMMA; }
  "+"  { return PLUS; }
  "-"  { return MINUS; }
  "*"  { return MUL; }
  "/"  { return DIV; }
  "="  { return EQ; }
  "^"  { return POW; }
  "#"  { return HASH; }
  "~"  { return TILDE; }
  "%"  { return PERCENT; }
  "&"  { return AMPERS_AND; }
  "|"  { return PIPE; }
  "<<" { return SHIFT_L; }
  ">>" { return SHIFT_R; }
  "("  { return L_PAREN; }
  ")"  { return R_PAREN; }

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
  [tT][rR][aA][pP]                { return TRAP; }
  [tT][rR][aA][pP][vV]            { return TRAPV; }
  [lL][iI][nN][kK]                { return LINK; }
  [uU][nN][lL][kK]                { return UNLK; }

  [mM][oO][vV][eE]                { return MOVE; }
  [mM][oO][vV][eE][aA]            { return MOVEA; }
  [mM][oO][vV][eE][qQ]            { return MOVEQ; }
  [mM][oO][vV][eE][mM]            { return MOVEM; }

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

  [bB][rR][aA]                    { return BRA; }
  [bB][cC][sS]                    { return BCS; }
  [bB][lL][oO]                    { return BLO; }
  [bB][lL][sS]                    { return BLS; }
  [bB][eE][qQ]                    { return BEQ; }
  [bB][nN][eE]                    { return BNE; }
  [bB][hH][iI]                    { return BHI; }
  [bB][cC][cC]                    { return BCC; }
  [bB][pP][lL]                    { return BPL; }
  [bB][vV][cC]                    { return BVC; }
  [bB][lL][tT]                    { return BLT; }
  [bB][lL][eE]                    { return BLE; }
  [bB][gG][tT]                    { return BGT; }
  [bB][gG][eE]                    { return BGE; }
  [bB][mM][iI]                    { return BMI; }
  [bB][vV][sS]                    { return BVS; }

  [dD][bB][rR][aA]                { return DBRA; }
  [dD][bB][cC][sS]                { return DBCS; }
  [dD][bB][lL][sS]                { return DBLS; }
  [dD][bB][eE][qQ]                { return DBEQ; }
  [dD][bB][nN][eE]                { return DBNE; }
  [dD][bB][hH][iI]                { return DBHI; }
  [dD][bB][cC][cC]                { return DBCC; }
  [dD][bB][pP][lL]                { return DBPL; }
  [dD][bB][vV][cC]                { return DBVC; }
  [dD][bB][lL][tT]                { return DBLT; }
  [dD][bB][lL][eE]                { return DBLE; }
  [dD][bB][gG][tT]                { return DBGT; }
  [dD][bB][gG][eE]                { return DBGE; }
  [dD][bB][mM][iI]                { return DBMI; }
  [dD][bB][vV][sS]                { return DBVS; }
  [dD][bB][fF]                    { return DBF; }
  [dD][bB][tT]                    { return DBT; }

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


  "." [bB]                        { return DOT_B; }
  "." [sS]                        { return DOT_S; }
  "." [wW]                        { return DOT_W; }
  "." [lL]                        { return DOT_L; }


  [bB][lL][kK]                    { return BLK; }
  [dD][cC]                        { return DC; }
  [dD][cC][bB]                    { return DCB; }
  [dD][sS]                        { return DS; }
  [eE][qQ][uU]                    { return EQU; }
  [eE][qQ][uU][rR]                { return EQUR; }
  [eE][vV][eE][nN]                { return EVEN; }
  [iI][nN][cC][bB][iI][nN]        { return INCBIN; }
  [iI][nN][cC][dD][iI][rR]        { return INCDIR; }
  [iI][nN][cC][lL][uU][dD][eE]    { return INCLUDE; }
  [oO][dD][dD]                    { return ODD; }
  [oO][pP][tT]                    { return OPT; }
  [rR][sS]                        { return RS; }
  [rR][sS][sS][eE][tT]            { return RSSET; }
  [rR][sS][rR][eE][sS][eE][tT]    { return RSRESET; }


  {EOL_COMMENT}                   { yybegin(IN_COMMENT); return COMMENT; }

  {DECNUMBER}                     { return DEC_NUMBER; }
  {HEXNUMBER}                     { return HEX_NUMBER; }
  {OCTNUMBER}                     { return OCT_NUMBER; }
  {BINNUMBER}                     { return BIN_NUMBER; }

  {ID}                            { return ID; }
  {STRING}                        { return STRING; }
}

[^] { return BAD_CHARACTER; }
