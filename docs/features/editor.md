---
title: Editor
parent: Features
nav_order: 1
---

# Editor

## Syntax Highlighting
 
### Settings

Fully customizable via *Settings/Preferences \| Editor \| Color Scheme \| M68k Assembler*

![Color Scheme](../assets/editor/color_scheme.png)

### Parser Highlighting

Detailed on-the-fly semantic error highlighting

![Parser error](../assets/editor/parser_error.png)

* Method separator for `macro`/`endm`, `section` blocks
* Highlight privileged instructions
* Highlight matching brace/paired directives
* Highlight/navigate URLs in comments and string literals

## Documentation

_Hover_: enable _Show quick documentation on hover_ in *Settings/Preferences \| Editor \| Code Editing*

### Mnemonics

*View \| Quick Documentation* reference documentation for mnemonics

![Reference doc](../assets/editor/reference_doc.png)

_Hover_: Show mnemonic reference overview on mouse hover; link to reference documentation

![Mnemonic reference](../assets/editor/hover_doc.png)

### Labels

*View \| Quick Documentation* for labels: show preceding lines or EOL comment(s)
                      
![Label doc](../assets/editor/label_doc.png)

_Hover_: Show type and value tooltip for labels on mouse hover

![Label hover](../assets/editor/label_hover.png)

### Literals

*View \| Quick Documentation* for number literal: show in decimal, hexadecimal, octal, and binary notation

![Number doc](../assets/editor/number_doc.png)

## Labels

*Navigate \| Declaration or Usages* - <kbd>Ctrl+Click</kbd>

### Quick Definition 

*View \| Quick Definition* to inspect label at caret in popup

![Quick definition](../assets/editor/quick_definition.png)

### Find Usages 

*Edit \| Find Usages* (grouped by type)

![Find Usages](../assets/editor/find_usages.png)

### Rename Refactoring

Rename labels using *Refactor \| Rename* across whole project with usages preview

![Rename preview](../assets/editor/rename_preview.png)
                     
## Editing

* Smart *Edit \| Extend/Shrink Selection*
* *Code \| Comment with Line Comment*
* *Code \| Move Element Left/Right* for `exg`, binary expressions, register list, `dc` values
* *Move Caret to Code Block Start/End* for matching directives
* Spellchecker with bundled dictionaries (M68k/Amiga)
* Code Folding (*Settings/Preferences \| Editor \| General \| Code Folding*)
  * Zero-terminated string literal (`dc.b "a text",0`)
  * Custom regions via enclosing `* region [placeholderText]` / `* endregion` comments
* Live Templates (*Settings/Preferences \| Editor \| Live Templates*)
* Code Style Settings (*Settings/Preferences \| Editor \| Code Style \| M68k Assembler*)
  * Tab Size
  * Visual Guides

## Code Insight

### Intentions

Invoke intention via <kbd>Alt</kbd>+<kbd>Enter</kbd>

*Show Used/Free Registers* for selection

![Used registers](../assets/editor/used_registers.png)
