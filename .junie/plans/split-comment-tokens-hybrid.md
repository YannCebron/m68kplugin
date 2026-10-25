---
sessionId: session-261025-172413-49k5
---

# Requirements

### Overview

Split the single `COMMENT` lexer token into two distinct types: `COMMENT` (full-line comments starting with `;` or `*`) and `EOL_COMMENT` (trailing comments after code on the same line). Keep "Doc" as a semantic role determined by the documentation provider at PSI level.

### Goals

- Eliminate the `skipWhitespacesBackward` heuristic in `M68kLabelDocumentationProvider` for distinguishing EOL comments.
- Enable distinct syntax highlighting for EOL comments (e.g., lighter color) versus full-line comments.
- Keep "Doc" comment detection as a position-based role: a full-line `COMMENT` preceding a label.

### In Scope

- Add `EOL_COMMENT` token type in `M68kTokenTypes`.
- Modify the lexer to emit `EOL_COMMENT` for trailing comments (AFTER_OPERAND state and comments after instructions/labels on the same line).
- Update `M68kTokenGroups.COMMENTS` to include `EOL_COMMENT`.
- Update `M68kSyntaxHighlighter` to map `EOL_COMMENT` to a new text attribute.
- Add `EOL_COMMENT` text attribute in `M68kTextAttributes`.
- Add color settings descriptor in `M68kColorSettingsPage`.
- Update `M68kLabelDocumentationProvider` to filter by token type instead of position heuristic.
- Update lexer tests in `CommentLexerTest`.
- Update bundle messages for the new attribute descriptor.

### Out of Scope

- No new "Doc" token type. Doc remains a semantic role.
- No changes to the BNF grammar or parser regeneration.
- No changes to the `rem`/`erem` block comment handling.


# Technical Design

### Current Implementation

**Lexer** (`src/grammar/_M68kLexer.flex`):
- Line 88: `COMMENT=;.*|\*.*` — matches both semicolon and star comments.
- Line 89: `EOL_COMMENT=;.*` — defined but only used in `IN_OPERAND` state (line 187) where it still returns `COMMENT`.
- Line 131 (YYINITIAL): `{COMMENT}` returns `COMMENT`.
- Line 147 (AFTER_LABEL): `{COMMENT}` returns `COMMENT`.
- Line 179 (AFTER_OPERAND): `.+` returns `COMMENT` — this is the "automatic" comment after 2nd whitespace in operands.
- Line 187 (IN_OPERAND): `{EOL_COMMENT}` returns `COMMENT`.

**Token types** (`M68kTokenTypes.java`):
- Line 95: `COMMENT = new M68kTokenType("comment")`
- Line 96: `COMMENT_REM = new M68kTokenType("comment_rem")`

**Token groups** (`M68kTokenGroups.java`):
- Line 354: `COMMENTS = TokenSet.create(COMMENT, COMMENT_REM)`

**Syntax highlighter** (`M68kSyntaxHighlighter.java`):
- Line 53: `keys.put(M68kTokenTypes.COMMENT, M68kTextAttributes.COMMENT)`

**Text attributes** (`M68kTextAttributes.java`):
- Line 94-95: `COMMENT` maps to `DefaultLanguageHighlighterColors.LINE_COMMENT`.

**Documentation provider** (`M68kLabelDocumentationProvider.java`):
- Line 101: `PsiTreeUtil.skipWhitespacesBackward(child) instanceof M68kPsiElement` — heuristic to detect EOL comments.
- Lines 118-125: Falls back to EOL comment on the same line if no full-line comments found.

### Key Decisions

1. **EOL_COMMENT covers three cases:**
   - Trailing text in AFTER_OPERAND state (automatic comments without prefix).
   - Semicolon or star comments after code on the same line in AFTER_LABEL state (e.g., `label ; comment`).
   - Semicolon comments in IN_OPERAND state (already matched by `EOL_COMMENT` pattern but returned as `COMMENT`).

2. **COMMENT (full-line) covers two cases:**
   - Semicolon or star comments at the start of a line (YYINITIAL state).
   - Star comments in AFTER_LABEL state when the line starts with `*` (full-line star comments).

3. **Detection of EOL vs full-line in AFTER_LABEL:** After a label, a comment is EOL if preceded by non-whitespace on the same line. The lexer already tracks state transitions — after `ID` in YYINITIAL, the lexer enters AFTER_LABEL. If the first non-whitespace token in AFTER_LABEL is a comment, it is a full-line comment only if the comment appears at the start of a new line. However, the lexer cannot easily distinguish this because `label ; comment` and `label\n; comment` both reach AFTER_LABEL.

   **Revised approach:** In AFTER_LABEL, return `EOL_COMMENT` for all comments. In YYINITIAL, return `COMMENT` for all comments. In AFTER_OPERAND, return `EOL_COMMENT` for all trailing text. In IN_OPERAND, return `EOL_COMMENT` for semicolon comments.

   This means `label ; comment` produces `EOL_COMMENT`, and `; comment` on its own line produces `COMMENT`. The documentation provider can then filter by token type directly.

4. **Doc comments remain semantic:** A "Doc" comment is a full-line `COMMENT` that immediately precedes a label. The documentation provider determines this role at PSI level.

### Proposed Changes

#### 1. Add `EOL_COMMENT` token type

**File:** `src/com/yanncebron/m68kplugin/lang/psi/M68kTokenTypes.java`
- Add: `public static final IElementType EOL_COMMENT = new M68kTokenType("eol_comment");`

#### 2. Update token groups

**File:** `src/com/yanncebron/m68kplugin/lang/psi/M68kTokenGroups.java`
- Change: `COMMENTS = TokenSet.create(COMMENT, COMMENT_REM, EOL_COMMENT)`

#### 3. Modify lexer rules

**File:** `src/grammar/_M68kLexer.flex`
- Line 131 (YYINITIAL): Keep `{COMMENT}` returning `COMMENT` (full-line comments).
- Line 147 (AFTER_LABEL): Change `{COMMENT}` to return `EOL_COMMENT` (trailing after label).
- Line 179 (AFTER_OPERAND): Change `.+` to return `EOL_COMMENT` (automatic trailing comments).
- Line 187 (IN_OPERAND): Change `{EOL_COMMENT}` to return `EOL_COMMENT` (semicolon in operands).

#### 4. Add text attribute for EOL comments

**File:** `src/com/yanncebron/m68kplugin/lang/highlighting/M68kTextAttributes.java`
- Add: `public static final TextAttributesKey EOL_COMMENT = createTextAttributesKey("M68K_EOL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);`

#### 5. Map EOL_COMMENT in syntax highlighter

**File:** `src/com/yanncebron/m68kplugin/lang/highlighting/M68kSyntaxHighlighter.java`
- Add: `keys.put(M68kTokenTypes.EOL_COMMENT, M68kTextAttributes.EOL_COMMENT);`

#### 6. Add color settings descriptor

**File:** `src/com/yanncebron/m68kplugin/lang/highlighting/M68kColorSettingsPage.java`
- Add descriptor after the existing comment descriptors:
  `createDescriptor("color.settings.group.comments", "attribute.descriptor.comment.eol", M68kTextAttributes.EOL_COMMENT)`

**File:** `plugin-resources/messages/M68kBundle.properties`
- Add: `attribute.descriptor.comment.eol=EOL comment`

#### 7. Update documentation provider

**File:** `src/com/yanncebron/m68kplugin/documentation/M68kLabelDocumentationProvider.java`
- Line 101: Replace `PsiTreeUtil.skipWhitespacesBackward(child) instanceof M68kPsiElement` with `child.getNode().getElementType() == M68kTokenTypes.EOL_COMMENT`.
- Lines 118-125: Keep the EOL fallback, but check for `EOL_COMMENT` token type instead of position.

#### 8. Update lexer tests

**File:** `tests/com/yanncebron/m68kplugin/lexer/CommentLexerTest.java`
- Update expected token names from `comment` to `eol_comment` for all trailing comment cases.
- Full-line comments (YYINITIAL) keep the `comment` token name.

### Architecture Diagram

``` mermaid
graph LR
  A[_M68kLexer.flex] -->|COMMENT| B(M68kTokenTypes.COMMENT)
  A -->|EOL_COMMENT| C(M68kTokenTypes.EOL_COMMENT)
  A -->|COMMENT_REM| D(M68kTokenTypes.COMMENT_REM)
  B --> E(M68kTokenGroups.COMMENTS)
  C --> E
  D --> E
  E --> F[M68kParserDefinition.getCommentTokens]
  B --> G[M68kSyntaxHighlighter]
  C --> G
  G --> H[M68kTextAttributes.COMMENT]
  G --> I[M68kTextAttributes.EOL_COMMENT]
  C --> J[M68kLabelDocumentationProvider]
  J -->|Filter out EOL_COMMENT| K[Doc comment detection]
```

### Risks

- **Star comments in AFTER_LABEL:** A star comment after a label (`label * comment`) is always EOL. Full-line star comments (`* comment` at the start of a line) are handled in YYINITIAL and return `COMMENT`. This distinction is correct.
- **Automatic comments in AFTER_OPERAND:** Text without a prefix character (e.g., `move #1,d6 comment`) becomes `EOL_COMMENT`. This is correct because it always follows code.
- **Existing tests:** All lexer tests that expect `comment` for trailing comments must be updated to `eol_comment`.
- **Color scheme migration:** Existing user color schemes will not have the `EOL_COMMENT` key. The fallback to `LINE_COMMENT` ensures no visual regression.

# Delivery Steps

###   Step 1: Add EOL_COMMENT token type and update token groups
Introduce the new token type and wire it into the comment token set.
- Add `EOL_COMMENT` constant to `M68kTokenTypes.java`.
- Update `M68kTokenGroups.COMMENTS` to include `EOL_COMMENT`.
- Regenerate the lexer with `./gradlew generateLexer`.


###   Step 2: Split lexer rules to emit EOL_COMMENT for trailing comments
Change the lexer so trailing comments produce `EOL_COMMENT` instead of `COMMENT`.
- In AFTER_LABEL state: change `{COMMENT}` to return `EOL_COMMENT`.
- In AFTER_OPERAND state: change `.+` to return `EOL_COMMENT`.
- In IN_OPERAND state: change `{EOL_COMMENT}` to return `EOL_COMMENT`.
- Keep YYINITIAL returning `COMMENT` for full-line comments.
- Regenerate the lexer.


###   Step 3: Add EOL_COMMENT highlighting and color settings
Wire the new token type through the highlighting pipeline.
- Add `EOL_COMMENT` text attribute in `M68kTextAttributes.java` (fallback to `LINE_COMMENT`).
- Map `EOL_COMMENT` to the text attribute in `M68kSyntaxHighlighter.java`.
- Add color settings descriptor in `M68kColorSettingsPage.java`.
- Add bundle message `attribute.descriptor.comment.eol` in `M68kBundle.properties`.


###   Step 4: Update documentation provider to use token type
Replace the position-based EOL heuristic with token type checking.
- In `M68kLabelDocumentationProvider.getCommentText()`: replace `skipWhitespacesBackward` check with `child.getNode().getElementType() == M68kTokenTypes.EOL_COMMENT`.
- Update the EOL fallback path to check for `EOL_COMMENT` token type.
- Run `M68kLabelDocumentationProviderTest` to verify no regressions.


###   Step 5: Update lexer tests and run full test suite
Update test expectations and validate the complete change.
- Update `CommentLexerTest.java`: change expected `comment` to `eol_comment` for all trailing comment cases (AFTER_LABEL, AFTER_OPERAND, IN_OPERAND).
- Keep `comment` for full-line cases (YYINITIAL).
- Run `./gradlew :test` to verify all tests pass.
- Update copyright years to 2026 in modified files.
