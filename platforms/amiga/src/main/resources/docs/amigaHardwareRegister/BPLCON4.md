| BIT# | [BPLCON4](BPLCON4.md)  | DESCRIPTION                                              |
|---|---|---|
| 15   | BPLAM7=0 | This 8 bit field is XOR'ed with the 8 bit plane color    |
| 14   | BPLAM6=0 | address, thereby altering the color address sent to the  |
| 13   | BPLAM5=0 | color table (x=1-8).                                     |
| 12   | BPLAM4=0 |                                                          |
| 11   | BPLAM3=0 |                                                          |
| 10   | BPLAM2=0 |                                                          |
| 09   | BPLAM1=0 |                                                          |
| 08   | BPLAM0=0 |                                                          |
|      |          |                                                          |
| 07   | ESPRM7=0 | 4 Bit field provides the 4 high order color table address|
| 06   | ESPRM6=0 | bits for even sprites: SPR0,SPR2,SPR4,SPR6. Default value|
| 05   | ESPRM5=0 | is 0001 binary. (x=7-4)                                  |
| 04   | ESPRM4=1 |                                                          |
|      |          |                                                          |
| 03   | OSPRM7=0 | 4 Bit field provides the 4 high order color table address|
| 02   | OSPRM6=0 | bits for odd sprites: SPR1,SPR3,SPR5,SPR7. Default value |
| 01   | OSPRM5=0 | is 0001 binary. (x=7-4)                                  |
| 00   | OSPRM4=1 |                                                          |