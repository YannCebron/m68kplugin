# INITNEAR

## Syntax
```assembly
initnear
```

## Description
Initializes the selected small data base register. 

In contrast to PhxAss, where this directive comes from, just a reference to `_LinkerDB` is generated, 
which has to be resolved by a linker: `lea _LinkerDB,An`