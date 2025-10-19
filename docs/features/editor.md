---
title: Editor
parent: Features
nav_order: 1
---

# Editor

## Syntax Highlighting

### Settings

Fully customizable via *Settings \| Editor \| Color Scheme \| M68k Assembler*.

Virtually every syntax element can be configured separately.
Additional highlighting is performed for privileged instructions (possibly depending on operands).
Enable _Semantic highlighting_ to highlight macro call names using a configurable rainbow color scheme.

![Color Scheme](../assets/editor/color_scheme.png)

### Parser Highlighting

Detailed on-the-fly semantic error highlighting for all instructions and directives

![Parser error](../assets/editor/parser_error.png)

### Method Separator

For `macro`/`endm`, `section` and machine directives (`machine`, `mc68010` etc.) via *Show method separators* option in *Settings \| Editor \| General \| Appearance*

### Highlight matching braces and directives

![Matching directive](../assets/editor/matching_directive.png)

### Highlight Misc

* Highlight/navigate URLs in comments and string literals

## Documentation

_Hover_: enable _Show quick documentation on hover_ in *Settings \| Editor \| Code Editing*

### Mnemonics

_View \| View Quick Documentation_ Show mnemonic documentation (or on mouse hover), the current variant is underlined

![Mnemonic documentation](../assets/editor/hover_doc.png)

_M68k Browser_ tool window lists all supported mnemonics with additional reference documentation

![Mnemonic reference documentation](../assets/ide/reference_doc.png)

### Directives

_View \| View Quick Documentation_ Show directive reference documentation

### Registers

_View \| View Quick Documentation_ Show register reference documentation

### Labels

*View \| Quick Documentation* for labels: show preceding lines or EOL comment(s)

![Label doc](../assets/editor/label_doc.png)

<kbd>Ctrl+Click</kbd>: Show type and value tooltip for labels

![Label hover](../assets/editor/label_hover.png)

### Literals

*View \| Quick Documentation* for number literal: show in decimal, hexadecimal, octal, and binary notation

![Number doc](../assets/editor/number_doc.png)

## Labels

*Navigate \| Declaration or Usages* - <kbd>Ctrl+Click</kbd>

### Completion

Smart completion for labels, e.g., ranking current local labels and labels from the current file higher in the
completion popup.

### Builtin Symbols

Completion and <kbd>Ctrl+Click</kbd> hover information

![Builtin symbol](../assets/editor/builtin_symbol.png)

### Quick Definition

*View \| Quick Definition* to inspect label at caret in a popup without leaving the current editor

![Quick definition](../assets/editor/quick_definition.png)

### Find Usages

*Edit \| Find Usages* (grouped by type)

![Find Usages](../assets/editor/find_usages.png)

### Rename Refactoring

Rename labels using *Refactor \| Rename* across the whole project with usage preview

![Rename preview](../assets/editor/rename_preview.png)

## Editing

* Smart *Edit \| Extend/Shrink Selection*
* Smart braces/quote handling
* *Code \| Comment with Line Comment*
* *Code \| Move Element Left/Right* for `exg`/`cmpm`, binary expressions, register list, macro call parameters, `dc`/
  `dr` values
* Spellchecker with bundled dictionaries (M68k/Amiga/"Scene" jargon)
* Code Folding (*Settings \| Editor \| General \| Code Folding*)
  * Zero-terminated string literal (`dc.b "a text",0` &rarr; `"a text"`)
  * Custom regions via enclosing `* region [placeholderText]` / `* endregion` comments
* Live Templates (*Settings \| Editor \| Live Templates*)
* Code Style Settings (*Settings \| Editor \| Code Style \| M68k Assembler*)
  * Tab Size
  * Visual Guides

## Navigation

* *Move Caret to Code Block Start/End* for matching directives
* *View \| Appearance \| Navigation Bar* 
  * Enable _Show Members_ to show current parent label

### Structure

*Structure View* tool window and *Navigate \| File Structure* popup for the current file

![Structure tool window](../assets/editor/structure_toolwindow.png)

## Code Insight

### Intentions

Invoke intention via <kbd>Alt</kbd>+<kbd>Enter</kbd>

*Show Used/Free Registers* for selection

![Used registers](../assets/editor/used_registers.png)
