The patterns in these two registers (see also: **BLTAFWM**)
are "anded" with the first and last words of each line of data
from Source A into the Blitter. A zero in any bit overrides
data from Source A. These registers should be set to all
"ones" for fill mode or for line drawing mode.