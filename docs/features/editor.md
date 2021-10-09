---
title: Editor
parent: Features
nav_order: 1
---

# Editor

## Syntax Highlighting
 
### Settings

Fully customizable via *Settings/Preferences \| Editor \| Color Scheme \| M68k Assembler*.

Virtually every syntax element can be configured separately.
Additional semantic highlighting is performed for privileged instructions (possibly depending on operands).

![Color Scheme](../assets/editor/color_scheme.png)

### Parser Highlighting

Detailed on-the-fly semantic error highlighting for all instructions and directives

![Parser error](../assets/editor/parser_error.png)

### Method Separator

For `macro`/`endm` and `section` via *Show method separators* option in *Settings/Preferences \| Editor \| General \| Appearance*
 
### Highlight matching brace and directives

![Matching directive](../assets/editor/matching_directive.png)
                            
### Highlight Misc
 
* Highlight/navigate URLs in comments and string literals

## Documentation

_Hover_: enable _Show quick documentation on hover_ in *Settings/Preferences \| Editor \| Code Editing*

### Mnemonics

*View \| Quick Documentation* reference documentation for mnemonics (only 68000)

![Reference doc](../assets/editor/reference_doc.png)

_Hover_: Show mnemonic reference overview on mouse hover; link to reference documentation (only 68000)

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
                
### Completion

Smart completion for labels, e.g., ranking current local labels and labels from the current file higher in suggestions
 
### Builtin Symbols 

Completion and hover information

![Builtin symbol](../assets/editor/builtin_symbol.png)

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
