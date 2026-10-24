# Agent Guidelines

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
