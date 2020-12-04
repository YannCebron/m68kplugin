label
 cmpi #1,-(a0)
 cmpi #1,d0
 cmpm.b (a0)+,(a1)+
 cmpa d0,a0
 cmp.b d0,d1
 cmpm (a0)+,(a1)+
 cmpa.l d0,a0
 cmp label,d1
 cmp d0,d1