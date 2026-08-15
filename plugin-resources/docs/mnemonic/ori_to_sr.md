# ORI to SR - Inclusive OR immediate to status register

## Operation
IF [S] = 1<br/>
&nbsp;&nbsp;THEN<br/>
&nbsp;&nbsp;&nbsp;&nbsp;[SR] ← \<literal\> + [SR]<br/>
&nbsp;&nbsp;ELSE TRAP

## Description
OR the immediate data to the status register and store the result in the status register. All bits of the status register are affected.

## Application
Used to set bits in the SR (i.e., the S, T, and interrupt mask bits). For example, `ORI #$8000,SR` sets bit 15 of the SR (i.e., the trace bit).
