## Syntax
```
odd
```

## Description
Aligns to an odd address. Adds 1 byte when the address is even, and 0 bytes when the address is already odd.
Unlike `cnop 1,2`, which adds 2 bytes at an odd address, `odd` adds 0 bytes.
See [cnop](cnop.md) directive.
