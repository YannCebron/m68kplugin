# Alternate Function Code Registers (`SFC` and `DFC`)

The SFC and DFC are 32-bit registers where only the lower 3 bits are used;
the upper 28 bits are reserved and ignored. These 3-bit function codes can be
considered extensions of the 32-bit logical address that optionally provides as many as eight
4-Gbyte address spaces. The processor automatically generates function codes to select
address spaces for data and programs at the user and supervisor modes. Certain
instructions use SFC and DFC to specify the function codes for operations.
