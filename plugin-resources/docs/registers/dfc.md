# Alternate Function Code Registers (`SFC` and `DFC`)

The SFC and DFC are 32-bit registers with only bits 0-2 implemented. These bits contain
the address space values for the read or write operands of MOVES, PFLUSH, and PTEST
instructions. Values transfer to and from the SFC and DFC by using the MOVEC instruction.
These are long-word transfers; the upper 29 bits are read as zeros and are ignored when
written.

Function codes can be considered extensions of the 32-bit logical address that optionally
provides as many as eight 4-Gbyte address spaces. The processor automatically generates
function codes to select address spaces for data and programs at the user and supervisor
modes. Certain instructions use SFC and DFC to specify the function codes for operations.
