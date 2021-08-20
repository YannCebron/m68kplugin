---
title: Editor
parent: Features
nav_order: 1
---

# Editor

## Syntax Highlighting

* Fully customizable via *Settings/Preferences \| Editor \| Color Scheme \| M68k Assembler*
* Parsing with semantic error highlighting
* Method separator for `macro`/`endm`, `section` blocks
* Highlight privileged instructions
* Highlight matching brace/paired directives
* Highlight/navigate URLs in comments and string literals

## Documentation

*View \| Quick Documentation* for mnemonics

*View \| Quick Documentation* for labels: show preceding lines or EOL comment(s)

Show type and value tooltip for labels on mouse hover (enable _Show quick documentation on hover_ in *Settings/Preferences \| Editor \| Code Editing*)

*View \| Quick Documentation* for number literal: show in decimal, hexadecimal, octal, and binary notation

## Labels

* *Navigate \| Declaration or Usages*
* *View \| Quick Definition*
* *Edit \| Find Usages* (grouped by type)
* *Refactor \| Rename*

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

*Show Used/Free Registers* for selection