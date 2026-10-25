---
sessionId: session-261025-163736-1ajx

## Status

- [x] Investigated project structure and existing condition code implementation
- [x] Selected Variant 1 (enrich hover docs with Boolean formulas)
- [ ] Implement ConditionFormula lookup table
- [ ] Wire formulas into hover documentation
- [ ] Add tests

## Requirements

### Overview

Add Boolean formulas from PRM TABLE 3-18 to the hover documentation for instructions that have result-derived condition codes (marked with `?` in the PRM). The formulas will appear below the existing condition code table and text explanations.

### Scope

**In Scope:**
- Store Boolean formulas for V and C condition codes per instruction category
- Display formulas in hover docs below the existing condition code section
- Cover all instruction categories from TABLE 3-18 that have non-trivial formulas

**Out of Scope:**
- Reference documentation pages
- Tool windows or interactive explorers
- TABLE 3-19 (conditional branch tests)
- Inline replacement of text explanations with formulas

### User Value

Users hovering over an instruction like `ADD` will see the exact Boolean formulas for overflow and carry computation, instead of only the generic "From result" text.

## Technical Design

### Current Implementation

The plugin stores condition code effects as a 5-character pattern in `M68kMnemonic.ConditionCodes` (`src/com/yanncebron/m68kplugin/lang/psi/M68kMnemonic.java`). The `ConditionCodes` class holds five `Code` enum values (X, N, Z, V, C) and provides:

- `getDisplayIds()` — returns a list of display characters for the HTML table
- `getDisplayTexts()` — returns a list of explanation strings from `M68kBundle.properties`

The hover docs generator (`M68kInstructionMnemonicDocsGenerator.java`) renders the condition code section in `appendConditionalCodesSection()`:
1. An HTML table with X/N/Z/V/C headers and symbol values
2. Text lines below the table: `X – Not affected`, `N – From result (usually if negative)`, etc.

The `M68kMnemonic` record carries `affected` and `tested` ConditionCodes instances. These are populated from `M68kMnemonicRegistryRuntimeData.txt` via `M68kMnemonicRegistryRuntimeParser` during registry generation.

Multiple mnemonics share the same condition code pattern. For example, `ADD`, `ADDI`, `ADDQ`, and `CMP` all use `C****` or `-****` patterns. The formulas from TABLE 3-18 are shared at the instruction category level, not per-mnemonic.

### Key Decisions

**Decision 1: Formula lookup by mnemonic element type**

Formulas are stored in a lookup map keyed by `IElementType` (the mnemonic token type). This avoids modifying the `ConditionCodes` class and keeps formulas separate from the 5-char pattern data. Multiple mnemonics that share formulas (e.g., ADD/ADDI/ADDQ) map to the same formula entry.

**Decision 2: Formulas stored as static constants**

A new `ConditionCodeFormulas` class holds the formulas as `@Nullable String` constants for V and C. The class provides a `findByMnemonic(IElementType)` static method that returns the appropriate formula pair. This approach:
- Keeps formulas in a single, maintainable location
- Avoids adding formula data to the runtime data file (which would require parser changes)
- Allows precise control over which mnemonics get which formulas

**Decision 3: Plain-text Boolean notation in hover docs**

Formulas use the PRM notation with Unicode symbols:
- `·` for AND
- `∨` for OR
- `'` for NOT (apostrophe, as in the PRM)
- Example: `V = Sm·Dm·Rm' ∨ Sm'·Dm'·Rm`

This matches the PRM exactly and avoids introducing LaTeX rendering dependencies.

### Proposed Changes

#### 1. Create `ConditionCodeFormulas` class

**File:** `src/com/yanncebron/m68kplugin/lang/psi/ConditionCodeFormulas.java`

This class provides a lookup from mnemonic element types to their V and C Boolean formulas.

```
record ConditionCodeFormulas(@Nullable String v, @Nullable String c) {}

class ConditionCodeFormulas {
  // Operand notation legend (for hover docs)
  static final String OPERAND_NOTATION = "...";

  // Formula groups (shared by instruction families)
  private static final ConditionCodeFormulas ARITH_ADD = new ConditionCodeFormulas(
    "V = Sm·Dm·Rm' ∨ Sm'·Dm'·Rm",
    "C = Sm·Dm' ∨ Rm'·Dm' ∨ Sm'·Rm'"
  );
  private static final ConditionCodeFormulas ARITH_SUB = new ConditionCodeFormulas(
    "V = Sm·Dm·Rm' ∨ Sm'·Dm'·Rm",
    "C = Sm·Dm' ∨ Rm'·Dm' ∨ Sm'·Rm'"
  );
  // ... other formula groups

  // Lookup map
  private static final Map<IElementType, ConditionCodeFormulas> FORMULA_MAP;

  static {
    Map<IElementType, ConditionCodeFormulas> map = new HashMap<>();
    // Arithmetic: ADD family
    map.put(M68kTokenTypes.ADD, ARITH_ADD);
    map.put(M68kTokenTypes.ADDI, ARITH_ADD);
    map.put(M68kTokenTypes.ADDQ, ARITH_ADD);
    // ... all other mnemonics
    FORMULA_MAP = Collections.unmodifiableMap(map);
  }

  public static @Nullable ConditionCodeFormulas findByMnemonic(IElementType type) {
    return FORMULA_MAP.get(type);
  }
}
```

#### 2. Populate formula map with TABLE 3-18 data

The formulas are grouped by operation family. The following groups cover all TABLE 3-18 categories:

| Group | Mnemonics | V Formula | C Formula |
|-------|-----------|-----------|-----------|
| ARITH_ADD | ADD, ADDI, ADDQ | `Sm·Dm·Rm' ∨ Sm'·Dm'·Rm` | `Sm·Dm' ∨ Rm'·Dm' ∨ Sm'·Rm'` |
| ARITH_SUB | SUB, SUBI, SUBQ | Same as ADD | Same as ADD |
| ARITH_ADDX | ADDX | `Sm·Dm·Rm' ∨ Sm'·Dm'·Rm` | `Sm·Dm' ∨ Rm'·Dm' ∨ Sm'·Rm' ∨ X` |
| ARITH_SUBX | SUBX | Same as ADDX | Same as ADDX |
| ARITH_NEG | NEG | `Dm·Rm'` | `Dm'` |
| ARITH_NEGX | NEGX | `Dm·Rm'` | `Dm' ∨ X` |
| CMP | CMP, CMPA, CMPI, CMPM | Same as ADD | Same as ADD |
| CHK | CHK | `U` (undefined) | `U` (undefined) |
| ASL/LSL | ASL, LSL | `Dm·Dm-1' ∨ ... ∨ Dm-r' ∨ Dm·(Dm-1' ∨ ... ∨ Dm-r')` | `Dm-r+1` |
| ASR/LSR | ASR, LSR | `Dm·Dm-1' ∨ ... ∨ Dm-r' ∨ Dm·(Dm-1' ∨ ... ∨ Dm-r')` | `Dm-r+1` |
| ROL | ROL | `—` (cleared) | `Dm-r+1` |
| ROR | ROR | `—` (cleared) | `Dm-r+1` |
| ROXL | ROXL | `—` (cleared) | `Dm-r+1` |
| ROXR | ROXR | `—` (cleared) | `Dm-r+1` |
| ABCD | ABCD | `U` (undefined) | `U` (undefined) |
| SBCD | SBCD | `U` (undefined) | `U` (undefined) |
| NB CD | NB CD | `U` (undefined) | `U` (undefined) |
| DIVS/DIVU | DIVS, DIVU | `—` (cleared) | `—` (cleared) |
| TBL | TBLS, TBLSN, TBLU, TBLUN | `—` (cleared) | `—` (cleared) |

Note: Groups where V and C are always cleared (`0`) or not affected (`-`) do not need formula entries. Only groups where V or C is `*` (from result) require formula entries. The patterns that need formulas are:

- `C****` / `-****`: V=`*`, C=`*` → needs both V and C formulas (ADD, SUB, CMP families)
- `C*A**`: V=`*`, C=`*` → needs both formulas, C includes X (ADDX, SUBX, NEGX)
- `*****`: V=`*`, C=`*` → needs both formulas (shift operations)
- `***0*`: V=`0` (cleared), C=`*` → needs C formula only (ASL, LSL, ASR, LSR)
- `CUAU*`: V=`U`, C=`U` → undefined, no formula (ABCD, SBCD, NB CD)
- `-***0`: V=`*`, C=`0` → needs V formula only (DIVS, DIVU, TBL family)

#### 3. Update `M68kInstructionMnemonicDocsGenerator`

Modify `appendConditionalCodesSection()` to append formula text after the existing condition code explanations when formulas are available.

The method currently:
1. Renders the HTML table with condition code symbols
2. Renders text explanations below the table

The change adds:
3. After the text explanations, look up formulas by the mnemonic's element type
4. If formulas exist, append a `<blockquote>` or similar section with the formulas
5. Include an operand notation legend on first use

Pseudocode:
```
private void appendConditionalCodesSection(String sectionName,
                                           M68kMnemonic.ConditionCodes value,
                                           boolean skipIfNoneAffected) {
  // ... existing code ...
  
  // Append formulas if available
  ConditionCodeFormulas formulas = ConditionCodeFormulas.findByMnemonic(mnemonicElementType);
  if (formulas != null && (formulas.v() != null || formulas.c() != null)) {
    sb.append("<blockquote>");
    if (formulas.v() != null) {
      sb.append("<code>V = ").append(formulas.v()).append("</code><br/>");
    }
    if (formulas.c() != null) {
      sb.append("<code>C = ").append(formulas.c()).append("</code><br/>");
    }
    sb.append("</blockquote>");
  }
  
  sb.append(DocumentationMarkup.SECTION_END);
}
```

#### 4. Add operand notation legend

Add a bundle message for the operand notation legend:
```
documentation.condition.codes.operand.notation=
Rm = Result Operand (MSB), Rm' = NOT Rm, Sm = Source Operand (MSB), Dm = Destination Operand (MSB), r = Shift count
```

Display this legend once, before the first formula block, or as a tooltip-style note.

### File Structure

| File | Action |
|------|--------|
| `src/.../lang/psi/ConditionCodeFormulas.java` | **Add** — Formula lookup class |
| `src/.../documentation/M68kInstructionMnemonicDocsGenerator.java` | **Modify** — Append formulas in hover docs |
| `plugin-resources/messages/M68kBundle.properties` | **Modify** — Add operand notation message |
| `tests/.../lang/psi/M68kMnemonicConditionCodesTest.java` | **Modify** — Add formula lookup tests |
| `tests/.../documentation/M68KInstructionDocumentationTargetProviderTest.java` | **Modify** — Update hover doc assertions |

### Risks

1. **Formula accuracy:** The formulas must match the PRM exactly. Each formula should be verified against TABLE 3-18.
2. **Readability:** Long formulas (especially for shift operations) may wrap in narrow editors. The blockquote format should handle wrapping.
3. **Maintenance:** New mnemonics added in the future need formula entries. The lookup map should provide a clear pattern for additions.

### Testing

1. **Unit tests** for `ConditionCodeFormulas.findByMnemonic()` — verify each mnemonic maps to the correct formulas.
2. **Hover doc tests** — update existing assertions in `M68KInstructionDocumentationTargetProviderTest` to include formula text.
3. **Edge cases** — verify that mnemonics without formulas (e.g., `AND`, `UNLK`) do not show a formula section.

