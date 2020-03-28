## Known issues
- brackets == parentheses?
- add missing instructions (??)
- research available directives, case-sensitivity (DevPac: no)?
- add missing expression operators (XOR?)
- macros (WIP)
- I18n

### Broken lexing/parsing
- `.b|w|l` must be immediate after instruction (Parser prob?)

## Before 1st release
- file icons
- plugin description
- `ErrorReportSubmitter`
- README.md
- CHANGELOG.md

## Highlighting
- `macro` missing `endm`
- privileged instructions
- registers:
  - dedicated color for each (**WIP** at least special registers (`SP` == `A7`!) vs. A*n* vs. D*n*)
  - rainbow highlighting?
- display used vs. free registers in selected code block
- `ReadWriteAccessDetector` for registers
- method separators
  - "detected" subroutines
  - macro definition
- color-picker/inlays: copper list colors

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

## Editing
- `EnterHandlerDelegate`: smart indent? and/or `LineIndentProvider`
- expand/shrink selection: 
  - full line
  - "blocks"
  - immediate data preceding `#`
  - `offsetExpression(An)`
- move left/right: swap src|dest
- templates, `TemplateContextType`
- spellchecker, bundled dictionary
- highlight returns
- formatter
- documentation:
  - quick doc/hover for number literals: dec/hex/oct/bin
  - quick doc/hover for `equ`/`equr`/`=` directives

## Inspections
- nonsensical `move`, e.g. src==dest
- non-symmetrical `movem`
- sort/optimize register list
- string in `dc.b` not terminated with `,0`
- check optimizations from vasm/DevPac
- multiple `opt` directives (?)
- macro label naming convention (all uppercase)

## Navigation
- goto symbol for labels/macros/EQUs (?) --> Stubs
- **WIP** structure view 
  - `equr` "location"
  - macros
  - nest local labels
  - include/incbin (?)
- breadcrumbs

## IDE
- support copyright plugin
- symbol names in commit dialog (`PlainTextSymbolCompletionContributor`)

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