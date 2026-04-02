# CMPA - Compare address

## Operation
[destination] - [source]

## Description
Subtract the source operand from the destination address register and set the condition codes accordingly. The address register is not modified. The size of the operation may be specified as word or longword. Word length operands are sign-extended to 32 bits before the comparison is carried out.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|*|*|*|*|

*From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with permission.*