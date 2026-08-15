# ORI to CCR - Inclusive OR to CCR

## Operation
[CCR] ← [source] + [CCR]

## Description
OR the source operand with the condition code register (i.e., the least-significant byte of the status register). For example, the Z flag of the CCR can be set by `OR #$04,CCR`.
