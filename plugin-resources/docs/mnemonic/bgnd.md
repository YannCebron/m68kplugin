# BGND - Enter Background Mode

## Operation
If Background Mode Enabled<br/>
&nbsp;&nbsp;Then Enter Background Mode<br/>
Else Format/Vector Offset → – (SSP);<br/>
&nbsp;&nbsp;PC → – (SSP)<br/>
&nbsp;&nbsp;SR → – (SSP)<br/>
&nbsp;&nbsp;(Vector) → PC

## Description
The processor suspends instruction execution and enters background mode
if background mode is enabled. The freeze output is asserted to acknowledge entrance
into background mode. Upon exiting background mode, instruction execution
continues with the instruction pointed to by the current program counter.

If background mode is not enabled, the processor initiates illegal instruction exception processing.
The vector number is generated to reference the illegal instruction exception vector.
Refer to the appropriate user’s manual for detailed information on background mode.

## Condition codes
Not affected.