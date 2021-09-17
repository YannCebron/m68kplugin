---
title: Getting Started
nav_order: 2
---

# Getting Started

## About this guide
     
This guide uses the following notations to distinguish certain items:

- *Main Menu \| Sub Menu \| Menu Item* - menu item/action
- *Settings/Preferences \| Settings Page* - page in _Settings/Preferences_ dialog
- `monospaced text` - assembly code
- <kbd>F10</kbd> - keyboard shortcut or input

## Installation

There are currently no published releases, snapshot artifacts for [manual installation](https://www.jetbrains.com/help/idea/plugins-settings.html) are available via [GitHub CI](https://github.com/YannCebron/m68kplugin/actions?query=workflow%3A%22Build+%26+Test%22) in IDE version 2020.2.4 and higher.              

> 🧐 Not familiar with IntelliJ-based IDEs? See [Getting Started Guide](https://www.jetbrains.com/help/idea/getting-started.html).
               
## Project Setup

Currently, there is no dedicated support for configuring or creating a specific variant for M68k projects.

All sources and include files must be located in the same project to be resolved.

By default, all files with extension `.s`, `.asm`, `.i`, and `.x68` are treated as *. See *Motorola 68000 Assembler* in *Settings/Preferences \| Editor \| File Types* to customize. 