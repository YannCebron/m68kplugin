These registers contain the modulos for the odd and
even bit planes. A modulo is a number that is automatically
added to the address at the end of each line,
in order that the address then points to the start of
the next line. Since they have separate modulos, the
odd and even bit planes may have sizes that are
different from each other, as well as different from
the display window size.
If scan-doubling is enabled, [BPL1MOD](BPLxMOD.md) serves as the
primary bitplane modulos and [BPL2MOD](BPLxMOD.md) serves as the
alternate. Lines whose LSBs of beam counter and [DIWSTRT](DIWSTRT.md)
match are designated primary, whereas lines whose LSBs
don't match are designated alternate.ted alternate.