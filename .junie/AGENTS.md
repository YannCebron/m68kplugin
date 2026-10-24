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

