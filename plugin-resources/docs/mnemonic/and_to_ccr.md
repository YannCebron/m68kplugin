# AND to CCR - AND to condition code register

## Operation
[CCR] ← [source].[CCR]

## Description
*AND* the source operand to the condition code register (i.e., the least-significant byte of the status register).

## Application
`AND` is used to clear selected bits of the `CCR`. For example, `AND #$FA,CCR` clears the Z- and C-bits, i.e., XNZVC = X N 0 V 0.
