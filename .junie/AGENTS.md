# Guidelines

## Regenerating the Parser

After modifying `src/grammar/m68k.bnf`, the parser must be regenerated via the IDE.

**Steps:**

1. Regenerate the parser by invoking the "Generate Parser Code" IDE action on `src/grammar/m68k.bnf` via the `execute_tool` tool.
2. If the automatic generation fails, ask the user to invoke the action manually. Do not attempt to generate the parser via Gradle.
3. Remove any PSI duplicates in `gen/` that also exist in `api/psi-api/`. First, use `glob_search` to find all `.java` files in `api/psi-api/src/main/java/` (pattern: `api/psi-api/src/main/java/**/*.java`). Then, for each file found, compute the matching path in `gen/` by replacing the `api/psi-api/src/main/java/` prefix with `gen/`. If the file exists in `gen/`, delete it by running `rm <path-to-file>` via the `bash` tool. Run one `rm` command per file. Do not use loops or complex shell scripts.

## Code style rule

Any change to the lexer file, BNF grammar, Java code, tests, or documentation must follow the same naming conventions, style, and semantics as existing code. Copy the pattern from similar files and rules already in the project.

When modifying a file with a copyright header, you must update the year to the current year. Check the copyright header before saving any changes to a source file.

## Regenerating the Lexer

After modifying `src/grammar/_M68kLexer.flex`, regenerate via Gradle: `./gradlew generateLexer`.

## Running Tests

The project has multiple Gradle modules. Running `./gradlew test` without specifying a module will search all modules and fail if the test class is not found in the default module.

### Main Module Tests

Tests in `tests/` belong to the main module. Always prefix with `:test`:

```bash
./gradlew :test --tests "com.yanncebron.m68kplugin.lang.psi.M68kMnemonicRegistryGeneratorTest"
```

### Amiga Platform Tests

Tests in `platforms/amiga/src/test/` belong to the `amiga` module. Use `:amiga:test`:

```bash
./gradlew :amiga:test --tests "com.yanncebron.m68kplugin.amiga.hardware.M68kAmigaHardwareRegisterGeneratorTest"
```

### Running All Tests

To run all tests in a specific module, omit the `--tests` filter:

```bash
./gradlew :test
./gradlew :amiga:test
```

## Retrieving Relevant Facts

Every decision must be based on exact, fact-checked evidence from authoritative sources. Never rely on web searches, guesses, or facts deduced only from this project's source code. If unsure, ask the user to confirm manually.

This rule applies to everything, including but not limited to executed skills. When any task requires domain-specific knowledge, consult the authoritative sources listed below or ask the user for the definitive reference. Skills must ensure their steps follow this rule.

### Authoritative Sources

The authoritative sources for this project are:

1. **M68000 Family Programmer's Reference Manual** (NXP) — identifier: `m68000-prm`
   - Online: https://www.nxp.com/docs/en/reference-manual/M68000PRM.pdf
   - Referenced in source code via comments like `_Ref x.y.z_` (e.g., `_Ref 5.3.2_`)

2. **vasm Sources** (the vasm assembler) — identifier: `vasm`
   - Online: https://sun.hasenbraten.de/vasm/
   - The source directory contains both code and documentation files. Consult the local documentation before using online resources.
   - **Before navigating to vasm source files, read `.junie/vasm-knowledgebase.md`** to find the relevant directories, functions, or opcode definitions. The knowledge base covers: directory structure, opcode definitions, operand types, special registers, CPU models, CPU support flags, directive flags, directives, key functions, and navigation tips.
   - **IMPORTANT:** The knowledge base may contain inaccurate or incomplete information. Always verify facts against the actual vasm source files before making changes.

3. **vasm Executable** (the vasm assembler binary) — identifier: `vasm-executable`
   - Ask the user for the local path to the vasm executable compiled for `mot` syntax.
   - Store the path in `authoritative-sources.properties` under the `vasm-executable` key.
   - Run the executable with no arguments to retrieve the version string, then store it in `authoritative-sources.properties` under the `vasm-executable.version` key.
   - Use the executable to fact-check by assembling test code and comparing the output with expected behavior.

For each source, ask the user for a local copy; if provided, store the path in `authoritative-sources.properties` and inform the user where it is saved.
The `authoritative-sources.properties` file is located in the `.m68kplugin` directory under the user's home folder (e.g., `~/.m68kplugin/` on macOS/Linux, `%USERPROFILE%\.m68kplugin\` on Windows).

### How to Retrieve Facts

When implementing or verifying domain-specific behavior (such as M68k instruction behavior, addressing modes, register usage, condition codes, or assembler directives):

1. **Read `authoritative-sources.properties` in the `.m68kplugin` directory under the user's home folder** — get the local paths for the configured authoritative sources. If a path is missing, ask the user for it, then write it to this file.
2. **Extract PDF sources to text if needed** — if a local source is a PDF and no extracted text directory exists, extract the content to Markdown files stored in `~/.m68kplugin/`. Try available PDF extraction tools such as `pdftotext`, `mutool`, or Python libraries. If no tool is available, open the PDF directly using the `open` tool to read sections on demand. Store extracted text in a directory named after the source identifier (e.g., `~/.m68kplugin/m68000-prm/`). Update `authoritative-sources.properties` to point to the extracted text directory. If automatic extraction is not possible, ask the user to provide the extracted text.
3. **Consult the local authoritative sources** — navigate to the referenced section for the definitive specification. Use the extracted text directory if available; otherwise, open the source directly.
4. **Fact-check with the vasm executable** — if the `vasm-executable` path is configured, invoke the vasm executable with test code to verify assembler behavior. Create a temporary assembly file with the test code in a directory outside the project, run the executable with the `mot` syntax flag, and examine the output (such as generated object files, error messages, or listing files) to confirm expected behavior. Do not use the project directory for input or output files. When reporting fact-check results, include the vasm version stored in `authoritative-sources.properties` under the `vasm-executable.version` key.
5. **Fallback to online resources** — if a local copy is unavailable, use the online URL listed for that source.
6. **Cross-reference with other authoritative sources** — examine how the same concept is implemented or documented elsewhere, especially for edge cases, syntax variations, or tool-specific behavior.
7. **If sources disagree, STOP** — present the contradicting facts to the user and let the user choose which source is correct.
8. **If still unsure, ask the user** — never guess or assume behavior without evidence from these sources.

### Citation Format

When referencing authoritative sources, use **bold** formatting with the source identifier and location.

Examples:

- **m68000-prm: 5.3.2** — references section 5.3.2 of the M68000 PRM.
- **vasm: cpus/m68k/opcodes.h:5678** — references line 5678 in the vasm source file.

Use this format only in chat responses.

### What Must Be Fact-Checked

For M68k-related work, the following must always be verified against authoritative sources:

- Instruction mnemonics and their valid syntax
- Addressing modes and operand combinations
- Register usage and condition code effects
- Assembler directives and their parameters
- Instruction encoding and byte patterns
- CPU mode restrictions (e.g., supervisor-only instructions)
- Behavior differences between M68000, M68010, M68020, M68030, M68040, M68060

## Confirming Skill Results

After any executed skill has finished, present the new findings, updates, improvements, and learnings clearly to the user and wait for confirmation before proceeding. Then ask the user to update the skill with the new findings.

## Reading vasm Knowledge Base

Before navigating to vasm source files or verifying behavior against vasm, read `.junie/vasm-knowledgebase.md` to locate the relevant code quickly.

## Recording vasm Verification Findings

After verifying behavior against vasm, record any new findings in `.junie/vasm-verification-learnings.md`.

This includes lessons learned from false positives, test design mistakes, exit code misinterpretation, or any other insight about how vasm parses operands or how to correctly test assembler behavior.

Update the knowledge base when a verification reveals incorrect assumptions about vasm behavior, operand types, or parsing order.

## Updating Documentation

After completing a change, check if `docs/known_issues.md` or `docs/plan.md` requires an update.

- If a change resolves a known issue, removes a limitation, or adds support for a previously unsupported feature, update `docs/known_issues.md` by removing or modifying the affected entry.
- If a change implements a planned feature listed in `docs/plan.md`, update `docs/plan.md` by removing or modifying the completed item.
- If a change introduces a new limitation or workaround, add it to `docs/known_issues.md`.

Review both files before submitting the final result to ensure the documentation reflects the current state.

## Language — ASD-STE100 Simplified Technical English

All output, generated code, generated documentation, and any other artifacts must use **ASD-STE100 Simplified Technical English**.
This rule applies to source comments, documentation pages, error messages, commit messages, and chat responses.

### Writing Rules

Follow these ASD-STE100 rules:

1. **Use only approved words** or technical nouns. Do not use unapproved words except as technical nouns. (Rule 1.1)
2. **Use one meaning per word.** Do not use a word with more than one meaning. For example, use "obey" for instructions, not "follow". (Rule 1.3)
3. **Use the active voice.** In descriptive text, you can use the passive voice only when the agent is unknown. Write "The parser reads the token" instead of "The token is read by the parser". (Rule 3.6)
4. **Use short sentences.** A descriptive sentence must have no more than 25 words. An instruction sentence must have no more than 20 words. (Rules 5.1, 6.3)
5. **Use the imperative mood for instructions.** Write "Run the test" instead of "You should run the test". (Rule 5.3)
6. **Use only approved modal verbs.** The dictionary approves "can", "will", and "must". Do not use "should", "would", "may", "might", or "could". (Rule 3.2)
   - Use "must" for requirements.
   - Use "can" for possibilities.
   - Use "will" for future facts.
7. **Use only approved verb forms and tenses.** Do not use the present perfect tense. Use the infinitive, imperative, simple present, simple past, or simple future. (Rule 3.2)
8. **Do not use technical nouns as verbs.** For example, write "Lubricate the linkage with oil" instead of "Oil the linkage". (Rule 1.7)
9. **Use one name per item.** Do not call the same item "config" in one place and "settings" in another. (Rule 1.11)
10. **Use an approved verb to describe an action,** not a noun. Write "Compress the file" instead of "Perform compression of the file". (Rule 3.7)
11. **Write one instruction per sentence.** Do not combine multiple instructions in one sentence. (Rule 5.2)

### Technical Nouns from Authoritative Sources

You can use words that are not in the ASD-STE100 dictionary when they are **technical nouns** from the authoritative sources.

### Examples

**Good:**
- "The parser reads the instruction mnemonic."
- "You must regenerate the parser after modifying the grammar file."
- "The MC68020 supports the Base Register addressing mode."

**Not Good:**
- "The instruction mnemonic is read by the parser." (passive voice)
- "You should regenerate the parser." (unapproved modal "should")
- "The 68020 can use base register mode." (inconsistent terminology)

