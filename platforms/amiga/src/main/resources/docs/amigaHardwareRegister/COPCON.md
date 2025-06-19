This is a 1 bit register that when set true, allows the coprocessor
to access the Blitter hardware. This bit is cleared power on reset,
so that the coprocessor cannot access the Blitter hardware.


| BIT# | NAME  | FUNCTION                                        |
|:----:|:-----:|-------------------------------------------------|
| 01   | CDANG | Coprocessor danger mode. Allows coprocessor     |
|      |       | access to all RGA registers if true.            |
|      |       | (if 0, access to RGA>7E)                        |
|      |       | (On old chips access to only RGA>3E if CDANG=1) |
|      |       | (see [VPOSR](VPOSR.md))                     |