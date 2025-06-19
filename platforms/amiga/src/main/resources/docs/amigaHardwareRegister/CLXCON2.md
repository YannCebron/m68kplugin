This reg controls when bit planes 7 and 8 are included in collision
detection, and their required state if included. Contents of this
register are reset by a write to [CLXCON](CLXCON.md).

BITS INITIALIZED BY RESET


| BIT#  | FUNCTION | DESCRIPTION                                   |
|:-----:|:--------:|-----------------------------------------------|
| 15-08 |          | _(unused)_                                      |
| 07    | ENBP8    | Enable bit plane 8 (match reqd. for collision)|
| 06    | ENBP7    | Enable bit plane 7 (match reqd. for collision)|
| 05-02 |          | _(unused)_                                      |
| 01    | MVBP8    | Match value for bit plane 8 collision         |
| 00    | MVBP7    | Match value for bit plane 7 collision         |


  > Note: Disabled bit planes cannot prevent collisions. Therefore if all
bitplanes are disabled, collision will be continuous, regardless
of the match values.