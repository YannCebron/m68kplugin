label
 dc -1
 dc 10%3
 dc 1+2
 dc 1-2
 dc (1+2)
 dc 1|2
 dc ~1
 dc label
 dc @0123
 dc 1+(2+3)/-5
 dc (1)
 dc $1234abc
 dc +1
 dc 1>>2
 dc 1<<2
 dc %01010
 dc 1&2
 dc [1]
 dc 1^2
bpl
 dc 1234
trap
 dc 1*2/trap*bpl
 dc 'string'
 dc 1/2
 dc "string"
 dc *-42
; dc.b ((2/(1+2)*3)+label)/2 ; 'label' completion broken
 dc.b 2/1+2*3+label/2
