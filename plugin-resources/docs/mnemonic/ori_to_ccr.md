# ORI to CCR - Inclusive OR immediate to CCR

## Operation
[CCR] ← \<literal\> + [CCR]

## Description
OR the immediate data with the condition code register (i.e., the least-significant byte of the status register). For example, the Z flag of the CCR can be set by `ORI #$04,CCR`.
