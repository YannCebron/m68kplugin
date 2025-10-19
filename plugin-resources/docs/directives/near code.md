# NEAR CODE

## Syntax
```assembly
near code
```

## Description
All `JMP` and `JSR` instructions to external labels will be converted into 16-bit `PC`-relative jumps. 
The small code mode can be switched off by a [far](far.md) directive. 