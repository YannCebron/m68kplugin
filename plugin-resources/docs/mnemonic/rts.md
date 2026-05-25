# RTS - Return from subroutine

## Operation
[PC] ← [M([SP])]; [SP] ← [SP] + 4

## Description
The program counter is pulled from the stack and the previous value of the PC is lost. `RTS` is used to terminate a subroutine.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|-|-|-|-|
