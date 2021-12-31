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

package com.yanncebron.m68kplugin.amiga.hardware;

import org.jetbrains.annotations.NonNls;

import java.util.EnumSet;
import java.util.Set;

@SuppressWarnings("SpellCheckingInspection")
public enum M68kAmigaHardwareRegister {

  BLTDDAT("BLTDDAT", "DFF000", "Blitter dest. early read (dummy address)", "BLTDDAT", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.AGNUS_ALICE)),
  DMACONR("DMACONR", "DFF002", "DMA control (and blitter status) read", "DMACON", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.AGNUS_ALICE, Chip.PAULA)),
  VPOSR("VPOSR", "DFF004", "Read vert most sig. bits (and frame flop)", "VPOSR", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.AGNUS_ALICE)),
  VHPOSR("VHPOSR", "DFF006", "Read vert and horiz position of beam", "VHPOSR", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.AGNUS_ALICE)),
  DSKDATR("DSKDATR", "DFF008", "Disk data early read (dummy address)", "DSKDATR", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  JOY0DAT("JOY0DAT", "DFF00A", "Joystick-mouse 0 data (vert,horiz)", "JOYxDAT", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.DENISE_LISA)),
  JOT1DAT("JOT1DAT", "DFF00C", "Joystick-mouse 1 data (vert,horiz)", "JOYxDAT", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.DENISE_LISA)),
  CLXDAT("CLXDAT", "DFF00E", "Collision data reg. (read and clear)", "CLXDAT", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.DENISE_LISA)),
  ADKCONR("ADKCONR", "DFF010", "Audio,disk control register read", "ADKCON", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  POT0DAT("POT0DAT", "DFF012", "Pot counter pair 0 data (vert,horiz)", "POTxDAT", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  POT1DAT("POT1DAT", "DFF014", "Pot counter pair 1 data (vert,horiz)", "POTxDAT", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  POTINP("POTINP", "DFF016", "Pot pin data read", "POTINP", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  SERDATR("SERDATR", "DFF018", "Serial port data and status read", "SERDATR", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  DSKBYTR("DSKBYTR", "DFF01A", "Disk data byte and status read", "DSKBYTR", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  INTENAR("INTENAR", "DFF01C", "Interrupt enable bits read", "INTENA", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  INTREQR("INTREQR", "DFF01E", "Interrupt request bits read", "INTREQ", Chipset.OCS, true, Access.READ, EnumSet.of(Chip.PAULA)),
  DSKPTH("DSKPTH", "DFF020", "Disk pointer (high 5 bits)", "DSKPTH", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  DSKPTL("DSKPTL", "DFF022", "Disk pointer (low 15 bits)", "DSKPTH", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  DSKLEN("DSKLEN", "DFF024", "Disk length", "DSKLEN", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.PAULA)),
  DSKDAT("DSKDAT", "DFF026", "Disk DMA data write", "DSKDAT", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.PAULA)),
  REFPTR("REFPTR", "DFF028", "Refresh pointer", "REFPTR", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  VPOSW("VPOSW", "DFF02A", "Write vert most sig. bits (and frame flop)", "VPOSR", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  VHPOSW("VHPOSW", "DFF02C", "Write vert and horiz pos of beam", "VHPOSR", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  COPCON("COPCON", "DFF02E", "Coprocessor control reg (CDANG)", "COPCON", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SERDAT("SERDAT", "DFF030", "Serial port data and stop bits write", "SERDAT", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.PAULA)),
  SERPER("SERPER", "DFF032", "Serial port period and control", "SERPER", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.PAULA)),
  POTGO("POTGO", "DFF034", "Pot count start,pot pin drive enable data", "POTGO", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.PAULA)),
  JOYTEST("JOYTEST", "DFF036", "Write to all 4 joystick-mouse counters at once", "JOYTEST", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  STREQU("STREQU", "DFF038", "Strobe for horiz sync with VB and EQU", "STREQU", Chipset.OCS, true, Access.STROBE, EnumSet.of(Chip.DENISE_LISA)),
  STRVBL("STRVBL", "DFF03A", "Strobe for horiz sync with VB (vert blank)", "STREQU", Chipset.OCS, true, Access.STROBE, EnumSet.of(Chip.DENISE_LISA)),
  STRHOR("STRHOR", "DFF03C", "Strobe for horiz sync", "STREQU", Chipset.OCS, true, Access.STROBE, EnumSet.of(Chip.DENISE_LISA, Chip.PAULA)),
  STRLONG("STRLONG", "DFF03E", "Strobe for identification of long horiz line", "STREQU", Chipset.OCS, true, Access.STROBE, EnumSet.of(Chip.DENISE_LISA)),
  BLTCON0("BLTCON0", "DFF040", "Blitter control reg 0", "BLTCON0", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTCON1("BLTCON1", "DFF042", "Blitter control reg 1", "BLTCON0", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTAFWM("BLTAFWM", "DFF044", "Blitter first word mask for source A", "BLTAFWM", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTALWM("BLTALWM", "DFF046", "Blitter last word mask for source A", "BLTALWM", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTCPTH("BLTCPTH", "DFF048", "Blitter pointer to source C (high 5 bits)", "BLTxPTH", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTCPTL("BLTCPTL", "DFF04A", "Blitter pointer to source C (low 15 bits)", "BLTxPTL", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTBPTH("BLTBPTH", "DFF04C", "Blitter pointer to source B (high 5 bits)", "BLTxPTH", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTBPTL("BLTBPTL", "DFF04E", "Blitter pointer to source B (low 15 bits)", "BLTxPTL", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTAPTH("BLTAPTH", "DFF050", "Blitter pointer to source A (high 5 bits)", "BLTxPTH", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTAPTL("BLTAPTL", "DFF052", "Blitter pointer to source A (low 15 bits)", "BLTxPTL", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPTDPTH("BPTDPTH", "DFF054", "Blitter pointer to destn  D (high 5 bits)", "BLTxPTH", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTDPTL("BLTDPTL", "DFF056", "Blitter pointer to destn  D (low 15 bits)", "BLTxPTL", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTSIZE("BLTSIZE", "DFF058", "Blitter start and size (win/width,height)", "BLTSIZE", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTCON0L("BLTCON0L", "DFF05A", "Blitter control 0 lower 8 bits (minterms)", "BLTCON0", Chipset.ECS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTSIZV("BLTSIZV", "DFF05C", "Blitter V size (for 15 bit vert size)", "BLTSIZV", Chipset.ECS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTSIZH("BLTSIZH", "DFF05E", "Blitter H size & start (for 11 bit H size)", "BLTSIZH", Chipset.ECS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTCMOD("BLTCMOD", "DFF060", "Blitter modulo for source C", "BLTxMOD", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTBMOD("BLTBMOD", "DFF062", "Blitter modulo for source B", "BLTxMOD", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTAMOD("BLTAMOD", "DFF064", "Blitter modulo for source A", "BLTxMOD", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTDMOD("BLTDMOD", "DFF066", "Blitter modulo for destn  D", "BLTxMOD", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTCDAT("BLTCDAT", "DFF070", "Blitter source C data reg", "BLTxDAT", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTBDAT("BLTBDAT", "DFF072", "Blitter source B data reg", "BLTxDAT", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BLTADAT("BLTADAT", "DFF074", "Blitter source A data reg", "BLTxDAT", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPRHDAT("SPRHDAT", "DFF078", "Ext logic UHRES sprite pointer and data identifier", "SPRHDAT", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPLHDAT("BPLHDAT", "DFF07A", "Ext logic UHRES bit plane identifier", "BPLHDAT", Chipset.ECS, true, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  LISAID("LISAID", "DFF07C", "Chip revision level for Denise/Lisa", "LISAID", Chipset.ECS, true, Access.READ, EnumSet.of(Chip.DENISE_LISA)),
  DSKSYNC("DSKSYNC", "DFF07E", "Disk sync pattern reg for disk read", "DSKSYNC", Chipset.OCS, true, Access.WRITE, EnumSet.of(Chip.PAULA)),
  COP1LCH("COP1LCH", "DFF080", "Coprocessor first location reg (high 5 bits)", "COP1LCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  COP1LCL("COP1LCL", "DFF082", "Coprocessor first location reg (low 15 bits)", "COP1LCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  COP2LCH("COP2LCH", "DFF084", "Coprocessor second reg (high 5 bits)", "COP1LCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  COP2LCL("COP2LCL", "DFF086", "Coprocessor second reg (low 15 bits)", "COP1LCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  COPJMP1("COPJMP1", "DFF088", "Coprocessor restart at first location", "COPJMP1", Chipset.OCS, false, Access.STROBE, EnumSet.of(Chip.AGNUS_ALICE)),
  COPJMP2("COPJMP2", "DFF08A", "Coprocessor restart at second location", "COPJMP2", Chipset.OCS, false, Access.STROBE, EnumSet.of(Chip.AGNUS_ALICE)),
  COPINS("COPINS", "DFF08C", "Coprocessor inst fetch identify", "COPINS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  DIWSTRT("DIWSTRT", "DFF08E", "Display window start (upper left vert-hor pos)", "DIWSTRT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  DIWSTOP("DIWSTOP", "DFF090", "Display window stop (lower right vert-hor pos)", "DIWSTRT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  DDFSTRT("DDFSTRT", "DFF092", "Display bit plane data fetch start.hor pos", "DDFSTRT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  DDFSTOP("DDFSTOP", "DFF094", "Display bit plane data fetch stop.hor pos", "DDFSTRT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  DMACON("DMACON", "DFF096", "DMA control write (clear or set)", "DMACON", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.PAULA)),
  CLXCON("CLXCON", "DFF098", "Collision control", "CLXCON", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  INTENA("INTENA", "DFF09A", "Interrupt enable bits (clear or set bits)", "INTENA", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  INTREQ("INTREQ", "DFF09C", "Interrupt request bits (clear or set bits)", "INTREQ", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  ADKCON("ADKCON", "DFF09E", "Audio,disk,UART,control", "ADKCON", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD0LCH("AUD0LCH", "DFF0A0", "Audio channel 0 location (high 5 bits)", "AUDxLCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD0LCL("AUD0LCL", "DFF0A2", "Audio channel 0 location (low 15 bits)", "AUDxLCL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD0LEN("AUD0LEN", "DFF0A4", "Audio channel 0 length", "AUDxLEN", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD0PER("AUD0PER", "DFF0A6", "Audio channel 0 period", "AUDxPER", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD0VOL("AUD0VOL", "DFF0A8", "Audio channel 0 volume", "AUDxVOL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD0DAT("AUD0DAT", "DFF0AA", "Audio channel 0 data", "AUDxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD1LCH("AUD1LCH", "DFF0B0", "Audio channel 1 location (high 5 bits)", "AUDxLCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD1LCL("AUD1LCL", "DFF0B2", "Audio channel 1 location (low 15 bits)", "AUDxLCL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD1LEN("AUD1LEN", "DFF0B4", "Audio channel 1 length", "AUDxLEN", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD1PER("AUD1PER", "DFF0B6", "Audio channel 1 period", "AUDxPER", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD1VOL("AUD1VOL", "DFF0B8", "Audio channel 1 volume", "AUDxVOL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD1DAT("AUD1DAT", "DFF0BA", "Audio channel 1 data", "AUDxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD2LCH("AUD2LCH", "DFF0C0", "Audio channel 2 location (high 5 bits)", "AUDxLCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD2LCL("AUD2LCL", "DFF0C2", "Audio channel 2 location (low 15 bits)", "AUDxLCL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD2LEN("AUD2LEN", "DFF0C4", "Audio channel 2 length", "AUDxLEN", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD2PER("AUD2PER", "DFF0C6", "Audio channel 2 period", "AUDxPER", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD2VOL("AUD2VOL", "DFF0C8", "Audio channel 2 volume", "AUDxVOL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD2DAT("AUD2DAT", "DFF0CA", "Audio channel 2 data", "AUDxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD3LCH("AUD3LCH", "DFF0D0", "Audio channel 3 location (high 5 bits)", "AUDxLCH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD3LCL("AUD3LCL", "DFF0D2", "Audio channel 3 location (low 15 bits)", "AUDxLCL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  AUD3LEN("AUD3LEN", "DFF0D4", "Audio channel 3 length", "AUDxLEN", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD3PER("AUD3PER", "DFF0D6", "Audio channel 3 period", "AUDxPER", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD3VOL("AUD3VOL", "DFF0D8", "Audio channel 3 volume", "AUDxVOL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  AUD3DAT("AUD3DAT", "DFF0DA", "Audio channel 3 data", "AUDxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.PAULA)),
  BPL1PTH("BPL1PTH", "DFF0E0", "Bit plane pointer 1 (high 5 bits)", "BPLxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL1PTL("BPL1PTL", "DFF0E2", "Bit plane pointer 1 (low 15 bits)", "BPLxPTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL2PTH("BPL2PTH", "DFF0E4", "Bit plane pointer 2 (high 5 bits)", "BPLxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL2PTL("BPL2PTL", "DFF0E6", "Bit plane pointer 2 (low 15 bits)", "BPLxPTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL3PTH("BPL3PTH", "DFF0E8", "Bit plane pointer 3 (high 5 bits)", "BPLxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL3PTL("BPL3PTL", "DFF0EA", "Bit plane pointer 3 (low 15 bits)", "BPLxPTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL4PTH("BPL4PTH", "DFF0EC", "Bit plane pointer 4 (high 5 bits)", "BPLxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL4PTL("BPL4PTL", "DFF0EE", "Bit plane pointer 4 (low 15 bits)", "BPLxPTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL5PTH("BPL5PTH", "DFF0F0", "Bit plane pointer 5 (high 5 bits)", "BPLxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL5PTL("BPL5PTL", "DFF0F2", "Bit plane pointer 5 (low 15 bits)", "BPLxPTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL6PTH("BPL6PTH", "DFF0F4", "Bit plane pointer 6 (high 5 bits)", "BPLxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL6PTL("BPL6PTL", "DFF0F6", "Bit plane pointer 6 (low 15 bits)", "BPLxPTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL7PTH("BPL7PTH", "DFF0F8", "Bit plane pointer 7 (high 5 bits)", "BPLxPTH", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL7PTL("BPL7PTL", "DFF0FA", "Bit plane pointer 7 (low 15 bits)", "BPLxPTL", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL8PTH("BPL8PTH", "DFF0FC", "Bit plane pointer 8 (high 5 bits)", "BPLxPTH", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL8PTL("BPL8PTL", "DFF0FE", "Bit plane pointer 8 (low 15 bits)", "BPLxPTL", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPLCON0("BPLCON0", "DFF100", "Bit plane control reg (misc control bits)", "BPLCON0", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  BPLCON1("BPLCON1", "DFF102", "Bit plane control reg (scroll val PF1,PF2)", "BPLCON1", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPLCON2("BPLCON2", "DFF104", "Bit plane control reg (priority control)", "BPLCON2", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPLCON3("BPLCON3", "DFF106", "Bit plane control reg (enhanced features)", "BPLCON3", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL1MOD("BPL1MOD", "DFF108", "Bit plane modulo (odd planes or active-fetch lines if bitplane scan-doubling is enabled)", "BPLxMOD", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPL2MOD("BPL2MOD", "DFF10A", "Bit plane modulo (even planes or inactive-fetch lines if bitplane scan-doubling is enabled)", "BPLxMOD", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPLCON4("BPLCON4", "DFF10C", "Bit plane control reg (bitplane and sprite masks)", "BPLCON4", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  CLXCON2("CLXCON2", "DFF10E", "Extended collision control reg", "CLXCON2", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL1DAT("BPL1DAT", "DFF110", "Bit plane 1 data (parallel to serial convert)", "BPLxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL2DAT("BPL2DAT", "DFF112", "Bit plane 2 data (parallel to serial convert)", "BPLxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL3DAT("BPL3DAT", "DFF114", "Bit plane 3 data (parallel to serial convert)", "BPLxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL4DAT("BPL4DAT", "DFF116", "Bit plane 4 data (parallel to serial convert)", "BPLxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL5DAT("BPL5DAT", "DFF118", "Bit plane 5 data (parallel to serial convert)", "BPLxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL6DAT("BPL6DAT", "DFF11A", "Bit plane 6 data (parallel to serial convert)", "BPLxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL7DAT("BPL7DAT", "DFF11C", "Bit plane 7 data (parallel to serial convert)", "BPLxDAT", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  BPL8DAT("BPL8DAT", "DFF11E", "Bit plane 8 data (parallel to serial convert)", "BPLxDAT", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR0PTH("SPR0PTH", "DFF120", "Sprite 0 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR0PTL("SPR0PTL", "DFF122", "Sprite 0 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR1PTH("SPR1PTH", "DFF124", "Sprite 1 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR1PTL("SPR1PTL", "DFF126", "Sprite 1 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR2PTH("SPR2PTH", "DFF128", "Sprite 2 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR2PTL("SPR2PTL", "DFF12A", "Sprite 2 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR3PTH("SPR3PTH", "DFF12C", "Sprite 3 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR3PTL("SPR3PTL", "DFF12E", "Sprite 3 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR4PTH("SPR4PTH", "DFF130", "Sprite 4 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR4PTL("SPR4PTL", "DFF132", "Sprite 4 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR5PTH("SPR5PTH", "DFF134", "Sprite 5 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR5PTL("SPR5PTL", "DFF136", "Sprite 5 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR6PTH("SPR6PTH", "DFF138", "Sprite 6 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR6PTL("SPR6PTL", "DFF13A", "Sprite 6 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR7PTH("SPR7PTH", "DFF13C", "Sprite 7 pointer (high 5 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR7PTL("SPR7PTL", "DFF13E", "Sprite 7 pointer (low 15 bits)", "SPRxPTH", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPR0POS("SPR0POS", "DFF140", "Sprite 0 vert-horiz start pos data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR0CTL("SPR0CTL", "DFF142", "Sprite 0 position and control data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR0DATA("SPR0DATA", "DFF144", "Sprite 0 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR0DATB("SPR0DATB", "DFF146", "Sprite 0 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR1POS("SPR1POS", "DFF148", "Sprite 1 vert-horiz start pos data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR1CTL("SPR1CTL", "DFF14A", "Sprite 1 position and control data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR1DATA("SPR1DATA", "DFF14C", "Sprite 1 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR1DATB("SPR1DATB", "DFF14E", "Sprite 1 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR2POS("SPR2POS", "DFF150", "Sprite 2 vert-horiz start pos data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR2CTL("SPR2CTL", "DFF152", "Sprite 2 position and control data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR2DATA("SPR2DATA", "DFF154", "Sprite 2 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR2DATB("SPR2DATB", "DFF156", "Sprite 2 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR3POS("SPR3POS", "DFF158", "Sprite 3 vert-horiz start pos data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR3CTL("SPR3CTL", "DFF15A", "Sprite 3 position and control data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR3DATA("SPR3DATA", "DFF15C", "Sprite 3 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR3DATB("SPR3DATB", "DFF15E", "Sprite 3 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR4POS("SPR4POS", "DFF160", "Sprite 4 vert-horiz start pos data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR4CTL("SPR4CTL", "DFF162", "Sprite 4 position and control data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR4DATA("SPR4DATA", "DFF164", "Sprite 4 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR4DATB("SPR4DATB", "DFF166", "Sprite 4 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR5POS("SPR5POS", "DFF168", "Sprite 5 vert-horiz start pos data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR5CTL("SPR5CTL", "DFF16A", "Sprite 5 position and control data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR5DATA("SPR5DATA", "DFF16C", "Sprite 5 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR5DATB("SPR5DATB", "DFF16E", "Sprite 5 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR6POS("SPR6POS", "DFF170", "Sprite 6 vert-horiz start pos data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR6CTL("SPR6CTL", "DFF172", "Sprite 6 position and control data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR6DATA("SPR6DATA", "DFF174", "Sprite 6 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR6DATB("SPR6DATB", "DFF176", "Sprite 6 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR7POS("SPR7POS", "DFF178", "Sprite 7 vert-horiz start pos data", "SPRxPOS", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR7CTL("SPR7CTL", "DFF17A", "Sprite 7 position and control data", "SPRxCTL", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  SPR7DATA("SPR7DATA", "DFF17C", "Sprite 7 image data register A", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  SPR7DATB("SPR7DATB", "DFF17E", "Sprite 7 image data register B", "SPRxDAT", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR00("COLOR00", "DFF180", "Color table 00", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR01("COLOR01", "DFF182", "Color table 01", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR02("COLOR02", "DFF184", "Color table 02", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR03("COLOR03", "DFF186", "Color table 03", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR04("COLOR04", "DFF188", "Color table 04", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR05("COLOR05", "DFF18A", "Color table 05", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR06("COLOR06", "DFF18C", "Color table 06", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR07("COLOR07", "DFF18E", "Color table 07", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR08("COLOR08", "DFF190", "Color table 08", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR09("COLOR09", "DFF192", "Color table 09", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR10("COLOR10", "DFF194", "Color table 10", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR11("COLOR11", "DFF196", "Color table 11", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR12("COLOR12", "DFF198", "Color table 12", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR13("COLOR13", "DFF19A", "Color table 13", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR14("COLOR14", "DFF19C", "Color table 14", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR15("COLOR15", "DFF19E", "Color table 15", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR16("COLOR16", "DFF1A0", "Color table 16", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR17("COLOR17", "DFF1A2", "Color table 17", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR18("COLOR18", "DFF1A4", "Color table 18", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR19("COLOR19", "DFF1A6", "Color table 19", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR20("COLOR20", "DFF1A8", "Color table 20", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR21("COLOR21", "DFF1AA", "Color table 21", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR22("COLOR22", "DFF1AC", "Color table 22", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR23("COLOR23", "DFF1AE", "Color table 23", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR24("COLOR24", "DFF1B0", "Color table 24", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR25("COLOR25", "DFF1B2", "Color table 25", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR26("COLOR26", "DFF1B4", "Color table 26", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR27("COLOR27", "DFF1B6", "Color table 27", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR28("COLOR28", "DFF1B8", "Color table 28", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR29("COLOR29", "DFF1BA", "Color table 29", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR30("COLOR30", "DFF1BC", "Color table 30", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  COLOR31("COLOR31", "DFF1BE", "Color table 31", "COLORx", Chipset.OCS, false, Access.WRITE, EnumSet.of(Chip.DENISE_LISA)),
  HTOTAL("HTOTAL", "DFF1C0", "Highest number count in horiz line (VARBEAMEN = 1)", "HTOTAL", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  HSSTOP("HSSTOP", "DFF1C2", "Horiz line pos for HSYNC stop", "HSSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  HBSTRT("HBSTRT", "DFF1C4", "Horiz line pos for HBLANK start", "HBSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  HBSTOP("HBSTOP", "DFF1C6", "Horiz line pos for HBLANK stop", "HBSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  VTOTAL("VTOTAL", "DFF1C8", "Highest numbered vertical line (VARBEAMEN = 1)", "VSSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  VSSTOP("VSSTOP", "DFF1CA", "Vert line for VBLANK start", "VSSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  VBSTRT("VBSTRT", "DFF1CC", "Vert line for VBLANK start", "VBSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  VBSTOP("VBSTOP", "DFF1CE", "Vert line for VBLANK stop", "VBSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPRHSTRT("SPRHSTRT", "DFF1D0", "UHRES sprite vertical start", "SPRHSTRT", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPRHSTOP("SPRHSTOP", "DFF1D2", "UHRES sprite vertical stop", "SPRHSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPLHSTRT("BPLHSTRT", "DFF1D4", "UHRES bit plane vertical stop", "BPLHSTRT", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPLHSTOP("BPLHSTOP", "DFF1D6", "UHRES bit plane vertical stop", "BPLHSTOP", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  HHPOSW("HHPOSW", "DFF1D8", "DUAL mode hires H beam counter write", "HHPOSR", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  HHPOSR("HHPOSR", "DFF1DA", "DUAL mode hires H beam counter read", "HHPOSR", Chipset.ECS, false, Access.READ, EnumSet.of(Chip.AGNUS_ALICE)),
  BEAMCON0("BEAMCON0", "DFF1DC", "Beam counter control register (SHRES,UHRES,PAL)", "BEAMCON0", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  HSSTRT("HSSTRT", "DFF1DE", "Horizontal sync start (VARHSY)", "HSSTRT", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  VSSTRT("VSSTRT", "DFF1E0", "Vertical sync start (VARVSY)", "HSSTRT", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  HCENTER("HCENTER", "DFF1E2", "Horizontal pos for vsync on interlace", "HCENTER", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  DIWHIGH("DIWHIGH", "DFF1E4", "Display window upper bits for start/stop", "DIWHIGH", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA)),
  BPLHMOD("BPLHMOD", "DFF1E6", "UHRES bit plane modulo", "BPLHMOD", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPRHPTH("SPRHPTH", "DFF1E8", "UHRES sprite pointer (high 5 bits)", "SPRHPTH", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  SPRHPTL("SPRHPTL", "DFF1EA", "UHRES sprite pointer (low 15 bits)", "SPRHPTH", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPLHPTH("BPLHPTH", "DFF1EC", "VRam (UHRES) bitplane pointer (hi 5 bits)", "BPLHPTH", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  BPLHPTL("BPLHPTL", "DFF1EE", "VRam (UHRES) bitplane pointer (lo 15 bits)", "BPLHPTH", Chipset.ECS, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE)),
  FMODE("FMODE", "DFF1FC", "Fetch mode register", "FMODE", Chipset.AGA, false, Access.WRITE, EnumSet.of(Chip.AGNUS_ALICE, Chip.DENISE_LISA))
  ;

  private final String name;
  private final String address;
  private final String description;
  private final String descriptionFileName;
  private final Chipset chipset;
  private final boolean copperDanger;
  private final Access access;
  private final Set<Chip> chips;

  M68kAmigaHardwareRegister(String name,
                            String address,
                            String description,
                            String descriptionFileName,
                            Chipset chipset,
                            boolean copperDanger,
                            Access access,
                            Set<Chip> chips) {
    this.name = name;
    this.address = address;
    this.description = description;
    this.descriptionFileName = descriptionFileName;
    this.chipset = chipset;
    this.copperDanger = copperDanger;
    this.access = access;
    this.chips = chips;
  }

  @NonNls
  public String getName() {
    return name;
  }

  @NonNls
  public String getAddress() {
    return address;
  }

  @NonNls
  public String getDescription() {
    return description;
  }

  public String getDescriptionFileName() {
    return descriptionFileName;
  }

  public Chipset getChipset() {
    return chipset;
  }

  public boolean isCopperDanger() {
    return copperDanger;
  }

  public Access getAccess() {
    return access;
  }

  public Set<Chip> getChips() {
    return chips;
  }

  enum Chipset {
    OCS("OCS", "Original Chip Set"),
    ECS("ECS", "Enhanced Chip Set"),
    AGA("AGA", "Advanced Graphics Architecture");

    private final String displayName;
    private final String fullName;

    Chipset(String displayName, String fullName) {
      this.displayName = displayName;
      this.fullName = fullName;
    }

    public String getDisplayName() {
      return displayName;
    }

    public String getFullName() {
      return fullName;
    }
  }

  enum Access {
    WRITE("Write"),
    READ("Read"),
    EARLY_READ("Early read"),
    STROBE("Strobe");

    private final String displayName;

    Access(String displayName) {
      this.displayName = displayName;
    }

    public String getDisplayName() {
      return displayName;
    }
  }

  enum Chip {
    AGNUS_ALICE("Agnus/Alice"),
    DENISE_LISA("Denise/Lisa"),
    PAULA("Paula");

    private final String displayName;

    Chip(String displayName) {
      this.displayName = displayName;
    }

    public String getDisplayName() {
      return displayName;
    }
  }
}
