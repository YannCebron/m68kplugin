  move.l d0,<error descr="Usage of A7 register">a7</error>
  move.l d0,(<error descr="Usage of A7 register">a7</error>)
  move.l d0,-(<error descr="Usage of A7 register">a7</error>)
  move.l d0,(<error descr="Usage of A7 register">a7</error>)+
  move.l d0,4(<error descr="Usage of A7 register">a7</error>)
  move.l d0,4(<error descr="Usage of A7 register">a7</error>,d7)
  jmp 4(pc,<error descr="Usage of A7 register">a7</error>.l)