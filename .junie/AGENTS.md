# Guidelines

## Regenerating the Parser

After modifying `src/grammar/m68k.bnf`, the parser must be regenerated manually via the IDE.

**Steps:**

1. Ask the user to regenerate the parser by right-clicking `src/grammar/m68k.bnf` and selecting **Generate Parser and Actions**.
2. Wait for confirmation.
3. Remove any PSI duplicates in `gen/` that also exist in `api/psi-api/`:
   ```
   comm -12 <(cd api/psi-api/src/main/java && find . -name "*.java" | sort) <(cd gen && find . -name "*.java" | sort) | sed 's|^\./||' | while read f; do rm -f "gen/$f"; done
   ```
   This finds and deletes duplicates in a single pipeline.

## Code style rule

Any change to the lexer file, BNF grammar, Java code, tests, or documentation must follow the same naming conventions, style, and semantics as existing code. Copy the pattern from similar files and rules already in the project.

## Regenerating the Lexer

After modifying `src/grammar/_M68kLexer.flex`, regenerate via Gradle: `./gradlew generateLexer`.

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
   - The sources also contain reference documentation for assembler directives, syntax variations, and tool-specific behavior.

For each source, ask the user for a local copy; if provided, store the path in `authoritative-sources.properties` and inform the user where it is saved.
The `authoritative-sources.properties` file is located in the `.m68kplugin` directory under the user's home folder (e.g., `~/.m68kplugin/` on macOS/Linux, `%USERPROFILE%\.m68kplugin\` on Windows).

### How to Retrieve Facts

When implementing or verifying domain-specific behavior (such as M68k instruction behavior, addressing modes, register usage, condition codes, or assembler directives):

1. **Read `authoritative-sources.properties` in the `.m68kplugin` directory under the user's home folder** — get the local paths for the configured authoritative sources. If a path is missing, ask the user for it, then write it to this file.
2. **Consult the local authoritative sources** — navigate to the referenced section for the definitive specification.
3. **Fallback to online resources** — if a local copy is unavailable, use the online URL listed for that source.
4. **Cross-reference with other authoritative sources** — examine how the same concept is implemented or documented elsewhere, especially for edge cases, syntax variations, or tool-specific behavior.
5. **If still unsure, ask the user** — never guess or assume behavior without evidence from these sources.

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

