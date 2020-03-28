## Motorola 68000 Assembler Support for IntelliJ Platform

Adds support for [Motorola 68000](https://en.wikipedia.org/wiki/Motorola_68000) assembly language in [IntelliJ Platform](http://www.jetbrains.org/intellij/sdk/docs/intro/intellij_platform.html#ides-based-on-the-intellij-platform) based IDEs (like [IntelliJ IDEA](https://www.jetbrains.com/idea/)).

**This plugin is at a very early stage and provided "as-is", please see also [Known issues](#known-issues) below.**

## Features

### Editor
* Syntax highlighting
  * fully customizable via *Preferences | Editor | Color Scheme | M68k Assembler*
  * method separator for `macro`/`endm` blocks
* Parsing with semantic error highlighting
* Labels: Goto Declaration/Find Usages/Rename
* Smart *Extend|Shrink Selection*
* *Move Element Left|Right* for binary expressions, register list, `dc` values

### IDE
* Structure View


## Known issues
* various smaller parser issues

## Plans
See [Plan](plan.md) for planned features and ideas.

## Credits
Plugin Logo from [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:Motorola_M_symbol_blue.svg)
 
Language support built using [Grammar-Kit](https://github.com/JetBrains/Grammar-Kit)

Gradle setup uses [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin/), [gradle-grammar-kit-plugin](https://github.com/JetBrains/gradle-grammar-kit-plugin) and [verifier.gradle](https://github.com/FWDekker/intellij-randomness/blob/master/gradle/scripts/verifier.gradle)

## License
Licensed under the Apache License, Version 2.0 (the "License"), see [LICENCE](LICENCE)

*All product names, trademarks and registered trademarks are property of their respective owners.*