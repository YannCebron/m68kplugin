# EOR to SR - Exclusive-OR to status register

## Operation
IF [S] = 1<br/>
&nbsp;THEN<br/>
&nbsp;&nbsp;[SR] ← [source] ⊕ [SR]<br/>
&nbsp;ELSE TRAP<br/>

## Description
EOR (exclusive OR) the source operand with the contents of the status register and store the result in the status register. All bits of the status register are affected.
