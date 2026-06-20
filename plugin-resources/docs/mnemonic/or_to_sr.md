# ORI to SR - Inclusive OR to status register

## Operation
IF [S] = 1<br/>
&nbsp;&nbsp;THEN<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[SR] ← [source] + [SR]<br/>
&nbsp;&nbsp;ELSE TRAP

## Description
OR the source operand to the status register and store the result in the status register. All bits of the status register are affected.

## Application
Used to set bits in the SR (i.e., the S, T, and interrupt mask bits). For example, `OR #$8000,SR` sets bit 15 of the SR (i.e., the trace bit).

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|

X is set if bit 4 of data = 1; unchanged otherwise<br/>
N is set if bit 3 of data = 1; unchanged otherwise<br/>
Z is set if bit 2 of data = 1; unchanged otherwise<br/>
V is set if bit 1 of data = 1; unchanged otherwise<br/>
C is set if bit 0 of data = 1; unchanged otherwise