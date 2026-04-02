# SUBA - Subtract address

## Operation
[destination] ← [destination] - [source]

## Syntax
```assembly
SUBA <ea>,An
```

## Description
Subtract the source operand from the destination operand and store the result in the destination address register. Word operations are sign-extended to 32 bits prior to subtraction.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|-|-|-|-|

*From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with permission.*