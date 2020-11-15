* not declared yet
  <error descr="Cannot resolve macro 'macro1'">macro1</error>

macro1 macro
  endm

macro2 macro
  endm

  macro1
  macro2

  <error descr="Cannot resolve macro 'INVALID_MACRO_NAME'">INVALID_MACRO_NAME</error>