# Vector Base Register (`VBR`)

The VBR contains the base address of the exception vector table in memory. The
displacement of an exception vector adds to the value in this register, which accesses the
vector table.

*From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with
permission.*