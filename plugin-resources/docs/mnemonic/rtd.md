# RTD - Return and Deallocate

## Operation
(SP) → PC; SP + 4 + dn → SP

## Description
Pulls the program counter value from the stack and adds the sign-extended 16-bit displacement value to the stack pointer. The previous program counter value is lost.

## Condition codes
Not affected.
