| BIT# | 15 | 14 | 13 | 12 | 11 | 10 | 09 | 08 | 07 | 06 | 05 | 04 | 03 | 02 | 01 | 00 |
|------|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|
| USE | x | h14 | h13 | h12 | h11 | h10 | h9 | h8 | h7 | h6 | h5 | h4 | h3 | h2 | h1 | h0 |

These are the Blitter size regs for blits larger than the earlier
chips could accept. The original commands are retained for
compatibility. **BLTSIZV** should be written first, followed by **BLTSIZH**,
which starts the Blitter. **BLTSIZV** need not be rewritten for
subsequent bits if the vertical size is the same. Max size of
blit 32k pixels * 32k lines, x's should be written to 0 for
upward compatibility.