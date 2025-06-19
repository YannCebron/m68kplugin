| BIT# | [BPLCON3](BPLCON3.md)    | DESCRIPTION                                            |
|:----:|:----------:|--------------------------------------------------------|
| 15   | BANK2=0    | BANKx = Selects one of eight color banks, x=0-2.       |
| 14   | BANK1=0    |                                                        |
| 13   | BANK0=0    |                                                        |
|      |            |                                                        |
| 12   | PF2OF2=0   | Determine bit plane color table offset when playfield 2|
| 11   | PF2OF1=1   | has priority in dual playfield mode, see table below.  |
| 10   | PF2OF0=1   |                                                        |
|      |            |                                                        |
| 09   | LOCT=0     | Dictates that subsequent color palette values will be  |
|      |            | written to a second 12- bit color palette, constituting|
|      |            | the RGB low minus order bits. Writes to the normal hi  |
|      |            | minus order color palette automatically copied to the |
|      |            | low order for backwards compatibility.                 |
| 08   | X          | Don't care - but drive to 0 for upward compatibility!  |
|      |            |                                                        |
| 07   | SPRES1=0   | Determine resolution of all 8 sprites (SPRES1,SPRS0):  |
| 06   | SPRES0=0   | 0,0 = ECS defaults (LORES, HIRES=140ns, SHRES=70ns)    |
|      |            | 0,1 = LORES (140ns)                                    |
|      |            | 1,0 = HIRES (70ns)                                     |
|      |            | 1,1 = SHRES (35ns)                                     |
|      |            |                                                        |
| 05   | BRDRBLNK=0 | "Border area" is blanked instead of color (0).         |
|      |            | Disabled when ECSENA low.                              |
| 04   | BRDNTRAN=0 | "Border area" is non minus transparent (ZD pin is low  |
|      |            | when border is displayed). Disabled when ECSENA low.   |
| 03   | X          | Don't care - but drive to 0 for upward compatibility!  |
| 02   | ZDCLKEN=0  | ZD pin outputs a 14MHz clock whose falling edge        |
|      |            | coincides with hires (7MHz) video data. This bit when  |
|      |            | set disables all other ZD functions.                   |
|      |            | Disabled when ECSENA low.                              |
| 01   | BRDSPRT=0  | Enables sprites outside the display window.            |
|      |            | Disabled when ECSENA low.                              |
| 00   | EXTBLKEN=0 | Causes BLANK output to be programmable instead of      |
|      |            | reflecting internal fixed decodes.                     |
|      |            | Disabled when ECSENA low.                              |


## PF20F2-0 // AFFECTED BITPLANE (8-1) // OFFSET

| PF2OF2 | PF2OF1 | PF2OF0 |  | 8 | 7 | 6 | 5 | 4 | 3 | 2 | 1 |  | OFFSET (decimal) |
|:------:|:------:|:------:|--|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|--|------------------|
| 0      | 0      | 0      |  | - | - | - | - | - | - | - | - |  | none             |
| 0      | 0      | 1      |  | - | - | - | - | - | - | 1 | - |  | 2                |
| 0      | 1      | 0      |  | - | - | - | - | - | 1 | - | - |  | 4                |
| 0      | 1      | 1      |  | - | - | - | - | - | 1 | - | - |  | 8 (default)      |
| 1      | 0      | 0      |  | - | - | - | 1 | - | - | - | - |  | 16               |
| 1      | 0      | 1      |  | - | - | 1 | - | - | - | - | - |  | 32               |
| 1      | 1      | 0      |  | - | 1 | - | - | - | - | - | - |  | 64               |
| 1      | 1      | 1      |  | 1 | - | - | - | - | - | - | - |  | 128              |