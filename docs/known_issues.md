---
title: Known Issues
---

# Known Issues
{: .no_toc }

1. TOC
{:toc}

## Resolving

- `include` directives:
  - not evaluated, symbols are resolved across all files
  - navigation to a symbol declared multiple times (possibly in multiple files) will show a popup chooser
  - all included files must be located inside the project (or added as separate content root)
- conditional assembly directives are not evaluated
- macro block:
  - highlight `jsr _LVO\1(a6)` as invalid outside of macro
  - resolve global label inside current first

## Editor

- Code block handling of nested conditional assembly directives

## Lexing/Parsing

The plugin supports only [vasm](http://sun.hasenbraten.de/vasm/)-compatible syntax.
Support for other assemblers is not a priority in the nearest future.

### Unsupported CPUs

Currently, only 68000/68010 architecture is supported.

Non-supported instructions/registers will yield false positive errors
> _"Cannot resolve macro '$MNEMONIC$ \| $REGISTER$'"_

Using a non-supported address mode will result in a syntax error or
> _"<$ADDRESS_MODE$> expected, got $TEXT$"_

### Directives

[vasm Docs 4.4](http://sun.hasenbraten.de/vasm/release/vasm_4.html)
[vasm Docs 23.5](http://sun.hasenbraten.de/vasm/release/vasm_23.html)

Unsupported directives, these will display as a false positive
> _"Cannot resolve macro '$DIRECTIVE$'"_


- `.sdreg`
- `cargs` (Devpac)
- `comm`
- `comment`
- `common`
- `db`/`dw`/`dl` == `dc.b|w|l`
- `debug`
- `dsource`
- `entry`, `extrn`, `global`, `export` == `xdef`
- `elif`
- `equrl`
- `fequr`
- `fequrl`
- `freg`
- `iif` (Devpac)
- `image`
- `import` == `xref`
- `mcf5XXX`
- `module`
- `nref` (PhxAss)
- `optc`
- `public`
- `rorg`
- `rseven`
- `showoffset` (PhxAss)
- `struct`/`estruct`
- `symdebug`
- `weak`

--- 
MadMac assembler specific ([vasm Docs](http://sun.hasenbraten.de/vasm/release/vasm_5.html)) (a dot may optionally precede all directives):

- `abs`
- `assert`
- `dphrase`
- `globl`
- `long`
- `macundef`
- `nlist`
- `offset`
- `phrase`
- `qphrase`

#### Directives with known limitations

- `printt`: multiple strings

### Labels

- single-digit local label `1$`
- allow `@` (Devpac)
- `-ldots` option: allow dots within identifiers
- `-localu` option: allow local labels starting with `_` instead of `.` (Devpac)
- ending with double-colon `::` &rarr; automatically exported (`xdef`)
- allow referencing `global_name\local_name` syntax (PhxAss)

### Builtin Symbols

- `REPTN`: only valid inside `rept`
- `CARG`, `NARG`: only valid inside `macro`

### Macros

- macro declaration variants:
  - `macro<$macroName$>` (not supported by vasm)
- special symbols
  - `\@!`
  - `\@?`
  - `\@@`
  - `\#`
  - `\?n`
  - `\.`
  - `\+`
  - `\-`
  - `\<symbolname>`
  - `\@<symbolname>`
- valid label
  - `\1`
  - `\1\3\2 equ \4`
  - `\+`
- highlight illegal macro parameter names: `\X`, `\123`

### Misc

- string literals:
  - `'\'` and `''''` are valid
- add option to allow whitespaces in instruction arguments ([Issue](https://github.com/YannCebron/m68kplugin/issues/47))
- support `equr`/`reg` replacement names: `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`
