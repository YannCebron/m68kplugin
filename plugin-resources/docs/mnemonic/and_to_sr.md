# AND to SR - AND to status register

## Operation
```
IF [S] = 1
  THEN
    [SR] ← [source].[SR]
  ELSE TRAP
```

## Description
*AND* the source operand to the status register and store the result in the status register. All bits of the SR are affected.

## Application
This instruction is used to clear the interrupt mask, the S-bit, and the T-bit of the *SR*. `AND #<data>,SR` affects both the status byte of the *SR* and the *CCR*. For example, `AND #$7FFF,SR` clears the trace bit of the status register, while `AND #$7FFE,SR` clears the trace bit and also clears the carry bit of the *CCR*.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|*|*|*|*|*|
