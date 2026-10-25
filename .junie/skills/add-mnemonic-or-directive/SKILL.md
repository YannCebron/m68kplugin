---
name: add-mnemonic-or-directive
description: Add a new M68k instruction mnemonic or assembler directive. TRIGGER when the user asks to add, implement, or support a new M68k instruction (mnemonic) or assembler directive. DO NOT TRIGGER for unrelated changes.
params:
  - name: name
    description: The name of the mnemonic or directive to add.
---

# Adding a new M68k mnemonic or directive

## Check first: no modified related files

Check for uncommitted modified files. The files relevant to this task are described in the steps below. If modified files exist, stop and ask the user to commit or discard them first.

## Check first: operand count

The mnemonic registry supports only instructions with up to two operands. If the new instruction has more, stop and ask the user to confirm. Supporting more operands requires changes to `M68kMnemonic`, `M68kMnemonicRegistry`, `M68kMnemonicRegistryRuntimeParser`, `M68kMnemonicRegistryGeneratorTest`, and all related tests.

## Check first: supported CPU

If the new mnemonic requires a CPU not yet present in `M68kCpu`, stop and ask the user to confirm. Adding a new CPU group requires changes to `M68kCpu`, `M68kMnemonicsBrowserPane`, `M68kDocsGeneratorUtil`, and all related classes. If confirmed, add the new CPU group following the existing pattern, then continue.

## Adding a new instruction mnemonic

### Step 1: Verify the scope of work

Present the mnemonic's syntax and registry entries so the user can confirm the scope.

**Mnemonic syntax:** Consult the M68000 PRM (`m68000-prm`). Quote the syntax line(s) verbatim with page number. Quote each form, supported CPU(s), privilege level, condition code effects, and operand constraints. Do not paraphrase — quote the source directly.

**Registry entries:** Look up the mnemonic in the vasm sources (`vasm`) at `cpus/m68k/opcodes.h`. Show only raw lines for supported CPUs. The data size field (e.g., `L` = long-only, `BWL` = byte/word/long) determines the BNF `data_size_*?` prefix.

**Related existing instructions:** Search the BNF for similar instructions (e.g., `ext` for `extb`) to verify expected data size and operand patterns. Use only to verify registry data — authoritative sources are the single source of truth.

Check how many types are missing using the following steps. Each missing entry requires multi-layer changes. If many are missing, inform the user about the scale. Suggest concrete mnemonics from the vasm sources that share the same missing types and are supported on the same or lower CPU version. Do not suggest mnemonics for unsupported CPUs. Present a numbered list. If none share missing types, explain why. Ask the user to select an alternative or confirm proceeding.

**Halt and confirm:** After presenting the scope analysis, stop and ask the user to either confirm continuing or select an alternative.

### Step 2: Declare the token type and add to the INSTRUCTIONS token group

In `src/com/yanncebron/m68kplugin/lang/psi/M68kTokenTypes.java`, add a token type following the existing pattern — uppercase mnemonic name, lowercase debug name, placed alphabetically.

In `src/com/yanncebron/m68kplugin/lang/psi/M68kTokenGroups.java`, add the new token type to the `INSTRUCTIONS` TokenSet. The `BCC_INSTRUCTIONS`, `DBCC_INSTRUCTIONS`, `SCC_INSTRUCTIONS`, and `CPU32_INSTRUCTIONS` groups are complete — do not add to them.

Run `M68kTokenTest` to verify the new token is registered in exactly one group with a unique debug name.

### Step 3: Add the lexer token

In `src/grammar/_M68kLexer.flex`, add a rule in the `<IN_INSTRUCTION>` state matching the mnemonic keyword. Refer to the existing rules and comments explaining the four possible state transitions:

- `AFTER_INSTRUCTION` — if the instruction extends `M68kDataSized` (has `{DATA_SIZE}?` suffix and calls `pushbackDataSizeIfPresent()`)
- `IN_OPERAND` — if the instruction takes one or more operands but no optional data size
- `STRING_DIRECTIVE` — if the first operand is a string
- `AFTER_OPERAND` — if the instruction takes no operands

Examples with optional data size: `move`, `add`, `tst`. Examples without: `nop`, `rts`, `jsr`. Place the new rule alphabetically.

### Step 4: Check for missing types

Verify that all required types are present. Follow these sub-steps in order — later steps depend on earlier ones.

1. **Check `M68kAddressMode` enum.** If a required addressing mode is missing (e.g., 68020+ 32-bit displacements, 68040+ cache control register modes), add a placeholder entry. Include the M68000 PRM reference number in the Javadoc comment (e.g., `/** Ref 2.2.1 */`). The entry needs a `notation` string synced with the BNF `name` attribute, and a PSI class reference. **Add a TODO comment** noting that `M68kAddressMode` does not yet encode supported CPUs. Then add a test in `M68kMnemonicRegistryTest` verifying the new addressing mode matches the expected PSI class.

2. **Check `M68kOperand` enum.** If a required operand is missing, add a placeholder entry. Include the corresponding vasm code in the Javadoc comment (e.g., `/** vasm: {@code XX} */`). A new operand may require new `M68kAddressMode` values — verify sub-step 1 is complete. Then add a test in `M68kMnemonicRegistryTest` verifying the new operand matches the expected addressing modes.

3. **Check `M68kDataSize` enum.** If the instruction uses a data size not yet present, add a new entry to `M68kDataSize` following the existing pattern (e.g., `BYTE(".b")`). Add a corresponding `GROUP_*` constant if needed. Check the generator's `DATA_SIZE_MAP` for a matching shorthand; if missing, add it. Then add a corresponding `data_size_*` BNF rule. If the new data size requires a new lexer token (e.g., `DOT_X`), add the token type to `M68kTokenTypes.java` and a lexer rule in `_M68kLexer.flex`.

4. **Check operand map.** If the operand code from vasm is not yet mapped, add the vasm code to enum mapping in the generator's `OPERAND_MAP`.

5. **Check `M68kRegister` enum.** If a required register is missing (e.g., 68040+ cache control registers), add a placeholder entry. Include the `M68kTokenTypes` constant and the CPU group set (e.g., `M68kCpu.GROUP_68040_UP`). Add a lexer token rule in `_M68kLexer.flex` and the required token type to `M68kTokenTypes.java` if missing. Then add a test in `M68kRegisterTest` verifying the new register can be found by token type and is supported on the correct CPU.

6. **Check BNF `operand_*` rules.** Each `operand_*` rule is shared by multiple instructions. If the instruction's addressing mode constraints differ from any existing operand rule, create a new `operand_*` rule instead of extending an existing one. Follow the existing pattern with `{ name = "..." }` attribute. Ask the user to confirm.

7. **Check BNF `adm_*` rules.** If a new addressing mode or register requires an addressing mode rule not yet in the BNF, add a new `adm_*` rule following the existing pattern. Every `adm_*` rule requires `implements` (PSI interface), `mixin` (implementation class), and `name` (synced with `M68kAddressMode` notation) attributes. Ask the user to confirm.

**Circular dependency note:** Sub-step 1 requires PSI class references, but PSI classes are generated by Grammar-Kit from the BNF `adm_*` rules in sub-step 7. Use a placeholder class reference in sub-step 1. After completing sub-step 7 and regenerating the parser, update the `M68kAddressMode` entries with the correct generated PSI class references.

### Step 5: Add the parser grammar rule

In `src/grammar/m68k.bnf`, define the instruction rule.

**Rule name:** Use `{{name}}_instruction`. The BNF header automatically applies `name = "instruction"`, `implements = "M68kInstruction"`, and `pin = 1` to all rules matching `*_instruction`. Do not specify these attributes.

**Derive the rule from registry data:** Use the exact verbatim information from the registry entries presented in Step 1. The operand codes (e.g., `{D_}`, `{BS,D_}`) determine the operand types. The data size field (e.g., `L` means `data_size_long?`, `BWL` means `data_size_all?`) determines the data size prefix. Do not guess — the registry data is the single source of truth.

**Verify with related instructions:** After deriving the rule, search the BNF for similar instructions (e.g., `ext` for `extb`) to verify the pattern matches. If the pattern differs, recheck the registry data.

**Special interfaces:** If the registry data specifies a special status, check the existing instruction rules for the corresponding `implements` attribute pattern and apply it.

Add the new instruction rule to the appropriate sub-group within `m68000_instructions`. Search the BNF for existing rules named `*_instructions` to find a matching sub-group. If none matches, suggest creating a new one, then add it to `m68000_instructions`. If the instruction requires a newer CPU (e.g., 68020+, 68040+) and no CPU-specific group exists, suggest creating one, then add it to the top-level `instructions` choice rule. The `cpu32_instructions` group is complete — do not modify it. If unsure, ask the user.

**Choosing operand types:** The BNF defines `operand_*` rules that group addressing modes by instruction constraints. Each has a `{ name = "..." }` attribute mapping to an `M68kOperand` enum value. Some instructions use `adm_*` rules directly. Choose the type matching the instruction's constraints.

**Fake base rules:** The BNF uses regex-based `extends()` annotations to automatically apply `fake` base rules. Search the BNF for existing `extends()` annotations. If no pattern matches but the instruction needs `data_sized`, manually add `extends` in the instruction's attributes block.

**Methods block for duplicate operand types:** If an instruction has two operands of the same type (e.g., `adm_rrd COMMA adm_rrd`), add a `methods` block to distinguish them. See `exg_instruction` as a template.

### Step 6: Regenerate the mnemonic registry

The generator produces the `M68kMnemonicRegistry` constructor — do not edit it manually.

1. **Add runtime data placeholders.** Add entries in `tests/com/yanncebron/m68kplugin/lang/psi/M68kMnemonicRegistryRuntimeData.txt`. Add one entry per operand combination. The file's header documents the format. Line 1 values map to vasm `opcodes.h`. Line 2 you must fill in manually; use `-` for unknown values.

2. Update the hardcoded expected counts in `M68kMnemonicRegistryGeneratorTest`.

3. Run the test via Gradle:
   ```
   M68kMnemonicRegistryGeneratorTest=1 ./gradlew :test --tests "com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistryGeneratorTest.testGenerateMnemonicRegistryData"
   ```
   The test writes to `System.out`, captured in `build/test-results/test/TEST-com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistryGeneratorTest.xml`. Open that XML file and extract the content from the `<system-out>` element.

4. Use `search_replace` on `M68kMnemonicRegistry.java` to replace the constructor body. Search from the first `create()` call to the line before `registry = ...`, and replace with the extracted output. Do not write to a temporary file — pass the extracted content directly to the replace parameter.

5. Update the hardcoded expected values in `M68kMnemonicRegistryTest`.

### Step 7: Regenerate the parser

Follow `.junie/AGENTS.md` — manual IDE step, then remove duplicates.

### Step 8: Update all-instructions parsing data

This step requires the mnemonic registry and parser to be regenerated.

1. Run the test via Gradle:
   ```
   MnemonicGeneratedParserDataTest=1 ./gradlew :test --tests "com.yanncebron.m68kplugin.parser.MnemonicGeneratedParserDataTest.testGenerateAndParseAllInstructions"
   ```
   The test writes to `System.out`, captured in `build/test-results/test/TEST-com.yanncebron.m68kplugin.parser.MnemonicGeneratedParserDataTest.xml`. Open that XML file and extract the content from the `<system-out>` element.
2. Verify the extracted output contains the new mnemonic variants. If missing, regenerate the registry and parser first.
3. Open `testData/sanity/AllInstructionsParsing.s` in the editor and replace its entire content with the extracted output. You must use the editor to write this file — never use `cp`, `bash`, or any shell command. The output includes `* M68kMnemonic{...}` metadata lines, `*` separator lines (rows of asterisks), and instruction variant lines — preserve all lines without stripping or modifying any content. The output looks like:

```
****************************************************************

* M68kMnemonic{add, DATA_REGISTER, ADDRESS_REGISTER_INDIRECT, [BYTE], MC68000 Family, CUAU*, ?-?--}
         add.b       d0,$4000
         add.w       d0,$4000
```

All lines starting with `*` are required — they are not comments.
4. Update the hardcoded expected total variant count in `MnemonicGeneratedParserDataTest`.

### Step 9: Add parsing tests

Create or update a test class in `tests/com/yanncebron/m68kplugin/parser/`. Refer to existing test classes like `MoveInstructionParsingTest` as a template, including the `@TestDataPath` annotation and constructor.

Test data files go in `testData/parser/` under a subdirectory matching your test class. Each test uses a `.txt` file containing the code to parse.

**Note:** Follow the leading space convention in `doCodeTest` strings.

### Step 10: Verify inspections

Existing inspections operate on PSI element types, not on specific mnemonics, so they automatically cover new instructions. Run the inspection tests to confirm no regression.

Research whether the new mnemonic warrants a new inspection. If the instruction has unique constraints not covered by existing inspections, propose one following the existing pattern in `src/com/yanncebron/m68kplugin/inspections/`, register it in `plugin.xml`, and add corresponding tests.

### Step 11: Update spell checker dictionary

Add the mnemonic keyword to `plugin-resources/dictionaries/m68k.dic` in alphabetical order. Without this entry, the IDE's spell checker will underline the keyword.

### Step 12: Update CPU group handling

If the new mnemonic requires a CPU group not yet present in `M68kCpu`, check `M68kMnemonicsBrowserPane` and `M68kDocsGeneratorUtil` for references to existing CPU groups. Add the new group following the existing pattern.

### Step 13: Update intention tests

If the new mnemonic uses a new register or addressing mode, add a test to `M68kShowUsedRegistersIntentionTest` verifying the intention correctly identifies the registers used.

### Step 14: Add reference documentation

Create `plugin-resources/docs/mnemonic/{{name}}.md`. Consult the authoritative sources to fill in the content. Follow the format, layout, and style of existing files, **except** for the two sections below which must be omitted.

**Primary source:** The M68000 Programmer's Reference Manual (`m68000-prm`).

**Omit "CPU Support" section.** The registry data encodes CPU support.

**Omit "Condition Codes" section.** The registry data encodes condition code effects, unless the instruction has a special documented behavior.

**Mark missing information.** You must mark any missing or unclear information with `<!-- TODO: verify ... -->`.

**Cross-link related docs:** Search `plugin-resources/docs/mnemonic/` for related instructions — similar operation, shared operands, or alternative forms. Add bi-directional cross-links: a link in the new document to the related document, and a reverse link back. Use short explanations, e.g., `[MOVE](move.md)` with a note on how they differ.

### Step 15: Verify all changes and update changelog

Run a fact-check against all authoritative sources. Verify the lexer token, BNF grammar rule, mnemonic registry entries, runtime data, all-instructions parsing data, parsing tests, reference documentation, and any new enum values match the authoritative sources. Check each file modified in the previous steps. If any expected entry is missing or incorrect, STOP and alert the user.

Add a succinct note to `CHANGELOG.md` under `## Unreleased`. If the 68000 does not support the instruction, mention the minimum CPU.

1. **Generate a commit message.** Output a concise commit message mentioning the added mnemonic and key changed files.
2. **Ask the user to verify.** Explicitly ask the user to verify everything in a test IDE instance before committing. Wait for confirmation.

### Step 16: Suggest related missing mnemonics

Search the authoritative sources for semantically related unsupported mnemonics or plain variants of the one just added. Present a numbered list if any exist.

---

## Adding a new assembler directive

### Step 1: Verify the scope of work

Present the directive's syntax and related information so the user can confirm the scope.

**Directive syntax:** Consult the authoritative sources (`vasm`). Quote the syntax line(s) verbatim, including all variants. Also quote any important information like supported assembler modes, operand constraints, or special behavior. Do not paraphrase — quote the source directly.

**Related existing directives:** Search the BNF for similar directives (e.g., `dc` for `ds`) to verify expected data size and operand patterns. Use only to verify the scope — authoritative sources are the single source of truth.

Check how many types are missing using the following steps. Each missing entry requires multi-layer changes. If many are missing, inform the user about the scale. Suggest concrete directives from the authoritative sources that share the same missing types. Present a numbered list. If none share missing types, explain why. Ask the user to select an alternative or confirm proceeding.

**Halt and confirm:** After presenting the scope analysis, stop and ask the user to either confirm continuing or select an alternative.

### Step 2: Declare the token type and add to the DIRECTIVES token group

In `src/com/yanncebron/m68kplugin/lang/psi/M68kTokenTypes.java`, add a token type following the existing pattern — uppercase directive name, lowercase debug name, placed alphabetically.

In `src/com/yanncebron/m68kplugin/lang/psi/M68kTokenGroups.java`, add the new token type to the `DIRECTIVES` TokenSet.

Run `M68kTokenTest` to verify the new token is registered in exactly one group with a unique debug name.

### Step 3: Add the lexer token

In `src/grammar/_M68kLexer.flex`, add the directive keyword in the `<IN_INSTRUCTION>` state. Follow the same rules as instructions. Refer to existing directives as samples:

- Without operands: `even`, `end`, `text`
- With operands: `org`, `rept`, `section`
- With optional data size: `dc`, `ds`, `fo`
- With string operand: `include`, `echo`, `fail`

Place the new rule alphabetically.

### Step 4: Check for missing data size

If the directive uses a data size not yet present in `M68kDataSize`, add a new entry following the existing pattern (e.g., `BYTE(".b")`). Add a corresponding `GROUP_*` constant if needed. Check the generator's `DATA_SIZE_MAP` for a matching shorthand; if missing, add it. Then add a corresponding `data_size_*` BNF rule. If the new data size requires a new lexer token (e.g., `DOT_X`), add the token type to `M68kTokenTypes.java` and a lexer rule in `_M68kLexer.flex`.

### Step 5: Add the parser grammar rule

In `src/grammar/m68k.bnf`, define the directive rule.

**Rule name:** Use `{{name}}_directive`. The BNF header automatically applies `implements = "M68kDirective"` to all rules matching `*_directive`. Do not specify this attribute.

**Every directive rule must include `pin = 1`** (or `pin = 2` for labeled directives) — this is mandatory for error recovery.

Refer to existing directives as templates: `equ_directive` shows a labeled directive extending `equ_directive_base`, `dc_directive` shows `extends = data_sized`, and `cnop_directive` shows a `methods` block.

Add the new directive rule to the `directives` rule.

### Step 6: Regenerate the parser

Follow `.junie/AGENTS.md` — manual IDE step, then remove duplicates.

### Step 7: Add parsing tests

In `tests/com/yanncebron/m68kplugin/parser/DirectivesParsingTest.java`, add test methods following the existing pattern.

**Note:** Follow the leading space convention in `doCodeTest` strings.

### Step 8: Update all-directives parsing data

Open `testData/sanity/DirectivesParsing.s` and add a section containing all valid variants of the new directive. Follow the format of existing entries.

### Step 9: Update spell checker dictionary

Add the directive keyword to `plugin-resources/dictionaries/m68k.dic` in alphabetical order. Without this entry, the IDE's spell checker will underline the keyword.

### Step 10: Update known issues

If the directive was previously listed as unsupported in `docs/known_issues.md`, remove it.

### Step 11: Update brace matcher

If the directive creates a code block paired with another directive (e.g., `basereg`/`endb`), add a `BracePair` entry to `M68kBraceMatcher.java`.

### Step 12: Update existing inspections

If the directive has unique constraints, add a `visit` method to `M68kDirectivesInspection` following the existing pattern. The method name must match the generated PSI class.

### Step 13: Add reference documentation

Create `plugin-resources/docs/directives/{{name}}.md`. Consult the authoritative sources to fill in the content. Follow the format, layout, and style of existing files.

**Primary source:** The vasm sources (`vasm`).

**Mark missing information.** You must mark any missing or unclear information with `<!-- TODO: verify ... -->`.

**Cross-link related docs:** Search `plugin-resources/docs/directives/` for related directives — equivalent forms, paired directives, or dependencies. Add bi-directional cross-links: a link in the new document to the related document, and a reverse link back. Use short explanations, e.g., `[section](section.md)` with a note on the relationship.

### Step 14: Verify all changes and update changelog

Run a fact-check against all authoritative sources. Verify the lexer token, BNF grammar rule, parsing tests, reference documentation, and any new enum values match the authoritative sources. Check each file modified in the previous steps. If any expected entry is missing or incorrect, STOP and alert the user.

Add a succinct note to `CHANGELOG.md` under `## Unreleased`.

1. **Generate a commit message.** Output a concise commit message mentioning the added directive and key changed files.
2. **Ask the user to verify.** Explicitly ask the user to verify everything in a test IDE instance before committing. Wait for confirmation.

### Step 15: Suggest related missing directives

Search the authoritative sources for semantically related unsupported directives or equivalent forms of the one just added. Present a numbered list if any exist.


