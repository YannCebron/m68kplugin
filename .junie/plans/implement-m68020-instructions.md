# Requirements

### Overview

Implement parser support for all MC68020-specific instructions currently missing from the plugin. The plugin currently supports MC68000/MC68010 and CPU32 instructions, but lacks the MC68020-only opcodes. Each instruction needs a lexer token, a BNF grammar rule, and parser tests. The goal is to eliminate false-positive "Cannot resolve macro" errors when these instructions appear in source files.

### Missing MC68020 Instructions (from vasm sources)

The following instructions are unique to MC68020 and later (not in MC68000/MC68010):

| Instruction | Category | Syntax (vasm operand types) | Complexity |
|-------------|----------|---------------------------|------------|
| `bfchg` | Bit field | `bfchg BD` (alterable control/data + `{offset:width}`) | Complex |
| `bfclr` | Bit field | `bfclr BD` (alterable control/data + `{offset:width}`) | Complex |
| `bftst` | Bit field | `bftst BS` (control/data + `{offset:width}`) | Complex |
| `bfset` | Bit field | `bfset BD` (alterable control/data + `{offset:width}`) | Complex |
| `bfexts` | Bit field | `bfexts BS, Dn` (control/data + `{offset:width}`, Dn) | Complex |
| `bfextu` | Bit field | `bfextu BS, Dn` (control/data + `{offset:width}`, Dn) | Complex |
| `bfffo` | Bit field | `bfffo BS, Dn` (control/data + `{offset:width}`, Dn) | Complex |
| `bfins` | Bit field | `bfins Dn, BD` (Dn, alterable control/data + `{offset:width}`) | Complex |
| `cas` | Compare swap | `cas Dn, Dn, AM` (two Dn, alterable memory) | Moderate |
| `cas2` | Compare swap 2 | `cas2 Dn, Dn, (An)` (two Dn, address register indirect) | Moderate |
| `chk2` | Check 2 | `chk2 CT, Rn` (control operand, any register) | Moderate |
| `cmp2` | Compare 2 | `cmp2 CT, Rn` (control operand, any register) | Moderate |
| `divsl` | Divide signed long | `divsl operand_data, Dn` | Simple |
| `divul` | Divide unsigned long | `divul operand_data, Dn` | Simple |
| `extb` | Extend byte | `extb Dn` | Trivial |
| `pack` | Pack | `pack Dn, Dn, #quick` | Simple |
| `unpk` | Unpack | `unpk Dn, Dn, #quick` | Simple |
| `callm` | Call monitor | `callm #quick, CT` (quick, control operand) | Simple |
| `rtm` | Return from trap monitor | `rtm Rn` (any register) | Trivial |
| `jra` | Jump register | `jra operand_control` | Simple |
| `jbsr` | Jump to subroutine register | `jbsr operand_control` | Simple |
| `trapcc` | Trap carry clear | `trapcc [#vector]` | Trivial |
| `trapcs` | Trap carry set | `trapcs [#vector]` | Trivial |
| `traphi` | Trap high | `traphi [#vector]` | Trivial |
| `traphs` | Trap high same | `traphs [#vector]` | Trivial |
| `trapls` | Trap low same | `trapls [#vector]` | Trivial |
| `traplo` | Trap low | `traplo [#vector]` | Trivial |
| `trapeq` | Trap equal | `trapeq [#vector]` | Trivial |
| `trapne` | Trap not equal | `trapne [#vector]` | Trivial |
| `trapvc` | Trap overflow clear | `trapvc [#vector]` | Trivial |
| `trapvs` | Trap overflow set | `trapvs [#vector]` | Trivial |
| `trappl` | Trap plus | `trappl [#vector]` | Trivial |
| `trapmi` | Trap minus | `trapmi [#vector]` | Trivial |
| `trapge` | Trap greater equal | `trapge [#vector]` | Trivial |
| `traplt` | Trap less than | `traplt [#vector]` | Trivial |
| `trapgt` | Trap greater than | `trapgt [#vector]` | Trivial |
| `traple` | Trap less equal | `traple [#vector]` | Trivial |
| `trapt` | Trap true | `trapt [#vector]` | Trivial |
| `trapf` | Trap false | `trapf [#vector]` | Trivial |

### Sorting by Complexity (Ascending)

**Group 1 — Trivial (no operands or single register):** `extb`, `rtm`, `trapcc`, `trapcs`, `traphi`, `traphs`, `trapls`, `traplo`, `trapeq`, `trapne`, `trapvc`, `trapvs`, `trappl`, `trapmi`, `trapge`, `traplt`, `trapgt`, `traple`, `trapt`, `trapf`

**Group 2 — Simple (existing operand types):** `divsl`, `divul`, `pack`, `unpk`, `jra`, `jbsr`, `callm`

**Group 3 — Moderate (multiple operands, existing types):** `cas`, `cas2`, `chk2`, `cmp2`

**Group 4 — Complex (new addressing mode):** `bfchg`, `bfclr`, `bftst`, `bfset`, `bfexts`, `bfextu`, `bfffo`, `bfins`

### New Addressing Modes and Tokens Required

The following are missing from the current implementation and must be added:

| Item | Type | Used By | Notes |
|------|------|---------|-------|
| `{` / `}` | Lexer tokens | All bit field instructions | Curly braces for `{offset:width}` syntax |
| `adm_bit_field` | BNF rule | All bit field instructions | `{offset:width}` after an addressing mode |
| `operand_bit_field` | BNF rule | `bfchg`, `bfclr`, `bftst`, `bfset` | Alterable control/data with bit field suffix |
| `operand_bit_field_src` | BNF rule | `bfexts`, `bfextu`, `bfffo`, `bfins` | Control/data with bit field suffix |

### Goals

- Eliminate false-positive errors for all listed instructions
- Follow existing patterns in the lexer, BNF, and test data
- Each instruction must parse without syntax errors
- Instructions must be marked as MC68020+ only

### Out of Scope

- Instruction encoding/decoding
- Semantic evaluation of bit field offsets/widths
- CPU model detection based on instructions
- Runtime behavior simulation
- Base register addressing mode (used by `jra`/`jbsr` on 68020+, but the `operand_control` form covers the primary syntax)

# Technical Design

### Current Implementation

The plugin uses:
- **Lexer** (`src/grammar/_M68kLexer.flex`): JFlex lexer with states (`YYINITIAL`, `IN_INSTRUCTION`, `IN_OPERAND`, `STRING_DIRECTIVE`, `AFTER_OPERAND`). Instruction mnemonics are recognized in the `<IN_INSTRUCTION>` state and transition to `IN_OPERAND`.
- **Parser** (`src/grammar/m68k.bnf`): Grammar-Kit BNF. Instructions are defined under the `m68000_instructions` choice rule (line 379). Each instruction gets an `*_instruction` rule. The BNF header auto-applies `M68kInstruction` interface to all `*_instruction` rules.
- **Token types** (`src/com/yanncebron/m68kplugin/lang/psi/M68kTokenTypes.java`): declares token types for each keyword.
- **Tests** (`testData/parser/`): text-based parser tree snapshots, organized by category (e.g., `bitInstructions/`, `mulDivInstructions/`, `miscInstructions/`).

**Existing operand types** (`m68k.bnf` lines 354–370):
- `operand_all` — all addressing modes
- `operand_data` — data register or memory (no address direct)
- `operand_memory` — memory modes only
- `operand_alterable` — alterable data (no PC-relative, no index)
- `operand_alterable_memory` — alterable memory modes
- `operand_control` — control operands (address indirect, PC-based, absolute)
- `operand_ctrl_register` — special registers (DFC, SFC, VBR only)

**Existing addressing modes** (`m68k.bnf` lines 132–350):
- `adm_drd` — Dn (data register)
- `adm_ard` — An/SP (address register)
- `adm_ari` — (An) (address indirect)
- `adm_api` — (An)+ (postincrement)
- `adm_apd` — -(An) (predecrement)
- `adm_adi` — d16(An) (displacement)
- `adm_aix` — bd(An,Xn) (indexed)
- `adm_pcd` — d16(PC) (PC relative)
- `adm_pci` — bd(PC,Xn) (PC indexed)
- `adm_abs` — absolute address
- `adm_imm` — #immediate
- `adm_quick` — #quick (1-8)
- `adm_sr`, `adm_ccr`, `adm_usp`, `adm_dfc`, `adm_sfc`, `adm_vbr` — special registers

**Missing for 68020:**
- Curly brace tokens (`{` / `}`) for bit field syntax
- `adm_bit_field` — `{offset:width}` suffix
- Operand variants combining addressing modes with bit field suffix

### Key Decisions

- **Follow existing patterns**: Each new instruction will mirror an existing similar instruction (e.g., `divsl` mirrors `divs`, `extb` mirrors `ext`).
- **No semantic evaluation**: The parser only recognizes syntax. Bit field offset/width evaluation is out of scope.
- **Group by complexity**: Implement trivial instructions first, then simple, moderate, and complex.
- **Bit field addressing mode**: The `{offset:width}` syntax requires new lexer tokens (`L_BRACE`, `R_BRACE`) and a new BNF rule `adm_bit_field`.
- **`cas2` uses address register indirect**: Verified from vasm — `cas2` takes two data registers and an address register indirect `(An)`, NOT a special register. The third operand type `CS` in vasm maps to mode 2 (`MODE_AnIndir`).
- **`chk2`/`cmp2` use control operand + any register**: Verified from vasm — operand types `CT` (control) and `R_` (any register). The `CT` type includes immediate, so syntax is `chk2 #immediate, Dn` or `chk2 #immediate, An`.
- **`callm` uses quick + control operand**: Verified from vasm — `callm #quick, control_operand`.
- **`rtm` uses any register**: Verified from vasm — `rtm Dn` or `rtm An`, NOT a special register.
- **Trap condition codes**: The 18 `trap*` variants share the same syntax pattern: optional immediate vector (`adm_quick?`).
- **`jra`/`jbsr`**: Accept `operand_control` (same as `jmp`/`jsr`). Base register mode (`BR`) is a 68020+ extension but covered by `operand_control` for parsing purposes.

### Proposed Changes

#### Group 1 — Trivial Instructions

**`extb`**: `extb_instruction ::= EXTB data_size_long? adm_drd` — mirrors `ext_instruction`

**`rtm`**: `rtm_instruction ::= RTM adm_rrd` — any data or address register

**Trap condition codes** (18 instructions): All share the same pattern:
`trapcc_instruction ::= TRAPCC adm_quick?`
`trapcs_instruction ::= TRAPCS adm_quick?`
... and so on for all 18 variants (`trapcc`, `trapcs`, `traphi`, `traphs`, `trapls`, `traplo`, `trapeq`, `trapne`, `trapvc`, `trapvs`, `trappl`, `trapmi`, `trapge`, `traplt`, `trapgt`, `traple`, `trapt`, `trapf`).
Mirrors `trap_instruction` but with optional vector.

#### Group 2 — Simple Instructions (existing operand types)

**`divsl`/`divul`**: `divsl_instruction ::= DIVSL operand_data COMMA adm_drd` — mirrors `divs_instruction` (no data size needed, always long)

**`pack`/`unpk`**: `pack_instruction ::= PACK adm_drd COMMA adm_drd COMMA adm_quick` — three operands, all existing types

**`jra`/`jbsr`**: `jra_instruction ::= JRA operand_control` — mirrors `jmp_instruction`

**`callm`**: `callm_instruction ::= CALLM adm_quick COMMA operand_control` — quick + control operand

#### Group 3 — Moderate Instructions (multiple operands, existing types)

**`cas`**: `cas_instruction ::= CAS data_size_word_long? adm_drd COMMA adm_drd COMMA operand_alterable_memory` — two data registers + alterable memory

**`cas2`**: `cas2_instruction ::= CAS2 data_size_word_long? adm_drd COMMA adm_drd COMMA adm_ari` — two data registers + address register indirect

**`chk2`/`cmp2`**: `chk2_instruction ::= CHK2 data_size_word_long? adm_imm COMMA adm_rrd` — immediate + any register

#### Group 4 — Complex Instructions (new bit field addressing mode)

**New lexer tokens**: `L_BRACE` (`{`) and `R_BRACE` (`}`)

**New BNF rules**:
```
adm_bit_field ::= L_BRACE expression COLON expression R_BRACE
operand_bit_field ::= operand_alterable_control adm_bit_field?
operand_bit_field_src ::= operand_control adm_bit_field?
```

**Bit field instructions** (8 instructions):
- `bfchg_instruction ::= BFCHG operand_bit_field`
- `bfclr_instruction ::= BFCLR operand_bit_field`
- `bftst_instruction ::= BFTST operand_bit_field_src`
- `bfset_instruction ::= BFSET operand_bit_field`
- `bfexts_instruction ::= BFEXTS operand_bit_field_src COMMA adm_drd`
- `bfextu_instruction ::= BFEXTU operand_bit_field_src COMMA adm_drd`
- `bfffo_instruction ::= BFFFO operand_bit_field_src COMMA adm_drd`
- `bfins_instruction ::= BFINS adm_drd COMMA operand_bit_field`

The bit field operand (`operand_bit_field`) combines an alterable control/data addressing mode with an optional `{offset:width}` suffix. The source variant (`operand_bit_field_src`) includes more addressing modes (immediate, PC-relative) for extract instructions.

### Bit Field Addressing Mode Details

The bit field syntax `{offset:width}` appears after a standard addressing mode. Verified from vasm (`cpu.c:2160-2172`):
- Syntax: `{offset:width}` with curly braces
- `offset` and `width` are expressions (can be immediate values or registers)
- The colon `:` is already a `COLON` token in the lexer
- New tokens needed: `L_BRACE` (`{`) and `R_BRACE` (`}`)

The `operand_bit_field` rule covers vasm operand type `BD` (alterable control or data with bit field):
- Modes: Dn, (An), -(An), d16(An), bd(An,Xn), absolute
- With `{offset:width}` suffix

The `operand_bit_field_src` rule covers vasm operand type `BS` (control or data with bit field):
- Modes: Dn, (An), -(An), d16(An), bd(An,Xn), immediate, PC-relative
- With `{offset:width}` suffix

# Delivery Steps

###   Step 1: Group 1: Trivial instructions (extb, rtm, and trap condition codes)
Add lexer tokens, BNF rules, and tests for `extb`, `rtm`, and all 18 trap condition code variants (`trapcc`, `trapcs`, `traphi`, `traphs`, `trapls`, `traplo`, `trapeq`, `trapne`, `trapvc`, `trapvs`, `trappl`, `trapmi`, `trapge`, `traplt`, `trapgt`, `traple`, `trapt`, `trapf`). These have the simplest syntax.

- Add `EXTB`, `RTM`, and 18 `TRAP*` tokens to `M68kTokenTypes.java`
- Add lexer rules in `_M68kLexer.flex` (transition to `AFTER_OPERAND` for `extb`, `rtm`; `AFTER_OPERAND` for trap variants)
- Add BNF rules: `extb_instruction`, `rtm_instruction`, and 18 `trap*_instruction` rules
- Add to `misc_instructions` choice rule in BNF
- Add test files in `testData/parser/miscInstructions/`

###   Step 2: Group 2: Simple instructions (existing operand types)
Add `divsl`, `divul`, `pack`, `unpk`, `jra`, `jbsr`, and `callm`. These use only existing operand types.

- Add `DIVSL`, `DIVUL`, `PACK`, `UNPK`, `JRA`, `JBSR`, `CALLM` tokens to `M68kTokenTypes.java`
- Add lexer rules in `_M68kLexer.flex`
- Add BNF rules using existing operand types (`operand_data`, `operand_control`, `adm_drd`, `adm_quick`)
- Add to appropriate instruction groups in BNF (`mul_div_instructions`, `jump_instructions`, `misc_instructions`)
- Add test files

###   Step 3: Group 3: Moderate instructions (multiple operands)
Add `cas`, `cas2`, `chk2`, and `cmp2`. These use multiple operands with existing types.

- Add `CAS`, `CAS2`, `CHK2`, `CMP2` tokens to `M68kTokenTypes.java`
- Add lexer rules in `_M68kLexer.flex`
- Add BNF rules: `cas_instruction`, `cas2_instruction`, `chk2_instruction`, `cmp2_instruction`
- `cas2` uses `adm_ari` (address register indirect), not special registers
- `chk2`/`cmp2` use `adm_imm` + `adm_rrd` (immediate + any register)
- Add test files

###   Step 4: Group 4: Complex instructions (bit field addressing mode)
Add the bit field addressing mode and all 8 bit field instructions (`bfchg`, `bfclr`, `bftst`, `bfset`, `bfexts`, `bfextu`, `bfffo`, `bfins`). This requires new lexer tokens and BNF rules.

- Add `L_BRACE` and `R_BRACE` tokens to `M68kTokenTypes.java`
- Add lexer rules for `{` and `}` in `_M68kLexer.flex`
- Add `BFCHG`, `BFCLR`, `BFTST`, `BFSET`, `BFEXTS`, `BFEXTU`, `BFFFO`, `BFINS` tokens
- Add new BNF rules: `adm_bit_field`, `operand_bit_field`, `operand_bit_field_src`
- Add 8 bit field instruction rules
- Add test files in `testData/parser/bitInstructions/`

###   Step 5: Finalize: Update documentation and verify
Update `docs/known_issues.md` to remove implemented instructions, regenerate the parser, run all tests, and verify no regressions.
