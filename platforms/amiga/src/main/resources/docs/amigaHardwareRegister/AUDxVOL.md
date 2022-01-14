This reg contains the volume setting for audio channel x.
Bits 6,5,4,3,2,1,0 specify 65 linear volume levels as shown below.


| BITS   | USE                                              |
|---|---|
| 15-07  | _(unused)_                                         |
|  06    | Forces volume to max (64 ones, no zeros)         |
| 05-00  | Sets one of the 64 levels (000000 = no output,   |
|        | 111111 = 63 ones, one zero)                      |