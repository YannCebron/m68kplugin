---
title: Getting Started
nav_order: 2
---

# Getting Started
   

#### About this guide

This guide uses the following notations to distinguish certain items:

| Notation                                     | Meaning                    |
|----------------------------------------------|----------------------------|
| *Main Menu &#124; Sub Menu &#124; Menu Item* | Menu item/Action           |
| *Settings &#124; Settings Page*              | Page in _Settings_ dialog  |
| `monospaced text`                            | Assembly code/Filename     |
| <kbd>F10</kbd>                               | Keyboard shortcut or input |

## Installation

Latest GA release: Open [Plugin Homepage](https://plugins.jetbrains.com/plugin/17712-motorola-68000-series-assembler/) and click *Install to...* button on upper right.

See [Changelog](https://github.com/YannCebron/m68kplugin/blob/main/CHANGELOG.md) for the full changelog.

Snapshot build artifacts (`m68kplugin-x.y.z.zip`) for [manual installation](https://www.jetbrains.com/help/idea/plugins-settings.html) are available via [GitHub CI](https://github.com/YannCebron/m68kplugin/actions/workflows/build.yml).              

> 🧐 Not familiar with IntelliJ-based IDEs? See [IntelliJ IDEA - Getting Started](https://www.jetbrains.com/help/idea/getting-started.html).
               
## Project Setup

There is no dedicated support for configuring or creating a specific variant for M68k projects.

All sources and include files must be located in the same project to be resolved.

By default, all files with extension `.s`, `.asm`, `.i`, `.inc`, and `.x68` are treated as M68k assembly sources. See *Motorola 68000 Assembler* entry in *Settings \| Editor \| File Types* to customize.
                                     
On initial opening of existing sources, or when many source files are changed, the IDE will _index_ changed files to track all relevant information.
It should usually not take a noticeable amount of time, during which some functionality (e.g., resolving/navigation) is not fully available.

## Building

[vasm](http://sun.hasenbraten.de/vasm/) executable can easily be invoked via [External Tools](https://www.jetbrains.com/help/idea/configuring-third-party-tools.html) and mapped to a keyboard shortcut for convenience.
Warning/error messages in the console provide navigation links to the _file:lineNumber_ or _label name_ source (see [IDE](features/ide.md)).                  

Alternatively, any supported build system can be used, e.g., [Makefile](https://plugins.jetbrains.com/plugin/9333-makefile-language).