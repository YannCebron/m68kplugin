---
sessionId: session-261025-174325-zvg7
---

# Requirements

### Overview & Goals

Create a `DirectivesRegistry` that tracks assembler compatibility, parameter metadata, and other details for each assembler directive, similar to how `MnemonicRegistry` tracks CPU support and operand types for each instruction mnemonic.

### Scope

**In Scope:**
- New `M68kDirectiveParameterGroup` record (groups parameters into alternative forms)
- New `M68kDirectiveEntry` record (metadata container for each directive including parameter groups)
- New `M68kDirectiveParameter` record (describes a single directive parameter: name, type, valid values)
- New `M68kDirectivesRegistry` singleton (lookup API + builder + populated data)
- Update `M68kDirectivesInspection` to use the registry instead of hard-coded checks
- Update `M68kDirectivesBrowserPane` to show assembler compatibility
- Update `M68kDirectiveDocumentationProvider` to include assembler info
- Enable completion and validation for directive parameters (especially `opt`)
- Add unit tests

**Out of Scope:**
- Generator test from vasm sources (directives are fewer and more stable than mnemonics; data will be hand-populated initially)
- Runtime parser for directive metadata
- Adding new directives to the lexer or parser (26 vasm directives exist but are not in the plugin's grammar yet; they are tracked separately)

### Functional Requirements

- The registry must expose `findAll(IElementType)` and `find(IElementType)` methods
- The registry must track which assemblers support each directive (using the existing `M68kAssembler` enum)
- The registry must track deprecated directives
- The registry must track directive parameters: name, type (expression, string, identifier, enum), and valid values when limited
- The inspection must report unsupported directives based on the configured assembler
- The inspection must validate directive parameter names and values against the registry
- The browser pane must display assembler compatibility information
- Documentation must include assembler support and parameter details
- Code completion must suggest valid parameter names and values for directives


# Technical Design

### Current Implementation

**MnemonicRegistry pattern** (`src/.../lang/psi/M68kMnemonicRegistry.java`):
- Singleton with `getInstance()`, `findAll(IElementType)`, and `find(M68kInstruction)` methods
- Uses a `MnemonicBuilder` inner class with fluent API (`dataSizes()`, `first()`, `second()`, `cpus()`, `deprecated()`, `privileged()`, `controlFlow()`, `affected()`, `tested()`, `build()`)
- Private constructor populates a `MultiMap<IElementType, M68kMnemonic>` with ~244 entries
- `freezeMnemonics()` converts to immutable `Map<IElementType, List<M68kMnemonic>>`
- Data generated from vasm `cpus/m68k/opcodes.h` via `M68kMnemonicRegistryGeneratorTest`

**M68kMnemonic record** (`src/.../lang/psi/M68kMnemonic.java`):
- Fields: `elementType`, `dataSizes`, `firstOperand`, `secondOperand`, `cpus`, `deprecated`, `privilegedType`, `controlFlow`, `affected`, `tested`
- `Set<M68kCpu> cpus` tracks CPU compatibility (e.g., `GROUP_68000_UP`, `GROUP_68020_UP`, `GROUP_CPU32`)

**Existing M68kAssembler enum** (`src/.../assembler/M68kAssembler.java`):
- Values: `ANY`, `BASM`, `PHX_ASS`, `DEVPAC`, `VASM`

**Current directive handling:**
- Directives are raw token types in `M68kTokenGroups.DIRECTIVES` (~80 entries) and `CONDITIONAL_ASSEMBLY_DIRECTIVES` (~25 entries)
- `M68kDirectivesInspection` hard-codes `auto` and `mask2` as unsupported
- `M68kDirectivesBrowserPane` iterates token types directly, no metadata
- `M68kDirectiveDocumentationProvider` loads docs from markdown files, no assembler info

### Key Decisions

1. **Hand-populated data vs. generator**: Directives are fewer (~105 total) and more stable than mnemonics (~340). Hand-populating with a builder pattern is sufficient and avoids the complexity of a vasm-based generator for directives.

2. **Parameter metadata in the registry**: Each directive entry tracks a list of `M68kDirectiveParameterGroup` records. Each group contains an ordered list of `M68kDirectiveParameter` records. When a directive has multiple groups, the groups represent alternative parameter forms (either A or B). Most directives have a single group (linear parameter sequence). This enables completion and validation for all forms.

3. **Parameter variants support**: Some directives accept different sets of arguments depending on context. For example:
   - `opt` accepts either `name=value` or `name+`/`name-` forms
   - `reg` accepts either a register list or an immediate value
   - `section` accepts either an ID or a STRING for the section name
   - `machine` accepts either a DEC_NUMBER or an ID
   The registry tracks these as separate `M68kDirectiveParameterGroup` entries within a single directive.

4. **M68kDirectiveParameterType enum**: Defines the kinds of values a parameter accepts:
   - `EXPRESSION` — arithmetic expression (e.g., `cnop` alignment)
   - `STRING` — quoted string (e.g., `include` path)
   - `IDENTIFIER` — plain identifier (e.g., `opt` parameter name)
   - `ENUM` — limited set of valid values (e.g., `opt` parameter names from vasm)
   - `NUMBER` — numeric literal
   - `REGISTER` — M68k register reference

5. **M68kAssembler.ANY as default**: Directives supported by all known assemblers will use `Set.of(M68kAssembler.ANY)`. Vasm-specific directives will use `Set.of(M68kAssembler.VASM)`.

6. **Optional parameters**: Parameters can be marked as optional (e.g., `offset` in `incbin`, expression in `offset_directive`). The `required` field in `M68kDirectiveParameter` tracks this. Optional parameters appear at the end of a group.

### Proposed Changes

**New: `M68kDirectiveParameterType` enum**
```java
public enum M68kDirectiveParameterType {
    EXPRESSION,    // arithmetic expression
    STRING,        // quoted string literal
    IDENTIFIER,    // plain identifier
    ENUM,          // limited set of named values
    NUMBER,        // numeric literal
    REGISTER       // M68k register reference
}
```

**New: `M68kValidValue` record**
```java
public record M68kValidValue(
    @NotNull String value,
    @NotNull String description
) {
}
```

**New: `M68kDirectiveParameter` record**
```java
public record M68kDirectiveParameter(
    @NotNull String name,
    @NotNull M68kDirectiveParameterType type,
    @Nullable List<M68kValidValue> validValues,
    boolean required,
    @Nullable String description
) {
    /** Returns the list of valid values, or empty if any value of the given type is accepted. */
    public @NotNull List<M68kValidValue> getValidValues() {
        return validValues != null ? validValues : List.of();
    }
}
```

**New: `M68kDirectiveParameterGroup` record**
```java
public record M68kDirectiveParameterGroup(
    @NotNull List<M68kDirectiveParameter> parameters,
    @Nullable String description
) {
    /** Returns the ordered list of parameters in this variant. */
    public @NotNull List<M68kDirectiveParameter> getParameters() {
        return parameters;
    }
}
```
A group represents one valid way to call a directive. When a directive has multiple groups, each group is an alternative form (either A or B). Most directives have a single group.

**New: `M68kDirectiveEntry` record**
```java
public record M68kDirectiveEntry(
    IElementType elementType,
    Set<M68kAssembler> assemblers,
    boolean deprecated,
    List<M68kDirectiveParameterGroup> parameterGroups
) {
    public boolean isSupported(M68kAssembler assembler) {
        return assemblers.contains(M68kAssembler.ANY) || assemblers.contains(assembler);
    }

    /** Returns all parameter groups, or empty if no parameters defined. */
    public @NotNull List<M68kDirectiveParameterGroup> getParameterGroups() {
        return parameterGroups != null ? parameterGroups : List.of();
    }

    /** Returns the first (and typically only) parameter group. */
    public @Nullable M68kDirectiveParameterGroup getFirstGroup() {
        return parameterGroups != null && !parameterGroups.isEmpty() ? parameterGroups.get(0) : null;
    }

    /** Find a parameter by name across all groups. */
    public @Nullable M68kDirectiveParameter findParameter(String name) { ... }

    /** Returns true if this directive has multiple parameter variants. */
    public boolean hasMultipleVariants() {
        return parameterGroups != null && parameterGroups.size() > 1;
    }
}
```

**New: `M68kDirectivesRegistry` singleton**
- `getInstance()` — static accessor
- `findAll(IElementType)` — return all entries for a token type
- `find(IElementType)` — return the single entry (directives have one entry per token, unlike mnemonics with multiple operand variants)
- `isSupported(IElementType, M68kAssembler)` — convenience check
- `isDeprecated(IElementType)` — convenience check
- `findParameter(IElementType, String)` — find a parameter definition for a directive
- Private `DirectiveBuilder` with: `assemblers(Set<M68kAssembler>)`, `deprecated()`, `parameterGroup(M68kDirectiveParameterGroup)`, `parameterGroups(List<M68kDirectiveParameterGroup>)`, `build()`
- Private constructor populates all directives with their parameter metadata
- `freezeDirectives()` — convert to immutable map

**Directive categories, assembler support, and parameters:**

| Category | Assemblers | Examples | Parameters |
|---|---|---|---|
| Universal (ANY) | All assemblers | `dc`, `ds`, `dx`, `equ`, `equr`, `org`, `section`, `include`, `xdef`, `xref`, `align`, `even`, `end`, `equ`, `set`, `assert`, `ttl`, `text`, `data`, `bss`, `cseg`, `dseg`, `page`, `nopage`, `list`, `nolist`, `echo`, `fail`, `rem`, `endr`, `rept`, `if*`, `endif`, `else`, `elseif`, `endc`, `macro`, `endm`, `inline`, `einline`, `msource`, `mexit`, `load`, `rs`, `rsreset`, `rsset`, `rseven`, `output`, `far`, `near`, `initnear`, `near_code`, `offset`, `jumperr`, `jumpptr`, `fpu`, `cnop`, `spc`, `dr`, `bl`, `l`, `plen`, `llen`, `printt`, `printv`, `org`, `code`, `dr`, `idnt`, `odd`, `dseg` | Tracked per directive (see below) |
| Vasm-only (VASM) | vasm only | `auto`, `mask2`, `basereg`, `endb`, `clrfo`, `setfo`, `clrso`, `setso`, `fo`, `so`, `ac68080`, `mc68000`–`mc68060`, `cpu32`, `addwatch`, `opt`, `incdir`, `erem`, `eq` (directive form), `machine` | `opt` has named parameters with enum-type valid values |
| Deprecated | vasm only, deprecated | `bss_c`, `bss_f`, `code_c`, `code_f`, `data_c`, `data_f`, `pushsection`, `popsection` | Same as their non-deprecated counterparts |

**Directives in vasm but NOT in the plugin's grammar yet (26 total):**

These directives exist in vasm (`syntax/mot/syntax.c`) but have no token, no grammar rule, and no PSI support. They are tracked here for future reference but are **out of scope** for the registry:

| Directive | vasm Handler | Description |
|---|---|---|
| `rorg` | `handle_rorg` | Relative reorg |
| `public` | `handle_global` | Export symbol |
| `nref` | `handle_nref` | Near reference |
| `entry` | `handle_global` | Entry point |
| `extrn` | `handle_global` | External symbol |
| `global` | `handle_global` | Global symbol |
| `import` | `handle_xref` | Import symbol (PureC) |
| `export` | `handle_xdef` | Export symbol (PureC) |
| `weak` | `handle_weak` | Weak symbol |
| `comm` | `handle_comm` | Common symbol |
| `db` | `handle_d8` | 8-bit data |
| `dw` | `handle_d16` | 16-bit data |
| `dl` | `handle_d32` | 32-bit data |
| `fequ` | `fequate()` | Floating-point equate |
| `image` | `handle_incbin` | Include image file |
| `elif` | `handle_elseif` | Else if (DevPac) |
| `comment` | `handle_comment` | Comment block |
| `symdebug` | `eol` | Symbol debug (no-op) |
| `dsource` | `handle_dsource` | Debug source |
| `debug` | `handle_debug` | Debug info |
| `vdebug` | `handle_vdebug` | Verbose debug |
| `showoffset` | `handle_showoffset` | Show offset |
| `cargs` | `handle_cargs` | C arguments |
| `local` | `handle_local` | Local label character |
| `module` | `handle_idnt` | Module name |
| `struct` / `estruct` | `handle_struct` / `handle_endstruct` | Structure directives |

**Key directive parameter definitions:**

Directives with **multiple parameter groups** (alternative forms) are marked with **\***.

| Directive | Parameter Groups |
|---|---|
| `opt` **\*** | **Group 1:** `name` (ENUM), `value` (NUMBER), `scale` (NUMBER, optional) — form: `name=value[/scale]`<br>**Group 2:** `name` (ENUM), `modifier` (ENUM: `+`, `-`) — form: `name+` or `name-` |
| `section` **\*** | **Group 1:** `name` (IDENTIFIER), `type` (IDENTIFIER, optional), `alignment` (IDENTIFIER/NUMBER, optional)<br>**Group 2:** `name` (STRING), `type` (IDENTIFIER, optional), `alignment` (IDENTIFIER/NUMBER, optional) |
| `reg` **\*** | **Group 1:** `name` (IDENTIFIER), `registers` (REGISTER list)<br>**Group 2:** `name` (IDENTIFIER), `value` (EXPRESSION) |
| `machine` **\*** | **Group 1:** `cpu_type` (NUMBER) — numeric CPU model<br>**Group 2:** `cpu_type` (IDENTIFIER) — named CPU model (e.g., `mc68020`) |
| `incbin` | `path` (STRING), `offset` (EXPRESSION, optional), `length` (EXPRESSION, optional) |
| `include` | `path` (STRING) |
| `cnop` | `alignment` (EXPRESSION), `fill_value` (EXPRESSION) |
| `align` | `alignment` (EXPRESSION) |
| `dc` | `values...` (EXPRESSION, variable count) |
| `ds` | `count` (EXPRESSION) |
| `equ` | `value` (EXPRESSION) |
| `org` | `address` (EXPRESSION) |
| `assert` | `condition` (EXPRESSION), `message` (STRING, optional) |
| `rs` | `name` (IDENTIFIER), `size` (EXPRESSION, optional) |
| `macro` | `name` (IDENTIFIER), `params...` (IDENTIFIER, variable count) |
| `rept` | `count` (EXPRESSION) |
| `basereg` | `offset` (EXPRESSION), `register` (REGISTER) |
| `endb` | `register` (REGISTER) |
| `addwatch` | `address` (EXPRESSION) |
| `dc.b/.w/.l/.q/.s/.d/.x/.p` | `values...` (EXPRESSION, variable count) |
| `ds.b/.w/.l/.q/.s/.d/.x/.p` | `count` (EXPRESSION) |
| `dcb` | `count` (EXPRESSION), `value` (EXPRESSION, optional) |
| `dx` | `count` (EXPRESSION) |
| `dr` | `values...` (EXPRESSION, variable count) |
| `blk` | `count` (EXPRESSION), `value` (EXPRESSION, optional) |
| `fo` | `name` (IDENTIFIER), `size` (EXPRESSION, optional) |
| `so` | `name` (IDENTIFIER), `size` (EXPRESSION, optional) |
| `equr` | `name` (IDENTIFIER), `register` (REGISTER) |
| `xdef` | `symbols...` (IDENTIFIER, variable count) |
| `xref` | `symbols...` (IDENTIFIER, variable count) |
| `set` | `name` (IDENTIFIER), `value` (EXPRESSION) |
| `load` | `address` (EXPRESSION) |
| `jumperr` | `address` (EXPRESSION) |
| `jumpptr` | `address` (EXPRESSION) |
| `offset` | `address` (EXPRESSION, optional) |
| `output` | `format` (STRING) |
| `echo` | `message` (STRING) |
| `fail` | `message` (STRING, optional) |
| `idnt` | `string` (STRING) |
| `ttl` | `string` (STRING) |
| `plen` | `lines` (EXPRESSION) |
| `llen` | `chars` (EXPRESSION) |
| `spc` | `count` (EXPRESSION) |
| `incdir` | `path` (STRING) |
| `rsset` | `value` (EXPRESSION) |
| `printt` | `format` (STRING, optional) |
| `printv` | `values...` (EXPRESSION, variable count) |
| `msource` | `mode` (ENUM: `on`, `off`, `ON`, `OFF`) |
| `fpu` | `cpID` (EXPRESSION) |
| `near` | `register` (REGISTER, optional) |
| `setfo` | `value` (EXPRESSION) |
| `setso` | `value` (EXPRESSION) |
| `dseg` | (no parameters) |
| `odd` | (no parameters) |

**Modified: `M68kDirectivesInspection`**
- Replace hard-coded `visitAutoDirective` and `visitMask2Directive` with a generic check: look up the directive type in `M68kDirectivesRegistry`, and if the assembler set doesn't include `ANY` or the configured assembler, register a problem.
- Add validation for directive parameters: check parameter names against the registry's `ENUM`-type parameters, and report unknown parameter names.

**Modified: `M68kDirectivesBrowserPane`**
- Use `M68kDirectivesRegistry` to build the list instead of iterating token types
- Show assembler info in the cell renderer (e.g., gray text "(vasm only)" for vasm-specific directives)
- Show deprecated icon or styling for deprecated directives

**Modified: `M68kDirectiveDocumentationProvider`**
- Append assembler compatibility line to generated docs (e.g., "Supported by: vasm" or "Supported by: all assemblers")
- After loading the markdown reference doc, append a parameter section generated from `M68kDirectivesRegistry.find(directiveType).getParameterGroups()`:
  - For each parameter, render a table row with: parameter name, type description, description text, and valid values if the type is `ENUM`
  - For `ENUM`-type parameters, each valid value shows its description text (e.g., `a`: "Automatically optimize absolute to PC-relative references")
  - Example output for `opt` directive: a table listing all valid parameter names with descriptions and valid values
  - Example output for `section` directive: parameters like `name` (string), `type` (enum: code, data, bss), `alignment` (expression)
  - The parameter section appears after the markdown content, before the contribution footer
  - If no parameters are defined for a directive, skip the parameter section entirely

**New: `M68kOptDirectiveCompletionProvider` (contributor)**
- Provide completion for `opt` directive parameter names using the registry's `ENUM` valid values
- Provide completion for `+`/`-`/`=` modifiers after parameter names

### File Structure

```
src/com/yanncebron/m68kplugin/lang/psi/
  + M68kDirectiveParameterType.java      (new enum)
  + M68kDirectiveParameter.java          (new record)
  + M68kDirectiveParameterGroup.java     (new record)
  + M68kDirectiveEntry.java              (new record, was M68kDirective)
  + M68kDirectivesRegistry.java          (new singleton)
  ~ M68kDirectivesInspection.java        (modified)
  ~ M68kDirectivesBrowserPane.java       (modified)
  ~ M68kDirectiveDocumentationProvider.java  (modified)
  + M68kOptDirectiveCompletionProvider.java (new completion contributor)

tests/com/yanncebron/m68kplugin/lang/psi/
  + M68kDirectivesRegistryTest.java      (new tests)
```

### Risks

- **Assembler support accuracy**: The assembler compatibility data must be fact-checked against vasm documentation and other assembler references. Incorrect classification will cause false positives or missed inspections.
- **Directive parameter variants**: Some directives (like `opt`, `reg`, `section`, `machine`) accept different sets of arguments (either A or B). The registry tracks these as separate `M68kDirectiveParameterGroup` entries. Completion and validation must consider all groups.
- **Directive token variants**: Some directives (like `dc`, `ds`, `dx`) have multiple forms but share the same token type. The registry handles one entry per token type, which is sufficient since assembler support is the same across variants.
- **Opt parameter completeness**: The `opt` directive has many parameters in vasm. The valid values list must be extracted from vasm sources to ensure completion is accurate. Missing parameters will result in incomplete completion suggestions.
- **Parameter type accuracy**: The parameter types must match the grammar rules in `m68k.bnf`. A mismatch between registry metadata and actual parser grammar will cause completion to suggest invalid constructs or miss valid ones.
- **Missing directives**: 26 vasm directives are not in the plugin's grammar. They are documented above for future reference but are out of scope for the registry. Adding them requires lexer, grammar, and PSI changes.


# Delivery Steps

### * Step 1: Create parameter types, parameter record, directive entry, and DirectivesRegistry
Create the foundation types and registry singleton with parameter metadata support.

- Create `src/com/yanncebron/m68kplugin/lang/psi/M68kValidValue.java` as a record with fields: `String value`, `String description`.
- Create `src/com/yanncebron/m68kplugin/lang/psi/M68kDirectiveParameterType.java` as an enum with values: `EXPRESSION`, `STRING`, `IDENTIFIER`, `ENUM`, `NUMBER`, `REGISTER`.
- Create `src/com/yanncebron/m68kplugin/lang/psi/M68kDirectiveParameter.java` as a record with fields: `String name`, `M68kDirectiveParameterType type`, `List<M68kValidValue> validValues` (nullable), `boolean required`, `String description` (nullable).
- Create `src/com/yanncebron/m68kplugin/lang/psi/M68kDirectiveParameterGroup.java` as a record with fields: `List<M68kDirectiveParameter> parameters`, `String description` (nullable). A group represents one valid way to call a directive.
- Create `src/com/yanncebron/m68kplugin/lang/psi/M68kDirectiveEntry.java` as a record with fields: `IElementType elementType`, `Set<M68kAssembler> assemblers`, `boolean deprecated`, `List<M68kDirectiveParameterGroup> parameterGroups`. Include `isSupported(M68kAssembler)`, `findParameter(String)`, `getFirstGroup()`, and `hasMultipleVariants()` methods.
- Create `src/com/yanncebron/m68kplugin/lang/psi/M68kDirectivesRegistry.java` as a singleton with:
  - `getInstance()` static accessor
  - `findAll(IElementType)` to return all registered directives for a token type
  - `find(IElementType)` to return the directive entry for a given token
  - `isSupported(IElementType, M68kAssembler)` to check if a directive is supported by a specific assembler
  - `isDeprecated(IElementType)` to check if deprecated
  - `findParameter(IElementType, String)` to find a parameter definition
  - Builder pattern (`DirectiveBuilder`) with methods: `assemblers(Set<M68kAssembler>)`, `deprecated()`, `parameterGroup(M68kDirectiveParameterGroup)`, `parameterGroups(List<M68kDirectiveParameterGroup>)`, `build()`
  - Private constructor that populates the registry using the builder for each directive in `M68kTokenGroups.DIRECTIVES` and `CONDITIONAL_ASSEMBLY_DIRECTIVES`
  - `freezeDirectives()` method to convert the mutable MultiMap to an immutable map (same pattern as `freezeMnemonics`)
- Populate the registry with all ~80 regular directives and ~25 conditional assembly directives, marking assembler support and parameter metadata. Most directives use `ANY` (all assemblers). Vasm-specific directives (e.g., `auto`, `mask2`, `basereg`, `clrfo`, `setfo`, `clrso`, `setso`, `fo`, `so`, `opt`) are marked `VASM` only. Deprecated directives (e.g., `bss_c`, `bss_f`, `code_c`, `code_f`, `data_c`, `data_f`) are flagged.
- Populate `opt` directive with **two parameter groups**: Group 1 for `name=value[/scale]` form, Group 2 for `name+` or `name-` form. Each valid value includes a description text (e.g., `a`: "Automatically optimize absolute to PC-relative references", `c`: "Case-sensitivity for all symbols and macros", `o1`: "Optimize branches without an explicit size extension").
- Populate `reg` directive with **two parameter groups**: Group 1 for register list form, Group 2 for immediate value form.
- Populate `section` directive with **two parameter groups**: Group 1 for ID name, Group 2 for STRING name.
- Populate `machine` directive with **two parameter groups**: Group 1 for numeric CPU type, Group 2 for named CPU type.
- Follow the same code style, naming conventions, and copyright header as `M68kMnemonicRegistry.java`.

###   Step 2: Update consumers to use DirectivesRegistry with parameter support
Replace hard-coded checks with registry lookups, add parameter validation, and enable completion.

- Update `M68kDirectivesInspection.java`: replace hard-coded `visitAutoDirective` and `visitMask2Directive` problems with a lookup against `M68kDirectivesRegistry.isSupported()`. Register problems only when the directive is not supported by the target assembler. Add validation for `opt` directive parameter names against the registry's ENUM valid values.
- Update `M68kDirectivesBrowserPane.java`: use `M68kDirectivesRegistry` to filter and display directives. Show assembler compatibility info in the list cell renderer (similar to how `M68kMnemonicsBrowserPane` shows CPU-only mnemonics in gray). Add a toggle action to filter by assembler support.
- Update `M68kDirectiveDocumentationProvider.java`: include assembler compatibility information and parameter definitions in the generated documentation. After loading the markdown reference doc, append a parameter section from `M68kDirectivesRegistry.find(directiveType).getParameterGroups()`. For each parameter, render a table row with: parameter name, type description, and valid values (for `ENUM` type). This highlights possible arguments and their valid values directly in the quick-doc popup. For the `opt` directive, this shows all valid parameter names (e.g., `caselist`, `symdebug`) with their valid values.
- Create `src/com/yanncebron/m68kplugin/completion/M68kOptDirectiveCompletionProvider.java` as a completion contributor that provides parameter name completion for the `opt` directive using the registry's ENUM valid values.
- Add tests in `tests/com/yanncebron/m68kplugin/lang/psi/M68kDirectivesRegistryTest.java` to verify:
  - All directives from `M68kTokenGroups.DIRECTIVES` have a registry entry
  - All conditional assembly directives have a registry entry
  - Vasm-specific directives are not marked as `ANY`
  - Deprecated directives are flagged correctly
  - `opt` directive has parameters with ENUM type, valid values, and descriptions
  - Each `M68kValidValue` has a non-empty description
  - Parameter lookup methods work correctly
  - `opt` directive has two parameter groups (value form and modifier form)
  - `reg` directive has two parameter groups (register list and immediate)
  - `section` directive has two parameter groups (ID and STRING name)
  - `machine` directive has two parameter groups (numeric and named CPU)
  - `hasMultipleVariants()` returns true for opt, reg, section, machine