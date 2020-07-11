# Known issues & Plans

## Broken lexing/parsing
- research available directives, case-sensitivity (DevPac: no)?
  - `align`
  - `fail`
  - `endif`
- add missing expression operators (XOR?)
- do not allow spaces, e.g. `.b|w|l` must be immediate after instruction
- allow `equr` replacement names everywhere `jsr _LVO_Something(MY_A7_CUSTOM_NAME)`
- macro: parameters, calls

## Before 1st release
- file icons
- plugin description
- `ErrorReportSubmitter`
- README.md

## Instruction Sets
* 680x0 variants
* 68881 FPU

## Highlighting
- `macro` missing `endm`
- conditional assembly missing `endc`
- registers:
  - dedicated color for each (**WIP** at least special registers (`SP` == `A7`!) vs. A*n* vs. D*n*)
  - rainbow highlighting?
- display used vs. free registers in selected code block
- `ReadWriteAccessDetector` for registers
- method separators
  - "detected" subroutines
- color-picker/inlays: copper list colors
- anything after `end` directive
- highlight returns

## Resolve/Refactor
- rename/resolve to label
  - resolve references
  - local label: in-place rename 
  - highlight usages
  - eventually `UsageTargetProvider`
- highlight unused label/EQUs(?)
- safe delete label/EQUs
- `NamesValidator`
- register
  - shift up/down through available ones
  - change/cycle through address mode by typing/intention/...?
  - `a7` <-> `sp`
- `refactoring.extractIncludeHandler`

## Editing
- `EnterHandlerDelegate`: smart indent? and/or `LineIndentProvider`
- expand/shrink selection: 
  - full line
  - "blocks"
- move left/right: swap src|dest
- formatter
- documentation:
  - quick doc/hover for number literals: dec/hex/oct/bin
  - quick doc/hover for `equ`/`equr`/`=` directives

## Inspections
- nonsensical `move`, e.g. src==dest
- `movem` 
  - non-symmetrical
  - duplicated registers (`d3/d0-d4` -> `d3`)
  - mixed up registers (`d0-a2`) 
- sort/optimize register list
- string in `dc.b` not terminated with `,0`
- check optimizations from vasm/DevPac
- multiple `opt` directives (?)
- label naming conventions

## Navigation
- **WIP** goto symbol for labels/macros/EQUs (?) --> Stubs
- **WIP** structure view 
  - `equr` "location"
  - macros
  - nest local labels
- breadcrumbs
- Goto Related: `.i`/binary file -> including files
- Gutter Icon: `include` all included files (recursively)

## IDE
- support copyright plugin
- symbol names in commit dialog (`PlainTextSymbolCompletionContributor`)

## External Tools
- make errors navigatable (`ConsoleFilterProvider`)

## Tools
- http://deadliners.net/gradientmaster/

## Resources
- http://mrjester.hapisan.com/04_MC68/Index.html
- https://github.com/alpine9000/amiga_examples
- https://amigasourcepres.gitlab.io/page/books/assembler/
- https://amigasourcecodepreservation.gitlab.io/mc680x0-reference/
- https://amigasourcecodepreservation.gitlab.io/total-amiga-assembler/
- http://eab.abime.net/showthread.php?t=21516
- http://tigcc.ticalc.org/doc/a68k.html
- http://obligement.free.fr/programmation.php