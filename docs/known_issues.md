---
title: Known Issues
nav_order: 4
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

Currently, only MC68000/MC68010 and CPU32 architecture is supported.
For select mnemonics, some MC68020+ only variants are supported.

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
- `cargs` (Devpac, PhxAss)
- `comm`
- `comment` (Devpac, PhxAss)
- `common`
- `db`/`dw`/`dl` == `dc.b|w|l`
- `debug` (PhxAss)
- `dsource` (PhxAss)
- `entry`, `extrn`, `global`, `export` == `xdef`
- `elif`
- `equrl`
- `fequ`
- `fequr`
- `fequrl`
- `freg`
- `iif` (Devpac)
- `image`
- `import` == `xref`
- `local`
- `mcf5XXX`
- `module` (Devpac, PhxAss)
- `nref` (PhxAss)
- `optc` (PhxAss)
- `public` (PhxAss)
- `rorg` (Devpac, PhxAss)
- `showoffset` (PhxAss)
- `struct`/`estruct`
- `symdebug` (PhxAss)
- `vdebug`
- `weak`

#### Directives with known limitations

- `echo`: vasm supports both expressions and strings; the plugin parses only strings
- `include`: vasm supports angle-bracket paths (`<file.i>`); the plugin parses only quoted strings
- `incdir`: vasm supports angle-bracket paths and multiple comma-separated paths; the plugin parses only one quoted string
- `org`: vasm supports `org * + expr` to reserve bytes; the plugin parses only `org expression`
- `printt`: vasm supports multiple comma-separated strings; the plugin parses only one string
- `rept`: vasm supports an optional iterator name as second parameter; the plugin parses only the count
- `xref`: vasm supports `xref.l` variant for long references; the plugin does not parse the `.l` suffix

##### Floating-point suffixes

vasm supports floating-point suffixes `.s`, `.d`, `.x`, and `.p`; the plugin does not parse floating-point suffixes.

This affects data definition and offset directives that accept floating-point values. Code using these suffixes will show syntax errors.

- `blk`
- `dc`
- `dcb`
- `ds`
- `dx`
- `fo`
- `rs`
- `so`

### Labels

- single-digit local label `1$`
- `-localu` option: allow local labels starting with `_` instead of `.` (Devpac)
- ending with double-colon `::` automatically exported (`xdef`)
- referencing `global_name\local_name` syntax (PhxAss)

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
- add an option to allow whitespaces in instruction arguments ([Issue](https://github.com/YannCebron/m68kplugin/issues/47))
- support `equr`/`reg` replacement names: `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`
