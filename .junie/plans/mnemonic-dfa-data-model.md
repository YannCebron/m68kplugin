---
sessionId: session-261025-150745-j6vh
---

# Requirements

### Overview & Goals

Extend `M68kMnemonic` with per-operand read/write access and effective size information, stored via the runtime registry data mechanism. Build a DFA engine and a Control Flow Graph (CFG) builder on top of this data. This enables the plugin to track register flow, detect dead writes, detect overlapping sizes, track jumps and returns, and detect unreachable code.

### User Stories

- As a developer, I want the plugin to detect **dead writes** (a register or memory location is written but never read before the next write).
- As a developer, I want the plugin to detect **overlapping access** (two operands access the same register or memory with mismatched sizes).
- As a developer, I want the plugin to detect **uninitialized reads** (a register is read before any write in a basic block).
- As a developer, I want the plugin to detect **unreachable code** after certain control flow instructions.
- As a developer, I want the plugin to show a **CFG graph** for a function, displaying basic blocks and edges for branches, jumps, and returns.
- As a developer, I want the plugin to track **JSR/RTS pairs** to detect missing returns and call/return mismatches.
- As a developer, I want the plugin to **detect routines** (labeled code blocks entered via JSR and exited via RTS/RTE).
- As a developer, I want the plugin to detect **orphaned routines** (routines with no incoming JSR calls).
- As a developer, I want the plugin to detect **missing returns** (routines without a return path in all CFG exit blocks).
- As a developer, I want the plugin to detect **call/return mismatches** (JSR to a label that is not a routine, or RTS without a matching JSR).
- As a developer, I want to see **used/unused registers** for a selected range of code.
- As a developer, I want to see **register availability** after a point, with color coding (green = free, orange = free until overwritten, grey = unknown due to calls/macros).
- As a developer, I want to **remap registers** by swapping two registers throughout a selected range.
- As a developer, I want to view a **Call Hierarchy** showing callers and callees for each routine.
- As a developer, I want the plugin to **track registers through subroutines** by following JSR/BSR into subroutines.
- As a developer, I want the plugin to **track stack save/restore patterns** (MOVEM and MOVE to `-(sp)` and back from `(sp)+`).
- As a developer, I want the plugin to suggest **optimizations** (e.g., `CLR.L D0` → `MOVEQ.L #0,D0`).
- As a developer, I want the plugin to detect **correctness issues** (dead stores, uninitialized reads, stale condition codes, stack imbalance).
- As a developer, I want to see **read/write access highlighting** for registers and label references in the editor.
- As a developer, I want to see **method separators** visually marking detected subroutines.
- As a developer, I want to **highlight returns** (RTS/RTE) in the editor.
- As a developer, I want to **highlight all exit points** inside a detected routine (RTS/RTE/RTD, JMP, TRAP, STOP).
- As a developer, I want to detect **paired MOVEM** instructions with non-symmetrical register ranges.
- As a developer, I want the plugin to warn on **suspicious word access to address registers** (MOVEA.W, CMPA.W, TST.W on address registers).

### Functional Requirements

1. **Control flow PSI hierarchy**: Create a `M68kControlFlowInstruction` PSI interface for instructions that affect control flow. The interface delegates to `M68kMnemonic.ControlFlow` enum data, similar to how `M68kPrivilegedInstruction` delegates to `M68kMnemonic.PrivilegedType`. Update the grammar so that branch, jump, return, and trap instructions implement this interface. Validate that the PSI hierarchy matches the mnemonic `controlFlow` field for all instructions. BSR remains `ControlFlow.BRANCH` (not `JUMP`) because it has fall-through. The grammar approach: add `implements` to `bcc_instruction_base` and `dbcc_instruction_base` extends blocks, and add `implements` individually to `bsr_instruction`, `rts_instruction`, `jsr_instruction`, `jmp_instruction`, `rtd_instruction`, `rte_instruction`, `rtr_instruction`, `trap_instruction`, `trapv_instruction`, `stop_instruction`, and `bkpt_instruction`.
2. **Per-operand read/write direction**: For each operand (first and second), record whether the operand is READ, WRITE, or READ_WRITE.
3. **Effective size per operand and data size**: For each operand and each supported data size (BYTE, WORD, LONGWORD), record the actual number of bytes accessed.
4. **Additional DFA metadata**: Capture other instruction properties useful for flow analysis (see Technical Design).
5. **Runtime registry extension**: Store the new data in `M68kMnemonicRegistryRuntimeData.txt` and parse it into the `M68kMnemonic` record.
6. **DFA analysis API**: Provide a query API on `M68kMnemonic` to determine read/write sets for a given instruction instance.
7. **CFG builder**: Build a CFG from a sequence of `M68kInstruction` elements, grouping instructions into basic blocks and connecting them with edges.
8. **CFG visualization**: Provide a way to view the CFG (e.g., as a graph in the IDE).
9. **Routine detection**: Identify labeled code blocks as routines based on JSR entry points and RTS/RTE exit points.
10. **Orphaned routine detection**: Detect routines with no incoming JSR calls in the project.
11. **Missing return detection**: Detect routines where not all CFG exit blocks contain a return instruction.
12. **Used/unused registers for selected range (enhancement)**: This functionality already exists in the plugin but treats all register usage the same way. Extend it to show the access type (READ, WRITE, or READ_WRITE) for each register using the new `OperandAccess` data from `M68kMnemonic`. The UI will display different indicators or colors for read-only, write-only, and read-write registers. This enhancement also includes highlighting unused writes and uninitialized reads.
13. **Register availability analysis**: After a given instruction, determine which registers are free to reuse with color-coded status.
14. **Register remapping**: Allow swapping two registers throughout a selected range with conflict validation.
15. **Call Hierarchy**: Build call graphs showing callers (JSR/BSR targets) and callees (JSR/BSR within routines) for each routine.
16. **Register tracking through subroutines**: Follow JSR/BSR into subroutines to determine register effects including saves and restores.
17. **Stack save/restore tracking**: Detect MOVEM and MOVE patterns to `-(sp)` and from `(sp)+` to determine preserved registers across routines.
18. **Optimization suggestions**: Apply pattern matching rules to suggest smaller code alternatives. This is a separate capability built on top of DFA/CFG data: the DFA provides read/write sets to validate that optimized code produces equivalent register effects, and the CFG provides basic block structure for pattern matching within blocks. Optimization rules are domain-specific transformations (e.g., `CLR.L D0` → `MOVEQ.L #0,D0`) that reduce code size, validated against DFA to ensure semantic equivalence. Cycle savings are not available as no reliable cycle count data source exists in the M68000 PRM or vasm sources.
19. **Correctness inspections**: Detect dead stores, uninitialized reads, stale condition codes, stack imbalance, size mismatches, and privilege violations.
20. **Read/write access highlighting**: Use `ReadWriteAccessDetector` to highlight register and label references based on DFA read/write sets.
21. **Method separators**: Visually separate detected subroutines in the editor using code separators.
22. **Highlight returns**: Highlight RTS/RTE/RTD instructions in the editor to mark routine exits.
23. **Highlight routine exit points**: Highlight all exit points (RTS/RTE/RTD, JMP, TRAP, STOP) inside a detected routine to visualize where control flow leaves the routine.
24. **Paired MOVEM inspection**: Detect MOVEM save/restore pairs with non-symmetrical register lists.
25. **Suspicious address register word access**: Warn on `MOVEA.W`, `CMPA.W` with address registers, and `TST.W` on address registers (68020+) as these are often typos. This inspection is independent of DFA/CFG — it matches operand type and size directly.

### Out of Scope

- Memory alias analysis (determining whether two memory addresses overlap at runtime).
- FPU register tracking.
- Macro expansion support (initially).

### Analysis Boundaries

All DFA and CFG analysis must stop at the following boundaries:

1. **Macro calls**: Macros are not expanded or evaluated. Analysis cannot follow into macro bodies or assume macro effects.
2. **Section directives**: Section boundaries (`section`, `pushsection`, `popsection`) separate analysis scopes. Analysis does not cross section boundaries.
3. **Conditional assembly directives**: Directives like `if`, `if1`, `if2`, `ifb`, `ifc`, `ifd`, `ifeq`, `ifge`, `ifgt`, `ifle`, `iflt`, `ifmacrod`, `ifmacrond`, `ifmi`, `ifnb`, `ifnc`, `ifnd`, `ifne`, `ifp1`, `ifpl`, `elseif`, `else`, `endc`, `endif`, `rept`, `endr`, `irp`, `irpc`, `macro`, `endm` are not evaluated. Analysis stops at conditional blocks because the assembler may include or exclude code based on conditions unknown to the plugin.


# Technical Design

### Current Implementation

**M68kMnemonic** (`src/.../lang/psi/M68kMnemonic.java`) is a Java record with:
- `elementType` — instruction token type
- `dataSizes` — supported data sizes (GROUP_BWL, GROUP_WL, etc.)
- `firstOperand`, `secondOperand` — operand types from `M68kOperand` enum
- `cpus` — supported CPU set
- `deprecated`, `privilegedType`, `controlFlow`, `affected`, `tested`

**M68kMnemonicRegistry** (`src/.../lang/psi/M68kMnemonicRegistry.java`) uses a fluent `MnemonicBuilder` inner class. Data is generated by `M68kMnemonicRegistryGeneratorTest` from vasm's `opcodes.h`, enriched with runtime info from `M68kMnemonicRegistryRuntimeParser` reading `M68kMnemonicRegistryRuntimeData.txt`.

**Runtime data format** (2 lines per entry):
```
"move",     {DA,AD},    BWL,    m68000up
-,          -,          -**00
```
Line 2 currently holds: privileged type, control flow, affected condition codes, tested condition codes.

### Key Decisions

**1. Operand access direction as enum per operand position**

Add `OperandAccess` enum to describe whether an operand reads, writes, or both:

```
enum OperandAccess {
    NONE,       // operand does not exist
    READ,       // operand is read only (source)
    WRITE,      // operand is written only (destination)
    READ_WRITE  // operand is read and written (e.g., ADD D0,(A0) — D0 is read then written)
}
```

Each `M68kMnemonic` gets `firstOperandAccess` and `secondOperandAccess` fields.

**2. Effective size as a map from data size to byte count**

Not all operands use the declared data size. For example, `MOVEA` with WORD size only reads 16 bits but the address register itself is always 32 bits. Add an `effectiveSize` field per operand that maps `M68kDataSize` → effective byte count.

**3. Extend runtime data file format**

Add a third line to each runtime data entry for DFA metadata:
```
"move",     {DA,AD},    BWL,    m68000up
-,          -,          -**00
READ,WRITE
```
Line 3: `first_operand_access[,second_operand_access]`

**4. Additional DFA metadata to capture**

Beyond read/write and size, these properties enable richer analysis. **All properties listed below must be added to both the runtime data file and the M68kMnemonic record.**

| Property | Purpose | Example |
|----------|---------|---------|
| `modifiesProgramCounter` | Branch/jump targets | `BRA`, `JSR`, `JMP` |
| `modifiesStackPointer` | Stack frame tracking | `PEA`, `JSR`, `RTS` |
| `implicitRegisterRead` | Registers read implicitly | `DBCC` reads condition codes |
| `implicitRegisterWrite` | Registers written implicitly | `JSR` writes PC and A7 |
| `clobbersMemory` | Instruction may write to unknown memory | `RESET` (hardware) |
| `sizeOverride(operand, dataSize)` | Effective size differs from declared | `MOVEA.W` reads 16 bits, writes 32-bit A-register |

### Proposed Changes

#### 1. New `OperandAccess` enum

Create `OperandAccess` enum in `src/.../lang/psi/OperandAccess.java`:

```
public enum OperandAccess {
    NONE,
    READ,
    WRITE,
    READ_WRITE
}
```

#### 2. New `M68kOperandSize` record

Create `M68kOperandSize` record to map data size to effective byte count:

```
public record M68kOperandSize(M68kDataSize dataSize, int effectiveBytes) {}
```

#### 3. Extend `M68kMnemonic` record

Add all DFA metadata fields to the `M68kMnemonic` record:

```
public record M68kMnemonic(
    // ... existing fields ...
    OperandAccess firstOperandAccess,
    OperandAccess secondOperandAccess,
    List<M68kOperandSize> firstOperandSizes,
    List<M68kOperandSize> secondOperandSizes,
    Set<String> implicitReadRegisters,
    Set<String> implicitWriteRegisters,
    boolean modifiesProgramCounter,
    boolean modifiesStackPointer,
    boolean clobbersMemory
)
```

#### 4. Extend `MnemonicBuilder` in `M68kMnemonicRegistry`

Add builder methods for all DFA metadata:
```
.firstAccess(OperandAccess)
.secondAccess(OperandAccess)
.firstSizes(List<M68kOperandSize>)
.secondSizes(List<M68kOperandSize>)
.implicitReads(Set<String>)
.implicitWrites(Set<String>)
.modifiesProgramCounter(boolean)
.modifiesStackPointer(boolean)
.clobbersMemory(boolean)
```

#### 5. Extend runtime data file format

Add line 3 to `M68kMnemonicRegistryRuntimeData.txt` for operand access, and line 4 for additional DFA metadata:

**Line 3: Operand access direction**
```
"move",     {DA,AD},    BWL,    m68000up
-,          -,          -**00
READ,WRITE
```
Line 3 format: `first_operand_access[,second_operand_access]`

For READ_WRITE operands (e.g., `ADD D0,(A0)` where D0 is read then written):
```
"add",      {DA,D_},    BWL,    m68000up
-,          -,          C****
READ,READ_WRITE
```

**Line 4: Additional DFA metadata**

Line 4 captures the 6 DFA metadata properties from the table above. Format: semicolon-separated key-value pairs.

```
"jsr",      {CT},       UNS,    m68000up
-,          JUMP
READ,
modifies_pc=true;modifies_sp=true;implicit_writes:PC,A7
```

Line 4 format: `[modifies_pc=[true|false]];[modifies_sp=[true|false]];[clobbers_mem=[true|false]];[implicit_reads:reg1,reg2];[implicit_writes:reg1,reg2]`

For instructions with no implicit registers (most instructions):
```
"move",     {DA,AD},    BWL,    m68000up
-,          -,          -**00
READ,WRITE
-
```

For instructions with implicit reads (e.g., DBCC reads condition codes):
```
"dbcc",     {D_,LB},    WU,     m68000up
-,          BRANCH
READ_WRITE,
implicit_reads:CCR
```

For instructions with implicit writes (e.g., PEA writes A7):
```
"pea",      {EA},       BWL,    m68000up
-,          -,          -**00
READ,
modifies_sp=true;implicit_writes:A7
```

#### 6. Extend `M68kMnemonicRegistryRuntimeParser`

Parse the new line 3 (operand access) and line 4 (additional DFA metadata) formats and populate all new fields in M68kMnemonic.

#### 7. Extend `M68kMnemonicRegistryGeneratorTest`

Update `dumpCode()` to emit the new builder calls for access direction and sizes.

#### 8. New `M68kControlFlowInstruction` PSI interface

Create `M68kControlFlowInstruction` interface in `src/.../lang/psi/M68kControlFlowInstruction.java`, following the same pattern as `M68kPrivilegedInstruction`:

```
public interface M68kControlFlowInstruction extends M68kInstruction {
    default M68kMnemonic.ControlFlow getControlFlow() {
        M68kMnemonic mnemonic = M68kMnemonicRegistry.getInstance().find(this);
        return mnemonic != null ? mnemonic.controlFlow() : M68kMnemonic.ControlFlow.NOTHING;
    }
}
```

Update the grammar (`src/grammar/m68k.bnf`) to add `implements = "com.yanncebron.m68kplugin.lang.psi.M68kControlFlowInstruction"` to the instruction groups that affect control flow:
- `bCC_instructions` (BRA, BCC, etc.) — ControlFlow.BRANCH
- `dbCC_instructions` (DBRA, DBCC, etc.) — ControlFlow.BRANCH
- `jump_instructions` (JSR, JMP, RTS, RTE, RTD, RTR) — ControlFlow.JUMP, RETURN, TRAP_RETURN. BSR is ControlFlow.BRANCH (has fall-through), so it is handled via the `bcc_instruction_base` extends block.
- `trap_instruction` — ControlFlow.TRAP
- `stop_instruction` — ControlFlow.TRAP

After grammar changes, regenerate the parser. Then validate that all generated PSI types implementing `M68kControlFlowInstruction` have a non-NOTHING `controlFlow` value in their `M68kMnemonic` entry, and vice versa.

#### 9. Query API on `M68kMnemonic`

Add helper methods:
```
public Set<String> getReadRegisters(M68kInstruction instruction)
public Set<String> getWriteRegisters(M68kInstruction instruction)
public int getEffectiveSize(M68kOperand operand, M68kDataSize dataSize)
```

### CFG Design

The CFG is built on top of the DFA data model. It consists of basic blocks connected by edges representing control flow.

#### Basic Block

A basic block is a sequence of instructions with a single entry point and a single exit point. A new basic block starts at:
- The first instruction of a function (entry).
- Any instruction target of a branch or jump (label reference).
- The instruction immediately after a branch or jump (fall-through).

A basic block ends at:
- A branch instruction (`BRA`, `BCC`, `BHI`, etc.) — the last instruction in the block.
- A jump instruction (`JMP`) — terminates the block, no fall-through.
- A return instruction (`RTS`, `RTE`) — terminates the block, no fall-through.
- A trap instruction (`TRAP`, `STOP`) — terminates the block, no fall-through.
- The last instruction before the next basic block start.

#### CFG Edge Types

```
enum CfgEdgeType {
    FALL_THROUGH,   // normal sequential flow
    CONDITIONAL,    // conditional branch (BCC, BHI, etc.)
    UNCONDITIONAL,  // unconditional branch (BRA) or jump (JMP)
    CALL,           // JSR — flow goes to target, returns to fall-through
    RETURN,         // RTS/RTE — flow returns to caller
    TRAP            // TRAP/STOP — flow goes to trap handler
}
```

#### CFG Builder

The `M68kCfgBuilder` class takes a range of `M68kInstruction` elements and produces:

```
record CfgBlock(
    List<M68kInstruction> instructions,
    List<CfgEdge> outgoingEdges
)

record CfgEdge(
    CfgBlock target,
    CfgEdgeType type
)

record M68kCfg(
    List<CfgBlock> blocks,
    CfgBlock entryBlock,
    List<CfgBlock> exitBlocks  // blocks ending with RTS, RTE, JMP, TRAP, STOP
)
```

The builder algorithm:
1. Scan instructions to find all label targets (branch destinations).
2. Group instructions into basic blocks based on entry/exit rules.
3. Create edges between blocks based on instruction types and resolved label references.
4. Mark entry and exit blocks.

#### CFG and DFA Integration

The CFG enables DFA to flow register state across basic blocks:
- Each block computes its own read/write sets using the DFA API.
- The CFG edges determine how state flows between blocks.
- Unreachable code detection uses CFG traversal from the entry block.
- Dead write detection uses the CFG to find if a written register is read in any reachable successor block.

```
mermaid
graph LR
    A[M68kMnemonicRegistryRuntimeData.txt] --> B[M68kMnemonicRegistryRuntimeParser]
    C[vasm/opcodes.h] --> D[M68kMnemonicRegistryGeneratorTest]
    B --> E[M68kMnemonicRegistry]
    D --> E
    E --> F[M68kMnemonic record]
    F --> G[OperandAccess enum]
    F --> H[M68kOperandSize record]
    F --> I[DFA Analysis API]
    I --> J[M68kCfgBuilder]
    J --> K[M68kCfg]
    K --> L[Inspections: dead write, overlap, unreachable, etc.]
    K --> M[CFG Visualization]
```

### Routine Detection

Routine detection builds on top of the CFG to identify labeled code blocks as routines (functions/subroutines). A routine is a code block entered via JSR and exited via RTS/RTE.

#### Routine Identification

A label is considered a routine entry point if:
1. The label is a target of a JSR instruction (CALL edge in CFG).
2. The code block starting at the label contains at least one RTS or RTE instruction.

A routine boundary is defined by:
- **Entry**: The label that is the target of a JSR.
- **Exit**: Any instruction (RTS, RTE, RTE with offset, or RTD) that returns control to the caller.

#### Routine Data Model

```
record M68kRoutine(
    M68kLabel entryLabel,
    M68kCfg cfg,
    List<M68kInstruction> exitInstructions,  // RTS, RTE, RTD instructions
    List<M68kJsrs> callers  // JSR instructions that call this routine
)
```

#### Routine Detection Algorithm

1. **Collect JSR/BSR targets**: Scan all instructions in a file. For each JSR or BSR, resolve its label reference to find the target label.
2. **Build CFG for each candidate**: For each label that is a JSR target, build a CFG starting from that label.
3. **Check for return instructions**: A label is a routine if its CFG contains at least one exit block with RTS/RTE/RTD.
4. **Determine routine boundaries**: The routine ends at the next global label or at the end of the file.
5. **Validate return paths**: Check that all CFG exit blocks contain a return instruction. Flag exit blocks without returns as missing returns.

#### Orphaned Routine Detection

A routine is orphaned if no JSR instruction in the project targets its entry label. The detection algorithm:
1. Collect all routine entry labels.
2. Collect all JSR/BSR targets from the project.
3. Flag routines whose entry label is not in the JSR target set.

#### Missing Return Detection

A routine has a missing return if not all CFG exit blocks contain a return instruction. The detection algorithm:
1. Build the CFG for the routine.
2. Collect all exit blocks (blocks with no outgoing edges except RETURN).
3. Flag exit blocks that do not end with RTS/RTE/RTD.

#### Call/Return Mismatch Detection

A mismatch occurs when:
- A JSR targets a label that is not a routine (no RTS/RTE in the target's code block).
- An RTS/RTE appears without a matching JSR in the same scope.

```
mermaid
graph LR
    A[M68kFile] --> B[M68kRoutineDetector]
    B --> C[Collect JSR targets]
    C --> D[Build CFG for each candidate]
    D --> E[Check for returns]
    E --> F[List of M68kRoutine]
    F --> G[Inspections: orphaned, missing return, mismatch]
```

### Risks

- **Data volume**: 244 mnemonics × 2 operands × multiple data sizes each. The runtime data file will grow significantly.
- **Implicit register tracking**: Some instructions implicitly read/write registers (PC, A7/SP, condition codes). These must be enumerated per mnemonic variant.
- **Size override complexity**: Some operands have different effective sizes depending on the declared data size (e.g., MOVEA.W reads 16 bits but the A-register is always 32 bits). This requires per-operand, per-size mapping.
- **Memory addressing modes**: For memory operands, the effective size is the data size, but the address register used for calculation may also be modified (post-increment, pre-decrement). This requires per-addressing-mode size info, not just per-operand.

### Fact-Checked Issues

The following issues were found during fact-checking against the M68000 PRM and vasm `opcodes.h`:

**1. Missing implicit register data

The following mnemonics modify the stack pointer implicitly but have no line 2:
- `pea` — writes A7 (SP)
- `link` — writes A7 (SP)
- `unlk` — writes A7 (SP)

**2. Cycle count data unavailable**

The M68000 PRM does not contain instruction cycle count tables. The vasm sources do not contain cycle data. All cycle claims in optimization rules must be removed until a reliable source is identified.

#### Correctness Rules

| Rule | Description | Data Needed |
|------|-------------|-------------|
| `dead-store` | Register written but never read before next write | DFA write set, CFG |
| `uninitialized-read` | Register read before any write in basic block | DFA read set, CFG |
| `stale-condition-code` | Branch uses condition code not set by preceding instruction | `affected`, `tested` |
| `stack-imbalance` | LINK/UNLK mismatch or missing UNLK | `implicitWriteRegisters` (A7) |
| `size-mismatch` | Two operands access same register with different sizes | `firstOperandSizes`, `secondOperandSizes` |
| `overlapping-access` | Memory and register operands overlap | Addressing mode info |
| `privilege-violation` | Privileged instruction in user mode | `privilegedType` |
| `missing-return` | Routine exit block without RTS/RTE | CFG exit blocks |
| `orphaned-routine` | Routine with no incoming JSR calls | JSR targets |
| `call-return-mismatch` | JSR to non-routine or RTS without JSR | Routine detection |
| `address-register-word-access` | MOVEA.W, CMPA.W on address registers, TST.W on address registers (68020+) | Operand type, size (no DFA needed) |

# Delivery Steps

### Stage 1: DFA data model foundation
Add operand access, size, and control flow data to M68kMnemonic.
- Create `OperandAccess` enum (NONE, READ, WRITE, READ_WRITE) and `M68kOperandSize` record.
- Extend `M68kMnemonic` record with `firstOperandAccess`, `secondOperandAccess`, size lists, and implicit register sets.
- Update `MnemonicBuilder` with fluent methods for new fields.
- Extend runtime data file format with line 3 for DFA metadata.
- Update `M68kMnemonicRegistryRuntimeParser` to parse line 3.
- Create `M68kControlFlowInstruction` PSI interface and update grammar to add `implements` for control flow groups.
- Regenerate the parser and validate PSI hierarchy matches mnemonic `controlFlow` field.
- Populate runtime data for all 244 mnemonics with access direction, sizes, and implicit registers.
- Add query API helpers: `getReadRegisters()`, `getWriteRegisters()`, `getEffectiveSize()`.
- Add validation tests that all mnemonics have DFA data populated.

### Stage 2: CFG builder and loop detection
Build CFG from instructions, detect DBCC loops, and detect unreachable code.
- Create `CfgBlock`, `CfgEdge`, `CfgEdgeType`, and `M68kCfg` records in `src/.../lang/dfa/`.
- Create `M68kCfgBuilder` that forms basic blocks and connects them with edges.
- Detect DBCC loop boundaries by resolving branch targets directly from DBCC operands (no CFG required).
- Handle DBCC fall-through edges to the DBCC instruction, not past it.
- Use `M68kMnemonic.ControlFlow` to classify edges (BRANCH, JUMP, RETURN, TRAP).
- Create `M68kUnreachableCodeInspection` that uses CFG traversal to flag unreachable blocks.
- Add tests for CFG construction and unreachable code detection.

### Stage 3: Routine detection and inspections
Detect routines, orphaned routines, missing returns, and paired MOVEM issues.
- Create `M68kRoutine` record with entry label, CFG, exit instructions, and callers.
- Create `M68kRoutineDetector` that collects JSR/BSR targets and validates return paths.
- Create `M68kOrphanedRoutineInspection` for routines with no incoming calls.
- Create `M68kMissingReturnInspection` for routines with missing return paths.
- Create `M68kPairedMovemInspection` for non-symmetrical MOVEM save/restore pairs (independent of CFG/DFA).
- Create `M68kAddressRegisterWordAccessInspection` for suspicious word-size access to address registers (independent of CFG/DFA).
- Add tests for routine detection and inspections.

### Stage 4: Editor highlighting and visual markers
Add read/write highlighting, method separators, and exit point markers.
- Implement `ReadWriteAccessDetector` to highlight register references based on DFA read/write sets.
- Implement `CodeSeparatorProvider` to display separators before routine entry labels.
- Highlight routine exit points (RTS/RTE/RTD, JMP, TRAP, STOP) with an `Annotator`, distinguishing normal returns from abnormal exits.
- Extend highlighting to label references (xdef/xref analysis).

### Stage 5: Register analysis and call hierarchy
Add register-level analysis, call graphs, and inter-procedural tracking.
- Implement used/unused registers analysis for a selected code range.
- Implement register availability analysis with color-coded status (free, free until overwritten, unknown).
- Implement register remapping by swapping two registers in a selected range.
- Build call graphs showing callers and callees for each routine.
- Follow JSR/BSR into subroutines to determine register effects.
- Detect MOVEM and MOVE stack save/restore patterns to `-(sp)` and from `(sp)+`.

### Stage 6: Optimization suggestions and correctness inspections
Add pattern-based optimizations and correctness checks built on DFA/CFG data.
- Create optimization rule engine with static pattern matching (e.g., `CLR.L D0` → `MOVEQ.L #0,D0`).
- Use DFA read/write sets to validate semantic equivalence of optimizations.
- Use CFG basic block structure for pattern matching within blocks.
- Implement correctness inspections: dead stores, uninitialized reads, stale condition codes, stack imbalance, size mismatches, and privilege violations.