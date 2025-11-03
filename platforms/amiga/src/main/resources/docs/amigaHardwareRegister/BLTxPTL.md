This pair of registers ([BLTxPTH](BLTxPTL.md)/[BLTxPTL](BLTxPTL.md))
contains the 20 bit address of Blitter source (X=A,B,C) or dest.
(x=D) DMA data. This pointer must be preloaded with the
starting address of the data to be processed by the Blitter. After
the Blitter is finished it will contain the last data address
(plus increment and modulo).