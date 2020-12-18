---
title: Editor
parent: Features
nav_order: 1
---

# Editor

## Syntax Highlighting

* Fully customizable via *Settings/Preferences \| Editor \| Color Scheme \| M68k Assembler*
* Method separator for `macro`/`endm`, `section` blocks
* Highlight privileged instructions
* Highlight matching brace/paired directives
* Parsing with semantic error highlighting

## Documentation

*View \| Quick Documentation* for mnemonics

## Labels

* *Navigate \| Declaration or Usages*
* *Edit \| Find Usages* (grouped by type)
* *Refactor \| Rename*

## Editing

* Smart *Edit \| Extend/Shrink Selection*
* *Code \| Move Element Left/Right* for `exg`, binary expressions, register list, `dc` values
* Spellchecker with bundled dictionaries (M68k/Amiga)
* Code Folding
  * Zero-terminated string literal (`dc.b "a text",0`)
  * Custom regions via enclosing `* region [placeholderText]` / `* endregion` comments
* Live Templates
* Code Style Settings: Tab Size, Visual Guides
                         
## Code Insight

* *Show Used/Free Registers* for selection