| BIT#  | FUNCTION                   |
|:-----:|:--------------------------:|
| 15    | _(unused)_                   |
| 14    | HARDDIS                    |
| 13    | LPENDIS                    |
| 12    | VARVBEN                    |
| 11    | LOLDIS                     |
| 10    | CSCBEN                     |
| 9     | VARVSYEN                   |
| 8     | VARHSYEN                   |
| 7     | VARBEAMEN                  |
| 6     | DUAL                       |
| 5     | PAL                        |
| 4     | VARCSYEN                   |
| 3     | (unused, formerly BLANKEN) |
| 2     | CSYTRUE                    |
| 1     | VSYTRUE                    |
| 0     | HSYTRUE                    |


HARDDIS = This bit is used to disable the hardwired vertical horizontal
window limits. It is cleared upon reset.

LPENDIS = When this bit is a low and LPE ([BPLCON0](BPLCON0.md),BIT 3) is enabled, the
light-pen latched value (beam hit position) will be read by
[VHPOSR](VHPOSR.md), [VPOSR](VPOSR.md) and [HHPOSR](HHPOSR.md). When
the bit is a high the light-pen latched value is ignored and
the actual beam counter position is read by [VHPOSR](VHPOSR.md), [VPOSR](VPOSR.md), and
[HHPOSR](HHPOSR.md).

VARVBEN = Use the comparator generated vertical blank (from [VBSTRT](VBSTOP.md), [VBSTOP](VBSTOP.md))
to run the internal chip stuff-sending RGA signals to Denise,
starting sprites, resetting light pen. It also disables the hard
stop on the vertical display window.

LOLDIS  = Disable long line/short toggle. This is useful for DUAL mode
where even multiples are wanted, or in any single display
where this toggling is not desired.

CSCBEN  = The variable composite sync comes out on the HSY pin, and the
variable composite blank comes out on the VSY pin. The idea is
to allow all the information to come out of the chip for a
DUAL mode display. The normal monitor uses the normal composite
sync, and the variable composite sync & blank come out the HSY &
VSY pins. The bits VARVSYEN & VARHSYEN (below) have priority over
this control bit.

VARVSYEN = Comparator VSY -> VSY pin. The variable VSY is set vertically on
[VSSTRT](HSSTRT.md), reset vertically on [VSSTOP](VSSTOP.md), with the horizontal position
for set set & reset [HSSTRT](HSSTRT.md) on short fields (all fields are short
if LACE = 0) and [HCENTER](HCENTER.md) on long fields (every other field if
LACE = 1).

VARHSYEN = Comparator HSY -> HSY pin. Set on [HSSTRT](HSSTRT.md) value, reset on [HSSTOP](HSSTOP.md)
value.

VARBEAMEN = Enables the variable beam counter comparators to operate
(allowing different beam counter total values) on the main horiz
counter. It also disables hard display stops on both horizontal
and vertical.

DUAL    = Run the horizontal comparators with the alternate horizontal beam
counter, and starts the UHRES pointer chain with the reset of
this counter rather than the normal one. This allows the UHRES
pointers to come out more than once in a horizontal line,
assuming there is some memory bandwidth left (it doesn't work in
640*400*4 interlace mode). Also, to keep the two displays synced,
the horizontal line lengths should be multiples of each other.
If you are amazingly clever, you might not need to do this.

PAL     = Set appropriate decodes (in normal mode) for PAL. In variable
beam counter mode this bit disables the long line/short line
toggle- ends up short line.

VARCSYEN = Enables CSY from the variable decoders to come out the CSY
(VARCSY is set on [HSSTRT](HSSTRT.md) match always, and also on [HCENTER](HCENTER.md)
match when in vertical sync. It is reset on [HSSTOP](HSSTOP.md) match when VSY
and on both [HBSTRT](HBSTOP.md) & [HBSTOP](HBSTOP.md) matches during VSY. A reasonable
composite can be generated by setting [HCENTER](HCENTER.md) half a horiz line
from [HSSTRT](HSSTRT.md), and [HBSTOP](HBSTOP.md) at ([HSSTOP](HSSTOP.md)-[HSSTRT](HSSTRT.md)) before [HCENTER](HCENTER.md), with
[HBSTRT](HBSTOP.md) at ([HSSTOP](HSSTOP.md)-[HSSTRT](HSSTRT.md)) before [HSSTRT](HSSTRT.md).

HSYTRUE, VSYTRUE, CSYTRUE = These change the polarity of the
HSY*, VSY*, & CSY* pins to HSY, VSY, & CSY respectively for
input & output.