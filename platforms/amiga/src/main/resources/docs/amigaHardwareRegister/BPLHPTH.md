When UHRES is enabled, this pointer comes out on the 2nd 'free' cycle
after the start of each horizontal line. Its modulo is added every
time it comes out. 'free' means priority above the copper and below
the fixed stuff (audio, sprites, ...).
[BPLHDAT](BPLHDAT.md) comes out as an identifier on the RGA lines when the pointer
address is valid so that external detectors can use this to do the
special cycle for the VRAMs, the SHRHDAT gets the first and third
free cycles.