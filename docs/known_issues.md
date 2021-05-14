---
title: Known Issues
---

# Known Issues

## Resolving

- `include` directives not evaluated, resolving symbols across all project files
- macro block:
  - highlight `jsr _LVO\1(a6)` as invalid outside of macro
  - resolve global label inside current first

## Editor

- Code block handling of nested conditional assembly directives
- Missing directive inspection: nesting

## Lexing/Parsing

### Unsupported CPUs

Currently, only 68000 CPU is supported.

Non-supported instructions will display false positive
> _"Cannot resolve macro '$MNEMONIC$'"_

Non-supported registers will display false positive
> _"Cannot resolve label '$REGISTER$'"_

### Directives

Unsupported directives, these will display false positive
> _"Cannot resolve macro '$DIRECTIVE$'"_

[vasm Docs 1](http://sun.hasenbraten.de/vasm/release/vasm_4.html)
[vasm Docs 2](http://sun.hasenbraten.de/vasm/release/vasm_18.html)

- `.sdreg`
- `auto`
- `basereg`/`endb`
- `cargs` (Devpac)
- `comm`
- `comment`
- `common`
- `cpu32`
- `debug`
- `dr.*`
- `dsource`
- `dx.*`
- `echo` (PhxAss)
- `entry`, `extrn`, `global`, `export` == `xdef`
- `equrl`
- `far`
- `fequr`
- `fequrl`
- `fo.*`, `clrfo`/`setfo`
- `fpu`
- `freg`
- `idnt`
- `iif` (Devpac)
- `image`
- `import` == `xref`
- `initnear`
- `machine`
- `mask2` (no-op)
- `mc68XXX`/`ac68060`/`mcf5XXX`
- `module`
- `near code`
- `nref` (PhxAss)
- `offset` (Devpac)
- `optc`
- `output`
- `public`
- `reg`
- `rorg`
- `so.*`, `clrso`/`setso`
- `struct`/`estruct`
- `symdebug`
- `ttl`
- `weak`

#### Directives with known limitations

- `printt`: multiple strings

### Labels

- allow `.`
- allow `@` (Devpac)
- local label ending with `$`
- ending with double-colon `::` -> automatically exported (`xdef`)
- allow referencing `global_name\local_name` syntax (PhxAss)

### Builtin Symbols

- `*`
- `REPTN` (inside `rept`)
- `__LINE__`
- Macros
  - `CARG`
  - `NARG`
- Devpac
  - `__G2`
  - `__LK`
  - `__RS`, `__SO`, `__FO`
- PhxAss
  - `_PHXASS_`
  - `__CPU`, `__FPU`, `__MMU`
  - `__OPTC`
- vasm
  - `__VASM`

### Macros

- support `macro <macroName>` notation
- macro call with register list `myMacro d0/d7`
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
- valid label: `\1`
- support parameters `addq.\0 #1,\1` with `macroName.\0` call syntax
- allow `a`-`z` for macro parameters

### Misc

- add missing expression operators
  - XOR?
- do not allow spaces (?!)
- allow `equr`/`reg` etc. replacement names everywhere `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`

### Internal Notes

- `LexerTestCase.checkCorrectRestartOnEveryToken`
- FBI tracking macro/conditional assembly/target machine scopes