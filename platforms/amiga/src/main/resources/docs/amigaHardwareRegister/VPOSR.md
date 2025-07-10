| BIT# | 15 | 14 | 13 | 12 | 11 | 10 | 09 | 08 | 07 | 06 | 05 | 04 | 03 | 02 | 01 | 00 |
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| USE | LOF | I6 | I5 | I4 | I3 | I2 | I1 | I0 | LOL | -- | -- | -- | -- | V10 | V9 | V8 |

### LOF
Long frame (auto toggle control bit in [BPLCON0](BPLCON0.md))

### I0, I1, I2, I3, I4, I5, I6
Chip identification


| Chip                                      | Value           |
|-------------------------------------------|:---------------:|
| 8361 (Regular) or 8370 (Fat) (Agnus-NTSC) | 10              |
| 8367 (PAL) or 8371 (Fat-PAL) (Agnus-PAL)  | 00              |
| 8372 (Fat-hr) (agnushr),through rev 4     | 20 PAL, 30 NTSC |
| 8372 (Fat-hr) (agnushr),rev 5             | 22 PAL, 31 NTSC |
| 8374 (Alice) through rev 2                | 22 PAL, 32 NTSC |
| 8374 (Alice) rev 3 through rev 4          | 23 PAL, 33 NTSC |


### LOL
Long line bit. When low, it indicates short raster line.
v9,10 -- hires chips only (20,30 identifiers)