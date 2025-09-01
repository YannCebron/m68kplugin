# Status Register (`SR`)

The SR stores the processor status and contains the condition
codes that reflect the results of a previous operation.

In the supervisor mode, software can access the full SR, including the interrupt priority mask and additional control
bits. These bits indicate the following states for the processor: one of two trace modes (T1, T0), supervisor
or user mode (S), and master or interrupt mode (M).

For the MC68000, MC68EC000, MC68008, MC68010, MC68HC000, MC68HC001, and CPU32, only one trace mode is
supported, where T0 is always zero, and only one system stack where the M-bit is always
zero. I2, I1, and I0 define the interrupt mask level.

## Status Register Bits

_15-8 SYSTEM BYTE_ / _7-0 USER BYTE ([CCR](ccr.md))_

| 15 | 14 | 13 | 12 | 11 | 10 | 9  | 8  |   | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
|:--:|:--:|:--:|:--:|:--:|:--:|:---|:--:|---|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| T1 | T0 | S  | M  | 0  | I2 | I1 | I0 |   | 0 | 0 | 0 | X | N | Z | V | C |

#### TRACE MODE: `T1`/`T0`

| T1 | T0 | Trace Mode               |
|:--:|:--:|--------------------------|
| 0  | 0  | NO TRACE                 |
| 1  | 0  | TRACE ON ANY INSTRUCTION |
| 0  | 1  | TRACE ON CHANGE OF FLOW  |
| 1  | 1  | UNDEFINED                |

#### MASTER/INTERRUPT STATE: `S`/`M`

| S | M | Active Stack |
|:-:|:-:|--------------|
| 0 | x | USP          |
| 1 | 0 | ISP          |
| 1 | 1 | MSP          |

#### INTERRUPT PRIORITY MASK: `I2`/`I1`/`I0`

*From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with
permission.*