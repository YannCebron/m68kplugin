# Plans

## Instruction Sets

* 680x0 variants
* 68881 FPU

## Highlighting

- `rem` sections: treat as real comment
- unpaired directives:
  - `struct`/`estruct`
- unpaired conditional assembly directives (nesting!), missing `endc`/`endif`
- highlight static conditional assembly directives status, e.g., `IFD`/`IFND`
- macro call: parameter count mismatch
- `ReadWriteAccessDetector` for registers/label references
- method separators:
  - "detected" subroutines
- color-picker/inlays: copper list colors
- highlight returns
- highlight/navigate control structures `CodeBlockSupportHandler`:
  - loop ranges
- unreachable code `ControlFlowProvider`
- `section` directive: smarter highlighting/codeinsight

## Resolve/Refactor

- labels:
  - local label: in-place rename
  - find usages
  - highlight usages
  - safe delete
  - rename:
    - `RenameInputValidatorEx`
    - `NameSuggestionProvider` ?
  - unresolved: quick-fix to add `include` for existing label
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
  - **WIP** "blocks" - stop at branch instructions
  - macro/conditional assembly blocks
- `M68kCommaFlipper`: swap src\|dest if valid
- formatter
- documentation:
  - quick doc/hover for number literals: dec/hex/oct/bin/ASCII
  - render mode for comments before label
- **WIP** code folding with settings for:
  - `movem` push/pop
- macro call: parameter info/preview referring place
- `dc`:
  - join lines
  - smart enter handler to split value list
- Surround Descriptors?
- `FocusModeProvider`

## Inspections

- nonsensical `move` (?!)
- `movem` non-symmetrical register ranges
- unused label: global - located in non-include files only?
- register list:
  - sort/optimize
- string in `dc.b` not terminated with `,0`
- check optimizations from vasm/DevPac
- multiple `opt` directives (?)
- label naming conventions (`AbstractNamingConventionInspection`)
- cyclic `include`
- expression:
  - type check
  - range check
  - simplify
  - convert numeric literal to other bases

## Navigation

- breadcrumbs
- Quick Definition: better range for labels/"code-blocks"
- Goto Related: `.i`/binary file -> including files
- Gutter Icon: `include` all included files (recursively)
- `include` UML visualization

## IDE

- support copyright plugin
- symbol names in commit dialog (`PlainTextSymbolCompletionContributor`)

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