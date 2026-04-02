# SUBI - Subtract immediate

## Operation
[destination] ← [destination] - \<literal\>

## Syntax
```assembly
SUBI #<data>,<ea>
```

## Description
Subtract the immediate data from the destination operand. Store the result in the destination operand.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|

*From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with permission.*