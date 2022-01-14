This register contains the length (number of words) of
disk DMA data. It also contains 2 control bits. These
are a DMA enable bit, and a DMA direction (read/write) bit.


| BIT# | FUNCTION | DESCRIPTION                        |
|---|---|---|
| 15   | DMAEN    | Disk DMA enable                    |
| 14   | WRITE    | Disk write (RAM or disk) if 1      |
| 13-0 | LENGTH   | Length (# of words) of DMA data.   |