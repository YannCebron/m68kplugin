# MOVE to CCR - Copy data to CCR from source

## Operation
[CCR] ← [source]

## Description
Move the contents of the source operand to the condition code register. The source operand is a *word*, but only the low-order *byte* contains the condition codes. The upper byte is neglected. Note that `MOVE <ea>,CCR` is a word operation, but `ANDI`, `ORI`, and `EORI` to `CCR` are all byte operations.

## Application
The move to CCR instruction permits the programmer to preset the CCR. For example, `MOVE #0,CCR` clears all the CCR's bits.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|