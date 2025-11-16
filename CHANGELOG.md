# Changelog

## Unreleased

### Changed

- M68k Browser: 
  - re-use default IDE documentation component for better UX
  - persist selection and pane settings per project
- M68k Browser "Amiga Hardware" tab: 
  - add _Related_ registers
  - add _Copy_ buttons for _Address_

### Added

- Mnemonics docs: 
  - add cross-links in reference docs
  - optimize layout and contents
- "Amiga Hardware" tab: `CIAx` registers
- Mnemonics: support some 68020+ only variants for `BSR`, `Bcc`, `CHK`, `DIVS`/`DIVU`, `LINK`, `MULS`/`MULU`, `TST`

### Fixed

- Mnemonics docs:
  - fix `BRA` reference docs
  - fix duplicate entry in instruction docs for `BCHG`, `BCLR`, `BSET`
- Parser: too lenient parsing for
  - `ADD`/`SUB` 
  - `ADDQ.B`/`SUBQ.B`
  - `AND`/`OR`/`EOR`
  - `ANDI`/`ORI`/`EORI`
  - `ASL`/`ASR`/`LSL`/`LSR`/`ROL`/`ROR`/`ROXL`/`ROXR`
  - `BCHG`, `BCLR`, `BSET`, `BTST`
  - `CMP`
  - `DBcc`
  - `LEA`
  - `MOVE`
  - `MOVES`

## 0.2.0 - 2025-10-31

### Changed

- directives reference docs: added missing contents, cleanup
- added many terms to Amiga dictionary
- _Directives problems_ inspection
  - highlight unmatched `basereg`/`endb`
  - highlight usage of `A7` with `basereg`
- builtin symbols: improve presentation

### Added

- *View \| Appearance \| Navigation Bar*
- add method separator for machine directives (`machine`, `mc68010`, etc.)
- reference documentation for conditional assembly directives (`IFND`, `ELSE`, etc.)
  - in editor
  - M68k Browser "Directives" tab
- support directives:
  - `assert`
  - `basereg`/`endb`

### Fixed

- `machine` directive: support non-numeric _cpu_type_ (`mc68010`)
- `reg` directive: support register mask constant

## 0.1.9 - 2025-09-08

### Changed

- M68k Browser "Mnemonics" tab: show _68010_ suffix

### Added

- implicit macro labels: 
  - configure macros defining labels via their first parameter, see inspection _Unresolved label reference_ for details
  - use _Edit | Find Usages_ on parameter to find usages of implicit label
- reference documentation for registers:
  - in editor
  - M68k Browser "Registers" tab

### Fixed

- instruction documentation: fix matching current variant for some cases
- label documentation: 
  - fix showing the preceding EOL comment instead of the current line EOL comment 
  - show the preceding comment if separated by one conditional assembly directive
- macro call: resolve to macros in the current file defined _after_ the current location
- `rem` - `erem` section: treat as real comment

## 0.1.8 - 2025-07-20

### Changed

- raise the minimum IDE version to 2024.1 (from 2021.2)
- mark the plugin compatible with all future IDE versions
- enable most code insight features when the IDE is indexing
- M68k Browser: restore the selected item when re-opening a project
- M68k Browser "Amiga Hardware" tab: 
  - improve layout for tables
  - icons for _Access_ and _Copper Danger_

### Added

- highlight escape sequences in string literals ("`\n`")

### Fixed

- missing documentation for `dcb` directive ([Issue](https://github.com/prb28/m68k-instructions-documentation/pull/27))
- no completion for labels inside parenthesized expressions
- M68k Browser "Amiga Hardware" tab:
  - `BLTDPTH` (typo `BPTDPTH`)
  - _Access_ flag: correct "Early Read" (`BLTDDAT`, `DSKDATR`)
- `section` directive: allow number value for <mem_type> parameter

## 0.1.73 - 2023-07-25

### Changed

- mark plugin compatible with 2023.2|3

### Fixed

- fix assertion in `M68kConditionalAssemblyCodeBlockSupportHandler` [#56](../../issues/56)

## 0.1.72 - 2023-02-21

### Changed

- regenerate mnemonic data from vasm 1.9a
- raise minimum IDE version to 2021.2 (from 2021.1)
- mark plugin compatible with 2022.3 and 2023.1

## 0.1.6

### Added

- support `RTD` instruction (68010+)
- macro calls: optional semantic highlighting ("rainbow colors")

### Fixed

- macro call parameters
  - support enclosed in `< ... >` for parameter containing `,` (`MESSAGE <"some text",10>`)
  - support register lists (`PUSH D0/D3/A0-A2`)
  - support control registers (`DFC`, `SFC`, `VBR`)
- "current PC" for `(*-1)` expression

## 0.1.5

### Added

- support `CPU32` directive
- support `FPU` directive
- support `OUTPUT` directive
- support _Code | Move Element Left/Right_
  - macro call parameters
  - `CMPM`
  - `DR` directive
  - `IFC`/`IFNC` conditional assembly directive
  - `PRINTV` directive
  - `XDEF`/`XREF` directive

### Fixed

- fix assertion via "Directives" reference documentation provider invoked on macro call [#44](https://github.com/YannCebron/m68kplugin/issues/44)

### Changed

- Improve _Edit | Extend/Shrink Selection_ for blocks inside
  - conditional assembly/macro directives
  - `DBcc` loops

## 0.1.4

### Added

- Reference documentation for directives
- M68k Browser: new tab "Directives" with reference documentation

### Changed

- M68k Browser "Amiga Hardware" tab: add links between registers in reference documentation
- M68k Browser: add _Back_/_Forward_ history navigation

## 0.1.3

### Added

- M68k Browser: new tab "Amiga Hardware" showing register information/reference documentation
- support `AUTO` directive, highlight as unsupported
- support `MSOURCE` directive
- support `OFFSET` directive
- support `MASK2` directive, highlight as unsupported

### Fixed

- show used/free registers: handle register-list/range, PC indirect with index
- `FAIL` directive: support optional message
- M68k Browser: keep currently selected item upon filter changes

### Changed

- minimum IDE version raised to 2021.1 (from 2020.3)
- added many terms to Amiga dictionary

## 0.1.2

### Added

- support `DX` directive
- support `IF1`/`IF2`/`IFP1` directives
- support `FO`, `CLRFO`, `SETFO` directives
- support `SO`, `CLRSO`, `SETSO` directives
- add inspection: Conditional assembly directives problems
- 68010 support: add reference documentation for `BKPT`, `MOVEC`, `MOVES` instructions
- "M68k Browser": new tool window to browse instructions and (reference) docs

### Fixed

- instruction documentation: show UNSIZED

### Changed

- instruction documentation: underline matching variant if multiple
- instruction documentation: move reference documentation to new "M68k Browser" tool window

## 0.1.1

### Added

- 68010 support: instructions `BKPT`, `MOVE` from `CCR`, `MOVEC`, `MOVES`
- 68010 support: registers `DFC`, `SFC`, `VBR`

### Fixed

- single dot without following identifier recognized as valid local label
- directives after label with colon

### Changed

- Editor Color Scheme settings: group 'Supervisor Registers'
- Instruction hover documentation: improve layout

## 0.1.0

### Added

- Initial public release
