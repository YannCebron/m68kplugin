---
title: Known Issues
---

# Known Issues
                                            
## Resolving

- Includes (resolves across all files)

## Broken Lexing/Parsing

- missing directives ([VASM](http://sun.hasenbraten.de/vasm/release/vasm_4.html#Mot-Syntax-Module)), these will
  currently display false error _"Cannot resolve macro [name]"_
  - `rorg`
  - `offset`
  - `public`
  - `nref`
  - `entry`
  - `extrn`
  - `global`
  - `import`
  - `export`
  - `weak`
  - `comm`
  - `load`
  - `mask2`
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
  - `rept`/`endr`
  - `ifmi`
  - `ifpl`
  - `so.*`, `clrso`/`setso`
  - `fo.*`, `clrfo`/`setfo`
  - `cargs`
  - `echo`
  - `printt`/`printv`
  - `auto`
  - `struct`/`estruct`
  - `reg`
- local label: ends with `$`
- label: allow `.`
- `include`/`incbin` path without quotes
- in expressions, identifier parsed as instruction (`size = (bpls*bpl)`)
- in directive, parsed as token, not identifier (`SECTION code,code`)
- add missing expression operators
  - XOR?
  - `!`
- do not allow spaces (?!)
- allow `equr`/`reg` replacement names everywhere `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`
- macros:
  - support `macro <macroName>` notation
  - label-counters (`\@`)
  - allow `a`-`z` for macro parameters
  - `ifmacrod`/`ifmacrond` code insight
- Internal tasks:
  - `LexerTestCase.checkCorrectRestartOnEveryToken`
