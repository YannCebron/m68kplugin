# SUBX - Subtract extended

## Operation
[destination] ← [destination] - [source] - [X]

## Description
Subtract the source operand from the destination operand along with the extend bit, and store the result in the destination location.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|

Z: Cleared if the result is non-zero, unchanged otherwise. The Z-bit can be used to test for zero after a chain of multiple precision operations.
