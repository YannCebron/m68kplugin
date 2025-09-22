# Plans
{: .no_toc }

1. TOC
{:toc}

> 💡 Please see [Contributing](https://github.com/YannCebron/m68kplugin/blob/main/CONTRIBUTING.md) on how to submit feature ideas and contribute to this project.

## Target Architecture

* 68020/30/40/60
* 68881 FPU
* 68851 MMU
* Apollo Core 68080

## Highlighting

### Directives

- unpaired directives:
  - `struct`/`estruct`
- unpaired conditional assembly directives (nesting!), missing `endc`/`endif`
- highlight static conditional assembly directives status, e.g., `ifd`/`ifnd`
- smarter highlighting/code insight:
  - `section`
  - `opt`
  - `ifmacrod`/`ifmacrond`
  - `machine`

### Macros

- macro call: parameter count mismatch
- macro block:
  - `Symbol` API
  - highlight usages of parameter at caret
  - usage of parameters outside of a macro block
  - non-sequential parameter `\n` numbering
  - highlight parameters in labels `jsr _LVO\1(a6)`

### Other

- `ReadWriteAccessDetector`
  - registers
  - label references
- opcode: 
  - inlay for target architecture
  - inlay for privileged instruction
- method separators:
  - "detected" subroutines
- highlight returns
- highlight/navigate control structures:
  - loop ranges
- unreachable code `ControlFlowProvider`

## Resolve/Refactor

### Labels

- local label: in-place rename
- safe delete
- rename:
  - `RenameInputValidatorEx`
  - `NameSuggestionProvider` ?
- unresolved:
  - quick-fix to add `include` for existing label
  - exclusion list setting to filter out dynamic label names

### Register

- shift up/down through available ones (https://register-blaster.grahambates.com/)
- change/cycle through address mode by typing/intention/...?

### Extract/Duplicates

- extract an include file from selection
- duplicates detection:
  - textual duplicate -> wrap in `rept`/`endr`
  - "extract macro"

## Editing

- `EnterHandlerDelegate` smart indent? or `LineIndentProvider`
- `M68kCommaFlipper`: swap src\|dest if valid
- formatter
  - upper-/lowercase code style settings
    - mnemonic/directive/conditional assembly
    - data size
    - register (An/Dn/special)
    - hex number
  - `:` after label code style setting
- documentation:
  - quick doc for string literal: length
  - render mode for preceding label comments
  - quick doc for `opt` flags
  - quick doc for builtin symbols
  - docs for address mode
- **WIP** code folding with settings for:
  - `movem` push/pop
- macro call: parameter info/preview referring place
- macro block: complete existing/n+1 `\n` everywhere
- `dc`:
  - join lines
  - smart enter handler to split a value list / `ListSplitJoinContext`
- surround descriptors: valid address mode variants
- `FocusModeProvider`
- string literal: support language injection
- comment: smart enter `CodeDocumentationAwareCommenter`

## Inspections

- paired `movem`'s with non-symmetrical register ranges
- register list:
  - sort
  - optimize notation: `d0/d1/d2` &rarr; `d0-d2`
- string literal is not terminated with `,0`
- check optimizations from vasm/DevPac [23.6 vasm Docs](http://sun.hasenbraten.de/vasm/release/vasm_23.html)

### Labels

- unused label: global - located in non-include files only?
- highlight duplicated names (conditional!)
- label naming conventions (`AbstractNamingConventionInspection`)

### Expression

- type check
- range check
- **WIP** simplify, unnecessary parentheses
- intention: convert numeric literal to other bases (`AbstractNumberConversionIntention`)

## Navigation

- breadcrumbs
- quick definition: better range for labels/"code-blocks"
- structure view:
  - `section`
  - macro: # of parameters

### Includes

- goto related: from `.i`/binary file -> including files
- gutter icon: `include` all included files (recursively)
- graph visualization
- highlight cycles

## IDE

- support copyright plugin

## Tools

- show cycles/size in editor [https://68kcounter-web.vercel.app/](https://68kcounter-web.vercel.app/), [MC680x0 Reference](http://oldwww.nvg.ntnu.no/amiga/MC680x0_Sections/index.HTML)

## Amiga

- copper list:
  - color-picker/inlays
  - register name completion/inlay
  - http://deadliners.net/gradientmaster/
- support IFF images in IDE [https://github.com/haraldk/TwelveMonkeys](https://github.com/haraldk/TwelveMonkeys)
- FS-UAE [configuration files](https://fs-uae.net/configuration-files)                         
