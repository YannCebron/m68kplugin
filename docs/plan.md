# Plans

> Suggest features missing in this list in [GitHub tracker](https://github.com/YannCebron/m68kplugin/issues)

## Instruction Sets

* 680x0 variants
* 68881 FPU

## Highlighting

### Directives

- `rem` sections: treat as real comment
- unpaired directives:
  - `struct`/`estruct`
- unpaired conditional assembly directives (nesting!), missing `endc`/`endif`
- highlight static conditional assembly directives status, e.g., `IFD`/`IFND`
- `section` directive: smarter highlighting/code insight
- `ifmacrod`/`ifmacrond` code insight

### Macros

- macro call: parameter count mismatch
- macro block:
  - usage of parameters outside of macro block
  - non-sequential parameter `\n` numbering
  - highlight parameters in labels `jsr _LVO\1(a6)`

### Other

- `ReadWriteAccessDetector`
  - registers
  - label references
- method separators:
  - "detected" subroutines
- highlight returns
- highlight/navigate control structures `CodeBlockSupportHandler`:
  - loop ranges
- rainbow highlighter: macros? labels?
- unreachable code `ControlFlowProvider`

## Resolve/Refactor

### Labels

- local label: in-place rename
- find usages
- highlight usages
- safe delete (quickfix via `M68kUnusedLabelInspection`)
- rename:
  - `RenameInputValidatorEx`
  - `NameSuggestionProvider` ?
- unresolved: quick-fix to add `include` for existing label

### Register

- shift up/down through available ones
- change/cycle through address mode by typing/intention/...?

### Extract/Duplicates

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
  - quick doc for comments preceding label
  - render mode for comments preceding label
- **WIP** code folding with settings for:
  - `movem` push/pop
- macro call: parameter info/preview referring place
- macro block: complete existing/n+1 `\n` everywhere
- `dc`:
  - join lines
  - smart enter handler to split value list
- surround descriptors?
- `FocusModeProvider`

## Inspections

- ability to suppress via comment
- nonsensical `move` (?!)
- `movem` non-symmetrical register ranges
- register list:
  - sort/optimize
- string in `dc.b` not terminated with `,0`
- check optimizations from vasm/DevPac
- multiple `opt` directives (?)

### Labels

- unused label: global - located in non-include files only?
- highlight duplicated names (conditional!)
- label naming conventions (`AbstractNamingConventionInspection`)

### Expression

- unnecessary parentheses
- type check
- range check
- simplify
- intention: convert numeric literal to other bases

## Navigation

- breadcrumbs
- quick definition: better range for labels/"code-blocks"
- structure view:
  - `section`
  - macro: # of parameters

### Include

- goto related: `.i`/binary file -> including files
- gutter icon: `include` all included files (recursively)
- UML visualization
- highlight cycles

## IDE

- support copyright plugin
- symbol names in commit dialog (`PlainTextSymbolCompletionContributor`)

## Tools

- show cycles/size in editor https://68kcounter-web.vercel.app/

## Amiga

- copper list:
  - color-picker/inlays
  - register name inlay
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