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
macroName MACRO
 dc.b 42
 dcb.l 42,666
 data
 code
 opt w+,u+
label=42
 section tos,code,chip
 dc.l 1,2,3
label equ 42
 end
label equr a4
 erem
 ds 42
 plen 22
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
 align 0,4
label equr d6
 incdir "path"
 page
 inline
 data_f
 code_f
 llen 22
 endm
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
 incbin "path",offset,42
 rs.b 42
 xdef label
 xdef label,anotherLabel
 xref label
 xref label,anotherLabel
