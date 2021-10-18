---
title: Inspections
parent: Features
nav_order: 2
---

# Inspections

## Suppression

A "special" line comment followed by the inspection ID to disable can be used to suppress false positives for specific places.

In the following snippet, `a7` will no longer be highlighted by _Usage of A7 register_ inspection:

```
* @@@suppress_inspection@@@ M68kUsageA7Register
   jmp (a7)
```

To insert these for given highlighting, use <kbd>Alt+Enter</kbd> quick fix menu:
- _Suppress for element_ adds suppression comment in preceding line
- _Suppress for file_  adds suppression comment to begin of the file (must occur before first instruction/directive)
  
## Inspections

Inspection settings and descriptions can be accessed under *M68k assembler* group in *Settings/Preferences \| Editor \| Inspections*.

### Unresolved label reference

### Unresolved macro reference

### Unused local label

### Directives problems

### Conditional assembly directives problems

### Register list/range problems

### Usage of A7 register

### Simplifiable expression

