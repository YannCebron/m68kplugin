# Changelog

## [Unreleased]
### Added
- support `AUTO` directive, highlight as unsupported
- support `MSOURCE` directive
- support `OFFSET` directive
- support `MASK2` directive, highlight as unsupported

### Fixed
- Show used/free registers: handle register-list/range, PC indirect with index
- `FAIL` directive: support optional message

### Changed
- added many terms to Amiga dictionary     
- "M68k Browser" keeps currently selected item upon filter changes

## [0.1.2]
### Added
- support DX directive
- support IF1/IF2/IFP1 directives
- support FO, CLRFO, SETFO directives
- support SO, CLRSO, SETSO directives
- add inspection: Conditional assembly directives problems
- 68010 support: add reference documentation for BKPT, MOVEC, MOVES instructions
- "M68k Browser": new tool window to browse instructions and (reference) docs

### Fixed
- instruction documentation: show UNSIZED

### Changed
- instruction documentation: underline matching variant if multiple
- instruction documentation: move reference documentation to new "M68k Browser" tool window

## [0.1.1]
### Added
- 68010 support: instructions BKPT, MOVE from CCR, MOVEC, MOVES
- 68010 support: registers DFC, SFC, VBR


### Fixed
- single dot without following identifier recognized as valid local label
- directives after label with colon


### Changed
- Editor Color Scheme settings: group 'Supervisor Registers'
- Instruction hover documentation: improve layout

## [0.1.0]
### Added
- Initial public release