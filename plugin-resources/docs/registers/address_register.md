# Address registers (`A0`-`A7`)

These registers can be used as software stack pointers, index registers, or base address
registers. The base address registers can be used for word and long-word operations.
Register A7 is used as a hardware stack pointer during stacking for subroutine calls and
exception handling. 

## Address Register `A7` = `ISP`/`MSP`/`SP`/`SSP`/`USP`

In the user programming model, A7 refers to the user stack pointer
(USP).

In the supervisor programming model register, A7 refers to the interrupt stack pointer,
A7’(ISP) and the master stack pointer, A7" (MSP). The supervisor stack pointer is the active
stack pointer (ISP or MSP). For processors that do not support ISP or MSP, the system stack
is the system stack pointer (SSP). The ISP and MSP are general-purpose address registers
for the supervisor mode. They can be used as software stack pointers, index registers, or
base address registers. The ISP and MSP can be used for word and long-word operations.

*From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with permission.*