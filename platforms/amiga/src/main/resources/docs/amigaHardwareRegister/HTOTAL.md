| BIT# | 15 | 14 | 13 | 12 | 11 | 10 | 09 | 08 | 07 | 06 | 05 | 04 | 03 | 02 | 01 | 00 |
|------|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|
| USE | x | x | x | x | x | x | x | x | h8 | h7 | h6 | h5 | h4 | h3 | h2 | h1 |

(x's should be driven to 0 for upward compatibility)

Horiz line has these many + 1 280nS increments. If the
pal bit & LOLDIS are not high, long line/short line toggle
will occur, and there will be this many +2 every other line.
Active if VARBEAMEN=1 or DUAL+1.