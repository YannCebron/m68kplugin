These two control registers are used together to control Blitter
operations. There are 2 basic modes, area and line, which are
selected by bit 0 of [BLTCON1](BLTCON0.md), as shown below.

[BLTCON Helper](https://bltcon-helper.grahambates.com/) by grahambates

## AREA MODE ("normal")


| BIT# | [BLTCON0](BLTCON0.md) | [BLTCON1](BLTCON0.md) |
|:----:|:-------:|:-------:|
| 15   | ASH3    | BSH3    |
| 14   | ASH2    | BSH2    |
| 13   | ASH1    | BSH1    |
| 12   | ASA0    | BSH0    |
| 11   | USEA    | 0       |
| 10   | USEB    | 0       |
| 09   | USEC    | 0       |
| 08   | USED    | 0       |
| 07   | LF7     | DOFF    |
| 06   | LF6     | 0       |
| 05   | LF5     | 0       |
| 04   | LF4     | EFE     |
| 03   | LF3     | IFE     |
| 02   | LF2     | FCI     |
| 01   | LF1     | DESC    |
| 00   | LF0     | LINE(=0)|


## LINE MODE (line draw)


| BIT# | [BLTCON0](BLTCON0.md) | [BLTCON1](BLTCON0.md)  |
|:----:|:-------:|:--------:|
| 15   | ASH3    | BSH3     |
| 14   | ASH2    | BSH2     |
| 13   | ASH1    | BSH1     |
| 12   | ASH0    | BSH0     |
| 11   | 1       | 0        |
| 10   | 0       | 0        |
| 09   | 1       | 0        |
| 08   | 1       | 0        |
| 07   | LF7     | DPFF     |
| 06   | LF6     | SIGN     |
| 05   | LF5     | OVF      |
| 04   | LF4     | SUD      |
| 03   | LF3     | SUL      |
| 02   | LF2     | AUL      |
| 01   | LF1     | SING     |
| 00   | LF0     | LINE(=1) |



| NAME     | DESCRIPTION                               |
|:--------:|-------------------------------------------|
| ASH3-0   | Shift value of A source                   |
| BSH3-0   | Shift value of B source and line texture  |
| USEA     | Mode control bit to use source A          |
| USEB     | Mode control bit to use source B          |
| USEC     | Mode control bit to use source C          |
| USED     | Mode control bit to use destination D     |
| LF7-0    | Logic function minterm select lines       |
| EFE      | Exclusive fill enable                     |
| IFE      | Inclusive fill enable                     |
| FCI      | Fill carry input                          |
| DESC     | Descending (dec address) control bit      |
| LINE     | Line mode control bit                     |
| SIGN     | Line draw sign flag                       |
| OVF      | Line/draw r/l word overflow flag          |
| SUD      | Line draw, Sometimes up or down (=AUD)    |
| SUL      | Line draw, Sometimes up or left           |
| AUL      | Line draw, Always up or left              |
| SING     | Line draw, Single bit per horiz line      |
| DOFF     | Disables the D output for external ALUs.  |
|          | The cycle occurs normally, but the data   |
|          | bus is tristate (hires chips only).       |