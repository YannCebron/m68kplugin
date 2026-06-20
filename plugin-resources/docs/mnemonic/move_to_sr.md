# MOVE to SR - Copy data to SR from source

## Operation
IF [S] = 1<br/>
&nbsp;&nbsp;THEN [SR] ← [source]<br/>
ELSE TRAP

## Description
Move the contents of the source operand to the status register. The source operand is a word and all bits of the status register are affected.

## Application
The `MOVE to SR` instruction allows the programmer to preset the contents of the status register. This instruction permits the trace mode, interrupt mask, and status bits to be modified. For example, `MOVE #$2700,SR` moves 00100111 00000000 to the status register which clears all bits of the CCR, sets the S-bit, clears the T-bit, and sets the interrupt mask level to 7.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|
