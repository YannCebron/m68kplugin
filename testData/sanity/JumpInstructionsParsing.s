label
 bsr label+label2
 rte
offset
 bsr.s offset
 bsr.b offset
 bsr.w offset
 jmp (pc,d0)
 bsr label
 rtr
 rts
 jsr label
 jsr label(a0)
 jsr 42(a0,d0)
 jmp label