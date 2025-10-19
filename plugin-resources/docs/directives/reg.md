# REG

## Syntax
```assembly
<symbol> reg <reglist>
```

## Description
Defines a new symbol named `<symbol>` and assign the register list `<reglist>` to it. 
Registers in a list must be separated by a slash (`/`) and ranges or registers can be defined 
by using a hyphen (`-`). 
No character at all represents an empty register list. 

Optionally you may specify the `<reglist>` as a 16-bit register mask constant 
(`d0` = bit `0`, `a7` = bit `15`). 

Examples for valid register lists are: 
- `d0-d7/a0-a6`
- `d3-6/a0/a1/a4-5`