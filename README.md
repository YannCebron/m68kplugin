[![CI](https://github.com/YannCebron/m68kplugin/workflows/CI/badge.svg)](https://github.com/YannCebron/m68kplugin/actions?query=workflow%3ACI)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

## Motorola 68000 Series Assembler Support for IntelliJ Platform

Adds support for [Motorola 68000 series](https://en.wikipedia.org/wiki/Motorola_68000_series) assembly language
in [IntelliJ Platform](http://www.jetbrains.org/intellij/sdk/docs/intro/intellij_platform.html#ides-based-on-the-intellij-platform)
based IDEs (like [IntelliJ IDEA](https://www.jetbrains.com/idea/)).
                               
There are currently no published releases, please run `./gradlew buildPlugin` to create plugin distribution from sources in `build/distributions`.

> NOTE **This plugin is at a very early stage and provided "as-is". Please see also [Known Issues](#known-issues) below.**

## Features

Support is currently focused on editing, code-insight, and navigation. There is no support for project setup,
integration with build tools, or running/debugging.

Please see [Plan](plan.md) for planned features and ideas.

### Supported CPUs

* 68000

### Editor

* Syntax Highlighting
  * Fully customizable via *Preferences | Editor | Color Scheme | M68k Assembler*
  * Method separator for `macro`/`endm`, `section` blocks
  * Highlight privileged instructions
  * Highlight matching brace/paired directives
* Parsing with semantic error highlighting
* Documentation for mnemonics
* Labels: *Navigate|Declaration or Usages*, *Edit|Find Usages* (grouped by type), *Refactor|Rename*
* Smart *Edit|Extend/Shrink Selection*
* *Code|Move Element Left/Right* for `exg`, binary expressions, register list, `dc` values
* Spellchecker
* Code Folding  
* Live Templates
* Code Style Settings: Tab Size, Visual Guides
* Code Inspections
  * Unresolved label reference
  * Register list/range problems

### IDE

* Structure View
* *Navigate|Symbol* for labels

## Known Issues

* Various smaller parser issues (see [Broken Lexing/Parsing](plan.md#broken-lexingparsing)), please [submit issue](https://github.com/YannCebron/m68kplugin/issues) if not listed yet
* Unsupported features:
  - Includes

## Credits

Plugin Logo from [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:Motorola_M_symbol_blue.svg)

Language support built using [Grammar-Kit](https://github.com/JetBrains/Grammar-Kit)

Gradle setup uses [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin/)
, [gradle-grammar-kit-plugin](https://github.com/JetBrains/gradle-grammar-kit-plugin)
and [verifier.gradle](https://github.com/FWDekker/intellij-randomness/blob/master/gradle/scripts/verifier.gradle)

Mnemonic documentation taken
from [m68k-instructions-documentation](https://github.com/prb28/m68k-instructions-documentation)

## License

Licensed under the Apache License, Version 2.0 (the "License"), see [LICENCE](LICENCE)

This software includes [commonmark-java](https://github.com/atlassian/commonmark-java) library, Copyright (c) 2015-2016,
Atlassian Pty Ltd

*All product names, trademarks, and registered trademarks are property of their respective owners.*