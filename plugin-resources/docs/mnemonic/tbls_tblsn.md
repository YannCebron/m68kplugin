# TBLS, TBLSN - Table Lookup and Interpolate (Signed)

## Operation
Rounded:<br/>
&nbsp;&nbsp;ENTRY(n) + {(ENTRY(n + 1) – ENTRY(n)) x Dx 7 – 0} ÷ 256 → Dx

Unrounded:<br/>
&nbsp;&nbsp;ENTRY(n) x 256 + {(ENTRY(n + 1) – ENTRY(n)) x Dx 7 – 0} → Dx

Where ENTRY(n) and ENTRY(n + 1) are either:
1. Consecutive entries in the table pointed to by the <ea> and
   indexed by Dx 15 – 8 π SIZE or;
2. The registers Dym, Dyn respectively.

## Description
The `TBLS` and `TBLSN` instructions allow the efficient use of piecewise linear
compressed data tables to model complex functions. The `TBLS` instruction has two
modes of operation: table lookup and interpolate mode and data register interpolate
mode.

For table lookup and interpolate mode, data register Dx 15 – 0 contains the
independent variable X. The effective address points to the start of a signed byte, word,
or long-word table containing a linearized representation of the dependent variable, Y,
as a function of X. In general, the independent variable, located in the low-order word
of Dx, consists of an 8-bit integer part and an 8-bit fractional part. An assumed radix
point is located between bits 7 and 8. The integer part, Dx 15 – 8, is scaled by the
operand size and is used as an offset into the table. The selected entry in the table is
subtracted from the next consecutive entry. A fractional portion of this difference is
taken by multiplying by the interpolation fraction, Dx 7 – 0 .The adjusted difference is
then added to the selected table entry. The result is returned in the destination data
register, Dx.

For register interpolate mode, the interpolation occurs using the Dym and Dyn registers
in place of the two table entries. For this mode, only the fractional portion, Dx 7 – 0, is
used in the interpolation, and the integer portion, Dx 15 – 8, is ignored. The register
interpolation mode may be used with several table lookup and interpolations to model
multidimensional functions.

Signed table entries range from – 2<sup>n – 1</sup> to 2<sup>n – 1</sup> – 1; whereas, unsigned table entries
range from 0 to 2<sup>n – 1</sup> where n is 8, 16, or 32 for byte, word, and long-word tables, respectively.

Rounding of the result is optionally selected via the "R" instruction field. If R = 0
(TABLE), the fractional portion is rounded according to the round-to-nearest algorithm.
The following table summerizes the rounding procedure:

| Adjusted Difference Fraction | Rounding Adjustment |
|:----------------------------:|:-------------------:|
|          &le; – 1/2          |         – 1         |
|   &gt; – 1/2 and &lt; 1/2    |         + 0         |
|           &ge; 1/2           |         + 1         |

The adjusted difference is then added to the selected table entry. The rounded result
is returned in the destination data register, Dx. Only the portion of the register
corresponding to the selected size is affected.

|          | 31&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;24 | 23&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;16 | 15&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8 | 7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0 |
|----------|:----------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------:|
| **BYTE** |                                              UNAFFECTED                                              |                                              UNAFFECTED                                              |                                             UNAFFECTED                                              |                                               RESULT                                               |
| **WORD** |                                              UNAFFECTED                                              |                                              UNAFFECTED                                              |                                               RESULT                                                |                                               RESULT                                               |
| **LONG** |                                                RESULT                                                |                                                RESULT                                                |                                               RESULT                                                |                                               RESULT                                               |

If R = 1 (`TBLSN`), the result is returned in register Dx without rounding. If the size is
byte, the integer portion of the result is returned in Dx 15 – 8; the integer portion of a
word result is stored in Dx 23 – 8; the least significant 24 bits of a long result are stored
in Dx 31 – 8. Byte and word results are sign-extended to fill the entire 32-bit register.

|          | 31&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;24 | 23&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;16 | 15&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;8 | 7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0 |
|----------|:----------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------:|
| **BYTE** |                                            SIGN_EXTENDED                                             |                                            SIGN_EXTENDED                                             |                                               RESULT                                                |                                              FRACTION                                              |
| **WORD** |                                            SIGN_EXTENDED                                             |                                                RESULT                                                |                                               RESULT                                                |                                              FRACTION                                              |
| **LONG** |                                                RESULT                                                |                                                RESULT                                                |                                               RESULT                                                |                                              FRACTION                                              |

**NOTE** The long-word result contains only the least significant 24 bits of integer precision.

For all sizes, the 8-bit fractional portion of the result is returned to the low byte of the
data register, Dx 7 – 0. User software can make use of the fractional data to reduce
cumulative errors in lengthy calculations or implement rounding algorithms different
from that provided by other forms of `TBLS`. The previously described assumed radix
point places two restrictions on the programmer:

1. Tables are limited to 257 entries in length.
2. Interpolation resolution is limited to 1/256, the distance between consecutive table
   entries. The assumed radix point should not, however, be construed by the
   programmer as a requirement that the independent variable be calculated as a
   fractional number in the range 0 < X < 255. On the contrary, X should be considered
   an integer in the range 0 < X < 65535, realizing that the table is actually a
   compressed representation of a linearized function in which only every 256th
   value is actually stored in memory.   

## Condition codes
| X | N | Z | V | C |
|:-:|:-:|:-:|:-:|:-:|
| – | * | * | * | 0 |