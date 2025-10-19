# NEAR

## Syntax
```assembly
near [<An>]
```

## Description
Enables small data (base-relative) mode and sets the base register to `An`.

`near` without an argument will reactivate a previously defined small data mode, 
which might have been switched off by a [far](far.md) directive. 