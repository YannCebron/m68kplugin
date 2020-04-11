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

import com.intellij.psi.tree.IElementType;

/**
 * @see M68kTokenGroups
 */
public abstract class M68kTokenTypes {

  public static final IElementType ABCD = new M68kTokenType("abcd");
  public static final IElementType ADD = new M68kTokenType("add");
  public static final IElementType ADDA = new M68kTokenType("adda");
  public static final IElementType ADDI = new M68kTokenType("addi");
  public static final IElementType ADDQ = new M68kTokenType("addq");
  public static final IElementType ADDX = new M68kTokenType("addx");
  public static final IElementType ADDRESS_REGISTER = new M68kTokenType("address_register");
  public static final IElementType AMPERSAND = new M68kTokenType("&");
  public static final IElementType AND = new M68kTokenType("and");
  public static final IElementType ANDI = new M68kTokenType("andi");
  public static final IElementType ASL = new M68kTokenType("asl");
  public static final IElementType ASR = new M68kTokenType("asr");
  public static final IElementType BACKSLASH = new M68kTokenType("\\");
  public static final IElementType BCC = new M68kTokenType("bcc");
  public static final IElementType BCHG = new M68kTokenType("bchg");
  public static final IElementType BCLR = new M68kTokenType("bclr");
  public static final IElementType BCS = new M68kTokenType("bcs");
  public static final IElementType BEQ = new M68kTokenType("beq");
  public static final IElementType BGE = new M68kTokenType("bge");
  public static final IElementType BGT = new M68kTokenType("bgt");
  public static final IElementType BHI = new M68kTokenType("bhi");
  public static final IElementType BHS = new M68kTokenType("bhs");
  public static final IElementType BIN_NUMBER = new M68kTokenType("bin_number");
  public static final IElementType BLE = new M68kTokenType("ble");
  public static final IElementType BLK = new M68kTokenType("blk");
  public static final IElementType BLO = new M68kTokenType("blo");
  public static final IElementType BLS = new M68kTokenType("bls");
  public static final IElementType BLT = new M68kTokenType("blt");
  public static final IElementType BMI = new M68kTokenType("bmi");
  public static final IElementType BNE = new M68kTokenType("bne");
  public static final IElementType BPL = new M68kTokenType("bpl");
  public static final IElementType BRA = new M68kTokenType("bra");
  public static final IElementType BSET = new M68kTokenType("bset");
  public static final IElementType BSR = new M68kTokenType("bsr");
  public static final IElementType BTST = new M68kTokenType("btst");
  public static final IElementType BVC = new M68kTokenType("bvc");
  public static final IElementType BVS = new M68kTokenType("bvs");
  public static final IElementType CCR = new M68kTokenType("ccr");
  public static final IElementType CHK = new M68kTokenType("chk");
  public static final IElementType CLR = new M68kTokenType("clr");
  public static final IElementType CMP = new M68kTokenType("cmp");
  public static final IElementType CMPA = new M68kTokenType("cmpa");
  public static final IElementType CMPI = new M68kTokenType("cmpi");
  public static final IElementType CMPM = new M68kTokenType("cmpm");
  public static final IElementType COLON = new M68kTokenType(":");
  public static final IElementType COMMA = new M68kTokenType(",");
  public static final IElementType COMMENT = new M68kTokenType("comment");
  public static final IElementType DATA_REGISTER = new M68kTokenType("data_register");
  public static final IElementType DBCC = new M68kTokenType("dbcc");
  public static final IElementType DBCS = new M68kTokenType("dbcs");
  public static final IElementType DBEQ = new M68kTokenType("dbeq");
  public static final IElementType DBF = new M68kTokenType("dbf");
  public static final IElementType DBGE = new M68kTokenType("dbge");
  public static final IElementType DBGT = new M68kTokenType("dbgt");
  public static final IElementType DBHI = new M68kTokenType("dbhi");
  public static final IElementType DBLE = new M68kTokenType("dble");
  public static final IElementType DBLS = new M68kTokenType("dbls");
  public static final IElementType DBLT = new M68kTokenType("dblt");
  public static final IElementType DBMI = new M68kTokenType("dbmi");
  public static final IElementType DBNE = new M68kTokenType("dbne");
  public static final IElementType DBPL = new M68kTokenType("dbpl");
  public static final IElementType DBRA = new M68kTokenType("dbra");
  public static final IElementType DBT = new M68kTokenType("dbt");
  public static final IElementType DBVC = new M68kTokenType("dbvc");
  public static final IElementType DBVS = new M68kTokenType("dbvs");
  public static final IElementType DC = new M68kTokenType("dc");
  public static final IElementType DCB = new M68kTokenType("dcb");
  public static final IElementType DEC_NUMBER = new M68kTokenType("dec_number");
  public static final IElementType DIV = new M68kTokenType("/");
  public static final IElementType DIVS = new M68kTokenType("divs");
  public static final IElementType DIVU = new M68kTokenType("divu");
  public static final IElementType DOT = new M68kTokenType(".");
  public static final IElementType DOT_B = new M68kTokenType(".b");
  public static final IElementType DOT_L = new M68kTokenType(".l");
  public static final IElementType DOT_S = new M68kTokenType(".s");
  public static final IElementType DOT_W = new M68kTokenType(".w");
  public static final IElementType DS = new M68kTokenType("ds");
  public static final IElementType EOR = new M68kTokenType("eor");
  public static final IElementType EORI = new M68kTokenType("eori");
  public static final IElementType ENDM = new M68kTokenType("endm");
  public static final IElementType EQ = new M68kTokenType("=");
  public static final IElementType EQU = new M68kTokenType("equ");
  public static final IElementType EQUR = new M68kTokenType("equr");
  public static final IElementType EVEN = new M68kTokenType("even");
  public static final IElementType EXG = new M68kTokenType("exg");
  public static final IElementType EXT = new M68kTokenType("ext");
  public static final IElementType HASH = new M68kTokenType("#");
  public static final IElementType HEX_NUMBER = new M68kTokenType("hex_number");
  public static final IElementType ID = new M68kTokenType("id");
  public static final IElementType ILLEGAL = new M68kTokenType("illegal");
  public static final IElementType INCBIN = new M68kTokenType("incbin");
  public static final IElementType INCDIR = new M68kTokenType("incdir");
  public static final IElementType INCLUDE = new M68kTokenType("include");
  public static final IElementType JMP = new M68kTokenType("jmp");
  public static final IElementType JSR = new M68kTokenType("jsr");
  public static final IElementType LEA = new M68kTokenType("lea");
  public static final IElementType LINK = new M68kTokenType("link");
  public static final IElementType LSL = new M68kTokenType("lsl");
  public static final IElementType LSR = new M68kTokenType("lsr");
  public static final IElementType L_BRACKET = new M68kTokenType("[");
  public static final IElementType L_PAREN = new M68kTokenType("(");
  public static final IElementType MACRO = new M68kTokenType("macro");
  public static final IElementType MINUS = new M68kTokenType("-");
  public static final IElementType MOVE = new M68kTokenType("move");
  public static final IElementType MOVEA = new M68kTokenType("movea");
  public static final IElementType MOVEM = new M68kTokenType("movem");
  public static final IElementType MOVEQ = new M68kTokenType("moveq");
  public static final IElementType MUL = new M68kTokenType("*");
  public static final IElementType MULS = new M68kTokenType("muls");
  public static final IElementType MULU = new M68kTokenType("mulu");
  public static final IElementType NBCD = new M68kTokenType("nbcd");
  public static final IElementType NEG = new M68kTokenType("neg");
  public static final IElementType NEGX = new M68kTokenType("negx");
  public static final IElementType NOP = new M68kTokenType("nop");
  public static final IElementType NOT = new M68kTokenType("not");
  public static final IElementType OCT_NUMBER = new M68kTokenType("oct_number");
  public static final IElementType ODD = new M68kTokenType("odd");
  public static final IElementType OPT = new M68kTokenType("opt");
  public static final IElementType OR = new M68kTokenType("or");
  public static final IElementType ORI = new M68kTokenType("ori");
  public static final IElementType PEA = new M68kTokenType("pea");
  public static final IElementType PC = new M68kTokenType("pc");
  public static final IElementType PERCENT = new M68kTokenType("%");
  public static final IElementType PIPE = new M68kTokenType("|");
  public static final IElementType PLUS = new M68kTokenType("+");
  public static final IElementType POW = new M68kTokenType("^");
  public static final IElementType RESET = new M68kTokenType("reset");
  public static final IElementType ROL = new M68kTokenType("rol");
  public static final IElementType ROR = new M68kTokenType("ror");
  public static final IElementType ROXL = new M68kTokenType("roxl");
  public static final IElementType ROXR = new M68kTokenType("roxr");
  public static final IElementType RS = new M68kTokenType("rs");
  public static final IElementType RSSET = new M68kTokenType("rsset");
  public static final IElementType RSRESET = new M68kTokenType("rsreset");
  public static final IElementType RTE = new M68kTokenType("rte");
  public static final IElementType RTR = new M68kTokenType("rtr");
  public static final IElementType RTS = new M68kTokenType("rts");
  public static final IElementType R_BRACKET = new M68kTokenType("]");
  public static final IElementType R_PAREN = new M68kTokenType(")");
  public static final IElementType SBCD = new M68kTokenType("sbcd");
  public static final IElementType SEMICOLON = new M68kTokenType(";");
  public static final IElementType SEQ = new M68kTokenType("seq");
  public static final IElementType SNE = new M68kTokenType("sne");
  public static final IElementType SPL = new M68kTokenType("spl");
  public static final IElementType SMI = new M68kTokenType("smi");
  public static final IElementType SVC = new M68kTokenType("svc");
  public static final IElementType SVS = new M68kTokenType("svs");
  public static final IElementType ST = new M68kTokenType("st");
  public static final IElementType SF = new M68kTokenType("sf");
  public static final IElementType SGE = new M68kTokenType("sge");
  public static final IElementType SGT = new M68kTokenType("sgt");
  public static final IElementType SLE = new M68kTokenType("sle");
  public static final IElementType SLT = new M68kTokenType("slt");
  public static final IElementType SCC = new M68kTokenType("scc");
  public static final IElementType SHI = new M68kTokenType("shi");
  public static final IElementType SHIFT_L = new M68kTokenType("<<");
  public static final IElementType SHIFT_R = new M68kTokenType(">>");
  public static final IElementType SLS = new M68kTokenType("sls");
  public static final IElementType SCS = new M68kTokenType("scs");
  public static final IElementType SP = new M68kTokenType("sp");
  public static final IElementType SSP = new M68kTokenType("ssp");
  public static final IElementType SR = new M68kTokenType("sr");
  public static final IElementType STOP = new M68kTokenType("stop");
  public static final IElementType STRING = new M68kTokenType("string");
  public static final IElementType SUB = new M68kTokenType("sub");
  public static final IElementType SUBA = new M68kTokenType("suba");
  public static final IElementType SUBI = new M68kTokenType("subi");
  public static final IElementType SUBQ = new M68kTokenType("subq");
  public static final IElementType SUBX = new M68kTokenType("subx");
  public static final IElementType SWAP = new M68kTokenType("swap");
  public static final IElementType TAS = new M68kTokenType("tas");
  public static final IElementType TILDE = new M68kTokenType("~");
  public static final IElementType TRAP = new M68kTokenType("trap");
  public static final IElementType TRAPV = new M68kTokenType("trapv");
  public static final IElementType TST = new M68kTokenType("tst");
  public static final IElementType UNDERSCORE = new M68kTokenType("_");
  public static final IElementType UNLK = new M68kTokenType("unlk");
  public static final IElementType USP = new M68kTokenType("usp");

}