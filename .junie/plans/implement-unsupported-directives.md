---
sessionId: session-261025-143028-1ebw
---

# Requirements

### Overview

Implement parser support for all assembler directives currently listed as unsupported in `docs/known_issues.md`. Each directive needs a lexer token, a BNF grammar rule, and a parser test. The goal is to eliminate false-positive "Cannot resolve macro" errors when these directives appear in source files.

### Unsupported Directives (from known_issues.md)

The following directives are listed as unsupported:

| Directive | Assembler | Syntax | Complexity |
|-----------|-----------|--------|------------|
| `elif` | DevPac | `elif expression` | Trivial |
| `entry` | vasm | `entry label` | Trivial |
| `extrn` | vasm | `extrn label [, label...]` | Trivial |
| `global` | vasm | `global label [, label...]` | Trivial |
| `export` | vasm | `export label [, label...]` | Trivial |
| `import` | vasm | `import label [, label...]` | Trivial |
| `weak` | vasm | `weak label [, label...]` | Trivial |
| `nref` | PhxAss | `nref label [, label...]` | Trivial |
| `showoffset` | PhxAss | `showoffset` | Trivial |
| `symdebug` | PhxAss | `symdebug` | Trivial |
| `vdebug` | vasm | `vdebug` | Trivial |
| `dsource` | PhxAss | `dsource` | Trivial |
| `debug` | PhxAss | `debug` | Trivial |
| `optc` | PhxAss | `optc` | Trivial |
| `public` | PhxAss | `public label [, label...]` | Trivial |
| `rorg` | Devpac, PhxAss | `rorg [expression]` | Trivial |
| `sdreg` | vasm | `sdreg register` | Simple |
| `equrl` | vasm | `equrl label expression` | Simple |
| `fequ` | vasm | `fequ label expression [.s/.d/.x/.p]` | Simple |
| `fequr` | vasm | `fequr label register [.s/.d/.x/.p]` | Simple |
| `fequurl` | vasm | `fequurl label expression [.s/.d/.x/.p]` | Simple |
| `freg` | vasm | `freg label register` | Simple |
| `image` | vasm | `image filename` | Simple |
| `local` | vasm | `local char` | Simple |
| `iif` | DevPac | `iif expression` | Simple |
| `mcf5xxx` | vasm | `mcf5200` etc. | Simple |
| `cargs` | DevPac, PhxAss | `cargs arg1 [, arg2...]` | Simple |
| `comm` | vasm | `comm label size [.size]` | Moderate |
| `comment` | DevPac, PhxAss | `comment delim ... delim` | Moderate |
| `common` | vasm | `common label size [.size]` | Moderate |
| `db`/`dw`/`dl` | vasm | `db/dw/dl expr [, expr...]` | Moderate |
| `module` | DevPac, PhxAss | `module name` | Moderate |
| `struct`/`estruct` | vasm | `struct [name]` / `estruct` | Complex |
| `rept` (iterator) | vasm | `rept count iterator` | Enhancement |
| `xref.l` | vasm | `xref.l label` | Enhancement |
| Floating-point suffixes | vasm | `.s/.d/.x/.p` on dc/ds/etc. | Enhancement |

### Sorting by Complexity (Ascending)

**Group 1 — Trivial (no operands or single expression):** `elif`, `entry`, `extrn`, `global`, `export`, `import`, `weak`, `nref`, `showoffset`, `symdebug`, `vdebug`, `dsource`, `debug`, `optc`, `public`, `rorg`

**Group 2 — Simple (one operand or label):** `sdreg`, `equrl`, `fequ`, `fequr`, `fequurl`, `freg`, `image`, `local`, `iif`, `mcf5xxx`, `cargs`

**Group 3 — Moderate (multiple operands or special syntax):** `comm`, `comment`, `common`, `db`/`dw`/`dl`, `module`

**Group 4 — Complex or Enhancement:** `struct`/`estruct`, `rept` iterator, `xref.l`, floating-point suffixes

### Goals

- Eliminate false-positive errors for all listed directives
- Follow existing patterns in the lexer, BNF, and test data
- Each directive must parse without syntax errors

### Out of Scope

- Semantic evaluation of directives (e.g., actual struct layout computation)
- Runtime behavior simulation
- Cross-file resolution improvements

# Technical Design

### Current Implementation

The plugin uses:
- **Lexer** (`src/grammar/_M68kLexer.flex`): JFlex lexer with states (`YYINITIAL`, `IN_INSTRUCTION`, `IN_OPERAND`, `STRING_DIRECTIVE`, `AFTER_OPERAND`). Directive keywords are recognized in the `<IN_INSTRUCTION>` state and transition to the appropriate next state.
- **Parser** (`src/grammar/m68k.bnf`): Grammar-Kit BNF. Directives are defined under the `directives` choice rule (line 1216). Each directive gets a `*_directive` rule. The BNF header auto-applies `M68kDirective` interface to all `*_directive` rules.
- **Token types** (`src/com/yanncebron/m68kplugin/lang/psi/M68kTokenTypes.java`): declares token types for each keyword.
- **Tests** (`testData/parser/directives/`): text-based parser tree snapshots.

### Key Decisions

- **Follow existing patterns**: Each new directive will mirror an existing similar directive (e.g., `elif` mirrors `elseif`, `extrn` mirrors `xdef`, `db` mirrors `dc`).
- **No semantic evaluation**: The parser only recognizes syntax. Evaluation logic is out of scope.
- **Group by complexity**: Implement trivial directives first, then simple, moderate, and complex.
- **`elif` as alias**: `elif` is a DevPac alias for `elseif` — implement as a separate lexer token and BNF rule, but reuse the same PSI structure.
- **`db`/`dw`/`dl` as aliases**: These are vasm aliases for `dc.b`, `dc.w`, `dc.l` — implement as separate directives that share the `dc_directive` grammar pattern.
- **Floating-point suffixes**: Add `DATA_SIZE_S`, `DATA_SIZE_D`, `DATA_SIZE_X`, `DATA_SIZE_P` lexer patterns and extend `data_size_all` in the BNF.

### Proposed Changes

#### Group 1 — Trivial Directives

**No-operand directives** (`showoffset`, `symdebug`, `vdebug`, `dsource`, `debug`, `optc`):
- Add token type in `M68kTokenTypes.java`
- Add lexer rule in `_M68kLexer.flex` (transition to `AFTER_OPERAND`)
- Add BNF rule: `showoffset_directive ::= SHOWOFFSET`
- Add to `directives` choice rule
- Add test file

**Label-list directives** (`extrn`, `global`, `export`, `import`, `weak`, `nref`, `public`):
- Pattern: `directive label [, label...]`
- Mirror `xdef_directive`: `xdef_directive ::= XDEF tail_expression_list`
- Add token, lexer rule (transition to `IN_OPERAND`), BNF rule, test

**Single-expression directives** (`entry`, `rorg`):
- Pattern: `directive expression?`
- Mirror `org_directive`: `org_directive ::= ORG expression`

**`elif`** (DevPac alias for `elseif`):
- Add to conditional assembly parser (separate parser class `M68kConditionalAssemblyParser`)
- Mirror `elseif_conditional_assembly_directive`

#### Group 2 — Simple Directives

**`sdreg`**: `sdreg_directive ::= SDREG adm_register_list`
**`equrl`**: `equrl_directive ::= label EQUURL expression` (mirror `equ_directive`)
**`fequ`/`fequr`/`fequurl`**: Mirror `fo_directive` pattern with optional data size
**`freg`**: `freg_directive ::= label FREG adm_register_list`
**`image`**: `image_directive ::= IMAGE include_path` (mirror `incbin_directive`)
**`local`**: `local_directive ::= LOCAL ID`
**`iif`**: Add to conditional assembly parser, mirror `if_conditional_assembly_directive`
**`mcf5xxx`**: Add CPU model directives (mirror `mc68000_directive`)
**`cargs`**: `cargs_directive ::= CARGS tail_expression_list`

#### Group 3 — Moderate Directives

**`comm`/`common`**: `comm_directive ::= COMM label expression data_size_all?`
**`comment`**: Special lexer state (like `rem`/`erem`), consume until delimiter
**`db`/`dw`/`dl`**: Mirror `dc_directive` with fixed data size
**`module`**: `module_directive ::= MODULE STRING` (mirror `ttl_directive`)

#### Group 4 — Complex / Enhancement

**`struct`/`estruct`**: `struct_directive ::= STRUCT ID?`, `estruct_directive ::= ESTRICT`
**`rept` iterator**: Extend `rept_directive ::= REPT expression (ID)?`
**`xref.l`**: Extend `xref_directive` to accept optional data size
**Floating-point suffixes**: Extend `data_size_all` to include `.s`, `.d`, `.x`, `.p`

# Delivery Steps

###   Step 1: Group 1: Trivial no-operand and label-list directives
Add lexer tokens, BNF rules, and tests for: `showoffset`, `symdebug`, `vdebug`, `dsource`, `debug`, `optc`, `extrn`, `global`, `export`, `import`, `weak`, `nref`, `public`. These follow the simplest patterns (no operands or label lists).

###   Step 2: Group 1 continued: Single-expression and conditional directives
Add `entry`, `rorg`, and `elif` (DevPac alias for elseif in conditional assembly parser). These require a single expression or conditional assembly integration.

###   Step 3: Group 2: Simple single-operand directives
Add `sdreg`, `equrl`, `fequ`, `fequr`, `fequurl`, `freg`, `image`, `local`, `iif`, `mcf5xxx` CPU directives, and `cargs`. These require one operand (register, expression, or string).

###   Step 4: Group 3: Moderate multi-operand directives
Add `comm`, `common`, `comment` (with special lexer state), `db`/`dw`/`dl` (DC aliases), and `module`. These require multiple operands or special lexer handling.

###   Step 5: Group 4: Complex directives and enhancements
Add `struct`/`estruct`, extend `rept` with optional iterator name, extend `xref` with `.l` suffix support, and add floating-point suffixes (`.s`/`.d`/`.x`/`.p`) to data size directives.

###   Step 6: Finalize: Update documentation and verify
Update `docs/known_issues.md` to remove implemented directives, regenerate the parser, run all tests, and verify no regressions.