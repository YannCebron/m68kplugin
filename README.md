[![Build & Test](https://github.com/YannCebron/m68kplugin/workflows/Build%20&%20Test/badge.svg)](https://github.com/YannCebron/m68kplugin/actions?query=workflow%3A%22Build+%26+Test%22)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

## Motorola 68000 Series Assembly Language Support for IntelliJ-based IDEs

Adds support for [Motorola 68000 series](https://en.wikipedia.org/wiki/Motorola_68000_series) assembly language
in [IntelliJ](https://www.jetbrains.org/intellij/sdk/docs/intro/intellij_platform.html#ides-based-on-the-intellij-platform) based IDEs
(including [IntelliJ IDEA](https://www.jetbrains.com/idea/), [Android Studio](https://developer.android.com/studio/) and many more).

> **NOTE:** This plugin is at a very early stage and provided "as-is", please see also [Known Issues](#known-issues) below.

## Installation

There are currently no published releases, snapshot artifacts for manual installation are available via <kbd>Build & Test</kbd> GitHub CI (badge on top).

## Features

Support is currently focused on editing, code-insight, and navigation. There is no support for project setup, integration with build tools, or running/debugging.

Please see [Plan](plan.md) for planned features and ideas.

### Supported CPUs

* 68000

### Editor

* Syntax Highlighting
  * Fully customizable via *Preferences | Editor | Color Scheme | M68k Assembler*
  * Method separator for `macro`/`endm`, `section` blocks
  * Highlight privileged instructions
  * Highlight matching brace/paired directives
  * Highlight missing matching opening/closing directive (`macro`/`endm`, `inline`/`einline`, `rem`/`erem`)
* Parsing with semantic error highlighting
* Documentation for mnemonics
* Labels: *Navigate | Declaration or Usages*, *Edit | Find Usages* (grouped by type), *Refactor | Rename*
* Smart *Edit | Extend/Shrink Selection*
* *Code | Move Element Left/Right* for `exg`, binary expressions, register list, `dc` values
* Spellchecker
* Code Folding
  * Zero-terminated string literal (`dc.b "a text",0`)
  * Custom regions via enclosing `* region [placeholderText]` / `* endregion` comments
* Live Templates
* Code Style Settings: Tab Size, Visual Guides
* Code Inspections
  * Unresolved label reference
  * Register list/range problems

### IDE

* Structure View
* *Navigate | Symbol* for labels
* External tools: navigate to _file:lineNumber_ location or label from vasm console message                        

## Known Issues

* Various smaller [parser issues](plan.md#broken-lexingparsing), please [submit issue](https://github.com/YannCebron/m68kplugin/issues) if not listed yet
* Unsupported features:
  - Includes (resolves across all files)

## Credits

Plugin Logo from [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:Motorola_M_symbol_blue.svg)

Language support built using [Grammar-Kit](https://github.com/JetBrains/Grammar-Kit)

Gradle setup uses [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin/)
, [gradle-grammar-kit-plugin](https://github.com/JetBrains/gradle-grammar-kit-plugin)
and [verifier.gradle](https://github.com/FWDekker/intellij-randomness/blob/master/gradle/scripts/verifier.gradle)

Mnemonic documentation taken from [m68k-instructions-documentation](https://github.com/prb28/m68k-instructions-documentation)

## License

Licensed under the Apache License, Version 2.0 (the "License"), see [LICENCE](LICENCE)

This software includes [commonmark-java](https://github.com/atlassian/commonmark-java) library, Copyright (c) 2015-2016, Atlassian Pty Ltd

*All product names, trademarks, and registered trademarks are property of their respective owners.*