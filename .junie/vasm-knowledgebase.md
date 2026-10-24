# vasm Source Knowledge Base

This document helps navigate the vasm assembler source code for adding and checking M68k instructions and directives. The vasm sources are the authoritative reference for M68k assembler directives, instruction encoding, operand types, and CPU support.

> **Note:** This knowledge base is optimized for the M68000 family. Information about ColdFire and Apollo CPU families has been removed to reduce size and focus on the plugin's primary target. ColdFire and Apollo special registers, CPU models, and operand codes are not documented here.

## Directory Structure

### Core Files

| File | Purpose |
|------|---------|
| `vasm.h` | Global definitions and types |
| `parse.c` | Main parsing logic, including `parse_name()` for string formats |
| `parse.h` | Parser API declarations |
| `symbol.c` | Symbol table | Symbol creation and management |
| `atom.c` | Code and data atom handling |
| `expr.h` | Expression parsing and evaluation |

### M68k CPU-Specific Files (`cpus/m68k/`)

| File | Purpose |
|------|---------|
| `opcodes.h` | **Instruction definitions** — all mnemonics, operands, encoding, and CPU support |
| `cpu.c` | CPU implementation — encoding, decoding, and instruction validation |
| `cpu.h` | CPU API — macros, types, and function declarations |
| `operands.h` | **Operand types** — operand codes, addressing modes, and special registers |
| `cpu_models.h` | **CPU models** — supported CPU names and their feature flags |
| `cpu_errors.h` | CPU-specific error messages |
| `specregs.h` | Special register names (e.g., SFC, DFC, CACR) |

### M68k Syntax Files (`syntax/mot/`)

| File | Purpose |
|------|---------|
| `syntax.c` | **Directive parsing** — all assembler directives, their flags, and handlers |
| `syntax.h` | Syntax module API |
| `syntax_errors.h` | Syntax-specific error messages |

## Opcode Definition Format (`cpus/m68k/opcodes.h`)

Each line defines one instruction variant. The format is:

```c
"mnemonic", {operand1, operand2}, {{encoding1, encoding2}, {opcode, extension}, flags|size|status, cpu_support},
```

### Field Descriptions

1. **Mnemonic**: String literal, e.g., `"add"`, `"move"`, `"nop"`
2. **Operands**: Two operand codes from `operands.h` enum (e.g., `D_`, `A_`, `IM`, `AD`). Use `{0}` for no operand.
3. **Encoding**: Two encoding method codes (e.g., `SEA`, `RLO`, `RHI`, `NEA`, `C2H`)
4. **Opcode**: Base opcode and extension byte as hex values (e.g., `{0xd000, 0}`)
5. **Flags**: Pipe-separated flags:
   - Number prefix: `1` = standard, `2` = extended, `3` = special
   - Size: `B` (byte), `W` (word), `L` (long), `BW`, `WL`, `BWL`, `CFBWL` (coldfire byte/word/long)
   - Status: `S_NONE`, `S_STD`, `S_WL8`, etc.
6. **CPU support**: CPU model identifier (e.g., `m68000up`, `m68020`, `apollo`, `mcf`)

### Example

```c
"add",      {DA,D_},      {{SEA,RHI},        {0xd000,0},1|CFBWL|S_STD,m68000up|mcf},
```

- Mnemonic: `add`
- Operands: `DA` (data register or address register) and `D_` (data register)
- Encoding: `SEA` (source effective address) and `RHI` (register high)
- Opcode: `0xd000`, extension `0`
- Flags: `1` (standard), `CFBWL` (byte/word/long sizes), `S_STD` (standard status effects)
- CPU: `m68000up` (68000 and later) or `mcf` (ColdFire)

### Ordering Rules (lines 1-14)

1. Subset operand types come **before** superset operand types.
2. Higher CPU requirements come **after** lower CPU requirements.
3. Same name and operands but different sizes stay **together**.
4. No-operand variants (`{0}`) come **last** for that mnemonic.

## Operand Types (`cpus/m68k/operands.h`)

### Operand Codes (enum, lines 65-74)

The operand codes appear in opcode definitions. Key codes:

| Code | Meaning | Code | Meaning |
|------|---------|------|---------|
| `D_` | Data register (D0-D7) | `A_` | Address register (A0-A7) |
| `IM` | Immediate data | `QI` | Quick immediate (1-8) |
| `AD` | Alterable data (0,2-6,7.0-1) | `DA` | Data (0,2-6,7.0-4) |
| `DN` | Data, but not immediate (0,2-6,7.0-3) | | |
| `AM` | Alterable memory (2-6,7.0-1) | `MA` | Memory addressing modes (2-6,7.0-4) |
| `AY` | All addressing modes (0-6,7.0-4) | `MI` | Memory modes without immediate (2-6,7.0-3) |
| `FA` | Memory modes with float immediate | `FF` | Double FPU register |
| `FR` | FPU special register (FPCR/FPSR/FPIAR) | `F_` | FPU register FPn |
| `B_` | Apollo base register | `VX` | Apollo virtual register |
| `_CCR` | Condition code register | `_SR` | Status register |
| `_USP` | User stack pointer | `_CTRL` | Control register (MOVEC) |
| `_ACC` | Accumulator (ColdFire) | `_MACSR` | MAC status register |

### Addressing Modes (addrmodes[], lines 1-18)

| Index | Mode | Description |
|-------|------|-------------|
| 0 | `MODE_Dn` | Data register direct |
| 1 | `MODE_An` | Address register direct |
| 2 | `MODE_AnIndir` | Address register indirect |
| 3 | `MODE_AnPostInc` | Address register indirect with postincrement |
| 4 | `MODE_AnPreDec` | Address register indirect with predecrement |
| 5 | `MODE_An16Disp` | Address register indirect with 16-bit displacement |
| 6 | `MODE_An8Format` | Address register indirect with 8-bit displacement (format) |
| 7 | `MODE_Extended,REG_AbsShort` | Absolute short (16-bit address) |
| 8 | `MODE_Extended,REG_AbsLong` | Absolute long (32-bit address) |
| 9 | `MODE_Extended,REG_PC16Disp` | PC relative with 16-bit displacement |
| 10 | `MODE_Extended,REG_PC8Format` | PC relative with 8-bit displacement |
| 11 | `MODE_Extended,REG_Immediate` | Immediate data |
| 12 | `MODE_Extended,REG_RnList` | Register list (MOVEM) |
| 13 | `MODE_Extended,REG_FPnList` | FPU register list |
| 14 | `MODE_FPn` | FPU data register direct |
| 15 | `MODE_SpecReg` | Special register |

### Special Registers (specregs.h)

Special registers are used by `movec`, `pmove`, and cache instructions. Only M68000 family registers are listed.

#### M68000 Family (movec instruction)

| Register | Code | CPU Support | Description |
|----------|------|-------------|-------------|
| `ccr` | -1 | m68000up | Condition code register |
| `sr` | -1 | m68000up | Status register |
| `usp` | -1 | m68000up | User stack pointer |
| `sfc` | 0x000 | m68010up | Source function code |
| `dfc` | 0x001 | m68010up | Destination function code |
| `cacr` | 0x002 | m68020up | Cache control register |
| `caar` | 0x802 | m68020, m68030 | Cache address register |
| `msp` | 0x803 | m68020-m68040 | Master stack pointer |
| `isp` | 0x804 | m68020-m68040 | Interrupt stack pointer |
| `vbr` | 0x801 | m68010up | Vector base register |
| `mmusr` | 0x805 | m68040 | MMU status register |
| `urp` | 0x806 | m68040up | User register pointer |
| `srp` | 0x807 | m68040up | Supervisor register pointer |
| `itt0` | 0x004 | m68040up | Instruction translation register 0 |
| `itt1` | 0x005 | m68040up | Instruction translation register 1 |
| `dtt0` | 0x006 | m68040up | Data translation register 0 |
| `dtt1` | 0x007 | m68040up | Data translation register 1 |
| `iacr0` | 0x004 | m68040up | Instruction access control register 0 |
| `iacr1` | 0x005 | m68040up | Instruction access control register 1 |
| `dacr0` | 0x006 | m68040up | Data access control register 0 |
| `dacr1` | 0x007 | m68040up | Data access control register 1 |
| `tc` | 0x003 | m68040up | Thermal control register |
| `buscr` | 0x008 | m68060 | Bus control register |
| `pcr` | 0x808 | m68060 | Performance control register |

#### M68040+ Cache Instructions

| Register | Code | CPU Support | Description |
|----------|------|-------------|-------------|
| `nc` | 0 | m68040up | Next cache |
| `dc` | 1 | m68040up | Data cache |
| `ic` | 2 | m68040up | Instruction cache |
| `bc` | 3 | m68040up | Back cache |

#### M68030+ (pmove instruction)

| Register | Code | CPU Support | Description |
|----------|------|-------------|-------------|
| `tc` | 0 | m68030 | Task register |
| `srp` | 2 | m68030 | Supervisor register pointer |
| `crp` | 3 | m68030 | Child register pointer |
| `mmusr` | 0 | m68030 | MMU status register |
| `psr` | 0 | m68030 | Processor status register |
| `tt0` | 2 | m68030 | Translation register 0 |
| `tt1` | 3 | m68030 | Translation register 1 |

## CPU Models (`cpus/m68k/cpu_models.h`)

### M68000 Family

| Name | Model | Notes |
|------|-------|-------|
| `68000` | `m68000` | Base CPU |
| `68008` | `m68000` | 8-bit data bus variant |
| `68010` | `m68010` | Obsolete, rarely used |
| `68020` | `m68020` | 32-bit data bus, new addressing modes |
| `68030` | `m68030` | Memory management, virtual memory |
| `68040` | `m68040` | Integrated FPU, cache |
| `68060` | `m68060` | Superscalar, pipelined |

### FPU Variants

| Name | Model | Notes |
|------|-------|-------|
| `68881` | `m68881` | External FPU |
| `68882` | `m68881` | Enhanced external FPU |
| `68851` | `m68851` | Alternative FPU |

### CPU Support in Opcodes

The last field of each opcode entry specifies supported CPUs. Common values:

- `m68000up` — 68000 and all later CPUs
- `m68020` — 68020 only
- `m68020up` — 68020 and later
- `m68030` — 68030 only
- `m68040` — 68040 only
- `m68060` — 68060 only
- `m68881` — FPU required
- `m68851` — 68851 FPU

Multiple CPUs can be combined with `|`, e.g., `m68000up|mcf`.

## Directives (`syntax/mot/syntax.c`)

### Directive Table (lines 1823-2023)

The directive table is a C array of structs. Each entry has:

- **Name**: String literal (e.g., `"org"`, `"dc"`)
- **Flags**: Bitmask of directive flags
- **Handler**: Function pointer (e.g., `handle_org`)

### Directive Flags

| Flag | Value | Meaning |
|------|-------|---------|
| `P` (DIRF_PHXASS) | `(1<<5)` | PhxAss-compatible prefix (no dot required) |
| `D` (DIRF_DEVPAC) | `(1<<4)` | DevPac-compatible (dot prefix required) |
| `DT_IF` | `1` | Conditional IF type |
| `DT_ELSE` | `2` | Conditional ELSE type |
| `DT_ELIF` | `3` | Conditional ELIF type |
| `DT_ENDIF` | `4` | Conditional ENDIF type |

A directive with `P\|D` supports both forms (e.g., `org` and `.org`). A directive with `0` supports neither prefix form.

### Complete Directive List

#### Section Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `org` | P\|D | `handle_org` | Set origin address |
| `rorg` | P\|D | `handle_rorg` | Relative reorg |
| `section` | P\|D | `handle_section` | Switch section |
| `offset` | P\|D | `handle_offset` | Offset section address |
| `code` / `.code` | P\|D | `handle_csec` | Switch to code section |
| `text` / `.text` | P\|D | `handle_csec` | Switch to code section (alias for code) |
| `cseg` | P | `handle_csec` | Switch to code section |
| `data` / `.data` | P\|D | `handle_dsec` | Switch to data section |
| `dseg` | P | `handle_dsec` | Switch to data section |
| `bss` / `.bss` | P\|D | `handle_bss` | Switch to BSS section |
| `code_c` / `.code_c` | P\|D | `handle_codec` | Code section (const) |
| `code_f` / `.code_f` | P\|D | `handle_codef` | Code section (far) |
| `data_c` / `.data_c` | P\|D | `handle_datac` | Data section (const) |
| `data_f` / `.data_f` | P\|D | `handle_dataf` | Data section (far) |
| `bss_c` / `.bss_c` | P\|D | `handle_bssc` | BSS section (const) |
| `bss_f` / `.bss_f` | P\|D | `handle_bssf` | BSS section (far) |
| `pushsection` | 0 | `handle_pushsect` | Push current section |
| `popsection` | 0 | `handle_popsect` | Pop section |

#### Symbol Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `public` | P | `handle_global` | Export symbol |
| `xdef` / `.xdef` | P\|D | `handle_xdef` | Cross-define symbol |
| `xref` / `.xref` | P\|D | `handle_xref` | Cross-reference symbol |
| `xref.l` / `.xref.l` | P\|D | `handle_xref` | Cross-reference (long) |
| `nref` | P | `handle_nref` | Near reference |
| `entry` | 0 | `handle_global` | Entry point |
| `extrn` | 0 | `handle_global` | External symbol |
| `global` | 0 | `handle_global` | Global symbol |
| `import` | 0 | `handle_xref` | Import symbol (PureC) |
| `export` | 0 | `handle_xdef` | Export symbol (PureC) |
| `weak` | 0 | `handle_weak` | Weak symbol |
| `comm` | 0 | `handle_comm` | Common symbol |

#### Data Definition Directives

| Directive | Flags | Handler | Size |
|-----------|-------|---------|------|
| `dc` / `.dc` | P\|D | `handle_d16` | 16-bit default |
| `dc.b` / `.dc.b` | P\|D | `handle_d8` | 8-bit |
| `dc.w` / `.dc.w` | P\|D | `handle_d16` | 16-bit |
| `dc.l` / `.dc.l` | P\|D | `handle_d32` | 32-bit |
| `dc.q` / `.dc.q` | P | `handle_d64` | 64-bit |
| `dc.s` / `.dc.s` | P\|D | `handle_f32` | 32-bit float (`#if FLOAT_PARSER`) |
| `dc.d` / `.dc.d` | P\|D | `handle_f64` | 64-bit float (`#if FLOAT_PARSER`) |
| `dc.x` / `.dc.x` | P\|D | `handle_f96` | 96-bit float (`#if FLOAT_PARSER`) |
| `dc.p` / `.dc.p` | P\|D | `handle_fpd` | Packed decimal (`#if FLOAT_PARSER`) |
| `db` | 0 | `handle_d8` | 8-bit data |
| `dw` | 0 | `handle_d16` | 16-bit data |
| `dl` | 0 | `handle_d32` | 32-bit data |

#### Space Allocation Directives

| Directive | Flags | Handler | Size |
|-----------|-------|---------|------|
| `ds` / `.ds` | P\|D | `handle_spc16` | 16-bit default |
| `ds.b` / `.ds.b` | P\|D | `handle_spc8` | 8-bit |
| `ds.w` / `.ds.w` | P\|D | `handle_spc16` | 16-bit |
| `ds.l` / `.ds.l` | P\|D | `handle_spc32` | 32-bit |
| `ds.q` / `.ds.q` | P | `handle_spc64` | 64-bit |
| `ds.s` / `.ds.s` | P\|D | `handle_spc32` | 32-bit float |
| `ds.d` / `.ds.d` | P\|D | `handle_spc64` | 64-bit float |
| `ds.x` / `.ds.x` | P\|D | `handle_spc96` | 96-bit float |
| `ds.p` / `.ds.p` | P\|D | `handle_spc96` | Packed decimal |
| `dx` / `.dx` | P | `handle_xspc16` | 16-bit default (executable) |
| `dx.b` / `.dx.b` | P | `handle_xspc8` | 8-bit (executable) |
| `dx.w` / `.dx.w` | P | `handle_xspc16` | 16-bit (executable) |
| `dx.l` / `.dx.l` | P | `handle_xspc32` | 32-bit (executable) |
| `dx.q` / `.dx.q` | P | `handle_xspc64` | 64-bit (executable) |
| `dx.s` / `.dx.s` | P | `handle_xspc32` | 32-bit float (executable) |
| `dx.d` / `.dx.d` | P | `handle_xspc64` | 64-bit float (executable) |
| `dx.x` / `.dx.x` | P | `handle_xspc96` | 96-bit float (executable) |
| `dx.p` / `.dx.p` | P | `handle_xspc96` | Packed decimal (executable) |

#### Block Fill Directives

| Directive | Flags | Handler | Size |
|-----------|-------|---------|------|
| `dcb` / `.dcb` | P\|D | `handle_blk16` | 16-bit default |
| `dcb.b` / `.dcb.b` | P\|D | `handle_blk8` | 8-bit |
| `dcb.w` / `.dcb.w` | P\|D | `handle_blk16` | 16-bit |
| `dcb.l` / `.dcb.l` | P\|D | `handle_blk32` | 32-bit |
| `dcb.q` / `.dcb.q` | P | `handle_blk64` | 64-bit |
| `dcb.s` / `.dcb.s` | P\|D | `handle_blk32` | 32-bit float |
| `dcb.d` / `.dcb.d` | P\|D | `handle_blk64` | 64-bit float |
| `dcb.x` / `.dcb.x` | P\|D | `handle_blk96` | 96-bit float |
| `dcb.p` / `.dcb.p` | P\|D | `handle_blk96` | Packed decimal |
| `blk` / `.blk` | P | `handle_blk16` | 16-bit default |
| `blk.b` / `.blk.b` | P | `handle_blk8` | 8-bit |
| `blk.w` / `.blk.w` | P | `handle_blk16` | 16-bit |
| `blk.l` / `.blk.l` | P | `handle_blk32` | 32-bit |
| `blk.q` / `.blk.q` | P | `handle_blk64` | 64-bit |
| `blk.s` / `.blk.s` | P | `handle_blk32` | 32-bit float |
| `blk.d` / `.blk.d` | P | `handle_blk64` | 64-bit float |
| `blk.x` / `.blk.x` | P | `handle_blk96` | 96-bit float |
| `blk.p` / `.blk.p` | P | `handle_blk96` | Packed decimal |

#### Alignment Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `cnop` / `.cnop` | P\|D | `handle_cnop` | Conditional no-op (padding) |
| `align` | 0 | `handle_align` | Align to power of 2 |
| `even` / `.even` | P\|D | `handle_even` | Align to 2 bytes |
| `odd` | 0 | `handle_odd` | Align to odd address |

#### Equate Directives

The equate directives are handled inline in the main parsing loop in `syntax.c`, not in the directive table:

| Directive | Handler | Lines |
|-----------|---------|-------|
| `equ` | `new_equate()` inline | ~2274 |
| `fequ` | `fequate()` | ~2074 |
| `set` | `new_abs()` inline | ~2311 |

The `fequ` directive is the only one with a standalone helper function. The `equ` and `set` directives are handled inline using `new_equate()` and `new_abs()`. The `fequ` directive supports optional suffixes (.s, .d, .x, .p) for Devpac compatibility, but vasm ignores them.

#### Include Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `include` / `.include` | P\|D | `handle_include` | Include file |
| `incbin` / `.incbin` | P\|D | `handle_incbin` | Include binary file |
| `image` | 0 | `handle_incbin` | Include image file |
| `incdir` / `.incdir` | P\|D | `handle_incdir` | Add include directory |

The `include` directive accepts three string formats (see `parse_name()` in `parse.c`):
1. Double-quoted: `include "file.i"`
2. Single-quoted: `include 'file.i'`
3. Angle-bracketed: `include <file.i>` (M68K only, `parse.c:137-145`)

#### Macro Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `macro` / `.macro` | P\|D | `handle_macro` | Define macro |
| `endm` / `.endm` | P\|D | `handle_endm` | End macro |
| `mexit` / `.mexit` | P\|D | `handle_mexit` | Exit macro |
| `rept` / `.rept` | P\|D | `handle_rept` | Repeat block |
| `endr` / `.endr` | P\|D | `handle_endr` | End repeat |
| `rem` | P | `handle_rem` | Repeat with macro |
| `erem` | P | `handle_erem` | End rem |
| `inline` | P | `handle_inline` | Inline macro block |
| `einline` | P | `handle_einline` | End inline |

#### Conditional Assembly Directives

| Directive | Flags | Handler | Type |
|-----------|-------|---------|------|
| `ifb` | DT_IF | `handle_ifb` | If blank |
| `ifnb` | DT_IF | `handle_ifnb` | If not blank |
| `ifc` / `.ifc` | P\|D\|DT_IF | `handle_ifc` | If character |
| `ifnc` / `.ifnc` | P\|D\|DT_IF | `handle_ifnc` | If not character |
| `ifd` / `.ifd` | P\|D\|DT_IF | `handle_ifd` | If defined |
| `ifnd` / `.ifnd` | P\|D\|DT_IF | `handle_ifnd` | If not defined |
| `ifmacrod` | DT_IF | `handle_ifmacrod` | If macro defined |
| `ifmacrond` | DT_IF | `handle_ifmacrond` | If macro not defined |
| `ifeq` / `.ifeq` | P\|D\|DT_IF | `handle_ifeq` | If equal |
| `ifne` / `.ifne` | P\|D\|DT_IF | `handle_ifne` | If not equal |
| `ifgt` / `.ifgt` | P\|D\|DT_IF | `handle_ifgt` | If greater than |
| `ifge` / `.ifge` | P\|D\|DT_IF | `handle_ifge` | If greater or equal |
| `iflt` / `.iflt` | P\|D\|DT_IF | `handle_iflt` | If less than |
| `ifle` / `.ifle` | P\|D\|DT_IF | `handle_ifle` | If less or equal |
| `ifmi` | DT_IF | `handle_iflt` | If minus (alias for iflt) |
| `ifpl` | DT_IF | `handle_ifge` | If plus (alias for ifge) |
| `if` / `.if` | P\|DT_IF | `handle_ifne` | If expression (alias for ifne) |
| `ifp1` | DT_IF | `handle_ifp1` | If platform 1 |
| `if1` | DT_IF | `handle_ifp1` | If platform 1 (short) |
| `if2` | DT_IF | `handle_ifp2` | If platform 2 |
| `else` / `.else` | P\|D\|DT_ELSE | `handle_else` | Else |
| `elseif` / `.elseif` | P\|D\|DT_ELSE | `handle_else` | Else if |
| `elif` | DT_ELIF | `handle_elseif` | Else if (DevPac) |
| `endif` / `.endif` | P\|D\|DT_ENDIF | `handle_endif` | End if |
| `endc` / `.endc` | P\|D\|DT_ENDIF | `handle_endif` | End conditional (DevPac) |

#### Assertion Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `end` / `.end` | P\|D | `handle_end` | End of assembly |
| `fail` / `.fail` | P\|D | `handle_fail` | Assembly error |
| `assert` | 0 | `handle_assert` | Assert condition |

#### Listing Control Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `idnt` / `.idnt` | P\|D | `handle_idnt` | Identifier string |
| `ttl` / `.ttl` | P\|D | `handle_title` | Title string |
| `list` / `.list` | P\|D | `handle_list` | Enable listing |
| `nolist` / `.nolist` | P\|D | `handle_nolist` | Disable listing |
| `plen` / `.plen` | P\|D | `handle_plen` | Page length |
| `llen` / `.llen` | P\|D | `handle_dummy_cexpr` | Line length |
| `page` / `.page` | P\|D | `handle_page` | Page break |
| `nopage` / `.nopage` | P\|D | `handle_nopage` | No page break |
| `comment` / `.comment` | P\|D | `handle_comment` | Comment block |

#### Register Save Directives

| Directive | Flags | Handler | Size |
|-----------|-------|---------|------|
| `rs` / `.rs` | P\|D | `handle_rs16` | 16-bit default |
| `rs.b` / `.rs.b` | P\|D | `handle_rs8` | 8-bit |
| `rs.w` / `.rs.w` | P\|D | `handle_rs16` | 16-bit |
| `rs.l` / `.rs.l` | P\|D | `handle_rs32` | 32-bit |
| `rs.q` / `.rs.q` | P | `handle_rs64` | 64-bit |
| `rs.s` / `.rs.s` | P | `handle_rs32` | 32-bit float |
| `rs.d` / `.rs.d` | P | `handle_rs64` | 64-bit float |
| `rs.x` / `.rs.x` | P | `handle_rs96` | 96-bit float |
| `rs.p` / `.rs.p` | P | `handle_rs96` | Packed decimal |
| `so` | P | `handle_rs16` | 16-bit default |
| `so.b` | P | `handle_rs8` | 8-bit |
| `so.w` | P | `handle_rs16` | 16-bit |
| `so.l` | P | `handle_rs32` | 32-bit |
| `so.q` | P | `handle_rs64` | 64-bit |
| `so.s` | P | `handle_rs32` | 32-bit float |
| `so.d` | P | `handle_rs64` | 64-bit float |
| `so.x` | P | `handle_rs96` | 96-bit float |
| `so.p` | P | `handle_rs96` | Packed decimal |
| `fo` | P | `handle_fo16` | 16-bit default |
| `fo.b` | P | `handle_fo8` | 8-bit |
| `fo.w` | P | `handle_fo16` | 16-bit |
| `fo.l` | P | `handle_fo32` | 32-bit |
| `fo.q` | P | `handle_fo64` | 64-bit |
| `fo.s` | P | `handle_fo32` | 32-bit float |
| `fo.d` | P | `handle_fo64` | 64-bit float |
| `fo.x` | P | `handle_fo96` | 96-bit float |
| `fo.p` | P | `handle_fo96` | Packed decimal |
| `rsreset` / `.rsreset` | P\|D | `handle_rsreset` | Reset register save |
| `rsset` / `.rsset` | P\|D | `handle_rsset` | Set register save |
| `setso` | P | `handle_rsset` | Set save offset |
| `rseven` | 0 | `handle_rseven` | Align register save |
| `clrso` | P | `handle_rsreset` | Clear save offset |
| `setfo` | P | `handle_setfo` | Set fill offset |
| `clrfo` | P | `handle_clrfo` | Clear fill offset |

#### Debug Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `symdebug` | P | `eol` | Symbol debug (no-op) |
| `dsource` | P | `handle_dsource` | Debug source |
| `debug` | P | `handle_debug` | Debug info |
| `msource` | 0 | `handle_msource` | Macro source |
| `vdebug` | 0 | `handle_vdebug` | Verbose debug |

#### Output Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `echo` | P | `handle_echo` | Print message |
| `printt` | 0 | `handle_printt` | Print text |
| `printv` | 0 | `handle_printv` | Print value |
| `showoffset` | P | `handle_showoffset` | Show offset |
| `output` / `.output` | P\|D | `handle_output` | Output format |

#### Structure Directives

(Conditional on `#if STRUCT`)

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `struct` | 0 | `handle_struct` | Begin structure |
| `estruct` | 0 | `handle_endstruct` | End structure |

#### Other Directives

| Directive | Flags | Handler | Description |
|-----------|-------|---------|-------------|
| `cargs` / `.cargs` | P\|D | `handle_cargs` | C arguments |
| `local` | 0 | `handle_local` | Local label character |
| `module` / `.module` | P\|D | `handle_idnt` | Module name |
| `dr` | 0 | `handle_reldata16` | Relocatable data (16-bit) |
| `dr.b` | 0 | `handle_reldata8` | Relocatable data (8-bit) |
| `dr.w` | 0 | `handle_reldata16` | Relocatable data (16-bit) |
| `dr.l` | 0 | `handle_reldata32` | Relocatable data (32-bit) |
| `load` | P | `handle_dummy_expr` | Load address |
| `jumperr` | 0 | `handle_dummy_expr` | Jump error |
| `jumpptr` | 0 | `handle_dummy_expr` | Jump pointer |
| `auto` | 0 | `handle_noop` | No operation |
| `mask2` | 0 | `eol` | Mask 2 (no-op) |
| `spc` | P\|D | `handle_dummy_cexpr` | Space control |

## Key Functions in `syntax/mot/syntax.c`

### Directive Parsing

| Function | Lines | Description |
|----------|-------|-------------|
| `fequate()` | ~2074 | Handle FEQU directive with optional suffix (.s, .d, .x, .p) |

Note: EQU and SET directives are handled inline in the main parsing loop, not as standalone functions. EQU uses `new_equate()` (line ~2274) and SET uses `new_abs()` (line ~2311).

### Data Handlers

| Function | Lines | Description |
|----------|-------|-------------|
| `handle_data()` | ~697 | Parse data operands for dc, db, dw, dl |
| `handle_d8()` | ~737 | Handle 8-bit data |
| `handle_d16()` | ~743 | Handle 16-bit data |
| `handle_d32()` | ~749 | Handle 32-bit data |
| `handle_d64()` | ~755 | Handle 64-bit data |
| `handle_f32()` | ~762 | Handle 32-bit float |
| `handle_f64()` | ~768 | Handle 64-bit float |
| `handle_f96()` | ~774 | Handle 96-bit float |
| `handle_fpd()` | ~780 | Handle packed decimal |

### Space Allocation Handlers

| Function | Lines | Description |
|----------|-------|-------------|
| `handle_spc8()` | ~895 | 8-bit space |
| `handle_spc16()` | ~901 | 16-bit space |
| `handle_spc32()` | ~907 | 32-bit space |
| `handle_spc64()` | ~913 | 64-bit space |
| `handle_spc96()` | ~919 | 96-bit space |

### Block Fill Handlers

| Function | Lines | Description |
|----------|-------|-------------|
| `handle_blk8()` | ~925 | 8-bit block fill |
| `handle_blk16()` | ~931 | 16-bit block fill |
| `handle_blk32()` | ~937 | 32-bit block fill |
| `handle_blk64()` | ~943 | 64-bit block fill |
| `handle_blk96()` | ~949 | 64-bit block fill |

## Navigation Tips

1. **Find an instruction**: Search `cpus/m68k/opcodes.h` for the mnemonic string. The lines will show all operand combinations and CPU support.
2. **Find a directive**: Search `syntax/mot/syntax.c` for the directive name in the directive table (lines 1823-2023). The handler function reveals the implementation.
3. **Find an operand type**: Search `cpus/m68k/operands.h` for the operand code enum (e.g., `D_`, `A_`, `IM`). The struct array shows valid addressing modes for each type.
4. **Find a CPU model**: Check `cpus/m68k/cpu_models.h` for the CPU name and its model flag. Use the model flag to search `opcodes.h` for supported instructions.
5. **Find addressing modes**: Check `cpus/m68k/operands.h` `addrmodes[]` array (lines 1-18) for the mode index and description.
6. **Check equate directives**: Look at the main parsing loop in `syntax.c` (~line 2274 for EQU, ~line 2311 for SET) — equate directives are not in the main directive table. The `fequ` directive uses the `fequate()` function (~line 2074).
7. **Check include string formats**: Look at `parse_name()` in `parse.c` (~line 137) — the angle-bracket form is M68K-specific.
