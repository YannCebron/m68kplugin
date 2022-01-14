| BIT#  | SYM      | FUNCTION                                       |
|---|---|---|
| 15-08 | EV7-EV0  | End (stop) vert. value. Low 8 bits             |
| 07    | ATT	    | Sprite attach control bit (odd sprites only)   |
| 06    | SV9	    | Start vert value 10th bit.                     |
| 05    | EV9      | End (stop) vert. value 10th bit                |
| 04    | SH1=0    | Start horiz. value, 70nS increment             |
| 03    | SH0=0    | Start horiz. value, 35nS increment             |
| 02    | SV8      | Start vert. value 9th bit                      |
| 01    | EV8      | End (stop) vert. value 9th bit                 |
| 00    | SH2      | Start horiz. value, 140nS increment            |


These 2 registers work together as position, size and
feature sprite control registers. They are usually loaded
by the sprite DMA channel, during horizontal blank,
however they may be loaded by either processor any time.
Writing to **SPRxCTL** disables the corresponding sprite.