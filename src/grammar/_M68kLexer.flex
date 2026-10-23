/*
 * Copyright 2026 The Authors
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

  private char prevChar() {
    return getTokenStart() > 0 ? zzBuffer.charAt(getTokenStart() - 1) : (char) -1;
  }

  private boolean afterSpaceOrComma() {
    char c = prevChar();
    return c == ' ' || c == '\t' || c == ',';
  }

  /**
   * Whether given {@code '*'} is "current PC" symbol instead of {@link MUL}.
   */
  private boolean isCurrentPcSymbol() {
    char c = prevChar();
    return c == ' ' || c == '\t' || c == ',' || c == '-' || c == '+' || c == '(';
  }

  /**
   * Push back DATA_SIZE token.
   */
  private void pushbackDataSize() {
    yypushback(2);
  }

  /**
   * Check if the current match ends with a data size suffix and push it back.
   * Only handles normal data sizes (.w, .b, .l, .s) — macro parameter placeholders (.\0)
   * are handled by the dedicated rule in AFTER_INSTRUCTION since DATA_SIZE excludes [\\0].
   */
  private void pushbackDataSizeIfPresent() {
    int len = zzMarkedPos - zzStartRead;
    if (len >= 2) {
      char prev = zzBuffer.charAt(zzMarkedPos - 2);
      if (prev == '.') {
        char ch = zzBuffer.charAt(zzMarkedPos - 1);
        if (ch == 's' || ch == 'S' || ch == 'b' || ch == 'B' || ch == 'w' || ch == 'W' || ch == 'l' || ch == 'L') {
          yypushback(2);
        }
      }
    }
  }

  int operandSpaceCount = 0;
%}

%public
%class _M68kLexer
%implements FlexLexer
%function advance
%type IElementType
%ignorecase

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

DATA_SIZE_WITH_MACRO=[.][[sS]|[bB]|[wW]|[lL]|[\\0]]
DATA_SIZE=[.][[sS]|[bB]|[wW]|[lL]]
DATA_SIZE_S=[.][sS]
DATA_SIZE_B=[.][bB]
DATA_SIZE_W=[.][wW]
DATA_SIZE_L=[.][lL]

%state MACRO_DECLARATION
%state AFTER_LABEL
%state IN_INSTRUCTION
%state AFTER_INSTRUCTION
%state STRING_DIRECTIVE
%state IN_OPERAND
%state MACRO_PARAMETER
%state AFTER_OPERAND
%state IN_REM

%%
<YYINITIAL, MACRO_DECLARATION, AFTER_LABEL, IN_INSTRUCTION, AFTER_INSTRUCTION, STRING_DIRECTIVE, IN_OPERAND, MACRO_PARAMETER, AFTER_OPERAND> {
  {CRLF}                                       { operandSpaceCount = 0; yybegin(YYINITIAL); return LINEFEED; }
}

<YYINITIAL> {
  "."                                          { operandSpaceCount = 0; return DOT; }

  {LABEL}                                      { operandSpaceCount = 0; yybegin(AFTER_LABEL); return ID; }
  {COMMENT}                                    { return COMMENT; }

  // whitespace followed NOT by instruction variants
  {WHITE_SPACE}+ / {ID} ":"                    { return WHITE_SPACE; }
  {WHITE_SPACE}+ / "macro" {WHITE_SPACE}+      { yybegin(MACRO_DECLARATION); return WHITE_SPACE; }

  {WHITE_SPACE}+                               { operandSpaceCount = 0; yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}

<MACRO_DECLARATION> {
  {WHITE_SPACE}+                               { return WHITE_SPACE; }
  "macro"                                      { return MACRO; }
  {MACRO_NAME}                                 { operandSpaceCount = 0; yybegin(AFTER_LABEL); return ID; }
}

<AFTER_LABEL> {
  {COMMENT}                                    { return COMMENT; }

  "$"                                          { return DOLLAR; }
  ":"                                          { yybegin(IN_INSTRUCTION); return COLON; }
  "="                                          { yybegin(IN_OPERAND); return EQ_DIRECTIVE; } // EQ in <IN_INSTRUCTION>

  "macro"                                      { yybegin(AFTER_OPERAND); return MACRO; }
  "equ"                                        { yybegin(IN_OPERAND); return EQU; }
  "equr"                                       { yybegin(IN_OPERAND); return EQUR; }

  // whitespace followed NOT by instruction variants
  {WHITE_SPACE}+ / ({COMMENT} | "equ" | "equr" | "macro")  { return WHITE_SPACE; }

  {WHITE_SPACE}+                               { yybegin(IN_INSTRUCTION); return WHITE_SPACE; }
}


// after M68kDataSized instruction: data_size || WHITE_SPACE + operand
<AFTER_INSTRUCTION> {
  {WHITE_SPACE}+                               { operandSpaceCount = 1; yybegin(IN_OPERAND); return WHITE_SPACE; }

  {DATA_SIZE_S}                                { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_S; }
  {DATA_SIZE_B}                                { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_B; }
  {DATA_SIZE_W}                                { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_W; }
  {DATA_SIZE_L}                                { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_L; }
  ".\\0"                                       { operandSpaceCount = 0; yybegin(IN_OPERAND); return DOT_W; } // fake for macro parameter
}


// after all operands (if any): only whitespace/automatic comment
<AFTER_OPERAND> {
  {WHITE_SPACE}+                               { return WHITE_SPACE; }
  .+                                           { return COMMENT; }
}

// optimizations: separate case for without {DATA_SIZE}?
<IN_OPERAND> {
  // after 2nd WHITE_SPACE -> AFTER_OPERAND for automatic comment
  {WHITE_SPACE}+                               { if (operandSpaceCount++ == 1) { yybegin(AFTER_OPERAND); } return WHITE_SPACE; }

  {EOL_COMMENT}                                { return COMMENT; }

  "sp"                                         { return SP; }
  "sp" {DATA_SIZE_WITH_MACRO}                  { pushbackDataSize(); return SP; }
  "ssp"                                        { return SSP; }
  "usp"                                        { return USP; }
  "pc"                                         { return PC; }
  "sr"                                         { return SR; }
  "ccr"                                        { return CCR; }
  "dfc"                                        { return DFC; }
  "sfc"                                        { return SFC; }
  "vbr"                                        { return VBR; }

  d[0-7]                                       { return DATA_REGISTER; }
  d[0-7] {DATA_SIZE_WITH_MACRO}                { pushbackDataSize(); return DATA_REGISTER; }
  a[0-7]                                       { return ADDRESS_REGISTER; }
  a[0-7] {DATA_SIZE_WITH_MACRO}                { pushbackDataSize(); return ADDRESS_REGISTER; }

  // distinguish 'd6.l'/'$4000.l' vs. 'bra .l'/'dbf d0,.s'
  {DATA_SIZE_S}                                { if (afterSpaceOrComma()) { return ID; } return DOT_S; }
  {DATA_SIZE_B}                                { if (afterSpaceOrComma()) { return ID; } return DOT_B; }
  {DATA_SIZE_W}                                { if (afterSpaceOrComma()) { return ID; } return DOT_W; }
  {DATA_SIZE_L}                                { if (afterSpaceOrComma()) { return ID; } return DOT_L; }

  // must be after all register names
  {ID}                                         { return ID; }

  "."                                          { return DOT; }
  ","                                          { return COMMA; }
  "+"                                          { return PLUS; }
  "-"                                          { return MINUS; }
  "*"                                          { if (isCurrentPcSymbol()) { return ID; } return MUL; }
  "//"                                         { return SLASH_SLASH; }
  "/"                                          { return DIV; }
  "^"                                          { return POW; }
  "#"                                          { return HASH; }
  "~"                                          { return TILDE; }
  "("                                          { return L_PAREN; }
  ")"                                          { return R_PAREN; }
  "["                                          { return L_BRACKET; }
  "]"                                          { return R_BRACKET; }
  "!="                                         { return EXCLAMATION_EQ; }
  "!"                                          { return EXCLAMATION; }
  "%"                                          { return PERCENT; }
  "&&"                                         { return AMPERSAND_AMPERSAND; }
  "&"                                          { return AMPERSAND; }
  "\\"                                         { yybegin(MACRO_PARAMETER); return BACKSLASH; }
  "||"                                         { return PIPE_PIPE; }
  "|"                                          { return PIPE; }
  "<>"                                         { return LT_GT; }
  "<<"                                         { return LT_LT; }
  "<="                                         { return LT_EQ; }
  "<"                                          { return LT; }
  ">>"                                         { return GT_GT; }
  ">="                                         { return GT_EQ; }
  ">"                                          { return GT; }
  "=="                                         { return EQ_EQ; }
  "="                                          { return EQ; }
  ":"                                          { return COLON; }

  {DECNUMBER}                                  { return DEC_NUMBER; }
  {HEXNUMBER}                                  { return HEX_NUMBER; }
  {OCTNUMBER}                                  { return OCT_NUMBER; }
  {BINNUMBER}                                  { return BIN_NUMBER; }

  {SINGLE_QUOTED_STRING}                       { return STRING; }
  {DOUBLE_QUOTED_STRING}                       { return STRING; }
}

// '\0' or '\a'
<MACRO_PARAMETER> {
  \d                                           { yybegin(IN_OPERAND); return DEC_NUMBER; }
  [a-z]                                        { yybegin(IN_OPERAND); return ID; }
}

// 1st operand==STRING, optionally followed by IN_OPERAND
<STRING_DIRECTIVE> {
  // after 2nd WHITE_SPACE -> AFTER_OPERAND for automatic comment
  {WHITE_SPACE}+                               { operandSpaceCount++; return WHITE_SPACE; }

  {COMMENT}                                    { return COMMENT; }
  {EOL_COMMENT}                                { return COMMENT; }

  {SINGLE_QUOTED_STRING}                       { yybegin(IN_OPERAND); return STRING; }
  {DOUBLE_QUOTED_STRING}                       { yybegin(IN_OPERAND); return STRING; }
  {UNQUOTED_STRING}                            { yybegin(IN_OPERAND); return STRING; }
}

// Instructions must switch to:
// - AFTER_INSTRUCTION - if M68kDataSized, must have '{DATA_SIZE}?' suffix and call 'pushbackDataSizeIfPresent()'
// - IN_OPERAND - if >=1 operand
// - STRING_DIRECTIVE - if 1st operand==STRING
// - AFTER_OPERAND - if no operands
<IN_INSTRUCTION> {
  {WHITE_SPACE}+                               { return WHITE_SPACE; }

  // instruction itself can be macro param inside macro block
  "\\"                                         { yybegin(IN_OPERAND); return BACKSLASH; }

  "nop"                                        { yybegin(AFTER_OPERAND); return NOP; }
  "illegal"                                    { yybegin(AFTER_OPERAND); return ILLEGAL; }
  "reset"                                      { yybegin(AFTER_OPERAND); return RESET; }
  "stop"                                       { yybegin(IN_OPERAND); return STOP; }
  "trap"                                       { yybegin(IN_OPERAND); return TRAP; }
  "bkpt"                                       { yybegin(IN_OPERAND); return BKPT; }
  "trapv"                                      { yybegin(AFTER_OPERAND); return TRAPV; }
  "link" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return LINK; }
  "unlk"                                       { yybegin(IN_OPERAND); return UNLK; }

  "move" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MOVE; }
  "movea" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MOVEA; }
  "movec" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MOVEC; }
  "movem" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MOVEM; }
  "movep" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MOVEP; }
  "moveq" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MOVEQ; }
  "moves" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MOVES; }

  "tst" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return TST; }
  "tas" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return TAS; }
  "lea" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return LEA; }
  "pea" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return PEA; }
  "clr" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return CLR; }

  "jmp" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return JMP; }
  "jsr"                                        { yybegin(IN_OPERAND); return JSR; }
  "bsr" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BSR; }

  "rts"                                        { yybegin(AFTER_OPERAND); return RTS; }
  "rtd"                                        { yybegin(IN_OPERAND); return RTD; }
  "rte"                                        { yybegin(AFTER_OPERAND); return RTE; }
  "rtr"                                        { yybegin(AFTER_OPERAND); return RTR; }

  "add" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ADD; }
  "adda" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ADDA; }
  "addi" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ADDI; }
  "addq" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ADDQ; }
  "addx" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ADDX; }
  "sub" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SUB; }
  "suba" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SUBA; }
  "subi" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SUBI; }
  "subq" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SUBQ; }
  "subx" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SUBX; }
  "muls" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MULS; }
  "mulu" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return MULU; }
  "divs" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DIVS; }
  "divu" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DIVU; }

  "abcd" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ABCD; }
  "nbcd" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return NBCD; }
  "sbcd" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SBCD; }

  "and" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return AND; }
  "andi" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ANDI; }
  "or" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return OR; }
  "ori" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ORI; }
  "eor" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return EOR; }
  "eori" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return EORI; }

  "ext" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return EXT; }
  "neg" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return NEG; }
  "negx" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return NEGX; }
  "swap" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SWAP; }
  "not" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return NOT; }
  "chk" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return CHK; }
  "exg" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return EXG; }

  "cmp" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return CMP; }
  "cmpa" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return CMPA; }
  "cmpi" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return CMPI; }
  "cmpm" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return CMPM; }

  "bchg" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BCHG; }
  "bclr" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BCLR; }
  "bset" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BSET; }
  "btst" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BTST; }

  "bra" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BRA; }
  "bcs" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BCS; }
  "blo" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BLO; }
  "bls" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BLS; }
  "beq" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BEQ; }
  "bne" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BNE; }
  "bhi" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BHI; }
  "bcc" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BCC; }
  "bhs" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BHS; }
  "bpl" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BPL; }
  "bvc" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BVC; }
  "blt" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BLT; }
  "ble" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BLE; }
  "bgt" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BGT; }
  "bge" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BGE; }
  "bmi" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BMI; }
  "bvs" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BVS; }

  "dbra" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBRA; }
  "dbcs" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBCS; }
  "dblo" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBLO; }
  "dbls" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBLS; }
  "dbeq" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBEQ; }
  "dbne" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBNE; }
  "dbhi" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBHI; }
  "dbcc" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBCC; }
  "dbhs" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBHS; }
  "dbpl" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBPL; }
  "dbvc" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBVC; }
  "dblt" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBLT; }
  "dble" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBLE; }
  "dbgt" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBGT; }
  "dbge" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBGE; }
  "dbmi" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBMI; }
  "dbvs" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBVS; }
  "dbf" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBF; }
  "dbt" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DBT; }

  "seq" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SEQ; }
  "sne" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SNE; }
  "spl" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SPL; }
  "smi" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SMI; }
  "svc" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SVC; }
  "svs" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SVS; }
  "st" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ST; }
  "sf" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SF; }
  "sge" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SGE; }
  "sgt" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SGT; }
  "sle" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SLE; }
  "slt" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SLT; }
  "scc" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SCC; }
  "shi" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SHI; }
  "sls" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SLS; }
  "scs" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SCS; }
  "shs" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SHS; }
  "slo" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SLO; }

  "asl" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ASL; }
  "asr" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ASR; }
  "lsl" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return LSL; }
  "lsr" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return LSR; }
  "rol" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ROL; }
  "ror" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ROR; }
  "roxl" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ROXL; }
  "roxr" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return ROXR; }

  "bgnd"                                       { yybegin(AFTER_OPERAND); return BGND; }
  "lpstop" {DATA_SIZE}?                        { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return LPSTOP; }
  "tbls" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return TBLS; }
  "tblsn" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return TBLSN; }
  "tblu" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return TBLU; }
  "tblun" {DATA_SIZE}?                         { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return TBLUN; }

  "addwatch"                                   { yybegin(IN_OPERAND); return ADDWATCH; }
  "align"                                      { yybegin(IN_OPERAND); return ALIGN; }
  "assert"                                     { yybegin(IN_OPERAND); return ASSERT; }
  "auto"                                       { yybegin(AFTER_OPERAND); return AUTO; }
  "basereg"                                    { yybegin(IN_OPERAND); return BASEREG; }
  "blk" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return BLK; }
  "bss"                                        { yybegin(AFTER_OPERAND); return BSS; }
  "bss_c"                                      { yybegin(AFTER_OPERAND); return BSS_C; }
  "bss_f"                                      { yybegin(AFTER_OPERAND); return BSS_F; }
  "clrfo"                                      { yybegin(IN_OPERAND); return CLRFO; }
  "clrso"                                      { yybegin(IN_OPERAND); return CLRSO; }
  "cnop"                                       { yybegin(IN_OPERAND); return CNOP; }
  "code"                                       { yybegin(AFTER_OPERAND); return CODE; }
  "code_c"                                     { yybegin(AFTER_OPERAND); return CODE_C; }
  "code_f"                                     { yybegin(AFTER_OPERAND); return CODE_F; }
  "cseg"                                       { yybegin(AFTER_OPERAND); return CSEG; }
  "data"                                       { yybegin(AFTER_OPERAND); return DATA; }
  "data_c"                                     { yybegin(AFTER_OPERAND); return DATA_C; }
  "data_f"                                     { yybegin(AFTER_OPERAND); return DATA_F; }
  "dc" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DC; }
  "dcb" {DATA_SIZE}?                           { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DCB; }
  "dr" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DR; }
  "ds" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DS; }
  "dseg" {DATA_SIZE}?                          { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DSEG; }
  "dx" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return DX; }
  "echo"                                       { yybegin(STRING_DIRECTIVE); return ECHO; }
  "einline"                                    { yybegin(AFTER_OPERAND); return EINLINE; }
  "end"                                        { yybegin(AFTER_OPERAND); return END; }
  "endb"                                       { yybegin(IN_OPERAND); return ENDB; }
  "endr"                                       { yybegin(AFTER_OPERAND); return ENDR; }
  "erem"                                       { yybegin(AFTER_OPERAND); return EREM; }
  "even"                                       { yybegin(AFTER_OPERAND); return EVEN; }
  "fail"                                       { yybegin(STRING_DIRECTIVE); return FAIL; }
  "far"                                        { yybegin(AFTER_OPERAND); return FAR; }
  "fo" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return FO; }
  "idnt"                                       { yybegin(STRING_DIRECTIVE); return IDNT; }
  "incbin"                                     { yybegin(STRING_DIRECTIVE); return INCBIN; }
  "incdir"                                     { yybegin(STRING_DIRECTIVE); return INCDIR; }
  "include"                                    { yybegin(STRING_DIRECTIVE); return INCLUDE; }
  "initnear"                                   { yybegin(AFTER_OPERAND); return INITNEAR; }
  "inline"                                     { yybegin(AFTER_OPERAND); return INLINE; }
  "jumperr"                                    { yybegin(IN_OPERAND); return JUMPERR; }
  "jumpptr"                                    { yybegin(IN_OPERAND); return JUMPPTR; }
  "list"                                       { yybegin(AFTER_OPERAND); return LIST; }
  "llen"                                       { yybegin(IN_OPERAND); return LLEN; }
  "load"                                       { yybegin(IN_OPERAND); return LOAD; }
  "msource"                                    { yybegin(IN_OPERAND); return MSOURCE; }
  "mask2"                                      { yybegin(AFTER_OPERAND); return MASK2; }
  "near"                                       { yybegin(IN_OPERAND); return NEAR; }
  "near" {WHITE_SPACE} "code"                  { yybegin(AFTER_OPERAND); return NEAR_CODE; }
  "nolist"                                     { yybegin(AFTER_OPERAND); return NOLIST; }
  "nopage"                                     { yybegin(AFTER_OPERAND); return NOPAGE; }
  "odd"                                        { yybegin(AFTER_OPERAND); return ODD; }
  "offset"                                     { yybegin(IN_OPERAND); return OFFSET; }
  "opt"                                        { yybegin(IN_OPERAND); return OPT; }
  "org"                                        { yybegin(IN_OPERAND); return ORG; }
  "output"                                     { yybegin(STRING_DIRECTIVE); return OUTPUT; }
  "page"                                       { yybegin(AFTER_OPERAND); return PAGE; }
  "plen"                                       { yybegin(IN_OPERAND); return PLEN; }
  "popsection"                                 { yybegin(AFTER_OPERAND); return POPSECTION; }
  "printt"                                     { yybegin(STRING_DIRECTIVE); return PRINTT; }
  "printv"                                     { yybegin(IN_OPERAND); return PRINTV; }
  "pushsection"                                { yybegin(AFTER_OPERAND); return PUSHSECTION; }
  "reg"                                        { yybegin(IN_OPERAND); return REG; }
  "rem"                                        { yybegin(IN_REM); return REM; }
  "rept"                                       { yybegin(IN_OPERAND); return REPT; }
  "rs" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return RS; }
  "rseven"                                     { yybegin(AFTER_OPERAND); return RSEVEN; }
  "rsreset"                                    { yybegin(AFTER_OPERAND); return RSRESET; }
  "rsset"                                      { yybegin(IN_OPERAND); return RSSET; }
  "section"                                    { yybegin(IN_OPERAND); return SECTION; }
  "set"                                        { yybegin(IN_OPERAND); return SET; }
  "setfo"                                      { yybegin(IN_OPERAND); return SETFO; }
  "setso"                                      { yybegin(IN_OPERAND); return SETSO; }
  "spc"                                        { yybegin(IN_OPERAND); return SPC; }
  "so" {DATA_SIZE}?                            { yybegin(AFTER_INSTRUCTION); pushbackDataSizeIfPresent(); return SO; }
  "text"                                       { yybegin(AFTER_OPERAND); return TEXT; }
  "ttl"                                        { yybegin(STRING_DIRECTIVE); return TTL; }
  "xdef"                                       { yybegin(IN_OPERAND); return XDEF; }
  "xref"                                       { yybegin(IN_OPERAND); return XREF; }

  "endm"                                       { yybegin(AFTER_OPERAND); return ENDM; }
  "mexit"                                      { yybegin(AFTER_OPERAND); return MEXIT; }

  "if"                                         { yybegin(IN_OPERAND); return IF; }
  "if1"                                        { yybegin(IN_OPERAND); return IF1; }
  "if2"                                        { yybegin(IN_OPERAND); return IF2; }
  "ifb"                                        { yybegin(IN_OPERAND); return IFB; }
  "ifnb"                                       { yybegin(IN_OPERAND); return IFNB; }
  "ifc"                                        { yybegin(IN_OPERAND); return IFC; }
  "ifnc"                                       { yybegin(IN_OPERAND); return IFNC; }
  "ifd"                                        { yybegin(IN_OPERAND); return IFD; }
  "ifeq"                                       { yybegin(IN_OPERAND); return IFEQ; }
  "ifge"                                       { yybegin(IN_OPERAND); return IFGE; }
  "ifp1"                                       { yybegin(IN_OPERAND); return IFP1; }
  "ifpl"                                       { yybegin(IN_OPERAND); return IFPL; }
  "ifgt"                                       { yybegin(IN_OPERAND); return IFGT; }
  "ifmacrod"                                   { yybegin(IN_OPERAND); return IFMACROD; }
  "ifmacrond"                                  { yybegin(IN_OPERAND); return IFMACROND; }
  "ifnd"                                       { yybegin(IN_OPERAND); return IFND; }
  "ifne"                                       { yybegin(IN_OPERAND); return IFNE; }
  "ifle"                                       { yybegin(IN_OPERAND); return IFLE; }
  "iflt"                                       { yybegin(IN_OPERAND); return IFLT; }
  "ifmi"                                       { yybegin(IN_OPERAND); return IFMI; }
  "endc"                                       { yybegin(AFTER_OPERAND); return ENDC; }
  "endif"                                      { yybegin(AFTER_OPERAND); return ENDIF; }
  "else"                                       { yybegin(AFTER_OPERAND); return ELSE; }
  "elseif"                                     { yybegin(AFTER_OPERAND); return ELSEIF; }

  "cpu32"                                      { yybegin(AFTER_OPERAND); return CPU32; }
  "mc68000"                                    { yybegin(AFTER_OPERAND); return MC68000; }
  "mc68010"                                    { yybegin(AFTER_OPERAND); return MC68010; }
  "mc68020"                                    { yybegin(AFTER_OPERAND); return MC68020; }
  "mc68030"                                    { yybegin(AFTER_OPERAND); return MC68030; }
  "mc68040"                                    { yybegin(AFTER_OPERAND); return MC68040; }
  "mc68060"                                    { yybegin(AFTER_OPERAND); return MC68060; }
  "ac68080"                                    { yybegin(AFTER_OPERAND); return AC68080; }
  "machine"                                    { yybegin(IN_OPERAND); return MACHINE; }
  "fpu"                                        { yybegin(IN_OPERAND); return FPU; }

  // after 'label:', duplicated from <AFTER_LABEL>
  "macro"                                      { yybegin(AFTER_OPERAND); return MACRO; }
  "equ"                                        { yybegin(IN_OPERAND); return EQU; }
  "equr"                                       { yybegin(IN_OPERAND); return EQUR; }
  "="                                          { yybegin(IN_OPERAND); return EQ_DIRECTIVE; }

  // anything else is macro name
  {MACRO_NAME}                                 { yybegin(AFTER_INSTRUCTION); return MACRO_CALL_ID; }
  {MACRO_NAME} {DATA_SIZE}                     { yybegin(AFTER_INSTRUCTION); pushbackDataSize(); return MACRO_CALL_ID; }

  {COMMENT}                                    { return COMMENT; }
}

// `rem` until matching `erem`
<IN_REM> {
  {WHITE_SPACE}+ "erem"                        { yybegin(AFTER_OPERAND); return EREM; }
  {CRLF}                                       { return LINEFEED; }
  .+                                           { return COMMENT_REM; }
}

[^]                                            { return BAD_CHARACTER; }
