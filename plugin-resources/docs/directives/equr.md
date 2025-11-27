## Syntax
```assembly
<symbol> equr <Rn>
```

## Description
Define a new symbol named `<symbol>` and assign the data or address register `Rn`, 
which can be used from now on in operands. 

When 68080 code generation is enabled, also `Bn` base address registers and `En` vector registers
are allowed to be assigned. 

Note that a register symbol must be defined before it can be used! 