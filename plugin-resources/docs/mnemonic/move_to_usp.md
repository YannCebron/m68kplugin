# MOVE USP - Copy data to USP

## Operation
IF [S] = 1<br/>
&nbsp;&nbsp;THEN [USP] ← [An]<br/>
ELSE TRAP

## Description
Move the contents of an address register to the user stack pointer. This is a privileged instruction and allows the operating system running in the supervisor state either to read the contents of the user stack pointer or to set up the user stack pointer.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|-|-|-|-|