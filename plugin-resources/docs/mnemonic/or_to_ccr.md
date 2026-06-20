# ORI to CCR - Inclusive OR to CCR

## Operation
[CCR] ← [source] + [CCR]

## Description
OR the source operand with the condition code register (i.e., the least-significant byte of the status register). For example, the Z flag of the CCR can be set by `OR #$04,CCR`.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|

X is set if bit 4 of data = 1; unchanged otherwise</br>
N is set if bit 3 of data = 1; unchanged otherwise</br>
Z is set if bit 2 of data = 1; unchanged otherwise</br>
V is set if bit 1 of data = 1; unchanged otherwise</br>
C is set if bit 0 of data = 1; unchanged otherwise</br>
