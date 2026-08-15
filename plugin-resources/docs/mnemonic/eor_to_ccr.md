# EOR to CCR - Exclusive-OR to CCR

## Operation
[CCR] ← [source] ⊕ [CCR]

## Description
EOR the source operand with the contents of the condition code register (i.e., the least-significant byte of the status register).

## Application
Used to toggle bits in the CCR. For example, `EOR #$0C,CCR` toggles the N- and Z-bits of the CCR.
