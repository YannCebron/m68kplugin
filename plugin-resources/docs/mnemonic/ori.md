# ORI - OR immediate

## Operation
[destination] ← \<literal\> + [destination]

## Description
OR the immediate data with the destination operand. Store the result in the destination operand.

## Application
`ORI` forms the logical OR of the immediate source with the effective address, which may be a memory location. For example,

```assembly
     ORI.B #%00000011,(A0)+
```
