 blk 42
 spc 22
 rsset 0
 dcb 42
 mexit
 jumpptr label
 opt w+
label = 42
 cnop 0,4
 rem
 erem
 dc.b 42
 dcb.l 42,666
 data
 code
 opt w+,u+
label2=42 ;intentional duplicate
 section tos,code,chip
 dc.l 1,2,3
label equ 42
reg_label equr a4
 dr label,label2
 ds 42
 dx.l 42
 plen 22
 inline
 einline
 list
 even
 bss
 nolist
 jumperr label
 bss_f
 rs 42
 include "includeFile"
 addwatch label
 align 4
 incdir "path"
 page
 data_f
 code_f
 llen 22
 bss_c
 text
 nopage
 incbin "path"
 dseg
 blk 42,666
label set 42
 dc 42
 rsreset
 data_c
 code_c
 odd
 cseg
 org $50000
offset equ 22
 incbin "path",offset,42
 rs.b 42
 xdef label
 xdef label,anotherLabel
 xref label
 xref label,anotherLabel
 fail "FAIL MESSAGE"
 clrfo
 setfo 42

 clrso
 setso 44

 fpu 1
 output "fileName"

 msource on
 msource off

 offset 42
 offset

 mask2
 auto

 end
; comment
 auto
