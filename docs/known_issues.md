---
title: Known Issues
---

# Known Issues

## Resolving

- `include` directives not evaluated, resolving across all project files

## Broken Lexing/Parsing

- missing directives ([VASM](http://sun.hasenbraten.de/vasm/release/vasm_4.html#Mot-Syntax-Module)), these will
  currently display false error _"Cannot resolve macro $DIRECTIVE$"_
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
  - `fail`
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
- labels:
  - allow `.`
  - allow `@` (Devpac)
  - local label ending with `$`
  - local label in `=`/`equ`/`equr` directive  
  - ending with double-colon `::` -> automatically exported (`xdef`)
  - allow referencing `global_name\local_name` syntax (PhxAss)
- `include`/`incbin` path without quotes
- in expressions, identifier parsed as token (`size = (bpls*bpl)`)
- in directive, identifier parsed as token (`SECTION code,code`)
- add missing expression operators
  - XOR?
- do not allow spaces (?!)
- allow `equr`/`reg` replacement names everywhere `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`
- macros:
  - support `macro <macroName>` notation
  - macro call with register list `myMacro d0/d7`  
  - special symbols:
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
- global builtin symbols
  - `*`
  - `REPTN`
  - `__LINE__`
  - Macros
    - `CARG`
    - `NARG`
  - Devpac:
    - `__G2`
    - `__LK`
    - `__RS`, `__SO`, `__FO`
  - PhxAss
    - `_PHXASS_`
    - `__CPU`, `__FPU`, `__MMU`
    - `__OPTC`
  - vasm
    - `__VASM`
- Internal tasks:
  - `LexerTestCase.checkCorrectRestartOnEveryToken`
