| BIT# | 15 | 14 | 13 | 12 | 11 | 10 | 09 | 08 | 07 | 06 | 05 | 04 | 03 | 02 | 01 | 00 |
|------|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|
| USE | X | X | H10 | H1 | H0 | V10 | V9 | V8 | X | X | H10 | H1 | H0 | V10 | V9 | V8 |

15 – 08 = STOP / 07 – 00 = START

Take care (X) bits should always be written to 0 to
maintain upwards compatibility. H1 and H0 values define
70ns amd 35ns increments respectively, and new LISA bits.

  > Note: In all 3 display window registers, horizontal bit
positions have been renamed to reflect HIRES pixel
increments, e.g. what used to be called H0 is now referred to as H2.