# MOVE USP - Copy data from USP

## Operation
IF [S] = 1<br/>
&nbsp;&nbsp;THEN [An] ← [USP]<br/>
ELSE TRAP

## Description
Move the contents of the user stack pointer to an address register. This is a privileged instruction and allows the operating system running in the supervisor state either to read the contents of the user stack pointer or to set up the user stack pointer.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|-|-|-|-|