---
title: Known Issues
---

# Known Issues

## Resolving

- `include` directives not evaluated, resolving across all project files
- respects only first match for symbols defined more than once (e.g., inside conditional assembly directives)

## Editor

- Code block handling of nested conditional assembly directives

## Lexing/Parsing

### Unsupported CPUs

Currently, only 68000 instructions are supported. Others will display false positive
> _"Cannot resolve macro '$MNEMONIC$'"_

### Directives

Unsupported directives ([VASM](http://sun.hasenbraten.de/vasm/release/vasm_4.html#Mot-Syntax-Module)) will display
false positive
> _"Cannot resolve macro '$DIRECTIVE$'"_

- `rorg`
- `offset` (Devpac)
- `public`
- `nref` (PhxAss)
- `entry`, `extrn`, `global`, `export` == `xdef`
- `import` == `xref`
- `weak`
- `comm`
- `common`
- `load`
- `mask2` (no-op)
- `dx.*`
- `dr.*`
- `idnt`
- `ttl`
- `module`
- `output`
- `symdebug`
- `dsource`
- `debug`
- `comment`
- `image`
- `so.*`, `clrso`/`setso`
- `fo.*`, `clrfo`/`setfo`
- `cargs` (Devpac)
- `echo` (PhxAss)
- `printt`/`printv` (AsmOne)
- `auto`
- `struct`/`estruct`
- `reg`
- `optc`
- `near code`
- `iif` (Devpac)

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
  - `\@` unique ID
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
- allow `a`-`z` for macro parameters
- `ifmacrod`/`ifmacrond` code insight

### Misc

- `include`/`incbin` path without quotes
- add missing expression operators
  - XOR?
- do not allow spaces (?!)
- allow `equr`/`reg` replacement names everywhere `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`
- Internal tasks:
  - `LexerTestCase.checkCorrectRestartOnEveryToken`
