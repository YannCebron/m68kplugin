The original Denise (8362) does not have this register, so
whatever value is left over on the bus from the last cycle
will be there. ECS Denise (8373) returns hex (fc) in the lower
8 bits. Lisa returns hex (f8). The upper 8 bits of this
register are loaded from the serial mouse bus, and are
reserved for future hardware implementation.

The 8 low-order bits are encoded as follows:


| BIT# | Description                                              |
|:----:|----------------------------------------------------------|
| 7-4  | Lisa/Denise/ECS Denise Revision level (decrement to      |
|      | bump revision level, hex F represents 0th rev. level).   |
|   3  | Maintain as a 1 for future generation                    |
|   2  | When low indicates AA feature set (LISA)                 |
|   1  | When low indicates ECS feature set (LISA or ECS DENISE)  |
|   0  | Maintain as a 1 for future generation                    |