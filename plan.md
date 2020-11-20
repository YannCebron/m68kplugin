# Known Issues & Plans

## Broken Lexing/Parsing

- missing directives ([VASM](http://sun.hasenbraten.de/vasm/release/vasm_4.html#Mot-Syntax-Module)), these will
  currently display false error _"Cannot resolve macro [name]"_
  - `rorg`
  - `offset`
  - `public`
  - `xdef`
  - `xref`
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
- local label: ends with `$`
- label: allow `.`
- `include`/`incbin` path without quotes
- in expressions, mnemonic parsed as instruction, not identifier (`size = (bpls*bpl)`)
- in directive, parsed as token, not identifier (`SECTION code,code`)
- add missing expression operators
  - XOR?
  - `!`
- do not allow spaces (?!)
- allow `equr`/`reg` replacement names everywhere `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`
- macros:
  - resolve in other files
  - label-counters (`\@`)
  - allow `a`-`z` for macro parameters
  - `ifmacrod`/`ifmacrond` code insight
- Internal tasks:
  - `LexerTestCase.checkCorrectRestartOnEveryToken`

## Before 1st Release

- file icons (source/include)
- plugin description

## Instruction Sets

* 680x0 variants
* 68881 FPU

## Highlighting

- `rem` sections: treat as real comment
- unpaired directives:
  - `rept`/`endr`
  - `struct`/`estruct`
- unpaired conditional assembly directives, missing `endc`/`endif`
- highlight static conditional assembly directives status, e.g., `IFD`/`IFND`
- macro call: parameter count mismatch
- registers:
  - dedicated color for each (**WIP** at least special registers (`SP` == `A7`!) vs. A*n* vs. D*n*)
  - rainbow highlighting?
- display used vs. free registers in selected code block
- `ReadWriteAccessDetector` for registers
- method separators:
  - "detected" subroutines
- highlight unused label (local only? located in non-include files only?)
- color-picker/inlays: copper list colors
- highlight returns

## Resolve/Refactor

- labels:
  - resolve references
  - local label: in-place rename
  - find usages
  - highlight usages
  - eventually `UsageTargetProvider`
  - safe delete
  - unresolved: quick-fix add `include` to existing label
- register:
  - shift up/down through available ones
  - change/cycle through address mode by typing/intention/...?
- `refactoring.extractIncludeHandler`
- duplicates detection:
  - textual duplicate -> wrap in `rept`/`endr`
  - "extract macro"

## Editing

- `EnterHandlerDelegate` smart indent? or `LineIndentProvider`
- expand/shrink selection:
  - **WIP** "blocks"
  - macro/conditional assembly blocks
- `M68kCommaFlipper`: swap src|dest if valid
- formatter
- documentation:
  - quick doc/hover for number literals: dec/hex/oct/bin/ASCII
  - quick doc/hover for `equ`/`equr`/`=` directives
- **WIP** code folding with settings for:
  - `movem` push/pop
- macro call: parameter info/preview referring place
- `dc`:
  - join lines
  - smart enter handler to split value list
- Surround Descriptors?
- `FocusModeProvider`

## Inspections

- nonsensical `move`, e.g. src==dest
- `movem` non-symmetrical register ranges
- register list:
  - sort/optimize
- string in `dc.b` not terminated with `,0`
- check optimizations from vasm/DevPac
- multiple `opt` directives (?)
- label naming conventions
- cyclic `include`
- expression:
  - type check
  - range check
  - simplify
  - convert numeric literal to other bases

## Navigation

- **WIP** goto symbol for labels/macros/EQUs (?) --> Stubs
- **WIP** structure view
  - `equr` "location"
  - macros
- breadcrumbs
- Quick Definition: better range for labels like `macro` etc. `ImplementationTextSelectioner`
- Conditional Assembly structures: `CodeBlockSupportHandler`
- Goto Related: `.i`/binary file -> including files
- Gutter Icon: `include` all included files (recursively)
- `include` UML visualization

## IDE

- support copyright plugin
- symbol names in commit dialog (`PlainTextSymbolCompletionContributor`)

## External Tools

## Tools

- http://deadliners.net/gradientmaster/

## Resources

- https://www.nxp.com/files-static/archives/doc/ref_manual/M68000PRM.pdf
- http://mrjester.hapisan.com/04_MC68/Index.html
- https://github.com/alpine9000/amiga_examples
- https://amigasourcepres.gitlab.io/page/books/assembler/
- https://amigasourcecodepreservation.gitlab.io/mc680x0-reference/
- https://amigasourcecodepreservation.gitlab.io/total-amiga-assembler/
- http://eab.abime.net/showthread.php?t=21516
- http://tigcc.ticalc.org/doc/a68k.html
- http://obligement.free.fr/programmation.php