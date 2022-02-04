| BIT# | [BPLCON0](BPLCON0.md) | DESCRIPTION                                             |
|---|---|---|
| 15   | HIRES   | HIRES = High resolution (640*200/640*400 interlace) mode|
|      |         |                                                         |
| 14   | BPU2    | Bit plane use code 0000-1000 (NODE thru 8 inclusive)    |
| 13   | BPU1    |                                                         |
| 12   | BPU0    |                                                         |
|      |         |                                                         |
| 11   | HAM     | Hold and modify mode, now using either 6 or 8 bit       |
|      |         | planes.                                                 |
| 10   | DPF     | Double playfield (PF1=odd / PF2=even bit planes)        |
|      |         | now available in all resolutions.                       |
|      |         | If BPU=6 and HAM=0 and DPF=0 a special mode is          |
|      |         | defined that allows bitplane 6 to cause an intensity    |
|      |         | reduction of the other 5 bitplanes. The color           |
|      |         | register output selected by 5 bitplanes is shifted      |
|      |         | to half intensity by the 6th bit plane. This is         |
|      |         | called EXTRA-HALFBRITE Mode.                            |
| 09   |  COLOR  | Enables color burst output signal                       |
| 08   |  GAUD   | Genlock audio enable. This level appears on the ZD      |
|      |         | pin on denise during all blanking periods, unless ZDCLK |
|      |         | bit is set.                                             |
| 07   |  UHRES  | Ultrahi res enables the UHRES pointers (for 1k*1k) (also|
|      |         | needs bits in [DMACON](DMACON.md) (hires chips only).                |
|      |         | Disables hard stops for vert, horiz display windows.    |
| 06   |  SHRES  | Super hi-res mode (35ns pixel width)                    |
| 05   | BYPASS=0| Bitplanes are scrolled and prioritized normally, but    |
|      |         | bypass color table and 8 bit wide data appear on R(7:0).|
| 04   |  BPU3=0 | See above (BPU0/1/2)                                    |
| 03   |  LPEN   | Light pen enable (reset on power up)                    |
| 02   |  LACE   | Interlace enable (reset on power up)                    |
| 01   |  ERSY   | External resync (HSYNC, VSYNC pads become inputs)       |
|      |         | (reset on power up)                                     |
| 00   | ECSENA=0| When low (default), the following bits in [BPLCON3](BPLCON3.md) are   |
|      |         | disabled: BRDRBLNK, BRDNTRAN, ZDCLKEN, BRDSPRT, and     |
|      |         | EXTBLKEN. These 5 bits can always be set by writing     |
|      |         | to [BPLCON3](BPLCON3.md), however their effects are inhibited until   |
|      |         | ECSENA goes high. This allows rapid context switching   |
|      |         | between pre-ECS viewports and new ones.                 |