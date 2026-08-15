# EORI to CCR - Exclusive-OR immediate to CCR

## Operation
[CCR] ← \<literal\> ⊕ [CCR]

## Description
EOR the immediate data with the contents of the condition code register (i.e., the least-significant byte of the status register).

## Application
Used to toggle bits in the CCR. For example, `EORI #$0C,CCR` toggles the N- and Z-bits of the CCR.
