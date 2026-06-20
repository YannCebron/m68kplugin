# MOVE from SR - Copy data from SR to destination

## Operation
[destination] ← [SR]

## Description
Move the contents of the status register to the destination location. The source operand, the status register, is a word. This instruction is not privileged in the 68000, but is privileged in the 68010 or above. Executing a `MOVE SR,<ea>` while in the user mode on these processors results in a privilege violation trap.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|-|-|-|-|
