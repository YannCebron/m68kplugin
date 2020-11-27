  move.l d0,<warning descr="Usage of A7 register">a7</warning>
  move.l d0,(<warning descr="Usage of A7 register">a7</warning>)
  move.l d0,-(<warning descr="Usage of A7 register">a7</warning>)
  move.l d0,(<warning descr="Usage of A7 register">a7</warning>)+
  move.l d0,4(<warning descr="Usage of A7 register">a7</warning>)
  move.l d0,4(<warning descr="Usage of A7 register">a7</warning>,d7)
  jmp 4(pc,<warning descr="Usage of A7 register">a7</warning>.l)