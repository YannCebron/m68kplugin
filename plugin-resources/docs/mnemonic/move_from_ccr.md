# MOVE from CCR - Copy data from CCR to destination

## Operation
[destination] ← [CCR] 

## Description
Move the contents of the condition code register to the destination location. The source operand is a *word*, but only the low-order *byte* contains the condition codes. The upper byte is neglected. 

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|