********************************************************************************

* M68kMnemonic{abcd, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         abcd        d0,d0
         abcd.b      d0,d0

* M68kMnemonic{abcd, deprecated=false, src=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, dst=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         abcd        -(a0),-(a0)
         abcd.b      -(a0),-(a0)


********************************************************************************

* M68kMnemonic{add, deprecated=false, src=DATA, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         add         d0,d0
         add.b       d0,d0
         add.w       d0,d0
         add.l       d0,d0
         add         #42,d0
         add.b       #42,d0
         add.w       #42,d0
         add.l       #42,d0
         add         (a0),d0
         add.b       (a0),d0
         add.w       (a0),d0
         add.l       (a0),d0
         add         (a0)+,d0
         add.b       (a0)+,d0
         add.w       (a0)+,d0
         add.l       (a0)+,d0
         add         -(a0),d0
         add.b       -(a0),d0
         add.w       -(a0),d0
         add.l       -(a0),d0
         add         42(a0),d0
         add.b       42(a0),d0
         add.w       42(a0),d0
         add.l       42(a0),d0
         add         (-42,a0),d0
         add.b       (-42,a0),d0
         add.w       (-42,a0),d0
         add.l       (-42,a0),d0
         add         12(a0,d0),d0
         add.b       12(a0,d0),d0
         add.w       12(a0,d0),d0
         add.l       12(a0,d0),d0
         add         (12,a0,a0),d0
         add.b       (12,a0,a0),d0
         add.w       (12,a0,a0),d0
         add.l       (12,a0,a0),d0
         add         $4000,d0
         add.b       $4000,d0
         add.w       $4000,d0
         add.l       $4000,d0
         add         $4000.W,d0
         add.b       $4000.W,d0
         add.w       $4000.W,d0
         add.l       $4000.W,d0
         add         $4000.L,d0
         add.b       $4000.L,d0
         add.w       $4000.L,d0
         add.l       $4000.L,d0
         add         (PC),d0
         add.b       (PC),d0
         add.w       (PC),d0
         add.l       (PC),d0
         add         66(PC),d0
         add.b       66(PC),d0
         add.w       66(PC),d0
         add.l       66(PC),d0
         add         (-66,PC),d0
         add.b       (-66,PC),d0
         add.w       (-66,PC),d0
         add.l       (-66,PC),d0
         add         66(PC,d0),d0
         add.b       66(PC,d0),d0
         add.w       66(PC,d0),d0
         add.l       66(PC,d0),d0
         add         (66,PC,a0),d0
         add.b       (66,PC,a0),d0
         add.w       (66,PC,a0),d0
         add.l       (66,PC,a0),d0

* M68kMnemonic{add, deprecated=false, src=ADDRESS_REGISTER, dst=DATA_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         add         a0,d0
         add.w       a0,d0
         add.l       a0,d0

* M68kMnemonic{add, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_MEMORY, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         add         d0,(a0)
         add.b       d0,(a0)
         add.w       d0,(a0)
         add.l       d0,(a0)
         add         d0,(a0)+
         add.b       d0,(a0)+
         add.w       d0,(a0)+
         add.l       d0,(a0)+
         add         d0,-(a0)
         add.b       d0,-(a0)
         add.w       d0,-(a0)
         add.l       d0,-(a0)
         add         d0,42(a0)
         add.b       d0,42(a0)
         add.w       d0,42(a0)
         add.l       d0,42(a0)
         add         d0,(-42,a0)
         add.b       d0,(-42,a0)
         add.w       d0,(-42,a0)
         add.l       d0,(-42,a0)
         add         d0,12(a0,d0)
         add.b       d0,12(a0,d0)
         add.w       d0,12(a0,d0)
         add.l       d0,12(a0,d0)
         add         d0,(12,a0,a0)
         add.b       d0,(12,a0,a0)
         add.w       d0,(12,a0,a0)
         add.l       d0,(12,a0,a0)
         add         d0,$4000
         add.b       d0,$4000
         add.w       d0,$4000
         add.l       d0,$4000
         add         d0,$4000.W
         add.b       d0,$4000.W
         add.w       d0,$4000.W
         add.l       d0,$4000.W
         add         d0,$4000.L
         add.b       d0,$4000.L
         add.w       d0,$4000.L
         add.l       d0,$4000.L

* M68kMnemonic{add, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         add         d0,a0
         add.w       d0,a0
         add.l       d0,a0
         add         a0,a0
         add.w       a0,a0
         add.l       a0,a0
         add         (a0),a0
         add.w       (a0),a0
         add.l       (a0),a0
         add         (a0)+,a0
         add.w       (a0)+,a0
         add.l       (a0)+,a0
         add         -(a0),a0
         add.w       -(a0),a0
         add.l       -(a0),a0
         add         42(a0),a0
         add.w       42(a0),a0
         add.l       42(a0),a0
         add         (-42,a0),a0
         add.w       (-42,a0),a0
         add.l       (-42,a0),a0
         add         12(a0,d0),a0
         add.w       12(a0,d0),a0
         add.l       12(a0,d0),a0
         add         (12,a0,a0),a0
         add.w       (12,a0,a0),a0
         add.l       (12,a0,a0),a0
         add         $4000,a0
         add.w       $4000,a0
         add.l       $4000,a0
         add         $4000.W,a0
         add.w       $4000.W,a0
         add.l       $4000.W,a0
         add         $4000.L,a0
         add.w       $4000.L,a0
         add.l       $4000.L,a0
         add         (PC),a0
         add.w       (PC),a0
         add.l       (PC),a0
         add         66(PC),a0
         add.w       66(PC),a0
         add.l       66(PC),a0
         add         (-66,PC),a0
         add.w       (-66,PC),a0
         add.l       (-66,PC),a0
         add         66(PC,d0),a0
         add.w       66(PC,d0),a0
         add.l       66(PC,d0),a0
         add         (66,PC,a0),a0
         add.w       (66,PC,a0),a0
         add.l       (66,PC,a0),a0
         add         #42,a0
         add.w       #42,a0
         add.l       #42,a0

* M68kMnemonic{add, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         add         #42,d0
         add.b       #42,d0
         add.w       #42,d0
         add.l       #42,d0
         add         #42,(a0)
         add.b       #42,(a0)
         add.w       #42,(a0)
         add.l       #42,(a0)
         add         #42,(a0)+
         add.b       #42,(a0)+
         add.w       #42,(a0)+
         add.l       #42,(a0)+
         add         #42,-(a0)
         add.b       #42,-(a0)
         add.w       #42,-(a0)
         add.l       #42,-(a0)
         add         #42,42(a0)
         add.b       #42,42(a0)
         add.w       #42,42(a0)
         add.l       #42,42(a0)
         add         #42,(-42,a0)
         add.b       #42,(-42,a0)
         add.w       #42,(-42,a0)
         add.l       #42,(-42,a0)
         add         #42,12(a0,d0)
         add.b       #42,12(a0,d0)
         add.w       #42,12(a0,d0)
         add.l       #42,12(a0,d0)
         add         #42,(12,a0,a0)
         add.b       #42,(12,a0,a0)
         add.w       #42,(12,a0,a0)
         add.l       #42,(12,a0,a0)
         add         #42,$4000
         add.b       #42,$4000
         add.w       #42,$4000
         add.l       #42,$4000
         add         #42,$4000.W
         add.b       #42,$4000.W
         add.w       #42,$4000.W
         add.l       #42,$4000.W
         add         #42,$4000.L
         add.b       #42,$4000.L
         add.w       #42,$4000.L
         add.l       #42,$4000.L


********************************************************************************

* M68kMnemonic{adda, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         adda        d0,a0
         adda.w      d0,a0
         adda.l      d0,a0
         adda        a0,a0
         adda.w      a0,a0
         adda.l      a0,a0
         adda        (a0),a0
         adda.w      (a0),a0
         adda.l      (a0),a0
         adda        (a0)+,a0
         adda.w      (a0)+,a0
         adda.l      (a0)+,a0
         adda        -(a0),a0
         adda.w      -(a0),a0
         adda.l      -(a0),a0
         adda        42(a0),a0
         adda.w      42(a0),a0
         adda.l      42(a0),a0
         adda        (-42,a0),a0
         adda.w      (-42,a0),a0
         adda.l      (-42,a0),a0
         adda        12(a0,d0),a0
         adda.w      12(a0,d0),a0
         adda.l      12(a0,d0),a0
         adda        (12,a0,a0),a0
         adda.w      (12,a0,a0),a0
         adda.l      (12,a0,a0),a0
         adda        $4000,a0
         adda.w      $4000,a0
         adda.l      $4000,a0
         adda        $4000.W,a0
         adda.w      $4000.W,a0
         adda.l      $4000.W,a0
         adda        $4000.L,a0
         adda.w      $4000.L,a0
         adda.l      $4000.L,a0
         adda        (PC),a0
         adda.w      (PC),a0
         adda.l      (PC),a0
         adda        66(PC),a0
         adda.w      66(PC),a0
         adda.l      66(PC),a0
         adda        (-66,PC),a0
         adda.w      (-66,PC),a0
         adda.l      (-66,PC),a0
         adda        66(PC,d0),a0
         adda.w      66(PC,d0),a0
         adda.l      66(PC,d0),a0
         adda        (66,PC,a0),a0
         adda.w      (66,PC,a0),a0
         adda.l      (66,PC,a0),a0
         adda        #42,a0
         adda.w      #42,a0
         adda.l      #42,a0


********************************************************************************

* M68kMnemonic{addi, deprecated=false, src=IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         addi        #42,d0
         addi.b      #42,d0
         addi.w      #42,d0
         addi.l      #42,d0

* M68kMnemonic{addi, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         addi        #42,d0
         addi.b      #42,d0
         addi.w      #42,d0
         addi.l      #42,d0
         addi        #42,(a0)
         addi.b      #42,(a0)
         addi.w      #42,(a0)
         addi.l      #42,(a0)
         addi        #42,(a0)+
         addi.b      #42,(a0)+
         addi.w      #42,(a0)+
         addi.l      #42,(a0)+
         addi        #42,-(a0)
         addi.b      #42,-(a0)
         addi.w      #42,-(a0)
         addi.l      #42,-(a0)
         addi        #42,42(a0)
         addi.b      #42,42(a0)
         addi.w      #42,42(a0)
         addi.l      #42,42(a0)
         addi        #42,(-42,a0)
         addi.b      #42,(-42,a0)
         addi.w      #42,(-42,a0)
         addi.l      #42,(-42,a0)
         addi        #42,12(a0,d0)
         addi.b      #42,12(a0,d0)
         addi.w      #42,12(a0,d0)
         addi.l      #42,12(a0,d0)
         addi        #42,(12,a0,a0)
         addi.b      #42,(12,a0,a0)
         addi.w      #42,(12,a0,a0)
         addi.l      #42,(12,a0,a0)
         addi        #42,$4000
         addi.b      #42,$4000
         addi.w      #42,$4000
         addi.l      #42,$4000
         addi        #42,$4000.W
         addi.b      #42,$4000.W
         addi.w      #42,$4000.W
         addi.l      #42,$4000.W
         addi        #42,$4000.L
         addi.b      #42,$4000.L
         addi.w      #42,$4000.L
         addi.l      #42,$4000.L


********************************************************************************

* M68kMnemonic{addq, deprecated=false, src=QUICK_IMMEDIATE, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         addq        #1,a0
         addq.w      #1,a0
         addq.l      #1,a0

* M68kMnemonic{addq, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         addq        #1,d0
         addq.b      #1,d0
         addq.w      #1,d0
         addq.l      #1,d0
         addq        #1,(a0)
         addq.b      #1,(a0)
         addq.w      #1,(a0)
         addq.l      #1,(a0)
         addq        #1,(a0)+
         addq.b      #1,(a0)+
         addq.w      #1,(a0)+
         addq.l      #1,(a0)+
         addq        #1,-(a0)
         addq.b      #1,-(a0)
         addq.w      #1,-(a0)
         addq.l      #1,-(a0)
         addq        #1,42(a0)
         addq.b      #1,42(a0)
         addq.w      #1,42(a0)
         addq.l      #1,42(a0)
         addq        #1,(-42,a0)
         addq.b      #1,(-42,a0)
         addq.w      #1,(-42,a0)
         addq.l      #1,(-42,a0)
         addq        #1,12(a0,d0)
         addq.b      #1,12(a0,d0)
         addq.w      #1,12(a0,d0)
         addq.l      #1,12(a0,d0)
         addq        #1,(12,a0,a0)
         addq.b      #1,(12,a0,a0)
         addq.w      #1,(12,a0,a0)
         addq.l      #1,(12,a0,a0)
         addq        #1,$4000
         addq.b      #1,$4000
         addq.w      #1,$4000
         addq.l      #1,$4000
         addq        #1,$4000.W
         addq.b      #1,$4000.W
         addq.w      #1,$4000.W
         addq.l      #1,$4000.W
         addq        #1,$4000.L
         addq.b      #1,$4000.L
         addq.w      #1,$4000.L
         addq.l      #1,$4000.L


********************************************************************************

* M68kMnemonic{addx, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         addx        d0,d0
         addx.b      d0,d0
         addx.w      d0,d0
         addx.l      d0,d0

* M68kMnemonic{addx, deprecated=false, src=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, dst=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         addx        -(a0),-(a0)
         addx.b      -(a0),-(a0)
         addx.w      -(a0),-(a0)
         addx.l      -(a0),-(a0)


********************************************************************************

* M68kMnemonic{and, deprecated=false, src=DATA, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         and         d0,d0
         and.b       d0,d0
         and.w       d0,d0
         and.l       d0,d0
         and         #42,d0
         and.b       #42,d0
         and.w       #42,d0
         and.l       #42,d0
         and         (a0),d0
         and.b       (a0),d0
         and.w       (a0),d0
         and.l       (a0),d0
         and         (a0)+,d0
         and.b       (a0)+,d0
         and.w       (a0)+,d0
         and.l       (a0)+,d0
         and         -(a0),d0
         and.b       -(a0),d0
         and.w       -(a0),d0
         and.l       -(a0),d0
         and         42(a0),d0
         and.b       42(a0),d0
         and.w       42(a0),d0
         and.l       42(a0),d0
         and         (-42,a0),d0
         and.b       (-42,a0),d0
         and.w       (-42,a0),d0
         and.l       (-42,a0),d0
         and         12(a0,d0),d0
         and.b       12(a0,d0),d0
         and.w       12(a0,d0),d0
         and.l       12(a0,d0),d0
         and         (12,a0,a0),d0
         and.b       (12,a0,a0),d0
         and.w       (12,a0,a0),d0
         and.l       (12,a0,a0),d0
         and         $4000,d0
         and.b       $4000,d0
         and.w       $4000,d0
         and.l       $4000,d0
         and         $4000.W,d0
         and.b       $4000.W,d0
         and.w       $4000.W,d0
         and.l       $4000.W,d0
         and         $4000.L,d0
         and.b       $4000.L,d0
         and.w       $4000.L,d0
         and.l       $4000.L,d0
         and         (PC),d0
         and.b       (PC),d0
         and.w       (PC),d0
         and.l       (PC),d0
         and         66(PC),d0
         and.b       66(PC),d0
         and.w       66(PC),d0
         and.l       66(PC),d0
         and         (-66,PC),d0
         and.b       (-66,PC),d0
         and.w       (-66,PC),d0
         and.l       (-66,PC),d0
         and         66(PC,d0),d0
         and.b       66(PC,d0),d0
         and.w       66(PC,d0),d0
         and.l       66(PC,d0),d0
         and         (66,PC,a0),d0
         and.b       (66,PC,a0),d0
         and.w       (66,PC,a0),d0
         and.l       (66,PC,a0),d0

* M68kMnemonic{and, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_MEMORY, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         and         d0,(a0)
         and.b       d0,(a0)
         and.w       d0,(a0)
         and.l       d0,(a0)
         and         d0,(a0)+
         and.b       d0,(a0)+
         and.w       d0,(a0)+
         and.l       d0,(a0)+
         and         d0,-(a0)
         and.b       d0,-(a0)
         and.w       d0,-(a0)
         and.l       d0,-(a0)
         and         d0,42(a0)
         and.b       d0,42(a0)
         and.w       d0,42(a0)
         and.l       d0,42(a0)
         and         d0,(-42,a0)
         and.b       d0,(-42,a0)
         and.w       d0,(-42,a0)
         and.l       d0,(-42,a0)
         and         d0,12(a0,d0)
         and.b       d0,12(a0,d0)
         and.w       d0,12(a0,d0)
         and.l       d0,12(a0,d0)
         and         d0,(12,a0,a0)
         and.b       d0,(12,a0,a0)
         and.w       d0,(12,a0,a0)
         and.l       d0,(12,a0,a0)
         and         d0,$4000
         and.b       d0,$4000
         and.w       d0,$4000
         and.l       d0,$4000
         and         d0,$4000.W
         and.b       d0,$4000.W
         and.w       d0,$4000.W
         and.l       d0,$4000.W
         and         d0,$4000.L
         and.b       d0,$4000.L
         and.w       d0,$4000.L
         and.l       d0,$4000.L

* M68kMnemonic{and, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         and         #42,d0
         and.b       #42,d0
         and.w       #42,d0
         and.l       #42,d0
         and         #42,(a0)
         and.b       #42,(a0)
         and.w       #42,(a0)
         and.l       #42,(a0)
         and         #42,(a0)+
         and.b       #42,(a0)+
         and.w       #42,(a0)+
         and.l       #42,(a0)+
         and         #42,-(a0)
         and.b       #42,-(a0)
         and.w       #42,-(a0)
         and.l       #42,-(a0)
         and         #42,42(a0)
         and.b       #42,42(a0)
         and.w       #42,42(a0)
         and.l       #42,42(a0)
         and         #42,(-42,a0)
         and.b       #42,(-42,a0)
         and.w       #42,(-42,a0)
         and.l       #42,(-42,a0)
         and         #42,12(a0,d0)
         and.b       #42,12(a0,d0)
         and.w       #42,12(a0,d0)
         and.l       #42,12(a0,d0)
         and         #42,(12,a0,a0)
         and.b       #42,(12,a0,a0)
         and.w       #42,(12,a0,a0)
         and.l       #42,(12,a0,a0)
         and         #42,$4000
         and.b       #42,$4000
         and.w       #42,$4000
         and.l       #42,$4000
         and         #42,$4000.W
         and.b       #42,$4000.W
         and.w       #42,$4000.W
         and.l       #42,$4000.W
         and         #42,$4000.L
         and.b       #42,$4000.L
         and.w       #42,$4000.L
         and.l       #42,$4000.L

* M68kMnemonic{and, deprecated=false, src=IMMEDIATE, dst=CCR_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         and         #42,CCR
         and.b       #42,CCR

* M68kMnemonic{and, deprecated=false, src=IMMEDIATE, dst=SR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         and         #42,SR
         and.w       #42,SR


********************************************************************************

* M68kMnemonic{andi, deprecated=false, src=IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         andi        #42,d0
         andi.b      #42,d0
         andi.w      #42,d0
         andi.l      #42,d0

* M68kMnemonic{andi, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         andi        #42,d0
         andi.b      #42,d0
         andi.w      #42,d0
         andi.l      #42,d0
         andi        #42,(a0)
         andi.b      #42,(a0)
         andi.w      #42,(a0)
         andi.l      #42,(a0)
         andi        #42,(a0)+
         andi.b      #42,(a0)+
         andi.w      #42,(a0)+
         andi.l      #42,(a0)+
         andi        #42,-(a0)
         andi.b      #42,-(a0)
         andi.w      #42,-(a0)
         andi.l      #42,-(a0)
         andi        #42,42(a0)
         andi.b      #42,42(a0)
         andi.w      #42,42(a0)
         andi.l      #42,42(a0)
         andi        #42,(-42,a0)
         andi.b      #42,(-42,a0)
         andi.w      #42,(-42,a0)
         andi.l      #42,(-42,a0)
         andi        #42,12(a0,d0)
         andi.b      #42,12(a0,d0)
         andi.w      #42,12(a0,d0)
         andi.l      #42,12(a0,d0)
         andi        #42,(12,a0,a0)
         andi.b      #42,(12,a0,a0)
         andi.w      #42,(12,a0,a0)
         andi.l      #42,(12,a0,a0)
         andi        #42,$4000
         andi.b      #42,$4000
         andi.w      #42,$4000
         andi.l      #42,$4000
         andi        #42,$4000.W
         andi.b      #42,$4000.W
         andi.w      #42,$4000.W
         andi.l      #42,$4000.W
         andi        #42,$4000.L
         andi.b      #42,$4000.L
         andi.w      #42,$4000.L
         andi.l      #42,$4000.L

* M68kMnemonic{andi, deprecated=false, src=IMMEDIATE, dst=CCR_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         andi        #42,CCR
         andi.b      #42,CCR

* M68kMnemonic{andi, deprecated=false, src=IMMEDIATE, dst=SR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         andi        #42,SR
         andi.w      #42,SR


********************************************************************************

* M68kMnemonic{asl, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asl         (a0)
         asl.w       (a0)
         asl         (a0)+
         asl.w       (a0)+
         asl         -(a0)
         asl.w       -(a0)
         asl         42(a0)
         asl.w       42(a0)
         asl         (-42,a0)
         asl.w       (-42,a0)
         asl         12(a0,d0)
         asl.w       12(a0,d0)
         asl         (12,a0,a0)
         asl.w       (12,a0,a0)
         asl         $4000
         asl.w       $4000
         asl         $4000.W
         asl.w       $4000.W
         asl         $4000.L
         asl.w       $4000.L

* M68kMnemonic{asl, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asl         d0,d0
         asl.b       d0,d0
         asl.w       d0,d0
         asl.l       d0,d0

* M68kMnemonic{asl, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asl         #1,d0
         asl.b       #1,d0
         asl.w       #1,d0
         asl.l       #1,d0

* M68kMnemonic{asl, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asl         d0
         asl.b       d0
         asl.w       d0
         asl.l       d0


********************************************************************************

* M68kMnemonic{asr, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asr         (a0)
         asr.w       (a0)
         asr         (a0)+
         asr.w       (a0)+
         asr         -(a0)
         asr.w       -(a0)
         asr         42(a0)
         asr.w       42(a0)
         asr         (-42,a0)
         asr.w       (-42,a0)
         asr         12(a0,d0)
         asr.w       12(a0,d0)
         asr         (12,a0,a0)
         asr.w       (12,a0,a0)
         asr         $4000
         asr.w       $4000
         asr         $4000.W
         asr.w       $4000.W
         asr         $4000.L
         asr.w       $4000.L

* M68kMnemonic{asr, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asr         d0,d0
         asr.b       d0,d0
         asr.w       d0,d0
         asr.l       d0,d0

* M68kMnemonic{asr, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asr         #1,d0
         asr.b       #1,d0
         asr.w       #1,d0
         asr.l       #1,d0

* M68kMnemonic{asr, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         asr         d0
         asr.b       d0
         asr.w       d0
         asr.l       d0


********************************************************************************

* M68kMnemonic{bcc, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label00: bcc         label00
         bcc.s       label00
         bcc.b       label00
         bcc.w       label00

* M68kMnemonic{bcc, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label01: bcc         label01
         bcc.s       label01
         bcc.b       label01
         bcc.w       label01
         bcc.l       label01


********************************************************************************

* M68kMnemonic{bchg, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bchg        d0,d0
         bchg.l      d0,d0

* M68kMnemonic{bchg, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_MEMORY, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bchg        d0,(a0)
         bchg.b      d0,(a0)
         bchg        d0,(a0)+
         bchg.b      d0,(a0)+
         bchg        d0,-(a0)
         bchg.b      d0,-(a0)
         bchg        d0,42(a0)
         bchg.b      d0,42(a0)
         bchg        d0,(-42,a0)
         bchg.b      d0,(-42,a0)
         bchg        d0,12(a0,d0)
         bchg.b      d0,12(a0,d0)
         bchg        d0,(12,a0,a0)
         bchg.b      d0,(12,a0,a0)
         bchg        d0,$4000
         bchg.b      d0,$4000
         bchg        d0,$4000.W
         bchg.b      d0,$4000.W
         bchg        d0,$4000.L
         bchg.b      d0,$4000.L

* M68kMnemonic{bchg, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bchg        #1,d0
         bchg.l      #1,d0

* M68kMnemonic{bchg, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_MEMORY_CF, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bchg        #1,(a0)
         bchg.b      #1,(a0)
         bchg        #1,(a0)+
         bchg.b      #1,(a0)+
         bchg        #1,-(a0)
         bchg.b      #1,-(a0)
         bchg        #1,42(a0)
         bchg.b      #1,42(a0)
         bchg        #1,(-42,a0)
         bchg.b      #1,(-42,a0)
         bchg        #1,12(a0,d0)
         bchg.b      #1,12(a0,d0)
         bchg        #1,(12,a0,a0)
         bchg.b      #1,(12,a0,a0)
         bchg        #1,$4000
         bchg.b      #1,$4000
         bchg        #1,$4000.W
         bchg.b      #1,$4000.W
         bchg        #1,$4000.L
         bchg.b      #1,$4000.L

* M68kMnemonic{bchg, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_MEMORY, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bchg        #1,(a0)
         bchg.b      #1,(a0)
         bchg        #1,(a0)+
         bchg.b      #1,(a0)+
         bchg        #1,-(a0)
         bchg.b      #1,-(a0)
         bchg        #1,42(a0)
         bchg.b      #1,42(a0)
         bchg        #1,(-42,a0)
         bchg.b      #1,(-42,a0)
         bchg        #1,12(a0,d0)
         bchg.b      #1,12(a0,d0)
         bchg        #1,(12,a0,a0)
         bchg.b      #1,(12,a0,a0)
         bchg        #1,$4000
         bchg.b      #1,$4000
         bchg        #1,$4000.W
         bchg.b      #1,$4000.W
         bchg        #1,$4000.L
         bchg.b      #1,$4000.L


********************************************************************************

* M68kMnemonic{bclr, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bclr        d0,d0
         bclr.l      d0,d0

* M68kMnemonic{bclr, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_MEMORY, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bclr        d0,(a0)
         bclr.b      d0,(a0)
         bclr        d0,(a0)+
         bclr.b      d0,(a0)+
         bclr        d0,-(a0)
         bclr.b      d0,-(a0)
         bclr        d0,42(a0)
         bclr.b      d0,42(a0)
         bclr        d0,(-42,a0)
         bclr.b      d0,(-42,a0)
         bclr        d0,12(a0,d0)
         bclr.b      d0,12(a0,d0)
         bclr        d0,(12,a0,a0)
         bclr.b      d0,(12,a0,a0)
         bclr        d0,$4000
         bclr.b      d0,$4000
         bclr        d0,$4000.W
         bclr.b      d0,$4000.W
         bclr        d0,$4000.L
         bclr.b      d0,$4000.L

* M68kMnemonic{bclr, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bclr        #1,d0
         bclr.l      #1,d0

* M68kMnemonic{bclr, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_MEMORY_CF, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bclr        #1,(a0)
         bclr.b      #1,(a0)
         bclr        #1,(a0)+
         bclr.b      #1,(a0)+
         bclr        #1,-(a0)
         bclr.b      #1,-(a0)
         bclr        #1,42(a0)
         bclr.b      #1,42(a0)
         bclr        #1,(-42,a0)
         bclr.b      #1,(-42,a0)
         bclr        #1,12(a0,d0)
         bclr.b      #1,12(a0,d0)
         bclr        #1,(12,a0,a0)
         bclr.b      #1,(12,a0,a0)
         bclr        #1,$4000
         bclr.b      #1,$4000
         bclr        #1,$4000.W
         bclr.b      #1,$4000.W
         bclr        #1,$4000.L
         bclr.b      #1,$4000.L

* M68kMnemonic{bclr, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_MEMORY, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bclr        #1,(a0)
         bclr.b      #1,(a0)
         bclr        #1,(a0)+
         bclr.b      #1,(a0)+
         bclr        #1,-(a0)
         bclr.b      #1,-(a0)
         bclr        #1,42(a0)
         bclr.b      #1,42(a0)
         bclr        #1,(-42,a0)
         bclr.b      #1,(-42,a0)
         bclr        #1,12(a0,d0)
         bclr.b      #1,12(a0,d0)
         bclr        #1,(12,a0,a0)
         bclr.b      #1,(12,a0,a0)
         bclr        #1,$4000
         bclr.b      #1,$4000
         bclr        #1,$4000.W
         bclr.b      #1,$4000.W
         bclr        #1,$4000.L
         bclr.b      #1,$4000.L


********************************************************************************

* M68kMnemonic{bcs, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label02: bcs         label02
         bcs.s       label02
         bcs.b       label02
         bcs.w       label02

* M68kMnemonic{bcs, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label03: bcs         label03
         bcs.s       label03
         bcs.b       label03
         bcs.w       label03
         bcs.l       label03


********************************************************************************

* M68kMnemonic{beq, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label04: beq         label04
         beq.s       label04
         beq.b       label04
         beq.w       label04

* M68kMnemonic{beq, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label05: beq         label05
         beq.s       label05
         beq.b       label05
         beq.w       label05
         beq.l       label05


********************************************************************************

* M68kMnemonic{bge, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label06: bge         label06
         bge.s       label06
         bge.b       label06
         bge.w       label06

* M68kMnemonic{bge, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label07: bge         label07
         bge.s       label07
         bge.b       label07
         bge.w       label07
         bge.l       label07


********************************************************************************

* M68kMnemonic{bgt, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label08: bgt         label08
         bgt.s       label08
         bgt.b       label08
         bgt.w       label08

* M68kMnemonic{bgt, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label09: bgt         label09
         bgt.s       label09
         bgt.b       label09
         bgt.w       label09
         bgt.l       label09


********************************************************************************

* M68kMnemonic{bhi, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label10: bhi         label10
         bhi.s       label10
         bhi.b       label10
         bhi.w       label10

* M68kMnemonic{bhi, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label11: bhi         label11
         bhi.s       label11
         bhi.b       label11
         bhi.w       label11
         bhi.l       label11


********************************************************************************

* M68kMnemonic{bhs, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label12: bhs         label12
         bhs.s       label12
         bhs.b       label12
         bhs.w       label12

* M68kMnemonic{bhs, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label13: bhs         label13
         bhs.s       label13
         bhs.b       label13
         bhs.w       label13
         bhs.l       label13


********************************************************************************

* M68kMnemonic{bkpt, deprecated=false, src=QUICK_IMMEDIATE, dst=NONE, [UNSIZED], [M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bkpt      #1


********************************************************************************

* M68kMnemonic{ble, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label14: ble         label14
         ble.s       label14
         ble.b       label14
         ble.w       label14

* M68kMnemonic{ble, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label15: ble         label15
         ble.s       label15
         ble.b       label15
         ble.w       label15
         ble.l       label15


********************************************************************************

* M68kMnemonic{blo, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label16: blo         label16
         blo.s       label16
         blo.b       label16
         blo.w       label16

* M68kMnemonic{blo, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label17: blo         label17
         blo.s       label17
         blo.b       label17
         blo.w       label17
         blo.l       label17


********************************************************************************

* M68kMnemonic{bls, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label18: bls         label18
         bls.s       label18
         bls.b       label18
         bls.w       label18

* M68kMnemonic{bls, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label19: bls         label19
         bls.s       label19
         bls.b       label19
         bls.w       label19
         bls.l       label19


********************************************************************************

* M68kMnemonic{blt, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label20: blt         label20
         blt.s       label20
         blt.b       label20
         blt.w       label20

* M68kMnemonic{blt, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label21: blt         label21
         blt.s       label21
         blt.b       label21
         blt.w       label21
         blt.l       label21


********************************************************************************

* M68kMnemonic{bmi, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label22: bmi         label22
         bmi.s       label22
         bmi.b       label22
         bmi.w       label22

* M68kMnemonic{bmi, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label23: bmi         label23
         bmi.s       label23
         bmi.b       label23
         bmi.w       label23
         bmi.l       label23


********************************************************************************

* M68kMnemonic{bne, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label24: bne         label24
         bne.s       label24
         bne.b       label24
         bne.w       label24

* M68kMnemonic{bne, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label25: bne         label25
         bne.s       label25
         bne.b       label25
         bne.w       label25
         bne.l       label25


********************************************************************************

* M68kMnemonic{bpl, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label26: bpl         label26
         bpl.s       label26
         bpl.b       label26
         bpl.w       label26

* M68kMnemonic{bpl, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label27: bpl         label27
         bpl.s       label27
         bpl.b       label27
         bpl.w       label27
         bpl.l       label27


********************************************************************************

* M68kMnemonic{bra, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label28: bra         label28
         bra.s       label28
         bra.b       label28
         bra.w       label28

* M68kMnemonic{bra, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label29: bra         label29
         bra.s       label29
         bra.b       label29
         bra.w       label29
         bra.l       label29


********************************************************************************

* M68kMnemonic{bset, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bset        d0,d0
         bset.l      d0,d0

* M68kMnemonic{bset, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_MEMORY, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bset        d0,(a0)
         bset.b      d0,(a0)
         bset        d0,(a0)+
         bset.b      d0,(a0)+
         bset        d0,-(a0)
         bset.b      d0,-(a0)
         bset        d0,42(a0)
         bset.b      d0,42(a0)
         bset        d0,(-42,a0)
         bset.b      d0,(-42,a0)
         bset        d0,12(a0,d0)
         bset.b      d0,12(a0,d0)
         bset        d0,(12,a0,a0)
         bset.b      d0,(12,a0,a0)
         bset        d0,$4000
         bset.b      d0,$4000
         bset        d0,$4000.W
         bset.b      d0,$4000.W
         bset        d0,$4000.L
         bset.b      d0,$4000.L

* M68kMnemonic{bset, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bset        #1,d0
         bset.l      #1,d0

* M68kMnemonic{bset, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_MEMORY_CF, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bset        #1,(a0)
         bset.b      #1,(a0)
         bset        #1,(a0)+
         bset.b      #1,(a0)+
         bset        #1,-(a0)
         bset.b      #1,-(a0)
         bset        #1,42(a0)
         bset.b      #1,42(a0)
         bset        #1,(-42,a0)
         bset.b      #1,(-42,a0)
         bset        #1,12(a0,d0)
         bset.b      #1,12(a0,d0)
         bset        #1,(12,a0,a0)
         bset.b      #1,(12,a0,a0)
         bset        #1,$4000
         bset.b      #1,$4000
         bset        #1,$4000.W
         bset.b      #1,$4000.W
         bset        #1,$4000.L
         bset.b      #1,$4000.L

* M68kMnemonic{bset, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_MEMORY, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         bset        #1,(a0)
         bset.b      #1,(a0)
         bset        #1,(a0)+
         bset.b      #1,(a0)+
         bset        #1,-(a0)
         bset.b      #1,-(a0)
         bset        #1,42(a0)
         bset.b      #1,42(a0)
         bset        #1,(-42,a0)
         bset.b      #1,(-42,a0)
         bset        #1,12(a0,d0)
         bset.b      #1,12(a0,d0)
         bset        #1,(12,a0,a0)
         bset.b      #1,(12,a0,a0)
         bset        #1,$4000
         bset.b      #1,$4000
         bset        #1,$4000.W
         bset.b      #1,$4000.W
         bset        #1,$4000.L
         bset.b      #1,$4000.L


********************************************************************************

* M68kMnemonic{bsr, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label30: bsr         label30
         bsr.s       label30
         bsr.b       label30
         bsr.w       label30

* M68kMnemonic{bsr, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label31: bsr         label31
         bsr.s       label31
         bsr.b       label31
         bsr.w       label31
         bsr.l       label31


********************************************************************************

* M68kMnemonic{btst, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         btst        d0,d0
         btst.l      d0,d0

* M68kMnemonic{btst, deprecated=false, src=DATA_REGISTER, dst=MEMORY, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         btst        d0,(a0)
         btst.b      d0,(a0)
         btst        d0,(a0)+
         btst.b      d0,(a0)+
         btst        d0,-(a0)
         btst.b      d0,-(a0)
         btst        d0,42(a0)
         btst.b      d0,42(a0)
         btst        d0,(-42,a0)
         btst.b      d0,(-42,a0)
         btst        d0,12(a0,d0)
         btst.b      d0,12(a0,d0)
         btst        d0,(12,a0,a0)
         btst.b      d0,(12,a0,a0)
         btst        d0,$4000
         btst.b      d0,$4000
         btst        d0,$4000.W
         btst.b      d0,$4000.W
         btst        d0,$4000.L
         btst.b      d0,$4000.L
         btst        d0,(PC)
         btst.b      d0,(PC)
         btst        d0,66(PC)
         btst.b      d0,66(PC)
         btst        d0,(-66,PC)
         btst.b      d0,(-66,PC)
         btst        d0,66(PC,d0)
         btst.b      d0,66(PC,d0)
         btst        d0,(66,PC,a0)
         btst.b      d0,(66,PC,a0)
         btst        d0,#42
         btst.b      d0,#42

* M68kMnemonic{btst, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         btst        #1,d0
         btst.l      #1,d0

* M68kMnemonic{btst, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_MEMORY_CF, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         btst        #1,(a0)
         btst.b      #1,(a0)
         btst        #1,(a0)+
         btst.b      #1,(a0)+
         btst        #1,-(a0)
         btst.b      #1,-(a0)
         btst        #1,42(a0)
         btst.b      #1,42(a0)
         btst        #1,(-42,a0)
         btst.b      #1,(-42,a0)
         btst        #1,12(a0,d0)
         btst.b      #1,12(a0,d0)
         btst        #1,(12,a0,a0)
         btst.b      #1,(12,a0,a0)
         btst        #1,$4000
         btst.b      #1,$4000
         btst        #1,$4000.W
         btst.b      #1,$4000.W
         btst        #1,$4000.L
         btst.b      #1,$4000.L

* M68kMnemonic{btst, deprecated=false, src=QUICK_IMMEDIATE, dst=MEMORY_WITHOUT_IMMEDIATE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         btst        #1,(a0)
         btst.b      #1,(a0)
         btst        #1,(a0)+
         btst.b      #1,(a0)+
         btst        #1,-(a0)
         btst.b      #1,-(a0)
         btst        #1,42(a0)
         btst.b      #1,42(a0)
         btst        #1,(-42,a0)
         btst.b      #1,(-42,a0)
         btst        #1,12(a0,d0)
         btst.b      #1,12(a0,d0)
         btst        #1,(12,a0,a0)
         btst.b      #1,(12,a0,a0)
         btst        #1,$4000
         btst.b      #1,$4000
         btst        #1,$4000.W
         btst.b      #1,$4000.W
         btst        #1,$4000.L
         btst.b      #1,$4000.L
         btst        #1,(PC)
         btst.b      #1,(PC)
         btst        #1,66(PC)
         btst.b      #1,66(PC)
         btst        #1,(-66,PC)
         btst.b      #1,(-66,PC)
         btst        #1,66(PC,d0)
         btst.b      #1,66(PC,d0)
         btst        #1,(66,PC,a0)
         btst.b      #1,(66,PC,a0)


********************************************************************************

* M68kMnemonic{bvc, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label32: bvc         label32
         bvc.s       label32
         bvc.b       label32
         bvc.w       label32

* M68kMnemonic{bvc, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label33: bvc         label33
         bvc.s       label33
         bvc.b       label33
         bvc.w       label33
         bvc.l       label33


********************************************************************************

* M68kMnemonic{bvs, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
label34: bvs         label34
         bvs.s       label34
         bvs.b       label34
         bvs.w       label34

* M68kMnemonic{bvs, deprecated=false, src=BRANCH_DESTINATION, dst=NONE, [SHORT, BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
label35: bvs         label35
         bvs.s       label35
         bvs.b       label35
         bvs.w       label35
         bvs.l       label35


********************************************************************************

* M68kMnemonic{chk, deprecated=false, src=DATA, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         chk         d0,d0
         chk.w       d0,d0
         chk         #42,d0
         chk.w       #42,d0
         chk         (a0),d0
         chk.w       (a0),d0
         chk         (a0)+,d0
         chk.w       (a0)+,d0
         chk         -(a0),d0
         chk.w       -(a0),d0
         chk         42(a0),d0
         chk.w       42(a0),d0
         chk         (-42,a0),d0
         chk.w       (-42,a0),d0
         chk         12(a0,d0),d0
         chk.w       12(a0,d0),d0
         chk         (12,a0,a0),d0
         chk.w       (12,a0,a0),d0
         chk         $4000,d0
         chk.w       $4000,d0
         chk         $4000.W,d0
         chk.w       $4000.W,d0
         chk         $4000.L,d0
         chk.w       $4000.L,d0
         chk         (PC),d0
         chk.w       (PC),d0
         chk         66(PC),d0
         chk.w       66(PC),d0
         chk         (-66,PC),d0
         chk.w       (-66,PC),d0
         chk         66(PC,d0),d0
         chk.w       66(PC,d0),d0
         chk         (66,PC,a0),d0
         chk.w       (66,PC,a0),d0

* M68kMnemonic{chk, deprecated=false, src=DATA, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         chk         d0,d0
         chk.l       d0,d0
         chk         #42,d0
         chk.l       #42,d0
         chk         (a0),d0
         chk.l       (a0),d0
         chk         (a0)+,d0
         chk.l       (a0)+,d0
         chk         -(a0),d0
         chk.l       -(a0),d0
         chk         42(a0),d0
         chk.l       42(a0),d0
         chk         (-42,a0),d0
         chk.l       (-42,a0),d0
         chk         12(a0,d0),d0
         chk.l       12(a0,d0),d0
         chk         (12,a0,a0),d0
         chk.l       (12,a0,a0),d0
         chk         $4000,d0
         chk.l       $4000,d0
         chk         $4000.W,d0
         chk.l       $4000.W,d0
         chk         $4000.L,d0
         chk.l       $4000.L,d0
         chk         (PC),d0
         chk.l       (PC),d0
         chk         66(PC),d0
         chk.l       66(PC),d0
         chk         (-66,PC),d0
         chk.l       (-66,PC),d0
         chk         66(PC,d0),d0
         chk.l       66(PC,d0),d0
         chk         (66,PC,a0),d0
         chk.l       (66,PC,a0),d0


********************************************************************************

* M68kMnemonic{clr, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         clr         d0
         clr.b       d0
         clr.w       d0
         clr.l       d0
         clr         (a0)
         clr.b       (a0)
         clr.w       (a0)
         clr.l       (a0)
         clr         (a0)+
         clr.b       (a0)+
         clr.w       (a0)+
         clr.l       (a0)+
         clr         -(a0)
         clr.b       -(a0)
         clr.w       -(a0)
         clr.l       -(a0)
         clr         42(a0)
         clr.b       42(a0)
         clr.w       42(a0)
         clr.l       42(a0)
         clr         (-42,a0)
         clr.b       (-42,a0)
         clr.w       (-42,a0)
         clr.l       (-42,a0)
         clr         12(a0,d0)
         clr.b       12(a0,d0)
         clr.w       12(a0,d0)
         clr.l       12(a0,d0)
         clr         (12,a0,a0)
         clr.b       (12,a0,a0)
         clr.w       (12,a0,a0)
         clr.l       (12,a0,a0)
         clr         $4000
         clr.b       $4000
         clr.w       $4000
         clr.l       $4000
         clr         $4000.W
         clr.b       $4000.W
         clr.w       $4000.W
         clr.l       $4000.W
         clr         $4000.L
         clr.b       $4000.L
         clr.w       $4000.L
         clr.l       $4000.L


********************************************************************************

* M68kMnemonic{cmp, deprecated=false, src=ADDRESS_REGISTER, dst=DATA_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmp         a0,d0
         cmp.w       a0,d0
         cmp.l       a0,d0

* M68kMnemonic{cmp, deprecated=false, src=DATA, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmp         d0,d0
         cmp.b       d0,d0
         cmp.w       d0,d0
         cmp.l       d0,d0
         cmp         #42,d0
         cmp.b       #42,d0
         cmp.w       #42,d0
         cmp.l       #42,d0
         cmp         (a0),d0
         cmp.b       (a0),d0
         cmp.w       (a0),d0
         cmp.l       (a0),d0
         cmp         (a0)+,d0
         cmp.b       (a0)+,d0
         cmp.w       (a0)+,d0
         cmp.l       (a0)+,d0
         cmp         -(a0),d0
         cmp.b       -(a0),d0
         cmp.w       -(a0),d0
         cmp.l       -(a0),d0
         cmp         42(a0),d0
         cmp.b       42(a0),d0
         cmp.w       42(a0),d0
         cmp.l       42(a0),d0
         cmp         (-42,a0),d0
         cmp.b       (-42,a0),d0
         cmp.w       (-42,a0),d0
         cmp.l       (-42,a0),d0
         cmp         12(a0,d0),d0
         cmp.b       12(a0,d0),d0
         cmp.w       12(a0,d0),d0
         cmp.l       12(a0,d0),d0
         cmp         (12,a0,a0),d0
         cmp.b       (12,a0,a0),d0
         cmp.w       (12,a0,a0),d0
         cmp.l       (12,a0,a0),d0
         cmp         $4000,d0
         cmp.b       $4000,d0
         cmp.w       $4000,d0
         cmp.l       $4000,d0
         cmp         $4000.W,d0
         cmp.b       $4000.W,d0
         cmp.w       $4000.W,d0
         cmp.l       $4000.W,d0
         cmp         $4000.L,d0
         cmp.b       $4000.L,d0
         cmp.w       $4000.L,d0
         cmp.l       $4000.L,d0
         cmp         (PC),d0
         cmp.b       (PC),d0
         cmp.w       (PC),d0
         cmp.l       (PC),d0
         cmp         66(PC),d0
         cmp.b       66(PC),d0
         cmp.w       66(PC),d0
         cmp.l       66(PC),d0
         cmp         (-66,PC),d0
         cmp.b       (-66,PC),d0
         cmp.w       (-66,PC),d0
         cmp.l       (-66,PC),d0
         cmp         66(PC,d0),d0
         cmp.b       66(PC,d0),d0
         cmp.w       66(PC,d0),d0
         cmp.l       66(PC,d0),d0
         cmp         (66,PC,a0),d0
         cmp.b       (66,PC,a0),d0
         cmp.w       (66,PC,a0),d0
         cmp.l       (66,PC,a0),d0

* M68kMnemonic{cmp, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmp         d0,a0
         cmp.w       d0,a0
         cmp.l       d0,a0
         cmp         a0,a0
         cmp.w       a0,a0
         cmp.l       a0,a0
         cmp         (a0),a0
         cmp.w       (a0),a0
         cmp.l       (a0),a0
         cmp         (a0)+,a0
         cmp.w       (a0)+,a0
         cmp.l       (a0)+,a0
         cmp         -(a0),a0
         cmp.w       -(a0),a0
         cmp.l       -(a0),a0
         cmp         42(a0),a0
         cmp.w       42(a0),a0
         cmp.l       42(a0),a0
         cmp         (-42,a0),a0
         cmp.w       (-42,a0),a0
         cmp.l       (-42,a0),a0
         cmp         12(a0,d0),a0
         cmp.w       12(a0,d0),a0
         cmp.l       12(a0,d0),a0
         cmp         (12,a0,a0),a0
         cmp.w       (12,a0,a0),a0
         cmp.l       (12,a0,a0),a0
         cmp         $4000,a0
         cmp.w       $4000,a0
         cmp.l       $4000,a0
         cmp         $4000.W,a0
         cmp.w       $4000.W,a0
         cmp.l       $4000.W,a0
         cmp         $4000.L,a0
         cmp.w       $4000.L,a0
         cmp.l       $4000.L,a0
         cmp         (PC),a0
         cmp.w       (PC),a0
         cmp.l       (PC),a0
         cmp         66(PC),a0
         cmp.w       66(PC),a0
         cmp.l       66(PC),a0
         cmp         (-66,PC),a0
         cmp.w       (-66,PC),a0
         cmp.l       (-66,PC),a0
         cmp         66(PC,d0),a0
         cmp.w       66(PC,d0),a0
         cmp.l       66(PC,d0),a0
         cmp         (66,PC,a0),a0
         cmp.w       (66,PC,a0),a0
         cmp.l       (66,PC,a0),a0
         cmp         #42,a0
         cmp.w       #42,a0
         cmp.l       #42,a0

* M68kMnemonic{cmp, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmp         #42,d0
         cmp.b       #42,d0
         cmp.w       #42,d0
         cmp.l       #42,d0
         cmp         #42,(a0)
         cmp.b       #42,(a0)
         cmp.w       #42,(a0)
         cmp.l       #42,(a0)
         cmp         #42,(a0)+
         cmp.b       #42,(a0)+
         cmp.w       #42,(a0)+
         cmp.l       #42,(a0)+
         cmp         #42,-(a0)
         cmp.b       #42,-(a0)
         cmp.w       #42,-(a0)
         cmp.l       #42,-(a0)
         cmp         #42,42(a0)
         cmp.b       #42,42(a0)
         cmp.w       #42,42(a0)
         cmp.l       #42,42(a0)
         cmp         #42,(-42,a0)
         cmp.b       #42,(-42,a0)
         cmp.w       #42,(-42,a0)
         cmp.l       #42,(-42,a0)
         cmp         #42,12(a0,d0)
         cmp.b       #42,12(a0,d0)
         cmp.w       #42,12(a0,d0)
         cmp.l       #42,12(a0,d0)
         cmp         #42,(12,a0,a0)
         cmp.b       #42,(12,a0,a0)
         cmp.w       #42,(12,a0,a0)
         cmp.l       #42,(12,a0,a0)
         cmp         #42,$4000
         cmp.b       #42,$4000
         cmp.w       #42,$4000
         cmp.l       #42,$4000
         cmp         #42,$4000.W
         cmp.b       #42,$4000.W
         cmp.w       #42,$4000.W
         cmp.l       #42,$4000.W
         cmp         #42,$4000.L
         cmp.b       #42,$4000.L
         cmp.w       #42,$4000.L
         cmp.l       #42,$4000.L

* M68kMnemonic{cmp, deprecated=false, src=ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, dst=ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmp         (a0)+,(a0)+
         cmp.b       (a0)+,(a0)+
         cmp.w       (a0)+,(a0)+
         cmp.l       (a0)+,(a0)+


********************************************************************************

* M68kMnemonic{cmpa, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmpa        d0,a0
         cmpa.w      d0,a0
         cmpa.l      d0,a0
         cmpa        a0,a0
         cmpa.w      a0,a0
         cmpa.l      a0,a0
         cmpa        (a0),a0
         cmpa.w      (a0),a0
         cmpa.l      (a0),a0
         cmpa        (a0)+,a0
         cmpa.w      (a0)+,a0
         cmpa.l      (a0)+,a0
         cmpa        -(a0),a0
         cmpa.w      -(a0),a0
         cmpa.l      -(a0),a0
         cmpa        42(a0),a0
         cmpa.w      42(a0),a0
         cmpa.l      42(a0),a0
         cmpa        (-42,a0),a0
         cmpa.w      (-42,a0),a0
         cmpa.l      (-42,a0),a0
         cmpa        12(a0,d0),a0
         cmpa.w      12(a0,d0),a0
         cmpa.l      12(a0,d0),a0
         cmpa        (12,a0,a0),a0
         cmpa.w      (12,a0,a0),a0
         cmpa.l      (12,a0,a0),a0
         cmpa        $4000,a0
         cmpa.w      $4000,a0
         cmpa.l      $4000,a0
         cmpa        $4000.W,a0
         cmpa.w      $4000.W,a0
         cmpa.l      $4000.W,a0
         cmpa        $4000.L,a0
         cmpa.w      $4000.L,a0
         cmpa.l      $4000.L,a0
         cmpa        (PC),a0
         cmpa.w      (PC),a0
         cmpa.l      (PC),a0
         cmpa        66(PC),a0
         cmpa.w      66(PC),a0
         cmpa.l      66(PC),a0
         cmpa        (-66,PC),a0
         cmpa.w      (-66,PC),a0
         cmpa.l      (-66,PC),a0
         cmpa        66(PC,d0),a0
         cmpa.w      66(PC,d0),a0
         cmpa.l      66(PC,d0),a0
         cmpa        (66,PC,a0),a0
         cmpa.w      (66,PC,a0),a0
         cmpa.l      (66,PC,a0),a0
         cmpa        #42,a0
         cmpa.w      #42,a0
         cmpa.l      #42,a0


********************************************************************************

* M68kMnemonic{cmpi, deprecated=false, src=IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmpi        #42,d0
         cmpi.b      #42,d0
         cmpi.w      #42,d0
         cmpi.l      #42,d0

* M68kMnemonic{cmpi, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmpi        #42,d0
         cmpi.b      #42,d0
         cmpi.w      #42,d0
         cmpi.l      #42,d0
         cmpi        #42,(a0)
         cmpi.b      #42,(a0)
         cmpi.w      #42,(a0)
         cmpi.l      #42,(a0)
         cmpi        #42,(a0)+
         cmpi.b      #42,(a0)+
         cmpi.w      #42,(a0)+
         cmpi.l      #42,(a0)+
         cmpi        #42,-(a0)
         cmpi.b      #42,-(a0)
         cmpi.w      #42,-(a0)
         cmpi.l      #42,-(a0)
         cmpi        #42,42(a0)
         cmpi.b      #42,42(a0)
         cmpi.w      #42,42(a0)
         cmpi.l      #42,42(a0)
         cmpi        #42,(-42,a0)
         cmpi.b      #42,(-42,a0)
         cmpi.w      #42,(-42,a0)
         cmpi.l      #42,(-42,a0)
         cmpi        #42,12(a0,d0)
         cmpi.b      #42,12(a0,d0)
         cmpi.w      #42,12(a0,d0)
         cmpi.l      #42,12(a0,d0)
         cmpi        #42,(12,a0,a0)
         cmpi.b      #42,(12,a0,a0)
         cmpi.w      #42,(12,a0,a0)
         cmpi.l      #42,(12,a0,a0)
         cmpi        #42,$4000
         cmpi.b      #42,$4000
         cmpi.w      #42,$4000
         cmpi.l      #42,$4000
         cmpi        #42,$4000.W
         cmpi.b      #42,$4000.W
         cmpi.w      #42,$4000.W
         cmpi.l      #42,$4000.W
         cmpi        #42,$4000.L
         cmpi.b      #42,$4000.L
         cmpi.w      #42,$4000.L
         cmpi.l      #42,$4000.L


********************************************************************************

* M68kMnemonic{cmpm, deprecated=false, src=ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, dst=ADDRESS_REGISTER_INDIRECT_POST_INCREMENT, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         cmpm        (a0)+,(a0)+
         cmpm.b      (a0)+,(a0)+
         cmpm.w      (a0)+,(a0)+
         cmpm.l      (a0)+,(a0)+


********************************************************************************

* M68kMnemonic{dbcc, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbcc        d0,label
         dbcc.w      d0,label


********************************************************************************

* M68kMnemonic{dbcs, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbcs        d0,label
         dbcs.w      d0,label


********************************************************************************

* M68kMnemonic{dbeq, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbeq        d0,label
         dbeq.w      d0,label


********************************************************************************

* M68kMnemonic{dbf, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbf         d0,label
         dbf.w       d0,label


********************************************************************************

* M68kMnemonic{dbge, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbge        d0,label
         dbge.w      d0,label


********************************************************************************

* M68kMnemonic{dbgt, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbgt        d0,label
         dbgt.w      d0,label


********************************************************************************

* M68kMnemonic{dbhi, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbhi        d0,label
         dbhi.w      d0,label


********************************************************************************

* M68kMnemonic{dbhs, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbhs        d0,label
         dbhs.w      d0,label


********************************************************************************

* M68kMnemonic{dble, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dble        d0,label
         dble.w      d0,label


********************************************************************************

* M68kMnemonic{dblo, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dblo        d0,label
         dblo.w      d0,label


********************************************************************************

* M68kMnemonic{dbls, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbls        d0,label
         dbls.w      d0,label


********************************************************************************

* M68kMnemonic{dblt, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dblt        d0,label
         dblt.w      d0,label


********************************************************************************

* M68kMnemonic{dbmi, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbmi        d0,label
         dbmi.w      d0,label


********************************************************************************

* M68kMnemonic{dbne, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbne        d0,label
         dbne.w      d0,label


********************************************************************************

* M68kMnemonic{dbpl, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbpl        d0,label
         dbpl.w      d0,label


********************************************************************************

* M68kMnemonic{dbra, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbra        d0,label
         dbra.w      d0,label


********************************************************************************

* M68kMnemonic{dbt, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbt         d0,label
         dbt.w       d0,label


********************************************************************************

* M68kMnemonic{dbvc, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbvc        d0,label
         dbvc.w      d0,label


********************************************************************************

* M68kMnemonic{dbvs, deprecated=false, src=DATA_REGISTER, dst=DBCC_BRANCH_DESTINATION, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         dbvs        d0,label
         dbvs.w      d0,label


********************************************************************************

* M68kMnemonic{divs, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divs        d0,d0
         divs.w      d0,d0
         divs        (a0),d0
         divs.w      (a0),d0
         divs        (a0)+,d0
         divs.w      (a0)+,d0
         divs        -(a0),d0
         divs.w      -(a0),d0
         divs        42(a0),d0
         divs.w      42(a0),d0
         divs        (-42,a0),d0
         divs.w      (-42,a0),d0
         divs        12(a0,d0),d0
         divs.w      12(a0,d0),d0
         divs        (12,a0,a0),d0
         divs.w      (12,a0,a0),d0
         divs        $4000,d0
         divs.w      $4000,d0
         divs        $4000.W,d0
         divs.w      $4000.W,d0
         divs        $4000.L,d0
         divs.w      $4000.L,d0

* M68kMnemonic{divs, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divs        d0,d0
         divs.l      d0,d0
         divs        (a0),d0
         divs.l      (a0),d0
         divs        (a0)+,d0
         divs.l      (a0)+,d0
         divs        -(a0),d0
         divs.l      -(a0),d0
         divs        42(a0),d0
         divs.l      42(a0),d0
         divs        (-42,a0),d0
         divs.l      (-42,a0),d0
         divs        12(a0,d0),d0
         divs.l      12(a0,d0),d0
         divs        (12,a0,a0),d0
         divs.l      (12,a0,a0),d0
         divs        $4000,d0
         divs.l      $4000,d0
         divs        $4000.W,d0
         divs.l      $4000.W,d0
         divs        $4000.L,d0
         divs.l      $4000.L,d0

* M68kMnemonic{divs, deprecated=false, src=DATA, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divs        d0,d0
         divs.w      d0,d0
         divs        #42,d0
         divs.w      #42,d0
         divs        (a0),d0
         divs.w      (a0),d0
         divs        (a0)+,d0
         divs.w      (a0)+,d0
         divs        -(a0),d0
         divs.w      -(a0),d0
         divs        42(a0),d0
         divs.w      42(a0),d0
         divs        (-42,a0),d0
         divs.w      (-42,a0),d0
         divs        12(a0,d0),d0
         divs.w      12(a0,d0),d0
         divs        (12,a0,a0),d0
         divs.w      (12,a0,a0),d0
         divs        $4000,d0
         divs.w      $4000,d0
         divs        $4000.W,d0
         divs.w      $4000.W,d0
         divs        $4000.L,d0
         divs.w      $4000.L,d0
         divs        (PC),d0
         divs.w      (PC),d0
         divs        66(PC),d0
         divs.w      66(PC),d0
         divs        (-66,PC),d0
         divs.w      (-66,PC),d0
         divs        66(PC,d0),d0
         divs.w      66(PC,d0),d0
         divs        (66,PC,a0),d0
         divs.w      (66,PC,a0),d0

* M68kMnemonic{divs, deprecated=false, src=DATA, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divs        d0,d0
         divs.l      d0,d0
         divs        #42,d0
         divs.l      #42,d0
         divs        (a0),d0
         divs.l      (a0),d0
         divs        (a0)+,d0
         divs.l      (a0)+,d0
         divs        -(a0),d0
         divs.l      -(a0),d0
         divs        42(a0),d0
         divs.l      42(a0),d0
         divs        (-42,a0),d0
         divs.l      (-42,a0),d0
         divs        12(a0,d0),d0
         divs.l      12(a0,d0),d0
         divs        (12,a0,a0),d0
         divs.l      (12,a0,a0),d0
         divs        $4000,d0
         divs.l      $4000,d0
         divs        $4000.W,d0
         divs.l      $4000.W,d0
         divs        $4000.L,d0
         divs.l      $4000.L,d0
         divs        (PC),d0
         divs.l      (PC),d0
         divs        66(PC),d0
         divs.l      66(PC),d0
         divs        (-66,PC),d0
         divs.l      (-66,PC),d0
         divs        66(PC,d0),d0
         divs.l      66(PC,d0),d0
         divs        (66,PC,a0),d0
         divs.l      (66,PC,a0),d0


********************************************************************************

* M68kMnemonic{divu, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divu        d0,d0
         divu.w      d0,d0
         divu        (a0),d0
         divu.w      (a0),d0
         divu        (a0)+,d0
         divu.w      (a0)+,d0
         divu        -(a0),d0
         divu.w      -(a0),d0
         divu        42(a0),d0
         divu.w      42(a0),d0
         divu        (-42,a0),d0
         divu.w      (-42,a0),d0
         divu        12(a0,d0),d0
         divu.w      12(a0,d0),d0
         divu        (12,a0,a0),d0
         divu.w      (12,a0,a0),d0
         divu        $4000,d0
         divu.w      $4000,d0
         divu        $4000.W,d0
         divu.w      $4000.W,d0
         divu        $4000.L,d0
         divu.w      $4000.L,d0

* M68kMnemonic{divu, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divu        d0,d0
         divu.l      d0,d0
         divu        (a0),d0
         divu.l      (a0),d0
         divu        (a0)+,d0
         divu.l      (a0)+,d0
         divu        -(a0),d0
         divu.l      -(a0),d0
         divu        42(a0),d0
         divu.l      42(a0),d0
         divu        (-42,a0),d0
         divu.l      (-42,a0),d0
         divu        12(a0,d0),d0
         divu.l      12(a0,d0),d0
         divu        (12,a0,a0),d0
         divu.l      (12,a0,a0),d0
         divu        $4000,d0
         divu.l      $4000,d0
         divu        $4000.W,d0
         divu.l      $4000.W,d0
         divu        $4000.L,d0
         divu.l      $4000.L,d0

* M68kMnemonic{divu, deprecated=false, src=DATA, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divu        d0,d0
         divu.w      d0,d0
         divu        #42,d0
         divu.w      #42,d0
         divu        (a0),d0
         divu.w      (a0),d0
         divu        (a0)+,d0
         divu.w      (a0)+,d0
         divu        -(a0),d0
         divu.w      -(a0),d0
         divu        42(a0),d0
         divu.w      42(a0),d0
         divu        (-42,a0),d0
         divu.w      (-42,a0),d0
         divu        12(a0,d0),d0
         divu.w      12(a0,d0),d0
         divu        (12,a0,a0),d0
         divu.w      (12,a0,a0),d0
         divu        $4000,d0
         divu.w      $4000,d0
         divu        $4000.W,d0
         divu.w      $4000.W,d0
         divu        $4000.L,d0
         divu.w      $4000.L,d0
         divu        (PC),d0
         divu.w      (PC),d0
         divu        66(PC),d0
         divu.w      66(PC),d0
         divu        (-66,PC),d0
         divu.w      (-66,PC),d0
         divu        66(PC,d0),d0
         divu.w      66(PC,d0),d0
         divu        (66,PC,a0),d0
         divu.w      (66,PC,a0),d0

* M68kMnemonic{divu, deprecated=false, src=DATA, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         divu        d0,d0
         divu.l      d0,d0
         divu        #42,d0
         divu.l      #42,d0
         divu        (a0),d0
         divu.l      (a0),d0
         divu        (a0)+,d0
         divu.l      (a0)+,d0
         divu        -(a0),d0
         divu.l      -(a0),d0
         divu        42(a0),d0
         divu.l      42(a0),d0
         divu        (-42,a0),d0
         divu.l      (-42,a0),d0
         divu        12(a0,d0),d0
         divu.l      12(a0,d0),d0
         divu        (12,a0,a0),d0
         divu.l      (12,a0,a0),d0
         divu        $4000,d0
         divu.l      $4000,d0
         divu        $4000.W,d0
         divu.l      $4000.W,d0
         divu        $4000.L,d0
         divu.l      $4000.L,d0
         divu        (PC),d0
         divu.l      (PC),d0
         divu        66(PC),d0
         divu.l      66(PC),d0
         divu        (-66,PC),d0
         divu.l      (-66,PC),d0
         divu        66(PC,d0),d0
         divu.l      66(PC,d0),d0
         divu        (66,PC,a0),d0
         divu.l      (66,PC,a0),d0


********************************************************************************

* M68kMnemonic{eor, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eor         d0,d0
         eor.b       d0,d0
         eor.w       d0,d0
         eor.l       d0,d0
         eor         d0,(a0)
         eor.b       d0,(a0)
         eor.w       d0,(a0)
         eor.l       d0,(a0)
         eor         d0,(a0)+
         eor.b       d0,(a0)+
         eor.w       d0,(a0)+
         eor.l       d0,(a0)+
         eor         d0,-(a0)
         eor.b       d0,-(a0)
         eor.w       d0,-(a0)
         eor.l       d0,-(a0)
         eor         d0,42(a0)
         eor.b       d0,42(a0)
         eor.w       d0,42(a0)
         eor.l       d0,42(a0)
         eor         d0,(-42,a0)
         eor.b       d0,(-42,a0)
         eor.w       d0,(-42,a0)
         eor.l       d0,(-42,a0)
         eor         d0,12(a0,d0)
         eor.b       d0,12(a0,d0)
         eor.w       d0,12(a0,d0)
         eor.l       d0,12(a0,d0)
         eor         d0,(12,a0,a0)
         eor.b       d0,(12,a0,a0)
         eor.w       d0,(12,a0,a0)
         eor.l       d0,(12,a0,a0)
         eor         d0,$4000
         eor.b       d0,$4000
         eor.w       d0,$4000
         eor.l       d0,$4000
         eor         d0,$4000.W
         eor.b       d0,$4000.W
         eor.w       d0,$4000.W
         eor.l       d0,$4000.W
         eor         d0,$4000.L
         eor.b       d0,$4000.L
         eor.w       d0,$4000.L
         eor.l       d0,$4000.L

* M68kMnemonic{eor, deprecated=false, src=IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eor         #42,d0
         eor.b       #42,d0
         eor.w       #42,d0
         eor.l       #42,d0

* M68kMnemonic{eor, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eor         #42,d0
         eor.b       #42,d0
         eor.w       #42,d0
         eor.l       #42,d0
         eor         #42,(a0)
         eor.b       #42,(a0)
         eor.w       #42,(a0)
         eor.l       #42,(a0)
         eor         #42,(a0)+
         eor.b       #42,(a0)+
         eor.w       #42,(a0)+
         eor.l       #42,(a0)+
         eor         #42,-(a0)
         eor.b       #42,-(a0)
         eor.w       #42,-(a0)
         eor.l       #42,-(a0)
         eor         #42,42(a0)
         eor.b       #42,42(a0)
         eor.w       #42,42(a0)
         eor.l       #42,42(a0)
         eor         #42,(-42,a0)
         eor.b       #42,(-42,a0)
         eor.w       #42,(-42,a0)
         eor.l       #42,(-42,a0)
         eor         #42,12(a0,d0)
         eor.b       #42,12(a0,d0)
         eor.w       #42,12(a0,d0)
         eor.l       #42,12(a0,d0)
         eor         #42,(12,a0,a0)
         eor.b       #42,(12,a0,a0)
         eor.w       #42,(12,a0,a0)
         eor.l       #42,(12,a0,a0)
         eor         #42,$4000
         eor.b       #42,$4000
         eor.w       #42,$4000
         eor.l       #42,$4000
         eor         #42,$4000.W
         eor.b       #42,$4000.W
         eor.w       #42,$4000.W
         eor.l       #42,$4000.W
         eor         #42,$4000.L
         eor.b       #42,$4000.L
         eor.w       #42,$4000.L
         eor.l       #42,$4000.L

* M68kMnemonic{eor, deprecated=false, src=IMMEDIATE, dst=CCR_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eor         #42,CCR
         eor.b       #42,CCR

* M68kMnemonic{eor, deprecated=false, src=IMMEDIATE, dst=SR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eor         #42,SR
         eor.w       #42,SR


********************************************************************************

* M68kMnemonic{eori, deprecated=false, src=IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eori        #42,d0
         eori.b      #42,d0
         eori.w      #42,d0
         eori.l      #42,d0

* M68kMnemonic{eori, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eori        #42,d0
         eori.b      #42,d0
         eori.w      #42,d0
         eori.l      #42,d0
         eori        #42,(a0)
         eori.b      #42,(a0)
         eori.w      #42,(a0)
         eori.l      #42,(a0)
         eori        #42,(a0)+
         eori.b      #42,(a0)+
         eori.w      #42,(a0)+
         eori.l      #42,(a0)+
         eori        #42,-(a0)
         eori.b      #42,-(a0)
         eori.w      #42,-(a0)
         eori.l      #42,-(a0)
         eori        #42,42(a0)
         eori.b      #42,42(a0)
         eori.w      #42,42(a0)
         eori.l      #42,42(a0)
         eori        #42,(-42,a0)
         eori.b      #42,(-42,a0)
         eori.w      #42,(-42,a0)
         eori.l      #42,(-42,a0)
         eori        #42,12(a0,d0)
         eori.b      #42,12(a0,d0)
         eori.w      #42,12(a0,d0)
         eori.l      #42,12(a0,d0)
         eori        #42,(12,a0,a0)
         eori.b      #42,(12,a0,a0)
         eori.w      #42,(12,a0,a0)
         eori.l      #42,(12,a0,a0)
         eori        #42,$4000
         eori.b      #42,$4000
         eori.w      #42,$4000
         eori.l      #42,$4000
         eori        #42,$4000.W
         eori.b      #42,$4000.W
         eori.w      #42,$4000.W
         eori.l      #42,$4000.W
         eori        #42,$4000.L
         eori.b      #42,$4000.L
         eori.w      #42,$4000.L
         eori.l      #42,$4000.L

* M68kMnemonic{eori, deprecated=false, src=IMMEDIATE, dst=CCR_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eori        #42,CCR
         eori.b      #42,CCR

* M68kMnemonic{eori, deprecated=false, src=IMMEDIATE, dst=SR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         eori        #42,SR
         eori.w      #42,SR


********************************************************************************

* M68kMnemonic{exg, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         exg         d0,d0
         exg.l       d0,d0

* M68kMnemonic{exg, deprecated=false, src=ADDRESS_REGISTER, dst=ADDRESS_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         exg         a0,a0
         exg.l       a0,a0

* M68kMnemonic{exg, deprecated=false, src=DATA_REGISTER, dst=ADDRESS_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         exg         d0,a0
         exg.l       d0,a0

* M68kMnemonic{exg, deprecated=false, src=ADDRESS_REGISTER, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         exg         a0,d0
         exg.l       a0,d0


********************************************************************************

* M68kMnemonic{ext, deprecated=false, src=DATA_REGISTER, dst=NONE, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ext         d0
         ext.w       d0
         ext.l       d0


********************************************************************************

* M68kMnemonic{illegal, deprecated=false, src=NONE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         illegal


********************************************************************************

* M68kMnemonic{jmp, deprecated=false, src=CONTROL, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         jmp       (a0)
         jmp       42(a0)
         jmp       (-42,a0)
         jmp       12(a0,d0)
         jmp       (12,a0,a0)
         jmp       $4000
         jmp       $4000.W
         jmp       $4000.L
         jmp       (PC)
         jmp       66(PC)
         jmp       (-66,PC)
         jmp       66(PC,d0)
         jmp       (66,PC,a0)


********************************************************************************

* M68kMnemonic{jsr, deprecated=false, src=CONTROL, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         jsr       (a0)
         jsr       42(a0)
         jsr       (-42,a0)
         jsr       12(a0,d0)
         jsr       (12,a0,a0)
         jsr       $4000
         jsr       $4000.W
         jsr       $4000.L
         jsr       (PC)
         jsr       66(PC)
         jsr       (-66,PC)
         jsr       66(PC,d0)
         jsr       (66,PC,a0)


********************************************************************************

* M68kMnemonic{lea, deprecated=false, src=CONTROL, dst=ADDRESS_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lea         (a0),a0
         lea.l       (a0),a0
         lea         42(a0),a0
         lea.l       42(a0),a0
         lea         (-42,a0),a0
         lea.l       (-42,a0),a0
         lea         12(a0,d0),a0
         lea.l       12(a0,d0),a0
         lea         (12,a0,a0),a0
         lea.l       (12,a0,a0),a0
         lea         $4000,a0
         lea.l       $4000,a0
         lea         $4000.W,a0
         lea.l       $4000.W,a0
         lea         $4000.L,a0
         lea.l       $4000.L,a0
         lea         (PC),a0
         lea.l       (PC),a0
         lea         66(PC),a0
         lea.l       66(PC),a0
         lea         (-66,PC),a0
         lea.l       (-66,PC),a0
         lea         66(PC,d0),a0
         lea.l       66(PC,d0),a0
         lea         (66,PC,a0),a0
         lea.l       (66,PC,a0),a0


********************************************************************************

* M68kMnemonic{link, deprecated=false, src=ADDRESS_REGISTER, dst=IMMEDIATE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         link        a0,#42
         link.w      a0,#42

* M68kMnemonic{link, deprecated=false, src=ADDRESS_REGISTER, dst=IMMEDIATE, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         link        a0,#42
         link.l      a0,#42


********************************************************************************

* M68kMnemonic{lsl, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsl         (a0)
         lsl.w       (a0)
         lsl         (a0)+
         lsl.w       (a0)+
         lsl         -(a0)
         lsl.w       -(a0)
         lsl         42(a0)
         lsl.w       42(a0)
         lsl         (-42,a0)
         lsl.w       (-42,a0)
         lsl         12(a0,d0)
         lsl.w       12(a0,d0)
         lsl         (12,a0,a0)
         lsl.w       (12,a0,a0)
         lsl         $4000
         lsl.w       $4000
         lsl         $4000.W
         lsl.w       $4000.W
         lsl         $4000.L
         lsl.w       $4000.L

* M68kMnemonic{lsl, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsl         d0,d0
         lsl.b       d0,d0
         lsl.w       d0,d0
         lsl.l       d0,d0

* M68kMnemonic{lsl, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsl         #1,d0
         lsl.b       #1,d0
         lsl.w       #1,d0
         lsl.l       #1,d0

* M68kMnemonic{lsl, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsl         d0
         lsl.b       d0
         lsl.w       d0
         lsl.l       d0


********************************************************************************

* M68kMnemonic{lsr, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsr         (a0)
         lsr.w       (a0)
         lsr         (a0)+
         lsr.w       (a0)+
         lsr         -(a0)
         lsr.w       -(a0)
         lsr         42(a0)
         lsr.w       42(a0)
         lsr         (-42,a0)
         lsr.w       (-42,a0)
         lsr         12(a0,d0)
         lsr.w       12(a0,d0)
         lsr         (12,a0,a0)
         lsr.w       (12,a0,a0)
         lsr         $4000
         lsr.w       $4000
         lsr         $4000.W
         lsr.w       $4000.W
         lsr         $4000.L
         lsr.w       $4000.L

* M68kMnemonic{lsr, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsr         d0,d0
         lsr.b       d0,d0
         lsr.w       d0,d0
         lsr.l       d0,d0

* M68kMnemonic{lsr, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsr         #1,d0
         lsr.b       #1,d0
         lsr.w       #1,d0
         lsr.l       #1,d0

* M68kMnemonic{lsr, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         lsr         d0
         lsr.b       d0
         lsr.w       d0
         lsr.l       d0


********************************************************************************

* M68kMnemonic{move, deprecated=false, src=ADDRESS_REGISTER, dst=ALTERABLE, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        a0,d0
         move.w      a0,d0
         move.l      a0,d0
         move        a0,a0
         move.w      a0,a0
         move.l      a0,a0
         move        a0,(a0)
         move.w      a0,(a0)
         move.l      a0,(a0)
         move        a0,(a0)+
         move.w      a0,(a0)+
         move.l      a0,(a0)+
         move        a0,-(a0)
         move.w      a0,-(a0)
         move.l      a0,-(a0)
         move        a0,42(a0)
         move.w      a0,42(a0)
         move.l      a0,42(a0)
         move        a0,(-42,a0)
         move.w      a0,(-42,a0)
         move.l      a0,(-42,a0)
         move        a0,12(a0,d0)
         move.w      a0,12(a0,d0)
         move.l      a0,12(a0,d0)
         move        a0,(12,a0,a0)
         move.w      a0,(12,a0,a0)
         move.l      a0,(12,a0,a0)
         move        a0,$4000
         move.w      a0,$4000
         move.l      a0,$4000
         move        a0,$4000.W
         move.w      a0,$4000.W
         move.l      a0,$4000.W
         move        a0,$4000.L
         move.w      a0,$4000.L
         move.l      a0,$4000.L

* M68kMnemonic{move, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        d0,a0
         move.w      d0,a0
         move.l      d0,a0
         move        a0,a0
         move.w      a0,a0
         move.l      a0,a0
         move        (a0),a0
         move.w      (a0),a0
         move.l      (a0),a0
         move        (a0)+,a0
         move.w      (a0)+,a0
         move.l      (a0)+,a0
         move        -(a0),a0
         move.w      -(a0),a0
         move.l      -(a0),a0
         move        42(a0),a0
         move.w      42(a0),a0
         move.l      42(a0),a0
         move        (-42,a0),a0
         move.w      (-42,a0),a0
         move.l      (-42,a0),a0
         move        12(a0,d0),a0
         move.w      12(a0,d0),a0
         move.l      12(a0,d0),a0
         move        (12,a0,a0),a0
         move.w      (12,a0,a0),a0
         move.l      (12,a0,a0),a0
         move        $4000,a0
         move.w      $4000,a0
         move.l      $4000,a0
         move        $4000.W,a0
         move.w      $4000.W,a0
         move.l      $4000.W,a0
         move        $4000.L,a0
         move.w      $4000.L,a0
         move.l      $4000.L,a0
         move        (PC),a0
         move.w      (PC),a0
         move.l      (PC),a0
         move        66(PC),a0
         move.w      66(PC),a0
         move.l      66(PC),a0
         move        (-66,PC),a0
         move.w      (-66,PC),a0
         move.l      (-66,PC),a0
         move        66(PC,d0),a0
         move.w      66(PC,d0),a0
         move.l      66(PC,d0),a0
         move        (66,PC,a0),a0
         move.w      (66,PC,a0),a0
         move.l      (66,PC,a0),a0
         move        #42,a0
         move.w      #42,a0
         move.l      #42,a0

* M68kMnemonic{move, deprecated=false, src=DATA, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        d0,d0
         move.b      d0,d0
         move.w      d0,d0
         move.l      d0,d0
         move        d0,(a0)
         move.b      d0,(a0)
         move.w      d0,(a0)
         move.l      d0,(a0)
         move        d0,(a0)+
         move.b      d0,(a0)+
         move.w      d0,(a0)+
         move.l      d0,(a0)+
         move        d0,-(a0)
         move.b      d0,-(a0)
         move.w      d0,-(a0)
         move.l      d0,-(a0)
         move        d0,42(a0)
         move.b      d0,42(a0)
         move.w      d0,42(a0)
         move.l      d0,42(a0)
         move        d0,(-42,a0)
         move.b      d0,(-42,a0)
         move.w      d0,(-42,a0)
         move.l      d0,(-42,a0)
         move        d0,12(a0,d0)
         move.b      d0,12(a0,d0)
         move.w      d0,12(a0,d0)
         move.l      d0,12(a0,d0)
         move        d0,(12,a0,a0)
         move.b      d0,(12,a0,a0)
         move.w      d0,(12,a0,a0)
         move.l      d0,(12,a0,a0)
         move        d0,$4000
         move.b      d0,$4000
         move.w      d0,$4000
         move.l      d0,$4000
         move        d0,$4000.W
         move.b      d0,$4000.W
         move.w      d0,$4000.W
         move.l      d0,$4000.W
         move        d0,$4000.L
         move.b      d0,$4000.L
         move.w      d0,$4000.L
         move.l      d0,$4000.L
         move        #42,d0
         move.b      #42,d0
         move.w      #42,d0
         move.l      #42,d0
         move        #42,(a0)
         move.b      #42,(a0)
         move.w      #42,(a0)
         move.l      #42,(a0)
         move        #42,(a0)+
         move.b      #42,(a0)+
         move.w      #42,(a0)+
         move.l      #42,(a0)+
         move        #42,-(a0)
         move.b      #42,-(a0)
         move.w      #42,-(a0)
         move.l      #42,-(a0)
         move        #42,42(a0)
         move.b      #42,42(a0)
         move.w      #42,42(a0)
         move.l      #42,42(a0)
         move        #42,(-42,a0)
         move.b      #42,(-42,a0)
         move.w      #42,(-42,a0)
         move.l      #42,(-42,a0)
         move        #42,12(a0,d0)
         move.b      #42,12(a0,d0)
         move.w      #42,12(a0,d0)
         move.l      #42,12(a0,d0)
         move        #42,(12,a0,a0)
         move.b      #42,(12,a0,a0)
         move.w      #42,(12,a0,a0)
         move.l      #42,(12,a0,a0)
         move        #42,$4000
         move.b      #42,$4000
         move.w      #42,$4000
         move.l      #42,$4000
         move        #42,$4000.W
         move.b      #42,$4000.W
         move.w      #42,$4000.W
         move.l      #42,$4000.W
         move        #42,$4000.L
         move.b      #42,$4000.L
         move.w      #42,$4000.L
         move.l      #42,$4000.L
         move        (a0),d0
         move.b      (a0),d0
         move.w      (a0),d0
         move.l      (a0),d0
         move        (a0),(a0)
         move.b      (a0),(a0)
         move.w      (a0),(a0)
         move.l      (a0),(a0)
         move        (a0),(a0)+
         move.b      (a0),(a0)+
         move.w      (a0),(a0)+
         move.l      (a0),(a0)+
         move        (a0),-(a0)
         move.b      (a0),-(a0)
         move.w      (a0),-(a0)
         move.l      (a0),-(a0)
         move        (a0),42(a0)
         move.b      (a0),42(a0)
         move.w      (a0),42(a0)
         move.l      (a0),42(a0)
         move        (a0),(-42,a0)
         move.b      (a0),(-42,a0)
         move.w      (a0),(-42,a0)
         move.l      (a0),(-42,a0)
         move        (a0),12(a0,d0)
         move.b      (a0),12(a0,d0)
         move.w      (a0),12(a0,d0)
         move.l      (a0),12(a0,d0)
         move        (a0),(12,a0,a0)
         move.b      (a0),(12,a0,a0)
         move.w      (a0),(12,a0,a0)
         move.l      (a0),(12,a0,a0)
         move        (a0),$4000
         move.b      (a0),$4000
         move.w      (a0),$4000
         move.l      (a0),$4000
         move        (a0),$4000.W
         move.b      (a0),$4000.W
         move.w      (a0),$4000.W
         move.l      (a0),$4000.W
         move        (a0),$4000.L
         move.b      (a0),$4000.L
         move.w      (a0),$4000.L
         move.l      (a0),$4000.L
         move        (a0)+,d0
         move.b      (a0)+,d0
         move.w      (a0)+,d0
         move.l      (a0)+,d0
         move        (a0)+,(a0)
         move.b      (a0)+,(a0)
         move.w      (a0)+,(a0)
         move.l      (a0)+,(a0)
         move        (a0)+,(a0)+
         move.b      (a0)+,(a0)+
         move.w      (a0)+,(a0)+
         move.l      (a0)+,(a0)+
         move        (a0)+,-(a0)
         move.b      (a0)+,-(a0)
         move.w      (a0)+,-(a0)
         move.l      (a0)+,-(a0)
         move        (a0)+,42(a0)
         move.b      (a0)+,42(a0)
         move.w      (a0)+,42(a0)
         move.l      (a0)+,42(a0)
         move        (a0)+,(-42,a0)
         move.b      (a0)+,(-42,a0)
         move.w      (a0)+,(-42,a0)
         move.l      (a0)+,(-42,a0)
         move        (a0)+,12(a0,d0)
         move.b      (a0)+,12(a0,d0)
         move.w      (a0)+,12(a0,d0)
         move.l      (a0)+,12(a0,d0)
         move        (a0)+,(12,a0,a0)
         move.b      (a0)+,(12,a0,a0)
         move.w      (a0)+,(12,a0,a0)
         move.l      (a0)+,(12,a0,a0)
         move        (a0)+,$4000
         move.b      (a0)+,$4000
         move.w      (a0)+,$4000
         move.l      (a0)+,$4000
         move        (a0)+,$4000.W
         move.b      (a0)+,$4000.W
         move.w      (a0)+,$4000.W
         move.l      (a0)+,$4000.W
         move        (a0)+,$4000.L
         move.b      (a0)+,$4000.L
         move.w      (a0)+,$4000.L
         move.l      (a0)+,$4000.L
         move        -(a0),d0
         move.b      -(a0),d0
         move.w      -(a0),d0
         move.l      -(a0),d0
         move        -(a0),(a0)
         move.b      -(a0),(a0)
         move.w      -(a0),(a0)
         move.l      -(a0),(a0)
         move        -(a0),(a0)+
         move.b      -(a0),(a0)+
         move.w      -(a0),(a0)+
         move.l      -(a0),(a0)+
         move        -(a0),-(a0)
         move.b      -(a0),-(a0)
         move.w      -(a0),-(a0)
         move.l      -(a0),-(a0)
         move        -(a0),42(a0)
         move.b      -(a0),42(a0)
         move.w      -(a0),42(a0)
         move.l      -(a0),42(a0)
         move        -(a0),(-42,a0)
         move.b      -(a0),(-42,a0)
         move.w      -(a0),(-42,a0)
         move.l      -(a0),(-42,a0)
         move        -(a0),12(a0,d0)
         move.b      -(a0),12(a0,d0)
         move.w      -(a0),12(a0,d0)
         move.l      -(a0),12(a0,d0)
         move        -(a0),(12,a0,a0)
         move.b      -(a0),(12,a0,a0)
         move.w      -(a0),(12,a0,a0)
         move.l      -(a0),(12,a0,a0)
         move        -(a0),$4000
         move.b      -(a0),$4000
         move.w      -(a0),$4000
         move.l      -(a0),$4000
         move        -(a0),$4000.W
         move.b      -(a0),$4000.W
         move.w      -(a0),$4000.W
         move.l      -(a0),$4000.W
         move        -(a0),$4000.L
         move.b      -(a0),$4000.L
         move.w      -(a0),$4000.L
         move.l      -(a0),$4000.L
         move        42(a0),d0
         move.b      42(a0),d0
         move.w      42(a0),d0
         move.l      42(a0),d0
         move        42(a0),(a0)
         move.b      42(a0),(a0)
         move.w      42(a0),(a0)
         move.l      42(a0),(a0)
         move        42(a0),(a0)+
         move.b      42(a0),(a0)+
         move.w      42(a0),(a0)+
         move.l      42(a0),(a0)+
         move        42(a0),-(a0)
         move.b      42(a0),-(a0)
         move.w      42(a0),-(a0)
         move.l      42(a0),-(a0)
         move        42(a0),42(a0)
         move.b      42(a0),42(a0)
         move.w      42(a0),42(a0)
         move.l      42(a0),42(a0)
         move        42(a0),(-42,a0)
         move.b      42(a0),(-42,a0)
         move.w      42(a0),(-42,a0)
         move.l      42(a0),(-42,a0)
         move        42(a0),12(a0,d0)
         move.b      42(a0),12(a0,d0)
         move.w      42(a0),12(a0,d0)
         move.l      42(a0),12(a0,d0)
         move        42(a0),(12,a0,a0)
         move.b      42(a0),(12,a0,a0)
         move.w      42(a0),(12,a0,a0)
         move.l      42(a0),(12,a0,a0)
         move        42(a0),$4000
         move.b      42(a0),$4000
         move.w      42(a0),$4000
         move.l      42(a0),$4000
         move        42(a0),$4000.W
         move.b      42(a0),$4000.W
         move.w      42(a0),$4000.W
         move.l      42(a0),$4000.W
         move        42(a0),$4000.L
         move.b      42(a0),$4000.L
         move.w      42(a0),$4000.L
         move.l      42(a0),$4000.L
         move        (-42,a0),d0
         move.b      (-42,a0),d0
         move.w      (-42,a0),d0
         move.l      (-42,a0),d0
         move        (-42,a0),(a0)
         move.b      (-42,a0),(a0)
         move.w      (-42,a0),(a0)
         move.l      (-42,a0),(a0)
         move        (-42,a0),(a0)+
         move.b      (-42,a0),(a0)+
         move.w      (-42,a0),(a0)+
         move.l      (-42,a0),(a0)+
         move        (-42,a0),-(a0)
         move.b      (-42,a0),-(a0)
         move.w      (-42,a0),-(a0)
         move.l      (-42,a0),-(a0)
         move        (-42,a0),42(a0)
         move.b      (-42,a0),42(a0)
         move.w      (-42,a0),42(a0)
         move.l      (-42,a0),42(a0)
         move        (-42,a0),(-42,a0)
         move.b      (-42,a0),(-42,a0)
         move.w      (-42,a0),(-42,a0)
         move.l      (-42,a0),(-42,a0)
         move        (-42,a0),12(a0,d0)
         move.b      (-42,a0),12(a0,d0)
         move.w      (-42,a0),12(a0,d0)
         move.l      (-42,a0),12(a0,d0)
         move        (-42,a0),(12,a0,a0)
         move.b      (-42,a0),(12,a0,a0)
         move.w      (-42,a0),(12,a0,a0)
         move.l      (-42,a0),(12,a0,a0)
         move        (-42,a0),$4000
         move.b      (-42,a0),$4000
         move.w      (-42,a0),$4000
         move.l      (-42,a0),$4000
         move        (-42,a0),$4000.W
         move.b      (-42,a0),$4000.W
         move.w      (-42,a0),$4000.W
         move.l      (-42,a0),$4000.W
         move        (-42,a0),$4000.L
         move.b      (-42,a0),$4000.L
         move.w      (-42,a0),$4000.L
         move.l      (-42,a0),$4000.L
         move        12(a0,d0),d0
         move.b      12(a0,d0),d0
         move.w      12(a0,d0),d0
         move.l      12(a0,d0),d0
         move        12(a0,d0),(a0)
         move.b      12(a0,d0),(a0)
         move.w      12(a0,d0),(a0)
         move.l      12(a0,d0),(a0)
         move        12(a0,d0),(a0)+
         move.b      12(a0,d0),(a0)+
         move.w      12(a0,d0),(a0)+
         move.l      12(a0,d0),(a0)+
         move        12(a0,d0),-(a0)
         move.b      12(a0,d0),-(a0)
         move.w      12(a0,d0),-(a0)
         move.l      12(a0,d0),-(a0)
         move        12(a0,d0),42(a0)
         move.b      12(a0,d0),42(a0)
         move.w      12(a0,d0),42(a0)
         move.l      12(a0,d0),42(a0)
         move        12(a0,d0),(-42,a0)
         move.b      12(a0,d0),(-42,a0)
         move.w      12(a0,d0),(-42,a0)
         move.l      12(a0,d0),(-42,a0)
         move        12(a0,d0),12(a0,d0)
         move.b      12(a0,d0),12(a0,d0)
         move.w      12(a0,d0),12(a0,d0)
         move.l      12(a0,d0),12(a0,d0)
         move        12(a0,d0),(12,a0,a0)
         move.b      12(a0,d0),(12,a0,a0)
         move.w      12(a0,d0),(12,a0,a0)
         move.l      12(a0,d0),(12,a0,a0)
         move        12(a0,d0),$4000
         move.b      12(a0,d0),$4000
         move.w      12(a0,d0),$4000
         move.l      12(a0,d0),$4000
         move        12(a0,d0),$4000.W
         move.b      12(a0,d0),$4000.W
         move.w      12(a0,d0),$4000.W
         move.l      12(a0,d0),$4000.W
         move        12(a0,d0),$4000.L
         move.b      12(a0,d0),$4000.L
         move.w      12(a0,d0),$4000.L
         move.l      12(a0,d0),$4000.L
         move        (12,a0,a0),d0
         move.b      (12,a0,a0),d0
         move.w      (12,a0,a0),d0
         move.l      (12,a0,a0),d0
         move        (12,a0,a0),(a0)
         move.b      (12,a0,a0),(a0)
         move.w      (12,a0,a0),(a0)
         move.l      (12,a0,a0),(a0)
         move        (12,a0,a0),(a0)+
         move.b      (12,a0,a0),(a0)+
         move.w      (12,a0,a0),(a0)+
         move.l      (12,a0,a0),(a0)+
         move        (12,a0,a0),-(a0)
         move.b      (12,a0,a0),-(a0)
         move.w      (12,a0,a0),-(a0)
         move.l      (12,a0,a0),-(a0)
         move        (12,a0,a0),42(a0)
         move.b      (12,a0,a0),42(a0)
         move.w      (12,a0,a0),42(a0)
         move.l      (12,a0,a0),42(a0)
         move        (12,a0,a0),(-42,a0)
         move.b      (12,a0,a0),(-42,a0)
         move.w      (12,a0,a0),(-42,a0)
         move.l      (12,a0,a0),(-42,a0)
         move        (12,a0,a0),12(a0,d0)
         move.b      (12,a0,a0),12(a0,d0)
         move.w      (12,a0,a0),12(a0,d0)
         move.l      (12,a0,a0),12(a0,d0)
         move        (12,a0,a0),(12,a0,a0)
         move.b      (12,a0,a0),(12,a0,a0)
         move.w      (12,a0,a0),(12,a0,a0)
         move.l      (12,a0,a0),(12,a0,a0)
         move        (12,a0,a0),$4000
         move.b      (12,a0,a0),$4000
         move.w      (12,a0,a0),$4000
         move.l      (12,a0,a0),$4000
         move        (12,a0,a0),$4000.W
         move.b      (12,a0,a0),$4000.W
         move.w      (12,a0,a0),$4000.W
         move.l      (12,a0,a0),$4000.W
         move        (12,a0,a0),$4000.L
         move.b      (12,a0,a0),$4000.L
         move.w      (12,a0,a0),$4000.L
         move.l      (12,a0,a0),$4000.L
         move        $4000,d0
         move.b      $4000,d0
         move.w      $4000,d0
         move.l      $4000,d0
         move        $4000,(a0)
         move.b      $4000,(a0)
         move.w      $4000,(a0)
         move.l      $4000,(a0)
         move        $4000,(a0)+
         move.b      $4000,(a0)+
         move.w      $4000,(a0)+
         move.l      $4000,(a0)+
         move        $4000,-(a0)
         move.b      $4000,-(a0)
         move.w      $4000,-(a0)
         move.l      $4000,-(a0)
         move        $4000,42(a0)
         move.b      $4000,42(a0)
         move.w      $4000,42(a0)
         move.l      $4000,42(a0)
         move        $4000,(-42,a0)
         move.b      $4000,(-42,a0)
         move.w      $4000,(-42,a0)
         move.l      $4000,(-42,a0)
         move        $4000,12(a0,d0)
         move.b      $4000,12(a0,d0)
         move.w      $4000,12(a0,d0)
         move.l      $4000,12(a0,d0)
         move        $4000,(12,a0,a0)
         move.b      $4000,(12,a0,a0)
         move.w      $4000,(12,a0,a0)
         move.l      $4000,(12,a0,a0)
         move        $4000,$4000
         move.b      $4000,$4000
         move.w      $4000,$4000
         move.l      $4000,$4000
         move        $4000,$4000.W
         move.b      $4000,$4000.W
         move.w      $4000,$4000.W
         move.l      $4000,$4000.W
         move        $4000,$4000.L
         move.b      $4000,$4000.L
         move.w      $4000,$4000.L
         move.l      $4000,$4000.L
         move        $4000.W,d0
         move.b      $4000.W,d0
         move.w      $4000.W,d0
         move.l      $4000.W,d0
         move        $4000.W,(a0)
         move.b      $4000.W,(a0)
         move.w      $4000.W,(a0)
         move.l      $4000.W,(a0)
         move        $4000.W,(a0)+
         move.b      $4000.W,(a0)+
         move.w      $4000.W,(a0)+
         move.l      $4000.W,(a0)+
         move        $4000.W,-(a0)
         move.b      $4000.W,-(a0)
         move.w      $4000.W,-(a0)
         move.l      $4000.W,-(a0)
         move        $4000.W,42(a0)
         move.b      $4000.W,42(a0)
         move.w      $4000.W,42(a0)
         move.l      $4000.W,42(a0)
         move        $4000.W,(-42,a0)
         move.b      $4000.W,(-42,a0)
         move.w      $4000.W,(-42,a0)
         move.l      $4000.W,(-42,a0)
         move        $4000.W,12(a0,d0)
         move.b      $4000.W,12(a0,d0)
         move.w      $4000.W,12(a0,d0)
         move.l      $4000.W,12(a0,d0)
         move        $4000.W,(12,a0,a0)
         move.b      $4000.W,(12,a0,a0)
         move.w      $4000.W,(12,a0,a0)
         move.l      $4000.W,(12,a0,a0)
         move        $4000.W,$4000
         move.b      $4000.W,$4000
         move.w      $4000.W,$4000
         move.l      $4000.W,$4000
         move        $4000.W,$4000.W
         move.b      $4000.W,$4000.W
         move.w      $4000.W,$4000.W
         move.l      $4000.W,$4000.W
         move        $4000.W,$4000.L
         move.b      $4000.W,$4000.L
         move.w      $4000.W,$4000.L
         move.l      $4000.W,$4000.L
         move        $4000.L,d0
         move.b      $4000.L,d0
         move.w      $4000.L,d0
         move.l      $4000.L,d0
         move        $4000.L,(a0)
         move.b      $4000.L,(a0)
         move.w      $4000.L,(a0)
         move.l      $4000.L,(a0)
         move        $4000.L,(a0)+
         move.b      $4000.L,(a0)+
         move.w      $4000.L,(a0)+
         move.l      $4000.L,(a0)+
         move        $4000.L,-(a0)
         move.b      $4000.L,-(a0)
         move.w      $4000.L,-(a0)
         move.l      $4000.L,-(a0)
         move        $4000.L,42(a0)
         move.b      $4000.L,42(a0)
         move.w      $4000.L,42(a0)
         move.l      $4000.L,42(a0)
         move        $4000.L,(-42,a0)
         move.b      $4000.L,(-42,a0)
         move.w      $4000.L,(-42,a0)
         move.l      $4000.L,(-42,a0)
         move        $4000.L,12(a0,d0)
         move.b      $4000.L,12(a0,d0)
         move.w      $4000.L,12(a0,d0)
         move.l      $4000.L,12(a0,d0)
         move        $4000.L,(12,a0,a0)
         move.b      $4000.L,(12,a0,a0)
         move.w      $4000.L,(12,a0,a0)
         move.l      $4000.L,(12,a0,a0)
         move        $4000.L,$4000
         move.b      $4000.L,$4000
         move.w      $4000.L,$4000
         move.l      $4000.L,$4000
         move        $4000.L,$4000.W
         move.b      $4000.L,$4000.W
         move.w      $4000.L,$4000.W
         move.l      $4000.L,$4000.W
         move        $4000.L,$4000.L
         move.b      $4000.L,$4000.L
         move.w      $4000.L,$4000.L
         move.l      $4000.L,$4000.L
         move        (PC),d0
         move.b      (PC),d0
         move.w      (PC),d0
         move.l      (PC),d0
         move        (PC),(a0)
         move.b      (PC),(a0)
         move.w      (PC),(a0)
         move.l      (PC),(a0)
         move        (PC),(a0)+
         move.b      (PC),(a0)+
         move.w      (PC),(a0)+
         move.l      (PC),(a0)+
         move        (PC),-(a0)
         move.b      (PC),-(a0)
         move.w      (PC),-(a0)
         move.l      (PC),-(a0)
         move        (PC),42(a0)
         move.b      (PC),42(a0)
         move.w      (PC),42(a0)
         move.l      (PC),42(a0)
         move        (PC),(-42,a0)
         move.b      (PC),(-42,a0)
         move.w      (PC),(-42,a0)
         move.l      (PC),(-42,a0)
         move        (PC),12(a0,d0)
         move.b      (PC),12(a0,d0)
         move.w      (PC),12(a0,d0)
         move.l      (PC),12(a0,d0)
         move        (PC),(12,a0,a0)
         move.b      (PC),(12,a0,a0)
         move.w      (PC),(12,a0,a0)
         move.l      (PC),(12,a0,a0)
         move        (PC),$4000
         move.b      (PC),$4000
         move.w      (PC),$4000
         move.l      (PC),$4000
         move        (PC),$4000.W
         move.b      (PC),$4000.W
         move.w      (PC),$4000.W
         move.l      (PC),$4000.W
         move        (PC),$4000.L
         move.b      (PC),$4000.L
         move.w      (PC),$4000.L
         move.l      (PC),$4000.L
         move        66(PC),d0
         move.b      66(PC),d0
         move.w      66(PC),d0
         move.l      66(PC),d0
         move        66(PC),(a0)
         move.b      66(PC),(a0)
         move.w      66(PC),(a0)
         move.l      66(PC),(a0)
         move        66(PC),(a0)+
         move.b      66(PC),(a0)+
         move.w      66(PC),(a0)+
         move.l      66(PC),(a0)+
         move        66(PC),-(a0)
         move.b      66(PC),-(a0)
         move.w      66(PC),-(a0)
         move.l      66(PC),-(a0)
         move        66(PC),42(a0)
         move.b      66(PC),42(a0)
         move.w      66(PC),42(a0)
         move.l      66(PC),42(a0)
         move        66(PC),(-42,a0)
         move.b      66(PC),(-42,a0)
         move.w      66(PC),(-42,a0)
         move.l      66(PC),(-42,a0)
         move        66(PC),12(a0,d0)
         move.b      66(PC),12(a0,d0)
         move.w      66(PC),12(a0,d0)
         move.l      66(PC),12(a0,d0)
         move        66(PC),(12,a0,a0)
         move.b      66(PC),(12,a0,a0)
         move.w      66(PC),(12,a0,a0)
         move.l      66(PC),(12,a0,a0)
         move        66(PC),$4000
         move.b      66(PC),$4000
         move.w      66(PC),$4000
         move.l      66(PC),$4000
         move        66(PC),$4000.W
         move.b      66(PC),$4000.W
         move.w      66(PC),$4000.W
         move.l      66(PC),$4000.W
         move        66(PC),$4000.L
         move.b      66(PC),$4000.L
         move.w      66(PC),$4000.L
         move.l      66(PC),$4000.L
         move        (-66,PC),d0
         move.b      (-66,PC),d0
         move.w      (-66,PC),d0
         move.l      (-66,PC),d0
         move        (-66,PC),(a0)
         move.b      (-66,PC),(a0)
         move.w      (-66,PC),(a0)
         move.l      (-66,PC),(a0)
         move        (-66,PC),(a0)+
         move.b      (-66,PC),(a0)+
         move.w      (-66,PC),(a0)+
         move.l      (-66,PC),(a0)+
         move        (-66,PC),-(a0)
         move.b      (-66,PC),-(a0)
         move.w      (-66,PC),-(a0)
         move.l      (-66,PC),-(a0)
         move        (-66,PC),42(a0)
         move.b      (-66,PC),42(a0)
         move.w      (-66,PC),42(a0)
         move.l      (-66,PC),42(a0)
         move        (-66,PC),(-42,a0)
         move.b      (-66,PC),(-42,a0)
         move.w      (-66,PC),(-42,a0)
         move.l      (-66,PC),(-42,a0)
         move        (-66,PC),12(a0,d0)
         move.b      (-66,PC),12(a0,d0)
         move.w      (-66,PC),12(a0,d0)
         move.l      (-66,PC),12(a0,d0)
         move        (-66,PC),(12,a0,a0)
         move.b      (-66,PC),(12,a0,a0)
         move.w      (-66,PC),(12,a0,a0)
         move.l      (-66,PC),(12,a0,a0)
         move        (-66,PC),$4000
         move.b      (-66,PC),$4000
         move.w      (-66,PC),$4000
         move.l      (-66,PC),$4000
         move        (-66,PC),$4000.W
         move.b      (-66,PC),$4000.W
         move.w      (-66,PC),$4000.W
         move.l      (-66,PC),$4000.W
         move        (-66,PC),$4000.L
         move.b      (-66,PC),$4000.L
         move.w      (-66,PC),$4000.L
         move.l      (-66,PC),$4000.L
         move        66(PC,d0),d0
         move.b      66(PC,d0),d0
         move.w      66(PC,d0),d0
         move.l      66(PC,d0),d0
         move        66(PC,d0),(a0)
         move.b      66(PC,d0),(a0)
         move.w      66(PC,d0),(a0)
         move.l      66(PC,d0),(a0)
         move        66(PC,d0),(a0)+
         move.b      66(PC,d0),(a0)+
         move.w      66(PC,d0),(a0)+
         move.l      66(PC,d0),(a0)+
         move        66(PC,d0),-(a0)
         move.b      66(PC,d0),-(a0)
         move.w      66(PC,d0),-(a0)
         move.l      66(PC,d0),-(a0)
         move        66(PC,d0),42(a0)
         move.b      66(PC,d0),42(a0)
         move.w      66(PC,d0),42(a0)
         move.l      66(PC,d0),42(a0)
         move        66(PC,d0),(-42,a0)
         move.b      66(PC,d0),(-42,a0)
         move.w      66(PC,d0),(-42,a0)
         move.l      66(PC,d0),(-42,a0)
         move        66(PC,d0),12(a0,d0)
         move.b      66(PC,d0),12(a0,d0)
         move.w      66(PC,d0),12(a0,d0)
         move.l      66(PC,d0),12(a0,d0)
         move        66(PC,d0),(12,a0,a0)
         move.b      66(PC,d0),(12,a0,a0)
         move.w      66(PC,d0),(12,a0,a0)
         move.l      66(PC,d0),(12,a0,a0)
         move        66(PC,d0),$4000
         move.b      66(PC,d0),$4000
         move.w      66(PC,d0),$4000
         move.l      66(PC,d0),$4000
         move        66(PC,d0),$4000.W
         move.b      66(PC,d0),$4000.W
         move.w      66(PC,d0),$4000.W
         move.l      66(PC,d0),$4000.W
         move        66(PC,d0),$4000.L
         move.b      66(PC,d0),$4000.L
         move.w      66(PC,d0),$4000.L
         move.l      66(PC,d0),$4000.L
         move        (66,PC,a0),d0
         move.b      (66,PC,a0),d0
         move.w      (66,PC,a0),d0
         move.l      (66,PC,a0),d0
         move        (66,PC,a0),(a0)
         move.b      (66,PC,a0),(a0)
         move.w      (66,PC,a0),(a0)
         move.l      (66,PC,a0),(a0)
         move        (66,PC,a0),(a0)+
         move.b      (66,PC,a0),(a0)+
         move.w      (66,PC,a0),(a0)+
         move.l      (66,PC,a0),(a0)+
         move        (66,PC,a0),-(a0)
         move.b      (66,PC,a0),-(a0)
         move.w      (66,PC,a0),-(a0)
         move.l      (66,PC,a0),-(a0)
         move        (66,PC,a0),42(a0)
         move.b      (66,PC,a0),42(a0)
         move.w      (66,PC,a0),42(a0)
         move.l      (66,PC,a0),42(a0)
         move        (66,PC,a0),(-42,a0)
         move.b      (66,PC,a0),(-42,a0)
         move.w      (66,PC,a0),(-42,a0)
         move.l      (66,PC,a0),(-42,a0)
         move        (66,PC,a0),12(a0,d0)
         move.b      (66,PC,a0),12(a0,d0)
         move.w      (66,PC,a0),12(a0,d0)
         move.l      (66,PC,a0),12(a0,d0)
         move        (66,PC,a0),(12,a0,a0)
         move.b      (66,PC,a0),(12,a0,a0)
         move.w      (66,PC,a0),(12,a0,a0)
         move.l      (66,PC,a0),(12,a0,a0)
         move        (66,PC,a0),$4000
         move.b      (66,PC,a0),$4000
         move.w      (66,PC,a0),$4000
         move.l      (66,PC,a0),$4000
         move        (66,PC,a0),$4000.W
         move.b      (66,PC,a0),$4000.W
         move.w      (66,PC,a0),$4000.W
         move.l      (66,PC,a0),$4000.W
         move        (66,PC,a0),$4000.L
         move.b      (66,PC,a0),$4000.L
         move.w      (66,PC,a0),$4000.L
         move.l      (66,PC,a0),$4000.L

* M68kMnemonic{move, deprecated=false, src=CCR_REGISTER, dst=ALTERABLE_DATA, [WORD], [M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        CCR,d0
         move.w      CCR,d0
         move        CCR,(a0)
         move.w      CCR,(a0)
         move        CCR,(a0)+
         move.w      CCR,(a0)+
         move        CCR,-(a0)
         move.w      CCR,-(a0)
         move        CCR,42(a0)
         move.w      CCR,42(a0)
         move        CCR,(-42,a0)
         move.w      CCR,(-42,a0)
         move        CCR,12(a0,d0)
         move.w      CCR,12(a0,d0)
         move        CCR,(12,a0,a0)
         move.w      CCR,(12,a0,a0)
         move        CCR,$4000
         move.w      CCR,$4000
         move        CCR,$4000.W
         move.w      CCR,$4000.W
         move        CCR,$4000.L
         move.w      CCR,$4000.L

* M68kMnemonic{move, deprecated=false, src=SR_REGISTER, dst=ALTERABLE_DATA, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        SR,d0
         move.w      SR,d0
         move        SR,(a0)
         move.w      SR,(a0)
         move        SR,(a0)+
         move.w      SR,(a0)+
         move        SR,-(a0)
         move.w      SR,-(a0)
         move        SR,42(a0)
         move.w      SR,42(a0)
         move        SR,(-42,a0)
         move.w      SR,(-42,a0)
         move        SR,12(a0,d0)
         move.w      SR,12(a0,d0)
         move        SR,(12,a0,a0)
         move.w      SR,(12,a0,a0)
         move        SR,$4000
         move.w      SR,$4000
         move        SR,$4000.W
         move.w      SR,$4000.W
         move        SR,$4000.L
         move.w      SR,$4000.L

* M68kMnemonic{move, deprecated=false, src=DATA, dst=CCR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        d0,CCR
         move.w      d0,CCR
         move        #42,CCR
         move.w      #42,CCR
         move        (a0),CCR
         move.w      (a0),CCR
         move        (a0)+,CCR
         move.w      (a0)+,CCR
         move        -(a0),CCR
         move.w      -(a0),CCR
         move        42(a0),CCR
         move.w      42(a0),CCR
         move        (-42,a0),CCR
         move.w      (-42,a0),CCR
         move        12(a0,d0),CCR
         move.w      12(a0,d0),CCR
         move        (12,a0,a0),CCR
         move.w      (12,a0,a0),CCR
         move        $4000,CCR
         move.w      $4000,CCR
         move        $4000.W,CCR
         move.w      $4000.W,CCR
         move        $4000.L,CCR
         move.w      $4000.L,CCR
         move        (PC),CCR
         move.w      (PC),CCR
         move        66(PC),CCR
         move.w      66(PC),CCR
         move        (-66,PC),CCR
         move.w      (-66,PC),CCR
         move        66(PC,d0),CCR
         move.w      66(PC,d0),CCR
         move        (66,PC,a0),CCR
         move.w      (66,PC,a0),CCR

* M68kMnemonic{move, deprecated=false, src=DATA, dst=SR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        d0,SR
         move.w      d0,SR
         move        #42,SR
         move.w      #42,SR
         move        (a0),SR
         move.w      (a0),SR
         move        (a0)+,SR
         move.w      (a0)+,SR
         move        -(a0),SR
         move.w      -(a0),SR
         move        42(a0),SR
         move.w      42(a0),SR
         move        (-42,a0),SR
         move.w      (-42,a0),SR
         move        12(a0,d0),SR
         move.w      12(a0,d0),SR
         move        (12,a0,a0),SR
         move.w      (12,a0,a0),SR
         move        $4000,SR
         move.w      $4000,SR
         move        $4000.W,SR
         move.w      $4000.W,SR
         move        $4000.L,SR
         move.w      $4000.L,SR
         move        (PC),SR
         move.w      (PC),SR
         move        66(PC),SR
         move.w      66(PC),SR
         move        (-66,PC),SR
         move.w      (-66,PC),SR
         move        66(PC,d0),SR
         move.w      66(PC,d0),SR
         move        (66,PC,a0),SR
         move.w      (66,PC,a0),SR

* M68kMnemonic{move, deprecated=false, src=USP_REGISTER, dst=ADDRESS_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        USP,a0
         move.l      USP,a0

* M68kMnemonic{move, deprecated=false, src=ADDRESS_REGISTER, dst=USP_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         move        a0,USP
         move.l      a0,USP


********************************************************************************

* M68kMnemonic{movea, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movea       d0,a0
         movea.w     d0,a0
         movea.l     d0,a0
         movea       a0,a0
         movea.w     a0,a0
         movea.l     a0,a0
         movea       (a0),a0
         movea.w     (a0),a0
         movea.l     (a0),a0
         movea       (a0)+,a0
         movea.w     (a0)+,a0
         movea.l     (a0)+,a0
         movea       -(a0),a0
         movea.w     -(a0),a0
         movea.l     -(a0),a0
         movea       42(a0),a0
         movea.w     42(a0),a0
         movea.l     42(a0),a0
         movea       (-42,a0),a0
         movea.w     (-42,a0),a0
         movea.l     (-42,a0),a0
         movea       12(a0,d0),a0
         movea.w     12(a0,d0),a0
         movea.l     12(a0,d0),a0
         movea       (12,a0,a0),a0
         movea.w     (12,a0,a0),a0
         movea.l     (12,a0,a0),a0
         movea       $4000,a0
         movea.w     $4000,a0
         movea.l     $4000,a0
         movea       $4000.W,a0
         movea.w     $4000.W,a0
         movea.l     $4000.W,a0
         movea       $4000.L,a0
         movea.w     $4000.L,a0
         movea.l     $4000.L,a0
         movea       (PC),a0
         movea.w     (PC),a0
         movea.l     (PC),a0
         movea       66(PC),a0
         movea.w     66(PC),a0
         movea.l     66(PC),a0
         movea       (-66,PC),a0
         movea.w     (-66,PC),a0
         movea.l     (-66,PC),a0
         movea       66(PC,d0),a0
         movea.w     66(PC,d0),a0
         movea.l     66(PC,d0),a0
         movea       (66,PC,a0),a0
         movea.w     (66,PC,a0),a0
         movea.l     (66,PC,a0),a0
         movea       #42,a0
         movea.w     #42,a0
         movea.l     #42,a0

* M68kMnemonic{movea, deprecated=true, src=ADDRESS_REGISTER, dst=ALTERABLE, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
;        movea       a0,d0 ; DEPRECATED
;        movea.w     a0,d0 ; DEPRECATED
;        movea.l     a0,d0 ; DEPRECATED
         movea       a0,a0
         movea.w     a0,a0
         movea.l     a0,a0
;        movea       a0,(a0) ; DEPRECATED
;        movea.w     a0,(a0) ; DEPRECATED
;        movea.l     a0,(a0) ; DEPRECATED
;        movea       a0,(a0)+ ; DEPRECATED
;        movea.w     a0,(a0)+ ; DEPRECATED
;        movea.l     a0,(a0)+ ; DEPRECATED
;        movea       a0,-(a0) ; DEPRECATED
;        movea.w     a0,-(a0) ; DEPRECATED
;        movea.l     a0,-(a0) ; DEPRECATED
;        movea       a0,42(a0) ; DEPRECATED
;        movea.w     a0,42(a0) ; DEPRECATED
;        movea.l     a0,42(a0) ; DEPRECATED
;        movea       a0,(-42,a0) ; DEPRECATED
;        movea.w     a0,(-42,a0) ; DEPRECATED
;        movea.l     a0,(-42,a0) ; DEPRECATED
;        movea       a0,12(a0,d0) ; DEPRECATED
;        movea.w     a0,12(a0,d0) ; DEPRECATED
;        movea.l     a0,12(a0,d0) ; DEPRECATED
;        movea       a0,(12,a0,a0) ; DEPRECATED
;        movea.w     a0,(12,a0,a0) ; DEPRECATED
;        movea.l     a0,(12,a0,a0) ; DEPRECATED
;        movea       a0,$4000 ; DEPRECATED
;        movea.w     a0,$4000 ; DEPRECATED
;        movea.l     a0,$4000 ; DEPRECATED
;        movea       a0,$4000.W ; DEPRECATED
;        movea.w     a0,$4000.W ; DEPRECATED
;        movea.l     a0,$4000.W ; DEPRECATED
;        movea       a0,$4000.L ; DEPRECATED
;        movea.w     a0,$4000.L ; DEPRECATED
;        movea.l     a0,$4000.L ; DEPRECATED

* M68kMnemonic{movea, deprecated=true, src=DATA, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
;        movea       d0,d0 ; DEPRECATED
;        movea.b     d0,d0 ; DEPRECATED
;        movea.w     d0,d0 ; DEPRECATED
;        movea.l     d0,d0 ; DEPRECATED
;        movea       d0,(a0) ; DEPRECATED
;        movea.b     d0,(a0) ; DEPRECATED
;        movea.w     d0,(a0) ; DEPRECATED
;        movea.l     d0,(a0) ; DEPRECATED
;        movea       d0,(a0)+ ; DEPRECATED
;        movea.b     d0,(a0)+ ; DEPRECATED
;        movea.w     d0,(a0)+ ; DEPRECATED
;        movea.l     d0,(a0)+ ; DEPRECATED
;        movea       d0,-(a0) ; DEPRECATED
;        movea.b     d0,-(a0) ; DEPRECATED
;        movea.w     d0,-(a0) ; DEPRECATED
;        movea.l     d0,-(a0) ; DEPRECATED
;        movea       d0,42(a0) ; DEPRECATED
;        movea.b     d0,42(a0) ; DEPRECATED
;        movea.w     d0,42(a0) ; DEPRECATED
;        movea.l     d0,42(a0) ; DEPRECATED
;        movea       d0,(-42,a0) ; DEPRECATED
;        movea.b     d0,(-42,a0) ; DEPRECATED
;        movea.w     d0,(-42,a0) ; DEPRECATED
;        movea.l     d0,(-42,a0) ; DEPRECATED
;        movea       d0,12(a0,d0) ; DEPRECATED
;        movea.b     d0,12(a0,d0) ; DEPRECATED
;        movea.w     d0,12(a0,d0) ; DEPRECATED
;        movea.l     d0,12(a0,d0) ; DEPRECATED
;        movea       d0,(12,a0,a0) ; DEPRECATED
;        movea.b     d0,(12,a0,a0) ; DEPRECATED
;        movea.w     d0,(12,a0,a0) ; DEPRECATED
;        movea.l     d0,(12,a0,a0) ; DEPRECATED
;        movea       d0,$4000 ; DEPRECATED
;        movea.b     d0,$4000 ; DEPRECATED
;        movea.w     d0,$4000 ; DEPRECATED
;        movea.l     d0,$4000 ; DEPRECATED
;        movea       d0,$4000.W ; DEPRECATED
;        movea.b     d0,$4000.W ; DEPRECATED
;        movea.w     d0,$4000.W ; DEPRECATED
;        movea.l     d0,$4000.W ; DEPRECATED
;        movea       d0,$4000.L ; DEPRECATED
;        movea.b     d0,$4000.L ; DEPRECATED
;        movea.w     d0,$4000.L ; DEPRECATED
;        movea.l     d0,$4000.L ; DEPRECATED
;        movea       #42,d0 ; DEPRECATED
;        movea.b     #42,d0 ; DEPRECATED
;        movea.w     #42,d0 ; DEPRECATED
;        movea.l     #42,d0 ; DEPRECATED
;        movea       #42,(a0) ; DEPRECATED
;        movea.b     #42,(a0) ; DEPRECATED
;        movea.w     #42,(a0) ; DEPRECATED
;        movea.l     #42,(a0) ; DEPRECATED
;        movea       #42,(a0)+ ; DEPRECATED
;        movea.b     #42,(a0)+ ; DEPRECATED
;        movea.w     #42,(a0)+ ; DEPRECATED
;        movea.l     #42,(a0)+ ; DEPRECATED
;        movea       #42,-(a0) ; DEPRECATED
;        movea.b     #42,-(a0) ; DEPRECATED
;        movea.w     #42,-(a0) ; DEPRECATED
;        movea.l     #42,-(a0) ; DEPRECATED
;        movea       #42,42(a0) ; DEPRECATED
;        movea.b     #42,42(a0) ; DEPRECATED
;        movea.w     #42,42(a0) ; DEPRECATED
;        movea.l     #42,42(a0) ; DEPRECATED
;        movea       #42,(-42,a0) ; DEPRECATED
;        movea.b     #42,(-42,a0) ; DEPRECATED
;        movea.w     #42,(-42,a0) ; DEPRECATED
;        movea.l     #42,(-42,a0) ; DEPRECATED
;        movea       #42,12(a0,d0) ; DEPRECATED
;        movea.b     #42,12(a0,d0) ; DEPRECATED
;        movea.w     #42,12(a0,d0) ; DEPRECATED
;        movea.l     #42,12(a0,d0) ; DEPRECATED
;        movea       #42,(12,a0,a0) ; DEPRECATED
;        movea.b     #42,(12,a0,a0) ; DEPRECATED
;        movea.w     #42,(12,a0,a0) ; DEPRECATED
;        movea.l     #42,(12,a0,a0) ; DEPRECATED
;        movea       #42,$4000 ; DEPRECATED
;        movea.b     #42,$4000 ; DEPRECATED
;        movea.w     #42,$4000 ; DEPRECATED
;        movea.l     #42,$4000 ; DEPRECATED
;        movea       #42,$4000.W ; DEPRECATED
;        movea.b     #42,$4000.W ; DEPRECATED
;        movea.w     #42,$4000.W ; DEPRECATED
;        movea.l     #42,$4000.W ; DEPRECATED
;        movea       #42,$4000.L ; DEPRECATED
;        movea.b     #42,$4000.L ; DEPRECATED
;        movea.w     #42,$4000.L ; DEPRECATED
;        movea.l     #42,$4000.L ; DEPRECATED
;        movea       (a0),d0 ; DEPRECATED
;        movea.b     (a0),d0 ; DEPRECATED
;        movea.w     (a0),d0 ; DEPRECATED
;        movea.l     (a0),d0 ; DEPRECATED
;        movea       (a0),(a0) ; DEPRECATED
;        movea.b     (a0),(a0) ; DEPRECATED
;        movea.w     (a0),(a0) ; DEPRECATED
;        movea.l     (a0),(a0) ; DEPRECATED
;        movea       (a0),(a0)+ ; DEPRECATED
;        movea.b     (a0),(a0)+ ; DEPRECATED
;        movea.w     (a0),(a0)+ ; DEPRECATED
;        movea.l     (a0),(a0)+ ; DEPRECATED
;        movea       (a0),-(a0) ; DEPRECATED
;        movea.b     (a0),-(a0) ; DEPRECATED
;        movea.w     (a0),-(a0) ; DEPRECATED
;        movea.l     (a0),-(a0) ; DEPRECATED
;        movea       (a0),42(a0) ; DEPRECATED
;        movea.b     (a0),42(a0) ; DEPRECATED
;        movea.w     (a0),42(a0) ; DEPRECATED
;        movea.l     (a0),42(a0) ; DEPRECATED
;        movea       (a0),(-42,a0) ; DEPRECATED
;        movea.b     (a0),(-42,a0) ; DEPRECATED
;        movea.w     (a0),(-42,a0) ; DEPRECATED
;        movea.l     (a0),(-42,a0) ; DEPRECATED
;        movea       (a0),12(a0,d0) ; DEPRECATED
;        movea.b     (a0),12(a0,d0) ; DEPRECATED
;        movea.w     (a0),12(a0,d0) ; DEPRECATED
;        movea.l     (a0),12(a0,d0) ; DEPRECATED
;        movea       (a0),(12,a0,a0) ; DEPRECATED
;        movea.b     (a0),(12,a0,a0) ; DEPRECATED
;        movea.w     (a0),(12,a0,a0) ; DEPRECATED
;        movea.l     (a0),(12,a0,a0) ; DEPRECATED
;        movea       (a0),$4000 ; DEPRECATED
;        movea.b     (a0),$4000 ; DEPRECATED
;        movea.w     (a0),$4000 ; DEPRECATED
;        movea.l     (a0),$4000 ; DEPRECATED
;        movea       (a0),$4000.W ; DEPRECATED
;        movea.b     (a0),$4000.W ; DEPRECATED
;        movea.w     (a0),$4000.W ; DEPRECATED
;        movea.l     (a0),$4000.W ; DEPRECATED
;        movea       (a0),$4000.L ; DEPRECATED
;        movea.b     (a0),$4000.L ; DEPRECATED
;        movea.w     (a0),$4000.L ; DEPRECATED
;        movea.l     (a0),$4000.L ; DEPRECATED
;        movea       (a0)+,d0 ; DEPRECATED
;        movea.b     (a0)+,d0 ; DEPRECATED
;        movea.w     (a0)+,d0 ; DEPRECATED
;        movea.l     (a0)+,d0 ; DEPRECATED
;        movea       (a0)+,(a0) ; DEPRECATED
;        movea.b     (a0)+,(a0) ; DEPRECATED
;        movea.w     (a0)+,(a0) ; DEPRECATED
;        movea.l     (a0)+,(a0) ; DEPRECATED
;        movea       (a0)+,(a0)+ ; DEPRECATED
;        movea.b     (a0)+,(a0)+ ; DEPRECATED
;        movea.w     (a0)+,(a0)+ ; DEPRECATED
;        movea.l     (a0)+,(a0)+ ; DEPRECATED
;        movea       (a0)+,-(a0) ; DEPRECATED
;        movea.b     (a0)+,-(a0) ; DEPRECATED
;        movea.w     (a0)+,-(a0) ; DEPRECATED
;        movea.l     (a0)+,-(a0) ; DEPRECATED
;        movea       (a0)+,42(a0) ; DEPRECATED
;        movea.b     (a0)+,42(a0) ; DEPRECATED
;        movea.w     (a0)+,42(a0) ; DEPRECATED
;        movea.l     (a0)+,42(a0) ; DEPRECATED
;        movea       (a0)+,(-42,a0) ; DEPRECATED
;        movea.b     (a0)+,(-42,a0) ; DEPRECATED
;        movea.w     (a0)+,(-42,a0) ; DEPRECATED
;        movea.l     (a0)+,(-42,a0) ; DEPRECATED
;        movea       (a0)+,12(a0,d0) ; DEPRECATED
;        movea.b     (a0)+,12(a0,d0) ; DEPRECATED
;        movea.w     (a0)+,12(a0,d0) ; DEPRECATED
;        movea.l     (a0)+,12(a0,d0) ; DEPRECATED
;        movea       (a0)+,(12,a0,a0) ; DEPRECATED
;        movea.b     (a0)+,(12,a0,a0) ; DEPRECATED
;        movea.w     (a0)+,(12,a0,a0) ; DEPRECATED
;        movea.l     (a0)+,(12,a0,a0) ; DEPRECATED
;        movea       (a0)+,$4000 ; DEPRECATED
;        movea.b     (a0)+,$4000 ; DEPRECATED
;        movea.w     (a0)+,$4000 ; DEPRECATED
;        movea.l     (a0)+,$4000 ; DEPRECATED
;        movea       (a0)+,$4000.W ; DEPRECATED
;        movea.b     (a0)+,$4000.W ; DEPRECATED
;        movea.w     (a0)+,$4000.W ; DEPRECATED
;        movea.l     (a0)+,$4000.W ; DEPRECATED
;        movea       (a0)+,$4000.L ; DEPRECATED
;        movea.b     (a0)+,$4000.L ; DEPRECATED
;        movea.w     (a0)+,$4000.L ; DEPRECATED
;        movea.l     (a0)+,$4000.L ; DEPRECATED
;        movea       -(a0),d0 ; DEPRECATED
;        movea.b     -(a0),d0 ; DEPRECATED
;        movea.w     -(a0),d0 ; DEPRECATED
;        movea.l     -(a0),d0 ; DEPRECATED
;        movea       -(a0),(a0) ; DEPRECATED
;        movea.b     -(a0),(a0) ; DEPRECATED
;        movea.w     -(a0),(a0) ; DEPRECATED
;        movea.l     -(a0),(a0) ; DEPRECATED
;        movea       -(a0),(a0)+ ; DEPRECATED
;        movea.b     -(a0),(a0)+ ; DEPRECATED
;        movea.w     -(a0),(a0)+ ; DEPRECATED
;        movea.l     -(a0),(a0)+ ; DEPRECATED
;        movea       -(a0),-(a0) ; DEPRECATED
;        movea.b     -(a0),-(a0) ; DEPRECATED
;        movea.w     -(a0),-(a0) ; DEPRECATED
;        movea.l     -(a0),-(a0) ; DEPRECATED
;        movea       -(a0),42(a0) ; DEPRECATED
;        movea.b     -(a0),42(a0) ; DEPRECATED
;        movea.w     -(a0),42(a0) ; DEPRECATED
;        movea.l     -(a0),42(a0) ; DEPRECATED
;        movea       -(a0),(-42,a0) ; DEPRECATED
;        movea.b     -(a0),(-42,a0) ; DEPRECATED
;        movea.w     -(a0),(-42,a0) ; DEPRECATED
;        movea.l     -(a0),(-42,a0) ; DEPRECATED
;        movea       -(a0),12(a0,d0) ; DEPRECATED
;        movea.b     -(a0),12(a0,d0) ; DEPRECATED
;        movea.w     -(a0),12(a0,d0) ; DEPRECATED
;        movea.l     -(a0),12(a0,d0) ; DEPRECATED
;        movea       -(a0),(12,a0,a0) ; DEPRECATED
;        movea.b     -(a0),(12,a0,a0) ; DEPRECATED
;        movea.w     -(a0),(12,a0,a0) ; DEPRECATED
;        movea.l     -(a0),(12,a0,a0) ; DEPRECATED
;        movea       -(a0),$4000 ; DEPRECATED
;        movea.b     -(a0),$4000 ; DEPRECATED
;        movea.w     -(a0),$4000 ; DEPRECATED
;        movea.l     -(a0),$4000 ; DEPRECATED
;        movea       -(a0),$4000.W ; DEPRECATED
;        movea.b     -(a0),$4000.W ; DEPRECATED
;        movea.w     -(a0),$4000.W ; DEPRECATED
;        movea.l     -(a0),$4000.W ; DEPRECATED
;        movea       -(a0),$4000.L ; DEPRECATED
;        movea.b     -(a0),$4000.L ; DEPRECATED
;        movea.w     -(a0),$4000.L ; DEPRECATED
;        movea.l     -(a0),$4000.L ; DEPRECATED
;        movea       42(a0),d0 ; DEPRECATED
;        movea.b     42(a0),d0 ; DEPRECATED
;        movea.w     42(a0),d0 ; DEPRECATED
;        movea.l     42(a0),d0 ; DEPRECATED
;        movea       42(a0),(a0) ; DEPRECATED
;        movea.b     42(a0),(a0) ; DEPRECATED
;        movea.w     42(a0),(a0) ; DEPRECATED
;        movea.l     42(a0),(a0) ; DEPRECATED
;        movea       42(a0),(a0)+ ; DEPRECATED
;        movea.b     42(a0),(a0)+ ; DEPRECATED
;        movea.w     42(a0),(a0)+ ; DEPRECATED
;        movea.l     42(a0),(a0)+ ; DEPRECATED
;        movea       42(a0),-(a0) ; DEPRECATED
;        movea.b     42(a0),-(a0) ; DEPRECATED
;        movea.w     42(a0),-(a0) ; DEPRECATED
;        movea.l     42(a0),-(a0) ; DEPRECATED
;        movea       42(a0),42(a0) ; DEPRECATED
;        movea.b     42(a0),42(a0) ; DEPRECATED
;        movea.w     42(a0),42(a0) ; DEPRECATED
;        movea.l     42(a0),42(a0) ; DEPRECATED
;        movea       42(a0),(-42,a0) ; DEPRECATED
;        movea.b     42(a0),(-42,a0) ; DEPRECATED
;        movea.w     42(a0),(-42,a0) ; DEPRECATED
;        movea.l     42(a0),(-42,a0) ; DEPRECATED
;        movea       42(a0),12(a0,d0) ; DEPRECATED
;        movea.b     42(a0),12(a0,d0) ; DEPRECATED
;        movea.w     42(a0),12(a0,d0) ; DEPRECATED
;        movea.l     42(a0),12(a0,d0) ; DEPRECATED
;        movea       42(a0),(12,a0,a0) ; DEPRECATED
;        movea.b     42(a0),(12,a0,a0) ; DEPRECATED
;        movea.w     42(a0),(12,a0,a0) ; DEPRECATED
;        movea.l     42(a0),(12,a0,a0) ; DEPRECATED
;        movea       42(a0),$4000 ; DEPRECATED
;        movea.b     42(a0),$4000 ; DEPRECATED
;        movea.w     42(a0),$4000 ; DEPRECATED
;        movea.l     42(a0),$4000 ; DEPRECATED
;        movea       42(a0),$4000.W ; DEPRECATED
;        movea.b     42(a0),$4000.W ; DEPRECATED
;        movea.w     42(a0),$4000.W ; DEPRECATED
;        movea.l     42(a0),$4000.W ; DEPRECATED
;        movea       42(a0),$4000.L ; DEPRECATED
;        movea.b     42(a0),$4000.L ; DEPRECATED
;        movea.w     42(a0),$4000.L ; DEPRECATED
;        movea.l     42(a0),$4000.L ; DEPRECATED
;        movea       (-42,a0),d0 ; DEPRECATED
;        movea.b     (-42,a0),d0 ; DEPRECATED
;        movea.w     (-42,a0),d0 ; DEPRECATED
;        movea.l     (-42,a0),d0 ; DEPRECATED
;        movea       (-42,a0),(a0) ; DEPRECATED
;        movea.b     (-42,a0),(a0) ; DEPRECATED
;        movea.w     (-42,a0),(a0) ; DEPRECATED
;        movea.l     (-42,a0),(a0) ; DEPRECATED
;        movea       (-42,a0),(a0)+ ; DEPRECATED
;        movea.b     (-42,a0),(a0)+ ; DEPRECATED
;        movea.w     (-42,a0),(a0)+ ; DEPRECATED
;        movea.l     (-42,a0),(a0)+ ; DEPRECATED
;        movea       (-42,a0),-(a0) ; DEPRECATED
;        movea.b     (-42,a0),-(a0) ; DEPRECATED
;        movea.w     (-42,a0),-(a0) ; DEPRECATED
;        movea.l     (-42,a0),-(a0) ; DEPRECATED
;        movea       (-42,a0),42(a0) ; DEPRECATED
;        movea.b     (-42,a0),42(a0) ; DEPRECATED
;        movea.w     (-42,a0),42(a0) ; DEPRECATED
;        movea.l     (-42,a0),42(a0) ; DEPRECATED
;        movea       (-42,a0),(-42,a0) ; DEPRECATED
;        movea.b     (-42,a0),(-42,a0) ; DEPRECATED
;        movea.w     (-42,a0),(-42,a0) ; DEPRECATED
;        movea.l     (-42,a0),(-42,a0) ; DEPRECATED
;        movea       (-42,a0),12(a0,d0) ; DEPRECATED
;        movea.b     (-42,a0),12(a0,d0) ; DEPRECATED
;        movea.w     (-42,a0),12(a0,d0) ; DEPRECATED
;        movea.l     (-42,a0),12(a0,d0) ; DEPRECATED
;        movea       (-42,a0),(12,a0,a0) ; DEPRECATED
;        movea.b     (-42,a0),(12,a0,a0) ; DEPRECATED
;        movea.w     (-42,a0),(12,a0,a0) ; DEPRECATED
;        movea.l     (-42,a0),(12,a0,a0) ; DEPRECATED
;        movea       (-42,a0),$4000 ; DEPRECATED
;        movea.b     (-42,a0),$4000 ; DEPRECATED
;        movea.w     (-42,a0),$4000 ; DEPRECATED
;        movea.l     (-42,a0),$4000 ; DEPRECATED
;        movea       (-42,a0),$4000.W ; DEPRECATED
;        movea.b     (-42,a0),$4000.W ; DEPRECATED
;        movea.w     (-42,a0),$4000.W ; DEPRECATED
;        movea.l     (-42,a0),$4000.W ; DEPRECATED
;        movea       (-42,a0),$4000.L ; DEPRECATED
;        movea.b     (-42,a0),$4000.L ; DEPRECATED
;        movea.w     (-42,a0),$4000.L ; DEPRECATED
;        movea.l     (-42,a0),$4000.L ; DEPRECATED
;        movea       12(a0,d0),d0 ; DEPRECATED
;        movea.b     12(a0,d0),d0 ; DEPRECATED
;        movea.w     12(a0,d0),d0 ; DEPRECATED
;        movea.l     12(a0,d0),d0 ; DEPRECATED
;        movea       12(a0,d0),(a0) ; DEPRECATED
;        movea.b     12(a0,d0),(a0) ; DEPRECATED
;        movea.w     12(a0,d0),(a0) ; DEPRECATED
;        movea.l     12(a0,d0),(a0) ; DEPRECATED
;        movea       12(a0,d0),(a0)+ ; DEPRECATED
;        movea.b     12(a0,d0),(a0)+ ; DEPRECATED
;        movea.w     12(a0,d0),(a0)+ ; DEPRECATED
;        movea.l     12(a0,d0),(a0)+ ; DEPRECATED
;        movea       12(a0,d0),-(a0) ; DEPRECATED
;        movea.b     12(a0,d0),-(a0) ; DEPRECATED
;        movea.w     12(a0,d0),-(a0) ; DEPRECATED
;        movea.l     12(a0,d0),-(a0) ; DEPRECATED
;        movea       12(a0,d0),42(a0) ; DEPRECATED
;        movea.b     12(a0,d0),42(a0) ; DEPRECATED
;        movea.w     12(a0,d0),42(a0) ; DEPRECATED
;        movea.l     12(a0,d0),42(a0) ; DEPRECATED
;        movea       12(a0,d0),(-42,a0) ; DEPRECATED
;        movea.b     12(a0,d0),(-42,a0) ; DEPRECATED
;        movea.w     12(a0,d0),(-42,a0) ; DEPRECATED
;        movea.l     12(a0,d0),(-42,a0) ; DEPRECATED
;        movea       12(a0,d0),12(a0,d0) ; DEPRECATED
;        movea.b     12(a0,d0),12(a0,d0) ; DEPRECATED
;        movea.w     12(a0,d0),12(a0,d0) ; DEPRECATED
;        movea.l     12(a0,d0),12(a0,d0) ; DEPRECATED
;        movea       12(a0,d0),(12,a0,a0) ; DEPRECATED
;        movea.b     12(a0,d0),(12,a0,a0) ; DEPRECATED
;        movea.w     12(a0,d0),(12,a0,a0) ; DEPRECATED
;        movea.l     12(a0,d0),(12,a0,a0) ; DEPRECATED
;        movea       12(a0,d0),$4000 ; DEPRECATED
;        movea.b     12(a0,d0),$4000 ; DEPRECATED
;        movea.w     12(a0,d0),$4000 ; DEPRECATED
;        movea.l     12(a0,d0),$4000 ; DEPRECATED
;        movea       12(a0,d0),$4000.W ; DEPRECATED
;        movea.b     12(a0,d0),$4000.W ; DEPRECATED
;        movea.w     12(a0,d0),$4000.W ; DEPRECATED
;        movea.l     12(a0,d0),$4000.W ; DEPRECATED
;        movea       12(a0,d0),$4000.L ; DEPRECATED
;        movea.b     12(a0,d0),$4000.L ; DEPRECATED
;        movea.w     12(a0,d0),$4000.L ; DEPRECATED
;        movea.l     12(a0,d0),$4000.L ; DEPRECATED
;        movea       (12,a0,a0),d0 ; DEPRECATED
;        movea.b     (12,a0,a0),d0 ; DEPRECATED
;        movea.w     (12,a0,a0),d0 ; DEPRECATED
;        movea.l     (12,a0,a0),d0 ; DEPRECATED
;        movea       (12,a0,a0),(a0) ; DEPRECATED
;        movea.b     (12,a0,a0),(a0) ; DEPRECATED
;        movea.w     (12,a0,a0),(a0) ; DEPRECATED
;        movea.l     (12,a0,a0),(a0) ; DEPRECATED
;        movea       (12,a0,a0),(a0)+ ; DEPRECATED
;        movea.b     (12,a0,a0),(a0)+ ; DEPRECATED
;        movea.w     (12,a0,a0),(a0)+ ; DEPRECATED
;        movea.l     (12,a0,a0),(a0)+ ; DEPRECATED
;        movea       (12,a0,a0),-(a0) ; DEPRECATED
;        movea.b     (12,a0,a0),-(a0) ; DEPRECATED
;        movea.w     (12,a0,a0),-(a0) ; DEPRECATED
;        movea.l     (12,a0,a0),-(a0) ; DEPRECATED
;        movea       (12,a0,a0),42(a0) ; DEPRECATED
;        movea.b     (12,a0,a0),42(a0) ; DEPRECATED
;        movea.w     (12,a0,a0),42(a0) ; DEPRECATED
;        movea.l     (12,a0,a0),42(a0) ; DEPRECATED
;        movea       (12,a0,a0),(-42,a0) ; DEPRECATED
;        movea.b     (12,a0,a0),(-42,a0) ; DEPRECATED
;        movea.w     (12,a0,a0),(-42,a0) ; DEPRECATED
;        movea.l     (12,a0,a0),(-42,a0) ; DEPRECATED
;        movea       (12,a0,a0),12(a0,d0) ; DEPRECATED
;        movea.b     (12,a0,a0),12(a0,d0) ; DEPRECATED
;        movea.w     (12,a0,a0),12(a0,d0) ; DEPRECATED
;        movea.l     (12,a0,a0),12(a0,d0) ; DEPRECATED
;        movea       (12,a0,a0),(12,a0,a0) ; DEPRECATED
;        movea.b     (12,a0,a0),(12,a0,a0) ; DEPRECATED
;        movea.w     (12,a0,a0),(12,a0,a0) ; DEPRECATED
;        movea.l     (12,a0,a0),(12,a0,a0) ; DEPRECATED
;        movea       (12,a0,a0),$4000 ; DEPRECATED
;        movea.b     (12,a0,a0),$4000 ; DEPRECATED
;        movea.w     (12,a0,a0),$4000 ; DEPRECATED
;        movea.l     (12,a0,a0),$4000 ; DEPRECATED
;        movea       (12,a0,a0),$4000.W ; DEPRECATED
;        movea.b     (12,a0,a0),$4000.W ; DEPRECATED
;        movea.w     (12,a0,a0),$4000.W ; DEPRECATED
;        movea.l     (12,a0,a0),$4000.W ; DEPRECATED
;        movea       (12,a0,a0),$4000.L ; DEPRECATED
;        movea.b     (12,a0,a0),$4000.L ; DEPRECATED
;        movea.w     (12,a0,a0),$4000.L ; DEPRECATED
;        movea.l     (12,a0,a0),$4000.L ; DEPRECATED
;        movea       $4000,d0 ; DEPRECATED
;        movea.b     $4000,d0 ; DEPRECATED
;        movea.w     $4000,d0 ; DEPRECATED
;        movea.l     $4000,d0 ; DEPRECATED
;        movea       $4000,(a0) ; DEPRECATED
;        movea.b     $4000,(a0) ; DEPRECATED
;        movea.w     $4000,(a0) ; DEPRECATED
;        movea.l     $4000,(a0) ; DEPRECATED
;        movea       $4000,(a0)+ ; DEPRECATED
;        movea.b     $4000,(a0)+ ; DEPRECATED
;        movea.w     $4000,(a0)+ ; DEPRECATED
;        movea.l     $4000,(a0)+ ; DEPRECATED
;        movea       $4000,-(a0) ; DEPRECATED
;        movea.b     $4000,-(a0) ; DEPRECATED
;        movea.w     $4000,-(a0) ; DEPRECATED
;        movea.l     $4000,-(a0) ; DEPRECATED
;        movea       $4000,42(a0) ; DEPRECATED
;        movea.b     $4000,42(a0) ; DEPRECATED
;        movea.w     $4000,42(a0) ; DEPRECATED
;        movea.l     $4000,42(a0) ; DEPRECATED
;        movea       $4000,(-42,a0) ; DEPRECATED
;        movea.b     $4000,(-42,a0) ; DEPRECATED
;        movea.w     $4000,(-42,a0) ; DEPRECATED
;        movea.l     $4000,(-42,a0) ; DEPRECATED
;        movea       $4000,12(a0,d0) ; DEPRECATED
;        movea.b     $4000,12(a0,d0) ; DEPRECATED
;        movea.w     $4000,12(a0,d0) ; DEPRECATED
;        movea.l     $4000,12(a0,d0) ; DEPRECATED
;        movea       $4000,(12,a0,a0) ; DEPRECATED
;        movea.b     $4000,(12,a0,a0) ; DEPRECATED
;        movea.w     $4000,(12,a0,a0) ; DEPRECATED
;        movea.l     $4000,(12,a0,a0) ; DEPRECATED
;        movea       $4000,$4000 ; DEPRECATED
;        movea.b     $4000,$4000 ; DEPRECATED
;        movea.w     $4000,$4000 ; DEPRECATED
;        movea.l     $4000,$4000 ; DEPRECATED
;        movea       $4000,$4000.W ; DEPRECATED
;        movea.b     $4000,$4000.W ; DEPRECATED
;        movea.w     $4000,$4000.W ; DEPRECATED
;        movea.l     $4000,$4000.W ; DEPRECATED
;        movea       $4000,$4000.L ; DEPRECATED
;        movea.b     $4000,$4000.L ; DEPRECATED
;        movea.w     $4000,$4000.L ; DEPRECATED
;        movea.l     $4000,$4000.L ; DEPRECATED
;        movea       $4000.W,d0 ; DEPRECATED
;        movea.b     $4000.W,d0 ; DEPRECATED
;        movea.w     $4000.W,d0 ; DEPRECATED
;        movea.l     $4000.W,d0 ; DEPRECATED
;        movea       $4000.W,(a0) ; DEPRECATED
;        movea.b     $4000.W,(a0) ; DEPRECATED
;        movea.w     $4000.W,(a0) ; DEPRECATED
;        movea.l     $4000.W,(a0) ; DEPRECATED
;        movea       $4000.W,(a0)+ ; DEPRECATED
;        movea.b     $4000.W,(a0)+ ; DEPRECATED
;        movea.w     $4000.W,(a0)+ ; DEPRECATED
;        movea.l     $4000.W,(a0)+ ; DEPRECATED
;        movea       $4000.W,-(a0) ; DEPRECATED
;        movea.b     $4000.W,-(a0) ; DEPRECATED
;        movea.w     $4000.W,-(a0) ; DEPRECATED
;        movea.l     $4000.W,-(a0) ; DEPRECATED
;        movea       $4000.W,42(a0) ; DEPRECATED
;        movea.b     $4000.W,42(a0) ; DEPRECATED
;        movea.w     $4000.W,42(a0) ; DEPRECATED
;        movea.l     $4000.W,42(a0) ; DEPRECATED
;        movea       $4000.W,(-42,a0) ; DEPRECATED
;        movea.b     $4000.W,(-42,a0) ; DEPRECATED
;        movea.w     $4000.W,(-42,a0) ; DEPRECATED
;        movea.l     $4000.W,(-42,a0) ; DEPRECATED
;        movea       $4000.W,12(a0,d0) ; DEPRECATED
;        movea.b     $4000.W,12(a0,d0) ; DEPRECATED
;        movea.w     $4000.W,12(a0,d0) ; DEPRECATED
;        movea.l     $4000.W,12(a0,d0) ; DEPRECATED
;        movea       $4000.W,(12,a0,a0) ; DEPRECATED
;        movea.b     $4000.W,(12,a0,a0) ; DEPRECATED
;        movea.w     $4000.W,(12,a0,a0) ; DEPRECATED
;        movea.l     $4000.W,(12,a0,a0) ; DEPRECATED
;        movea       $4000.W,$4000 ; DEPRECATED
;        movea.b     $4000.W,$4000 ; DEPRECATED
;        movea.w     $4000.W,$4000 ; DEPRECATED
;        movea.l     $4000.W,$4000 ; DEPRECATED
;        movea       $4000.W,$4000.W ; DEPRECATED
;        movea.b     $4000.W,$4000.W ; DEPRECATED
;        movea.w     $4000.W,$4000.W ; DEPRECATED
;        movea.l     $4000.W,$4000.W ; DEPRECATED
;        movea       $4000.W,$4000.L ; DEPRECATED
;        movea.b     $4000.W,$4000.L ; DEPRECATED
;        movea.w     $4000.W,$4000.L ; DEPRECATED
;        movea.l     $4000.W,$4000.L ; DEPRECATED
;        movea       $4000.L,d0 ; DEPRECATED
;        movea.b     $4000.L,d0 ; DEPRECATED
;        movea.w     $4000.L,d0 ; DEPRECATED
;        movea.l     $4000.L,d0 ; DEPRECATED
;        movea       $4000.L,(a0) ; DEPRECATED
;        movea.b     $4000.L,(a0) ; DEPRECATED
;        movea.w     $4000.L,(a0) ; DEPRECATED
;        movea.l     $4000.L,(a0) ; DEPRECATED
;        movea       $4000.L,(a0)+ ; DEPRECATED
;        movea.b     $4000.L,(a0)+ ; DEPRECATED
;        movea.w     $4000.L,(a0)+ ; DEPRECATED
;        movea.l     $4000.L,(a0)+ ; DEPRECATED
;        movea       $4000.L,-(a0) ; DEPRECATED
;        movea.b     $4000.L,-(a0) ; DEPRECATED
;        movea.w     $4000.L,-(a0) ; DEPRECATED
;        movea.l     $4000.L,-(a0) ; DEPRECATED
;        movea       $4000.L,42(a0) ; DEPRECATED
;        movea.b     $4000.L,42(a0) ; DEPRECATED
;        movea.w     $4000.L,42(a0) ; DEPRECATED
;        movea.l     $4000.L,42(a0) ; DEPRECATED
;        movea       $4000.L,(-42,a0) ; DEPRECATED
;        movea.b     $4000.L,(-42,a0) ; DEPRECATED
;        movea.w     $4000.L,(-42,a0) ; DEPRECATED
;        movea.l     $4000.L,(-42,a0) ; DEPRECATED
;        movea       $4000.L,12(a0,d0) ; DEPRECATED
;        movea.b     $4000.L,12(a0,d0) ; DEPRECATED
;        movea.w     $4000.L,12(a0,d0) ; DEPRECATED
;        movea.l     $4000.L,12(a0,d0) ; DEPRECATED
;        movea       $4000.L,(12,a0,a0) ; DEPRECATED
;        movea.b     $4000.L,(12,a0,a0) ; DEPRECATED
;        movea.w     $4000.L,(12,a0,a0) ; DEPRECATED
;        movea.l     $4000.L,(12,a0,a0) ; DEPRECATED
;        movea       $4000.L,$4000 ; DEPRECATED
;        movea.b     $4000.L,$4000 ; DEPRECATED
;        movea.w     $4000.L,$4000 ; DEPRECATED
;        movea.l     $4000.L,$4000 ; DEPRECATED
;        movea       $4000.L,$4000.W ; DEPRECATED
;        movea.b     $4000.L,$4000.W ; DEPRECATED
;        movea.w     $4000.L,$4000.W ; DEPRECATED
;        movea.l     $4000.L,$4000.W ; DEPRECATED
;        movea       $4000.L,$4000.L ; DEPRECATED
;        movea.b     $4000.L,$4000.L ; DEPRECATED
;        movea.w     $4000.L,$4000.L ; DEPRECATED
;        movea.l     $4000.L,$4000.L ; DEPRECATED
;        movea       (PC),d0 ; DEPRECATED
;        movea.b     (PC),d0 ; DEPRECATED
;        movea.w     (PC),d0 ; DEPRECATED
;        movea.l     (PC),d0 ; DEPRECATED
;        movea       (PC),(a0) ; DEPRECATED
;        movea.b     (PC),(a0) ; DEPRECATED
;        movea.w     (PC),(a0) ; DEPRECATED
;        movea.l     (PC),(a0) ; DEPRECATED
;        movea       (PC),(a0)+ ; DEPRECATED
;        movea.b     (PC),(a0)+ ; DEPRECATED
;        movea.w     (PC),(a0)+ ; DEPRECATED
;        movea.l     (PC),(a0)+ ; DEPRECATED
;        movea       (PC),-(a0) ; DEPRECATED
;        movea.b     (PC),-(a0) ; DEPRECATED
;        movea.w     (PC),-(a0) ; DEPRECATED
;        movea.l     (PC),-(a0) ; DEPRECATED
;        movea       (PC),42(a0) ; DEPRECATED
;        movea.b     (PC),42(a0) ; DEPRECATED
;        movea.w     (PC),42(a0) ; DEPRECATED
;        movea.l     (PC),42(a0) ; DEPRECATED
;        movea       (PC),(-42,a0) ; DEPRECATED
;        movea.b     (PC),(-42,a0) ; DEPRECATED
;        movea.w     (PC),(-42,a0) ; DEPRECATED
;        movea.l     (PC),(-42,a0) ; DEPRECATED
;        movea       (PC),12(a0,d0) ; DEPRECATED
;        movea.b     (PC),12(a0,d0) ; DEPRECATED
;        movea.w     (PC),12(a0,d0) ; DEPRECATED
;        movea.l     (PC),12(a0,d0) ; DEPRECATED
;        movea       (PC),(12,a0,a0) ; DEPRECATED
;        movea.b     (PC),(12,a0,a0) ; DEPRECATED
;        movea.w     (PC),(12,a0,a0) ; DEPRECATED
;        movea.l     (PC),(12,a0,a0) ; DEPRECATED
;        movea       (PC),$4000 ; DEPRECATED
;        movea.b     (PC),$4000 ; DEPRECATED
;        movea.w     (PC),$4000 ; DEPRECATED
;        movea.l     (PC),$4000 ; DEPRECATED
;        movea       (PC),$4000.W ; DEPRECATED
;        movea.b     (PC),$4000.W ; DEPRECATED
;        movea.w     (PC),$4000.W ; DEPRECATED
;        movea.l     (PC),$4000.W ; DEPRECATED
;        movea       (PC),$4000.L ; DEPRECATED
;        movea.b     (PC),$4000.L ; DEPRECATED
;        movea.w     (PC),$4000.L ; DEPRECATED
;        movea.l     (PC),$4000.L ; DEPRECATED
;        movea       66(PC),d0 ; DEPRECATED
;        movea.b     66(PC),d0 ; DEPRECATED
;        movea.w     66(PC),d0 ; DEPRECATED
;        movea.l     66(PC),d0 ; DEPRECATED
;        movea       66(PC),(a0) ; DEPRECATED
;        movea.b     66(PC),(a0) ; DEPRECATED
;        movea.w     66(PC),(a0) ; DEPRECATED
;        movea.l     66(PC),(a0) ; DEPRECATED
;        movea       66(PC),(a0)+ ; DEPRECATED
;        movea.b     66(PC),(a0)+ ; DEPRECATED
;        movea.w     66(PC),(a0)+ ; DEPRECATED
;        movea.l     66(PC),(a0)+ ; DEPRECATED
;        movea       66(PC),-(a0) ; DEPRECATED
;        movea.b     66(PC),-(a0) ; DEPRECATED
;        movea.w     66(PC),-(a0) ; DEPRECATED
;        movea.l     66(PC),-(a0) ; DEPRECATED
;        movea       66(PC),42(a0) ; DEPRECATED
;        movea.b     66(PC),42(a0) ; DEPRECATED
;        movea.w     66(PC),42(a0) ; DEPRECATED
;        movea.l     66(PC),42(a0) ; DEPRECATED
;        movea       66(PC),(-42,a0) ; DEPRECATED
;        movea.b     66(PC),(-42,a0) ; DEPRECATED
;        movea.w     66(PC),(-42,a0) ; DEPRECATED
;        movea.l     66(PC),(-42,a0) ; DEPRECATED
;        movea       66(PC),12(a0,d0) ; DEPRECATED
;        movea.b     66(PC),12(a0,d0) ; DEPRECATED
;        movea.w     66(PC),12(a0,d0) ; DEPRECATED
;        movea.l     66(PC),12(a0,d0) ; DEPRECATED
;        movea       66(PC),(12,a0,a0) ; DEPRECATED
;        movea.b     66(PC),(12,a0,a0) ; DEPRECATED
;        movea.w     66(PC),(12,a0,a0) ; DEPRECATED
;        movea.l     66(PC),(12,a0,a0) ; DEPRECATED
;        movea       66(PC),$4000 ; DEPRECATED
;        movea.b     66(PC),$4000 ; DEPRECATED
;        movea.w     66(PC),$4000 ; DEPRECATED
;        movea.l     66(PC),$4000 ; DEPRECATED
;        movea       66(PC),$4000.W ; DEPRECATED
;        movea.b     66(PC),$4000.W ; DEPRECATED
;        movea.w     66(PC),$4000.W ; DEPRECATED
;        movea.l     66(PC),$4000.W ; DEPRECATED
;        movea       66(PC),$4000.L ; DEPRECATED
;        movea.b     66(PC),$4000.L ; DEPRECATED
;        movea.w     66(PC),$4000.L ; DEPRECATED
;        movea.l     66(PC),$4000.L ; DEPRECATED
;        movea       (-66,PC),d0 ; DEPRECATED
;        movea.b     (-66,PC),d0 ; DEPRECATED
;        movea.w     (-66,PC),d0 ; DEPRECATED
;        movea.l     (-66,PC),d0 ; DEPRECATED
;        movea       (-66,PC),(a0) ; DEPRECATED
;        movea.b     (-66,PC),(a0) ; DEPRECATED
;        movea.w     (-66,PC),(a0) ; DEPRECATED
;        movea.l     (-66,PC),(a0) ; DEPRECATED
;        movea       (-66,PC),(a0)+ ; DEPRECATED
;        movea.b     (-66,PC),(a0)+ ; DEPRECATED
;        movea.w     (-66,PC),(a0)+ ; DEPRECATED
;        movea.l     (-66,PC),(a0)+ ; DEPRECATED
;        movea       (-66,PC),-(a0) ; DEPRECATED
;        movea.b     (-66,PC),-(a0) ; DEPRECATED
;        movea.w     (-66,PC),-(a0) ; DEPRECATED
;        movea.l     (-66,PC),-(a0) ; DEPRECATED
;        movea       (-66,PC),42(a0) ; DEPRECATED
;        movea.b     (-66,PC),42(a0) ; DEPRECATED
;        movea.w     (-66,PC),42(a0) ; DEPRECATED
;        movea.l     (-66,PC),42(a0) ; DEPRECATED
;        movea       (-66,PC),(-42,a0) ; DEPRECATED
;        movea.b     (-66,PC),(-42,a0) ; DEPRECATED
;        movea.w     (-66,PC),(-42,a0) ; DEPRECATED
;        movea.l     (-66,PC),(-42,a0) ; DEPRECATED
;        movea       (-66,PC),12(a0,d0) ; DEPRECATED
;        movea.b     (-66,PC),12(a0,d0) ; DEPRECATED
;        movea.w     (-66,PC),12(a0,d0) ; DEPRECATED
;        movea.l     (-66,PC),12(a0,d0) ; DEPRECATED
;        movea       (-66,PC),(12,a0,a0) ; DEPRECATED
;        movea.b     (-66,PC),(12,a0,a0) ; DEPRECATED
;        movea.w     (-66,PC),(12,a0,a0) ; DEPRECATED
;        movea.l     (-66,PC),(12,a0,a0) ; DEPRECATED
;        movea       (-66,PC),$4000 ; DEPRECATED
;        movea.b     (-66,PC),$4000 ; DEPRECATED
;        movea.w     (-66,PC),$4000 ; DEPRECATED
;        movea.l     (-66,PC),$4000 ; DEPRECATED
;        movea       (-66,PC),$4000.W ; DEPRECATED
;        movea.b     (-66,PC),$4000.W ; DEPRECATED
;        movea.w     (-66,PC),$4000.W ; DEPRECATED
;        movea.l     (-66,PC),$4000.W ; DEPRECATED
;        movea       (-66,PC),$4000.L ; DEPRECATED
;        movea.b     (-66,PC),$4000.L ; DEPRECATED
;        movea.w     (-66,PC),$4000.L ; DEPRECATED
;        movea.l     (-66,PC),$4000.L ; DEPRECATED
;        movea       66(PC,d0),d0 ; DEPRECATED
;        movea.b     66(PC,d0),d0 ; DEPRECATED
;        movea.w     66(PC,d0),d0 ; DEPRECATED
;        movea.l     66(PC,d0),d0 ; DEPRECATED
;        movea       66(PC,d0),(a0) ; DEPRECATED
;        movea.b     66(PC,d0),(a0) ; DEPRECATED
;        movea.w     66(PC,d0),(a0) ; DEPRECATED
;        movea.l     66(PC,d0),(a0) ; DEPRECATED
;        movea       66(PC,d0),(a0)+ ; DEPRECATED
;        movea.b     66(PC,d0),(a0)+ ; DEPRECATED
;        movea.w     66(PC,d0),(a0)+ ; DEPRECATED
;        movea.l     66(PC,d0),(a0)+ ; DEPRECATED
;        movea       66(PC,d0),-(a0) ; DEPRECATED
;        movea.b     66(PC,d0),-(a0) ; DEPRECATED
;        movea.w     66(PC,d0),-(a0) ; DEPRECATED
;        movea.l     66(PC,d0),-(a0) ; DEPRECATED
;        movea       66(PC,d0),42(a0) ; DEPRECATED
;        movea.b     66(PC,d0),42(a0) ; DEPRECATED
;        movea.w     66(PC,d0),42(a0) ; DEPRECATED
;        movea.l     66(PC,d0),42(a0) ; DEPRECATED
;        movea       66(PC,d0),(-42,a0) ; DEPRECATED
;        movea.b     66(PC,d0),(-42,a0) ; DEPRECATED
;        movea.w     66(PC,d0),(-42,a0) ; DEPRECATED
;        movea.l     66(PC,d0),(-42,a0) ; DEPRECATED
;        movea       66(PC,d0),12(a0,d0) ; DEPRECATED
;        movea.b     66(PC,d0),12(a0,d0) ; DEPRECATED
;        movea.w     66(PC,d0),12(a0,d0) ; DEPRECATED
;        movea.l     66(PC,d0),12(a0,d0) ; DEPRECATED
;        movea       66(PC,d0),(12,a0,a0) ; DEPRECATED
;        movea.b     66(PC,d0),(12,a0,a0) ; DEPRECATED
;        movea.w     66(PC,d0),(12,a0,a0) ; DEPRECATED
;        movea.l     66(PC,d0),(12,a0,a0) ; DEPRECATED
;        movea       66(PC,d0),$4000 ; DEPRECATED
;        movea.b     66(PC,d0),$4000 ; DEPRECATED
;        movea.w     66(PC,d0),$4000 ; DEPRECATED
;        movea.l     66(PC,d0),$4000 ; DEPRECATED
;        movea       66(PC,d0),$4000.W ; DEPRECATED
;        movea.b     66(PC,d0),$4000.W ; DEPRECATED
;        movea.w     66(PC,d0),$4000.W ; DEPRECATED
;        movea.l     66(PC,d0),$4000.W ; DEPRECATED
;        movea       66(PC,d0),$4000.L ; DEPRECATED
;        movea.b     66(PC,d0),$4000.L ; DEPRECATED
;        movea.w     66(PC,d0),$4000.L ; DEPRECATED
;        movea.l     66(PC,d0),$4000.L ; DEPRECATED
;        movea       (66,PC,a0),d0 ; DEPRECATED
;        movea.b     (66,PC,a0),d0 ; DEPRECATED
;        movea.w     (66,PC,a0),d0 ; DEPRECATED
;        movea.l     (66,PC,a0),d0 ; DEPRECATED
;        movea       (66,PC,a0),(a0) ; DEPRECATED
;        movea.b     (66,PC,a0),(a0) ; DEPRECATED
;        movea.w     (66,PC,a0),(a0) ; DEPRECATED
;        movea.l     (66,PC,a0),(a0) ; DEPRECATED
;        movea       (66,PC,a0),(a0)+ ; DEPRECATED
;        movea.b     (66,PC,a0),(a0)+ ; DEPRECATED
;        movea.w     (66,PC,a0),(a0)+ ; DEPRECATED
;        movea.l     (66,PC,a0),(a0)+ ; DEPRECATED
;        movea       (66,PC,a0),-(a0) ; DEPRECATED
;        movea.b     (66,PC,a0),-(a0) ; DEPRECATED
;        movea.w     (66,PC,a0),-(a0) ; DEPRECATED
;        movea.l     (66,PC,a0),-(a0) ; DEPRECATED
;        movea       (66,PC,a0),42(a0) ; DEPRECATED
;        movea.b     (66,PC,a0),42(a0) ; DEPRECATED
;        movea.w     (66,PC,a0),42(a0) ; DEPRECATED
;        movea.l     (66,PC,a0),42(a0) ; DEPRECATED
;        movea       (66,PC,a0),(-42,a0) ; DEPRECATED
;        movea.b     (66,PC,a0),(-42,a0) ; DEPRECATED
;        movea.w     (66,PC,a0),(-42,a0) ; DEPRECATED
;        movea.l     (66,PC,a0),(-42,a0) ; DEPRECATED
;        movea       (66,PC,a0),12(a0,d0) ; DEPRECATED
;        movea.b     (66,PC,a0),12(a0,d0) ; DEPRECATED
;        movea.w     (66,PC,a0),12(a0,d0) ; DEPRECATED
;        movea.l     (66,PC,a0),12(a0,d0) ; DEPRECATED
;        movea       (66,PC,a0),(12,a0,a0) ; DEPRECATED
;        movea.b     (66,PC,a0),(12,a0,a0) ; DEPRECATED
;        movea.w     (66,PC,a0),(12,a0,a0) ; DEPRECATED
;        movea.l     (66,PC,a0),(12,a0,a0) ; DEPRECATED
;        movea       (66,PC,a0),$4000 ; DEPRECATED
;        movea.b     (66,PC,a0),$4000 ; DEPRECATED
;        movea.w     (66,PC,a0),$4000 ; DEPRECATED
;        movea.l     (66,PC,a0),$4000 ; DEPRECATED
;        movea       (66,PC,a0),$4000.W ; DEPRECATED
;        movea.b     (66,PC,a0),$4000.W ; DEPRECATED
;        movea.w     (66,PC,a0),$4000.W ; DEPRECATED
;        movea.l     (66,PC,a0),$4000.W ; DEPRECATED
;        movea       (66,PC,a0),$4000.L ; DEPRECATED
;        movea.b     (66,PC,a0),$4000.L ; DEPRECATED
;        movea.w     (66,PC,a0),$4000.L ; DEPRECATED
;        movea.l     (66,PC,a0),$4000.L ; DEPRECATED


********************************************************************************

* M68kMnemonic{movec, deprecated=false, src=CTRL_REGISTER, dst=DATA_OR_ADDRESS_REGISTER, [LONGWORD], [M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movec       DFC,d0
         movec.l     DFC,d0
         movec       DFC,a0
         movec.l     DFC,a0
         movec       SFC,d0
         movec.l     SFC,d0
         movec       SFC,a0
         movec.l     SFC,a0
         movec       VBR,d0
         movec.l     VBR,d0
         movec       VBR,a0
         movec.l     VBR,a0

* M68kMnemonic{movec, deprecated=false, src=DATA_OR_ADDRESS_REGISTER, dst=CTRL_REGISTER, [LONGWORD], [M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movec       d0,DFC
         movec.l     d0,DFC
         movec       d0,SFC
         movec.l     d0,SFC
         movec       d0,VBR
         movec.l     d0,VBR
         movec       a0,DFC
         movec.l     a0,DFC
         movec       a0,SFC
         movec.l     a0,SFC
         movec       a0,VBR
         movec.l     a0,VBR


********************************************************************************

* M68kMnemonic{movem, deprecated=false, src=DATA_OR_ADDRESS_REGISTER_LIST, dst=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movem       d0/a0-a2,-(a0)
         movem.w     d0/a0-a2,-(a0)
         movem.l     d0/a0-a2,-(a0)
         movem       #1,-(a0)
         movem.w     #1,-(a0)
         movem.l     #1,-(a0)

* M68kMnemonic{movem, deprecated=false, src=DATA_OR_ADDRESS_REGISTER_LIST, dst=ALTERABLE_CONTROL, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movem       d0/a0-a2,(a0)
         movem.w     d0/a0-a2,(a0)
         movem.l     d0/a0-a2,(a0)
         movem       d0/a0-a2,42(a0)
         movem.w     d0/a0-a2,42(a0)
         movem.l     d0/a0-a2,42(a0)
         movem       d0/a0-a2,(-42,a0)
         movem.w     d0/a0-a2,(-42,a0)
         movem.l     d0/a0-a2,(-42,a0)
         movem       d0/a0-a2,12(a0,d0)
         movem.w     d0/a0-a2,12(a0,d0)
         movem.l     d0/a0-a2,12(a0,d0)
         movem       d0/a0-a2,(12,a0,a0)
         movem.w     d0/a0-a2,(12,a0,a0)
         movem.l     d0/a0-a2,(12,a0,a0)
         movem       d0/a0-a2,$4000
         movem.w     d0/a0-a2,$4000
         movem.l     d0/a0-a2,$4000
         movem       d0/a0-a2,$4000.W
         movem.w     d0/a0-a2,$4000.W
         movem.l     d0/a0-a2,$4000.W
         movem       d0/a0-a2,$4000.L
         movem.w     d0/a0-a2,$4000.L
         movem.l     d0/a0-a2,$4000.L
         movem       #1,(a0)
         movem.w     #1,(a0)
         movem.l     #1,(a0)
         movem       #1,42(a0)
         movem.w     #1,42(a0)
         movem.l     #1,42(a0)
         movem       #1,(-42,a0)
         movem.w     #1,(-42,a0)
         movem.l     #1,(-42,a0)
         movem       #1,12(a0,d0)
         movem.w     #1,12(a0,d0)
         movem.l     #1,12(a0,d0)
         movem       #1,(12,a0,a0)
         movem.w     #1,(12,a0,a0)
         movem.l     #1,(12,a0,a0)
         movem       #1,$4000
         movem.w     #1,$4000
         movem.l     #1,$4000
         movem       #1,$4000.W
         movem.w     #1,$4000.W
         movem.l     #1,$4000.W
         movem       #1,$4000.L
         movem.w     #1,$4000.L
         movem.l     #1,$4000.L

* M68kMnemonic{movem, deprecated=false, src=RESTORE_OPERANDS, dst=DATA_OR_ADDRESS_REGISTER_LIST, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movem       (a0),d0/a0-a2
         movem.w     (a0),d0/a0-a2
         movem.l     (a0),d0/a0-a2
         movem       (a0),#1
         movem.w     (a0),#1
         movem.l     (a0),#1
         movem       (a0)+,d0/a0-a2
         movem.w     (a0)+,d0/a0-a2
         movem.l     (a0)+,d0/a0-a2
         movem       (a0)+,#1
         movem.w     (a0)+,#1
         movem.l     (a0)+,#1
         movem       42(a0),d0/a0-a2
         movem.w     42(a0),d0/a0-a2
         movem.l     42(a0),d0/a0-a2
         movem       42(a0),#1
         movem.w     42(a0),#1
         movem.l     42(a0),#1
         movem       (-42,a0),d0/a0-a2
         movem.w     (-42,a0),d0/a0-a2
         movem.l     (-42,a0),d0/a0-a2
         movem       (-42,a0),#1
         movem.w     (-42,a0),#1
         movem.l     (-42,a0),#1
         movem       12(a0,d0),d0/a0-a2
         movem.w     12(a0,d0),d0/a0-a2
         movem.l     12(a0,d0),d0/a0-a2
         movem       12(a0,d0),#1
         movem.w     12(a0,d0),#1
         movem.l     12(a0,d0),#1
         movem       (12,a0,a0),d0/a0-a2
         movem.w     (12,a0,a0),d0/a0-a2
         movem.l     (12,a0,a0),d0/a0-a2
         movem       (12,a0,a0),#1
         movem.w     (12,a0,a0),#1
         movem.l     (12,a0,a0),#1
         movem       $4000,d0/a0-a2
         movem.w     $4000,d0/a0-a2
         movem.l     $4000,d0/a0-a2
         movem       $4000,#1
         movem.w     $4000,#1
         movem.l     $4000,#1
         movem       $4000.W,d0/a0-a2
         movem.w     $4000.W,d0/a0-a2
         movem.l     $4000.W,d0/a0-a2
         movem       $4000.W,#1
         movem.w     $4000.W,#1
         movem.l     $4000.W,#1
         movem       $4000.L,d0/a0-a2
         movem.w     $4000.L,d0/a0-a2
         movem.l     $4000.L,d0/a0-a2
         movem       $4000.L,#1
         movem.w     $4000.L,#1
         movem.l     $4000.L,#1
         movem       (PC),d0/a0-a2
         movem.w     (PC),d0/a0-a2
         movem.l     (PC),d0/a0-a2
         movem       (PC),#1
         movem.w     (PC),#1
         movem.l     (PC),#1
         movem       66(PC),d0/a0-a2
         movem.w     66(PC),d0/a0-a2
         movem.l     66(PC),d0/a0-a2
         movem       66(PC),#1
         movem.w     66(PC),#1
         movem.l     66(PC),#1
         movem       (-66,PC),d0/a0-a2
         movem.w     (-66,PC),d0/a0-a2
         movem.l     (-66,PC),d0/a0-a2
         movem       (-66,PC),#1
         movem.w     (-66,PC),#1
         movem.l     (-66,PC),#1
         movem       66(PC,d0),d0/a0-a2
         movem.w     66(PC,d0),d0/a0-a2
         movem.l     66(PC,d0),d0/a0-a2
         movem       66(PC,d0),#1
         movem.w     66(PC,d0),#1
         movem.l     66(PC,d0),#1
         movem       (66,PC,a0),d0/a0-a2
         movem.w     (66,PC,a0),d0/a0-a2
         movem.l     (66,PC,a0),d0/a0-a2
         movem       (66,PC,a0),#1
         movem.w     (66,PC,a0),#1
         movem.l     (66,PC,a0),#1

* M68kMnemonic{movem, deprecated=false, src=IMMEDIATE_REGISTER_LIST_VALUE, dst=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movem       #42,-(a0)
         movem.w     #42,-(a0)
         movem.l     #42,-(a0)

* M68kMnemonic{movem, deprecated=false, src=IMMEDIATE_REGISTER_LIST_VALUE, dst=ALTERABLE_CONTROL, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movem       #42,(a0)
         movem.w     #42,(a0)
         movem.l     #42,(a0)
         movem       #42,42(a0)
         movem.w     #42,42(a0)
         movem.l     #42,42(a0)
         movem       #42,(-42,a0)
         movem.w     #42,(-42,a0)
         movem.l     #42,(-42,a0)
         movem       #42,12(a0,d0)
         movem.w     #42,12(a0,d0)
         movem.l     #42,12(a0,d0)
         movem       #42,(12,a0,a0)
         movem.w     #42,(12,a0,a0)
         movem.l     #42,(12,a0,a0)
         movem       #42,$4000
         movem.w     #42,$4000
         movem.l     #42,$4000
         movem       #42,$4000.W
         movem.w     #42,$4000.W
         movem.l     #42,$4000.W
         movem       #42,$4000.L
         movem.w     #42,$4000.L
         movem.l     #42,$4000.L

* M68kMnemonic{movem, deprecated=false, src=RESTORE_OPERANDS, dst=IMMEDIATE_REGISTER_LIST_VALUE, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movem       (a0),#42
         movem.w     (a0),#42
         movem.l     (a0),#42
         movem       (a0)+,#42
         movem.w     (a0)+,#42
         movem.l     (a0)+,#42
         movem       42(a0),#42
         movem.w     42(a0),#42
         movem.l     42(a0),#42
         movem       (-42,a0),#42
         movem.w     (-42,a0),#42
         movem.l     (-42,a0),#42
         movem       12(a0,d0),#42
         movem.w     12(a0,d0),#42
         movem.l     12(a0,d0),#42
         movem       (12,a0,a0),#42
         movem.w     (12,a0,a0),#42
         movem.l     (12,a0,a0),#42
         movem       $4000,#42
         movem.w     $4000,#42
         movem.l     $4000,#42
         movem       $4000.W,#42
         movem.w     $4000.W,#42
         movem.l     $4000.W,#42
         movem       $4000.L,#42
         movem.w     $4000.L,#42
         movem.l     $4000.L,#42
         movem       (PC),#42
         movem.w     (PC),#42
         movem.l     (PC),#42
         movem       66(PC),#42
         movem.w     66(PC),#42
         movem.l     66(PC),#42
         movem       (-66,PC),#42
         movem.w     (-66,PC),#42
         movem.l     (-66,PC),#42
         movem       66(PC,d0),#42
         movem.w     66(PC,d0),#42
         movem.l     66(PC,d0),#42
         movem       (66,PC,a0),#42
         movem.w     (66,PC,a0),#42
         movem.l     (66,PC,a0),#42


********************************************************************************

* M68kMnemonic{movep, deprecated=false, src=ADDRESS_REGISTER_DISPLACEMENT, dst=DATA_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movep       42(a0),d0
         movep.w     42(a0),d0
         movep.l     42(a0),d0
         movep       (-42,a0),d0
         movep.w     (-42,a0),d0
         movep.l     (-42,a0),d0

* M68kMnemonic{movep, deprecated=false, src=DATA_REGISTER, dst=ADDRESS_REGISTER_DISPLACEMENT, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         movep       d0,42(a0)
         movep.w     d0,42(a0)
         movep.l     d0,42(a0)
         movep       d0,(-42,a0)
         movep.w     d0,(-42,a0)
         movep.l     d0,(-42,a0)


********************************************************************************

* M68kMnemonic{moveq, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         moveq       #1,d0
         moveq.l     #1,d0


********************************************************************************

* M68kMnemonic{moves, deprecated=false, src=ALTERABLE_MEMORY, dst=DATA_OR_ADDRESS_REGISTER, [BYTE, WORD, LONGWORD], [M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         moves       (a0),d0
         moves.b     (a0),d0
         moves.w     (a0),d0
         moves.l     (a0),d0
         moves       (a0),a0
         moves.b     (a0),a0
         moves.w     (a0),a0
         moves.l     (a0),a0
         moves       (a0)+,d0
         moves.b     (a0)+,d0
         moves.w     (a0)+,d0
         moves.l     (a0)+,d0
         moves       (a0)+,a0
         moves.b     (a0)+,a0
         moves.w     (a0)+,a0
         moves.l     (a0)+,a0
         moves       -(a0),d0
         moves.b     -(a0),d0
         moves.w     -(a0),d0
         moves.l     -(a0),d0
         moves       -(a0),a0
         moves.b     -(a0),a0
         moves.w     -(a0),a0
         moves.l     -(a0),a0
         moves       42(a0),d0
         moves.b     42(a0),d0
         moves.w     42(a0),d0
         moves.l     42(a0),d0
         moves       42(a0),a0
         moves.b     42(a0),a0
         moves.w     42(a0),a0
         moves.l     42(a0),a0
         moves       (-42,a0),d0
         moves.b     (-42,a0),d0
         moves.w     (-42,a0),d0
         moves.l     (-42,a0),d0
         moves       (-42,a0),a0
         moves.b     (-42,a0),a0
         moves.w     (-42,a0),a0
         moves.l     (-42,a0),a0
         moves       12(a0,d0),d0
         moves.b     12(a0,d0),d0
         moves.w     12(a0,d0),d0
         moves.l     12(a0,d0),d0
         moves       12(a0,d0),a0
         moves.b     12(a0,d0),a0
         moves.w     12(a0,d0),a0
         moves.l     12(a0,d0),a0
         moves       (12,a0,a0),d0
         moves.b     (12,a0,a0),d0
         moves.w     (12,a0,a0),d0
         moves.l     (12,a0,a0),d0
         moves       (12,a0,a0),a0
         moves.b     (12,a0,a0),a0
         moves.w     (12,a0,a0),a0
         moves.l     (12,a0,a0),a0
         moves       $4000,d0
         moves.b     $4000,d0
         moves.w     $4000,d0
         moves.l     $4000,d0
         moves       $4000,a0
         moves.b     $4000,a0
         moves.w     $4000,a0
         moves.l     $4000,a0
         moves       $4000.W,d0
         moves.b     $4000.W,d0
         moves.w     $4000.W,d0
         moves.l     $4000.W,d0
         moves       $4000.W,a0
         moves.b     $4000.W,a0
         moves.w     $4000.W,a0
         moves.l     $4000.W,a0
         moves       $4000.L,d0
         moves.b     $4000.L,d0
         moves.w     $4000.L,d0
         moves.l     $4000.L,d0
         moves       $4000.L,a0
         moves.b     $4000.L,a0
         moves.w     $4000.L,a0
         moves.l     $4000.L,a0

* M68kMnemonic{moves, deprecated=false, src=DATA_OR_ADDRESS_REGISTER, dst=ALTERABLE_MEMORY, [BYTE, WORD, LONGWORD], [M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         moves       d0,(a0)
         moves.b     d0,(a0)
         moves.w     d0,(a0)
         moves.l     d0,(a0)
         moves       d0,(a0)+
         moves.b     d0,(a0)+
         moves.w     d0,(a0)+
         moves.l     d0,(a0)+
         moves       d0,-(a0)
         moves.b     d0,-(a0)
         moves.w     d0,-(a0)
         moves.l     d0,-(a0)
         moves       d0,42(a0)
         moves.b     d0,42(a0)
         moves.w     d0,42(a0)
         moves.l     d0,42(a0)
         moves       d0,(-42,a0)
         moves.b     d0,(-42,a0)
         moves.w     d0,(-42,a0)
         moves.l     d0,(-42,a0)
         moves       d0,12(a0,d0)
         moves.b     d0,12(a0,d0)
         moves.w     d0,12(a0,d0)
         moves.l     d0,12(a0,d0)
         moves       d0,(12,a0,a0)
         moves.b     d0,(12,a0,a0)
         moves.w     d0,(12,a0,a0)
         moves.l     d0,(12,a0,a0)
         moves       d0,$4000
         moves.b     d0,$4000
         moves.w     d0,$4000
         moves.l     d0,$4000
         moves       d0,$4000.W
         moves.b     d0,$4000.W
         moves.w     d0,$4000.W
         moves.l     d0,$4000.W
         moves       d0,$4000.L
         moves.b     d0,$4000.L
         moves.w     d0,$4000.L
         moves.l     d0,$4000.L
         moves       a0,(a0)
         moves.b     a0,(a0)
         moves.w     a0,(a0)
         moves.l     a0,(a0)
         moves       a0,(a0)+
         moves.b     a0,(a0)+
         moves.w     a0,(a0)+
         moves.l     a0,(a0)+
         moves       a0,-(a0)
         moves.b     a0,-(a0)
         moves.w     a0,-(a0)
         moves.l     a0,-(a0)
         moves       a0,42(a0)
         moves.b     a0,42(a0)
         moves.w     a0,42(a0)
         moves.l     a0,42(a0)
         moves       a0,(-42,a0)
         moves.b     a0,(-42,a0)
         moves.w     a0,(-42,a0)
         moves.l     a0,(-42,a0)
         moves       a0,12(a0,d0)
         moves.b     a0,12(a0,d0)
         moves.w     a0,12(a0,d0)
         moves.l     a0,12(a0,d0)
         moves       a0,(12,a0,a0)
         moves.b     a0,(12,a0,a0)
         moves.w     a0,(12,a0,a0)
         moves.l     a0,(12,a0,a0)
         moves       a0,$4000
         moves.b     a0,$4000
         moves.w     a0,$4000
         moves.l     a0,$4000
         moves       a0,$4000.W
         moves.b     a0,$4000.W
         moves.w     a0,$4000.W
         moves.l     a0,$4000.W
         moves       a0,$4000.L
         moves.b     a0,$4000.L
         moves.w     a0,$4000.L
         moves.l     a0,$4000.L


********************************************************************************

* M68kMnemonic{muls, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         muls        d0,d0
         muls.w      d0,d0
         muls        (a0),d0
         muls.w      (a0),d0
         muls        (a0)+,d0
         muls.w      (a0)+,d0
         muls        -(a0),d0
         muls.w      -(a0),d0
         muls        42(a0),d0
         muls.w      42(a0),d0
         muls        (-42,a0),d0
         muls.w      (-42,a0),d0
         muls        12(a0,d0),d0
         muls.w      12(a0,d0),d0
         muls        (12,a0,a0),d0
         muls.w      (12,a0,a0),d0
         muls        $4000,d0
         muls.w      $4000,d0
         muls        $4000.W,d0
         muls.w      $4000.W,d0
         muls        $4000.L,d0
         muls.w      $4000.L,d0

* M68kMnemonic{muls, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         muls        d0,d0
         muls.l      d0,d0
         muls        (a0),d0
         muls.l      (a0),d0
         muls        (a0)+,d0
         muls.l      (a0)+,d0
         muls        -(a0),d0
         muls.l      -(a0),d0
         muls        42(a0),d0
         muls.l      42(a0),d0
         muls        (-42,a0),d0
         muls.l      (-42,a0),d0
         muls        12(a0,d0),d0
         muls.l      12(a0,d0),d0
         muls        (12,a0,a0),d0
         muls.l      (12,a0,a0),d0
         muls        $4000,d0
         muls.l      $4000,d0
         muls        $4000.W,d0
         muls.l      $4000.W,d0
         muls        $4000.L,d0
         muls.l      $4000.L,d0

* M68kMnemonic{muls, deprecated=false, src=DATA, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         muls        d0,d0
         muls.w      d0,d0
         muls        #42,d0
         muls.w      #42,d0
         muls        (a0),d0
         muls.w      (a0),d0
         muls        (a0)+,d0
         muls.w      (a0)+,d0
         muls        -(a0),d0
         muls.w      -(a0),d0
         muls        42(a0),d0
         muls.w      42(a0),d0
         muls        (-42,a0),d0
         muls.w      (-42,a0),d0
         muls        12(a0,d0),d0
         muls.w      12(a0,d0),d0
         muls        (12,a0,a0),d0
         muls.w      (12,a0,a0),d0
         muls        $4000,d0
         muls.w      $4000,d0
         muls        $4000.W,d0
         muls.w      $4000.W,d0
         muls        $4000.L,d0
         muls.w      $4000.L,d0
         muls        (PC),d0
         muls.w      (PC),d0
         muls        66(PC),d0
         muls.w      66(PC),d0
         muls        (-66,PC),d0
         muls.w      (-66,PC),d0
         muls        66(PC,d0),d0
         muls.w      66(PC,d0),d0
         muls        (66,PC,a0),d0
         muls.w      (66,PC,a0),d0

* M68kMnemonic{muls, deprecated=false, src=DATA, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         muls        d0,d0
         muls.l      d0,d0
         muls        #42,d0
         muls.l      #42,d0
         muls        (a0),d0
         muls.l      (a0),d0
         muls        (a0)+,d0
         muls.l      (a0)+,d0
         muls        -(a0),d0
         muls.l      -(a0),d0
         muls        42(a0),d0
         muls.l      42(a0),d0
         muls        (-42,a0),d0
         muls.l      (-42,a0),d0
         muls        12(a0,d0),d0
         muls.l      12(a0,d0),d0
         muls        (12,a0,a0),d0
         muls.l      (12,a0,a0),d0
         muls        $4000,d0
         muls.l      $4000,d0
         muls        $4000.W,d0
         muls.l      $4000.W,d0
         muls        $4000.L,d0
         muls.l      $4000.L,d0
         muls        (PC),d0
         muls.l      (PC),d0
         muls        66(PC),d0
         muls.l      66(PC),d0
         muls        (-66,PC),d0
         muls.l      (-66,PC),d0
         muls        66(PC,d0),d0
         muls.l      66(PC,d0),d0
         muls        (66,PC,a0),d0
         muls.l      (66,PC,a0),d0


********************************************************************************

* M68kMnemonic{mulu, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         mulu        d0,d0
         mulu.w      d0,d0
         mulu        (a0),d0
         mulu.w      (a0),d0
         mulu        (a0)+,d0
         mulu.w      (a0)+,d0
         mulu        -(a0),d0
         mulu.w      -(a0),d0
         mulu        42(a0),d0
         mulu.w      42(a0),d0
         mulu        (-42,a0),d0
         mulu.w      (-42,a0),d0
         mulu        12(a0,d0),d0
         mulu.w      12(a0,d0),d0
         mulu        (12,a0,a0),d0
         mulu.w      (12,a0,a0),d0
         mulu        $4000,d0
         mulu.w      $4000,d0
         mulu        $4000.W,d0
         mulu.w      $4000.W,d0
         mulu        $4000.L,d0
         mulu.w      $4000.L,d0

* M68kMnemonic{mulu, deprecated=false, src=ALTERABLE_DATA_CF, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         mulu        d0,d0
         mulu.l      d0,d0
         mulu        (a0),d0
         mulu.l      (a0),d0
         mulu        (a0)+,d0
         mulu.l      (a0)+,d0
         mulu        -(a0),d0
         mulu.l      -(a0),d0
         mulu        42(a0),d0
         mulu.l      42(a0),d0
         mulu        (-42,a0),d0
         mulu.l      (-42,a0),d0
         mulu        12(a0,d0),d0
         mulu.l      12(a0,d0),d0
         mulu        (12,a0,a0),d0
         mulu.l      (12,a0,a0),d0
         mulu        $4000,d0
         mulu.l      $4000,d0
         mulu        $4000.W,d0
         mulu.l      $4000.W,d0
         mulu        $4000.L,d0
         mulu.l      $4000.L,d0

* M68kMnemonic{mulu, deprecated=false, src=DATA, dst=DATA_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         mulu        d0,d0
         mulu.w      d0,d0
         mulu        #42,d0
         mulu.w      #42,d0
         mulu        (a0),d0
         mulu.w      (a0),d0
         mulu        (a0)+,d0
         mulu.w      (a0)+,d0
         mulu        -(a0),d0
         mulu.w      -(a0),d0
         mulu        42(a0),d0
         mulu.w      42(a0),d0
         mulu        (-42,a0),d0
         mulu.w      (-42,a0),d0
         mulu        12(a0,d0),d0
         mulu.w      12(a0,d0),d0
         mulu        (12,a0,a0),d0
         mulu.w      (12,a0,a0),d0
         mulu        $4000,d0
         mulu.w      $4000,d0
         mulu        $4000.W,d0
         mulu.w      $4000.W,d0
         mulu        $4000.L,d0
         mulu.w      $4000.L,d0
         mulu        (PC),d0
         mulu.w      (PC),d0
         mulu        66(PC),d0
         mulu.w      66(PC),d0
         mulu        (-66,PC),d0
         mulu.w      (-66,PC),d0
         mulu        66(PC,d0),d0
         mulu.w      66(PC,d0),d0
         mulu        (66,PC,a0),d0
         mulu.w      (66,PC,a0),d0

* M68kMnemonic{mulu, deprecated=false, src=DATA, dst=DATA_REGISTER, [LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         mulu        d0,d0
         mulu.l      d0,d0
         mulu        #42,d0
         mulu.l      #42,d0
         mulu        (a0),d0
         mulu.l      (a0),d0
         mulu        (a0)+,d0
         mulu.l      (a0)+,d0
         mulu        -(a0),d0
         mulu.l      -(a0),d0
         mulu        42(a0),d0
         mulu.l      42(a0),d0
         mulu        (-42,a0),d0
         mulu.l      (-42,a0),d0
         mulu        12(a0,d0),d0
         mulu.l      12(a0,d0),d0
         mulu        (12,a0,a0),d0
         mulu.l      (12,a0,a0),d0
         mulu        $4000,d0
         mulu.l      $4000,d0
         mulu        $4000.W,d0
         mulu.l      $4000.W,d0
         mulu        $4000.L,d0
         mulu.l      $4000.L,d0
         mulu        (PC),d0
         mulu.l      (PC),d0
         mulu        66(PC),d0
         mulu.l      66(PC),d0
         mulu        (-66,PC),d0
         mulu.l      (-66,PC),d0
         mulu        66(PC,d0),d0
         mulu.l      66(PC,d0),d0
         mulu        (66,PC,a0),d0
         mulu.l      (66,PC,a0),d0


********************************************************************************

* M68kMnemonic{nbcd, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         nbcd        d0
         nbcd.b      d0
         nbcd        (a0)
         nbcd.b      (a0)
         nbcd        (a0)+
         nbcd.b      (a0)+
         nbcd        -(a0)
         nbcd.b      -(a0)
         nbcd        42(a0)
         nbcd.b      42(a0)
         nbcd        (-42,a0)
         nbcd.b      (-42,a0)
         nbcd        12(a0,d0)
         nbcd.b      12(a0,d0)
         nbcd        (12,a0,a0)
         nbcd.b      (12,a0,a0)
         nbcd        $4000
         nbcd.b      $4000
         nbcd        $4000.W
         nbcd.b      $4000.W
         nbcd        $4000.L
         nbcd.b      $4000.L


********************************************************************************

* M68kMnemonic{neg, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         neg         d0
         neg.b       d0
         neg.w       d0
         neg.l       d0

* M68kMnemonic{neg, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         neg         d0
         neg.b       d0
         neg.w       d0
         neg.l       d0
         neg         (a0)
         neg.b       (a0)
         neg.w       (a0)
         neg.l       (a0)
         neg         (a0)+
         neg.b       (a0)+
         neg.w       (a0)+
         neg.l       (a0)+
         neg         -(a0)
         neg.b       -(a0)
         neg.w       -(a0)
         neg.l       -(a0)
         neg         42(a0)
         neg.b       42(a0)
         neg.w       42(a0)
         neg.l       42(a0)
         neg         (-42,a0)
         neg.b       (-42,a0)
         neg.w       (-42,a0)
         neg.l       (-42,a0)
         neg         12(a0,d0)
         neg.b       12(a0,d0)
         neg.w       12(a0,d0)
         neg.l       12(a0,d0)
         neg         (12,a0,a0)
         neg.b       (12,a0,a0)
         neg.w       (12,a0,a0)
         neg.l       (12,a0,a0)
         neg         $4000
         neg.b       $4000
         neg.w       $4000
         neg.l       $4000
         neg         $4000.W
         neg.b       $4000.W
         neg.w       $4000.W
         neg.l       $4000.W
         neg         $4000.L
         neg.b       $4000.L
         neg.w       $4000.L
         neg.l       $4000.L


********************************************************************************

* M68kMnemonic{negx, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         negx        d0
         negx.b      d0
         negx.w      d0
         negx.l      d0

* M68kMnemonic{negx, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         negx        d0
         negx.b      d0
         negx.w      d0
         negx.l      d0
         negx        (a0)
         negx.b      (a0)
         negx.w      (a0)
         negx.l      (a0)
         negx        (a0)+
         negx.b      (a0)+
         negx.w      (a0)+
         negx.l      (a0)+
         negx        -(a0)
         negx.b      -(a0)
         negx.w      -(a0)
         negx.l      -(a0)
         negx        42(a0)
         negx.b      42(a0)
         negx.w      42(a0)
         negx.l      42(a0)
         negx        (-42,a0)
         negx.b      (-42,a0)
         negx.w      (-42,a0)
         negx.l      (-42,a0)
         negx        12(a0,d0)
         negx.b      12(a0,d0)
         negx.w      12(a0,d0)
         negx.l      12(a0,d0)
         negx        (12,a0,a0)
         negx.b      (12,a0,a0)
         negx.w      (12,a0,a0)
         negx.l      (12,a0,a0)
         negx        $4000
         negx.b      $4000
         negx.w      $4000
         negx.l      $4000
         negx        $4000.W
         negx.b      $4000.W
         negx.w      $4000.W
         negx.l      $4000.W
         negx        $4000.L
         negx.b      $4000.L
         negx.w      $4000.L
         negx.l      $4000.L


********************************************************************************

* M68kMnemonic{nop, deprecated=false, src=NONE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         nop


********************************************************************************

* M68kMnemonic{not, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         not         d0
         not.b       d0
         not.w       d0
         not.l       d0

* M68kMnemonic{not, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         not         d0
         not.b       d0
         not.w       d0
         not.l       d0
         not         (a0)
         not.b       (a0)
         not.w       (a0)
         not.l       (a0)
         not         (a0)+
         not.b       (a0)+
         not.w       (a0)+
         not.l       (a0)+
         not         -(a0)
         not.b       -(a0)
         not.w       -(a0)
         not.l       -(a0)
         not         42(a0)
         not.b       42(a0)
         not.w       42(a0)
         not.l       42(a0)
         not         (-42,a0)
         not.b       (-42,a0)
         not.w       (-42,a0)
         not.l       (-42,a0)
         not         12(a0,d0)
         not.b       12(a0,d0)
         not.w       12(a0,d0)
         not.l       12(a0,d0)
         not         (12,a0,a0)
         not.b       (12,a0,a0)
         not.w       (12,a0,a0)
         not.l       (12,a0,a0)
         not         $4000
         not.b       $4000
         not.w       $4000
         not.l       $4000
         not         $4000.W
         not.b       $4000.W
         not.w       $4000.W
         not.l       $4000.W
         not         $4000.L
         not.b       $4000.L
         not.w       $4000.L
         not.l       $4000.L


********************************************************************************

* M68kMnemonic{or, deprecated=false, src=DATA, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         or          d0,d0
         or.b        d0,d0
         or.w        d0,d0
         or.l        d0,d0
         or          #42,d0
         or.b        #42,d0
         or.w        #42,d0
         or.l        #42,d0
         or          (a0),d0
         or.b        (a0),d0
         or.w        (a0),d0
         or.l        (a0),d0
         or          (a0)+,d0
         or.b        (a0)+,d0
         or.w        (a0)+,d0
         or.l        (a0)+,d0
         or          -(a0),d0
         or.b        -(a0),d0
         or.w        -(a0),d0
         or.l        -(a0),d0
         or          42(a0),d0
         or.b        42(a0),d0
         or.w        42(a0),d0
         or.l        42(a0),d0
         or          (-42,a0),d0
         or.b        (-42,a0),d0
         or.w        (-42,a0),d0
         or.l        (-42,a0),d0
         or          12(a0,d0),d0
         or.b        12(a0,d0),d0
         or.w        12(a0,d0),d0
         or.l        12(a0,d0),d0
         or          (12,a0,a0),d0
         or.b        (12,a0,a0),d0
         or.w        (12,a0,a0),d0
         or.l        (12,a0,a0),d0
         or          $4000,d0
         or.b        $4000,d0
         or.w        $4000,d0
         or.l        $4000,d0
         or          $4000.W,d0
         or.b        $4000.W,d0
         or.w        $4000.W,d0
         or.l        $4000.W,d0
         or          $4000.L,d0
         or.b        $4000.L,d0
         or.w        $4000.L,d0
         or.l        $4000.L,d0
         or          (PC),d0
         or.b        (PC),d0
         or.w        (PC),d0
         or.l        (PC),d0
         or          66(PC),d0
         or.b        66(PC),d0
         or.w        66(PC),d0
         or.l        66(PC),d0
         or          (-66,PC),d0
         or.b        (-66,PC),d0
         or.w        (-66,PC),d0
         or.l        (-66,PC),d0
         or          66(PC,d0),d0
         or.b        66(PC,d0),d0
         or.w        66(PC,d0),d0
         or.l        66(PC,d0),d0
         or          (66,PC,a0),d0
         or.b        (66,PC,a0),d0
         or.w        (66,PC,a0),d0
         or.l        (66,PC,a0),d0

* M68kMnemonic{or, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_MEMORY, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         or          d0,(a0)
         or.b        d0,(a0)
         or.w        d0,(a0)
         or.l        d0,(a0)
         or          d0,(a0)+
         or.b        d0,(a0)+
         or.w        d0,(a0)+
         or.l        d0,(a0)+
         or          d0,-(a0)
         or.b        d0,-(a0)
         or.w        d0,-(a0)
         or.l        d0,-(a0)
         or          d0,42(a0)
         or.b        d0,42(a0)
         or.w        d0,42(a0)
         or.l        d0,42(a0)
         or          d0,(-42,a0)
         or.b        d0,(-42,a0)
         or.w        d0,(-42,a0)
         or.l        d0,(-42,a0)
         or          d0,12(a0,d0)
         or.b        d0,12(a0,d0)
         or.w        d0,12(a0,d0)
         or.l        d0,12(a0,d0)
         or          d0,(12,a0,a0)
         or.b        d0,(12,a0,a0)
         or.w        d0,(12,a0,a0)
         or.l        d0,(12,a0,a0)
         or          d0,$4000
         or.b        d0,$4000
         or.w        d0,$4000
         or.l        d0,$4000
         or          d0,$4000.W
         or.b        d0,$4000.W
         or.w        d0,$4000.W
         or.l        d0,$4000.W
         or          d0,$4000.L
         or.b        d0,$4000.L
         or.w        d0,$4000.L
         or.l        d0,$4000.L

* M68kMnemonic{or, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         or          #42,d0
         or.b        #42,d0
         or.w        #42,d0
         or.l        #42,d0
         or          #42,(a0)
         or.b        #42,(a0)
         or.w        #42,(a0)
         or.l        #42,(a0)
         or          #42,(a0)+
         or.b        #42,(a0)+
         or.w        #42,(a0)+
         or.l        #42,(a0)+
         or          #42,-(a0)
         or.b        #42,-(a0)
         or.w        #42,-(a0)
         or.l        #42,-(a0)
         or          #42,42(a0)
         or.b        #42,42(a0)
         or.w        #42,42(a0)
         or.l        #42,42(a0)
         or          #42,(-42,a0)
         or.b        #42,(-42,a0)
         or.w        #42,(-42,a0)
         or.l        #42,(-42,a0)
         or          #42,12(a0,d0)
         or.b        #42,12(a0,d0)
         or.w        #42,12(a0,d0)
         or.l        #42,12(a0,d0)
         or          #42,(12,a0,a0)
         or.b        #42,(12,a0,a0)
         or.w        #42,(12,a0,a0)
         or.l        #42,(12,a0,a0)
         or          #42,$4000
         or.b        #42,$4000
         or.w        #42,$4000
         or.l        #42,$4000
         or          #42,$4000.W
         or.b        #42,$4000.W
         or.w        #42,$4000.W
         or.l        #42,$4000.W
         or          #42,$4000.L
         or.b        #42,$4000.L
         or.w        #42,$4000.L
         or.l        #42,$4000.L

* M68kMnemonic{or, deprecated=false, src=IMMEDIATE, dst=CCR_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         or          #42,CCR
         or.b        #42,CCR

* M68kMnemonic{or, deprecated=false, src=IMMEDIATE, dst=SR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         or          #42,SR
         or.w        #42,SR


********************************************************************************

* M68kMnemonic{ori, deprecated=false, src=IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ori         #42,d0
         ori.b       #42,d0
         ori.w       #42,d0
         ori.l       #42,d0

* M68kMnemonic{ori, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ori         #42,d0
         ori.b       #42,d0
         ori.w       #42,d0
         ori.l       #42,d0
         ori         #42,(a0)
         ori.b       #42,(a0)
         ori.w       #42,(a0)
         ori.l       #42,(a0)
         ori         #42,(a0)+
         ori.b       #42,(a0)+
         ori.w       #42,(a0)+
         ori.l       #42,(a0)+
         ori         #42,-(a0)
         ori.b       #42,-(a0)
         ori.w       #42,-(a0)
         ori.l       #42,-(a0)
         ori         #42,42(a0)
         ori.b       #42,42(a0)
         ori.w       #42,42(a0)
         ori.l       #42,42(a0)
         ori         #42,(-42,a0)
         ori.b       #42,(-42,a0)
         ori.w       #42,(-42,a0)
         ori.l       #42,(-42,a0)
         ori         #42,12(a0,d0)
         ori.b       #42,12(a0,d0)
         ori.w       #42,12(a0,d0)
         ori.l       #42,12(a0,d0)
         ori         #42,(12,a0,a0)
         ori.b       #42,(12,a0,a0)
         ori.w       #42,(12,a0,a0)
         ori.l       #42,(12,a0,a0)
         ori         #42,$4000
         ori.b       #42,$4000
         ori.w       #42,$4000
         ori.l       #42,$4000
         ori         #42,$4000.W
         ori.b       #42,$4000.W
         ori.w       #42,$4000.W
         ori.l       #42,$4000.W
         ori         #42,$4000.L
         ori.b       #42,$4000.L
         ori.w       #42,$4000.L
         ori.l       #42,$4000.L

* M68kMnemonic{ori, deprecated=false, src=IMMEDIATE, dst=CCR_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ori         #42,CCR
         ori.b       #42,CCR

* M68kMnemonic{ori, deprecated=false, src=IMMEDIATE, dst=SR_REGISTER, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ori         #42,SR
         ori.w       #42,SR


********************************************************************************

* M68kMnemonic{pea, deprecated=false, src=CONTROL, dst=NONE, [LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         pea         (a0)
         pea.l       (a0)
         pea         42(a0)
         pea.l       42(a0)
         pea         (-42,a0)
         pea.l       (-42,a0)
         pea         12(a0,d0)
         pea.l       12(a0,d0)
         pea         (12,a0,a0)
         pea.l       (12,a0,a0)
         pea         $4000
         pea.l       $4000
         pea         $4000.W
         pea.l       $4000.W
         pea         $4000.L
         pea.l       $4000.L
         pea         (PC)
         pea.l       (PC)
         pea         66(PC)
         pea.l       66(PC)
         pea         (-66,PC)
         pea.l       (-66,PC)
         pea         66(PC,d0)
         pea.l       66(PC,d0)
         pea         (66,PC,a0)
         pea.l       (66,PC,a0)


********************************************************************************

* M68kMnemonic{reset, deprecated=false, src=NONE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         reset


********************************************************************************

* M68kMnemonic{rol, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rol         (a0)
         rol.w       (a0)
         rol         (a0)+
         rol.w       (a0)+
         rol         -(a0)
         rol.w       -(a0)
         rol         42(a0)
         rol.w       42(a0)
         rol         (-42,a0)
         rol.w       (-42,a0)
         rol         12(a0,d0)
         rol.w       12(a0,d0)
         rol         (12,a0,a0)
         rol.w       (12,a0,a0)
         rol         $4000
         rol.w       $4000
         rol         $4000.W
         rol.w       $4000.W
         rol         $4000.L
         rol.w       $4000.L

* M68kMnemonic{rol, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rol         d0,d0
         rol.b       d0,d0
         rol.w       d0,d0
         rol.l       d0,d0

* M68kMnemonic{rol, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rol         #1,d0
         rol.b       #1,d0
         rol.w       #1,d0
         rol.l       #1,d0

* M68kMnemonic{rol, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rol         d0
         rol.b       d0
         rol.w       d0
         rol.l       d0


********************************************************************************

* M68kMnemonic{ror, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ror         (a0)
         ror.w       (a0)
         ror         (a0)+
         ror.w       (a0)+
         ror         -(a0)
         ror.w       -(a0)
         ror         42(a0)
         ror.w       42(a0)
         ror         (-42,a0)
         ror.w       (-42,a0)
         ror         12(a0,d0)
         ror.w       12(a0,d0)
         ror         (12,a0,a0)
         ror.w       (12,a0,a0)
         ror         $4000
         ror.w       $4000
         ror         $4000.W
         ror.w       $4000.W
         ror         $4000.L
         ror.w       $4000.L

* M68kMnemonic{ror, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ror         d0,d0
         ror.b       d0,d0
         ror.w       d0,d0
         ror.l       d0,d0

* M68kMnemonic{ror, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ror         #1,d0
         ror.b       #1,d0
         ror.w       #1,d0
         ror.l       #1,d0

* M68kMnemonic{ror, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         ror         d0
         ror.b       d0
         ror.w       d0
         ror.l       d0


********************************************************************************

* M68kMnemonic{roxl, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxl        (a0)
         roxl.w      (a0)
         roxl        (a0)+
         roxl.w      (a0)+
         roxl        -(a0)
         roxl.w      -(a0)
         roxl        42(a0)
         roxl.w      42(a0)
         roxl        (-42,a0)
         roxl.w      (-42,a0)
         roxl        12(a0,d0)
         roxl.w      12(a0,d0)
         roxl        (12,a0,a0)
         roxl.w      (12,a0,a0)
         roxl        $4000
         roxl.w      $4000
         roxl        $4000.W
         roxl.w      $4000.W
         roxl        $4000.L
         roxl.w      $4000.L

* M68kMnemonic{roxl, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxl        d0,d0
         roxl.b      d0,d0
         roxl.w      d0,d0
         roxl.l      d0,d0

* M68kMnemonic{roxl, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxl        #1,d0
         roxl.b      #1,d0
         roxl.w      #1,d0
         roxl.l      #1,d0

* M68kMnemonic{roxl, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxl        d0
         roxl.b      d0
         roxl.w      d0
         roxl.l      d0


********************************************************************************

* M68kMnemonic{roxr, deprecated=false, src=ALTERABLE_MEMORY, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxr        (a0)
         roxr.w      (a0)
         roxr        (a0)+
         roxr.w      (a0)+
         roxr        -(a0)
         roxr.w      -(a0)
         roxr        42(a0)
         roxr.w      42(a0)
         roxr        (-42,a0)
         roxr.w      (-42,a0)
         roxr        12(a0,d0)
         roxr.w      12(a0,d0)
         roxr        (12,a0,a0)
         roxr.w      (12,a0,a0)
         roxr        $4000
         roxr.w      $4000
         roxr        $4000.W
         roxr.w      $4000.W
         roxr        $4000.L
         roxr.w      $4000.L

* M68kMnemonic{roxr, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxr        d0,d0
         roxr.b      d0,d0
         roxr.w      d0,d0
         roxr.l      d0,d0

* M68kMnemonic{roxr, deprecated=false, src=QUICK_IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxr        #1,d0
         roxr.b      #1,d0
         roxr.w      #1,d0
         roxr.l      #1,d0

* M68kMnemonic{roxr, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         roxr        d0
         roxr.b      d0
         roxr.w      d0
         roxr.l      d0


********************************************************************************

* M68kMnemonic{rtd, deprecated=false, src=QUICK_IMMEDIATE, dst=NONE, [UNSIZED], [M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rtd       #1


********************************************************************************

* M68kMnemonic{rte, deprecated=false, src=NONE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rte


********************************************************************************

* M68kMnemonic{rtr, deprecated=false, src=NONE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rtr


********************************************************************************

* M68kMnemonic{rts, deprecated=false, src=NONE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         rts


********************************************************************************

* M68kMnemonic{sbcd, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sbcd        d0,d0
         sbcd.b      d0,d0

* M68kMnemonic{sbcd, deprecated=false, src=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, dst=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sbcd        -(a0),-(a0)
         sbcd.b      -(a0),-(a0)


********************************************************************************

* M68kMnemonic{scc, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         scc         d0
         scc.b       d0

* M68kMnemonic{scc, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         scc         d0
         scc.b       d0
         scc         (a0)
         scc.b       (a0)
         scc         (a0)+
         scc.b       (a0)+
         scc         -(a0)
         scc.b       -(a0)
         scc         42(a0)
         scc.b       42(a0)
         scc         (-42,a0)
         scc.b       (-42,a0)
         scc         12(a0,d0)
         scc.b       12(a0,d0)
         scc         (12,a0,a0)
         scc.b       (12,a0,a0)
         scc         $4000
         scc.b       $4000
         scc         $4000.W
         scc.b       $4000.W
         scc         $4000.L
         scc.b       $4000.L


********************************************************************************

* M68kMnemonic{scs, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         scs         d0
         scs.b       d0

* M68kMnemonic{scs, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         scs         d0
         scs.b       d0
         scs         (a0)
         scs.b       (a0)
         scs         (a0)+
         scs.b       (a0)+
         scs         -(a0)
         scs.b       -(a0)
         scs         42(a0)
         scs.b       42(a0)
         scs         (-42,a0)
         scs.b       (-42,a0)
         scs         12(a0,d0)
         scs.b       12(a0,d0)
         scs         (12,a0,a0)
         scs.b       (12,a0,a0)
         scs         $4000
         scs.b       $4000
         scs         $4000.W
         scs.b       $4000.W
         scs         $4000.L
         scs.b       $4000.L


********************************************************************************

* M68kMnemonic{seq, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         seq         d0
         seq.b       d0

* M68kMnemonic{seq, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         seq         d0
         seq.b       d0
         seq         (a0)
         seq.b       (a0)
         seq         (a0)+
         seq.b       (a0)+
         seq         -(a0)
         seq.b       -(a0)
         seq         42(a0)
         seq.b       42(a0)
         seq         (-42,a0)
         seq.b       (-42,a0)
         seq         12(a0,d0)
         seq.b       12(a0,d0)
         seq         (12,a0,a0)
         seq.b       (12,a0,a0)
         seq         $4000
         seq.b       $4000
         seq         $4000.W
         seq.b       $4000.W
         seq         $4000.L
         seq.b       $4000.L


********************************************************************************

* M68kMnemonic{sf, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sf          d0
         sf.b        d0

* M68kMnemonic{sf, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sf          d0
         sf.b        d0
         sf          (a0)
         sf.b        (a0)
         sf          (a0)+
         sf.b        (a0)+
         sf          -(a0)
         sf.b        -(a0)
         sf          42(a0)
         sf.b        42(a0)
         sf          (-42,a0)
         sf.b        (-42,a0)
         sf          12(a0,d0)
         sf.b        12(a0,d0)
         sf          (12,a0,a0)
         sf.b        (12,a0,a0)
         sf          $4000
         sf.b        $4000
         sf          $4000.W
         sf.b        $4000.W
         sf          $4000.L
         sf.b        $4000.L


********************************************************************************

* M68kMnemonic{sge, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sge         d0
         sge.b       d0

* M68kMnemonic{sge, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sge         d0
         sge.b       d0
         sge         (a0)
         sge.b       (a0)
         sge         (a0)+
         sge.b       (a0)+
         sge         -(a0)
         sge.b       -(a0)
         sge         42(a0)
         sge.b       42(a0)
         sge         (-42,a0)
         sge.b       (-42,a0)
         sge         12(a0,d0)
         sge.b       12(a0,d0)
         sge         (12,a0,a0)
         sge.b       (12,a0,a0)
         sge         $4000
         sge.b       $4000
         sge         $4000.W
         sge.b       $4000.W
         sge         $4000.L
         sge.b       $4000.L


********************************************************************************

* M68kMnemonic{sgt, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sgt         d0
         sgt.b       d0

* M68kMnemonic{sgt, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sgt         d0
         sgt.b       d0
         sgt         (a0)
         sgt.b       (a0)
         sgt         (a0)+
         sgt.b       (a0)+
         sgt         -(a0)
         sgt.b       -(a0)
         sgt         42(a0)
         sgt.b       42(a0)
         sgt         (-42,a0)
         sgt.b       (-42,a0)
         sgt         12(a0,d0)
         sgt.b       12(a0,d0)
         sgt         (12,a0,a0)
         sgt.b       (12,a0,a0)
         sgt         $4000
         sgt.b       $4000
         sgt         $4000.W
         sgt.b       $4000.W
         sgt         $4000.L
         sgt.b       $4000.L


********************************************************************************

* M68kMnemonic{shi, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         shi         d0
         shi.b       d0

* M68kMnemonic{shi, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         shi         d0
         shi.b       d0
         shi         (a0)
         shi.b       (a0)
         shi         (a0)+
         shi.b       (a0)+
         shi         -(a0)
         shi.b       -(a0)
         shi         42(a0)
         shi.b       42(a0)
         shi         (-42,a0)
         shi.b       (-42,a0)
         shi         12(a0,d0)
         shi.b       12(a0,d0)
         shi         (12,a0,a0)
         shi.b       (12,a0,a0)
         shi         $4000
         shi.b       $4000
         shi         $4000.W
         shi.b       $4000.W
         shi         $4000.L
         shi.b       $4000.L


********************************************************************************

* M68kMnemonic{shs, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         shs         d0
         shs.b       d0

* M68kMnemonic{shs, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         shs         d0
         shs.b       d0
         shs         (a0)
         shs.b       (a0)
         shs         (a0)+
         shs.b       (a0)+
         shs         -(a0)
         shs.b       -(a0)
         shs         42(a0)
         shs.b       42(a0)
         shs         (-42,a0)
         shs.b       (-42,a0)
         shs         12(a0,d0)
         shs.b       12(a0,d0)
         shs         (12,a0,a0)
         shs.b       (12,a0,a0)
         shs         $4000
         shs.b       $4000
         shs         $4000.W
         shs.b       $4000.W
         shs         $4000.L
         shs.b       $4000.L


********************************************************************************

* M68kMnemonic{sle, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sle         d0
         sle.b       d0

* M68kMnemonic{sle, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sle         d0
         sle.b       d0
         sle         (a0)
         sle.b       (a0)
         sle         (a0)+
         sle.b       (a0)+
         sle         -(a0)
         sle.b       -(a0)
         sle         42(a0)
         sle.b       42(a0)
         sle         (-42,a0)
         sle.b       (-42,a0)
         sle         12(a0,d0)
         sle.b       12(a0,d0)
         sle         (12,a0,a0)
         sle.b       (12,a0,a0)
         sle         $4000
         sle.b       $4000
         sle         $4000.W
         sle.b       $4000.W
         sle         $4000.L
         sle.b       $4000.L


********************************************************************************

* M68kMnemonic{slo, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         slo         d0
         slo.b       d0

* M68kMnemonic{slo, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         slo         d0
         slo.b       d0
         slo         (a0)
         slo.b       (a0)
         slo         (a0)+
         slo.b       (a0)+
         slo         -(a0)
         slo.b       -(a0)
         slo         42(a0)
         slo.b       42(a0)
         slo         (-42,a0)
         slo.b       (-42,a0)
         slo         12(a0,d0)
         slo.b       12(a0,d0)
         slo         (12,a0,a0)
         slo.b       (12,a0,a0)
         slo         $4000
         slo.b       $4000
         slo         $4000.W
         slo.b       $4000.W
         slo         $4000.L
         slo.b       $4000.L


********************************************************************************

* M68kMnemonic{sls, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sls         d0
         sls.b       d0

* M68kMnemonic{sls, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sls         d0
         sls.b       d0
         sls         (a0)
         sls.b       (a0)
         sls         (a0)+
         sls.b       (a0)+
         sls         -(a0)
         sls.b       -(a0)
         sls         42(a0)
         sls.b       42(a0)
         sls         (-42,a0)
         sls.b       (-42,a0)
         sls         12(a0,d0)
         sls.b       12(a0,d0)
         sls         (12,a0,a0)
         sls.b       (12,a0,a0)
         sls         $4000
         sls.b       $4000
         sls         $4000.W
         sls.b       $4000.W
         sls         $4000.L
         sls.b       $4000.L


********************************************************************************

* M68kMnemonic{slt, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         slt         d0
         slt.b       d0

* M68kMnemonic{slt, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         slt         d0
         slt.b       d0
         slt         (a0)
         slt.b       (a0)
         slt         (a0)+
         slt.b       (a0)+
         slt         -(a0)
         slt.b       -(a0)
         slt         42(a0)
         slt.b       42(a0)
         slt         (-42,a0)
         slt.b       (-42,a0)
         slt         12(a0,d0)
         slt.b       12(a0,d0)
         slt         (12,a0,a0)
         slt.b       (12,a0,a0)
         slt         $4000
         slt.b       $4000
         slt         $4000.W
         slt.b       $4000.W
         slt         $4000.L
         slt.b       $4000.L


********************************************************************************

* M68kMnemonic{smi, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         smi         d0
         smi.b       d0

* M68kMnemonic{smi, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         smi         d0
         smi.b       d0
         smi         (a0)
         smi.b       (a0)
         smi         (a0)+
         smi.b       (a0)+
         smi         -(a0)
         smi.b       -(a0)
         smi         42(a0)
         smi.b       42(a0)
         smi         (-42,a0)
         smi.b       (-42,a0)
         smi         12(a0,d0)
         smi.b       12(a0,d0)
         smi         (12,a0,a0)
         smi.b       (12,a0,a0)
         smi         $4000
         smi.b       $4000
         smi         $4000.W
         smi.b       $4000.W
         smi         $4000.L
         smi.b       $4000.L


********************************************************************************

* M68kMnemonic{sne, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sne         d0
         sne.b       d0

* M68kMnemonic{sne, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sne         d0
         sne.b       d0
         sne         (a0)
         sne.b       (a0)
         sne         (a0)+
         sne.b       (a0)+
         sne         -(a0)
         sne.b       -(a0)
         sne         42(a0)
         sne.b       42(a0)
         sne         (-42,a0)
         sne.b       (-42,a0)
         sne         12(a0,d0)
         sne.b       12(a0,d0)
         sne         (12,a0,a0)
         sne.b       (12,a0,a0)
         sne         $4000
         sne.b       $4000
         sne         $4000.W
         sne.b       $4000.W
         sne         $4000.L
         sne.b       $4000.L


********************************************************************************

* M68kMnemonic{spl, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         spl         d0
         spl.b       d0

* M68kMnemonic{spl, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         spl         d0
         spl.b       d0
         spl         (a0)
         spl.b       (a0)
         spl         (a0)+
         spl.b       (a0)+
         spl         -(a0)
         spl.b       -(a0)
         spl         42(a0)
         spl.b       42(a0)
         spl         (-42,a0)
         spl.b       (-42,a0)
         spl         12(a0,d0)
         spl.b       12(a0,d0)
         spl         (12,a0,a0)
         spl.b       (12,a0,a0)
         spl         $4000
         spl.b       $4000
         spl         $4000.W
         spl.b       $4000.W
         spl         $4000.L
         spl.b       $4000.L


********************************************************************************

* M68kMnemonic{st, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         st          d0
         st.b        d0

* M68kMnemonic{st, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         st          d0
         st.b        d0
         st          (a0)
         st.b        (a0)
         st          (a0)+
         st.b        (a0)+
         st          -(a0)
         st.b        -(a0)
         st          42(a0)
         st.b        42(a0)
         st          (-42,a0)
         st.b        (-42,a0)
         st          12(a0,d0)
         st.b        12(a0,d0)
         st          (12,a0,a0)
         st.b        (12,a0,a0)
         st          $4000
         st.b        $4000
         st          $4000.W
         st.b        $4000.W
         st          $4000.L
         st.b        $4000.L


********************************************************************************

* M68kMnemonic{stop, deprecated=false, src=QUICK_IMMEDIATE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         stop      #1


********************************************************************************

* M68kMnemonic{sub, deprecated=false, src=DATA, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sub         d0,d0
         sub.b       d0,d0
         sub.w       d0,d0
         sub.l       d0,d0
         sub         #42,d0
         sub.b       #42,d0
         sub.w       #42,d0
         sub.l       #42,d0
         sub         (a0),d0
         sub.b       (a0),d0
         sub.w       (a0),d0
         sub.l       (a0),d0
         sub         (a0)+,d0
         sub.b       (a0)+,d0
         sub.w       (a0)+,d0
         sub.l       (a0)+,d0
         sub         -(a0),d0
         sub.b       -(a0),d0
         sub.w       -(a0),d0
         sub.l       -(a0),d0
         sub         42(a0),d0
         sub.b       42(a0),d0
         sub.w       42(a0),d0
         sub.l       42(a0),d0
         sub         (-42,a0),d0
         sub.b       (-42,a0),d0
         sub.w       (-42,a0),d0
         sub.l       (-42,a0),d0
         sub         12(a0,d0),d0
         sub.b       12(a0,d0),d0
         sub.w       12(a0,d0),d0
         sub.l       12(a0,d0),d0
         sub         (12,a0,a0),d0
         sub.b       (12,a0,a0),d0
         sub.w       (12,a0,a0),d0
         sub.l       (12,a0,a0),d0
         sub         $4000,d0
         sub.b       $4000,d0
         sub.w       $4000,d0
         sub.l       $4000,d0
         sub         $4000.W,d0
         sub.b       $4000.W,d0
         sub.w       $4000.W,d0
         sub.l       $4000.W,d0
         sub         $4000.L,d0
         sub.b       $4000.L,d0
         sub.w       $4000.L,d0
         sub.l       $4000.L,d0
         sub         (PC),d0
         sub.b       (PC),d0
         sub.w       (PC),d0
         sub.l       (PC),d0
         sub         66(PC),d0
         sub.b       66(PC),d0
         sub.w       66(PC),d0
         sub.l       66(PC),d0
         sub         (-66,PC),d0
         sub.b       (-66,PC),d0
         sub.w       (-66,PC),d0
         sub.l       (-66,PC),d0
         sub         66(PC,d0),d0
         sub.b       66(PC,d0),d0
         sub.w       66(PC,d0),d0
         sub.l       66(PC,d0),d0
         sub         (66,PC,a0),d0
         sub.b       (66,PC,a0),d0
         sub.w       (66,PC,a0),d0
         sub.l       (66,PC,a0),d0

* M68kMnemonic{sub, deprecated=false, src=ADDRESS_REGISTER, dst=DATA_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sub         a0,d0
         sub.w       a0,d0
         sub.l       a0,d0

* M68kMnemonic{sub, deprecated=false, src=DATA_REGISTER, dst=ALTERABLE_MEMORY, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sub         d0,(a0)
         sub.b       d0,(a0)
         sub.w       d0,(a0)
         sub.l       d0,(a0)
         sub         d0,(a0)+
         sub.b       d0,(a0)+
         sub.w       d0,(a0)+
         sub.l       d0,(a0)+
         sub         d0,-(a0)
         sub.b       d0,-(a0)
         sub.w       d0,-(a0)
         sub.l       d0,-(a0)
         sub         d0,42(a0)
         sub.b       d0,42(a0)
         sub.w       d0,42(a0)
         sub.l       d0,42(a0)
         sub         d0,(-42,a0)
         sub.b       d0,(-42,a0)
         sub.w       d0,(-42,a0)
         sub.l       d0,(-42,a0)
         sub         d0,12(a0,d0)
         sub.b       d0,12(a0,d0)
         sub.w       d0,12(a0,d0)
         sub.l       d0,12(a0,d0)
         sub         d0,(12,a0,a0)
         sub.b       d0,(12,a0,a0)
         sub.w       d0,(12,a0,a0)
         sub.l       d0,(12,a0,a0)
         sub         d0,$4000
         sub.b       d0,$4000
         sub.w       d0,$4000
         sub.l       d0,$4000
         sub         d0,$4000.W
         sub.b       d0,$4000.W
         sub.w       d0,$4000.W
         sub.l       d0,$4000.W
         sub         d0,$4000.L
         sub.b       d0,$4000.L
         sub.w       d0,$4000.L
         sub.l       d0,$4000.L

* M68kMnemonic{sub, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sub         d0,a0
         sub.w       d0,a0
         sub.l       d0,a0
         sub         a0,a0
         sub.w       a0,a0
         sub.l       a0,a0
         sub         (a0),a0
         sub.w       (a0),a0
         sub.l       (a0),a0
         sub         (a0)+,a0
         sub.w       (a0)+,a0
         sub.l       (a0)+,a0
         sub         -(a0),a0
         sub.w       -(a0),a0
         sub.l       -(a0),a0
         sub         42(a0),a0
         sub.w       42(a0),a0
         sub.l       42(a0),a0
         sub         (-42,a0),a0
         sub.w       (-42,a0),a0
         sub.l       (-42,a0),a0
         sub         12(a0,d0),a0
         sub.w       12(a0,d0),a0
         sub.l       12(a0,d0),a0
         sub         (12,a0,a0),a0
         sub.w       (12,a0,a0),a0
         sub.l       (12,a0,a0),a0
         sub         $4000,a0
         sub.w       $4000,a0
         sub.l       $4000,a0
         sub         $4000.W,a0
         sub.w       $4000.W,a0
         sub.l       $4000.W,a0
         sub         $4000.L,a0
         sub.w       $4000.L,a0
         sub.l       $4000.L,a0
         sub         (PC),a0
         sub.w       (PC),a0
         sub.l       (PC),a0
         sub         66(PC),a0
         sub.w       66(PC),a0
         sub.l       66(PC),a0
         sub         (-66,PC),a0
         sub.w       (-66,PC),a0
         sub.l       (-66,PC),a0
         sub         66(PC,d0),a0
         sub.w       66(PC,d0),a0
         sub.l       66(PC,d0),a0
         sub         (66,PC,a0),a0
         sub.w       (66,PC,a0),a0
         sub.l       (66,PC,a0),a0
         sub         #42,a0
         sub.w       #42,a0
         sub.l       #42,a0

* M68kMnemonic{sub, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         sub         #42,d0
         sub.b       #42,d0
         sub.w       #42,d0
         sub.l       #42,d0
         sub         #42,(a0)
         sub.b       #42,(a0)
         sub.w       #42,(a0)
         sub.l       #42,(a0)
         sub         #42,(a0)+
         sub.b       #42,(a0)+
         sub.w       #42,(a0)+
         sub.l       #42,(a0)+
         sub         #42,-(a0)
         sub.b       #42,-(a0)
         sub.w       #42,-(a0)
         sub.l       #42,-(a0)
         sub         #42,42(a0)
         sub.b       #42,42(a0)
         sub.w       #42,42(a0)
         sub.l       #42,42(a0)
         sub         #42,(-42,a0)
         sub.b       #42,(-42,a0)
         sub.w       #42,(-42,a0)
         sub.l       #42,(-42,a0)
         sub         #42,12(a0,d0)
         sub.b       #42,12(a0,d0)
         sub.w       #42,12(a0,d0)
         sub.l       #42,12(a0,d0)
         sub         #42,(12,a0,a0)
         sub.b       #42,(12,a0,a0)
         sub.w       #42,(12,a0,a0)
         sub.l       #42,(12,a0,a0)
         sub         #42,$4000
         sub.b       #42,$4000
         sub.w       #42,$4000
         sub.l       #42,$4000
         sub         #42,$4000.W
         sub.b       #42,$4000.W
         sub.w       #42,$4000.W
         sub.l       #42,$4000.W
         sub         #42,$4000.L
         sub.b       #42,$4000.L
         sub.w       #42,$4000.L
         sub.l       #42,$4000.L


********************************************************************************

* M68kMnemonic{suba, deprecated=false, src=ALL, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         suba        d0,a0
         suba.w      d0,a0
         suba.l      d0,a0
         suba        a0,a0
         suba.w      a0,a0
         suba.l      a0,a0
         suba        (a0),a0
         suba.w      (a0),a0
         suba.l      (a0),a0
         suba        (a0)+,a0
         suba.w      (a0)+,a0
         suba.l      (a0)+,a0
         suba        -(a0),a0
         suba.w      -(a0),a0
         suba.l      -(a0),a0
         suba        42(a0),a0
         suba.w      42(a0),a0
         suba.l      42(a0),a0
         suba        (-42,a0),a0
         suba.w      (-42,a0),a0
         suba.l      (-42,a0),a0
         suba        12(a0,d0),a0
         suba.w      12(a0,d0),a0
         suba.l      12(a0,d0),a0
         suba        (12,a0,a0),a0
         suba.w      (12,a0,a0),a0
         suba.l      (12,a0,a0),a0
         suba        $4000,a0
         suba.w      $4000,a0
         suba.l      $4000,a0
         suba        $4000.W,a0
         suba.w      $4000.W,a0
         suba.l      $4000.W,a0
         suba        $4000.L,a0
         suba.w      $4000.L,a0
         suba.l      $4000.L,a0
         suba        (PC),a0
         suba.w      (PC),a0
         suba.l      (PC),a0
         suba        66(PC),a0
         suba.w      66(PC),a0
         suba.l      66(PC),a0
         suba        (-66,PC),a0
         suba.w      (-66,PC),a0
         suba.l      (-66,PC),a0
         suba        66(PC,d0),a0
         suba.w      66(PC,d0),a0
         suba.l      66(PC,d0),a0
         suba        (66,PC,a0),a0
         suba.w      (66,PC,a0),a0
         suba.l      (66,PC,a0),a0
         suba        #42,a0
         suba.w      #42,a0
         suba.l      #42,a0


********************************************************************************

* M68kMnemonic{subi, deprecated=false, src=IMMEDIATE, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         subi        #42,d0
         subi.b      #42,d0
         subi.w      #42,d0
         subi.l      #42,d0

* M68kMnemonic{subi, deprecated=false, src=IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         subi        #42,d0
         subi.b      #42,d0
         subi.w      #42,d0
         subi.l      #42,d0
         subi        #42,(a0)
         subi.b      #42,(a0)
         subi.w      #42,(a0)
         subi.l      #42,(a0)
         subi        #42,(a0)+
         subi.b      #42,(a0)+
         subi.w      #42,(a0)+
         subi.l      #42,(a0)+
         subi        #42,-(a0)
         subi.b      #42,-(a0)
         subi.w      #42,-(a0)
         subi.l      #42,-(a0)
         subi        #42,42(a0)
         subi.b      #42,42(a0)
         subi.w      #42,42(a0)
         subi.l      #42,42(a0)
         subi        #42,(-42,a0)
         subi.b      #42,(-42,a0)
         subi.w      #42,(-42,a0)
         subi.l      #42,(-42,a0)
         subi        #42,12(a0,d0)
         subi.b      #42,12(a0,d0)
         subi.w      #42,12(a0,d0)
         subi.l      #42,12(a0,d0)
         subi        #42,(12,a0,a0)
         subi.b      #42,(12,a0,a0)
         subi.w      #42,(12,a0,a0)
         subi.l      #42,(12,a0,a0)
         subi        #42,$4000
         subi.b      #42,$4000
         subi.w      #42,$4000
         subi.l      #42,$4000
         subi        #42,$4000.W
         subi.b      #42,$4000.W
         subi.w      #42,$4000.W
         subi.l      #42,$4000.W
         subi        #42,$4000.L
         subi.b      #42,$4000.L
         subi.w      #42,$4000.L
         subi.l      #42,$4000.L


********************************************************************************

* M68kMnemonic{subq, deprecated=false, src=QUICK_IMMEDIATE, dst=ADDRESS_REGISTER, [WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         subq        #1,a0
         subq.w      #1,a0
         subq.l      #1,a0

* M68kMnemonic{subq, deprecated=false, src=QUICK_IMMEDIATE, dst=ALTERABLE_DATA, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         subq        #1,d0
         subq.b      #1,d0
         subq.w      #1,d0
         subq.l      #1,d0
         subq        #1,(a0)
         subq.b      #1,(a0)
         subq.w      #1,(a0)
         subq.l      #1,(a0)
         subq        #1,(a0)+
         subq.b      #1,(a0)+
         subq.w      #1,(a0)+
         subq.l      #1,(a0)+
         subq        #1,-(a0)
         subq.b      #1,-(a0)
         subq.w      #1,-(a0)
         subq.l      #1,-(a0)
         subq        #1,42(a0)
         subq.b      #1,42(a0)
         subq.w      #1,42(a0)
         subq.l      #1,42(a0)
         subq        #1,(-42,a0)
         subq.b      #1,(-42,a0)
         subq.w      #1,(-42,a0)
         subq.l      #1,(-42,a0)
         subq        #1,12(a0,d0)
         subq.b      #1,12(a0,d0)
         subq.w      #1,12(a0,d0)
         subq.l      #1,12(a0,d0)
         subq        #1,(12,a0,a0)
         subq.b      #1,(12,a0,a0)
         subq.w      #1,(12,a0,a0)
         subq.l      #1,(12,a0,a0)
         subq        #1,$4000
         subq.b      #1,$4000
         subq.w      #1,$4000
         subq.l      #1,$4000
         subq        #1,$4000.W
         subq.b      #1,$4000.W
         subq.w      #1,$4000.W
         subq.l      #1,$4000.W
         subq        #1,$4000.L
         subq.b      #1,$4000.L
         subq.w      #1,$4000.L
         subq.l      #1,$4000.L


********************************************************************************

* M68kMnemonic{subx, deprecated=false, src=DATA_REGISTER, dst=DATA_REGISTER, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         subx        d0,d0
         subx.b      d0,d0
         subx.w      d0,d0
         subx.l      d0,d0

* M68kMnemonic{subx, deprecated=false, src=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, dst=ADDRESS_REGISTER_INDIRECT_PRE_DECREMENT, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         subx        -(a0),-(a0)
         subx.b      -(a0),-(a0)
         subx.w      -(a0),-(a0)
         subx.l      -(a0),-(a0)


********************************************************************************

* M68kMnemonic{svc, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         svc         d0
         svc.b       d0

* M68kMnemonic{svc, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         svc         d0
         svc.b       d0
         svc         (a0)
         svc.b       (a0)
         svc         (a0)+
         svc.b       (a0)+
         svc         -(a0)
         svc.b       -(a0)
         svc         42(a0)
         svc.b       42(a0)
         svc         (-42,a0)
         svc.b       (-42,a0)
         svc         12(a0,d0)
         svc.b       12(a0,d0)
         svc         (12,a0,a0)
         svc.b       (12,a0,a0)
         svc         $4000
         svc.b       $4000
         svc         $4000.W
         svc.b       $4000.W
         svc         $4000.L
         svc.b       $4000.L


********************************************************************************

* M68kMnemonic{svs, deprecated=false, src=DATA_REGISTER, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         svs         d0
         svs.b       d0

* M68kMnemonic{svs, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         svs         d0
         svs.b       d0
         svs         (a0)
         svs.b       (a0)
         svs         (a0)+
         svs.b       (a0)+
         svs         -(a0)
         svs.b       -(a0)
         svs         42(a0)
         svs.b       42(a0)
         svs         (-42,a0)
         svs.b       (-42,a0)
         svs         12(a0,d0)
         svs.b       12(a0,d0)
         svs         (12,a0,a0)
         svs.b       (12,a0,a0)
         svs         $4000
         svs.b       $4000
         svs         $4000.W
         svs.b       $4000.W
         svs         $4000.L
         svs.b       $4000.L


********************************************************************************

* M68kMnemonic{swap, deprecated=false, src=DATA_REGISTER, dst=NONE, [WORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         swap        d0
         swap.w      d0


********************************************************************************

* M68kMnemonic{tas, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         tas         d0
         tas.b       d0
         tas         (a0)
         tas.b       (a0)
         tas         (a0)+
         tas.b       (a0)+
         tas         -(a0)
         tas.b       -(a0)
         tas         42(a0)
         tas.b       42(a0)
         tas         (-42,a0)
         tas.b       (-42,a0)
         tas         12(a0,d0)
         tas.b       12(a0,d0)
         tas         (12,a0,a0)
         tas.b       (12,a0,a0)
         tas         $4000
         tas.b       $4000
         tas         $4000.W
         tas.b       $4000.W
         tas         $4000.L
         tas.b       $4000.L


********************************************************************************

* M68kMnemonic{trap, deprecated=false, src=QUICK_IMMEDIATE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         trap      #1


********************************************************************************

* M68kMnemonic{trapv, deprecated=false, src=NONE, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         trapv


********************************************************************************

* M68kMnemonic{tst, deprecated=false, src=ALTERABLE_DATA, dst=NONE, [BYTE, WORD, LONGWORD], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         tst         d0
         tst.b       d0
         tst.w       d0
         tst.l       d0
         tst         (a0)
         tst.b       (a0)
         tst.w       (a0)
         tst.l       (a0)
         tst         (a0)+
         tst.b       (a0)+
         tst.w       (a0)+
         tst.l       (a0)+
         tst         -(a0)
         tst.b       -(a0)
         tst.w       -(a0)
         tst.l       -(a0)
         tst         42(a0)
         tst.b       42(a0)
         tst.w       42(a0)
         tst.l       42(a0)
         tst         (-42,a0)
         tst.b       (-42,a0)
         tst.w       (-42,a0)
         tst.l       (-42,a0)
         tst         12(a0,d0)
         tst.b       12(a0,d0)
         tst.w       12(a0,d0)
         tst.l       12(a0,d0)
         tst         (12,a0,a0)
         tst.b       (12,a0,a0)
         tst.w       (12,a0,a0)
         tst.l       (12,a0,a0)
         tst         $4000
         tst.b       $4000
         tst.w       $4000
         tst.l       $4000
         tst         $4000.W
         tst.b       $4000.W
         tst.w       $4000.W
         tst.l       $4000.W
         tst         $4000.L
         tst.b       $4000.L
         tst.w       $4000.L
         tst.l       $4000.L

* M68kMnemonic{tst, deprecated=false, src=DATA, dst=NONE, [BYTE, WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         tst         d0
         tst.b       d0
         tst.w       d0
         tst.l       d0
         tst         #42
         tst.b       #42
         tst.w       #42
         tst.l       #42
         tst         (a0)
         tst.b       (a0)
         tst.w       (a0)
         tst.l       (a0)
         tst         (a0)+
         tst.b       (a0)+
         tst.w       (a0)+
         tst.l       (a0)+
         tst         -(a0)
         tst.b       -(a0)
         tst.w       -(a0)
         tst.l       -(a0)
         tst         42(a0)
         tst.b       42(a0)
         tst.w       42(a0)
         tst.l       42(a0)
         tst         (-42,a0)
         tst.b       (-42,a0)
         tst.w       (-42,a0)
         tst.l       (-42,a0)
         tst         12(a0,d0)
         tst.b       12(a0,d0)
         tst.w       12(a0,d0)
         tst.l       12(a0,d0)
         tst         (12,a0,a0)
         tst.b       (12,a0,a0)
         tst.w       (12,a0,a0)
         tst.l       (12,a0,a0)
         tst         $4000
         tst.b       $4000
         tst.w       $4000
         tst.l       $4000
         tst         $4000.W
         tst.b       $4000.W
         tst.w       $4000.W
         tst.l       $4000.W
         tst         $4000.L
         tst.b       $4000.L
         tst.w       $4000.L
         tst.l       $4000.L
         tst         (PC)
         tst.b       (PC)
         tst.w       (PC)
         tst.l       (PC)
         tst         66(PC)
         tst.b       66(PC)
         tst.w       66(PC)
         tst.l       66(PC)
         tst         (-66,PC)
         tst.b       (-66,PC)
         tst.w       (-66,PC)
         tst.l       (-66,PC)
         tst         66(PC,d0)
         tst.b       66(PC,d0)
         tst.w       66(PC,d0)
         tst.l       66(PC,d0)
         tst         (66,PC,a0)
         tst.b       (66,PC,a0)
         tst.w       (66,PC,a0)
         tst.l       (66,PC,a0)

* M68kMnemonic{tst, deprecated=false, src=ADDRESS_REGISTER, dst=NONE, [WORD, LONGWORD], [M_68020, M_68030, M_68040, M_68060, AC_68080]}
         tst         a0
         tst.w       a0
         tst.l       a0


********************************************************************************

* M68kMnemonic{unlk, deprecated=false, src=ADDRESS_REGISTER, dst=NONE, [UNSIZED], [M_68000, M_68010, M_68020, M_68030, M_68040, M_68060, AC_68080]}
         unlk      a0
* Instructions count: 128

* 0/5916 deprecated: 781