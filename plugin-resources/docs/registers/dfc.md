# Alternate Function Code Registers (`SFC` and `DFC`)

The alternate function code registers contain 3-bit function codes. Function codes can be
considered extensions of the 32-bit logical address that optionally provides as many as eight
4-Gbyte address spaces. The processor automatically generates function codes to select
address spaces for data and programs at the user and supervisor modes. Certain
instructions use SFC and DFC to specify the function codes for operations.

*From MOTOROLA M68000 FAMILY Programmer's reference manual. Copyright 1992 by Motorola Inc./NXP. Adapted with permission.*