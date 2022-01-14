| BIT#  | Name    |
|---|---|
| 15    | BPLHWRM |
| 14-11 | _(unused)_|
| 10-0  | V10-V0  |


BPLHWRM = Swaps the polarity of ARW* when the **BPLHDAT** comes out so
that external devices can detect the RGA and put things into memory
(ECS and later versions).