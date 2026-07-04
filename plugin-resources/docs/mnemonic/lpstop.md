# LPSTOP - Low-Power Stop

## Operation
If Supervisor State<br/>
&nbsp;&nbsp;Immediate Data → SR<br/>
&nbsp;&nbsp;Interrupt Mask → External Bus Interface (EBI)<br/>
&nbsp;&nbsp;STOP<br/>
Else TRAP

## Description
The immediate operand moves into the entire status register, the program
counter advances to point to the next instruction, and the processor stops fetching and
executing instructions. A CPU LPSTOP broadcast cycle is executed to CPU space $3
to copy the updated interrupt mask to the external bus interface (EBI). The internal
clocks are stopped.

Instruction execution resumes when a trace, interrupt, or reset exception occurs. A
trace exception will occur if the trace state is on when the LPSTOP instruction is
executed. If an interrupt request is asserted with a higher priority that the current
priority level set by the new status register value, an interrupt exception occurs;
otherwise, the interrupt request is ignored. If the bit of the immediate data
corresponding to the S-bit is off, execution of the instruction will cause a privilege
violation. An external reset always initiates reset exception processing.

## Condition codes
Set according to the imemdiate operand.