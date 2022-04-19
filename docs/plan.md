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

- `rem` sections: treat as real comment
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
  - highlight usages of parameter at caret
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
- safe delete (quickfix via `M68kUnusedLabelInspection`)
- rename:
  - `RenameInputValidatorEx`
  - `NameSuggestionProvider` ?
- unresolved:
  - quick-fix to add `include` for existing label
  - exclusion list setting to filter out dynamic label names

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
- `M68kCommaFlipper`: swap src\|dest if valid
- formatter
- documentation:
  - quick doc for string literal: length
  - render mode for preceding label comments
  - quick doc for `opt` flags
  - quick doc for builtin symbols
- **WIP** code folding with settings for:
  - `movem` push/pop
- macro call: parameter info/preview referring place
- macro block: complete existing/n+1 `\n` everywhere
- `dc`:
  - join lines
  - smart enter handler to split value list
- surround descriptors?
- `FocusModeProvider`
- string literal: support language injection
- comment: smart enter `CodeDocumentationAwareCommenter`

## Inspections

- `movem` non-symmetrical register ranges
- register list:
  - sort
  - optimize notation: `d0/d1/d2` &rarr; `d0-d2`
- string literal not terminated with `,0`
- check optimizations from vasm/DevPac [19.6 vasm Docs](http://sun.hasenbraten.de/vasm/release/vasm_19.html)

### Labels

- unused label: global - located in non-include files only?
- highlight duplicated names (conditional!)
- label naming conventions (`AbstractNamingConventionInspection`)

### Expression

- type check
- range check
- **WIP** simplify, unnecessary parentheses
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
- graph visualization
- highlight cycles

## IDE

- support copyright plugin

## Tools

- show cycles/size in editor [https://68kcounter-web.vercel.app/](https://68kcounter-web.vercel.app/), [MC680x0 Reference](http://oldwww.nvg.ntnu.no/amiga/MC680x0_Sections/index.HTML)

## Amiga

- copper list:
  - color-picker/inlays
  - register name inlay
  - http://deadliners.net/gradientmaster/
- support IFF images in IDE [https://github.com/haraldk/TwelveMonkeys](https://github.com/haraldk/TwelveMonkeys)
- FS-UAE [configuration files](https://fs-uae.net/configuration-files)                         
