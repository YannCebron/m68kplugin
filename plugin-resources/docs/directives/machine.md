## Syntax
```
machine [mc|ac]<cpu_type>
```

## Description
Sets the target CPU for code generation. The `<cpu_type>` can be one of the following:
`68000`, `68010`, `68020`, `68030`, `68040`, `68060`, `68080`, `68851`, `68881`, `68882`, `cpu32`.

The optional prefix `mc` is accepted for Devpac compatibility. The prefix `ac` is used for Apollo CPUs (e.g., `ac68080`).

Examples:
* `machine 68020` — Generate code for the MC68020.
* `machine mc68020` — Equivalent to `machine 68020`.
* `machine ac68080` — Generate code for the Apollo 68080.

See [mc68000](mc68000.md), [mc68010](mc68010.md), [mc68020](mc68020.md),
[mc68030](mc68030.md), [mc68040](mc68040.md), [mc68060](mc68060.md), [ac68080](ac68080.md),
[cpu32](cpu32.md) and [fpu](fpu.md) directive.