# ANDI - AND immediate

## Operation
[destination] ← \<literal\>.[destination]

## Description
*AND* the immediate data to the destination operand. The `ANDI` permits a literal operand to be ANDed with a destination other than a data register. For example, `ANDI #$FE00,$1234` or `ANDI.B #$F0,(A2)+`.

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|*|*|0|0|
