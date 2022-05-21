# Changelog

## [Unreleased]
### Added
- support `RTD` instruction (68010+)
- macro calls: optional semantic highlighting ("rainbow colors")

### Fixed
- macro call parameters 
  - support enclosed in `< ... >` for parameter containing `,` (`MESSAGE <"some text",10>`)
  - support register lists (`PUSH D0/D3/A0-A2`)
  - support control registers (`DFC`, `SFC`, `VBR`)
- "current PC" for `(*-1)` expression

## [0.1.5]
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

## [0.1.4]
### Added
- Reference documentation for directives
- M68k Browser: new tab "Directives" with reference documentation

### Changed
- M68k Browser "Amiga Hardware" tab: add links between registers in reference documentation
- M68k Browser: add _Back_/_Forward_ history navigation

## [0.1.3]
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

## [0.1.2]
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

## [0.1.1]
### Added
- 68010 support: instructions `BKPT`, `MOVE` from `CCR`, `MOVEC`, `MOVES`
- 68010 support: registers `DFC`, `SFC`, `VBR`

### Fixed
- single dot without following identifier recognized as valid local label
- directives after label with colon

### Changed
- Editor Color Scheme settings: group 'Supervisor Registers'
- Instruction hover documentation: improve layout

## [0.1.0]
### Added
- Initial public release