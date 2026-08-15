# ANDI to CCR - AND immediate to condition code register

## Operation
[CCR] ← \<data\>.[CCR]

## Description
*AND* the immediate data to the condition code register (i.e., the least-significant byte of the status register).

## Application
`ANDI` is used to clear selected bits of the `CCR`. For example, `ANDI #$FA,CCR` clears the Z- and C-bits, i.e., XNZVC = X N 0 V 0.
