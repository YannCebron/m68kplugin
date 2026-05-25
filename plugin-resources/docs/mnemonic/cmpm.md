# CMPM - Compare memory with memory

## Operation
[destination] - [source]

## Description
Subtract the source operand from the destination operand and set the condition codes accordingly. The destination is not modified by this instruction.

## Application
Used to compare the contents of two blocks of memory. For example:


```assembly
*   Compare two blocks of memory for equality
    LEA    Source,A0      ;A0 points to source block
    LEA    Destination,A1 ;A1 points to destination block
    MOVE.W #Count-1,D0    ;Compare Count words
RPT CMPM.W (A0)+,(A1)+    ;Compare pair of words
    DBNE   D0,RPT         ;Repeat until all done
    .
    .
```

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
|-|*|*|*|*|
