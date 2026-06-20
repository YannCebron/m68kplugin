# EORI to SR - Exclusive-OR immediate to status register

## Operation
IF [S] = 1<br/>
&nbsp;THEN<br/>
&nbsp;&nbsp;[SR] ← \<literal\> ⊕ [SR]<br/>
&nbsp;ELSE TRAP<br/>

## Description
EOR (exclusive OR) the immediate data with the contents of the status register and store the result in the status register. All bits of the status register are affected.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|

X:= toggled if bit 4 of data = 1; unchanged otherwise<br/>
N:= toggled if bit 3 of data = 1; unchanged otherwise<br/>
Z:= toggled if bit 2 of data = 1; unchanged otherwise<br/>
V:= toggled if bit 1 of data = 1; unchanged otherwise<br/>
C:= toggled if bit 0 of data = 1; unchanged otherwise<br/>
