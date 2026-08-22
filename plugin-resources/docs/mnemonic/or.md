# OR - OR logical

## Operation
[destination] ← [source] + [destination]

## Description
OR the source operand to the destination operand, and store the result in the destination location.

## Application
The `OR` instruction is used to set selected bits of the operand. For example, we can set the four most-significant bits of a longword operand in D0 by executing:

```assembly
* OR sample
  OR.L #$F0000000,D0
```
