Register bit assignment

| BIT# | 15 | 14 | 13 | 12 | 11 | 10 | 09 | 08 | 07 | 06 | 05 | 04 | 03 | 02 | 01 | 00 |
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| USE |  X |  X |  X |  X |  X |  X |  X |  X | H8 | H7 | H6 | H5 | H4 | H3 | H2 |  X |

(X bits should always be driven with 0 to maintain upward
compatibility)

The tables below show the start and stop timing for
different register contents

[DDFSTRT](DDFSTRT.md) (Left edge of display data fetch)

| PURPOSE          | H8 | H7 | H6 | H5 | H4 |
|---|---|---|---|---|---|
| Extra wide (max) | 0  | 0  | 1  | 0  | 1  |
| wide             | 0  | 0  | 1  | 1  | 0  |
| normal           | 0  | 0  | 1  | 1  | 1  |
| narrow           | 0  | 1  | 0  | 0  | 0  |


[DDFSTOP](DDFSTRT.md) (Right edge of display data fetch)

| PURPOSE          | H8 | H7 | H6 | H5 | H4 |
|---|---|---|---|---|---|
| narrow           | 1  | 1  | 0  | 0  | 1  |
| normal           | 1  | 1  | 0  | 1  | 0  |
| wide (max)       | 1  | 1  | 0  | 1  | 1  |


Note that these numbers will vary with variable beam counter
mode set (the maxes and mins, that is).