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
  - navigation to symbol declared multiple times (possibly in multiple files) will show popup chooser
  - all included files must be located inside project (or added as separate content root)
- conditional assembly directives are not evaluated
- macro block:
  - highlight `jsr _LVO\1(a6)` as invalid outside of macro
  - resolve global label inside current first

## Editor

- Code block handling of nested conditional assembly directives

## Lexing/Parsing

### Unsupported CPUs

Currently, only 68000 architecture is supported.

Non-supported instructions/registers will display false positive
> _"Cannot resolve macro '$MNEMONIC$ \| $REGISTER$'"_

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
- `dsource`
- `dx.*`
- `entry`, `extrn`, `global`, `export` == `xdef`
- `equrl`
- `fequr`
- `fequrl`
- `fo.*`, `clrfo`/`setfo`
- `fpu`
- `freg`
- `iif` (Devpac)
- `image`
- `import` == `xref`
- `mask2` (no-op)
- `mcf5XXX`
- `module`
- `nref` (PhxAss)
- `offset` (Devpac)
- `optc`
- `output`
- `public`
- `rorg`
- `so.*`, `clrso`/`setso`
- `struct`/`estruct`
- `symdebug`
- `weak`

MadMac assembler specific (all directives may be optionally preceded by a dot):

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
- ending with double-colon `::` &rarr; automatically exported (`xdef`)
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
  - `__UNIXFS`, `__MSDOSFS`, `__AMIGAFS`

### Macros

- macro declaration variants:
  - `macro $macroName$`
  - `macro<$macroName$>`
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
- valid label
  - `\1`
  - `\1\3\2 equ \4`
- support parameters `addq.\0 #1,\1` with `macroName.\0` call syntax
- allow `a`-`z` for macro parameters

### Misc

- EOL comment: do not include preceding WS in PSI
- `'\'` and `''''` are valid string literals
- do not allow spaces (?!)
- support `equr`/`reg` etc. replacement names `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`

### Internal Notes

- `LexerTestCase.checkCorrectRestartOnEveryToken`
- FBI tracking macro/conditional assembly/target architecture scopes