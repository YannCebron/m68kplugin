label
 neg d0
 swap d0
 clr.l label
 chk.w (a0),d0
 ext d0
 ext.w d0
 swap.w d0
 link a0,#1
 lea 8(a0,d1.w),a0
 tas (a0)
 lea id(pc),a0
 tst.l label
 illegal
 exg.l d0,a0
 negx.b 42(a1)
 stop #1
 trapv
 negx d0
 nop
 pea (a0)
 clr label
 tas d0
 chk (a0)+,d0
 neg.b $400
 reset
 exg d0,a0
 tst (a0)
 tst d0
 lea.l src,a0
 unlk a0
 lea $4000+42,a0
 pea.l 50000
 trap #1